package ru.spbau.mit.androidcontroller.clicker;

import java.util.Scanner;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hi, here is our amazing application! Write 'new' to create new connection, or write 'exit' to exit!\n");
        Scanner in = new Scanner(System.in);
        while (true) { // main loop
            String s = in.nextLine();
            switch(s) {
                case "new": //creating new connection
                    try {
                        Thread newThread = new Thread(new Clicker());
                        newThread.setDaemon(true);
                        newThread.start();
                    } catch (IOException e) {
                        System.out.print("Connection problem. Connection closed. Try to create new connection\n");
                    }
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Unknown command '" + s + "'! Please, try again!\n");
            }
        }
    }
}