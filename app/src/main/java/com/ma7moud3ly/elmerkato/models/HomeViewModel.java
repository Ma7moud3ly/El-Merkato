/**
 * El Merkato الميركاتو -
 * @author  Mahmoud Aly
 * @version 1.0
 * @since   2020-12-04
 */
package com.ma7moud3ly.elmerkato.models;

import com.ma7moud3ly.elmerkato.repositories.CategoriesRepository;
import com.ma7moud3ly.elmerkato.repositories.Category;
import com.ma7moud3ly.elmerkato.repositories.CompaniesRepository;
import com.ma7moud3ly.elmerkato.repositories.Company;
import com.ma7moud3ly.elmerkato.repositories.HomeRepository;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class HomeViewModel extends ViewModel {
    public MutableLiveData<List<String>> ads;
    public MutableLiveData<List<Category>> categories;
    public MutableLiveData<List<Company>> companies;
    public MutableLiveData<List<Company>> products;

    private HomeRepository homeRepo;
    private CategoriesRepository catRepo;
    private CompaniesRepository compRepo;

    @Inject
    public HomeViewModel(HomeRepository homeRepo, CategoriesRepository catRepo, CompaniesRepository compRepo) {
        this.homeRepo = homeRepo;
        this.ads = homeRepo.data;

        this.catRepo = catRepo;
        this.categories = catRepo.data;

        this.compRepo = compRepo;
        this.companies = compRepo.data;
    }

    public void read() {
        homeRepo.read();
        catRepo.read();
        compRepo.read("");
    }

    public boolean nullOrEmpty() {
        return (ads.getValue() == null || ads.getValue().isEmpty()) ||
                (categories.getValue() == null || categories.getValue().isEmpty()) ||
                (companies.getValue() == null || companies.getValue().isEmpty());
    }

}
