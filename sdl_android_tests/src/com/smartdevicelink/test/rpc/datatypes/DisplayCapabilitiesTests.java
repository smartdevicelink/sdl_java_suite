package com.smartdevicelink.test.rpc.datatypes;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.DisplayCapabilities;
import com.smartdevicelink.proxy.rpc.ImageField;
import com.smartdevicelink.proxy.rpc.ScreenParams;
import com.smartdevicelink.proxy.rpc.TextField;
import com.smartdevicelink.proxy.rpc.enums.DisplayType;
import com.smartdevicelink.proxy.rpc.enums.MediaClockFormat;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.DisplayCapabilities}
 */
public class DisplayCapabilitiesTests extends TestCase{
	
    private DisplayCapabilities msg;

    @Override
    public void setUp(){    	
    	msg = new DisplayCapabilities();

        msg.setGraphicSupported(Test.GENERAL_BOOLEAN);
        msg.setNumCustomPresetsAvailable(Test.GENERAL_INT);
        msg.setDisplayType(Test.GENERAL_DISPLAYTYPE);
        msg.setImageFields(Test.GENERAL_IMAGEFIELD_LIST);
        msg.setTextFields(Test.GENERAL_TEXTFIELD_LIST);
        msg.setMediaClockFormats(Test.GENERAL_MEDIACLOCKFORMAT_LIST);
        msg.setScreenParams(Test.GENERAL_SCREENPARAMS);
        msg.setTemplatesAvailable(Test.GENERAL_STRING_LIST);
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
        boolean graphicSupported = msg.getGraphicSupported();
        int numPresets = msg.getNumCustomPresetsAvailable();
        DisplayType displayType = msg.getDisplayType();
        ScreenParams screenParams = msg.getScreenParams();
        List<String> templatesAvailable = msg.getTemplatesAvailable();
        List<MediaClockFormat> mediaClock = msg.getMediaClockFormats();
        List<TextField> textFields = msg.getTextFields();
        List<ImageField> imageFields = msg.getImageFields();
        
        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, graphicSupported);
        assertEquals(Test.MATCH, Test.GENERAL_INT, numPresets);
        assertEquals(Test.MATCH, Test.GENERAL_DISPLAYTYPE, displayType);
        assertTrue(Test.TRUE, Validator.validateScreenParams(Test.GENERAL_SCREENPARAMS, screenParams));
        assertEquals(Test.MATCH, Test.GENERAL_STRING_LIST.size(), templatesAvailable.size());
		assertEquals(Test.MATCH, Test.GENERAL_MEDIACLOCKFORMAT_LIST.size(), mediaClock.size());
		assertEquals(Test.MATCH, Test.GENERAL_TEXTFIELD_LIST.size(), textFields.size());
		assertEquals(Test.MATCH, Test.GENERAL_IMAGEFIELD_LIST.size(), imageFields.size());
		
		for(int i = 0; i < Test.GENERAL_STRING_LIST.size(); i++){
            assertEquals(Test.MATCH, Test.GENERAL_STRING_LIST.get(i), templatesAvailable.get(i));
        }
        
        for(int i = 0; i < Test.GENERAL_MEDIACLOCKFORMAT_LIST.size(); i++){
            assertEquals(Test.MATCH, Test.GENERAL_MEDIACLOCKFORMAT_LIST.get(i), mediaClock.get(i));
        }

        for(int i = 0; i < Test.GENERAL_TEXTFIELD_LIST.size(); i++){
            assertTrue(Test.TRUE, Validator.validateTextFields(Test.GENERAL_TEXTFIELD_LIST.get(i), textFields.get(i)));
        }

        for(int i = 0; i < Test.GENERAL_IMAGEFIELD_LIST.size(); i++){
            assertTrue(Test.TRUE, Validator.validateImageFields(Test.GENERAL_IMAGEFIELD_LIST.get(i), imageFields.get(i)));
        }
        
        // Invalid/Null Tests
        DisplayCapabilities msg = new DisplayCapabilities();
        assertNotNull(Test.NOT_NULL, msg);

        assertNull(Test.NULL, msg.getDisplayType());
        assertNull(Test.NULL, msg.getGraphicSupported());
        assertNull(Test.NULL, msg.getImageFields());
        assertNull(Test.NULL, msg.getMediaClockFormats());
        assertNull(Test.NULL, msg.getNumCustomPresetsAvailable());
        assertNull(Test.NULL, msg.getScreenParams());
        assertNull(Test.NULL, msg.getTemplatesAvailable());
        assertNull(Test.NULL, msg.getTextFields());
    }
    
    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(DisplayCapabilities.KEY_NUM_CUSTOM_PRESETS_AVAILABLE, Test.GENERAL_INT);
            reference.put(DisplayCapabilities.KEY_GRAPHIC_SUPPORTED, Test.GENERAL_BOOLEAN);
            reference.put(DisplayCapabilities.KEY_DISPLAY_TYPE, Test.GENERAL_DISPLAYTYPE);
            reference.put(DisplayCapabilities.KEY_TEMPLATES_AVAILABLE, JsonUtils.createJsonArray(Test.GENERAL_STRING_LIST));
            reference.put(DisplayCapabilities.KEY_MEDIA_CLOCK_FORMATS, JsonUtils.createJsonArray(Test.GENERAL_MEDIACLOCKFORMAT_LIST));
            reference.put(DisplayCapabilities.KEY_TEXT_FIELDS, Test.JSON_TEXTFIELDS);
            reference.put(DisplayCapabilities.KEY_IMAGE_FIELDS, Test.JSON_IMAGEFIELDS);
            reference.put(DisplayCapabilities.KEY_SCREEN_PARAMS, Test.JSON_SCREENPARAMS);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(Test.MATCH, reference.length(), underTest.length());
            
            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                if(key.equals(DisplayCapabilities.KEY_IMAGE_FIELDS)){
                    JSONArray referenceArray = JsonUtils.readJsonArrayFromJsonObject(reference, key);
                    JSONArray underTestArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
                    assertEquals(Test.MATCH, referenceArray.length(), underTestArray.length());

                    for(int i = 0; i < referenceArray.length(); i++){
                    	Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(referenceArray.getJSONObject(i));
                    	Hashtable<String, Object> hashTest= JsonRPCMarshaller.deserializeJSONObject(underTestArray.getJSONObject(i));
                    	assertTrue(Test.TRUE, Validator.validateImageFields(new ImageField(hashReference), new ImageField(hashTest)));
                    }
                } else if(key.equals(DisplayCapabilities.KEY_TEXT_FIELDS)){
                	JSONArray referenceArray = JsonUtils.readJsonArrayFromJsonObject(reference, key);
                    JSONArray underTestArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
                    assertEquals(Test.MATCH, referenceArray.length(), underTestArray.length());
                    
                    for(int i = 0; i < referenceArray.length(); i++){
                    	Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(referenceArray.getJSONObject(i));
                    	Hashtable<String, Object> hashTest= JsonRPCMarshaller.deserializeJSONObject(underTestArray.getJSONObject(i));
                    	assertTrue(Test.TRUE, Validator.validateTextFields(new TextField(hashReference), new TextField(hashTest)));
                    }
                } else if(key.equals(DisplayCapabilities.KEY_TEMPLATES_AVAILABLE)){
                    JSONArray referenceArray = JsonUtils.readJsonArrayFromJsonObject(reference, key);
                    JSONArray underTestArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
                    assertEquals(Test.MATCH, referenceArray.length(), underTestArray.length());
                    assertTrue(Test.TRUE, Validator.validateStringList(JsonUtils.readStringListFromJsonObject(reference, key), JsonUtils.readStringListFromJsonObject(underTest, key)));
                } else if(key.equals(DisplayCapabilities.KEY_SCREEN_PARAMS)){
                    JSONObject referenceArray = JsonUtils.readJsonObjectFromJsonObject(reference, key);
                    JSONObject underTestArray = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
                	Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(referenceArray);
                	Hashtable<String, Object> hashTest= JsonRPCMarshaller.deserializeJSONObject(underTestArray);
                    
                    assertTrue(Test.TRUE, Validator.validateScreenParams(new ScreenParams(hashReference), new ScreenParams(hashTest)));
                } else if(key.equals(DisplayCapabilities.KEY_MEDIA_CLOCK_FORMATS)){
                    JSONArray referenceArray = JsonUtils.readJsonArrayFromJsonObject(reference, key);
                    JSONArray underTestArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
                    assertEquals(Test.MATCH, referenceArray.length(), underTestArray.length());

                    for(int i = 0; i < referenceArray.length(); i++){
                        assertTrue(Test.TRUE, Validator.validateText(referenceArray.getString(i), underTestArray.getString(i)));// not a string?
                    }
                } else{
                    assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
                }
            }
        }catch(JSONException e){
        	fail(Test.JSON_FAIL);
        }
    }
}