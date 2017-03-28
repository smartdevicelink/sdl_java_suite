package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.DeviceStatus;
import com.smartdevicelink.proxy.rpc.enums.DeviceLevelStatus;
import com.smartdevicelink.proxy.rpc.enums.PrimaryAudioSource;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.DeviceStatus}
 */
public class DeviceStatusTests extends TestCase {

    private DeviceStatus msg;

    @Override
    public void setUp(){
        msg = new DeviceStatus();

        msg.setBattLevelStatus(Test.GENERAL_DEVICELEVELSTATUS);
        msg.setBtIconOn(Test.GENERAL_BOOLEAN);
        msg.setCallActive(Test.GENERAL_BOOLEAN);
        msg.setECallEventActive(Test.GENERAL_BOOLEAN);
        msg.setMonoAudioOutputMuted(Test.GENERAL_BOOLEAN);
        msg.setPhoneRoaming(Test.GENERAL_BOOLEAN);
        msg.setPrimaryAudioSource(Test.GENERAL_PRIMARYAUDIOSOURCE);
        msg.setSignalLevelStatus(Test.GENERAL_DEVICELEVELSTATUS);
        msg.setStereoAudioOutputMuted(Test.GENERAL_BOOLEAN);
        msg.setTextMsgAvailable(Test.GENERAL_BOOLEAN);
        msg.setVoiceRecOn(Test.GENERAL_BOOLEAN);
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
        DeviceLevelStatus battLevel = msg.getBattLevelStatus();
        boolean btIcon = msg.getBtIconOn();
        boolean callActive = msg.getCallActive();
        boolean ecall = msg.getECallEventActive();
        boolean monoAudio = msg.getMonoAudioOutputMuted();
        boolean phoneRoaming = msg.getPhoneRoaming();
        PrimaryAudioSource primaryAudio = msg.getPrimaryAudioSource();
        DeviceLevelStatus signalLevel = msg.getSignalLevelStatus();
        boolean stereoAudio = msg.getStereoAudioOutputMuted();
        boolean textAvailable = msg.getTextMsgAvailable();
        boolean voiceRec = msg.getVoiceRecOn();
        
        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_DEVICELEVELSTATUS, battLevel);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, btIcon);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, callActive);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, ecall);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, monoAudio);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, phoneRoaming);
        assertEquals(Test.MATCH, Test.GENERAL_PRIMARYAUDIOSOURCE, primaryAudio);
        assertEquals(Test.MATCH, Test.GENERAL_DEVICELEVELSTATUS, signalLevel);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, stereoAudio);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, textAvailable);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, voiceRec);
        
        // Invalid/Null Tests
        DeviceStatus msg = new DeviceStatus();
        assertNotNull(Test.NOT_NULL, msg);

        assertNull(Test.NULL, msg.getBattLevelStatus());
        assertNull(Test.NULL, msg.getBtIconOn());
        assertNull(Test.NULL, msg.getCallActive());
        assertNull(Test.NULL, msg.getECallEventActive());
        assertNull(Test.NULL, msg.getMonoAudioOutputMuted());
        assertNull(Test.NULL, msg.getPhoneRoaming());
        assertNull(Test.NULL, msg.getPrimaryAudioSource());
        assertNull(Test.NULL, msg.getSignalLevelStatus());
        assertNull(Test.NULL, msg.getStereoAudioOutputMuted());
        assertNull(Test.NULL, msg.getTextMsgAvailable());
        assertNull(Test.NULL, msg.getVoiceRecOn());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(DeviceStatus.KEY_BATT_LEVEL_STATUS, Test.GENERAL_DEVICELEVELSTATUS);
            reference.put(DeviceStatus.KEY_SIGNAL_LEVEL_STATUS, Test.GENERAL_DEVICELEVELSTATUS);
            reference.put(DeviceStatus.KEY_PRIMARY_AUDIO_SOURCE, Test.GENERAL_PRIMARYAUDIOSOURCE);
            reference.put(DeviceStatus.KEY_BT_ICON_ON, Test.GENERAL_BOOLEAN);
            reference.put(DeviceStatus.KEY_CALL_ACTIVE, Test.GENERAL_BOOLEAN);
            reference.put(DeviceStatus.KEY_E_CALL_EVENT_ACTIVE, Test.GENERAL_BOOLEAN);
            reference.put(DeviceStatus.KEY_MONO_AUDIO_OUTPUT_MUTED, Test.GENERAL_BOOLEAN);
            reference.put(DeviceStatus.KEY_STEREO_AUDIO_OUTPUT_MUTED, Test.GENERAL_BOOLEAN);
            reference.put(DeviceStatus.KEY_TEXT_MSG_AVAILABLE, Test.GENERAL_BOOLEAN);
            reference.put(DeviceStatus.KEY_PHONE_ROAMING, Test.GENERAL_BOOLEAN);
            reference.put(DeviceStatus.KEY_VOICE_REC_ON, Test.GENERAL_BOOLEAN);

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