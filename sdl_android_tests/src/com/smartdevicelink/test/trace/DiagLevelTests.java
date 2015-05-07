package com.smartdevicelink.test.trace;

import com.smartdevicelink.trace.DiagLevel;
import com.smartdevicelink.trace.enums.DetailLevel;
import com.smartdevicelink.trace.enums.Mod;

import junit.framework.TestCase;

public class DiagLevelTests extends TestCase {
	
	private final String MSG = "Value did not match expected value.";
	
	public void testMethods () {
		
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
	
		// Test -- setAllLevels (DetailLevel thisDetail) 
		DiagLevel.setAllLevels(testVerbose);
		assertEquals(MSG, testVerbose, DiagLevel.getLevel(testProto));
		assertEquals(MSG, testVerbose, DiagLevel.getLevel(testProxy));
		assertEquals(MSG, testVerbose, DiagLevel.getLevel(testTran));
		assertEquals(MSG, testVerbose, DiagLevel.getLevel(testApp));
		assertEquals(MSG, testVerbose, DiagLevel.getLevel(testRpc));
		assertEquals(MSG, testVerbose, DiagLevel.getLevel(testMar));
				
		DiagLevel.setAllLevels(testTerse);
		assertEquals(MSG, testTerse, DiagLevel.getLevel(testProto));
		assertEquals(MSG, testTerse, DiagLevel.getLevel(testProxy));
		assertEquals(MSG, testTerse, DiagLevel.getLevel(testTran));
		assertEquals(MSG, testTerse, DiagLevel.getLevel(testApp));
		assertEquals(MSG, testTerse, DiagLevel.getLevel(testRpc));
		assertEquals(MSG, testTerse, DiagLevel.getLevel(testMar));
		
		DiagLevel.setAllLevels(testOff);
		assertEquals(MSG, testOff, DiagLevel.getLevel(testProto));
		assertEquals(MSG, testOff, DiagLevel.getLevel(testProxy));
		assertEquals(MSG, testOff, DiagLevel.getLevel(testTran));
		assertEquals(MSG, testOff, DiagLevel.getLevel(testApp));
		assertEquals(MSG, testOff, DiagLevel.getLevel(testRpc));
		assertEquals(MSG, testOff, DiagLevel.getLevel(testMar));
		
		// Test -- setLevel (Mod thisMod, DetailLevel thisDetail)
		DiagLevel.setLevel(testMar, testVerbose);
		assertEquals(MSG, testVerbose, DiagLevel.getLevel(testMar));		
		DiagLevel.setLevel(testMar, testTerse);
		assertEquals(MSG, testTerse, DiagLevel.getLevel(testMar));		
		DiagLevel.setLevel(testMar, testOff);
		assertEquals(MSG, testOff, DiagLevel.getLevel(testMar));
		
		DiagLevel.setLevel(testRpc, testVerbose);
		assertEquals(MSG, testVerbose, DiagLevel.getLevel(testRpc));	
		DiagLevel.setLevel(testRpc, testTerse);
		assertEquals(MSG, testTerse, DiagLevel.getLevel(testRpc));			
		DiagLevel.setLevel(testRpc, testOff);
		assertEquals(MSG, testOff, DiagLevel.getLevel(testRpc));	
		
		DiagLevel.setLevel(testApp, testVerbose);
		assertEquals(MSG, testVerbose, DiagLevel.getLevel(testApp));
		DiagLevel.setLevel(testApp, testTerse);
		assertEquals(MSG, testTerse, DiagLevel.getLevel(testApp));
		DiagLevel.setLevel(testApp, testOff);
		assertEquals(MSG, testOff, DiagLevel.getLevel(testApp));
		
		DiagLevel.setLevel(testTran, testVerbose);
		assertEquals(MSG, testVerbose, DiagLevel.getLevel(testTran));	
		DiagLevel.setLevel(testTran, testTerse);
		assertEquals(MSG, testTerse, DiagLevel.getLevel(testTran));		
		DiagLevel.setLevel(testTran, testOff);
		assertEquals(MSG, testOff, DiagLevel.getLevel(testTran));	
		
		DiagLevel.setLevel(testProxy, testVerbose);
		assertEquals(MSG, testVerbose, DiagLevel.getLevel(testProxy));			
		DiagLevel.setLevel(testProxy, testTerse);
		assertEquals(MSG, testTerse, DiagLevel.getLevel(testProxy));		
		DiagLevel.setLevel(testProxy, testOff);
		assertEquals(MSG, testOff, DiagLevel.getLevel(testProxy));
		
		DiagLevel.setLevel(testProto, testVerbose);
		assertEquals(MSG, testVerbose, DiagLevel.getLevel(testProto));
		DiagLevel.setLevel(testProto, testTerse);
		assertEquals(MSG, testTerse, DiagLevel.getLevel(testProto));
		DiagLevel.setLevel(testProto, testOff);
		assertEquals(MSG, testOff, DiagLevel.getLevel(testProto));
		
		// Test -- isValidDetailLevel (String dtString)		
		assertTrue(MSG, DiagLevel.isValidDetailLevel(testVerboseS));		
		assertTrue(MSG, DiagLevel.isValidDetailLevel(testTerseS));		
		assertTrue(MSG, DiagLevel.isValidDetailLevel(testOffS));		
		assertFalse(MSG, DiagLevel.isValidDetailLevel(testInvalidS));
				
		// Test -- toDetailLevel (String dtString)		
		assertEquals(MSG, testVerbose, DiagLevel.toDetailLevel(testVerboseS));
		assertEquals(MSG, testTerse, DiagLevel.toDetailLevel(testTerseS));
		assertEquals(MSG, testOff, DiagLevel.toDetailLevel(testOffS));
		assertEquals(MSG, testOff, DiagLevel.toDetailLevel(testInvalidS));
		// Note : assumes 'off' as default for invalid data
	}
}