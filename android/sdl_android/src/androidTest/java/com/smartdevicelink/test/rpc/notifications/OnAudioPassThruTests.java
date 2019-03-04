package com.smartdevicelink.test.rpc.notifications;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.OnAudioPassThru;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.OnAudioPassThru}
 */
public class OnAudioPassThruTests extends BaseRpcTests{

    @Override
    protected RPCMessage createMessage(){
        return new OnAudioPassThru();
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_NOTIFICATION;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ON_AUDIO_PASS_THRU.toString();
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
        OnAudioPassThru msg = new OnAudioPassThru();
        assertNotNull(Test.NOT_NULL, msg);
        testNullBase(msg);
    }
}