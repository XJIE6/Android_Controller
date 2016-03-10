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


    public Clicker() throws IOException {
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        key = rand.nextInt(1 << 10);
        ServerSocket s;
        s = new ServerSocket(0);
        String ip = InetAddress.getLocalHost().toString().split("/")[1];
        System.out.print(InetAddress.getLocalHost().toString());
        int port = s.getLocalPort();
        System.out.println("Enter '" + getCode(ip, port, key) + "' into your phone!");
        in = new DataInputStream(s.accept().getInputStream());
        System.out.println("Accept!");
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
                System.out.print("Your computer is not supported.\n");
                System.exit(0);
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
            System.out.print("Connection problem. Connection closed. Try to create new connection\n");
            return;
        }
        while (true) {
            try {
                cmd = in.readInt();
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
                System.out.print("Connection problem. Connection closed. Try to create new connection\n");
                return;
            }
        }
    }
    private void addCmd() throws IOException {
        int n = in.readInt();
        for (int i = 0; i < n; ++i) {
            int m = in.readInt();
            List<Integer> list = new ArrayList<>();
            for (int j = 0; j < m; ++j) {
                list.add(in.readInt());
            }
            commands.add(new Command(list));
        }
    }

    private void runCmd() throws IOException {
        while (true) {
            int cmd = in.readInt();
            if (cmd == -1) {
                break;
            }
            else  {
                commands.get(cmd).run();
            }
        }
    }
    private void newCmd() throws IOException {
        commands = new ArrayList<>();
        addCmd();
    }
}
