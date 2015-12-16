package com.example.admin.androidpk;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.security.Provider;

/**
 * Created by Admin on 18.10.2015.
 */
public class ConnectService extends IntentService {
    static final String OUT = "out_buffer";
    DataOutputStream out;
    static final String IP = "IP";
    public ConnectService() {
        super("ConnectService");
    }

    boolean tryToConnect(final String address) {
        try {
            long res = 0;
            for (int i = 0; i < address.length(); ++i) {
                res *= 94;
                res += address.charAt(i) - 33;
            }
            System.out.println(res);
            String ip = "";
            for (int i = 0; i < 4; ++i) {
                if (i != 0) {
                    ip += '.';
                }
                ip += res % (1 << 8);
                res /= (1 << 8);

                System.out.println(res);

            }
            final int serverPort = (int) (res % (1 << 16));
            res /= (1 << 16);

            System.out.println(res);

            final String ipAdress = ip;
            final int key = (int) res;
            Log.d(MainActivity.TAG, ip);
            Log.d(MainActivity.TAG, String.valueOf(serverPort));
            Log.d(MainActivity.TAG, String.valueOf(key));
            new Thread() {
                @Override
                public void run() {
                    try {
                        InetAddress ipAddress = InetAddress.getByName(ipAdress);
                        Socket socket = new Socket(ipAddress, serverPort);
                        OutputStream sout = socket.getOutputStream();
                        out = new DataOutputStream(sout);
                        out.writeInt(key);

                        /*out.writeInt(1);
                        out.writeInt(38);

                        out.writeInt(1);
                        out.writeInt(87);
                        out.writeInt(1);
                        out.writeInt(83);
                        out.writeInt(1);
                        out.writeInt(65);
                        out.writeInt(1);
                        out.writeInt(68);
                        out.writeInt(1);
                        out.writeInt(38);
                        out.writeInt(1);
                        out.writeInt(40);
                        out.writeInt(1);
                        out.writeInt(37);
                        out.writeInt(1);
                        out.writeInt(39);
                        out.writeInt(1);
                        out.writeInt(69);
                        out.writeInt(1);
                        out.writeInt(81);
                        out.writeInt(1);
                        out.writeInt(32);

                        out.writeInt(1);
                        out.writeInt(87);

                        out.writeInt(2);
                        out.writeInt(87);
                        out.writeInt(65);

                        out.writeInt(1);
                        out.writeInt(65);

                        out.writeInt(2);
                        out.writeInt(65);
                        out.writeInt(83);

                        out.writeInt(1);
                        out.writeInt(83);

                        out.writeInt(2);
                        out.writeInt(83);
                        out.writeInt(68);

                        out.writeInt(1);
                        out.writeInt(68);

                        out.writeInt(2);
                        out.writeInt(68);
                        out.writeInt(87);

                        out.writeInt(1);
                        out.writeInt(-87);
                        out.writeInt(1);
                        out.writeInt(-83);
                        out.writeInt(1);
                        out.writeInt(-65);
                        out.writeInt(1);
                        out.writeInt(-68);
                        out.writeInt(1);
                        out.writeInt(-38);
                        out.writeInt(1);
                        out.writeInt(-40);
                        out.writeInt(1);
                        out.writeInt(-37);
                        out.writeInt(1);
                        out.writeInt(-39);
                        out.writeInt(1);
                        out.writeInt(-69);
                        out.writeInt(1);
                        out.writeInt(-81);
                        out.writeInt(1);
                        out.writeInt(-32);



                        out.writeInt(1);
                        out.writeInt(-87);

                        out.writeInt(2);
                        out.writeInt(-87);
                        out.writeInt(-65);

                        out.writeInt(1);
                        out.writeInt(-65);

                        out.writeInt(2);
                        out.writeInt(-65);
                        out.writeInt(-83);

                        out.writeInt(1);
                        out.writeInt(-83);

                        out.writeInt(2);
                        out.writeInt(-83);
                        out.writeInt(-68);

                        out.writeInt(1);
                        out.writeInt(-68);

                        out.writeInt(2);
                        out.writeInt(-68);
                        out.writeInt(-87);

                        out.writeInt(2);*/

                        int [] arr = new int[]{1, 30,
                                1, 87, //w
                                1, -87,
                                1, 68, //d
                                1, -68,
                                1, 83, //s
                                1, -83,
                                1, 65, //a
                                1, -65,
                                1, 38, //up
                                1, -38,
                                1, 39, //right
                                1, -39,
                                1, 40, //down
                                1, -40,
                                1, 37, //left
                                1, -37,
                                1, 69, //e
                                1, -69,
                                1, 81, //q
                                1, -81,
                                1, 32, //space
                                1, -32,
                                2, 87, 68, //wd
                                2, -68, -87,
                                2, 68, 83, //ds
                                2, -83, -68,
                                2, 83, 65, //sa
                                2, -65, -83,
                                2, 65, 87, //aw
                                2, -87, -65,
                                2
                        };

                        for (int i = 0; i < arr.length; ++i) {
                            out.writeInt(arr[i]);
                        }
                        out.flush();
                        Log.d(MainActivity.TAG, "WOW");

                        while (true) {
                            synchronized (MainActivity.mail) {
                                while (MainActivity.mail.isEmpty()) {
                                    MainActivity.mail.wait();
                                }
                                while (!MainActivity.mail.isEmpty()) {
                                    final Integer s = MainActivity.mail.remove();
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                Log.d(MainActivity.TAG, s.toString());
                                                out.writeInt(s);
                                                out.flush();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }).start();

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
        if (extras == null || curIP == null) {
            Intent intent = new Intent(this, InfoActivity.class);
            intent.putExtra("information", "Error");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
//            sendBroadcast(new Intent("finishDownloadIntent"));
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
