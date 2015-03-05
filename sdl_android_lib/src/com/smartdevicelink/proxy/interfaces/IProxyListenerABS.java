package com.smartdevicelink.proxy.interfaces;

import com.smartdevicelink.proxy.rpc.RegisterAppInterfaceResponse;

public interface IProxyListenerABS extends IProxyListenerALM{
	
	public void onRegisterAppInterfaceResponse(RegisterAppInterfaceResponse response);
	
	public void onResumeDataPersistence(Boolean bSuccess);
	
}
