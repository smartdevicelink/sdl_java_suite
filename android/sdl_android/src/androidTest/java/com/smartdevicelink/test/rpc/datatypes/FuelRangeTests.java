package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.FuelRange;
import com.smartdevicelink.proxy.rpc.enums.FuelType;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.FuelRange}
 */
public class FuelRangeTests extends TestCase{

    private FuelRange msg;

    @Override
    public void setUp(){
        msg = new FuelRange();

        msg.setType(TestValues.GENERAL_FUELTYPE);
        msg.setRange(TestValues.GENERAL_FLOAT);
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
        FuelType fuelType = msg.getType();
        float range = msg.getRange();
        
        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_FLOAT, range);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_FUELTYPE, fuelType);
        
        // Invalid/Null Tests
        FuelRange msg = new FuelRange();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getType());
        assertNull(TestValues.NULL, msg.getRange());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(FuelRange.KEY_TYPE, TestValues.GENERAL_FUELTYPE);
            reference.put(FuelRange.KEY_RANGE, (Float) TestValues.GENERAL_FLOAT);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()) {
                String key = (String) iterator.next();

                assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
            }
        }catch(JSONException e){
        	fail(TestValues.JSON_FAIL);
        }
    }
}