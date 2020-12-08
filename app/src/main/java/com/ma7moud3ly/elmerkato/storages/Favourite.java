package com.ma7moud3ly.elmerkato.storages;

import com.ma7moud3ly.elmerkato.repositories.Product;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = Favourite.TABLE_NAME)
public class Favourite {
    public static final String DB_NAME = "fav_db";

    public static final String TABLE_NAME = "fav_products";

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "_id")
    public String id;
    @NonNull
    @ColumnInfo(name = "data")
    public String data;

    public Favourite(String id,String data) {
        this.id = id;
        this.data =data;
    }


    public Product toProduct() {
        return Product.deserialize(this.data);
    }
}