package com.smartdevicelink.proxy.interfaces;

import com.smartdevicelink.proxy.rpc.OnAppInterfaceUnregistered;
import com.smartdevicelink.proxy.rpc.RegisterAppInterfaceResponse;
import com.smartdevicelink.proxy.rpc.UnregisterAppInterfaceResponse;

public interface IProxyListener extends IProxyListenerBase{
	// Adds Legacy Life-cycle Management call-backs to the IProxyListenerAbstract interface
	
	public void onProxyOpened();
	
	public void onRegisterAppInterfaceResponse(RegisterAppInterfaceResponse response);

	public void onOnAppInterfaceUnregistered(OnAppInterfaceUnregistered notification);
	
	public void onUnregisterAppInterfaceResponse(UnregisterAppInterfaceResponse response);
	
}
