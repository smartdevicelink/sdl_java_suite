package com.smartdevicelink.proxy;

import com.smartdevicelink.exception.SdlException;
@Deprecated
public class SdlProxyFactory {
	
	@Deprecated
	public static SdlProxy buildSdlProxy(IProxyListener listener) {
		SdlProxy ret = null;
		try {
			ret = new SdlProxy(listener);
		} catch (SdlException e) {
			e.printStackTrace();
		}
		return ret;
	}
	@Deprecated
	public static SdlProxy buildSdlProxy(IProxyListener listener, String sAppName, String sAppID) {
		SdlProxy ret = null;
		try {
			ret = new SdlProxy(listener,sAppName,sAppID);
		} catch (SdlException e) {
			e.printStackTrace();
		}
		return ret;
	}	
}