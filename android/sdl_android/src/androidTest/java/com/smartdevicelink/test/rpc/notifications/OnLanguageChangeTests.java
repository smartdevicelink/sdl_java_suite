package com.smartdevicelink.test.rpc.notifications;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.OnLanguageChange;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.OnLanguageChange}
 */
public class OnLanguageChangeTests extends BaseRpcTests{

    @Override
    protected RPCMessage createMessage(){
        OnLanguageChange msg = new OnLanguageChange();

        msg.setLanguage(Test.GENERAL_LANGUAGE);
        msg.setHmiDisplayLanguage(Test.GENERAL_LANGUAGE);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_NOTIFICATION;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ON_LANGUAGE_CHANGE.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(OnLanguageChange.KEY_LANGUAGE, Test.GENERAL_LANGUAGE);
            result.put(OnLanguageChange.KEY_HMI_DISPLAY_LANGUAGE, Test.GENERAL_LANGUAGE);
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
        Language lang = ( (OnLanguageChange) msg ).getLanguage();
        Language hmiLang = ( (OnLanguageChange) msg ).getHmiDisplayLanguage();
        
        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_LANGUAGE, lang);
        assertEquals(Test.MATCH, Test.GENERAL_LANGUAGE, hmiLang);
    
        // Invalid/Null Tests
        OnLanguageChange msg = new OnLanguageChange();
        assertNotNull(Test.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(Test.NULL, msg.getLanguage());
        assertNull(Test.NULL, msg.getHmiDisplayLanguage());
    }
}