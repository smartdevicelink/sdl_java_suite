package com.smartdevicelink.protocol.enums;

import java.util.Vector;

import com.smartdevicelink.util.ByteEnumer;

public class FrameDataControlFrameType extends ByteEnumer {
	private static Vector<FrameDataControlFrameType> theList = new Vector<FrameDataControlFrameType>();
	public static Vector<FrameDataControlFrameType> getList() { return theList; } 

	@SuppressWarnings("unused")
    private byte _i = 0x00;

	protected FrameDataControlFrameType(byte value, String name) {super(value, name);}
	public final static FrameDataControlFrameType HEARTBEAT = new FrameDataControlFrameType((byte)0x0, "Heartbeat");
	public final static FrameDataControlFrameType START_SESSION = new FrameDataControlFrameType((byte)0x01, "StartSession");
	public final static FrameDataControlFrameType START_SESSION_ACK = new FrameDataControlFrameType((byte)0x02, "StartSessionACK");
	public final static FrameDataControlFrameType START_SESSION_NACK = new FrameDataControlFrameType((byte)0x03, "StartSessionNACK");
	public final static FrameDataControlFrameType END_SESSION = new FrameDataControlFrameType((byte)0x04, "EndSession");
	public final static FrameDataControlFrameType END_SESSION_ACK = new FrameDataControlFrameType((byte)0x05, "EndSessionACK");
	public final static FrameDataControlFrameType END_SESSION_NACK = new FrameDataControlFrameType((byte)0x06, "EndSessionNACK");
	public final static FrameDataControlFrameType HEARTBEAT_ACK = new FrameDataControlFrameType((byte)0xFF, "HeartbeatACK");

	static {
		theList.addElement(HEARTBEAT);
		theList.addElement(START_SESSION);
		theList.addElement(START_SESSION_ACK);
		theList.addElement(START_SESSION_NACK);
		theList.addElement(END_SESSION);	
		theList.addElement(END_SESSION_ACK);
		theList.addElement(END_SESSION_NACK);
		theList.addElement(HEARTBEAT_ACK);
	}

	public static FrameDataControlFrameType valueOf(String passedButton) {
		return (FrameDataControlFrameType) get(theList, passedButton);
	} // end-method

	public static FrameDataControlFrameType[] values() {
		return (FrameDataControlFrameType[]) theList.toArray();
	} // end-method
} // end-class