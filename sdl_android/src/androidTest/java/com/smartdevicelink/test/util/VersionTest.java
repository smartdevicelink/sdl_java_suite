package com.smartdevicelink.test.util;

import android.test.AndroidTestCase;

import com.smartdevicelink.util.Version;

public class VersionTest extends AndroidTestCase {

    private static final String TEST_VERSION = "1.2.3";

    public void testConstructorCorrect(){
        Version version = new Version(TEST_VERSION);
        assertEquals(1, version.getMajor());
        assertEquals(2, version.getMinor());
        assertEquals(3, version.getPatch());
    }

    public void testConstructorIncorrect(){
        try{
            Version version = new Version("1.2");
        }catch (Exception e){
            assert true;
        }
        assert false;
    }

    public void testToString(){
        Version version = new Version(TEST_VERSION);
        assertEquals(version.toString(), TEST_VERSION);
    }
}
