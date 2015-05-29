package com.smartdevicelink.test.proxy;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.LockScreenManager;
import com.smartdevicelink.proxy.rpc.OnLockScreenStatus;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.LockScreenStatus;
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.LockScreenManager}
 */
public class LockScreenManagerTest extends TestCase{

	/**
	 * This is a unit test for the following methods : 
	 * {@link com.smartdevicelink.proxy.LockScreenManager#setDriverDistStatus(boolean)}
	 */
    public void testDriverDistStatus(){
        LockScreenManager lockMan = new LockScreenManager();
        lockMan.setDriverDistStatus(true);
        assertEquals(Test.MATCH, true, (boolean) lockMan.getLockObj().getDriverDistractionStatus());

        lockMan.setDriverDistStatus(false);
        assertEquals(Test.MATCH, false, (boolean) lockMan.getLockObj().getDriverDistractionStatus());
    }

    /**
	 * This is a unit test for the following methods : 
	 * {@link com.smartdevicelink.proxy.LockScreenManager#setHMILevel(HMILevel)}
	 */
    public void testHmiLevelStatus(){
        LockScreenManager lockMan = new LockScreenManager();
        lockMan.setHMILevel(HMILevel.HMI_BACKGROUND);
        assertEquals(Test.MATCH, HMILevel.HMI_BACKGROUND, lockMan.getLockObj().getHMILevel());

        lockMan.setHMILevel(HMILevel.HMI_FULL);
        assertEquals(Test.MATCH, HMILevel.HMI_FULL, lockMan.getLockObj().getHMILevel());

        lockMan.setHMILevel(HMILevel.HMI_LIMITED);
        assertEquals(Test.MATCH, HMILevel.HMI_LIMITED, lockMan.getLockObj().getHMILevel());

        lockMan.setHMILevel(HMILevel.HMI_NONE);
        assertEquals(Test.MATCH, HMILevel.HMI_NONE, lockMan.getLockObj().getHMILevel());

        lockMan.setHMILevel(null);
        assertNull(Test.NULL, lockMan.getLockObj().getHMILevel());
    }

    /**
     * Test the lock screen status when no setter methods are called.
     */
    public void testLockScreenStatusNull(){
        LockScreenManager lockMan = new LockScreenManager();
        OnLockScreenStatus result = lockMan.getLockObj();
        assertNotNull(Test.NOT_NULL, result);
        assertEquals(Test.MATCH, LockScreenStatus.OFF, result.getShowLockScreen());
    }

    /**
     * Test the invalid settings of lock screen status.
     */
    public void testLockScreenStatusDriverDistNull(){
        LockScreenManager lockMan = new LockScreenManager();
      
        OnLockScreenStatus result = lockMan.getLockObj();
        assertEquals(Test.MATCH, LockScreenStatus.OFF, result.getShowLockScreen());

        lockMan.setHMILevel(HMILevel.HMI_NONE);
        result = lockMan.getLockObj();
        assertEquals(Test.MATCH, LockScreenStatus.OFF, result.getShowLockScreen());

        lockMan.setHMILevel(HMILevel.HMI_BACKGROUND);
        result = lockMan.getLockObj();
        assertEquals(Test.MATCH, LockScreenStatus.OFF, result.getShowLockScreen());

        lockMan.setHMILevel(HMILevel.HMI_FULL);
        result = lockMan.getLockObj();
        assertEquals(Test.MATCH, LockScreenStatus.REQUIRED, result.getShowLockScreen());

        lockMan.setHMILevel(HMILevel.HMI_LIMITED);
        result = lockMan.getLockObj();
        assertEquals(Test.MATCH, LockScreenStatus.REQUIRED, result.getShowLockScreen());
    }

    /**
     * Test the enabled settings of lock screen status.
     */
    public void testLockScreenStatusDriverDistEnabled(){
        LockScreenManager lockMan = new LockScreenManager();
        lockMan.setDriverDistStatus(true);

        // HMI level is null
        OnLockScreenStatus result = lockMan.getLockObj();
        assertEquals(Test.MATCH, LockScreenStatus.OFF, result.getShowLockScreen());

        lockMan.setHMILevel(HMILevel.HMI_NONE);
        result = lockMan.getLockObj();
        assertEquals(Test.MATCH, LockScreenStatus.OFF, result.getShowLockScreen());

        lockMan.setHMILevel(HMILevel.HMI_BACKGROUND);
        result = lockMan.getLockObj();
        assertEquals(Test.MATCH, LockScreenStatus.OFF, result.getShowLockScreen());

        lockMan.setHMILevel(HMILevel.HMI_FULL);
        result = lockMan.getLockObj();
        assertEquals(Test.MATCH, LockScreenStatus.REQUIRED, result.getShowLockScreen());

        lockMan.setHMILevel(HMILevel.HMI_LIMITED);
        result = lockMan.getLockObj();
        assertEquals(Test.MATCH, LockScreenStatus.REQUIRED, result.getShowLockScreen());
    }

    /**
     * Test the disabled settings of lock screen status.
     */
    public void testLockScreenStatusDriverDistDisabled(){
        LockScreenManager lockMan = new LockScreenManager();
        lockMan.setDriverDistStatus(false);

        OnLockScreenStatus result = lockMan.getLockObj();
        assertEquals(Test.MATCH, LockScreenStatus.OFF, result.getShowLockScreen());

        lockMan.setHMILevel(HMILevel.HMI_NONE);
        result = lockMan.getLockObj();
        assertEquals(Test.MATCH, LockScreenStatus.OFF, result.getShowLockScreen());

        lockMan.setHMILevel(HMILevel.HMI_BACKGROUND);
        result = lockMan.getLockObj();
        assertEquals(Test.MATCH, LockScreenStatus.OFF, result.getShowLockScreen());

        lockMan.setHMILevel(HMILevel.HMI_FULL);
        result = lockMan.getLockObj();
        assertEquals(Test.MATCH, LockScreenStatus.OPTIONAL, result.getShowLockScreen());

        lockMan.setHMILevel(HMILevel.HMI_LIMITED);
        result = lockMan.getLockObj();
        assertEquals(Test.MATCH, LockScreenStatus.OPTIONAL, result.getShowLockScreen());
    }
}