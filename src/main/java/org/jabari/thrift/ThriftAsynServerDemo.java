package org.jabari.thrift;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import thrift.generated.PersonService;
import thrift.generated.PersonServiceImpl;

/**
 * Description:
 * Thrift异步服务端测试类。
 * @author Jabari Lu
 * @date 2019/1/13
 */
public class ThriftAsynServerDemo {

    public static final int SERVER_PORT = 8899;


    public void startServer(int port) {
        try {
            System.out.println("Person TNonblockingServer start ....");

            TProcessor tprocessor = new PersonService.Processor<PersonService.Iface>(new PersonServiceImpl());

            TNonblockingServerSocket tnbSocketTransport = new TNonblockingServerSocket(port);
            TNonblockingServer.Args tnbArgs = new TNonblockingServer.Args(tnbSocketTransport);
            tnbArgs.processor(tprocessor);
            tnbArgs.transportFactory(new TFramedTransport.Factory());
            tnbArgs.protocolFactory(new TCompactProtocol.Factory());

            // 使用非阻塞式IO，服务端和客户端需要指定TFramedTransport数据传输的方式
            TServer server = new TNonblockingServer(tnbArgs);
            System.out.println("---------Server start:");
            server.serve();

        } catch (Exception e) {
            System.err.println("Server start error!!!");
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        ThriftAsynServerDemo thriftAsynServer = new ThriftAsynServerDemo();
        thriftAsynServer.startServer(SERVER_PORT);
    }

}
