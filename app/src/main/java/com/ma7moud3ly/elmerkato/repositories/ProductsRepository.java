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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ma7moud3ly.elmerkato.App;
import com.ma7moud3ly.elmerkato.util.CONSTANTS;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

public class ProductsRepository {
    @Inject
    ProductsRepository() {
    }

    public MutableLiveData<List<Product>> data = new MutableLiveData<>();
    public MutableLiveData<Long> items_count = new MutableLiveData<>();

    private Query getQuery(String load_from, String key) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("products");
        Query query;
        if (!load_from.isEmpty() && !key.isEmpty())
            query = myRef.orderByChild(load_from).equalTo(key);
        else {//of all stores
            query = myRef;
        }
        return query;
    }

    private Product snapshotToProduct(DataSnapshot snapshot) {
        try {
            Product product = new Product();
            product.product_id = snapshot.getKey();
            product.department = snapshot.child("department").getValue().toString();
            product.company = snapshot.child("company").getValue().toString();
            product.company_id = snapshot.child("company_id").getValue().toString();
            product.details = snapshot.child("details").getValue().toString();
            product.name = snapshot.child("name").getValue().toString();
            product.new_price = snapshot.child("discount").getValue().toString();
            product.old_price = snapshot.child("price").getValue().toString();
            if (!product.new_price.isEmpty() && !product.old_price.isEmpty()) {
                Double _new = Double.parseDouble(product.new_price);
                Double _old = Double.parseDouble(product.old_price);
                product.discount = (int) ((_new - _old) / _old * 100);
            }
            List<String> images = new ArrayList<>();
            long images_count = (long) snapshot.child("imgs").getValue();
            for (int i = 0; i < images_count; i++)
                images.add(product.product_id + "__" + (i + 1) + ".jpg");
            product.images = images;
            return product;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void count(MyPager pager) {
        Query query = getQuery("", "");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                items_count.setValue(dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void read(String load_from, String key, MyPager pager) {
        try {
            List<Product> list = new ArrayList<>();
            Query query = getQuery(load_from, key);
            if (load_from.isEmpty())
                query = query.orderByKey().limitToFirst(pager.page_size);
            else {
                query = query.limitToFirst(pager.page_size);
                if (!pager.last_key.equals("")) query = query.startAt(pager.last_key);
            }
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshots) {
                    for (DataSnapshot snapshot : snapshots.getChildren()) {
                        if (pager.last_key.equals(snapshots.getKey())) continue;
                        Product product = snapshotToProduct(snapshot);
                        if (product != null)
                            list.add(product);
                    }
                    data.setValue(list);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    App.l(databaseError.getDetails());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            App.l(e.getMessage());
        }
    }

    public void search(String load_from, String key, String search_query) {
        try {
            List<Product> list = new ArrayList<>();
            Query query = getQuery(load_from, key);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshots) {
                    for (DataSnapshot snapshot : snapshots.getChildren()) {
                        if (snapshot.hasChild("name") && snapshot.child("name").getValue().toString().contains(search_query)) {
                            Product product = snapshotToProduct(snapshot);
                            list.add(product);
                        }
                    }
                    data.setValue(list);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    App.l(databaseError.getDetails());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            App.l(e.getMessage());
        }
    }


}
