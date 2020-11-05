package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.ClimateControlCapabilities;
import com.smartdevicelink.proxy.rpc.ModuleInfo;
import com.smartdevicelink.proxy.rpc.enums.DefrostZone;
import com.smartdevicelink.proxy.rpc.enums.VentilationMode;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.ClimateControlCapabilities}
 */
public class ClimateControlCapabilitiesTests extends TestCase {

    private ClimateControlCapabilities msg;

    @Override
    public void setUp() {
        msg = new ClimateControlCapabilities();

        msg.setModuleName(TestValues.GENERAL_STRING);
        msg.setFanSpeedAvailable(TestValues.GENERAL_BOOLEAN);
        msg.setDesiredTemperatureAvailable(TestValues.GENERAL_BOOLEAN);
        msg.setCurrentTemperatureAvailable(TestValues.GENERAL_BOOLEAN);
        msg.setAcEnableAvailable(TestValues.GENERAL_BOOLEAN);
        msg.setAcMaxEnableAvailable(TestValues.GENERAL_BOOLEAN);
        msg.setCirculateAirEnableAvailable(TestValues.GENERAL_BOOLEAN);
        msg.setAutoModeEnableAvailable(TestValues.GENERAL_BOOLEAN);
        msg.setDualModeEnableAvailable(TestValues.GENERAL_BOOLEAN);
        msg.setDefrostZoneAvailable(TestValues.GENERAL_BOOLEAN);
        msg.setDefrostZone(TestValues.GENERAL_DEFROSTZONE_LIST);
        msg.setVentilationModeAvailable(TestValues.GENERAL_BOOLEAN);
        msg.setVentilationMode(TestValues.GENERAL_VENTILATIONMODE_LIST);
        msg.setHeatedSteeringWheelAvailable(TestValues.GENERAL_BOOLEAN);
        msg.setHeatedWindshieldAvailable(TestValues.GENERAL_BOOLEAN);
        msg.setHeatedRearWindowAvailable(TestValues.GENERAL_BOOLEAN);
        msg.setHeatedMirrorsAvailable(TestValues.GENERAL_BOOLEAN);
        msg.setModuleInfo(TestValues.GENERAL_MODULE_INFO);
        msg.setClimateEnableAvailable(TestValues.GENERAL_BOOLEAN);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        // Test Values
        String moduleName = msg.getModuleName();
        boolean fanSpeedAvailable = msg.getFanSpeedAvailable();
        boolean desiredTemperatureAvailable = msg.getDesiredTemperatureAvailable();
        boolean currentTemperatureAvailable = msg.getCurrentTemperatureAvailable();
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
        ModuleInfo info = msg.getModuleInfo();
        boolean climateEnableAvailable = msg.getClimateEnableAvailable();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, moduleName);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, fanSpeedAvailable);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, desiredTemperatureAvailable);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, currentTemperatureAvailable);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, acEnableAvailable);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, acMaxEnableAvailable);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, circulateAirEnableAvailable);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, autoModeEnableAvailable);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, dualModeEnableAvailable);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, defrostZoneAvailable);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, ventilationModeAvailable);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_MODULE_INFO, info);

        assertEquals(TestValues.MATCH, TestValues.GENERAL_DEFROSTZONE_LIST.size(), defrostZone.size());
        assertEquals(TestValues.MATCH, TestValues.GENERAL_VENTILATIONMODE_LIST.size(), ventilationMode.size());

        for (int i = 0; i < TestValues.GENERAL_DEFROSTZONE_LIST.size(); i++) {
            assertEquals(TestValues.MATCH, TestValues.GENERAL_DEFROSTZONE_LIST.get(i), defrostZone.get(i));
        }
        for (int i = 0; i < TestValues.GENERAL_VENTILATIONMODE_LIST.size(); i++) {
            assertEquals(TestValues.MATCH, TestValues.GENERAL_VENTILATIONMODE_LIST.get(i), ventilationMode.get(i));
        }

        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, heatedSteeringWheelAvailable);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, heatedWindshieldAvailable);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, heatedRearWindowAvailable);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, heatedMirrorsAvailable);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, climateEnableAvailable);

        // Invalid/Null Tests
        ClimateControlCapabilities msg = new ClimateControlCapabilities();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getModuleName());
        assertNull(TestValues.NULL, msg.getFanSpeedAvailable());
        assertNull(TestValues.NULL, msg.getDesiredTemperatureAvailable());
        assertNull(TestValues.NULL, msg.getCurrentTemperatureAvailable());
        assertNull(TestValues.NULL, msg.getAcEnableAvailable());
        assertNull(TestValues.NULL, msg.getAcMaxEnableAvailable());
        assertNull(TestValues.NULL, msg.getAutoModeEnableAvailable());
        assertNull(TestValues.NULL, msg.getDualModeEnableAvailable());
        assertNull(TestValues.NULL, msg.getDefrostZoneAvailable());
        assertNull(TestValues.NULL, msg.getDefrostZone());
        assertNull(TestValues.NULL, msg.getVentilationModeAvailable());
        assertNull(TestValues.NULL, msg.getVentilationMode());
        assertNull(TestValues.NULL, msg.getHeatedSteeringWheelAvailable());
        assertNull(TestValues.NULL, msg.getHeatedWindshieldAvailable());
        assertNull(TestValues.NULL, msg.getHeatedRearWindowAvailable());
        assertNull(TestValues.NULL, msg.getHeatedMirrorsAvailable());
        assertNull(TestValues.NULL, msg.getModuleInfo());
        assertNull(TestValues.NULL, msg.getClimateEnableAvailable());
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(ClimateControlCapabilities.KEY_MODULE_NAME, TestValues.GENERAL_STRING);
            reference.put(ClimateControlCapabilities.KEY_FAN_SPEED_AVAILABLE, TestValues.GENERAL_BOOLEAN);
            reference.put(ClimateControlCapabilities.KEY_DESIRED_TEMPERATURE_AVAILABLE, TestValues.GENERAL_BOOLEAN);
            reference.put(ClimateControlCapabilities.KEY_CURRENT_TEMPERATURE_AVAILABLE, TestValues.GENERAL_BOOLEAN);
            reference.put(ClimateControlCapabilities.KEY_AC_ENABLE_AVAILABLE, TestValues.GENERAL_BOOLEAN);
            reference.put(ClimateControlCapabilities.KEY_AC_MAX_ENABLE_AVAILABLE, TestValues.GENERAL_BOOLEAN);
            reference.put(ClimateControlCapabilities.KEY_CIRCULATE_AIR_ENABLE_AVAILABLE, TestValues.GENERAL_BOOLEAN);
            reference.put(ClimateControlCapabilities.KEY_AUTO_MODE_ENABLE_AVAILABLE, TestValues.GENERAL_BOOLEAN);
            reference.put(ClimateControlCapabilities.KEY_DUAL_MODE_ENABLE_AVAILABLE, TestValues.GENERAL_BOOLEAN);
            reference.put(ClimateControlCapabilities.KEY_DEFROST_ZONE_AVAILABLE, TestValues.GENERAL_BOOLEAN);
            reference.put(ClimateControlCapabilities.KEY_VENTILATION_MODE_AVAILABLE, TestValues.GENERAL_BOOLEAN);
            reference.put(ClimateControlCapabilities.KEY_DEFROST_ZONE, JsonUtils.createJsonArray(TestValues.GENERAL_DEFROSTZONE_LIST));
            reference.put(ClimateControlCapabilities.KEY_VENTILATION_MODE, JsonUtils.createJsonArray(TestValues.GENERAL_VENTILATIONMODE_LIST));
            reference.put(ClimateControlCapabilities.KEY_HEATED_STEERING_WHEEL_AVAILABLE, TestValues.GENERAL_BOOLEAN);
            reference.put(ClimateControlCapabilities.KEY_HEATED_WIND_SHIELD_AVAILABLE, TestValues.GENERAL_BOOLEAN);
            reference.put(ClimateControlCapabilities.KEY_HEATED_REAR_WINDOW_AVAILABLE, TestValues.GENERAL_BOOLEAN);
            reference.put(ClimateControlCapabilities.KEY_HEATED_MIRRORS_AVAILABLE, TestValues.GENERAL_BOOLEAN);
            reference.put(ClimateControlCapabilities.KEY_MODULE_INFO, TestValues.JSON_MODULE_INFO);
            reference.put(ClimateControlCapabilities.KEY_CLIMATE_ENABLE_AVAILABLE, TestValues.GENERAL_BOOLEAN);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();

                if (key.equals(ClimateControlCapabilities.KEY_DEFROST_ZONE)) {
                    JSONArray defrostZoneArrayReference = JsonUtils.readJsonArrayFromJsonObject(reference, key);
                    JSONArray defrostZoneArrayTest = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
                    List<DefrostZone> defrostZoneListReference = new ArrayList<DefrostZone>();
                    List<DefrostZone> defrostZoneListTest = new ArrayList<DefrostZone>();

                    assertEquals(TestValues.MATCH, defrostZoneArrayReference.length(), defrostZoneArrayTest.length());

                    for (int index = 0; index < defrostZoneArrayReference.length(); index++) {
                        defrostZoneListReference.add((DefrostZone) defrostZoneArrayReference.get(index));
                        defrostZoneListTest.add((DefrostZone) defrostZoneArrayTest.get(index));
                    }
                    assertTrue(TestValues.TRUE, defrostZoneListReference.containsAll(defrostZoneListTest) && defrostZoneListTest.containsAll(defrostZoneListReference));
                } else if (key.equals(ClimateControlCapabilities.KEY_VENTILATION_MODE)) {
                    JSONArray ventilationModeArrayReference = JsonUtils.readJsonArrayFromJsonObject(reference, key);
                    JSONArray ventilationModeArrayTest = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
                    List<VentilationMode> ventilationModeListReference = new ArrayList<VentilationMode>();
                    List<VentilationMode> ventilationModeListTest = new ArrayList<VentilationMode>();

                    assertEquals(TestValues.MATCH, ventilationModeArrayReference.length(), ventilationModeArrayTest.length());

                    for (int index = 0; index < ventilationModeArrayReference.length(); index++) {
                        ventilationModeListReference.add((VentilationMode) ventilationModeArrayReference.get(index));
                        ventilationModeListTest.add((VentilationMode) ventilationModeArrayTest.get(index));
                    }
                    assertTrue(TestValues.TRUE, ventilationModeListReference.containsAll(ventilationModeListTest) && ventilationModeListTest.containsAll(ventilationModeListReference));
                } else if (key.equals(ClimateControlCapabilities.KEY_MODULE_INFO)) {
                    JSONObject o1 = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
                    JSONObject o2 = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
                    Hashtable<String, Object> h1 = JsonRPCMarshaller.deserializeJSONObject(o1);
                    Hashtable<String, Object> h2 = JsonRPCMarshaller.deserializeJSONObject(o2);
                    assertTrue(TestValues.TRUE, Validator.validateModuleInfo(new ModuleInfo(h1), new ModuleInfo(h2)));
                } else {
                    assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
                }
            }
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}