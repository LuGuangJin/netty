package org.jabari.io.aio;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Description:
 *  AIO之Server
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
            System.out.println("The AIO time server is started in port : " + port);
            Socket socket = null;
            // 创建IO任务的线程池
            TimeServerHandlerExecutePool pool = new TimeServerHandlerExecutePool(50, 10000);
            while (true) {
                socket = serverSocket.accept();
                pool.execute(new TimeServerHandler(socket));
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
