package com.smartdevicelink.abstraction;

import com.smartdevicelink.abstraction.listeners.AudioPassThruListener;
import com.smartdevicelink.proxy.rpc.PerformAudioPassThru;

public class PerformAudioPassThruWithListener  extends PerformAudioPassThru {
	
	private AudioPassThruListener mListener;

	public AudioPassThruListener getListener() {
		return mListener;
	}

	public void setListener(AudioPassThruListener mListener) {
		this.mListener = mListener;
	}
}
