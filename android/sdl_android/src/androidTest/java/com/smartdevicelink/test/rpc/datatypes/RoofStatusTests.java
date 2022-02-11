package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.Grid;
import com.smartdevicelink.proxy.rpc.RoofStatus;
import com.smartdevicelink.proxy.rpc.WindowState;
import com.smartdevicelink.proxy.rpc.enums.DoorStatusType;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Iterator;

public class RoofStatusTests extends TestCase {

    private RoofStatus msg;

    @Override
    public void setUp() {
        msg = new RoofStatus(TestValues.GENERAL_GRID, TestValues.GENERAL_DOORSTATUSTYPE);
        msg.setState(TestValues.GENERAL_WINDOW_STATE);
    }

    public void testRpcValues() {
        // Test Values
        Grid location = msg.getLocation();
        DoorStatusType status = msg.getStatus();
        WindowState windowState = msg.getState();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_GRID, location);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_DOORSTATUSTYPE, status);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_WINDOW_STATE, windowState);
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(RoofStatus.KEY_STATUS, TestValues.GENERAL_DOORSTATUSTYPE);
            reference.put(RoofStatus.KEY_STATE, TestValues.GENERAL_WINDOW_STATE.serializeJSON());
            reference.put(RoofStatus.KEY_LOCATION, TestValues.JSON_GRID);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                if (key.equals(RoofStatus.KEY_STATE)) {
                    Hashtable<String, Object> hs1 = JsonRPCMarshaller.deserializeJSONObject((JSONObject) JsonUtils.readObjectFromJsonObject(reference, key));
                    Hashtable<String, Object> hs2 = JsonRPCMarshaller.deserializeJSONObject((JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key));

                    assertTrue(Validator.validateWindowStates(new WindowState(hs1), new WindowState(hs2)));
                } else if (key.equals(RoofStatus.KEY_LOCATION)) {
                    Hashtable<String, Object> hs1 = JsonRPCMarshaller.deserializeJSONObject((JSONObject) JsonUtils.readObjectFromJsonObject(reference, key));
                    Hashtable<String, Object> hs2 = JsonRPCMarshaller.deserializeJSONObject((JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key));

                    assertTrue(Validator.validateGrid(new Grid(hs1), new Grid(hs2)));

                } else {
                    assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
                }
            }
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}
