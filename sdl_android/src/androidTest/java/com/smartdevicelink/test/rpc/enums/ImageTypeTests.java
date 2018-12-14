package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.ImageType;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.ImageType}
 */
public class ImageTypeTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = "STATIC";
		ImageType enumStatic = ImageType.valueForString(example);
		example = "DYNAMIC";
		ImageType enumDynamic = ImageType.valueForString(example);
		
		assertNotNull("STATIC returned null", enumStatic);
		assertNotNull("DYNAMIC returned null", enumDynamic);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "sTatIc";
		try {
		    ImageType temp = ImageType.valueForString(example);
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
		    ImageType temp = ImageType.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}	

	/**
	 * Verifies the possible enum values of ImageType.
	 */
	public void testListEnum() {
 		List<ImageType> enumValueList = Arrays.asList(ImageType.values());

		List<ImageType> enumTestList = new ArrayList<ImageType>();
		enumTestList.add(ImageType.STATIC);
		enumTestList.add(ImageType.DYNAMIC);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}