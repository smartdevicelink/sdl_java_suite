package com.smartdevicelink.test.rpc.responses;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.DiagnosticMessageResponse;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.json.rpc.JsonFileReader;
import com.smartdevicelink.test.utils.JsonUtils;
import com.smartdevicelink.test.utils.Validator;

public class DiagnosticMessageResponseTests extends BaseRpcTests{

    private final List<Integer> MESSAGE_DATA_RESULT = Arrays.asList(new Integer[] { 1, 1, 3, 5, 8, 13, 21, 34,
            55, 89                                        });

    @Override
    protected RPCMessage createMessage(){
        DiagnosticMessageResponse msg = new DiagnosticMessageResponse();

        msg.setMessageDataResult(MESSAGE_DATA_RESULT);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_RESPONSE;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.DIAGNOSTIC_MESSAGE;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(DiagnosticMessageResponse.KEY_MESSAGE_DATA_RESULT,
                    JsonUtils.createJsonArray(MESSAGE_DATA_RESULT));
        }catch(JSONException e){
            /* do nothing */
        }

        return result;
    }

    public void testMessageData(){
        List<Integer> cmdId = ( (DiagnosticMessageResponse) msg ).getMessageDataResult();

        assertNotSame("Message data was not defensive copied.", MESSAGE_DATA_RESULT, cmdId);
        assertEquals("Array size didn't match expected size.", MESSAGE_DATA_RESULT.size(), cmdId.size());

        for(int i = 0; i < MESSAGE_DATA_RESULT.size(); i++){
            assertEquals("Message data didn't match input message data.", MESSAGE_DATA_RESULT.get(i), cmdId.get(i));
        }
    }

    public void testNull(){
        DiagnosticMessageResponse msg = new DiagnosticMessageResponse();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);

        assertNull("Message data wasn't set, but getter method returned an object.", msg.getMessageDataResult());
    }
    
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(getCommandType(), getMessageType());
    	assertNotNull("Command object is null", commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			DiagnosticMessageResponse cmd = new DiagnosticMessageResponse(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull("Command type doesn't match expected message type", body);
			
			// test everything in the body
			assertEquals("Command name doesn't match input name", JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals("Correlation ID doesn't match input ID", JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			
			List<Integer> dataResultList = JsonUtils.readIntegerListFromJsonObject(parameters, DiagnosticMessageResponse.KEY_MESSAGE_DATA_RESULT);
			List<Integer> testResultList = cmd.getMessageDataResult();
			
			assertEquals("Data result list length not same as reference list length", dataResultList.size(), testResultList.size());
			assertTrue("Data result list doesn't match input data result", Validator.validateIntegerList(dataResultList, testResultList));
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
    	
    }
}
