package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.Choice;
import com.smartdevicelink.proxy.rpc.CreateInteractionChoiceSet;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Hashtable;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static androidx.test.InstrumentationRegistry.getTargetContext;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.CreateInteractionChoiceSet}
 */
public class CreateInteractionChoiceSetTests extends BaseRpcTests{
    
    @Override
    protected RPCMessage createMessage(){
        CreateInteractionChoiceSet msg = new CreateInteractionChoiceSet();

        msg.setInteractionChoiceSetID(TestValues.GENERAL_INT);
        msg.setChoiceSet(TestValues.GENERAL_CHOICE_LIST);

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
            result.put(CreateInteractionChoiceSet.KEY_INTERACTION_CHOICE_SET_ID, TestValues.GENERAL_INT);
            result.put(CreateInteractionChoiceSet.KEY_CHOICE_SET, TestValues.JSON_CHOICES);
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
        int testCmdId = ( (CreateInteractionChoiceSet) msg ).getInteractionChoiceSetID(); 
        List<Choice> testChoices = ( (CreateInteractionChoiceSet) msg ).getChoiceSet();
       
        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, testCmdId);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_CHOICE_LIST.size(), testChoices.size());
        for(int i = 0; i < testChoices.size(); i++){
            assertTrue(TestValues.TRUE, Validator.validateChoice(TestValues.GENERAL_CHOICE_LIST.get(i), testChoices.get(i)));
        }
   
        // Invalid/Null Tests
        CreateInteractionChoiceSet msg = new CreateInteractionChoiceSet();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getChoiceSet());
        assertNull(TestValues.NULL, msg.getInteractionChoiceSetID());
    }

    /**
     * Tests a valid JSON construction of this RPC message.
     */
    @Test
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(getTargetContext(), getCommandType(), getMessageType());
    	assertNotNull(TestValues.NOT_NULL, commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			CreateInteractionChoiceSet cmd = new CreateInteractionChoiceSet(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(TestValues.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, CreateInteractionChoiceSet.KEY_INTERACTION_CHOICE_SET_ID), cmd.getInteractionChoiceSetID());
			
			JSONArray choiceSetArray = JsonUtils.readJsonArrayFromJsonObject(parameters, CreateInteractionChoiceSet.KEY_CHOICE_SET);
			for (int index = 0; index < choiceSetArray.length(); index++) {
				Choice chunk = new Choice(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)choiceSetArray.get(index)) );
				assertTrue(TestValues.TRUE,  Validator.validateChoice(chunk, cmd.getChoiceSet().get(index)) );
			}			
		} catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
		}    	
    }
}