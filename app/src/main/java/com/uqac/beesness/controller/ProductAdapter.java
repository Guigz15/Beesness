package com.uqac.beesness.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.uqac.beesness.model.ProductModel;

/**
 * Adapter for the products recycler view
 */
public class ProductAdapter extends FirebaseRecyclerAdapter<ProductModel, ProductAdapter.ViewHolder> {

    public ProductAdapter(@NonNull FirebaseRecyclerOptions<ProductModel> options) {
        super(options);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position, @NonNull ProductModel model) {

        Glide.with(holder.productImage.getContext()).load(model.getPictureUrl()).into(holder.productImage);
        holder.productName.setText(model.getName());
        holder.productQuantity.setText(model.getQuantityProduct() + "kg");
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_products, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.setOnClickListener((view1, position) -> {
            Intent intent = new Intent(view1.getContext(), ProductDetailsActivity.class);
            intent.putExtra("idProduct", getItem(position).getIdProduct());
            view1.getContext().startActivity(intent);
        });

        return viewHolder;
    }

    /**
     * View holder for the products recycler view
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView productImage;
        private final TextView productName;
        private final TextView productQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productQuantity = itemView.findViewById(R.id.product_quantity);
            itemView.setOnClickListener(v -> mClickListener.onItemClick(v, getAbsoluteAdapterPosition()));
        }

        private ProductAdapter.ViewHolder.ClickListener mClickListener;

        public interface ClickListener{
            void onItemClick(View view, int position);
        }

        public void setOnClickListener(ProductAdapter.ViewHolder.ClickListener clickListener){
            mClickListener = clickListener;
        }
    }
}
