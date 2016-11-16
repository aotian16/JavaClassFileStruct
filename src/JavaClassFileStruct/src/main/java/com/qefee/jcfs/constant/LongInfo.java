package com.qefee.jcfs.constant;

/**
 * Created by tongjin on 2016/11/16.
 */
public class LongInfo extends AbstractConstantObj {
    private long bytes;

    public LongInfo() {
        this.setTag((byte) 5);
    }

    public long getBytes() {
        return bytes;
    }

    public void setBytes(long bytes) {
        this.bytes = bytes;
    }
}
