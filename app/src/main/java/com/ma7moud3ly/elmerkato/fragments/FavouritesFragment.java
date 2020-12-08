/**
 * El Merkato الميركاتو -
 * @author  Mahmoud Aly
 * @version 1.0
 * @since   2020-12-04
 */
package com.ma7moud3ly.elmerkato.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ma7moud3ly.elmerkato.activities.MainActivity;
import com.ma7moud3ly.elmerkato.activities.ProductActivity;
import com.ma7moud3ly.elmerkato.adapters.ProductsAdapter;
import com.ma7moud3ly.elmerkato.databinding.SectionRecyclerBinding;
import com.ma7moud3ly.elmerkato.models.FavouritesViewModel;
import com.ma7moud3ly.elmerkato.repositories.Product;
import com.ma7moud3ly.elmerkato.storages.Favourite;
import com.ma7moud3ly.elmerkato.util.CONSTANTS;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

public class FavouritesFragment extends BaseFragment {
    private FavouritesViewModel model;
    private List<Product> list = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SectionRecyclerBinding.inflate(inflater);
        binding.setUi(((MainActivity) getActivity()).uiState);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initRecyclerView(binding.recyclerView, new ProductsAdapter(list, uiState));
        currentFragment(CONSTANTS.FAV_FRAGMENT);

        model = new ViewModelProvider(getActivity(),viewModelFactory).get(FavouritesViewModel.class);
        model.data.observe(getViewLifecycleOwner(), favourites -> {
            if (favourites != null) {
                list.clear();
                for (Favourite favourite : favourites) list.add(favourite.toProduct());
                recyclerAdapter.notifyDataSetChanged();
                networkState(CONSTANTS.LOADED);
            }
        });

    }


    @Override
    public void onSearch(String query) {
        List<Product> temp = new ArrayList<>();
        for (Favourite favourite : model.data.getValue()) {
            Product product = favourite.toProduct();
            if (product.name.contains(query)) temp.add(product);
        }
        list.clear();
        list.addAll(temp);
        recyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSearchCleared() {
        list.clear();
        for (Favourite favourite : model.data.getValue())
            list.add(favourite.toProduct());
        recyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(int index) {
        Product product = list.get(index);
        Intent intent = new Intent(getActivity(), ProductActivity.class);
        intent.putExtra("product", product.toString());
        intent.putExtra("show_images", uiState.showImages.get());
        startActivity(intent);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}

