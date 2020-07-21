package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.Turn;
import com.smartdevicelink.proxy.rpc.UpdateTurnList;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static android.support.test.InstrumentationRegistry.getTargetContext;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.UpdateTurnList}
 */
public class UpdateTurnListTests extends BaseRpcTests {

    @Override
    protected RPCMessage createMessage() {
    	UpdateTurnList msg = new UpdateTurnList();
    	
    	msg.setTurnList(TestValues.GENERAL_TURN_LIST);
    	msg.setSoftButtons(TestValues.GENERAL_SOFTBUTTON_LIST);
    	
    	return msg;
    }
    
    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_REQUEST;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.UPDATE_TURN_LIST.toString();
    }
    
    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();
        
        try{			
			result.put(UpdateTurnList.KEY_SOFT_BUTTONS, TestValues.JSON_SOFTBUTTONS);
			result.put(UpdateTurnList.KEY_TURN_LIST, TestValues.JSON_TURNS);
        } catch(JSONException e){
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
    	List<SoftButton> testSoftButtonList = ( (UpdateTurnList) msg ).getSoftButtons();
    	List<Turn>       testTurnList       = ( (UpdateTurnList) msg ).getTurnList();
		
    	// Valid Tests
		assertNotNull(TestValues.NOT_NULL, testSoftButtonList);
		assertNotNull(TestValues.NOT_NULL, testTurnList);
		
		assertTrue(TestValues.TRUE, Validator.validateSoftButtons(TestValues.GENERAL_SOFTBUTTON_LIST, testSoftButtonList));
		assertTrue(TestValues.TRUE, Validator.validateTurnList(TestValues.GENERAL_TURN_LIST, testTurnList));
	
		// Invalid/Null Tests
		UpdateTurnList msg = new UpdateTurnList();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getTurnList());
        assertNull(TestValues.NULL, msg.getSoftButtons());
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
			UpdateTurnList cmd = new UpdateTurnList(hash);
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(TestValues.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			JSONArray softButtonArray = JsonUtils.readJsonArrayFromJsonObject(parameters, UpdateTurnList.KEY_SOFT_BUTTONS);
			List<SoftButton> softButtonList = new ArrayList<SoftButton>();
			for (int index = 0; index < softButtonArray.length(); index++) {
				SoftButton chunk = new SoftButton(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)softButtonArray.get(index)) );
				softButtonList.add(chunk);
			}
			assertTrue(TestValues.TRUE,  Validator.validateSoftButtons(softButtonList, cmd.getSoftButtons()));
			
			JSONArray turnArray = JsonUtils.readJsonArrayFromJsonObject(parameters, UpdateTurnList.KEY_TURN_LIST);
			List<Turn> turnList = new ArrayList<Turn>();
			for (int index = 0; index < turnArray.length(); index++) {
				Turn chunk = new Turn(JsonRPCMarshaller.deserializeJSONObject((JSONObject) turnArray.get(index)));
				if (chunk != null)
					turnList.add(chunk);
			}
			assertTrue(TestValues.TRUE,  Validator.validateTurnList(turnList, cmd.getTurnList()));
		} catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
		}
    }
}