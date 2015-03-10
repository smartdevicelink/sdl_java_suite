package com.smartdevicelink.test.proxy;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.LockScreenManager;
import com.smartdevicelink.proxy.rpc.OnLockScreenStatus;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.LockScreenStatus;

public class LockScreenManagerTest extends TestCase{
    private static final String ERROR_MSG = "Input value didn't match result.";

    public void testDriverDistStatus(){
        LockScreenManager lockMan = new LockScreenManager();
        lockMan.setDriverDistStatus(true);
        assertEquals(ERROR_MSG, true, (boolean) lockMan.getLockObj().getDriverDistractionStatus());

        lockMan.setDriverDistStatus(false);
        assertEquals(ERROR_MSG, false, (boolean) lockMan.getLockObj().getDriverDistractionStatus());

        lockMan.setDriverDistStatus(null);
        assertNull(ERROR_MSG, lockMan.getLockObj().getDriverDistractionStatus());
    }

    public void testHmiLevelStatus(){
        LockScreenManager lockMan = new LockScreenManager();
        lockMan.setHMILevel(HMILevel.HMI_BACKGROUND);
        assertEquals(ERROR_MSG, HMILevel.HMI_BACKGROUND, lockMan.getLockObj().getHMILevel());

        lockMan.setHMILevel(HMILevel.HMI_FULL);
        assertEquals(ERROR_MSG, HMILevel.HMI_FULL, lockMan.getLockObj().getHMILevel());

        lockMan.setHMILevel(HMILevel.HMI_LIMITED);
        assertEquals(ERROR_MSG, HMILevel.HMI_LIMITED, lockMan.getLockObj().getHMILevel());

        lockMan.setHMILevel(HMILevel.HMI_NONE);
        assertEquals(ERROR_MSG, HMILevel.HMI_NONE, lockMan.getLockObj().getHMILevel());

        lockMan.setHMILevel(null);
        assertEquals(ERROR_MSG, null, lockMan.getLockObj().getHMILevel());
    }

    // test lock screen status when no setters are called
    public void testLockScreenStatusNull(){
        LockScreenManager lockMan = new LockScreenManager();
        OnLockScreenStatus result = lockMan.getLockObj();
        assertNotNull(ERROR_MSG, result);
        assertEquals(ERROR_MSG, LockScreenStatus.OFF, result.getShowLockScreen());
    }

    public void testLockScreenStatusDriverDistNull(){
        LockScreenManager lockMan = new LockScreenManager();
        // driver dist status is null

        // HMI level is null
        OnLockScreenStatus result = lockMan.getLockObj();
        assertEquals(ERROR_MSG, LockScreenStatus.OFF, result.getShowLockScreen());

        lockMan.setHMILevel(HMILevel.HMI_NONE);
        result = lockMan.getLockObj();
        assertEquals(ERROR_MSG, LockScreenStatus.OFF, result.getShowLockScreen());

        lockMan.setHMILevel(HMILevel.HMI_BACKGROUND);
        result = lockMan.getLockObj();
        assertEquals(ERROR_MSG, LockScreenStatus.OFF, result.getShowLockScreen());

        lockMan.setHMILevel(HMILevel.HMI_FULL);
        result = lockMan.getLockObj();
        assertEquals(ERROR_MSG, LockScreenStatus.REQUIRED, result.getShowLockScreen());

        lockMan.setHMILevel(HMILevel.HMI_LIMITED);
        result = lockMan.getLockObj();
        assertEquals(ERROR_MSG, LockScreenStatus.REQUIRED, result.getShowLockScreen());
    }

    public void testLockScreenStatusDriverDistEnabled(){
        LockScreenManager lockMan = new LockScreenManager();
        lockMan.setDriverDistStatus(true);

        // HMI level is null
        OnLockScreenStatus result = lockMan.getLockObj();
        assertEquals(ERROR_MSG, LockScreenStatus.OFF, result.getShowLockScreen());

        lockMan.setHMILevel(HMILevel.HMI_NONE);
        result = lockMan.getLockObj();
        assertEquals(ERROR_MSG, LockScreenStatus.OFF, result.getShowLockScreen());

        lockMan.setHMILevel(HMILevel.HMI_BACKGROUND);
        result = lockMan.getLockObj();
        assertEquals(ERROR_MSG, LockScreenStatus.OFF, result.getShowLockScreen());

        lockMan.setHMILevel(HMILevel.HMI_FULL);
        result = lockMan.getLockObj();
        assertEquals(ERROR_MSG, LockScreenStatus.REQUIRED, result.getShowLockScreen());

        lockMan.setHMILevel(HMILevel.HMI_LIMITED);
        result = lockMan.getLockObj();
        assertEquals(ERROR_MSG, LockScreenStatus.REQUIRED, result.getShowLockScreen());
    }

    public void testLockScreenStatusDriverDistDisabled(){
        LockScreenManager lockMan = new LockScreenManager();
        lockMan.setDriverDistStatus(false);

        // HMI level is null
        OnLockScreenStatus result = lockMan.getLockObj();
        assertEquals(ERROR_MSG, LockScreenStatus.OFF, result.getShowLockScreen());

        lockMan.setHMILevel(HMILevel.HMI_NONE);
        result = lockMan.getLockObj();
        assertEquals(ERROR_MSG, LockScreenStatus.OFF, result.getShowLockScreen());

        lockMan.setHMILevel(HMILevel.HMI_BACKGROUND);
        result = lockMan.getLockObj();
        assertEquals(ERROR_MSG, LockScreenStatus.OFF, result.getShowLockScreen());

        lockMan.setHMILevel(HMILevel.HMI_FULL);
        result = lockMan.getLockObj();
        assertEquals(ERROR_MSG, LockScreenStatus.OPTIONAL, result.getShowLockScreen());

        lockMan.setHMILevel(HMILevel.HMI_LIMITED);
        result = lockMan.getLockObj();
        assertEquals(ERROR_MSG, LockScreenStatus.OPTIONAL, result.getShowLockScreen());
    }

}
