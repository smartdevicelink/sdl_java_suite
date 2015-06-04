package com.smartdevicelink.protocol.enums;

import java.util.Vector;

import com.smartdevicelink.util.ByteEnumer;

public class FrameType extends ByteEnumer {

	private static Vector<FrameType> theList = new Vector<FrameType>();
	public static Vector<FrameType> getList() { return theList; } 
	
	byte i = 0x00;
	
	protected FrameType(byte value, String name) {super(value, name);}
	public final static FrameType CONTROL = new FrameType((byte)0x00, "Control");
	public final static FrameType SINGLE = new FrameType((byte)0x01, "Single");
	public final static FrameType FIRST = new FrameType((byte)0x02, "First");
	public final static FrameType CONSECUTIVE = new FrameType((byte)0x03, "Consecutive");
	
	static {
		theList.addElement(CONTROL);
		theList.addElement(SINGLE);
		theList.addElement(FIRST);
		theList.addElement(CONSECUTIVE);
	}
	
	public static FrameType valueOf(byte passed) {
		return (FrameType) get(theList, passed);
	}
	
	public static FrameType[] values() {
		return (FrameType[]) theList.toArray();
	}
}
