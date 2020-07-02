package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.StabilityControlsStatus;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataStatus;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class StabilityControlsStatusTests extends TestCase {
    private StabilityControlsStatus msg;

    @Override
    public void setUp() {
        msg = new StabilityControlsStatus();

        msg.setEscSystem(Test.GENERAL_ESC_SYSTEM);
        msg.setTrailerSwayControl(Test.GENERAL_S_WAY_CONTROL);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues () {
        // Test Values
        VehicleDataStatus esc = msg.getEscSystem();
        VehicleDataStatus sTrailer = msg.getTrailerSWayControl();

        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_ESC_SYSTEM, esc);
        assertEquals(Test.MATCH, Test.GENERAL_S_WAY_CONTROL, sTrailer);
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(StabilityControlsStatus.KEY_TRAILER_SWAY_CONTROL, Test.GENERAL_S_WAY_CONTROL);
            reference.put(StabilityControlsStatus.KEY_ESC_SYSTEM, Test.GENERAL_ESC_SYSTEM);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(Test.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
            }
        } catch (JSONException e) {
            fail(Test.JSON_FAIL);
        }
    }
}