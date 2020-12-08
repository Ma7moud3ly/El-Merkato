/**
 * El Merkato الميركاتو -
 * @author  Mahmoud Aly
 * @version 1.0
 * @since   2020-12-04
 */
package com.ma7moud3ly.elmerkato.adapters;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BaseViewHolder extends RecyclerView.ViewHolder {
    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void setHeight(View view, int divider) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) view.getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int deviceHeight = displaymetrics.heightPixels / divider;
        view.getLayoutParams().height = deviceHeight;
    }

    public void setWidth(View view, int divider) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) view.getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int deviceWidth = displaymetrics.widthPixels / divider;
        view.getLayoutParams().width = deviceWidth;
    }

    public void setWH(View view, int divider) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) view.getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels / divider - 30;
        view.getLayoutParams().height = width - 10;
        view.getLayoutParams().width = width;

    }
}
