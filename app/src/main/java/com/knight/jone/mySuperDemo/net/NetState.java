package com.knight.jone.mySuperDemo.net;

import android.annotation.TargetApi;
import android.app.usage.NetworkStats;
import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static android.content.Context.NETWORK_STATS_SERVICE;

public class NetState {
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    NetworkStatsManager networkStatsManager;

    private static class NetStateHolder {
        static NetState INSTANCE = new NetState();
    }

    public static NetState getInstance() {
        return NetStateHolder.INSTANCE;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void autoGetDownLoadSpeed(Context context) {
        if (networkStatsManager != null) {
            return;
        }
        networkStatsManager = (NetworkStatsManager) context.getSystemService(NETWORK_STATS_SERVICE);
        Log.e("RemoteException", "AutoGetDownLoadSpeed1");

        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                //int speed = DownLoadSpeedUtil.getNetSpeed(getApplicationInfo().uid);
                Log.e("RemoteException", "AutoGetDownLoadSpeed2");

                NetworkStats.Bucket bucket = null;
                // try {
                //     //bucket = networkStatsManager.querySummaryForDevice(ConnectivityManager.TYPE_WIFI, "", 0, System.currentTimeMillis());
                //     Log.i("Info", "autoGetDownLoadSpeed Total: " + (bucket.getRxBytes() + bucket.getTxBytes()));
                // } catch (RemoteException e) {
                //     Log.e("RemoteException","AutoGetDownLoadSpeed:" + e);
                //     e.printStackTrace();
                // }
                //mTvNetRate.setText("" + speed);
            }
        }, 1, 100, TimeUnit.MILLISECONDS);
    }

    public static int getUidByPackageName(Context context, String packageName) {
        int uid = -1;
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_META_DATA);

            uid = packageInfo.applicationInfo.uid;
            Log.i("GetUidByPackageName", packageInfo.packageName + " uid:" + uid);


        } catch (PackageManager.NameNotFoundException e) {
        }

        return uid;
    }
}
