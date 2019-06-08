package com.example.Etc;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DBPHPTask extends AsyncTask<String, Void, String> {
    private String phpname;
    private String address = "http://ec2-54-158-199-36.compute-1.amazonaws.com/fitgotu/";//util의 sendNotification도 바꿔야되요!

    public DBPHPTask(String phpname) {
        this.phpname = phpname;
    }

    public DBPHPTask(String phpname, String newaddress) {
        this.phpname = phpname;
        this.address = newaddress;
    }

    @Override
    protected String doInBackground(String... Strings) {
        HttpURLConnection conn = null;
        StringBuffer postParameters = new StringBuffer();
        String link = address + phpname + ".php";//쿼리php 파일 불러오기
        try {
            int num = 0;
            for (String str : Strings) {
                if (num % 2 == 0) {
                    if (num > 0) {
                        postParameters.append("&");
                    }
                    postParameters.append(str).append("=");
                } else {
                    postParameters.append(str);
                }
                num++;
            }

            URL url = new URL(link);

            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setDefaultUseCaches(false);
            conn.connect();

            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postParameters.toString().getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();

            InputStream inputStream;
            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = conn.getInputStream();
            } else {
                inputStream = conn.getErrorStream();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder builder = new StringBuilder();

            String line;

            while((line = reader.readLine()) != null) {
                builder.append(line);
            }
            reader.close();

            return builder.toString().trim();
        } catch (Exception e) {
            e.printStackTrace();

            return e.getMessage();
        }
    }
}
