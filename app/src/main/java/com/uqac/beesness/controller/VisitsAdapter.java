package com.uqac.beesness.controller;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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

        if (model.getVisitType().equals(VisitModel.VisitType.SANITARY_CONTROL)) {
            holder.visitTextView.setVisibility(View.GONE);
            holder.visitImageView.setVisibility(View.VISIBLE);
            holder.visitImageView.setImageResource(R.drawable.heart_dropdown);
        } else if (model.getVisitType().equals(VisitModel.VisitType.FEEDING)) {
            holder.visitTextView.setVisibility(View.GONE);
            holder.visitImageView.setVisibility(View.VISIBLE);
            holder.visitImageView.setImageResource(R.drawable.beefeed);
        } else if (model.getVisitType().equals(VisitModel.VisitType.HARVESTING)) {
            holder.visitTextView.setVisibility(View.GONE);
            holder.visitImageView.setVisibility(View.VISIBLE);
            holder.visitImageView.setImageResource(R.drawable.harvest);
        } else {
            holder.visitImageView.setVisibility(View.GONE);
            holder.visitTextView.setVisibility(View.VISIBLE);
            holder.visitTextView.setText(model.getDescription());
        }
    }

    @NonNull
    @Override
    public VisitsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_visits, parent, false);

        return new ViewHolder(view);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        private final TextView dateTextView;
        private final TextView visitTextView;
        private final ImageView visitImageView;
        private final CardView visitCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.visit_date);
            visitTextView = itemView.findViewById(R.id.visit_text);
            visitImageView = itemView.findViewById(R.id.visit_image);
            visitCard = itemView.findViewById(R.id.cardView);
            visitCard.setOnCreateContextMenuListener(this);
        }


        @Override
        public void onCreateContextMenu(@NonNull ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Sélectionnez une action");
            contextMenu.add(getAbsoluteAdapterPosition(), 2, 0, "Modifier");
            contextMenu.add(getAbsoluteAdapterPosition(), 3, 1, "Supprimer");
        }
    }
}
