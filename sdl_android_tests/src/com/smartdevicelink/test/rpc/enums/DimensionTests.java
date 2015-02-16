package com.smartdevicelink.test.rpc.enums;

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
	
	//use this test if it's supposed to throw an exception
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
