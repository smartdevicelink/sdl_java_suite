package com.smartdevicelink.test.trace;

import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.trace.DiagLevel;
import com.smartdevicelink.trace.enums.DetailLevel;
import com.smartdevicelink.trace.enums.Mod;

import junit.framework.TestCase;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.trace.DiagLevel}
 */
public class DiagLevelTests extends TestCase {
	
	/**
	 * This is a unit test for the following methods : 
	 * {@link com.smartdevicelink.trace.DiagLevel#setAllLevels(DetailLevel)}
	 * {@link com.smartdevicelink.trace.DiagLevel#setLevel(Mod, DetailLevel)}
	 * {@link com.smartdevicelink.trace.DiagLevel#getLevel(Mod)}
	 * {@link com.smartdevicelink.trace.DiagLevel#isValidDetailLevel(String)}
	 * {@link com.smartdevicelink.trace.DiagLevel#toDetailLevel(String)}
	 */
	public void testLevels () {
		
		// Test Values
		Mod testMar   = Mod.mar;
		Mod testRpc   = Mod.rpc;
		Mod testApp   = Mod.app;
		Mod testTran  = Mod.tran;
		Mod testProxy = Mod.proxy;
		Mod testProto = Mod.proto;
		
		String testOffS     = "off";
		String testTerseS   = "terse";
		String testVerboseS = "verbose";
		String testInvalidS = "invalid";
		
		DetailLevel testOff     = DetailLevel.OFF;
		DetailLevel testTerse   = DetailLevel.TERSE;
		DetailLevel testVerbose = DetailLevel.VERBOSE;
				
		// Valid Tests
		DiagLevel.setAllLevels(testVerbose);
		assertEquals(TestValues.MATCH, testVerbose, DiagLevel.getLevel(testProto));
		assertEquals(TestValues.MATCH, testVerbose, DiagLevel.getLevel(testProxy));
		assertEquals(TestValues.MATCH, testVerbose, DiagLevel.getLevel(testTran));
		assertEquals(TestValues.MATCH, testVerbose, DiagLevel.getLevel(testApp));
		assertEquals(TestValues.MATCH, testVerbose, DiagLevel.getLevel(testRpc));
		assertEquals(TestValues.MATCH, testVerbose, DiagLevel.getLevel(testMar));
		
		DiagLevel.setAllLevels(testTerse);
		assertEquals(TestValues.MATCH, testTerse, DiagLevel.getLevel(testProto));
		assertEquals(TestValues.MATCH, testTerse, DiagLevel.getLevel(testProxy));
		assertEquals(TestValues.MATCH, testTerse, DiagLevel.getLevel(testTran));
		assertEquals(TestValues.MATCH, testTerse, DiagLevel.getLevel(testApp));
		assertEquals(TestValues.MATCH, testTerse, DiagLevel.getLevel(testRpc));
		assertEquals(TestValues.MATCH, testTerse, DiagLevel.getLevel(testMar));
		
		DiagLevel.setAllLevels(testOff);
		assertEquals(TestValues.MATCH, testOff, DiagLevel.getLevel(testProto));
		assertEquals(TestValues.MATCH, testOff, DiagLevel.getLevel(testProxy));
		assertEquals(TestValues.MATCH, testOff, DiagLevel.getLevel(testTran));
		assertEquals(TestValues.MATCH, testOff, DiagLevel.getLevel(testApp));
		assertEquals(TestValues.MATCH, testOff, DiagLevel.getLevel(testRpc));
		assertEquals(TestValues.MATCH, testOff, DiagLevel.getLevel(testMar));
		
		
		DiagLevel.setLevel(testMar, testVerbose);
		assertEquals(TestValues.MATCH, testVerbose, DiagLevel.getLevel(testMar));
		DiagLevel.setLevel(testMar, testTerse);
		assertEquals(TestValues.MATCH, testTerse, DiagLevel.getLevel(testMar));
		DiagLevel.setLevel(testMar, testOff);
		assertEquals(TestValues.MATCH, testOff, DiagLevel.getLevel(testMar));
		
		DiagLevel.setLevel(testRpc, testVerbose);
		assertEquals(TestValues.MATCH, testVerbose, DiagLevel.getLevel(testRpc));
		DiagLevel.setLevel(testRpc, testTerse);
		assertEquals(TestValues.MATCH, testTerse, DiagLevel.getLevel(testRpc));
		DiagLevel.setLevel(testRpc, testOff);
		assertEquals(TestValues.MATCH, testOff, DiagLevel.getLevel(testRpc));
		
		DiagLevel.setLevel(testApp, testVerbose);
		assertEquals(TestValues.MATCH, testVerbose, DiagLevel.getLevel(testApp));
		DiagLevel.setLevel(testApp, testTerse);
		assertEquals(TestValues.MATCH, testTerse, DiagLevel.getLevel(testApp));
		DiagLevel.setLevel(testApp, testOff);
		assertEquals(TestValues.MATCH, testOff, DiagLevel.getLevel(testApp));
		
		DiagLevel.setLevel(testTran, testVerbose);
		assertEquals(TestValues.MATCH, testVerbose, DiagLevel.getLevel(testTran));
		DiagLevel.setLevel(testTran, testTerse);
		assertEquals(TestValues.MATCH, testTerse, DiagLevel.getLevel(testTran));
		DiagLevel.setLevel(testTran, testOff);
		assertEquals(TestValues.MATCH, testOff, DiagLevel.getLevel(testTran));
		
		DiagLevel.setLevel(testProxy, testVerbose);
		assertEquals(TestValues.MATCH, testVerbose, DiagLevel.getLevel(testProxy));
		DiagLevel.setLevel(testProxy, testTerse);
		assertEquals(TestValues.MATCH, testTerse, DiagLevel.getLevel(testProxy));
		DiagLevel.setLevel(testProxy, testOff);
		assertEquals(TestValues.MATCH, testOff, DiagLevel.getLevel(testProxy));
		
		DiagLevel.setLevel(testProto, testVerbose);
		assertEquals(TestValues.MATCH, testVerbose, DiagLevel.getLevel(testProto));
		DiagLevel.setLevel(testProto, testTerse);
		assertEquals(TestValues.MATCH, testTerse, DiagLevel.getLevel(testProto));
		DiagLevel.setLevel(testProto, testOff);
		assertEquals(TestValues.MATCH, testOff, DiagLevel.getLevel(testProto));
		
		assertTrue(TestValues.TRUE, DiagLevel.isValidDetailLevel(testVerboseS));
		assertTrue(TestValues.TRUE, DiagLevel.isValidDetailLevel(testTerseS));
		assertTrue(TestValues.TRUE, DiagLevel.isValidDetailLevel(testOffS));
		
		assertEquals(TestValues.MATCH, testVerbose, DiagLevel.toDetailLevel(testVerboseS));
		assertEquals(TestValues.MATCH, testTerse, DiagLevel.toDetailLevel(testTerseS));
		assertEquals(TestValues.MATCH, testOff, DiagLevel.toDetailLevel(testOffS));
		assertEquals(TestValues.MATCH, testOff, DiagLevel.toDetailLevel(testInvalidS));
		
		try {
			// Invalid/Null Tests
			assertFalse(TestValues.FALSE, DiagLevel.isValidDetailLevel(testInvalidS));
			assertFalse(TestValues.FALSE, DiagLevel.isValidDetailLevel(null));
			assertNull(TestValues.NULL, DiagLevel.getLevel(null));
			DiagLevel.setLevel(null, null);
			DiagLevel.setAllLevels(null);
		} catch (NullPointerException e) {
			fail("NullPointerException was thrown when attempting to set null values.");
		}
	}
}