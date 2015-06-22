package com.smartdevicelink.protocol.enums;

import java.util.Arrays;
import java.util.Vector;

public enum FrameDataControlFrameType {
	
	HEARTBEAT          ((byte) 0x00),	
	START_SESSION      ((byte) 0x01),
	START_SESSION_ACK  ((byte) 0x02),
	START_SESSION_NACK ((byte) 0x03),
	END_SESSION        ((byte) 0x04),
	END_SESSION_ACK    ((byte) 0x05),
	END_SESSION_NACK   ((byte) 0x06),
	HEARTBEAT_ACK      ((byte) 0xFF);
	
	private final byte value;
	
	FrameDataControlFrameType (byte value) {
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
	public boolean equals(FrameDataControlFrameType other) {
		return (this == other);
	}
	
	/** Use == operator instead. */
	@Deprecated
	public boolean eq(FrameDataControlFrameType other) {
		return (this == other);
	}
	
	/** Use .getValue() instead. */
	@Deprecated
	public byte value() {
		return value;
	}
	
	public static FrameDataControlFrameType valueOf (byte key) {
		return lookUp(FrameDataControlFrameType.values(), key);
	}
	
	/** Use .valueOf(byte) */
	@Deprecated
	public static FrameDataControlFrameType get(Vector<FrameType> list, byte key) {
		return lookUp(list.toArray(new FrameDataControlFrameType[list.size()]), key);
	}
	
	/** Use == operator instead. */
	@Deprecated
	public static FrameDataControlFrameType get(Vector<FrameDataControlFrameType> list, String key) {
		return lookUp(list.toArray(new FrameDataControlFrameType[list.size()]), key);
	}
	
	private static FrameDataControlFrameType lookUp (FrameDataControlFrameType[] list, byte key) {
		for(FrameDataControlFrameType value : list) {
			if (value.getValue() == key) {
				return value;
			}
		}
		return null;
	}
	
	private static FrameDataControlFrameType lookUp (FrameDataControlFrameType[] list, String key) {
		for(FrameDataControlFrameType value : list) {
			if (value.toString().equals(key)) {
				return value;
			}
		}
		return null;
	}
	
	/** Use enum .values() method instead. */
	@Deprecated
	public Vector<FrameDataControlFrameType> getList() {
		return new Vector<FrameDataControlFrameType>(Arrays.asList(values()));
	}
}