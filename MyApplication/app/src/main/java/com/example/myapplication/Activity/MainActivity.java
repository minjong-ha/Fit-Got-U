package com.example.myapplication.Activity;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.widget.Toast;

import com.example.myapplication.Etc.MySQLiteOpenHelper;
import com.example.myapplication.Etc.Util;
import com.example.myapplication.Fragment.DataAnalysisFragment;
import com.example.myapplication.Fragment.HomeTraining2Fragment;
import com.example.myapplication.Fragment.HomeTraining3Fragment;
import com.example.myapplication.Fragment.HomeTrainingFragment;
import com.example.myapplication.Fragment.MyInfoFragment;
import com.example.myapplication.Fragment.MyPageFragment;
import com.example.myapplication.Fragment.TrainerMatch2Fragment;
import com.example.myapplication.Fragment.TrainerMatch3Fragment;
import com.example.myapplication.Fragment.TrainerMatchFragment;
import com.example.myapplication.R;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Stack;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    //private MySQLiteOpenHelper dbhelper;
    private SQLiteDatabase sqliteDB;

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
    private String weight;
    private String height;
    private String is_user;
    private String address;
    private String youtubechannelid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Util.startTestActivity(this);
        //getHK();
        boolean istest = false;
        if (!istest) {//카카오 로그인 없이 진행
            requestMe(this);
        } else {
            kakaoid = 1;
            nickname = "테스트1";
            thumbnail = "";
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
        ChangeFragmentMain(0);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tablayout);
        tabLayout.addOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
                mainfragment = HTfragment.peek();
                menu = 1;
                break;
            case 1:
                mainfragment = TMfragment.peek();
                menu = 2;
                break;
            case 2:
                mainfragment = DAfragment.peek();
                menu = 3;
                break;
            case 3:
                mainfragment = MPfragment.peek();
                menu = 4;
                break;
        }
        ChangeFragmentMain(0);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
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
            ChangeFragmentMain(0);
        }
    }

    public void ChangeFragmentMain(Fragment newfragment) {
        ChangeFragmentMain(0, newfragment);
    }

    public void ChangeFragmentMain(int id) {
        ChangeFragmentMain(id, null);
    }

    public void ChangeFragmentMain(int id, Fragment newfragment) {
        if (id != 0) {
            Bundle args = new Bundle();
            args.putInt("id", id);
            switch (id) {
                case R.id.ht_f1:
                case R.id.ht_f2:
                case R.id.ht_f3:
                case R.id.ht_f4:
                case R.id.ht_f5:
                case R.id.ht_f6:
                    Fragment ht2 = new HomeTraining2Fragment();
                    args.putInt("id",id);
                    ht2.setArguments(args);
                    HTfragment.push(ht2);
                    mainfragment = HTfragment.peek();
                    break;
                case R.string.fitness_1_1:
                case R.string.fitness_1_2:
                case R.string.fitness_1_3:
                    Fragment ht3 = new HomeTraining3Fragment();
                    args.putInt("id",id);
                    ht3.setArguments(args);
                    HTfragment.push(ht3);
                    mainfragment = HTfragment.peek();
                    break;
                case R.string.my_info:
                    MPfragment.push(new MyInfoFragment());
                    mainfragment = MPfragment.peek();
                    break;
                case R.string.logout:
                    Logout(this);
                    break;
                case R.string.separate:
                    Logout(this);
                    break;
            }
        }
        if (newfragment != null && (newfragment instanceof TrainerMatch2Fragment || newfragment instanceof TrainerMatch3Fragment)) {
            TMfragment.push(newfragment);
            mainfragment = TMfragment.peek();
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainfragment, mainfragment);
        ft.commit();
    }

    private void Logout(final Activity activity) {
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
                HashMap<String, String> userdata = Util.SelectUser(kakaoid + "");
                if (userdata != null) {
                    weight = userdata.get("weight");
                    height = userdata.get("height");
                    address = userdata.get("address");
                    is_user = userdata.get("is_user");
                }
                if (!Util.Information_Filled(weight, height, address, is_user)) {
                    Util.startJoinActivity(activity, kakaoid);
                }
            }
        });
    }

    private void getHK() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.example.myapplication", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                System.out.println("HK=" + Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public long getKakaoid() {
        return kakaoid;
    }

    public void setKakaoid(long kakaoid) {
        this.kakaoid = kakaoid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getIs_user() {
        return is_user;
    }

    public void setIs_user(String is_user) {
        this.is_user = is_user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getYoutubechannelid() {
        return youtubechannelid;
    }

    public void setYoutubechannelid(String youtubechannelid) {
        this.youtubechannelid = youtubechannelid;
    }
}
