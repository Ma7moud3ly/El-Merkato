/**
 * El Merkato الميركاتو -
 * @author  Mahmoud Aly
 * @version 1.0
 * @since   2020-12-04
 */
package com.ma7moud3ly.elmerkato.models;

import com.ma7moud3ly.elmerkato.App;
import com.ma7moud3ly.elmerkato.repositories.CategoriesRepository;
import com.ma7moud3ly.elmerkato.repositories.Category;
import com.ma7moud3ly.elmerkato.repositories.Company;
import com.ma7moud3ly.elmerkato.repositories.MyPager;
import com.ma7moud3ly.elmerkato.repositories.Product;
import com.ma7moud3ly.elmerkato.repositories.ProductsRepository;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProductsViewModel extends ViewModel {
    public MutableLiveData<List<Product>> data = new MutableLiveData<>();
    public MutableLiveData<Long> items_count = new MutableLiveData<>();
    private ProductsRepository repo;
    public String load_from;

    @Inject
    public ProductsViewModel(ProductsRepository repo) {
        this.repo = repo;
        data = repo.data;
        items_count = repo.items_count;
    }

    public void read(String load_from, String key, MyPager pager) {
        repo.read(load_from, key, pager);
    }

    public void search(String load_from, String key, String search) {
        repo.search(load_from, key, search);
    }

    public void count(MyPager pager) {
        repo.count(pager);
    }

    public boolean nullOrEmpty() {
        return data.getValue() == null || data.getValue().isEmpty();
    }

}
