package com.smartdevicelink.abstraction.listeners;

public interface AudioPassThruListener extends NotificationListener{

	public void handleAudioData(byte[] data);
	
}
