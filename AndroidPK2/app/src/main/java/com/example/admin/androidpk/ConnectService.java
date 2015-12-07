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
import java.io.InputStream;
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
            //final int serverPort = 6666;
            long res = 0;
            for (int i = 0; i < address.length(); ++i) {
                res *= 94;
                res += address.charAt(i) - 33;
            }

            //System.out.println(res);
            String ip = "";
            for (int i = 0; i < 4; ++i) {
                if (i != 0) {
                    ip += '.';
                }
                ip += res % (1 << 8);
                res /= (1 << 8);

                //System.out.println(res);
            }
            final int serverPort = (int) (res % (1 << 16));
            res /= (1 << 16);

            //System.out.println(res);
            final String ipAdress = ip;
            final int key = (int) res;
            Log.d(MainActivity.TAG, ip);
            Log.d(MainActivity.TAG, String.valueOf(serverPort));
            Log.d(MainActivity.TAG, String.valueOf(key));
            new Thread() {
                @Override
                public void run() {
                    try {
                        InetAddress ipAddress = InetAddress.getByName(ipAdress); // создаем объект который отображает вышеописанный IP-адрес.
                        //System.out.println("Any of you heard of a socket with IP address " + address + " and port " + serverPort + "?");
                        Socket socket = new Socket(ipAddress, serverPort); // создаем сокет используя IP-адрес и порт сервера.
                        //System.out.println("Yes! I just got hold of the program.");

                        // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиентом.
                        InputStream sin = socket.getInputStream();
                        OutputStream sout = socket.getOutputStream();

                        // Конвертируем потоки в другой тип, чтоб легче обрабатывать текстовые сообщения.
                        in = new DataInputStream(sin);
                        out = new DataOutputStream(sout);

                        // Создаем поток для чтения с клавиатуры.
                        //String line = null;

                        //while (true) {
                        //line = "lol"; // ждем пока пользователь введет что-то и нажмет кнопку Enter.
                        //System.out.println("Sending this line to the server...");
                        //out.writeUTF(line); // отсылаем введенную строку текста серверу.
                        //out.flush(); // заставляем поток закончить передачу данных.
                        //line = in.readUTF(); // ждем пока сервер отошлет строку текста.
                        //}
                        while (true) {
                            synchronized (MainActivity.mail) {
                                while (MainActivity.mail.isEmpty()) {
                                    MainActivity.mail.wait();
                                }
                                while (!MainActivity.mail.isEmpty()) {
                                    String s = MainActivity.mail.remove();
                                    out.writeUTF(s);
                                    out.flush();
                                    Log.d(MainActivity.TAG, "wahaha");
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
