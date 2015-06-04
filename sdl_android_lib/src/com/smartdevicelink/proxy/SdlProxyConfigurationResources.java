package com.smartdevicelink.proxy;

import android.telephony.TelephonyManager;

public class SdlProxyConfigurationResources {
	private String sdlConfigurationFilePath;
	private TelephonyManager telephonyManager;
	
	public SdlProxyConfigurationResources() {
		this(null, null);
	}
	
	public SdlProxyConfigurationResources(String sdlConfigurationFilePath, 
			TelephonyManager telephonyManager) {
		this.sdlConfigurationFilePath = sdlConfigurationFilePath;
		this.telephonyManager = telephonyManager;
	}
	
	public void setSdlConfigurationFilePath(String sdlConfigurationFilePath) {
		this.sdlConfigurationFilePath = sdlConfigurationFilePath;
	}
	
	public String getSdlConfigurationFilePath() {
		return sdlConfigurationFilePath;
	}
	
	public void setTelephonyManager(TelephonyManager telephonyManager) {
		this.telephonyManager = telephonyManager;
	}
	
	public TelephonyManager getTelephonyManager() {
		return telephonyManager;
	}
}
