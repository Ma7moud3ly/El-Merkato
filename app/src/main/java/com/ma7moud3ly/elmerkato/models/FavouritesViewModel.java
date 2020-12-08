/**
 * El Merkato الميركاتو -
 * @author  Mahmoud Aly
 * @version 1.0
 * @since   2020-12-04
 */
package com.ma7moud3ly.elmerkato.models;

import com.ma7moud3ly.elmerkato.App;
import com.ma7moud3ly.elmerkato.repositories.FavouriteRepository;
import com.ma7moud3ly.elmerkato.storages.Favourite;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FavouritesViewModel extends ViewModel {
    public LiveData<List<Favourite>> data = new MutableLiveData<>();
    private FavouriteRepository repo;

    @Inject
    public FavouritesViewModel(FavouriteRepository repo) {
        this.repo = repo;
        data = repo.getAll();
    }

}
