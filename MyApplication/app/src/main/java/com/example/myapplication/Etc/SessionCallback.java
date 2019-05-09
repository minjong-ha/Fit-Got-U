package com.example.myapplication.Etc;

import android.app.Activity;

import com.kakao.auth.ISessionCallback;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

public class SessionCallback implements ISessionCallback {
    private Activity activity;

    public SessionCallback(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onSessionOpened() {
        //Util.startJoinActivity(activity);
        Util.startMainActivity(activity);
    }

    @Override
    public void onSessionOpenFailed(KakaoException exception) {
        if (exception != null) {
            Logger.e(exception);
        }
    }
}
