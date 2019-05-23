package com.example.myapplication.Etc;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FCMService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            System.out.println("aaaaaaaaaa" + remoteMessage.getData().toString());
        }
    }
}
