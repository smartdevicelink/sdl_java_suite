package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.ClusterModeStatus;
import com.smartdevicelink.proxy.rpc.enums.CarModeStatus;
import com.smartdevicelink.proxy.rpc.enums.PowerModeQualificationStatus;
import com.smartdevicelink.proxy.rpc.enums.PowerModeStatus;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.ClusterModeStatus}
 */
public class ClusterModeStatusTests extends TestCase {

    private ClusterModeStatus msg;

    @Override
    public void setUp() {
        msg = new ClusterModeStatus();

        msg.setPowerModeActive(TestValues.GENERAL_BOOLEAN);
        msg.setCarModeStatus(TestValues.GENERAL_CARMODESTATUS);
        msg.setPowerModeQualificationStatus(TestValues.GENERAL_POWERMODEQUALIFICATIONSTATUS);
        msg.setPowerModeStatus(TestValues.GENERAL_POWERMODESTATUS);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        // Test Values
        boolean powerMode = msg.getPowerModeActive();
        PowerModeQualificationStatus qualification = msg.getPowerModeQualificationStatus();
        PowerModeStatus status = msg.getPowerModeStatus();
        CarModeStatus carStatus = msg.getCarModeStatus();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, powerMode);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_POWERMODEQUALIFICATIONSTATUS, qualification);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_POWERMODESTATUS, status);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_CARMODESTATUS, carStatus);

        // Invalid/Null Tests
        ClusterModeStatus msg = new ClusterModeStatus();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getPowerModeActive());
        assertNull(TestValues.NULL, msg.getPowerModeStatus());
        assertNull(TestValues.NULL, msg.getPowerModeQualificationStatus());
        assertNull(TestValues.NULL, msg.getCarModeStatus());
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(ClusterModeStatus.KEY_POWER_MODE_ACTIVE, TestValues.GENERAL_BOOLEAN);
            reference.put(ClusterModeStatus.KEY_POWER_MODE_STATUS, TestValues.GENERAL_POWERMODESTATUS);
            reference.put(ClusterModeStatus.KEY_POWER_MODE_QUALIFICATION_STATUS, TestValues.GENERAL_POWERMODEQUALIFICATIONSTATUS);
            reference.put(ClusterModeStatus.KEY_CAR_MODE_STATUS, TestValues.GENERAL_CARMODESTATUS);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
            }
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}