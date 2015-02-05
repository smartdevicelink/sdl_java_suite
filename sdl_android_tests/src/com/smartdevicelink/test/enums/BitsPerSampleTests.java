package com.smartdevicelink.test.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.BitsPerSample;

public class BitsPerSampleTests extends TestCase {

	public void testValidEnums () {	
		String example = "8_BIT";
		BitsPerSample enum8Bit = BitsPerSample.valueForString(example);
		example = "16_BIT";
		BitsPerSample enum16Bit = BitsPerSample.valueForString(example);
		
		assertNotNull("8_BIT returned null", enum8Bit);
		assertNotNull("16_BIT returned null", enum16Bit);
	}

	//use this test if it's supposed to throw an exception
	public void testInvalidEnum () {
		String example = "8_biT";
		try {
			BitsPerSample.valueForString(example);
			fail("Sample string did not throw an IllegalArgumentException");
		}
		catch (IllegalArgumentException exception) {
			//If the method throws this exception then this test will be shown as passed.
		}
	}
	
	//use this test if it's supposed to return null
	public void testInvalidEnum2 () {
		String example = "8_biT";
		BitsPerSample result = BitsPerSample.valueForString(example);
		assertNull("Invalid string didn't return null", result);
	}
	
	//use this test if it's supposed to throw an exception
	public void testNullEnum () {
		String example = null;
		try {
			BitsPerSample.valueForString(example);
			fail("Sample string did not throw a NullPointerException");
		}
		catch (NullPointerException exception) {
			//If the method throws this exception then this test will be shown as passed.
		}
	}	
	
	//use this test if it's supposed to return null
	public void testNullEnum2 () {
		String example = null;
		BitsPerSample result = BitsPerSample.valueForString(example);
		assertNull("Null string didn't return null", result);
	}
	
	public void testListEnum() {
 		List<BitsPerSample> enumValueList = Arrays.asList(BitsPerSample.values());

		List<BitsPerSample> enumTestList = new ArrayList<BitsPerSample>();
		enumTestList.add(BitsPerSample._8_BIT);
		enumTestList.add(BitsPerSample._16_BIT);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
	
}
