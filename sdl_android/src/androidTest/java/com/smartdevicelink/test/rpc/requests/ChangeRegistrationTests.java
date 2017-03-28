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
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.ChangeRegistration}
 */
public class ChangeRegistrationTests extends BaseRpcTests{
    
    @Override
    protected RPCMessage createMessage(){
        ChangeRegistration msg = new ChangeRegistration();

        msg.setLanguage(Test.GENERAL_LANGUAGE);
        msg.setHmiDisplayLanguage(Test.GENERAL_LANGUAGE);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_REQUEST;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.CHANGE_REGISTRATION.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(ChangeRegistration.KEY_LANGUAGE, Test.GENERAL_LANGUAGE);
            result.put(ChangeRegistration.KEY_HMI_DISPLAY_LANGUAGE, Test.GENERAL_LANGUAGE);
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
        Language testLanguage    = ( (ChangeRegistration) msg ).getLanguage();
        Language testHmiLanguage = ( (ChangeRegistration) msg ).getHmiDisplayLanguage();
        
        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_LANGUAGE, testLanguage);
        assertEquals(Test.MATCH, Test.GENERAL_LANGUAGE, testHmiLanguage);
   
    	// Invalid/Null Tests
        ChangeRegistration msg = new ChangeRegistration();
        assertNotNull(Test.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(Test.NULL, msg.getLanguage());
        assertNull(Test.NULL, msg.getHmiDisplayLanguage());
    }
    
    /**
     * Tests a valid JSON construction of this RPC message.
     */
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
    	assertNotNull(Test.NOT_NULL, commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			ChangeRegistration cmd = new ChangeRegistration(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, ChangeRegistration.KEY_LANGUAGE), cmd.getLanguage().toString());
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, ChangeRegistration.KEY_HMI_DISPLAY_LANGUAGE), cmd.getHmiDisplayLanguage().toString());
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}    	
    }
}