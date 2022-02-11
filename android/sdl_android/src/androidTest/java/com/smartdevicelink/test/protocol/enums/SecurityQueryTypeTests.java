package com.smartdevicelink.test.protocol.enums;

import com.smartdevicelink.protocol.enums.SecurityQueryType;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import java.util.Vector;

public class SecurityQueryTypeTests extends TestCase {

    private Vector<SecurityQueryType> list = SecurityQueryType.getList();

    public void testValidEnums() {
        final byte REQUEST_BYTE = (byte) 0x00;
        final String REQUEST_STRING = "REQUEST";

        final byte RESPONSE_BYTE = (byte) 0x10;
        final String RESPONSE_STRING = "RESPONSE";

        final byte NOTIFICATION_BYTE = (byte) 0x20;
        final String NOTIFICATION_STRING = "NOTIFICATION";

        final byte INVALID_QUERY_TYPE_BYTE = (byte) 0xFF;
        final String INVALID_QUERY_TYPE_STRING = "INVALID_QUERY_TYPE";

        try {
            assertNotNull("QueryType list returned null", list);

            SecurityQueryType enumRequest = (SecurityQueryType) SecurityQueryType.get(list, REQUEST_BYTE);
            SecurityQueryType enumResponse = (SecurityQueryType) SecurityQueryType.get(list, RESPONSE_BYTE);
            SecurityQueryType enumNotification = (SecurityQueryType) SecurityQueryType.get(list, NOTIFICATION_BYTE);
            SecurityQueryType enumInvalidSecurityQueryType = (SecurityQueryType) SecurityQueryType.get(list, INVALID_QUERY_TYPE_BYTE);

            assertNotNull("Request byte match returned null", enumRequest);
            assertNotNull("Response byte match returned null", enumResponse);
            assertNotNull("Notification byte match returned null", enumNotification);
            assertNotNull("Invalid Query Type byte match returned null", enumInvalidSecurityQueryType);

            enumRequest = (SecurityQueryType) SecurityQueryType.get(list, REQUEST_STRING);
            enumResponse = (SecurityQueryType) SecurityQueryType.get(list, RESPONSE_STRING);
            enumNotification = (SecurityQueryType) SecurityQueryType.get(list, NOTIFICATION_STRING);
            enumInvalidSecurityQueryType = (SecurityQueryType) SecurityQueryType.get(list, INVALID_QUERY_TYPE_STRING);

            assertNotNull("Request string match returned null", enumRequest);
            assertNotNull("Response string match returned null", enumResponse);
            assertNotNull("Notification string match returned null", enumNotification);
            assertNotNull("Invalid Query string byte match returned null", enumInvalidSecurityQueryType);


        } catch (NullPointerException exception) {
            fail("Null enum list throws NullPointerException.");
        }
    }

    public void testInvalidEnum() {

        final byte INVALID_BYTE = (byte) 0xAB;
        final String INVALID_STRING = "Invalid";

        try {
            SecurityQueryType enumInvalid = (SecurityQueryType) SecurityQueryType.get(list, INVALID_BYTE);
            assertNull("Invalid byte match didn't return null", enumInvalid);

            enumInvalid = (SecurityQueryType) SecurityQueryType.get(list, INVALID_STRING);
            assertNull("Invalid string match didn't return null", enumInvalid);
        } catch (IllegalArgumentException exception) {
            fail("Invalid enum throws IllegalArgumentException.");
        }
    }

    public void testNullEnum() {
        try {
            SecurityQueryType enumNull = (SecurityQueryType) SecurityQueryType.get(list, null);
            assertNull("Null lookup returns a value", enumNull);
        } catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
        }
    }

    public void testListEnum() {
        Vector<SecurityQueryType> enumTestList = new Vector<>();
        enumTestList.add(SecurityQueryType.REQUEST);
        enumTestList.add(SecurityQueryType.RESPONSE);
        enumTestList.add(SecurityQueryType.NOTIFICATION);
        enumTestList.add(SecurityQueryType.INVALID_QUERY_TYPE);

        assertTrue("List does not match enum test list.",
                list.containsAll(enumTestList) &&
                        enumTestList.containsAll(list));

        SecurityQueryType[] enumValueArray = SecurityQueryType.values();
        SecurityQueryType[] enumTestArray = {SecurityQueryType.REQUEST, SecurityQueryType.RESPONSE, SecurityQueryType.NOTIFICATION, SecurityQueryType.INVALID_QUERY_TYPE};
        assertTrue("Array does not match enum values array.",
                Validator.validateQueryTypeArray(enumValueArray, enumTestArray));
    }
}
