/**
 * El Merkato الميركاتو -
 * @author  Mahmoud Aly
 * @version 1.0
 * @since   2020-12-04
 */
package com.ma7moud3ly.elmerkato.repositories;

import com.google.gson.Gson;

import java.util.List;

public class Product {
    public String name, old_price, new_price, details, company_id, product_id, department,company;
    public List<String> images;
    public Integer discount;
    public String toString() {
        return new Gson().toJson(this);
    }

    public static Product deserialize(String str) {
        return new Gson().fromJson(str, Product.class);
    }
}