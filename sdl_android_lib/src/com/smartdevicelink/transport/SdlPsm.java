package com.smartdevicelink.transport;

import android.util.Log;

import com.c4.android.transport.IPacketStateMachine;

public class SdlPsm implements IPacketStateMachine {
	private static final String TAG = "Sdl PSM";
	//Each state represents the byte that should be incomming
	//public static final int VERISION_COMP_TYPE_STATE			= 	0x01;
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

	
	private static final byte FIRST_FRAME_DATA_SIZE 			= 0x08;
	
	private static final int VERSION_MASK 						= 0xF0; //4 highest bits
	private static final int COMPRESSION_MASK 					= 0x08; //4th lowest bit
	private static final int FRAME_TYPE_MASK 					= 0x07; //3 lowest bits
	
	private static final int FRAME_TYPE_CONTROL 				= 0x00;
	private static final int FRAME_TYPE_SINGLE 					= 0x01;
	private static final int FRAME_TYPE_FIRST 					= 0x02;
	private static final int FRAME_TYPE_CONSECUTIVE				= 0x03;
	
	//Frame Info
	private static final int FRAME_INFO_HEART_BEAT 				= 0x00;
	private static final int FRAME_INFO_END_SERVICE_ACK			= 0x06;
	private static final int FRAME_INFO_SERVICE_DATA_ACK		= 0xFE;
	private static final int FRAME_INFO_HEART_BEAT_ACK			= 0xFF;
	
	
	
	int state ;
	int dataLength;
	int version;
	int frameType;
	
	public SdlPsm(){
		reset();
	}
	
	@Override
	public boolean handleByte(byte data) {
		//Log.d(TAG, data + " = incomming");
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
			
			frameType = rawByte&(byte)FRAME_TYPE_MASK;
			//Log.d(TAG, rawByte + " = Frame Type: " + frameType + " Version: " + version);
			
			switch(version){
			case 0:
				if(frameType!=FRAME_TYPE_CONTROL){
					return ERROR_STATE;
				}
			case 1:
			case 2:
				break;
			default:
				return ERROR_STATE;
			}

			if(frameType<FRAME_TYPE_CONTROL || frameType > FRAME_TYPE_CONSECUTIVE){
				return ERROR_STATE;
			}
			
			return SERVICE_TYPE_STATE;
			
		case SERVICE_TYPE_STATE:
			return CONTROL_FRAME_INFO_STATE;
			
		case CONTROL_FRAME_INFO_STATE:
			int frameInfo = (int)(rawByte&0xFF);  //TODO WE MIGHT WANT TO SAVE THIS?
			//Log.d(TAG,"Frame Info: " + frameInfo);
			switch(frameType){
				case FRAME_TYPE_CONTROL:
					/*if(frameInfo<FRAME_INFO_HEART_BEAT 
							|| (frameInfo>FRAME_INFO_END_SERVICE_ACK 
									&& (frameInfo!=FRAME_INFO_SERVICE_DATA_ACK || frameInfo!=FRAME_INFO_HEART_BEAT_ACK))){
						return ERROR_STATE;
					}*/ //Although some bits are reserved...whatever
					break;
				case FRAME_TYPE_SINGLE: //Fall through since they are both the same
				case FRAME_TYPE_FIRST:
					if(frameInfo!=0x00){
						return ERROR_STATE;
					}
					break;
				case FRAME_TYPE_CONSECUTIVE:
					//It might be a good idea to check packet sequence numbers here
					break;

				default:
					return ERROR_STATE;
			}
			return SESSION_ID_STATE;
			
		case SESSION_ID_STATE:
			
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
			//Log.d(TAG, "Data Size: " + dataLength);
			//We should have data length now for the pump state
			/* we actually don't need this
			switch(frameType){ //If all is correct we should break out of this switch statement
			case FRAME_TYPE_SINGLE:
			case FRAME_TYPE_CONSECUTIVE:
				break;
			case FRAME_TYPE_CONTROL:
				break; //FIXME check the spec because something is off (TDK or spec).
				//if(dataLength == 0){ //As it should be
				//	break; 
				//}
			case FRAME_TYPE_FIRST:
				if(dataLength==FIRST_FRAME_DATA_SIZE){
					break;
				}
			default:
				return ERROR_STATE;
			}*/
			
			return MESSAGE_1_STATE;
			
		case MESSAGE_1_STATE:
			//Log.d(TAG, "Message 1: " + rawByte);
			return MESSAGE_2_STATE;
			
		case MESSAGE_2_STATE:
			//Log.d(TAG, "Message 2: " + rawByte);
			return MESSAGE_3_STATE;
			
		case MESSAGE_3_STATE:
			//Log.d(TAG, "Message 3: " + rawByte);
			return MESSAGE_4_STATE;
			
		case MESSAGE_4_STATE:
			//Log.d(TAG, "Message 4: " + rawByte);
			if(dataLength==0){
				return FINISHED_STATE; //We are done if we don't have any payload
			}
			return DATA_PUMP_STATE;
			
		case DATA_PUMP_STATE:
			//Log.d(TAG, "Pump: "+dataLength+": " + rawByte);
			dataLength--;
			//Log.d(TAG,rawByte + " read. Data Length remaining: " + dataLength);
			//Do we have any more bytes to read in?
			if(dataLength>0){
				return DATA_PUMP_STATE;
			}
			else if(dataLength==0){
				
				return FINISHED_STATE;
			}else{
				return ERROR_STATE;
			}
		case FINISHED_STATE: //We shouldn't be here...gtfo
		default: 
			return ERROR_STATE;
			
		}
		
	}
	
	@Override
	public int getState() {
		return state;
	}

	@Override
	public void reset() {
		version = 0;
		state = START_STATE;
		dataLength = 0;
		frameType = 0x00; //Set it to null
	}

}
