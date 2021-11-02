package com.smartdevicelink.protocol.enums;

import androidx.annotation.RestrictTo;

import com.smartdevicelink.util.ByteEnumer;

import java.util.Vector;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public class SecurityQueryType extends ByteEnumer {

    private static final Vector<SecurityQueryType> theList = new Vector<>();

    public static Vector<SecurityQueryType> getList() {
        return theList;
    }

    protected SecurityQueryType(byte value, String name) {
        super(value, name);
    }

    public final static SecurityQueryType REQUEST = new SecurityQueryType((byte) 0x00, "REQUEST");
    public final static SecurityQueryType RESPONSE = new SecurityQueryType((byte) 0x10, "RESPONSE");
    public final static SecurityQueryType NOTIFICATION = new SecurityQueryType((byte) 0x20, "NOTIFICATION");
    public final static SecurityQueryType INVALID_QUERY_TYPE = new SecurityQueryType((byte) 0xFF, "INVALID_QUERY_TYPE");

    static {
        theList.addElement(REQUEST);
        theList.addElement(RESPONSE);
        theList.addElement(NOTIFICATION);
        theList.addElement(INVALID_QUERY_TYPE);
    }

    public static SecurityQueryType valueOf(byte passedByte) {
        return (SecurityQueryType) get(theList, passedByte);
    }

    public static SecurityQueryType[] values() {
        return theList.toArray(new SecurityQueryType[theList.size()]);
    }
}