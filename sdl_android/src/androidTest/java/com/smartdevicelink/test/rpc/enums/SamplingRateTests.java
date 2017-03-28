package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.SamplingRate;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.SamplingRate}
 */
public class SamplingRateTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
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

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "8kHz";
		try {
		    SamplingRate temp = SamplingRate.valueForString(example);
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
		    SamplingRate temp = SamplingRate.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}

	/**
	 * Verifies the possible enum values of SamplingRate.
	 */
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