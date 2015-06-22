package com.smartdevicelink.protocol.enums;

import java.util.Arrays;
import java.util.Vector;

public enum SessionType {
	
	CONTROL   ((byte) 0x00),
	RPC       ((byte) 0x07),
	PCM       ((byte) 0x0A),
	NAV       ((byte) 0x0B),
	BULK_DATA ((byte) 0xF);
	
	private final byte value;
	
	SessionType (byte value) {
		this.value = value;
	}
	
	public byte getValue () {
		return value;
	}
	
	/** Use enum .toString() method instead. */
	@Deprecated
	public String getName () {
		return toString();
	}
	
	/** Use == operator instead. */
	@Deprecated
	public boolean equals(SessionType other) {
		return (this == other);
	}
	
	/** Use == operator instead. */
	@Deprecated
	public boolean eq(SessionType other) {
		return (this == other);
	}
	
	/** Use .getValue() instead. */
	@Deprecated
	public byte value() {
		return value;
	}
	
	public static SessionType valueOf (byte key) {
		return lookUp(SessionType.values(), key);
	}
	
	/** Use .valueOf(byte) */
	@Deprecated
	public static SessionType get(Vector<SessionType> list, byte key) {
		return lookUp(list.toArray(new SessionType[list.size()]), key);
	}
	
	/** Use == operator instead. */
	@Deprecated
	public static SessionType get(Vector<SessionType> list, String key) {
		return lookUp(list.toArray(new SessionType[list.size()]), key);
	}
	
	private static SessionType lookUp (SessionType[] list, byte key) {
		for(SessionType value : list) {
			if (value.getValue() == key) {
				return value;
			}
		}
		return null;
	}
	
	private static SessionType lookUp (SessionType[] list, String key) {
		for(SessionType value : list) {
			if (value.toString().equals(key)) {
				return value;
			}
		}
		return null;
	}
	
	/** Use enum .values() method instead. */
	@Deprecated
	public Vector<SessionType> getList() {
		return new Vector<SessionType>(Arrays.asList(values()));
	}
}