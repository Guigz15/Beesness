package com.uqac.beesness.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.uqac.beesness.R;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<String> {

    Context context;
    List<String> visitType;
    int[] visitTypeImages;

    public SpinnerAdapter(Context context, List<String> visitType, int[] visitTypeImages) {
        super(context, R.layout.dropdown_item, visitType);
        this.context = context;
        this.visitType = visitType;
        this.visitTypeImages = visitTypeImages;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.dropdown_item, parent, false);

        TextView state = row.findViewById(R.id.visit_type_text);
        ImageView flag = row.findViewById(R.id.visit_type_image);

        state.setText(visitType.get(position));
        flag.setImageResource(visitTypeImages[position]);

        return row;
    }
}
