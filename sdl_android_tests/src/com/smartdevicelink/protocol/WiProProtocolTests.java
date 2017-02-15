package com.smartdevicelink.protocol;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import junit.framework.Assert;

import com.smartdevicelink.protocol.IProtocolListener;
import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.protocol.WiProProtocol;
import com.smartdevicelink.protocol.WiProProtocol.MessageFrameAssembler;
import com.smartdevicelink.protocol.enums.MessageType;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.test.SampleRpc;
import com.smartdevicelink.util.DebugTool;

import android.test.AndroidTestCase;

public class WiProProtocolTests extends AndroidTestCase {
	
	IProtocolListener defaultListener = new IProtocolListener(){

		@Override
		public void onProtocolMessageBytesToSend(SdlPacket packet) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProtocolMessageReceived(ProtocolMessage msg) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProtocolSessionStarted(SessionType sessionType,
				byte sessionID, byte version, String correlationID, int hashID,
				boolean isEncrypted) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProtocolSessionNACKed(SessionType sessionType,
				byte sessionID, byte version, String correlationID) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProtocolSessionEnded(SessionType sessionType,
				byte sessionID, String correlationID) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProtocolSessionEndedNACKed(SessionType sessionType,
				byte sessionID, String correlationID) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProtocolHeartbeat(SessionType sessionType, byte sessionID) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProtocolHeartbeatACK(SessionType sessionType,
				byte sessionID) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProtocolServiceDataACK(SessionType sessionType,
				int dataSize, byte sessionID) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onResetOutgoingHeartbeat(SessionType sessionType,
				byte sessionID) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onResetIncomingHeartbeat(SessionType sessionType,
				byte sessionID) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProtocolError(String info, Exception e) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	
	public void testBase(){
		WiProProtocol wiProProtocol = new WiProProtocol(defaultListener);
		
	}
	
	public void testVersion(){
		WiProProtocol wiProProtocol = new WiProProtocol(defaultListener);
		
		wiProProtocol.setVersion((byte)0x01);
		assertEquals((byte)0x01,wiProProtocol.getVersion());
		
		wiProProtocol = new WiProProtocol(defaultListener);
		wiProProtocol.setVersion((byte)0x02);
		assertEquals((byte)0x02,wiProProtocol.getVersion());
		
		wiProProtocol = new WiProProtocol(defaultListener);
		wiProProtocol.setVersion((byte)0x03);
		assertEquals((byte)0x03,wiProProtocol.getVersion());
		
		wiProProtocol = new WiProProtocol(defaultListener);
		wiProProtocol.setVersion((byte)0x04);
		assertEquals((byte)0x04,wiProProtocol.getVersion());
		
		//If we get newer than 4, it should fall back to 4
		wiProProtocol = new WiProProtocol(defaultListener);
		wiProProtocol.setVersion((byte)0x05);
		assertEquals((byte)0x04,wiProProtocol.getVersion());
		
		//Is this right?
		wiProProtocol = new WiProProtocol(defaultListener);
		wiProProtocol.setVersion((byte)0x00);
		assertEquals((byte)0x01,wiProProtocol.getVersion());
	}
	
	public void testMtu(){
		WiProProtocol wiProProtocol = new WiProProtocol(defaultListener);
		
		wiProProtocol.setVersion((byte)0x01);
		 
		try{
			Field field = wiProProtocol.getClass().getDeclaredField("MAX_DATA_SIZE");    
			field.setAccessible(true);
			int mtu = (Integer) field.get(wiProProtocol);
			assertEquals(mtu, 1500-8);
			//Ok our reflection works we can test the rest of the cases
			
			//Version 2
			wiProProtocol.setVersion((byte)0x02);
			mtu = (Integer) field.get(wiProProtocol);
			assertEquals(mtu, 1500-12);
			
			//Version 3
			wiProProtocol.setVersion((byte)0x03);
			mtu = (Integer) field.get(wiProProtocol);
			assertEquals(mtu, 131072);

			//Version 4
			wiProProtocol.setVersion((byte)0x04);
			mtu = (Integer) field.get(wiProProtocol);
			assertEquals(mtu, 131072);
			
			//Version 4+
			wiProProtocol.setVersion((byte)0x05);
			mtu = (Integer) field.get(wiProProtocol);
			assertEquals(mtu, 1500-12);
			
		}catch(Exception e){
			Assert.fail("Exceptin during reflection");
		}

	}
	
	public void testHandleFrame(){
		SampleRpc sampleRpc = new SampleRpc(4);
		WiProProtocol wiProProtocol = new WiProProtocol(defaultListener);
		MessageFrameAssembler assembler = wiProProtocol.new MessageFrameAssembler();
		try{
			assembler.handleFrame(sampleRpc.toSdlPacket());
		}catch(Exception e){
			Assert.fail("Exceptin during handleFrame - " + e.toString());
		}
	}
	public void testHandleFrameCorrupt(){
		SampleRpc sampleRpc = new SampleRpc(4);
		BinaryFrameHeader header = sampleRpc.getBinaryFrameHeader(true);
		header.setJsonSize(Integer.MAX_VALUE);
		sampleRpc.setBinaryFrameHeader(header);
		WiProProtocol wiProProtocol = new WiProProtocol(defaultListener);
		MessageFrameAssembler assembler = wiProProtocol.new MessageFrameAssembler();
		try{
			assembler.handleFrame(sampleRpc.toSdlPacket());
		}catch(Exception e){
			Assert.fail("Exceptin during handleFrame - " + e.toString());
		}
	}
	
	public void testHandleSingleFrameMessageFrame(){
		SampleRpc sampleRpc = new SampleRpc(4);
		WiProProtocol wiProProtocol = new WiProProtocol(defaultListener);
		MessageFrameAssembler assembler = wiProProtocol.new MessageFrameAssembler();

		
		try{
			Method  method = assembler.getClass().getDeclaredMethod ("handleSingleFrameMessageFrame", SdlPacket.class);
			method.setAccessible(true);	
			method.invoke (assembler, sampleRpc.toSdlPacket());	
		}catch(Exception e){
			Assert.fail("Exceptin during handleSingleFrameMessageFrame - " + e.toString());
		}	
	}
	
	public void testHandleSingleFrameMessageFrameCorruptBfh(){
		SampleRpc sampleRpc = new SampleRpc(4);
		
		//Create a corrupted header
		BinaryFrameHeader header = sampleRpc.getBinaryFrameHeader(true);
		header.setJsonSize(Integer.MAX_VALUE);
		sampleRpc.setBinaryFrameHeader(header);
		
		assertEquals(Integer.MAX_VALUE,sampleRpc.getBinaryFrameHeader(false).getJsonSize());
		SdlPacket packet = sampleRpc.toSdlPacket();
		
		assertEquals(SdlPacket.SERVICE_TYPE_RPC,packet.serviceType);
		
		
		WiProProtocol wiProProtocol = new WiProProtocol(defaultListener);
		MessageFrameAssembler assembler = wiProProtocol.new MessageFrameAssembler();

		
		try{
			Method  method = assembler.getClass().getDeclaredMethod("handleSingleFrameMessageFrame", SdlPacket.class);
			method.setAccessible(true);	
			method.invoke (assembler, sampleRpc.toSdlPacket());	
		}catch(Exception e){
			Assert.fail("Exceptin during handleSingleFrameMessageFrame - " + e.toString());
		}	
	}
	


}
