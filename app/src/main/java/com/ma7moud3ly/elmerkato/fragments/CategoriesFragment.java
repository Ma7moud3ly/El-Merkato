/**
 * El Merkato الميركاتو -
 * @author  Mahmoud Aly
 * @version 1.0
 * @since   2020-12-04
 */
package com.ma7moud3ly.elmerkato.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.FirebaseDatabase;
import com.ma7moud3ly.elmerkato.R;
import com.ma7moud3ly.elmerkato.activities.MainActivity;
import com.ma7moud3ly.elmerkato.adapters.CategoryAdapter;
import com.ma7moud3ly.elmerkato.databinding.SectionRecyclerBinding;
import com.ma7moud3ly.elmerkato.di.ViewModelFactory;
import com.ma7moud3ly.elmerkato.models.CategoriesViewModel;
import com.ma7moud3ly.elmerkato.repositories.Category;
import com.ma7moud3ly.elmerkato.util.CONSTANTS;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

public class CategoriesFragment extends BaseFragment {
    private  CategoriesViewModel model;
    private List<Category> list = new ArrayList<>();

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
        //set the title in the header with categories title
        setTitle(getResources().getString(R.string.nav_categories), "", "");
        //init the recyclerView shows the categories
        initRecyclerView(binding.recyclerView, new CategoryAdapter(list, uiState));
        // select categories icon in the footer
        currentFragment(CONSTANTS.CAT_FRAGMENT);

        //attach CategoriesViewModel
        model = new ViewModelProvider(getActivity(),viewModelFactory).get(CategoriesViewModel.class);
        // observe the categories list coming from the database
        model.data.observe(getViewLifecycleOwner(), categories -> {
            if (categories != null && categories.size() > 0) {
                FirebaseDatabase.getInstance().getReference().keepSynced(false);
                list.clear();
                list.addAll(categories);
                recyclerAdapter.notifyDataSetChanged();
                networkState(CONSTANTS.LOADED);
            } else {
                networkState(CONSTANTS.RETRY);
            }
        });

        read();

    }

    //check for persisting data, if not read data from the server..
    @Override
    public void read() {
        if (model.nullOrEmpty()) {
            super.read();
            model.read();
        }
    }

    //search for a query
    @Override
    public void onSearch(String query) {
        List<Category> temp = new ArrayList<>();
        for (Category category : model.data.getValue())
            if (category.name.contains(query)) temp.add(category);
        list.clear();
        list.addAll(temp);
        recyclerAdapter.notifyDataSetChanged();

    }

    //when search end, show all categories
    @Override
    public void onSearchCleared() {
        list.clear();
        list.addAll(model.data.getValue());
        recyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(int index) {
        Category category = list.get(index);
        Bundle bundle = new Bundle();
        bundle.putString("category", category.name);
        ((MainActivity) getActivity()).navigateTo(new CompaniesFragment(), bundle, true);
        setTitle(category.name, "", "");
    }

    @Override
    public void onNetworkRetry() {
        super.onNetworkRetry();
        model.read();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

