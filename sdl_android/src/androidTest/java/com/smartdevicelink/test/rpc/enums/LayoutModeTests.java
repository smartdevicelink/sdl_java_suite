package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.LayoutMode;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.LayoutMode}
 */
public class LayoutModeTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.icon_only_caps);
		LayoutMode enumIconOnly = LayoutMode.valueForString(example);
		example = mContext.getString(R.string.icon_with_search_caps);
		LayoutMode enumIconWithSearch = LayoutMode.valueForString(example);
		example = mContext.getString(R.string.lists_only_caps);
		LayoutMode enumListOnly = LayoutMode.valueForString(example);
		example = mContext.getString(R.string.list_w_search_caps);
		LayoutMode enumListWithSearch = LayoutMode.valueForString(example);
		example = mContext.getString(R.string.keyboard_caps);
		LayoutMode enumKeyboard = LayoutMode.valueForString(example);
		
		assertNotNull("ICON_ONLY returned null", enumIconOnly);
		assertNotNull("ICON_WITH_SEARCH returned null", enumIconWithSearch);
		assertNotNull("LIST_ONLY returned null", enumListOnly);
		assertNotNull("LIST_WITH_SEARCH returned null", enumListWithSearch);
		assertNotNull("KEYBOARD returned null", enumKeyboard);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    LayoutMode temp = LayoutMode.valueForString(example);
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
		    LayoutMode temp = LayoutMode.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}	
	

	/**
	 * Verifies the possible enum values of LayoutMode.
	 */
	public void testListEnum() {
 		List<LayoutMode> enumValueList = Arrays.asList(LayoutMode.values());

		List<LayoutMode> enumTestList = new ArrayList<LayoutMode>();
		enumTestList.add(LayoutMode.ICON_ONLY);
		enumTestList.add(LayoutMode.ICON_WITH_SEARCH);
		enumTestList.add(LayoutMode.LIST_ONLY);
		enumTestList.add(LayoutMode.LIST_WITH_SEARCH);
		enumTestList.add(LayoutMode.KEYBOARD);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}