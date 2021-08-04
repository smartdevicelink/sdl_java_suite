package com.smartdevicelink.protocol.enums;

import com.smartdevicelink.util.ByteEnumer;

import java.util.Vector;

public class QueryID extends ByteEnumer {

    private static final Vector<QueryID> theList = new Vector<>();

    public static Vector<QueryID> getList() {
        return theList;
    }

    protected QueryID(byte value, String name) {
        super(value, name);
    }

    public final static QueryID SEND_HANDSHAKE_DATA = new QueryID((byte) 0x01, "SEND_HANDSHAKE_DATA");
    public final static QueryID SEND_INTERNAL_ERROR = new QueryID((byte) 0x02, "SEND_INTERNAL_ERROR");
    public final static QueryID INVALID_QUERY_ID = new QueryID((byte) 0xFF, "INVALID_QUERY_ID");

    static {
        theList.addElement(SEND_HANDSHAKE_DATA);
        theList.addElement(SEND_INTERNAL_ERROR);
        theList.addElement(INVALID_QUERY_ID);
    }

    public static QueryID valueOf(byte passedByte) {
        return (QueryID) get(theList, passedByte);
    }

    public static QueryID[] values() {
        return theList.toArray(new QueryID[theList.size()]);
    }
}
