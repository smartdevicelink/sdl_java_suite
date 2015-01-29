package com.smartdevicelink.test.rpc.responses;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.ScrollableMessageResponse;
import com.smartdevicelink.test.BaseRpcTests;

public class ScrollableMessageResponseTest extends BaseRpcTests {

	@Override
    protected RPCMessage createMessage(){
        return new ScrollableMessageResponse();
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_RESPONSE;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.SCROLLABLE_MESSAGE;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        return new JSONObject();
    }

    public void testNull(){
    	ScrollableMessageResponse msg = new ScrollableMessageResponse();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);
    }

}
