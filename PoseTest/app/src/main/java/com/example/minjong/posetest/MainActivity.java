package com.example.minjong.posetest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button poseTestBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("MainActivity On!");

        //Activity에서 타이틀 텍스트 변경, 이 부분을 설정하는 함수들도 다수 존재.
        this.setTitle("테스트 어플리케이션");

        poseTestBtn = (Button)findViewById(R.id.poseTestBtn);
        poseTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("테스트 버튼 클릭!");

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                //알림창 밖을 터치했을 때 알림창을 사라지게 할 것인지 아닌지
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setTitle("테스트 화면으로 이동합니다!");
                alertDialogBuilder.setMessage("제발 잘 되었으면 좋겠다");
                alertDialogBuilder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        //여기에 다음 화면으로 넘기는거 넣으면 됨.
                        Intent intent = new Intent(getApplicationContext(), PoseActivity.class);
                        startActivity(intent);
                    }
                });
                alertDialogBuilder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog dialog = alertDialogBuilder.create();
                alertDialogBuilder.show();
            }
        });
    }
}
