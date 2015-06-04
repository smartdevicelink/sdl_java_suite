package com.smartdevicelink.protocol;

import com.smartdevicelink.util.BitConverter;

public class BinaryFrameHeader {
	private byte rpcType;
	private int functionId;
	private int correlationId;
	private int jsonSize;
	
	private byte[] jsonData;
	private byte[] bulkData;
	
	public BinaryFrameHeader() {}
	
	public static BinaryFrameHeader parseBinaryHeader(byte[] binHeader) {
		BinaryFrameHeader msg = new BinaryFrameHeader();
		
		byte rpcType = (byte) (binHeader[0] >>> 4);
		msg.setRpcType(rpcType);
		
		int functionId = (BitConverter.intFromByteArray(binHeader, 0) & 0x0FFFFFFF);
		msg.setFunctionId(functionId);
		
		int corrId = BitConverter.intFromByteArray(binHeader, 4);
		msg.setCorrId(corrId);
		
		int jsonSize = BitConverter.intFromByteArray(binHeader, 8);
		msg.setJsonSize(jsonSize);
		
		if (jsonSize > 0) {
			byte[] jsonData = new byte[jsonSize];
			System.arraycopy(binHeader, 12, jsonData, 0, jsonSize);
			msg.setJsonData(jsonData);
		}
		
		if (binHeader.length - jsonSize - 12 > 0) {
			byte[] bulkData = new byte[binHeader.length - jsonSize - 12];
			System.arraycopy(binHeader, 12 + jsonSize, bulkData, 0, bulkData.length);
			msg.setBulkData(bulkData);
		}		
		
		return msg;
	}
	
	protected byte[] assembleHeaderBytes() {
		int binHeader = functionId;
        // reset the 4 leftmost bits, for _rpcType
        binHeader &= 0xFFFFFFFF >>> 4;
		binHeader |= (rpcType << 28);
		
		byte[] ret = new byte[12];
		System.arraycopy(BitConverter.intToByteArray(binHeader), 0, ret, 0, 4);
		System.arraycopy(BitConverter.intToByteArray(correlationId), 0, ret, 4, 4);
		System.arraycopy(BitConverter.intToByteArray(jsonSize), 0, ret, 8, 4);
		
		return ret;
	}
	
	public byte getRpcType() {
		return rpcType;
	}

	public void setRpcType(byte rpcType) {
		this.rpcType = rpcType;
	}

	public int getFunctionId() {
		return functionId;
	}

	public void setFunctionId(int functionId) {
		this.functionId = functionId;
	}

	public int getCorrId() {
		return correlationId;
	}

	public void setCorrId(int correlationId) {
		this.correlationId = correlationId;
	}

	public int getJsonSize() {
		return jsonSize;
	}

	public void setJsonSize(int jsonSize) {
		this.jsonSize = jsonSize;
	}
	
	public byte[] getJsonData() {
		return jsonData;
	}
	
	public void setJsonData(byte[] jsonData) {
		this.jsonData = new byte[this.jsonSize];
		System.arraycopy(jsonData, 0, this.jsonData, 0, jsonSize);
		//this.jsonData = jsonData;
	}
	
	public byte[] getBulkData() {
		return bulkData;
	}
	
	public void setBulkData(byte[] bulkData) {
		this.bulkData = bulkData;
	}
}
