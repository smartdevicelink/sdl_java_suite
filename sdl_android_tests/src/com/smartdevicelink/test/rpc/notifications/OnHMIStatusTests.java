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
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.OnHMIStatus}
 */
public class OnHMIStatusTests extends BaseRpcTests{

	@Override
    protected RPCMessage createMessage(){
        OnHMIStatus msg = new OnHMIStatus();

        msg.setAudioStreamingState(Test.GENERAL_AUDIOSTREAMINGSTATE);
        msg.setFirstRun(Test.GENERAL_BOOLEAN);
        msg.setHmiLevel(Test.GENERAL_HMILEVEL);
        msg.setSystemContext(Test.GENERAL_SYSTEMCONTEXT);

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
            result.put(OnHMIStatus.KEY_AUDIO_STREAMING_STATE, Test.GENERAL_AUDIOSTREAMINGSTATE);
            result.put(OnHMIStatus.KEY_HMI_LEVEL, Test.GENERAL_HMILEVEL);
            result.put(OnHMIStatus.KEY_SYSTEM_CONTEXT, Test.GENERAL_SYSTEMCONTEXT);
        }catch(JSONException e){
        	fail(Test.JSON_FAIL);
        }

        return result;
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {       	
    	// Test Values
        AudioStreamingState state = ( (OnHMIStatus) msg ).getAudioStreamingState();
        HMILevel hmiLevel = ( (OnHMIStatus) msg ).getHmiLevel();
        SystemContext context = ( (OnHMIStatus) msg ).getSystemContext();
        
        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_AUDIOSTREAMINGSTATE, state);
        assertEquals(Test.MATCH, Test.GENERAL_HMILEVEL, hmiLevel);
        assertEquals(Test.MATCH, Test.GENERAL_SYSTEMCONTEXT, context);
   
        // Invalid/Null Tests
        OnHMIStatus msg = new OnHMIStatus();
        assertNotNull(Test.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(Test.NULL, msg.getAudioStreamingState());
        assertNull(Test.NULL, msg.getHmiLevel());
        assertNull(Test.NULL, msg.getSystemContext());
    }
}