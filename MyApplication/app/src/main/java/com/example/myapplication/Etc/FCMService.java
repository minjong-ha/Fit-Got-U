package com.example.myapplication.Etc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FCMService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {

        }
        System.out.println("bbbbbbbbbbbbbbbbbbbb" + remoteMessage.getNotification().getBody());
    }

    @Override
    public void onNewToken(String token) {
        System.out.println("eeeeeeeeeeeeeeeeeeeeee" + token);
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {

    }
}
