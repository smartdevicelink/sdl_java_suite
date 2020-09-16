package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.DeviceStatus;
import com.smartdevicelink.proxy.rpc.enums.DeviceLevelStatus;
import com.smartdevicelink.proxy.rpc.enums.PrimaryAudioSource;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.rpc.DeviceStatus}
 */
public class DeviceStatusTests extends TestCase {

    private DeviceStatus msg;

    @Override
    public void setUp(){
        msg = new DeviceStatus();

        msg.setBattLevelStatus(TestValues.GENERAL_DEVICELEVELSTATUS);
        msg.setBtIconOn(TestValues.GENERAL_BOOLEAN);
        msg.setCallActive(TestValues.GENERAL_BOOLEAN);
        msg.setECallEventActive(TestValues.GENERAL_BOOLEAN);
        msg.setMonoAudioOutputMuted(TestValues.GENERAL_BOOLEAN);
        msg.setPhoneRoaming(TestValues.GENERAL_BOOLEAN);
        msg.setPrimaryAudioSource(TestValues.GENERAL_PRIMARYAUDIOSOURCE);
        msg.setSignalLevelStatus(TestValues.GENERAL_DEVICELEVELSTATUS);
        msg.setStereoAudioOutputMuted(TestValues.GENERAL_BOOLEAN);
        msg.setTextMsgAvailable(TestValues.GENERAL_BOOLEAN);
        msg.setVoiceRecOn(TestValues.GENERAL_BOOLEAN);
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
        assertEquals(TestValues.MATCH, TestValues.GENERAL_DEVICELEVELSTATUS, battLevel);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, btIcon);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, callActive);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, ecall);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, monoAudio);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, phoneRoaming);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_PRIMARYAUDIOSOURCE, primaryAudio);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_DEVICELEVELSTATUS, signalLevel);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, stereoAudio);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, textAvailable);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, voiceRec);
        
        // Invalid/Null Tests
        DeviceStatus msg = new DeviceStatus();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getBattLevelStatus());
        assertNull(TestValues.NULL, msg.getBtIconOn());
        assertNull(TestValues.NULL, msg.getCallActive());
        assertNull(TestValues.NULL, msg.getECallEventActive());
        assertNull(TestValues.NULL, msg.getMonoAudioOutputMuted());
        assertNull(TestValues.NULL, msg.getPhoneRoaming());
        assertNull(TestValues.NULL, msg.getPrimaryAudioSource());
        assertNull(TestValues.NULL, msg.getSignalLevelStatus());
        assertNull(TestValues.NULL, msg.getStereoAudioOutputMuted());
        assertNull(TestValues.NULL, msg.getTextMsgAvailable());
        assertNull(TestValues.NULL, msg.getVoiceRecOn());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(DeviceStatus.KEY_BATT_LEVEL_STATUS, TestValues.GENERAL_DEVICELEVELSTATUS);
            reference.put(DeviceStatus.KEY_SIGNAL_LEVEL_STATUS, TestValues.GENERAL_DEVICELEVELSTATUS);
            reference.put(DeviceStatus.KEY_PRIMARY_AUDIO_SOURCE, TestValues.GENERAL_PRIMARYAUDIOSOURCE);
            reference.put(DeviceStatus.KEY_BT_ICON_ON, TestValues.GENERAL_BOOLEAN);
            reference.put(DeviceStatus.KEY_CALL_ACTIVE, TestValues.GENERAL_BOOLEAN);
            reference.put(DeviceStatus.KEY_E_CALL_EVENT_ACTIVE, TestValues.GENERAL_BOOLEAN);
            reference.put(DeviceStatus.KEY_MONO_AUDIO_OUTPUT_MUTED, TestValues.GENERAL_BOOLEAN);
            reference.put(DeviceStatus.KEY_STEREO_AUDIO_OUTPUT_MUTED, TestValues.GENERAL_BOOLEAN);
            reference.put(DeviceStatus.KEY_TEXT_MSG_AVAILABLE, TestValues.GENERAL_BOOLEAN);
            reference.put(DeviceStatus.KEY_PHONE_ROAMING, TestValues.GENERAL_BOOLEAN);
            reference.put(DeviceStatus.KEY_VOICE_REC_ON, TestValues.GENERAL_BOOLEAN);

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