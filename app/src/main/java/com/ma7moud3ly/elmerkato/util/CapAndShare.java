package com.ma7moud3ly.elmerkato.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.ma7moud3ly.elmerkato.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/*
 * capture and share
 * capture a view as bitmap and share or store it locally*/
public class CapAndShare {
    private final Context context;
    private final View view;
    public static final String SCREENSHOTS = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Screenshots").getAbsolutePath();

    public CapAndShare(Context context, View view) {
        this.context = context;
        this.view = view;
    }

    public File save(String name, String path) {
        File pathDir = new File(path);
        if (!pathDir.exists()) pathDir.mkdirs();
        return captureLocally(path + "/" + name + "-" + System.currentTimeMillis() + ".jpg");
    }

    public void share(String path, String subject, String text) {
        File pathDir = new File(path);
        if (!pathDir.exists()) pathDir.mkdirs();
        File capture = captureLocally(path + "/super-novel.jpg");
        shareIntent(capture, subject, text);
    }

    private void shareIntent(File file, String subject, String text) {
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        try {
            context.startActivity(Intent.createChooser(intent, context.getResources().getString(R.string.app_name)));
        } catch (Exception e) {
            Toast.makeText(context, "No App Available", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private File captureLocally(String path) {
        try {
            File capture = new File(path);
            view.setFocusable(false);
            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = view.getDrawingCache();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 95, new FileOutputStream(capture));
            view.setDrawingCacheEnabled(false);
            addToGallery(capture);
            return capture;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void addToGallery(File pic) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(pic);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

}
