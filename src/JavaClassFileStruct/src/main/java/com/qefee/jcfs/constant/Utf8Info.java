package com.qefee.jcfs.constant;

/**
 * Created by tongjin on 2016/11/16.
 */
public class Utf8Info extends AbstractConstantObj {
    private short length;
    private String bytes;

    public Utf8Info() {
        this.setTag((byte) 1);
    }

    public short getLength() {
        return length;
    }

    public void setLength(short length) {
        this.length = length;
    }

    public String getBytes() {
        return bytes;
    }

    public void setBytes(String bytes) {
        this.bytes = bytes;
    }
}
