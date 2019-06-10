package com.example.Etc;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Pair;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class MySQLiteOpenHelper extends SQLiteOpenHelper implements Serializable {
    final private String[] bodyPart = {"좌측 팔 하박", "좌측 팔 상박", "좌측 다리 하박", "좌측 다리 상박",
            "몸통", "엉덩이", "우측 팔 하박", "우측 팔 상박", "우측 다리 하박", "우측 다리 상박",
            "좌측 어깨", "우측 어깨"};
    private final int POINT_STANDARD_YELLO_RED = 15; // 노란 또는 빨강 기준
    private final int POINT_STANDARD_YELLO_GREEN = 5;

    final private String exerciseNames[] = {"'스쿼트'", "'플랭크'", "'런지'", "'사이드런지'", "'와이드스쿼트'"};
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
        // 데이터베이스의 버전이 바뀌었을 때 호출되는 콜백 메서드
        // 버전 바뀌었을 때 기존데이터베이스를 어떻게 변경할 것인지 작성한다
        // 각 버전의 변경 내용들을 버전마다 작성해야함
        if (sqliteDB != null) {
            String sql = "drop table 'EXERCISE_DESCRIBE';"; // 테이블 드랍
            sqliteDB.execSQL(sql);
            sql = "drop table 'EXERCISE_RECORD';"; // 테이블 드랍
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

    // ******
    public int insertRecord(String exerciseName, String exerciseDate, int exerciseCount) {
        SQLiteDatabase db = openDB();
        int recordID = -1;
        exerciseName = "'" + exerciseName + "'";
        exerciseDate = "'" + exerciseDate + "'";
        if (db != null) {

            String sql = "INSERT INTO EXERCISE_RECORD ( Exercise_name, Exercise_date, Exercise_count ) VALUES ( " + exerciseName + ", " + exerciseDate + ", " + exerciseCount + ");";
            db.execSQL(sql);

            sql = "SELECT Id From EXERCISE_RECORD WHERE " +
                    "Exercise_name = " + exerciseName + " AND " +
                    "Exercise_date = " + exerciseDate + " AND Exercise_count != 0 ;";
            Cursor cursor = null;

            cursor = db.rawQuery(sql, null);

            if (cursor.moveToNext()) {
                recordID = cursor.getInt(0);
            }
        }

        return recordID;

    } // 쿼리를 가짐

    //******
    public void insertDescribe(int recordID, String weakPart, int weakAngle) { // 퀴리를 가짐
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
    public void updateRecordCount(int recordID, int exerciseCount) {
        SQLiteDatabase db = openDB();
        if (db != null) {
            String sql = "UPDATE EXERCISE_RECORD SET Exercise_count = " + exerciseCount +
                    " WHERE Id = " + recordID + ";";
            db.execSQL(sql);
        }

    }

    public int getRecordID(String exerciseName, String exerciseDate) {
        SQLiteDatabase db = openDB();
        int recordID = -1;
        exerciseName = "'" + exerciseName + "'";
        exerciseDate = "'" + exerciseDate + "'";

        if (db != null) {
            String sql;
            sql = "select Id from EXERCISE_RECORD where " +
                    "Exercise_name = " + exerciseName + " AND " +
                    "Exercise_date = " + exerciseDate + " AND Exercise_count != 0;";
            Cursor cursor = null;
            cursor = db.rawQuery(sql, null);

            if (cursor.moveToNext()) {
                recordID = cursor.getInt(0);
            }

            return recordID;

        }


        return recordID;
    }

    //todo TEST
    public void insertTest() {
        final int TEST_SIZE = 5;

        int recordID = insertRecord("스쿼트", "2019-06-03-04-51-25", 0);
        for (int i = 0; i < TEST_SIZE; i++) {
            insertDescribe(recordID, bodyPart[i], i + 5);
        }

        updateRecordCount(recordID, 5);

        recordID = insertRecord("플랭크", "2019-06-02-04-51-26", 5);
        for (int i = 0; i < TEST_SIZE; i++) {
            insertDescribe(recordID, bodyPart[i + 3], (i + 5) * -1);
        }

    }

    public int[] getJointValues(String exerciseDate, String exerciseName) {
        SQLiteDatabase db = openDB();
        int[] jointValues = new int[12];

        if (db != null) {

            for (int i = 0; i < 12; i++) {
                String partName = bodyPart[i];
                jointValues[i] = calcJointValue(partName, exerciseDate, exerciseName);
            }
        }
        return jointValues;
    }

    private int calcJointValue(String partName, String exerciseDate, String exerciseName) {
        SQLiteDatabase db = openDB();
        int jointValue;
        int recordID = -1;
        String recordIDCondition = "";
        partName = "'" + partName + "'";

        if (db != null) {
            Cursor cursor = null;

            if (exerciseDate != null && exerciseName != null) {
                recordID = getRecordID(exerciseName, exerciseDate);
                recordIDCondition = " AND Record_id = " + recordID;
            }


            String sqlSelectP_Angle = "SELECT AVG(Weak_angle) as average FROM EXERCISE_DESCRIBE WHERE Weak_part = " + partName +
                    recordIDCondition + ";";
            cursor = db.rawQuery(sqlSelectP_Angle, null);

            if (cursor.moveToNext()) {
                jointValue = (int)cursor.getDouble(0);
            } else {
                jointValue = 0;
            }
            return jointValue;
        }
        return 0;
    } // 쿼리를 가짐

    public int countDayExercise(String exerciseDate) {
        SQLiteDatabase db = openDB();
        Cursor cursor = null;
        int exerciseCount = 0;
        exerciseDate = "'" + exerciseDate + "%'";

        if (db != null) {
            String query = "SELECT Exercise_name FROM EXERCISE_RECORD where Exercise_date LIKE " + exerciseDate + " AND Exercise_count != 0;";
            System.out.println(query);
            cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    exerciseCount++;
                    cursor.moveToNext();
                }
            }

        }

        return exerciseCount;
    }  // 쿼리를 가짐

    public ArrayList<Pair<Pair<String, String>, Integer>> getDescribe(String exerciseDate) {
        SQLiteDatabase db = openDB();
        Cursor cursor = null;

        exerciseDate = "'" + exerciseDate.substring(0, 10) + "%'";

        ArrayList<Pair<Pair<String, String>, Integer>> exerciseNames = new ArrayList<Pair<Pair<String, String>, Integer>>();

        if (db != null) {
            String query = "SELECT Exercise_name, Exercise_date, Exercise_count FROM EXERCISE_RECORD WHERE Exercise_date like " + exerciseDate + " AND Exercise_count != 0;";
            cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {

                    exerciseNames.add(Pair.create(Pair.create(cursor.getString(0), cursor.getString(1)), cursor.getInt(2)));
                    cursor.moveToNext();
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
                up_part += "[" + bodyPart[i] + "] ";
            } else if (jointValues[i] <= -1 * POINT_STANDARD_YELLO_GREEN) {
                down_part += "[" + bodyPart[i] + "] ";
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

    public String getDayAnalysisText(String exerciseDate, String exerciseName) {
        return analysisText(getJointValues(exerciseDate, exerciseName));
    }

    // 달력에 운동 데이터 날림
    public HashSet<String> getExerciseData() {
        HashSet<String> dates = new HashSet<String>();

        SQLiteDatabase db = openDB();
        Cursor cursor = null;

        if (db != null) {
            String query = "SELECT distinct Exercise_date FROM EXERCISE_RECORD WHERE Exercise_date AND Exercise_count != 0 " + ";";
            System.out.println(query);
            cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    String tmp_date = cursor.getString(0);
                    dates.add(tmp_date.substring(0, 10));

                    cursor.moveToNext();
                }
            }
        }
        return dates;
    }

    public String getExerciseText(String exerciseDate) {
        SQLiteDatabase db = openDB();
        Cursor cursor = null;

        String result = "";
        int result_count=0;

        if (db != null) {
            String query = "SELECT Exercise_name, Exercise_count FROM EXERCISE_RECORD WHERE Exercise_date like " + exerciseDate + " AND Exercise_count != 0;";
            cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    String exerciseName = cursor.getString(0);
                    int exerciseCount = cursor.getInt(1);

                    result += exerciseName + " " + exerciseCount + "회 ";
                    result_count++;

                    if(result_count%3==0) result+="\n";

                    cursor.moveToNext();
                }
            }
        }
        return result;
    }
}