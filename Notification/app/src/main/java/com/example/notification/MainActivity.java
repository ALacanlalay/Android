package com.example.notification;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, 0);

        Notification.Action action = new Notification.Action.Builder(
                Icon.createWithResource(getApplicationContext(), android.R.drawable.sym_action_chat),
                "Chat",
                pendingIntent).build();


        Notification notification = new Notification.Builder(getApplicationContext())
                .setContentTitle("Lunch is ready")
                .setContentText("It's getting cold")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
                .setSmallIcon(android.R.drawable.sym_def_app_icon)
                .addAction(action)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, notification);
    }
}
