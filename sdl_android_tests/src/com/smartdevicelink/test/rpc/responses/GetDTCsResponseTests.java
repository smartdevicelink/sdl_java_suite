package com.smartdevicelink.test.rpc.responses;

import java.util.Hashtable;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.GetDTCsResponse;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.GetDTCsResponse}
 */
public class GetDTCsResponseTests extends BaseRpcTests{
	
    @Override
    protected RPCMessage createMessage(){
        GetDTCsResponse msg = new GetDTCsResponse();

        msg.setDtc(Test.GENERAL_STRING_LIST);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_RESPONSE;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.GET_DTCS.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(GetDTCsResponse.KEY_DTC, JsonUtils.createJsonArray(Test.GENERAL_STRING_LIST));
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
        List<String> cmdId = ( (GetDTCsResponse) msg ).getDtc();

        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_STRING_LIST.size(), cmdId.size());

        for(int i = 0; i < Test.GENERAL_STRING_LIST.size(); i++){
            assertEquals(Test.MATCH, Test.GENERAL_STRING_LIST.get(i), cmdId.get(i));
        }
    
        // Invalid/Null Tests
        GetDTCsResponse msg = new GetDTCsResponse();
        assertNotNull(Test.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(Test.NULL, msg.getDtc());
    }

    /**
     * Tests a valid JSON construction of this RPC message.
     */
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
    	assertNotNull(Test.NOT_NULL, commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			GetDTCsResponse cmd = new GetDTCsResponse(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			
			List<String> dtcList = JsonUtils.readStringListFromJsonObject(parameters, GetDTCsResponse.KEY_DTC);
			List<String> testDtcList = cmd.getDtc();
			assertEquals(Test.MATCH, dtcList.size(), testDtcList.size());
			assertTrue(Test.TRUE, Validator.validateStringList(dtcList, testDtcList));
			
		} catch (JSONException e) {
			e.printStackTrace();
		}    	
    }    
}