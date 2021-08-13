package com.smartdevicelink.test.protocol.enums;

import com.smartdevicelink.protocol.enums.QueryID;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import java.util.Vector;

public class QueryIDTests extends TestCase {

    private Vector<QueryID> list = QueryID.getList();

    public void testValidEnums() {
        final byte[] SEND_HANDSHAKE_DATA_BYTES = {(byte) 0x00, (byte) 0x00, (byte) 0x01};
        final String SEND_HANDSHAKE_DATA_STRING = "SEND_HANDSHAKE_DATA";

        final byte[] SEND_INTERNAL_ERROR_BYTES = {(byte) 0x00, (byte) 0x00, (byte) 0x02};
        final String SEND_INTERNAL_ERROR_STRING = "SEND_INTERNAL_ERROR";

        final byte[] INVALID_QUERY_ID_BYTES = {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
        final String INVALID_QUERY_ID_STRING = "INVALID_QUERY_ID";

        try {
            assertNotNull("QueryID list returned null", list);

            QueryID enumHandshakeData = (QueryID) QueryID.get(list, SEND_HANDSHAKE_DATA_BYTES);
            QueryID enumInternalError = (QueryID) QueryID.get(list, SEND_INTERNAL_ERROR_BYTES);
            QueryID enumInvalidQueryId = (QueryID) QueryID.get(list, INVALID_QUERY_ID_BYTES);

            assertNotNull("Send Handshake Data byte match returned null", enumHandshakeData);
            assertNotNull("Send Internal Error byte match returned null", enumInternalError);
            assertNotNull("Send Invalid QueryID byte match returned null", enumInvalidQueryId);

            enumHandshakeData = (QueryID) QueryID.get(list, SEND_HANDSHAKE_DATA_STRING);
            enumInternalError = (QueryID) QueryID.get(list, SEND_INTERNAL_ERROR_STRING);
            enumInvalidQueryId = (QueryID) QueryID.get(list, INVALID_QUERY_ID_STRING);

            assertNotNull("Send Handshake Data string match returned null", enumHandshakeData);
            assertNotNull("Send Internal Error string match returned null", enumInternalError);
            assertNotNull("Send Invalid QueryID string match returned null", enumInvalidQueryId);
        } catch(NullPointerException exception) {
            fail("Null enum list throws NullPointerException.");
        }
    }

    public void testInvalidEnum() {

        final byte[] INVALID_BYTE_ARRAY = {(byte) 0xAB, (byte) 0xAB, (byte) 0xAB};
        final String INVALID_STRING = "Invalid";

        try {
            QueryID enumInvalid = (QueryID) QueryID.get(list, INVALID_BYTE_ARRAY);
            assertNull("Invalid byte[] match didn't return null", enumInvalid);

            enumInvalid = (QueryID) QueryID.get(list, INVALID_STRING);
            assertNull("Invalid string match didn't return null", enumInvalid);
        } catch (IllegalArgumentException exception) {
            fail("Invalid enum throws IllegalArgumentException.");
        }
    }

    public void testNullEnum() {
        try {
            QueryID enumNull = (QueryID) QueryID.get(list, (String) null);
            assertNull("Null lookup returns a null string value", enumNull);

            enumNull = (QueryID) QueryID.get(list, (byte[]) null);
            assertNull("Null lookup returns a null byte[] value", enumNull);
        }catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
        }
    }

    public void testListEnum() {
        Vector<QueryID> enumTestList = new Vector<>();
        enumTestList.add(QueryID.SEND_HANDSHAKE_DATA);
        enumTestList.add(QueryID.SEND_INTERNAL_ERROR);
        enumTestList.add(QueryID.INVALID_QUERY_ID);

        assertTrue("List does not match enum test list.",
                list.containsAll(enumTestList) &&
                enumTestList.containsAll(list));

        QueryID[] enumValueArray = QueryID.values();
        QueryID[] enumTestArray = {QueryID.SEND_HANDSHAKE_DATA, QueryID.SEND_INTERNAL_ERROR, QueryID.INVALID_QUERY_ID};
        assertTrue("Array does not match enum values array.",
                Validator.validateQueryIDArray(enumValueArray, enumTestArray));
    }

}
