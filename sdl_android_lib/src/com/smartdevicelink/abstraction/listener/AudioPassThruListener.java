package com.smartdevicelink.abstraction.listener;

public interface AudioPassThruListener extends NotificationListener{

	public void handleAudioData(byte[] data);
	
}
