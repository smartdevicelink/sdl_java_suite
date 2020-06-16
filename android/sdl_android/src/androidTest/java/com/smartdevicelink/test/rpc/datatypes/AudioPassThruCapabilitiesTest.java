package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.AudioPassThruCapabilities;
import com.smartdevicelink.proxy.rpc.enums.AudioType;
import com.smartdevicelink.proxy.rpc.enums.BitsPerSample;
import com.smartdevicelink.proxy.rpc.enums.SamplingRate;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.AudioPassThruCapabilities}
 */
public class AudioPassThruCapabilitiesTest extends TestCase{

    private AudioPassThruCapabilities  msg;

    @Override
    public void setUp(){
        msg = new AudioPassThruCapabilities();
        assertNotNull(TestValues.NOT_NULL, msg);
        
        msg.setAudioType(TestValues.GENERAL_AUDIOTYPE);
        msg.setBitsPerSample(TestValues.GENERAL_BITSPERSAMPLE);
        msg.setSamplingRate(TestValues.GENERAL_SAMPLINGRATE);
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () { 
    	// Test Values
        SamplingRate samplingRate = msg.getSamplingRate();
        BitsPerSample bitsPerSample = msg.getBitsPerSample();
        AudioType audioType = msg.getAudioType();
        
        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_SAMPLINGRATE, samplingRate);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BITSPERSAMPLE, bitsPerSample);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_AUDIOTYPE, audioType);
    
        // Invalid/Null Tests
        AudioPassThruCapabilities msg = new AudioPassThruCapabilities();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getAudioType());
        assertNull(TestValues.NULL, msg.getBitsPerSample());
        assertNull(TestValues.NULL, msg.getSamplingRate());
    }
    
    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(AudioPassThruCapabilities.KEY_AUDIO_TYPE, TestValues.GENERAL_AUDIOTYPE);
            reference.put(AudioPassThruCapabilities.KEY_BITS_PER_SAMPLE, TestValues.GENERAL_BITSPERSAMPLE);
            reference.put(AudioPassThruCapabilities.KEY_SAMPLING_RATE, TestValues.GENERAL_SAMPLINGRATE);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
            }
        } catch(JSONException e){
        	fail(TestValues.JSON_FAIL);
        }
    }
}