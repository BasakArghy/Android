package com.example.chat;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class Notification {

    private  static final String CHANNEL_ID = "My channel";
    private  static final int NotificATION_ID = 120;
    private  static final int req = 11;

   public Notification(Context context, String sender, String msg,String id) {
        NotificationManager nm= (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        android.app.Notification notification;

        Intent iNotify = new Intent(context.getApplicationContext(),ChatActivity.class);

       iNotify.putExtra("id",id);
       iNotify.putExtra("name",sender);

        iNotify.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi = PendingIntent.getActivity(context,req,iNotify,PendingIntent.FLAG_UPDATE_CURRENT);
        //Big picture
       /* Notification.BigPictureStyle bigPicture = new Notification.BigPictureStyle()
                .bigPicture(((BitmapDrawable) (ResourcesCompat.getDrawable(getResources(),R.drawable.baseline_notifications_24,null))).getBitmap())
                .bigLargeIcon(largeIcon);*/
        //inboxStyle


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification = new android.app.Notification.Builder(context)
                    //.setLargeIcon(largeIcon)
                    .setSmallIcon(R.drawable.chat)
                    .setContentText(msg)
                    .setSubText(sender)
                    // .setStyle(bigPicture)
                    .setAutoCancel(true)
                    //.setOngoing(true)
                    .setContentIntent(pi)
                    .setChannelId(CHANNEL_ID)


                    .build();
            nm.createNotificationChannel(new NotificationChannel(CHANNEL_ID,"ne channel",NotificationManager.IMPORTANCE_HIGH));
        }
        else {     notification = new android.app.Notification.Builder(context)
                // .setLargeIcon(largeIcon)
                .setSmallIcon(R.drawable.chat)
                .setContentText(msg)
                .setSubText(sender)
                .setContentIntent(pi)
                .build();
        }


            nm.notify(NotificATION_ID,notification);
       }


}
