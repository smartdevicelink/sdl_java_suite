package com.smartdevicelink.protocol;

import java.io.ByteArrayOutputStream;
import java.util.Hashtable;

import com.smartdevicelink.SdlConnection.SdlConnection;
import com.smartdevicelink.SdlConnection.SdlSession;
import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.exception.SdlExceptionCause;
import com.smartdevicelink.protocol.enums.FrameDataControlFrameType;
import com.smartdevicelink.protocol.enums.FrameType;
import com.smartdevicelink.protocol.enums.MessageType;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.security.SdlSecurityBase;
import com.smartdevicelink.util.BitConverter;
import com.smartdevicelink.util.DebugTool;

public class WiProProtocol extends AbstractProtocol {
	byte _version = 1;
	private final static String FailurePropagating_Msg = "Failure propagating ";

	private static final int V1_V2_MTU_SIZE = 1500;
	private static final int V3_V4_MTU_SIZE = 131072;
	private static int HEADER_SIZE = 8;
	private static int MAX_DATA_SIZE = V1_V2_MTU_SIZE  - HEADER_SIZE;

	int hashID = 0;
	int messageID = 0;
	SdlConnection sdlconn = null;
	
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
		
		if (protocolListener instanceof SdlConnection)
		{
			sdlconn = (SdlConnection) protocolListener;
		}
	} // end-ctor
	
	public byte getVersion() {
		return this._version;
	}
	
	public void setVersion(byte version) {
        if (version > 4) {
            this._version = 4; //protect for future, proxy only supports v4 or lower
            HEADER_SIZE = 12;
            MAX_DATA_SIZE = V1_V2_MTU_SIZE - HEADER_SIZE; //default to lowest size since capabilities of this version are unknown
        } else if (version == 4) {
            this._version = version;
            HEADER_SIZE = 12;
            MAX_DATA_SIZE = V3_V4_MTU_SIZE; //versions 4 supports 128k MTU
        } else if (version == 3) {
            this._version = version;
            HEADER_SIZE = 12;
            MAX_DATA_SIZE = V3_V4_MTU_SIZE; //versions 3 supports 128k MTU
        } else if (version == 2) {
            this._version = version;
            HEADER_SIZE = 12;
            MAX_DATA_SIZE = V1_V2_MTU_SIZE - HEADER_SIZE;
        } else if (version == 1){
            this._version = version;
            HEADER_SIZE = 8;
            MAX_DATA_SIZE = V1_V2_MTU_SIZE - HEADER_SIZE;
        }
    }

	public void StartProtocolSession(SessionType sessionType) {
		SdlPacket header = SdlPacketFactory.createStartSession(sessionType, 0x00, _version, (byte) 0x00, false);
		handlePacketToSend(header);
	} // end-method

	private void sendStartProtocolSessionACK(SessionType sessionType, byte sessionID) {
		SdlPacket header = SdlPacketFactory.createStartSessionACK(sessionType, sessionID, 0x00, _version);
		handlePacketToSend(header);
	} // end-method
	
	public void EndProtocolSession(SessionType sessionType, byte sessionID, int hashId) {
		SdlPacket header = SdlPacketFactory.createEndSession(sessionType, sessionID, hashID, _version, BitConverter.intToByteArray(hashId));
		handlePacketToSend(header);

	} // end-method

	public void SendMessage(ProtocolMessage protocolMsg) {	
		protocolMsg.setRPCType((byte) 0x00); //always sending a request
		SessionType sessionType = protocolMsg.getSessionType();
		byte sessionID = protocolMsg.getSessionID();
		
		byte[] data = null;
		if (_version > 1 && sessionType != SessionType.NAV && sessionType != SessionType.PCM) {
            if (sessionType.eq(SessionType.CONTROL)) {
                final byte[] secureData = protocolMsg.getData().clone();
                data = new byte[HEADER_SIZE + secureData.length];
                
                final BinaryFrameHeader binFrameHeader =
                		SdlPacketFactory.createBinaryFrameHeader(protocolMsg.getRPCType(),protocolMsg.getFunctionID(), protocolMsg.getCorrID(), 0);
                System.arraycopy(binFrameHeader.assembleHeaderBytes(), 0, data, 0, HEADER_SIZE);
                System.arraycopy(secureData, 0, data,HEADER_SIZE, secureData.length);
            }
            else if (protocolMsg.getBulkData() != null) {
				data = new byte[12 + protocolMsg.getJsonSize() + protocolMsg.getBulkData().length];
				sessionType = SessionType.BULK_DATA;
			} else { 
				data = new byte[12 + protocolMsg.getJsonSize()];
			}
            if (!sessionType.eq(SessionType.CONTROL)) {
            	BinaryFrameHeader binFrameHeader = SdlPacketFactory.createBinaryFrameHeader(protocolMsg.getRPCType(), protocolMsg.getFunctionID(), protocolMsg.getCorrID(), protocolMsg.getJsonSize());
            	System.arraycopy(binFrameHeader.assembleHeaderBytes(), 0, data, 0, 12);
            	System.arraycopy(protocolMsg.getData(), 0, data, 12, protocolMsg.getJsonSize());
            	if (protocolMsg.getBulkData() != null) {
            		System.arraycopy(protocolMsg.getBulkData(), 0, data, 12 + protocolMsg.getJsonSize(), protocolMsg.getBulkData().length);
            	}
            }
		} else {
			data = protocolMsg.getData();
		}
		
		if (sdlconn != null && protocolMsg.getPayloadProtected())
		{			
			if (data != null && data.length > 0) {
				SdlSession session = sdlconn.findSessionById(sessionID);
				
				if (session == null)
					return;
				
				byte[] dataToRead = new byte[4096];
				SdlSecurityBase sdlSec = session.getSdlSecurity();
				if (sdlSec == null) 
					return;
				 
				Integer iNumBytes = sdlSec.encryptData(data, dataToRead);
				if ((iNumBytes == null) || (iNumBytes <= 0))
					return;
				
		        byte[] encryptedData = new byte[iNumBytes];
		        System.arraycopy(dataToRead, 0, encryptedData, 0, iNumBytes);
				data = encryptedData;
			}
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

				SdlPacket firstHeader = SdlPacketFactory.createMultiSendDataFirst(sessionType, sessionID, messageID, _version,firstFrameData,protocolMsg.getPayloadProtected());
				firstHeader.setPriorityCoefficient(1+protocolMsg.priorityCoefficient);
				//Send the first frame
				handlePacketToSend(firstHeader);
				
				int currentOffset = 0;
				byte frameSequenceNumber = 0;

				for (int i = 0; i < frameCount; i++) {
					if (i < (frameCount - 1)) {
	                     ++frameSequenceNumber;
	                        if (frameSequenceNumber ==
	                                SdlPacket.FRAME_INFO_FINAL_CONNESCUTIVE_FRAME) {
	                            // we can't use 0x00 as frameSequenceNumber, because
	                            // it's reserved for the last frame
	                            ++frameSequenceNumber;
	                        }
					} else {
						frameSequenceNumber = SdlPacket.FRAME_INFO_FINAL_CONNESCUTIVE_FRAME;
					} // end-if
					
					int bytesToWrite = data.length - currentOffset;
					if (bytesToWrite > MAX_DATA_SIZE) { 
						bytesToWrite = MAX_DATA_SIZE; 
					}
					SdlPacket consecHeader = SdlPacketFactory.createMultiSendDataRest(sessionType, sessionID, bytesToWrite, frameSequenceNumber , messageID, _version,data, currentOffset, bytesToWrite, protocolMsg.getPayloadProtected());
					consecHeader.setPriorityCoefficient(i+2+protocolMsg.priorityCoefficient);
					handlePacketToSend(consecHeader);
					currentOffset += bytesToWrite;
				}
			} else {
				messageID++;
				SdlPacket header = SdlPacketFactory.createSingleSendData(sessionType, sessionID, data.length, messageID, _version,data, protocolMsg.getPayloadProtected());
				header.setPriorityCoefficient(protocolMsg.priorityCoefficient);
				handlePacketToSend(header);
			}
		}
	}

	public void handlePacketReceived(SdlPacket packet){
		//Check for a version difference
		if (_version == 1) {
			setVersion((byte)packet.version);	
		}
		
		MessageFrameAssembler assembler = getFrameAssemblerForFrame(packet);
		assembler.handleFrame(packet);

		onResetIncomingHeartbeat(SessionType.valueOf((byte)packet.getServiceType()), (byte)packet.getSessionId());

	}

	
	
	protected MessageFrameAssembler getFrameAssemblerForFrame(SdlPacket packet) {
		Integer iSessionId = Integer.valueOf(packet.getSessionId());
		Byte bySessionId = iSessionId.byteValue();
	
		Hashtable<Integer, MessageFrameAssembler> hashSessionID = _assemblerForSessionID.get(bySessionId);
		if (hashSessionID == null) {
			hashSessionID = new Hashtable<Integer, MessageFrameAssembler>();
			_assemblerForSessionID.put(bySessionId, hashSessionID);
		} // end-if
		
		MessageFrameAssembler ret = (MessageFrameAssembler) _assemblerForMessageID.get(Integer.valueOf(packet.getMessageId()));
		if (ret == null) {
			ret = new MessageFrameAssembler();
			_assemblerForMessageID.put(Integer.valueOf(packet.getMessageId()), ret);
		} // end-if
		
		return ret;
	} // end-method

	protected class MessageFrameAssembler {
		protected boolean hasFirstFrame = false;
		protected ByteArrayOutputStream accumulator = null;
		protected int totalSize = 0;
		protected int framesRemaining = 0;

		protected void handleFirstDataFrame(SdlPacket packet) {
			//The message is new, so let's figure out how big it is.
			hasFirstFrame = true;
			totalSize = BitConverter.intFromByteArray(packet.payload, 0) - HEADER_SIZE;
			framesRemaining = BitConverter.intFromByteArray(packet.payload, 4);
			accumulator = new ByteArrayOutputStream(totalSize);
		}
		
		protected void handleRemainingFrame(SdlPacket packet) {
			accumulator.write(packet.payload, 0, (int)packet.getDataSize());
			notifyIfFinished(packet);
		}
		
		protected void notifyIfFinished(SdlPacket packet) {
			//if (framesRemaining == 0) {
			if (packet.getFrameType() == FrameType.Consecutive && packet.getFrameInfo() == 0x0) 
			{
				ProtocolMessage message = new ProtocolMessage();
				message.setPayloadProtected(packet.isEncrypted());
				message.setSessionType(SessionType.valueOf((byte)packet.getServiceType()));
				message.setSessionID((byte)packet.getSessionId());
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
				} else{
					message.setData(accumulator.toByteArray());
				}
				
				_assemblerForMessageID.remove(packet.getMessageId());
				
				try {
					handleProtocolMessageReceived(message);
				} catch (Exception excp) {
					DebugTool.logError(FailurePropagating_Msg + "onProtocolMessageReceived: " + excp.toString(), excp);
				} // end-catch
				
				hasFirstFrame = false;
				accumulator = null;
			} // end-if
		} // end-method
		
		protected void handleMultiFrameMessageFrame(SdlPacket packet) {
			if (packet.getFrameType() == FrameType.First){
				handleFirstDataFrame(packet);
			}
			else{
				handleRemainingFrame(packet);
			}
				
		} // end-method
		
		protected void handleFrame(SdlPacket packet) {
	
			if (packet.getPayload() != null && packet.getDataSize() > 0 && packet.isEncrypted()  )
			{
				if (sdlconn != null)
				{
					SdlSession session = sdlconn.findSessionById((byte)packet.getSessionId());
		
					if (session == null)
						return;
		
					SdlSecurityBase sdlSec = session.getSdlSecurity();
					byte[] dataToRead = new byte[4096];	

					Integer iNumBytes = sdlSec.decryptData(packet.getPayload(), dataToRead);
					 if ((iNumBytes == null) || (iNumBytes <= 0))
						 return;
										 
					byte[] decryptedData = new byte[iNumBytes];
					System.arraycopy(dataToRead, 0, decryptedData, 0, iNumBytes);
					packet.payload = decryptedData;
				}
			}
		
			if (packet.getFrameType().equals(FrameType.Control)) {
				handleControlFrame(packet);
			} else {
				// Must be a form of data frame (single, first, consecutive, etc.)
				if (   packet.getFrameType() == FrameType.First
					|| packet.getFrameType() == FrameType.Consecutive
					) {
					handleMultiFrameMessageFrame(packet);
				} else {
					handleSingleFrameMessageFrame(packet);
				}
			} // end-if
		} // end-method
		
        private void handleProtocolHeartbeatACK(SdlPacket packet) {
        		WiProProtocol.this.handleProtocolHeartbeatACK(SessionType.valueOf((byte)packet.getServiceType()),(byte)packet.getSessionId());
        } // end-method		
        private void handleProtocolHeartbeat(SdlPacket packet) {
        	WiProProtocol.this.handleProtocolHeartbeat(SessionType.valueOf((byte)packet.getServiceType()),(byte)packet.getSessionId());
        } // end-method		
	
		private void handleControlFrame(SdlPacket packet) {
			Integer frameTemp = Integer.valueOf(packet.getFrameInfo());
			Byte frameInfo = frameTemp.byteValue();
			
            SessionType serviceType = SessionType.valueOf((byte)packet.getServiceType());

            if (frameInfo == FrameDataControlFrameType.Heartbeat.getValue()) {
                handleProtocolHeartbeat(packet);
            }
			if (frameInfo == FrameDataControlFrameType.HeartbeatACK.getValue()) {
                handleProtocolHeartbeatACK(packet);
            }
            else if (frameInfo == FrameDataControlFrameType.StartSession.getValue()) {
				sendStartProtocolSessionACK(serviceType, (byte)packet.getSessionId());
			} else if (frameInfo == FrameDataControlFrameType.StartSessionACK.getValue()) {
				// Use this sessionID to create a message lock
				Object messageLock = _messageLocks.get(packet.getSessionId());
				if (messageLock == null) {
					messageLock = new Object();
					_messageLocks.put((byte)packet.getSessionId(), messageLock);
				}
				int hashID = 0;
				if (_version > 1){
					if (packet.payload!= null && packet.dataSize == 4){ //hashid will be 4 bytes in length
						hashID = BitConverter.intFromByteArray(packet.payload, 0);
					}
				}	
				handleProtocolSessionStarted(serviceType,(byte) packet.getSessionId(), _version, "", hashID, packet.isEncrypted());				
			} else if (frameInfo == FrameDataControlFrameType.StartSessionNACK.getValue()) {
				if (serviceType.eq(SessionType.NAV) || serviceType.eq(SessionType.PCM)) {
					handleProtocolSessionNACKed(serviceType, (byte)packet.getSessionId(), _version, "");
				} else {
					handleProtocolError("Got StartSessionNACK for protocol sessionID=" + packet.getSessionId(), null);
				}
			} else if (frameInfo == FrameDataControlFrameType.EndSession.getValue()) {
				if (_version > 1) {
					handleProtocolSessionEnded(serviceType, (byte)packet.getSessionId(), "");
				} else {
					handleProtocolSessionEnded(serviceType, (byte)packet.getSessionId(), "");
				}
			} else if (frameInfo == FrameDataControlFrameType.EndSessionACK.getValue()) {
				handleProtocolSessionEnded(serviceType, (byte)packet.getSessionId(), "");
			} else if (frameInfo == FrameDataControlFrameType.EndSessionNACK.getValue()) {
				handleProtocolSessionEndedNACK(serviceType, (byte)packet.getSessionId(), "");
			} else if (frameInfo == FrameDataControlFrameType.ServiceDataACK.getValue()) {
				if (packet.getPayload() != null && packet.getDataSize() == 4) //service data ack will be 4 bytes in length
				{
					int serviceDataAckSize = BitConverter.intFromByteArray(packet.getPayload(), 0);
					handleProtocolServiceDataACK(serviceType, serviceDataAckSize,(byte)packet.getSessionId ());
				}
			}
            _assemblerForMessageID.remove(packet.getMessageId());
		} // end-method
				
		private void handleSingleFrameMessageFrame(SdlPacket packet) {
			ProtocolMessage message = new ProtocolMessage();
			message.setPayloadProtected(packet.isEncrypted());
            SessionType serviceType = SessionType.valueOf((byte)packet.getServiceType());
			if (serviceType == SessionType.RPC) {
				message.setMessageType(MessageType.RPC);
			} else if (serviceType == SessionType.BULK_DATA) {
				message.setMessageType(MessageType.BULK);
			} // end-if
			message.setSessionType(serviceType);
			message.setSessionID((byte)packet.getSessionId());
			//If it is WiPro 2.0 it must have binary header
			boolean isControlService = message.getSessionType().equals(SessionType.CONTROL);
			if (_version > 1&& !isControlService) {
				BinaryFrameHeader binFrameHeader = BinaryFrameHeader.
						parseBinaryHeader(packet.payload);
				message.setVersion(_version);
				message.setRPCType(binFrameHeader.getRPCType());
				message.setFunctionID(binFrameHeader.getFunctionID());
				message.setCorrID(binFrameHeader.getCorrID());
				if (binFrameHeader.getJsonSize() > 0){
					message.setData(binFrameHeader.getJsonData());
				}
				if (binFrameHeader.getBulkData() != null){
					message.setBulkData(binFrameHeader.getBulkData());
				}
			} else {
				message.setData(packet.payload);
			}
			
			_assemblerForMessageID.remove(packet.getMessageId());
			
			try {
				handleProtocolMessageReceived(message);
			} catch (Exception ex) {
				DebugTool.logError(FailurePropagating_Msg + "onProtocolMessageReceived: " + ex.toString(), ex);
				handleProtocolError(FailurePropagating_Msg + "onProtocolMessageReceived: ", ex);
			} // end-catch
		} // end-method
	} // end-class

	@Override
	public void StartProtocolService(SessionType sessionType, byte sessionID, boolean isEncrypted) {
		SdlPacket header = SdlPacketFactory.createStartSession(sessionType, 0x00, _version, sessionID, isEncrypted);
		handlePacketToSend(header);
		
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
        final SdlPacket heartbeat = SdlPacketFactory.createHeartbeat(SessionType.CONTROL, sessionID, _version);        
        handlePacketToSend(heartbeat);		
	}

	@Override
	public void SendHeartBeatACK(byte sessionID) {
        final SdlPacket heartbeat = SdlPacketFactory.createHeartbeatACK(SessionType.CONTROL, sessionID, _version);        
        handlePacketToSend(heartbeat);		
	}

	@Override
	public void EndProtocolService(SessionType serviceType, byte sessionID) {
 		SdlPacket header = SdlPacketFactory.createEndSession(serviceType, sessionID, hashID, _version, new byte[4]);
		handlePacketToSend(header);
		
	}

} // end-class
