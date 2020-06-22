package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.FuelRange;
import com.smartdevicelink.proxy.rpc.enums.CapacityUnit;
import com.smartdevicelink.proxy.rpc.enums.ComponentVolumeStatus;
import com.smartdevicelink.proxy.rpc.enums.FuelType;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

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

        msg.setType(Test.GENERAL_FUELTYPE);
        msg.setRange(Test.GENERAL_FLOAT);
        msg.setLevel(Test.GENERAL_FLOAT);
        msg.setLevelState(Test.GENERAL_COMPONENTVOLUMESTATUS);
        msg.setCapacity(Test.GENERAL_FLOAT);
        msg.setCapacityUnit(Test.GENERAL_CAPACITYUNIT);
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
        FuelType fuelType = msg.getType();
        float range = msg.getRange();
        float level = msg.getLevel();
        ComponentVolumeStatus levelState = msg.getLevelState();
        CapacityUnit capacityUnit = msg.getCapacityUnit();
        float capacity = msg.getCapacity();

        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_FLOAT, range);
        assertEquals(Test.MATCH, Test.GENERAL_FUELTYPE, fuelType);
        assertEquals(Test.MATCH, Test.GENERAL_FLOAT, level);
        assertEquals(Test.MATCH, Test.GENERAL_COMPONENTVOLUMESTATUS, levelState);
        assertEquals(Test.MATCH, Test.GENERAL_CAPACITYUNIT, capacityUnit);
        assertEquals(Test.MATCH, Test.GENERAL_FLOAT, capacity);
        
        // Invalid/Null Tests
        FuelRange msg = new FuelRange();
        assertNotNull(Test.NOT_NULL, msg);

        assertNull(Test.NULL, msg.getType());
        assertNull(Test.NULL, msg.getRange());
        assertNull(Test.NULL, msg.getLevel());
        assertNull(Test.NULL, msg.getLevelState());
        assertNull(Test.NULL, msg.getCapacityUnit());
        assertNull(Test.NULL, msg.getCapacity());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(FuelRange.KEY_TYPE, Test.GENERAL_FUELTYPE);
            reference.put(FuelRange.KEY_RANGE, (Float) Test.GENERAL_FLOAT);
            reference.put(FuelRange.KEY_LEVEL, Test.GENERAL_FLOAT);
            reference.put(FuelRange.KEY_LEVEL_STATE, Test.GENERAL_COMPONENTVOLUMESTATUS);
            reference.put(FuelRange.KEY_CAPACITY, Test.GENERAL_FLOAT);
            reference.put(FuelRange.KEY_CAPACITY_UNIT, Test.GENERAL_CAPACITYUNIT);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(Test.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()) {
                String key = (String) iterator.next();

                assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
            }
        }catch(JSONException e){
        	fail(Test.JSON_FAIL);
        }
    }
}