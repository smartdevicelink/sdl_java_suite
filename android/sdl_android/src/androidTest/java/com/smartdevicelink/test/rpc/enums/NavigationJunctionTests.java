package com.smartdevicelink.test.rpc.enums;

import com.smartdevicelink.proxy.rpc.enums.NavigationJunction;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.enums.NavigationJunction}
 */
public class NavigationJunctionTests extends TestCase {

    /**
     * Verifies that the enum values are not null upon valid assignment.
     */
    public void testValidEnums() {
        String example = "REGULAR";
        NavigationJunction enumRegular = NavigationJunction.valueForString(example);
        example = "BIFURCATION";
        NavigationJunction enumBifurcation = NavigationJunction.valueForString(example);
        example = "MULTI_CARRIAGEWAY";
        NavigationJunction enumMultiCarriageway = NavigationJunction.valueForString(example);
        example = "ROUNDABOUT";
        NavigationJunction enumRoundabout = NavigationJunction.valueForString(example);
        example = "TRAVERSABLE_ROUNDABOUT";
        NavigationJunction enumTraversableRoundabout = NavigationJunction.valueForString(example);
        example = "JUGHANDLE";
        NavigationJunction enumJughandle = NavigationJunction.valueForString(example);
        example = "ALL_WAY_YIELD";
        NavigationJunction enumAllWayYield = NavigationJunction.valueForString(example);
        example = "TURN_AROUND";
        NavigationJunction enumTurnAround = NavigationJunction.valueForString(example);

        assertNotNull("REGULAR returned null", enumRegular);
        assertNotNull("BIFURCATION returned null", enumBifurcation);
        assertNotNull("MULTI_CARRIAGEWAY returned null", enumMultiCarriageway);
        assertNotNull("ROUNDABOUT returned null", enumRoundabout);
        assertNotNull("TRAVERSABLE_ROUNDABOUT returned null", enumTraversableRoundabout);
        assertNotNull("JUGHANDLE returned null", enumJughandle);
        assertNotNull("ALL_WAY_YIELD returned null", enumAllWayYield);
        assertNotNull("TURN_AROUND returned null", enumTurnAround);
    }

    /**
     * Verifies that an invalid assignment is null.
     */
    public void testInvalidEnum() {
        String example = "fRONT";
        try {
            NavigationJunction temp = NavigationJunction.valueForString(example);
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
            NavigationJunction temp = NavigationJunction.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
        } catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
        }
    }

    /**
     * Verifies the possible enum values of NavigationJunction.
     */
    public void testListEnum() {
        List<NavigationJunction> enumValueList = Arrays.asList(NavigationJunction.values());

        List<NavigationJunction> enumTestList = new ArrayList<>();
        enumTestList.add(NavigationJunction.REGULAR);
        enumTestList.add(NavigationJunction.BIFURCATION);
        enumTestList.add(NavigationJunction.MULTI_CARRIAGEWAY);
        enumTestList.add(NavigationJunction.ROUNDABOUT);
        enumTestList.add(NavigationJunction.TRAVERSABLE_ROUNDABOUT);
        enumTestList.add(NavigationJunction.JUGHANDLE);
        enumTestList.add(NavigationJunction.ALL_WAY_YIELD);
        enumTestList.add(NavigationJunction.TURN_AROUND);

        assertTrue("Enum value list does not match enum class list",
                enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
    }
}
