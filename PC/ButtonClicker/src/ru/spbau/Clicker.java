package ru.spbau;

import java.io.IOException;
import java.net.*;
import java.util.Collections;
import java.util.Random;

public class Clicker implements Runnable {

    private long key;

    public Clicker() {
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        key = abs(rand.nextInt()) % (1 << 10);
        ServerSocket s;
        try {
            s = new ServerSocket(0);
            String ip = getIp();
            int port = s.getLocalPort();
            System.out.println(ip);
            System.out.println(port);
            System.out.println(key);
            System.out.println("Enter '" + getCode(ip, port, key) + "' into your phone!");
            s.accept();
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

    public static String getIp() throws SocketException {
        return Collections.list(NetworkInterface.getNetworkInterfaces()).stream()
                .flatMap(i -> Collections.list(i.getInetAddresses()).stream())
                .filter(ip -> ip instanceof Inet4Address && ip.isSiteLocalAddress())
                .findFirst().orElseThrow(RuntimeException::new)
                .getHostAddress();

    }

    private static void toByte(long a) {
        if (a == 0) {
            return;
        }
        toByte(a / 2);
        System.out.print(a % 2);
    }


    private static String getCode(String ip, long port, long key) {
        long res = 0;
        int i = 0;
        for (String s : ip.split("\\.")) {
            res += Long.parseLong(s) << i;
            i += 8;

            //System.out.println(res);
            //toByte(res);
            //System.out.println();
            //toByte(Integer.parseInt(s));
            //System.out.println();
        }
        res += port << 32;
        //System.out.println(res);
        res += key << 48;
        //System.out.println(res);
        String ans = "";
        while (res != 0) {
            ans = (char)((res % 94) + 33) + ans;
            res /= 94;
        }

        return ans;
    }

    @Override
    public void run() {

    }
}
