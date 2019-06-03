package com.example.myapplication.Etc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.support.v4.app.ActivityCompat;

import com.example.myapplication.Activity.JoinActivity;
import com.example.myapplication.Activity.LoginActivity;
import com.example.myapplication.Activity.MainActivity;
import com.example.myapplication.Activity.TestActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Util {
    public static final int DIALOG_REQUEST_CODE = 1;

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

    public static void UpdateDBData(Context context, String value) {
        MySQLiteOpenHelper dbhelper = new MySQLiteOpenHelper(context, "DB", null, 1);
        SQLiteDatabase database = dbhelper.getWritableDatabase();
        String sql = "UPDATE db_name SET value=" + value + " WHERE name='name';";
        database.execSQL(sql);
        database.close();
        dbhelper.close();
    }

    public static String getDBData(Context context) {
        MySQLiteOpenHelper dbhelper = new MySQLiteOpenHelper(context, "DB", null, 1);
        SQLiteDatabase database = dbhelper.getReadableDatabase();
        String sql = "SELECT value FROM db_name WHERE name='name';";
        Cursor cursor = database.rawQuery(sql,null);
        String id = "0";
        while(cursor.moveToNext()){
            id = cursor.getString(0);
        }
        cursor.close();
        database.close();
        dbhelper.close();
        return id;
    }

    public static void InsertDBData(Context context) {
        MySQLiteOpenHelper dbhelper = new MySQLiteOpenHelper(context, "DB", null, 1);
        SQLiteDatabase database = dbhelper.getWritableDatabase();
        String sql = "INSERT INTO db_name VALUES('name','value')";
        database.execSQL(sql);
        database.close();
        dbhelper.close();
    }

    public static String paringYoutubeUserJsonData(JSONObject jsonObject) throws JSONException {
        String userid = null;
        JSONArray contacts = jsonObject.getJSONArray("items");

        if (contacts.length() > 0) {
            userid = contacts.getJSONObject(0).getString("id");
        }
        return userid;
    }

    public static ArrayList<YoutubeData> paringYoutubeVideoJsonData(JSONObject jsonObject) throws JSONException {
        ArrayList<YoutubeData> sdata = new ArrayList<YoutubeData>();
        JSONArray contacts = jsonObject.getJSONArray("items");
        String vodid;

        for (int i = 0; i < contacts.length(); i++) {
            JSONObject c = contacts.getJSONObject(i);
            String kind =  c.getJSONObject("id").getString("kind");
            if(kind.equals("youtube#video")){//재생목록은 버림
                vodid = c.getJSONObject("id").getString("videoId");

                String title = c.getJSONObject("snippet").getString("title");
                try {
                    title = new String(title.getBytes("8859_1"), "utf-8");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String desc = c.getJSONObject("snippet").getString("description");
                try {
                    desc = new String(desc.getBytes("8859_1"), "utf-8");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String date = c.getJSONObject("snippet").getString("publishedAt").substring(0, 10);
                String imgUrl = c.getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("default").getString("url");

                sdata.add(new YoutubeData(vodid, title, desc, imgUrl, date));
            }
        }
        return sdata;
    }

    public static String InsertUser(String kakaoid, String name, String profile_image, String address, String weight, String height, String is_user) {//트레이너 포함
        String str = null;
        try {
            DBPHPTask task = new DBPHPTask("insert_user");
            str = task.execute("user_id", kakaoid, "username", name, "profile_image", profile_image, "home_address", address, "weight", weight, "height", height, "is_user", is_user).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String UpdateUser(String kakaoid, String address, String weight, String height, String youtube) {//트레이너 포함
        String str = null;
        try {
            DBPHPTask task = new DBPHPTask("update_user");
            str =  task.execute("user_id", kakaoid, "home_address", address, "weight", weight, "height", height, "youtube", youtube).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String UpdateUser_Auto(String kakaoid, String name, String profile_image) {//트레이너 포함
        String str = null;
        try {
            DBPHPTask task = new DBPHPTask("update_user_auto");
            str =  task.execute("user_id", kakaoid, "username", name, "profile_image", profile_image).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String DeleteUser(String kakaoid) {//트레이너 포함
        String str = null;
        try {
            DBPHPTask task = new DBPHPTask("delete_user");
            str =  task.execute("user_id", kakaoid).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static HashMap<String, String> SelectUser(String userid) {//트레이너 포함
        HashMap<String, String> mHashMap = null;
        try {
            DBPHPTask task = new DBPHPTask("select_user");
            mHashMap =  getJsonUser(task.execute("user_id", userid).get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mHashMap;
    }

    public static HashMap<String, String> getJsonUser(String JsonString) {
        try {
            JSONObject jsonObject = new JSONObject(JsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("fgy");

            if (jsonArray.length() > 0) {
                JSONObject item = jsonArray.getJSONObject(0);
                String address = item.getString("home_address");
                String is_user = item.getString("is_user");
                String weight = item.getString("weight");
                String height = item.getString("height");

                HashMap<String,String> hashMap = new HashMap<>();

                hashMap.put("address", address);
                hashMap.put("is_user", is_user);
                hashMap.put("weight", weight);
                hashMap.put("height", height);

                return hashMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<HashMap<String, String>> SelectTrainerList() {
        ArrayList<HashMap<String, String>> mArrayList = new ArrayList<>();
        try {
            DBPHPTask task = new DBPHPTask("select_trainer_list");
            mArrayList =  getJsonTrainerList(task.execute().get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mArrayList;
    }

    public static ArrayList<HashMap<String, String>> getJsonTrainerList(String JsonString) {
        ArrayList<HashMap<String, String>> mArrayList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(JsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("fgy");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String userid = item.getString("user_id");
                String name = item.getString("username");
                String profile_image = item.getString("profile_image");
                String address = item.getString("home_address");
                String is_user = item.getString("is_user");
                String youtube = item.getString("youtube");
                if (youtube == null) {
                    youtube = "";
                }

                HashMap<String,String> hashMap = new HashMap<>();

                hashMap.put("userid", userid);
                hashMap.put("name", name);
                hashMap.put("profile_image", profile_image);
                hashMap.put("address", address);
                hashMap.put("is_user", is_user);
                hashMap.put("youtube", youtube);

                mArrayList.add(hashMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mArrayList;
    }

    public static String InsertFirebaseToken(String kakaoid, String token) {
        String str = null;
        try {
            DBPHPTask task = new DBPHPTask("insert_token");
            str =  task.execute("id", kakaoid, "token", token).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String UpdateFirebaseToken(String kakaoid, String token) {
        String str = null;
        try {
            DBPHPTask task = new DBPHPTask("update_token");
            str =  task.execute("id", kakaoid, "token", token).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String SelectFirebaseToken(String userid) {
        String str = "";
        try {
            DBPHPTask task = new DBPHPTask("select_token");
            str =  getJsonFirebaseToken(task.execute("id", userid).get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String getJsonFirebaseToken(String JsonString) {
        try {
            JSONObject jsonObject = new JSONObject(JsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("fgy");

            if (jsonArray.length() > 0) {
                JSONObject item = jsonArray.getJSONObject(0);
                return item.getString("token");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String sendNotification(String targetid, String title, String content, String action) {
        String str = null;
        try {
            DBPHPTask task = new DBPHPTask("null", "http://ec2-54-158-199-36.compute-1.amazonaws.com:4000/");
            str =  task.execute("targetid", targetid, "title", title, "content", content, "action", action).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static boolean Information_Filled(String weight, String height, String address, String is_user) {
        return weight != null && height != null && address != null && is_user != null && !weight.equals("") && !height.equals("") && !address.equals("") && !is_user.equals("");
    }

    public static void startLoginActivity(Activity activity) {
        ActivityCompat.finishAffinity(activity);
        activity.startActivity(new Intent(activity.getApplicationContext(), LoginActivity.class));
    }

    public static void startJoinActivity(Activity activity, long kakaoid, String name, String profile_image) {
        Intent intent = new Intent(activity.getApplicationContext(), JoinActivity.class);
        intent.putExtra("kakaoid", kakaoid);
        intent.putExtra("username", name);
        intent.putExtra("profile_image", profile_image);
        ActivityCompat.finishAffinity(activity);
        activity.startActivity(intent);
    }

    public static void startMainActivity(Activity activity) {
        ActivityCompat.finishAffinity(activity);
        activity.startActivity(new Intent(activity.getApplicationContext(), MainActivity.class));
    }

    public static void startTestActivity(Activity activity) {
        ActivityCompat.finishAffinity(activity);
        activity.startActivity(new Intent(activity.getApplicationContext(), TestActivity.class));
    }
}
