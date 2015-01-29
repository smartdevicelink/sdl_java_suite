package com.smartdevicelink.test.rpc.responses;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.EndAudioPassThruResponse;
import com.smartdevicelink.test.BaseRpcTests;

public class EndAudioPassThruResponseTests extends BaseRpcTests{

    @Override
    protected RPCMessage createMessage(){
        return new EndAudioPassThruResponse();
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_RESPONSE;
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
        EndAudioPassThruResponse msg = new EndAudioPassThruResponse();
        assertNotNull("Null object creation failed.", msg);
        
        testNullBase(msg);
    }
}
