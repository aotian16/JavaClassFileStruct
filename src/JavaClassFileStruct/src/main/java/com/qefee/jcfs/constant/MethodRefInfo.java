package com.qefee.jcfs.constant;

/**
 * Created by tongjin on 2016/11/16.
 */
public class MethodRefInfo extends AbstractConstantObj {
    private short index1;
    private short index2;

    public MethodRefInfo() {
        this.setTag((byte) 10);
    }

    public short getIndex1() {
        return index1;
    }

    public void setIndex1(short index1) {
        this.index1 = index1;
    }

    public short getIndex2() {
        return index2;
    }

    public void setIndex2(short index2) {
        this.index2 = index2;
    }
}
