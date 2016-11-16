package com.qefee.jcfs.constant;

/**
 * Created by tongjin on 2016/11/16.
 */
public class IntegerInfo extends AbstractConstantObj {
    private int bytes;

    public IntegerInfo() {
        this.setTag((byte) 3);
    }

    public int getBytes() {
        return bytes;
    }

    public void setBytes(int bytes) {
        this.bytes = bytes;
    }
}
