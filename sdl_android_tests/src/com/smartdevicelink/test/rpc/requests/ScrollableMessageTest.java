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
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

public class ScrollableMessageTest extends BaseRpcTests {

	private static final String MESSAGE = "message";
	private static final Integer TIMEOUT = 0;
	private final List<SoftButton> SOFT_BUTTON_LIST = new ArrayList<SoftButton>();
	private static final String SOFT_BUTTON_TEXT = "Hello";
	private static final Boolean SOFT_BUTTON_HIGHLIGHTED = true;
    
	@Override
	protected RPCMessage createMessage() {
		ScrollableMessage msg = new ScrollableMessage();

		createCustomObjects();
		
		msg.setTimeout(TIMEOUT);
		msg.setSoftButtons(SOFT_BUTTON_LIST);
		msg.setScrollableMessageBody(MESSAGE);

		return msg;
	}
	
	private void createCustomObjects() {
		SoftButton softButton = new SoftButton();
		softButton.setText(SOFT_BUTTON_TEXT);
		softButton.setIsHighlighted(SOFT_BUTTON_HIGHLIGHTED);
		SOFT_BUTTON_LIST.add(softButton);
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.SCROLLABLE_MESSAGE;
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();

		try {
			result.put(ScrollableMessage.KEY_SCROLLABLE_MESSAGE_BODY, MESSAGE);
			result.put(ScrollableMessage.KEY_TIMEOUT, TIMEOUT);
			
			JSONObject softButtonObj = new JSONObject();
			softButtonObj.put(SoftButton.KEY_TEXT, SOFT_BUTTON_TEXT);
			softButtonObj.put(SoftButton.KEY_IS_HIGHLIGHTED, SOFT_BUTTON_HIGHLIGHTED);
			JSONArray softButtonArray = new JSONArray();
			softButtonArray.put(softButtonObj);
			result.put(ScrollableMessage.KEY_SOFT_BUTTONS, softButtonArray);
			
		} catch (JSONException e) {
			/* do nothing */
		}

		return result;
	}

	public void testMessage () {
		String copy = ( (ScrollableMessage) msg ).getScrollableMessageBody();
		
		assertEquals("Data didn't match input data.", MESSAGE, copy);
	}
	
	public void testTimeout () {
		Integer copy = ( (ScrollableMessage) msg ).getTimeout();
		
		assertEquals("Data didn't match input data.", TIMEOUT, copy);
	}
	
	public void testSoftButton () {
		List<SoftButton> copy = ( (ScrollableMessage) msg ).getSoftButtons();

		assertEquals("List size didn't match expected size.", SOFT_BUTTON_LIST.size(), copy.size());
		
		for (int i = 0; i < SOFT_BUTTON_LIST.size(); i++) {
			assertEquals("Input value didn't match expected value.", SOFT_BUTTON_LIST.get(i), copy.get(i));
		}
	}

	public void testNull() {
		ScrollableMessage msg = new ScrollableMessage();
		assertNotNull("Null object creation failed.", msg);

		testNullBase(msg);

		assertNull("Message wasn't set, but getter method returned an object.", msg.getScrollableMessageBody());
		assertNull("Timeout wasn't set, but getter method returned an object.", msg.getTimeout());
		assertNull("Soft button wasn't set, but getter method returned an object.", msg.getSoftButtons());
	}
	
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(getCommandType(), getMessageType());
    	assertNotNull("Command object is null", commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			ScrollableMessage cmd = new ScrollableMessage(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull("Command type doesn't match expected message type", body);
			
			// test everything in the body
			assertEquals("Command name doesn't match input name", JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals("Correlation ID doesn't match input ID", JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals("Scrollable message body doesn't match input body", 
					JsonUtils.readStringFromJsonObject(parameters, ScrollableMessage.KEY_SCROLLABLE_MESSAGE_BODY), cmd.getScrollableMessageBody());
			assertEquals("Timeout doesn't match input timeout", JsonUtils.readIntegerFromJsonObject(parameters, ScrollableMessage.KEY_TIMEOUT), cmd.getTimeout());

			JSONArray softButtonArray = JsonUtils.readJsonArrayFromJsonObject(parameters, ScrollableMessage.KEY_SOFT_BUTTONS);
			List<SoftButton> softButtonList = new ArrayList<SoftButton>();
			for (int index = 0; index < softButtonArray.length(); index++) {
				SoftButton chunk = new SoftButton(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)softButtonArray.get(index)) );
				softButtonList.add(chunk);
			}
			assertTrue("Soft button list doesn't match input button list",  Validator.validateSoftButtons(softButtonList, cmd.getSoftButtons()));
			
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
    	
    }
	
}
