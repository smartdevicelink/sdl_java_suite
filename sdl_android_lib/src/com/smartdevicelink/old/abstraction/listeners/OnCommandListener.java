package com.smartdevicelink.old.abstraction.listeners;

import com.smartdevicelink.old.abstraction.listeners.NotificationListener;
import com.smartdevicelink.proxy.rpc.OnCommand;

public interface OnCommandListener extends NotificationListener {

	public void handleCommand(OnCommand command);
	
}
