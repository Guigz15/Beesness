package com.uqac.beesness.controller;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.uqac.beesness.R;
import com.uqac.beesness.database.DAOHoneySuper;
import com.uqac.beesness.model.BeehiveModel;

/**
 * Adapter for the beehives recycler view
 */
public class BeehivesAdapter extends FirebaseRecyclerAdapter<BeehiveModel, BeehivesAdapter.ViewHolder> {

    public BeehivesAdapter(@NonNull FirebaseRecyclerOptions<BeehiveModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull BeehivesAdapter.ViewHolder holder, int position, @NonNull BeehiveModel model) {
        holder.beehiveName.setText(model.getName());

        DAOHoneySuper daoHoneySuper = new DAOHoneySuper();
        daoHoneySuper.findAllForBeehive(model.getIdBeehive()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder.honeySuperNumber.setText(String.valueOf(snapshot.getChildrenCount()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @NonNull
    @Override
    public BeehivesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_beehives, parent, false);
        BeehivesAdapter.ViewHolder viewHolder = new BeehivesAdapter.ViewHolder(view);

        viewHolder.setOnClickListener((view1, position) -> {
            Intent intent = new Intent(view1.getContext(), BeehiveDetailsActivity.class);
            intent.putExtra("idBeehive", getItem(position).getIdBeehive());
            view1.getContext().startActivity(intent);
        });

        return viewHolder;
    }

    /**
     * View holder for the beehives recycler view
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView beehiveName;
        private final TextView honeySuperNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            beehiveName = itemView.findViewById(R.id.beehive_name);
            honeySuperNumber = itemView.findViewById(R.id.honey_super_number);

            itemView.setOnClickListener(v -> mClickListener.onItemClick(v, getAbsoluteAdapterPosition()));
        }

        private ClickListener mClickListener;

        public interface ClickListener{
            void onItemClick(View view, int position);
        }

        public void setOnClickListener(ClickListener clickListener){
            mClickListener = clickListener;
        }
    }
}
