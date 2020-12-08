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
import com.ma7moud3ly.elmerkato.App;
import com.ma7moud3ly.elmerkato.R;
import com.ma7moud3ly.elmerkato.activities.BaseActivity;
import com.ma7moud3ly.elmerkato.activities.MainActivity;
import com.ma7moud3ly.elmerkato.adapters.CategoryAdapter;
import com.ma7moud3ly.elmerkato.adapters.CompaniesAdapter;
import com.ma7moud3ly.elmerkato.databinding.FragmentHomeBinding;
import com.ma7moud3ly.elmerkato.databinding.SectionRecyclerBinding;
import com.ma7moud3ly.elmerkato.models.CategoriesViewModel;
import com.ma7moud3ly.elmerkato.models.HomeViewModel;
import com.ma7moud3ly.elmerkato.repositories.Category;
import com.ma7moud3ly.elmerkato.repositories.Company;
import com.ma7moud3ly.elmerkato.util.CONSTANTS;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


public class HomeFragment extends BaseFragment {
    private HomeViewModel model;
    private FragmentHomeBinding binding;
    private final int max_more_items = 4;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater);
        binding.setUi(((MainActivity) getActivity()).uiState);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(getResources().getString(R.string.nav_home), "", "");
        currentFragment(CONSTANTS.HOME_FRAGMENT);
        uiState.showSearch.set(false);
        model = new ViewModelProvider(getActivity(), viewModelFactory).get(HomeViewModel.class);
        model.ads.observe(getViewLifecycleOwner(), ads -> {
            if (ads != null && ads.size() > 0) {
                FirebaseDatabase.getInstance().getReference().keepSynced(false);
                ((MainActivity) getActivity()).initImageSlider(ads, binding.productSlider, binding.productIndicator, CONSTANTS.FROM_ADS,true);
                networkState(CONSTANTS.LOADED);
                uiState.showAds.set(true);
            } else {
                networkState(CONSTANTS.RETRY);
            }
        });
        model.categories.observe(getViewLifecycleOwner(), data -> {
            if (data != null && data.size() > max_more_items) {
                FirebaseDatabase.getInstance().getReference().keepSynced(false);
                List<Category> categories = new ArrayList<>();
                Random randomizer = new Random();
                for (int i = 0; i < max_more_items; i++)
                    categories.add(data.get(randomizer.nextInt(data.size())));
                initRecyclerView(binding.categoriesRecyclerView, new CategoryAdapter(categories, uiState));
                networkState(CONSTANTS.LOADED);
            } else {
                networkState(CONSTANTS.RETRY);
            }
        });
        model.companies.observe(getViewLifecycleOwner(), data -> {
            if (data != null && data.size() > max_more_items) {
                FirebaseDatabase.getInstance().getReference().keepSynced(false);
                List<Company> companies = new ArrayList<>();
                Random randomizer = new Random();
                for (int i = 0; i < max_more_items; i++)
                    companies.add(data.get(randomizer.nextInt(data.size())));
                initRecyclerView(binding.companiesRecyclerView, new CompaniesAdapter(companies, uiState));
                networkState(CONSTANTS.LOADED);
            } else {
                networkState(CONSTANTS.RETRY);
            }
        });

        read();
    }



    @Override
    public void read() {
        if (model.nullOrEmpty()) {
            super.read();
            model.read();
        }
    }

    @Override
    public void onNetworkRetry() {
        super.onNetworkRetry();
        model.read();
    }


}

