package com.example.myapplication.Etc;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.kakao.auth.ApiResponseCallback;
import com.kakao.auth.ApprovalType;
import com.kakao.auth.AuthType;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.IPushConfig;
import com.kakao.auth.ISessionConfig;
import com.kakao.auth.KakaoAdapter;
import com.kakao.auth.KakaoSDK;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.util.helper.SharedPreferencesCache;

import java.util.UUID;

public class GlobalApplication extends Application {
    private static GlobalApplication instance;
    public static final GlobalApplication getGlobalApplicationContext() {
        if (instance == null)
            throw new IllegalStateException("this application does not inherit com.kakao.GlobalApplication");
        return instance;
    }
    public static class KakaoSDKAdapter extends KakaoAdapter {
        @Override
        public ISessionConfig getSessionConfig() {
            return new ISessionConfig() {
                @Override
                public AuthType[] getAuthTypes() {
                    return new AuthType[]{AuthType.KAKAO_LOGIN_ALL};
                }
                @Override
                public boolean isUsingWebviewTimer() {
                    return false;
                }
                @Override
                public boolean isSecureMode() {
                    return false;
                }
                @Override
                public ApprovalType getApprovalType() {
                    return ApprovalType.INDIVIDUAL;
                }
                @Override
                public boolean isSaveFormData() {
                    return true;
                }
            };
        }
        @Override
        public IApplicationConfig getApplicationConfig() {
            return new IApplicationConfig() {
                @Override
                public Context getApplicationContext() {
                    return GlobalApplication.getGlobalApplicationContext();
                }
            };
        }

        @Override
        public IPushConfig getPushConfig() {
            return new IPushConfig() {
                @Override
                public String getDeviceUUID() {
                    String deviceUUID;
                    final SharedPreferencesCache cache = Session.getAppCache();
                    final String id = cache.getString(PROPERTY_DEVICE_ID);

                    if (id != null) {
                        deviceUUID = id;
                        return deviceUUID;
                    } else {
                        UUID uuid = null;
                        Context context = getApplicationConfig().getApplicationContext();
                        final String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                        try {
                            if (!"9774d56d682e549c".equals(androidId)) {
                                uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
                            } else {
                                final String deviceId = ((TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE)).getDeviceId();
                                uuid = deviceId != null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")) : UUID.randomUUID();
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                        Bundle bundle = new Bundle();
                        bundle.putString(PROPERTY_DEVICE_ID, uuid.toString());
                        cache.save(bundle);

                        deviceUUID = uuid.toString();
                        return deviceUUID;
                    }
                }

                @Override
                public ApiResponseCallback<Integer> getTokenRegisterCallback() {
                    return new ApiResponseCallback<Integer>() {
                        @Override
                        public void onFailure(ErrorResult errorResult) {
                            // FCM 토큰 등록 실패 처리.
                        }

                        @Override
                        public void onSessionClosed(ErrorResult errorResult) {
                            // 현재 로그인이 되어 있지 않은 상태.
                        }

                        @Override
                        public void onNotSignedUp() {
                            // 앱에 카카오톡 계정으로 가입이 되어있지 않은 상태.
                        }

                        @Override
                        public void onSuccess(Integer result) {
                            // 성공적으로 토큰이 등록된 상태
                        }
                    };
                }
            };
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;;
        if (KakaoSDK.getAdapter() == null) {
            KakaoSDK.init(new GlobalApplication.KakaoSDKAdapter());
        }
    }
}
