package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.DeviceStatus;
import com.smartdevicelink.proxy.rpc.enums.DeviceLevelStatus;
import com.smartdevicelink.proxy.rpc.enums.PrimaryAudioSource;
import com.smartdevicelink.test.utils.JsonUtils;

public class DeviceStatusTests extends TestCase{

    private static final boolean            VOICE_REC_ON         = true;
    private static final boolean            BT_ICON_ON           = true;
    private static final boolean            CALL_ACTIVE          = false;
    private static final boolean            PHONE_ROAMING        = false;
    private static final boolean            TEXT_MSG_AVAILABLE   = true;
    private static final boolean            STEREO_AUDIO_MUTED   = false;
    private static final boolean            MONO_AUDIO_MUTED     = false;
    private static final boolean            E_CALL_EVENT_ACTIVE  = false;
    private static final DeviceLevelStatus  BATT_LEVEL_STATUS    = DeviceLevelStatus.FOUR_LEVEL_BARS;
    private static final DeviceLevelStatus  SIGNAL_LEVEL_STATUS  = DeviceLevelStatus.TWO_LEVEL_BARS;
    private static final PrimaryAudioSource PRIMARY_AUDIO_SOURCE = PrimaryAudioSource.BLUETOOTH_STEREO_BTST;

    private DeviceStatus                    msg;

    @Override
    public void setUp(){
        msg = new DeviceStatus();

        msg.setBattLevelStatus(BATT_LEVEL_STATUS);
        msg.setBtIconOn(BT_ICON_ON);
        msg.setCallActive(CALL_ACTIVE);
        msg.setECallEventActive(E_CALL_EVENT_ACTIVE);
        msg.setMonoAudioOutputMuted(MONO_AUDIO_MUTED);
        msg.setPhoneRoaming(PHONE_ROAMING);
        msg.setPrimaryAudioSource(PRIMARY_AUDIO_SOURCE);
        msg.setSignalLevelStatus(SIGNAL_LEVEL_STATUS);
        msg.setStereoAudioOutputMuted(STEREO_AUDIO_MUTED);
        msg.setTextMsgAvailable(TEXT_MSG_AVAILABLE);
        msg.setVoiceRecOn(VOICE_REC_ON);
    }

    public void testBattLevelStatus(){
        DeviceLevelStatus copy = msg.getBattLevelStatus();
        assertEquals("Input value didn't match expected value.", BATT_LEVEL_STATUS, copy);
    }

    public void testBtIconOn(){
        boolean copy = msg.getBtIconOn();
        assertEquals("Input value didn't match expected value.", BT_ICON_ON, copy);
    }

    public void testCallActive(){
        boolean copy = msg.getCallActive();
        assertEquals("Input value didn't match expected value.", CALL_ACTIVE, copy);
    }

    public void testECallEventActive(){
        boolean copy = msg.getECallEventActive();
        assertEquals("Input value didn't match expected value.", E_CALL_EVENT_ACTIVE, copy);
    }

    public void testMonoAudioOutputMuted(){
        boolean copy = msg.getMonoAudioOutputMuted();
        assertEquals("Input value didn't match expected value.", MONO_AUDIO_MUTED, copy);
    }

    public void testPhoneRoaming(){
        boolean copy = msg.getPhoneRoaming();
        assertEquals("Input value didn't match expected value.", PHONE_ROAMING, copy);
    }

    public void testPrimaryAudioSource(){
        PrimaryAudioSource copy = msg.getPrimaryAudioSource();
        assertEquals("Input value didn't match expected value.", PRIMARY_AUDIO_SOURCE, copy);
    }

    public void testSignalLevelStatus(){
        DeviceLevelStatus copy = msg.getSignalLevelStatus();
        assertEquals("Input value didn't match expected value.", SIGNAL_LEVEL_STATUS, copy);
    }

    public void testStereoAudioOutputMuted(){
        boolean copy = msg.getStereoAudioOutputMuted();
        assertEquals("Input value didn't match expected value.", STEREO_AUDIO_MUTED, copy);
    }

    public void testTextMsgAvailable(){
        boolean copy = msg.getTextMsgAvailable();
        assertEquals("Input value didn't match expected value.", TEXT_MSG_AVAILABLE, copy);
    }

    public void testVoiceRecOn(){
        boolean copy = msg.getVoiceRecOn();
        assertEquals("Input value didn't match expected value.", VOICE_REC_ON, copy);
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(DeviceStatus.KEY_BATT_LEVEL_STATUS, BATT_LEVEL_STATUS);
            reference.put(DeviceStatus.KEY_SIGNAL_LEVEL_STATUS, SIGNAL_LEVEL_STATUS);
            reference.put(DeviceStatus.KEY_PRIMARY_AUDIO_SOURCE, PRIMARY_AUDIO_SOURCE);
            reference.put(DeviceStatus.KEY_BT_ICON_ON, BT_ICON_ON);
            reference.put(DeviceStatus.KEY_CALL_ACTIVE, CALL_ACTIVE);
            reference.put(DeviceStatus.KEY_E_CALL_EVENT_ACTIVE, E_CALL_EVENT_ACTIVE);
            reference.put(DeviceStatus.KEY_MONO_AUDIO_OUTPUT_MUTED, MONO_AUDIO_MUTED);
            reference.put(DeviceStatus.KEY_STEREO_AUDIO_OUTPUT_MUTED, STEREO_AUDIO_MUTED);
            reference.put(DeviceStatus.KEY_TEXT_MSG_AVAILABLE, TEXT_MSG_AVAILABLE);
            reference.put(DeviceStatus.KEY_PHONE_ROAMING, PHONE_ROAMING);
            reference.put(DeviceStatus.KEY_VOICE_REC_ON, VOICE_REC_ON);

            JSONObject underTest = msg.serializeJSON();

            assertEquals("JSON size didn't match expected size.", reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                assertEquals("JSON value didn't match expected value for key \"" + key + "\".",
                        JsonUtils.readObjectFromJsonObject(reference, key),
                        JsonUtils.readObjectFromJsonObject(underTest, key));
            }
        }catch(JSONException e){
            /* do nothing */
        }
    }

    public void testNull(){
        DeviceStatus msg = new DeviceStatus();
        assertNotNull("Null object creation failed.", msg);

        assertNull("Batt level status wasn't set, but getter method returned an object.", msg.getBattLevelStatus());
        assertNull("BT icon on wasn't set, but getter method returned an object.", msg.getBtIconOn());
        assertNull("Call active wasn't set, but getter method returned an object.", msg.getCallActive());
        assertNull("E-call event active wasn't set, but getter method returned an object.", msg.getECallEventActive());
        assertNull("Mono audio output muted wasn't set, but getter method returned an object.",
                msg.getMonoAudioOutputMuted());
        assertNull("Phone roaming wasn't set, but getter method returned an object.", msg.getPhoneRoaming());
        assertNull("Primary audio source wasn't set, but getter method returned an object.",
                msg.getPrimaryAudioSource());
        assertNull("Signal level status wasn't set, but getter method returned an object.", msg.getSignalLevelStatus());
        assertNull("Stereo audio output muted wasn't set, but getter method returned an object.",
                msg.getStereoAudioOutputMuted());
        assertNull("Text msg available wasn't set, but getter method returned an object.", msg.getTextMsgAvailable());
        assertNull("Voice rec on wasn't set, but getter method returned an object.", msg.getVoiceRecOn());
    }
  //TODO: remove testCopy()?
    /*
    public void testCopy(){
        DeviceStatus copy = new DeviceStatus(msg);

        assertNotSame("Object was not copied.", copy, msg);

        String error = "Object data was not copied correctly.";
        assertEquals(error, copy.getBattLevelStatus(), msg.getBattLevelStatus());
        assertEquals(error, copy.getBtIconOn(), msg.getBtIconOn());
        assertEquals(error, copy.getCallActive(), msg.getCallActive());
        assertEquals(error, copy.getECallEventActive(), msg.getECallEventActive());
        assertEquals(error, copy.getMonoAudioOutputMuted(), msg.getMonoAudioOutputMuted());
        assertEquals(error, copy.getPhoneRoaming(), msg.getPhoneRoaming());
        assertEquals(error, copy.getPrimaryAudioSource(), msg.getPrimaryAudioSource());
        assertEquals(error, copy.getSignalLevelStatus(), msg.getSignalLevelStatus());
        assertEquals(error, copy.getStereoAudioOutputMuted(), msg.getStereoAudioOutputMuted());
        assertEquals(error, copy.getTextMsgAvailable(), msg.getTextMsgAvailable());
        assertEquals(error, copy.getVoiceRecOn(), msg.getVoiceRecOn());
    }
    */
}
