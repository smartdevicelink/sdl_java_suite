package com.smartdevicelink.test.rpc.responses;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.DiagnosticMessageResponse;
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
import static androidx.test.InstrumentationRegistry.getTargetContext;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.DiagnosticMessageResponse}
 */
public class DiagnosticMessageResponseTests extends BaseRpcTests{

    @Override
    protected RPCMessage createMessage(){
        DiagnosticMessageResponse msg = new DiagnosticMessageResponse();

        msg.setMessageDataResult(TestValues.GENERAL_INTEGER_LIST);

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
                    JsonUtils.createJsonArray(TestValues.GENERAL_INTEGER_LIST));
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
        List<Integer> cmdId = ( (DiagnosticMessageResponse) msg ).getMessageDataResult();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INTEGER_LIST.size(), cmdId.size());

        for(int i = 0; i < TestValues.GENERAL_INTEGER_LIST.size(); i++){
            assertEquals(TestValues.MATCH, TestValues.GENERAL_INTEGER_LIST.get(i), cmdId.get(i));
        }
        
        // Invalid/Null Tests
        DiagnosticMessageResponse msg = new DiagnosticMessageResponse();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getMessageDataResult());
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
			DiagnosticMessageResponse cmd = new DiagnosticMessageResponse(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(TestValues.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			
			List<Integer> dataResultList = JsonUtils.readIntegerListFromJsonObject(parameters, DiagnosticMessageResponse.KEY_MESSAGE_DATA_RESULT);
			List<Integer> testResultList = cmd.getMessageDataResult();
			
			assertEquals(TestValues.MATCH, dataResultList.size(), testResultList.size());
			assertTrue(TestValues.TRUE, Validator.validateIntegerList(dataResultList, testResultList));
		} catch (JSONException e) {
			e.printStackTrace();
		}    	
    }
}