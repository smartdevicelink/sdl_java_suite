package com.smartdevicelink.test.rpc.notifications;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.OnCommand;
import com.smartdevicelink.proxy.rpc.enums.TriggerSource;
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
 * {@link com.smartdevicelink.proxy.rpc.OnCommand}
 */
public class OnCommandTests extends BaseRpcTests{

    @Override
    protected RPCMessage createMessage(){
        OnCommand msg = new OnCommand();

        msg.setCmdID(TestValues.GENERAL_INT);
        msg.setTriggerSource(TestValues.GENERAL_TRIGGERSOURCE);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_NOTIFICATION;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ON_COMMAND.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(OnCommand.KEY_CMD_ID, TestValues.GENERAL_INT);
            result.put(OnCommand.KEY_TRIGGER_SOURCE, TestValues.GENERAL_TRIGGERSOURCE);
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
        int cmdId = ( (OnCommand) msg ).getCmdID();
        TriggerSource triggerSource = ( (OnCommand) msg ).getTriggerSource();
        
        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, cmdId);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_TRIGGERSOURCE, triggerSource);
       
        // Invalid/Null Tests
        OnCommand msg = new OnCommand();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getTriggerSource());
        assertNull(TestValues.NULL, msg.getCmdID());
    }
}