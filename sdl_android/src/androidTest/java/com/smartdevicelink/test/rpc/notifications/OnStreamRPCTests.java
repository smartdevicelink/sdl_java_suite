package com.smartdevicelink.test.rpc.notifications;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.OnStreamRPC;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.Test;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by austinkirk on 6/7/17.
 */

public class OnStreamRPCTests extends BaseRpcTests {
    @Override
    protected RPCMessage createMessage(){
        OnStreamRPC msg = new OnStreamRPC();

        msg.setBytesComplete(Test.GENERAL_LONG);
        msg.setFileName(Test.GENERAL_STRING);
        msg.setFileSize(Test.GENERAL_LONG);

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
            result.put(OnStreamRPC.KEY_BYTESCOMPLETE, Test.GENERAL_LONG);
            result.put(OnStreamRPC.KEY_FILENAME, Test.GENERAL_STRING);
            result.put(OnStreamRPC.KEY_FILESIZE, Test.GENERAL_LONG);
        }catch(JSONException e){
            fail(Test.JSON_FAIL);
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
        assertEquals(Test.MATCH, Test.GENERAL_LONG, bytes);
        assertEquals(Test.MATCH, Test.GENERAL_STRING, fileName);
        assertEquals(Test.MATCH, Test.GENERAL_LONG, fileSize);

        // Invalid/Null Tests
        OnStreamRPC msg = new OnStreamRPC();
        assertNotNull(Test.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(Test.NULL, msg.getBytesComplete());
        assertNull(Test.NULL, msg.getFileName());
        assertNull(Test.NULL, msg.getFileSize());
    }
}
