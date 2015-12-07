package ru.spbau;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Clicker implements Runnable {

    private long key;
    DataInputStream in;
    ArrayList<Command> commands;


    public Clicker() {
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        key = abs(rand.nextInt()) % (1 << 10);
        ServerSocket s;
        try {
            s = new ServerSocket(0);
            String ip = InetAddress.getLocalHost().toString().split("/")[1];
            int port = s.getLocalPort();
            System.out.println(ip);
            System.out.println(port);
            System.out.println(key);
            System.out.println("Enter '" + getCode(ip, port, key) + "' into your phone!");
            in = new DataInputStream(s.accept().getInputStream());
            System.out.println("Accept!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int abs(int i) {
        if (i < 0) {
            return -i;
        }
        return i;
    }


    private static String getCode(String ip, long port, long key) {
        long res = 0;
        int i = 0;
        for (String s : ip.split("\\.")) {
            res += Long.parseLong(s) << i;
            i += 8;
        }
        res += port << 32;
        res += key << 48;
        String ans = "";
        while (res != 0) {
            ans = (char)((res % 94) + 33) + ans;
            res /= 94;
        }

        return ans;
    }

    class Command implements Runnable{
        private  List<Integer> list;
        Robot robot;
        Command (List<Integer> list) {
            this.list = list;
            try {
                robot = new Robot();
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            for (int i : list) {
                if (i > 0) {
                    robot.keyPress(i);
                }
                else {
                    robot.keyRelease(-i);
                }
            }
        }
    }

    @Override
    public void run() {
        int cmd;
        try {
            cmd = in.readInt();
            if (cmd != key) {
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                cmd = in.readInt();
                if (cmd == 0) {
                    break;
                }
                if (cmd == 1) {
                    readCmd();
                }
                if (cmd == 2) {
                    runCmd();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void runCmd() {
        while (true) {
            try {
                int cmd = in.readInt();
                if (cmd == -1) {
                    break;
                }
                else  {
                    commands.get(cmd).run();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void readCmd() {
        try {
            int n = in.readInt();
            commands = new ArrayList<>();
            for (int i = 0; i < n; ++i) {
                int m = in.readInt();
                List<Integer> list = new ArrayList<>();
                for (int j = 0; j < m; ++j) {
                    list.add(in.readInt());
                }
                commands.add(new Command(list));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
