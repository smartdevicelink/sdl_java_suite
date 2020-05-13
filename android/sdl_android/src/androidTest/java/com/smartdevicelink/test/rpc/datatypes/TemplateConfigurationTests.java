package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.TemplateColorScheme;
import com.smartdevicelink.proxy.rpc.TemplateConfiguration;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.rpc.TemplateConfiguration}
 */
public class TemplateConfigurationTests extends TestCase {

    private TemplateConfiguration msg;

    @Override
    public void setUp(){
        msg = new TemplateConfiguration();

        msg.setTemplate(Test.GENERAL_STRING);
        msg.setDayColorScheme(Test.GENERAL_DAYCOLORSCHEME);
        msg.setNightColorScheme(Test.GENERAL_NIGHTCOLORSCHEME);

    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues () {
    	// Test Values
        String testTemplate = msg.getTemplate();
        TemplateColorScheme testDayColorScheme = msg.getDayColorScheme();
        TemplateColorScheme testNightColorScheme = msg.getNightColorScheme();
        
        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_STRING, testTemplate);
        assertEquals(Test.MATCH, Test.GENERAL_DAYCOLORSCHEME, testDayColorScheme);
        assertEquals(Test.MATCH, Test.GENERAL_NIGHTCOLORSCHEME, testNightColorScheme);
        
        // Invalid/Null Tests
        TemplateConfiguration msg = new TemplateConfiguration();
        assertNotNull(Test.NOT_NULL, msg);

        assertNull(Test.NULL, msg.getTemplate());
        assertNull(Test.NULL, msg.getDayColorScheme());
        assertNull(Test.NULL, msg.getNightColorScheme());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(TemplateConfiguration.KEY_TEMPLATE, Test.GENERAL_STRING);
            reference.put(TemplateConfiguration.KEY_DAY_COLOR_SCHEME, JsonRPCMarshaller.serializeHashtable(Test.GENERAL_DAYCOLORSCHEME.getStore()));
            reference.put(TemplateConfiguration.KEY_NIGHT_COLOR_SCHEME, JsonRPCMarshaller.serializeHashtable(Test.GENERAL_NIGHTCOLORSCHEME.getStore()));

            JSONObject underTest = msg.serializeJSON();
            assertEquals(Test.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();

                if (key.equals(TemplateConfiguration.KEY_DAY_COLOR_SCHEME)) {
                    JSONObject objectEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
                    JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
                    Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
                    Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
                    assertTrue(Test.TRUE, Validator.validateTemplateColorScheme(new TemplateColorScheme(hashReference), new TemplateColorScheme(hashTest)));
                } else if (key.equals(TemplateConfiguration.KEY_NIGHT_COLOR_SCHEME)) {
                    JSONObject objectEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
                    JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
                    Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
                    Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
                    assertTrue(Test.TRUE, Validator.validateTemplateColorScheme(new TemplateColorScheme(hashReference), new TemplateColorScheme(hashTest)));
                } else {
                    assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
                }
            }
        } catch(JSONException e){
        	fail(Test.JSON_FAIL);
        }
    }
}