package com.smartdevicelink.test.util;

import com.smartdevicelink.AndroidTestCase2;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.util.CompareUtils;

public class CompareUtilsTest extends AndroidTestCase2 {

    public void testAreStringsEqual(){

        assertTrue(CompareUtils.areStringsEqual(Test.GENERAL_STRING, Test.GENERAL_STRING, true, true));
        assertTrue(CompareUtils.areStringsEqual(Test.GENERAL_STRING, Test.GENERAL_STRING, false, true));
        assertTrue(CompareUtils.areStringsEqual(Test.GENERAL_STRING, Test.GENERAL_STRING, true, false));
        assertTrue(CompareUtils.areStringsEqual(Test.GENERAL_STRING, Test.GENERAL_STRING, false, false));

        assertFalse(CompareUtils.areStringsEqual(Test.GENERAL_STRING, Test.GENERAL_APP_ID, true, true));
        assertFalse(CompareUtils.areStringsEqual(Test.GENERAL_STRING, Test.GENERAL_APP_ID, false, true));
        assertFalse(CompareUtils.areStringsEqual(Test.GENERAL_STRING, Test.GENERAL_APP_ID, true, false));
        assertFalse(CompareUtils.areStringsEqual(Test.GENERAL_STRING, Test.GENERAL_APP_ID, false, false));

        assertFalse(CompareUtils.areStringsEqual(Test.GENERAL_STRING, Test.GENERAL_STRING.toUpperCase(), false, false));
        assertFalse(CompareUtils.areStringsEqual(Test.GENERAL_STRING, Test.GENERAL_STRING.toUpperCase(), false, true));
        assertTrue(CompareUtils.areStringsEqual(Test.GENERAL_STRING, Test.GENERAL_STRING.toUpperCase(), true, false));
        assertTrue(CompareUtils.areStringsEqual(Test.GENERAL_STRING, Test.GENERAL_STRING.toUpperCase(), true, true));

        assertFalse(CompareUtils.areStringsEqual(Test.GENERAL_STRING.toUpperCase(), Test.GENERAL_STRING, false, false));
        assertFalse(CompareUtils.areStringsEqual(Test.GENERAL_STRING.toUpperCase(), Test.GENERAL_STRING, false, true));
        assertTrue(CompareUtils.areStringsEqual(Test.GENERAL_STRING.toUpperCase(), Test.GENERAL_STRING.toUpperCase(), true, false));
        assertTrue(CompareUtils.areStringsEqual(Test.GENERAL_STRING.toUpperCase(), Test.GENERAL_STRING.toUpperCase(), true, true));

        assertTrue(CompareUtils.areStringsEqual(null, null, true, true));
        assertFalse(CompareUtils.areStringsEqual(null, null, true, false));
        assertTrue(CompareUtils.areStringsEqual(null, null, false, true));
        assertFalse(CompareUtils.areStringsEqual(null, null, false, false));

        assertFalse(CompareUtils.areStringsEqual(Test.GENERAL_STRING, null, true, true));
        assertFalse(CompareUtils.areStringsEqual(Test.GENERAL_STRING, null, true, false));
        assertFalse(CompareUtils.areStringsEqual(Test.GENERAL_STRING, null, false, true));
        assertFalse(CompareUtils.areStringsEqual(Test.GENERAL_STRING, null, false, false));

        assertFalse(CompareUtils.areStringsEqual(null, Test.GENERAL_STRING, false, true));
        assertFalse(CompareUtils.areStringsEqual(null, Test.GENERAL_STRING, false, false));
        assertFalse(CompareUtils.areStringsEqual(null, Test.GENERAL_STRING, true, true));
        assertFalse(CompareUtils.areStringsEqual(null, Test.GENERAL_STRING, true, false));

    }
}
