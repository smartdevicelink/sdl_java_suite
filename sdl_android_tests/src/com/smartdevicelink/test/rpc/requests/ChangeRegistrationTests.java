package com.smartdevicelink.test.rpc.requests;

import java.util.Hashtable;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.ChangeRegistration;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.json.rpc.JsonFileReader;
import com.smartdevicelink.test.utils.JsonUtils;


public class ChangeRegistrationTests extends BaseRpcTests{

    private static final Language LANGUAGE = Language.EN_AU;
    private static final Language HMI_LANGUAGE = Language.EN_GB;
    
    @Override
    protected RPCMessage createMessage(){
        ChangeRegistration msg = new ChangeRegistration();

        msg.setLanguage(LANGUAGE);
        msg.setHmiDisplayLanguage(HMI_LANGUAGE);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_REQUEST;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.CHANGE_REGISTRATION;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(ChangeRegistration.KEY_LANGUAGE, LANGUAGE);
            result.put(ChangeRegistration.KEY_HMI_DISPLAY_LANGUAGE, HMI_LANGUAGE);
        }catch(JSONException e){
            /* do nothing */
        }

        return result;
    }

    public void testLanguage(){
        Language language = ( (ChangeRegistration) msg ).getLanguage();
        assertEquals("Language didn't match input language.", LANGUAGE, language);
    }

    public void testHmiLanguage(){
        Language hmiLanguage = ( (ChangeRegistration) msg ).getHmiDisplayLanguage();
        assertEquals("HMI language didn't match input language.", HMI_LANGUAGE, hmiLanguage);
    }

    public void testNull(){
        ChangeRegistration msg = new ChangeRegistration();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);

        assertNull("Language wasn't set, but getter method returned an object.", msg.getLanguage());
        assertNull("HMI language wasn't set, but getter method returned an object.", msg.getHmiDisplayLanguage());
    }
    
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(getCommandType(), getMessageType());
    	assertNotNull("Command object is null", commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			ChangeRegistration cmd = new ChangeRegistration(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull("Command type doesn't match expected message type", body);
			
			// test everything in the body
			assertEquals("Command name doesn't match input name", JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals("Correlation ID doesn't match input ID", JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals("Language doesn't match input language", 
					JsonUtils.readStringFromJsonObject(parameters, ChangeRegistration.KEY_LANGUAGE), cmd.getLanguage().toString());
			assertEquals("HMI Language doesn't match input language", 
					JsonUtils.readStringFromJsonObject(parameters, ChangeRegistration.KEY_HMI_DISPLAY_LANGUAGE), cmd.getHmiDisplayLanguage().toString());
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
    	
    }

}
