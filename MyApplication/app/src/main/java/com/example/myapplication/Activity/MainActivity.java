package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.example.myapplication.Etc.DataObject;
import com.example.myapplication.Etc.onFragmentListener;
import com.example.myapplication.Fragment.DataAnalysisFragment;
import com.example.myapplication.Fragment.HomeTrainingFragment;
import com.example.myapplication.Fragment.HomeTrainingFragment2;
import com.example.myapplication.Fragment.MyPageFragment;
import com.example.myapplication.Fragment.TrainerMatchFragment;
import com.example.myapplication.R;

import java.util.Stack;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, onFragmentListener {
    private Fragment mainfragment = null;
    private Stack<Fragment> HTfragment = new Stack<>();
    private Stack<Fragment> TMfragment = new Stack<>();
    private Stack<Fragment> DAfragment = new Stack<>();
    private Stack<Fragment> MPfragment = new Stack<>();
    private String maintitle = "";
    private int menu = -1;//1~4까지. 현재 선택 fragment 구별

    private boolean logged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!logged) {//로그인하지 않았을 때
            Intent login = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(login);
            finish();
            return;
        } else {
            setContentView(R.layout.activity_main);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            HTfragment.push(new HomeTrainingFragment());
            TMfragment.push(new TrainerMatchFragment());
            DAfragment.push(new DataAnalysisFragment());
            MPfragment.push(new MyPageFragment());

            mainfragment = HTfragment.peek();
            maintitle = getString(R.string.app_name);
            menu = 1;
            ChangeFragmentMain();

            ImageButton homeTraining = findViewById(R.id.hometraining);
            homeTraining.setOnClickListener(this);
            ImageButton TrainerMatch = findViewById(R.id.trainer_match);
            TrainerMatch.setOnClickListener(this);
            ImageButton DataAnalysis = findViewById(R.id.data_analysis);
            DataAnalysis.setOnClickListener(this);
            ImageButton MyPage = findViewById(R.id.mypage);
            MyPage.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.hometraining:
                mainfragment = HTfragment.peek();
                menu = 1;
                break;
            case R.id.trainer_match:
                mainfragment = TMfragment.peek();
                menu = 2;
                break;
            case R.id.data_analysis:
                mainfragment = DAfragment.peek();
                menu = 3;
                break;
            case R.id.mypage:
                mainfragment = MPfragment.peek();
                menu = 4;
                break;
        }
        ChangeFragmentMain();
    }

    @Override
    public void onBackPressed() {
        boolean realback  = false;
        switch (menu) {
            case 1:
                if (HTfragment.size() > 1) {
                    HTfragment.pop();
                } else {
                    realback = true;
                }
                mainfragment = HTfragment.peek();
                break;
            case 2:
                if (TMfragment.size() > 1) {
                    TMfragment.pop();
                } else {
                    realback = true;
                }
                mainfragment = TMfragment.peek();
                break;
            case 3:
                if (DAfragment.size() > 1) {
                    DAfragment.pop();
                } else {
                    realback = true;
                }
                mainfragment = DAfragment.peek();
                break;
            case 4:
                if (MPfragment.size() > 1) {
                    MPfragment.pop();
                } else {
                    realback = true;
                }
                mainfragment = MPfragment.peek();
                break;
        }
        if (realback) {
            super.onBackPressed();
        } else {
            ChangeFragmentMain();
        }
    }

    public void ChangeFragmentMain() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainfragment, mainfragment);
        ft.commit();
    }

    public void onReceivedData(Object data) {
        switch ((int)data) {
            case R.id.ht_f1:
            case R.id.ht_f2:
            case R.id.ht_f3:
            case R.id.ht_f4:
            case R.id.ht_f5:
            case R.id.ht_f6:
                HTfragment.push(new HomeTrainingFragment2().newInstance((int)data));
                mainfragment = HTfragment.peek();
                break;
        }
        ChangeFragmentMain();
    }
}
