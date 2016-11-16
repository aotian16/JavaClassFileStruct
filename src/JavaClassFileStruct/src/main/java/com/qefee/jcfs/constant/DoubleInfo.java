package com.qefee.jcfs.constant;

/**
 * Created by tongjin on 2016/11/16.
 */
public class DoubleInfo extends AbstractConstantObj {
    private double bytes;

    public DoubleInfo() {
        this.setTag((byte) 6);
    }

    public double getBytes() {
        return bytes;
    }

    public void setBytes(double bytes) {
        this.bytes = bytes;
    }
}
