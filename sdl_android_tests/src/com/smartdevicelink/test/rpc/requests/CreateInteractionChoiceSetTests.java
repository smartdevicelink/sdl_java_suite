package com.smartdevicelink.test.rpc.requests;

import java.util.Hashtable;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.Choice;
import com.smartdevicelink.proxy.rpc.CreateInteractionChoiceSet;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.CreateInteractionChoiceSet}
 */
public class CreateInteractionChoiceSetTests extends BaseRpcTests{
    
    @Override
    protected RPCMessage createMessage(){
        CreateInteractionChoiceSet msg = new CreateInteractionChoiceSet();

        msg.setInteractionChoiceSetID(Test.GENERAL_INT);
        msg.setChoiceSet(Test.GENERAL_CHOICE_LIST);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_REQUEST;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.CREATE_INTERACTION_CHOICE_SET.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(CreateInteractionChoiceSet.KEY_INTERACTION_CHOICE_SET_ID, Test.GENERAL_INT);
            result.put(CreateInteractionChoiceSet.KEY_CHOICE_SET, Test.JSON_CHOICES);
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
        int testCmdId = ( (CreateInteractionChoiceSet) msg ).getInteractionChoiceSetID(); 
        List<Choice> testChoices = ( (CreateInteractionChoiceSet) msg ).getChoiceSet();
       
        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_INT, testCmdId);
        assertEquals(Test.MATCH, Test.GENERAL_CHOICE_LIST.size(), testChoices.size());
        for(int i = 0; i < testChoices.size(); i++){
            assertTrue(Test.TRUE, Validator.validateChoice(Test.GENERAL_CHOICE_LIST.get(i), testChoices.get(i)));
        }
   
        // Invalid/Null Tests
        CreateInteractionChoiceSet msg = new CreateInteractionChoiceSet();
        assertNotNull(Test.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(Test.NULL, msg.getChoiceSet());
        assertNull(Test.NULL, msg.getInteractionChoiceSetID());
    }

    /**
     * Tests a valid JSON construction of this RPC message.
     */
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
    	assertNotNull(Test.NOT_NULL, commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			CreateInteractionChoiceSet cmd = new CreateInteractionChoiceSet(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, CreateInteractionChoiceSet.KEY_INTERACTION_CHOICE_SET_ID), cmd.getInteractionChoiceSetID());
			
			JSONArray choiceSetArray = JsonUtils.readJsonArrayFromJsonObject(parameters, CreateInteractionChoiceSet.KEY_CHOICE_SET);
			for (int index = 0; index < choiceSetArray.length(); index++) {
				Choice chunk = new Choice(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)choiceSetArray.get(index)) );
				assertTrue(Test.TRUE,  Validator.validateChoice(chunk, cmd.getChoiceSet().get(index)) );
			}			
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}    	
    }
}