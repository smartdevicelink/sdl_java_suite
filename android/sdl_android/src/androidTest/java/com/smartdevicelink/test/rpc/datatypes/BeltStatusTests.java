package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.BeltStatus;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.BeltStatus}
 */
public class BeltStatusTests extends TestCase{
	
    private BeltStatus                          msg;

    @Override
    protected void setUp() throws Exception{
        msg = new BeltStatus();
        assertNotNull(TestValues.NOT_NULL, msg);
        
        msg.setLeftRearInflatableBelted(TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
        msg.setPassengerChildDetected(TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
        msg.setRightRearInflatableBelted(TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
        msg.setDriverBuckleBelted(TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
        msg.setPassengerBuckleBelted(TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
        msg.setRightRow2BuckleBelted(TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
        msg.setDriverBeltDeployed(TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
        msg.setPassengerBeltDeployed(TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
        msg.setMiddleRow1BuckleBelted(TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
        msg.setMiddleRow1BeltDeployed(TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
        msg.setLeftRow2BuckleBelted(TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
        msg.setMiddleRow2BuckleBelted(TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
        msg.setMiddleRow3BuckleBelted(TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
        msg.setLeftRow3BuckleBelted(TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
        msg.setRightRow3BuckleBelted(TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () { 
    	// Valid Tests
        VehicleDataEventStatus leftRearInflatable = msg.getLeftRearInflatableBelted();
        assertEquals(TestValues.MATCH, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS, leftRearInflatable);
   
        VehicleDataEventStatus rightRearInflatable = msg.getRightRearInflatableBelted();
        assertEquals(TestValues.MATCH, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS, rightRearInflatable);
    
        VehicleDataEventStatus passengerChild = msg.getPassengerChildDetected();
        assertEquals(TestValues.MATCH, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS, passengerChild);
   
    	VehicleDataEventStatus driverBuckled = msg.getDriverBuckleBelted();
    	assertEquals(TestValues.MATCH, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS, driverBuckled);
    	driverBuckled = msg.getPassengerBuckleBelted();
    	assertEquals(TestValues.MATCH, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS, driverBuckled);
    	driverBuckled = msg.getRightRow2BuckleBelted();
    	assertEquals(TestValues.MATCH, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS, driverBuckled);
    	driverBuckled = msg.getMiddleRow1BuckleBelted();
    	assertEquals(TestValues.MATCH, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS, driverBuckled);
    	driverBuckled = msg.getLeftRow2BuckleBelted();
    	assertEquals(TestValues.MATCH, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS, driverBuckled);
    	driverBuckled = msg.getMiddleRow2BuckleBelted();
    	assertEquals(TestValues.MATCH, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS, driverBuckled);
    	driverBuckled = msg.getMiddleRow3BuckleBelted();
    	assertEquals(TestValues.MATCH, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS, driverBuckled);
    	driverBuckled = msg.getLeftRow3BuckleBelted();
    	assertEquals(TestValues.MATCH, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS, driverBuckled);
    	driverBuckled = msg.getRightRow3BuckleBelted();
    	assertEquals(TestValues.MATCH, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS, driverBuckled);
    
    	VehicleDataEventStatus driverBeltDeployed = msg.getDriverBeltDeployed();
    	assertEquals(TestValues.MATCH, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS, driverBeltDeployed);
    	driverBeltDeployed = msg.getPassengerBeltDeployed();
    	assertEquals(TestValues.MATCH, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS, driverBeltDeployed);
    	driverBeltDeployed = msg.getMiddleRow1BeltDeployed();
    	assertEquals(TestValues.MATCH, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS, driverBeltDeployed);
    
    	// Invalid/Null Tests
        BeltStatus msg = new BeltStatus();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getLeftRearInflatableBelted());
        assertNull(TestValues.NULL, msg.getRightRearInflatableBelted());
        assertNull(TestValues.NULL, msg.getPassengerChildDetected());
        assertNull(TestValues.NULL, msg.getDriverBuckleBelted());
        assertNull(TestValues.NULL, msg.getPassengerBuckleBelted());
        assertNull(TestValues.NULL, msg.getRightRow2BuckleBelted());
        assertNull(TestValues.NULL, msg.getMiddleRow1BuckleBelted());
        assertNull(TestValues.NULL, msg.getLeftRow2BuckleBelted());
        assertNull(TestValues.NULL, msg.getMiddleRow2BuckleBelted());
        assertNull(TestValues.NULL, msg.getMiddleRow3BuckleBelted());
        assertNull(TestValues.NULL, msg.getLeftRow3BuckleBelted());
        assertNull(TestValues.NULL, msg.getRightRow3BuckleBelted());
        assertNull(TestValues.NULL, msg.getPassengerBeltDeployed());
        assertNull(TestValues.NULL, msg.getMiddleRow1BeltDeployed());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(BeltStatus.KEY_PASSENGER_CHILD_DETECTED, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(BeltStatus.KEY_LEFT_REAR_INFLATABLE_BELTED, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(BeltStatus.KEY_RIGHT_REAR_INFLATABLE_BELTED, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(BeltStatus.KEY_DRIVER_BELT_DEPLOYED, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(BeltStatus.KEY_DRIVER_BUCKLE_BELTED, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(BeltStatus.KEY_PASSENGER_BELT_DEPLOYED, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(BeltStatus.KEY_PASSENGER_BUCKLE_BELTED, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(BeltStatus.KEY_LEFT_ROW_2_BUCKLE_BELTED, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(BeltStatus.KEY_RIGHT_ROW_2_BUCKLE_BELTED, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(BeltStatus.KEY_MIDDLE_ROW_2_BUCKLE_BELTED, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(BeltStatus.KEY_MIDDLE_ROW_3_BUCKLE_BELTED, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(BeltStatus.KEY_LEFT_ROW_3_BUCKLE_BELTED, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(BeltStatus.KEY_RIGHT_ROW_3_BUCKLE_BELTED, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(BeltStatus.KEY_MIDDLE_ROW_1_BELT_DEPLOYED, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);
            reference.put(BeltStatus.KEY_MIDDLE_ROW_1_BUCKLE_BELTED, TestValues.GENERAL_VEHCILEDATAEVENTSTATUS);

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