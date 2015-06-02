package com.smartdevicelink.protocol.enums;

public enum FrameDataControlFrameType {
	
	HEARTBEAT          ((byte) 0x00),	
	START_SESSION      ((byte) 0x01),
	START_SESSION_ACK  ((byte) 0x02),
	START_SESSION_NACK ((byte) 0x03),
	END_SESSION        ((byte) 0x04),
	END_SESSION_ACK    ((byte) 0x05),
	END_SESSION_NACK   ((byte) 0x06),
	HEARTBEAT_ACK      ((byte) 0xFF);
	
	private final byte id;
	
	FrameDataControlFrameType (byte id) {
		this.id = id;
	}
	
	public byte getId () {
		return id;
	}	
	
	public static FrameDataControlFrameType valueOf (byte key) {
		for(FrameDataControlFrameType value : FrameDataControlFrameType.values()) {
			if (value.getId() == key) {
				return value;
			}
		}
		return null;
	}
}