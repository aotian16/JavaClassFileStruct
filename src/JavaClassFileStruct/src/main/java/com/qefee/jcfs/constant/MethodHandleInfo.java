package com.qefee.jcfs.constant;

/**
 * Created by tongjin on 2016/11/16.
 */
public class MethodHandleInfo extends AbstractConstantObj {
    private byte referenceKind;
    private short referenceIndex;

    public MethodHandleInfo() {
        this.setTag((byte) 15);
    }

    public byte getReferenceKind() {
        return referenceKind;
    }

    public void setReferenceKind(byte referenceKind) {
        this.referenceKind = referenceKind;
    }

    public short getReferenceIndex() {
        return referenceIndex;
    }

    public void setReferenceIndex(short referenceIndex) {
        this.referenceIndex = referenceIndex;
    }
}
