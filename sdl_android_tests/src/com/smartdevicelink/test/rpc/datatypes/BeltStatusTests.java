package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.BeltStatus;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.BeltStatus}
 */
public class BeltStatusTests extends TestCase{
	
    private BeltStatus                          msg;

    @Override
    protected void setUp() throws Exception{
        msg = new BeltStatus();
        assertNotNull(Test.NOT_NULL, msg);
        
        msg.setLeftRearInflatableBelted(Test.GENERAL_VEHCILEDATAEVENTSTATUS);
        msg.setPassengerChildDetected(Test.GENERAL_VEHCILEDATAEVENTSTATUS);
        msg.setRightRearInflatableBelted(Test.GENERAL_VEHCILEDATAEVENTSTATUS);
        msg.setDriverBuckleBelted(Test.GENERAL_VEHCILEDATAEVENTSTATUS);
        msg.setPassengerBuckleBelted(Test.GENERAL_VEHCILEDATAEVENTSTATUS);
        msg.setRightRow2BuckleBelted(Test.GENERAL_VEHCILEDATAEVENTSTATUS);
        msg.setDriverBeltDeployed(Test.GENERAL_VEHCILEDATAEVENTSTATUS);
        msg.setPassengerBeltDeployed(Test.GENERAL_VEHCILEDATAEVENTSTATUS);
        msg.setMiddleRow1BuckleBelted(Test.GENERAL_VEHCILEDATAEVENTSTATUS);
        msg.setMiddleRow1BeltDeployed(Test.GENERAL_VEHCILEDATAEVENTSTATUS);
        msg.setLeftRow2BuckleBelted(Test.GENERAL_VEHCILEDATAEVENTSTATUS);
        msg.setMiddleRow2BuckleBelted(Test.GENERAL_VEHCILEDATAEVENTSTATUS);
        msg.setMiddleRow3BuckleBelted(Test.GENERAL_VEHCILEDATAEVENTSTATUS);
        msg.setLeftRow3BuckleBelted(Test.GENERAL_VEHCILEDATAEVENTSTATUS);
        msg.setRightRow3BuckleBelted(Test.GENERAL_VEHCILEDATAEVENTSTATUS);
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () { 
    	// Valid Tests
        VehicleDataEventStatus leftRearInflatable = msg.getLeftRearInflatableBelted();
        assertEquals(Test.MATCH, Test.GENERAL_VEHCILEDATAEVENTSTATUS, leftRearInflatable);
   
        VehicleDataEventStatus rightRearInflatable = msg.getRightRearInflatableBelted();
        assertEquals(Test.MATCH, Test.GENERAL_VEHCILEDATAEVENTSTATUS, rightRearInflatable);
    
        VehicleDataEventStatus passengerChild = msg.getPassengerChildDetected();
        assertEquals(Test.MATCH, Test.GENERAL_VEHCILEDATAEVENTSTATUS, passengerChild);
   
    	VehicleDataEventStatus driverBuckled = msg.getDriverBuckleBelted();
    	assertEquals(Test.MATCH, Test.GENERAL_VEHCILEDATAEVENTSTATUS, driverBuckled);
    	driverBuckled = msg.getPassengerBuckleBelted();
    	assertEquals(Test.MATCH, Test.GENERAL_VEHCILEDATAEVENTSTATUS, driverBuckled);
    	driverBuckled = msg.getRightRow2BuckleBelted();
    	assertEquals(Test.MATCH, Test.GENERAL_VEHCILEDATAEVENTSTATUS, driverBuckled);
    	driverBuckled = msg.getMiddleRow1BuckleBelted();
    	assertEquals(Test.MATCH, Test.GENERAL_VEHCILEDATAEVENTSTATUS, driverBuckled);
    	driverBuckled = msg.getLeftRow2BuckleBelted();
    	assertEquals(Test.MATCH, Test.GENERAL_VEHCILEDATAEVENTSTATUS, driverBuckled);
    	driverBuckled = msg.getMiddleRow2BuckleBelted();
    	assertEquals(Test.MATCH, Test.GENERAL_VEHCILEDATAEVENTSTATUS, driverBuckled);
    	driverBuckled = msg.getMiddleRow3BuckleBelted();
    	assertEquals(Test.MATCH, Test.GENERAL_VEHCILEDATAEVENTSTATUS, driverBuckled);
    	driverBuckled = msg.getLeftRow3BuckleBelted();
    	assertEquals(Test.MATCH, Test.GENERAL_VEHCILEDATAEVENTSTATUS, driverBuckled);
    	driverBuckled = msg.getRightRow3BuckleBelted();
    	assertEquals(Test.MATCH, Test.GENERAL_VEHCILEDATAEVENTSTATUS, driverBuckled);
    
    	VehicleDataEventStatus driverBeltDeployed = msg.getDriverBeltDeployed();
    	assertEquals(Test.MATCH, Test.GENERAL_VEHCILEDATAEVENTSTATUS, driverBeltDeployed);
    	driverBeltDeployed = msg.getPassengerBeltDeployed();
    	assertEquals(Test.MATCH, Test.GENERAL_VEHCILEDATAEVENTSTATUS, driverBeltDeployed);
    	driverBeltDeployed = msg.getMiddleRow1BeltDeployed();
    	assertEquals(Test.MATCH, Test.GENERAL_VEHCILEDATAEVENTSTATUS, driverBeltDeployed);
    
    	// Invalid/Null Tests
        BeltStatus msg = new BeltStatus();
        assertNotNull(Test.NOT_NULL, msg);

        assertNull(Test.NULL, msg.getLeftRearInflatableBelted());
        assertNull(Test.NULL, msg.getRightRearInflatableBelted());
        assertNull(Test.NULL, msg.getPassengerChildDetected());
        assertNull(Test.NULL, msg.getDriverBuckleBelted());
        assertNull(Test.NULL, msg.getPassengerBuckleBelted());
        assertNull(Test.NULL, msg.getRightRow2BuckleBelted());
        assertNull(Test.NULL, msg.getMiddleRow1BuckleBelted());
        assertNull(Test.NULL, msg.getLeftRow2BuckleBelted());
        assertNull(Test.NULL, msg.getMiddleRow2BuckleBelted());
        assertNull(Test.NULL, msg.getMiddleRow3BuckleBelted());
        assertNull(Test.NULL, msg.getLeftRow3BuckleBelted());
        assertNull(Test.NULL, msg.getRightRow3BuckleBelted());
        assertNull(Test.NULL, msg.getPassengerBeltDeployed());
        assertNull(Test.NULL, msg.getMiddleRow1BeltDeployed());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(BeltStatus.KEY_PASSENGER_CHILD_DETECTED, Test.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(BeltStatus.KEY_REAR_INFLATABLE_BELTED, Test.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(BeltStatus.KEY_RIGHT_REAR_INFLATABLE_BELTED, Test.GENERAL_VEHCILEDATAEVENTSTATUS);            
            reference.put(BeltStatus.KEY_DRIVER_BELT_DEPLOYED, Test.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(BeltStatus.KEY_DRIVER_BUCKLE_BELTED, Test.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(BeltStatus.KEY_PASSENGER_BELT_DEPLOYED, Test.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(BeltStatus.KEY_PASSENGER_BUCKLE_BELTED, Test.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(BeltStatus.KEY_LEFT_ROW_2_BUCKLE_BELTED, Test.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(BeltStatus.KEY_RIGHT_ROW_2_BUCKLE_BELTED, Test.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(BeltStatus.KEY_MIDDLE_ROW_2_BUCKLE_BELTED, Test.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(BeltStatus.KEY_MIDDLE_ROW_3_BUCKLE_BELTED, Test.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(BeltStatus.KEY_LEFT_ROW_3_BUCKLE_BELTED, Test.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(BeltStatus.KEY_RIGHT_ROW_3_BUCKLE_BELTED, Test.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(BeltStatus.KEY_MIDDLE_ROW_1_BELT_DEPLOYED, Test.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(BeltStatus.KEY_MIDDLE_ROW_1_BUCKLE_BELTED, Test.GENERAL_VEHCILEDATAEVENTSTATUS);

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