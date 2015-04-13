package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.AudioType;

public class AudioTypeTests extends TestCase {

	public void testValidEnums () {	
		String example = "PCM";
		AudioType enumPcm = AudioType.valueForString(example);
		
		assertNotNull("PCM returned null", enumPcm);
	}
	
	public void testInvalidEnum () {
		String example = "pCM";
		try {
		    AudioType temp = AudioType.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (IllegalArgumentException exception) {
            fail("Invalid enum throws IllegalArgumentException.");
		}
	}
	
	public void testNullEnum () {
		String example = null;
		try {
		    AudioType temp = AudioType.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}	
	
	public void testListEnum() {
 		List<AudioType> enumValueList = Arrays.asList(AudioType.values());

		List<AudioType> enumTestList = new ArrayList<AudioType>();
		enumTestList.add(AudioType.PCM);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
	
}
