package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.ClimateControlData;
import com.smartdevicelink.proxy.rpc.Temperature;
import com.smartdevicelink.proxy.rpc.enums.DefrostZone;
import com.smartdevicelink.proxy.rpc.enums.VentilationMode;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
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

        msg.setFanSpeed(TestValues.GENERAL_INT);
        msg.setCurrentTemperature(TestValues.GENERAL_TEMPERATURE);
        msg.setDesiredTemperature(TestValues.GENERAL_TEMPERATURE);
        msg.setAcEnable(TestValues.GENERAL_BOOLEAN);
        msg.setCirculateAirEnable(TestValues.GENERAL_BOOLEAN);
        msg.setAutoModeEnable(TestValues.GENERAL_BOOLEAN);
        msg.setDefrostZone(TestValues.GENERAL_DEFROSTZONE);
        msg.setDualModeEnable(TestValues.GENERAL_BOOLEAN);
        msg.setAcMaxEnable(TestValues.GENERAL_BOOLEAN);
        msg.setVentilationMode(TestValues.GENERAL_VENTILATIONMODE);
        msg.setHeatedSteeringWheelEnable(TestValues.GENERAL_BOOLEAN);
        msg.setHeatedWindshieldEnable(TestValues.GENERAL_BOOLEAN);
        msg.setHeatedRearWindowEnable(TestValues.GENERAL_BOOLEAN);
        msg.setHeatedMirrorsEnable(TestValues.GENERAL_BOOLEAN);
        msg.setClimateEnable(TestValues.GENERAL_BOOLEAN);
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
        boolean climateEnable = msg.getClimateEnable();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, fanSpeed);
        assertTrue(TestValues.TRUE, Validator.validateTemperature(TestValues.GENERAL_TEMPERATURE, currentTemperature));
        assertTrue(TestValues.TRUE, Validator.validateTemperature(TestValues.GENERAL_TEMPERATURE, desiredTemperature));
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, acEnable);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, circulateAirEnable);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, autoModeEnable);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_DEFROSTZONE, defrostZone);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, dualModeEnable);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, acMaxEnable);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_VENTILATIONMODE, ventilationMode);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, heatedSteeringWheelEnable);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, heatedWindshieldEnable);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, heatedRearWindowEnable);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, heatedMirrorsEnable);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, climateEnable);

        // Invalid/Null Tests
        ClimateControlData msg = new ClimateControlData();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getFanSpeed());
        assertNull(TestValues.NULL, msg.getCurrentTemperature());
        assertNull(TestValues.NULL, msg.getDesiredTemperature());
        assertNull(TestValues.NULL, msg.getAcEnable());
        assertNull(TestValues.NULL, msg.getCirculateAirEnable());
        assertNull(TestValues.NULL, msg.getAutoModeEnable());
        assertNull(TestValues.NULL, msg.getDefrostZone());
        assertNull(TestValues.NULL, msg.getDualModeEnable());
        assertNull(TestValues.NULL, msg.getAcMaxEnable());
        assertNull(TestValues.NULL, msg.getVentilationMode());
        assertNull(TestValues.NULL, msg.getHeatedSteeringWheelEnable());
        assertNull(TestValues.NULL, msg.getHeatedWindshieldEnable());
        assertNull(TestValues.NULL, msg.getHeatedRearWindowEnable());
        assertNull(TestValues.NULL, msg.getHeatedMirrorsEnable());
        assertNull(TestValues.NULL, msg.getClimateEnable());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(ClimateControlData.KEY_FAN_SPEED, TestValues.GENERAL_INT);
            reference.put(ClimateControlData.KEY_CURRENT_TEMPERATURE, JsonRPCMarshaller.serializeHashtable(TestValues.GENERAL_TEMPERATURE.getStore()));
            reference.put(ClimateControlData.KEY_DESIRED_TEMPERATURE, JsonRPCMarshaller.serializeHashtable(TestValues.GENERAL_TEMPERATURE.getStore()));
            reference.put(ClimateControlData.KEY_AC_ENABLE, TestValues.GENERAL_BOOLEAN);
            reference.put(ClimateControlData.KEY_CIRCULATE_AIR_ENABLE, TestValues.GENERAL_BOOLEAN);
            reference.put(ClimateControlData.KEY_AUTO_MODE_ENABLE, TestValues.GENERAL_BOOLEAN);
            reference.put(ClimateControlData.KEY_DUAL_MODE_ENABLE, TestValues.GENERAL_BOOLEAN);
            reference.put(ClimateControlData.KEY_AC_MAX_ENABLE, TestValues.GENERAL_BOOLEAN);
            reference.put(ClimateControlData.KEY_DEFROST_ZONE, TestValues.GENERAL_DEFROSTZONE);
            reference.put(ClimateControlData.KEY_VENTILATION_MODE, TestValues.GENERAL_VENTILATIONMODE);
            reference.put(ClimateControlData.KEY_HEATED_STEERING_WHEEL_ENABLE, TestValues.GENERAL_BOOLEAN);
            reference.put(ClimateControlData.KEY_HEATED_WIND_SHIELD_ENABLE, TestValues.GENERAL_BOOLEAN);
            reference.put(ClimateControlData.KEY_HEATED_REAR_WINDOW_ENABLE, TestValues.GENERAL_BOOLEAN);
            reference.put(ClimateControlData.KEY_HEATED_MIRRORS_ENABLE, TestValues.GENERAL_BOOLEAN);
            reference.put(ClimateControlData.KEY_CLIMATE_ENABLE, TestValues.GENERAL_BOOLEAN);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();

                if(key.equals(ClimateControlData.KEY_CURRENT_TEMPERATURE)){
                    JSONObject objectEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
                    JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
                    Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
                    Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
                    assertTrue(TestValues.TRUE, Validator.validateTemperature( new Temperature(hashReference), new Temperature(hashTest)));
                } else if(key.equals(ClimateControlData.KEY_DESIRED_TEMPERATURE)){
                    JSONObject objectEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
                    JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
                    Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
                    Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
                    assertTrue(TestValues.TRUE, Validator.validateTemperature( new Temperature(hashReference), new Temperature(hashTest)));
                } else{
                    assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
                }
            }
        } catch(JSONException e){
        	fail(TestValues.JSON_FAIL);
        }
    }
}