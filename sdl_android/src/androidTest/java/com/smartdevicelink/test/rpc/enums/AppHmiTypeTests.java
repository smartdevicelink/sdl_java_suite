package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.AppHMIType;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.AppHmiType}
 */
public class AppHmiTypeTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = "DEFAULT";
		AppHMIType enumDefault = AppHMIType.valueForString(example);
		example = "COMMUNICATION";
		AppHMIType enumCommunication = AppHMIType.valueForString(example);
		example = "MEDIA";
		AppHMIType enumMedia = AppHMIType.valueForString(example);
		example = "MESSAGING";
		AppHMIType enumMessaging = AppHMIType.valueForString(example);
		example = "NAVIGATION";
		AppHMIType enumNavigation = AppHMIType.valueForString(example);
		example = "INFORMATION";
		AppHMIType enumInformation = AppHMIType.valueForString(example);
		example = "SOCIAL";
		AppHMIType enumSocial = AppHMIType.valueForString(example);
		example = "BACKGROUND_PROCESS";
		AppHMIType enumBackgroundProcess = AppHMIType.valueForString(example);
		example = "TESTING";
		AppHMIType enumTesting = AppHMIType.valueForString(example);
		example = "SYSTEM";
		AppHMIType enumSystem = AppHMIType.valueForString(example);
		
		assertNotNull("DEFAULT returned null", enumDefault);
		assertNotNull("COMMUNICATION returned null", enumCommunication);
		assertNotNull("MEDIA returned null", enumMedia);
		assertNotNull("MESSAGING returned null", enumMessaging);
		assertNotNull("NAVIGATION returned null", enumNavigation);
		assertNotNull("INFORMATION returned null", enumInformation);
		assertNotNull("SOCIAL returned null", enumSocial);
		assertNotNull("BACKGROUND_PROCESS returned null", enumBackgroundProcess);
		assertNotNull("TESTING returned null", enumTesting);
		assertNotNull("SYSTEM returned null", enumSystem);
	}
	
	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "deFaUlt";
		try {
		    AppHMIType temp = AppHMIType.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (IllegalArgumentException exception) {
            fail("Invalid enum throws IllegalArgumentException.");
		}
	}
	
	/**
	 * Verifies that a null assignment is invalid.
	 */
	public void testNullEnum () {
		String example = null;
		try {
		    AppHMIType temp = AppHMIType.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}	
	
	/**
	 * Verifies the possible enum values of AmbientLightStatus.
	 */
	public void testListEnum() {
 		List<AppHMIType> enumValueList = Arrays.asList(AppHMIType.values());
 		
		List<AppHMIType> enumTestList = new ArrayList<AppHMIType>();
		enumTestList.add(AppHMIType.DEFAULT);
		enumTestList.add(AppHMIType.COMMUNICATION);
		enumTestList.add(AppHMIType.MEDIA);
		enumTestList.add(AppHMIType.MESSAGING);
		enumTestList.add(AppHMIType.NAVIGATION);
		enumTestList.add(AppHMIType.INFORMATION);		
		enumTestList.add(AppHMIType.SOCIAL);
		enumTestList.add(AppHMIType.BACKGROUND_PROCESS);	
		enumTestList.add(AppHMIType.TESTING);
		enumTestList.add(AppHMIType.SYSTEM);	

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
}