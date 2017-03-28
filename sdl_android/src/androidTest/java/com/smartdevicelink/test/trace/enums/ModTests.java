package com.smartdevicelink.test.trace.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.test.Test;
import com.smartdevicelink.trace.enums.Mod;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.trace.enums.Mod}
 */
public class ModTests extends TestCase {
	
	/**
	 * This is a unit test for the following enum : 
	 * {@link com.smartdevicelink.trace.enums.Mod}
	 */
	public void testModEnum () {
		
		// TestValues
		String testApp     = "app";
		String testRpc     = "rpc";
		String testMar     = "mar";
		String testTran    = "tran";
		String testProto   = "proto";
		String testProxy   = "proxy";
		String testInvalid = "invalid";
		
		try {
			// Comparison Values
			Mod expectedAppEnum   = Mod.app;
			Mod expectedRpcEnum   = Mod.rpc;
			Mod expectedMarEnum   = Mod.mar;
			Mod expectedTranEnum  = Mod.tran;
			Mod expectedProtoEnum = Mod.proto;
			Mod expectedProxyEnum = Mod.proxy;
			List<Mod> expectedEnumList = new ArrayList<Mod>();
			expectedEnumList.add(Mod.app);
			expectedEnumList.add(Mod.rpc);
			expectedEnumList.add(Mod.mar);
			expectedEnumList.add(Mod.tran);
			expectedEnumList.add(Mod.proto);
			expectedEnumList.add(Mod.proxy);
			
			Mod actualNullEnum       = Mod.valueForString(null);
			Mod actualAppEnum        = Mod.valueForString(testApp);
			Mod actualRpcEnum        = Mod.valueForString(testRpc);
			Mod actualMarEnum        = Mod.valueForString(testMar);
			Mod actualTranEnum       = Mod.valueForString(testTran);
			Mod actualProtoEnum      = Mod.valueForString(testProto);
			Mod actualProxyEnum      = Mod.valueForString(testProxy);
			Mod actualInvalidEnum    = Mod.valueForString(testInvalid);
			List<Mod> actualEnumList = Arrays.asList(Mod.values());
			
			// Valid Tests
			assertEquals(Test.MATCH, expectedAppEnum, actualAppEnum);
			assertEquals(Test.MATCH, expectedRpcEnum, actualRpcEnum);
			assertEquals(Test.MATCH, expectedMarEnum, actualMarEnum);
			assertEquals(Test.MATCH, expectedTranEnum, actualTranEnum);
			assertEquals(Test.MATCH, expectedProtoEnum, actualProtoEnum);
			assertEquals(Test.MATCH, expectedProxyEnum, actualProxyEnum);
			assertTrue(Test.ARRAY, expectedEnumList.containsAll(actualEnumList) && actualEnumList.containsAll(expectedEnumList));
			
			// Invalid/Null Tests
			assertNull(Test.NULL, actualInvalidEnum);
			assertNull(Test.NULL, actualNullEnum);
			
		} catch (NullPointerException e) {
			fail("Could not retrieve value for null string, should return null.");
		} catch (IllegalArgumentException e) {
			fail("Could not retrieve value for invalid string, should return null.");
		}
		
	}
}