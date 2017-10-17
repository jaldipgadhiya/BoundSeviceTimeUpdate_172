package com.android.acadgild.boundsevicetimeupdate;

/**
 * Created by Jal on 17-10-2017.
 * BoundService class
 */

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Chronometer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BoundService extends Service {
    private static String LOG_TAG = "BoundService";
    //Create object of binder class
    private IBinder mBinder = new MyBinder();
    // Object of Chronometer Class that implements a simple timer.
    private Chronometer mChronometer;

    //onCreate method
    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(LOG_TAG, "in onCreate");
        mChronometer = new Chronometer(this);
        //give it a start time in the elapsedRealtime() timebase, and it counts up from that
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.start();
    }

    //onBind method
    @Override
    public IBinder onBind(Intent intent) {
        Log.v(LOG_TAG, "in onBind");
        return mBinder;
    }

    //onRebind method
    @Override
    public void onRebind(Intent intent) {
        Log.v(LOG_TAG, "in onRebind");
        super.onRebind(intent);
    }

    //onUnbind method
    @Override
    public boolean onUnbind(Intent intent) {
        Log.v(LOG_TAG, "in onUnbind");
        return true;
    }

    //onDestroy method
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(LOG_TAG, "in onDestroy");
        mChronometer.stop();
    }

    //getTimestamp method to return dateformat
    public String getTimestamp() {
        SimpleDateFormat dateformat =
                new SimpleDateFormat("yyyy/dd/MM HH:mm:ss", Locale.US);
        return (dateformat.format(new Date()));
    }

    //MyBinder class to return instance of BoundService
    public class MyBinder extends Binder {
        BoundService getService() {
            return BoundService.this;
        }
    }
}
