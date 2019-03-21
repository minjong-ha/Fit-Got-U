package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        Button user_login = (Button)findViewById(R.id.user_login);//텍스트뷰 불러오기

        user_login.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login_intent = new Intent(getApplicationContext(),LoginActivity.class);
                login_intent.putExtra("who","user");//로그인 액티비티에 who 값 보내기
                startActivity(login_intent);
            }
        });

        Button fitness_login = (Button)findViewById(R.id.fitness_login);

        fitness_login.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login_intent = new Intent(getApplicationContext(),LoginActivity.class);
                login_intent.putExtra("who","fitness");//로그인 액티비티에 who 값 보내기
                startActivity(login_intent);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();

        finish();//화면 사라지면 바로 종료
    }
}
