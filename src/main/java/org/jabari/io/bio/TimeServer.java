package org.jabari.io.bio;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Description:
 *  BIO之Server
 * @author Jabari Lu
 * @date 2019/1/4
 */
public class TimeServer {


    public static void main(String[] args) throws IOException {
        int port = 8899;
        if (args != null && args.length > 0) {

            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                // 采用默认端口值。
            }
        }

        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("The BIO time server is started in port : " + port);
            Socket socket = null;
            while (true) {
                socket = serverSocket.accept();
                new Thread(new TimeServerHandler(socket)).start();

            }

        } finally {
            if (serverSocket != null) {
                System.out.println("The time server closed !");
                serverSocket.close();
                serverSocket = null;
            }
        }

    }

}
