package com.uqac.beesness.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.uqac.beesness.R;

import com.uqac.beesness.database.DAOProducts;
import com.uqac.beesness.model.ProductModel;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ProductAdapter extends FirebaseRecyclerAdapter<ProductModel, ProductAdapter.ViewHolder> {



    public ProductAdapter(@NonNull FirebaseRecyclerOptions<ProductModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position, @NonNull ProductModel model) {

        Glide.with(holder.productImage.getContext()).load(model.getPictureUrl()).into(holder.productImage);
        holder.productName.setText(model.getName());
        holder.productQuantity.setText(String.valueOf(model.getQuantityProduct()));

        /*DAOProducts daoProducts = new DAOProducts();
        daoProducts.findAllForUser().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder.productQuantity.setText(String.valueOf(snapshot.getChildrenCount()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });*/
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_products, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        /*viewHolder.setOnClickListener((view1, position) -> {
            Intent intent = new Intent(view1.getContext(), ApiaryDetailsActivity.class);
            intent.putExtra("idApiary", getItem(position).getIdProduct());
            view1.getContext().startActivity(intent);
        });*/

        return viewHolder;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView productImage;
        private final TextView productName;
        private final TextView productQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productQuantity = itemView.findViewById(R.id.product_quantity);
            //itemView.setOnClickListener(v -> mClickListener.onItemClick(v, getAbsoluteAdapterPosition()));
        }

        /*private ApiariesAdapter.ViewHolder.ClickListener mClickListener;

        public interface ClickListener{
            void onItemClick(View view, int position);
        }

        public void setOnClickListener(ApiariesAdapter.ViewHolder.ClickListener clickListener){
            mClickListener = clickListener;
        }*/
    }

}
