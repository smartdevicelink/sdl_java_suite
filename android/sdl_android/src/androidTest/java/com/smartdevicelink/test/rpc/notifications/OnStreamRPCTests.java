package com.smartdevicelink.test.rpc.notifications;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.OnStreamRPC;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.TestValues;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by austinkirk on 6/7/17.
 */

public class OnStreamRPCTests extends BaseRpcTests {
    @Override
    protected RPCMessage createMessage(){
        OnStreamRPC msg = new OnStreamRPC();

        msg.setBytesComplete(TestValues.GENERAL_LONG);
        msg.setFileName(TestValues.GENERAL_STRING);
        msg.setFileSize(TestValues.GENERAL_LONG);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_NOTIFICATION;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ON_STREAM_RPC.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(OnStreamRPC.KEY_BYTESCOMPLETE, TestValues.GENERAL_LONG);
            result.put(OnStreamRPC.KEY_FILENAME, TestValues.GENERAL_STRING);
            result.put(OnStreamRPC.KEY_FILESIZE, TestValues.GENERAL_LONG);
        }catch(JSONException e){
            fail(TestValues.JSON_FAIL);
        }

        return result;
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues () {
        // Test Values
        Long bytes = ((OnStreamRPC) msg).getBytesComplete();
        String fileName = ((OnStreamRPC) msg).getFileName();
        Long fileSize = ((OnStreamRPC) msg).getFileSize();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_LONG, bytes);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, fileName);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_LONG, fileSize);

        // Invalid/Null Tests
        OnStreamRPC msg = new OnStreamRPC();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getBytesComplete());
        assertNull(TestValues.NULL, msg.getFileName());
        assertNull(TestValues.NULL, msg.getFileSize());
    }
}
