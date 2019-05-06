package com.example.myapplication.Etc;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;

import com.example.myapplication.Activity.JoinActivity;
import com.example.myapplication.Activity.LoginActivity;
import com.example.myapplication.Activity.MainActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Util {
    private static ArrayList<HashMap<String, String>> getJsonTable(String JsonString) {
        ArrayList<HashMap<String, String>> mArrayList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(JsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("cs");

            for(int i=0;i<jsonArray.length();i++){
                JSONObject item = jsonArray.getJSONObject(i);

                String id = item.getString("id");
                String ownerid = item.getString("ownerid");

                HashMap<String,String> hashMap = new HashMap<>();

                hashMap.put("id", id);
                hashMap.put("ownerid", ownerid);

                mArrayList.add(hashMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mArrayList;
    }

    public static Bitmap getImagefromURL(String path) {
        Bitmap bitmap = null;
        try {
            ImageTask task = new ImageTask();
            bitmap = task.execute(path).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static class ImageTask extends AsyncTask<String, Void, Bitmap> {
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

    public static void startLoginActivity(Activity activity) {
        ActivityCompat.finishAffinity(activity);
        activity.startActivity(new Intent(activity.getApplicationContext(), LoginActivity.class));
    }

    public static void startJoinActivity(Activity activity) {
        ActivityCompat.finishAffinity(activity);
        activity.startActivity(new Intent(activity.getApplicationContext(), JoinActivity.class));
    }

    public static void startMainActivity(Activity activity) {
        ActivityCompat.finishAffinity(activity);
        activity.startActivity(new Intent(activity.getApplicationContext(), MainActivity.class));
    }
}
