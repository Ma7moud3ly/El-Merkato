
/**
 * El Merkato الميركاتو -
 * @author  Mahmoud Aly
 * @version 1.0
 * @since   2020-12-04
 */
package com.ma7moud3ly.elmerkato.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.github.chrisbanes.photoview.PhotoView;
import com.ma7moud3ly.elmerkato.R;

import androidx.appcompat.app.AppCompatActivity;

//this activity receives an image form intent and show it with zoom ability PhotoView
public class PreviewActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        PhotoView img = findViewById(R.id.showimage);
        if (getIntent().hasExtra("image")) {
            try {
                byte[] byteArray = getIntent().getByteArrayExtra("image");
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                img.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
