package com.uqac.beesness.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uqac.beesness.R;
import com.uqac.beesness.model.ApiaryModel;

import java.util.ArrayList;

public class ApiariesAdapter extends RecyclerView.Adapter<ApiariesAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(ApiaryModel item);
    }

    private final Context context;
    private final ArrayList<ApiaryModel> apiaryModelArrayList;
    private final OnItemClickListener listener;

    public ApiariesAdapter(Context context, ArrayList<ApiaryModel> apiaryModelArrayList, OnItemClickListener listener) {
        this.context = context;
        this.apiaryModelArrayList = apiaryModelArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ApiariesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApiariesAdapter.ViewHolder holder, int position) {
        // to set data to textviews of each card layout
        holder.bind(apiaryModelArrayList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number of card items in recycler view
        return apiaryModelArrayList.size();
    }

    // View holder class for initializing of your views such as TextViews
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView apiaryName;
        private final TextView beehivesNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            apiaryName = itemView.findViewById(R.id.apiary_name);
            beehivesNumber = itemView.findViewById(R.id.beehive_number);
        }

        public void bind(final ApiaryModel item, final OnItemClickListener listener) {
            apiaryName.setText(item.getName());
            beehivesNumber.setText(String.valueOf(item.getBeehivesNumber()));
            itemView.setOnClickListener(v -> listener.onItemClick(item));
        }
    }
}
