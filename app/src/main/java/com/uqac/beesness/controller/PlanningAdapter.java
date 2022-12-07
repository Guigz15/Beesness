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
import com.uqac.beesness.database.DAOBeehives;
import com.uqac.beesness.model.BeehiveModel;
import com.uqac.beesness.model.VisitModel;

import java.util.Calendar;

public class PlanningAdapter extends FirebaseRecyclerAdapter<VisitModel, PlanningAdapter.ViewHolder> {

    private final String[] MONTHS = {"JAN.", "FEV.", "MAR.", "AVR.", "MAI.", "JUIN.", "JUIL.", "AOU.", "SEP.", "OCT.", "NOV.", "DEC."};

    public PlanningAdapter(@NonNull FirebaseRecyclerOptions<VisitModel> options) {
        super(options);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanningAdapter.ViewHolder holder, int position, @NonNull VisitModel model) {
        holder.dateTextView.setText(model.getDate().split("-")[2] + " " + MONTHS[Integer.parseInt(model.getDate().split("-")[1])]);

        DAOBeehives daoBeehives = new DAOBeehives();
        daoBeehives.find(model.getIdBeehive()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    BeehiveModel beehiveModel = dataSnapshot.getValue(BeehiveModel.class);
                    assert beehiveModel != null;
                    holder.beehiveTextView.setText(beehiveModel.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    @NonNull
    @Override
    public PlanningAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_planning_visit, parent, false);
        PlanningAdapter.ViewHolder viewHolder = new PlanningAdapter.ViewHolder(view);

        viewHolder.setOnClickListener((view1, position) -> {
            Intent intent = new Intent(view1.getContext(), BeehiveDetailsActivity.class);
            intent.putExtra("idBeehive", getItem(position).getIdBeehive());
            view1.getContext().startActivity(intent);
        });

        return viewHolder;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView dateTextView;
        private final TextView beehiveTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dateTextView = itemView.findViewById(R.id.visit_date);
            beehiveTextView = itemView.findViewById(R.id.beehive_name_text);

            itemView.setOnClickListener(v -> mClickListener.onItemClick(v, getAbsoluteAdapterPosition()));
        }

        private PlanningAdapter.ViewHolder.ClickListener mClickListener;

        public interface ClickListener{
            void onItemClick(View view, int position);
        }

        public void setOnClickListener(PlanningAdapter.ViewHolder.ClickListener clickListener){
            mClickListener = clickListener;
        }
    }
}
