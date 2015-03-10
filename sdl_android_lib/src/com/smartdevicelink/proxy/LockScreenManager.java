package com.smartdevicelink.proxy;

import com.smartdevicelink.proxy.rpc.OnLockScreenStatus;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.LockScreenStatus;

public class LockScreenManager{

    private Boolean  bDriverDistStatus = null;
    private HMILevel hmiLevel          = null;

    @Deprecated
    public synchronized void setSessionID(int iVal){/* do nothing */}

    public synchronized void setDriverDistStatus(Boolean bVal){
        bDriverDistStatus = bVal;
    }

    public synchronized void setHMILevel(HMILevel hmiVal){
        hmiLevel = hmiVal;
    }

    public synchronized OnLockScreenStatus getLockObj(){
        OnLockScreenStatus myLock = new OnLockScreenStatus();
        myLock.setDriverDistractionStatus(bDriverDistStatus);
        myLock.setHMILevel(hmiLevel);
        myLock.setUserSelected(calculateUserSelected(hmiLevel));
        myLock.setShowLockScreen(getLockScreenStatus());

        return myLock;
    }

    private synchronized LockScreenStatus getLockScreenStatus(){
        LockScreenStatus result = LockScreenStatus.OFF;
        HMILevel temp = ( hmiLevel == null ) ? HMILevel.HMI_NONE : hmiLevel;

        if(calculateUserSelected(temp)){
            result = driverDistStatus();
        }

        return result;
    }

    private synchronized LockScreenStatus driverDistStatus(){
        if(bDriverDistStatus == null || bDriverDistStatus){
            return LockScreenStatus.REQUIRED;
        }
        return LockScreenStatus.OPTIONAL;
    }

    private static boolean calculateUserSelected(HMILevel hmiLevel){
        return ( hmiLevel != null && ( hmiLevel == HMILevel.HMI_FULL || hmiLevel == HMILevel.HMI_LIMITED ) );
    }
}
