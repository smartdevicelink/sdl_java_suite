package com.smartdevicelink.test.rpc.requests;

import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.DiagnosticMessage;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.utils.JsonUtils;

public class DiagnosticMessageTests extends BaseRpcTests{

    private static final int           TARGET_ID      = 48531;
    private static final List<Integer> MESSAGE_DATA   = Arrays.asList(new Integer[] { 1, 1, 2, 3, 5, 8, 13, 21, 34 });
    private static final int           MESSAGE_LENGTH = MESSAGE_DATA.size();

    @Override
    protected RPCMessage createMessage(){
        DiagnosticMessage msg = new DiagnosticMessage();

        msg.setMessageLength(MESSAGE_LENGTH);
        msg.setTargetID(TARGET_ID);
        msg.setMessageData(MESSAGE_DATA);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_REQUEST;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.DIAGNOSTIC_MESSAGE;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(DiagnosticMessage.KEY_TARGET_ID, TARGET_ID);
            result.put(DiagnosticMessage.KEY_MESSAGE_LENGTH, MESSAGE_LENGTH);
            result.put(DiagnosticMessage.KEY_MESSAGE_DATA, JsonUtils.createJsonArray(MESSAGE_DATA));
        }catch(JSONException e){
            /* do nothing */
        }

        return result;
    }

    public void testTargetId(){
        int cmdId = ( (DiagnosticMessage) msg ).getTargetID();
        assertEquals("Target ID didn't match input target ID.", TARGET_ID, cmdId);
    }

    public void testMessageLength(){
        int cmdId = ( (DiagnosticMessage) msg ).getMessageLength();
        assertEquals("Message length didn't match input message length.", MESSAGE_LENGTH, cmdId);
    }

    public void testMessageData(){
        List<Integer> cmdId = ( (DiagnosticMessage) msg ).getMessageData();

        assertNotSame("Message data was not defensive copied.", MESSAGE_DATA, cmdId);
        assertEquals("Message data size didn't match expected size.", MESSAGE_DATA.size(), cmdId.size());

        for(int i = 0; i < cmdId.size(); i++){
            assertEquals("Value didn't match input value.", MESSAGE_DATA.get(i), cmdId.get(i));
        }
    }

    public void testNull(){
        DiagnosticMessage msg = new DiagnosticMessage();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);

        assertNull("Target ID wasn't set, but getter method returned an object.", msg.getTargetID());
        assertNull("Message length wasn't set, but getter method returned an object.", msg.getMessageLength());
        assertNull("Message data wasn't set, but getter method returned an object.", msg.getMessageData());
    }

}
