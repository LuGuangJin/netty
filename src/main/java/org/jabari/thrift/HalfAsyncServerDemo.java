package org.jabari.thrift;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;
import thrift.generated.PersonService;
import thrift.generated.PersonServiceImpl;

/**
 * Description:
 * THsHaServer半同步半异步的服务模型<br/>
 * 需要指定为：TFramedTransport数据传输方式。
 * @author Jabari Lu
 * @date 2019/1/13
 */
public class HalfAsyncServerDemo {


    public static final int SERVER_PORT = 8899;

    public void startServer(int port) {
        System.out.println("Person HalfAsyncServer start ...");
        TProcessor processor = new PersonService.Processor<PersonService.Iface>(new PersonServiceImpl());

        try {
            TNonblockingServerSocket serverSocket = new TNonblockingServerSocket(port);

            THsHaServer.Args args = new THsHaServer.Args(serverSocket);
            args.processor(processor);
            args.transportFactory(new TFramedTransport.Factory());
            args.protocolFactory(new TBinaryProtocol.Factory());

            // 半同步半异步的服务模型
            TServer server = new THsHaServer(args);
            System.out.println("半同步半异步的服务模型Server start ...");
            server.serve();



        } catch (TTransportException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        HalfAsyncServerDemo serverDemo = new HalfAsyncServerDemo();
        serverDemo.startServer(SERVER_PORT);
    }


}
