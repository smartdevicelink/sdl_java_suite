package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.Temperature;
import com.smartdevicelink.proxy.rpc.enums.TemperatureUnit;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.rpc.Temperature}
 */
public class TemperatureTests extends TestCase{
	
    private Temperature msg;

    @Override
    public void setUp(){
        msg = new Temperature();

        msg.setUnit(TestValues.GENERAL_TEMPERATUREUNIT);
        msg.setValue(TestValues.GENERAL_FLOAT);
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
        // Test Values
        TemperatureUnit unit = msg.getUnit();
        float value = msg.getValue();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_FLOAT, value);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_TEMPERATUREUNIT, unit);

        // Invalid/Null Tests
        Temperature msg = new Temperature();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getUnit());
        assertNull(TestValues.NULL, msg.getValue());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(Temperature.KEY_VALUE, (Float) TestValues.GENERAL_FLOAT);
            reference.put(Temperature.KEY_UNIT, TestValues.GENERAL_TEMPERATUREUNIT);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();

                assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));

            }
        } catch(JSONException e){
        	fail(TestValues.JSON_FAIL);
        }
    }
}