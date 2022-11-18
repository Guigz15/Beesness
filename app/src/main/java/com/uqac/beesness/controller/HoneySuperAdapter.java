package com.uqac.beesness.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.uqac.beesness.R;
import com.uqac.beesness.model.HoneySuperModel;

public class HoneySuperAdapter extends FirebaseRecyclerAdapter<HoneySuperModel, HoneySuperAdapter.ViewHolder> {

    public HoneySuperAdapter(@NonNull FirebaseRecyclerOptions<HoneySuperModel> options) {
        super(options);
    }

    @Override
    public void onBindViewHolder(@NonNull HoneySuperAdapter.ViewHolder holder, int position, @NonNull HoneySuperModel model) {

    }

    @NonNull
    @Override
    public HoneySuperAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_honey_supers, parent, false);
        HoneySuperAdapter.ViewHolder viewHolder = new HoneySuperAdapter.ViewHolder(view);

        return viewHolder;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
