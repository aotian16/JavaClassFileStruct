package com.qefee.jcfs.constant;

/**
 * Created by tongjin on 2016/11/16.
 */
public class FloatInfo extends AbstractConstantObj {
    private float bytes;

    public FloatInfo() {
        this.setTag((byte) 4);
    }

    public float getBytes() {
        return bytes;
    }

    public void setBytes(float bytes) {
        this.bytes = bytes;
    }
}
