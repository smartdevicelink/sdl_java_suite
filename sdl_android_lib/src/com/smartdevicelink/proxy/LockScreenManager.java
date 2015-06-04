package com.smartdevicelink.proxy;

import com.smartdevicelink.proxy.rpc.OnLockScreenStatus;
import com.smartdevicelink.proxy.rpc.enums.HmiLevel;
import com.smartdevicelink.proxy.rpc.enums.LockScreenStatus;

public class LockScreenManager {
	
	private Boolean bUserSelected = false;
	private Boolean bDriverDistStatus = null;
	private HmiLevel  hmiLevel = null;
	@SuppressWarnings("unused")
    private int iSessionId;

	public synchronized void setSessionId(int iVal)
	{
			iSessionId = iVal;
	}
	
	private synchronized void setUserSelectedStatus(boolean bVal)
	{
			bUserSelected = bVal;
	}	

	public synchronized void setDriverDistStatus(boolean bVal)
	{
			bDriverDistStatus = bVal;	
	}

	public synchronized void setHMILevel(HmiLevel hmiVal)
	{		
		hmiLevel = hmiVal;
		
		if ( (hmiVal.equals(HmiLevel.HMI_FULL)) || (hmiVal.equals(HmiLevel.HMI_LIMITED)) )
			setUserSelectedStatus(true);
		else if (hmiVal.equals(HmiLevel.HMI_NONE))
			setUserSelectedStatus(false);				
	}
	
	public synchronized OnLockScreenStatus getLockObj(/*int SessionID*/)
	{
		//int iSessionID = SessionID;
		OnLockScreenStatus myLock = new OnLockScreenStatus();
		myLock.setDriverDistractionStatus(bDriverDistStatus);
		myLock.setHmiLevel(hmiLevel);
		myLock.setUserSelected(bUserSelected);
		myLock.setShowLockScreen(getLockScreenStatus());
		
		return myLock;
	}
	
	private synchronized LockScreenStatus getLockScreenStatus() 
	{
		
		if ( (hmiLevel == null) || (hmiLevel.equals(HmiLevel.HMI_NONE)) )
		{
			return LockScreenStatus.OFF;
		}
		else if ( hmiLevel.equals(HmiLevel.HMI_BACKGROUND) )
		{
			if (bDriverDistStatus == null)
			{
				//we don't have driver distraction, lockscreen is entirely based on userselection
				if (bUserSelected)
					return LockScreenStatus.REQUIRED;
				else
					return LockScreenStatus.OFF;
			}
			else if (bDriverDistStatus && bUserSelected)
			{
				return LockScreenStatus.REQUIRED;
			}
			else if (!bDriverDistStatus && bUserSelected)
			{
				return LockScreenStatus.OPTIONAL;
			}
			else
			{
				return LockScreenStatus.OFF;
			}
		}
		else if ( (hmiLevel.equals(HmiLevel.HMI_FULL)) || (hmiLevel.equals(HmiLevel.HMI_LIMITED)) )
		{
			if ( (bDriverDistStatus != null) && (!bDriverDistStatus) )
			{
				return LockScreenStatus.OPTIONAL;
			}
			else
				return LockScreenStatus.REQUIRED;
		}
		return LockScreenStatus.OFF;
	}
}
