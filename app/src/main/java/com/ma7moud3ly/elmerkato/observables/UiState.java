/**
 * El Merkato الميركاتو -
 * @author  Mahmoud Aly
 * @version 1.0
 * @since   2020-12-04
 */
package com.ma7moud3ly.elmerkato.observables;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.ma7moud3ly.elmerkato.R;
import com.ma7moud3ly.elmerkato.repositories.ImagesRepository;
import com.ma7moud3ly.elmerkato.util.CONSTANTS;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

@Singleton
public class UiState {

    public ObservableField<Boolean> isSearch = new ObservableField<>();
    public ObservableField<Integer> footerButton = new ObservableField<>();
    public ObservableField<Integer> split = new ObservableField<>();
    public ObservableField<Boolean> showSlider = new ObservableField<>();
    public ObservableField<Boolean> showImages = new ObservableField<>();
    public ObservableField<Integer> state = new ObservableField<>();
    public ObservableField<String> title = new ObservableField<>();
    public ObservableField<String> subTitle = new ObservableField<>();
    public ObservableField<String> subSubTitle = new ObservableField<>();
    public ObservableField<Boolean> showAds = new ObservableField<>();
    public ObservableField<Boolean> showSearch = new ObservableField<>();

    @Inject
    public UiState() {
        isSearch.set(false);
        footerButton.set(1);
        split.set(CONSTANTS.SINGLE);
        state.set(CONSTANTS.LOADING);
        showImages.set(true);
        showSlider.set(true);
        title.set("");
        subTitle.set("");
        subSubTitle.set("");
        showAds.set(false);
        showSearch.set(false);
    }

    //to show whether 2 or 3 items in the row of recyclerView
    public void splitRecycler(View v) {
        CheckBox btn = (CheckBox) v;
        if (btn.isChecked())
            this.split.set(CONSTANTS.DOUBLE);
        else
            this.split.set(CONSTANTS.SINGLE);
    }

    //load or stop loading images from the server
    public void showImages(View v) {
        CheckBox btn = (CheckBox) v;
        ImagesRepository.loadImages = btn.isChecked();
        this.showImages.set(btn.isChecked());
    }

    /**
     * custom data binding xml attribute to change color of footer icons depending on  the current fragment
     */
    @BindingAdapter("tint")
    public static void tint(ImageView view, boolean selected) {
        if (selected)
            view.setColorFilter(ContextCompat.getColor(view.getContext(), R.color.black));
        else
            view.setColorFilter(ContextCompat.getColor(view.getContext(), R.color.white));
    }

    /**
     * custom data binding xml attribute to change the GridLayoutManager of the RecyclerView tp split the row in single or two cols
     */
    @BindingAdapter("myLayoutManager")
    public static void myLayoutManager(RecyclerView view, int split) {
        int buffer_size = split == CONSTANTS.SINGLE ? 2 : 3;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), buffer_size, GridLayoutManager.VERTICAL, false);
        view.setLayoutManager(gridLayoutManager);
    }


}
