package com.smartdevicelink.test.trace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.trace.enums.Mod;

public class ModTests extends TestCase {
	
	private final String MSG = "Value did not match expected value.";
	
	public void testValidEnums () {
		
		String test = "proto";
		Mod enumProto = Mod.valueForString(test);		
		test = "proxy";
		Mod enumProxy = Mod.valueForString(test);
		test = "tran";
		Mod enumTran = Mod.valueForString(test);
		test = "app";
		Mod enumApp = Mod.valueForString(test);
		test = "rpc";
		Mod enumRpc = Mod.valueForString(test);
		test = "mar";
		Mod enumMar = Mod.valueForString(test);
		
		assertNotNull(MSG, enumProto);
		assertNotNull(MSG, enumProxy);
		assertNotNull(MSG, enumTran);
		assertNotNull(MSG, enumApp);
		assertNotNull(MSG, enumRpc);
		assertNotNull(MSG, enumMar);
	}

	public void testInvalidEnum () {		
		String test = "invalid";
		try {
			Mod temp = Mod.valueForString(test);
			assertNull(MSG, temp);
		} catch (IllegalArgumentException e) {
			fail("Invalid enum throws IllegalArgumentException.");
		}
	}
	
	public void testNullEnum () {
		String test = null;
		try {
			Mod temp = Mod.valueForString(test);
			assertNull(MSG, temp);
		} catch (NullPointerException e) {
			fail("Invalid enum throws NullPointerException.");
		}
	}
	
	public void testListEnum () {
		List<Mod> enumValueList = Arrays.asList(Mod.values());
		List<Mod> enumTestList  = new ArrayList<Mod>();
		
		enumTestList.add(Mod.tran);
		enumTestList.add(Mod.proto);
		enumTestList.add(Mod.mar);
		enumTestList.add(Mod.rpc);
		enumTestList.add(Mod.app);
		enumTestList.add(Mod.proxy);
		
		assertTrue("Enum value list does not match enum class list.",
				enumValueList.containsAll(enumTestList) &&
				enumTestList.containsAll(enumValueList));		
	}
}