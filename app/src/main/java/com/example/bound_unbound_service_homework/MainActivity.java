package com.example.bound_unbound_service_homework;

import androidx.appcompat.app.AppCompatActivity;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btOn,btOff;
    private MusicService musicService;
    private boolean isBound = false;
    private ServiceConnection connection;
    private ServiceConnection serviceConnection;

    private boolean isConnected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectService();
        btOn = (Button) findViewById(R.id.btnPlayMusic);
        btOff = (Button) findViewById(R.id.btnStopMusic);
        musicService= new MusicService();
        btOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isConnected){
                    return;
                }
                musicService.play();
            }
        });

        btOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isConnected){
                    return;
                }
                musicService.pause();
            }
        });
    }
    private void connectService() {

        Intent intent = new Intent(this, MusicService.class);

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MusicService.MyBinder myBinder = (MusicService.MyBinder) service;

                musicService = myBinder.getService();
                isConnected = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                isConnected = false;
                musicService = null;
            }
        };
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }
}