package com.smartdevicelink.test.rpc.responses;

import java.util.Hashtable;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.AlertResponse;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

public class AlertResponseTests extends BaseRpcTests{

    private static final int TRY_AGAIN_TIME = 400;

    @Override
    protected RPCMessage createMessage(){
    	AlertResponse alert = new AlertResponse();
    	alert.setTryAgainTime(TRY_AGAIN_TIME);
        return alert;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_RESPONSE;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ALERT;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(AlertResponse.KEY_TRY_AGAIN_TIME, TRY_AGAIN_TIME);
        }catch(JSONException e){
            /* do nothing */
        }

        return result;
    }

    public void testTryAgainTime(){
        int tryAgainTime = ( (AlertResponse) msg ).getTryAgainTime();
        assertEquals("Try again time didn't match expected time.", TRY_AGAIN_TIME, tryAgainTime);
    }

    public void testNull(){
        AlertResponse msg = new AlertResponse();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);

        assertNull("Try again time wasn't set, but getter method returned an object.", msg.getTryAgainTime());
    }
    
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(getCommandType(), getMessageType());
    	assertNotNull("Command object is null", commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			AlertResponse cmd = new AlertResponse(hash);
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull("Command type doesn't match expected message type", body);
			
			// test everything in the body
			assertEquals("Command name doesn't match input name", JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals("Correlation ID doesn't match input ID", JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals("Try again time doesn't match input time", JsonUtils.readIntegerFromJsonObject(parameters, AlertResponse.KEY_TRY_AGAIN_TIME), cmd.getTryAgainTime());
			
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
    	
    }

}
