package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.WindowState;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

public class WindowStateTests extends TestCase {
    private WindowState msg;

    @Override
    protected void setUp() throws Exception {
        msg = new WindowState();
        msg.setApproximatePosition(Test.GENERAL_APPROX_POSITION);
        msg.setDeviation(Test.GENERAL_DEVIATION);
    }

    public void testRpcValues() {
        Integer approxPosition = msg.getApproximatePosition();
        Integer deviation = msg.getDeviation();

        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_APPROX_POSITION, approxPosition);
        assertEquals(Test.MATCH, Test.GENERAL_DEVIATION, deviation);
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(WindowState.KEY_APPROXIMATE_POSITION, Test.GENERAL_APPROX_POSITION);
            reference.put(WindowState.KEY_DEVIATION, Test.GENERAL_DEVIATION);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(Test.MATCH, reference.length(), underTest.length());

            assertTrue(Validator.validateWindowStates(
                    new WindowState(JsonRPCMarshaller.deserializeJSONObject(reference)),
                    new WindowState(JsonRPCMarshaller.deserializeJSONObject(underTest)))
            );

        } catch (JSONException e) {
            fail(Test.JSON_FAIL);
        }
    }
}

