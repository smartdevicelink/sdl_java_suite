package com.smartdevicelink.test.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.ImageType;

public class ImageTypeTests extends TestCase {

	public void testValidEnums () {	
		String example = "STATIC";
		ImageType enumStatic = ImageType.valueForString(example);
		example = "DYNAMIC";
		ImageType enumDynamic = ImageType.valueForString(example);
		
		assertNotNull("STATIC returned null", enumStatic);
		assertNotNull("DYNAMIC returned null", enumDynamic);
	}
	
	public void testInvalidEnum () {
		String example = "sTatIc";
		try {
			ImageType.valueForString(example);
			fail("Sample string did not throw an IllegalArgumentException");
		}
		catch (IllegalArgumentException exception) {
			//If the method throws this exception then this test will be shown as passed.
		}
	}
	
	public void testNullEnum () {
		String example = null;
		try {
			ImageType.valueForString(example);
			fail("Sample string did not throw a NullPointerException");
		}
		catch (NullPointerException exception) {
			//If the method throws this exception then this test will be shown as passed.
		}
	}	
	
	public void testListEnum() {
 		List<ImageType> enumValueList = Arrays.asList(ImageType.values());

		List<ImageType> enumTestList = new ArrayList<ImageType>();
		enumTestList.add(ImageType.STATIC);
		enumTestList.add(ImageType.DYNAMIC);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
	
}
