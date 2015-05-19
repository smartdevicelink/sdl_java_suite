package com.smartdevicelink.test.trace;

import junit.framework.TestCase;

import com.smartdevicelink.test.Test;
import com.smartdevicelink.trace.DiagLevel;
import com.smartdevicelink.trace.SdlTrace;
import com.smartdevicelink.trace.enums.DetailLevel;
import com.smartdevicelink.trace.enums.Mod;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.trace.SdlTrace}
 */
public class SdlTraceTests extends TestCase {
	
	/**
	 * This is a unit test for the following methods : 
	 * {@link com.smartdevicelink.trace.SdlTrace#setAcceptAPITraceAdjustments(Boolean)}
	 * {@link com.smartdevicelink.trace.SdlTrace#getAcceptAPITraceAdjustments()}
	 */
	public void testAdjustmentFlags () {
		
		SdlTrace.setAcceptAPITraceAdjustments(true);
		assertTrue(Test.TRUE, SdlTrace.getAcceptAPITraceAdjustments());
		
		SdlTrace.setAcceptAPITraceAdjustments(false);
		assertFalse(Test.FALSE, SdlTrace.getAcceptAPITraceAdjustments());
		
		// This should not change the value from the previous setting.
		SdlTrace.setAcceptAPITraceAdjustments(null);
		assertFalse(Test.FALSE, SdlTrace.getAcceptAPITraceAdjustments());
	}
	
	/**
	 * This is a unit test for the following methods : 
	 * {@link com.smartdevicelink.trace.SdlTrace#setAppTraceLevel(DetailLevel)}
	 * {@link com.smartdevicelink.trace.SdlTrace#setProxyTraceLevel(DetailLevel)}
	 * {@link com.smartdevicelink.trace.SdlTrace#setRpcTraceLevel(DetailLevel)}
	 * {@link com.smartdevicelink.trace.SdlTrace#setMarshallingTraceLevel(DetailLevel)}
	 * {@link com.smartdevicelink.trace.SdlTrace#setProtocolTraceLevel(DetailLevel)}
	 * {@link com.smartdevicelink.trace.SdlTrace#setTransportTraceLevel(DetailLevel)}
	 */
	public void testTraceLevelFlags () {
		
		SdlTrace.setAcceptAPITraceAdjustments(true);
		
		// App Trace Level - - - - - - - - - - - - - - - - - - - - - - - - - - -
		SdlTrace.setAppTraceLevel(DetailLevel.VERBOSE);
		assertEquals(Test.MATCH, DetailLevel.VERBOSE, DiagLevel.getLevel(Mod.app));		
		SdlTrace.setAppTraceLevel(DetailLevel.TERSE);
		assertEquals(Test.MATCH, DetailLevel.TERSE, DiagLevel.getLevel(Mod.app));
		SdlTrace.setAppTraceLevel(DetailLevel.OFF);
		assertEquals(Test.MATCH, DetailLevel.OFF, DiagLevel.getLevel(Mod.app));
		
		// This should not change the value from the previous setting.
		SdlTrace.setAppTraceLevel(null);
		assertEquals(Test.MATCH, DetailLevel.OFF, DiagLevel.getLevel(Mod.app));
		
		// Proxy Trace Level - - - - - - - - - - - - - - - - - - - - - - - - - -
		SdlTrace.setProxyTraceLevel(DetailLevel.VERBOSE);
		assertEquals(Test.MATCH, DetailLevel.VERBOSE, DiagLevel.getLevel(Mod.proxy));
		SdlTrace.setProxyTraceLevel(DetailLevel.TERSE);
		assertEquals(Test.MATCH, DetailLevel.TERSE, DiagLevel.getLevel(Mod.proxy));
		SdlTrace.setProxyTraceLevel(DetailLevel.OFF);
		assertEquals(Test.MATCH, DetailLevel.OFF, DiagLevel.getLevel(Mod.proxy));
		
		// This should not change the value from the previous setting.
		SdlTrace.setProxyTraceLevel(null);
		assertEquals(Test.MATCH, DetailLevel.OFF, DiagLevel.getLevel(Mod.proxy));
		
		// Rpc Trace Level - - - - - - - - - - - - - - - - - - - - - - - - - - -
		SdlTrace.setRpcTraceLevel(DetailLevel.VERBOSE);
		assertEquals(Test.MATCH, DetailLevel.VERBOSE, DiagLevel.getLevel(Mod.rpc));
		SdlTrace.setRpcTraceLevel(DetailLevel.TERSE);
		assertEquals(Test.MATCH, DetailLevel.TERSE, DiagLevel.getLevel(Mod.rpc));
		SdlTrace.setRpcTraceLevel(DetailLevel.OFF);
		assertEquals(Test.MATCH, DetailLevel.OFF, DiagLevel.getLevel(Mod.rpc));
		
		// This should not change the value from the previous setting.
		SdlTrace.setRpcTraceLevel(null);
		assertEquals(Test.MATCH, DetailLevel.OFF, DiagLevel.getLevel(Mod.rpc));
		
		// Marshalling Trace Level - - - - - - - - - - - - - - - - - - - - - - -
		SdlTrace.setMarshallingTraceLevel(DetailLevel.VERBOSE);
		assertEquals(Test.MATCH, DetailLevel.VERBOSE, DiagLevel.getLevel(Mod.mar));
		SdlTrace.setMarshallingTraceLevel(DetailLevel.TERSE);
		assertEquals(Test.MATCH, DetailLevel.TERSE, DiagLevel.getLevel(Mod.mar));
		SdlTrace.setMarshallingTraceLevel(DetailLevel.OFF);
		assertEquals(Test.MATCH, DetailLevel.OFF, DiagLevel.getLevel(Mod.mar));
		
		// This should not change the value from the previous setting.
		SdlTrace.setMarshallingTraceLevel(null);
		assertEquals(Test.MATCH, DetailLevel.OFF, DiagLevel.getLevel(Mod.mar));
		
		// Protocol Trace Level - - - - - - - - - - - - - - - - - - - - - - - -
		SdlTrace.setProtocolTraceLevel(DetailLevel.VERBOSE);
		assertEquals(Test.MATCH, DetailLevel.VERBOSE, DiagLevel.getLevel(Mod.proto));
		SdlTrace.setProtocolTraceLevel(DetailLevel.TERSE);
		assertEquals(Test.MATCH, DetailLevel.TERSE, DiagLevel.getLevel(Mod.proto));
		SdlTrace.setProtocolTraceLevel(DetailLevel.OFF);
		assertEquals(Test.MATCH, DetailLevel.OFF, DiagLevel.getLevel(Mod.proto));
		
		// This should not change the value from the previous setting.
		SdlTrace.setProtocolTraceLevel(null);
		assertEquals(Test.MATCH, DetailLevel.OFF, DiagLevel.getLevel(Mod.proto));
		
		// Transport Trace Level - - - - - - - - - - - - - - - - - - - - - - - -
		SdlTrace.setTransportTraceLevel(DetailLevel.VERBOSE);
		assertEquals(Test.MATCH, DetailLevel.VERBOSE, DiagLevel.getLevel(Mod.tran));
		SdlTrace.setTransportTraceLevel(DetailLevel.TERSE);
		assertEquals(Test.MATCH, DetailLevel.TERSE, DiagLevel.getLevel(Mod.tran));
		SdlTrace.setTransportTraceLevel(DetailLevel.OFF);
		assertEquals(Test.MATCH, DetailLevel.OFF, DiagLevel.getLevel(Mod.tran));
		
		// This should not change the value from the previous setting.
		SdlTrace.setTransportTraceLevel(null);
		assertEquals(Test.MATCH, DetailLevel.OFF, DiagLevel.getLevel(Mod.tran));
	}
	
	// NOTE : No testing can currently be done for the logging methods.
		
	// NOTE : Cannot test for Bluetooth connected information currently.
}