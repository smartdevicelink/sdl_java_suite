package com.smartdevicelink.test.rpc.enums;

import com.smartdevicelink.proxy.rpc.enums.WindowType;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.enums.WindowType}
 */
public class WindowTypeTests extends TestCase {

    /**
     * Verifies that the enum values are not null upon valid assignment.
     */
    public void testValidEnums() {
        String example = "MAIN";
        WindowType enumMain = WindowType.valueForString(example);
        example = "WIDGET";
        WindowType enumWidget = WindowType.valueForString(example);

        assertNotNull("MAIN returned null", enumMain);
        assertNotNull("WIDGET returned null", enumWidget);
    }

    /**
     * Verifies that an invalid assignment is null.
     */
    public void testInvalidEnum() {
        String example = "mAIN";
        try {
            WindowType temp = WindowType.valueForString(example);
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
            WindowType temp = WindowType.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
        } catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
        }
    }


    /**
     * Verifies the possible enum values of WindowType.
     */
    public void testListEnum() {
        List<WindowType> enumValueList = Arrays.asList(WindowType.values());

        List<WindowType> enumTestList = new ArrayList<WindowType>();
        enumTestList.add(WindowType.MAIN);
        enumTestList.add(WindowType.WIDGET);

        assertTrue("Enum value list does not match enum class list",
                enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
    }
}