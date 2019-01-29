package org.jabari.io.nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * Description:
 *
 * @author Jabari Lu
 * @date 2019/1/12
 */
public class NioTest1 {

    public static void main(String[] args) {
        /**
         * Buffer本身就是一块内存，底层实现上，它实际上是个数组。
         * 数据的读、写都是通过Buffer来实现的。
         *
         * 除了数组之外，Buffer还提供了对于数据的结构化访问方式，并且可以追踪到系统的读写过程。
         *
         * Java中的8种原生数据类型（除了boolean）都有相应的Buffer类。
         */
        IntBuffer buffer = IntBuffer.allocate(10);
        for (int i = 0; i < buffer.capacity(); i++) {
            int randomNumber = new SecureRandom().nextInt(20);
            buffer.put(randomNumber);
        }

        buffer.flip();

        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
        

    }
}
