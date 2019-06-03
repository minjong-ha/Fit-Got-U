package com.example.myapplication.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

public class InfoChangeActivity extends AppCompatActivity {
    Button Change, Cancel;
    EditText Changed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info);

        String name = getString(getIntent().getIntExtra("whatchange", 0));
        TextView nameview = (TextView) findViewById(R.id.ci_name);
        nameview.setText(name);

        Change = (Button) findViewById(R.id.IdChange);
        Cancel = (Button) findViewById(R.id.IdCancel);
        Changed = (EditText) findViewById(R.id.ChangedId);

        Change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String CId = Changed.getText().toString();
                if (CId.length() == 0) {
                    Toast.makeText(getApplicationContext(), "입력하지 않으셨습니다.", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("changed",CId);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        });
        
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
