package com.smartdevicelink.test.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.Dimension;

public class DimensionTests extends TestCase {

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

	//use this test if it's supposed to throw an exception
	public void testInvalidEnum () {
		String example = "no_FiX";
		try {
			Dimension.valueForString(example);
			fail("Sample string did not throw an IllegalArgumentException");
		}
		catch (IllegalArgumentException exception) {
		}
	}
	
	//use this test if it's supposed to return null
	public void testInvalidEnum2 () {
		String example = "no_FiX";
		Dimension result = Dimension.valueForString(example);
		assertNull("Invalid string didn't return null", result);
	}
	
	//use this test if it's supposed to throw an exception
	public void testNullEnum () {
		String example = null;
		try {
			Dimension.valueForString(example);
			fail("Sample string did not throw a NullPointerException");
		}
		catch (NullPointerException exception) {
		}
	}	
	
	//use this test if it's supposed to return null
	public void testNullEnum2 () {
		String example = null;
		Dimension result = Dimension.valueForString(example);
		assertNull("Null string didn't return null", result);
	}
	
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
