package com.smartdevicelink.test.rpc.responses;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.StreamRPCResponse;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Hashtable;
import static androidx.test.platform.app.InstrumentationRegistry.getTargetContext;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;


/**
 * Created by austinkirk on 6/7/17.
 */

public class StreamRPCResponseTests extends BaseRpcTests {
    @Override
    protected RPCMessage createMessage(){
        StreamRPCResponse response = new StreamRPCResponse();
        response.setFileSize((Long) TestValues.GENERAL_LONG);
        response.setFileName(TestValues.GENERAL_STRING);
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
            result.put(StreamRPCResponse.KEY_FILESIZE, (Long) TestValues.GENERAL_LONG);
            result.put(StreamRPCResponse.KEY_FILENAME, TestValues.GENERAL_STRING);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Tests the expected values of the RPC message.
     */
    @Test
    public void testRpcValues () {
        // Invalid/Null Tests
        StreamRPCResponse msg = new StreamRPCResponse();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);
    }

    /**
     * Tests a valid JSON construction of this RPC message.
     */
    @Test
    public void testJsonConstructor () {
        JSONObject commandJson = JsonFileReader.readId(getTargetContext(), getCommandType(), getMessageType());
        assertNotNull(TestValues.NOT_NULL, commandJson);

        try {
            Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
            StreamRPCResponse cmd = new StreamRPCResponse(hash);

            JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
            assertNotNull(TestValues.NOT_NULL, body);

            // Test everything in the json body.
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
            assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

            JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, StreamRPCResponse.KEY_FILENAME), cmd.getFileName());
            assertEquals(TestValues.MATCH, JsonUtils.readLongFromJsonObject(parameters, StreamRPCResponse.KEY_FILESIZE), cmd.getFileSize());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
