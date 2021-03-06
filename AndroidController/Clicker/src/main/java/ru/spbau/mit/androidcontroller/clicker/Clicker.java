package ru.spbau.mit.androidcontroller.clicker;

import ru.spbau.mit.androidcontroller.tools.*;

import java.awt.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Clicker implements Runnable { //this class creates connection with one device and clicks necessary buttons

    private long key;
    DataInputStream in;
    ServerSocket s;
    ArrayList<Command> commands;

    private static final int MAX_KEY = 1 << 10;


    public Clicker(String ip) throws IOException {
        //random key generation
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        key = rand.nextInt(MAX_KEY);

        s = new ServerSocket(0);
        int port = s.getLocalPort();

        System.out.println("Enter '" + Connector.encode(ip, port, key) + "' into your phone!");

    }

    private class Command implements Runnable { //presses/releases integer commends from list
        private  List<Integer> list;
        Robot robot;

        Command (List<Integer> list) {
            this.list = list;
            try {
                robot = new Robot();
            } catch (AWTException e) {
                System.out.println("Your computer is not supported.");
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
        try {
            in = new DataInputStream(s.accept().getInputStream()); //waiting for connection
        } catch (IOException e) {
            System.out.println("Connection problem. Connection closed. Try to create new connection");
            return;
        }

        System.out.println("Accept!");

        int cmd;
        //key check
        try {
            cmd = in.readInt();
            if (cmd != key) {
                System.out.println("Connection with wrong key. Create new connection and try again");
                return;
            }
        } catch (IOException e) {
            System.out.println("Connection problem. Connection closed. Try to create new connection");
            return;
        }
        // main loop
        while (true) {
            try {
                cmd = in.readInt();
                if (cmd == Protocol.END_CONNECTION) {
                    break;
                }
                if (cmd == Protocol.NEW_COMMAND) {
                    newCmd();
                }
                if (cmd == Protocol.RUN_COMMAND) {
                    runCmd();
                }
                if (cmd == Protocol.ADD_COMMAND) {
                    addCmd();
                }
            } catch (IOException e) {
                System.out.println("Connection problem. Connection closed. Try to create new connection");
                return;
            }
        }
    }

    private void addCmd() throws IOException {
        int commandNumber = in.readInt();

        for (int i = 0; i < commandNumber; ++i) {
            int commandLength = in.readInt();

             List<Integer> list = new ArrayList<>();
            for (int j = 0; j < commandLength; ++j) {
                list.add(in.readInt());
            }

            commands.add(new Command(list));
        }
    }

    private void runCmd() throws IOException {
        while (true) {
            int cmd = in.readInt();
            if (cmd == Protocol.END_RUN_COMMAND) {
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
