package com.qefee.jcfs.constant;

/**
 * Created by tongjin on 2016/11/16.
 */
public abstract class AbstractConstantObj {
    private int constantPoolIndex;
    private byte tag;

    public byte getTag() {
        return tag;
    }

    public void setTag(byte tag) {
        this.tag = tag;
    }

    public int getConstantPoolIndex() {
        return constantPoolIndex;
    }

    public void setConstantPoolIndex(int constantPoolIndex) {
        this.constantPoolIndex = constantPoolIndex;
    }


}
