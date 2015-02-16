package com.smartdevicelink.test.rpc.requests;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.SystemRequest;
import com.smartdevicelink.proxy.rpc.enums.RequestType;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.json.rpc.JsonFileReader;
import com.smartdevicelink.test.utils.JsonUtils;
import com.smartdevicelink.test.utils.Validator;

public class SystemRequestTest extends BaseRpcTests {

	private final List<String> LEGACY_DATA = Arrays.asList(new String[]{"param1","param2"});
	private static final String FILE_NAME = "fileName";
	private static final RequestType REQUEST_TYPE = RequestType.AUTH_ACK;
	private static final byte[] BULK_DATA = new byte[]{0x00, 0x01, 0x02};
    
	@Override
	protected RPCMessage createMessage() {
		SystemRequest msg = new SystemRequest();

		msg.setLegacyData(LEGACY_DATA);
		msg.setFileName(FILE_NAME);
		msg.setRequestType(REQUEST_TYPE);
		msg.setBulkData(BULK_DATA);

		return msg;
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.SYSTEM_REQUEST;
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();

		try {
			result.put(SystemRequest.KEY_DATA, JsonUtils.createJsonArray(LEGACY_DATA));
			result.put(SystemRequest.KEY_FILE_NAME, FILE_NAME);
			result.put(SystemRequest.KEY_REQUEST_TYPE, REQUEST_TYPE);
			
		} catch (JSONException e) {
			/* do nothing */
		}

		return result;
	}

	public void testLegacyData () {
		List<String> copy = ( (SystemRequest) msg ).getLegacyData();
		
	    assertTrue("Input value didn't match expected value.", Validator.validateStringList(LEGACY_DATA, copy));
	}
	
	public void testFileName () {
		String copy = ( (SystemRequest) msg ).getFileName();
		
		assertEquals("Data didn't match input data.", FILE_NAME, copy);
	}
	
	public void testRequestType () {
		RequestType copy = ( (SystemRequest) msg ).getRequestType();
		
		assertEquals("Data didn't match input data.", REQUEST_TYPE, copy);
	}
	
	public void testBulkData(){
	    byte[] copy = ( (SystemRequest) msg ).getBulkData();
	    
	    assertTrue("Input value didn't match expected value.", Validator.validateBulkData(copy, BULK_DATA));
	}

	public void testNull() {
		SystemRequest msg = new SystemRequest();
		assertNotNull("Null object creation failed.", msg);

		testNullBase(msg);

		assertNull("Legacy data wasn't set, but getter method returned an object.", msg.getLegacyData());
		assertNull("File name wasn't set, but getter method returned an object.", msg.getFileName());
		assertNull("Request type wasn't set, but getter method returned an object.", msg.getRequestType());
		assertNull("Bulk data wasn't set, but getter method returned an object.", msg.getBulkData());
	}
	
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(getCommandType(), getMessageType());
    	assertNotNull("Command object is null", commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			SystemRequest cmd = new SystemRequest(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull("Command type doesn't match expected message type", body);
			
			// test everything in the body
			assertEquals("Command name doesn't match input name", JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals("Correlation ID doesn't match input ID", JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());
		
			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals("File name doesn't match input name", JsonUtils.readStringFromJsonObject(parameters, SystemRequest.KEY_FILE_NAME), cmd.getFileName());
			assertEquals("Request type doesn't match input type", 
					JsonUtils.readStringFromJsonObject(parameters, SystemRequest.KEY_REQUEST_TYPE), cmd.getRequestType().toString());

			List<String> dataList = JsonUtils.readStringListFromJsonObject(parameters, SystemRequest.KEY_DATA);
			List<String> testDataList = cmd.getLegacyData();
			assertEquals("Data list length not same as reference data list length", dataList.size(), testDataList.size());
			assertTrue("Data list doesn't match input data list", Validator.validateStringList(dataList, testDataList));
			
			
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
    	
    }
	
}
