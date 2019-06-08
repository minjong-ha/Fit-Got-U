package com.example.calendar.etc;


import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.R;
import com.example.databinding.DayItemBinding;

public class DateDialog extends AppCompatActivity {
    private DayItemBinding binding;
    public static TextView morning;
    private TextView routine;
    private TextView record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_date_dialog);

        morning = (TextView)findViewById(R.id.morning);
        morning.setText("사과 1/2개, 닭가슴살 150g");
        TextView lunch =(TextView) findViewById(R.id.lunch);
        lunch.setText("고구마 50개");
        TextView dinner=(TextView) findViewById(R.id.dinner);
        dinner.setText("KFC 6조각(9시 이후)");

        TextView per_set=(TextView) findViewById(R.id.per_set);
        per_set.setText("세트당 10회!");
        routine=(TextView) findViewById(R.id.routine);
        routine.setText("유산소 15분 → 스쿼트 4세트 → 레그레이즈 4세트 → 플랭크 30분 → 윗몸일으키기 50회 → 팔굽혀펴기 100회");

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
