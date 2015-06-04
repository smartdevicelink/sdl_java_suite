package com.smartdevicelink.proxy.interfaces;

import com.smartdevicelink.rpc.notifications.OnAppInterfaceUnregistered;
import com.smartdevicelink.rpc.responses.RegisterAppInterfaceResponse;
import com.smartdevicelink.rpc.responses.UnregisterAppInterfaceResponse;

public interface IProxyListener extends IProxyListenerBase{
	// Adds Legacy Life-cycle Management call-backs to the IProxyListenerAbstract interface
	
	public void onProxyOpened();
	
	public void onRegisterAppInterfaceResponse(RegisterAppInterfaceResponse response);

	public void onOnAppInterfaceUnregistered(OnAppInterfaceUnregistered notification);
	
	public void onUnregisterAppInterfaceResponse(UnregisterAppInterfaceResponse response);
	
}
