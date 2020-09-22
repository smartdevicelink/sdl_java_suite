package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.ModuleInfo;
import com.smartdevicelink.proxy.rpc.RadioControlCapabilities;
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
 * {@link com.smartdevicelink.proxy.rpc.RadioControlCapabilities}
 */
public class RadioControlCapabilitiesTests extends TestCase {

    private RadioControlCapabilities msg;

    @Override
    public void setUp() {
        msg = new RadioControlCapabilities();

        msg.setModuleName(TestValues.GENERAL_STRING);
        msg.setRadioEnableAvailable(TestValues.GENERAL_BOOLEAN);
        msg.setRadioBandAvailable(TestValues.GENERAL_BOOLEAN);
        msg.setRadioFrequencyAvailable(TestValues.GENERAL_BOOLEAN);
        msg.setHdChannelAvailable(TestValues.GENERAL_BOOLEAN);
        msg.setRdsDataAvailable(TestValues.GENERAL_BOOLEAN);
        msg.setAvailableHDsAvailable(TestValues.GENERAL_BOOLEAN);
        msg.setStateAvailable(TestValues.GENERAL_BOOLEAN);
        msg.setSignalStrengthAvailable(TestValues.GENERAL_BOOLEAN);
        msg.setSignalChangeThresholdAvailable(TestValues.GENERAL_BOOLEAN);
        msg.setHdRadioEnableAvailable(TestValues.GENERAL_BOOLEAN);
        msg.setSiriusXMRadioAvailable(TestValues.GENERAL_BOOLEAN);
        msg.setSisDataAvailable(TestValues.GENERAL_BOOLEAN);
        msg.setModuleInfo(TestValues.GENERAL_MODULE_INFO);
        msg.setAvailableHdChannelsAvailable(TestValues.GENERAL_BOOLEAN);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        // Test Values
        String moduleName = msg.getModuleName();
        boolean radioEnableAvailable = msg.getRadioEnableAvailable();
        boolean radioBandAvailable = msg.getRadioBandAvailable();
        boolean radioFrequencyAvailable = msg.getRadioFrequencyAvailable();
        boolean hdChannelAvailable = msg.getHdChannelAvailable();
        boolean rdsDataAvailable = msg.getRdsDataAvailable();
        boolean availableHDsAvailable = msg.getAvailableHDsAvailable();
        boolean stateAvailable = msg.getStateAvailable();
        boolean signalStrengthAvailable = msg.getSignalStrengthAvailable();
        boolean signalChangeThresholdAvailable = msg.getSignalChangeThresholdAvailable();
        boolean hdRadioEnableAvailable = msg.getHdRadioEnableAvailable();
        boolean siriusXMRadioAvailable = msg.getSiriusXMRadioAvailable();
        boolean sisDataAvailable = msg.getSisDataAvailable();
        ModuleInfo info = msg.getModuleInfo();
        boolean availableHdChannelsAvailable = msg.getAvailableHdChannelsAvailable();


        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, moduleName);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, radioEnableAvailable);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, radioBandAvailable);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, radioFrequencyAvailable);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, hdChannelAvailable);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, rdsDataAvailable);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, availableHDsAvailable);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, stateAvailable);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, signalStrengthAvailable);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, signalChangeThresholdAvailable);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, hdRadioEnableAvailable);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, siriusXMRadioAvailable);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, sisDataAvailable);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_MODULE_INFO, info);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, availableHdChannelsAvailable);

        // Invalid/Null Tests
        RadioControlCapabilities msg = new RadioControlCapabilities();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getModuleName());
        assertNull(TestValues.NULL, msg.getRadioEnableAvailable());
        assertNull(TestValues.NULL, msg.getRadioBandAvailable());
        assertNull(TestValues.NULL, msg.getRadioFrequencyAvailable());
        assertNull(TestValues.NULL, msg.getHdChannelAvailable());
        assertNull(TestValues.NULL, msg.getRdsDataAvailable());
        assertNull(TestValues.NULL, msg.getAvailableHDsAvailable());
        assertNull(TestValues.NULL, msg.getStateAvailable());
        assertNull(TestValues.NULL, msg.getSignalStrengthAvailable());
        assertNull(TestValues.NULL, msg.getSignalChangeThresholdAvailable());
        assertNull(TestValues.NULL, msg.getHdRadioEnableAvailable());
        assertNull(TestValues.NULL, msg.getSiriusXMRadioAvailable());
        assertNull(TestValues.NULL, msg.getSisDataAvailable());
        assertNull(TestValues.NULL, msg.getModuleInfo());
        assertNull(TestValues.NULL, msg.getAvailableHdChannelsAvailable());
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(RadioControlCapabilities.KEY_MODULE_NAME, TestValues.GENERAL_STRING);
            reference.put(RadioControlCapabilities.KEY_RADIO_ENABLE_AVAILABLE, TestValues.GENERAL_BOOLEAN);
            reference.put(RadioControlCapabilities.KEY_RADIO_BAND_AVAILABLE, TestValues.GENERAL_BOOLEAN);
            reference.put(RadioControlCapabilities.KEY_RADIO_FREQUENCY_AVAILABLE, TestValues.GENERAL_BOOLEAN);
            reference.put(RadioControlCapabilities.KEY_HD_CHANNEL_AVAILABLE, TestValues.GENERAL_BOOLEAN);
            reference.put(RadioControlCapabilities.KEY_RDS_DATA_AVAILABLE, TestValues.GENERAL_BOOLEAN);
            reference.put(RadioControlCapabilities.KEY_AVAILABLE_HDS_AVAILABLE, TestValues.GENERAL_BOOLEAN);
            reference.put(RadioControlCapabilities.KEY_STATE_AVAILABLE, TestValues.GENERAL_BOOLEAN);
            reference.put(RadioControlCapabilities.KEY_SIGNAL_STRENGTH_AVAILABLE, TestValues.GENERAL_BOOLEAN);
            reference.put(RadioControlCapabilities.KEY_SIGNAL_CHANGE_THRESHOLD_AVAILABLE, TestValues.GENERAL_BOOLEAN);
            reference.put(RadioControlCapabilities.KEY_HD_RADIO_ENABLE_AVAILABLE, TestValues.GENERAL_BOOLEAN);
            reference.put(RadioControlCapabilities.KEY_SIRIUS_XM_RADIO_AVAILABLE, TestValues.GENERAL_BOOLEAN);
            reference.put(RadioControlCapabilities.KEY_SIS_DATA_AVAILABLE, TestValues.GENERAL_BOOLEAN);
            reference.put(RadioControlCapabilities.KEY_MODULE_INFO, TestValues.JSON_MODULE_INFO);
            reference.put(RadioControlCapabilities.KEY_AVAILABLE_HD_CHANNELS_AVAILABLE, TestValues.GENERAL_BOOLEAN);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                if (key.equals(RadioControlCapabilities.KEY_MODULE_INFO)) {
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