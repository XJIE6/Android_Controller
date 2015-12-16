package ru.spbau;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hi, here is our amazing application! Write 'new' to create new connection, or write 'exit' to exit!");
        Scanner in = new Scanner(System.in);
        while (true) {
            String s = in.nextLine();
            if (s.equals("new")) {
                new Thread(new Clicker()).start();
            }
            else if (s.equals("exit")) {
                break;
            }
            else {
                System.out.println("Unknown command '" + s + "'! Please, try again!");
            }
        }
    }
}
