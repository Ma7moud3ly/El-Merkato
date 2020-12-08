/**
 * El Merkato الميركاتو -
 * @author  Mahmoud Aly
 * @version 1.0
 * @since   2020-12-04
 */
package com.ma7moud3ly.elmerkato.repositories;

import com.google.gson.Gson;

import java.util.List;

public class Company {
    public String id, name, details, products, address, phone, category, code, discount;
    public List<String> images;
    public List<Category> departments;

    public String toString() {
        return new Gson().toJson(this);
    }

    public static Company deserialize(String str) {
        return new Gson().fromJson(str, Company.class);
    }
}