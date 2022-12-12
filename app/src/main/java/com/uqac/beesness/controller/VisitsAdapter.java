package com.uqac.beesness.controller;

import android.annotation.SuppressLint;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.uqac.beesness.R;
import com.uqac.beesness.model.VisitModel;

/**
 * Adapter for the visits recycler view
 */
public class VisitsAdapter extends FirebaseRecyclerAdapter<VisitModel, VisitsAdapter.ViewHolder> {

    private final String[] MONTHS = {"JAN.", "FEV.", "MAR.", "AVR.", "MAI.", "JUIN.", "JUIL.", "AOU.", "SEP.", "OCT.", "NOV.", "DEC."};

    public VisitsAdapter(@NonNull FirebaseRecyclerOptions<VisitModel> options) {
        super(options);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull VisitsAdapter.ViewHolder holder, int position, @NonNull VisitModel model) {
        holder.dateTextView.setText(model.getDate().split("-")[2] + " " + MONTHS[Integer.parseInt(model.getDate().split("-")[1])]);

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
        VisitsAdapter.ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.setOnClickListener((view1, position) -> Toast.makeText(view1.getContext(), "Appuyez longtemps pour modifier ou supprimer la visite", Toast.LENGTH_LONG).show());

        return viewHolder;
    }

    /**
     * View holder for the visits recycler view
     */
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        private final TextView dateTextView;
        private final TextView visitTextView;
        private final ImageView visitImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.visit_date);
            visitTextView = itemView.findViewById(R.id.visit_text);
            visitImageView = itemView.findViewById(R.id.visit_image);
            CardView visitCard = itemView.findViewById(R.id.cardView);
            visitCard.setOnCreateContextMenuListener(this);

            itemView.setOnClickListener(v -> mClickListener.onItemClick(v, getAbsoluteAdapterPosition()));
        }

        private VisitsAdapter.ViewHolder.ClickListener mClickListener;

        public interface ClickListener{
            void onItemClick(View view, int position);
        }

        public void setOnClickListener(VisitsAdapter.ViewHolder.ClickListener clickListener){
            mClickListener = clickListener;
        }

        @Override
        public void onCreateContextMenu(@NonNull ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Sélectionnez une action");
            contextMenu.add(getAbsoluteAdapterPosition(), 2, 0, "Modifier");
            contextMenu.add(getAbsoluteAdapterPosition(), 3, 1, "Supprimer");
        }
    }
}
