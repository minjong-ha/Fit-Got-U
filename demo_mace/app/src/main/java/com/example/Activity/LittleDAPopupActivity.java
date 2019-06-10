package com.example.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.Fragment.DataAnalysisFragment;
import com.example.R;

public class LittleDAPopupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_littleda);

        Intent intent = getIntent();
        setTitle("# " + intent.getExtras().getString("name") + "님의 운동 상태");
        Fragment f = new DataAnalysisFragment(true);
        Bundle b = new Bundle();
        b.putString("traineeID",intent.getExtras().getString("traineeID"));
        f.setArguments(b);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.little_layout, f);
        ft.commit();
    }
}
