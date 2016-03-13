package ru.spbau.mit.androidcontroller.controller;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import java.io.DataOutputStream;
import java.net.SocketException;
import java.util.concurrent.ExecutionException;

import ru.spbau.mit.androidcontroller.tools.Connector;

public class ConnectService extends IntentService {
    DataOutputStream out;
    static final String IP = "IP";
    public ConnectService() {
        super("ConnectService");
    }
    final static Boolean[] isConnect = {true};

    boolean tryToConnect(final String address) { //try to create a connection
        final ConnectService connectService = this;
        isConnect[0] = true;
        new Thread() {
            @Override
            public void run() {
                try {
                    out = Connector.decode(address);
                    while (true) {
                        synchronized (MainActivity.mail) {
                            while (MainActivity.mail.isEmpty()) {
                                MainActivity.mail.wait();
                            }
                            while (!MainActivity.mail.isEmpty()) {
                                final Integer s = MainActivity.mail.remove();
                                Log.d(MainActivity.TAG, "Service " + s.toString());
                                int countAttempts = 0;
                                try {
                                    out.writeInt(s);
                                } catch (SocketException e) {
                                    countAttempts++;
                                    if (countAttempts > 10) {
                                        throw new SocketException();
                                    }
                                }
                                out.flush();
                            }
                        }
                    }
                } catch (Exception x) {
                    isConnect[0] = false;
//                    Intent intent = new Intent(connectService, InfoActivity.class);
//                    intent.putExtra(InfoActivity.inform, "Wrong command format");
//                    startActivity(intent);
                }
            }
        }.start();
        return isConnect[0];
    }

    @Override
    protected void onHandleIntent(Intent curIntent) {
        Bundle extras = curIntent.getExtras();
        String curIP = extras.getString(IP);
        if (curIP == null) {
            Intent intent = new Intent(this, InfoActivity.class);
            intent.putExtra("information", "Error");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            if (tryToConnect(curIP)) {
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
