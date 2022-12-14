package com.uqac.beesness.controller;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.uqac.beesness.R;
import com.uqac.beesness.model.HoneySuperModel;

/**
 * Adapter for the honey super recycler view
 */
public class HoneySuperAdapter extends FirebaseRecyclerAdapter<HoneySuperModel, HoneySuperAdapter.ViewHolder> {

    public HoneySuperAdapter(@NonNull FirebaseRecyclerOptions<HoneySuperModel> options) {
        super(options);
    }

    @Override
    public void onBindViewHolder(@NonNull HoneySuperAdapter.ViewHolder holder, int position, @NonNull HoneySuperModel model) {
        holder.honeySuperName.setText(model.getName());
        holder.honeySuperFrameNumber.setText(String.valueOf(model.getNbFrames()));
    }

    @NonNull
    @Override
    public HoneySuperAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_honey_supers, parent, false);
        HoneySuperAdapter.ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.setOnClickListener((view1, position) -> Toast.makeText(view1.getContext(), "Appuyez longtemps pour modifier ou supprimer la hausse", Toast.LENGTH_LONG).show());

        return viewHolder;
    }

    /**
     * View holder for the honey super recycler view
     */
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        private final TextView honeySuperName;
        private final TextView honeySuperFrameNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            honeySuperName = itemView.findViewById(R.id.honey_super_name);
            honeySuperFrameNumber = itemView.findViewById(R.id.frame_number);
            CardView honeySuperCard = itemView.findViewById(R.id.card_view);
            honeySuperCard.setOnCreateContextMenuListener(this);

            itemView.setOnClickListener(v -> mClickListener.onItemClick(v, getAbsoluteAdapterPosition()));
        }

        private HoneySuperAdapter.ViewHolder.ClickListener mClickListener;

        public interface ClickListener{
            void onItemClick(View view, int position);
        }

        public void setOnClickListener(HoneySuperAdapter.ViewHolder.ClickListener clickListener){
            mClickListener = clickListener;
        }

        @Override
        public void onCreateContextMenu(@NonNull ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("S??lectionnez une action");
            contextMenu.add(getAbsoluteAdapterPosition(), 0, 0, "Modifier");
            contextMenu.add(getAbsoluteAdapterPosition(), 1, 1, "Supprimer");
        }
    }
}
