package com.smartdevicelink.test.util;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.smartdevicelink.util.Version;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

@RunWith(AndroidJUnit4.class)
public class VersionTest {

    private static final String TEST_VERSION_STRING = "1.2.3";
    private static final Version TEST_VERSION = new Version(1, 2, 3);

    @Test
    public void testConstructorCorrect() {
        Version version = new Version(TEST_VERSION_STRING);
        assertEquals(1, version.getMajor());
        assertEquals(2, version.getMinor());
        assertEquals(3, version.getPatch());
    }

    @Test
    public void testConstructorIncorrect() {
        try {
            Version version = new Version("1.2");
            fail();
        } catch (Exception e) {}
    }

    @Test
    public void testToString() {
        Version version = new Version(TEST_VERSION_STRING);
        assertEquals(version.toString(), TEST_VERSION_STRING);
    }

    @Test
    public void testisNewerThan() {
        Version version1 = new Version(5, 0, 0);

        //Supplied version is newer
        assertEquals(-1, version1.isNewerThan(new Version(6, 0, 0)));
        assertEquals(-1, version1.isNewerThan(new Version(5, 1, 0)));
        assertEquals(-1, version1.isNewerThan(new Version(5, 0, 1)));

        //Supplied version is older
        assertEquals(1, version1.isNewerThan(new Version(4, 0, 0)));
        assertEquals(1, version1.isNewerThan(new Version(4, 1, 0)));
        assertEquals(1, version1.isNewerThan(new Version(4, 0, 1)));

        //Supplied  version is equal
        assertEquals(0, version1.isNewerThan(new Version(5, 0, 0)));

    }

    @Test
    public void testIsBetween() {

        assertEquals(TEST_VERSION.isBetween(new Version(1, 0, 0), new Version(2, 0, 0)), 1);
        assertEquals(TEST_VERSION.isBetween(new Version(2, 0, 0), new Version(1, 0, 0)), -1);
        assertEquals(TEST_VERSION.isBetween(new Version(2, 0, 0), new Version(3, 0, 0)), -1);

        assertEquals(TEST_VERSION.isBetween(new Version(1, 0, 0), new Version(1, 2, 3)), 0);
        assertEquals(TEST_VERSION.isBetween(new Version(1, 2, 3), new Version(3, 2, 3)), 0);

        assertEquals(TEST_VERSION.isBetween(new Version(1, 2, 3), new Version(1, 2, 3)), 0);

    }
}
