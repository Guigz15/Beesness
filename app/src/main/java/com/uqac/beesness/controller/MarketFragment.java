package com.uqac.beesness.controller;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.uqac.beesness.R;
import com.uqac.beesness.database.DAOProducts;
import com.uqac.beesness.databinding.FragmentMarketBinding;
import com.uqac.beesness.model.MarketViewModel;
import com.uqac.beesness.model.ProductModel;

public class MarketFragment extends Fragment {

    private FragmentMarketBinding binding;
    private ProductAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MarketViewModel marketViewModel = new ViewModelProvider(this).get(MarketViewModel.class);
        binding = FragmentMarketBinding.inflate(inflater, container, false);

        requireActivity().findViewById(R.id.toolbar_add).setVisibility(View.VISIBLE);
        requireActivity().findViewById(R.id.space).setVisibility(View.VISIBLE);
        ((TextView) requireActivity().findViewById(R.id.title_text)).setText("Marché");

        View root = binding.getRoot();

        DAOProducts daoProducts = new DAOProducts();
        RecyclerView productsRecyclerView = root.findViewById(R.id.product_list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        productsRecyclerView.setItemAnimator(null);
        FirebaseRecyclerOptions<ProductModel> options = new FirebaseRecyclerOptions.Builder<ProductModel>()
                .setQuery(daoProducts.findAllForUser(), ProductModel.class)
                .build();
        adapter = new ProductAdapter(options);

        productsRecyclerView.setLayoutManager(linearLayoutManager);
        productsRecyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}