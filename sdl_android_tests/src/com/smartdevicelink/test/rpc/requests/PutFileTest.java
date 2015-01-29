package com.smartdevicelink.test.rpc.requests;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.PutFile;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.utils.Validator;

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

}
