package com.example.myapplication.Etc;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class MySQLiteOpenHelper extends SQLiteOpenHelper  implements Serializable {
    final private String[] bodyPart = {"'좌측 팔 하박'", "'좌측 팔 상박'", "'좌측 다리 하박'", "'좌측 다리 상박'",
            "'몸통'", "'엉덩이'", "'우측 팔 하박'", "'우측 팔 상박'", "'우측 다리 하박'", "'우측 다리 상박'",
            "'좌측 어깨'", "'우측 어깨'"};
    private final int POINT_STANDARD_YELLO_RED = 15; // 노란 또는 빨강 기준
    private final int POINT_STANDARD_YELLO_GREEN = 5;

    final private String exerciseName[] = {"'스쿼트'", "'플랭크'", "'런지'", "'사이드런지'", "'와이드스쿼트'"};
    private Context context = null;


    // 안드로이드에서 SQLite 데이터 베이스를 쉽게 사용할 수 있도록 도와주는 클래스
    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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
                sqlCreateTbl = "CREATE TABLE IF NOT EXISTS EXERCISE_TABLE (" +
                        "Id " + "INTEGER primary key autoincrement," +
                        "Exercise_name " + "TEXT," +
                        "Weak_part " + "TEXT," +
                        "Exercise_time " + "TEXT," +
                        "Weak_angle " + "INTEGER" + ");";
                System.out.println(sqlCreateTbl);
                sqliteDB.execSQL(sqlCreateTbl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqliteDB, int oldVersion, int newVersion) {
        // 데이터베이스의 버전이 바뀌었을 때 호출되는 콜백 메서드
        // 버전 바뀌었을 때 기존데이터베이스를 어떻게 변경할 것인지 작성한다
        // 각 버전의 변경 내용들을 버전마다 작성해야함
        if (sqliteDB != null) {
            String sql = "drop table 'exercise_data';"; // 테이블 드랍
            sqliteDB.execSQL(sql);
            onCreate(sqliteDB); // 다시 테이블 생성
        }
    }

    private SQLiteDatabase openDB() {
        File file = new File(this.context.getFilesDir(), "jelly.db");
        SQLiteDatabase db = null;
        System.out.println("PATH : " + file.toString());
        try {
            db = SQLiteDatabase.openOrCreateDatabase(file, null);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return db;
    }

    public void insertExerciseDataTest() {
        SQLiteDatabase db = openDB();

        if (db != null) {
            for (int i = 0; i < exerciseName.length; i++) {
                String ex_name = exerciseName[i];
                String part_name = bodyPart[i];
                String sqlInsert = "INSERT INTO EXERCISE_TABLE (Exercise_name, Weak_part,Exercise_time,Weak_angle) VALUES (" + ex_name + ", " + part_name + ",'2019-05-19', 5);";
                db.execSQL(sqlInsert);
            }
        }

        if (db != null) {
            for (int i = 0; i < exerciseName.length; i++) {
                String ex_name = exerciseName[i];
                String part_name = bodyPart[i];
                String sqlInsert = "INSERT INTO EXERCISE_TABLE (Exercise_name, Weak_part,Exercise_time,Weak_angle) VALUES (" + ex_name + ", " + part_name + ",'2019-05-18', 2);";
                db.execSQL(sqlInsert);
            }
        }

    }

    public int[] getJointValues(String sDay, String exerciseName) {
        SQLiteDatabase db = openDB();
        int[] jointValues = new int[12];

        if (db != null) {
            Cursor cursor = null;

            for (int i = 0; i < 12; i++) {
                String partName = bodyPart[i];
                jointValues[i] = calcJointValue(partName,sDay,exerciseName);
            }
        }
        return jointValues;
    }

    private int calcJointValue(String partName, String sDay, String exerciseName) {
        SQLiteDatabase db = openDB();
        int jointValue;

        String dayQuery = "";
        String exerciseNameQuery = "";
        if(sDay!=null){
            dayQuery = " AND Exercise_time = " + sDay;
        }

        if(exerciseName != null){
            exerciseNameQuery = " AND Exercise_name = " + exerciseName;
        }

        if (db != null) {
            Cursor cursor = null;
            String sqlSelectP_Angle = "SELECT AVG(Weak_angle) as average FROM EXERCISE_TABLE WHERE Weak_part = " + partName + dayQuery+exerciseNameQuery+";";
            cursor = db.rawQuery(sqlSelectP_Angle, null);

            if (cursor.moveToNext()) {
                jointValue = cursor.getInt(0);
            } else {
                jointValue = 0;
            }
            return jointValue;
        }
        return 0;
    }

    public ArrayList<String> countDayExercise(String sDate) {
        SQLiteDatabase db = openDB();
        Cursor cursor = null;

        ArrayList<String> exerciseNames = new ArrayList<String>();

        if (db != null) {
            String query = "SELECT DISTINCT Exercise_name FROM EXERCISE_TABLE WHERE Exercise_time = " + sDate + ";";
            System.out.println(query);
            cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (cursor.moveToNext()) {
                    exerciseNames.add(cursor.getString(0));
                }

            }
        }

        return exerciseNames;
    }

    public String analysisText(int[] jointValues) {

        String analysisText = "";
        String up_part = "";
        String down_part = "";
        for (int i = 0; i < jointValues.length; i++) {
            if (jointValues[i] >= POINT_STANDARD_YELLO_GREEN) {
                up_part += bodyPart[i] + " ";
            } else if (jointValues[i] <= -1 * POINT_STANDARD_YELLO_GREEN) {
                down_part += bodyPart[i] + " ";
            }

        }

        if (up_part.length() > 0) {
            analysisText += up_part + "부위가 몸 바깥쪽 방향으로 향하는 경향이 있습니다.\n";
        }

        if (down_part.length() > 0) {
            analysisText += down_part + "부위가 몸 안쪽 방향으로 향하는 경향이 있습니다.\n";
        }

        if (analysisText.length() < 1) {
            analysisText = "운동 자세를 바르게 따라하고 있습니다.";
        }

        return analysisText;
    }

    public String getDayAnalysisText(String sDay, String exerciseName ) {
        return analysisText(getJointValues(sDay,exerciseName));
    }

}
