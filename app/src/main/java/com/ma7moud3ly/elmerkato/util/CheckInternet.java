package com.ma7moud3ly.elmerkato.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ma7moud3ly.elmerkato.App;

public class CheckInternet {
    public static boolean isConnected() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        } catch (Exception e) {
            return false;
        }
    }
}
