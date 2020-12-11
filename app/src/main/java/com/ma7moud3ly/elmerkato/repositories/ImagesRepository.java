/**
 * El Merkato الميركاتو -
 *
 * @author Mahmoud Aly
 * @version 1.0
 * @since 2020-12-04
 */
package com.ma7moud3ly.elmerkato.repositories;

import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ma7moud3ly.elmerkato.App;
import com.ma7moud3ly.elmerkato.R;


import androidx.databinding.BindingAdapter;

public class ImagesRepository {
    public static boolean loadImages = true;


    @BindingAdapter("loadImage")
    public static void loadImage(ImageView imageView, String path) {
        load("https://firebasestorage.googleapis.com/v0/b/market-sohag.appspot.com/o/" +
                path.replace("/", "%2F") + "?alt=media", imageView);
        /*
        App.getStorageReference().child(path).getDownloadUrl().addOnSuccessListener(uri -> {
            load(uri.toString(), imageView);
        }).addOnFailureListener(exception -> {
            App.l(exception.getMessage());
        });*/
    }


    private static void load(String url, ImageView imageView) {
        if (!loadImages) return;
        RequestOptions options = new RequestOptions().centerCrop().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher);
        Glide.with(App.getContext()).load(url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).apply(options).into(imageView);
    }

}


