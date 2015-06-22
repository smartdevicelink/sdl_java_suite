package com.smartdevicelink.protocol.enums;

import java.util.Arrays;
import java.util.Vector;

public enum FrameType {
	
	CONTROL     ((byte) 0x00),
	SINGLE      ((byte) 0x01),
	FIRST       ((byte) 0x02),
	CONSECUTIVE ((byte) 0x03);
	
	private final byte value;
	
	FrameType (byte value) {
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
	public boolean equals(FrameType other) {
		return (this == other);
	}
	
	/** Use == operator instead. */
	@Deprecated
	public boolean eq(FrameType other) {
		return (this == other);
	}
	
	/** Use .getValue() instead. */
	@Deprecated
	public byte value() {
		return value;
	}
	
	public static FrameType valueOf (byte key) {
		return lookUp(FrameType.values(), key);
	}
	
	/** Use .valueOf(byte) */
	@Deprecated
	public static FrameType get(Vector<FrameType> list, byte key) {
		return lookUp(list.toArray(new FrameType[list.size()]), key);
	}
	
	/** Use == operator instead. */
	@Deprecated
	public static FrameType get(Vector<FrameType> list, String key) {
		return lookUp(list.toArray(new FrameType[list.size()]), key);
	}
	
	private static FrameType lookUp (FrameType[] list, byte key) {
		for(FrameType value : list) {
			if (value.getValue() == key) {
				return value;
			}
		}
		return null;
	}
	
	private static FrameType lookUp (FrameType[] list, String key) {
		for(FrameType value : list) {
			if (value.toString().equals(key)) {
				return value;
			}
		}
		return null;
	}
	
	/** Use enum .values() method instead. */
	@Deprecated
	public Vector<FrameType> getList() {
		return new Vector<FrameType>(Arrays.asList(values()));
	}
}