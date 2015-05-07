package com.smartdevicelink.test.trace;

import junit.framework.TestCase;

import com.smartdevicelink.trace.DiagLevel;
import com.smartdevicelink.trace.SdlTrace;
import com.smartdevicelink.trace.enums.DetailLevel;
import com.smartdevicelink.trace.enums.Mod;

public class SdlTraceTests extends TestCase {
	
	private final String MSG = "Value did not match expected value.";
	
	public void testMethods () {
		
//		long testTimestamp = 1234567890;
//		Mod testMod = Mod.app;
//		InterfaceActivityDirection testIAD = InterfaceActivityDirection.None;
//		String testStr = "test";
		
//		StringBuilder sb = new StringBuilder();
//		sb.append("<msg><dms>1234567890</dms><pid>");
//		sb.append(Process.myPid());
//		sb.append("</pid><tid>");
//		sb.append(Thread.currentThread().getId());
//		sb.append("</tid><mod>app</mod>test</msg>");
		
		// Test -- set/get AcceptAPITraceAdjustments
		SdlTrace.setAcceptAPITraceAdjustments(false);
		assertFalse(MSG, SdlTrace.getAcceptAPITraceAdjustments());
		SdlTrace.setAcceptAPITraceAdjustments(null);
		assertFalse(MSG, SdlTrace.getAcceptAPITraceAdjustments());
		SdlTrace.setAcceptAPITraceAdjustments(true);
		assertTrue(MSG, SdlTrace.getAcceptAPITraceAdjustments());
				
		// Cannot test values for setTracingEnable (Boolean enable)
		
		// Test -- DaigLevel methods
		SdlTrace.setAppTraceLevel(DetailLevel.VERBOSE);
		assertEquals(MSG, DetailLevel.VERBOSE, DiagLevel.getLevel(Mod.app));		
		SdlTrace.setAppTraceLevel(DetailLevel.TERSE);
		assertEquals(MSG, DetailLevel.TERSE, DiagLevel.getLevel(Mod.app));
		SdlTrace.setAppTraceLevel(DetailLevel.OFF);
		assertEquals(MSG, DetailLevel.OFF, DiagLevel.getLevel(Mod.app));
		
		SdlTrace.setProxyTraceLevel(DetailLevel.VERBOSE);
		assertEquals(MSG, DetailLevel.VERBOSE, DiagLevel.getLevel(Mod.proxy));
		SdlTrace.setProxyTraceLevel(DetailLevel.TERSE);
		assertEquals(MSG, DetailLevel.TERSE, DiagLevel.getLevel(Mod.proxy));
		SdlTrace.setProxyTraceLevel(DetailLevel.OFF);
		assertEquals(MSG, DetailLevel.OFF, DiagLevel.getLevel(Mod.proxy));
		
		SdlTrace.setRpcTraceLevel(DetailLevel.VERBOSE);
		assertEquals(MSG, DetailLevel.VERBOSE, DiagLevel.getLevel(Mod.rpc));
		SdlTrace.setRpcTraceLevel(DetailLevel.TERSE);
		assertEquals(MSG, DetailLevel.TERSE, DiagLevel.getLevel(Mod.rpc));
		SdlTrace.setRpcTraceLevel(DetailLevel.OFF);
		assertEquals(MSG, DetailLevel.OFF, DiagLevel.getLevel(Mod.rpc));
		
		SdlTrace.setMarshallingTraceLevel(DetailLevel.VERBOSE);
		assertEquals(MSG, DetailLevel.VERBOSE, DiagLevel.getLevel(Mod.mar));
		SdlTrace.setMarshallingTraceLevel(DetailLevel.TERSE);
		assertEquals(MSG, DetailLevel.TERSE, DiagLevel.getLevel(Mod.mar));
		SdlTrace.setMarshallingTraceLevel(DetailLevel.OFF);
		assertEquals(MSG, DetailLevel.OFF, DiagLevel.getLevel(Mod.mar));
		
		SdlTrace.setProtocolTraceLevel(DetailLevel.VERBOSE);
		assertEquals(MSG, DetailLevel.VERBOSE, DiagLevel.getLevel(Mod.proto));
		SdlTrace.setProtocolTraceLevel(DetailLevel.TERSE);
		assertEquals(MSG, DetailLevel.TERSE, DiagLevel.getLevel(Mod.proto));
		SdlTrace.setProtocolTraceLevel(DetailLevel.OFF);
		assertEquals(MSG, DetailLevel.OFF, DiagLevel.getLevel(Mod.proto));
		
		SdlTrace.setTransportTraceLevel(DetailLevel.VERBOSE);
		assertEquals(MSG, DetailLevel.VERBOSE, DiagLevel.getLevel(Mod.tran));
		SdlTrace.setTransportTraceLevel(DetailLevel.TERSE);
		assertEquals(MSG, DetailLevel.TERSE, DiagLevel.getLevel(Mod.tran));
		SdlTrace.setTransportTraceLevel(DetailLevel.OFF);
		assertEquals(MSG, DetailLevel.OFF, DiagLevel.getLevel(Mod.tran));
		
		// Cannot use reflection - java test package issues?
		// Test -- encodeTraceMessage (long timestamp, Mod module, InterfaceActivityDirection msgDirection, String msgBodyXml)
		// This is using "reflection" to test the private method
//		try {
//			Method method = (SdlTrace.class).getDeclaredMethod("encodeTraceMessage", new Class[] { long.class, Mod.class, InterfaceActivityDirection.class, String.class });
//			method.setAccessible(true);
//			String actual = (String) method.invoke(new SdlTrace(), new Object[] { testTimestamp, testMod, testIAD, testStr });
//			assertEquals(MSG, sb.toString(), actual);
//		} catch (Exception e) {
//			fail("Could not test private method \"encodeTraceMessage\".");
//		}
		
		// Note: cannot test log info -- at least not beyond 'do not throw exception when run'
		// Note: no need to test deprecated method		
	}
}