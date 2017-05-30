package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.ComponentVolumeStatus;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.ComponentVolumeStatus}
 */
public class ComponentVolumeStatusTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.unknown_caps);
		ComponentVolumeStatus enumUnknown = ComponentVolumeStatus.valueForString(example);
		example = mContext.getString(R.string.normal_caps);
		ComponentVolumeStatus enumNormal = ComponentVolumeStatus.valueForString(example);
		example = mContext.getString(R.string.low_caps);
		ComponentVolumeStatus enumLow = ComponentVolumeStatus.valueForString(example);
		example = mContext.getString(R.string.fault_caps);
		ComponentVolumeStatus enumFault = ComponentVolumeStatus.valueForString(example);
		example = mContext.getString(R.string.alert_caps);
		ComponentVolumeStatus enumAlert = ComponentVolumeStatus.valueForString(example);
		example = mContext.getString(R.string.not_supported_caps);
		ComponentVolumeStatus enumNotSupported = ComponentVolumeStatus.valueForString(example);
		
		assertNotNull("UNKNOWN returned null", enumUnknown);
		assertNotNull("NORMAL returned null", enumNormal);
		assertNotNull("LOW returned null", enumLow);
		assertNotNull("FAULT returned null", enumFault);
		assertNotNull("ALERT returned null", enumAlert);
		assertNotNull("NOT_SUPPORTED returned null", enumNotSupported);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    ComponentVolumeStatus temp = ComponentVolumeStatus.valueForString(example);
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
		    ComponentVolumeStatus temp = ComponentVolumeStatus.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}	

	/**
	 * Verifies the possible enum values of ComponentVolumeStatus.
	 */
	public void testListEnum() {
 		List<ComponentVolumeStatus> enumValueList = Arrays.asList(ComponentVolumeStatus.values());

		List<ComponentVolumeStatus> enumTestList = new ArrayList<ComponentVolumeStatus>();
		enumTestList.add(ComponentVolumeStatus.UNKNOWN);
		enumTestList.add(ComponentVolumeStatus.NORMAL);
		enumTestList.add(ComponentVolumeStatus.LOW);
		enumTestList.add(ComponentVolumeStatus.FAULT);
		enumTestList.add(ComponentVolumeStatus.ALERT);
		enumTestList.add(ComponentVolumeStatus.NOT_SUPPORTED);		

		assertTrue(mContext.getString(R.string.enum_value_list_does_not_match_enum_class_list),
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}