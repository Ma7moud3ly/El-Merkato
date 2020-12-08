/**
 * El Merkato الميركاتو -
 * @author  Mahmoud Aly
 * @version 1.0
 * @since   2020-12-04
 */
package com.ma7moud3ly.elmerkato.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.ma7moud3ly.elmerkato.App;
import com.ma7moud3ly.elmerkato.adapters.CategoryAdapter;
import com.ma7moud3ly.elmerkato.adapters.DepartmentAdapter;
import com.ma7moud3ly.elmerkato.databinding.FragmentCompanyBinding;
import com.ma7moud3ly.elmerkato.models.CompaniesViewModel;
import com.ma7moud3ly.elmerkato.repositories.Category;
import com.ma7moud3ly.elmerkato.repositories.Company;
import com.ma7moud3ly.elmerkato.util.CONSTANTS;
import com.ma7moud3ly.elmerkato.activities.MainActivity;
import com.ma7moud3ly.elmerkato.adapters.SliderAdapter;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CompanyFragment extends BaseFragment {

    private FragmentCompanyBinding binding;
    private CompaniesViewModel model;
    private List<Category> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCompanyBinding.inflate(inflater);
        binding.setUi(((MainActivity) getActivity()).uiState);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initRecyclerView(binding.companyRecycler, new DepartmentAdapter(list));
        currentFragment(CONSTANTS.COMP_FRAGMENT);

        model = new ViewModelProvider(getActivity(),viewModelFactory).get(CompaniesViewModel.class);
        model.company.observe(getViewLifecycleOwner(), company -> {
            if (company == null) return;
            this.list.clear();
            this.list.addAll(company.departments);
            recyclerAdapter.notifyDataSetChanged();
            binding.setCompany(company);
            ((MainActivity) getActivity()).initImageSlider(company.images, binding.companySlider, binding.companySliderIndicator, CONSTANTS.FROM_COMPANIES,false);
            setTitle(company.category, company.name, "");
        });

        list.clear();
        recyclerAdapter.notifyDataSetChanged();

    }


    @Override
    public void onSearch(String query) {
        List<Category> temp = new ArrayList<>();
        for (Category category : model.company.getValue().departments)
            if (category.name.contains(query)) temp.add(category);
        list.clear();
        list.addAll(temp);
        recyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSearchCleared() {
        list.clear();
        list.addAll(model.company.getValue().departments);
        recyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(int index) {
        Bundle bundle = new Bundle();
        bundle.putString("company", model.company.toString());
        bundle.putString("load_from", CONSTANTS.FROM_DEPARTMENTS);
        bundle.putString("key", list.get(index).id);
        ((MainActivity) getActivity()).navigateTo(new ProductsFragment(), bundle, true);
        setTitle(model.company.getValue().category, model.company.getValue().name, list.get(index).name);
    }


    @Override
    public void onNetworkRetry() {
        super.onNetworkRetry();
    }
}

