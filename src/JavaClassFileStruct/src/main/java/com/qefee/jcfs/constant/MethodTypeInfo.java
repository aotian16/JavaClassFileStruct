package com.qefee.jcfs.constant;

/**
 * Created by tongjin on 2016/11/16.
 */
public class MethodTypeInfo extends AbstractConstantObj {
    private short descriptorIndex;

    public MethodTypeInfo() {
        this.setTag((byte) 16);
    }

    public short getDescriptorIndex() {
        return descriptorIndex;
    }

    public void setDescriptorIndex(short descriptorIndex) {
        this.descriptorIndex = descriptorIndex;
    }
}
