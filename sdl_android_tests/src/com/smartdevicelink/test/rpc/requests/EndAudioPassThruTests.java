package com.smartdevicelink.test.rpc.requests;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.EndAudioPassThru;
import com.smartdevicelink.test.BaseRpcTests;

public class EndAudioPassThruTests extends BaseRpcTests{

    @Override
    protected RPCMessage createMessage(){
        return new EndAudioPassThru();
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_REQUEST;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.END_AUDIO_PASS_THRU;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        return new JSONObject();
    }

    public void testNull(){
        EndAudioPassThru msg = new EndAudioPassThru();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);
    }
}
