package ru.spbau;

import java.awt.*;
import java.io.DataInputStream;
import java.io.IOException;
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
        System.out.println("inRun");
        int cmd;
        try {
            cmd = in.readInt();
            if (cmd != key) {
                System.out.println("Wrong Key!");
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            System.out.println("inWhile");
            try {
                cmd = in.readInt();
                System.out.println(cmd);
                if (cmd == 0) {
                    break;
                }
                if (cmd == 1) {
                    newCmd();
                }
                if (cmd == 2) {
                    runCmd();
                }
                if (cmd == 3) {
                    addCmd();
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
        System.out.println("outRun");
    }
    private void addCmd() {
        try {
            int n = in.readInt();
            System.out.println(n);
            for (int i = 0; i < n; ++i) {
                int m = in.readInt();
                System.out.print(m);
                System.out.print(" -> ");
                List<Integer> list = new ArrayList<>();
                for (int j = 0; j < m; ++j) {
                    int k = in.readInt();
                    list.add(k);
                    System.out.print(k);
                    System.out.print(' ');
                }
                System.out.println();
                commands.add(new Command(list));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void runCmd() {
        System.out.println("inRunCmd");
        while (true) {
            int cmd = 0;
            try {
                cmd = in.readInt();
                System.out.println(cmd);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
            if (cmd == -1) {
                break;
            }
            else  {
                commands.get(cmd).run();
            }
        }

        System.out.println("outRunCmd");
    }
    private void newCmd() {
        commands = new ArrayList<>();
        addCmd();
    }
}
