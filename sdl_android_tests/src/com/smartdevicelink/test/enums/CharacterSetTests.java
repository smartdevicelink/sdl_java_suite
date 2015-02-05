package com.smartdevicelink.test.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.CharacterSet;

public class CharacterSetTests extends TestCase {
	
	public void testValidEnums () {	
		String example = "TYPE2SET";
		CharacterSet enumType2Set = CharacterSet.valueForString(example);
		example = "TYPE5SET";
		CharacterSet enumType5Set = CharacterSet.valueForString(example);
		example = "CID1SET";
		CharacterSet enumCid1Set = CharacterSet.valueForString(example);
		example = "CID2SET";
		CharacterSet enumCid2Set = CharacterSet.valueForString(example);
		
		assertNotNull("TYPE2SET returned null", enumType2Set);
		assertNotNull("TYPE5SET returned null", enumType5Set);
		assertNotNull("CID1SET returned null", enumCid1Set);
		assertNotNull("CID2SET returned null", enumCid2Set);
	}
	
	public void testInvalidEnum () {
		String example = "tyPe2SeT";
		try {
			CharacterSet.valueForString(example);
			fail("Sample string did not throw an IllegalArgumentException");
		}
		catch (IllegalArgumentException exception) {
			//If the method throws this exception then this test will be shown as passed.
		}
	}
	
	public void testNullEnum () {
		String example = null;
		try {
			CharacterSet.valueForString(example);
			fail("Sample string did not throw a NullPointerException");
		}
		catch (NullPointerException exception) {
			//If the method throws this exception then this test will be shown as passed.
		}
	}	
	
	public void testListEnum() {
 		List<CharacterSet> enumValueList = Arrays.asList(CharacterSet.values());

		List<CharacterSet> enumTestList = new ArrayList<CharacterSet>();
		enumTestList.add(CharacterSet.TYPE2SET);
		enumTestList.add(CharacterSet.TYPE5SET);
		enumTestList.add(CharacterSet.CID1SET);
		enumTestList.add(CharacterSet.CID2SET);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
	
}
