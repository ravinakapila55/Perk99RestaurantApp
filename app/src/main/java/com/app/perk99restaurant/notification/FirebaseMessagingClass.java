package com.app.perk99restaurant.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.app.perk99restaurant.R;
import com.app.perk99restaurant.home.HomeActivity;
import com.app.perk99restaurant.reviews.RatingReviews;
import com.app.perk99restaurant.utils.MyApplication;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class FirebaseMessagingClass extends FirebaseMessagingService {


    String notification_type;
    private static final String TAG = FirebaseMessagingClass.class.getSimpleName();
    RemoteMessage mRemoteMessage;
    private NotificationManagerCompat notificationManager;

    //new order place
    /*{description=Your order has been placed successfully, id=, text=Your order has been placed successfully, title=Order Placeed, is_read=0}*/


    //new order place
    /*{body=New order has been placed by bzbs sbbsn, data=[], type=1, Title=New Order Received}*/


    //order drop off by driver
    /*{body=Your order 918 picked up successfully, data=[], type=1, Title=Order picked up}*/


    //order accepted by assigned driver
    /*{body=Your order 923 has been accepted by driver successfully, data=[], type=1, Title=Order accepted by driver}*/

    //order pickup by driver
    /*{body=Your order 923 is pickup successfully, data=[], type=1, Title=Order Pickup}*/

    //rating submit by user
    /*{body=Rating has been submitted byevening user, data=[], type=1, Title=Rating Submitted}*/

    //order confirm reserve by user
    /*{body=926 Confirm by user, data=[], type=1, Title=Order Confirmation}*/


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        super.onMessageReceived(remoteMessage);
        Log.e("RemoteMessage ",remoteMessage+"");
        Log.e("RemoteMessageData ",remoteMessage.getData()+"");

        if (remoteMessage.getData().size() != 0)
        {
            Log.e("inside ", String.valueOf(remoteMessage.getData()));
            handleNotification(remoteMessage.getData().get("body"), remoteMessage.getData());
        }
    }

    MyApplication myApplication;
    private void handleNotification(String message, Map<String, String> data)
    {
        Intent intent=null;
        myApplication = (MyApplication) getApplication();

        if (data.containsKey("body"))
        {
            notification_type = data.get("Title");
            Log.e("NotiifcationType ",notification_type);

            if (notification_type.equalsIgnoreCase("New Order Received"))
            {
                intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("noti_type","new");
            } else if (notification_type.equalsIgnoreCase("Order picked up"))
            {
                intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("noti_type","pick");
            }
            else if (notification_type.equalsIgnoreCase("Order accepted by driver"))
            {
                intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("noti_type","accept");
            }
            else if (notification_type.equalsIgnoreCase("Order Pickup"))
            {
                intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("noti_type","pick");

            }  else if (notification_type.equalsIgnoreCase("Rating Submitted"))
            {
                intent = new Intent(getApplicationContext(), RatingReviews.class);
            }
            else if (notification_type.equalsIgnoreCase("Order Confirmation"))
            {
                intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("noti_type","new");
            }

        }




        try
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
                // intent = new Intent(getApplicationContext(), SplashActivity.class);
                PendingIntent contentIntent=null;
                if (notification_type.equalsIgnoreCase("chat_message"))
                {
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    contentIntent = PendingIntent.getActivity(this, (int) (Math.random() * 100), intent, 0);
                }else
                {
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    contentIntent = PendingIntent.getActivity(this, (int) (Math.random() * 100), intent, 0);
                }


                Notification notification = new NotificationCompat.Builder(this, MyApplication.CHANNEL_1_ID)
                        .setSmallIcon(R.mipmap.logo_update)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setOnlyAlertOnce(true)
                        .setContentIntent(contentIntent)
                        //.setFullScreenIntent(contentIntent, true)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                        .setDefaults(NotificationCompat.DEFAULT_SOUND | NotificationCompat.DEFAULT_VIBRATE)
                        //.setSound(defaultSoundUri)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .build();

//                ringtone();

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                {
                    NotificationChannel channel = new NotificationChannel(MyApplication.CHANNEL_1_ID, "Channel human readable title", NotificationManager.IMPORTANCE_HIGH);
                    channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                    channel.setShowBadge(true);
                    notificationManager.createNotificationChannel(channel);
                }

                //notification.flags = Notification.FLAG_AUTO_CANCEL;
                notificationManager.notify(1, notification);
            }
            else
            {
                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
                //intent = new Intent(getApplicationContext(), SplashActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                // play notification sound

                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                intent.putExtra("message", message);
                showNotificationMessage(getApplicationContext(), "StudentApp", message, "", intent);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    private NotificationUtils notificationUtils;

    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent)
    {
        notificationUtils = new NotificationUtils(context);
        //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }
}
