/*
 * Copyright (c) 2019, Livio, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the Livio Inc. nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.smartdevicelink.protocol;

import android.os.Parcel;
import android.os.Parcelable;

import com.livio.BSON.BsonEncoder;
import com.smartdevicelink.protocol.enums.FrameType;
import com.smartdevicelink.transport.utl.TransportRecord;
import com.smartdevicelink.util.DebugTool;

import java.nio.ByteBuffer;
import java.util.HashMap;

/**
 * This class is only intended to be parcelable from the transport broker to the SDL Router Service.
 * Any other binder transactions must include an additional int flag into their bundle or the parsing
 * of this object will fail.
 */
class BaseSdlPacket {

	/**
	 * This is the amount of bytes added to the bundle from the router service for a specific int
	 * flag; this data will always and must be included. This flag is the
	 * TransportConstants.BYTES_TO_SEND_FLAGS.
	 *
	 *	@see com.smartdevicelink.transport.TransportConstants#BYTES_TO_SEND_FLAGS
	 */
	private static final int EXTRA_PARCEL_DATA_LENGTH 			= 24;
	
	public static final int HEADER_SIZE 						= 12;
	public static final int HEADER_SIZE_V1 						= 8;//Backwards

	private static final int ENCRYPTION_MASK 					= 0x08; //4th lowest bit
	
	public static final int FRAME_TYPE_CONTROL 					= 0x00;
	public static final int FRAME_TYPE_SINGLE 					= 0x01;
	public static final int FRAME_TYPE_FIRST 					= 0x02;
	public static final int FRAME_TYPE_CONSECUTIVE				= 0x03;
	
	/*
	 * Service Type
	 */
	public static final int SERVICE_TYPE_CONTROL 				= 0x00;
	//RESERVED 0x01 - 0x06
	public static final int SERVICE_TYPE_RPC	 				= 0x07;
	//RESERVED 0x08 - 0x09
	public static final int SERVICE_TYPE_PCM 					= 0x0A;
	public static final int SERVICE_TYPE_VIDEO 					= 0x0B;
	//RESERVED 0x0C - 0x0E
	public static final int SERVICE_TYPE_BULK_DATA				= 0x0F;
	//RESERVED 0x10 - 0xFF

	
	/*
	 * Frame Info
	 */
	//Control Frame Info
	public static final int FRAME_INFO_HEART_BEAT 				= 0x00;
	public static final int FRAME_INFO_START_SERVICE 			= 0x01;
	public static final int FRAME_INFO_START_SERVICE_ACK		= 0x02;
	public static final int FRAME_INFO_START_SERVICE_NAK		= 0x03;
	public static final int FRAME_INFO_END_SERVICE 				= 0x04;
	public static final int FRAME_INFO_END_SERVICE_ACK			= 0x05;
	public static final int FRAME_INFO_END_SERVICE_NAK			= 0x06;
	public static final int FRAME_INFO_REGISTER_SECONDARY_TRANSPORT     = 0x07;
	public static final int FRAME_INFO_REGISTER_SECONDARY_TRANSPORT_ACK = 0x08;
	public static final int FRAME_INFO_REGISTER_SECONDARY_TRANSPORT_NAK = 0x09;
	//0x0A-0xFC are reserved
	public static final int FRAME_INFO_TRANSPORT_EVENT_UPDATE           = 0xFD;
	public static final int FRAME_INFO_SERVICE_DATA_ACK			= 0xFE;
	public static final int FRAME_INFO_HEART_BEAT_ACK			= 0xFF;
	
	public static final int FRAME_INFO_FINAL_CONNESCUTIVE_FRAME	= 0x00;

	//Most others
	public static final int FRAME_INFO_RESERVED 				= 0x00;


	int version;
	boolean encryption;
	int frameType;
	int serviceType;
	int frameInfo;
	int sessionId;
	int dataSize;
	int messageId;
	int priorityCoefficient;
	byte[] payload = null;
	HashMap<String, Object> bsonPayload;

	int messagingVersion = 1;
	TransportRecord transportRecord;

	BaseSdlPacket(int version, boolean encryption, int frameType,
						 int serviceType, int frameInfo, int sessionId,
						 int dataSize, int messageId, byte[] payload) {
		this.version = version;
		this.encryption = encryption;
		this.frameType = frameType;
		this.serviceType = serviceType;
		this.frameInfo = frameInfo;
		this.sessionId = sessionId;
		this.dataSize = dataSize;
		this.messageId = messageId;
		this.priorityCoefficient = 0;
		if(payload!=null){
			this.payload = new byte[payload.length];
			System.arraycopy(payload, 0, this.payload, 0, payload.length);
		}
	}

	BaseSdlPacket(int version, boolean encryption, int frameType,
						 int serviceType, int frameInfo, int sessionId,
						 int dataSize, int messageId, byte[] payload, int offset, int bytesToWrite) {
		this.version = version;
		this.encryption = encryption;
		this.frameType = frameType;
		this.serviceType = serviceType;
		this.frameInfo = frameInfo;
		this.sessionId = sessionId;
		this.dataSize = dataSize;
		this.messageId = messageId;
		this.priorityCoefficient = 0;
		if(payload!=null){
			this.payload = new byte[bytesToWrite];
			System.arraycopy(payload, offset, this.payload, 0, bytesToWrite);
		}
	}
	/**
	 * This constructor is available as a protected method. A few defaults have been set, however a few things <b>MUST</b> be set before use. The rest will "work"
	 * however, it won't be valid data.
	 * 
	 * <p>Frame Type
	 * <p>Service Type
	 * <p>Frame Info
	 * <p>
	 */
	protected BaseSdlPacket(){
		//Package only empty constructor
		this.version = 1;
		this.encryption = false;
		this.frameType = -1;	//This NEEDS to be set
		this.serviceType = -1;
		this.frameInfo = -1;
		this.sessionId = 0;
		this.dataSize = 0;
		this.messageId = 0;
		
	}
	
	/**
	 * Creates a new packet based on previous packet definitions. Will not copy payload.
	 * @param packet an instance of the packet that should be copied.
	 */
	protected BaseSdlPacket(BaseSdlPacket packet){
		this.version = packet.version;
		this.encryption = packet.encryption;
		this.frameType = packet.frameType;	
		this.serviceType = packet.serviceType;
		this.frameInfo = packet.frameInfo;
		this.sessionId = packet.sessionId;
		this.dataSize = 0;
		this.messageId = 0;
	}

	public int getVersion() {
		return version;
	}

	public boolean isEncrypted() {
		return encryption;
	}

	public FrameType getFrameType() {
		switch(frameType){
		case FRAME_TYPE_CONTROL:
			return FrameType.Control;
		case FRAME_TYPE_FIRST:
			return FrameType.First;
		case FRAME_TYPE_CONSECUTIVE:
			return FrameType.Consecutive;
		case FRAME_TYPE_SINGLE:
		default:
			return FrameType.Single;
		}
	}

	public int getServiceType() {
		return serviceType;
	}

	public int getFrameInfo() {
		return frameInfo;
	}

	public int getSessionId() {
		return sessionId;
	}
	
	public int getMessageId() {
		return messageId;
	}
	
	public long getDataSize() {
		return dataSize;
	}

	public byte[] getPayload() {
		return payload;
	}
	
	public byte[] constructPacket() {
		if (bsonPayload != null && !bsonPayload.isEmpty()) {
			byte[] bsonBytes = BsonEncoder.encodeToBytes(bsonPayload);
			if(bsonBytes != null) {
				payload = bsonBytes;
				dataSize = bsonBytes.length;
			}
		}
		return constructPacket(version, encryption, frameType,
				serviceType, frameInfo, sessionId,
				dataSize, messageId, payload);
	}
	public void setPayload(byte[] bytes){
		this.payload = bytes;
	}
	/**
	 * Set the priority for this packet. The lower the number the higher the priority. <br>0 is the highest priority and the default.
	 * @param priority the priority of this packet
	 */
	public void setPriorityCoefficient(int priority){
		this.priorityCoefficient = priority;
	}
	public int getPrioirtyCoefficient(){
		return this.priorityCoefficient;
	}

	public void setTransportRecord(TransportRecord transportRecord){
		this.transportRecord = transportRecord;
	}

	public TransportRecord getTransportRecord() {
		return this.transportRecord;
	}

	/**
	 * This method takes in the various components to the SDL packet structure and creates a new byte array that can be sent via the transport
	 * @param version protocol version to use
	 * @param encryption whether or not this packet is encrypted
	 * @param frameType the packet frame type
	 * @param serviceType the service that this packet is associated with
	 * @param controlFrameInfo specific frame info related to this packet
	 * @param sessionId ID this packet is associated with
	 * @param dataSize size of the payload that will be added
	 * @param messageId ID of this specific packet
	 * @param payload raw data that will be attached to the packet (RPC message, raw bytes, etc)
	 * @return a byte[] representation of an SdlPacket built using the supplied params
	 */
	public static byte[] constructPacket(int version, boolean encryption, int frameType,
			int serviceType, int controlFrameInfo, int sessionId,
			int dataSize, int messageId, byte[] payload){

		ByteBuffer builder;
		switch(version){
			case 1:
				builder = ByteBuffer.allocate(HEADER_SIZE_V1 + dataSize);
				break;
			default:
				builder = ByteBuffer.allocate(HEADER_SIZE + dataSize);
				break;
		}
		
		builder.put((byte)((version<<4) + getEncryptionBit(encryption) + frameType));
		builder.put((byte)serviceType);
		builder.put((byte)controlFrameInfo);
		builder.put((byte)sessionId);
		
		builder.put((byte)((dataSize&0xFF000000)>>24));
		builder.put((byte)((dataSize&0x00FF0000)>>16));
		builder.put((byte)((dataSize&0x0000FF00)>>8));
		builder.put((byte)((dataSize&0x000000FF)));
		
		if(version>1){	//Version 1 did not include this part of the header
			builder.put((byte)((messageId&0xFF000000)>>24));
			builder.put((byte)((messageId&0x00FF0000)>>16));
			builder.put((byte)((messageId&0x0000FF00)>>8));
			builder.put((byte)((messageId&0x000000FF)));
		}
		
		if(payload!=null && payload.length>0){
			builder.put(payload);
		}
		
		return builder.array();
	}
	
	
	public static int getEncryptionBit(boolean encryption){
		if(encryption){
			return ENCRYPTION_MASK;
		}else{
			return 0;
		}
	}
	


@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("***** Sdl Packet ******");
		builder.append("\nVersion:  ").append(version);
		builder.append("\nEncryption:  ").append(encryption);
		builder.append("\nFrameType:  ").append(frameType);
		builder.append("\nServiceType:  ").append(serviceType);
		builder.append("\nFrameInfo:  ").append(frameInfo);
		builder.append("\nSessionId:  ").append(sessionId);
		builder.append("\nDataSize:  ").append(dataSize);
		if(version>1){
			builder.append("\nMessageId:  ").append(messageId);
		}
		builder.append("\n***** Sdl Packet  End******");


		return builder.toString();
	}

	public void setMessagingVersion(int version){
		this.messagingVersion = version;
	}

	public void putTag(String tag, Object data){
		if(bsonPayload == null){
			bsonPayload = new HashMap<>();
		}
		bsonPayload.put(tag, data);
	}

	public Object getTag(String tag){
		if(payload == null){
			return null;
		}else if(bsonPayload == null || bsonPayload.isEmpty()){
			bsonPayload = BsonEncoder.decodeFromBytes(payload);
		}

		if(bsonPayload == null){
			return null;
		}

		return bsonPayload.get(tag);
	}
}
