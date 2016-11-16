package com.qefee.jcfs.constant;

/**
 * Created by tongjin on 2016/11/16.
 */
public class StringInfo extends AbstractConstantObj {
    private short index;

    public StringInfo() {
        this.setTag((byte) 8);
    }

    public short getIndex() {
        return index;
    }

    public void setIndex(short index) {
        this.index = index;
    }
}
