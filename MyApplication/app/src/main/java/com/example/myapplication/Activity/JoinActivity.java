package com.example.myapplication.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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
        final RadioGroup rg = (RadioGroup)findViewById(R.id.radioGroup);

        Button b = findViewById(R.id.join_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText weight = findViewById(R.id.join_weight_text);
                EditText height = findViewById(R.id.join_height_text);
                EditText address = findViewById(R.id.join_address_text);
                int id = rg.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) findViewById(id);

                if (Util.Information_Filled(weight.getText().toString(), height.getText().toString(), address.getText().toString())) {

                } else {
                    Util.requestUpdateProfile(activity, weight.getText().toString(), height.getText().toString(), address.getText().toString(), rb.getText().toString());
                }
            }
        });
    }
}
