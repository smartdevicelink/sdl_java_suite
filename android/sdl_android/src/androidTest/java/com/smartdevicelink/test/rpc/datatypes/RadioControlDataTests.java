package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.RadioControlData;
import com.smartdevicelink.proxy.rpc.RdsData;
import com.smartdevicelink.proxy.rpc.SisData;
import com.smartdevicelink.proxy.rpc.enums.RadioBand;
import com.smartdevicelink.proxy.rpc.enums.RadioState;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.RadioControlData}
 */
public class RadioControlDataTests extends TestCase{
	
    private RadioControlData msg;

    @Override
    public void setUp(){
        msg = new RadioControlData();

        msg.setFrequencyInteger(TestValues.GENERAL_INT);
        msg.setFrequencyFraction(TestValues.GENERAL_INT);
        msg.setBand(TestValues.GENERAL_RADIOBAND);
        msg.setRdsData(TestValues.GENERAL_RDSDATA);
        msg.setAvailableHDs(TestValues.GENERAL_INT);
        msg.setHdChannel(TestValues.GENERAL_INT);
        msg.setSignalStrength(TestValues.GENERAL_INT);
        msg.setSignalChangeThreshold(TestValues.GENERAL_INT);
        msg.setRadioEnable(TestValues.GENERAL_BOOLEAN);
        msg.setState(TestValues.GENERAL_RADIOSTATE);
        msg.setHdRadioEnable(TestValues.GENERAL_BOOLEAN);
        msg.setSisData(TestValues.GENERAL_SISDATA);
        msg.setAvailableHdChannels(TestValues.GENERAL_AVAILABLE_HD_CHANNELS_LIST);
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
        List<Integer> availableHdChannels = msg.getAvailableHdChannels();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, frequencyInteger);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, frequencyFraction);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_RADIOBAND, band);
        assertTrue(TestValues.TRUE, Validator.validateRdsData(TestValues.GENERAL_RDSDATA, rdsData));
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, availableHDs);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, hdChannel);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, signalStrength);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, signalChangeThreshold);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, radioEnable);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_RADIOSTATE, state);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, hdRadioEnable);
        assertTrue(TestValues.TRUE, Validator.validateSisData(TestValues.GENERAL_SISDATA, sisData));
        assertEquals(TestValues.MATCH, TestValues.GENERAL_AVAILABLE_HD_CHANNELS_LIST, availableHdChannels);

        // Invalid/Null Tests
        RadioControlData msg = new RadioControlData();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getFrequencyInteger());
        assertNull(TestValues.NULL, msg.getFrequencyFraction());
        assertNull(TestValues.NULL, msg.getBand());
        assertNull(TestValues.NULL, msg.getRdsData());
        assertNull(TestValues.NULL, msg.getAvailableHDs());
        assertNull(TestValues.NULL, msg.getHdChannel());
        assertNull(TestValues.NULL, msg.getSignalStrength());
        assertNull(TestValues.NULL, msg.getSignalChangeThreshold());
        assertNull(TestValues.NULL, msg.getRadioEnable());
        assertNull(TestValues.NULL, msg.getState());
        assertNull(TestValues.NULL, msg.getHdRadioEnable());
        assertNull(TestValues.NULL, msg.getSisData());
        assertNull(TestValues.NULL, msg.getAvailableHdChannels());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(RadioControlData.KEY_FREQUENCY_INTEGER, TestValues.GENERAL_INT);
            reference.put(RadioControlData.KEY_FREQUENCY_FRACTION, TestValues.GENERAL_INT);
            reference.put(RadioControlData.KEY_BAND, TestValues.GENERAL_RADIOBAND);
            reference.put(RadioControlData.KEY_RDS_DATA, JsonRPCMarshaller.serializeHashtable(TestValues.GENERAL_RDSDATA.getStore()));
            reference.put(RadioControlData.KEY_AVAILABLE_HDS, TestValues.GENERAL_INT);
            reference.put(RadioControlData.KEY_HD_CHANNEL, TestValues.GENERAL_INT);
            reference.put(RadioControlData.KEY_SIGNAL_STRENGTH, TestValues.GENERAL_INT);
            reference.put(RadioControlData.KEY_SIGNAL_CHANGE_THRESHOLD, TestValues.GENERAL_INT);
            reference.put(RadioControlData.KEY_RADIO_ENABLE, TestValues.GENERAL_BOOLEAN);
            reference.put(RadioControlData.KEY_STATE, TestValues.GENERAL_RADIOSTATE);
            reference.put(RadioControlData.KEY_HD_RADIO_ENABLE, TestValues.GENERAL_BOOLEAN);
            reference.put(RadioControlData.KEY_SIS_DATA, JsonRPCMarshaller.serializeHashtable(TestValues.GENERAL_SISDATA.getStore()));
            reference.put(RadioControlData.KEY_AVAILABLE_HD_CHANNELS, TestValues.GENERAL_AVAILABLE_HD_CHANNELS_LIST);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();

                if(key.equals(RadioControlData.KEY_RDS_DATA)){
                    JSONObject objectEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
                    JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
                    Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
                    Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
                    assertTrue(TestValues.TRUE, Validator.validateRdsData(new RdsData(hashReference), new RdsData(hashTest)));
                } else if (key.equals(RadioControlData.KEY_SIS_DATA)) {
	                JSONObject objectEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
	                JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
	                Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
	                Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
	                assertTrue(TestValues.TRUE, Validator.validateSisData(new SisData(hashReference), new SisData(hashTest)));
                } else if(key.equals(RadioControlData.KEY_AVAILABLE_HD_CHANNELS)){
                    List<Integer> list1 = TestValues.GENERAL_AVAILABLE_HD_CHANNELS_LIST;
                    List<Integer> list2 = JsonUtils.readIntegerListFromJsonObject(underTest, key);
                    assertTrue(TestValues.TRUE, Validator.validateIntegerList(list1,list2));
                } else{
                    assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
                }
            }
        } catch(JSONException e){
        	fail(TestValues.JSON_FAIL);
        }
    }
}