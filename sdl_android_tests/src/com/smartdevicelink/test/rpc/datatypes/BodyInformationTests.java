package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.BodyInformation;
import com.smartdevicelink.proxy.rpc.enums.IgnitionStableStatus;
import com.smartdevicelink.proxy.rpc.enums.IgnitionStatus;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.BodyInformation}
 */
public class BodyInformationTests extends TestCase{
	
    private BodyInformation                   msg;

    @Override
    public void setUp(){
        msg = new BodyInformation();
        msg.setParkBrakeActive(Test.GENERAL_BOOLEAN);
        msg.setIgnitionStatus(Test.GENERAL_IGNITIONSTATUS);
        msg.setIgnitionStableStatus(Test.GENERAL_IGNITIONSTABLESTATUS);

        msg.setDriverDoorAjar(Test.GENERAL_BOOLEAN);
        msg.setPassengerDoorAjar(Test.GENERAL_BOOLEAN);
        msg.setRearLeftDoorAjar(Test.GENERAL_BOOLEAN);
        msg.setRearRightDoorAjar(Test.GENERAL_BOOLEAN);
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {       	
    	// Test Values
        boolean parkBrake = msg.getParkBrakeActive();
        IgnitionStatus ignitionStatus = msg.getIgnitionStatus();
        IgnitionStableStatus ignitionStable = msg.getIgnitionStableStatus();
        
        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, parkBrake);
        assertEquals(Test.MATCH, Test.GENERAL_IGNITIONSTATUS, ignitionStatus);
        assertEquals(Test.MATCH, Test.GENERAL_IGNITIONSTABLESTATUS, ignitionStable);
   
    	assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, (boolean) msg.getDriverDoorAjar());
    	assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, (boolean) msg.getPassengerDoorAjar());
    	assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, (boolean) msg.getRearLeftDoorAjar());
    	assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, (boolean) msg.getRearRightDoorAjar());
    
    	// Invalid/Null Tests
        BodyInformation msg = new BodyInformation();
        assertNotNull(Test.NOT_NULL, msg);

        assertNull(Test.NULL, msg.getParkBrakeActive());
        assertNull(Test.NULL, msg.getIgnitionStatus());
        assertNull(Test.NULL, msg.getIgnitionStatus());
        assertNull(Test.NULL, msg.getDriverDoorAjar());
        assertNull(Test.NULL, msg.getPassengerDoorAjar());
        assertNull(Test.NULL, msg.getRearLeftDoorAjar());
        assertNull(Test.NULL, msg.getRearRightDoorAjar());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(BodyInformation.KEY_PARK_BRAKE_ACTIVE, Test.GENERAL_BOOLEAN);
            reference.put(BodyInformation.KEY_IGNITION_STATUS, Test.GENERAL_IGNITIONSTATUS);
            reference.put(BodyInformation.KEY_IGNITION_STABLE_STATUS, Test.GENERAL_IGNITIONSTABLESTATUS);
            reference.put(BodyInformation.KEY_DRIVER_DOOR_AJAR, Test.GENERAL_BOOLEAN);
            reference.put(BodyInformation.KEY_PASSENGER_DOOR_AJAR, Test.GENERAL_BOOLEAN);
            reference.put(BodyInformation.KEY_REAR_LEFT_DOOR_AJAR, Test.GENERAL_BOOLEAN);
            reference.put(BodyInformation.KEY_REAR_RIGHT_DOOR_AJAR, Test.GENERAL_BOOLEAN);

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