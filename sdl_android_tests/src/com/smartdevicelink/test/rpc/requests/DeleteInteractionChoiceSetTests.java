package com.smartdevicelink.test.rpc.requests;

import java.util.Hashtable;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.DeleteInteractionChoiceSet;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.json.rpc.JsonFileReader;
import com.smartdevicelink.test.utils.JsonUtils;

public class DeleteInteractionChoiceSetTests extends BaseRpcTests{

    private static final int CHOICE_SET_ID = 15904;

    @Override
    protected RPCMessage createMessage(){
        DeleteInteractionChoiceSet msg = new DeleteInteractionChoiceSet();

        msg.setInteractionChoiceSetID(CHOICE_SET_ID);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_REQUEST;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.DELETE_INTERACTION_CHOICE_SET;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(DeleteInteractionChoiceSet.KEY_INTERACTION_CHOICE_SET_ID, CHOICE_SET_ID);
        }catch(JSONException e){
            /* do nothing */
        }

        return result;
    }

    public void testChoiceSetId(){
        int cmdId = ( (DeleteInteractionChoiceSet) msg ).getInteractionChoiceSetID();
        assertEquals("Choice set ID didn't match input choice set ID.", CHOICE_SET_ID, cmdId);
    }

    public void testNull(){
        DeleteInteractionChoiceSet msg = new DeleteInteractionChoiceSet();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);

        assertNull("Choice set ID wasn't set, but getter method returned an object.", msg.getInteractionChoiceSetID());
    }
    
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(getCommandType(), getMessageType());
    	assertNotNull("Command object is null", commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			DeleteInteractionChoiceSet cmd = new DeleteInteractionChoiceSet(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull("Command type doesn't match expected message type", body);
			
			// test everything in the body
			assertEquals("Command name doesn't match input name", JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals("Correlation ID doesn't match input ID", JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals("Command ID doesn't match input ID", 
					JsonUtils.readIntegerFromJsonObject(parameters, DeleteInteractionChoiceSet.KEY_INTERACTION_CHOICE_SET_ID), cmd.getInteractionChoiceSetID());
			
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
    	
    }
    
}
