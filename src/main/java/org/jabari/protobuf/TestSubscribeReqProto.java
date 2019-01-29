package org.jabari.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author Jabari Lu
 * @date 2018/12/8
 */
public class TestSubscribeReqProto {

    /**
     * 加密
     * @param req
     * @return
     */
    private static byte[] encode(SubscribeReqProto.SubscribeReq req) {
        return req.toByteArray();
    }

    /**
     * 解密
     * @param body
     * @return
     */
   private static SubscribeReqProto.SubscribeReq decode(byte[] body) throws InvalidProtocolBufferException {
       return SubscribeReqProto.SubscribeReq.parseFrom(body);
   }

    /**
     * 创建请求。
     * @return
     */
   private static SubscribeReqProto.SubscribeReq createSubscribeReq(int i){
       SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
       builder.setSubReqID(i);
       builder.setUserName("Jabari Lu");
       builder.setProductName("Protobuf");
       List<String> address = new ArrayList<>();
       address.add("Su Zhou");
       address.add("Kun Shan");
       address.add("Shang Hai");
       builder.addAllAddress(address);
       return builder.build();
   }


    public static void main(String[] args) throws InvalidProtocolBufferException {
        SubscribeReqProto.SubscribeReq req1 = createSubscribeReq(1);
        System.out.println(String.format("Before encode: %s", req1.toString()));
        SubscribeReqProto.SubscribeReq req2 = decode(encode(req1));
        System.out.println(String.format("After decode: %s", req2.toString()));
        System.out.println(String.format("Assert equals: %s", req1.equals(req2)));

    }

}
