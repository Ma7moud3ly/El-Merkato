/**
 * El Merkato الميركاتو -
 * @author  Mahmoud Aly
 * @version 1.0
 * @since   2020-12-04
 */
package com.ma7moud3ly.elmerkato.models;

import com.ma7moud3ly.elmerkato.repositories.CompaniesRepository;
import com.ma7moud3ly.elmerkato.repositories.Company;


import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CompaniesViewModel extends ViewModel {
    public MutableLiveData<List<Company>> data = new MutableLiveData<>();
    public MutableLiveData<Company> company = new MutableLiveData<>();
    public String category;
    private CompaniesRepository repo;

    @Inject
    public CompaniesViewModel(CompaniesRepository repo) {
        this.repo = repo;
        data = repo.data;
    }

    public void read(String category) {
        repo.read(category);
    }

    public boolean nullOrEmpty() {
        return data.getValue() == null || data.getValue().isEmpty();
    }
}
