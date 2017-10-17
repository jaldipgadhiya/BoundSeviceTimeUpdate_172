package com.android.acadgild.boundsevicetimeupdate;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.android.acadgild.boundsevicetimeupdate.BoundService.MyBinder;

public class MainActivity extends AppCompatActivity {
    //Object of BoundService class
    BoundService mBoundService;
    boolean mServiceBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView timestampText = (TextView) findViewById(R.id.timestamp_text);
        Button printTimestampButton = (Button) findViewById(R.id.print_timestamp);

        //On click of Start Service button
        printTimestampButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mServiceBound) {
                    timestampText.setText(mBoundService.getTimestamp());
                }
            }
        });



    }

    //onStart method
    @Override
    protected void onStart() {
        super.onStart();
        //Create Intent object
        Intent intent = new Intent(this, BoundService.class);
        //call Start Service
        startService(intent);
        //call bindService
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    //onStop method
    @Override
    protected void onStop() {
        super.onStop();
        if (mServiceBound) {
            //call unbindService
            unbindService(mServiceConnection);
            mServiceBound = false;
        }
    }

    //Create object of ServiceConnection
    private ServiceConnection mServiceConnection = new ServiceConnection() {

        //call onServiceDisconnected
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mServiceBound = false;
        }

        //call onServiceConnected
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //create object of MyBinder class
            MyBinder myBinder = (MyBinder) service;
            //call getService method of MyBinder class
            mBoundService = myBinder.getService();
            mServiceBound = true;
        }
    };
}