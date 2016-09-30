package com.smartdevicelink.transport;

import com.smartdevicelink.protocol.SdlPacket;


public class SdlPsm{
	//private static final String TAG = "Sdl PSM";
	//Each state represents the byte that should be incomming
	
	public static final int START_STATE							= 	0x0;
	public static final int SERVICE_TYPE_STATE					= 	0x02;
	public static final int CONTROL_FRAME_INFO_STATE			= 	0x03;
	public static final int SESSION_ID_STATE					= 	0x04;
	public static final int DATA_SIZE_1_STATE					= 	0x05;
	public static final int DATA_SIZE_2_STATE					= 	0x06;
	public static final int DATA_SIZE_3_STATE					= 	0x07;
	public static final int DATA_SIZE_4_STATE					= 	0x08;
	public static final int MESSAGE_1_STATE						= 	0x09;
	public static final int MESSAGE_2_STATE						= 	0x0A;
	public static final int MESSAGE_3_STATE						= 	0x0B;
	public static final int MESSAGE_4_STATE						= 	0x0C;
	public static final int DATA_PUMP_STATE						= 	0x0D;
	public static final int FINISHED_STATE 						=	0xFF;
	public static final int ERROR_STATE 						= 	-1;

	
	private static final byte FIRST_FRAME_DATA_SIZE 			= 0x08;
	
	private static final int VERSION_MASK 						= 0xF0; //4 highest bits
	private static final int COMPRESSION_MASK 					= 0x08; //4th lowest bit
	private static final int FRAME_TYPE_MASK 					= 0x07; //3 lowest bits
	
	
	
	int state ;
	
	int version;
	boolean compression;   	
	int frameType;
	int serviceType;		
	int controlFrameInfo;	
	int sessionId;			
	int dumpSize, dataLength;
	int messageId = 0;
	
	byte[] payload;
	
	public SdlPsm(){
		reset();
	}
	
	public boolean handleByte(byte data) {
		//Log.trace(TAG, data + " = incomming");
		state = transitionOnInput(data,state);
		
		if(state==ERROR_STATE){
			return false;
		}
		return true;
	}
	
	private int transitionOnInput(byte rawByte, int state){
		switch(state){
		case START_STATE:
			version = (rawByte&(byte)VERSION_MASK)>>4;
			//Log.trace(TAG, "Version: " + version);
			if(version==0){ //It should never be 0
				return ERROR_STATE;
			}
			compression = (1 == ((rawByte&(byte)COMPRESSION_MASK)>>3));
			
			
			frameType = rawByte&(byte)FRAME_TYPE_MASK;
			//Log.trace(TAG, rawByte + " = Frame Type: " + frameType);
			
			if((version < 1 || version > 4) //These are known versions supported by this library.
					&& frameType!=SdlPacket.FRAME_TYPE_CONTROL){
					return ERROR_STATE;
			}

			if(frameType<SdlPacket.FRAME_TYPE_CONTROL || frameType > SdlPacket.FRAME_TYPE_CONSECUTIVE){
				return ERROR_STATE;
			}
			
			return SERVICE_TYPE_STATE;
			
		case SERVICE_TYPE_STATE:
			serviceType = (int)(rawByte&0xFF);
			return CONTROL_FRAME_INFO_STATE;
			
		case CONTROL_FRAME_INFO_STATE:
			controlFrameInfo = (int)(rawByte&0xFF);  
			//Log.trace(TAG,"Frame Info: " + controlFrameInfo);
			switch(frameType){
				case SdlPacket.FRAME_TYPE_CONTROL:
					/*if(frameInfo<FRAME_INFO_HEART_BEAT 
							|| (frameInfo>FRAME_INFO_END_SERVICE_ACK 
									&& (frameInfo!=FRAME_INFO_SERVICE_DATA_ACK || frameInfo!=FRAME_INFO_HEART_BEAT_ACK))){
						return ERROR_STATE;
					}*/ //Although some bits are reserved...whatever
					break;
				case SdlPacket.FRAME_TYPE_SINGLE: //Fall through since they are both the same
				case SdlPacket.FRAME_TYPE_FIRST:
					if(controlFrameInfo!=0x00){
						return ERROR_STATE;
					}
					break;
				case SdlPacket.FRAME_TYPE_CONSECUTIVE:
					//It might be a good idea to check packet sequence numbers here
					break;

				default:
					return ERROR_STATE;
			}
			return SESSION_ID_STATE;
			
		case SESSION_ID_STATE:
			sessionId = (int)(rawByte&0xFF);
			return DATA_SIZE_1_STATE;
			
		case DATA_SIZE_1_STATE:
			//First data size byte
			//Log.d(TAG, "Data byte 1: " + rawByte);
			dataLength += ((int)(rawByte& 0xFF))<<24; //3 bytes x 8 bits
			//Log.d(TAG, "Data Size 1 : " + dataLength);
			return DATA_SIZE_2_STATE;
			
		case DATA_SIZE_2_STATE:
			//Log.d(TAG, "Data byte 2: " + rawByte);
			dataLength += ((int)(rawByte& 0xFF))<<16; //2 bytes x 8 bits
			//Log.d(TAG, "Data Size 2 : " + dataLength);
			return DATA_SIZE_3_STATE;
			
		case DATA_SIZE_3_STATE:
			//Log.d(TAG, "Data byte 3: " + rawByte);
			dataLength += ((int)(rawByte& 0xFF))<<8; //1 byte x 8 bits
			//Log.d(TAG, "Data Size 3 : " + dataLength);
			return DATA_SIZE_4_STATE;
			
		case DATA_SIZE_4_STATE:
			//Log.d(TAG, "Data byte 4: " + rawByte);
			dataLength+=((int)rawByte) & 0xFF;
			//Log.trace(TAG, "Data Size: " + dataLength);
			//We should have data length now for the pump state
			switch(frameType){ //If all is correct we should break out of this switch statement
			case SdlPacket.FRAME_TYPE_SINGLE:
			case SdlPacket.FRAME_TYPE_CONSECUTIVE:
				break;
			case SdlPacket.FRAME_TYPE_CONTROL:
				//Ok, well here's some interesting bit of knowledge. Because the start session request is from the phone with no knowledge of version it sends out
				//a v1 packet. THEREFORE there is no message id field. **** Now you know and knowing is half the battle ****
				if(version==1 && controlFrameInfo == SdlPacket.FRAME_INFO_START_SERVICE){
					if(dataLength==0){
						return FINISHED_STATE; //We are done if we don't have any payload
					}
					payload = new byte[dataLength];
					dumpSize = dataLength;
					return DATA_PUMP_STATE;
				}
				break; 
				
			case SdlPacket.FRAME_TYPE_FIRST:
				if(dataLength==FIRST_FRAME_DATA_SIZE){
					break;
				}
			default:
				return ERROR_STATE;
			}
			if(version==1){ //Version 1 packets will not have message id's
				if(dataLength == 0){
					return FINISHED_STATE; //We are done if we don't have any payload
				}
				payload = new byte[dataLength];
				dumpSize = dataLength;
				return DATA_PUMP_STATE;
			}else{
				return MESSAGE_1_STATE;
			}
			
		case MESSAGE_1_STATE:
			messageId += ((int)(rawByte& 0xFF))<<24; //3 bytes x 8 bits
			return MESSAGE_2_STATE;
			
		case MESSAGE_2_STATE:
			messageId += ((int)(rawByte& 0xFF))<<16; //2 bytes x 8 bits
			return MESSAGE_3_STATE;
			
		case MESSAGE_3_STATE:
			messageId += ((int)(rawByte& 0xFF))<<8; //1 byte x 8 bits
			return MESSAGE_4_STATE;
			
		case MESSAGE_4_STATE:
			messageId+=((int)rawByte) & 0xFF;

			if(dataLength==0){
				return FINISHED_STATE; //We are done if we don't have any payload
			}
			try{
				payload = new byte[dataLength];
			}catch(OutOfMemoryError oom){
				return ERROR_STATE;
			}
			dumpSize = dataLength;
			return DATA_PUMP_STATE;
			
		case DATA_PUMP_STATE:
			payload[dataLength-dumpSize] = rawByte;
			dumpSize--;
			//Do we have any more bytes to read in?
			if(dumpSize>0){
				return DATA_PUMP_STATE;
			}
			else if(dumpSize==0){
				return FINISHED_STATE;
			}else{
				return ERROR_STATE;
			}
		case FINISHED_STATE: //We shouldn't be here...Should have been reset
		default: 
			return ERROR_STATE;
			
		}
		
	}
	
	public SdlPacket getFormedPacket(){
		if(state==FINISHED_STATE){
			//Log.trace(TAG, "Finished packet.");
			return new SdlPacket(version, compression, frameType,
					serviceType, controlFrameInfo, sessionId,
					dataLength, messageId, payload);
		}else{
			return null;
		}
	}
	
	public int getState() {
		return state;
	}

	public void reset() {
		version = 0;
		state = START_STATE;
		messageId = 0;
		dataLength = 0;
		frameType = 0x00; //Set it to null
		payload = null;
	}

}
