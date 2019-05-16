package com.example.myapplication.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;

public class InfoChangeActivity extends AppCompatActivity {
    Button IdChange, IdCancel;
    EditText ChangedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info);

        IdChange = (Button) findViewById(R.id.IdChange);
        IdCancel = (Button) findViewById(R.id.IdCancel);
        ChangedId = (EditText) findViewById(R.id.ChangedId);

        IdChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String CId = ChangedId.getText().toString();
                if (CId.length() == 0) {
                    Toast.makeText(getApplicationContext(), "입력하지 않으셨습니다.", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("name",CId);
                    setResult(Activity.RESULT_OK, intent);

                    finish();
                }
            }
        });

        IdCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("name","NULLNAME");
                setResult(Activity.RESULT_OK, intent);

                finish();
            }
        });

    }
}
