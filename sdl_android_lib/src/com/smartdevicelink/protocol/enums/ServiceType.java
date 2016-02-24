package com.smartdevicelink.protocol.enums;

import java.util.Vector;

import com.smartdevicelink.util.ByteEnumer;


public class ServiceType extends ByteEnumer {

	private static Vector<ServiceType> theList = new Vector<ServiceType>();
	public static Vector<ServiceType> getList() { return theList; } 
	
	byte i = 0x00;
	
	protected ServiceType(byte value, String name) {super(value, name);}
	public final static ServiceType CONTROL = new ServiceType((byte) 0, "CONTROL");
	public final static ServiceType RPC = new ServiceType((byte)0x07, "RPC");
	public final static ServiceType PCM = new ServiceType((byte)0x0A, "PCM");
	public final static ServiceType NAV = new ServiceType((byte)0x0B, "NAV");
	public final static ServiceType BULK_DATA = new ServiceType((byte)0xF, "BULK_DATA");

	static {
		theList.addElement(RPC);
		theList.addElement(PCM);
		theList.addElement(NAV);
		theList.addElement(BULK_DATA);
		theList.addElement(CONTROL);
	}
	
	public static ServiceType valueOf(byte passedButton) {
		return (ServiceType) get(theList, passedButton);
	}
	
	public static ServiceType[] values() {
		return theList.toArray(new ServiceType[theList.size()]);
	}
}
