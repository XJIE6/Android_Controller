package ru.spbau.mit.androidcontroller.tools;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by YuryKravchenko on 05/03/16.
 */
public class Connector {

    public static String encode (String ip, long port, long key) {
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

    public static DataOutputStream decode (String code) throws IOException {
        long res = 0;
        for (int i = 0; i < code.length(); ++i) {
            res *= 94;
            res += code.charAt(i) - 33;
        }
        String ip = "";

        for (int i = 0; i < 4; ++i) {
            if (i != 0) {
                ip += '.';
            }
            ip += res % (1 << 8);
            res /= (1 << 8);

        }
        final int serverPort = (int) (res % (1 << 16));
        res /= (1 << 16);

        System.out.println(res);

        final String ipAdress = ip;
        final int key = (int) res;
        DataOutputStream out = null;
        InetAddress ipAddress = InetAddress.getByName(ipAdress);
        Socket socket = new Socket(ipAddress, serverPort);
        OutputStream sout = socket.getOutputStream();
        out = new DataOutputStream(sout);
        out.writeInt(key);
        return out;
    }

}
