/**
 * El Merkato الميركاتو -
 *
 * @author Mahmoud Aly
 * @version 1.0
 * @since 2020-12-04
 */
package com.ma7moud3ly.elmerkato.models;

import com.ma7moud3ly.elmerkato.App;
import com.ma7moud3ly.elmerkato.repositories.CompaniesRepository;
import com.ma7moud3ly.elmerkato.repositories.Company;
import com.ma7moud3ly.elmerkato.repositories.FavouriteRepository;
import com.ma7moud3ly.elmerkato.repositories.MyPager;
import com.ma7moud3ly.elmerkato.repositories.Product;
import com.ma7moud3ly.elmerkato.repositories.ProductsRepository;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProductViewModel extends ViewModel {
    public MutableLiveData<Company> company = new MutableLiveData<>();
    public MutableLiveData<Boolean> isFaved = new MutableLiveData<>();

    private CompaniesRepository repo;
    private FavouriteRepository favRepo;

    @Inject
    public ProductViewModel(CompaniesRepository repo, FavouriteRepository favRepo) {
        this.repo = repo;
        company = repo.company;
        this.favRepo = favRepo;
        isFaved = this.favRepo.exists;
    }

    public void readCompany(String id) {
        repo.readCompany(id);
    }

    public void isExist(Product product) {
        favRepo.isExist(product.product_id);
    }

    public void addFav(Product product) {
        favRepo.insert(product);
    }

    public void delFav(Product product) {
        favRepo.delete(product.product_id);
    }
}
