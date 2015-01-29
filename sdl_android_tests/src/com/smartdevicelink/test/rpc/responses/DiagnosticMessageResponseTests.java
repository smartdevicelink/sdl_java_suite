package com.smartdevicelink.test.rpc.responses;

import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.DiagnosticMessageResponse;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.utils.JsonUtils;

public class DiagnosticMessageResponseTests extends BaseRpcTests{

    private static final List<Integer> MESSAGE_DATA_RESULT = Arrays.asList(new Integer[] { 1, 1, 3, 5, 8, 13, 21, 34,
            55, 89                                        });

    @Override
    protected RPCMessage createMessage(){
        DiagnosticMessageResponse msg = new DiagnosticMessageResponse();

        msg.setMessageDataResult(MESSAGE_DATA_RESULT);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_RESPONSE;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.DIAGNOSTIC_MESSAGE;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(DiagnosticMessageResponse.KEY_MESSAGE_DATA_RESULT,
                    JsonUtils.createJsonArray(MESSAGE_DATA_RESULT));
        }catch(JSONException e){
            /* do nothing */
        }

        return result;
    }

    public void testMessageData(){
        List<Integer> cmdId = ( (DiagnosticMessageResponse) msg ).getMessageDataResult();

        assertNotSame("Message data was not defensive copied.", MESSAGE_DATA_RESULT, cmdId);
        assertEquals("Array size didn't match expected size.", MESSAGE_DATA_RESULT.size(), cmdId.size());

        for(int i = 0; i < MESSAGE_DATA_RESULT.size(); i++){
            assertEquals("Message data didn't match input message data.", MESSAGE_DATA_RESULT.get(i), cmdId.get(i));
        }
    }

    public void testNull(){
        DiagnosticMessageResponse msg = new DiagnosticMessageResponse();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);

        assertNull("Message data wasn't set, but getter method returned an object.", msg.getMessageDataResult());
    }
}
