package com.qefee.jcfs.constant;

/**
 * Created by tongjin on 2016/11/16.
 */
public class ClassInfo extends AbstractConstantObj {
    private short index;

    public ClassInfo() {
        this.setTag((byte) 7);
    }

    public short getIndex() {
        return index;
    }

    public void setIndex(short index) {
        this.index = index;
    }
}
