package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.SdlConnectionState;

public class SdlConnectionStateTests extends TestCase {
	/*
	public void testValidEnums () {	
		String example = "SDL_CONNECTED";
		SdlConnectionState enumConnected = SdlConnectionState.valueForString(example);
		example = "SDL_DISCONNECTED";
		SdlConnectionState enumDisconnected = SdlConnectionState.valueForString(example);
		
		assertNotNull("SDL_CONNECTED returned null", enumConnected);
		assertNotNull("SDL_DISCONNECTED returned null", enumDisconnected);
	}
	
	public void testInvalidEnum () {
		String example = "sDL_ConNEctED";
		try {
			SdlConnectionState.valueForString(example);
			fail("Sample string did not throw an IllegalArgumentException");
		}
		catch (IllegalArgumentException exception) {
			//If the method throws this exception then this test will be shown as passed.
		}
	}
	
	public void testNullEnum () {
		String example = null;
		try {
			SdlConnectionState.valueForString(example);
			fail("Sample string did not throw a NullPointerException");
		}
		catch (NullPointerException exception) {
			//If the method throws this exception then this test will be shown as passed.
		}
	}	
	*/
	public void testListEnum() {
 		List<SdlConnectionState> enumValueList = Arrays.asList(SdlConnectionState.values());

		List<SdlConnectionState> enumTestList = new ArrayList<SdlConnectionState>();
		enumTestList.add(SdlConnectionState.SDL_CONNECTED);
		enumTestList.add(SdlConnectionState.SDL_DISCONNECTED);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
	
}
