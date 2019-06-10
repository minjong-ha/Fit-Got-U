package com.example.calendar.etc;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.Activity.MainActivity;
import com.example.Etc.MySQLiteOpenHelper;
import com.example.Etc.Util;
import com.example.R;
import com.example.databinding.DayItemBinding;

import java.util.ArrayList;
import java.util.HashMap;

public class DateDialog extends AppCompatActivity {
    private DayItemBinding binding;
    public static TextView morning, lunch, dinner;
    private static TextView routine;
    private static TextView record;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        Activity a = (Activity) context;
        a.setContentView(R.layout.item_date_dialog);

        final MySQLiteOpenHelper sqliteHelper = new MySQLiteOpenHelper(a, "jelly.db", null, 3);

        //ArrayList<HashMap<String, String>> sub = Util.SelectSubscriptionbyTrainer(((MainActivity)getActivity()).getKakaoid() + "");
        //HashMap<String, String> = Util.SelectSubscriptionbyTrainer((((MainActivity)getActivity()).getKakaoid()+"");

        morning = (TextView)findViewById(R.id.morning);
        morning.setText("고구마 1개");
        lunch =(TextView) findViewById(R.id.lunch);
        lunch.setText("고구마 50개");
        dinner=(TextView) findViewById(R.id.dinner);
        dinner.setText("KFC 6조각(9시 이후)");

        TextView per_set=(TextView) findViewById(R.id.per_set);
        per_set.setText("세트당 10회!");
        routine=(TextView) findViewById(R.id.routine);
        routine.setText("유산소 15분 → 스쿼트 4세트 → 레그레이즈 4세트 → 플랭크 30분 → 윗몸일으키기 50회 → 팔굽혀펴기 100회");

        record=(TextView) findViewById(R.id.record);

        record.setText(sqliteHelper.getExerciseText("2019-06-08"));
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
