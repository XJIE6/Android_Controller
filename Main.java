package ru.spbau.yury.kravchenko.pkg;

import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] ar)    {
        int port = 6666; // случайный порт (может быть любое число от 1025 до 65535)
        try {
            ServerSocket ss = new ServerSocket(port); // создаем сокет сервера и привязываем его к вышеуказанному порту
            System.out.println("Waiting for a client...");

            Socket socket = ss.accept(); // заставляем сервер ждать подключений и выводим сообщение когда кто-то связался с сервером
            System.out.println("Got a client :) ... Finally, someone saw me through all the cover!");
            System.out.println();

            // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиенту.
            InputStream sin = socket.getInputStream();

            // Конвертируем потоки в другой тип, чтоб легче обрабатывать текстовые сообщения.
            DataInputStream in = new DataInputStream(sin);
            String line = null;
            Robot robot = new Robot();

            while(true) {
                if (in.available() > 0) {
                    line = in.readUTF();
                    for (int i = 0; i < line.length(); ++i) {
                        int keyCode = KeyEvent.getExtendedKeyCodeForChar(line.charAt(i));
                        robot.keyPress(keyCode);
                        robot.keyRelease(keyCode);
                    }
                }
            }
        } catch(Exception x) { x.printStackTrace(); }
    }
}
