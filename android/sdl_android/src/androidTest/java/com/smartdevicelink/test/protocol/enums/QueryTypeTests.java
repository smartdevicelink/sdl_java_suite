package com.smartdevicelink.test.protocol.enums;

import com.smartdevicelink.protocol.enums.QueryType;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import java.util.Vector;

public class QueryTypeTests extends TestCase {

    private Vector<QueryType> list = QueryType.getList();

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

            QueryType enumRequest = (QueryType) QueryType.get(list, REQUEST_BYTE);
            QueryType enumResponse = (QueryType) QueryType.get(list, RESPONSE_BYTE);
            QueryType enumNotification = (QueryType) QueryType.get(list, NOTIFICATION_BYTE);
            QueryType enumInvalidQueryType = (QueryType) QueryType.get(list, INVALID_QUERY_TYPE_BYTE);

            assertNotNull("Request byte match returned null", enumRequest);
            assertNotNull("Response byte match returned null", enumResponse);
            assertNotNull("Notification byte match returned null", enumNotification);
            assertNotNull("Invalid Query Type byte match returned null", enumInvalidQueryType);

            enumRequest = (QueryType) QueryType.get(list, REQUEST_STRING);
            enumResponse = (QueryType) QueryType.get(list, RESPONSE_STRING);
            enumNotification = (QueryType) QueryType.get(list, NOTIFICATION_STRING);
            enumInvalidQueryType = (QueryType) QueryType.get(list, INVALID_QUERY_TYPE_STRING);

            assertNotNull("Request string match returned null", enumRequest);
            assertNotNull("Response string match returned null", enumResponse);
            assertNotNull("Notification string match returned null", enumNotification);
            assertNotNull("Invalid Query string byte match returned null", enumInvalidQueryType);


        }catch (NullPointerException exception) {
            fail("Null enum list throws NullPointerException.");
        }
    }

    public void testInvalidEnum() {

        final byte INVALID_BYTE = (byte) 0xAB;
        final String INVALID_STRING = "Invalid";

        try {
            QueryType enumInvalid = (QueryType) QueryType.get(list, INVALID_BYTE);
            assertNull("Invalid byte match didn't return null", enumInvalid);

            enumInvalid = (QueryType) QueryType.get(list, INVALID_STRING);
            assertNull("Invalid string match didn't return null", enumInvalid);
        } catch (IllegalArgumentException exception) {
            fail("Invalid enum throws IllegalArgumentException.");
        }
    }

    public void testNullEnum() {
        try {
            QueryType enumNull = (QueryType) QueryType.get(list, null);
            assertNull("Null lookup returns a value", enumNull);
        } catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
        }
    }

    public void testListEnum() {
        Vector<QueryType> enumTestList = new Vector<>();
        enumTestList.add(QueryType.REQUEST);
        enumTestList.add(QueryType.RESPONSE);
        enumTestList.add(QueryType.NOTIFICATION);
        enumTestList.add(QueryType.INVALID_QUERY_TYPE);

        assertTrue("List does not match enum test list.",
                list.containsAll(enumTestList) &&
                        enumTestList.containsAll(list));

        QueryType[] enumValueArray = QueryType.values();
        QueryType[] enumTestArray = {QueryType.REQUEST, QueryType.RESPONSE, QueryType.NOTIFICATION, QueryType.INVALID_QUERY_TYPE};
        assertTrue("Array does not match enum values array.",
                Validator.validateQueryTypeArray(enumValueArray, enumTestArray));
    }
}
