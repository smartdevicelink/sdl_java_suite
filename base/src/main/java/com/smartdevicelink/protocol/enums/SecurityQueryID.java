package com.smartdevicelink.protocol.enums;

import androidx.annotation.RestrictTo;

import com.smartdevicelink.util.BitConverter;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Objects;
import java.util.Vector;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public class SecurityQueryID {

    private static final Vector<SecurityQueryID> theList = new Vector<>();

    public static Vector<SecurityQueryID> getList() {
        return theList;
    }

    private static final byte[] sendHandshakeDataByteArray = {(byte) 0x00, (byte) 0x00, (byte) 0x01};
    private static final byte[] sendInternalErrorByteArray = {(byte) 0x00, (byte) 0x00, (byte) 0x02};
    private static final byte[] invalidQueryIdByteArray = {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
    public final static SecurityQueryID SEND_HANDSHAKE_DATA = new SecurityQueryID(sendHandshakeDataByteArray, "SEND_HANDSHAKE_DATA");
    public final static SecurityQueryID SEND_INTERNAL_ERROR = new SecurityQueryID(sendInternalErrorByteArray, "SEND_INTERNAL_ERROR");
    public final static SecurityQueryID INVALID_QUERY_ID = new SecurityQueryID(invalidQueryIdByteArray, "INVALID_QUERY_ID");

    static {
        theList.addElement(SEND_HANDSHAKE_DATA);
        theList.addElement(SEND_INTERNAL_ERROR);
        theList.addElement(INVALID_QUERY_ID);
    }

    protected SecurityQueryID(byte[] value, String name) {
        this.value = value;
        this.name = name;
    }

    private final byte[] value;
    private final String name;

    public byte[] getValue() {
        return value;
    }

    public int getIntValue() {
        byte[] copy = new byte[4];
        System.arraycopy(value, 0, copy, 1, 3);
        return BitConverter.intFromByteArray(copy, 0);
    }

    public String getName() {
        return name;
    }

    public boolean equals(SecurityQueryID other) {
        return Objects.equals(name, other.getName());
    }

    public boolean eq(SecurityQueryID other) {
        return equals(other);
    }

    public byte[] value() {
        return value;
    }

    public static SecurityQueryID get(Vector<?> theList, byte[] value) {
        Enumeration<?> enumer = theList.elements();
        while (enumer.hasMoreElements()) {
            try {
                SecurityQueryID current = (SecurityQueryID) enumer.nextElement();
                if (Arrays.equals(current.getValue(), value)) {
                    return current;
                }
            } catch (ClassCastException e) {
                return null;
            }
        }
        return null;
    }

    public static SecurityQueryID get(Vector<?> theList, String name) {
        Enumeration<?> enumer = theList.elements();
        while (enumer.hasMoreElements()) {
            try {
                SecurityQueryID current = (SecurityQueryID) enumer.nextElement();
                if (current.getName().equals(name)) {
                    return current;
                }
            } catch (ClassCastException e) {
                return null;
            }
        }
        return null;
    }

    public static SecurityQueryID valueOf(byte[] passedByteArray) {
        return (SecurityQueryID) get(theList, passedByteArray);
    }

    public static SecurityQueryID[] values() {
        return theList.toArray(new SecurityQueryID[theList.size()]);
    }
}
