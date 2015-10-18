package com.example.admin.androidpk;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import java.security.Provider;

/**
 * Created by Admin on 18.10.2015.
 */
public class ConnectService extends IntentService {
    static final String IP = "IP";
    public ConnectService() {
        super("ConnectService");
    }
    boolean tryToConnect(String IP) {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected void onHandleIntent(Intent curIntent) {
        Bundle extras = curIntent.getExtras();
        String curIP = extras.getString(IP);
        if (extras == null || curIP == null) {
            Intent intent = new Intent(this, InfoActivity.class);
            intent.putExtra("information", "Error");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
//            sendBroadcast(new Intent("finishDownloadIntent"));
            if (tryToConnect(IP)) {
                Intent intent = new Intent(this, MenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, InfoActivity.class);
                intent.putExtra("information", "Failed to connect");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }
}
