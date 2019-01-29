package org.jabari.thrift;

import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.*;
import thrift.generated.DataException;
import thrift.generated.Person;
import thrift.generated.PersonService;
import thrift.generated.PersonServiceImpl;

/**
 * Description:
 * THsHaServer半同步半异步的客户端<br/>
 *
 * @author Jabari Lu
 * @date 2019/1/13
 */
public class HalfAsyncClientDemo {

    public static final String SERVER_IP = "localhost";
    public static final int SERVER_PORT = 8899;
    public static final int TIMEOUT = 30000;

    public void startClient(String userName) {
        System.out.println("Person startClient() start ...");
        TTransport transport = null;
        try {
            transport = new TFramedTransport(new TSocket(SERVER_IP,SERVER_PORT,TIMEOUT));
            // 协议要和服务端一致
            TProtocol protocol = new TBinaryProtocol(transport);
            PersonService.Client client = new PersonService.Client(protocol);
            transport.open();
            Person p = client.getPersonByUserName(userName);
            if (p != null && null == p.getUserName()) {
                System.err.println("Thrift client result : Cannot find person with userName:[" + userName+ "]!");
            } else {
                System.out.println("Thrift client result =:" + p.getUserName() + "," + p.getAge() + "," + p.isMarried());
            }
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (DataException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        } finally {
            if (transport != null) {
                transport.close();
            }
        }

    }


    public static void main(String[] args) {
        HalfAsyncClientDemo clientDemo = new HalfAsyncClientDemo();
        clientDemo.startClient("ABC");
        clientDemo.startClient("jabari");
    }


}
