package com.smartdevicelink.test.rpc.responses;

import java.util.Hashtable;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.DiagnosticMessageResponse;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.DiagnosticMessageResponse}
 */
public class DiagnosticMessageResponseTests extends BaseRpcTests{

    @Override
    protected RPCMessage createMessage(){
        DiagnosticMessageResponse msg = new DiagnosticMessageResponse();

        msg.setMessageDataResult(Test.GENERAL_INTEGER_LIST);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_RESPONSE;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.DIAGNOSTIC_MESSAGE.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(DiagnosticMessageResponse.KEY_MESSAGE_DATA_RESULT,
                    JsonUtils.createJsonArray(Test.GENERAL_INTEGER_LIST));
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
        List<Integer> cmdId = ( (DiagnosticMessageResponse) msg ).getMessageDataResult();

        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_INTEGER_LIST.size(), cmdId.size());

        for(int i = 0; i < Test.GENERAL_INTEGER_LIST.size(); i++){
            assertEquals(Test.MATCH, Test.GENERAL_INTEGER_LIST.get(i), cmdId.get(i));
        }
        
        // Invalid/Null Tests
        DiagnosticMessageResponse msg = new DiagnosticMessageResponse();
        assertNotNull(Test.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(Test.NULL, msg.getMessageDataResult());
    }

    /**
     * Tests a valid JSON construction of this RPC message.
     */
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
    	assertNotNull(Test.NOT_NULL, commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			DiagnosticMessageResponse cmd = new DiagnosticMessageResponse(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			
			List<Integer> dataResultList = JsonUtils.readIntegerListFromJsonObject(parameters, DiagnosticMessageResponse.KEY_MESSAGE_DATA_RESULT);
			List<Integer> testResultList = cmd.getMessageDataResult();
			
			assertEquals(Test.MATCH, dataResultList.size(), testResultList.size());
			assertTrue(Test.TRUE, Validator.validateIntegerList(dataResultList, testResultList));
		} catch (JSONException e) {
			e.printStackTrace();
		}    	
    }
}