package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.FuelRange;
import com.smartdevicelink.proxy.rpc.enums.CapacityUnit;
import com.smartdevicelink.proxy.rpc.enums.ComponentVolumeStatus;
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
        msg.setLevel(TestValues.GENERAL_FLOAT);
        msg.setLevelState(TestValues.GENERAL_COMPONENTVOLUMESTATUS);
        msg.setCapacity(TestValues.GENERAL_FLOAT);
        msg.setCapacityUnit(TestValues.GENERAL_CAPACITYUNIT);
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
        assertEquals(TestValues.MATCH, TestValues.GENERAL_FLOAT, range);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_FUELTYPE, fuelType);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_FLOAT, level);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_COMPONENTVOLUMESTATUS, levelState);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_CAPACITYUNIT, capacityUnit);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_FLOAT, capacity);
        
        // Invalid/Null Tests
        FuelRange msg = new FuelRange();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getType());
        assertNull(TestValues.NULL, msg.getRange());
        assertNull(TestValues.NULL, msg.getLevel());
        assertNull(TestValues.NULL, msg.getLevelState());
        assertNull(TestValues.NULL, msg.getCapacityUnit());
        assertNull(TestValues.NULL, msg.getCapacity());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(FuelRange.KEY_TYPE, TestValues.GENERAL_FUELTYPE);
            reference.put(FuelRange.KEY_RANGE, (Float) TestValues.GENERAL_FLOAT);
            reference.put(FuelRange.KEY_LEVEL, TestValues.GENERAL_FLOAT);
            reference.put(FuelRange.KEY_LEVEL_STATE, TestValues.GENERAL_COMPONENTVOLUMESTATUS);
            reference.put(FuelRange.KEY_CAPACITY, TestValues.GENERAL_FLOAT);
            reference.put(FuelRange.KEY_CAPACITY_UNIT, TestValues.GENERAL_CAPACITYUNIT);

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