package com.smartdevicelink.test.util;

import com.smartdevicelink.AndroidTestCase2;
import com.smartdevicelink.util.Version;

public class VersionTest extends AndroidTestCase2 {

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

    public void testisNewerThan(){
        Version version1 = new Version(5,0,0);

        //Supplied version is newer
        assertEquals(-1,version1.isNewerThan(new Version(6,0,0)));
        assertEquals(-1,version1.isNewerThan( new Version(5,1,0)));
        assertEquals(-1,version1.isNewerThan( new Version(5,0,1)));

        //Supplied version is older
        assertEquals(1,version1.isNewerThan( new Version(4,0,0)));
        assertEquals(1,version1.isNewerThan( new Version(4,1,0)));
        assertEquals(1,version1.isNewerThan( new Version(4,0,1)));

        //Supplied  version is equal
        assertEquals(0,version1.isNewerThan( new Version(5,0,0)));

    }
}
