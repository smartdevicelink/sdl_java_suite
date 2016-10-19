package com.smartdevicelink.test.rpc.notifications;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.OnSdlChoiceChosen;
import com.smartdevicelink.proxy.rpc.OnSdlChoiceChosen.SdlChoice;
import com.smartdevicelink.proxy.rpc.enums.TriggerSource;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.OnSdlChoiceChosen}
 */
public class OnSdlChoiceChosenTests extends BaseRpcTests{

    @Override
    protected RPCMessage createMessage(){
        OnSdlChoiceChosen msg = new OnSdlChoiceChosen();                
        SdlChoice sdlChoice = msg.new SdlChoice(Test.GENERAL_CHOICE);
        
        msg.setTriggerSource(Test.GENERAL_TRIGGERSOURCE);
		msg.setSdlChoice(sdlChoice);
		
        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_NOTIFICATION;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ON_SDL_CHOICE_CHOSEN.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(OnSdlChoiceChosen.KEY_TRIGGER_SOURCE, Test.GENERAL_TRIGGERSOURCE);
            result.put(OnSdlChoiceChosen.KEY_SDL_CHOICE, Test.JSON_CHOICE);
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
        SdlChoice data = ( (OnSdlChoiceChosen) msg ).getSdlChoice();
        TriggerSource source = ( (OnSdlChoiceChosen) msg ).getTriggerSource();
        
        // Valid Tests
        assertTrue(Test.MATCH, Validator.validateChoice(Test.GENERAL_CHOICE, data.getChoice()));
        assertEquals(Test.MATCH, Test.GENERAL_TRIGGERSOURCE, source);
    
        // Invalid/Null Tests
    	OnSdlChoiceChosen msg = new OnSdlChoiceChosen();
        assertNotNull(Test.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(Test.NULL, msg.getTriggerSource());
        assertNull(Test.NULL, msg.getSdlChoice());
    }
}