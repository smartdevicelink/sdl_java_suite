package com.smartdevicelink.test.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.SamplingRate;

public class SamplingRateTests extends TestCase {

	public void testValidEnums () {	
		String example = "8KHZ";
		SamplingRate enum8Khz = SamplingRate.valueForString(example);
		example = "16KHZ";
		SamplingRate enum16Khz = SamplingRate.valueForString(example);
		example = "22KHZ";
		SamplingRate enum22Khz = SamplingRate.valueForString(example);
		example = "44KHZ";
		SamplingRate enum44Khz = SamplingRate.valueForString(example);
		
		assertNotNull("8KHZ returned null", enum8Khz);
		assertNotNull("16KHZ returned null", enum16Khz);
		assertNotNull("22KHZ returned null", enum22Khz);
		assertNotNull("44KHZ returned null", enum44Khz);
	}

	//use this test if it's supposed to throw an exception
	public void testInvalidEnum () {
		String example = "8kHz";
		try {
			SamplingRate.valueForString(example);
			fail("Sample string did not throw an IllegalArgumentException");
		}
		catch (IllegalArgumentException exception) {
		}
	}
	
	//use this test if it's supposed to return null
	public void testInvalidEnum2 () {
		String example = "8kHz";
		SamplingRate result = SamplingRate.valueForString(example);
		assertNull("Invalid string didn't return null", result);
	}
	
	//use this test if it's supposed to throw an exception
	public void testNullEnum () {
		String example = null;
		try {
			SamplingRate.valueForString(example);
			fail("Sample string did not throw a NullPointerException");
		}
		catch (NullPointerException exception) {
		}
	}	
	
	//use this test if it's supposed to return null
	public void testNullEnum2 () {
		String example = null;
		SamplingRate result = SamplingRate.valueForString(example);
		assertNull("Null string didn't return null", result);
	}
	
	public void testListEnum() {
 		List<SamplingRate> enumValueList = Arrays.asList(SamplingRate.values());

		List<SamplingRate> enumTestList = new ArrayList<SamplingRate>();
		enumTestList.add(SamplingRate._8KHZ);
		enumTestList.add(SamplingRate._16KHZ);
		enumTestList.add(SamplingRate._22KHZ);
		enumTestList.add(SamplingRate._44KHZ);
		
		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
	
}
