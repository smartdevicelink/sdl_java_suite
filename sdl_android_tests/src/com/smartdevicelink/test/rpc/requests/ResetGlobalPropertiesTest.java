package com.smartdevicelink.test.rpc.requests;

import java.util.ArrayList;
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
import com.smartdevicelink.test.json.rpc.JsonFileReader;
import com.smartdevicelink.test.utils.JsonUtils;

public class ResetGlobalPropertiesTest extends BaseRpcTests {

	private static final List<GlobalProperty> PROPERTIES = new ArrayList<GlobalProperty>();

	@Override
	protected RPCMessage createMessage() {
		PROPERTIES.add(GlobalProperty.VRHELPTITLE);
		PROPERTIES.add(GlobalProperty.MENUICON);
		
		ResetGlobalProperties msg = new ResetGlobalProperties();

		msg.setProperties(PROPERTIES);

		return msg;
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.RESET_GLOBAL_PROPERTIES;
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();

		try {
			result.put(ResetGlobalProperties.KEY_PROPERTIES, JsonUtils.createJsonArray(PROPERTIES));
			
		} catch (JSONException e) {
			/* do nothing */
		}

		return result;
	}

	public void testProperties() {
		List<GlobalProperty> copy = ( (ResetGlobalProperties) msg ).getProperties();
		
        assertNotSame("Variable under test was not defensive copied.", PROPERTIES, copy);
        assertEquals("List size didn't match expected size.", PROPERTIES.size(), copy.size());

        for(int i = 0; i < PROPERTIES.size(); i++){
            assertEquals("Input value didn't match expected value.", PROPERTIES.get(i), copy.get(i));
        }
	}

	public void testNull() {
		ResetGlobalProperties msg = new ResetGlobalProperties();
		assertNotNull("Null object creation failed.", msg);

		testNullBase(msg);

		assertNull("Properties wasn't set, but getter method returned an object.", msg.getProperties());
	}
	
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(getCommandType(), getMessageType());
    	assertNotNull("Command object is null", commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			ResetGlobalProperties cmd = new ResetGlobalProperties(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull("Command type doesn't match expected message type", body);
			
			// test everything in the body
			assertEquals("Command name doesn't match input name", JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals("Correlation ID doesn't match input ID", JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			
			JSONArray propertiesArray = JsonUtils.readJsonArrayFromJsonObject(parameters, ResetGlobalProperties.KEY_PROPERTIES);
			for (int index = 0; index < propertiesArray.length(); index++) {
				GlobalProperty property = GlobalProperty.valueOf(propertiesArray.get(index).toString());
				assertEquals("Global property item doesn't match input property item",  property, cmd.getProperties().get(index));
			}
			
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
    	
    }

}
