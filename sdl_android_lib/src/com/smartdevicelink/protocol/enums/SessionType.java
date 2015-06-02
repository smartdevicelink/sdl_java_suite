package com.smartdevicelink.protocol.enums;

public enum SessionType {
	
	CONTROL   ((byte) 0x00),
	RPC       ((byte) 0x07),
	PCM       ((byte) 0x0A),
	NAV       ((byte) 0x0B),
	BULK_DATA ((byte) 0xF);
	
	private final byte id;
	
	SessionType (byte id) {
		this.id = id;
	}
	
	public byte getId () {
		return id;
	}
	
	public static SessionType valueOf (byte key) {
		for(SessionType value : SessionType.values()) {
			if (value.getId() == key) {
				return value;
			}
		}
		return null;
	}
}