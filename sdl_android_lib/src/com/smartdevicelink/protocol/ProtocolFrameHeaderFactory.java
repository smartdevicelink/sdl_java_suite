package com.smartdevicelink.protocol;

import com.smartdevicelink.protocol.enums.*;

public class ProtocolFrameHeaderFactory {

	public static ProtocolFrameHeader createStartSession(SessionType serviceType, int messageId, byte version, byte sessionId) {
		ProtocolFrameHeader msg = new ProtocolFrameHeader();
		msg.setVersion(version);
		msg.setFrameType(FrameType.CONTROL);
		msg.setSessionType(serviceType);
		msg.setFrameData(FrameDataControlFrameType.START_SESSION.value());
		msg.setMessageId(messageId);
		msg.setSessionId(sessionId);

		return msg;
	}

    public static ProtocolFrameHeader createHeartbeat(SessionType serviceType, byte sessionId, 
            byte version) {
		return createControlFrame(serviceType, sessionId, version,
		FrameDataControlFrameType.HEARTBEAT);
    }

	public static ProtocolFrameHeader createHeartbeatAck(
			SessionType serviceType, byte sessionId, byte version) {
		return createControlFrame(serviceType, sessionId, version,
		FrameDataControlFrameType.HEARTBEAT_ACK);
	}

	private static ProtocolFrameHeader createControlFrame(SessionType serviceType, byte sessionId, byte version,
	            												FrameDataControlFrameType frameData) {
	        ProtocolFrameHeader msg = new ProtocolFrameHeader();
	        msg.setVersion(version);
	        msg.setFrameType(FrameType.CONTROL);
	        msg.setSessionType(serviceType);
	        msg.setSessionId(sessionId);
	        msg.setFrameData(frameData.value());
	        return msg;
	}	
	
	public static ProtocolFrameHeader createStartSessionAck(SessionType serviceType, byte sessionId, int messageId, byte version) {
		ProtocolFrameHeader msg = new ProtocolFrameHeader();
		msg.setVersion(version);
		msg.setFrameType(FrameType.CONTROL);
		msg.setSessionType(serviceType);
		msg.setSessionId(sessionId);
		msg.setFrameData(FrameDataControlFrameType.START_SESSION_ACK.value());
		msg.setMessageId(messageId);

		return msg;
	}

	public static ProtocolFrameHeader createStartSessionNack(SessionType serviceType, byte sessionId, int messageId, byte version) {
		ProtocolFrameHeader msg = new ProtocolFrameHeader();
		msg.setVersion(version);
		msg.setFrameType(FrameType.CONTROL);
		msg.setSessionType(serviceType);
		msg.setSessionId(sessionId);
		msg.setFrameData(FrameDataControlFrameType.START_SESSION_NACK.value());
		msg.setMessageId(messageId);

		return msg;
	}

	public static ProtocolFrameHeader createEndSession(SessionType serviceType, byte sessionId, int messageId, byte version) {
		ProtocolFrameHeader msg = new ProtocolFrameHeader();
		msg.setVersion(version);
		msg.setFrameType(FrameType.CONTROL);
		msg.setSessionType(serviceType);
		msg.setSessionId(sessionId);
		msg.setFrameData(FrameDataControlFrameType.END_SESSION.value());
		msg.setMessageId(messageId);

		return msg;
	}

	public static ProtocolFrameHeader createSingleSendData(SessionType serviceType, byte sessionId,
			int dataLength, int messageId, byte version) {
		ProtocolFrameHeader msg = new ProtocolFrameHeader();
		msg.setVersion(version);
		msg.setFrameType(FrameType.SINGLE);
		msg.setSessionType(serviceType);
		msg.setFrameData(ProtocolFrameHeader.FRAME_DATA_SINGLE);
		msg.setSessionId(sessionId);
		msg.setDataSize(dataLength);
		msg.setMessageId(messageId);

		return msg;
	}

	public static ProtocolFrameHeader createMultiSendDataFirst(SessionType serviceType, byte sessionId, 
			int messageId, byte version) {
		ProtocolFrameHeader msg = new ProtocolFrameHeader();
		msg.setVersion(version);
		msg.setFrameType(FrameType.FIRST);
		msg.setSessionType(serviceType);
		msg.setFrameData(ProtocolFrameHeader.FRAME_DATA_FIRST);
		msg.setSessionId(sessionId);
		msg.setDataSize(8);
		msg.setMessageId(messageId);

		return msg;
	}

	public static ProtocolFrameHeader createMultiSendDataRest(SessionType serviceType, byte sessionId,
			int dataLength, byte frameSequenceNumber, int messageId, byte version) {
		ProtocolFrameHeader msg = new ProtocolFrameHeader();
		msg.setVersion(version);
		msg.setFrameType(FrameType.CONSECUTIVE);
		msg.setSessionType(serviceType);
		msg.setFrameData(frameSequenceNumber/*FrameData.ConsecutiveFrame.value()*/);
		msg.setSessionId(sessionId);
		msg.setDataSize(dataLength);
		msg.setMessageId(messageId);

		return msg;
	}
	
	public static ProtocolFrameHeader createMultiSendDataRest(SessionType serviceType, byte sessionId,
			int dataLength, int messageId, byte version) {
		ProtocolFrameHeader msg = new ProtocolFrameHeader();
		msg.setVersion(version);
		msg.setFrameType(FrameType.CONSECUTIVE);
		msg.setSessionType(serviceType);
		msg.setFrameData(FrameData.CONSECUTIVE_FRAME.value());
		msg.setSessionId(sessionId);
		msg.setDataSize(dataLength);
		msg.setMessageId(messageId);

		return msg;
	}
	
	public static BinaryFrameHeader createBinaryFrameHeader(byte rpcType, int functionId, int corrId, int jsonSize) {
		BinaryFrameHeader msg = new BinaryFrameHeader();
		msg.setRpcType(rpcType);
		msg.setFunctionId(functionId);
		msg.setCorrId(corrId);
		msg.setJsonSize(jsonSize);
		
		return msg;
	}
}
