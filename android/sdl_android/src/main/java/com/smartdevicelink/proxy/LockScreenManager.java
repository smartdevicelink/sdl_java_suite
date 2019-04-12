/*
 * Copyright (c) 2017 - 2019, SmartDeviceLink Consortium, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the SmartDeviceLink Consortium, Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from this 
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.smartdevicelink.proxy;

import android.graphics.Bitmap;

import com.smartdevicelink.proxy.rpc.OnLockScreenStatus;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.LockScreenStatus;
import com.smartdevicelink.util.AndroidTools;

import java.io.IOException;

public class LockScreenManager {
	
    public interface OnLockScreenIconDownloadedListener{
        public void onLockScreenIconDownloaded(Bitmap icon);
        public void onLockScreenIconDownloadError(Exception e);
    }
    
    private Bitmap lockScreenIcon;
	private Boolean bUserSelected = false;
	private Boolean bDriverDistStatus = null;
	private HMILevel  hmiLevel = null;
	@SuppressWarnings("unused")
    private int iSessionID;

	public synchronized void setSessionID(int iVal)
	{
			iSessionID = iVal;
	}
	
	private synchronized void setUserSelectedStatus(boolean bVal)
	{
			bUserSelected = bVal;
	}	

	public synchronized void setDriverDistStatus(boolean bVal)
	{
			bDriverDistStatus = bVal;	
	}

	public synchronized void setHMILevel(HMILevel hmiVal)
	{		
		hmiLevel = hmiVal;

		if (hmiVal != null) {
			if ((hmiVal.equals(HMILevel.HMI_FULL)) || (hmiVal.equals(HMILevel.HMI_LIMITED)))
				setUserSelectedStatus(true);
			else if (hmiVal.equals(HMILevel.HMI_NONE))
				setUserSelectedStatus(false);
		}else{
			setUserSelectedStatus(false);
		}
	}
	
	public synchronized OnLockScreenStatus getLockObj(/*int SessionID*/)
	{
		//int iSessionID = SessionID;
		OnLockScreenStatus myLock = new OnLockScreenStatus();
		myLock.setDriverDistractionStatus(bDriverDistStatus);
		myLock.setHMILevel(hmiLevel);
		myLock.setUserSelected(bUserSelected);
		myLock.setShowLockScreen(getLockScreenStatus());
		
		return myLock;
	}
	
	private synchronized LockScreenStatus getLockScreenStatus() 
	{
		
		if ( (hmiLevel == null) || (hmiLevel.equals(HMILevel.HMI_NONE)) )
		{
			return LockScreenStatus.OFF;
		}
		else if ( hmiLevel.equals(HMILevel.HMI_BACKGROUND) )
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
		else if ( (hmiLevel.equals(HMILevel.HMI_FULL)) || (hmiLevel.equals(HMILevel.HMI_LIMITED)) )
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

    public void downloadLockScreenIcon(final String url, final OnLockScreenIconDownloadedListener l){
        new Thread(new Runnable(){
            @Override
            public void run(){
                try{
                    lockScreenIcon = AndroidTools.downloadImage(url);
                    if(l != null){
                        l.onLockScreenIconDownloaded(lockScreenIcon);
                    }
                }catch(IOException e){
                    if(l != null){
                        l.onLockScreenIconDownloadError(e);
                    }
                }
            }
        }).start();
    }

    public Bitmap getLockScreenIcon(){
        return this.lockScreenIcon;
    }
}
