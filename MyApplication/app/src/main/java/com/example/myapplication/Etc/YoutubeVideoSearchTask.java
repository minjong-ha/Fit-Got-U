package com.example.myapplication.Etc;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;

public class YoutubeVideoSearchTask extends AsyncTask<Void, Void, JSONObject> {
    private String key;
    private String id;

    public YoutubeVideoSearchTask(String key, String id) {
        this.key = key;
        this.id = id;
    }

    @Override
    protected JSONObject doInBackground(Void... voids) {
        HttpURLConnection conn = null;
        JSONObject jsonObject = new JSONObject();
        StringBuffer postParameters = new StringBuffer();
        String link = "https://www.googleapis.com/youtube/v3/search?"
                + "part=snippet&channelId=" + id
                + "&key="+ key+"&maxResults=50";
        try {
            HttpGet httpGet = new HttpGet(link);
            HttpClient client = new DefaultHttpClient();
            HttpResponse response;
            StringBuilder stringBuilder = new StringBuilder();
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while ((b = stream.read()) != -1) {
                stringBuilder.append((char) b);
            }
            jsonObject = new JSONObject(stringBuilder.toString());

            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
