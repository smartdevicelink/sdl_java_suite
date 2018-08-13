package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.ClimateControlData;
import com.smartdevicelink.proxy.rpc.Temperature;
import com.smartdevicelink.proxy.rpc.enums.DefrostZone;
import com.smartdevicelink.proxy.rpc.enums.VentilationMode;
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
 * {@link com.smartdevicelink.rpc.ClimateControlData}
 */
public class ClimateControlDataTests extends TestCase{
	
    private ClimateControlData msg;

    @Override
    public void setUp(){
        msg = new ClimateControlData();

        msg.setFanSpeed(Test.GENERAL_INT);
        msg.setCurrentTemperature(Test.GENERAL_TEMPERATURE);
        msg.setDesiredTemperature(Test.GENERAL_TEMPERATURE);
        msg.setAcEnable(Test.GENERAL_BOOLEAN);
        msg.setCirculateAirEnable(Test.GENERAL_BOOLEAN);
        msg.setAutoModeEnable(Test.GENERAL_BOOLEAN);
        msg.setDefrostZone(Test.GENERAL_DEFROSTZONE);
        msg.setDualModeEnable(Test.GENERAL_BOOLEAN);
        msg.setAcMaxEnable(Test.GENERAL_BOOLEAN);
        msg.setVentilationMode(Test.GENERAL_VENTILATIONMODE);
        msg.setHeatedSteeringWheelEnable(Test.GENERAL_BOOLEAN);
        msg.setHeatedWindshieldEnable(Test.GENERAL_BOOLEAN);
        msg.setHeatedRearWindowEnable(Test.GENERAL_BOOLEAN);
        msg.setHeatedMirrorsEnable(Test.GENERAL_BOOLEAN);
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
        // Test Values
        int fanSpeed = msg.getFanSpeed();
        Temperature currentTemperature = msg.getCurrentTemperature();
        Temperature desiredTemperature = msg.getDesiredTemperature();
        boolean acEnable = msg.getAcEnable();
        boolean circulateAirEnable = msg.getCirculateAirEnable();
        boolean autoModeEnable = msg.getAutoModeEnable();
        DefrostZone defrostZone = msg.getDefrostZone();
        boolean dualModeEnable = msg.getDualModeEnable();
        boolean acMaxEnable = msg.getAcMaxEnable();
        VentilationMode ventilationMode = msg.getVentilationMode();
        boolean heatedSteeringWheelEnable = msg.getHeatedSteeringWheelEnable();
        boolean heatedWindshieldEnable = msg.getHeatedWindshieldEnable();
        boolean heatedRearWindowEnable = msg.getHeatedRearWindowEnable();
        boolean heatedMirrorsEnable = msg.getHeatedMirrorsEnable();

        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_INT, fanSpeed);
        assertTrue(Test.TRUE, Validator.validateTemperature(Test.GENERAL_TEMPERATURE, currentTemperature));
        assertTrue(Test.TRUE, Validator.validateTemperature(Test.GENERAL_TEMPERATURE, desiredTemperature));
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, acEnable);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, circulateAirEnable);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, autoModeEnable);
        assertEquals(Test.MATCH, Test.GENERAL_DEFROSTZONE, defrostZone);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, dualModeEnable);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, acMaxEnable);
        assertEquals(Test.MATCH, Test.GENERAL_VENTILATIONMODE, ventilationMode);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, heatedSteeringWheelEnable);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, heatedWindshieldEnable);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, heatedRearWindowEnable);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, heatedMirrorsEnable);

        // Invalid/Null Tests
        ClimateControlData msg = new ClimateControlData();
        assertNotNull(Test.NOT_NULL, msg);

        assertNull(Test.NULL, msg.getFanSpeed());
        assertNull(Test.NULL, msg.getCurrentTemperature());
        assertNull(Test.NULL, msg.getDesiredTemperature());
        assertNull(Test.NULL, msg.getAcEnable());
        assertNull(Test.NULL, msg.getCirculateAirEnable());
        assertNull(Test.NULL, msg.getAutoModeEnable());
        assertNull(Test.NULL, msg.getDefrostZone());
        assertNull(Test.NULL, msg.getDualModeEnable());
        assertNull(Test.NULL, msg.getAcMaxEnable());
        assertNull(Test.NULL, msg.getVentilationMode());
        assertNull(Test.NULL, msg.getHeatedSteeringWheelEnable());
        assertNull(Test.NULL, msg.getHeatedWindshieldEnable());
        assertNull(Test.NULL, msg.getHeatedRearWindowEnable());
        assertNull(Test.NULL, msg.getHeatedMirrorsEnable());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(ClimateControlData.KEY_FAN_SPEED, Test.GENERAL_INT);
            reference.put(ClimateControlData.KEY_CURRENT_TEMPERATURE, JsonRPCMarshaller.serializeHashtable(Test.GENERAL_TEMPERATURE.getStore()));
            reference.put(ClimateControlData.KEY_DESIRED_TEMPERATURE, JsonRPCMarshaller.serializeHashtable(Test.GENERAL_TEMPERATURE.getStore()));
            reference.put(ClimateControlData.KEY_AC_ENABLE, Test.GENERAL_BOOLEAN);
            reference.put(ClimateControlData.KEY_CIRCULATE_AIR_ENABLE, Test.GENERAL_BOOLEAN);
            reference.put(ClimateControlData.KEY_AUTO_MODE_ENABLE, Test.GENERAL_BOOLEAN);
            reference.put(ClimateControlData.KEY_DUAL_MODE_ENABLE, Test.GENERAL_BOOLEAN);
            reference.put(ClimateControlData.KEY_AC_MAX_ENABLE, Test.GENERAL_BOOLEAN);
            reference.put(ClimateControlData.KEY_DEFROST_ZONE, Test.GENERAL_DEFROSTZONE);
            reference.put(ClimateControlData.KEY_VENTILATION_MODE, Test.GENERAL_VENTILATIONMODE);
            reference.put(ClimateControlData.KEY_HEATED_STEERING_WHEEL_ENABLE, Test.GENERAL_BOOLEAN);
            reference.put(ClimateControlData.KEY_HEATED_WIND_SHIELD_ENABLE, Test.GENERAL_BOOLEAN);
            reference.put(ClimateControlData.KEY_HEATED_REAR_WINDOW_ENABLE, Test.GENERAL_BOOLEAN);
            reference.put(ClimateControlData.KEY_HEATED_MIRRORS_ENABLE, Test.GENERAL_BOOLEAN);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(Test.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();

                if(key.equals(ClimateControlData.KEY_CURRENT_TEMPERATURE)){
                    JSONObject objectEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
                    JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
                    Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
                    Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
                    assertTrue(Test.TRUE, Validator.validateTemperature( new Temperature(hashReference), new Temperature(hashTest)));
                } else if(key.equals(ClimateControlData.KEY_DESIRED_TEMPERATURE)){
                    JSONObject objectEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
                    JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
                    Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
                    Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
                    assertTrue(Test.TRUE, Validator.validateTemperature( new Temperature(hashReference), new Temperature(hashTest)));
                } else{
                    assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
                }
            }
        } catch(JSONException e){
        	fail(Test.JSON_FAIL);
        }
    }
}