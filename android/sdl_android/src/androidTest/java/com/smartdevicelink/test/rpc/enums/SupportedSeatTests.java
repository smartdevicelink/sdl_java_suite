package com.smartdevicelink.test.rpc.enums;

import com.smartdevicelink.proxy.rpc.enums.SupportedSeat;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.enums.SupportedSeat}
 */
public class SupportedSeatTests extends TestCase {

    /**
     * Verifies that the enum values are not null upon valid assignment.
     */
    public void testValidEnums() {
        String example = "DRIVER";
        SupportedSeat enumDriver = SupportedSeat.valueForString(example);
        example = "FRONT_PASSENGER";
        SupportedSeat enumFrontPassenger = SupportedSeat.valueForString(example);

        assertNotNull("DRIVER returned null", enumDriver);
        assertNotNull("FRONT_PASSENGER returned null", enumFrontPassenger);
    }

    /**
     * Verifies that an invalid assignment is null.
     */
    public void testInvalidEnum() {
        String example = "dRIVER";
        try {
            SupportedSeat temp = SupportedSeat.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
        } catch (IllegalArgumentException exception) {
            fail("Invalid enum throws IllegalArgumentException.");
        }
    }

    /**
     * Verifies that a null assignment is invalid.
     */
    public void testNullEnum() {
        String example = null;
        try {
            SupportedSeat temp = SupportedSeat.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
        } catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
        }
    }

    /**
     * Verifies the possible enum values of SupportedSeat.
     */
    public void testListEnum() {
        List<SupportedSeat> enumValueList = Arrays.asList(SupportedSeat.values());

        List<SupportedSeat> enumTestList = new ArrayList<SupportedSeat>();
        enumTestList.add(SupportedSeat.DRIVER);
        enumTestList.add(SupportedSeat.FRONT_PASSENGER);

        assertTrue("Enum value list does not match enum class list",
                enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
    }
}