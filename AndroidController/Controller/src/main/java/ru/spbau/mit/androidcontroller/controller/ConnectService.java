package ru.spbau.mit.androidcontroller.controller;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import java.io.DataOutputStream;
import ru.spbau.mit.androidcontroller.tools.Connector;

public class ConnectService extends IntentService {
    DataOutputStream out;
    static final String IP = "IP";
    public ConnectService() {
        super("ConnectService");
    }

    boolean tryToConnect(final String address) { //try to create a connection
        try {

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
                                    out.writeInt(s);
                                    out.flush();
                                }
                            }

                        }
                    } catch (Exception x) {
                        x.printStackTrace();
                    }
                }
            }.start();
        } catch (Exception x) {
            return false;
        }
        return true;
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
