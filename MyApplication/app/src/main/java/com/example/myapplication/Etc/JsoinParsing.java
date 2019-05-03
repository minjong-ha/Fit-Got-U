package com.example.myapplication.Etc;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class JsoinParsing {
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
}
