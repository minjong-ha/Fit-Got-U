package com.example.calendar.etc;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Etc.MySQLiteOpenHelper;
import com.example.Etc.Util;
import com.example.R;
import com.example.databinding.DayItemBinding;

import java.util.ArrayList;
import java.util.HashMap;

public class DateDialog extends AppCompatActivity {
    public static TextView morning, lunch, dinner;
    private static TextView routine;
    private static TextView record;
    private Context context;
    private ArrayList<HashMap<String, String>> routines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        Activity a = (Activity) context;
        a.setContentView(R.layout.item_date_dialog);
        Intent intent = getIntent();

        int position = intent.getExtras().getInt("position");
        ArrayList<Integer> setDate = intent.getExtras().getIntegerArrayList("array");

        final MySQLiteOpenHelper sqliteHelper = new MySQLiteOpenHelper(a, "jelly.db", null, 3);

        String kakaoId = intent.getExtras().getString("kakaoId");
        routines = Util.SelectRoutine(kakaoId);

        Bundle bundle = this.getIntent().getExtras();
        HashMap<String, Integer> PosiToDate=null;
        if(bundle != null) {
            PosiToDate = (HashMap<String,Integer>) bundle.getSerializable("PosiToDate");
        }
        ArrayList<String> setBreak = new ArrayList<>();
        setBreak = intent.getExtras().getStringArrayList("setBreak");
        for(HashMap<String, String> t : routines) {
            for(int i=0; i<setBreak.size(); i++) {
                String date_mes = t.get("date_message");
                if (date_mes.equals(setBreak.get(i))) {
                    if (position==(PosiToDate.get(date_mes))) {
                        morning = (TextView) findViewById(R.id.morning);
                        morning.setText(t.get("break_message"));
                        lunch = (TextView) findViewById(R.id.lunch);
                        lunch.setText(t.get("lunch_message"));
                        dinner = (TextView) findViewById(R.id.dinner);
                        dinner.setText(t.get("dinner_message"));
                        TextView per_set = (TextView) findViewById(R.id.per_set);
                        per_set.setText(t.get("set_message"));
                        routine = (TextView) findViewById(R.id.routine);
                        routine.setText(t.get("routine_message"));
                        //mBinding = DataBindingUtil.setContentView(this, R.layout.item_day);
                        //mBinding.meal.setText("식사");
                    }
                }
            }
        }

        /*
        HashSet<String> gED =  sqliteHelper.getExerciseData();
        Iterator itr = PosiToDate..keySet().iterator();
        while(itr.hasNext()) {
            String is = (String)itr.next();
            for (String time : gED) {
                String times = time.substring(0,10);
                System.out.println("aaaaaaaaaaaaaa" + times);
                System.out.println("bbbbbbbbbbbbbb" + is);
                if(is.equals(times)) {
                    record = (TextView) findViewById(R.id.record);
                    record.setText(sqliteHelper.getExerciseText(time));
                    break;
                }
            }
        }*/
        //mBinding = DataBindingUtil.setContentView(this, R.layout.item_day);
        //mBinding.meal.setText("식사");
    }
    public String getMorning(){
        return morning.getText().toString();
    }

    public String getRoutine(){
        return routine.getText().toString();
    }

    public String getRecord(){
        return record.getText().toString();
    }

}
