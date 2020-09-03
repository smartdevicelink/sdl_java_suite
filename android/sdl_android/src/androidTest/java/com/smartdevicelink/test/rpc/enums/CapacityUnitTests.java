package com.smartdevicelink.test.rpc.enums;

import com.smartdevicelink.proxy.rpc.enums.CapacityUnit;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CapacityUnitTests extends TestCase {

    /**
     * Verifies that the enum values are not null upon valid assignment.
     */
    public void testValidEnums () {
        String example = "LITERS";
        CapacityUnit enumLiters = CapacityUnit.valueForString(example);
        example = "KILOWATTHOURS";
        CapacityUnit enumKWH = CapacityUnit.valueForString(example);
        example = "KILOGRAMS";
        CapacityUnit enumKilograms = CapacityUnit.valueForString(example);

        assertNotNull("LITERS returned null", enumLiters);
        assertNotNull("KILOWATTHOURS returned null", enumKWH);
        assertNotNull("KILOGRAMS returned null", enumKilograms);
    }

    /**
     * Verifies that an invalid assignment is null.
     */
    public void testInvalidEnum () {
        String example = "lONg";
        try {
            CapacityUnit temp = CapacityUnit.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
        }
        catch (IllegalArgumentException exception) {
            fail("Invalid enum throws IllegalArgumentException.");
        }
    }

    /**
     * Verifies that a null assignment is invalid.
     */
    public void testNullEnum () {
        String example = null;
        try {
            CapacityUnit temp = CapacityUnit.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
        }
        catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
        }
    }

    /**
     * Verifies the possible enum values of ButtonPressMode.
     */
    public void testListEnum() {
        List<CapacityUnit> enumValueList = Arrays.asList(CapacityUnit.values());

        List<CapacityUnit> enumTestList = new ArrayList<CapacityUnit>();
        enumTestList.add(CapacityUnit.KILOGRAMS);
        enumTestList.add(CapacityUnit.KILOWATTHOURS);
        enumTestList.add(CapacityUnit.LITERS);

        assertTrue("Enum value list does not match enum class list",
                enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
    }
}
