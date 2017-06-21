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
	Field frameType, dataLength, version, controlFrameInfo, state;
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
		state = SdlPsm.class.getDeclaredField("state");
		frameType.setAccessible(true);
		dataLength.setAccessible(true);
		version.setAccessible(true);
		controlFrameInfo.setAccessible(true);
		state.setAccessible(true);
	}
	/**
	 * These are unit tests for the following methods :
	 * {@link com.smartdevicelink.transport.SdlPsm#transitionOnInput()}
	 * {@link com.smartdevicelink.transport.SdlPsm#handleByte(byte)}
	 */


	public void testHandleByte() {
		try{
			rawByte = 0x0F;
			version.set(sdlPsm, 1);
			boolean result = sdlPsm.handleByte(rawByte);
			assertEquals(Test.MATCH, false, result);
			state.set(sdlPsm, SdlPsm.DATA_SIZE_1_STATE);
			result = sdlPsm.handleByte(rawByte);
			assertEquals(Test.MATCH, true, result);
		}catch (Exception e){
			Log.e(TAG, e.toString());
		}
	}

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

	public void testTransitionOnInputStartState(){
		try{
			rawByte = 0x04;
			version.set(sdlPsm, 1);
			frameType.set(sdlPsm, SdlPacket.FRAME_TYPE_SINGLE);
			dataLength.set(sdlPsm, 2143647);
			int STATE = (Integer) transitionOnInput.invoke(sdlPsm, rawByte, SdlPsm.START_STATE);
			assertEquals(Test.MATCH, SdlPsm.ERROR_STATE, STATE);
		}catch (Exception e){
			Log.e(TAG, e.toString());
		}
	}

	public void testTransitionOnInputServiceTypeState(){
		try{
			rawByte = 0x0;
			version.set(sdlPsm, 1);
			frameType.set(sdlPsm, SdlPacket.FRAME_TYPE_SINGLE);
			dataLength.set(sdlPsm, 2143647);
			int STATE = (Integer) transitionOnInput.invoke(sdlPsm, rawByte, SdlPsm.SERVICE_TYPE_STATE);
			assertEquals(Test.MATCH, SdlPsm.CONTROL_FRAME_INFO_STATE, STATE);
		}catch (Exception e){
			Log.e(TAG, e.toString());
		}
	}

	public void testTransitionOnInputControlFrameState(){
		try{
			rawByte = 0x0;
			version.set(sdlPsm, 1);
			frameType.set(sdlPsm, SdlPacket.FRAME_TYPE_SINGLE);

			dataLength.set(sdlPsm, 2143647);
			int STATE = (Integer) transitionOnInput.invoke(sdlPsm, rawByte, SdlPsm.CONTROL_FRAME_INFO_STATE);
			assertEquals(Test.MATCH, SdlPsm.SESSION_ID_STATE, STATE);

			frameType.set(sdlPsm, SdlPacket.FRAME_TYPE_CONTROL);
			transitionOnInput.invoke(sdlPsm, rawByte, SdlPsm.CONTROL_FRAME_INFO_STATE);
			assertEquals(Test.MATCH, SdlPsm.SESSION_ID_STATE, STATE);

			frameType.set(sdlPsm, SdlPacket.FRAME_TYPE_CONSECUTIVE);
			transitionOnInput.invoke(sdlPsm, rawByte, SdlPsm.CONTROL_FRAME_INFO_STATE);
			assertEquals(Test.MATCH, SdlPsm.SESSION_ID_STATE, STATE);

			frameType.set(sdlPsm, SdlPacket.FRAME_TYPE_FIRST);
			transitionOnInput.invoke(sdlPsm, rawByte, SdlPsm.CONTROL_FRAME_INFO_STATE);
			assertEquals(Test.MATCH, SdlPsm.SESSION_ID_STATE, STATE);

		}catch (Exception e){
			Log.e(TAG, e.toString());
		}
	}


	public void testTransitionOnInputSessionIDState(){
		try{
			rawByte = 0x0;
			version.set(sdlPsm, 1);
			frameType.set(sdlPsm, SdlPacket.FRAME_TYPE_SINGLE);

			dataLength.set(sdlPsm, 2147483647);
			int STATE = (Integer) transitionOnInput.invoke(sdlPsm, rawByte, SdlPsm.SESSION_ID_STATE);
			assertEquals(Test.MATCH, SdlPsm.DATA_SIZE_1_STATE, STATE);
		}catch (Exception e){
			Log.e(TAG, e.toString());
		}
	}

	public void testTransitionOnInputDS1(){
		try{
			rawByte = 0x0;
			version.set(sdlPsm, 1);
			frameType.set(sdlPsm, SdlPacket.FRAME_TYPE_SINGLE);

			dataLength.set(sdlPsm, 2147483647);
			int STATE = (Integer) transitionOnInput.invoke(sdlPsm, rawByte, SdlPsm.DATA_SIZE_1_STATE);
			assertEquals(Test.MATCH, SdlPsm.DATA_SIZE_2_STATE, STATE);
		}catch (Exception e){
			Log.e(TAG, e.toString());
		}
	}

	public void testTransitionOnInputDS2(){
		try{
			rawByte = 0x0;
			version.set(sdlPsm, 1);
			frameType.set(sdlPsm, SdlPacket.FRAME_TYPE_SINGLE);

			dataLength.set(sdlPsm, 2147483647);
			int STATE = (Integer) transitionOnInput.invoke(sdlPsm, rawByte, SdlPsm.DATA_SIZE_2_STATE);
			assertEquals(Test.MATCH, SdlPsm.DATA_SIZE_3_STATE, STATE);
		}catch (Exception e){
			Log.e(TAG, e.toString());
		}
	}


	public void testTransitionOnInputDS3(){
		try{
			rawByte = 0x0;
			version.set(sdlPsm, 1);
			frameType.set(sdlPsm, SdlPacket.FRAME_TYPE_SINGLE);
			dataLength.set(sdlPsm, 2147483647);
			int STATE = (Integer) transitionOnInput.invoke(sdlPsm, rawByte, SdlPsm.DATA_SIZE_3_STATE);
			assertEquals(Test.MATCH, SdlPsm.DATA_SIZE_4_STATE, STATE);
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

	public void testTransitionOnInputM1S(){
		try{
			rawByte = 0x0;
			version.set(sdlPsm, 1);
			frameType.set(sdlPsm, SdlPacket.FRAME_TYPE_SINGLE);
			dataLength.set(sdlPsm, 2147483647);
			int STATE = (Integer) transitionOnInput.invoke(sdlPsm, rawByte, SdlPsm.MESSAGE_1_STATE);
			assertEquals(Test.MATCH, SdlPsm.MESSAGE_2_STATE, STATE);
		}catch (Exception e){
			Log.e(TAG, e.toString());
		}
	}

	public void testTransitionOnInputM2S(){
		try{
			rawByte = 0x0;
			version.set(sdlPsm, 1);
			frameType.set(sdlPsm, SdlPacket.FRAME_TYPE_SINGLE);
			dataLength.set(sdlPsm, 2147483647);
			int STATE = (Integer) transitionOnInput.invoke(sdlPsm, rawByte, SdlPsm.MESSAGE_2_STATE);
			assertEquals(Test.MATCH, SdlPsm.MESSAGE_3_STATE, STATE);
		}catch (Exception e){
			Log.e(TAG, e.toString());
		}
	}

	public void testTransitionOnInputM3S(){
		try{
			rawByte = 0x0;
			version.set(sdlPsm, 1);
			frameType.set(sdlPsm, SdlPacket.FRAME_TYPE_SINGLE);
			dataLength.set(sdlPsm, 2147483647);
			int STATE = (Integer) transitionOnInput.invoke(sdlPsm, rawByte, SdlPsm.MESSAGE_3_STATE);
			assertEquals(Test.MATCH, SdlPsm.MESSAGE_4_STATE, STATE);
		}catch (Exception e){
			Log.e(TAG, e.toString());
		}
	}

	public void testTransitionOnInputM4S(){
		try{
			rawByte = 0x0;
			version.set(sdlPsm, 1);
			frameType.set(sdlPsm, SdlPacket.FRAME_TYPE_SINGLE);
			dataLength.set(sdlPsm, 2143647);
			int STATE = (Integer) transitionOnInput.invoke(sdlPsm, rawByte, SdlPsm.MESSAGE_4_STATE);
			assertEquals(Test.MATCH, SdlPsm.DATA_PUMP_STATE, STATE);
		}catch (Exception e){
			Log.e(TAG, e.toString());
		}
	}

	public void testOutOfMemoryM4S(){
		try{
			rawByte = 0x0;
			version.set(sdlPsm, 1);
			frameType.set(sdlPsm, SdlPacket.FRAME_TYPE_SINGLE);
			dataLength.set(sdlPsm, 2147483647);
			int STATE = (Integer) transitionOnInput.invoke(sdlPsm, rawByte, SdlPsm.MESSAGE_4_STATE);
			assertEquals(Test.MATCH, SdlPsm.ERROR_STATE, STATE);
		}catch (Exception e){
			Log.e(TAG, e.toString());
		}
	}

	public void testFinishedStateM4S(){
		try{
			rawByte = 0x0;
			version.set(sdlPsm, 1);
			frameType.set(sdlPsm, SdlPacket.FRAME_TYPE_SINGLE);
			dataLength.set(sdlPsm, 0);
			int STATE = (Integer) transitionOnInput.invoke(sdlPsm, rawByte, SdlPsm.MESSAGE_4_STATE);
			assertEquals(Test.MATCH, SdlPsm.FINISHED_STATE, STATE);
		}catch (Exception e){
			Log.e(TAG, e.toString());
		}
	}

	public void testTransitionOnInputDPSFinished(){
		try{
			rawByte = 0x0;
			version.set(sdlPsm, 1);
			frameType.set(sdlPsm, SdlPacket.FRAME_TYPE_SINGLE);
			dataLength.set(sdlPsm, 0);
			int STATE = (Integer) transitionOnInput.invoke(sdlPsm, rawByte, SdlPsm.DATA_PUMP_STATE);
			assertEquals(Test.MATCH, SdlPsm.FINISHED_STATE, STATE);
		}catch (Exception e){
			Log.e(TAG, e.toString());
		}
	}

	public void testTransitionOnInputDPS(){
		try{
			rawByte = 0x0;
			version.set(sdlPsm, 1);
			frameType.set(sdlPsm, SdlPacket.FRAME_TYPE_SINGLE);
			dataLength.set(sdlPsm, 2367);
			int STATE = (Integer) transitionOnInput.invoke(sdlPsm, rawByte, SdlPsm.DATA_PUMP_STATE);
			assertEquals(Test.MATCH, SdlPsm.DATA_PUMP_STATE, STATE);
		}catch (Exception e){
			Log.e(TAG, e.toString());
		}
	}

	public void testGetFormedPacket(){
		try{
			rawByte = 0x0;
			version.set(sdlPsm, 1);
			frameType.set(sdlPsm, SdlPacket.FRAME_TYPE_SINGLE);
			dataLength.set(sdlPsm, 2367);
			transitionOnInput.invoke(sdlPsm, rawByte, SdlPsm.DATA_PUMP_STATE);
			assertEquals(Test.MATCH, null, sdlPsm.getFormedPacket());
			dataLength.set(sdlPsm, 0);
			transitionOnInput.invoke(sdlPsm, rawByte, SdlPsm.DATA_PUMP_STATE);
			assertNotNull(sdlPsm.getFormedPacket());
		}catch (Exception e){
			Log.e(TAG, e.toString());
		}
	}

	public void testGetState(){
		try{
			rawByte = 0x0;
			version.set(sdlPsm, 1);
			frameType.set(sdlPsm, SdlPacket.FRAME_TYPE_SINGLE);
			state.set(sdlPsm, SdlPsm.DATA_PUMP_STATE);
			assertEquals(Test.MATCH, SdlPsm.DATA_PUMP_STATE, sdlPsm.getState());
		}catch (Exception e){
			Log.e(TAG, e.toString());
		}
	}

	protected void tearDown() throws Exception{
		super.tearDown();
	}
}