package com.smartdevicelink.test.rpc.responses;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.SetMediaClockTimerResponse;
import com.smartdevicelink.test.BaseRpcTests;

public class SetMediaClockTimerResponseTest extends BaseRpcTests {

	@Override
    protected RPCMessage createMessage(){
        return new SetMediaClockTimerResponse();
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_RESPONSE;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.SET_MEDIA_CLOCK_TIMER;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        return new JSONObject();
    }

    public void testNull(){
    	SetMediaClockTimerResponse msg = new SetMediaClockTimerResponse();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);
    }

}
