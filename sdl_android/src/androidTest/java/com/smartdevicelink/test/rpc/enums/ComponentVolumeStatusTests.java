package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.ComponentVolumeStatus;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

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
		    ComponentVolumeStatus temp = ComponentVolumeStatus.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
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

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}