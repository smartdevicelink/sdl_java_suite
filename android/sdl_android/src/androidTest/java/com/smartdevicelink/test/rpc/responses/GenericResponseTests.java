package com.smartdevicelink.test.rpc.responses;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.GenericResponse;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.TestValues;

import org.json.JSONObject;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.rpc.GenericResponse}
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
    @Test
    public void testRpcValues () {   
    	// Invalid/Null Tests
        GenericResponse msg = new GenericResponse();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);
    }
}