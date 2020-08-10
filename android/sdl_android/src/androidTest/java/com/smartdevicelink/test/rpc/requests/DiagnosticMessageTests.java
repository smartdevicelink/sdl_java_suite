package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.DiagnosticMessage;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Hashtable;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static androidx.test.InstrumentationRegistry.getTargetContext;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.DiagnosticMessage}
 */
public class DiagnosticMessageTests extends BaseRpcTests{

    @Override
    protected RPCMessage createMessage(){
        DiagnosticMessage msg = new DiagnosticMessage();

        msg.setMessageLength(TestValues.GENERAL_INT);
        msg.setTargetID(TestValues.GENERAL_INT);
        msg.setMessageData(TestValues.GENERAL_INTEGER_LIST);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_REQUEST;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.DIAGNOSTIC_MESSAGE.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(DiagnosticMessage.KEY_TARGET_ID, TestValues.GENERAL_INT);
            result.put(DiagnosticMessage.KEY_MESSAGE_LENGTH, TestValues.GENERAL_INT);
            result.put(DiagnosticMessage.KEY_MESSAGE_DATA, JsonUtils.createJsonArray(TestValues.GENERAL_INTEGER_LIST));
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
        int testTargetId = ( (DiagnosticMessage) msg ).getTargetID();
        int testMessageLength = ( (DiagnosticMessage) msg ).getMessageLength();
        List<Integer> testMessageData = ( (DiagnosticMessage) msg ).getMessageData();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, testTargetId);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, testMessageLength);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INTEGER_LIST.size(), testMessageData.size());

        for(int i = 0; i < testMessageData.size(); i++){
            assertEquals(TestValues.MATCH, TestValues.GENERAL_INTEGER_LIST.get(i), testMessageData.get(i));
        }
    
        // Invalid/Null Tests
        DiagnosticMessage msg = new DiagnosticMessage();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getTargetID());
        assertNull(TestValues.NULL, msg.getMessageLength());
        assertNull(TestValues.NULL, msg.getMessageData());
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
			DiagnosticMessage cmd = new DiagnosticMessage(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(TestValues.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, DiagnosticMessage.KEY_TARGET_ID), cmd.getTargetID());
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, DiagnosticMessage.KEY_MESSAGE_LENGTH), cmd.getMessageLength());
			
			List<Integer> messageDataList = JsonUtils.readIntegerListFromJsonObject(parameters, DiagnosticMessage.KEY_MESSAGE_DATA);
			List<Integer> testDataList = cmd.getMessageData();
			assertEquals(TestValues.MATCH, messageDataList.size(), testDataList.size());
			assertTrue(TestValues.TRUE, Validator.validateIntegerList(messageDataList, testDataList));
		} catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
		}    	
    }
}