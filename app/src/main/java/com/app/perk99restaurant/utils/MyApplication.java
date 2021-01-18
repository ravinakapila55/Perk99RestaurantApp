package com.app.perk99restaurant.utils;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import androidx.multidex.MultiDexApplication;

import com.app.perk99restaurant.home.models.GetOrderList;

import java.util.ArrayList;


public class MyApplication extends MultiDexApplication {


    private static MyApplication instance;

    ArrayList<GetOrderList> list;

    public static final String CHANNEL_1_ID="channel";

    private static Activity mActivity;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        instance=this;
    }

    public static void setActivity(Activity act){
        mActivity=act;
    }

    public static Activity getActivity(){
        return mActivity;
    }


    private void createNotificationChannels() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel1=new NotificationChannel(CHANNEL_1_ID, "Channel 1", NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("This is Channel 1");
            channel1.setShowBadge(true);

            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
    }


    public static boolean hasNetwork() {
        return instance.checkIfHasNetwork();
    }

    public boolean checkIfHasNetwork() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
