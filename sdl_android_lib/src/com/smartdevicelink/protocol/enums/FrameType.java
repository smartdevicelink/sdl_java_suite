package com.smartdevicelink.protocol.enums;

import java.util.Arrays;
import java.util.Vector;

public enum FrameType {
	
	CONTROL     ((byte) 0x00),
	SINGLE      ((byte) 0x01),
	FIRST       ((byte) 0x02),
	CONSECUTIVE ((byte) 0x03);
	
	private final byte id;
	
	FrameType (byte id) {
		this.id = id;
	}
	
	public byte getId () {
		return id;
	}
	
	public static FrameType valueOf (byte key) {
		for(FrameType value : FrameType.values()) {
			if (value.getId() == key) {
				return value;
			}
		}
		return null;
	}
	
	@Deprecated
	public Vector<FrameType> getList() {
		return new Vector<FrameType>(Arrays.asList(values()));
	}
}