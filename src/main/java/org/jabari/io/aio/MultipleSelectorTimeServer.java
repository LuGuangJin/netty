package org.jabari.io.aio;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * Description:
 * 多路复用类Server。
 * @author Jabari Lu
 * @date 2019/1/11
 */
public class MultipleSelectorTimeServer implements Runnable {

    private Selector selector;

    private ServerSocketChannel serverChannel;

    private volatile boolean stop;


    public MultipleSelectorTimeServer(int port) {


        try {
            selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }

    }



    @Override
    public void run() {

    }
}
