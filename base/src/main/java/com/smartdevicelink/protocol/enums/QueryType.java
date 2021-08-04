package com.smartdevicelink.protocol.enums;

import com.smartdevicelink.util.ByteEnumer;

import java.util.Vector;

public class QueryType extends ByteEnumer {

    private static final Vector<QueryType> theList = new Vector<>();

    public static Vector<QueryType> getList() {
        return theList;
    }

    protected QueryType(byte value, String name) {
        super(value, name);
    }

    public final static QueryType REQUEST = new QueryType((byte) 0x00, "REQUEST");
    public final static QueryType RESPONSE = new QueryType((byte) 0x10, "RESPONSE");
    public final static QueryType NOTIFICATION = new QueryType((byte) 0x20, "NOTIFICATION");
    public final static QueryType INVALID_QUERY_TYPE = new QueryType((byte) 0xFF, "INVALID_QUERY_TYPE");

    static {
        theList.addElement(REQUEST);
        theList.addElement(RESPONSE);
        theList.addElement(NOTIFICATION);
        theList.addElement(INVALID_QUERY_TYPE);
    }

    public static QueryType valueOf(byte passedByte) {
        return (QueryType) get(theList, passedByte);
    }

    public static QueryType[] values() {
        return theList.toArray(new QueryType[theList.size()]);
    }
}