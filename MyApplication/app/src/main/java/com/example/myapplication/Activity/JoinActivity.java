package com.example.myapplication.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.myapplication.R;

public class JoinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        final EditText join_id = (EditText)findViewById(R.id.join_id);
        final EditText join_pw = (EditText)findViewById(R.id.join_pw);
        final RadioGroup join_who_g = (RadioGroup)findViewById(R.id.join_who_g);

        Button join_b = (Button)findViewById(R.id.join_b);

        join_b.setOnClickListener(new Button.OnClickListener() {//클릭 이벤트
            @Override
            public void onClick(View view) {
                Intent join_intent = new Intent(getApplicationContext(), StartPageActivity.class);
                startActivity(join_intent);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();

        finish();//화면 사라지면 바로 종료
    }
}
