package com.smartdevicelink.test.trace.enums;

import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.trace.enums.Mod;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.trace.enums.Mod}
 */
public class ModTests extends TestCase {

    /**
     * This is a unit test for the following enum :
     * {@link com.smartdevicelink.trace.enums.Mod}
     */
    public void testModEnum() {

        // TestValues
        String testApp = "app";
        String testRpc = "rpc";
        String testMar = "mar";
        String testTran = "tran";
        String testProto = "proto";
        String testProxy = "proxy";
        String testInvalid = "invalid";

        try {
            // Comparison Values
            Mod expectedAppEnum = Mod.app;
            Mod expectedRpcEnum = Mod.rpc;
            Mod expectedMarEnum = Mod.mar;
            Mod expectedTranEnum = Mod.tran;
            Mod expectedProtoEnum = Mod.proto;
            Mod expectedProxyEnum = Mod.proxy;
            List<Mod> expectedEnumList = new ArrayList<Mod>();
            expectedEnumList.add(Mod.app);
            expectedEnumList.add(Mod.rpc);
            expectedEnumList.add(Mod.mar);
            expectedEnumList.add(Mod.tran);
            expectedEnumList.add(Mod.proto);
            expectedEnumList.add(Mod.proxy);

            Mod actualNullEnum = Mod.valueForString(null);
            Mod actualAppEnum = Mod.valueForString(testApp);
            Mod actualRpcEnum = Mod.valueForString(testRpc);
            Mod actualMarEnum = Mod.valueForString(testMar);
            Mod actualTranEnum = Mod.valueForString(testTran);
            Mod actualProtoEnum = Mod.valueForString(testProto);
            Mod actualProxyEnum = Mod.valueForString(testProxy);
            Mod actualInvalidEnum = Mod.valueForString(testInvalid);
            List<Mod> actualEnumList = Arrays.asList(Mod.values());

            // Valid Tests
            assertEquals(TestValues.MATCH, expectedAppEnum, actualAppEnum);
            assertEquals(TestValues.MATCH, expectedRpcEnum, actualRpcEnum);
            assertEquals(TestValues.MATCH, expectedMarEnum, actualMarEnum);
            assertEquals(TestValues.MATCH, expectedTranEnum, actualTranEnum);
            assertEquals(TestValues.MATCH, expectedProtoEnum, actualProtoEnum);
            assertEquals(TestValues.MATCH, expectedProxyEnum, actualProxyEnum);
            assertTrue(TestValues.ARRAY, expectedEnumList.containsAll(actualEnumList) && actualEnumList.containsAll(expectedEnumList));

            // Invalid/Null Tests
            assertNull(TestValues.NULL, actualInvalidEnum);
            assertNull(TestValues.NULL, actualNullEnum);

        } catch (NullPointerException e) {
            fail("Could not retrieve value for null string, should return null.");
        } catch (IllegalArgumentException e) {
            fail("Could not retrieve value for invalid string, should return null.");
        }

    }
}