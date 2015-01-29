package com.smartdevicelink.test.rpc.notifications;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.OnAudioPassThru;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.utils.Validator;

public class OnAudioPassThruTests extends BaseRpcTests{

    private static final byte[] BULK_DATA = new byte[] { 7, 2, 9, 1, 12, 9, 0, 0, 1, 15, 11, 3 };

    @Override
    protected RPCMessage createMessage(){
        OnAudioPassThru msg = new OnAudioPassThru();

        msg.setBulkData(BULK_DATA);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_NOTIFICATION;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ON_AUDIO_PASS_THRU;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        return new JSONObject();
    }

    public void testBulkData(){
        byte[] copy = ( (OnAudioPassThru) msg ).getBulkData();
        assertNotSame("Bulk data was not defensive copied.", BULK_DATA, copy);
        assertEquals("Bulk data size didn't match expected size.", BULK_DATA.length, copy.length);
        assertTrue("Input value didn't match expected value.", Validator.validateBulkData(BULK_DATA, copy));
    }

    public void testNull(){
        OnAudioPassThru msg = new OnAudioPassThru();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);
    }
}
