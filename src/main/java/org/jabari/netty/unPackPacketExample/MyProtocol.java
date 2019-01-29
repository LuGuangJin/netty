package org.jabari.netty.unPackPacketExample;

/**
 * Description:
 * 自定义协议。
 * @author Jabari Lu
 * @date 2018/12/16
 */
public class MyProtocol {

    private int length;
    private byte[] content;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
