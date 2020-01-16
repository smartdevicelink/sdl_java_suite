package com.smartdevicelink.test.rpc.enums;

import com.smartdevicelink.proxy.rpc.enums.PredefinedWindows;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.enums.PredefinedWindows}
 */
public class PredefinedWindowsTests extends TestCase {

    /**
     * Verifies that the enum values are not null upon valid assignment.
     */
    public void testValidEnums() {
        int example = 0;
        PredefinedWindows enumDefaultWindow = PredefinedWindows.valueForInt(example);
        example = 1;
        PredefinedWindows enumPrimaryWidget = PredefinedWindows.valueForInt(example);

        assertNotNull("DEFAULT_WINDOW returned null", enumDefaultWindow);
        assertNotNull("PRIMARY_WIDGET returned null", enumPrimaryWidget);
    }

    /**
     * Verifies that an invalid assignment is null.
     */
    public void testInvalidEnum() {
        int example = 3;
        try {
            PredefinedWindows temp = PredefinedWindows.valueForInt(example);
            assertNull("Result of valueForString should be null.", temp);
        } catch (IllegalArgumentException exception) {
            fail("Invalid enum throws IllegalArgumentException.");
        }
    }


    /**
     * Verifies the possible enum values of PredefinedWindows.
     */
    public void testListEnum() {
        List<PredefinedWindows> enumValueList = Arrays.asList(PredefinedWindows.values());

        List<PredefinedWindows> enumTestList = new ArrayList<PredefinedWindows>();
        enumTestList.add(PredefinedWindows.DEFAULT_WINDOW);
        enumTestList.add(PredefinedWindows.PRIMARY_WIDGET);

        assertTrue("Enum value list does not match enum class list",
                enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
    }
}