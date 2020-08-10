package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.DialNumber;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Hashtable;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.fail;
import static androidx.test.platform.app.InstrumentationRegistry.getTargetContext;

/**
 * Created by austinkirk on 6/6/17.
 */

public class DialNumberTests extends BaseRpcTests {

    private final String TEST_NUMBER = "5558675309";

    @Override
    protected RPCMessage createMessage(){
        DialNumber msg = new DialNumber();

        msg.setNumber(TEST_NUMBER);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_REQUEST;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.DIAL_NUMBER.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(DialNumber.KEY_NUMBER, TEST_NUMBER);
        }catch(JSONException e){
            fail(TestValues.JSON_FAIL);
        }

        return result;
    }

    /**
     * Tests the expected values of the RPC message.
     */
    @Test
    public void testRpcValues () {
        // Test Values
        String testNumber = ((DialNumber) msg).getNumber();

        // Valid Tests
        assertEquals(TestValues.MATCH, TEST_NUMBER, testNumber);
        // Invalid/Null Tests
        DialNumber msg = new DialNumber();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getNumber());
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
            DialNumber cmd = new DialNumber(hash);
            JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
            assertNotNull(TestValues.NOT_NULL, body);

            // Test everything in the json body.
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
            assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

            JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, DialNumber.KEY_NUMBER), cmd.getNumber());
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}
