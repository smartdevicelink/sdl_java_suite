package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.ScrollableMessage;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.rpc.ScrollableMessage}
 */
public class ScrollableMessageTests extends BaseRpcTests {
    
	@Override
	protected RPCMessage createMessage() {
		ScrollableMessage msg = new ScrollableMessage();
		
		msg.setTimeout(TestValues.GENERAL_INT);
		msg.setSoftButtons(TestValues.GENERAL_SOFTBUTTON_LIST);
		msg.setScrollableMessageBody(TestValues.GENERAL_STRING);
		msg.setCancelID(TestValues.GENERAL_INTEGER);

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
			result.put(ScrollableMessage.KEY_SCROLLABLE_MESSAGE_BODY, TestValues.GENERAL_STRING);
			result.put(ScrollableMessage.KEY_TIMEOUT, TestValues.GENERAL_INT);
			result.put(ScrollableMessage.KEY_SOFT_BUTTONS, TestValues.JSON_SOFTBUTTONS);
			result.put(ScrollableMessage.KEY_CANCEL_ID, TestValues.GENERAL_INTEGER);
		} catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
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
		Integer testCancelID = ( (ScrollableMessage) msg ).getCancelID();
		
		// Valid Tests
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testBody);
		assertEquals(TestValues.MATCH, (Integer) TestValues.GENERAL_INT, testTimeout);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_SOFTBUTTON_LIST.size(), testSoftButtons.size());
		for (int i = 0; i < TestValues.GENERAL_SOFTBUTTON_LIST.size(); i++) {
			assertEquals(TestValues.MATCH, TestValues.GENERAL_SOFTBUTTON_LIST.get(i), testSoftButtons.get(i));
		}
		assertEquals(TestValues.MATCH, TestValues.GENERAL_INTEGER, testCancelID);

		// Invalid/Null Tests
		ScrollableMessage msg = new ScrollableMessage();
		assertNotNull(TestValues.NOT_NULL, msg);

		testNullBase(msg);

		assertNull(TestValues.NULL, msg.getScrollableMessageBody());
		assertNull(TestValues.NULL, msg.getTimeout());
		assertNull(TestValues.NULL, msg.getSoftButtons());
		assertNull(TestValues.NULL, msg.getCancelID());
	}

    /**
     * Tests a valid JSON construction of this RPC message.
     */
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
    	assertNotNull(TestValues.NOT_NULL, commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			ScrollableMessage cmd = new ScrollableMessage(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(TestValues.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, ScrollableMessage.KEY_SCROLLABLE_MESSAGE_BODY), cmd.getScrollableMessageBody());
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, ScrollableMessage.KEY_TIMEOUT), cmd.getTimeout());
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, ScrollableMessage.KEY_CANCEL_ID), cmd.getCancelID());

			JSONArray softButtonArray = JsonUtils.readJsonArrayFromJsonObject(parameters, ScrollableMessage.KEY_SOFT_BUTTONS);
			List<SoftButton> softButtonList = new ArrayList<SoftButton>();
			for (int index = 0; index < softButtonArray.length(); index++) {
				SoftButton chunk = new SoftButton(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)softButtonArray.get(index)) );
				softButtonList.add(chunk);
			}
			assertTrue(TestValues.TRUE,  Validator.validateSoftButtons(softButtonList, cmd.getSoftButtons()));
		} catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
		}    	
    }	
}