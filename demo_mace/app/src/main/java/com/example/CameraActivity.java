/*
 * Copyright 2018 Zihua Zeng (edvard_hua@live.com)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.R;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Main {@code Activity} class for the Camera app.
 */
public class CameraActivity extends Activity {

    static {
        System.loadLibrary("opencv_java3");
        //System.loadLibrary("native-lib");
    }

    public static boolean isOpenCVInit = false;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                    isOpenCVInit = true;
                    break;
                case LoaderCallbackInterface.INCOMPATIBLE_MANAGER_VERSION:
                    break;
                case LoaderCallbackInterface.INIT_FAILED:
                    break;
                case LoaderCallbackInterface.INSTALL_CANCELED:
                    break;
                case LoaderCallbackInterface.MARKET_ERROR:
                    break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        TextView explain = (TextView)findViewById(R.id.text);

        //이거 자동업데이트 어떻게 해야할까

        if (null == savedInstanceState) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, Camera2BasicFragment.newInstance())
                    .commit();
        }
        Intent intent = getIntent();
        String message = intent.getStringExtra("exercise");
        explain.setText(message);

        Calculate.info = message;
        Calculate.setContext(this.getApplicationContext());
    }

    public void onClick(View view){
        Log.d("result", "exerciseCount: " + Calculate.exerciseCount +" validCount: " + Calculate.validCount);
        Log.d("result", "upperCount: " + Calculate.uppperCount + " lowerCount: " + Calculate.lowerCount);
        Log.d("result", "left: " + Calculate.left);
        Log.d("result", Calculate.left.toString());
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("운동결과");
        alertDialogBuilder
                .setMessage("Total: " + Calculate.exerciseCount +"\nValid: " + Calculate.validCount + "\n Upper: " + Calculate.uppperCount + "\nLower: " + Calculate.lowerCount)
                .setCancelable(false)
                .setPositiveButton("종료",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {
                                // 프로그램을 종료한다
                                Calculate.varInit();
                                CameraActivity.this.finish();
                            }
                        });

        // 다이얼로그 생성
        AlertDialog alertDialog = alertDialogBuilder.create();

        // 다이얼로그 보여주기
        alertDialog.show();

      //  timer.cancel();
        //finish();


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, mLoaderCallback);
        } else {
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
