package com.smartdevicelink.protocol.enums;

public enum FrameData {
	
	LAST_FRAME         ((byte) 0x00),
	FIRST_FRAME        ((byte) 0x00),
	SINGLE_FRAME       ((byte) 0x00),
	CONSECUTIVE_FRAME  ((byte) 0x00),
	
	START_SESSION      ((byte) 0x01),
	START_SESSION_ACK  ((byte) 0x02),
	START_SESSION_NACK ((byte) 0x03),
	END_SESSION        ((byte) 0x04);
	
	private final byte id;
	
	FrameData (byte id) {
		this.id = id;
	}
	
	public byte getId () {
		return id;
	}
	
	public static FrameData valueOf (byte key) {
		for(FrameData value : FrameData.values()) {
			if (value.getId() == key) {
				return value;
			}
		}
		return null;
	}
}