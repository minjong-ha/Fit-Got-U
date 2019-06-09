package com.example.PoseEstimation;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Pair;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;

public class MySQLiteOpenHelperForPose extends SQLiteOpenHelper implements Serializable {
    static final private String[] bodyPart = {"좌측 팔 하박", "좌측 팔 상박", "좌측 다리 하박", "좌측 다리 상박",
            "몸통", "엉덩이", "우측 팔 하박", "우측 팔 상박", "우측 다리 하박", "우측 다리 상박",
            "좌측 어깨", "우측 어깨"};

    static private Context context = null;
    static private int recordID;

    public MySQLiteOpenHelperForPose(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
        onCreate(openDB());
    }

    @Override
    public void onCreate(SQLiteDatabase sqliteDB) {
        // 최초에 데이터베이스가 없을경우, 데이터베이스 생성을 위해 호출됨
        // 테이블 생성하는 코드를 작성한다
        try {

            if (sqliteDB != null) {
                String sqlCreateTbl;

                sqlCreateTbl = "CREATE TABLE IF NOT EXISTS EXERCISE_RECORD (" +
                        "Id " + "INTEGER primary key autoincrement," +
                        "Exercise_name " + "TEXT," +
                        "Exercise_date " + "TEXT," +
                        "Exercise_count " + "INTEGER" + ");";
                System.out.println(sqlCreateTbl);
                sqliteDB.execSQL(sqlCreateTbl);

                sqlCreateTbl = "CREATE TABLE IF NOT EXISTS EXERCISE_DESCRIBE (" +
                        "Id " + "INTEGER primary key autoincrement," +
                        "Weak_part " + "TEXT," +
                        "Weak_angle " + "INTEGER," +
                        "Record_id " + "INTEGER," +
                        "CONSTRAINT Record_id_fk FOREIGN KEY(Record_id) " +
                        "REFERENCES EXERCISE_RECORD(Id));";

                System.out.println(sqlCreateTbl);
                sqliteDB.execSQL(sqlCreateTbl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    } // 쿼리를 가짐

    @Override
    public void onUpgrade(SQLiteDatabase sqliteDB, int oldVersion, int newVersion) { //쿼리를 가짐

        if (sqliteDB != null) {
            String sql = "drop table 'EXERCISE_DESCRIBE';"; // 테이블 드랍
            sqliteDB.execSQL(sql);
            sql = "drop table 'EXERCISE_RECORD';"; // 테이블 드랍
            sqliteDB.execSQL(sql);

            onCreate(sqliteDB); // 다시 테이블 생성
        }
    }

    private static SQLiteDatabase openDB() {
        File file = new File(context.getFilesDir(), "jelly.db");
        SQLiteDatabase db = null;
        System.out.println("PATH : " + file.toString());
        try {
            db = SQLiteDatabase.openOrCreateDatabase(file, null);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return db;
    }

    // ******
    public static int insertRecord(String exerciseName, String exerciseDate, int exerciseCount) {
        SQLiteDatabase db = openDB();
        int recordID = -1;
        exerciseName = "'" + exerciseName + "'";
        exerciseDate = "'" + exerciseDate + "'";
        if (db != null) {

            String sql = "INSERT INTO EXERCISE_RECORD ( Exercise_name, Exercise_date, Exercise_count ) VALUES ( " + exerciseName + ", " + exerciseDate + ", " + exerciseCount + ");";
            db.execSQL(sql);

            sql = "SELECT Id From EXERCISE_RECORD WHERE " +
                    "Exercise_name = " + exerciseName + " AND " +
                    "Exercise_date = " + exerciseDate + " ;";
            Cursor cursor = null;

            cursor = db.rawQuery(sql, null);

            if (cursor.moveToNext()) {
                recordID = cursor.getInt(0);
            }
        }
        MySQLiteOpenHelperForPose.recordID = recordID;
        return recordID;

    } // 쿼리를 가짐

    public static int getRecordID() {
        return recordID;
    }

    public static void setRecordID(int recordID) {
        MySQLiteOpenHelperForPose.recordID = recordID;
    }

    //******
    public static void insertDescribe(int recordID, String weakPart, int weakAngle) { // 퀴리를 가짐
        SQLiteDatabase db = openDB();
        weakPart = "'" + weakPart + "'";
        if (db != null) {
            String sql;
            sql = "insert into EXERCISE_DESCRIBE(Weak_part, Weak_angle, Record_id) values (" +
                    weakPart + ", " + weakAngle + ", " + recordID + ");";
            db.execSQL(sql);
        }
    }

    //*******
    public static void updateRecordCount(int recordID, int exerciseCount) {
        SQLiteDatabase db = openDB();
        if (db != null) {
            String sql = "UPDATE EXERCISE_RECORD SET Exercise_count = " + exerciseCount +
                    " WHERE Id = " + recordID + ";";
            db.execSQL(sql);
        }

    }

    static public String todayDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(cal.YEAR);
        int month = cal.get(cal.MONTH) + 1;
        int date = cal.get(cal.DATE);

        int hour = cal.get ( cal.HOUR_OF_DAY ) ;
        int min = cal.get ( cal.MINUTE );
        int sec = cal.get ( cal.SECOND );


        return year+"-"+dateFormat(month+"")+"-"+dateFormat(date+"")+"-"+dateFormat(hour+"")+
                "-"+dateFormat(min+"")+"-"+dateFormat(sec+"");
    }

    static private String dateFormat(String raw_date) {
        if (raw_date.length() == 1)
            return "0" + raw_date;
        return raw_date;
    }
}