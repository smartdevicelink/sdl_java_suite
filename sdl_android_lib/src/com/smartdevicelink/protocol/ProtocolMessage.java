package com.smartdevicelink.protocol;

import com.smartdevicelink.protocol.enums.MessageType;
import com.smartdevicelink.protocol.enums.SessionType;

public class ProtocolMessage {
	private byte version = 1;
	private SessionType sessionType = SessionType.RPC;
	private MessageType messageType = MessageType.UNDEFINED;
	private byte sessionId = 0;
	private byte rpcType;
	private int functionId;
	private int correlationId;
	private int jsonSize;
	
	private byte[] data = null;
	private byte[] bulkData = null;
	
	public ProtocolMessage() {}

	public byte getVersion() {
		return version;
	}

	public void setVersion(byte version) {
		this.version = version;
	}

	public byte getSessionId() {
		return sessionId;
	}

	public void setSessionId(byte sessionId) {
		this.sessionId = sessionId;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
		this.jsonSize = data.length;
	}
	
	public void setData(byte[] data, int length) {
		if (this.data != null)
			this.data = null;
		this.data = new byte[length];
		System.arraycopy(data, 0, this.data, 0, length);
		this.jsonSize = 0;
	}	

	public byte[] getBulkData() {
		return bulkData;
	}

	public void setBulkDataNoCopy(byte[] bulkData) {
		this.bulkData = bulkData;
	}

	public void setBulkData(byte[] bulkData) {
		if (this.bulkData != null)
			this.bulkData = null;
		this.bulkData = new byte[bulkData.length];
		System.arraycopy(bulkData, 0, this.bulkData, 0, bulkData.length);
		//this._bulkData = bulkData;
	}
	
	public void setBulkData(byte[] bulkData, int length) {
		if (this.bulkData != null)
			this.bulkData = null;
		this.bulkData = new byte[length];
		System.arraycopy(bulkData, 0, this.bulkData, 0, length);
		//this._bulkData = bulkData;
	}

	public SessionType getSessionType() {
		return sessionType;
	}

	public void setSessionType(SessionType sessionType) {
		this.sessionType = sessionType;
	}

	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
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
} // end-class