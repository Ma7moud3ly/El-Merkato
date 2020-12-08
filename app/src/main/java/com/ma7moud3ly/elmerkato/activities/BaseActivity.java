/**
 * El Merkato الميركاتو
 *
 * @author Mahmoud Aly
 * @version 1.0
 * @since 2020-12-04
 */
package com.ma7moud3ly.elmerkato.activities;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.ma7moud3ly.elmerkato.R;
import com.ma7moud3ly.elmerkato.adapters.SliderAdapter;
import com.ma7moud3ly.elmerkato.di.ActivityGraph;
import com.ma7moud3ly.elmerkato.di.ViewModelFactory;
import com.ma7moud3ly.elmerkato.observables.UiState;
import com.ma7moud3ly.elmerkato.util.CONSTANTS;
import com.ma7moud3ly.ustore.UPref;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import me.relex.circleindicator.CircleIndicator;


public abstract class BaseActivity extends AppCompatActivity {
    @Inject
    public ViewModelFactory viewModelFactory;
    @Inject
    public UiState uiState;
    @Inject
    public UPref pref;

    public ActivityGraph activityGraph;


    //hide keyboard programmatically
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    private int currentPage = 0;

    /**
     * init an image slider
     * @param images: a list of images to show in the slider
     * @param slider: slider(ViewPager) view
     * @param indicator: current slide indicator
     * @param loadFrom: load slider images from which directory of database
     * @param slide: automatic slide
     **/
    public void initImageSlider(List<String> images, ViewPager slider, CircleIndicator indicator, String loadFrom, boolean slide) {
        try {
            if (images == null || images.size() == 0) {
                uiState.showSlider.set(false);
                return;
            }
            uiState.showSlider.set(true);
            slider.setAdapter(new SliderAdapter(images, loadFrom));
            indicator.setViewPager(slider);
            if (slide) {
                final Handler handler = new Handler();
                final Runnable Update = () -> {
                    if (currentPage == images.size()) {
                        currentPage = 0;
                    }
                    slider.setCurrentItem(currentPage++, true);
                };
                Timer swipeTimer = new Timer();
                swipeTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(Update);
                    }
                }, CONSTANTS.SLIDER_DELAY, CONSTANTS.SLIDER_DELAY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * send a contact to call page
     * @param  contact: phone number
     * */
    public void call(String contact) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, CONSTANTS.REQUEST_PHONE_CALL);
        } else {
            try {
                contact = contact.replace("/", "").trim();
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contact)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //share the app link on google play
    public void share() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
        sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + getApplicationInfo().packageName);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getResources().getString(R.string.app_name)));
    }

    //open the app in google play to rate
    public void rate() {
        Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
        }

    }


}
