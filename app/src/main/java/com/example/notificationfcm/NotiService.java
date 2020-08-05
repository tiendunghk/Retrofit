package com.example.notificationfcm;

import android.app.NotificationManager;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.security.Provider;

public class NotiService extends FirebaseMessagingService {
    NotificationCompat.Builder mBuilder;
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        mBuilder = new NotificationCompat.Builder(this.getApplicationContext()).setSmallIcon(R.drawable.icons8_telegram_app_16)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody());

        NotificationManager notificationManager =(NotificationManager)this.getApplicationContext().getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0,mBuilder.build());

    }
}
