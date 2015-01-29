package com.smartdevicelink.test.rpc.requests;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.Show;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.SoftButtonType;
import com.smartdevicelink.proxy.rpc.enums.SystemAction;
import com.smartdevicelink.proxy.rpc.enums.TextAlignment;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.utils.JsonUtils;
import com.smartdevicelink.test.utils.Validator;

public class ShowTest extends BaseRpcTests {

	private static final String TEXT_1 = "text1";
	private static final String TEXT_2 = "text2";
	private static final String TEXT_3 = "text3";
	private static final String TEXT_4 = "text4";
	private static final String STATUS_BAR = "statusBar";
	private static final String MEDIA_CLOCK = "mediaClock";
	private static final String MEDIA_TRACK = "mediaTrack";
	private static final TextAlignment TEXT_ALIGNMENT = TextAlignment.CENTERED;
	private static final Image IMAGE_1 = new Image();
	private static final Image IMAGE_2 = new Image();
	private static final List<String> CUSTOM_PRESETS = new ArrayList<String>();
	private static final String SOFT_BUTTON_TEXT = "Hello";
	private static final Boolean SOFT_BUTTON_HIGHLIGHTED = true;
	private static final String CUSTOM_PRESET_SAMPLE = "Custom Preset 1";
	
	private List<SoftButton> softButtons = new ArrayList<SoftButton>();
    
	@Override
	protected RPCMessage createMessage() {
		CUSTOM_PRESETS.add(CUSTOM_PRESET_SAMPLE);
		
		Show msg = new Show();
		
		createCustomObjects();
		
		msg.setMainField1(TEXT_1);
		msg.setMainField2(TEXT_2);
		msg.setMainField3(TEXT_3);
		msg.setMainField4(TEXT_4);
		msg.setStatusBar(STATUS_BAR);
		msg.setMediaClock(MEDIA_CLOCK);
		msg.setMediaTrack(MEDIA_TRACK);
		msg.setAlignment(TEXT_ALIGNMENT);
		msg.setGraphic(IMAGE_1);
		msg.setSecondaryGraphic(IMAGE_2);
		msg.setCustomPresets(CUSTOM_PRESETS);
		msg.setSoftButtons(softButtons);

		return msg;
	}
	
	private void createCustomObjects () {
		softButtons = new ArrayList<SoftButton>(2);

        SoftButton button = new SoftButton();
        button.setType(SoftButtonType.SBT_TEXT);
        button.setIsHighlighted(false);
        button.setSoftButtonID(100);
        button.setText("ABC123");
        button.setSystemAction(SystemAction.DEFAULT_ACTION);
        softButtons.add(button);

        button = new SoftButton();
        button.setType(SoftButtonType.SBT_BOTH);
        button.setIsHighlighted(false);
        button.setSoftButtonID(100);
        button.setText("123ABC");
        button.setSystemAction(SystemAction.STEAL_FOCUS);
        softButtons.add(button);   
        
        button = new SoftButton();
        button.setText(SOFT_BUTTON_TEXT);
        button.setIsHighlighted(SOFT_BUTTON_HIGHLIGHTED);
		
        Image buttonImage = new Image();
        buttonImage.setImageType(ImageType.DYNAMIC);
        buttonImage.setValue("buttonImage.png");
        button.setImage(buttonImage);
        softButtons.add(button);
        
    	IMAGE_1.setValue("value1");
    	IMAGE_1.setImageType(ImageType.DYNAMIC);
    	IMAGE_2.setValue("value2");
    	IMAGE_2.setImageType(ImageType.STATIC);
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.SHOW;
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();
		JSONObject image1 = new JSONObject(), image2 = new JSONObject();
		JSONArray softButtons = new JSONArray();

		try {   
			JSONObject softButton = new JSONObject();
            softButton.put(SoftButton.KEY_SOFT_BUTTON_ID, 100);
            softButton.put(SoftButton.KEY_IS_HIGHLIGHTED, false);
            softButton.put(SoftButton.KEY_SYSTEM_ACTION, SystemAction.DEFAULT_ACTION);
            softButton.put(SoftButton.KEY_TEXT, "ABC123");
            softButton.put(SoftButton.KEY_TYPE, SoftButtonType.SBT_TEXT);
            softButtons.put(softButton);

            softButton = new JSONObject();
            softButton.put(SoftButton.KEY_SOFT_BUTTON_ID, 100);
            softButton.put(SoftButton.KEY_IS_HIGHLIGHTED, false);
            softButton.put(SoftButton.KEY_SYSTEM_ACTION, SystemAction.STEAL_FOCUS);
            softButton.put(SoftButton.KEY_TEXT, "123ABC");
            softButton.put(SoftButton.KEY_TYPE, SoftButtonType.SBT_BOTH);

            JSONObject image = new JSONObject();
            image.put(Image.KEY_IMAGE_TYPE, ImageType.DYNAMIC);
            image.put(Image.KEY_VALUE, "buttonImage.png");

            softButton.put(SoftButton.KEY_IMAGE, image);
            softButtons.put(softButton);
			
			image1.put(Image.KEY_IMAGE_TYPE, ImageType.DYNAMIC);
			image1.put(Image.KEY_VALUE, "value1");
			
			image2.put(Image.KEY_IMAGE_TYPE, ImageType.STATIC);
			image2.put(Image.KEY_VALUE, "value2");
			
			result.put(Show.KEY_MAIN_FIELD_1, TEXT_1);
			result.put(Show.KEY_MAIN_FIELD_2, TEXT_2);
			result.put(Show.KEY_MAIN_FIELD_3, TEXT_3);
			result.put(Show.KEY_MAIN_FIELD_4, TEXT_4);
			result.put(Show.KEY_STATUS_BAR, STATUS_BAR);
			result.put(Show.KEY_MEDIA_CLOCK, MEDIA_CLOCK);
			result.put(Show.KEY_MEDIA_TRACK, MEDIA_TRACK);			
			result.put(Show.KEY_GRAPHIC, image1);
			result.put(Show.KEY_SECONDARY_GRAPHIC, image2);
			result.put(Show.KEY_ALIGNMENT, TEXT_ALIGNMENT);
			result.put(Show.KEY_CUSTOM_PRESETS, JsonUtils.createJsonArray(CUSTOM_PRESETS));			
			result.put(Show.KEY_SOFT_BUTTONS, softButtons);
			
		} catch (JSONException e) {
			/* do nothing */
		}

		return result;
	}

	public void testText1 () {
		String copy = ( (Show) msg ).getMainField1();
		
		assertEquals("Data didn't match input data.", TEXT_1, copy);
	}
	
	public void testText2 () {
		String copy = ( (Show) msg ).getMainField2();
		
		assertEquals("Data didn't match input data.", TEXT_2, copy);
	}
	
	public void testText3 () {
		String copy = ( (Show) msg ).getMainField3();
		
		assertEquals("Data didn't match input data.", TEXT_3, copy);
	}
	
	public void testText4 () {
		String copy = ( (Show) msg ).getMainField4();
		
		assertEquals("Data didn't match input data.", TEXT_4, copy);
	}
	
	public void testStatusBar () {
		String copy = ( (Show) msg ).getStatusBar();
		
		assertEquals("Data didn't match input data.", STATUS_BAR, copy);
	}
	
	public void testMediaClock () {
		String copy = ( (Show) msg ).getMediaClock();
		
		assertEquals("Data didn't match input data.", MEDIA_CLOCK, copy);
	}
	
	public void testTextAlignment () {
		TextAlignment copy = ( (Show) msg ).getAlignment();
		
		assertEquals("Data didn't match input data.", TEXT_ALIGNMENT, copy);
	}

	public void testImage1 () {
		Image copy = ( (Show) msg ).getGraphic();
		
		assertNotSame("Image 1 was not defensive copied", IMAGE_1, copy);
		assertTrue("Input value didn't match expected value.", Validator.validateImage(IMAGE_1, copy));
	}
	
	public void testImage2 () {
		Image copy = ( (Show) msg ).getSecondaryGraphic();
		
		assertNotSame("Image 2 was not defensive copied", IMAGE_2, copy);
		assertTrue("Input value didn't match expected value.", Validator.validateImage(IMAGE_2, copy));
	}
	
	public void testCustomPresets () {
		List<String> copy = ( (Show) msg ).getCustomPresets();
		
		assertNotSame("Custom presets was not defensive copied", CUSTOM_PRESETS, copy);
		assertEquals("List size didn't match expected size.", CUSTOM_PRESETS.size(), copy.size());
		
		for (int i = 0; i < CUSTOM_PRESETS.size(); i++) {
			assertEquals("Input value didn't match expected value.", CUSTOM_PRESETS.get(i), copy.get(i));
		}
	}
	
	public void testMediaTrack () {
		String copy = ( (Show) msg ).getMediaTrack();
		
		assertEquals("Data didn't match input data.", MEDIA_TRACK, copy);
	}
	
	public void testSoftButtons () {
		List<SoftButton> copy = ( (Show) msg ).getSoftButtons();
		
		assertNotSame("Custom presets was not defensive copied", softButtons, copy);
		assertTrue("Input value didn't match expected value.", Validator.validateSoftButtons(softButtons, copy));
	}
	
	public void testNull() {
		Show msg = new Show();
		assertNotNull("Null object creation failed.", msg);

		testNullBase(msg);

		assertNull("Text 1 wasn't set, but getter method returned an object.", msg.getMainField1());
		assertNull("Text 2 wasn't set, but getter method returned an object.", msg.getMainField2());
		assertNull("Text 3 wasn't set, but getter method returned an object.", msg.getMainField3());
		assertNull("Text 4 wasn't set, but getter method returned an object.", msg.getMainField4());
		assertNull("Status bar wasn't set, but getter method returned an object.", msg.getStatusBar());
		assertNull("Media clock wasn't set, but getter method returned an object.", msg.getMediaClock());
		assertNull("Alignment wasn't set, but getter method returned an object.", msg.getAlignment());
		assertNull("Image 1 wasn't set, but getter method returned an object.", msg.getGraphic());
		assertNull("Image 2 wasn't set, but getter method returned an object.", msg.getSecondaryGraphic());
		assertNull("Custom presets wasn't set, but getter method returned an object.", msg.getCustomPresets());
		assertNull("Media track wasn't set, but getter method returned an object.", msg.getMediaTrack());
		assertNull("Soft buttons wasn't set, but getter method returned an object.", msg.getSoftButtons());		
	}

}
