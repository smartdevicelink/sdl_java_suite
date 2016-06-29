package com.smartdevicelink.test.rpc.requests;

import java.util.Hashtable;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.GetDTCs;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.GetDTCs}
 */
public class GetDTCsTests extends BaseRpcTests{

    @Override
    protected RPCMessage createMessage(){
        GetDTCs msg = new GetDTCs();

        msg.setEcuName(Test.GENERAL_INT);
        msg.setDtcMask(Test.GENERAL_INT);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_REQUEST;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.GET_DTCS.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(GetDTCs.KEY_ECU_NAME, Test.GENERAL_INT);
            result.put(GetDTCs.KEY_DTC_MASK, Test.GENERAL_INT);
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
    	int testDtcMask = ( (GetDTCs) msg ).getDtcMask();
    	int testEcuName = ( (GetDTCs) msg ).getEcuName();
    	
    	// Valid Tests
    	assertEquals(Test.MATCH, Test.GENERAL_INT, testDtcMask);
    	assertEquals(Test.MATCH, Test.GENERAL_INT, testEcuName);
    	
    	// Invalid/Null Tests
        GetDTCs msg = new GetDTCs();
        assertNotNull(Test.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(Test.NULL, msg.getEcuName());
        assertNull(Test.NULL, msg.getDtcMask());
    }
    
    /**
     * Tests a valid JSON construction of this RPC message.
     */
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
    	assertNotNull(Test.NOT_NULL, commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			GetDTCs cmd = new GetDTCs(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, GetDTCs.KEY_DTC_MASK), cmd.getDtcMask());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, GetDTCs.KEY_ECU_NAME), cmd.getEcuName());			
		} 
		catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}    	
    }    
}