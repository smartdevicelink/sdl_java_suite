package com.smartdevicelink.test.rpc.enums;

import com.smartdevicelink.proxy.rpc.enums.DoorStatusType;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DoorStatusTypeTests extends TestCase {

    /**
     * Verifies that the enum values are not null upon valid assignment.
     */
    public void testValidEnums() {
        String example = "CLOSED";
        DoorStatusType closed = DoorStatusType.valueForString(example);
        example = "LOCKED";
        DoorStatusType locked = DoorStatusType.valueForString(example);
        example = "AJAR";
        DoorStatusType ajar = DoorStatusType.valueForString(example);
        example = "REMOVED";
        DoorStatusType removed = DoorStatusType.valueForString(example);

        assertNotNull("CLOSED returned null", closed);
        assertNotNull("LOCKED returned null", locked);
        assertNotNull("AJAR returned null", ajar);
        assertNotNull("REMOVED returned null", removed);
    }

    /**
     * Verifies that an invalid assignment is null.
     */
    public void testInvalidEnum() {
        String example = "cloS_ed";
        try {
            DoorStatusType temp = DoorStatusType.valueForString(example);
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
            DoorStatusType temp = DoorStatusType.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
        } catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
        }
    }

    /**
     * Verifies the possible enum values of DriverDistractionState.
     */
    public void testListEnum() {
        List<DoorStatusType> enumValueList = Arrays.asList(DoorStatusType.values());

        List<DoorStatusType> enumTestList = new ArrayList<DoorStatusType>();
        enumTestList.add(DoorStatusType.CLOSED);
        enumTestList.add(DoorStatusType.LOCKED);
        enumTestList.add(DoorStatusType.AJAR);
        enumTestList.add(DoorStatusType.REMOVED);

        assertTrue("Enum value list does not match enum class list",
                enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
    }
}
