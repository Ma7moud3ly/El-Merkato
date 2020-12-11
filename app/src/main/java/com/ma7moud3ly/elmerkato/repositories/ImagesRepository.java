/**
 * El Merkato الميركاتو -
 *
 * @author Mahmoud Aly
 * @version 1.0
 * @since 2020-12-04
 */
package com.ma7moud3ly.elmerkato.repositories;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ma7moud3ly.elmerkato.App;
import com.ma7moud3ly.elmerkato.R;

import androidx.databinding.BindingAdapter;

public class ImagesRepository {
    public static boolean loadImages = true;


    public static void loadImage(String dir, String name, ImageView imageView) {
        App.getStorageReference().child(dir + "/" + name).getDownloadUrl().addOnSuccessListener(uri -> {
            load(uri.toString(), imageView);
        }).addOnFailureListener(exception -> {
            imageView.setImageResource(R.mipmap.ic_launcher);
            App.l(exception.getMessage());
        });
    }


    @BindingAdapter("loadImage")
    public static void loadImage(ImageView imageView, String path) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        storageReference.child(path).getDownloadUrl().addOnSuccessListener(uri -> {
            load(uri.toString(), imageView);
        }).addOnFailureListener(exception -> {
            imageView.setImageResource(R.mipmap.ic_launcher);
            App.l(exception.getMessage());
        });
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


