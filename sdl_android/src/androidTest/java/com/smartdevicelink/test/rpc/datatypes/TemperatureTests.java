package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.Temperature;
import com.smartdevicelink.proxy.rpc.enums.TemperatureUnit;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.Temperature}
 */
public class TemperatureTests extends TestCase{
	
    private Temperature msg;

    @Override
    public void setUp(){
        msg = new Temperature();

        msg.setUnit(Test.GENERAL_TEMPERATUREUNIT);
        msg.setValue(Test.GENERAL_FLOAT);
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
        // Test Values
        TemperatureUnit unit = msg.getUnit();
        float value = msg.getValue();

        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_FLOAT, value);
        assertEquals(Test.MATCH, Test.GENERAL_TEMPERATUREUNIT, unit);

        // Invalid/Null Tests
        Temperature msg = new Temperature();
        assertNotNull(Test.NOT_NULL, msg);

        assertNull(Test.NULL, msg.getUnit());
        assertNull(Test.NULL, msg.getValue());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(Temperature.KEY_VALUE, (Float) Test.GENERAL_FLOAT);
            reference.put(Temperature.KEY_UNIT, Test.GENERAL_TEMPERATUREUNIT);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(Test.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();

                assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));

            }
        } catch(JSONException e){
        	fail(Test.JSON_FAIL);
        }
    }
}