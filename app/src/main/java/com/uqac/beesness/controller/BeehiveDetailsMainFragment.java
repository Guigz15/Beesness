package com.uqac.beesness.controller;

import static android.provider.MediaStore.Images.Media.insertImage;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.uqac.beesness.database.DAOBeehives;
import com.uqac.beesness.databinding.FragmentBeehiveDetailsMainBinding;
import com.uqac.beesness.model.BeehiveModel;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BeehiveDetailsMainFragment extends Fragment {

    private FragmentBeehiveDetailsMainBinding binding;
    private ImageSlider imageSlider;
    private ImageButton addPictureButton;
    private DAOBeehives daoBeehives;
    private String idBeehive;

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
                BeehiveModel beehive = snapshot.getChildren().iterator().next().getValue(BeehiveModel.class);
                assert beehive != null;
                if (beehive.getPicturesUrl().isEmpty())
                    binding.imageSlider.setVisibility(View.GONE);
                else {
                    binding.imageSlider.setVisibility(View.VISIBLE);
                    slideModels.clear();
                    beehive.getPicturesUrl().forEach((key, value) ->
                        slideModels.add(new SlideModel(value, ScaleTypes.FIT))
                    );
                    imageSlider.setImageList(slideModels, ScaleTypes.FIT);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return root;
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
                        daoBeehives.addPictureFromGallery(idBeehive, selectedImageUri);
                    }
                }
            }
        }
    );

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
