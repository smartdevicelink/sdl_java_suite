package com.smartdevicelink.test.rpc.enums;

import com.smartdevicelink.proxy.rpc.enums.ServiceUpdateReason;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.enums.ServiceUpdateReason}
 */
public class ServiceUpdateReasonTests extends TestCase {

    /**
     * Verifies that the enum values are not null upon valid assignment.
     */
    public void testValidEnums() {
        String example = "PUBLISHED";
        ServiceUpdateReason enumPublished = ServiceUpdateReason.valueForString(example);
        example = "REMOVED";
        ServiceUpdateReason enumRemoved = ServiceUpdateReason.valueForString(example);
        example = "ACTIVATED";
        ServiceUpdateReason enumActivated = ServiceUpdateReason.valueForString(example);
        example = "DEACTIVATED";
        ServiceUpdateReason enumDeactivated = ServiceUpdateReason.valueForString(example);
        example = "MANIFEST_UPDATE";
        ServiceUpdateReason enumManifestUpdate = ServiceUpdateReason.valueForString(example);


        assertNotNull("PUBLISHED returned null", enumPublished);
        assertNotNull("REMOVED returned null", enumRemoved);
        assertNotNull("ACTIVATED returned null", enumActivated);
        assertNotNull("DEACTIVATED returned null", enumDeactivated);
        assertNotNull("MANIFEST_UPDATE returned null", enumManifestUpdate);
    }

    /**
     * Verifies that an invalid assignment is null.
     */
    public void testInvalidEnum() {
        String example = "HalFActIvAted";
        try {
            ServiceUpdateReason temp = ServiceUpdateReason.valueForString(example);
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
            ServiceUpdateReason temp = ServiceUpdateReason.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
        } catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
        }
    }

    /**
     * Verifies the possible enum values of ServiceUpdateReason.
     */
    public void testListEnum() {
        List<ServiceUpdateReason> enumValueList = Arrays.asList(ServiceUpdateReason.values());

        List<ServiceUpdateReason> enumTestList = new ArrayList<>();
        enumTestList.add(ServiceUpdateReason.MANIFEST_UPDATE);
        enumTestList.add(ServiceUpdateReason.ACTIVATED);
        enumTestList.add(ServiceUpdateReason.DEACTIVATED);
        enumTestList.add(ServiceUpdateReason.PUBLISHED);
        enumTestList.add(ServiceUpdateReason.REMOVED);

        assertTrue("Enum value list does not match enum class list",
                enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
    }
}