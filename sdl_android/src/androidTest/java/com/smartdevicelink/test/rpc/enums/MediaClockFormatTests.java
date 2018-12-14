package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.MediaClockFormat;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.MediaClockFormat}
 */
public class MediaClockFormatTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = "CLOCK1";
		MediaClockFormat enumClock1 = MediaClockFormat.valueForString(example);
		example = "CLOCK2";
		MediaClockFormat enumClock2 = MediaClockFormat.valueForString(example);
		example = "CLOCK3";
		MediaClockFormat enumClock3 = MediaClockFormat.valueForString(example);
		example = "CLOCKTEXT1";
		MediaClockFormat enumClockText1 = MediaClockFormat.valueForString(example);
		example = "CLOCKTEXT2";
		MediaClockFormat enumClockText2 = MediaClockFormat.valueForString(example);
		example = "CLOCKTEXT3";
		MediaClockFormat enumClockText3 = MediaClockFormat.valueForString(example);
		example = "CLOCKTEXT4";
		MediaClockFormat enumClockText4 = MediaClockFormat.valueForString(example);
		
		assertNotNull("CLOCK1 returned null", enumClock1);
		assertNotNull("CLOCK2 returned null", enumClock2);
		assertNotNull("CLOCK3 returned null", enumClock3);
		assertNotNull("CLOCKTEXT1 returned null", enumClockText1);
		assertNotNull("CLOCKTEXT2 returned null", enumClockText2);
		assertNotNull("CLOCKTEXT3 returned null", enumClockText3);
		assertNotNull("CLOCKTEXT4 returned null", enumClockText4);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "cloCK1";
		try {
		    MediaClockFormat temp = MediaClockFormat.valueForString(example);
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
		    MediaClockFormat temp = MediaClockFormat.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}		

	/**
	 * Verifies the possible enum values of MediaClockFormat.
	 */
	public void testListEnum() {
 		List<MediaClockFormat> enumValueList = Arrays.asList(MediaClockFormat.values());

		List<MediaClockFormat> enumTestList = new ArrayList<MediaClockFormat>();
		enumTestList.add(MediaClockFormat.CLOCK1);
		enumTestList.add(MediaClockFormat.CLOCK2);
		enumTestList.add(MediaClockFormat.CLOCK3);
		enumTestList.add(MediaClockFormat.CLOCKTEXT1);
		enumTestList.add(MediaClockFormat.CLOCKTEXT2);
		enumTestList.add(MediaClockFormat.CLOCKTEXT3);		
		enumTestList.add(MediaClockFormat.CLOCKTEXT4);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}