package com.example.admin.androidpk;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
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
    DataInputStream in;
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

                        out.writeInt(1);
                        out.writeInt(22);

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

                        out.writeInt(2);

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
                                                out.writeInt(s);
                                                out.flush();
                                                Thread.sleep(100);
                                                out.writeInt(s + 11);
                                                out.flush();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            } catch (InterruptedException e) {
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
                intent.putExtra("out_buffer", (Parcelable) out);
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
