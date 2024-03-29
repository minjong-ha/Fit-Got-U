package com.example.Etc;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.Activity.MainActivity;
import com.example.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FCMService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            showNotification(remoteMessage);
        }
    }

    @Override
    public void onNewToken(String token) {
        System.out.println("eeeeeeeeeeeeeeeeeeeeee" + token);
    }

    private void showNotification(RemoteMessage remoteMessage) {

        Intent notificationIntent = new Intent(this, MainActivity.class);  // 알림 클릭 시 이동할 액티비티 지정
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        String title = remoteMessage.getNotification().getTitle();
        String message = remoteMessage.getNotification().getBody();

        String channel = "FGU";
        String nchannel = "FGUC";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channel)
        .setSmallIcon(R.drawable.icon_fgy)
        .setContentTitle(title)
        .setContentText(message)
        .setAutoCancel(true)
        .setContentIntent(pendingIntent);

        // 알림 표시
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(new NotificationChannel(channel, nchannel, NotificationManager.IMPORTANCE_HIGH));
        }
        notificationManager.notify(9999, builder.build());
    }

    private void removeNotification() {
        NotificationManagerCompat.from(this).cancel(9999);
    }
}
