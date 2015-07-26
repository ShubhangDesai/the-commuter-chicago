package com.example.android.thecommuter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.example.android.thecommuter.MainActivity;
import com.example.android.thecommuter.R;
import com.example.android.thecommuter.managers.FavoritesManager;

import java.util.Calendar;

/**
 * Created by Shubhang on 7/16/2015.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        FavoritesManager favoritesManager = new FavoritesManager(context);

        if (!favoritesManager.isEmpty()) {
            String title = "Title";
            String message = "Message";

            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);
            NotificationManagerCompat manager = NotificationManagerCompat.from(context);
            NotificationCompat.Style style = new NotificationCompat.BigTextStyle();
            Calendar c = Calendar.getInstance();
            NotificationCompat.WearableExtender wearableExtender = new NotificationCompat.WearableExtender();
            wearableExtender.setBackground(BitmapFactory.decodeResource(context.getResources(), R.color.primary));
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

            builder.setContentIntent(contentIntent)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setStyle(style)
                    .setWhen(c.getTimeInMillis())
                    .setAutoCancel(true)
                    .extend(wearableExtender);

            manager.notify(0, builder.build());

            PendingIntent mAlarmSender = PendingIntent.getBroadcast(context, 0, new Intent(context, AlarmReceiver.class),
                    PendingIntent.FLAG_ONE_SHOT);
            long firstTime = c.getTimeInMillis() + 60000;
            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            am.set(AlarmManager.RTC_WAKEUP, firstTime, mAlarmSender);
        }
    }
}