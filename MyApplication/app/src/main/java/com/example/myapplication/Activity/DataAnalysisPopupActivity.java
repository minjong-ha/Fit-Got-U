package com.example.myapplication.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.myapplication.Etc.MySQLiteOpenHelper;
import com.example.myapplication.List.DA2_List_Item;
import com.example.myapplication.List.DA2_List_Item_Adapter;
import com.example.myapplication.R;

import java.util.ArrayList;

public class DataAnalysisPopupActivity extends AppCompatActivity {

    //list viwe
    private ListView m_oListView = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.da2_list_popup);

        Intent intent = getIntent();
        String sDay = intent.getExtras().getString("date");
        ArrayList<String> exerciseNames = intent.getExtras().getStringArrayList("exerciseNames");

        final MySQLiteOpenHelper sqliteHelper = new MySQLiteOpenHelper(getApplicationContext(), "jelly.db", null, 3);

        // 리스트 박스.
        ArrayList<DA2_List_Item> oAnalysis = new ArrayList<>();
        if (exerciseNames.size() != 0) {
            for (int i = 0; i < exerciseNames.size(); ++i) {
                DA2_List_Item oItem = new DA2_List_Item();
                String exerciseName = exerciseNames.get(i);
                oItem.setStrExercise(exerciseName);
                // todo: 운동한 횟수
                oItem.setExerciseCount("todo : 10회");
                oItem.setStrText(sqliteHelper.getDayAnalysisText(sDay, "'" + exerciseName + "'"));
                oAnalysis.add(oItem);
            }
        }
        else{
            DA2_List_Item oItem = new DA2_List_Item();
            oItem.setStrExercise("");
            oItem.setExerciseCount("운동 기록이 없습니다.");
            oItem.setStrText("");
            oAnalysis.add(oItem);
        }

        // ListView, Adapter 생성 및 연결 ------------------------
        m_oListView = (ListView) findViewById(R.id.listView);
        DA2_List_Item_Adapter oAdapter = new DA2_List_Item_Adapter(oAnalysis);
        m_oListView.setAdapter(oAdapter);

    }
}

