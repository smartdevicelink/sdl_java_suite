package com.smartdevicelink.test.rpc.requests;

import java.util.Hashtable;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.DiagnosticMessage;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.DiagnosticMessage}
 */
public class DiagnosticMessageTests extends BaseRpcTests{

    @Override
    protected RPCMessage createMessage(){
        DiagnosticMessage msg = new DiagnosticMessage();

        msg.setMessageLength(Test.GENERAL_INT);
        msg.setTargetID(Test.GENERAL_INT);
        msg.setMessageData(Test.GENERAL_INTEGER_LIST);

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
            result.put(DiagnosticMessage.KEY_TARGET_ID, Test.GENERAL_INT);
            result.put(DiagnosticMessage.KEY_MESSAGE_LENGTH, Test.GENERAL_INT);
            result.put(DiagnosticMessage.KEY_MESSAGE_DATA, JsonUtils.createJsonArray(Test.GENERAL_INTEGER_LIST));
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
        int testTargetId = ( (DiagnosticMessage) msg ).getTargetID();
        int testMessageLength = ( (DiagnosticMessage) msg ).getMessageLength();
        List<Integer> testMessageData = ( (DiagnosticMessage) msg ).getMessageData();

        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_INT, testTargetId);
        assertEquals(Test.MATCH, Test.GENERAL_INT, testMessageLength);
        assertEquals(Test.MATCH, Test.GENERAL_INTEGER_LIST.size(), testMessageData.size());

        for(int i = 0; i < testMessageData.size(); i++){
            assertEquals(Test.MATCH, Test.GENERAL_INTEGER_LIST.get(i), testMessageData.get(i));
        }
    
        // Invalid/Null Tests
        DiagnosticMessage msg = new DiagnosticMessage();
        assertNotNull(Test.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(Test.NULL, msg.getTargetID());
        assertNull(Test.NULL, msg.getMessageLength());
        assertNull(Test.NULL, msg.getMessageData());
    }

    /**
     * Tests a valid JSON construction of this RPC message.
     */
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
    	assertNotNull(Test.NOT_NULL, commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			DiagnosticMessage cmd = new DiagnosticMessage(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, DiagnosticMessage.KEY_TARGET_ID), cmd.getTargetID());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, DiagnosticMessage.KEY_MESSAGE_LENGTH), cmd.getMessageLength());
			
			List<Integer> messageDataList = JsonUtils.readIntegerListFromJsonObject(parameters, DiagnosticMessage.KEY_MESSAGE_DATA);
			List<Integer> testDataList = cmd.getMessageData();
			assertEquals(Test.MATCH, messageDataList.size(), testDataList.size());
			assertTrue(Test.TRUE, Validator.validateIntegerList(messageDataList, testDataList));
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}    	
    }
}