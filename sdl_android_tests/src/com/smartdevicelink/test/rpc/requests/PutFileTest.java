package com.smartdevicelink.test.rpc.requests;

import java.util.Hashtable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.PutFile;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

public class PutFileTest extends BaseRpcTests {

	private static final FileType FILE_TYPE = FileType.BINARY;
	private static final boolean IS_PERSISTENT = true;
	private static final boolean SYSTEM_FILE = true;
	private static final Integer OFFSET = 0;
	private static final Integer LENGTH = 0;
	private static final byte[] BULK_DATA = new byte[]{0x00, 0x01, 0x02};
	
	@Override
	protected RPCMessage createMessage() {
		PutFile msg = new PutFile();

		msg.setFileType(FILE_TYPE);
		msg.setPersistentFile(IS_PERSISTENT);
		msg.setSystemFile(SYSTEM_FILE);
		msg.setOffset(OFFSET);
		msg.setLength(LENGTH);
		msg.setBulkData(BULK_DATA);

		return msg;
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.PUT_FILE;
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();

		try {
			result.put(PutFile.KEY_FILE_TYPE, FILE_TYPE);
			result.put(PutFile.KEY_PERSISTENT_FILE, IS_PERSISTENT);
			result.put(PutFile.KEY_SYSTEM_FILE, SYSTEM_FILE);
			result.put(PutFile.KEY_OFFSET, OFFSET);
			result.put(PutFile.KEY_LENGTH, LENGTH);
			
		} catch (JSONException e) {
			/* do nothing */
		}

		return result;
	}
	
	public void testBulkData () {
		byte[] copy = ( (PutFile) msg ).getBulkData();
		
		assertTrue("Data didn't match input data.", Validator.validateBulkData(copy, BULK_DATA));
	}
	
	public void testFileType () {
		FileType copy = ( (PutFile) msg ).getFileType();
		
		assertEquals("Data didn't match input data.", FILE_TYPE, copy);
	}
	
	public void testIsPersistent () {
		boolean copy = ( (PutFile) msg ).getPersistentFile();
		
		assertEquals("Data didn't match input data.", IS_PERSISTENT, copy);
	}
	
	public void testSystemFile () {
		boolean copy = ( (PutFile) msg ).getSystemFile();
		
		assertEquals("Data didn't match input data.", SYSTEM_FILE, copy);
	}
	
	public void testOffset () {
		Integer copy = ( (PutFile) msg ).getOffset();
		
		assertEquals("Data didn't match input data.", OFFSET, copy);
	}
	
	public void testLength () {
		Integer copy = ( (PutFile) msg ).getLength();
		
		assertEquals("Data didn't match input data.", LENGTH, copy);
	}

	public void testNull() {
		PutFile msg = new PutFile();
		assertNotNull("Null object creation failed.", msg);

		testNullBase(msg);

		assertNull("File type wasn't set, but getter method returned an object.", msg.getFileType());
		assertNull("Persistent file wasn't set, but getter method returned an object.", msg.getPersistentFile());
		assertNull("System file wasn't set, but getter method returned an object.", msg.getSystemFile());
		assertNull("Offset wasn't set, but getter method returned an object.", msg.getOffset());
		assertNull("Length name wasn't set, but getter method returned an object.", msg.getLength());
	}
	
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(getCommandType(), getMessageType());
    	assertNotNull("Command object is null", commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			PutFile cmd = new PutFile(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull("Command type doesn't match expected message type", body);
			
			// test everything in the body
			assertEquals("Command name doesn't match input name", JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals("Correlation ID doesn't match input ID", JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals("Persistent file doesn't match input persistent file", 
					JsonUtils.readBooleanFromJsonObject(parameters, PutFile.KEY_PERSISTENT_FILE), cmd.getPersistentFile());
			assertEquals("System file doesn't match input system file", 
					JsonUtils.readBooleanFromJsonObject(parameters, PutFile.KEY_SYSTEM_FILE), cmd.getSystemFile());
			assertEquals("File type doesn't match input type", 
					JsonUtils.readStringFromJsonObject(parameters, PutFile.KEY_FILE_TYPE), cmd.getFileType().toString());
			assertEquals("SDL File name doesn't match input file name", 
					JsonUtils.readStringFromJsonObject(parameters, PutFile.KEY_SDL_FILE_NAME), cmd.getSdlFileName());
			assertEquals("Offset doesn't match input offset", 
					JsonUtils.readIntegerFromJsonObject(parameters, PutFile.KEY_OFFSET), cmd.getOffset());
			assertEquals("Length doesn't match input length", 
					JsonUtils.readIntegerFromJsonObject(parameters, PutFile.KEY_LENGTH), cmd.getLength());
			
			JSONArray bulkDataArray = JsonUtils.readJsonArrayFromJsonObject(parameters, RPCStruct.KEY_BULK_DATA);
			//TODO: make sure the bulk data test passes once the new code is checked out
			for (int index = 0; index < bulkDataArray.length(); index++) {
				Integer byteAsInt = (Integer)bulkDataArray.get(index);
				assertEquals("Bulk data item doesn't match input data item", byteAsInt, (Integer) ( (Byte)cmd.getBulkData()[index] ).intValue());
			}
			
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
    	
    }

}
