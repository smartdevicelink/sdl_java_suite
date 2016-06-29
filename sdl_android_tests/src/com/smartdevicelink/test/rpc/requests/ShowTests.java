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
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.Show;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.enums.TextAlignment;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.Show}
 */
public class ShowTests extends BaseRpcTests {
    
    @Override
	protected RPCMessage createMessage() {		
		Show msg = new Show();
		
		msg.setMainField1(Test.GENERAL_STRING);
		msg.setMainField2(Test.GENERAL_STRING);
		msg.setMainField3(Test.GENERAL_STRING);
		msg.setMainField4(Test.GENERAL_STRING);
		msg.setStatusBar(Test.GENERAL_STRING);
		msg.setMediaTrack(Test.GENERAL_STRING);
		msg.setAlignment(Test.GENERAL_TEXTALIGNMENT);
		msg.setGraphic(Test.GENERAL_IMAGE);
		msg.setSecondaryGraphic(Test.GENERAL_IMAGE);
		msg.setCustomPresets(Test.GENERAL_STRING_LIST);
		msg.setSoftButtons(Test.GENERAL_SOFTBUTTON_LIST);

		return msg;
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.SHOW.toString();
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();

		try { 
			result.put(Show.KEY_MAIN_FIELD_1, Test.GENERAL_STRING);
			result.put(Show.KEY_MAIN_FIELD_2, Test.GENERAL_STRING);
			result.put(Show.KEY_MAIN_FIELD_3, Test.GENERAL_STRING);
			result.put(Show.KEY_MAIN_FIELD_4, Test.GENERAL_STRING);
			result.put(Show.KEY_STATUS_BAR,  Test.GENERAL_STRING);
			result.put(Show.KEY_MEDIA_TRACK, Test.GENERAL_STRING);			
			result.put(Show.KEY_GRAPHIC, Test.JSON_IMAGE);
			result.put(Show.KEY_SECONDARY_GRAPHIC, Test.JSON_IMAGE);
			result.put(Show.KEY_ALIGNMENT, Test.GENERAL_TEXTALIGNMENT);
			result.put(Show.KEY_CUSTOM_PRESETS, JsonUtils.createJsonArray(Test.GENERAL_STRING_LIST));			
			result.put(Show.KEY_SOFT_BUTTONS, Test.JSON_SOFTBUTTONS);
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}

		return result;
	}
		
	public void testSoftButtons () {
		// TestValues
		String testTrack     = ( (Show) msg ).getMediaTrack();
		Image  testGraphic2  = ( (Show) msg ).getSecondaryGraphic();
		Image  testGraphic1  = ( (Show) msg ).getGraphic();
		String testStatusBar = ( (Show) msg ).getStatusBar();
		String testText1     = ( (Show) msg ).getMainField1();
		String testText2     = ( (Show) msg ).getMainField2();
		String testText3     = ( (Show) msg ).getMainField3();
		String testText4     = ( (Show) msg ).getMainField4();
		TextAlignment    testAlignment     = ( (Show) msg ).getAlignment();
		List<SoftButton> testSoftButtons   = ( (Show) msg ).getSoftButtons();
		List<String>     testCustomPresets = ( (Show) msg ).getCustomPresets();
		
		// Valid Tests
		assertEquals(Test.MATCH, Test.GENERAL_STRING, testTrack);
		assertEquals(Test.MATCH, Test.GENERAL_TEXTALIGNMENT, testAlignment);
		assertEquals(Test.MATCH, Test.GENERAL_STRING, testStatusBar);
		assertEquals(Test.MATCH, Test.GENERAL_STRING, testText1);
		assertEquals(Test.MATCH, Test.GENERAL_STRING, testText2);
		assertEquals(Test.MATCH, Test.GENERAL_STRING, testText3);
		assertEquals(Test.MATCH, Test.GENERAL_STRING, testText4);
		assertEquals(Test.MATCH, Test.GENERAL_STRING_LIST.size(), testCustomPresets.size());
		assertTrue(Test.TRUE, Validator.validateSoftButtons(Test.GENERAL_SOFTBUTTON_LIST, testSoftButtons));
		assertTrue(Test.TRUE, Validator.validateImage(Test.GENERAL_IMAGE, testGraphic2));
		assertTrue(Test.TRUE, Validator.validateImage(Test.GENERAL_IMAGE, testGraphic1));
	
		// Invalid/Null Tests
		Show msg = new Show();
		assertNotNull(Test.NOT_NULL, msg);
		testNullBase(msg);

		assertNull(Test.NULL, msg.getMainField1());
		assertNull(Test.NULL, msg.getMainField2());
		assertNull(Test.NULL, msg.getMainField3());
		assertNull(Test.NULL, msg.getMainField4());
		assertNull(Test.NULL, msg.getStatusBar());
		assertNull(Test.NULL, msg.getAlignment());
		assertNull(Test.NULL, msg.getGraphic());
		assertNull(Test.NULL, msg.getSecondaryGraphic());
		assertNull(Test.NULL, msg.getCustomPresets());
		assertNull(Test.NULL, msg.getMediaTrack());
		assertNull(Test.NULL, msg.getSoftButtons());		
	}

	/**
     * Tests a valid JSON construction of this RPC message.
     */
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(this.mContext,getCommandType(), getMessageType());
    	assertNotNull(Test.NOT_NULL, commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			Show cmd = new Show(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			
			JSONObject graphic = JsonUtils.readJsonObjectFromJsonObject(parameters, Show.KEY_GRAPHIC);
			Image referenceGraphic = new Image(JsonRPCMarshaller.deserializeJSONObject(graphic));
			assertTrue(Test.TRUE, Validator.validateImage(referenceGraphic, cmd.getGraphic()));
			
			List<String> customPresetsList = JsonUtils.readStringListFromJsonObject(parameters, Show.KEY_CUSTOM_PRESETS);
			List<String> testPresetsList = cmd.getCustomPresets();
			assertEquals(Test.MATCH, customPresetsList.size(), testPresetsList.size());
			assertTrue(Test.TRUE, Validator.validateStringList(customPresetsList, testPresetsList));

			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, Show.KEY_MAIN_FIELD_1), cmd.getMainField1());
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, Show.KEY_MAIN_FIELD_2), cmd.getMainField2());
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, Show.KEY_MAIN_FIELD_3), cmd.getMainField3());
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, Show.KEY_MAIN_FIELD_4), cmd.getMainField4());
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, Show.KEY_STATUS_BAR), cmd.getStatusBar());
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, Show.KEY_ALIGNMENT), cmd.getAlignment().toString());
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, Show.KEY_MEDIA_TRACK), cmd.getMediaTrack());

			JSONObject secondaryGraphic = JsonUtils.readJsonObjectFromJsonObject(parameters, Show.KEY_SECONDARY_GRAPHIC);
			Image referenceSecondaryGraphic = new Image(JsonRPCMarshaller.deserializeJSONObject(secondaryGraphic));
			assertTrue(Test.TRUE, Validator.validateImage(referenceSecondaryGraphic, cmd.getSecondaryGraphic()));
			
			JSONArray softButtonArray = JsonUtils.readJsonArrayFromJsonObject(parameters, Show.KEY_SOFT_BUTTONS);
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