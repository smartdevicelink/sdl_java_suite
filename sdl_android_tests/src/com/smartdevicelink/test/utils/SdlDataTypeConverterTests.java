package com.smartdevicelink.test.utils;

import com.smartdevicelink.util.SdlDataTypeConverter;

import junit.framework.TestCase;

public class SdlDataTypeConverterTests extends TestCase {
	
	public void testConverter () {
		
		final String MSG = "Values should match.";
	
		assertEquals(MSG, (Double) 1.0, SdlDataTypeConverter.objectToDouble((Integer) 1));
		assertEquals(MSG, (Double) 1.0, SdlDataTypeConverter.objectToDouble((Double) 1.0));
		assertNull(MSG, SdlDataTypeConverter.objectToDouble(null));
	}
}