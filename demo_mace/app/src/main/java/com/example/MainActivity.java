package com.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by a1 on 05/05/2019.
 */

public class MainActivity extends Activity implements View.OnClickListener{

    //private String[] exercise_name = {"R.id.button1", "R.id.button2"};
    @Override
    protected void onCreate(Bundle savedInstatnceState){
        super.onCreate(savedInstatnceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view){

        switch (view.getId()){
            case R.id.button1: {
                Intent intent = new Intent(this, CameraActivity.class);
                intent.putExtra("exercise", "스쿼트");
                startActivity(intent);
                break;
            }
            case R.id.button2:{
                Intent intent = new Intent(this, CameraActivity.class);
                intent.putExtra("exercise", "푸쉬업");
                startActivity(intent);
                break;
            }

        }
    }
}
