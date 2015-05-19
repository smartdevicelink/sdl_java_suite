package com.smartdevicelink.test.rpc.notifications;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.enums.AudioStreamingState;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.SystemContext;
import com.smartdevicelink.test.BaseRpcTests;

public class OnHMIStatusTests extends BaseRpcTests{

    private static final AudioStreamingState AUDIO_STREAMING_STATE = AudioStreamingState.AUDIBLE;
    private static final boolean             FIRST_RUN             = true;
    private static final HMILevel            HMI_LEVEL             = HMILevel.HMI_FULL;
    private static final SystemContext       SYSTEM_CONTEXT        = SystemContext.SYSCTXT_MAIN;

    @Override
    protected RPCMessage createMessage(){
        OnHMIStatus msg = new OnHMIStatus();

        msg.setAudioStreamingState(AUDIO_STREAMING_STATE);
        msg.setFirstRun(FIRST_RUN);
        msg.setHmiLevel(HMI_LEVEL);
        msg.setSystemContext(SYSTEM_CONTEXT);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_NOTIFICATION;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ON_HMI_STATUS;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(OnHMIStatus.KEY_AUDIO_STREAMING_STATE, AUDIO_STREAMING_STATE);
            result.put(OnHMIStatus.KEY_HMI_LEVEL, HMI_LEVEL);
            result.put(OnHMIStatus.KEY_SYSTEM_CONTEXT, SYSTEM_CONTEXT);
        }catch(JSONException e){
            /* do nothing */
        }

        return result;
    }

    public void testAudioStreamingState(){
        AudioStreamingState cmdId = ( (OnHMIStatus) msg ).getAudioStreamingState();
        assertEquals("Audio streaming state didn't match input audio streaming state.", AUDIO_STREAMING_STATE, cmdId);
    }

    public void testHmiLevel(){
        HMILevel cmdId = ( (OnHMIStatus) msg ).getHmiLevel();
        assertEquals("HMI level didn't match input HMI level.", HMI_LEVEL, cmdId);
    }

    public void testSystemContext(){
        SystemContext cmdId = ( (OnHMIStatus) msg ).getSystemContext();
        assertEquals("System context didn't match input system context.", SYSTEM_CONTEXT, cmdId);
    }

    public void testNull(){
        OnHMIStatus msg = new OnHMIStatus();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);

        assertNull("Audio streaming state wasn't set, but getter method returned an object.",
                msg.getAudioStreamingState());
        assertNull("HMI level wasn't set, but getter method returned an object.", msg.getHmiLevel());
        assertNull("System context wasn't set, but getter method returned an object.", msg.getSystemContext());
    }
}
