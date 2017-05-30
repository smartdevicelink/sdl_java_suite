package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.CharacterSet;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.CharacterSet}
 */
public class CharacterSetTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.type_two_set_caps);
		CharacterSet enumType2Set = CharacterSet.valueForString(example);
		example = mContext.getString(R.string.type_five_set_caps);
		CharacterSet enumType5Set = CharacterSet.valueForString(example);
		example = mContext.getString(R.string.cid_one_set_caps);
		CharacterSet enumCid1Set = CharacterSet.valueForString(example);
		example = mContext.getString(R.string.cid_two_set_caps);
		CharacterSet enumCid2Set = CharacterSet.valueForString(example);
		
		assertNotNull("TYPE2SET returned null", enumType2Set);
		assertNotNull("TYPE5SET returned null", enumType5Set);
		assertNotNull("CID1SET returned null", enumCid1Set);
		assertNotNull("CID2SET returned null", enumCid2Set);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    CharacterSet temp = CharacterSet.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (IllegalArgumentException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}

	/**
	 * Verifies that a null assignment is invalid.
	 */
	public void testNullEnum () {
		String example = null;
		try {
		    CharacterSet temp = CharacterSet.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}	

	/**
	 * Verifies the possible enum values of CharacterSet.
	 */
	public void testListEnum() {
 		List<CharacterSet> enumValueList = Arrays.asList(CharacterSet.values());

		List<CharacterSet> enumTestList = new ArrayList<CharacterSet>();
		enumTestList.add(CharacterSet.TYPE2SET);
		enumTestList.add(CharacterSet.TYPE5SET);
		enumTestList.add(CharacterSet.CID1SET);
		enumTestList.add(CharacterSet.CID2SET);

		assertTrue(mContext.getString(R.string.enum_value_list_does_not_match_enum_class_list),
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}