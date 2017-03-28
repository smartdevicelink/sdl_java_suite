package com.smartdevicelink.test.rpc.requests;

import java.util.Hashtable;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.ResetGlobalProperties;
import com.smartdevicelink.proxy.rpc.enums.GlobalProperty;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.ResetGlobalProperties}
 */
public class ResetGlobalPropertiesTests extends BaseRpcTests {

	@Override
	protected RPCMessage createMessage() {
		ResetGlobalProperties msg = new ResetGlobalProperties();

		msg.setProperties(Test.GENERAL_GLOBALPROPERTY_LIST);

		return msg;
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.RESET_GLOBAL_PROPERTIES.toString();
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();

		try {
			result.put(ResetGlobalProperties.KEY_PROPERTIES, JsonUtils.createJsonArray(Test.GENERAL_GLOBALPROPERTY_LIST));
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}

		return result;
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {    	
    	// Test Values
		List<GlobalProperty> copy = ( (ResetGlobalProperties) msg ).getProperties();
		
		// Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_GLOBALPROPERTY_LIST.size(), copy.size());
        for(int i = 0; i < Test.GENERAL_GLOBALPROPERTY_LIST.size(); i++){
            assertEquals(Test.MATCH, Test.GENERAL_GLOBALPROPERTY_LIST.get(i), copy.get(i));
        }
	
        // Invalid/Null Tests
		ResetGlobalProperties msg = new ResetGlobalProperties();
		assertNotNull(Test.NOT_NULL, msg);
		testNullBase(msg);

		assertNull(Test.NULL, msg.getProperties());
	}

    /**
     * Tests a valid JSON construction of this RPC message.
     */
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
    	assertNotNull(Test.NOT_NULL, commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			ResetGlobalProperties cmd = new ResetGlobalProperties(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			
			JSONArray propertiesArray = JsonUtils.readJsonArrayFromJsonObject(parameters, ResetGlobalProperties.KEY_PROPERTIES);
			for (int index = 0; index < propertiesArray.length(); index++) {
				GlobalProperty property = GlobalProperty.valueOf(propertiesArray.get(index).toString());
				assertEquals(Test.MATCH,  property, cmd.getProperties().get(index));
			}			
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}    	
    }
}