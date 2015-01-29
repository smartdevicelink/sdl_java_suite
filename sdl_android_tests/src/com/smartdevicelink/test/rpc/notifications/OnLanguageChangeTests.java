package com.smartdevicelink.test.rpc.notifications;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.OnLanguageChange;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.test.BaseRpcTests;

public class OnLanguageChangeTests extends BaseRpcTests{

    private static final Language LANGUAGE     = Language.AR_SA;
    private static final Language HMI_LANGUAGE = Language.DE_DE;

    @Override
    protected RPCMessage createMessage(){
        OnLanguageChange msg = new OnLanguageChange();

        msg.setLanguage(LANGUAGE);
        msg.setHmiDisplayLanguage(HMI_LANGUAGE);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_NOTIFICATION;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ON_LANGUAGE_CHANGE;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(OnLanguageChange.KEY_LANGUAGE, LANGUAGE);
            result.put(OnLanguageChange.KEY_HMI_DISPLAY_LANGUAGE, HMI_LANGUAGE);
        }catch(JSONException e){
            /* do nothing */
        }

        return result;
    }

    public void testLanguage(){
        Language data = ( (OnLanguageChange) msg ).getLanguage();
        assertEquals("Language didn't match input language.", LANGUAGE, data);
    }

    public void testHMILanguage(){
        Language data = ( (OnLanguageChange) msg ).getHmiDisplayLanguage();
        assertEquals("HMI language didn't match input language.", HMI_LANGUAGE, data);
    }

    public void testNull(){
        OnLanguageChange msg = new OnLanguageChange();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);

        assertNull("Language wasn't set, but getter method returned an object.", msg.getLanguage());
        assertNull("HMI language wasn't set, but getter method returned an object.", msg.getHmiDisplayLanguage());
    }
}
