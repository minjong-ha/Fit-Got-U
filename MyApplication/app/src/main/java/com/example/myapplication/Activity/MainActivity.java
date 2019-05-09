package com.example.myapplication.Activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.Etc.Util;
import com.example.myapplication.Etc.onFragmentListener;
import com.example.myapplication.Fragment.DataAnalysisFragment;
import com.example.myapplication.Fragment.HomeTrainingFragment;
import com.example.myapplication.Fragment.HomeTrainingFragment2;
import com.example.myapplication.Fragment.HomeTrainingFragment3;
import com.example.myapplication.Fragment.MyInfoFragment;
import com.example.myapplication.Fragment.MyPageFragment;
import com.example.myapplication.Fragment.TrainerMatchFragment;
import com.example.myapplication.R;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;

import java.util.Stack;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, onFragmentListener {
    //private MySQLiteOpenHelper dbhelper;
    //private SQLiteDatabase db;

    private Fragment mainfragment = null;
    private Stack<Fragment> HTfragment = new Stack<>();
    private Stack<Fragment> TMfragment = new Stack<>();
    private Stack<Fragment> DAfragment = new Stack<>();
    private Stack<Fragment> MPfragment = new Stack<>();
    private String maintitle = "";
    private int menu = -1;//1~4까지. 현재 선택 fragment 구별

    private long kakaoid;
    private String nickname;
    private String thumbnail;
    private String address;
    private String weight;
    private String height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean istest = true;
        if (!istest) {//카카오 로그인 없이 진행
            requestMe(this);
        } else {
            kakaoid = 1;
            nickname = "테스트1";
            thumbnail = "";
            /*if (address == null || height == null || weight == null) {
                Util.startJoinActivity(this);
            }*/
        }
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        HTfragment.push(new HomeTrainingFragment());
        TMfragment.push(new TrainerMatchFragment());
        DAfragment.push(new DataAnalysisFragment());
        MPfragment.push(new MyPageFragment());

        mainfragment = HTfragment.peek();
        maintitle = getString(R.string.app_name);
        menu = 1;
        ChangeFragmentMain();

        ImageButton homeTraining = findViewById(R.id.hometraining);
        homeTraining.setOnClickListener(this);
        ImageButton TrainerMatch = findViewById(R.id.trainer_match);
        TrainerMatch.setOnClickListener(this);
        ImageButton DataAnalysis = findViewById(R.id.data_analysis);
        DataAnalysis.setOnClickListener(this);
        ImageButton MyPage = findViewById(R.id.mypage);
        MyPage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.hometraining:
                mainfragment = HTfragment.peek();
                menu = 1;
                break;
            case R.id.trainer_match:
                mainfragment = TMfragment.peek();
                menu = 2;
                break;
            case R.id.data_analysis:
                mainfragment = DAfragment.peek();
                menu = 3;
                break;
            case R.id.mypage:
                mainfragment = MPfragment.peek();
                menu = 4;
                break;
        }
        ChangeFragmentMain();
    }

    @Override
    public void onBackPressed() {
        boolean realback  = false;
        switch (menu) {
            case 1:
                if (HTfragment.size() > 1) {
                    HTfragment.pop();
                } else {
                    realback = true;
                }
                mainfragment = HTfragment.peek();
                break;
            case 2:
                if (TMfragment.size() > 1) {
                    TMfragment.pop();
                } else {
                    realback = true;
                }
                mainfragment = TMfragment.peek();
                break;
            case 3:
                if (DAfragment.size() > 1) {
                    DAfragment.pop();
                } else {
                    realback = true;
                }
                mainfragment = DAfragment.peek();
                break;
            case 4:
                if (MPfragment.size() > 1) {
                    MPfragment.pop();
                } else {
                    realback = true;
                }
                mainfragment = MPfragment.peek();
                break;
        }
        if (realback) {
            //super.onBackPressed();
            ActivityCompat.finishAffinity(this);
        } else {
            ChangeFragmentMain();
        }
    }

    public void ChangeFragmentMain() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainfragment, mainfragment);
        ft.commit();
    }

    public void onReceivedData(Object data) {
        if (data instanceof String) {
            switch ((String)data) {
                case "내 정보":
                    MPfragment.push(new MyInfoFragment());
                    mainfragment = MPfragment.peek();
                    break;
            }
        } else if (data instanceof Integer) {
            switch ((int)data) {
                case R.id.ht_f1:
                case R.id.ht_f2:
                case R.id.ht_f3:
                case R.id.ht_f4:
                case R.id.ht_f5:
                case R.id.ht_f6:
                    HTfragment.push(new HomeTrainingFragment2().newInstance((int)data));
                    mainfragment = HTfragment.peek();
                    break;
                case R.string.fitness_1_1:
                case R.string.fitness_1_2:
                case R.string.fitness_1_3:
                    HTfragment.push(new HomeTrainingFragment3().newInstance((int)data));
                    mainfragment = HTfragment.peek();
                    break;
            }
        }
        ChangeFragmentMain();
    }

    protected void Logout(final Activity activity) {
        UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                Util.startLoginActivity(activity);
            }
        });
    }

    protected void requestMe(final Activity activity) {
        UserManagement.getInstance().me(new MeV2ResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                Toast.makeText(getApplicationContext(), "오류가 발생했습니다." , Toast.LENGTH_SHORT).show();
                Util.startLoginActivity(activity);
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Util.startLoginActivity(activity);
            }

            @Override
            public void onSuccess(MeV2Response result) {
                kakaoid = result.getId();
                nickname = result.getNickname();
                thumbnail = result.getThumbnailImagePath();
                Bitmap bitmap = Util.getImagefromURL(thumbnail);
                if (bitmap != null) {
                }
                address = result.getProperties().get("address");
                weight = result.getProperties().get("weight");
                height = result.getProperties().get("height");
                if (address == null || height == null || weight == null) {
                    Util.startJoinActivity(activity);
                }
            }
        });
    }
}
