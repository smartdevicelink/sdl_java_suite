package com.smartdevicelink.test.util;

import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.util.SdlDataTypeConverter;

import junit.framework.TestCase;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.util.SdlDataTypeConverter}
 */
public class SdlDataTypeConverterTests extends TestCase {

    /**
     * This is a unit test for the following method :
     * {@link com.smartdevicelink.util.SdlDataTypeConverter#objectToDouble(Object)}
     */
    public void testObjectToDouble() {

        // Test Values
        Long testLong = Long.valueOf(1);
        Short testShort = 1;
        Float testFloat = 1f;
        Double testDouble = 1.0;
        Integer testInteger = 1;

        // Comparison Values
        Double expectedValue = 1.0;
        Double actualNullValue = SdlDataTypeConverter.objectToDouble(null);
        Double actualLongValue = SdlDataTypeConverter.objectToDouble(testLong);
        Double actualShortValue = SdlDataTypeConverter.objectToDouble(testShort);
        Double actualFloatValue = SdlDataTypeConverter.objectToDouble(testFloat);
        Double actualDoubleValue = SdlDataTypeConverter.objectToDouble(testDouble);
        Double actualIntegerValue = SdlDataTypeConverter.objectToDouble(testInteger);

        // Valid Tests
        assertEquals(TestValues.MATCH, expectedValue, actualDoubleValue);
        assertEquals(TestValues.MATCH, expectedValue, actualIntegerValue);
        assertEquals(TestValues.MATCH, expectedValue, actualFloatValue);

        // Null Tests
        assertNull(TestValues.NULL, actualNullValue);
        assertNull(TestValues.NULL, actualLongValue);
        assertNull(TestValues.NULL, actualShortValue);
    }
}