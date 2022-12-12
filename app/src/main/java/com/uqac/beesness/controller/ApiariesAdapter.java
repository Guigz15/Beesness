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
import com.uqac.beesness.model.ApiaryModel;

/**
 * Adapter for the apiaries recycler view
 */
public class ApiariesAdapter extends FirebaseRecyclerAdapter<ApiaryModel, ApiariesAdapter.ViewHolder> {

    public ApiariesAdapter(@NonNull FirebaseRecyclerOptions<ApiaryModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ApiariesAdapter.ViewHolder holder, int position, @NonNull ApiaryModel model) {
        holder.apiaryName.setText(model.getName());

        DAOBeehives daoBeehives = new DAOBeehives();
        daoBeehives.findAllForApiary(model.getIdApiary()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder.beehiveNumber.setText(String.valueOf(snapshot.getChildrenCount()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    @NonNull
    @Override
    public ApiariesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_apiaries, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.setOnClickListener((view1, position) -> {
            Intent intent = new Intent(view1.getContext(), ApiaryDetailsActivity.class);
            intent.putExtra("idApiary", getItem(position).getIdApiary());
            view1.getContext().startActivity(intent);
        });

        return viewHolder;
    }

    /**
     * View holder for the apiaries recycler view
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView apiaryName;
        private final TextView beehiveNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            apiaryName = itemView.findViewById(R.id.apiary_name);
            beehiveNumber = itemView.findViewById(R.id.beehive_number);

            itemView.setOnClickListener(v -> mClickListener.onItemClick(v, getAbsoluteAdapterPosition()));
        }

        private ViewHolder.ClickListener mClickListener;

        public interface ClickListener{
            void onItemClick(View view, int position);
        }

        public void setOnClickListener(ViewHolder.ClickListener clickListener){
            mClickListener = clickListener;
        }
    }
}
