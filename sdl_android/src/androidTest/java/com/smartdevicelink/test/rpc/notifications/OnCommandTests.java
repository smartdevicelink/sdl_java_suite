package com.smartdevicelink.test.rpc.notifications;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.OnCommand;
import com.smartdevicelink.proxy.rpc.enums.TriggerSource;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.OnCommand}
 */
public class OnCommandTests extends BaseRpcTests{

    @Override
    protected RPCMessage createMessage(){
        OnCommand msg = new OnCommand();

        msg.setCmdID(Test.GENERAL_INT);
        msg.setTriggerSource(Test.GENERAL_TRIGGERSOURCE);

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
            result.put(OnCommand.KEY_CMD_ID, Test.GENERAL_INT);
            result.put(OnCommand.KEY_TRIGGER_SOURCE, Test.GENERAL_TRIGGERSOURCE);
        }catch(JSONException e){
        	fail(Test.JSON_FAIL);
        }

        return result;
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {       	
    	// Test Values
        int cmdId = ( (OnCommand) msg ).getCmdID();
        TriggerSource triggerSource = ( (OnCommand) msg ).getTriggerSource();
        
        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_INT, cmdId);
        assertEquals(Test.MATCH, Test.GENERAL_TRIGGERSOURCE, triggerSource);
       
        // Invalid/Null Tests
        OnCommand msg = new OnCommand();
        assertNotNull(Test.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(Test.NULL, msg.getTriggerSource());
        assertNull(Test.NULL, msg.getCmdID());
    }
}