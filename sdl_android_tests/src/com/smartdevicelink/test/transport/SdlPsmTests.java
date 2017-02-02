package com.smartdevicelink.test.transport;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.protocol.WiProProtocol;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.transport.SdlPsm;
import com.smartdevicelink.transport.SdlRouterService;

import android.util.Log;
import junit.framework.TestCase;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.transport.SdlPsm}
 */
public class SdlPsmTests extends TestCase {
	
	/**
	 * This is a unit test for the following methods : 
	 * {@link com.smartdevicelink.transport.SdlPsm#transitionOnInput()}
	 */
	public void testConfigs () {
		// Test Values
		byte rawByte = (byte) 0x0;
		int tooBigForControlFrame = 1501, tooBigToAllocate = 2147483647;
		SdlPsm sdlPsm = new SdlPsm();
		
		int STATE_EXTENDED_MAX = 0, STATE_EXACT_MAX = 0, STATE_OOM_ERROR = 0;
		
		try{
			Method method = SdlPsm.class.getDeclaredMethod("transitionOnInput", byte.class, int.class);
			method.setAccessible(true);
			
			Field frameType = SdlPsm.class.getDeclaredField("frameType");
			Field dataLength = SdlPsm.class.getDeclaredField("dataLength");
			Field version = SdlPsm.class.getDeclaredField("version");
			Field controlFrameInfo = SdlPsm.class.getDeclaredField("controlFrameInfo");
			frameType.setAccessible(true);
			dataLength.setAccessible(true);
			version.setAccessible(true);
			controlFrameInfo.setAccessible(true);
			
			version.set(sdlPsm, 1);
			controlFrameInfo.set(sdlPsm, SdlPacket.FRAME_INFO_START_SERVICE);
			
			frameType.set(sdlPsm, SdlPacket.FRAME_TYPE_CONTROL);
			
			dataLength.set(sdlPsm, tooBigForControlFrame);
			STATE_EXTENDED_MAX  = (Integer) method.invoke(sdlPsm, rawByte, SdlPsm.DATA_SIZE_4_STATE);
			
			dataLength.set(sdlPsm, tooBigForControlFrame - 1);
			STATE_EXACT_MAX  = (Integer) method.invoke(sdlPsm, rawByte, SdlPsm.DATA_SIZE_4_STATE);
			
			frameType.set(sdlPsm, SdlPacket.FRAME_TYPE_SINGLE);
			
			dataLength.set(sdlPsm, tooBigToAllocate);
			STATE_OOM_ERROR = (Integer) method.invoke(sdlPsm, rawByte, SdlPsm.DATA_SIZE_4_STATE);
			
		}catch(Exception e){
			Log.e("Shouldn't reach this", e.toString());
			assert(false);
		}
		
		// Comparison Values
		int EXPECTED_STATE_EXTENDED_MAX = SdlPsm.ERROR_STATE, EXPECTED_STATE_OOM_ERROR = SdlPsm.ERROR_STATE;
		int EXPECTED_STATE_EXACT_MAX = SdlPsm.DATA_PUMP_STATE;
		
		// Valid Tests
		assertEquals(Test.MATCH, EXPECTED_STATE_EXTENDED_MAX, STATE_EXTENDED_MAX);
		assertEquals(Test.MATCH, EXPECTED_STATE_OOM_ERROR, STATE_OOM_ERROR);
		assertEquals(Test.MATCH, EXPECTED_STATE_EXACT_MAX, STATE_EXACT_MAX);
		
		// Invalid/Null Tests
		
	}
}