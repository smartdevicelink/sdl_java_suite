/*
 * Copyright (c) 2017 - 2020, SmartDeviceLink Consortium, Inc.
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
 * Neither the name of the SmartDeviceLink Consortium Inc. nor the names of
 * its contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
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
package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;

public class HMICapabilities extends RPCStruct{
    public static final String KEY_NAVIGATION = "navigation";
    public static final String KEY_PHONE_CALL = "phoneCall";
    public static final String KEY_VIDEO_STREAMING = "videoStreaming";
    public static final String KEY_REMOTE_CONTROL = "remoteControl";
    public static final String KEY_APP_SERVICES = "appServices";
    public static final String KEY_DISPLAYS = "displays";
    public static final String KEY_SEAT_LOCATION = "seatLocation";
    public static final String KEY_DRIVER_DISTRACTION = "driverDistraction";

	public HMICapabilities() { }
	  
	 public HMICapabilities(Hashtable<String, Object> hash) {
		 super(hash);
	 }
	 
	 public boolean isNavigationAvailable(){
		 Object available = getValue(KEY_NAVIGATION);
		 if(available == null){
			 return false;
		 }
		 return (Boolean)available;
	 }
	 
	 public void setNavigationAvilable(Boolean available){
		 setValue(KEY_NAVIGATION, available);
	 }
	 
	 public boolean isPhoneCallAvailable(){
		 Object available = getValue(KEY_PHONE_CALL);
		 if(available == null){
			 return false;
		 }
		 return (Boolean)available;
	 }
	 
	 public void setPhoneCallAvilable(Boolean available){
		 setValue(KEY_PHONE_CALL, available);
	 }

	public boolean isVideoStreamingAvailable(){
		Object available = getValue(KEY_VIDEO_STREAMING);
		if(available == null){
			return false;
		}
		return (Boolean)available;
	}

	public void setVideoStreamingAvailable(Boolean available){
		setValue(KEY_VIDEO_STREAMING, available);
	}

	public boolean isRemoteControlAvailable(){
		Object available = getValue(KEY_REMOTE_CONTROL);
		if(available == null){
			return false;
		}
		return (Boolean)available;
	}

	public void setRemoteControlAvailable(Boolean available){
		setValue(KEY_REMOTE_CONTROL, available);
	}

	public boolean isAppServicesAvailable(){
		Object available = getValue(KEY_APP_SERVICES);
		if(available == null){
			return false;
		}
		return (Boolean)available;
	}

	public void setAppServicesAvailable(Boolean available){
		setValue(KEY_APP_SERVICES, available);
	}

	public boolean isDisplaysCapabilityAvailable(){
		Object available = getValue(KEY_DISPLAYS);
		if(available == null){
			return false;
		}
		return (Boolean)available;
	}

	public void setDisplaysCapabilityAvailable(Boolean available){
		setValue(KEY_DISPLAYS, available);
	}

	public boolean isSeatLocationAvailable(){
		Object available = getValue(KEY_SEAT_LOCATION);
		if(available == null){
			return false;
		}
		return (Boolean)available;
	}

	public void setSeatLocationAvailable(Boolean available){
		setValue(KEY_SEAT_LOCATION, available);
	}
    /**
     * Sets the driverDistraction.
     *
     * @param driverDistraction Availability of driver distraction capability. True: Available, False: Not Available
     * @since SmartDeviceLink 7.0.0
     */
    public void setDriverDistraction(Boolean driverDistraction) {
        setValue(KEY_DRIVER_DISTRACTION, driverDistraction);
    }

    /**
     * Gets the driverDistraction.
     *
     * @return Boolean Availability of driver distraction capability. True: Available, False: Not Available
     * @since SmartDeviceLink 7.0.0
     */
    public Boolean isDriverDistractionAvailable() {
        return getBoolean(KEY_DRIVER_DISTRACTION);
    }
}
