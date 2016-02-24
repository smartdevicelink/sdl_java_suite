package com.smartdevicelink.protocol;

import com.smartdevicelink.protocol.enums.*;

public class ProtocolFrameHeaderFactory {

	public static ProtocolFrameHeader createStartSession(ServiceType serviceType, int messageID, byte version, byte sessionID, boolean isEncrypted) {
		ProtocolFrameHeader msg = new ProtocolFrameHeader();
		msg.setVersion(version);
		msg.setFrameType(FrameType.Control);
		msg.setServiceType(serviceType);
		msg.setFrameData(FrameDataControlFrameType.StartSession.value());
		msg.setMessageID(messageID);
		msg.setSessionID(sessionID);
		msg.setEncrypted(isEncrypted);

		return msg;
	}

    public static ProtocolFrameHeader createHeartbeat(ServiceType serviceType, byte sessionID, 
            byte version) {
		return createControlFrame(serviceType, sessionID, version,
		FrameDataControlFrameType.Heartbeat);
    }

	public static ProtocolFrameHeader createHeartbeatACK(
			ServiceType serviceType, byte sessionID, byte version) {
		return createControlFrame(serviceType, sessionID, version,
		FrameDataControlFrameType.HeartbeatACK);
	}

	private static ProtocolFrameHeader createControlFrame(ServiceType serviceType, byte sessionID, byte version,
	            												FrameDataControlFrameType frameData) {
	        ProtocolFrameHeader msg = new ProtocolFrameHeader();
	        msg.setVersion(version);
	        msg.setFrameType(FrameType.Control);
	        msg.setServiceType(serviceType);
	        msg.setSessionID(sessionID);
	        msg.setFrameData(frameData.value());
	        return msg;
	}	
	
	public static ProtocolFrameHeader createStartSessionACK(ServiceType serviceType, byte sessionID, int messageID, byte version) {
		ProtocolFrameHeader msg = new ProtocolFrameHeader();
		msg.setVersion(version);
		msg.setFrameType(FrameType.Control);
		msg.setServiceType(serviceType);
		msg.setSessionID(sessionID);
		msg.setFrameData(FrameDataControlFrameType.StartSessionACK.value());
		msg.setMessageID(messageID);

		return msg;
	}

	public static ProtocolFrameHeader createStartSessionNACK(ServiceType serviceType, byte sessionID, int messageID, byte version) {
		ProtocolFrameHeader msg = new ProtocolFrameHeader();
		msg.setVersion(version);
		msg.setFrameType(FrameType.Control);
		msg.setServiceType(serviceType);
		msg.setSessionID(sessionID);
		msg.setFrameData(FrameDataControlFrameType.StartSessionNACK.value());
		msg.setMessageID(messageID);

		return msg;
	}

	public static ProtocolFrameHeader createEndSession(ServiceType serviceType, byte sessionID, int messageID, byte version) {
		ProtocolFrameHeader msg = new ProtocolFrameHeader();
		msg.setVersion(version);
		msg.setFrameType(FrameType.Control);
		msg.setServiceType(serviceType);
		msg.setSessionID(sessionID);
		msg.setFrameData(FrameDataControlFrameType.EndSession.value());
		msg.setMessageID(messageID);

		return msg;
	}

	public static ProtocolFrameHeader createSingleSendData(ServiceType serviceType, byte sessionID,
			int dataLength, int messageID, byte version, boolean isEncrypted) {
		ProtocolFrameHeader msg = new ProtocolFrameHeader();
		msg.setVersion(version);
		msg.setFrameType(FrameType.Single);
		msg.setServiceType(serviceType);
		msg.setFrameData(ProtocolFrameHeader.FrameDataSingleFrame);
		msg.setSessionID(sessionID);
		msg.setDataSize(dataLength);
		msg.setMessageID(messageID);
		msg.setEncrypted(isEncrypted);

		return msg;
	}

	public static ProtocolFrameHeader createMultiSendDataFirst(ServiceType serviceType, byte sessionID, 
			int messageID, byte version, boolean isEncrypted) {
		ProtocolFrameHeader msg = new ProtocolFrameHeader();
		msg.setVersion(version);
		msg.setFrameType(FrameType.First);
		msg.setServiceType(serviceType);
		msg.setFrameData(ProtocolFrameHeader.FrameDataFirstFrame);
		msg.setSessionID(sessionID);
		msg.setDataSize(8);
		msg.setMessageID(messageID);
		msg.setEncrypted(isEncrypted);

		return msg;
	}

	public static ProtocolFrameHeader createMultiSendDataRest(ServiceType serviceType, byte sessionID,
			int dataLength, byte frameSequenceNumber, int messageID, byte version, boolean isEncrypted) {
		ProtocolFrameHeader msg = new ProtocolFrameHeader();
		msg.setVersion(version);
		msg.setFrameType(FrameType.Consecutive);
		msg.setServiceType(serviceType);
		msg.setFrameData(frameSequenceNumber/*FrameData.ConsecutiveFrame.value()*/);
		msg.setSessionID(sessionID);
		msg.setDataSize(dataLength);
		msg.setMessageID(messageID);
		msg.setEncrypted(isEncrypted);

		return msg;
	}
	
	public static ProtocolFrameHeader createMultiSendDataRest(ServiceType serviceType, byte sessionID,
			int dataLength, int messageID, byte version) {
		ProtocolFrameHeader msg = new ProtocolFrameHeader();
		msg.setVersion(version);
		msg.setFrameType(FrameType.Consecutive);
		msg.setServiceType(serviceType);
		msg.setFrameData(FrameData.ConsecutiveFrame.value());
		msg.setSessionID(sessionID);
		msg.setDataSize(dataLength);
		msg.setMessageID(messageID);

		return msg;
	}
	
	public static BinaryFrameHeader createBinaryFrameHeader(byte rpcType, int functionID, int corrID, int jsonSize) {
		BinaryFrameHeader msg = new BinaryFrameHeader();
		msg.setRPCType(rpcType);
		msg.setFunctionID(functionID);
		msg.setCorrID(corrID);
		msg.setJsonSize(jsonSize);
		
		return msg;
	}
}
