package com.example.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class ReplyForTraineeActivity extends AppCompatActivity {
    HashMap<String, String> values = null;
    private String traineeName = null;
    private String traineeID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        //값 받아오기
        Intent intent = getIntent();
        traineeName = intent.getExtras().getString("name");
        traineeID = intent.getExtras().getString("traineeID");

        //스피너 초기화
        setDateSpinner();
        //버튼 초기화
        setButton();

    }

    private void setButton() {
        Button btnSend = (Button) findViewById(R.id.btnSend);
        Button btnCancel = (Button) findViewById((R.id.btnCancel));

        btnSend.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                values = getValues();
                //다이얼로그
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        ReplyForTraineeActivity.this);

                // 제목셋팅
                alertDialogBuilder.setTitle("전송정보확인");

                // AlertDialog 셋팅
                alertDialogBuilder
                        .setMessage(traineeName + "님에게 " + values.get("date") + "일자 정보를 보냅니다.")
                        .setCancelable(false)
                        .setNegativeButton("취소",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog, int id) {
                                        // 다이얼로그를 취소한다
                                        dialog.cancel();
                                    }
                                })
                        .setPositiveButton("확인",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // todo : 보낼 때 일어나는 데이터 전송

                                        dialog.dismiss();
                                    }
                                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });

        btnCancel.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setDateSpinner() {
        Calendar cal = Calendar.getInstance();

        //현재 년도
        int year = cal.get(cal.YEAR);
        int month = cal.get(cal.MONTH) + 1;
        int date = cal.get(cal.DATE);

        // 년 설정
        ArrayList<Integer> year_list = new ArrayList<Integer>();
        year_list.add(year - 1);
        year_list.add(year);
        year_list.add(year + 1);

        //ArrayAdapter
        ArrayAdapter<Integer> arrayAdapterYear = new ArrayAdapter<Integer>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                year_list);

        Spinner spinner_year = (Spinner) findViewById(R.id.spinner_year);
        spinner_year.setAdapter(arrayAdapterYear);
        spinner_year.setSelection(1); //display hint

        // 달 설정
        ArrayList<Integer> month_list = new ArrayList<Integer>();
        for (int i = 1; i < 13; i++) {
            month_list.add(i);
        }

        //ArrayAdapter
        ArrayAdapter<Integer> arrayAdapterMonth = new ArrayAdapter<Integer>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                month_list);

        Spinner spinner_month = (Spinner) findViewById(R.id.spinner_month);
        spinner_month.setAdapter(arrayAdapterMonth);
        spinner_month.setSelection(month - 1); //display hint

        //일 설정
        ArrayList<Integer> day_list = new ArrayList<Integer>();
        for (int i = 1; i < 31; i++) {
            day_list.add(i);
        }

        //ArrayAdapter
        ArrayAdapter<Integer> arrayAdapterDay = new ArrayAdapter<Integer>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                day_list);

        Spinner spinner_day = (Spinner) findViewById(R.id.spinner_day);
        spinner_day.setAdapter(arrayAdapterDay);
        spinner_day.setSelection(date - 1); //display hint

        //세트 설정
        ArrayList<Integer> set_list = new ArrayList<Integer>();
        for (int i = 1; i < 10; i++) {
            set_list.add(i);
        }

        //ArrayAdapter
        ArrayAdapter<Integer> arrayAdapterSet = new ArrayAdapter<Integer>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                set_list);

        Spinner spinner_set = (Spinner) findViewById(R.id.spinner_setCount);
        spinner_set.setAdapter(arrayAdapterSet);
        spinner_set.setSelection(0); //display hint

    }


    private HashMap<String, String> getValues() {
        HashMap<String, String> values = new HashMap<String, String>();

        TextInputEditText morningMeal = (TextInputEditText) findViewById(R.id.textMealMorning);
        values.put("morning_meal", morningMeal.getText().toString());

        TextInputEditText lunchMeal = (TextInputEditText) findViewById(R.id.textMealLunch);
        values.put("lunch_meal", lunchMeal.getText().toString());

        TextInputEditText dinnerMeal = (TextInputEditText) findViewById(R.id.textMealDinner);
        values.put("dinner_meal", dinnerMeal.getText().toString());

        TextInputEditText routineText = (TextInputEditText) findViewById(R.id.textRoutine);
        values.put("routine", routineText.getText().toString());

        Spinner spinner_year = (Spinner) findViewById(R.id.spinner_year);
        String year = spinner_year.getSelectedItem().toString();

        Spinner spinner_month = (Spinner) findViewById(R.id.spinner_month);
        String month = spinner_month.getSelectedItem().toString();

        Spinner spinner_day = (Spinner) findViewById(R.id.spinner_day);
        String day = spinner_day.getSelectedItem().toString();

        values.put("date", year + "-" + dateFormat(month) + "-" +dateFormat(day));

        Spinner spinner_setCount = (Spinner) findViewById(R.id.spinner_setCount);
        String setCount = spinner_setCount.getSelectedItem().toString();

        values.put("set", setCount);

        return values;

    }

    private String dateFormat(String raw_date){
        if(raw_date.length()==1)
            return "0"+raw_date;
        return raw_date;
    }
}
