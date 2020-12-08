/**
 * El Merkato الميركاتو -
 * @author  Mahmoud Aly
 * @version 1.0
 * @since   2020-12-04
 */
package com.ma7moud3ly.elmerkato.repositories;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ma7moud3ly.elmerkato.App;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;

public class CategoriesRepository {
    public MutableLiveData<List<Category>> data = new MutableLiveData<>();

    @Inject
    CategoriesRepository(){
    }
    public void read() {
        try {
            List<Category> list = new ArrayList<>();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("categories");
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot cats) {
                    for (DataSnapshot cat : cats.getChildren()) {
                        String id = cat.getKey();
                        String name = cat.child("category").getValue().toString();
                        String img = cat.child("img").getValue().toString();
                        Category category = new Category(id, name, img);
                        list.add(category);
                    }
                    data.setValue(list);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    App.l(databaseError.getDetails());
                }
            });
        } catch (Exception e) {
            App.l("error : " + e.getMessage());
            e.printStackTrace();
        }
    }

}
