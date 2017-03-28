package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.Dimension;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.Dimension}
 */
public class DimensionTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = "NO_FIX";
		Dimension enumNoFix = Dimension.valueForString(example);
		example = "2D";
		Dimension enum2D = Dimension.valueForString(example);
		example = "3D";
		Dimension enum3D = Dimension.valueForString(example);
		
		assertNotNull("NO_FIX returned null", enumNoFix);
		assertNotNull("2D returned null", enum2D);
		assertNotNull("3D returned null", enum3D);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "no_FiX";
		try {
		    Dimension temp = Dimension.valueForString(example);
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
		    Dimension temp = Dimension.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}
	
	/**
	 * Verifies the possible enum values of Dimension.
	 */
	public void testListEnum() {
 		List<Dimension> enumValueList = Arrays.asList(Dimension.values());

		List<Dimension> enumTestList = new ArrayList<Dimension>();
		enumTestList.add(Dimension.NO_FIX);
		enumTestList.add(Dimension._2D);
		enumTestList.add(Dimension._3D);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
}