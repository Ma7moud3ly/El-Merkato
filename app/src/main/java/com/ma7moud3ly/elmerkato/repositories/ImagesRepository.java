/**
 * El Merkato الميركاتو -
 * @author  Mahmoud Aly
 * @version 1.0
 * @since   2020-12-04
 */
package com.ma7moud3ly.elmerkato.repositories;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.imageview.ShapeableImageView;
import com.ma7moud3ly.elmerkato.App;
import com.ma7moud3ly.elmerkato.R;

import androidx.databinding.BindingAdapter;

public class ImagesRepository {
    public static boolean loadImages = true;

    public static void loadAdsImage(String img, ImageView imageView) {
        if (img == null || img.equals("")) return;
        String url = "https://firebasestorage.googleapis.com/v0/b/market-sohag.appspot.com/o/ads%2F" + img + "?alt=media";
        load(url, imageView);
    }
    public static void loadProductImage(String img, ImageView imageView) {
        if (img == null || img.equals("")) return;
        String url = "https://firebasestorage.googleapis.com/v0/b/market-sohag.appspot.com/o/products%2F" + img + "?alt=media";
        load(url, imageView);
    }

    @BindingAdapter("loadCategoryImage")
    public static void loadCategoryImage(ShapeableImageView imageView, String img) {
        if (img == null || img.equals("")) return;
        String url = "https://firebasestorage.googleapis.com/v0/b/market-sohag.appspot.com/o/category%2F" + img + "?alt=media";
        load(url, imageView);
    }

    public static void loadCompanyImage(String img, ImageView imageView) {
        if (img == null || img.equals("")) return;
        String url = "https://firebasestorage.googleapis.com/v0/b/market-sohag.appspot.com/o/company%2F" + img + "?alt=media";
        load(url, imageView);
    }

    @BindingAdapter("loadDepartmentImage")
    public static void loadDepartmentImage(ImageView imageView,String img) {
        if (img == null || img.equals("")) return;
        String url = "https://firebasestorage.googleapis.com/v0/b/market-sohag.appspot.com/o/company%2Fdeparts%2F" + img + "?alt=media";
        load(url, imageView);
    }

    private static void load(String url, ImageView imageView) {
        if (!loadImages) return;
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher);
        Glide.with(App.getContext()).load(url).apply(options).into(imageView);
    }

}
