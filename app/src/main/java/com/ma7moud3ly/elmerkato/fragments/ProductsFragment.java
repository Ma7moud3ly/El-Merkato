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

import com.google.firebase.database.FirebaseDatabase;
import com.ma7moud3ly.elmerkato.R;
import com.ma7moud3ly.elmerkato.activities.MainActivity;
import com.ma7moud3ly.elmerkato.activities.ProductActivity;
import com.ma7moud3ly.elmerkato.adapters.ProductsAdapter;
import com.ma7moud3ly.elmerkato.databinding.SectionRecyclerBinding;
import com.ma7moud3ly.elmerkato.models.ProductsViewModel;
import com.ma7moud3ly.elmerkato.repositories.MyPager;
import com.ma7moud3ly.elmerkato.util.CheckInternet;
import com.ma7moud3ly.elmerkato.repositories.Company;
import com.ma7moud3ly.elmerkato.repositories.Product;
import com.ma7moud3ly.elmerkato.util.CONSTANTS;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

public class ProductsFragment extends BaseFragment {
    private ProductsViewModel model;
    private List<Product> list = new ArrayList<>();
    private String key = "", load_from = "";
    private Company company;
    private MyPager pager;

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
        currentFragment(CONSTANTS.PROD_FRAGMENT);
        loadFrom();
        initPager();


        model = new ViewModelProvider(getActivity(),viewModelFactory).get(ProductsViewModel.class);
        model.items_count.observe(getViewLifecycleOwner(), items_count -> {
            if (items_count == null) return;
            pager.last_page = (int) Math.ceil(items_count * 1.0 / pager.page_size);
            model.read(load_from, key, pager);
        });
        model.data.observe(getViewLifecycleOwner(), products -> {
            if (products != null) {
                FirebaseDatabase.getInstance().getReference().keepSynced(false);
                if (load_from.equals("") && !isSearch())
                    pager.last_key = products.get(products.size() - 1).product_id;
                list.addAll(products);
                recyclerAdapter.notifyDataSetChanged();
                networkState(CONSTANTS.LOADED);
            }
        });

        read();

    }


    private void loadFrom() {
        Bundle arg = getArguments();
        if (arg == null) {
            setTitle(getResources().getString(R.string.nav_products), "", "");
            return;
        }
        if (arg.containsKey("key"))
            key = arg.getString("key");
        if (arg.containsKey("load_from"))
            load_from = getArguments().getString("load_from");
    }

    private void initPager() {
        pager = new MyPager();
        pager.last_key = "";
        pager.current_page = 1;
        pager.page_size = 50;
    }


    private boolean pageNext() {
        if (pager.current_page < pager.last_page) {
            pager.current_page += 1;
            return true;
        }
        return false;
    }

    @Override
    public void read() {
        list.clear();
        recyclerAdapter.notifyDataSetChanged();
        if (load_from.equals(""))
            model.count(pager);
        else
            model.read(load_from, key, pager);
        model.load_from = load_from;
        super.read();
    }

    @Override
    public void onSearch(String query) {
        list.clear();
        if (!CheckInternet.isConnected())
            noInternetMessage();
        else
            networkState(CONSTANTS.LOADING);
        model.search(load_from, key, query);
    }

    @Override
    public void onSearchCleared() {
        model.data.setValue(null);
        read();
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
    public void onNetworkRetry() {
        super.onNetworkRetry();
        model.read(load_from, key, pager);
    }

    @Override
    public void onScrollToBottom() {
        if (key.isEmpty() && pageNext() && !isSearch()) {
            networkState(CONSTANTS.LOADING);
            model.read(load_from, key, pager);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        model.data.setValue(null);
        model.items_count.setValue(null);
        list.clear();
        load_from = "";
        key = "";
        initPager();
    }

}

