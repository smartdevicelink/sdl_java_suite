package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.SystemAction;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.SystemAction}
 */
public class SystemActionTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.default_action_caps);
		SystemAction enumDefaultAction = SystemAction.valueForString(example);
		example = mContext.getString(R.string.steal_focus_caps);
		SystemAction enumStealFocus = SystemAction.valueForString(example);
		example = mContext.getString(R.string.keep_context_caps);
		SystemAction enumKeepContext = SystemAction.valueForString(example);
		
		assertNotNull("DEFAULT_ACTION returned null", enumDefaultAction);
		assertNotNull("STEAL_FOCUS returned null", enumStealFocus);
		assertNotNull("KEEP_CONTEXT returned null", enumKeepContext);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    SystemAction temp = SystemAction.valueForString(example);
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
		    SystemAction temp = SystemAction.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}	

	/**
	 * Verifies the possible enum values of SystemAction.
	 */
	public void testListEnum() {
 		List<SystemAction> enumValueList = Arrays.asList(SystemAction.values());

		List<SystemAction> enumTestList = new ArrayList<SystemAction>();
		enumTestList.add(SystemAction.DEFAULT_ACTION);
		enumTestList.add(SystemAction.STEAL_FOCUS);
		enumTestList.add(SystemAction.KEEP_CONTEXT);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}