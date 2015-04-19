package com.zifei.corebeau.common;

import org.apache.http.util.VersionInfo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.zifei.corebeau.R;
import com.zifei.corebeau.common.ui.MainActivity;

public class CommonNotificationManager {

    /**
     * show notification
     *
     * @param context
     * @param message
     */
    public static void showNotification(Context context, String message) {
        if (PreferenceManager.getInstance(context).getPreferencesBoolean(PreferenceConstants.ENABLE_NOTIFICATION, true)) {
            // Creates an Intent for the Activity
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("from_notify", true);
            // Sets the Activity to start in a new, empty task
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            
            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            nm.cancel(2);//delete before
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder mCompatBuilder = new NotificationCompat.Builder(context);
//            mCompatBuilder.setSmallIcon(R.drawable.noti_small_icon);
            mCompatBuilder.setAutoCancel(true);
//            mCompatBuilder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher));
            mCompatBuilder.setTicker(message);
            mCompatBuilder.setWhen(System.currentTimeMillis());
            mCompatBuilder.setContentTitle(context.getString(R.string.app_name));
            mCompatBuilder.setContentText(message);
            mCompatBuilder.setContentIntent(pendingIntent);
            
            nm.notify(2, mCompatBuilder.build());
            
        }
    }

    /**
     * show announcement notification
     *
     * @param context
     * @param title
     * @param announcement
     */
//    public static void showAnnouncement(Context context, String title, String announcement) {
//        Intent intent = new Intent(context, MainActivity.class);
//        intent.putExtra("announcement", true).putExtra("title", title).putExtra("content", announcement);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        Notification notify = new NotificationCompat.Builder(context)
//                .setContentTitle(context.getString(R.string.app_name))
//                .setContentText(title)
//                .setContentIntent(pendingIntent)
//                .setAutoCancel(true)
//                .setDefaults(Notification.DEFAULT_ALL)
//                .setSmallIcon(R.drawable.noti_small_icon)
//                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher)).build();
//
//        notificationManager.notify(3, notify);
//
//    }

    /**
     * show update version
     *
     * @param context
     * @param title
     * @param message
     * @param version
     */
//    public static void showUpdateVersionNotification(Context context, String title, String message, VersionInfo version) {
//        Intent intent = new Intent(context, MainActivity.class);
//        intent.putExtra("update", true).putExtra("version", version);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        Notification notify = new NotificationCompat.Builder(context)
//                .setContentTitle(context.getString(R.string.app_name))
//                .setContentText(title)
//                .setContentIntent(pendingIntent)
//                .setAutoCancel(true)
//                .setDefaults(Notification.DEFAULT_ALL)
//                .setSmallIcon(R.drawable.noti_small_icon)
//                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher)).build();
//
//        notificationManager.notify(4, notify);
//
//    }

}
