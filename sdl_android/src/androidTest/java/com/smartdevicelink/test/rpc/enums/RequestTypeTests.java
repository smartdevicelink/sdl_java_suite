package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.RequestType;
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.RequestType}
 */
public class RequestTypeTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = "HTTP";
		RequestType enumHttp = RequestType.valueForString(example);
		example = "FILE_RESUME";
		RequestType enumFileResume = RequestType.valueForString(example);
		example = "AUTH_REQUEST";
		RequestType enumAuthRequest = RequestType.valueForString(example);
		example = "AUTH_CHALLENGE";
		RequestType enumAuthChallenge = RequestType.valueForString(example);
		example = "AUTH_ACK";
		RequestType enumAuthAck = RequestType.valueForString(example);
		example = "PROPRIETARY";
		RequestType enumProprietary = RequestType.valueForString(example);
		
		example = "QUERY_APPS";
		RequestType enumQueryApps = RequestType.valueForString(example);
		example = "LAUNCH_APP";
		RequestType enumLaunchApp = RequestType.valueForString(example);
		example = "LOCK_SCREEN_ICON_URL";
		RequestType enumLockScreen = RequestType.valueForString(example);
		example = "TRAFFIC_MESSAGE_CHANNEL";
		RequestType enumTrafficMessage = RequestType.valueForString(example);
		example = "DRIVER_PROFILE";
		RequestType enumDriverProfile = RequestType.valueForString(example);
		example = "VOICE_SEARCH";
		RequestType enumVoiceSearch = RequestType.valueForString(example);
		example = "NAVIGATION";
		RequestType enumNavigation = RequestType.valueForString(example);
		example = "PHONE";
		RequestType enumPhone = RequestType.valueForString(example);
		example = "CLIMATE";
		RequestType enumClimate = RequestType.valueForString(example);
		example = "SETTINGS";
		RequestType enumSettings = RequestType.valueForString(example);
		example = "VEHICLE_DIAGNOSTICS";
		RequestType enumDiagnostics = RequestType.valueForString(example);
		example = "EMERGENCY";
		RequestType enumEmergency = RequestType.valueForString(example);
		example = "MEDIA";
		RequestType enumMedia = RequestType.valueForString(example);
		example = "FOTA";
		RequestType enumFota = RequestType.valueForString(example);
		
		assertNotNull("HTTP returned null", enumHttp);
		assertNotNull("FILE_RESUME returned null", enumFileResume);
		assertNotNull("AUTH_REQUEST returned null", enumAuthRequest);
		assertNotNull("AUTH_CHALLENGE returned null", enumAuthChallenge);
		assertNotNull("AUTH_ACK returned null", enumAuthAck);
		assertNotNull("PROPRIETARY returned null", enumProprietary);
		
		assertNotNull(Test.NOT_NULL, enumQueryApps);
		assertNotNull(Test.NOT_NULL, enumLaunchApp);
		assertNotNull(Test.NOT_NULL, enumLockScreen);
		assertNotNull(Test.NOT_NULL, enumTrafficMessage);
		assertNotNull(Test.NOT_NULL, enumDriverProfile);
		assertNotNull(Test.NOT_NULL, enumVoiceSearch);
		assertNotNull(Test.NOT_NULL, enumNavigation);
		assertNotNull(Test.NOT_NULL, enumPhone);
		assertNotNull(Test.NOT_NULL, enumClimate);
		assertNotNull(Test.NOT_NULL, enumSettings);
		assertNotNull(Test.NOT_NULL, enumDiagnostics);
		assertNotNull(Test.NOT_NULL, enumEmergency);
		assertNotNull(Test.NOT_NULL, enumMedia);
		assertNotNull(Test.NOT_NULL, enumFota);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "hTTp";
		try {
		    RequestType temp = RequestType.valueForString(example);
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
		    RequestType temp = RequestType.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}	

	/**
	 * Verifies the possible enum values of RequestType.
	 */
	public void testListEnum() {
 		List<RequestType> enumValueList = Arrays.asList(RequestType.values());

		List<RequestType> enumTestList = new ArrayList<RequestType>();
		enumTestList.add(RequestType.HTTP);
		enumTestList.add(RequestType.FILE_RESUME);
		enumTestList.add(RequestType.AUTH_REQUEST);
		enumTestList.add(RequestType.AUTH_CHALLENGE);
		enumTestList.add(RequestType.AUTH_ACK);
		enumTestList.add(RequestType.PROPRIETARY);	
		
		enumTestList.add(RequestType.QUERY_APPS);	
		enumTestList.add(RequestType.LAUNCH_APP);	
		enumTestList.add(RequestType.LOCK_SCREEN_ICON_URL);	
		enumTestList.add(RequestType.TRAFFIC_MESSAGE_CHANNEL);	
		enumTestList.add(RequestType.DRIVER_PROFILE);	
		enumTestList.add(RequestType.VOICE_SEARCH);	
		enumTestList.add(RequestType.NAVIGATION);	
		enumTestList.add(RequestType.PHONE);	
		enumTestList.add(RequestType.CLIMATE);	
		enumTestList.add(RequestType.SETTINGS);	
		enumTestList.add(RequestType.VEHICLE_DIAGNOSTICS);	
		enumTestList.add(RequestType.EMERGENCY);	
		enumTestList.add(RequestType.MEDIA);	
		enumTestList.add(RequestType.FOTA);	

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}