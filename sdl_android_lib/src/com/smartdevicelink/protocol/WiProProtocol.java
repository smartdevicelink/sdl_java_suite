package com.smartdevicelink.protocol;

import java.io.ByteArrayOutputStream;
import java.util.Hashtable;

import android.util.Log;

import com.smartdevicelink.exception.*;
import com.smartdevicelink.exception.enums.SdlExceptionCause;
import com.smartdevicelink.protocol.enums.*;
import com.smartdevicelink.protocol.interfaces.IProtocolListener;
import com.smartdevicelink.util.BitConverter;
import com.smartdevicelink.util.DebugTool;

public class WiProProtocol extends AbstractProtocol {
	byte version = 1;
	private final static String FAILURE_PROPOGATING_MSG = "Failure propagating ";

	private static final int MTU_SIZE = 1500;
	private static int HEADER_SIZE = 8;
	private static int MAX_DATA_SIZE = MTU_SIZE - HEADER_SIZE;

	boolean haveHeader = false;
	byte[] headerBuf = new byte[HEADER_SIZE];
	int headerBufWritePos = 0;
	ProtocolFrameHeader currentHeader = null;
	byte[] dataBuf = null;
	int dataBufWritePos = 0;
	
	int hashId = 0;
	int messageId = 0;

    @SuppressWarnings("unused")
    private int heartbeatSendIntervalMs = 0;
    @SuppressWarnings("unused")
    private int heartbeatReceiveIntervalMs = 0;
	
	Hashtable<Integer, MessageFrameAssembler> assemblerForMessageId = new Hashtable<Integer, MessageFrameAssembler>();
	Hashtable<Byte, Hashtable<Integer, MessageFrameAssembler>> assemblerForSessionId = new Hashtable<Byte, Hashtable<Integer, MessageFrameAssembler>>();
	Hashtable<Byte, Object> messageLocks = new Hashtable<Byte, Object>();

	// Hide no-arg ctor
	private WiProProtocol() {
		super(null);
	} // end-ctor

	public WiProProtocol(IProtocolListener protocolListener) {
		super(protocolListener);
	} // end-ctor
	
	public byte getVersion() {
		return this.version;
	}
	
	public void setVersion(byte version) {
		this.version = version;
		if (version > 1) {
			this.version = 2;
			HEADER_SIZE = 12;
			MAX_DATA_SIZE = MTU_SIZE - HEADER_SIZE;
			headerBuf = new byte[HEADER_SIZE];
		}
		else if (version == 1){
			HEADER_SIZE = 8;
			MAX_DATA_SIZE = MTU_SIZE - HEADER_SIZE;
			headerBuf = new byte[HEADER_SIZE];			
		}
			
	}

	public void startProtocolSession(SessionType sessionType) {
		ProtocolFrameHeader header = ProtocolFrameHeaderFactory.createStartSession(sessionType, 0x00, version, (byte) 0x00);
		sendFrameToTransport(header);
	} // end-method

	private void sendStartProtocolSessionAck(SessionType sessionType, byte sessionId) {
		ProtocolFrameHeader header = ProtocolFrameHeaderFactory.createStartSessionAck(sessionType, sessionId, 0x00, version);
		sendFrameToTransport(header);
	} // end-method
	
	public void endProtocolSession(SessionType sessionType, byte sessionId) {
		ProtocolFrameHeader header = ProtocolFrameHeaderFactory.createEndSession(sessionType, sessionId, hashId, version);
		//byte[] data = new byte[4];
		//data = BitConverter.intToByteArray(hashID);
		//handleProtocolFrameToSend(header, data, 0, data.length);
		sendFrameToTransport(header);
	} // end-method

	public void sendMessage(ProtocolMessage protocolMsg) {	
		protocolMsg.setRpcType((byte) 0x00); //always sending a request
		SessionType sessionType = protocolMsg.getSessionType();
		byte sessionId = protocolMsg.getSessionId();
		
		byte[] data = null;
		if (version > 1 && sessionType != SessionType.NAV) {
			if (protocolMsg.getBulkData() != null) {
				data = new byte[12 + protocolMsg.getJsonSize() + protocolMsg.getBulkData().length];
				sessionType = SessionType.BULK_DATA;
			} else data = new byte[12 + protocolMsg.getJsonSize()];
			BinaryFrameHeader binFrameHeader = new BinaryFrameHeader();
			binFrameHeader = ProtocolFrameHeaderFactory.createBinaryFrameHeader(protocolMsg.getRpcType(), protocolMsg.getFunctionId(), protocolMsg.getCorrId(), protocolMsg.getJsonSize());
			System.arraycopy(binFrameHeader.assembleHeaderBytes(), 0, data, 0, 12);
			System.arraycopy(protocolMsg.getData(), 0, data, 12, protocolMsg.getJsonSize());
			if (protocolMsg.getBulkData() != null) {
				System.arraycopy(protocolMsg.getBulkData(), 0, data, 12 + protocolMsg.getJsonSize(), protocolMsg.getBulkData().length);
			}
		} else {
			data = protocolMsg.getData();
		}
		
		// Get the message lock for this protocol session
		Object messageLock = messageLocks.get(sessionId);
		if (messageLock == null) {
			handleProtocolError("Error sending protocol message to SDL.", 
					new SdlException("Attempt to send protocol message prior to startSession ACK.", SdlExceptionCause.SDL_UNAVAILABLE));
			return;
		}
		
		synchronized(messageLock) {
			if (data.length > MAX_DATA_SIZE) {
				
				messageId++;
				ProtocolFrameHeader firstHeader = ProtocolFrameHeaderFactory.createMultiSendDataFirst(sessionType, sessionId, messageId, version);
	
				// Assemble first frame.
				int frameCount = data.length / MAX_DATA_SIZE;
				if (data.length % MAX_DATA_SIZE > 0) {
					frameCount++;
				}
				//byte[] firstFrameData = new byte[HEADER_SIZE];
				byte[] firstFrameData = new byte[8];
				// First four bytes are data size.
				System.arraycopy(BitConverter.intToByteArray(data.length), 0, firstFrameData, 0, 4);
				// Second four bytes are frame count.
				System.arraycopy(BitConverter.intToByteArray(frameCount), 0, firstFrameData, 4, 4);
				
				handleProtocolFrameToSend(firstHeader, firstFrameData, 0, firstFrameData.length);
				
				int currentOffset = 0;
				byte frameSequenceNumber = 0;
				
				for (int i = 0; i < frameCount; i++) {
					if (i < (frameCount - 1)) {
	                     ++frameSequenceNumber;
	                        if (frameSequenceNumber ==
	                                ProtocolFrameHeader.FRAME_DATA_FINAL_CONSECUTIVE) {
	                            // we can't use 0x00 as frameSequenceNumber, because
	                            // it's reserved for the last frame
	                            ++frameSequenceNumber;
	                        }
					} else {
						frameSequenceNumber = ProtocolFrameHeader.FRAME_DATA_FINAL_CONSECUTIVE;
					} // end-if
					
					int bytesToWrite = data.length - currentOffset;
					if (bytesToWrite > MAX_DATA_SIZE) { 
						bytesToWrite = MAX_DATA_SIZE; 
					}

					ProtocolFrameHeader consecHeader = ProtocolFrameHeaderFactory.createMultiSendDataRest(sessionType, sessionId, bytesToWrite, frameSequenceNumber , messageId, version);
					handleProtocolFrameToSend(consecHeader, data, currentOffset, bytesToWrite);
					currentOffset += bytesToWrite;
				}
			} else {
				messageId++;
				ProtocolFrameHeader header = ProtocolFrameHeaderFactory.createSingleSendData(sessionType, sessionId, data.length, messageId, version);
				handleProtocolFrameToSend(header, data, 0, data.length);
			}
		}
	}

	private void sendFrameToTransport(ProtocolFrameHeader header) {
		handleProtocolFrameToSend(header, null, 0, 0);
	}

	public void handleReceivedBytes(byte[] receivedBytes, int receivedBytesLength) {

		byte[] remainingBytes = processReceivedBytes(receivedBytes, receivedBytesLength);
		while (remainingBytes != null)
		{
			remainingBytes = processReceivedBytes(remainingBytes, remainingBytes.length);
		}
	}

	private byte[] processReceivedBytes(byte[] receivedBytes, int receivedBytesLength) {
		int receivedBytesReadPos = 0;
		
		//Check for a version difference
		if (version == 1) {
			//Nothing has been read into the buffer and version is 2
			if (headerBufWritePos == 0 && (byte) (receivedBytes[0] >>> 4) > 1) {
				setVersion((byte) (receivedBytes[0] >>> 4));
			//Buffer has something in it and version is 2
			} else if ((byte) (headerBuf[0] >>> 4) > 1) {
				//safe current state of the buffer and also set the new version
				byte[] tempHeader = new byte[headerBufWritePos];
				tempHeader = headerBuf;
				setVersion((byte) (headerBuf[0] >>> 4));
				headerBuf = tempHeader;
			} else if ( (version == 1) && ( HEADER_SIZE == 12) ){
				setVersion((byte) (1));
			}
			
		}
		
		// If I don't yet know the message size, grab those bytes.
		if (!haveHeader) {
			// If I can't get the size, just get the bytes that are there.
			int headerBytesNeeded = headerBuf.length - headerBufWritePos;
			if (receivedBytesLength < headerBytesNeeded) {
				System.arraycopy(receivedBytes, receivedBytesReadPos,
						headerBuf, headerBufWritePos, receivedBytesLength);
				headerBufWritePos += receivedBytesLength;
				return null;
			} else {
			// If I got the size, allocate the buffer
				System.arraycopy(receivedBytes, receivedBytesReadPos,
						headerBuf, headerBufWritePos, headerBytesNeeded);
				headerBufWritePos += headerBytesNeeded;
				receivedBytesReadPos += headerBytesNeeded;
				haveHeader = true;
				currentHeader  = ProtocolFrameHeader.parseWiProHeader(headerBuf);
				
				
				int iDataSize = currentHeader.getDataSize();	

				if (iDataSize <= 4000)
				{
					dataBuf = new byte[iDataSize];
				}
				else
				{
					//something is wrong with the header
					Log.e("HandleReceivedBytes", "Corrupt header found, request to allocate a byte array of size: " + iDataSize);	
					Log.e("HandleReceivedBytes", "headerBuf: " + headerBuf.toString());
					Log.e("HandleReceivedBytes", "currentHeader: " + currentHeader.toString());
					Log.e("HandleReceivedBytes", "receivedBytes: " + receivedBytes.toString());
					Log.e("HandleReceivedBytes", "receivedBytesReadPos: " + receivedBytesReadPos);
					Log.e("HandleReceivedBytes", "headerBufWritePos: " + headerBufWritePos);
					Log.e("HandleReceivedBytes", "headerBytesNeeded: " + headerBytesNeeded);
					handleProtocolError("Error handling protocol message from sdl, header invalid.", 
							new SdlException("Error handling protocol message from sdl, header invalid.", SdlExceptionCause.INVALID_HEADER));
					return null;
				}
				dataBufWritePos = 0;
			}
		}

		if (dataBuf == null)
		{
			Log.e("HandleReceivedBytes", "Error: Databuffer is null, logging debug info.");
			try
			{
				Log.e("HandleReceivedBytes", "headerBuf: " + headerBuf.toString());
				Log.e("HandleReceivedBytes", "currentHeader: " + currentHeader.toString());
				Log.e("HandleReceivedBytes", "receivedBytes: " + receivedBytes.toString());
				Log.e("HandleReceivedBytes", "receivedBytesReadPos: " + receivedBytesReadPos);
				Log.e("HandleReceivedBytes", "receivedBytesLength: " + receivedBytesLength);
				Log.e("HandleReceivedBytes", "headerBufWritePos: " + headerBufWritePos);
			}
			catch(NullPointerException e)
			{
				Log.e("HandleReceivedBytes", "Null Pointer Encountered: " + e);
			}

			handleProtocolError("Error handling protocol message from sdl, header invalid.",
					new SdlException("Error handling protocol message from sdl, data buffer is null.", SdlExceptionCause.DATA_BUFFER_NULL));
			return null;
		}

		int bytesLeft = receivedBytesLength - receivedBytesReadPos;
		int bytesNeeded = dataBuf.length - dataBufWritePos;
		// If I don't have enough bytes for the message, just grab what's there.
		if (bytesLeft < bytesNeeded) {
			System.arraycopy(receivedBytes, receivedBytesReadPos, dataBuf,
					dataBufWritePos, bytesLeft);
			dataBufWritePos += bytesLeft;
			return null;
		} else {
		// Fill the buffer and call the handler!
			System.arraycopy(receivedBytes, receivedBytesReadPos, dataBuf, dataBufWritePos, bytesNeeded);
			receivedBytesReadPos += bytesNeeded;

			MessageFrameAssembler assembler = getFrameAssemblerForFrame(currentHeader);
			handleProtocolFrameReceived(currentHeader, dataBuf, assembler);

			// Reset all class member variables for next frame
			dataBuf = null;
			dataBufWritePos = 0;
			haveHeader = false;
			headerBuf = new byte[HEADER_SIZE];
			currentHeader = null;
			headerBufWritePos = 0;
			
			// If there are any bytes left, recurse.
			int moreBytesLeft = receivedBytesLength - receivedBytesReadPos;
			if (moreBytesLeft > 0) {
				byte[] moreBytes = new byte[moreBytesLeft];
				System.arraycopy(receivedBytes, receivedBytesReadPos,
						moreBytes, 0, moreBytesLeft);
				return moreBytes;
			}
		}
		return null;
	}
	
	protected MessageFrameAssembler getFrameAssemblerForFrame(ProtocolFrameHeader header) {
		Hashtable<Integer, MessageFrameAssembler> hashSessionId = assemblerForSessionId.get(Byte.valueOf(header.getSessionId()));
		if (hashSessionId == null) {
			hashSessionId = new Hashtable<Integer, MessageFrameAssembler>();
			assemblerForSessionId.put(Byte.valueOf(header.getSessionId()), hashSessionId);
		} // end-if
		
		MessageFrameAssembler ret = (MessageFrameAssembler) assemblerForMessageId.get(Integer.valueOf(header.getMessageId()));
		if (ret == null) {
			ret = new MessageFrameAssembler();
			assemblerForMessageId.put(Integer.valueOf(header.getMessageId()), ret);
		} // end-if
		
		return ret;
	} // end-method

	protected class MessageFrameAssembler {
		protected boolean hasFirstFrame = false;
		protected boolean hasSecondFrame = false;
		protected ByteArrayOutputStream accumulator = null;
		protected int totalSize = 0;
		protected int framesRemaining = 0;

		protected void handleFirstDataFrame(ProtocolFrameHeader header, byte[] data) {
			//The message is new, so let's figure out how big it is.
			hasFirstFrame = true;
			totalSize = BitConverter.intFromByteArray(data, 0) - HEADER_SIZE;
			framesRemaining = BitConverter.intFromByteArray(data, 4);
			accumulator = new ByteArrayOutputStream(totalSize);
		}
		
		protected void handleSecondFrame(ProtocolFrameHeader header, byte[] data) {
			handleRemainingFrame(header, data);
		}
		
		protected void handleRemainingFrame(ProtocolFrameHeader header, byte[] data) {
			accumulator.write(data, 0, header.getDataSize());
			notifyIfFinished(header);
		}
		
		protected void notifyIfFinished(ProtocolFrameHeader header) {
			//if (framesRemaining == 0) {
			if (header.getFrameType() == FrameType.CONSECUTIVE && header.getFrameData() == 0x0) 
			{
				ProtocolMessage message = new ProtocolMessage();
				message.setSessionType(header.getSessionType());
				message.setSessionId(header.getSessionId());
				//If it is WiPro 2.0 it must have binary header
				if (version > 1) {
					BinaryFrameHeader binFrameHeader = BinaryFrameHeader.
							parseBinaryHeader(accumulator.toByteArray());
					message.setVersion(version);
					message.setRpcType(binFrameHeader.getRpcType());
					message.setFunctionId(binFrameHeader.getFunctionId());
					message.setCorrId(binFrameHeader.getCorrId());
					if (binFrameHeader.getJsonSize() > 0) message.setData(binFrameHeader.getJsonData());
					if (binFrameHeader.getBulkData() != null) message.setBulkData(binFrameHeader.getBulkData());
				} else message.setData(accumulator.toByteArray());
				
				assemblerForMessageId.remove(header.getMessageId());
				
				try {
					handleProtocolMessageReceived(message);
				} catch (Exception excp) {
					DebugTool.logError(FAILURE_PROPOGATING_MSG + "onProtocolMessageReceived: " + excp.toString(), excp);
				} // end-catch
				
				hasFirstFrame = false;
				hasSecondFrame = false;
				accumulator = null;
			} // end-if
		} // end-method
		
		protected void handleMultiFrameMessageFrame(ProtocolFrameHeader header, byte[] data) {
			//if (!hasFirstFrame) {
			//	hasFirstFrame = true;
			if (header.getFrameType() == FrameType.FIRST)
			{
				handleFirstDataFrame(header, data);
			}
				
			//} else if (!hasSecondFrame) {
			//	hasSecondFrame = true;
			//	framesRemaining--;
			//	handleSecondFrame(header, data);
			//} else {
			//	framesRemaining--;
			else
			{
				handleRemainingFrame(header, data);
			}
				
			//}
		} // end-method
		
		protected void handleFrame(ProtocolFrameHeader header, byte[] data) {
			if (header.getFrameType().equals(FrameType.CONTROL)) {
				handleControlFrame(header, data);
			} else {
				// Must be a form of data frame (single, first, consecutive, etc.)
				if (   header.getFrameType() == FrameType.FIRST
					|| header.getFrameType() == FrameType.CONSECUTIVE
					) {
					handleMultiFrameMessageFrame(header, data);
				} else {
					handleSingleFrameMessageFrame(header, data);
				}
			} // end-if
		} // end-method
		
        private void handleProtocolHeartbeatAck(ProtocolFrameHeader header,
                byte[] data) {
        		WiProProtocol.this.handleProtocolHeartbeatACK(header.getSessionType(),header.getSessionId());
        } // end-method		
		
		private void handleControlFrame(ProtocolFrameHeader header, byte[] data) {
            if (header.getFrameData() == FrameDataControlFrameType.HEARTBEAT_ACK.getValue()) {
                handleProtocolHeartbeatAck(header, data);
            }
            else if (header.getFrameData() == FrameDataControlFrameType.START_SESSION.getValue()) {
				sendStartProtocolSessionAck(header.getSessionType(), header.getSessionId());
			} else if (header.getFrameData() == FrameDataControlFrameType.START_SESSION_ACK.getValue()) {
				// Use this sessionID to create a message lock
				Object messageLock = messageLocks.get(header.getSessionId());
				if (messageLock == null) {
					messageLock = new Object();
					messageLocks.put(header.getSessionId(), messageLock);
				}
				//hashID = BitConverter.intFromByteArray(data, 0);
				if (version > 1) hashId = header.getMessageId();
				handleProtocolSessionStarted(header.getSessionType(), header.getSessionId(), version, "");				
			} else if (header.getFrameData() == FrameDataControlFrameType.START_SESSION_NACK.getValue()) {
				if (header.getSessionType().eq(SessionType.NAV)) {
					handleProtocolSessionNack(header.getSessionType(), header.getSessionId(), version, "");
				} else {
					handleProtocolError("Got StartSessionNACK for protocol sessionID=" + header.getSessionId(), null);
				}
			} else if (header.getFrameData() == FrameDataControlFrameType.END_SESSION.getValue()) {
				//if (hashID == BitConverter.intFromByteArray(data, 0)) 
				if (version > 1) {
					if (hashId == header.getMessageId())
						handleProtocolSessionEnded(header.getSessionType(), header.getSessionId(), "");
				} else handleProtocolSessionEnded(header.getSessionType(), header.getSessionId(), "");
			} else if (header.getFrameData() == FrameDataControlFrameType.END_SESSION_ACK.getValue()) {
				handleProtocolSessionEnded(header.getSessionType(), header.getSessionId(), "");
			}
		} // end-method
				
		private void handleSingleFrameMessageFrame(ProtocolFrameHeader header, byte[] data) {
			ProtocolMessage message = new ProtocolMessage();
			if (header.getSessionType() == SessionType.RPC) {
				message.setMessageType(MessageType.RPC);
			} else if (header.getSessionType() == SessionType.BULK_DATA) {
				message.setMessageType(MessageType.BULK);
			} // end-if
			message.setSessionType(header.getSessionType());
			message.setSessionId(header.getSessionId());
			//If it is WiPro 2.0 it must have binary header
			if (version > 1) {
				BinaryFrameHeader binFrameHeader = BinaryFrameHeader.
						parseBinaryHeader(data);
				message.setVersion(version);
				message.setRpcType(binFrameHeader.getRpcType());
				message.setFunctionId(binFrameHeader.getFunctionId());
				message.setCorrId(binFrameHeader.getCorrId());
				if (binFrameHeader.getJsonSize() > 0) message.setData(binFrameHeader.getJsonData());
				if (binFrameHeader.getBulkData() != null) message.setBulkData(binFrameHeader.getBulkData());
			} else message.setData(data);
			
			assemblerForMessageId.remove(header.getMessageId());
			
			try {
				handleProtocolMessageReceived(message);
			} catch (Exception ex) {
				DebugTool.logError(FAILURE_PROPOGATING_MSG + "onProtocolMessageReceived: " + ex.toString(), ex);
				handleProtocolError(FAILURE_PROPOGATING_MSG + "onProtocolMessageReceived: ", ex);
			} // end-catch
		} // end-method
	} // end-class

	@Override
	public void startProtocolService(SessionType sessionType, byte sessionId) {
		ProtocolFrameHeader header = ProtocolFrameHeaderFactory.createStartSession(sessionType, 0x00, version, sessionId);
		sendFrameToTransport(header);
		
	}

	@Override
	public void setHeartbeatSendInterval(int heartbeatSendIntervalMs) {
		this.heartbeatSendIntervalMs = heartbeatSendIntervalMs;
		
	}

	@Override
	public void setHeartbeatReceiveInterval(int heartbeatReceiveIntervalMs) {
		this.heartbeatReceiveIntervalMs = heartbeatReceiveIntervalMs;
		
	}

	@Override
	public void sendHeartBeat(byte sessionId) {
        final ProtocolFrameHeader heartbeat = ProtocolFrameHeaderFactory.createHeartbeat(SessionType.CONTROL, sessionId, version);        
        sendFrameToTransport(heartbeat);		
	}
} // end-class