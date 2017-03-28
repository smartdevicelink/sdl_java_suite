package com.smartdevicelink.test.rpc.responses;

import java.util.Hashtable;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.ListFilesResponse;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.ListFilesResponse}
 */
public class ListFilesResponseTests extends BaseRpcTests{

    @Override
    protected RPCMessage createMessage(){
        ListFilesResponse msg = new ListFilesResponse();

        msg.setFilenames(Test.GENERAL_STRING_LIST);
        msg.setSpaceAvailable(Test.GENERAL_INT);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_RESPONSE;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.LIST_FILES.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(ListFilesResponse.KEY_FILENAMES, JsonUtils.createJsonArray(Test.GENERAL_STRING_LIST));
            result.put(ListFilesResponse.KEY_SPACE_AVAILABLE, Test.GENERAL_INT);
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
        List<String> filenames = ( (ListFilesResponse) msg ).getFilenames();
        int spaceAvailable = ( (ListFilesResponse) msg ).getSpaceAvailable();
        
        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_STRING_LIST.size(), filenames.size());
        assertTrue(Test.TRUE, Validator.validateStringList(Test.GENERAL_STRING_LIST, filenames));        
        assertEquals(Test.MATCH, Test.GENERAL_INT, spaceAvailable);
    
        // Invalid/Null Tests
        ListFilesResponse msg = new ListFilesResponse();
        assertNotNull(Test.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(Test.NULL, msg.getFilenames());
        assertNull(Test.NULL, msg.getSpaceAvailable());
    }

    /**
     * Tests a valid JSON construction of this RPC message.
     */
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
    	assertNotNull(Test.NOT_NULL, commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			ListFilesResponse cmd = new ListFilesResponse(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			
			List<String> fileNamesList = JsonUtils.readStringListFromJsonObject(parameters, ListFilesResponse.KEY_FILENAMES);
			List<String> testNamesList = cmd.getFilenames();
			assertEquals(Test.MATCH, fileNamesList.size(), testNamesList.size());
			assertTrue(Test.TRUE, Validator.validateStringList(fileNamesList, testNamesList));
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, ListFilesResponse.KEY_SPACE_AVAILABLE), cmd.getSpaceAvailable());
		} catch (JSONException e) {
			e.printStackTrace();
		}    	
    }
}