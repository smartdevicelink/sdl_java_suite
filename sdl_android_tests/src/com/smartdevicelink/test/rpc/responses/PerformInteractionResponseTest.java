package com.smartdevicelink.test.rpc.responses;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.PerformInteractionResponse;
import com.smartdevicelink.proxy.rpc.enums.TriggerSource;
import com.smartdevicelink.test.BaseRpcTests;

public class PerformInteractionResponseTest extends BaseRpcTests {

	private static final Integer       CHOICE_ID         = -1;
	private static final TriggerSource TRIGGER_SOURCE    = TriggerSource.TS_VR;
	private static final String        MANUAL_TEXT_ENTRY = "manualTextEntry";
    
	@Override
	protected RPCMessage createMessage() {
		PerformInteractionResponse msg = new PerformInteractionResponse();

		msg.setChoiceID(CHOICE_ID);
		msg.setTriggerSource(TRIGGER_SOURCE);
		msg.setManualTextEntry(MANUAL_TEXT_ENTRY);

		return msg;
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_RESPONSE;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.PERFORM_INTERACTION;
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();

		try {
			result.put(PerformInteractionResponse.KEY_CHOICE_ID, CHOICE_ID);
			result.put(PerformInteractionResponse.KEY_TRIGGER_SOURCE, TRIGGER_SOURCE);
			result.put(PerformInteractionResponse.KEY_MANUAL_TEXT_ENTRY, MANUAL_TEXT_ENTRY);
			
		} catch (JSONException e) {
			/* do nothing */
		}

		return result;
	}

	public void testChoiceId() {
		Integer copy = ( (PerformInteractionResponse) msg).getChoiceID();
		
		
		assertEquals("Choice id didn't match input data.", CHOICE_ID, copy);
	}
	
	public void testTriggerSource () {
		TriggerSource copy = ( (PerformInteractionResponse) msg).getTriggerSource();
		
		assertEquals("Trigger source didn't match input data.", TRIGGER_SOURCE, copy);
	}
	
	public void testManualTextEntry () {
		String copy = ( (PerformInteractionResponse) msg).getManualTextEntry();
		
		assertEquals("Manual text entry didn't match input data.", MANUAL_TEXT_ENTRY, copy);
	}

	public void testNull() {
		PerformInteractionResponse msg = new PerformInteractionResponse();
		assertNotNull("Null object creation failed.", msg);

		testNullBase(msg);

		assertNull("Choice id wasn't set, but getter method returned an object.",         msg.getChoiceID());
		assertNull("Trigger source wasn't set, but getter method returned an object.",    msg.getTriggerSource());
		assertNull("Manual text entry wasn't set, but getter method returned an object.", msg.getManualTextEntry());
	}

}
