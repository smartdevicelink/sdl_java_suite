package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.ClimateControlData;
import com.smartdevicelink.proxy.rpc.ModuleData;
import com.smartdevicelink.proxy.rpc.RadioControlData;
import com.smartdevicelink.proxy.rpc.enums.ModuleType;
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
 * {@link com.smartdevicelink.rpc.ModuleData}
 */
public class ModuleDataTests extends TestCase{
	
    private ModuleData msg;

    @Override
    public void setUp(){
        msg = new ModuleData();

        msg.setModuleType(Test.GENERAL_MODULETYPE);
        msg.setRadioControlData(Test.GENERAL_RADIOCONTROLDATA);
        msg.setClimateControlData(Test.GENERAL_CLIMATECONTROLDATA);
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
        // Test Values
        ModuleType moduleType = msg.getModuleType();
        RadioControlData radioControlData = msg.getRadioControlData();
        ClimateControlData climateControlData = msg.getClimateControlData();

        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_MODULETYPE, moduleType);
        assertTrue(Test.TRUE, Validator.validateRadioControlData(Test.GENERAL_RADIOCONTROLDATA, radioControlData));
        assertTrue(Test.TRUE, Validator.validateClimateControlData(Test.GENERAL_CLIMATECONTROLDATA, climateControlData));

        // Invalid/Null Tests
        ModuleData msg = new ModuleData();
        assertNotNull(Test.NOT_NULL, msg);

        assertNull(Test.NULL, msg.getModuleType());
        assertNull(Test.NULL, msg.getRadioControlData());
        assertNull(Test.NULL, msg.getClimateControlData());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(ModuleData.KEY_MODULE_TYPE, Test.GENERAL_MODULETYPE);
            reference.put(ModuleData.KEY_RADIO_CONTROL_DATA, JsonRPCMarshaller.serializeHashtable(Test.GENERAL_RADIOCONTROLDATA.getStore()));
            reference.put(ModuleData.KEY_CLIMATE_CONTROL_DATA, JsonRPCMarshaller.serializeHashtable(Test.GENERAL_CLIMATECONTROLDATA.getStore()));

            JSONObject underTest = msg.serializeJSON();
            assertEquals(Test.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();

                if(key.equals(ModuleData.KEY_RADIO_CONTROL_DATA)){
                    JSONObject objectEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
                    JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
                    Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
                    Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
                    assertTrue(Test.TRUE, Validator.validateRadioControlData( new RadioControlData(hashReference), new RadioControlData(hashTest)));
                } else if(key.equals(ModuleData.KEY_CLIMATE_CONTROL_DATA)){
                    JSONObject objectEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
                    JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
                    Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
                    Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
                    assertTrue(Test.TRUE, Validator.validateClimateControlData( new ClimateControlData(hashReference), new ClimateControlData(hashTest)));
                } else{
                    assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
                }
            }
        } catch(JSONException e){
        	fail(Test.JSON_FAIL);
        }
    }
}