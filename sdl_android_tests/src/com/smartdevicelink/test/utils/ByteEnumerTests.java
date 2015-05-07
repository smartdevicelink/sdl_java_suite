package com.smartdevicelink.test.utils;

import java.util.Vector;

import junit.framework.TestCase;

import com.smartdevicelink.util.ByteEnumer;

public class ByteEnumerTests extends TestCase {
	
	public void testMethods () {
	
		ExtByteEnumer test = new ExtByteEnumer((byte) 0x01, "test a");
		assertEquals("Values should match.", (byte) 0x01, test.getValue());
		assertEquals("Values should match.", (byte) 0x01, test.value());
		assertEquals("Values should match.", "test a", test.getName());
		
		ExtByteEnumer copy = test;
		assertTrue("Value should be true.", test.equals(copy));
		assertTrue("Value should be true.", test.eq(copy));
		
		Vector<ByteEnumer> list = new Vector<ByteEnumer>();
		list.add(test);
		list.add(new ExtByteEnumer((byte) 0x02, "test b"));
		
		assertNotNull("Value should exist.", ByteEnumer.get(list, (byte) 0x01));
		assertNotNull("Value should exist.", ByteEnumer.get(list, (byte) 0x02));
		assertNotNull("Value should exist.", ByteEnumer.get(list, "test a"));
		assertNotNull("Value should exist.", ByteEnumer.get(list, "test b"));
		assertNull("Value should not exist.", ByteEnumer.get(list, null));
	}
}

class ExtByteEnumer extends ByteEnumer {
	protected ExtByteEnumer(byte value, String name) {
		super(value, name);
	}	
}