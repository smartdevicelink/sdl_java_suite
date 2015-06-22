package com.smartdevicelink.protocol.enums;

import java.util.Arrays;
import java.util.Vector;

public enum FrameData {
	
	LAST_FRAME         ((byte) 0x00),
	FIRST_FRAME        ((byte) 0x00),
	SINGLE_FRAME       ((byte) 0x00),
	CONSECUTIVE_FRAME  ((byte) 0x00),
	
	START_SESSION      ((byte) 0x01),
	START_SESSION_ACK  ((byte) 0x02),
	START_SESSION_NACK ((byte) 0x03),
	END_SESSION        ((byte) 0x04);
	
	private final byte value;
	
	FrameData (byte value) {
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
	public boolean equals(FrameData other) {
		return (this == other);
	}
	
	/** Use == operator instead. */
	@Deprecated
	public boolean eq(FrameData other) {
		return (this == other);
	}
	
	/** Use .getValue() instead. */
	@Deprecated
	public byte value() {
		return value;
	}
	
	public static FrameData valueOf (byte key) {
		return lookUp(FrameData.values(), key);
	}
	
	/** Use .valueOf(byte) */
	@Deprecated
	public static FrameData get(Vector<FrameData> list, byte key) {
		return lookUp(list.toArray(new FrameData[list.size()]), key);
	}
	
	/** Use == operator instead. */
	@Deprecated
	public static FrameData get(Vector<FrameData> list, String key) {
		return lookUp(list.toArray(new FrameData[list.size()]), key);
	}
	
	private static FrameData lookUp (FrameData[] list, byte key) {
		for(FrameData value : list) {
			if (value.getValue() == key) {
				return value;
			}
		}
		return null;
	}
	
	private static FrameData lookUp (FrameData[] list, String key) {
		for(FrameData value : list) {
			if (value.toString().equals(key)) {
				return value;
			}
		}
		return null;
	}
	
	/** Use enum .values() method instead. */
	@Deprecated
	public Vector<FrameData> getList() {
		return new Vector<FrameData>(Arrays.asList(values()));
	}
}