package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.EmergencyEvent;
import com.smartdevicelink.proxy.rpc.enums.EmergencyEventType;
import com.smartdevicelink.proxy.rpc.enums.FuelCutoffStatus;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.EmergencyEvent}
 */
public class EmergencyEventTests extends TestCase{

    private EmergencyEvent msg;

    @Override
    public void setUp(){
        msg = new EmergencyEvent();

        msg.setEmergencyEventType(Test.GENERAL_EMERGENCYEVENTTYPE);
        msg.setFuelCutoffStatus(Test.GENERAL_FUELCUTOFFSTATUS);
        msg.setMaximumChangeVelocity(Test.GENERAL_INT);
        msg.setMultipleEvents(Test.GENERAL_VEHCILEDATAEVENTSTATUS);
        msg.setRolloverEvent(Test.GENERAL_VEHCILEDATAEVENTSTATUS);
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
        EmergencyEventType type = msg.getEmergencyEventType();
        FuelCutoffStatus cutoffStatus = msg.getFuelCutoffStatus();
        VehicleDataEventStatus multipleEvents = msg.getMultipleEvents();
        int changeVelocity = msg.getMaximumChangeVelocity();
        VehicleDataEventStatus rollover = msg.getRolloverEvent();
        
        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_EMERGENCYEVENTTYPE, type);
        assertEquals(Test.MATCH, Test.GENERAL_FUELCUTOFFSTATUS, cutoffStatus);
        assertEquals(Test.MATCH, Test.GENERAL_INT, changeVelocity);
        assertEquals(Test.MATCH, Test.GENERAL_VEHCILEDATAEVENTSTATUS, multipleEvents);
        assertEquals(Test.MATCH, Test.GENERAL_VEHCILEDATAEVENTSTATUS, rollover);
        
        // Invalid/Null Tests
        EmergencyEvent msg = new EmergencyEvent();
        assertNotNull(Test.NOT_NULL, msg);

        assertNull(Test.NULL, msg.getEmergencyEventType());
        assertNull(Test.NULL, msg.getFuelCutoffStatus());
        assertNull(Test.NULL, msg.getMaximumChangeVelocity());
        assertNull(Test.NULL, msg.getMultipleEvents());
        assertNull(Test.NULL, msg.getRolloverEvent());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(EmergencyEvent.KEY_FUEL_CUTOFF_STATUS, Test.GENERAL_FUELCUTOFFSTATUS);
            reference.put(EmergencyEvent.KEY_MULTIPLE_EVENTS, Test.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(EmergencyEvent.KEY_ROLLOVER_EVENT, Test.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(EmergencyEvent.KEY_EMERGENCY_EVENT_TYPE, Test.GENERAL_EMERGENCYEVENTTYPE);
            reference.put(EmergencyEvent.KEY_MAXIMUM_CHANGE_VELOCITY, Test.GENERAL_INT);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(Test.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
            }
        }catch(JSONException e){
        	fail(Test.JSON_FAIL);
        }
    }
}