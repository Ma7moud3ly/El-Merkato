/**
 * El Merkato الميركاتو
 *
 * @author Mahmoud Aly
 * @version 1.0
 * @since 2020-12-04
 */
package com.ma7moud3ly.elmerkato;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;


public class App extends Application {
    private static Context context;
    private final static String DEBUG_TAG = "HINT";

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = getApplicationContext();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    public static void toast(Object o) {
        Toast.makeText(getContext(), o.toString(), Toast.LENGTH_SHORT).show();
    }


    public static void l(Object msg) {
        Log.i(DEBUG_TAG, msg.toString());
    }

    public static Context getContext() {
        return context;
    }


}
