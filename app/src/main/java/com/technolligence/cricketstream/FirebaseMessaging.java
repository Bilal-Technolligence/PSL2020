package com.technolligence.cricketstream;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.technolligence.cricketstream.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessaging extends FirebaseMessagingService {
    private final String CHANNEL_ID ="personal" ;
    public final int NOTIFICATION_ID = 001;


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        generateAlert(remoteMessage.getNotification().getTitle() , remoteMessage.getNotification().getBody());

    }
    private void generateAlert(String name, String msg) {

        Intent notificationIintent = new Intent(this, LiveScoreActivity.class);
        TaskStackBuilder taskStackBuilder= TaskStackBuilder.create(this);
        taskStackBuilder.addNextIntent(notificationIintent);
        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(100,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID);
        builder.setSmallIcon(R.drawable.logo);
        builder.setContentTitle(name);
        builder.setContentText(msg);
        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setContentIntent( pendingIntent );

        NotificationManager notificationManager = (NotificationManager)getSystemService( Context.NOTIFICATION_SERVICE );
        notificationManager.notify(NOTIFICATION_ID,builder.build() );

    }

}
