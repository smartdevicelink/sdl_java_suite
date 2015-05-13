package com.smartdevicelink.test.rpc.responses;

import java.util.Hashtable;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.UnsubscribeButtonResponse;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

public class UnsubscribeButtonResponseTest extends BaseRpcTests {

	@Override
    protected RPCMessage createMessage(){
        return new UnsubscribeButtonResponse();
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_RESPONSE;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.UNSUBSCRIBE_BUTTON;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        return new JSONObject();
    }

    public void testNull(){
    	UnsubscribeButtonResponse msg = new UnsubscribeButtonResponse();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);
    }
    
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(getCommandType(), getMessageType());
    	assertNotNull("Command object is null", commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			UnsubscribeButtonResponse cmd = new UnsubscribeButtonResponse(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull("Command type doesn't match expected message type", body);
			
			// test everything in the body
			assertEquals("Command name doesn't match input name", JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals("Correlation ID doesn't match input ID", JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
    	
    }
    
}
