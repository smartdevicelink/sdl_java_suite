package com.smartdevicelink.abstraction.listeners;

import java.util.Hashtable;

import com.smartdevicelink.abstraction.listeners.AudioPassThruListener;
import com.smartdevicelink.proxy.rpc.PerformAudioPassThru;

public class PerformAudioPassThruWithListener extends PerformAudioPassThru {
	
	private AudioPassThruListener mListener;
    public PerformAudioPassThruWithListener() { }

    public PerformAudioPassThruWithListener(Hashtable hash) {
        super(hash);
    }
	public AudioPassThruListener getListener() {
		return mListener;
	}

	public void setListener(AudioPassThruListener mListener) {
		this.mListener = mListener;
	}

}
