package com.example.myapplication.Etc;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;

public class ImageTask extends AsyncTask<String, Void, Bitmap> {
    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        Bitmap bitmap = null;
        InputStream in = null;

        try {
            in = new java.net.URL(url).openStream();
            bitmap = BitmapFactory.decodeStream(in);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                in.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        return bitmap;
    }
}
