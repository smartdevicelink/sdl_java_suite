package com.smartdevicelink.test.rpc.enums;

import com.smartdevicelink.proxy.rpc.enums.NavigationAction;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.enums.NavigationAction}
 */
public class NavigationActionTests extends TestCase {

    /**
     * Verifies that the enum values are not null upon valid assignment.
     */
    public void testValidEnums() {
        String example = "TURN";
        NavigationAction enumTurn = NavigationAction.valueForString(example);
        example = "EXIT";
        NavigationAction enumExit = NavigationAction.valueForString(example);
        example = "STAY";
        NavigationAction enumStay = NavigationAction.valueForString(example);
        example = "MERGE";
        NavigationAction enumMerge = NavigationAction.valueForString(example);
        example = "FERRY";
        NavigationAction enumFerry = NavigationAction.valueForString(example);
        example = "CAR_SHUTTLE_TRAIN";
        NavigationAction enumCarShuttleTrain = NavigationAction.valueForString(example);
        example = "WAYPOINT";
        NavigationAction enumWaypoint = NavigationAction.valueForString(example);

        assertNotNull("TURN returned null", enumTurn);
        assertNotNull("EXIT returned null", enumExit);
        assertNotNull("STAY returned null", enumStay);
        assertNotNull("MERGE returned null", enumMerge);
        assertNotNull("FERRY returned null", enumFerry);
        assertNotNull("CAR_SHUTTLE_TRAIN returned null", enumCarShuttleTrain);
        assertNotNull("WAYPOINT returned null", enumWaypoint);

    }

    /**
     * Verifies that an invalid assignment is null.
     */
    public void testInvalidEnum() {
        String example = "fRONT";
        try {
            NavigationAction temp = NavigationAction.valueForString(example);
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
            NavigationAction temp = NavigationAction.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
        } catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
        }
    }

    /**
     * Verifies the possible enum values of NavigationAction.
     */
    public void testListEnum() {
        List<NavigationAction> enumValueList = Arrays.asList(NavigationAction.values());

        List<NavigationAction> enumTestList = new ArrayList<>();
        enumTestList.add(NavigationAction.TURN);
        enumTestList.add(NavigationAction.EXIT);
        enumTestList.add(NavigationAction.STAY);
        enumTestList.add(NavigationAction.MERGE);
        enumTestList.add(NavigationAction.FERRY);
        enumTestList.add(NavigationAction.CAR_SHUTTLE_TRAIN);
        enumTestList.add(NavigationAction.WAYPOINT);

        assertTrue("Enum value list does not match enum class list",
                enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
    }
}
