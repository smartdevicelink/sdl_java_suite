package com.smartdevicelink.protocol;

import java.io.ByteArrayOutputStream;
import java.util.Hashtable;

import android.util.Log;

import com.smartdevicelink.exception.*;
import com.smartdevicelink.protocol.enums.*;
import com.smartdevicelink.util.BitConverter;
import com.smartdevicelink.util.DebugTool;

public class WiProProtocol extends AbstractProtocol {
	byte _version = 1;
	private final static String FailurePropagating_Msg = "Failure propagating ";

	private static final int V1_V2_MTU_SIZE = 1500;
	private static final int V3_V4_MTU_SIZE = 131072;
	private static int HEADER_SIZE = 8;
	private static int MAX_DATA_SIZE = V1_V2_MTU_SIZE  - HEADER_SIZE;

	boolean _haveHeader = false;
	byte[] _headerBuf = new byte[HEADER_SIZE];
	int _headerBufWritePos = 0;
	ProtocolFrameHeader _currentHeader = null;
	byte[] _dataBuf = null;
	int _dataBufWritePos = 0;
	
	int hashID = 0;
	int messageID = 0;

    @SuppressWarnings("unused")
    private int _heartbeatSendInterval_ms = 0;
    @SuppressWarnings("unused")
    private int _heartbeatReceiveInterval_ms = 0;
	
	Hashtable<Integer, MessageFrameAssembler> _assemblerForMessageID = new Hashtable<Integer, MessageFrameAssembler>();
	Hashtable<Byte, Hashtable<Integer, MessageFrameAssembler>> _assemblerForSessionID = new Hashtable<Byte, Hashtable<Integer, MessageFrameAssembler>>();
	Hashtable<Byte, Object> _messageLocks = new Hashtable<Byte, Object>();

	// Hide no-arg ctor
	private WiProProtocol() {
		super(null);
	} // end-ctor

	public WiProProtocol(IProtocolListener protocolListener) {
		super(protocolListener);
	} // end-ctor
	
	public byte getVersion() {
		return this._version;
	}
	
	public void setVersion(byte version) {
        if (version > 4) {
            this._version = 4; //protect for future, proxy only supports v4 or lower
            HEADER_SIZE = 12;
            MAX_DATA_SIZE = V1_V2_MTU_SIZE - HEADER_SIZE; //default to lowest size since capabilities of this version are unknown
            _headerBuf = new byte[HEADER_SIZE];
        } else if (version == 4) {
            this._version = version;
            HEADER_SIZE = 12;
            MAX_DATA_SIZE = V3_V4_MTU_SIZE; //versions 4 supports 128k MTU
            _headerBuf = new byte[HEADER_SIZE];
        } else if (version == 3) {
            this._version = version;
            HEADER_SIZE = 12;
            MAX_DATA_SIZE = V3_V4_MTU_SIZE; //versions 3 supports 128k MTU
            _headerBuf = new byte[HEADER_SIZE];
        } else if (version == 2) {
            this._version = version;
            HEADER_SIZE = 12;
            MAX_DATA_SIZE = V1_V2_MTU_SIZE - HEADER_SIZE;
            _headerBuf = new byte[HEADER_SIZE];
        } else if (version == 1){
            this._version = version;
            HEADER_SIZE = 8;
            MAX_DATA_SIZE = V1_V2_MTU_SIZE - HEADER_SIZE;
            _headerBuf = new byte[HEADER_SIZE];
        }
    }

	public void StartProtocolSession(SessionType sessionType) {
		ProtocolFrameHeader header = ProtocolFrameHeaderFactory.createStartSession(sessionType, 0x00, _version, (byte) 0x00);
		sendFrameToTransport(header);
	} // end-method

	private void sendStartProtocolSessionACK(SessionType sessionType, byte sessionID) {
		ProtocolFrameHeader header = ProtocolFrameHeaderFactory.createStartSessionACK(sessionType, sessionID, 0x00, _version);
		sendFrameToTransport(header);
	} // end-method
	
	public void EndProtocolSession(SessionType sessionType, byte sessionID) {
		ProtocolFrameHeader header = ProtocolFrameHeaderFactory.createEndSession(sessionType, sessionID, hashID, _version);
		//byte[] data = new byte[4];
		//data = BitConverter.intToByteArray(hashID);
		//handleProtocolFrameToSend(header, data, 0, data.length);
		sendFrameToTransport(header);
	} // end-method

	public void SendMessage(ProtocolMessage protocolMsg) {	
		protocolMsg.setRPCType((byte) 0x00); //always sending a request
		SessionType sessionType = protocolMsg.getSessionType();
		byte sessionID = protocolMsg.getSessionID();
		
		byte[] data = null;
		if (_version > 1 && sessionType != SessionType.NAV && sessionType != SessionType.PCM) {
			if (protocolMsg.getBulkData() != null) {
				data = new byte[12 + protocolMsg.getJsonSize() + protocolMsg.getBulkData().length];
				sessionType = SessionType.BULK_DATA;
			} else data = new byte[12 + protocolMsg.getJsonSize()];
			BinaryFrameHeader binFrameHeader = new BinaryFrameHeader();
			binFrameHeader = ProtocolFrameHeaderFactory.createBinaryFrameHeader(protocolMsg.getRPCType(), protocolMsg.getFunctionID(), protocolMsg.getCorrID(), protocolMsg.getJsonSize());
			System.arraycopy(binFrameHeader.assembleHeaderBytes(), 0, data, 0, 12);
			System.arraycopy(protocolMsg.getData(), 0, data, 12, protocolMsg.getJsonSize());
			if (protocolMsg.getBulkData() != null) {
				System.arraycopy(protocolMsg.getBulkData(), 0, data, 12 + protocolMsg.getJsonSize(), protocolMsg.getBulkData().length);
			}
		} else {
			data = protocolMsg.getData();
		}
		
		// Get the message lock for this protocol session
		Object messageLock = _messageLocks.get(sessionID);
		if (messageLock == null) {
			handleProtocolError("Error sending protocol message to SDL.", 
					new SdlException("Attempt to send protocol message prior to startSession ACK.", SdlExceptionCause.SDL_UNAVAILABLE));
			return;
		}
		
		synchronized(messageLock) {
			if (data.length > MAX_DATA_SIZE) {
				
				messageID++;
				ProtocolFrameHeader firstHeader = ProtocolFrameHeaderFactory.createMultiSendDataFirst(sessionType, sessionID, messageID, _version);
	
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
	                                ProtocolFrameHeader.FrameDataFinalConsecutiveFrame) {
	                            // we can't use 0x00 as frameSequenceNumber, because
	                            // it's reserved for the last frame
	                            ++frameSequenceNumber;
	                        }
					} else {
						frameSequenceNumber = ProtocolFrameHeader.FrameDataFinalConsecutiveFrame;
					} // end-if
					
					int bytesToWrite = data.length - currentOffset;
					if (bytesToWrite > MAX_DATA_SIZE) { 
						bytesToWrite = MAX_DATA_SIZE; 
					}

					ProtocolFrameHeader consecHeader = ProtocolFrameHeaderFactory.createMultiSendDataRest(sessionType, sessionID, bytesToWrite, frameSequenceNumber , messageID, _version);
					handleProtocolFrameToSend(consecHeader, data, currentOffset, bytesToWrite);
					currentOffset += bytesToWrite;
				}
			} else {
				messageID++;
				ProtocolFrameHeader header = ProtocolFrameHeaderFactory.createSingleSendData(sessionType, sessionID, data.length, messageID, _version);
				handleProtocolFrameToSend(header, data, 0, data.length);
			}
		}
	}

	private void sendFrameToTransport(ProtocolFrameHeader header) {
		handleProtocolFrameToSend(header, null, 0, 0);
	}

	public void HandleReceivedBytes(byte[] receivedBytes, int receivedBytesLength) {

		byte[] remainingBytes = processReceivedBytes(receivedBytes, receivedBytesLength);
		while (remainingBytes != null)
		{
			remainingBytes = processReceivedBytes(remainingBytes, remainingBytes.length);
		}
	}

	private byte[] processReceivedBytes(byte[] receivedBytes, int receivedBytesLength) {
		int receivedBytesReadPos = 0;
		
		//Check for a version difference
		if (_version == 1) {
			//Nothing has been read into the buffer and version is 2
			if (_headerBufWritePos == 0 && (byte) (receivedBytes[0] >>> 4) > 1) {
				setVersion((byte) (receivedBytes[0] >>> 4));
			//Buffer has something in it and version is 2
			} else if ((byte) (_headerBuf[0] >>> 4) > 1) {
				//safe current state of the buffer and also set the new version
				byte[] tempHeader = new byte[_headerBufWritePos];
				tempHeader = _headerBuf;
				setVersion((byte) (_headerBuf[0] >>> 4));
				_headerBuf = tempHeader;
			} else if ( (_version == 1) && ( HEADER_SIZE == 12) ){
				setVersion((byte) (1));
			}
			
		}
		
		// If I don't yet know the message size, grab those bytes.
		if (!_haveHeader) {
			// If I can't get the size, just get the bytes that are there.
			int headerBytesNeeded = _headerBuf.length - _headerBufWritePos;
			if (receivedBytesLength < headerBytesNeeded) {
				System.arraycopy(receivedBytes, receivedBytesReadPos,
						_headerBuf, _headerBufWritePos, receivedBytesLength);
				_headerBufWritePos += receivedBytesLength;
				return null;
			} else {
			// If I got the size, allocate the buffer
				System.arraycopy(receivedBytes, receivedBytesReadPos,
						_headerBuf, _headerBufWritePos, headerBytesNeeded);
				_headerBufWritePos += headerBytesNeeded;
				receivedBytesReadPos += headerBytesNeeded;
				_haveHeader = true;
				_currentHeader  = ProtocolFrameHeader.parseWiProHeader(_headerBuf);
				
				
				int iDataSize = _currentHeader.getDataSize();	
				if (iDataSize <= MAX_DATA_SIZE) {
					_dataBuf = new byte[iDataSize];
				}
				else {
					//something is wrong with the header
					Log.e("HandleReceivedBytes", "Corrupt header found, request to allocate a byte array of size: " + iDataSize);	
					Log.e("HandleReceivedBytes", "_headerBuf: " + _headerBuf.toString());
					Log.e("HandleReceivedBytes", "_currentHeader: " + _currentHeader.toString());
					Log.e("HandleReceivedBytes", "receivedBytes: " + receivedBytes.toString());
					Log.e("HandleReceivedBytes", "receivedBytesReadPos: " + receivedBytesReadPos);
					Log.e("HandleReceivedBytes", "_headerBufWritePos: " + _headerBufWritePos);
					Log.e("HandleReceivedBytes", "headerBytesNeeded: " + headerBytesNeeded);
					handleProtocolError("Error handling protocol message from sdl, header invalid.", 
							new SdlException("Error handling protocol message from sdl, header invalid.", SdlExceptionCause.INVALID_HEADER));
					return null;
				}
				_dataBufWritePos = 0;
			}
		}

		if (_dataBuf == null)
		{
			Log.e("HandleReceivedBytes", "Error: Databuffer is null, logging debug info.");
			try
			{
				Log.e("HandleReceivedBytes", "_headerBuf: " + _headerBuf.toString());
				Log.e("HandleReceivedBytes", "_currentHeader: " + _currentHeader.toString());
				Log.e("HandleReceivedBytes", "receivedBytes: " + receivedBytes.toString());
				Log.e("HandleReceivedBytes", "receivedBytesReadPos: " + receivedBytesReadPos);
				Log.e("HandleReceivedBytes", "receivedBytesLength: " + receivedBytesLength);
				Log.e("HandleReceivedBytes", "_headerBufWritePos: " + _headerBufWritePos);
			}
			catch(NullPointerException e)
			{
				Log.e("HandleReceivedBytes", "Null Pointer Encountered: " + e);
			}

			handleProtocolError("Error handling protocol message from sdl, header invalid.",
					new SdlException("Error handling protocol message from sdl, data buffer is null.", SdlExceptionCause.DATA_BUFFER_NULL));
			return null;
		}
		
		onResetIncomingHeartbeat(_currentHeader.getSessionType(), _currentHeader.getSessionID());

		int bytesLeft = receivedBytesLength - receivedBytesReadPos;
		int bytesNeeded = _dataBuf.length - _dataBufWritePos;
		// If I don't have enough bytes for the message, just grab what's there.
		if (bytesLeft < bytesNeeded) {
			System.arraycopy(receivedBytes, receivedBytesReadPos, _dataBuf,
					_dataBufWritePos, bytesLeft);
			_dataBufWritePos += bytesLeft;
			return null;
		} else {
		// Fill the buffer and call the handler!
			System.arraycopy(receivedBytes, receivedBytesReadPos, _dataBuf, _dataBufWritePos, bytesNeeded);
			receivedBytesReadPos += bytesNeeded;

			MessageFrameAssembler assembler = getFrameAssemblerForFrame(_currentHeader);
			handleProtocolFrameReceived(_currentHeader, _dataBuf, assembler);

			// Reset all class member variables for next frame
			_dataBuf = null;
			_dataBufWritePos = 0;
			_haveHeader = false;
			_headerBuf = new byte[HEADER_SIZE];
			_currentHeader = null;
			_headerBufWritePos = 0;
			
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
		Hashtable<Integer, MessageFrameAssembler> hashSessionID = _assemblerForSessionID.get(Byte.valueOf(header.getSessionID()));
		if (hashSessionID == null) {
			hashSessionID = new Hashtable<Integer, MessageFrameAssembler>();
			_assemblerForSessionID.put(Byte.valueOf(header.getSessionID()), hashSessionID);
		} // end-if
		
		MessageFrameAssembler ret = (MessageFrameAssembler) _assemblerForMessageID.get(Integer.valueOf(header.getMessageID()));
		if (ret == null) {
			ret = new MessageFrameAssembler();
			_assemblerForMessageID.put(Integer.valueOf(header.getMessageID()), ret);
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
			if (header.getFrameType() == FrameType.Consecutive && header.getFrameData() == 0x0) 
			{
				ProtocolMessage message = new ProtocolMessage();
				message.setSessionType(header.getSessionType());
				message.setSessionID(header.getSessionID());
				//If it is WiPro 2.0 it must have binary header
				if (_version > 1) {
					BinaryFrameHeader binFrameHeader = BinaryFrameHeader.
							parseBinaryHeader(accumulator.toByteArray());
					message.setVersion(_version);
					message.setRPCType(binFrameHeader.getRPCType());
					message.setFunctionID(binFrameHeader.getFunctionID());
					message.setCorrID(binFrameHeader.getCorrID());
					if (binFrameHeader.getJsonSize() > 0) message.setData(binFrameHeader.getJsonData());
					if (binFrameHeader.getBulkData() != null) message.setBulkData(binFrameHeader.getBulkData());
				} else message.setData(accumulator.toByteArray());
				
				_assemblerForMessageID.remove(header.getMessageID());
				
				try {
					handleProtocolMessageReceived(message);
				} catch (Exception excp) {
					DebugTool.logError(FailurePropagating_Msg + "onProtocolMessageReceived: " + excp.toString(), excp);
				} // end-catch
				
				hasFirstFrame = false;
				hasSecondFrame = false;
				accumulator = null;
			} // end-if
		} // end-method
		
		protected void handleMultiFrameMessageFrame(ProtocolFrameHeader header, byte[] data) {
			//if (!hasFirstFrame) {
			//	hasFirstFrame = true;
			if (header.getFrameType() == FrameType.First)
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
			if (header.getFrameType().equals(FrameType.Control)) {
				handleControlFrame(header, data);
			} else {
				// Must be a form of data frame (single, first, consecutive, etc.)
				if (   header.getFrameType() == FrameType.First
					|| header.getFrameType() == FrameType.Consecutive
					) {
					handleMultiFrameMessageFrame(header, data);
				} else {
					handleSingleFrameMessageFrame(header, data);
				}
			} // end-if
		} // end-method
		
        private void handleProtocolHeartbeat(ProtocolFrameHeader header,
                byte[] data) {
        		WiProProtocol.this.handleProtocolHeartbeat(header.getSessionType(),header.getSessionID());
        } // end-method		
		
        private void handleProtocolHeartbeatACK(ProtocolFrameHeader header,
                byte[] data) {
        		WiProProtocol.this.handleProtocolHeartbeatACK(header.getSessionType(),header.getSessionID());
        } // end-method		
		
		private void handleControlFrame(ProtocolFrameHeader header, byte[] data) {
			if (header.getFrameData() == FrameDataControlFrameType.Heartbeat.getValue()) {
                handleProtocolHeartbeat(header, data);
            } else if (header.getFrameData() == FrameDataControlFrameType.HeartbeatACK.getValue()) {
                handleProtocolHeartbeatACK(header, data);
            } else if (header.getFrameData() == FrameDataControlFrameType.StartSession.getValue()) {
				sendStartProtocolSessionACK(header.getSessionType(), header.getSessionID());
			} else if (header.getFrameData() == FrameDataControlFrameType.StartSessionACK.getValue()) {
				// Use this sessionID to create a message lock
				Object messageLock = _messageLocks.get(header.getSessionID());
				if (messageLock == null) {
					messageLock = new Object();
					_messageLocks.put(header.getSessionID(), messageLock);
				}
				//hashID = BitConverter.intFromByteArray(data, 0);
				if (_version > 1) hashID = header.getMessageID();
				handleProtocolSessionStarted(header.getSessionType(), header.getSessionID(), _version, "");				
			} else if (header.getFrameData() == FrameDataControlFrameType.StartSessionNACK.getValue()) {
				if (header.getSessionType().eq(SessionType.NAV) || header.getSessionType().eq(SessionType.PCM)) {
					handleProtocolSessionNACKed(header.getSessionType(), header.getSessionID(), _version, "");
				} else {
					handleProtocolError("Got StartSessionNACK for protocol sessionID=" + header.getSessionID(), null);
				}
			} else if (header.getFrameData() == FrameDataControlFrameType.EndSession.getValue()) {
				//if (hashID == BitConverter.intFromByteArray(data, 0)) 
				if (_version > 1) {
					if (hashID == header.getMessageID())
						handleProtocolSessionEnded(header.getSessionType(), header.getSessionID(), "");
				} else handleProtocolSessionEnded(header.getSessionType(), header.getSessionID(), "");
			} else if (header.getFrameData() == FrameDataControlFrameType.EndSessionACK.getValue()) {
				handleProtocolSessionEnded(header.getSessionType(), header.getSessionID(), "");
			} else if (header.getFrameData() == FrameDataControlFrameType.EndSessionNACK.getValue()) {
				handleProtocolSessionEndedNACK(header.getSessionType(), header.getSessionID(), "");
			} else if (header.getFrameData() == FrameDataControlFrameType.ServiceDataACK.getValue()) {
				handleProtocolServiceDataACK(header.getSessionType(), header.getSessionID());
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
			message.setSessionID(header.getSessionID());
			//If it is WiPro 2.0 it must have binary header
			if (_version > 1) {
				BinaryFrameHeader binFrameHeader = BinaryFrameHeader.
						parseBinaryHeader(data);
				message.setVersion(_version);
				message.setRPCType(binFrameHeader.getRPCType());
				message.setFunctionID(binFrameHeader.getFunctionID());
				message.setCorrID(binFrameHeader.getCorrID());
				if (binFrameHeader.getJsonSize() > 0) message.setData(binFrameHeader.getJsonData());
				if (binFrameHeader.getBulkData() != null) message.setBulkData(binFrameHeader.getBulkData());
			} else message.setData(data);
			
			_assemblerForMessageID.remove(header.getMessageID());
			
			try {
				handleProtocolMessageReceived(message);
			} catch (Exception ex) {
				DebugTool.logError(FailurePropagating_Msg + "onProtocolMessageReceived: " + ex.toString(), ex);
				handleProtocolError(FailurePropagating_Msg + "onProtocolMessageReceived: ", ex);
			} // end-catch
		} // end-method
	} // end-class

	@Override
	public void StartProtocolService(SessionType sessionType, byte sessionID) {
		ProtocolFrameHeader header = ProtocolFrameHeaderFactory.createStartSession(sessionType, 0x00, _version, sessionID);
		sendFrameToTransport(header);
		
	}

	@Override
	public void SetHeartbeatSendInterval(int heartbeatSendInterval_ms) {
		_heartbeatSendInterval_ms = heartbeatSendInterval_ms;
		
	}

	@Override
	public void SetHeartbeatReceiveInterval(int heartbeatReceiveInterval_ms) {
		_heartbeatReceiveInterval_ms = heartbeatReceiveInterval_ms;
		
	}

	@Override
	public void SendHeartBeat(byte sessionID) {
        final ProtocolFrameHeader heartbeat = ProtocolFrameHeaderFactory.createHeartbeat(SessionType.CONTROL, sessionID, _version);        
        sendFrameToTransport(heartbeat);		
	}

	@Override
	public void SendHeartBeatACK(byte sessionID) {
        final ProtocolFrameHeader heartbeat = ProtocolFrameHeaderFactory.createHeartbeatACK(SessionType.CONTROL, sessionID, _version);        
        sendFrameToTransport(heartbeat);		
	}
} // end-class