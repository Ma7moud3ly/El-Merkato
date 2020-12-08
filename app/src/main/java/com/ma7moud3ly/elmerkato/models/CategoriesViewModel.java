/**
 * El Merkato الميركاتو -
 * @author  Mahmoud Aly
 * @version 1.0
 * @since   2020-12-04
 */
package com.ma7moud3ly.elmerkato.models;

import com.ma7moud3ly.elmerkato.repositories.CategoriesRepository;
import com.ma7moud3ly.elmerkato.repositories.Category;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class CategoriesViewModel extends ViewModel {
    public MutableLiveData<List<Category>> data;
    private CategoriesRepository repo;

    @Inject
    public CategoriesViewModel(CategoriesRepository repo) {
        this.repo = repo;
        this.data = repo.data;
    }

    public void read() {
        repo.read();
    }

    public boolean nullOrEmpty() {
        return data.getValue() == null || data.getValue().isEmpty();
    }
}
