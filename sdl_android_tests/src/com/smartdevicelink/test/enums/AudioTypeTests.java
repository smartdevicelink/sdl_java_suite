package com.smartdevicelink.test.enums;

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
			AudioType.valueForString(example);
			fail("Sample string did not throw an IllegalArgumentException");
		}
		catch (IllegalArgumentException exception) {
			//If the method throws this exception then this test will be shown as passed.
		}
	}
	
	public void testNullEnum () {
		String example = null;
		try {
			AudioType.valueForString(example);
			fail("Sample string did not throw a NullPointerException");
		}
		catch (NullPointerException exception) {
			//If the method throws this exception then this test will be shown as passed.
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
