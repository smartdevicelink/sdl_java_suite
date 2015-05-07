package com.smartdevicelink.test.utils;

import junit.framework.TestCase;

import com.smartdevicelink.util.DebugTool;

public class DebugToolTests extends TestCase {
	
	// Cannot test logging methods
	
	public void testToolMethods () {
		
//		MockConsole mc1 = new MockConsole();
//		MockConsole mc2 = new MockConsole();
//		MockConsole mc3 = new MockConsole();
		
		// Test -- enableDebugTool()
		DebugTool.enableDebugTool();
		assertTrue("Value should be true.", DebugTool.isDebugEnabled());
		
		// Test -- disableDebugTool()
		DebugTool.disableDebugTool();
		assertFalse("Value should be false.", DebugTool.isDebugEnabled());
		
// Can't test protected static field --
//		// -> Uses reflection <-
//		try {
//			
//			// Test console methods
//			DebugTool.addConsole(mc1);
//			DebugTool.addConsole(mc2);
//			DebugTool.addConsole(mc3);
//			
//			Vector<IConsole> testList = new Vector<IConsole>();
//			testList.add(mc1);
//			testList.add(mc2);
//			testList.add(mc3);
//			
//			// >>>>> HERE
//			Field field = (DebugTool.class).getDeclaredField("isTransportEnabled");
//			// <<<<<
//			
//			assertEquals("Values should match.", testList, field);
//			
//			DebugTool.removeConsole(mc3);
//			testList.removeElement(mc3);
//			
//			// Need to update field-get here?
//			assertEquals("Values should match.", testList, field);
//			
//			DebugTool.clearConsoles();
//			testList = new Vector<IConsole>();
//			
//			assertEquals("Values should match.", testList, field);
//			
//		} catch (NoSuchFieldException e) {
//			fail("Could not test private field, doesn't exist?");
//		}
	}
}

//class MockConsole implements IConsole {
//
//	public MockConsole (String n) {}
//	@Override public void logInfo(String msg) { }
//	@Override public void logError(String msg) { }
//	@Override public void logError(String msg, Throwable ex) { }
//	@Override public void logRPCSend(String rpcMsg) { }
//	@Override public void logRPCReceive(String rpcMsg) { }
//	
//}