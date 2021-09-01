package com.smartdevicelink.test.protocol.enums;

import com.smartdevicelink.protocol.enums.SecurityQueryID;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import java.util.Vector;

public class SecurityQueryIDTests extends TestCase {

    private Vector<SecurityQueryID> list = SecurityQueryID.getList();

    public void testValidEnums() {
        final byte[] SEND_HANDSHAKE_DATA_BYTES = {(byte) 0x00, (byte) 0x00, (byte) 0x01};
        final String SEND_HANDSHAKE_DATA_STRING = "SEND_HANDSHAKE_DATA";

        final byte[] SEND_INTERNAL_ERROR_BYTES = {(byte) 0x00, (byte) 0x00, (byte) 0x02};
        final String SEND_INTERNAL_ERROR_STRING = "SEND_INTERNAL_ERROR";

        final byte[] INVALID_QUERY_ID_BYTES = {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
        final String INVALID_QUERY_ID_STRING = "INVALID_QUERY_ID";

        try {
            assertNotNull("QueryID list returned null", list);

            SecurityQueryID enumHandshakeData = (SecurityQueryID) SecurityQueryID.get(list, SEND_HANDSHAKE_DATA_BYTES);
            SecurityQueryID enumInternalError = (SecurityQueryID) SecurityQueryID.get(list, SEND_INTERNAL_ERROR_BYTES);
            SecurityQueryID enumInvalidSecurityQueryId = (SecurityQueryID) SecurityQueryID.get(list, INVALID_QUERY_ID_BYTES);

            assertNotNull("Send Handshake Data byte match returned null", enumHandshakeData);
            assertNotNull("Send Internal Error byte match returned null", enumInternalError);
            assertNotNull("Send Invalid QueryID byte match returned null", enumInvalidSecurityQueryId);

            enumHandshakeData = (SecurityQueryID) SecurityQueryID.get(list, SEND_HANDSHAKE_DATA_STRING);
            enumInternalError = (SecurityQueryID) SecurityQueryID.get(list, SEND_INTERNAL_ERROR_STRING);
            enumInvalidSecurityQueryId = (SecurityQueryID) SecurityQueryID.get(list, INVALID_QUERY_ID_STRING);

            assertNotNull("Send Handshake Data string match returned null", enumHandshakeData);
            assertNotNull("Send Internal Error string match returned null", enumInternalError);
            assertNotNull("Send Invalid QueryID string match returned null", enumInvalidSecurityQueryId);
        } catch(NullPointerException exception) {
            fail("Null enum list throws NullPointerException.");
        }
    }

    public void testInvalidEnum() {

        final byte[] INVALID_BYTE_ARRAY = {(byte) 0xAB, (byte) 0xAB, (byte) 0xAB};
        final String INVALID_STRING = "Invalid";

        try {
            SecurityQueryID enumInvalid = (SecurityQueryID) SecurityQueryID.get(list, INVALID_BYTE_ARRAY);
            assertNull("Invalid byte[] match didn't return null", enumInvalid);

            enumInvalid = (SecurityQueryID) SecurityQueryID.get(list, INVALID_STRING);
            assertNull("Invalid string match didn't return null", enumInvalid);
        } catch (IllegalArgumentException exception) {
            fail("Invalid enum throws IllegalArgumentException.");
        }
    }

    public void testNullEnum() {
        try {
            SecurityQueryID enumNull = (SecurityQueryID) SecurityQueryID.get(list, (String) null);
            assertNull("Null lookup returns a null string value", enumNull);

            enumNull = (SecurityQueryID) SecurityQueryID.get(list, (byte[]) null);
            assertNull("Null lookup returns a null byte[] value", enumNull);
        }catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
        }
    }

    public void testListEnum() {
        Vector<SecurityQueryID> enumTestList = new Vector<>();
        enumTestList.add(SecurityQueryID.SEND_HANDSHAKE_DATA);
        enumTestList.add(SecurityQueryID.SEND_INTERNAL_ERROR);
        enumTestList.add(SecurityQueryID.INVALID_QUERY_ID);

        assertTrue("List does not match enum test list.",
                list.containsAll(enumTestList) &&
                enumTestList.containsAll(list));

        SecurityQueryID[] enumValueArray = SecurityQueryID.values();
        SecurityQueryID[] enumTestArray = {SecurityQueryID.SEND_HANDSHAKE_DATA, SecurityQueryID.SEND_INTERNAL_ERROR, SecurityQueryID.INVALID_QUERY_ID};
        assertTrue("Array does not match enum values array.",
                Validator.validateQueryIDArray(enumValueArray, enumTestArray));
    }

}
