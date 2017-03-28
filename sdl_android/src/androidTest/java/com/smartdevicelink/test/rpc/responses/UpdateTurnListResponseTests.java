package com.smartdevicelink.test.rpc.responses;

import java.util.Hashtable;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.UpdateTurnListResponse;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.UpdateTurnListResponse}
 */
public class UpdateTurnListResponseTests extends BaseRpcTests {
	
	@Override
    protected RPCMessage createMessage(){
		return new UpdateTurnListResponse();
	}
	
	@Override
    protected String getMessageType(){
        return RPCMessage.KEY_RESPONSE;
    }
	 
	@Override
    protected String getCommandType(){
        return FunctionID.UPDATE_TURN_LIST.toString();
    }
	
	@Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        return new JSONObject();
	}
	
	/**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {   
		// Invalid/Null Tests
		UpdateTurnListResponse msg = new UpdateTurnListResponse();
		assertNotNull(Test.NOT_NULL, msg);
	    testNullBase(msg);
	 }
	 
    /**
	 * Tests the expected values of the RPC message.
	 */
	 public void testJsonConstructor () {
		JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
		assertNotNull(Test.NOT_NULL, commandJson);
		
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			UpdateTurnListResponse cmd = new UpdateTurnListResponse(hash);
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	 }
}