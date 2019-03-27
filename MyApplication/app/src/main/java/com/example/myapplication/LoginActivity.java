package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView login_text = (TextView)findViewById(R.id.login_text);//텍스트뷰 불러오기
        final EditText login_id = (EditText)findViewById(R.id.login_id);
        final EditText login_pw = (EditText)findViewById(R.id.login_pw);

        Intent intent = getIntent();
        String name = intent.getExtras().getString("who");//startpage에서 보낸 who 값 가져오기
        login_text.setText(name + " " + login_text.getText());//name 값을 넣기

        Button login_b = (Button)findViewById(R.id.login_b);

        login_b.setOnClickListener(new Button.OnClickListener() {//클릭 이벤트
            @Override
            public void onClick(View view) {
                Intent login_intent = new Intent(getApplicationContext(),MainActivity.class);
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
