package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.AppHMIType;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.AppHmiType}
 */
public class AppHmiTypeTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.default_caps);
		AppHMIType enumDefault = AppHMIType.valueForString(example);
		example = mContext.getString(R.string.communication_caps);
		AppHMIType enumCommunication = AppHMIType.valueForString(example);
		example = mContext.getString(R.string.media_caps);
		AppHMIType enumMedia = AppHMIType.valueForString(example);
		example = mContext.getString(R.string.messaging_caps);
		AppHMIType enumMessaging = AppHMIType.valueForString(example);
		example = mContext.getString(R.string.navigation_caps);
		AppHMIType enumNavigation = AppHMIType.valueForString(example);
		example = mContext.getString(R.string.information_caps);
		AppHMIType enumInformation = AppHMIType.valueForString(example);
		example = mContext.getString(R.string.social_caps);
		AppHMIType enumSocial = AppHMIType.valueForString(example);
		example = mContext.getString(R.string.background_process_caps);
		AppHMIType enumBackgroundProcess = AppHMIType.valueForString(example);
		example = mContext.getString(R.string.testing_caps);
		AppHMIType enumTesting = AppHMIType.valueForString(example);
		example = mContext.getString(R.string.system_caps);
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
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    AppHMIType temp = AppHMIType.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (IllegalArgumentException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}
	
	/**
	 * Verifies that a null assignment is invalid.
	 */
	public void testNullEnum () {
		String example = null;
		try {
		    AppHMIType temp = AppHMIType.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
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

		assertTrue(mContext.getString(R.string.enum_value_list_does_not_match_enum_class_list),
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
}