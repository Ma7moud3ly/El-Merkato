
/**
 * El Merkato الميركاتو -
 *
 * @author Mahmoud Aly
 * @version 1.0
 * @since 2020-12-04
 */
package com.ma7moud3ly.elmerkato.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.ma7moud3ly.elmerkato.databinding.ActivityAboutBinding;

public class AboutActivity extends BaseActivity {
    private ActivityAboutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.appVersion.setText(appVersion());
    }

    //return the application version
    public String appVersion() {
        PackageManager pm = getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    //open the app in google play to rate
    public void rate(View v) {
        super.rate();
    }

    //share the app link on google play
    public void share(View v) {
        super.share();
    }

    //open a link related to the developer
    public void contact(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        Uri d = Uri.parse("http://www.facebook.com/engma7moud3ly");
        i.setData(d);
        view.getContext().startActivity(i);
    }

    //finish this activity
    public void back(View view) {
        finish();
    }

}
