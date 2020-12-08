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
import com.ma7moud3ly.elmerkato.databinding.SectionRecyclerBinding;
import com.ma7moud3ly.elmerkato.models.CompaniesViewModel;
import com.ma7moud3ly.elmerkato.repositories.Company;
import com.ma7moud3ly.elmerkato.util.CONSTANTS;
import com.ma7moud3ly.elmerkato.activities.MainActivity;
import com.ma7moud3ly.elmerkato.adapters.CompaniesAdapter;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

public class CompaniesFragment extends BaseFragment {
    private CompaniesViewModel model;
    private List<Company> list = new ArrayList<>();
    private String category;

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

        initRecyclerView(binding.recyclerView, new CompaniesAdapter(list, ((MainActivity) getActivity()).uiState));
        currentFragment(CONSTANTS.COMP_FRAGMENT);
        readCategory();

        //attach CompaniesViewModel
        model = new ViewModelProvider(getActivity(),viewModelFactory).get(CompaniesViewModel.class);
        // observe the compaies list coming from the database
        model.data.observe(getViewLifecycleOwner(), companies -> {
            if (companies != null) {
                FirebaseDatabase.getInstance().getReference().keepSynced(false);
                list.clear();
                list.addAll(companies);
                recyclerAdapter.notifyDataSetChanged();
                networkState(CONSTANTS.LOADED);
            }
        });

        read();

    }

    /**
     * this method receives the category string from the arguments
     * category tell the viewModel which companies to fetch from database
     * if categories is empty, them fetch all companies.
     * */
    private void readCategory() {
        category = "";
        boolean hasCategory = getArguments() != null && getArguments().containsKey("category");
        if (hasCategory) {
            category = getArguments().getString("category");
            setTitle(category, "", "");
        } else {
            setTitle(getResources().getString(R.string.nav_companies), "", "");
        }
    }

    //read companies from database
    @Override
    public void read() {
        if (model.nullOrEmpty() || !category.equals(model.category)) {
            super.read();
            model.read(category);
            model.category = category;
        }
    }

    @Override
    public void onSearch(String query) {
        List<Company> temp = new ArrayList<>();
        for (Company company : model.data.getValue())
            if (company.name.contains(query)) temp.add(company);
        list.clear();
        list.addAll(temp);
        recyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSearchCleared() {
        list.clear();
        list.addAll(model.data.getValue());
        recyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(int index) {
        Company company = list.get(index);
        model.company.setValue(company);
        ((MainActivity) getActivity()).navigateTo(new CompanyFragment(), null, true);
        setTitle(category, "", "");
    }

    @Override
    public void onNetworkRetry() {
        super.onNetworkRetry();
        model.read(category);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}



