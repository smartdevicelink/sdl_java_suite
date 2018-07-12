package com.smartdevicelink.protocol;

import android.util.Log;

import com.smartdevicelink.SdlConnection.SdlConnection;
import com.smartdevicelink.SdlConnection.SdlSession;
import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.exception.SdlExceptionCause;
import com.smartdevicelink.protocol.enums.ControlFrameTags;
import com.smartdevicelink.protocol.enums.FrameDataControlFrameType;
import com.smartdevicelink.protocol.enums.FrameType;
import com.smartdevicelink.protocol.enums.MessageType;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.rpc.ImageResolution;
import com.smartdevicelink.proxy.rpc.VideoStreamingFormat;
import com.smartdevicelink.proxy.rpc.enums.VideoStreamingCodec;
import com.smartdevicelink.proxy.rpc.enums.VideoStreamingProtocol;
import com.smartdevicelink.security.SdlSecurityBase;
import com.smartdevicelink.streaming.video.VideoStreamingParameters;
import com.smartdevicelink.transport.TransportConstants;
import com.smartdevicelink.transport.enums.TransportType;
import com.smartdevicelink.util.BitConverter;
import com.smartdevicelink.util.DebugTool;
import com.smartdevicelink.util.Version;

import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class WiProProtocol extends AbstractProtocol {
	private static final String TAG ="SdlProtocol";
	private final static String FailurePropagating_Msg = "Failure propagating ";
	//If increasing MAX PROTOCOL VERSION major version, make sure to alter it in SdlPsm
	public static final Version MAX_PROTOCOL_VERSION = new Version("5.1.0");
	private Version protocolVersion = new Version("1.0.0");
	byte _version = 1;

	public static final int V1_V2_MTU_SIZE = 1500;
	public static final int V3_V4_MTU_SIZE = 131072;
	public static final int V1_HEADER_SIZE = 8;
	public static final int V2_HEADER_SIZE = 12;
	private static int HEADER_SIZE = 8;
	private static int TLS_MAX_RECORD_SIZE = 16384;
	private static final int PRIMARY_TRANSPORT_ID    = 1;
	private static final int SECONDARY_TRANSPORT_ID  = 2;

	int hashID = 0;
	int messageID = 0;

	SdlConnection sdlconn = null;
	WeakReference<SdlSession> sessionWeakReference;

    @SuppressWarnings("unused")
    private int _heartbeatSendInterval_ms = 0;
    @SuppressWarnings("unused")
    private int _heartbeatReceiveInterval_ms = 0;
	
	Hashtable<Integer, MessageFrameAssembler> _assemblerForMessageID = new Hashtable<Integer, MessageFrameAssembler>();
	Hashtable<Byte, Hashtable<Integer, MessageFrameAssembler>> _assemblerForSessionID = new Hashtable<Byte, Hashtable<Integer, MessageFrameAssembler>>();
	Hashtable<Byte, Object> _messageLocks = new Hashtable<Byte, Object>();
	private HashMap<SessionType, Long> mtus = new HashMap<SessionType,Long>();
	private HashMap<SessionType, TransportType> activeTransports = new HashMap<SessionType,TransportType>();
	private List<TransportType> availableTransports = new ArrayList<>();
	private Queue<TransportType> transportsToRegisterQueue = new LinkedList<>();

	public static final List<SessionType> HIGH_BANDWIDTH_SERVICES
			= Arrays.asList(SessionType.NAV, SessionType.PCM);
	List<TransportType> requestedPrimaryTransports;
	List<TransportType> supportedSecondaryTransports;
	Map<SessionType, List<Integer>> supportedServicesMap;
	boolean requiresHighBandwidth = false;
	Map<String, Object> secondaryTransportParams;
	TransportType connectedPrimaryTransport;
	Map<TransportType, List<ISecondaryTransportListener>> secondaryTransportListeners = new HashMap<>();

	// Hide no-arg ctor
	private WiProProtocol() {
		super(null);
	} // end-ctor

	public WiProProtocol(IProtocolListener protocolListener) {
		super(protocolListener);


		if (protocolListener instanceof SdlSession){
			sessionWeakReference = new WeakReference<>((SdlSession)protocolListener);
		}else if (protocolListener instanceof SdlConnection){
			sdlconn = (SdlConnection) protocolListener;
		}

		mtus.put(SessionType.RPC, new Long(V1_V2_MTU_SIZE  - HEADER_SIZE));
	} // end-ctor
	
	/**
	 * Retrieves the max payload size for a packet to be sent to the module
	 * @return the max transfer unit 
	 */
	public int getMtu(){
		return mtus.get(SessionType.RPC).intValue();
	}

	public long getMtu(SessionType type){
		Long mtu = mtus.get(type);
		if(mtu == null){
			mtu = mtus.get(SessionType.RPC);
		}
		return mtu;
	}

	public TransportType getTransportForSession(SessionType type){
		return activeTransports.get(type);
	}

	/**
	 * For logging purposes, prints active services on each connected transport
	 */
	public void printActiveTransports(){
		StringBuilder activeTransportString = new StringBuilder();
		for(Map.Entry entry : activeTransports.entrySet()){
			String sessionString = null;
			if(entry.getKey().equals(SessionType.NAV)) {
				sessionString = "NAV";
			}else if(entry.getKey().equals(SessionType.PCM)) {
				sessionString = "PCM";
			}else if(entry.getKey().equals(SessionType.RPC)) {
				sessionString = "RPC";
			}
			if(sessionString != null){
				activeTransportString.append("Session: " + sessionString
						+ " Transport: " + entry.getValue().toString() + "\n");
			}
		}
		Log.d(TAG, "Active transports --- \n" + activeTransportString.toString());
	}

	public void setRequiresHighBandwidth(boolean requiresHighBandwidth){
		this.requiresHighBandwidth = requiresHighBandwidth;
	}

	public void setPrimaryTransports(List<TransportType> primaryTransports){
		this.requestedPrimaryTransports = primaryTransports;
	}

	public void setSupportedSecondaryTransports(List<TransportType> secondaryTransports){
		this.supportedSecondaryTransports = secondaryTransports;
	}

	public void setSupportedServices(SessionType serviceType, List<Integer> order){
		if(supportedServicesMap == null){
			supportedServicesMap = new HashMap<>();
		}
		this.supportedServicesMap.put(serviceType, order);
		for(SessionType service : HIGH_BANDWIDTH_SERVICES){
			if (supportedServicesMap.get(service) != null
					&& supportedServicesMap.get(service).contains(PRIMARY_TRANSPORT_ID)) {
				if(connectedPrimaryTransport != null) {
					activeTransports.put(service, connectedPrimaryTransport);
				}
			}
		}
	}

	public void setRegisteredForTransport(boolean registered){
		TransportType transportType = transportsToRegisterQueue.poll();
		List<ISecondaryTransportListener> listenerList = secondaryTransportListeners.get(transportType);
		if(listenerList != null){
			for(ISecondaryTransportListener listener : listenerList){
				if(registered) {
					listener.onConnectionSuccess();
				}else{
					listener.onConnectionFailure();
				}
			}
			secondaryTransportListeners.remove(transportType);
		}
		if(registered) {
			Log.d(TAG, transportType.toString() + " transport was registered!");
			if (supportedSecondaryTransports.contains(transportType)) {
				for(SessionType secondaryService : HIGH_BANDWIDTH_SERVICES){
					if (supportedServicesMap.containsKey(secondaryService)) {
						for(int transportNum : supportedServicesMap.get(secondaryService)){
							if(transportNum == PRIMARY_TRANSPORT_ID){
								break; // Primary is favored, break out...
							}else if(transportNum == SECONDARY_TRANSPORT_ID){
								activeTransports.put(secondaryService, transportType);
								break;
							}
						}
					}
				}
			}
		}else{
			Log.d(TAG, transportType.toString() + " transport was NOT registered!");
		}
		printActiveTransports();
	}

	public void onTransportsConnectedUpdate(TransportType[] transportTypes){
		//TODO error checking for no longer connected transports
		Log.d(TAG, "onTransportsConnectedUpdate: ");
		for(TransportType t : transportTypes) {
			Log.d(TAG, t.toString());
		}
		List<TransportType> connectedTransports = Arrays.asList(transportTypes);
		if(!connectedTransports.contains(connectedPrimaryTransport)){
			connectedPrimaryTransport = null;
			return;
		}
		if(activeTransports.get(SessionType.RPC) == null){
			//need to start a service
			Log.d(TAG, "Sending start service RPC");
			startService(SessionType.RPC,(byte)0x00,false);
			return;
		}else if(requiresHighBandwidth){
			for(TransportType transportType : transportTypes){
				if(supportedSecondaryTransports.contains(transportType)){
					SdlSession session = null;
					if(sessionWeakReference != null){
						session = sessionWeakReference.get();
					}
					if(session != null) {
						Log.d(TAG, "Registering secondary transport!");
						registerSecondaryTransport(session.getSessionId(), transportType);
					}else{
						Log.d(TAG, "Session was null");
					}
					return; // For now, only support registering one secondary transport
				}else{
					Log.d(TAG, transportType.toString() + " not supported as secondary transport");
				}
			}
		}
	}

	/**
	 * Returns the preferred primary transport for an array of connected transport types
	 * @param connectedTransports array of connected transports
	 * @return The first connected TransportType requested by app
	 */
	public void setConnectedPrimaryTransport(TransportType[] connectedTransports){
		connectedPrimaryTransport = null;
		List<TransportType> transportList = Arrays.asList(connectedTransports);
		for(TransportType transportType: requestedPrimaryTransports) {
			if (transportList.contains(transportType)) {
				connectedPrimaryTransport = transportType;
				break;
			}
		}
	}

	public TransportType getConnectedPrimaryTransport(){
		return connectedPrimaryTransport;
	}

	private void onTransportNotAccepted(String info){
		if(sessionWeakReference != null) {
			SdlSession session = sessionWeakReference.get();
			if (session != null) {
				session.shutdown(info);
			}
		}
	}

	/**
	 * Use getProtocolVersion() or getMajorVersionByte instead.<br>
	 * Returns the Major version of the currently used protocol version
	 */
	@Deprecated
	public byte getVersion() {
		return getMajorVersionByte();
	}

	public Version getProtocolVersion(){
		return this.protocolVersion;
	}
	public byte getMajorVersionByte(){
		if(_version == 1){
			_version = new Integer(this.protocolVersion.getMajor()).byteValue();
		}
		return _version;

	}

	/**
	 * This method will set the major protocol version that we should use. It will also set the default MTU based on version.
	 * @param version
	 */
	public void setVersion(byte version) {
        if (version > 5) {
            this.protocolVersion = new Version("5.0.0"); //protect for future, proxy only supports v5 or lower
            HEADER_SIZE = 12;
            mtus.put(SessionType.RPC,new Long(V3_V4_MTU_SIZE) );
        } else if (version == 5) {
            this.protocolVersion = new Version("5.0.0");
            HEADER_SIZE = 12;
            mtus.put(SessionType.RPC,new Long(V3_V4_MTU_SIZE) );
        }else if (version == 4) {
            this.protocolVersion = new Version("4.0.0");
            HEADER_SIZE = 12;
            mtus.put(SessionType.RPC,new Long(V3_V4_MTU_SIZE) ); //versions 4 supports 128k MTU
        } else if (version == 3) {
            this.protocolVersion = new Version("3.0.0");
            HEADER_SIZE = 12;
            mtus.put(SessionType.RPC,new Long(V3_V4_MTU_SIZE) ); //versions 3 supports 128k MTU
        } else if (version == 2) {
            this.protocolVersion = new Version("2.0.0");
            HEADER_SIZE = 12;
            mtus.put(SessionType.RPC,new Long(V1_V2_MTU_SIZE - HEADER_SIZE) );
        } else if (version == 1){
            this.protocolVersion = new Version("1.0.0");
            HEADER_SIZE = 8;
            mtus.put(SessionType.RPC,new Long(V1_V2_MTU_SIZE - HEADER_SIZE) );
        }
    }

    @Deprecated
	public void StartProtocolSession(SessionType sessionType) {
		SdlPacket header = SdlPacketFactory.createStartSession(sessionType, 0x00, getMajorVersionByte(), (byte) 0x00, false);
		if(sessionType.equals(SessionType.RPC)){ // check for RPC session
			header.putTag(ControlFrameTags.RPC.StartService.PROTOCOL_VERSION, MAX_PROTOCOL_VERSION.toString());
		}
		handlePacketToSend(header);
	} // end-method

	private void sendStartProtocolSessionACK(SessionType sessionType, byte sessionID) {
		SdlPacket header = SdlPacketFactory.createStartSessionACK(sessionType, sessionID, 0x00, getMajorVersionByte());
		handlePacketToSend(header);
	} // end-method
	
	public void EndProtocolSession(SessionType sessionType, byte sessionID, int hashId) {
		SdlPacket header;
			if (sessionType.equals(SessionType.RPC)) { // check for RPC session
				if(_version < 5){
					header = SdlPacketFactory.createEndSession(sessionType, sessionID, hashID, getMajorVersionByte(), BitConverter.intToByteArray(hashID));
				}else{
					header = SdlPacketFactory.createEndSession(sessionType, sessionID, hashID, getMajorVersionByte(), new byte[0]);
					header.putTag(ControlFrameTags.RPC.EndService.HASH_ID, hashID);
				}
			}else{ //Any other service type we don't include the hash id
				header = SdlPacketFactory.createEndSession(sessionType, sessionID, hashID, getMajorVersionByte(), new byte[0]);
			}
		handlePacketToSend(header);

	} // end-method

	public void SendMessage(ProtocolMessage protocolMsg) {	
		protocolMsg.setRPCType((byte) 0x00); //always sending a request
		SessionType sessionType = protocolMsg.getSessionType();
		byte sessionID = protocolMsg.getSessionID();
		
		byte[] data = null;
		if (protocolVersion.getMajor() > 1 && sessionType != SessionType.NAV && sessionType != SessionType.PCM) {
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
		
		if ((sessionWeakReference != null || sdlconn != null) && protocolMsg.getPayloadProtected()){
			SdlSession session = null;
			if(sessionWeakReference != null){
				session = sessionWeakReference.get();
			}else if (sdlconn != null){
				 session = sdlconn.findSessionById(sessionID);
			}

			if(session == null){
				return;
			}
			if (data != null && data.length > 0) {
				byte[] dataToRead = new byte[TLS_MAX_RECORD_SIZE];
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
			if (data.length > getMtu(sessionType)) {

				messageID++;
	
				// Assemble first frame.
				Long mtu = getMtu(sessionType);
				int frameCount = new Long(data.length / mtu).intValue();
				if (data.length % mtu > 0) {
					frameCount++;
				}
				//byte[] firstFrameData = new byte[HEADER_SIZE];
				byte[] firstFrameData = new byte[8];
				// First four bytes are data size.
				System.arraycopy(BitConverter.intToByteArray(data.length), 0, firstFrameData, 0, 4);
				// Second four bytes are frame count.
				System.arraycopy(BitConverter.intToByteArray(frameCount), 0, firstFrameData, 4, 4);

				SdlPacket firstHeader = SdlPacketFactory.createMultiSendDataFirst(sessionType, sessionID, messageID, getMajorVersionByte(),firstFrameData,protocolMsg.getPayloadProtected());
				firstHeader.setPriorityCoefficient(1+protocolMsg.priorityCoefficient);
				firstHeader.setTransportType(activeTransports.get(sessionType));
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
					if (bytesToWrite > mtu) {
						bytesToWrite = mtu.intValue();
					}
					SdlPacket consecHeader = SdlPacketFactory.createMultiSendDataRest(sessionType, sessionID, bytesToWrite, frameSequenceNumber , messageID, getMajorVersionByte(),data, currentOffset, bytesToWrite, protocolMsg.getPayloadProtected());
					consecHeader.setTransportType(activeTransports.get(sessionType));
					consecHeader.setPriorityCoefficient(i+2+protocolMsg.priorityCoefficient);
					handlePacketToSend(consecHeader);
					currentOffset += bytesToWrite;
				}
			} else {
				messageID++;
				SdlPacket header = SdlPacketFactory.createSingleSendData(sessionType, sessionID, data.length, messageID, getMajorVersionByte(),data, protocolMsg.getPayloadProtected());
				header.setPriorityCoefficient(protocolMsg.priorityCoefficient);
				header.setTransportType(activeTransports.get(sessionType));
				handlePacketToSend(header);
			}
		}
	}

	public void handlePacketReceived(SdlPacket packet){
		//Check for a version difference
		if (getMajorVersionByte() == 1) {
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
			try {
				accumulator = new ByteArrayOutputStream(totalSize);
			}catch(OutOfMemoryError e){
				DebugTool.logError("OutOfMemory error", e); //Garbled bits were received
				accumulator = null;
			}
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
				if (protocolVersion.getMajor() > 1) {
					BinaryFrameHeader binFrameHeader = BinaryFrameHeader.
							parseBinaryHeader(accumulator.toByteArray());
					if(binFrameHeader == null) {
						return;
					}
					message.setVersion(getMajorVersionByte());
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
				if(accumulator != null)
					handleRemainingFrame(packet);
			}
				
		} // end-method
		
		protected void handleFrame(SdlPacket packet) {
	
			if (packet.getPayload() != null && packet.getDataSize() > 0 && packet.isEncrypted()  )
			{
				SdlSession session = null;
				if(sessionWeakReference != null){
					session = sessionWeakReference.get();
				}else if(sdlconn != null){
					session = sdlconn.findSessionById((byte)packet.getSessionId());
				}

				if(session == null){
					return;
				}

				SdlSecurityBase sdlSec = session.getSdlSecurity();
				byte[] dataToRead = new byte[4096];

				Integer iNumBytes = sdlSec.decryptData(packet.getPayload(), dataToRead);
				if ((iNumBytes == null) || (iNumBytes <= 0))
					return;
										 
				byte[] decryptedData = new byte[iNumBytes];
				System.arraycopy(dataToRead, 0, decryptedData, 0, iNumBytes);
				packet.payload = decryptedData;
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
				if(packet.version >= 5){
					String mtuTag = null;
					if(serviceType.equals(SessionType.RPC)){
						mtuTag = ControlFrameTags.RPC.StartServiceACK.MTU;
					}else if(serviceType.equals(SessionType.PCM)){
						mtuTag = ControlFrameTags.Audio.StartServiceACK.MTU;
					}else if(serviceType.equals(SessionType.NAV)){
						mtuTag = ControlFrameTags.Video.StartServiceACK.MTU;
					}
					Object mtu = packet.getTag(mtuTag);
					if(mtu!=null){
						mtus.put(serviceType,(Long) packet.getTag(mtuTag));
					}
					if(serviceType.equals(SessionType.RPC)){
						hashID = (Integer) packet.getTag(ControlFrameTags.RPC.StartServiceACK.HASH_ID);
						Object version = packet.getTag(ControlFrameTags.RPC.StartServiceACK.PROTOCOL_VERSION);

						//Check to make sure this is a transport we are willing to accept
						TransportType transportType = packet.getTransportType();
						if(transportType == null || !requestedPrimaryTransports.contains(transportType)){
							onTransportNotAccepted("Transport is not in requested primary transports");
							return;
						}

						StringBuilder secondaryDetailsBldr = new StringBuilder();
						secondaryDetailsBldr.append("RPC StartSessionACK: Checking secondary transport details \n");
						ArrayList<String> secondary = (ArrayList<String>) packet.getTag(ControlFrameTags.RPC.StartServiceACK.SECONDARY_TRANSPORTS);
						ArrayList<Integer> audio = (ArrayList<Integer>) packet.getTag(ControlFrameTags.RPC.StartServiceACK.AUDIO_SERVICE_TRANSPORTS);
						ArrayList<Integer> video = (ArrayList<Integer>) packet.getTag(ControlFrameTags.RPC.StartServiceACK.VIDEO_SERVICE_TRANSPORTS);

						if(secondary != null){
							secondaryDetailsBldr.append("Supported secondary transports: ");
							for(String s : secondary){
								secondaryDetailsBldr.append(" ").append(s);
							}
							secondaryDetailsBldr.append("\n");
						}else{
							Log.d(TAG, "Supported secondary transports list is empty!");
						}
						if(audio != null){
							secondaryDetailsBldr.append("Supported audio transports: ");
							for(int a : audio){
								secondaryDetailsBldr.append(" ").append(a);
							}
							secondaryDetailsBldr.append("\n");
						}
						if(video != null){
							secondaryDetailsBldr.append("Supported video transports: ");
							for(int v : video){
								secondaryDetailsBldr.append(" ").append(v);
							}
							secondaryDetailsBldr.append("\n");
						}

						Log.d(TAG, secondaryDetailsBldr.toString());

						List<TransportType> supportedTransports = new ArrayList<>();

						for (String s : secondary) {
							Log.d(TAG, "Secondary transports allowed by core: " + s);
							if(s.equals(TransportConstants.TCP_WIFI)){
								supportedTransports.add(TransportType.TCP);
							}else if(s.equals(TransportConstants.AOA_USB)){
								supportedTransports.add(TransportType.USB);
							}else if(s.equals(TransportConstants.SPP_BLUETOOTH)){
								supportedTransports.add(TransportType.BLUETOOTH);
							}
						}
						setSupportedSecondaryTransports(supportedTransports);
						setSupportedServices(SessionType.PCM, audio);
						setSupportedServices(SessionType.NAV, video);

						if(version!=null){
							//At this point we have confirmed the negotiated version between the module and the proxy
							protocolVersion = new Version((String)version);

							//Check if we support multiple transports by protocol version
							if(protocolVersion != null && protocolVersion.isNewerThan(new Version("5.1.0")) <=-1) {
								//Version is too old to just move on
								/*TODO: Modify this logic below
								if(requiresHighBandwidth
										&& TransportType.BLUETOOTH.equals(transportType)){
									//transport can't support high bandwidth
									onTransportNotAccepted(transportType + " can't support high bandwidth requirement, and secondary transport not supported in this protocol version: " + version.toString());
									return;
								}
								*/
							} //else we are good to go
							if(activeTransports.get(SessionType.RPC) == null) {	//Might be a better way to handle this
								availableTransports.add(transportType);
								activeTransports.put(SessionType.RPC, transportType);
								activeTransports.put(SessionType.BULK_DATA, transportType);
								activeTransports.put(SessionType.CONTROL, transportType);
							} else{
								Log.w(TAG, "Received a start service ack for RPC service while already active on a different transport.");
							}
						}
					}else if(serviceType.equals(SessionType.NAV)){
						SdlSession session = null;
						if(sessionWeakReference != null){
							session = sessionWeakReference.get();
						}else if(sdlconn != null){
							session = sdlconn.findSessionById((byte)packet.getSessionId());
						}
						if(session != null) {
							ImageResolution acceptedResolution = new ImageResolution();
							VideoStreamingFormat acceptedFormat = new VideoStreamingFormat();
							acceptedResolution.setResolutionHeight((Integer) packet.getTag(ControlFrameTags.Video.StartServiceACK.HEIGHT));
							acceptedResolution.setResolutionWidth((Integer) packet.getTag(ControlFrameTags.Video.StartServiceACK.WIDTH));
							acceptedFormat.setCodec(VideoStreamingCodec.valueForString((String) packet.getTag(ControlFrameTags.Video.StartServiceACK.VIDEO_CODEC)));
							acceptedFormat.setProtocol(VideoStreamingProtocol.valueForString((String) packet.getTag(ControlFrameTags.Video.StartServiceACK.VIDEO_PROTOCOL)));
							VideoStreamingParameters agreedVideoParams = session.getDesiredVideoParams();
							agreedVideoParams.setResolution(acceptedResolution);
							agreedVideoParams.setFormat(acceptedFormat);
							session.setAcceptedVideoParams(agreedVideoParams);
						}
					}
				}else{ //TODO check if we should set protocol version here
					TransportType transportType = packet.getTransportType();
					if(requiresHighBandwidth
							&& TransportType.BLUETOOTH.equals(transportType)){
						//transport can't support high bandwidth
						onTransportNotAccepted(transportType + "can't support high bandwidth requirement, and secondary transport not supported in this protocol version");
						return;
					}
					//If version < 5 and transport is acceptable we need to just add these
					activeTransports.put(SessionType.RPC, transportType);
					activeTransports.put(SessionType.BULK_DATA, transportType);
					activeTransports.put(SessionType.CONTROL, transportType);
					//activeTransports.put(SessionType.NAV, transportType);
					//activeTransports.put(SessionType.PCM, transportType);

					if (protocolVersion.getMajor() > 1){
						if (packet.payload!= null && packet.dataSize == 4){ //hashid will be 4 bytes in length
							hashID = BitConverter.intFromByteArray(packet.payload, 0);
						}
					}
				}
				handleProtocolSessionStarted(serviceType,(byte) packet.getSessionId(), getMajorVersionByte(), "", hashID, packet.isEncrypted());
            } else if (frameInfo == FrameDataControlFrameType.StartSessionNACK.getValue()) {
				List<String> rejectedParams = null;
				if(packet.version >= 5){
					String rejectedTag = null;
					if(serviceType.equals(SessionType.RPC)){
						rejectedTag = ControlFrameTags.RPC.StartServiceNAK.REJECTED_PARAMS;
					}else if(serviceType.equals(SessionType.PCM)){
						rejectedTag = ControlFrameTags.Audio.StartServiceNAK.REJECTED_PARAMS;
					}else if(serviceType.equals(SessionType.NAV)){
						rejectedTag = ControlFrameTags.Video.StartServiceNAK.REJECTED_PARAMS;
					}
					rejectedParams = (List<String>) packet.getTag(rejectedTag);
				}
				if (serviceType.eq(SessionType.NAV) || serviceType.eq(SessionType.PCM)) {
					handleProtocolSessionNACKed(serviceType, (byte)packet.getSessionId(), getMajorVersionByte(), "", rejectedParams);
				} else {
					handleProtocolError("Got StartSessionNACK for protocol sessionID=" + packet.getSessionId(), null);
				}
			} else if (frameInfo == FrameDataControlFrameType.EndSession.getValue()) {
				if (protocolVersion.getMajor() > 1) {
					handleProtocolSessionEnded(serviceType, (byte)packet.getSessionId(), "");
				} else {
					handleProtocolSessionEnded(serviceType, (byte)packet.getSessionId(), "");
				}
			} else if (frameInfo == FrameDataControlFrameType.EndSessionACK.getValue()) {
				handleProtocolSessionEnded(serviceType, (byte)packet.getSessionId(), "");
			} else if (frameInfo == FrameDataControlFrameType.EndSessionNACK.getValue()) {
				if(packet.version >= 5){
					String rejectedTag = null;
					if(serviceType.equals(SessionType.RPC)){
						rejectedTag = ControlFrameTags.RPC.EndServiceNAK.REJECTED_PARAMS;
					}else if(serviceType.equals(SessionType.PCM)){
						rejectedTag = ControlFrameTags.Audio.EndServiceNAK.REJECTED_PARAMS;
					}else if(serviceType.equals(SessionType.NAV)){
						rejectedTag = ControlFrameTags.Video.EndServiceNAK.REJECTED_PARAMS;
					}
					List<String> rejectedParams = (List<String>) packet.getTag(rejectedTag);
					// TODO: Pass these back
				}
				handleProtocolSessionEndedNACK(serviceType, (byte)packet.getSessionId(), "");
			} else if (frameInfo == FrameDataControlFrameType.ServiceDataACK.getValue()) {
				if (packet.getPayload() != null && packet.getDataSize() == 4) //service data ack will be 4 bytes in length
				{
					int serviceDataAckSize = BitConverter.intFromByteArray(packet.getPayload(), 0);
					handleProtocolServiceDataACK(serviceType, serviceDataAckSize,(byte)packet.getSessionId ());
				}
			} else if (frameInfo == FrameDataControlFrameType.RegisterSecondaryTransportACK.getValue()) {
				setRegisteredForTransport(true);
			} else if (frameInfo == FrameDataControlFrameType.RegisterSecondaryTransportNACK.getValue()) {
				String reason = (String) packet.getTag(ControlFrameTags.RPC.RegisterSecondaryTransportNAK.REASON);
				setRegisteredForTransport(false);
			} else if (frameInfo == FrameDataControlFrameType.TransportEventUpdate.getValue()) {
				// Get TCP params
				String ipAddr = (String) packet.getTag(ControlFrameTags.RPC.TransportEventUpdate.TCP_IP_ADDRESS);
				Integer port = (Integer) packet.getTag(ControlFrameTags.RPC.TransportEventUpdate.TCP_PORT);
				secondaryTransportParams = new HashMap<>();
				secondaryTransportParams.put(ControlFrameTags.RPC.TransportEventUpdate.TCP_IP_ADDRESS, ipAddr);
				secondaryTransportParams.put(ControlFrameTags.RPC.TransportEventUpdate.TCP_PORT, port);

				// TODO: Send these params later before connecting / sending registration
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
			if (protocolVersion.getMajor() > 1 && !isControlService) {
				BinaryFrameHeader binFrameHeader = BinaryFrameHeader.
						parseBinaryHeader(packet.payload);
				if(binFrameHeader == null) {
					return;
				}
				message.setVersion(getMajorVersionByte());
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
	@Deprecated
	public void StartProtocolService(SessionType sessionType, byte sessionID, boolean isEncrypted) {
		SdlPacket header = SdlPacketFactory.createStartSession(sessionType, 0x00, getMajorVersionByte(), sessionID, isEncrypted);
		if(sessionType.equals(SessionType.NAV)){
			SdlSession videoSession =  null;
			if(sessionWeakReference != null){
				videoSession = sessionWeakReference.get();
			}else if(sdlconn != null){
				videoSession = sdlconn.findSessionById(sessionID);
			}
			if(videoSession != null){
				ImageResolution desiredResolution = videoSession.getDesiredVideoParams().getResolution();
				VideoStreamingFormat desiredFormat = videoSession.getDesiredVideoParams().getFormat();
				if(desiredResolution != null){
					header.putTag(ControlFrameTags.Video.StartService.WIDTH, desiredResolution.getResolutionWidth());
					header.putTag(ControlFrameTags.Video.StartService.HEIGHT, desiredResolution.getResolutionHeight());
				}
				if(desiredFormat != null){
					header.putTag(ControlFrameTags.Video.StartService.VIDEO_CODEC, desiredFormat.getCodec().toString());
					header.putTag(ControlFrameTags.Video.StartService.VIDEO_PROTOCOL, desiredFormat.getProtocol().toString());
				}
			}
		}
		handlePacketToSend(header);
	}

	@Override
	public void registerSecondaryTransport(byte sessionId, TransportType transportType) {
		SdlPacket header = SdlPacketFactory.createRegisterSecondaryTransport(sessionId, getMajorVersionByte());
		header.setTransportType(transportType);
		transportsToRegisterQueue.offer(transportType);
		handlePacketToSend(header);
	}

	public void startService(SessionType serviceType, byte sessionID, boolean isEncrypted) {
		Log.d(TAG, "startService");
		final SdlPacket header = SdlPacketFactory.createStartSession(serviceType, 0x00, getMajorVersionByte(), sessionID, isEncrypted);
		if(SessionType.RPC.equals(serviceType)){
			if(connectedPrimaryTransport != null) {
				header.setTransportType(connectedPrimaryTransport);
			}
			//This is going to be our primary transport
			header.putTag(ControlFrameTags.RPC.StartService.PROTOCOL_VERSION, MAX_PROTOCOL_VERSION.toString());
			handlePacketToSend(header);
			return; // We don't need to go any further
		}else if(serviceType.equals(SessionType.NAV)){
			SdlSession videoSession =  null;
			if(sessionWeakReference != null){
				videoSession = sessionWeakReference.get();
			}else if(sdlconn != null){
				videoSession = sdlconn.findSessionById(sessionID);
			}
			if(videoSession != null){
				ImageResolution desiredResolution = videoSession.getDesiredVideoParams().getResolution();
				VideoStreamingFormat desiredFormat = videoSession.getDesiredVideoParams().getFormat();
				if(desiredResolution != null){
					header.putTag(ControlFrameTags.Video.StartService.WIDTH, desiredResolution.getResolutionWidth());
					header.putTag(ControlFrameTags.Video.StartService.HEIGHT, desiredResolution.getResolutionHeight());
				}
				if(desiredFormat != null){
					header.putTag(ControlFrameTags.Video.StartService.VIDEO_CODEC, desiredFormat.getCodec().toString());
					header.putTag(ControlFrameTags.Video.StartService.VIDEO_PROTOCOL, desiredFormat.getProtocol().toString());
				}
			}
		}
		if(supportedServicesMap == null || supportedServicesMap.get(serviceType) == null || supportedServicesMap.get(serviceType).isEmpty()){
			handlePacketToSend(header);
			return;
		}
		int transportNum = supportedServicesMap.get(serviceType).get(0);
		if(transportNum == PRIMARY_TRANSPORT_ID){
			// Primary is favored, and we're already connected...
			Log.d(TAG, "Starting service over primary.");
			header.setTransportType(connectedPrimaryTransport);
			handlePacketToSend(header);
		}else if(transportNum == SECONDARY_TRANSPORT_ID) {
			for(TransportType secondaryTransportType : supportedSecondaryTransports) {
				// Secondary is favored
				Log.d(TAG, "Starting service over secondary.");
				if(activeTransports.get(serviceType).equals(secondaryTransportType)){
					// Transport is already active
					header.setTransportType(secondaryTransportType);
					handlePacketToSend(header);
					return;
				}
				List<ISecondaryTransportListener> listenerList = secondaryTransportListeners.get(secondaryTransportType);
				if(listenerList == null){
					listenerList = new ArrayList<>();
					secondaryTransportListeners.put(secondaryTransportType, listenerList);
				}
				if(supportedServicesMap.get(serviceType).contains(PRIMARY_TRANSPORT_ID)){
					// Primary is also supported as backup
					listenerList.add(new ISecondaryTransportListener() {
						@Override
						public void onConnectionSuccess() {
							handlePacketToSend(header);
						}

						@Override
						public void onConnectionFailure() {
							//TODO: Ensure this is called appropriately when secondary transport fails to connect
							header.setTransportType(connectedPrimaryTransport);
							handlePacketToSend(header);
						}
					});
				}else{
					// Only secondary is supported for this service
					listenerList.add(new ISecondaryTransportListener() {
						@Override
						public void onConnectionSuccess() {
							handlePacketToSend(header);
						}

						@Override
						public void onConnectionFailure() {
							//TODO: Ensure this is called appropriately when secondary transport fails to connect
							Log.d(TAG, "Failed to connect secondary transport, threw away StartService");
						}
					});
				}
				header.setTransportType(secondaryTransportType);
				connectSecondaryTransport(sessionID, secondaryTransportType, secondaryTransportParams);

			}
		}
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
        final SdlPacket heartbeat = SdlPacketFactory.createHeartbeat(SessionType.CONTROL, sessionID, getMajorVersionByte());
		heartbeat.setTransportType(activeTransports.get(sessionID));
        handlePacketToSend(heartbeat);		
	}

	@Override
	public void SendHeartBeatACK(byte sessionID) {
        final SdlPacket heartbeat = SdlPacketFactory.createHeartbeatACK(SessionType.CONTROL, sessionID, getMajorVersionByte());
		heartbeat.setTransportType(activeTransports.get(sessionID));
        handlePacketToSend(heartbeat);		
	}

	@Override
	public void EndProtocolService(SessionType serviceType, byte sessionID) {
		if(serviceType.equals(SessionType.RPC)){ //RPC session will close all other sessions so we want to make sure we use the correct EndProtocolSession method
			EndProtocolSession(serviceType,sessionID,hashID);
		}else {
			SdlPacket header = SdlPacketFactory.createEndSession(serviceType, sessionID, hashID, getMajorVersionByte(), new byte[0]);
			TransportType transportType = activeTransports.get(sessionID);
			if(transportType != null){
				header.setTransportType(transportType);
				handlePacketToSend(header);
			}else{
				Log.w(TAG, "Not sending end session packet because there is no session on that transport");
			}
		}
	}

} // end-class
