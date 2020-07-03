package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.BodyInformation;
import com.smartdevicelink.proxy.rpc.enums.IgnitionStableStatus;
import com.smartdevicelink.proxy.rpc.enums.IgnitionStatus;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.BodyInformation}
 */
public class BodyInformationTests extends TestCase{
	
    private BodyInformation                   msg;

    @Override
    public void setUp(){
        msg = new BodyInformation();
        msg.setParkBrakeActive(TestValues.GENERAL_BOOLEAN);
        msg.setIgnitionStatus(TestValues.GENERAL_IGNITIONSTATUS);
        msg.setIgnitionStableStatus(TestValues.GENERAL_IGNITIONSTABLESTATUS);

        msg.setDriverDoorAjar(TestValues.GENERAL_BOOLEAN);
        msg.setPassengerDoorAjar(TestValues.GENERAL_BOOLEAN);
        msg.setRearLeftDoorAjar(TestValues.GENERAL_BOOLEAN);
        msg.setRearRightDoorAjar(TestValues.GENERAL_BOOLEAN);
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
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, parkBrake);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_IGNITIONSTATUS, ignitionStatus);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_IGNITIONSTABLESTATUS, ignitionStable);
   
    	assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, (boolean) msg.getDriverDoorAjar());
    	assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, (boolean) msg.getPassengerDoorAjar());
    	assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, (boolean) msg.getRearLeftDoorAjar());
    	assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, (boolean) msg.getRearRightDoorAjar());
    
    	// Invalid/Null Tests
        BodyInformation msg = new BodyInformation();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getParkBrakeActive());
        assertNull(TestValues.NULL, msg.getIgnitionStatus());
        assertNull(TestValues.NULL, msg.getIgnitionStatus());
        assertNull(TestValues.NULL, msg.getDriverDoorAjar());
        assertNull(TestValues.NULL, msg.getPassengerDoorAjar());
        assertNull(TestValues.NULL, msg.getRearLeftDoorAjar());
        assertNull(TestValues.NULL, msg.getRearRightDoorAjar());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(BodyInformation.KEY_PARK_BRAKE_ACTIVE, TestValues.GENERAL_BOOLEAN);
            reference.put(BodyInformation.KEY_IGNITION_STATUS, TestValues.GENERAL_IGNITIONSTATUS);
            reference.put(BodyInformation.KEY_IGNITION_STABLE_STATUS, TestValues.GENERAL_IGNITIONSTABLESTATUS);
            reference.put(BodyInformation.KEY_DRIVER_DOOR_AJAR, TestValues.GENERAL_BOOLEAN);
            reference.put(BodyInformation.KEY_PASSENGER_DOOR_AJAR, TestValues.GENERAL_BOOLEAN);
            reference.put(BodyInformation.KEY_REAR_LEFT_DOOR_AJAR, TestValues.GENERAL_BOOLEAN);
            reference.put(BodyInformation.KEY_REAR_RIGHT_DOOR_AJAR, TestValues.GENERAL_BOOLEAN);

            JSONObject underTest = msg.serializeJSON();            
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
            }
        }catch(JSONException e){
        	fail(TestValues.JSON_FAIL);
        }
    }
}