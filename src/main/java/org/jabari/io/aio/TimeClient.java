package org.jabari.io.aio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Description:
 * AIO的客户端
 * @author Jabari Lu
 * @date 2019/1/4
 */
public class TimeClient {

    public static void main(String[] args) {
        int port = 8899;
        if (args != null && args.length > 0) {

            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                // 采用默认端口值。
            }
        }

        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;

        try {
            socket = new Socket("127.0.0.1", port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println("Query Time Order");
            System.out.println("Client send order to server successfully!");
            String resp = in.readLine();
            System.out.println("Client receive resp : " + resp);

        } catch (IOException e) {

        } finally {

            if (out != null) {
                out.close();
                out = null;
            }

            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                in = null;
            }

            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                socket = null;
            }

        }


    }

}
