package com.example.Etc;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;

public class YoutubeUserSearchTask extends AsyncTask<Void, Void, JSONObject> {
    private String key;
    private String nameid;

    public YoutubeUserSearchTask(String key, String nameid) {
        this.key = key;
        this.nameid = nameid;
    }

    @Override
    protected JSONObject doInBackground(Void... voids) {
        HttpURLConnection conn = null;
        JSONObject jsonObject = new JSONObject();
        StringBuffer postParameters = new StringBuffer();
        String link = "https://www.googleapis.com/youtube/v3/channels?"
                + "part=snippet&" + "&key="+ key + "&forUsername=" + nameid;
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
