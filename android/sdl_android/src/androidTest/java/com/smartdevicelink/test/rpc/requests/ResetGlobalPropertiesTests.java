package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.ResetGlobalProperties;
import com.smartdevicelink.proxy.rpc.enums.GlobalProperty;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Hashtable;
import java.util.List;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.fail;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.rpc.ResetGlobalProperties}
 */
public class ResetGlobalPropertiesTests extends BaseRpcTests {

	@Override
	protected RPCMessage createMessage() {
		ResetGlobalProperties msg = new ResetGlobalProperties();

		msg.setProperties(TestValues.GENERAL_GLOBALPROPERTY_LIST);

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
			result.put(ResetGlobalProperties.KEY_PROPERTIES, JsonUtils.createJsonArray(TestValues.GENERAL_GLOBALPROPERTY_LIST));
		} catch (JSONException e) {
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
		List<GlobalProperty> copy = ( (ResetGlobalProperties) msg ).getProperties();
		
		// Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_GLOBALPROPERTY_LIST.size(), copy.size());
        for(int i = 0; i < TestValues.GENERAL_GLOBALPROPERTY_LIST.size(); i++){
            assertEquals(TestValues.MATCH, TestValues.GENERAL_GLOBALPROPERTY_LIST.get(i), copy.get(i));
        }
	
        // Invalid/Null Tests
		ResetGlobalProperties msg = new ResetGlobalProperties();
		assertNotNull(TestValues.NOT_NULL, msg);
		testNullBase(msg);

		assertNull(TestValues.NULL, msg.getProperties());
	}

    /**
     * Tests a valid JSON construction of this RPC message.
     */
    @Test
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(getInstrumentation().getTargetContext(), getCommandType(), getMessageType());
    	assertNotNull(TestValues.NOT_NULL, commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			ResetGlobalProperties cmd = new ResetGlobalProperties(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(TestValues.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			
			JSONArray propertiesArray = JsonUtils.readJsonArrayFromJsonObject(parameters, ResetGlobalProperties.KEY_PROPERTIES);
			for (int index = 0; index < propertiesArray.length(); index++) {
				GlobalProperty property = GlobalProperty.valueOf(propertiesArray.get(index).toString());
				assertEquals(TestValues.MATCH,  property, cmd.getProperties().get(index));
			}			
		} catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
		}    	
    }
}