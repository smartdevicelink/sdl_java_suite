package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.WindowState;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

public class WindowStateTests extends TestCase {
    private WindowState msg;

    @Override
    protected void setUp() throws Exception {
        msg = new WindowState();
        msg.setApproximatePosition(TestValues.GENERAL_APPROX_POSITION);
        msg.setDeviation(TestValues.GENERAL_DEVIATION);
    }

    public void testRpcValues() {
        Integer approxPosition = msg.getApproximatePosition();
        Integer deviation = msg.getDeviation();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_APPROX_POSITION, approxPosition);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_DEVIATION, deviation);
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(WindowState.KEY_APPROXIMATE_POSITION, TestValues.GENERAL_APPROX_POSITION);
            reference.put(WindowState.KEY_DEVIATION, TestValues.GENERAL_DEVIATION);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            assertTrue(Validator.validateWindowStates(
                    new WindowState(JsonRPCMarshaller.deserializeJSONObject(reference)),
                    new WindowState(JsonRPCMarshaller.deserializeJSONObject(underTest)))
            );

        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}

