package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.RadioControlCapabilities;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.rpc.RadioControlCapabilities}
 */
public class RadioControlCapabilitiesTests extends TestCase{
	
    private RadioControlCapabilities msg;

    @Override
    public void setUp(){
        msg = new RadioControlCapabilities();

        msg.setModuleName(Test.GENERAL_STRING);
        msg.setRadioEnableAvailable(Test.GENERAL_BOOLEAN);
        msg.setRadioBandAvailable(Test.GENERAL_BOOLEAN);
        msg.setRadioFrequencyAvailable(Test.GENERAL_BOOLEAN);
        msg.setHdChannelAvailable(Test.GENERAL_BOOLEAN);
        msg.setRdsDataAvailable(Test.GENERAL_BOOLEAN);
        msg.setAvailableHDsAvailable(Test.GENERAL_BOOLEAN);
        msg.setStateAvailable(Test.GENERAL_BOOLEAN);
        msg.setSignalStrengthAvailable(Test.GENERAL_BOOLEAN);
        msg.setSignalChangeThresholdAvailable(Test.GENERAL_BOOLEAN);
        msg.setHdRadioEnableAvailable(Test.GENERAL_BOOLEAN);
        msg.setSiriusXMRadioAvailable(Test.GENERAL_BOOLEAN);
        msg.setSisDataAvailable(Test.GENERAL_BOOLEAN);
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
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


        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_STRING, moduleName);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, radioEnableAvailable);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, radioBandAvailable);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, radioFrequencyAvailable);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, hdChannelAvailable);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, rdsDataAvailable);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, availableHDsAvailable);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, stateAvailable);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, signalStrengthAvailable);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, signalChangeThresholdAvailable);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, hdRadioEnableAvailable);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, siriusXMRadioAvailable);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, sisDataAvailable);

        // Invalid/Null Tests
        RadioControlCapabilities msg = new RadioControlCapabilities();
        assertNotNull(Test.NOT_NULL, msg);

        assertNull(Test.NULL, msg.getModuleName());
        assertNull(Test.NULL, msg.getRadioEnableAvailable());
        assertNull(Test.NULL, msg.getRadioBandAvailable());
        assertNull(Test.NULL, msg.getRadioFrequencyAvailable());
        assertNull(Test.NULL, msg.getHdChannelAvailable());
        assertNull(Test.NULL, msg.getRdsDataAvailable());
        assertNull(Test.NULL, msg.getAvailableHDsAvailable());
        assertNull(Test.NULL, msg.getStateAvailable());
        assertNull(Test.NULL, msg.getSignalStrengthAvailable());
        assertNull(Test.NULL, msg.getSignalChangeThresholdAvailable());
        assertNull(Test.NULL, msg.getHdRadioEnableAvailable());
        assertNull(Test.NULL, msg.getSiriusXMRadioAvailable());
        assertNull(Test.NULL, msg.getSisDataAvailable());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(RadioControlCapabilities.KEY_MODULE_NAME, Test.GENERAL_STRING);
            reference.put(RadioControlCapabilities.KEY_RADIO_ENABLE_AVAILABLE, Test.GENERAL_BOOLEAN);
            reference.put(RadioControlCapabilities.KEY_RADIO_BAND_AVAILABLE, Test.GENERAL_BOOLEAN);
            reference.put(RadioControlCapabilities.KEY_RADIO_FREQUENCY_AVAILABLE, Test.GENERAL_BOOLEAN);
            reference.put(RadioControlCapabilities.KEY_HD_CHANNEL_AVAILABLE, Test.GENERAL_BOOLEAN);
            reference.put(RadioControlCapabilities.KEY_RDS_DATA_AVAILABLE, Test.GENERAL_BOOLEAN);
            reference.put(RadioControlCapabilities.KEY_AVAILABLE_HDS_AVAILABLE, Test.GENERAL_BOOLEAN);
            reference.put(RadioControlCapabilities.KEY_STATE_AVAILABLE, Test.GENERAL_BOOLEAN);
            reference.put(RadioControlCapabilities.KEY_SIGNAL_STRENGTH_AVAILABLE, Test.GENERAL_BOOLEAN);
            reference.put(RadioControlCapabilities.KEY_SIGNAL_CHANGE_THRESHOLD_AVAILABLE, Test.GENERAL_BOOLEAN);
            reference.put(RadioControlCapabilities.KEY_HD_RADIO_ENABLE_AVAILABLE, Test.GENERAL_BOOLEAN);
            reference.put(RadioControlCapabilities.KEY_SIRIUS_XM_RADIO_AVAILABLE, Test.GENERAL_BOOLEAN);
            reference.put(RadioControlCapabilities.KEY_SIS_DATA_AVAILABLE, Test.GENERAL_BOOLEAN);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(Test.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();

                assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));

            }
        } catch(JSONException e){
        	fail(Test.JSON_FAIL);
        }
    }
}