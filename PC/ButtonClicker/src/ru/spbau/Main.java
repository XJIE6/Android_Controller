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
        //for (int i = 0; i < n; ++i) {

        //}
        /*int port = 6666; // случайный порт (может быть любое число от 1025 до 65535)
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
            int n = 100;
            while (true) {
                if (in.available() > 0) {
                    line = in.readUTF();
                    switch (line.charAt(0)) {
                        case 'w':
                            robot.keyPress(KeyEvent.VK_W);
                            Thread.sleep(n);
                            robot.keyRelease(KeyEvent.VK_W);
                            break;
                        case 's':
                            robot.keyPress(KeyEvent.VK_S);
                            Thread.sleep(n);
                            robot.keyRelease(KeyEvent.VK_S);
                            break;
                        case 'a':
                            robot.keyPress(KeyEvent.VK_A);
                            Thread.sleep(n);
                            robot.keyRelease(KeyEvent.VK_A);
                            break;
                        case 'd':
                            robot.keyPress(KeyEvent.VK_D);
                            Thread.sleep(n);
                            robot.keyRelease(KeyEvent.VK_D);
                            break;
                        case 'i':
                            robot.keyPress(KeyEvent.VK_UP);
                            Thread.sleep(n);
                            robot.keyRelease(KeyEvent.VK_UP);
                            break;
                        case 'k':
                            robot.keyPress(KeyEvent.VK_DOWN);
                            Thread.sleep(n);
                            robot.keyRelease(KeyEvent.VK_DOWN);
                            break;
                        case 'j':
                            robot.keyPress(KeyEvent.VK_LEFT);
                            Thread.sleep(n);
                            robot.keyRelease(KeyEvent.VK_LEFT);
                            break;
                        case 'l':
                            robot.keyPress(KeyEvent.VK_RIGHT);
                            Thread.sleep(n);
                            robot.keyRelease(KeyEvent.VK_RIGHT);
                            break;
                        case 'q':
                            robot.keyPress(KeyEvent.VK_Q);
                            Thread.sleep(n);
                            robot.keyRelease(KeyEvent.VK_Q);
                            break;
                        case 'e':
                            robot.keyPress(KeyEvent.VK_E);
                            Thread.sleep(n);
                            robot.keyRelease(KeyEvent.VK_E);
                            break;
                        case ' ':
                            robot.keyPress(KeyEvent.VK_SPACE);
                            Thread.sleep(n);
                            robot.keyRelease(KeyEvent.VK_SPACE);
                            break;
                    }
                }
            }

            while (true) {
                if (in.available() > 0) {
                    line = in.readUTF();
                    for (int i = 0; i < line.length(); ++i) {
                        int keyCode = KeyEvent.getExtendedKeyCodeForChar(line.charAt(i));
                        robot.keyPress(keyCode);
                        robot.keyRelease(keyCode);
                    }
                }
            }
        } catch (Exception x) {
            x.printStackTrace();
        }*/
    }
}
