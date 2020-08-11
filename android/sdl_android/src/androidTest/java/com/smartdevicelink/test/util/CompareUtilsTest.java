package com.smartdevicelink.test.util;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.util.CompareUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

@RunWith(AndroidJUnit4.class)
public class CompareUtilsTest {

    @Test
    public void testAreStringsEqual(){

        assertTrue(CompareUtils.areStringsEqual(TestValues.GENERAL_STRING, TestValues.GENERAL_STRING, true, true));
        assertTrue(CompareUtils.areStringsEqual(TestValues.GENERAL_STRING, TestValues.GENERAL_STRING, false, true));
        assertTrue(CompareUtils.areStringsEqual(TestValues.GENERAL_STRING, TestValues.GENERAL_STRING, true, false));
        assertTrue(CompareUtils.areStringsEqual(TestValues.GENERAL_STRING, TestValues.GENERAL_STRING, false, false));

        assertFalse(CompareUtils.areStringsEqual(TestValues.GENERAL_STRING, TestValues.GENERAL_APP_ID, true, true));
        assertFalse(CompareUtils.areStringsEqual(TestValues.GENERAL_STRING, TestValues.GENERAL_APP_ID, false, true));
        assertFalse(CompareUtils.areStringsEqual(TestValues.GENERAL_STRING, TestValues.GENERAL_APP_ID, true, false));
        assertFalse(CompareUtils.areStringsEqual(TestValues.GENERAL_STRING, TestValues.GENERAL_APP_ID, false, false));

        assertFalse(CompareUtils.areStringsEqual(TestValues.GENERAL_STRING, TestValues.GENERAL_STRING.toUpperCase(), false, false));
        assertFalse(CompareUtils.areStringsEqual(TestValues.GENERAL_STRING, TestValues.GENERAL_STRING.toUpperCase(), false, true));
        assertTrue(CompareUtils.areStringsEqual(TestValues.GENERAL_STRING, TestValues.GENERAL_STRING.toUpperCase(), true, false));
        assertTrue(CompareUtils.areStringsEqual(TestValues.GENERAL_STRING, TestValues.GENERAL_STRING.toUpperCase(), true, true));

        assertFalse(CompareUtils.areStringsEqual(TestValues.GENERAL_STRING.toUpperCase(), TestValues.GENERAL_STRING, false, false));
        assertFalse(CompareUtils.areStringsEqual(TestValues.GENERAL_STRING.toUpperCase(), TestValues.GENERAL_STRING, false, true));
        assertTrue(CompareUtils.areStringsEqual(TestValues.GENERAL_STRING.toUpperCase(), TestValues.GENERAL_STRING.toUpperCase(), true, false));
        assertTrue(CompareUtils.areStringsEqual(TestValues.GENERAL_STRING.toUpperCase(), TestValues.GENERAL_STRING.toUpperCase(), true, true));

        assertTrue(CompareUtils.areStringsEqual(null, null, true, true));
        assertFalse(CompareUtils.areStringsEqual(null, null, true, false));
        assertTrue(CompareUtils.areStringsEqual(null, null, false, true));
        assertFalse(CompareUtils.areStringsEqual(null, null, false, false));

        assertFalse(CompareUtils.areStringsEqual(TestValues.GENERAL_STRING, null, true, true));
        assertFalse(CompareUtils.areStringsEqual(TestValues.GENERAL_STRING, null, true, false));
        assertFalse(CompareUtils.areStringsEqual(TestValues.GENERAL_STRING, null, false, true));
        assertFalse(CompareUtils.areStringsEqual(TestValues.GENERAL_STRING, null, false, false));

        assertFalse(CompareUtils.areStringsEqual(null, TestValues.GENERAL_STRING, false, true));
        assertFalse(CompareUtils.areStringsEqual(null, TestValues.GENERAL_STRING, false, false));
        assertFalse(CompareUtils.areStringsEqual(null, TestValues.GENERAL_STRING, true, true));
        assertFalse(CompareUtils.areStringsEqual(null, TestValues.GENERAL_STRING, true, false));

    }
}
