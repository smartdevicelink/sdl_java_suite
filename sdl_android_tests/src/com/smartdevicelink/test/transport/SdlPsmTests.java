package com.smartdevicelink.test.transport;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.transport.SdlPsm;
import com.smartdevicelink.protocol.WiProProtocol;

import android.util.Log;
import junit.framework.TestCase;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.transport.SdlPsm}
 */
public class SdlPsmTests extends TestCase {
	private static final String TAG = "SdlPsmTests";
	private static final int MAX_DATA_LENGTH = WiProProtocol.V1_V2_MTU_SIZE - WiProProtocol.V1_HEADER_SIZE;
	SdlPsm sdlPsm;
	Field frameType, dataLength, version, controlFrameInfo;
	Method transitionOnInput;
	byte rawByte = (byte) 0x0;
	
	protected void setUp() throws Exception{
		super.setUp();
		sdlPsm = new SdlPsm();
		transitionOnInput = SdlPsm.class.getDeclaredMethod("transitionOnInput", byte.class, int.class);
		transitionOnInput.setAccessible(true);
		
		frameType = SdlPsm.class.getDeclaredField("frameType");
		dataLength = SdlPsm.class.getDeclaredField("dataLength");
		version = SdlPsm.class.getDeclaredField("version");
		controlFrameInfo = SdlPsm.class.getDeclaredField("controlFrameInfo");
		frameType.setAccessible(true);
		dataLength.setAccessible(true);
		version.setAccessible(true);
		controlFrameInfo.setAccessible(true);
	}
	
	/**
	 * These are unit tests for the following methods : 
	 * {@link com.smartdevicelink.transport.SdlPsm#transitionOnInput()}
	 */
	
	public void testGarbledControlFrame() {
		try{
			rawByte = 0x0;
			version.set(sdlPsm, 1);
			controlFrameInfo.set(sdlPsm, SdlPacket.FRAME_INFO_START_SERVICE);
			frameType.set(sdlPsm, SdlPacket.FRAME_TYPE_CONTROL);
			
			dataLength.set(sdlPsm, MAX_DATA_LENGTH + 1);
			int STATE = (Integer) transitionOnInput.invoke(sdlPsm, rawByte, SdlPsm.DATA_SIZE_4_STATE);
			
			assertEquals(Test.MATCH, SdlPsm.ERROR_STATE, STATE);
		}catch (Exception e){
			Log.e(TAG, e.toString());
		}
	}
	
	public void testMaximumControlFrame(){
		try{
			rawByte = 0x0;
			version.set(sdlPsm, 1);
			controlFrameInfo.set(sdlPsm, SdlPacket.FRAME_INFO_START_SERVICE);
			frameType.set(sdlPsm, SdlPacket.FRAME_TYPE_CONTROL);
			
			dataLength.set(sdlPsm, MAX_DATA_LENGTH);
			int STATE = (Integer) transitionOnInput.invoke(sdlPsm, rawByte, SdlPsm.DATA_SIZE_4_STATE);
			
			assertEquals(Test.MATCH, SdlPsm.DATA_PUMP_STATE, STATE);
		}catch (Exception e){
			Log.e(TAG, e.toString());
		}	
	}
	
	public void testOutOfMemoryDS4(){
		try{
			rawByte = 0x0;
			version.set(sdlPsm, 1);
			frameType.set(sdlPsm, SdlPacket.FRAME_TYPE_SINGLE);
			
			dataLength.set(sdlPsm, 2147483647);
			int STATE = (Integer) transitionOnInput.invoke(sdlPsm, rawByte, SdlPsm.DATA_SIZE_4_STATE);
			
			assertEquals(Test.MATCH, SdlPsm.ERROR_STATE, STATE);
		}catch (Exception e){
			Log.e(TAG, e.toString());
		}
	}
	
	protected void tearDown() throws Exception{
		super.tearDown();
	}
}