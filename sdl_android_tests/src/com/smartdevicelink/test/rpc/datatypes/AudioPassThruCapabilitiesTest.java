package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.AudioPassThruCapabilities;
import com.smartdevicelink.proxy.rpc.enums.AudioType;
import com.smartdevicelink.proxy.rpc.enums.BitsPerSample;
import com.smartdevicelink.proxy.rpc.enums.SamplingRate;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.AudioPassThruCapabilities}
 */
public class AudioPassThruCapabilitiesTest extends TestCase{

    private AudioPassThruCapabilities  msg;

    @Override
    public void setUp(){
        msg = new AudioPassThruCapabilities();
        assertNotNull(Test.NOT_NULL, msg);
        
        msg.setAudioType(Test.GENERAL_AUDIOTYPE);
        msg.setBitsPerSample(Test.GENERAL_BITSPERSAMPLE);
        msg.setSamplingRate(Test.GENERAL_SAMPLINGRATE);
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
        assertEquals(Test.MATCH, Test.GENERAL_SAMPLINGRATE, samplingRate);
        assertEquals(Test.MATCH, Test.GENERAL_BITSPERSAMPLE, bitsPerSample);
        assertEquals(Test.MATCH, Test.GENERAL_AUDIOTYPE, audioType);
    
        // Invalid/Null Tests
        AudioPassThruCapabilities msg = new AudioPassThruCapabilities();
        assertNotNull(Test.NOT_NULL, msg);

        assertNull(Test.NULL, msg.getAudioType());
        assertNull(Test.NULL, msg.getBitsPerSample());
        assertNull(Test.NULL, msg.getSamplingRate());
    }
    
    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(AudioPassThruCapabilities.KEY_AUDIO_TYPE, Test.GENERAL_AUDIOTYPE);
            reference.put(AudioPassThruCapabilities.KEY_BITS_PER_SAMPLE, Test.GENERAL_BITSPERSAMPLE);
            reference.put(AudioPassThruCapabilities.KEY_SAMPLING_RATE, Test.GENERAL_SAMPLINGRATE);

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