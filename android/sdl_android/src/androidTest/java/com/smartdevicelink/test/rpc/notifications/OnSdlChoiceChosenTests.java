package com.smartdevicelink.test.rpc.notifications;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.OnSdlChoiceChosen;
import com.smartdevicelink.proxy.rpc.OnSdlChoiceChosen.SdlChoice;
import com.smartdevicelink.proxy.rpc.enums.TriggerSource;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.rpc.OnSdlChoiceChosen}
 */
public class OnSdlChoiceChosenTests extends BaseRpcTests{

    SdlChoice sdlChoice;

    @Override
    protected RPCMessage createMessage(){
        OnSdlChoiceChosen msg = new OnSdlChoiceChosen();                
        sdlChoice = msg.new SdlChoice(TestValues.GENERAL_CHOICE);
        
        msg.setTriggerSource(TestValues.GENERAL_TRIGGERSOURCE);
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
            result.put(OnSdlChoiceChosen.KEY_TRIGGER_SOURCE, TestValues.GENERAL_TRIGGERSOURCE);
            result.put(OnSdlChoiceChosen.KEY_SDL_CHOICE, sdlChoice);
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
        SdlChoice data = ( (OnSdlChoiceChosen) msg ).getSdlChoice();
        TriggerSource source = ( (OnSdlChoiceChosen) msg ).getTriggerSource();
        
        // Valid Tests
        assertTrue(TestValues.MATCH, Validator.validateChoice(TestValues.GENERAL_CHOICE, data.getChoice()));
        assertEquals(TestValues.MATCH, TestValues.GENERAL_TRIGGERSOURCE, source);
    
        // Invalid/Null Tests
    	OnSdlChoiceChosen msg = new OnSdlChoiceChosen();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getTriggerSource());
        assertNull(TestValues.NULL, msg.getSdlChoice());
    }
}