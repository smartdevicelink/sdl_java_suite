package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.ClimateControlCapabilities;
import com.smartdevicelink.proxy.rpc.enums.DefrostZone;
import com.smartdevicelink.proxy.rpc.enums.VentilationMode;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

import junit.framework.TestCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.rpc.ClimateControlCapabilities}
 */
public class ClimateControlCapabilitiesTests extends TestCase{
	
    private ClimateControlCapabilities msg;

    @Override
    public void setUp(){
        msg = new ClimateControlCapabilities();

        msg.setModuleName(Test.GENERAL_STRING);
        msg.setFanSpeedAvailable(Test.GENERAL_BOOLEAN);
        msg.setDesiredTemperatureAvailable(Test.GENERAL_BOOLEAN);
        msg.setAcEnableAvailable(Test.GENERAL_BOOLEAN);
        msg.setAcMaxEnableAvailable(Test.GENERAL_BOOLEAN);
        msg.setCirculateAirEnableAvailable(Test.GENERAL_BOOLEAN);
        msg.setAutoModeEnableAvailable(Test.GENERAL_BOOLEAN);
        msg.setDualModeEnableAvailable(Test.GENERAL_BOOLEAN);
        msg.setDefrostZoneAvailable(Test.GENERAL_BOOLEAN);
        msg.setDefrostZone(Test.GENERAL_DEFROSTZONE_LIST);
        msg.setVentilationModeAvailable(Test.GENERAL_BOOLEAN);
        msg.setVentilationMode(Test.GENERAL_VENTILATIONMODE_LIST);
        msg.setHeatedSteeringWheelAvailable(Test.GENERAL_BOOLEAN);
        msg.setHeatedWindshieldAvailable(Test.GENERAL_BOOLEAN);
        msg.setHeatedRearWindowAvailable(Test.GENERAL_BOOLEAN);
        msg.setHeatedMirrorsAvailable(Test.GENERAL_BOOLEAN);
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
        // Test Values
        String moduleName = msg.getModuleName();
        boolean fanSpeedAvailable = msg.getFanSpeedAvailable();
        boolean desiredTemperatureAvailable = msg.getDesiredTemperatureAvailable();
        boolean acEnableAvailable = msg.getAcEnableAvailable();
        boolean acMaxEnableAvailable = msg.getAcMaxEnableAvailable();
        boolean circulateAirEnableAvailable = msg.getCirculateAirEnableAvailable();
        boolean autoModeEnableAvailable = msg.getAutoModeEnableAvailable();
        boolean dualModeEnableAvailable = msg.getDualModeEnableAvailable();
        boolean defrostZoneAvailable = msg.getDefrostZoneAvailable();
        List<DefrostZone> defrostZone = msg.getDefrostZone();
        boolean ventilationModeAvailable = msg.getVentilationModeAvailable();
        List<VentilationMode> ventilationMode = msg.getVentilationMode();
        boolean heatedSteeringWheelAvailable = msg.getHeatedSteeringWheelAvailable();
        boolean heatedWindshieldAvailable = msg.getHeatedWindshieldAvailable();
        boolean heatedRearWindowAvailable = msg.getHeatedRearWindowAvailable();
        boolean heatedMirrorsAvailable = msg.getHeatedMirrorsAvailable();

        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_STRING, moduleName);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, fanSpeedAvailable);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, desiredTemperatureAvailable);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, acEnableAvailable);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, acMaxEnableAvailable);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, circulateAirEnableAvailable);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, autoModeEnableAvailable);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, dualModeEnableAvailable);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, defrostZoneAvailable);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, ventilationModeAvailable);

        assertEquals(Test.MATCH, Test.GENERAL_DEFROSTZONE_LIST.size(), defrostZone.size());
        assertEquals(Test.MATCH, Test.GENERAL_VENTILATIONMODE_LIST.size(), ventilationMode.size());

        for(int i = 0; i < Test.GENERAL_DEFROSTZONE_LIST.size(); i++){
            assertEquals(Test.MATCH, Test.GENERAL_DEFROSTZONE_LIST.get(i), defrostZone.get(i));
        }
        for(int i = 0; i < Test.GENERAL_VENTILATIONMODE_LIST.size(); i++){
            assertEquals(Test.MATCH, Test.GENERAL_VENTILATIONMODE_LIST.get(i), ventilationMode.get(i));
        }

        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, heatedSteeringWheelAvailable);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, heatedWindshieldAvailable);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, heatedRearWindowAvailable);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, heatedMirrorsAvailable);
        // Invalid/Null Tests
        ClimateControlCapabilities msg = new ClimateControlCapabilities();
        assertNotNull(Test.NOT_NULL, msg);

        assertNull(Test.NULL, msg.getModuleName());
        assertNull(Test.NULL, msg.getFanSpeedAvailable());
        assertNull(Test.NULL, msg.getDesiredTemperatureAvailable());
        assertNull(Test.NULL, msg.getAcEnableAvailable());
        assertNull(Test.NULL, msg.getAcMaxEnableAvailable());
        assertNull(Test.NULL, msg.getAutoModeEnableAvailable());
        assertNull(Test.NULL, msg.getDualModeEnableAvailable());
        assertNull(Test.NULL, msg.getDefrostZoneAvailable());
        assertNull(Test.NULL, msg.getDefrostZone());
        assertNull(Test.NULL, msg.getVentilationModeAvailable());
        assertNull(Test.NULL, msg.getVentilationMode());
        assertNull(Test.NULL, msg.getHeatedSteeringWheelAvailable());
        assertNull(Test.NULL, msg.getHeatedWindshieldAvailable());
        assertNull(Test.NULL, msg.getHeatedRearWindowAvailable());
        assertNull(Test.NULL, msg.getHeatedMirrorsAvailable());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(ClimateControlCapabilities.KEY_MODULE_NAME, Test.GENERAL_STRING);
            reference.put(ClimateControlCapabilities.KEY_FAN_SPEED_AVAILABLE, Test.GENERAL_BOOLEAN);
            reference.put(ClimateControlCapabilities.KEY_DESIRED_TEMPERATURE_AVAILABLE, Test.GENERAL_BOOLEAN);
            reference.put(ClimateControlCapabilities.KEY_AC_ENABLE_AVAILABLE, Test.GENERAL_BOOLEAN);
            reference.put(ClimateControlCapabilities.KEY_AC_MAX_ENABLE_AVAILABLE, Test.GENERAL_BOOLEAN);
            reference.put(ClimateControlCapabilities.KEY_CIRCULATE_AIR_ENABLE_AVAILABLE, Test.GENERAL_BOOLEAN);
            reference.put(ClimateControlCapabilities.KEY_AUTO_MODE_ENABLE_AVAILABLE, Test.GENERAL_BOOLEAN);
            reference.put(ClimateControlCapabilities.KEY_DUAL_MODE_ENABLE_AVAILABLE, Test.GENERAL_BOOLEAN);
            reference.put(ClimateControlCapabilities.KEY_DEFROST_ZONE_AVAILABLE, Test.GENERAL_BOOLEAN);
            reference.put(ClimateControlCapabilities.KEY_VENTILATION_MODE_AVAILABLE, Test.GENERAL_BOOLEAN);
            reference.put(ClimateControlCapabilities.KEY_DEFROST_ZONE, JsonUtils.createJsonArray(Test.GENERAL_DEFROSTZONE_LIST));
            reference.put(ClimateControlCapabilities.KEY_VENTILATION_MODE, JsonUtils.createJsonArray(Test.GENERAL_VENTILATIONMODE_LIST));
            reference.put(ClimateControlCapabilities.KEY_HEATED_STEERING_WHEEL_AVAILABLE, Test.GENERAL_BOOLEAN);
            reference.put(ClimateControlCapabilities.KEY_HEATED_WIND_SHIELD_AVAILABLE, Test.GENERAL_BOOLEAN);
            reference.put(ClimateControlCapabilities.KEY_HEATED_REAR_WINDOW_AVAILABLE, Test.GENERAL_BOOLEAN);
            reference.put(ClimateControlCapabilities.KEY_HEATED_MIRRORS_AVAILABLE, Test.GENERAL_BOOLEAN);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(Test.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();

                if(key.equals(ClimateControlCapabilities.KEY_DEFROST_ZONE)) {
                    JSONArray defrostZoneArrayReference = JsonUtils.readJsonArrayFromJsonObject(reference, key);
                    JSONArray defrostZoneArrayTest = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
                    List<DefrostZone> defrostZoneListReference = new ArrayList<DefrostZone>();
                    List<DefrostZone> defrostZoneListTest = new ArrayList<DefrostZone>();

                    assertEquals(Test.MATCH, defrostZoneArrayReference.length(), defrostZoneArrayTest.length());

                    for (int index = 0 ; index < defrostZoneArrayReference.length(); index++) {
                        defrostZoneListReference.add( (DefrostZone)defrostZoneArrayReference.get(index) );
                        defrostZoneListTest.add( (DefrostZone)defrostZoneArrayTest.get(index) );
                    }
                    assertTrue(Test.TRUE, defrostZoneListReference.containsAll(defrostZoneListTest) && defrostZoneListTest.containsAll(defrostZoneListReference));
                } else if(key.equals(ClimateControlCapabilities.KEY_VENTILATION_MODE)) {
                    JSONArray ventilationModeArrayReference = JsonUtils.readJsonArrayFromJsonObject(reference, key);
                    JSONArray ventilationModeArrayTest = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
                    List<VentilationMode> ventilationModeListReference = new ArrayList<VentilationMode>();
                    List<VentilationMode> ventilationModeListTest = new ArrayList<VentilationMode>();

                    assertEquals(Test.MATCH, ventilationModeArrayReference.length(), ventilationModeArrayTest.length());

                    for (int index = 0 ; index < ventilationModeArrayReference.length(); index++) {
                        ventilationModeListReference.add( (VentilationMode)ventilationModeArrayReference.get(index) );
                        ventilationModeListTest.add( (VentilationMode)ventilationModeArrayTest.get(index) );
                    }
                    assertTrue(Test.TRUE, ventilationModeListReference.containsAll(ventilationModeListTest) && ventilationModeListTest.containsAll(ventilationModeListReference));
                } else{
                    assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
                }
            }
        } catch(JSONException e){
        	fail(Test.JSON_FAIL);
        }
    }
}