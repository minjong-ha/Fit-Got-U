package com.example.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Etc.Util;
import com.example.R;

public class JoinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        final AppCompatActivity activity = this;

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
                long kakaoid = getIntent().getLongExtra("kakaoid", 0);
                String username = getIntent().getStringExtra("username");
                String profile_image = getIntent().getStringExtra("profile_image");
                if (kakaoid != 0 && Util.Information_Filled(weight.getText().toString(), height.getText().toString(), address.getText().toString(), rb.getText().toString())) {
                    String str = Util.InsertUser(kakaoid + "", username, profile_image, address.getText().toString(), weight.getText().toString(), height.getText().toString(), rb.getText().toString());
                    if (str.equals("success")) {
                        Util.startMainActivity(activity);
                    }
                } else {
                }
            }
        });
    }
}
