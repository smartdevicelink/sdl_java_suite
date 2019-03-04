package com.smartdevicelink.test.rpc.responses;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.StreamRPCResponse;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

/**
 * Created by austinkirk on 6/7/17.
 */

public class StreamRPCResponseTests extends BaseRpcTests {
    @Override
    protected RPCMessage createMessage(){
        StreamRPCResponse response = new StreamRPCResponse();
        response.setFileSize((Long) Test.GENERAL_LONG);
        response.setFileName(Test.GENERAL_STRING);
        return response;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_RESPONSE;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.STREAM_RPC.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try {
            result.put(StreamRPCResponse.KEY_FILESIZE, (Long) Test.GENERAL_LONG);
            result.put(StreamRPCResponse.KEY_FILENAME, Test.GENERAL_STRING);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues () {
        // Invalid/Null Tests
        StreamRPCResponse msg = new StreamRPCResponse();
        assertNotNull(Test.NOT_NULL, msg);
        testNullBase(msg);
    }

    /**
     * Tests a valid JSON construction of this RPC message.
     */
    public void testJsonConstructor () {
        JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
        assertNotNull(Test.NOT_NULL, commandJson);

        try {
            Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
            StreamRPCResponse cmd = new StreamRPCResponse(hash);

            JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
            assertNotNull(Test.NOT_NULL, body);

            // Test everything in the json body.
            assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
            assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

            JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
            assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, StreamRPCResponse.KEY_FILENAME), cmd.getFileName());
            assertEquals(Test.MATCH, JsonUtils.readLongFromJsonObject(parameters, StreamRPCResponse.KEY_FILESIZE), cmd.getFileSize());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
