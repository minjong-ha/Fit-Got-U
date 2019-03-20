package com.example.minjong.posetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PoseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pose);

        System.out.println("PoseTest On!");

        this.setTitle("POSE TEST");


    }
}
