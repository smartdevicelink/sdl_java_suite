package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.AudioType;
import com.smartdevicelink.proxy.rpc.enums.BitsPerSample;
import com.smartdevicelink.proxy.rpc.enums.SamplingRate;

/**
 * This will open an audio pass thru session. By doing so the app can receive
 * audio data through the vehicles microphone
 * 
 * <p>Function Group: AudioPassThru</p>
 * 
 * <b>HMILevel needs to be FULL, LIMITED or BACKGROUND</b>
 * 
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 *                 <th>Reg.</th>
 *               <th>Notes</th>
 * 			<th> Version</th>
 * 		</tr>
 * 		<tr>
 * 			<td>initialPrompt</td>
 * 			<td>TTSChunk[]</td>
 * 			<td>SDL will speak this prompt before opening the audio pass thru session. </td>
 *                 <td>N</td>
 *                 <td>This is an array of text chunks of type TTSChunk. The array must have at least one item If omitted, then no initial prompt is spoken: <p>Array Minsize: 1</p> Array Maxsize: 100</td>
 * 			<td>SmartDeviceLink 2.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>audioPassThruDisplayText1</td>
 * 			<td>String</td>
 * 			<td>First line of text displayed during audio capture.</td>
 *                 <td>N</td>
 *                 <td>Maxlength = 500</td>
 * 			<td>SmartDeviceLink 2.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>samplingRate</td>
 * 			<td>SamplingRate</td>
 * 			<td>This value shall is allowed to be 8 or 16 or 22 or 44 khz.</td>
 *                 <td>Y</td>
 *                 <td></td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>maxDuration</td>
 * 			<td>Integer</td>
 * 			<td>The maximum duration of audio recording in milliseconds.</td>
 *                 <td>Y</td>
 *                 <td>Minvalue: 1; Maxvalue: 1000000</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>bitsPerSample</td>
 * 			<td>BitsPerSample</td>
 * 			<td>Specifies the quality the audio is recorded - 8 bit or 16 bit.</td>
 *                 <td>Y</td>
 *                 <td></td>
 * 			<td>SmartDeviceLink 2.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>audioType</td>
 * 			<td>AudioType</td>
 * 			<td>Specifies the type of audio data being requested.</td>
 *                 <td>Y</td>
 *                 <td></td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>muteAudio</td>
 * 			<td>Boolean</td>
 * 			<td>N</td>
 *                 <td>N</td>
 *                 <td></td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *
 * 		
 * 			
 * 			
 * 			
 *  </table>
 * @since SmartDeviceLink 2.0
 * @see EndAudioPassThru
 */
public class PerformAudioPassThru extends RPCRequest {
	public static final String KEY_MAX_DURATION = "maxDuration";
	public static final String KEY_AUDIO_PASS_THRU_DISPLAY_TEXT_1 = "audioPassThruDisplayText1";
	public static final String KEY_AUDIO_PASS_THRU_DISPLAY_TEXT_2 = "audioPassThruDisplayText2";
    public static final String KEY_MUTE_AUDIO = "muteAudio";
    public static final String KEY_SAMPLING_RATE = "samplingRate";
    public static final String KEY_AUDIO_TYPE = "audioType";
    public static final String KEY_INITIAL_PROMPT = "initialPrompt";
    public static final String KEY_BITS_PER_SAMPLE = "bitsPerSample";
	
	/**
	 * Constructs a new PerformAudioPassThru object
	 */
    public PerformAudioPassThru() {
        super(FunctionID.PERFORM_AUDIO_PASS_THRU.toString());
    }

	/**
	 * <p>Constructs a new PerformAudioPassThru object indicated by the Hashtable
	 * parameter</p>
	 * 
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public PerformAudioPassThru(Hashtable<String, Object> hash) {
        super(hash);
    }

	/**
	 * Sets initial prompt which will be spoken before opening the audio pass
	 * thru session by SDL
	 * 
	 * @param initialPrompt
	 *            a List<TTSChunk> value represents the initial prompt which
	 *            will be spoken before opening the audio pass thru session by
	 *            SDL
	 *            <p></p>
	 *            <b>Notes: </b>
	 *            <ul>
	 *            <li>This is an array of text chunks of type TTSChunk</li>
	 *            <li>The array must have at least one item</li>
	 *            <li>If omitted, then no initial prompt is spoken</li>
	 *            <li>Array Minsize: 1</li>
	 *            <li>Array Maxsize: 100</li>
	 *            </ul>
	 */
    public void setInitialPrompt(List<TTSChunk> initialPrompt) {
    	if (initialPrompt != null) {
    		parameters.put(KEY_INITIAL_PROMPT, initialPrompt);
    	} else {
    		parameters.remove(KEY_INITIAL_PROMPT);
    	}
    }

	/**
	 * Gets a List value representing an initial prompt which will be spoken
	 * before opening the audio pass thru session by SDL
	 * 
	 * @return List<TTSChunk> -a List value representing an initial prompt
	 *         which will be spoken before opening the audio pass thru session
	 *         by SDL
	 */
    @SuppressWarnings("unchecked")
    public List<TTSChunk> getInitialPrompt() {
    	if (parameters.get(KEY_INITIAL_PROMPT) instanceof List<?>) {
    		List<?> list = (List<?>)parameters.get(KEY_INITIAL_PROMPT);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof TTSChunk) {
	                return (List<TTSChunk>) list;
	            } else if (obj instanceof Hashtable) {
	            	List<TTSChunk> newList = new ArrayList<TTSChunk>();
	                for (Object hashObj : list) {
	                    newList.add(new TTSChunk((Hashtable<String, Object>)hashObj));
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
	 *            <p>a String value representing the line of text displayed during
	 *            audio capture</p>
	 *            <p></p>
	 *            <b>Notes: </b>Maxlength=500
	 */
    public void setAudioPassThruDisplayText1(String audioPassThruDisplayText1) {
    	if (audioPassThruDisplayText1 != null) {
    		parameters.put(KEY_AUDIO_PASS_THRU_DISPLAY_TEXT_1, audioPassThruDisplayText1);
    	} else {
    		parameters.remove(KEY_AUDIO_PASS_THRU_DISPLAY_TEXT_1);
    	}
    }

	/**
	 * Gets a first line of text displayed during audio capture
	 * 
	 * @return String -a String value representing a first line of text
	 *         displayed during audio capture
	 */
    public String getAudioPassThruDisplayText1() {
    	return (String) parameters.get(KEY_AUDIO_PASS_THRU_DISPLAY_TEXT_1);
    }

	/**
	 * Sets a line of text displayed during audio capture
	 * 
	 * @param audioPassThruDisplayText2
	 *            <p>a String value representing the line of text displayed during
	 *            audio capture</p>
	 *            <p></p>
	 *            <b>Notes: </b>Maxlength=500
	 */
    public void setAudioPassThruDisplayText2(String audioPassThruDisplayText2) {
    	if (audioPassThruDisplayText2 != null) {
    		parameters.put(KEY_AUDIO_PASS_THRU_DISPLAY_TEXT_2, audioPassThruDisplayText2);
    	} else {
    		parameters.remove(KEY_AUDIO_PASS_THRU_DISPLAY_TEXT_2);
    	}
    }

	/**
	 * Gets a second line of text displayed during audio capture
	 * 
	 * @return String -a String value representing a first line of text
	 *         displayed during audio capture
	 */
    public String getAudioPassThruDisplayText2() {
    	return (String) parameters.get(KEY_AUDIO_PASS_THRU_DISPLAY_TEXT_2);
    }

	/**
	 * Sets a samplingRate
	 * 
	 * @param samplingRate
	 *            a SamplingRate value representing a 8 or 16 or 22 or 24 khz
	 */
    public void setSamplingRate(SamplingRate samplingRate) {
    	if (samplingRate != null) {
    		parameters.put(KEY_SAMPLING_RATE, samplingRate);
    	} else {
    		parameters.remove(KEY_SAMPLING_RATE);
    	}
    }

	/**
	 * Gets a samplingRate
	 * 
	 * @return SamplingRate -a SamplingRate value
	 */
    public SamplingRate getSamplingRate() {
    	Object obj = parameters.get(KEY_SAMPLING_RATE);
    	if (obj instanceof SamplingRate) {
    		return (SamplingRate) obj;
    	} else if (obj instanceof String) {
    		return SamplingRate.valueForString((String) obj);
    	}
        return null;
    }

	/**
	 * Sets the maximum duration of audio recording in milliseconds
	 * 
	 * @param maxDuration
	 *            an Integer value representing the maximum duration of audio
	 *            recording in millisecond
	 *            <p></p>
	 *            <b>Notes: </b>Minvalue:1; Maxvalue:1000000
	 */
    public void setMaxDuration(Integer maxDuration) {
    	if (maxDuration != null) {
    		parameters.put(KEY_MAX_DURATION, maxDuration);
    	} else {
    		parameters.remove(KEY_MAX_DURATION);
    	}
    }

	/**
	 * Gets a max duration of audio recording in milliseconds
	 * 
	 * @return int -an int value representing the maximum duration of audio
	 *         recording in milliseconds
	 */
    public Integer getMaxDuration() {
    	return (Integer) parameters.get(KEY_MAX_DURATION);
    }

	/**
	 * Sets the quality the audio is recorded - 8 bit or 16 bit
	 * 
	 * @param audioQuality
	 *            a BitsPerSample value representing 8 bit or 16 bit
	 */
    public void setBitsPerSample(BitsPerSample audioQuality) {
    	if (audioQuality != null) {
    		parameters.put(KEY_BITS_PER_SAMPLE, audioQuality);
    	} else {
    		parameters.remove(KEY_BITS_PER_SAMPLE);
    	}
    }

	/**
	 * Gets a BitsPerSample value, 8 bit or 16 bit
	 * 
	 * @return BitsPerSample -a BitsPerSample value
	 */
    public BitsPerSample getBitsPerSample() {
    	Object obj = parameters.get(KEY_BITS_PER_SAMPLE);
    	if (obj instanceof BitsPerSample) {
    		return (BitsPerSample) obj;
    	} else if (obj instanceof String) {
    		return BitsPerSample.valueForString((String) obj);
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
    		parameters.put(KEY_AUDIO_TYPE, audioType);
    	} else {
    		parameters.remove(KEY_AUDIO_TYPE);
    	}
    }

	/**
	 * Gets a type of audio data
	 * 
	 * @return AudioType -an AudioType
	 */
    public AudioType getAudioType() {
    	Object obj = parameters.get(KEY_AUDIO_TYPE);
    	if (obj instanceof AudioType) {
    		return (AudioType) obj;
    	} else if (obj instanceof String) {
    		return AudioType.valueForString((String) obj);
    	}
        return null;
    }

	/**
	 *<p> Gets a Boolean value representing if the current audio source should be
	 * muted during the APT session</p>
	 * 
	 * 
	 * @return Boolean -a Boolean value representing if the current audio source
	 *         should be muted during the APT session
	 */
    public Boolean getMuteAudio() {
    	return (Boolean) parameters.get(KEY_MUTE_AUDIO);
    }

	/**
	 * <p>Sets a muteAudio value representing if the current audio source should be
	 * muted during the APT session
	 * If not, the audio source will play without interruption. If omitted, the
	 * value is set to true</p>
	 * 
	 * 
	 * @param muteAudio
	 *            a Boolean value representing if the current audio source
	 *            should be muted during the APT session
	 */
    public void setMuteAudio(Boolean muteAudio) {
    	if (muteAudio != null) {
    		parameters.put(KEY_MUTE_AUDIO, muteAudio);
    	} else {
    		parameters.remove(KEY_MUTE_AUDIO);
    	}
    }    
}
