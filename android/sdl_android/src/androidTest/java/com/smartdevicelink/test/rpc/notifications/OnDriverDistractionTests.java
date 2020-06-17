package com.smartdevicelink.test.rpc.notifications;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.OnDriverDistraction;
import com.smartdevicelink.proxy.rpc.enums.DriverDistractionState;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.TestValues;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.fail;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.OnDriverDistraction}
 */
public class OnDriverDistractionTests extends BaseRpcTests{

    private static final DriverDistractionState STATUS = DriverDistractionState.DD_ON;

    @Override
    protected RPCMessage createMessage(){
        OnDriverDistraction msg = new OnDriverDistraction();

        msg.setState(STATUS);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_NOTIFICATION;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ON_DRIVER_DISTRACTION.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(OnDriverDistraction.KEY_STATE, STATUS);
        }catch(JSONException e){
        	fail(TestValues.JSON_FAIL);
        }

        return result;
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    @Test
    public void testRpcValues () {       	
    	// Test Values
        DriverDistractionState cmdId = ( (OnDriverDistraction) msg ).getState();
        
        // Valid Tests
        assertEquals(TestValues.MATCH, STATUS, cmdId);
    
        // Invalid/Null Tests
        OnDriverDistraction msg = new OnDriverDistraction();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

         assertNull(TestValues.NULL, msg.getState());
    }
}