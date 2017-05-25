package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.RequestType;
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.RequestType}
 */
public class RequestTypeTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.http_caps);
		RequestType enumHttp = RequestType.valueForString(example);
		example = mContext.getString(R.string.file_resume_caps);
		RequestType enumFileResume = RequestType.valueForString(example);
		example = mContext.getString(R.string.auth_request_caps);
		RequestType enumAuthRequest = RequestType.valueForString(example);
		example = mContext.getString(R.string.auth_callenge_caps);
		RequestType enumAuthChallenge = RequestType.valueForString(example);
		example = mContext.getString(R.string.auth_ack_caps);
		RequestType enumAuthAck = RequestType.valueForString(example);
		example = mContext.getString(R.string.porprietary_caps);
		RequestType enumProprietary = RequestType.valueForString(example);
		
		example = mContext.getString(R.string.query_apps_caps);
		RequestType enumQueryApps = RequestType.valueForString(example);
		example = mContext.getString(R.string.launch_app_caps);
		RequestType enumLaunchApp = RequestType.valueForString(example);
		example = mContext.getString(R.string.lock_screen_icon_url_caps);
		RequestType enumLockScreen = RequestType.valueForString(example);
		example = mContext.getString(R.string.traffic_message_channel_caps);
		RequestType enumTrafficMessage = RequestType.valueForString(example);
		example = mContext.getString(R.string.driver_profile_caps);
		RequestType enumDriverProfile = RequestType.valueForString(example);
		example = mContext.getString(R.string.voicer_search_caps);
		RequestType enumVoiceSearch = RequestType.valueForString(example);
		example = mContext.getString(R.string.navigation_caps);
		RequestType enumNavigation = RequestType.valueForString(example);
		example = mContext.getString(R.string.phone_caps);
		RequestType enumPhone = RequestType.valueForString(example);
		example = mContext.getString(R.string.climate_caps);
		RequestType enumClimate = RequestType.valueForString(example);
		example = mContext.getString(R.string.settings_caps);
		RequestType enumSettings = RequestType.valueForString(example);
		example = mContext.getString(R.string.vehicle_diagostics_caps);
		RequestType enumDiagnostics = RequestType.valueForString(example);
		example = mContext.getString(R.string.emergency_caps);
		RequestType enumEmergency = RequestType.valueForString(example);
		example = mContext.getString(R.string.media_caps);
		RequestType enumMedia = RequestType.valueForString(example);
		example = mContext.getString(R.string.fota_caps);
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
		String example = mContext.getString(R.string.invalid_enum);
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