/**
 * El Merkato الميركاتو -
 *
 * @author Mahmoud Aly
 * @version 1.0
 * @since 2020-12-04
 */
package com.ma7moud3ly.elmerkato.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.ma7moud3ly.elmerkato.R;
import com.ma7moud3ly.elmerkato.activities.MainActivity;
import com.ma7moud3ly.elmerkato.activities.PreviewActivity;
import com.ma7moud3ly.elmerkato.repositories.ImagesRepository;
import com.ma7moud3ly.elmerkato.util.CONSTANTS;

import java.io.ByteArrayOutputStream;
import java.util.List;

import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {
    private List<String> images;
    private String loadImagesFrom;

    public SliderAdapter(List<String> images, String loadImagesFrom) {
        this.images = images;
        this.loadImagesFrom = loadImagesFrom;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        Context context = view.getContext();
        View layout = LayoutInflater.from(context).inflate(R.layout.item_slide, view, false);
        final ImageView imageView = layout.findViewById(R.id.slider_image);
        imageView.setOnClickListener(v -> {
            try {
                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                Intent intent = new Intent(context, PreviewActivity.class);
                intent.putExtra("image", byteArray);
                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ImagesRepository.loadImage(loadImagesFrom, images.get(position), imageView);
        view.addView(layout, 0);
        return layout;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
