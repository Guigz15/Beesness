package com.uqac.beesness.controller;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.uqac.beesness.R;
import com.uqac.beesness.database.DAOApiaries;
import com.uqac.beesness.database.DAOBeehives;
import com.uqac.beesness.database.DAOHoneySuper;
import com.uqac.beesness.database.DAOVisits;
import com.uqac.beesness.databinding.FragmentBeehiveDetailsMainBinding;
import com.uqac.beesness.model.ApiaryModel;
import com.uqac.beesness.model.BeehiveModel;
import com.uqac.beesness.model.HoneySuperModel;
import com.uqac.beesness.model.VisitModel;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import android.provider.CalendarContract.Events;

public class BeehiveDetailsMainFragment extends Fragment {

    private FragmentBeehiveDetailsMainBinding binding;
    private ImageSlider imageSlider;
    private ImageButton addPictureButton, addHoneySuperButton, addVisitButton;
    private DAOBeehives daoBeehives;
    private String idBeehive;
    private HoneySuperAdapter honeySuperAdapter;
    private DAOHoneySuper daoHoneySuper;
    private VisitsAdapter visitsAdapter;
    private DAOVisits daoVisits;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBeehiveDetailsMainBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        daoBeehives = new DAOBeehives();
        idBeehive = ((BeehiveDetailsActivity) requireActivity()).getIdBeehive();

        addPictureButton = binding.addPictures;
        addPictureButton.setOnClickListener(v -> selectImage(getContext()));
        imageSlider = binding.imageSlider;
        List<SlideModel> slideModels = new ArrayList<>();
        daoBeehives.find(idBeehive).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    BeehiveModel beehive = dataSnapshot.getValue(BeehiveModel.class);
                    assert beehive != null;
                    if (beehive.getPicturesUrl().isEmpty())
                        binding.imageSlider.setVisibility(View.GONE);
                    else {
                        binding.imageSlider.setVisibility(View.VISIBLE);
                        slideModels.clear();
                        beehive.getPicturesUrl().forEach((key, value) ->
                                slideModels.add(new SlideModel(value, ScaleTypes.CENTER_CROP))
                        );
                        imageSlider.setImageList(slideModels, ScaleTypes.CENTER_CROP);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addHoneySuperButton = binding.addHoneySuper;
        addHoneySuperButton.setOnClickListener(v -> addHoneySuperDialog(requireActivity()));
        daoHoneySuper = new DAOHoneySuper();
        Query query = daoHoneySuper.findAllForBeehive(idBeehive);
        RecyclerView honeySuperRecyclerView = root.findViewById(R.id.honey_super_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        honeySuperRecyclerView.setItemAnimator(null);
        FirebaseRecyclerOptions<HoneySuperModel> options = new FirebaseRecyclerOptions.Builder<HoneySuperModel>()
                .setQuery(query, HoneySuperModel.class)
                .build();
        honeySuperAdapter = new HoneySuperAdapter(options);
        honeySuperRecyclerView.setLayoutManager(linearLayoutManager);
        honeySuperRecyclerView.setAdapter(honeySuperAdapter);

        addVisitButton = binding.addVisit;
        addVisitButton.setOnClickListener(v -> addVisitDialog(requireActivity()));
        daoVisits = new DAOVisits();
        Query queryVisits = daoVisits.findAllForBeehive(idBeehive);
        RecyclerView visitsRecyclerView = root.findViewById(R.id.visit_list);
        LinearLayoutManager linearLayoutManagerVisit = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        visitsRecyclerView.setItemAnimator(null);
        FirebaseRecyclerOptions<VisitModel> visitsOptions = new FirebaseRecyclerOptions.Builder<VisitModel>()
                .setQuery(queryVisits, VisitModel.class)
                .build();
        visitsAdapter = new VisitsAdapter(visitsOptions);
        visitsRecyclerView.setLayoutManager(linearLayoutManagerVisit);
        visitsRecyclerView.setAdapter(visitsAdapter);

        return root;
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                editHoneySuperDialog(requireActivity(), honeySuperAdapter.getItem(item.getGroupId()));
                break;
            case 1:
                daoHoneySuper.delete(honeySuperAdapter.getItem(item.getGroupId()).getIdHoneySuper()).addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        Toast.makeText(getContext(), "Suppression réussie", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getContext(), "Erreur lors de la suppression", Toast.LENGTH_SHORT).show();
                });
                break;
            case 2:
                editVisitDialog(requireActivity(), visitsAdapter.getItem(item.getGroupId()));
                break;
            case 3:
                daoVisits.delete(visitsAdapter.getItem(item.getGroupId()).getIdVisit()).addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        Toast.makeText(getContext(), "Suppression réussie", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getContext(), "Erreur lors de la suppression", Toast.LENGTH_SHORT).show();
                });
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void selectImage(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(requireActivity(), new String[] {Manifest.permission.CAMERA}, 100);

        final CharSequence[] options = { "Prendre une photo", "Choisir depuis la galerie" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Ajouter une photo");

        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals("Prendre une photo")) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                launcher.launch(takePicture);
            } else if (options[item].equals("Choisir depuis la galerie")) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                launcher.launch(intent);
            }
        });
        builder.show();
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
        result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    if (data.getData() == null) {
                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                        daoBeehives.addPictureFromCamera(idBeehive, bitmap);
                    } else {
                        Uri selectedImageUri = data.getData();
                        InputStream imageStream = null;
                        try {
                            imageStream = requireActivity().getContentResolver().openInputStream(selectedImageUri);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                        daoBeehives.addPictureFromCamera(idBeehive, bitmap);
                    }
                }
            }
        }
    );

    private void addHoneySuperDialog(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_add_honey_super);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final EditText honeySuperName = dialog.findViewById(R.id.honey_super_name_value);
        final EditText honeySuperFrameNumber = dialog.findViewById(R.id.add_frame_number);

        Button addHoneySuperButton = dialog.findViewById(R.id.add_honey_super_button);
        addHoneySuperButton.setOnClickListener(v -> {
            String name = honeySuperName.getText().toString();
            String frameNumber = honeySuperFrameNumber.getText().toString();

            if (name.isEmpty()) {
                honeySuperName.setError("Veuillez entrer un nom");
            } else if (frameNumber.isEmpty()) {
                honeySuperFrameNumber.setError("Veuillez entrer un nombre de cadres");
            } else {
                DAOHoneySuper daoHoneySuper = new DAOHoneySuper();
                String idHoneySuper = daoHoneySuper.getKey();
                HoneySuperModel honeySuper = new HoneySuperModel(idHoneySuper, name, Integer.parseInt(frameNumber), idBeehive, Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
                daoHoneySuper.add(honeySuper).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(activity, "Hausse ajouté", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(activity, "Erreur lors de l'ajout de la hausse", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        Button cancelButton = dialog.findViewById(R.id.cancel_honey_super_button);
        cancelButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void editHoneySuperDialog(Activity activity, HoneySuperModel honeySuper) {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_add_honey_super);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final EditText honeySuperName = dialog.findViewById(R.id.honey_super_name_value);
        honeySuperName.setText(honeySuper.getName());
        final EditText honeySuperFrameNumber = dialog.findViewById(R.id.add_frame_number);
        honeySuperFrameNumber.setText(String.valueOf(honeySuper.getNbFrames()));

        Button editHoneySuperButton = dialog.findViewById(R.id.add_honey_super_button);
        editHoneySuperButton.setText("Modifier");
        editHoneySuperButton.setOnClickListener(v -> {
            String name = honeySuperName.getText().toString();
            String frameNumber = honeySuperFrameNumber.getText().toString();

            if (name.isEmpty()) {
                honeySuperName.setError("Veuillez entrer un nom");
            } else if (frameNumber.isEmpty()) {
                honeySuperFrameNumber.setError("Veuillez entrer un nombre de cadres");
            } else {
                DAOHoneySuper daoHoneySuper = new DAOHoneySuper();
                String idHoneySuper = honeySuper.getIdHoneySuper();
                HashMap<String, Object> map = new HashMap<>();
                map.put("name", name);
                map.put("nbFrames", Integer.parseInt(frameNumber));
                daoHoneySuper.update(idHoneySuper, map).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(activity, "Hausse modifié", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(activity, "Erreur lors de la modification de la hausse", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        Button cancelButton = dialog.findViewById(R.id.cancel_honey_super_button);
        cancelButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void addEvent(String title, String description, String location, long startDate) {
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(Events.CONTENT_URI)
                .putExtra(Events.TITLE, title)
                .putExtra(Events.DESCRIPTION, description)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startDate)
                .putExtra(Events.ALL_DAY, true)
                .putExtra(Events.EVENT_LOCATION, location);
        startActivity(intent);
    }

    private boolean checkPermission(String... permissionsId) {
        boolean permissions = true;
        for (String p : permissionsId) {
            permissions = permissions && ContextCompat.checkSelfPermission(requireContext(), p) == PERMISSION_GRANTED;
        }

        if (!permissions)
            ActivityCompat.requestPermissions(requireActivity(), permissionsId, 42);

        return permissions;
    }

    private void addVisitDialog(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_add_visit);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final DatePicker visitDate = dialog.findViewById(R.id.visit_date);
        final Spinner spinner = dialog.findViewById(R.id.spinner_visit_type);
        final EditText visitReason = dialog.findViewById(R.id.visitReason);
        final VisitModel.VisitType[] visitType = new VisitModel.VisitType[1];

        final List<String> visitTypes = Arrays.asList("Contrôle sanitaire", "Nourrissement", "Récolte", "Autre");
        final int[] visitImages = {R.drawable.heart_dropdown, R.drawable.beefeed, R.drawable.harvest, R.drawable.other};
        SpinnerAdapter adapter = new SpinnerAdapter(getContext(), visitTypes, visitImages);
        adapter.setDropDownViewResource(R.layout.dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        visitType[0] = VisitModel.VisitType.SANITARY_CONTROL;
                        visitReason.setVisibility(View.GONE);
                        break;
                    case 1:
                        visitType[0] = VisitModel.VisitType.FEEDING;
                        visitReason.setVisibility(View.GONE);
                        break;
                    case 2:
                        visitType[0] = VisitModel.VisitType.HARVESTING;
                        visitReason.setVisibility(View.GONE);
                        break;
                    default:
                        visitType[0] = VisitModel.VisitType.OTHER;
                        visitReason.setVisibility(View.VISIBLE);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        Button addVisitButton = dialog.findViewById(R.id.add_visit_button);
        addVisitButton.setOnClickListener(v -> {
            String date = visitDate.getYear() + "-" + visitDate.getMonth() + "-" + visitDate.getDayOfMonth();
            String reason = visitReason.getText().toString();
            DAOVisits daoVisit = new DAOVisits();
            String idVisit = daoVisit.getKey();
            VisitModel visit = new VisitModel(idVisit, date, reason, visitType[0], idBeehive, Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid() + "_" + visitType[0]);
            daoVisit.add(visit).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(activity, "Visite ajoutée", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    DAOBeehives daoBeehives = new DAOBeehives();
                    daoBeehives.find(idBeehive).get().addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            for (DataSnapshot snapshot : task1.getResult().getChildren()) {
                                BeehiveModel beehive = snapshot.getValue(BeehiveModel.class);
                                if (beehive != null) {
                                    DAOApiaries daoApiaries = new DAOApiaries();
                                    daoApiaries.find(beehive.getIdApiary()).get().addOnCompleteListener(task2 -> {
                                        if (task2.isSuccessful()) {
                                            for (DataSnapshot snapshot1 : task2.getResult().getChildren()) {
                                                ApiaryModel apiary = snapshot1.getValue(ApiaryModel.class);
                                                if (apiary != null) {
                                                    String title = "Visite de " + beehive.getName();
                                                    String description = visitTypes.get(visitType[0].ordinal());
                                                    String location = apiary.getLocation().toString();
                                                    Calendar calendar = Calendar.getInstance();
                                                    calendar.set(visitDate.getYear(), visitDate.getMonth(), visitDate.getDayOfMonth());
                                                    if (checkPermission(Manifest.permission.WRITE_CALENDAR, Manifest.permission.READ_CALENDAR))
                                                        addEvent(title, description, location, calendar.getTimeInMillis());
                                                }
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    });
                } else {
                    Toast.makeText(activity, "Erreur lors de l'ajout de la visite", Toast.LENGTH_SHORT).show();
                }
            });
        });

        Button cancelButton = dialog.findViewById(R.id.cancel_visit_button);
        cancelButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void editVisitDialog(Activity activity, VisitModel visit) {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_add_visit);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final DatePicker visitDate = dialog.findViewById(R.id.visit_date);
        visitDate.updateDate(Integer.parseInt(visit.getDate().split("-")[0]), Integer.parseInt(visit.getDate().split("-")[1]), Integer.parseInt(visit.getDate().split("-")[2]));
        final Spinner spinner = dialog.findViewById(R.id.spinner_visit_type);
        final EditText visitReason = dialog.findViewById(R.id.visitReason);
        visitReason.setText(visit.getDescription());
        final VisitModel.VisitType[] visitType = new VisitModel.VisitType[1];

        final List<String> visitTypes = Arrays.asList("Contrôle sanitaire", "Nourrissement", "Récolte", "Autre");
        final int[] visitImages = {R.drawable.heart_dropdown, R.drawable.beefeed, R.drawable.harvest, R.drawable.other};
        SpinnerAdapter adapter = new SpinnerAdapter(getContext(), visitTypes, visitImages);
        adapter.setDropDownViewResource(R.layout.dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(visit.getVisitType().ordinal());

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        visitType[0] = VisitModel.VisitType.SANITARY_CONTROL;
                        visitReason.setVisibility(View.GONE);
                        break;
                    case 1:
                        visitType[0] = VisitModel.VisitType.FEEDING;
                        visitReason.setVisibility(View.GONE);
                        break;
                    case 2:
                        visitType[0] = VisitModel.VisitType.HARVESTING;
                        visitReason.setVisibility(View.GONE);
                        break;
                    default:
                        visitType[0] = VisitModel.VisitType.OTHER;
                        visitReason.setVisibility(View.VISIBLE);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        Button editVisitButton = dialog.findViewById(R.id.add_visit_button);
        editVisitButton.setText("Modifier");
        editVisitButton.setOnClickListener(v -> {
            String date = visitDate.getYear() + "-" + visitDate.getMonth() + "-" + visitDate.getDayOfMonth();
            String reason = visitReason.getText().toString();
            DAOVisits daoVisit = new DAOVisits();
            String idVisit = visit.getIdVisit();
            HashMap<String, Object> map = new HashMap<>();
            map.put("date", date);
            map.put("description", reason);
            map.put("visitType", visitType[0]);
            daoVisit.update(idVisit, map).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(activity, "Visite modifiée", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(activity, "Erreur lors de la modification de la visite", Toast.LENGTH_SHORT).show();
                }
            });
        });

        Button cancelButton = dialog.findViewById(R.id.cancel_visit_button);
        cancelButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    @Override
    public void onStart() {
        super.onStart();
        honeySuperAdapter.startListening();
        visitsAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        honeySuperAdapter.stopListening();
        visitsAdapter.stopListening();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
