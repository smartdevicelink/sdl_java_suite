package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.RadioControlData;
import com.smartdevicelink.proxy.rpc.RdsData;
import com.smartdevicelink.proxy.rpc.SisData;
import com.smartdevicelink.proxy.rpc.enums.RadioBand;
import com.smartdevicelink.proxy.rpc.enums.RadioState;
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
 * {@link com.smartdevicelink.rpc.RadioControlData}
 */
public class RadioControlDataTests extends TestCase{
	
    private RadioControlData msg;

    @Override
    public void setUp(){
        msg = new RadioControlData();

        msg.setFrequencyInteger(Test.GENERAL_INT);
        msg.setFrequencyFraction(Test.GENERAL_INT);
        msg.setBand(Test.GENERAL_RADIOBAND);
        msg.setRdsData(Test.GENERAL_RDSDATA);
        msg.setAvailableHDs(Test.GENERAL_INT);
        msg.setHdChannel(Test.GENERAL_INT);
        msg.setSignalStrength(Test.GENERAL_INT);
        msg.setSignalChangeThreshold(Test.GENERAL_INT);
        msg.setRadioEnable(Test.GENERAL_BOOLEAN);
        msg.setState(Test.GENERAL_RADIOSTATE);
        msg.setHdRadioEnable(Test.GENERAL_BOOLEAN);
        msg.setSisData(Test.GENERAL_SISDATA);
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
        // Test Values
        int frequencyInteger = msg.getFrequencyInteger();
        int frequencyFraction = msg.getFrequencyFraction();
        RadioBand band = msg.getBand();
        RdsData rdsData = msg.getRdsData();
        int availableHDs = msg.getAvailableHDs();
        int hdChannel = msg.getHdChannel();
        int signalStrength = msg.getSignalStrength();
        int signalChangeThreshold = msg.getSignalChangeThreshold();
        boolean radioEnable = msg.getRadioEnable();
        RadioState state = msg.getState();
        boolean hdRadioEnable = msg.getHdRadioEnable();
        SisData sisData = msg.getSisData();

        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_INT, frequencyInteger);
        assertEquals(Test.MATCH, Test.GENERAL_INT, frequencyFraction);
        assertEquals(Test.MATCH, Test.GENERAL_RADIOBAND, band);
        assertTrue(Test.TRUE, Validator.validateRdsData(Test.GENERAL_RDSDATA, rdsData));
        assertEquals(Test.MATCH, Test.GENERAL_INT, availableHDs);
        assertEquals(Test.MATCH, Test.GENERAL_INT, hdChannel);
        assertEquals(Test.MATCH, Test.GENERAL_INT, signalStrength);
        assertEquals(Test.MATCH, Test.GENERAL_INT, signalChangeThreshold);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, radioEnable);
        assertEquals(Test.MATCH, Test.GENERAL_RADIOSTATE, state);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, hdRadioEnable);
        assertTrue(Test.TRUE, Validator.validateSisData(Test.GENERAL_SISDATA, sisData));

        // Invalid/Null Tests
        RadioControlData msg = new RadioControlData();
        assertNotNull(Test.NOT_NULL, msg);

        assertNull(Test.NULL, msg.getFrequencyInteger());
        assertNull(Test.NULL, msg.getFrequencyFraction());
        assertNull(Test.NULL, msg.getBand());
        assertNull(Test.NULL, msg.getRdsData());
        assertNull(Test.NULL, msg.getAvailableHDs());
        assertNull(Test.NULL, msg.getHdChannel());
        assertNull(Test.NULL, msg.getSignalStrength());
        assertNull(Test.NULL, msg.getSignalChangeThreshold());
        assertNull(Test.NULL, msg.getRadioEnable());
        assertNull(Test.NULL, msg.getState());
        assertNull(Test.NULL, msg.getHdRadioEnable());
        assertNull(Test.NULL, msg.getSisData());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(RadioControlData.KEY_FREQUENCY_INTEGER, Test.GENERAL_INT);
            reference.put(RadioControlData.KEY_FREQUENCY_FRACTION, Test.GENERAL_INT);
            reference.put(RadioControlData.KEY_BAND, Test.GENERAL_RADIOBAND);
            reference.put(RadioControlData.KEY_RDS_DATA, JsonRPCMarshaller.serializeHashtable(Test.GENERAL_RDSDATA.getStore()));
            reference.put(RadioControlData.KEY_AVAILABLE_HDS, Test.GENERAL_INT);
            reference.put(RadioControlData.KEY_HD_CHANNEL, Test.GENERAL_INT);
            reference.put(RadioControlData.KEY_SIGNAL_STRENGTH, Test.GENERAL_INT);
            reference.put(RadioControlData.KEY_SIGNAL_CHANGE_THRESHOLD, Test.GENERAL_INT);
            reference.put(RadioControlData.KEY_RADIO_ENABLE, Test.GENERAL_BOOLEAN);
            reference.put(RadioControlData.KEY_STATE, Test.GENERAL_RADIOSTATE);
            reference.put(RadioControlData.KEY_HD_RADIO_ENABLE, Test.GENERAL_BOOLEAN);
            reference.put(RadioControlData.KEY_SIS_DATA, JsonRPCMarshaller.serializeHashtable(Test.GENERAL_SISDATA.getStore()));

            JSONObject underTest = msg.serializeJSON();
            assertEquals(Test.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();

                if(key.equals(RadioControlData.KEY_RDS_DATA)){
                    JSONObject objectEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
                    JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
                    Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
                    Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
                    assertTrue(Test.TRUE, Validator.validateRdsData(new RdsData(hashReference), new RdsData(hashTest)));
                } else if (key.equals(RadioControlData.KEY_SIS_DATA)) {
	                JSONObject objectEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
	                JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
	                Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
	                Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
	                assertTrue(Test.TRUE, Validator.validateSisData(new SisData(hashReference), new SisData(hashTest)));
                } else{
                    assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
                }
            }
        } catch(JSONException e){
        	fail(Test.JSON_FAIL);
        }
    }
}