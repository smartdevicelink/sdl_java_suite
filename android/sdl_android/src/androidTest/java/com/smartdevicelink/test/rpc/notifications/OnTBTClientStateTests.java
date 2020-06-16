package com.smartdevicelink.test.rpc.notifications;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.OnTBTClientState;
import com.smartdevicelink.proxy.rpc.enums.TBTState;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.TestValues;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.OnTBTClientState}
 */
public class OnTBTClientStateTests extends BaseRpcTests{

    @Override
    protected RPCMessage createMessage(){
        OnTBTClientState msg = new OnTBTClientState();

        msg.setState(TestValues.GENERAL_TBTSTATE);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_NOTIFICATION;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ON_TBT_CLIENT_STATE.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(OnTBTClientState.KEY_STATE, TestValues.GENERAL_TBTSTATE);
        } catch(JSONException e){
        	fail(TestValues.JSON_FAIL);
        }

        return result;
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {       	
    	// Test Values
        TBTState data = ( (OnTBTClientState) msg ).getState();
        
        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_TBTSTATE, data);
    
        // Invalid/Null Tests
        OnTBTClientState msg = new OnTBTClientState();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getState());
    }
}