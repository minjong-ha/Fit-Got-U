package com.example.myapplication.Etc;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;

import com.example.myapplication.Activity.JoinActivity;
import com.example.myapplication.Activity.MainActivity;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.KakaoSDK;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

public class SessionCallback implements ISessionCallback {
    private boolean logged = false;
    private boolean restart = false;
    private Activity activity;

    public SessionCallback(boolean restart, Activity activity) {
        this.restart = restart;
        this.activity = activity;
        if (KakaoSDK.getAdapter() == null) {
            KakaoSDK.init(new GlobalApplication.KakaoSDKAdapter());
        }
    }

    @Override
    public void onSessionOpened() {
        logged = true;
        if (restart) {
            ActivityCompat.finishAffinity(activity);
            activity.getApplicationContext().startActivity(new Intent(activity.getApplicationContext(), MainActivity.class));
        }
    }

    @Override
    public void onSessionOpenFailed(KakaoException exception) {
        if (exception != null) {
            Logger.e(exception);
        }
    }

    public boolean isLogged() {
        return logged;
    }
}
