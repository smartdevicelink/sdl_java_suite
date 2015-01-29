package com.smartdevicelink.test.rpc.requests;

import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.SystemRequest;
import com.smartdevicelink.proxy.rpc.enums.RequestType;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.utils.JsonUtils;
import com.smartdevicelink.test.utils.Validator;

public class SystemRequestTest extends BaseRpcTests {

	private static final List<String> LEGACY_DATA = Arrays.asList(new String[]{"param1","param2"});
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
		
		assertNotSame("Legacy data was not defensive copied", LEGACY_DATA, copy);
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
	    
	    assertNotSame("Bulk data was not defensive copied.", copy, BULK_DATA);
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
}
