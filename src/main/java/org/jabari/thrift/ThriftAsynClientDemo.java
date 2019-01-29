package org.jabari.thrift;

import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TNonblockingTransport;
import thrift.generated.Person;
import thrift.generated.PersonService;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 * Thrift异步客户端测试类。
 * @author Jabari Lu
 * @date 2019/1/13
 */
public class ThriftAsynClientDemo {

    public static final String SERVER_IP = "localhost";
    public static final int SERVER_PORT = 8899;
    public static final int TIMEOUT = 30000;

    public void startClient(String userName) {
        try {
            TAsyncClientManager clientManager = new TAsyncClientManager();
            TNonblockingTransport transport = new TNonblockingSocket(SERVER_IP, SERVER_PORT, TIMEOUT);

            TProtocolFactory protocolFactory = new TCompactProtocol.Factory();
            PersonService.AsyncClient asyncClient = new PersonService.AsyncClient(protocolFactory, clientManager,
                    transport);
            System.out.println("Client start .....");

            CountDownLatch latch = new CountDownLatch(1);
            AsynCallback callBack = new AsynCallback(latch);
            System.out.println("call method getPersonByUserName() start ...");
            asyncClient.getPersonByUserName(userName, callBack);
            System.out.println("call method getPersonByUserName() end ...");
            boolean wait = latch.await(30, TimeUnit.SECONDS);
            System.out.println("latch.await =" + wait);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("startClient end!");
    }

    public class AsynCallback implements AsyncMethodCallback<Person> {

        private CountDownLatch latch;

        public AsynCallback(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void onComplete(Person response) {
            System.out.println("onComplete()......");
            try {
                // Thread.sleep(1000L * 1);
                System.out.println("AsynCall result =:" + response.getUserName() + "," + response.getAge() + "," +
                        (response.isMarried() ? "married":"unmarried"));
            } finally {
                latch.countDown();
            }
        }



        @Override
        public void onError(Exception exception) {
            System.out.println("onError :" + exception.getMessage());
            latch.countDown();
        }
    }



    public static void main(String[] args) {
        ThriftAsynClientDemo thriftAsynClient = new ThriftAsynClientDemo();
        thriftAsynClient.startClient("Jabari Lu");
        thriftAsynClient.startClient("LGJ");
    }

}
