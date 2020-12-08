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
import com.ma7moud3ly.elmerkato.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

public class CompaniesRepository {
    public MutableLiveData<List<Company>> data = new MutableLiveData<>();
    public MutableLiveData<Company> company = new MutableLiveData<>();

    @Inject
    CompaniesRepository(){}

    public void read(String category) {
        try {
            List<Company> list = new ArrayList<>();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("companies");
            Query q;
            if (!category.equals("")) {
                q = myRef.orderByChild("category").equalTo(category.trim());

            } else
                q = myRef;
            q.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot cats) {
                    for (DataSnapshot cat : cats.getChildren()) {
                        Company company = new Company();
                        company.id = cat.getKey();
                        company.name = cat.child("name").getValue().toString();
                        company.details = cat.child("details").getValue().toString();
                        company.products = cat.child("prod").getValue().toString();
                        company.address = cat.child("address").getValue().toString();
                        company.phone = cat.child("phone").getValue().toString();
                        company.category = cat.child("category").getValue().toString();
                        List<String> images = new ArrayList<>();
                        long images_count = (long) cat.child("imgs").getValue();
                        for (int i = 0; i < images_count; i++)
                            images.add(company.id + "__" + (i + 1) + ".jpg");
                        company.images = images;

                        if (cats.hasChild("code") && cats.hasChild("discount")) {
                            company.code = cats.child("code").getValue().toString();
                            company.discount = cats.child("discount").getValue().toString();
                        }
                        List<Category> departments = new ArrayList();
                        if (cat.hasChild("departs")) {
                            for (DataSnapshot depart : cat.child("departs").getChildren()) {
                                Category department = new Category();
                                department.id = depart.getKey();
                                department.name = depart.child("name").getValue().toString();
                                department.image = "";
                                String dep_images_count = depart.child("img").getValue().toString();
                                if (!dep_images_count.equals(""))
                                    department.image = department.id + ".jpg";
                                departments.add(department);
                            }
                        }
                        company.departments = departments;
                        list.add(company);
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
        }
    }

    public void readCompany(String id) {
        try {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("companies/" + id);
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.getValue() == null) {
                        company.setValue(new Company());
                        return;
                    }
                    Company comp = new Company();
                    comp.name = snapshot.child("name").getValue().toString();
                    comp.details = snapshot.child("details").getValue().toString();
                    comp.products = snapshot.child("prod").getValue().toString();
                    comp.address = snapshot.child("address").getValue().toString();
                    comp.phone = snapshot.child("phone").getValue().toString();
                    company.setValue(comp);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    App.l(databaseError.getDetails());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
