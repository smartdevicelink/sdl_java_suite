package com.smartdevicelink.test.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.SdlInterfaceAvailability;

public class SdlInterfaceAvailabilityTests extends TestCase {
	/*
	public void testValidEnums () {	
		String example = "SDL_INTERFACE_AVAILABLE";
		SdlInterfaceAvailability enumSdlInterfaceAvailable = SdlInterfaceAvailability.valueForString(example);
		example = "SDL_INTERFACE_UNAVAILABLE";
		SdlInterfaceAvailability enumSdlInterfaceUnavailable = SdlInterfaceAvailability.valueForString(example);
		
		assertNotNull("SDL_INTERFACE_AVAILABLE returned null", enumSdlInterfaceAvailable);
		assertNotNull("SDL_INTERFACE_UNAVAILABLE returned null", enumSdlInterfaceUnavailable);
	}
	
	public void testInvalidEnum () {
		String example = "sdL_inTERFacE_AvaIlAble";
		try {
			SdlInterfaceAvailability.valueForString(example);
			fail("Sample string did not throw an IllegalArgumentException");
		}
		catch (IllegalArgumentException exception) {
			//If the method throws this exception then this test will be shown as passed.
		}
	}
	
	public void testNullEnum () {
		String example = null;
		try {
			SdlInterfaceAvailability.valueForString(example);
			fail("Sample string did not throw a NullPointerException");
		}
		catch (NullPointerException exception) {
			//If the method throws this exception then this test will be shown as passed.
		}
	}	
	*/
	public void testListEnum() {
 		List<SdlInterfaceAvailability> enumValueList = Arrays.asList(SdlInterfaceAvailability.values());

		List<SdlInterfaceAvailability> enumTestList = new ArrayList<SdlInterfaceAvailability>();
		enumTestList.add(SdlInterfaceAvailability.SDL_INTERFACE_AVAILABLE);
		enumTestList.add(SdlInterfaceAvailability.SDL_INTERFACE_UNAVAILABLE);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
	
}
