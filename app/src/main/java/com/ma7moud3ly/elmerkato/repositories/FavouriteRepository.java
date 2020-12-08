/**
 * El Merkato الميركاتو -
 * @author  Mahmoud Aly
 * @version 1.0
 * @since   2020-12-04
 */
package com.ma7moud3ly.elmerkato.repositories;

import android.content.Context;

import com.ma7moud3ly.elmerkato.storages.Favourite;
import com.ma7moud3ly.elmerkato.storages.FavouriteDao;
import com.ma7moud3ly.elmerkato.storages.MyRoomDatabase;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


public class FavouriteRepository {

    private FavouriteDao dao;
    public MutableLiveData<Boolean> exists = new MutableLiveData<>();
    private LiveData<List<Favourite>> favList;
    private Context context;
    @Inject
    public FavouriteRepository(Context context) {
        MyRoomDatabase db = MyRoomDatabase.getDatabase(context);
        this.context = context;
        this.dao = db.dao();
        this.favList = dao.getAll();
    }

    public LiveData<List<Favourite>> getAll() {
        return favList;
    }

    public void insert(Product product) {
        Favourite row = new Favourite(product.product_id,product.toString());
        MyRoomDatabase.databaseWriteExecutor.execute(() -> {
            dao.insert(row);
        });
    }

    public void update(Product product) {
        Favourite row = new Favourite(product.product_id,product.toString());
        MyRoomDatabase.databaseWriteExecutor.execute(() -> {
            dao.update(row);
        });
    }

    public void clear() {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> {
            dao.deleteAll();
        });
    }

    public void delete(String col) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> {
            dao.delete(col);
        });
    }

    public void isExist(String col) {
        //exists.setValue(false);
        MyRoomDatabase.databaseWriteExecutor.execute(() -> {
            exists.postValue(dao.exists(col));
        });
        //return exists;
    }

}
