package com.smartdevicelink.test.rpc.notifications;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.OnLanguageChange;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.TestValues;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.fail;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.OnLanguageChange}
 */
public class OnLanguageChangeTests extends BaseRpcTests{

    @Override
    protected RPCMessage createMessage(){
        OnLanguageChange msg = new OnLanguageChange();

        msg.setLanguage(TestValues.GENERAL_LANGUAGE);
        msg.setHmiDisplayLanguage(TestValues.GENERAL_LANGUAGE);

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
            result.put(OnLanguageChange.KEY_LANGUAGE, TestValues.GENERAL_LANGUAGE);
            result.put(OnLanguageChange.KEY_HMI_DISPLAY_LANGUAGE, TestValues.GENERAL_LANGUAGE);
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
        Language lang = ( (OnLanguageChange) msg ).getLanguage();
        Language hmiLang = ( (OnLanguageChange) msg ).getHmiDisplayLanguage();
        
        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_LANGUAGE, lang);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_LANGUAGE, hmiLang);
    
        // Invalid/Null Tests
        OnLanguageChange msg = new OnLanguageChange();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getLanguage());
        assertNull(TestValues.NULL, msg.getHmiDisplayLanguage());
    }
}