package com.smartdevicelink.test.rpc.requests;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.ResetGlobalProperties;
import com.smartdevicelink.proxy.rpc.enums.GlobalProperty;
import com.smartdevicelink.test.BaseRpcTests;
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
}
