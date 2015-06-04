package com.smartdevicelink.protocol.enums;

import java.util.Vector;

import com.smartdevicelink.util.ByteEnumer;

public class FrameData extends ByteEnumer {

	private static Vector<FrameData> theList = new Vector<FrameData>();
	public static Vector<FrameData> getList() { return theList; } 
	
	byte i = 0x00;
	
	protected FrameData(byte value, String name) {super(value, name);}
	public final static FrameData START_SESSION = new FrameData((byte)0x01, "StartSession");
	public final static FrameData START_SESSION_ACK = new FrameData((byte)0x02, "StartSessionACK");
	public final static FrameData START_SESSION_NACK = new FrameData((byte)0x03, "StartSessionNACK");
	public final static FrameData END_SESSION = new FrameData((byte)0x04, "EndSession");
	
	public final static FrameData SINGLE_FRAME = new FrameData((byte)0x00, "SingleFrame");
	public final static FrameData FIRST_FRAME = new FrameData((byte)0x00, "FirstFrame");
	public final static FrameData CONSECUTIVE_FRAME = new FrameData((byte)0x00, "ConsecutiveFrame");
	public final static byte LAST_FRAME = (byte)0x00;
	
	static {
		theList.addElement(START_SESSION);
		theList.addElement(START_SESSION_ACK);
		theList.addElement(START_SESSION_NACK);
		theList.addElement(END_SESSION);	
	}
	
	public static FrameData valueOf(String passedButton) {
		return (FrameData) get(theList, passedButton);
	}
	
	public static FrameData[] values() {
		return (FrameData[]) theList.toArray();
	}
}
