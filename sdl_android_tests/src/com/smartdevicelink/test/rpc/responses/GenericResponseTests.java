package com.smartdevicelink.test.rpc.responses;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.GenericResponse;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.GenericResponse}
 */
public class GenericResponseTests extends BaseRpcTests{

    @Override
    protected RPCMessage createMessage(){
        return new GenericResponse();
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_RESPONSE;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.GENERIC_RESPONSE.toString();
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
        GenericResponse msg = new GenericResponse();
        assertNotNull(Test.NOT_NULL, msg);
        testNullBase(msg);
    }
}