package com.smartdevicelink.protocol;

import java.io.ByteArrayOutputStream;

import com.smartdevicelink.SdlConnection.SdlConnection;
import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.protocol.SdlPacketFactory;
import com.smartdevicelink.protocol.WiProProtocol;
import com.smartdevicelink.protocol.WiProProtocol.MessageFrameAssembler;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.test.SdlUnitTestContants;
import com.smartdevicelink.transport.BaseTransportConfig;
import com.smartdevicelink.transport.MultiplexTransportConfig;
import com.smartdevicelink.transport.RouterServiceValidator;

import android.test.AndroidTestCase;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.protocol.BinaryFrameHeader}
 */
public class WiProProtocolTests extends AndroidTestCase {
	int max_int = 2147483647;
	byte[] payload;
	MultiplexTransportConfig config;
	SdlConnectionTestClass connection;
	WiProProtocol protocol;
	
	public void setUp(){
		config = new MultiplexTransportConfig(this.mContext,SdlUnitTestContants.TEST_APP_ID);
		connection = new SdlConnectionTestClass(config, null);
		protocol = new WiProProtocol(connection);
	}
	
	public void testNormalCase(){
		setUp();
		payload = new byte[]{0x00,0x02,0x05,0x01,0x01,0x01,0x05,0x00};
		byte sessionID = 1, version = 1;
		int messageID = 1;
		boolean encrypted = false;
		SdlPacket sdlPacket = SdlPacketFactory.createMultiSendDataFirst(SessionType.RPC, sessionID, messageID, version, payload, encrypted);
		MessageFrameAssembler assembler = protocol.getFrameAssemblerForFrame(sdlPacket);
		
		assertNotNull(assembler);
		
		OutOfMemoryError oom_error = null;
		NullPointerException np_exception = null;
		try{
			assembler.handleMultiFrameMessageFrame(sdlPacket);
		}catch(OutOfMemoryError e){
			oom_error = e;
		}catch(NullPointerException z){
			np_exception = z;
		}catch(Exception e){
			e.printStackTrace();
			assertNotNull(null);
		}
		
		assertNull(np_exception);
		assertNull(oom_error);
		
		payload = new byte[23534];
		sdlPacket = SdlPacketFactory.createMultiSendDataRest(SessionType.RPC, sessionID, payload.length, (byte) 3, messageID, version, payload, 0, 1500, encrypted);
		assembler = protocol.getFrameAssemblerForFrame(sdlPacket);
		try{
			assembler.handleMultiFrameMessageFrame(sdlPacket);
		}catch(OutOfMemoryError e){
			oom_error = e;
		}catch(NullPointerException z){
			np_exception = z;
		}catch(Exception e){
			assertNotNull(null);
		}
		
		assertNull(np_exception);
		assertNull(oom_error);
	}
	
	public void testOverallocatingAccumulator(){
		setUp();
		ByteArrayOutputStream builder = new ByteArrayOutputStream();
		for(int i = 0; i < 8; i++){
			builder.write(0x0F);
		}
		payload = builder.toByteArray();
		byte sessionID = 1, version = 1;
		int messageID = 1;
		boolean encrypted = false;
		SdlPacket sdlPacket = SdlPacketFactory.createMultiSendDataFirst(SessionType.RPC, sessionID, messageID, version, payload, encrypted);
		MessageFrameAssembler assembler = protocol.getFrameAssemblerForFrame(sdlPacket);
		
		OutOfMemoryError oom_error = null;
		NullPointerException np_exception = null;
		try{
			assembler.handleMultiFrameMessageFrame(sdlPacket);
		}catch(OutOfMemoryError e){
			oom_error = e;
		}catch(NullPointerException z){
			np_exception = z;
		}catch(Exception e){
			assertNotNull(null);
		}
		
		assertNull(np_exception);
		assertNull(oom_error);

		payload = new byte[23534];
		sdlPacket = SdlPacketFactory.createMultiSendDataRest(SessionType.RPC, sessionID, payload.length, (byte) 3, messageID, version, payload, 0, 1500, encrypted);
		assembler = protocol.getFrameAssemblerForFrame(sdlPacket);
		
		try{
			assembler.handleMultiFrameMessageFrame(sdlPacket);
		}catch(OutOfMemoryError e){
			oom_error = e;
		}catch(NullPointerException z){
			np_exception = z;
		}catch(Exception e){
			assertNotNull(null);
		}
		
		assertNull(np_exception);
		assertNull(oom_error);
		
	}
	
	protected class SdlConnectionTestClass extends SdlConnection{
		protected boolean connected = false;
		public SdlConnectionTestClass(BaseTransportConfig transportConfig) {
			super(transportConfig);
		}

		protected SdlConnectionTestClass(BaseTransportConfig transportConfig,RouterServiceValidator rsvp){
			super(transportConfig,rsvp);
		}
		
		@Override
		public void onTransportConnected() {
			super.onTransportConnected();
			connected = true;
		}

		@Override
		public void onTransportDisconnected(String info) {
			connected = false;
			//Grab a currently running router service
			RouterServiceValidator rsvp2 = new RouterServiceValidator(mContext);
			rsvp2.setFlags(RouterServiceValidator.FLAG_DEBUG_NONE);
			assertTrue(rsvp2.validate());
			assertNotNull(rsvp2.getService());
			SdlConnectionTestClass.cachedMultiConfig.setService(rsvp2.getService());
			super.onTransportDisconnected(info);
		}

		@Override
		public void onTransportError(String info, Exception e) {
			connected = false;
			super.onTransportError(info, e);
		}
	}
}