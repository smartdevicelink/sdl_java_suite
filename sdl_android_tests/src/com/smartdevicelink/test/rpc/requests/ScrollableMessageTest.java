package com.smartdevicelink.test.rpc.requests;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.ScrollableMessage;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.utils.JsonUtils;

public class ScrollableMessageTest extends BaseRpcTests {

	private static final String MESSAGE = "message";
	private static final Integer TIMEOUT = 0;
	private static final List<SoftButton> SOFT_BUTTON_LIST = new ArrayList<SoftButton>();
	private static final String SOFT_BUTTON_TEXT = "Hello";
	private static final Boolean SOFT_BUTTON_HIGHLIGHTED = true;
    
	@Override
	protected RPCMessage createMessage() {
		SoftButton softButton = new SoftButton();
		softButton.setText(SOFT_BUTTON_TEXT);
		softButton.setIsHighlighted(SOFT_BUTTON_HIGHLIGHTED);
		SOFT_BUTTON_LIST.add(softButton);
		
		ScrollableMessage msg = new ScrollableMessage();

		msg.setTimeout(TIMEOUT);
		msg.setSoftButtons(SOFT_BUTTON_LIST);
		msg.setScrollableMessageBody(MESSAGE);

		return msg;
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
			result.put(ScrollableMessage.KEY_SOFT_BUTTONS, JsonUtils.createJsonArray(SOFT_BUTTON_LIST));
			
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

		assertNotSame("Variable under test was not defensive copied.", SOFT_BUTTON_LIST, copy);
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
}
