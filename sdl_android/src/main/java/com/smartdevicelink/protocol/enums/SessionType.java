package com.smartdevicelink.protocol.enums;

import java.util.Vector;

import com.smartdevicelink.util.ByteEnumer;


public class SessionType extends ByteEnumer {

	private static Vector<SessionType> theList = new Vector<SessionType>();
	public static Vector<SessionType> getList() { return theList; } 
	
	byte i = 0x00;
	
	protected SessionType(byte value, String name) {super(value, name);}
	public final static SessionType CONTROL = new SessionType((byte) 0, "CONTROL");
	public final static SessionType RPC = new SessionType((byte)0x07, "RPC");
	public final static SessionType PCM = new SessionType((byte)0x0A, "PCM");
	public final static SessionType NAV = new SessionType((byte)0x0B, "NAV");
	public final static SessionType BULK_DATA = new SessionType((byte)0xF, "BULK_DATA");

	static {
		theList.addElement(RPC);
		theList.addElement(PCM);
		theList.addElement(NAV);
		theList.addElement(BULK_DATA);
		theList.addElement(CONTROL);
	}
	
	public static SessionType valueOf(byte passedButton) {
		return (SessionType) get(theList, passedButton);
	}
	
	public static SessionType[] values() {
		return theList.toArray(new SessionType[theList.size()]);
	}
}
