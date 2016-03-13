package ru.spbau.mit.androidcontroller.clicker;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Scanner;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String ip = null;
        System.out.println("Hi, here is our amazing application! Write 'new' to create new connection, write 'ip' to change your ip, write 'help' to get set of probable ip, or write 'exit' to exit!");
        try {
            ip = InetAddress.getLocalHost().toString().split("/")[1];
            System.out.println("Your ip is " + ip);
        } catch (UnknownHostException e) {
            System.out.println("Your ip is undefined, please, set it");
        }
        Scanner in = new Scanner(System.in);
        while (true) { // main loop
            String s = in.nextLine();
            switch(s.split(" ")[0]) {
                case "new": //creating new connection
                    if (ip == null) {
                        System.out.println("Your ip is undefined, please, set it first");
                        break;
                    }
                    try {
                        Thread newThread = new Thread(new Clicker(ip));
                        newThread.setDaemon(true);
                        newThread.start();
                    } catch (IOException e) {
                        System.out.println("Connection problem. Connection closed. Try to create new connection");
                    }
                    break;
                case "help":
                    try {
                        Enumeration e = NetworkInterface.getNetworkInterfaces();
                        while(e.hasMoreElements()) {
                            NetworkInterface n = (NetworkInterface) e.nextElement();
                            Enumeration ee = n.getInetAddresses();
                            while (ee.hasMoreElements()) {
                                InetAddress i = (InetAddress) ee.nextElement();
                                System.out.println(i.getHostAddress());
                            }
                        }
                    } catch (SocketException e) {
                        System.out.println("No available ip");
                    }
                    break;
                case "exit":
                    return;
                case "ip":
                    if (s.split(" ").length > 1) {
                        ip = s.split(" ")[1];

                    }
                    else {
                        ip = in.nextLine();
                    }
                    System.out.println("Your ip is " + ip);
                    break;
                default:
                    System.out.println("Unknown command '" + s + "'! Please, try again!");
            }
        }
    }
}