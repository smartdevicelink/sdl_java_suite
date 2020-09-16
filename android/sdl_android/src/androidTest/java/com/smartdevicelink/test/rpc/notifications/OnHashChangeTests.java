package com.smartdevicelink.test.rpc.notifications;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.OnHashChange;
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
 * {@link com.smartdevicelink.proxy.rpc.OnHashChange}
 */
public class OnHashChangeTests extends BaseRpcTests{

    private static final String HASH_ID = "agh4lg2hb1g9gq3";

    @Override
    protected RPCMessage createMessage(){
        OnHashChange msg = new OnHashChange();

        msg.setHashID(HASH_ID);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_NOTIFICATION;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ON_HASH_CHANGE.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(OnHashChange.KEY_HASH_ID, HASH_ID);
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
        String hashId = ( (OnHashChange) msg ).getHashID();
        
        // Valid Tests
        assertEquals(TestValues.MATCH, HASH_ID, hashId);
   
        // Invalid/Null Tests
        OnHashChange msg = new OnHashChange();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getHashID());
    }
}