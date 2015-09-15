package com.smartdevicelink.proxy;

import android.telephony.TelephonyManager;

public class SdlProxyConfigurationResources {
	private String _sdlConfigurationFilePath;
	private TelephonyManager _telephonyManager;
	private String _sessionUUID;
	
	public SdlProxyConfigurationResources() {
		this(null, null);
	}
	
	public SdlProxyConfigurationResources(String sdlConfigurationFilePath, 
			TelephonyManager telephonyManager) {
		_sdlConfigurationFilePath = sdlConfigurationFilePath;
		_telephonyManager = telephonyManager;
	}
	
	public void setSdlConfigurationFilePath(String sdlConfigurationFilePath) {
		_sdlConfigurationFilePath = sdlConfigurationFilePath;
	}
	
	public String getSdlConfigurationFilePath() {
		return _sdlConfigurationFilePath;
	}
	
	public void setTelephonyManager(TelephonyManager telephonyManager) {
		_telephonyManager = telephonyManager;
	}
	
	public TelephonyManager getTelephonyManager() {
		return _telephonyManager;
	}

	public void setSessionUUID(String sessionUID) {
		_sessionUUID = sessionUID;
	}
	
	public String getSessionUUID() {
		return _sessionUUID;
	}
}
