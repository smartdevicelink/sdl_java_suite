package com.smartdevicelink.test.rpc.notifications;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.enums.AudioStreamingState;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.SystemContext;
import com.smartdevicelink.proxy.rpc.enums.VideoStreamingState;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.util.Version;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.fail;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.rpc.OnHMIStatus}
 */
public class OnHMIStatusTests extends BaseRpcTests{

	@Override
    protected RPCMessage createMessage(){
        OnHMIStatus msg = new OnHMIStatus();

        msg.setAudioStreamingState(TestValues.GENERAL_AUDIOSTREAMINGSTATE);
        msg.setVideoStreamingState(TestValues.GENERAL_VIDEOSTREAMINGSTATE);
        msg.setFirstRun(TestValues.GENERAL_BOOLEAN);
        msg.setHmiLevel(TestValues.GENERAL_HMILEVEL);
        msg.setSystemContext(TestValues.GENERAL_SYSTEMCONTEXT);
        msg.setWindowID(TestValues.GENERAL_INT);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_NOTIFICATION;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ON_HMI_STATUS.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(OnHMIStatus.KEY_AUDIO_STREAMING_STATE, TestValues.GENERAL_AUDIOSTREAMINGSTATE);
            result.put(OnHMIStatus.KEY_VIDEO_STREAMING_STATE, TestValues.GENERAL_VIDEOSTREAMINGSTATE);
            result.put(OnHMIStatus.KEY_HMI_LEVEL, TestValues.GENERAL_HMILEVEL);
            result.put(OnHMIStatus.KEY_SYSTEM_CONTEXT, TestValues.GENERAL_SYSTEMCONTEXT);
            result.put(OnHMIStatus.KEY_WINDOW_ID, TestValues.GENERAL_INT);
        }catch(JSONException e){
        	fail(TestValues.JSON_FAIL);
        }

        return result;
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    @Test
    public void testRpcValues () {       	
    	// Test Values
        AudioStreamingState audioStreamingState = ( (OnHMIStatus) msg ).getAudioStreamingState();
        VideoStreamingState videoStreamingState = ( (OnHMIStatus) msg ).getVideoStreamingState();
        HMILevel hmiLevel = ( (OnHMIStatus) msg ).getHmiLevel();
        SystemContext context = ( (OnHMIStatus) msg ).getSystemContext();
        int testWindowID = ( (OnHMIStatus) msg ).getWindowID();
        
        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_AUDIOSTREAMINGSTATE, audioStreamingState);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_VIDEOSTREAMINGSTATE, videoStreamingState);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_HMILEVEL, hmiLevel);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_SYSTEMCONTEXT, context);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, testWindowID);
   
        // Invalid/Null Tests
        OnHMIStatus msg = new OnHMIStatus();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getAudioStreamingState());

        assertNull(TestValues.NULL, msg.getVideoStreamingState());
        msg.format(new Version(4,5,0),true);
        assertEquals(TestValues.MATCH, VideoStreamingState.STREAMABLE, msg.getVideoStreamingState());
        assertNull(TestValues.NULL, msg.getHmiLevel());
        assertNull(TestValues.NULL, msg.getSystemContext());
        assertNull(TestValues.NULL, msg.getWindowID());
    }
}