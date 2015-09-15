package com.smartdevicelink.abstraction.application.listener;

import com.smartdevicelink.abstraction.constants.SDLVersion;
import com.smartdevicelink.proxy.rpc.OnLockScreenStatus;


public interface IAppLinkLifeCycleListener {
	
	public void onAppLinkConnected(SDLVersion sdlVersion);
	public void onAppLinkDisconnect();
	public void onLockScreenNotification(OnLockScreenStatus status);

}
