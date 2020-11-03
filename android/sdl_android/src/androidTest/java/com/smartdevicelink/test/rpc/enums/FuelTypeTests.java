package com.smartdevicelink.test.rpc.enums;

import com.smartdevicelink.proxy.rpc.enums.FuelType;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.enums.FuelType}
 */
public class FuelTypeTests extends TestCase {

    /**
     * Verifies that the enum values are not null upon valid assignment.
     */
    public void testValidEnums() {
        String example = "GASOLINE";
        FuelType enumGasoline = FuelType.valueForString(example);
        example = "DIESEL";
        FuelType enumDiesel = FuelType.valueForString(example);
        example = "CNG";
        FuelType enumCng = FuelType.valueForString(example);
        example = "LPG";
        FuelType enumLpg = FuelType.valueForString(example);
        example = "HYDROGEN";
        FuelType enumHydrogen = FuelType.valueForString(example);
        example = "BATTERY";
        FuelType enumBattery = FuelType.valueForString(example);

        assertNotNull("GASOLINE returned null", enumGasoline);
        assertNotNull("DIESEL returned null", enumDiesel);
        assertNotNull("CNG returned null", enumCng);
        assertNotNull("LPG returned null", enumLpg);
        assertNotNull("HYDROGEN returned null", enumHydrogen);
        assertNotNull("BATTERY returned null", enumBattery);
    }

    /**
     * Verifies that an invalid assignment is null.
     */
    public void testInvalidEnum() {
        String example = "gASOLINE";
        try {
            FuelType temp = FuelType.valueForString(example);
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
            FuelType temp = FuelType.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
        } catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
        }
    }

    /**
     * Verifies the possible enum values of FuelType.
     */
    public void testListEnum() {
        List<FuelType> enumValueList = Arrays.asList(FuelType.values());

        List<FuelType> enumTestList = new ArrayList<FuelType>();
        enumTestList.add(FuelType.GASOLINE);
        enumTestList.add(FuelType.DIESEL);
        enumTestList.add(FuelType.CNG);
        enumTestList.add(FuelType.LPG);
        enumTestList.add(FuelType.HYDROGEN);
        enumTestList.add(FuelType.BATTERY);

        assertTrue("Enum value list does not match enum class list",
                enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
    }
}