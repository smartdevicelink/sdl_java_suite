package com.smartdevicelink.test.protocol.enums;

import android.support.test.espresso.core.deps.guava.base.Function;

import com.smartdevicelink.protocol.enums.FunctionID;

import junit.framework.TestCase;

/**
 * Created by mat on 6/21/17.
 */

public class FunctionIdTests extends TestCase {


	public void testGetFunctionName() {

		FunctionID function = FunctionID.ALERT;
		int functionEnumId = function.getId();
		String name = FunctionID.getFunctionName(functionEnumId);

		assertEquals(FunctionID.getFunctionId(name), functionEnumId);
		assertEquals(FunctionID.ALERT.toString(), name);
	}
}
