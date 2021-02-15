package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.ClimateData;
import com.smartdevicelink.proxy.rpc.Temperature;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Iterator;

public class ClimateDataTests extends TestCase {

    private ClimateData msg;

    @Override
    public void setUp() {
        msg = new ClimateData();

        msg.setExternalTemperature(TestValues.GENERAL_TEMPERATURE);
        msg.setCabinTemperature(TestValues.GENERAL_TEMPERATURE);
        msg.setAtmosphericPressure(TestValues.GENERAL_FLOAT);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        // Test Values
        Temperature cabinTemperature = msg.getCabinTemperature();
        Temperature externalTemperature = msg.getExternalTemperature();
        Float atmosphericPressure = msg.getAtmosphericPressure();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_TEMPERATURE, cabinTemperature);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_TEMPERATURE, externalTemperature);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_FLOAT, atmosphericPressure);

        // Invalid/Null Tests
        ClimateData msg = new ClimateData();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getAtmosphericPressure());
        assertNull(TestValues.NULL, msg.getCabinTemperature());
        assertNull(TestValues.NULL, msg.getExternalTemperature());
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(ClimateData.KEY_EXTERNAL_TEMPERATURE, JsonRPCMarshaller.serializeHashtable(TestValues.GENERAL_TEMPERATURE.getStore()));
            reference.put(ClimateData.KEY_CABIN_TEMPERATURE, JsonRPCMarshaller.serializeHashtable(TestValues.GENERAL_TEMPERATURE.getStore()));
            reference.put(ClimateData.KEY_ATMOSPHERIC_PRESSURE, TestValues.GENERAL_FLOAT);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();

                if (key.equals(ClimateData.KEY_CABIN_TEMPERATURE) || key.equals(ClimateData.KEY_EXTERNAL_TEMPERATURE)) {
                    JSONObject objectEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
                    JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
                    Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
                    Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
                    assertTrue(TestValues.TRUE, Validator.validateTemperature(new Temperature(hashReference), new Temperature(hashTest)));
                } else {
                    assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
                }
            }
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}
