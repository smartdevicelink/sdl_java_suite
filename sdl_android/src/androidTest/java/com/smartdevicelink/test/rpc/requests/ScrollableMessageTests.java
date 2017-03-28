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
import com.smartdevicelink.proxy.rpc.ScrollableMessage;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.ScrollableMessage}
 */
public class ScrollableMessageTests extends BaseRpcTests {
    
	@Override
	protected RPCMessage createMessage() {
		ScrollableMessage msg = new ScrollableMessage();
		
		msg.setTimeout(Test.GENERAL_INT);
		msg.setSoftButtons(Test.GENERAL_SOFTBUTTON_LIST);
		msg.setScrollableMessageBody(Test.GENERAL_STRING);

		return msg;
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.SCROLLABLE_MESSAGE.toString();
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();

		try {
			result.put(ScrollableMessage.KEY_SCROLLABLE_MESSAGE_BODY, Test.GENERAL_STRING);
			result.put(ScrollableMessage.KEY_TIMEOUT, Test.GENERAL_INT);			
			result.put(ScrollableMessage.KEY_SOFT_BUTTONS, Test.JSON_SOFTBUTTONS);			
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
		String  testBody    = ( (ScrollableMessage) msg ).getScrollableMessageBody();
		Integer testTimeout = ( (ScrollableMessage) msg ).getTimeout();
		List<SoftButton> testSoftButtons = ( (ScrollableMessage) msg ).getSoftButtons();
		
		// Valid Tests
		assertEquals(Test.MATCH, Test.GENERAL_STRING, testBody);
		assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, testTimeout);
		assertEquals(Test.MATCH, Test.GENERAL_SOFTBUTTON_LIST.size(), testSoftButtons.size());
		for (int i = 0; i < Test.GENERAL_SOFTBUTTON_LIST.size(); i++) {
			assertEquals(Test.MATCH, Test.GENERAL_SOFTBUTTON_LIST.get(i), testSoftButtons.get(i));
		}
		
		// Invalid/Null Tests
		ScrollableMessage msg = new ScrollableMessage();
		assertNotNull(Test.NOT_NULL, msg);

		testNullBase(msg);

		assertNull(Test.NULL, msg.getScrollableMessageBody());
		assertNull(Test.NULL, msg.getTimeout());
		assertNull(Test.NULL, msg.getSoftButtons());
	}

    /**
     * Tests a valid JSON construction of this RPC message.
     */
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
    	assertNotNull(Test.NOT_NULL, commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			ScrollableMessage cmd = new ScrollableMessage(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, ScrollableMessage.KEY_SCROLLABLE_MESSAGE_BODY), cmd.getScrollableMessageBody());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, ScrollableMessage.KEY_TIMEOUT), cmd.getTimeout());

			JSONArray softButtonArray = JsonUtils.readJsonArrayFromJsonObject(parameters, ScrollableMessage.KEY_SOFT_BUTTONS);
			List<SoftButton> softButtonList = new ArrayList<SoftButton>();
			for (int index = 0; index < softButtonArray.length(); index++) {
				SoftButton chunk = new SoftButton(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)softButtonArray.get(index)) );
				softButtonList.add(chunk);
			}
			assertTrue(Test.TRUE,  Validator.validateSoftButtons(softButtonList, cmd.getSoftButtons()));
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}    	
    }	
}