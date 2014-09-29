package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.Vector;

import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.constants.Names;
import com.smartdevicelink.proxy.rpc.enums.AudioType;
import com.smartdevicelink.proxy.rpc.enums.BitsPerSample;
import com.smartdevicelink.proxy.rpc.enums.SamplingRate;
import com.smartdevicelink.util.DebugTool;

/**
 * This will open an audio pass thru session. By doing so the app can receive
 * audio data through the vehicle’s microphone
 * <p>
 * Function Group: AudioPassThru
 * <p>
 * <b>HMILevel needs to be FULL, LIMITED or BACKGROUND</b>
 * </p>
 * 
 * @since SmartDeviceLink 2.0
 * @see EndAudioPassThru
 */
public class PerformAudioPassThru extends RPCRequest {
	
	/**
	 * Constructs a new PerformAudioPassThru object
	 */
    public PerformAudioPassThru() {
        super("PerformAudioPassThru");
    }

	/**
	 * Constructs a new PerformAudioPassThru object indicated by the Hashtable
	 * parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public PerformAudioPassThru(Hashtable hash) {
        super(hash);
    }

	/**
	 * Sets initial prompt which will be spoken before opening the audio pass
	 * thru session by SDL
	 * 
	 * @param initialPrompt
	 *            a Vector<TTSChunk> value represents the initial prompt which
	 *            will be spoken before opening the audio pass thru session by
	 *            SDL
	 *            <p>
	 *            <b>Notes: </b>
	 *            <ul>
	 *            <li>This is an array of text chunks of type TTSChunk</li>
	 *            <li>The array must have at least one item</li>
	 *            <li>If omitted, then no initial prompt is spoken</li>
	 *            <li>Array Minsize: 1</li>
	 *            <li>Array Maxsize: 100</li>
	 *            </ul>
	 */
    public void setInitialPrompt(Vector<TTSChunk> initialPrompt) {
    	if (initialPrompt != null) {
    		parameters.put(Names.initialPrompt, initialPrompt);
    	} else {
    		parameters.remove(Names.initialPrompt);
    	}
    }

	/**
	 * Gets a Vector value representing an initial prompt which will be spoken
	 * before opening the audio pass thru session by SDL
	 * 
	 * @return Vector<TTSChunk> -a Vector value representing an initial prompt
	 *         which will be spoken before opening the audio pass thru session
	 *         by SDL
	 */
    public Vector<TTSChunk> getInitialPrompt() {
    	if (parameters.get(Names.initialPrompt) instanceof Vector<?>) {
	        Vector<?> list = (Vector<?>)parameters.get(Names.initialPrompt);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof TTSChunk) {
	                return (Vector<TTSChunk>) list;
	            } else if (obj instanceof Hashtable) {
	                Vector<TTSChunk> newList = new Vector<TTSChunk>();
	                for (Object hashObj : list) {
	                    newList.add(new TTSChunk((Hashtable)hashObj));
	                }
	                return newList;
	            }
	        }
    	}
        return null;
    }

	/**
	 * Sets a line of text displayed during audio capture
	 * 
	 * @param audioPassThruDisplayText1
	 *            a String value representing the line of text displayed during
	 *            audio capture
	 *            <p>
	 *            <b>Notes: </b>Maxlength=500
	 */
    public void setAudioPassThruDisplayText1(String audioPassThruDisplayText1) {
    	if (audioPassThruDisplayText1 != null) {
    		parameters.put(Names.audioPassThruDisplayText1, audioPassThruDisplayText1);
    	} else {
    		parameters.remove(Names.audioPassThruDisplayText1);
    	}
    }

	/**
	 * Gets a first line of text displayed during audio capture
	 * 
	 * @return String -a String value representing a first line of text
	 *         displayed during audio capture
	 */
    public String getAudioPassThruDisplayText1() {
    	return (String) parameters.get(Names.audioPassThruDisplayText1);
    }

	/**
	 * Sets a line of text displayed during audio capture
	 * 
	 * @param audioPassThruDisplayText2
	 *            a String value representing the line of text displayed during
	 *            audio capture
	 *            <p>
	 *            <b>Notes: </b>Maxlength=500
	 */
    public void setAudioPassThruDisplayText2(String audioPassThruDisplayText2) {
    	if (audioPassThruDisplayText2 != null) {
    		parameters.put(Names.audioPassThruDisplayText2, audioPassThruDisplayText2);
    	} else {
    		parameters.remove(Names.audioPassThruDisplayText2);
    	}
    }

	/**
	 * Gets a second line of text displayed during audio capture
	 * 
	 * @return String -a String value representing a first line of text
	 *         displayed during audio capture
	 */
    public String getAudioPassThruDisplayText2() {
    	return (String) parameters.get(Names.audioPassThruDisplayText2);
    }

	/**
	 * Sets a samplingRate
	 * 
	 * @param samplingRate
	 *            a SamplingRate value representing a 8 or 16 or 22 or 24 khz
	 */
    public void setSamplingRate(SamplingRate samplingRate) {
    	if (samplingRate != null) {
    		parameters.put(Names.samplingRate, samplingRate);
    	} else {
    		parameters.remove(Names.samplingRate);
    	}
    }

	/**
	 * Gets a samplingRate
	 * 
	 * @return SamplingRate -a SamplingRate value
	 */
    public SamplingRate getSamplingRate() {
    	Object obj = parameters.get(Names.samplingRate);
    	if (obj instanceof SamplingRate) {
    		return (SamplingRate) obj;
    	} else if (obj instanceof String) {
    		SamplingRate theCode = null;
            try {
                theCode = SamplingRate.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.samplingRate, e);
            }
            return theCode;
    	}
        return null;
    }

	/**
	 * Sets the maximum duration of audio recording in milliseconds
	 * 
	 * @param maxDuration
	 *            an Integer value representing the maximum duration of audio
	 *            recording in millisecond
	 *            <p>
	 *            <b>Notes: </b>Minvalue:1; Maxvalue:1000000
	 */
    public void setMaxDuration(Integer maxDuration) {
    	if (maxDuration != null) {
    		parameters.put(Names.maxDuration, maxDuration);
    	} else {
    		parameters.remove(Names.maxDuration);
    	}
    }

	/**
	 * Gets a max duration of audio recording in milliseconds
	 * 
	 * @return int -an int value representing the maximum duration of audio
	 *         recording in milliseconds
	 */
    public int getMaxDuration() {
    	return (Integer) parameters.get(Names.maxDuration);
    }

	/**
	 * Sets the quality the audio is recorded - 8 bit or 16 bit
	 * 
	 * @param audioQuality
	 *            a BitsPerSample value representing 8 bit or 16 bit
	 */
    public void setBitsPerSample(BitsPerSample audioQuality) {
    	if (audioQuality != null) {
    		parameters.put(Names.bitsPerSample, audioQuality);
    	} else {
    		parameters.remove(Names.bitsPerSample);
    	}
    }

	/**
	 * Gets a BitsPerSample value, 8 bit or 16 bit
	 * 
	 * @return BitsPerSample -a BitsPerSample value
	 */
    public BitsPerSample getBitsPerSample() {
    	Object obj = parameters.get(Names.bitsPerSample);
    	if (obj instanceof BitsPerSample) {
    		return (BitsPerSample) obj;
    	} else if (obj instanceof String) {
    		BitsPerSample theCode = null;
            try {
                theCode = BitsPerSample.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.bitsPerSample, e);
            }
            return theCode;
    	}
        return null;
    }

	/**
	 * Sets an audioType
	 * 
	 * @param audioType
	 *            an audioType
	 */
    public void setAudioType(AudioType audioType) {
    	if (audioType != null) {
    		parameters.put(Names.audioType, audioType);
    	} else {
    		parameters.remove(Names.audioType);
    	}
    }

	/**
	 * Gets a type of audio data
	 * 
	 * @return AudioType -an AudioType
	 */
    public AudioType getAudioType() {
    	Object obj = parameters.get(Names.audioType);
    	if (obj instanceof AudioType) {
    		return (AudioType) obj;
    	} else if (obj instanceof String) {
    		AudioType theCode = null;
            try {
                theCode = AudioType.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.audioType, e);
            }
            return theCode;
    	}
        return null;
    }

	/**
	 * Gets a Boolean value representing if the current audio source should be
	 * muted during the APT session<br/>
	 * 
	 * 
	 * @return Boolean -a Boolean value representing if the current audio source
	 *         should be muted during the APT session
	 */
    public Boolean getMuteAudio() {
    	return (Boolean) parameters.get(Names.muteAudio);
    }

	/**
	 * Sets a muteAudio value representing if the current audio source should be
	 * muted during the APT session<br/>
	 * If not, the audio source will play without interruption. If omitted, the
	 * value is set to true
	 * <p>
	 * 
	 * @param muteAudio
	 *            a Boolean value representing if the current audio source
	 *            should be muted during the APT session
	 */
    public void setMuteAudio(Boolean muteAudio) {
    	if (muteAudio != null) {
    		parameters.put(Names.muteAudio, muteAudio);
    	} else {
    		parameters.remove(Names.muteAudio);
    	}
    }    
}
