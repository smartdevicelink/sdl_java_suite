package com.smartdevicelink.test.rpc.enums;

import com.smartdevicelink.proxy.rpc.enums.TransmissionType;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransmissionTypeTests extends TestCase {
    /**
     * Verifies that the enum values are not null upon valid assignment.
     */
    public void testValidEnums () {
        String example = "MANUAL";
        TransmissionType manual = TransmissionType.valueForString(example);
        example = "AUTOMATIC";
        TransmissionType automatic = TransmissionType.valueForString(example);
        example = "SEMI_AUTOMATIC";
        TransmissionType semiAutomatic = TransmissionType.valueForString(example);
        example = "DUAL_CLUTCH";
        TransmissionType dualClutch = TransmissionType.valueForString(example);
        example = "CONTINUOUSLY_VARIABLE";
        TransmissionType enumSport = TransmissionType.valueForString(example);
        example = "INFINITELY_VARIABLE";
        TransmissionType continuouslyVariable = TransmissionType.valueForString(example);
        example = "ELECTRIC_VARIABLE";
        TransmissionType electricVariable = TransmissionType.valueForString(example);
        example = "DIRECT_DRIVE";
        TransmissionType directDrive = TransmissionType.valueForString(example);

        assertNotNull("PARK returned null", manual);
        assertNotNull("REVERSE returned null", automatic);
        assertNotNull("NEUTRAL returned null", semiAutomatic);
        assertNotNull("DRIVE returned null", dualClutch);
        assertNotNull("SPORT returned null", enumSport);
        assertNotNull("LOWGEAR returned null", continuouslyVariable);
        assertNotNull("FIRST returned null", electricVariable);
        assertNotNull("FIRST returned null", directDrive);
    }

    /**
     * Verifies that an invalid assignment is null.
     */
    public void testInvalidEnum () {
        String example = "pARk";
        try {
            TransmissionType temp = TransmissionType.valueForString(example);
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
            TransmissionType temp = TransmissionType.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
        }
        catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
        }
    }

    /**
     * Verifies the possible enum values of TransmissionType.
     */
    public void testListEnum() {
        List<TransmissionType> enumValueList = Arrays.asList(TransmissionType.values());

        List<TransmissionType> enumTestList = new ArrayList<TransmissionType>();
        enumTestList.add(TransmissionType.MANUAL);
        enumTestList.add(TransmissionType.AUTOMATIC);
        enumTestList.add(TransmissionType.SEMI_AUTOMATIC);
        enumTestList.add(TransmissionType.DUAL_CLUTCH);
        enumTestList.add(TransmissionType.CONTINUOUSLY_VARIABLE);
        enumTestList.add(TransmissionType.INFINITELY_VARIABLE);
        enumTestList.add(TransmissionType.ELECTRIC_VARIABLE);
        enumTestList.add(TransmissionType.DIRECT_DRIVE);

        assertTrue("Enum value list does not match enum class list",
                enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
    }
}
