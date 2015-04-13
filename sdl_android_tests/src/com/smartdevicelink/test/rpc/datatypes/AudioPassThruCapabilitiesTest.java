package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.AudioPassThruCapabilities;
import com.smartdevicelink.proxy.rpc.enums.AudioType;
import com.smartdevicelink.proxy.rpc.enums.BitsPerSample;
import com.smartdevicelink.proxy.rpc.enums.SamplingRate;
import com.smartdevicelink.test.utils.JsonUtils;

public class AudioPassThruCapabilitiesTest extends TestCase{

    private static final AudioType     AUDIO_TYPE      = AudioType.PCM;
    private static final BitsPerSample BITS_PER_SAMPLE = BitsPerSample._8_BIT;
    private static final SamplingRate  SAMPLING_RATE   = SamplingRate._16KHZ;

    private AudioPassThruCapabilities  msg;

    @Override
    public void setUp(){
        msg = new AudioPassThruCapabilities();
        msg.setAudioType(AUDIO_TYPE);
        msg.setBitsPerSample(BITS_PER_SAMPLE);
        msg.setSamplingRate(SAMPLING_RATE);
    }

    public void testCreation(){
        assertNotNull("Object creation failed.", msg);
    }

    public void testAudioType(){
        AudioType copy = msg.getAudioType();
        assertEquals("Audio type didn't match expected audio type.", AUDIO_TYPE, copy);
    }

    public void testBitsPerSample(){
        BitsPerSample copy = msg.getBitsPerSample();
        assertEquals("Bits per sample didn't match expected bits per sample.", BITS_PER_SAMPLE, copy);
    }

    public void testSamplingRate(){
        SamplingRate copy = msg.getSamplingRate();
        assertEquals("Sampling rate didn't match expected sampling rate.", SAMPLING_RATE, copy);
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(AudioPassThruCapabilities.KEY_AUDIO_TYPE, AUDIO_TYPE);
            reference.put(AudioPassThruCapabilities.KEY_BITS_PER_SAMPLE, BITS_PER_SAMPLE);
            reference.put(AudioPassThruCapabilities.KEY_SAMPLING_RATE, SAMPLING_RATE);

            JSONObject underTest = msg.serializeJSON();

            assertEquals("JSON size didn't match expected size.", reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                assertEquals("", JsonUtils.readObjectFromJsonObject(reference, key),
                        JsonUtils.readObjectFromJsonObject(underTest, key));
            }
        }catch(JSONException e){
            /* do nothing */
        }
    }

    public void testNull(){
        AudioPassThruCapabilities msg = new AudioPassThruCapabilities();
        assertNotNull("Null object creation failed.", msg);

        assertNull("Audio type wasn't set, but getter method returned an object.", msg.getAudioType());
        assertNull("Bits per sample wasn't set, but getter method returned an object.", msg.getBitsPerSample());
        assertNull("Sampling rate wasn't set, but getter method returned an object.", msg.getSamplingRate());
    }
    
    //remove this method?
    /*
    public void testCopy(){
        AudioPassThruCapabilities copy = new AudioPassThruCapabilities(msg);
        
        assertNotSame("Object was not copied.", copy, msg);
        
        String error = "Object data was not copied correctly.";
        assertEquals(error, copy.getAudioType(), msg.getAudioType());
        assertEquals(error, copy.getBitsPerSample(), msg.getBitsPerSample());
        assertEquals(error, copy.getSamplingRate(), msg.getSamplingRate());
    }
    */
}
