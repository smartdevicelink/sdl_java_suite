package com.smartdevicelink.test.protocol;

import junit.framework.Assert;

import com.smartdevicelink.protocol.BinaryFrameHeader;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.test.SampleRpc;

import android.test.AndroidTestCase;
import android.util.Log;

public class BinaryFrameHeaderTests extends AndroidTestCase {
	
	public static final byte RPC_TYPE_REQUEST 		= 0x00;
	public static final byte RPC_TYPE_RESPONSE 		= 0x01;
	public static final byte RPC_TYPE_NOTIFICAITON 	= 0x02;
	
	
	public static BinaryFrameHeader createDummyBfh(){
		BinaryFrameHeader bfh = new BinaryFrameHeader();
		bfh.setCorrID(123);
		bfh.setFunctionID(FunctionID.ON_HMI_STATUS.getId());
		bfh.setRPCType(RPC_TYPE_NOTIFICAITON);
		bfh.setBulkData(null);
		//There is no check for null bfh.setJsonData(null);
		bfh.setJsonSize(0);
		return bfh;
	}
	
	public BinaryFrameHeader safeParse(byte[] array){
		try{
			return BinaryFrameHeader.parseBinaryHeader(array);
		}catch(Exception e){
			return null;
		}
	}
	
	public void testAssemblyAndParse(){
		BinaryFrameHeader bfh = createDummyBfh();
		
		byte[] bfhBytes = bfh.assembleHeaderBytes();
		assertNotNull(bfhBytes);
		
		BinaryFrameHeader parsedBfh = BinaryFrameHeader.parseBinaryHeader(bfhBytes);
		assertNotNull(parsedBfh);
		
		assertTrue(bfh.getCorrID() == parsedBfh.getCorrID());
		
		assertTrue(bfh.getFunctionID() == parsedBfh.getFunctionID());
		
		assertTrue(bfh.getRPCType() == parsedBfh.getRPCType());
		
		assertTrue(bfh.getBulkData() == parsedBfh.getBulkData());
		
		assertTrue(bfh.getJsonData() == parsedBfh.getJsonData());
		
		assertTrue(bfh.getJsonSize() == parsedBfh.getJsonSize());
		
	}
	
	public void testCorruptHeader(){
		BinaryFrameHeader bfh = createDummyBfh();
		bfh.setJsonSize(5);
		bfh.setJsonData(new byte[5]);
		bfh.setJsonSize(Integer.MAX_VALUE);
		
		assertFalse(bfh.getJsonData().length == bfh.getJsonSize());
		
		byte[] bfhBytes = bfh.assembleHeaderBytes();
		
		assertNull(safeParse(bfhBytes));

		//Change bytes in the array manually
		int size = bfhBytes.length;
		for(int i =0; i<size;i++){
			bfhBytes[i] = (byte) 0x99;
		}

		assertNull(safeParse(bfhBytes));
		BinaryFrameHeader head = BinaryFrameHeader.parseBinaryHeader(bfhBytes);
		assertNull(head);
	}
	
	public void testJsonSetException(){
		try{
			BinaryFrameHeader bfh = createDummyBfh();
			bfh.setJsonData(null);
			Assert.fail("Setting JSON data to null should have thrown an exception.");
		}catch(Exception e){
			//Pass
		}
	}
	
	public void testAlteredDataInSampleRpc(){
		SampleRpc sampleRpc = new SampleRpc(4);
		//Create a corrupted header
		BinaryFrameHeader header = sampleRpc.getBinaryFrameHeader(true);
		header.setJsonSize(Integer.MAX_VALUE);
		assertEquals(Integer.MAX_VALUE, header.getJsonSize());
		sampleRpc.setBinaryFrameHeader(header);
		
		assertEquals(Integer.MAX_VALUE,sampleRpc.getBinaryFrameHeader(false).getJsonSize());
		
		
	}
	
}
