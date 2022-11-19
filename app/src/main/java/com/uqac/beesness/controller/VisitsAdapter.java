package com.uqac.beesness.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.uqac.beesness.R;
import com.uqac.beesness.model.VisitModel;

public class VisitsAdapter extends FirebaseRecyclerAdapter<VisitModel, VisitsAdapter.ViewHolder> {

    public VisitsAdapter(@NonNull FirebaseRecyclerOptions<VisitModel> options) {
        super(options);
    }

    @Override
    public void onBindViewHolder(@NonNull VisitsAdapter.ViewHolder holder, int position, @NonNull VisitModel model) {
        holder.dateTextView.setText(model.getDate());
        holder.visitTextView.setText(model.getDescription());
    }

    @NonNull
    @Override
    public VisitsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_visits, parent, false);
        VisitsAdapter.ViewHolder viewHolder = new VisitsAdapter.ViewHolder(view);

        return viewHolder;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView dateTextView;
        private final TextView visitTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.visit_date);
            visitTextView = itemView.findViewById(R.id.visit_text);
        }
    }
}
