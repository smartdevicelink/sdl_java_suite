package com.smartdevicelink.proxy;

import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.proxy.interfaces.IProxyListener;
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
	public static SdlProxy buildSdlProxy(IProxyListener listener, String sAppName, String sAppId) {
		SdlProxy ret = null;
		try {
			ret = new SdlProxy(listener,sAppName,sAppId);
		} catch (SdlException e) {
			e.printStackTrace();
		}
		return ret;
	}	
}