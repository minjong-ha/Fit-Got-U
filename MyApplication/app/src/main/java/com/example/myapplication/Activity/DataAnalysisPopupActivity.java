package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
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

        final MySQLiteOpenHelper sqliteHelper = new MySQLiteOpenHelper(getApplicationContext(), "jelly.db", null, 3);

        ArrayList<Pair<Pair<String,String>,Integer>> tempArray = sqliteHelper.getDescribe(sDay);
        // 리스트 박스.
        ArrayList<DA2_List_Item> oAnalysis = new ArrayList<>();

        if (tempArray.size() != 0) {
            for (int i = 0; i < tempArray.size(); ++i) {
                DA2_List_Item oItem = new DA2_List_Item();
                Pair<Pair<String,String>,Integer> tempPair = tempArray.get(i);
                String exercisetime = " ("+tempPair.first.second.substring(11,13)+"시 "+ tempPair.first.second.substring(14,16)+ "분)";
                oItem.setStrExercise(tempPair.first.first+ exercisetime);
                oItem.setExerciseCount("운동횟수 : "+ tempPair.second);
                oItem.setStrText(sqliteHelper.getDayAnalysisText(tempPair.first.second, tempPair.first.first));
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

