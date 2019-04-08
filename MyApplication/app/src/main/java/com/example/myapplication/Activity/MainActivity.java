package com.example.myapplication.Activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.example.myapplication.Fragment.DataAnalysisFragment;
import com.example.myapplication.Fragment.HomeTrainingFragment;
import com.example.myapplication.Fragment.MyPageFragment;
import com.example.myapplication.Fragment.TrainerMatchFragment;
import com.example.myapplication.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Fragment mainfragment = null;
    private Fragment HTfragment = new HomeTrainingFragment();
    private Fragment TMfragment = new TrainerMatchFragment();
    private Fragment DAfragment = new DataAnalysisFragment();
    private Fragment MPfragment = new MyPageFragment();
    private String maintitle = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainfragment = HTfragment;
        maintitle = getString(R.string.app_name);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.hometraining:
                mainfragment = HTfragment;
                break;
            case R.id.trainer_match:
                mainfragment = TMfragment;
                break;
            case R.id.data_analysis:
                mainfragment = DAfragment;
                break;
            case R.id.mypage:
                mainfragment = MPfragment;
                break;
        }
        ChangeFragmentMain();
    }

    public void ChangeFragmentMain() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainfragment, mainfragment);
        ft.commit();
    }
}
