package com.smartdevicelink.test.trace.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.R;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.trace.enums.Mod;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.trace.enums.Mod}
 */
public class ModTests extends AndroidTestCase {
	
	/**
	 * This is a unit test for the following enum : 
	 * {@link com.smartdevicelink.trace.enums.Mod}
	 */
	public void testModEnum () {
		
		// TestValues
		String testApp     = mContext.getString(R.string.app);
		String testRpc     = mContext.getString(R.string.rpc);
		String testMar     = mContext.getString(R.string.mar);
		String testTran    = mContext.getString(R.string.tran);
		String testProto   = mContext.getString(R.string.proto);
		String testProxy   = mContext.getString(R.string.proxy);
		String testInvalid = mContext.getString(R.string.invalid);
		
		try {
			// Comparison Values
			Mod expectedAppEnum   = Mod.app;
			Mod expectedRpcEnum   = Mod.rpc;
			Mod expectedMarEnum   = Mod.mar;
			Mod expectedTranEnum  = Mod.tran;
			Mod expectedProtoEnum = Mod.proto;
			Mod expectedProxyEnum = Mod.proxy;
			List<Mod> expectedEnumList = new ArrayList<Mod>();
			expectedEnumList.add(Mod.app);
			expectedEnumList.add(Mod.rpc);
			expectedEnumList.add(Mod.mar);
			expectedEnumList.add(Mod.tran);
			expectedEnumList.add(Mod.proto);
			expectedEnumList.add(Mod.proxy);
			
			Mod actualNullEnum       = Mod.valueForString(null);
			Mod actualAppEnum        = Mod.valueForString(testApp);
			Mod actualRpcEnum        = Mod.valueForString(testRpc);
			Mod actualMarEnum        = Mod.valueForString(testMar);
			Mod actualTranEnum       = Mod.valueForString(testTran);
			Mod actualProtoEnum      = Mod.valueForString(testProto);
			Mod actualProxyEnum      = Mod.valueForString(testProxy);
			Mod actualInvalidEnum    = Mod.valueForString(testInvalid);
			List<Mod> actualEnumList = Arrays.asList(Mod.values());
			
			// Valid Tests
			assertEquals(Test.MATCH, expectedAppEnum, actualAppEnum);
			assertEquals(Test.MATCH, expectedRpcEnum, actualRpcEnum);
			assertEquals(Test.MATCH, expectedMarEnum, actualMarEnum);
			assertEquals(Test.MATCH, expectedTranEnum, actualTranEnum);
			assertEquals(Test.MATCH, expectedProtoEnum, actualProtoEnum);
			assertEquals(Test.MATCH, expectedProxyEnum, actualProxyEnum);
			assertTrue(Test.ARRAY, expectedEnumList.containsAll(actualEnumList) && actualEnumList.containsAll(expectedEnumList));
			
			// Invalid/Null Tests
			assertNull(Test.NULL, actualInvalidEnum);
			assertNull(Test.NULL, actualNullEnum);
			
		} catch (NullPointerException e) {
			fail(mContext.getString(R.string.could_not_retrieve_value_for_null_string));
		} catch (IllegalArgumentException e) {
			fail(mContext.getString(R.string.could_not_retrieve_value_for_invalid_string));
		}
		
	}
}