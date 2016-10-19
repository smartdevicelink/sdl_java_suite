package com.smartdevicelink.test.rpc.responses;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.CreateInteractionChoiceSetResponse;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.CreateInteractionChoiceSetResponse}
 */
public class CreateInteractionChoiceSetResponseTests extends BaseRpcTests{

    @Override
    protected RPCMessage createMessage(){
        return new CreateInteractionChoiceSetResponse();
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_RESPONSE;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.CREATE_INTERACTION_CHOICE_SET.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        return new JSONObject();
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {    
    	// Invalid/Null Tests
        CreateInteractionChoiceSetResponse msg = new CreateInteractionChoiceSetResponse();
        assertNotNull(Test.NOT_NULL, msg);

        testNullBase(msg);
    }
}