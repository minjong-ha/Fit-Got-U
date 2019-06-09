package com.example.Etc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ImageView;

import androidx.core.app.ActivityCompat;

import com.example.Activity.JoinActivity;
import com.example.Activity.LoginActivity;
import com.example.Activity.MainActivity;
import com.example.Activity.TestActivity;

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

    public static void getImagefromURL(String path, ImageView view) {
        try {
            ImageTask task = new ImageTask(view);
            task.execute(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                String username = item.getString("username");
                String address = item.getString("home_address");
                String is_user = item.getString("is_user");
                String weight = item.getString("weight");
                String height = item.getString("height");

                HashMap<String,String> hashMap = new HashMap<>();

                hashMap.put("username", username);
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
                String weight = item.getString("weight");
                String height = item.getString("height");
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
                hashMap.put("weight", weight);
                hashMap.put("height", height);

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

    public static String InsertSubscription(String uid, String tid) {
        String str = null;
        try {
            DBPHPTask task = new DBPHPTask("insert_subscription");
            str = task.execute("user_id", uid, "trainer_id", tid).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String UpdateSubscription(String id, String status) {
        String str = null;
        try {
            DBPHPTask task = new DBPHPTask("update_subscription");
            str =  task.execute("id", id, "status", status).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String DeleteSubscription(String id) {
        String str = null;
        try {
            DBPHPTask task = new DBPHPTask("delete_subscription");
            str =  task.execute("id", id).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static ArrayList<HashMap<String, String>> SelectSubscriptionbyUser(String userid) {
        ArrayList<HashMap<String, String>> mArrayList = new ArrayList<>();
        try {
            DBPHPTask task = new DBPHPTask("select_subscription_by_user");
            mArrayList =  getJsonSubscription(task.execute("user_id", userid).get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mArrayList;
    }

    public static ArrayList<HashMap<String, String>> SelectSubscriptionbyTrainer(String trainerid) {
        ArrayList<HashMap<String, String>> mArrayList = new ArrayList<>();
        try {
            DBPHPTask task = new DBPHPTask("select_subscription_by_trainer");
            mArrayList =  getJsonSubscription(task.execute("trainer_id", trainerid).get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mArrayList;
    }

    public static ArrayList<HashMap<String, String>> getJsonSubscription(String JsonString) {
        ArrayList<HashMap<String, String>> mArrayList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(JsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("fgy");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String id = item.getString("id");
                String userid = item.getString("user_id");
                String trainerid = item.getString("trainer_id");
                String status = item.getString("status");

                HashMap<String,String> hashMap = new HashMap<>();

                hashMap.put("id", id);
                hashMap.put("userid", userid);
                hashMap.put("trainerid", trainerid);
                hashMap.put("status", status);

                mArrayList.add(hashMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mArrayList;
    }

    public static String InsertRoutine(String uid, String tid, String date, String breakm, String lunchm, String dinnerm, String setm, String routinem) {
        String str = null;
        try {
            DBPHPTask task = new DBPHPTask("insert_routine");
            str = task.execute("user_id", uid, "trainer_id", tid, "date_message", date,
                    "break_message", breakm, "lunch_message", lunchm, "dinner_message", dinnerm,
                    "set_message", setm, "routine_message", routinem).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static ArrayList<HashMap<String, String>> SelectRoutine(String userid) {
        ArrayList<HashMap<String, String>> mArrayList = new ArrayList<>();
        try {
            DBPHPTask task = new DBPHPTask("select_routine");
            mArrayList =  getJsonRoutine(task.execute("user_id", userid).get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mArrayList;
    }

    public static ArrayList<HashMap<String, String>> getJsonRoutine(String JsonString) {
        ArrayList<HashMap<String, String>> mArrayList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(JsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("fgy");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String id = item.getString("id");
                String userid = item.getString("user_id");
                String trainerid = item.getString("trainer_id");
                String date_message = item.getString("date_message");
                String break_message = item.getString("break_message");
                String lunch_message = item.getString("lunch_message");
                String dinner_message = item.getString("dinner_message");
                String set_message = item.getString("set_message");
                String routine_message = item.getString("routine_message");

                HashMap<String,String> hashMap = new HashMap<>();

                hashMap.put("id", id);
                hashMap.put("userid", userid);
                hashMap.put("trainerid", trainerid);
                hashMap.put("date_message", date_message);
                hashMap.put("break_message", break_message);
                hashMap.put("lunch_message", lunch_message);
                hashMap.put("dinner_message", dinner_message);
                hashMap.put("set_message", set_message);
                hashMap.put("routine_message", routine_message);

                mArrayList.add(hashMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mArrayList;
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
