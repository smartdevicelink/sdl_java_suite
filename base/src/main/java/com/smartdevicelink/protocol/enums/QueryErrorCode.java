package com.smartdevicelink.protocol.enums;

import androidx.annotation.RestrictTo;

import com.smartdevicelink.util.ByteEnumer;

import java.util.Vector;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public class QueryErrorCode extends ByteEnumer {

    private static final Vector<QueryErrorCode> theList = new Vector<>();

    public static Vector<QueryErrorCode> getList() {
        return theList;
    }

    protected QueryErrorCode(byte value, String name) {
        super(value, name);
    }

    public final static QueryErrorCode ERROR_SUCCESS = new QueryErrorCode((byte) 0x00, "ERROR_SUCCESS");
    public final static QueryErrorCode ERROR_INVALID_QUERY_SIZE = new QueryErrorCode((byte) 0x01, "ERROR_INVALID_QUERY_SIZE");
    public final static QueryErrorCode ERROR_INVALID_QUERY_ID = new QueryErrorCode((byte) 0x02, "ERROR_INVALID_QUERY_ID");
    public final static QueryErrorCode ERROR_NOT_SUPPORTED = new QueryErrorCode((byte) 0x03, "ERROR_NOT_SUPPORTED");
    public final static QueryErrorCode ERROR_SERVICE_ALREADY_PROTECTED = new QueryErrorCode((byte) 0x04, "ERROR_SERVICE_ALREADY_PROTECTED");
    public final static QueryErrorCode ERROR_SERVICE_NOT_PROTECTED = new QueryErrorCode((byte) 0x05, "ERROR_SERVICE_NOT_PROTECTED");
    public final static QueryErrorCode ERROR_DECRYPTION_FAILED = new QueryErrorCode((byte) 0x06, "ERROR_DECRYPTION_FAILED");
    public final static QueryErrorCode ERROR_ENCRYPTION_FAILED = new QueryErrorCode((byte) 0x07, "ERROR_ENCRYPTION_FAILED");
    public final static QueryErrorCode ERROR_SSL_INVALID_DATA = new QueryErrorCode((byte) 0x08, "ERROR_SSL_INVALID_DATA");
    public final static QueryErrorCode ERROR_HANDSHAKE_FAILED = new QueryErrorCode((byte) 0x09, "ERROR_HANDSHAKE_FAILED");
    public final static QueryErrorCode INVALID_CERT = new QueryErrorCode((byte) 0x0A, "INVALID_CERT");
    public final static QueryErrorCode EXPIRED_CERT = new QueryErrorCode((byte) 0x0B, "EXPIRED_CERT");
    public final static QueryErrorCode ERROR_INTERNAL = new QueryErrorCode((byte) 0xFF, "ERROR_INTERNAL");
    public final static QueryErrorCode ERROR_UNKNOWN_INTERNAL_ERROR = new QueryErrorCode((byte) 0xFE, "ERROR_UNKNOWN_INTERNAL_ERROR");

    static {
        theList.addElement(ERROR_SUCCESS);
        theList.addElement(ERROR_INVALID_QUERY_SIZE);
        theList.addElement(ERROR_INVALID_QUERY_ID);
        theList.addElement(ERROR_NOT_SUPPORTED);
        theList.addElement(ERROR_SERVICE_ALREADY_PROTECTED);
        theList.addElement(ERROR_SERVICE_NOT_PROTECTED);
        theList.addElement(ERROR_DECRYPTION_FAILED);
        theList.addElement(ERROR_ENCRYPTION_FAILED);
        theList.addElement(ERROR_SSL_INVALID_DATA);
        theList.addElement(ERROR_HANDSHAKE_FAILED);
        theList.addElement(INVALID_CERT);
        theList.addElement(EXPIRED_CERT);
        theList.addElement(ERROR_INTERNAL);
        theList.addElement(ERROR_UNKNOWN_INTERNAL_ERROR);
    }

    public static QueryErrorCode valueOf(byte passedByte) {
        return (QueryErrorCode) get(theList, passedByte);
    }

    public static QueryErrorCode[] values() {
        return theList.toArray(new QueryErrorCode[theList.size()]);
    }
}
