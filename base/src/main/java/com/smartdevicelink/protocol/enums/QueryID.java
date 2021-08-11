package com.smartdevicelink.protocol.enums;

import java.util.Enumeration;
import java.util.Objects;
import java.util.Vector;

public class QueryID {

    private static final Vector<QueryID> theList = new Vector<>();

    public static Vector<QueryID> getList() {
        return theList;
    }

    public static final byte[] sendHandshakeDataByteArray= {0x0, 0x0, 0x0, 0x0, 0x0, 0x1};
    public static final byte[] sendInternalErrorByteArray= {0x0, 0x0, 0x0, 0x0, 0x0, 0x2};
    public static final byte[] invalidQueryIdByteArray= {0xF, 0xF, 0xF, 0xF, 0xF, 0xF};
    public final static QueryID SEND_HANDSHAKE_DATA = new QueryID(sendHandshakeDataByteArray, "SEND_HANDSHAKE_DATA");
    public final static QueryID SEND_INTERNAL_ERROR = new QueryID(sendInternalErrorByteArray, "SEND_INTERNAL_ERROR");
    public final static QueryID INVALID_QUERY_ID = new QueryID(invalidQueryIdByteArray, "INVALID_QUERY_ID");

    static {
        theList.addElement(SEND_HANDSHAKE_DATA);
        theList.addElement(SEND_INTERNAL_ERROR);
        theList.addElement(INVALID_QUERY_ID);
    }

    protected QueryID(byte[] value, String name) {
        this.value = value;
        this.name = name;
    }

    private final byte[] value;
    private final String name;

    public byte[] getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public boolean equals(QueryID other) {
        return Objects.equals(name, other.getName());
    }

    public boolean eq(QueryID other) {
        return equals(other);
    }

    public byte[] value() {
        return value;
    }

    public static QueryID get(Vector<?> theList, byte[] value) {
        Enumeration<?> enumer = theList.elements();
        while (enumer.hasMoreElements()) {
            try {
                QueryID current = (QueryID) enumer.nextElement();
                if (current.getValue() == value) {
                    return current;
                }
            } catch (ClassCastException e) {
                return null;
            }
        }
        return null;
    }

    public static QueryID get(Vector<?> theList, String name) {
        Enumeration<?> enumer = theList.elements();
        while (enumer.hasMoreElements()) {
            try {
                QueryID current = (QueryID) enumer.nextElement();
                if (current.getName().equals(name)) {
                    return current;
                }
            } catch (ClassCastException e) {
                return null;
            }
        }
        return null;
    }

    public static QueryID valueOf(byte[] passedByteArray) {
        return (QueryID) get(theList, passedByteArray);
    }

    public static QueryID[] values() {
        return theList.toArray(new QueryID[theList.size()]);
    }
}
