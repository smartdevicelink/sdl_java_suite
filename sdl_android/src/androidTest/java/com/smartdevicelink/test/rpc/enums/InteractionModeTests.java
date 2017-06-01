package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.InteractionMode;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.InteractionMode}
 */
public class InteractionModeTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.manual_only_caps);
		InteractionMode enumManualOnly = InteractionMode.valueForString(example);
		example = mContext.getString(R.string.vr_only_caps);
		InteractionMode enumVrOnly = InteractionMode.valueForString(example);
		example = mContext.getString(R.string.both_caps);
		InteractionMode enumBoth = InteractionMode.valueForString(example);
		
		assertNotNull("MANUAL_ONLY returned null", enumManualOnly);
		assertNotNull("VR_ONLY returned null", enumVrOnly);
		assertNotNull("BOTH returned null", enumBoth);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    InteractionMode temp = InteractionMode.valueForString(example);
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
		    InteractionMode temp = InteractionMode.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}	

	/**
	 * Verifies the possible enum values of InteractionMode.
	 */
	public void testListEnum() {
 		List<InteractionMode> enumValueList = Arrays.asList(InteractionMode.values());

		List<InteractionMode> enumTestList = new ArrayList<InteractionMode>();
		enumTestList.add(InteractionMode.MANUAL_ONLY);
		enumTestList.add(InteractionMode.VR_ONLY);
		enumTestList.add(InteractionMode.BOTH);

		assertTrue(mContext.getString(R.string.enum_value_list_does_not_match_enum_class_list),
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}