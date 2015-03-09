package com.smartdevicelink.abstraction.listeners;

import com.smartdevicelink.proxy.rpc.OnAudioPassThru;

public interface AudioPassThruListener extends NotificationListener{

	public void handleAudioData(OnAudioPassThru audioPass);
	
}
