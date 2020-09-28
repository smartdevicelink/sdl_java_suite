package com.smartdevicelink.test.trace.enums;

import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.trace.enums.DetailLevel;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.trace.enums.DetailLevel}
 */
public class DetailLevelTests extends TestCase {

    /**
     * This is a unit test for the following enum :
     * {@link com.smartdevicelink.trace.enums.DetailLevel}
     */
    public void testDetailLevelEnum() {

        // Test Values
        String testOff = "OFF";
        String testTerse = "TERSE";
        String testInvalid = "INVALID";
        String testVerbose = "VERBOSE";

        try {
            // Comparison Values
            DetailLevel expectedOffEnum = DetailLevel.OFF;
            DetailLevel expectedTerseEnum = DetailLevel.TERSE;
            DetailLevel expectedVerboseEnum = DetailLevel.VERBOSE;
            List<DetailLevel> expectedEnumList = new ArrayList<DetailLevel>();
            expectedEnumList.add(DetailLevel.OFF);
            expectedEnumList.add(DetailLevel.TERSE);
            expectedEnumList.add(DetailLevel.VERBOSE);

            DetailLevel actualNullEnum = DetailLevel.valueForString(null);
            DetailLevel actualOffEnum = DetailLevel.valueForString(testOff);
            DetailLevel actualTerseEnum = DetailLevel.valueForString(testTerse);
            DetailLevel actualInvalidEnum = DetailLevel.valueForString(testInvalid);
            DetailLevel actualVerboseEnum = DetailLevel.valueForString(testVerbose);
            List<DetailLevel> actualEnumList = Arrays.asList(DetailLevel.values());

            // Valid Tests
            assertEquals(TestValues.MATCH, expectedOffEnum, actualOffEnum);
            assertEquals(TestValues.MATCH, expectedTerseEnum, actualTerseEnum);
            assertEquals(TestValues.MATCH, expectedVerboseEnum, actualVerboseEnum);
            assertTrue(TestValues.ARRAY, expectedEnumList.containsAll(actualEnumList) && actualEnumList.containsAll(expectedEnumList));

            // Invalid/Null Tests
            assertNull(TestValues.NULL, actualInvalidEnum);
            assertNull(TestValues.NULL, actualNullEnum);

        } catch (NullPointerException e) {
            fail("Could not retrieve value for null string, should return null.");
        } catch (IllegalArgumentException e) {
            fail("Could not retrieve value for invalid string, should return null.");
        }
    }
}