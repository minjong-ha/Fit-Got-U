package com.example.myapplication.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.Etc.Util;
import com.example.myapplication.R;
import com.kakao.auth.ApiResponseCallback;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;

import java.util.HashMap;
import java.util.Map;

public class JoinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        final Activity activity = this;

        Button b = findViewById(R.id.join_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText weight = findViewById(R.id.join_weight_text);
                EditText height = findViewById(R.id.join_height_text);
                EditText address = findViewById(R.id.join_address_text);
                if (weight.getText().toString().trim().equals("") || height.getText().toString().trim().equals("") || address.getText().toString().trim().equals("")) {

                } else {
                    Util.requestUpdateProfile(activity, weight.getText().toString(), height.getText().toString(), address.getText().toString());
                }
            }
        });
    }
}
