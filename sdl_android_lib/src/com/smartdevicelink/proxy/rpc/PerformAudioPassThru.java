package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.AudioType;
import com.smartdevicelink.proxy.rpc.enums.BitsPerSample;
import com.smartdevicelink.proxy.rpc.enums.SamplingRate;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;
import com.smartdevicelink.util.JsonUtils;

/**
 * This will open an audio pass thru session. By doing so the app can receive
 * audio data through the vehicle�s microphone
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
	public static final String KEY_MAX_DURATION = "maxDuration";
	public static final String KEY_AUDIO_PASS_THRU_DISPLAY_TEXT_1 = "audioPassThruDisplayText1";
	public static final String KEY_AUDIO_PASS_THRU_DISPLAY_TEXT_2 = "audioPassThruDisplayText2";
    public static final String KEY_MUTE_AUDIO = "muteAudio";
    public static final String KEY_SAMPLING_RATE = "samplingRate";
    public static final String KEY_AUDIO_TYPE = "audioType";
    public static final String KEY_INITIAL_PROMPT = "initialPrompt";
    public static final String KEY_BITS_PER_SAMPLE = "bitsPerSample";
	
    private List<TTSChunk> initialPrompt;
    private String text1, text2;
    private String samplingRate; // represents SamplingRate enum
    private String bitsPerSample; // represents BitsPerSample enum
    private String audioType; // represents AudioType enum
    private Integer maxDuration;
    private Boolean muteAudio;
    
	/**
	 * Constructs a new PerformAudioPassThru object
	 */
    public PerformAudioPassThru() {
        super(FunctionID.PERFORM_AUDIO_PASS_THRU);
    }

    /**
     * Creates a PerformAudioPassThru object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public PerformAudioPassThru(JSONObject jsonObject){
        super(SdlCommand.PERFORM_AUDIO_PASSTHRU, jsonObject);
        jsonObject = getParameters(jsonObject);
        switch(sdlVersion){
        default:
            this.text1 = JsonUtils.readStringFromJsonObject(jsonObject, KEY_AUDIO_PASS_THRU_DISPLAY_TEXT_1);
            this.text2 = JsonUtils.readStringFromJsonObject(jsonObject, KEY_AUDIO_PASS_THRU_DISPLAY_TEXT_2);
            this.samplingRate = JsonUtils.readStringFromJsonObject(jsonObject, KEY_SAMPLING_RATE);
            this.bitsPerSample = JsonUtils.readStringFromJsonObject(jsonObject, KEY_BITS_PER_SAMPLE);
            this.audioType = JsonUtils.readStringFromJsonObject(jsonObject, KEY_AUDIO_TYPE);
            this.maxDuration = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_MAX_DURATION);
            this.muteAudio = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_MUTE_AUDIO);
            
            List<JSONObject> ttsObjs = JsonUtils.readJsonObjectListFromJsonObject(jsonObject, KEY_INITIAL_PROMPT);
            if(ttsObjs != null){
                this.initialPrompt = new ArrayList<TTSChunk>(ttsObjs.size());
                for(JSONObject ttsObj : ttsObjs){
                    this.initialPrompt.add(new TTSChunk(ttsObj));
                }
            }
            break;
        }
    }

	/**
	 * Sets initial prompt which will be spoken before opening the audio pass
	 * thru session by SDL
	 * 
	 * @param initialPrompt
	 *            a List<TTSChunk> value represents the initial prompt which
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
    public void setInitialPrompt(List<TTSChunk> initialPrompt) {
    	this.initialPrompt = initialPrompt;
    }

	/**
	 * Gets a List value representing an initial prompt which will be spoken
	 * before opening the audio pass thru session by SDL
	 * 
	 * @return List<TTSChunk> -a List value representing an initial prompt
	 *         which will be spoken before opening the audio pass thru session
	 *         by SDL
	 */
    public List<TTSChunk> getInitialPrompt() {
    	return this.initialPrompt;
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
    	this.text1 = audioPassThruDisplayText1;
    }

	/**
	 * Gets a first line of text displayed during audio capture
	 * 
	 * @return String -a String value representing a first line of text
	 *         displayed during audio capture
	 */
    public String getAudioPassThruDisplayText1() {
    	return this.text1;
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
    	this.text2 = audioPassThruDisplayText2;
    }

	/**
	 * Gets a second line of text displayed during audio capture
	 * 
	 * @return String -a String value representing a first line of text
	 *         displayed during audio capture
	 */
    public String getAudioPassThruDisplayText2() {
    	return this.text2;
    }

	/**
	 * Sets a samplingRate
	 * 
	 * @param samplingRate
	 *            a SamplingRate value representing a 8 or 16 or 22 or 24 khz
	 */
    public void setSamplingRate(SamplingRate samplingRate) {
    	this.samplingRate = (samplingRate == null) ? null : samplingRate.getJsonName(sdlVersion);
    }

	/**
	 * Gets a samplingRate
	 * 
	 * @return SamplingRate -a SamplingRate value
	 */
    public SamplingRate getSamplingRate() {
    	return SamplingRate.valueForJsonName(this.samplingRate, sdlVersion);
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
    	this.maxDuration = maxDuration;
    }

	/**
	 * Gets a max duration of audio recording in milliseconds
	 * 
	 * @return int -an int value representing the maximum duration of audio
	 *         recording in milliseconds
	 */
    public Integer getMaxDuration() {
    	return this.maxDuration;
    }

	/**
	 * Sets the quality the audio is recorded - 8 bit or 16 bit
	 * 
	 * @param audioQuality
	 *            a BitsPerSample value representing 8 bit or 16 bit
	 */
    public void setBitsPerSample(BitsPerSample audioQuality) {
    	this.bitsPerSample = (audioQuality == null) ? null : audioQuality.getJsonName(sdlVersion);
    }

	/**
	 * Gets a BitsPerSample value, 8 bit or 16 bit
	 * 
	 * @return BitsPerSample -a BitsPerSample value
	 */
    public BitsPerSample getBitsPerSample() {
    	return BitsPerSample.valueForJsonName(this.bitsPerSample, sdlVersion);
    }

	/**
	 * Sets an audioType
	 * 
	 * @param audioType
	 *            an audioType
	 */
    public void setAudioType(AudioType audioType) {
    	this.audioType = (audioType == null) ? null : audioType.getJsonName(sdlVersion);
    }

	/**
	 * Gets a type of audio data
	 * 
	 * @return AudioType -an AudioType
	 */
    public AudioType getAudioType() {
    	return AudioType.valueForJsonName(this.audioType, sdlVersion);
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
    	return this.muteAudio;
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
    	this.muteAudio = muteAudio;
    }@Override
public JSONObject getJsonParameters(int sdlVersion){
    JSONObject result = super.getJsonParameters(sdlVersion);
    
    switch(sdlVersion){
    default:
        JsonUtils.addToJsonObject(result, KEY_AUDIO_PASS_THRU_DISPLAY_TEXT_1, this.text1);
        JsonUtils.addToJsonObject(result, KEY_AUDIO_PASS_THRU_DISPLAY_TEXT_2, this.text2);
        JsonUtils.addToJsonObject(result, KEY_AUDIO_TYPE, this.audioType);
        JsonUtils.addToJsonObject(result, KEY_BITS_PER_SAMPLE, this.bitsPerSample);
        JsonUtils.addToJsonObject(result, KEY_MAX_DURATION, this.maxDuration);
        JsonUtils.addToJsonObject(result, KEY_MUTE_AUDIO, this.muteAudio);
        JsonUtils.addToJsonObject(result, KEY_SAMPLING_RATE, this.samplingRate);
        
        JsonUtils.addToJsonObject(result, KEY_INITIAL_PROMPT, (this.initialPrompt == null) ? null : 
                JsonUtils.createJsonArrayOfJsonObjects(this.initialPrompt, sdlVersion));
        break;
    }
    
    return result;
}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((audioType == null) ? 0 : audioType.hashCode());
		result = prime * result
				+ ((bitsPerSample == null) ? 0 : bitsPerSample.hashCode());
		result = prime * result
				+ ((initialPrompt == null) ? 0 : initialPrompt.hashCode());
		result = prime * result
				+ ((maxDuration == null) ? 0 : maxDuration.hashCode());
		result = prime * result
				+ ((muteAudio == null) ? 0 : muteAudio.hashCode());
		result = prime * result
				+ ((samplingRate == null) ? 0 : samplingRate.hashCode());
		result = prime * result + ((text1 == null) ? 0 : text1.hashCode());
		result = prime * result + ((text2 == null) ? 0 : text2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) { 
			return true;
		}
		if (obj == null) { 
			return false;
		}
		if (getClass() != obj.getClass()) { 
			return false;
		}
		PerformAudioPassThru other = (PerformAudioPassThru) obj;
		if (audioType == null) {
			if (other.audioType != null) { 
				return false;
			}
		} else if (!audioType.equals(other.audioType)) { 
			return false;
		}
		if (bitsPerSample == null) {
			if (other.bitsPerSample != null) { 
				return false;
			}
		} else if (!bitsPerSample.equals(other.bitsPerSample)) { 
			return false;
		}
		if (initialPrompt == null) {
			if (other.initialPrompt != null) { 
				return false;
			}
		} else if (!initialPrompt.equals(other.initialPrompt)) { 
			return false;
		}
		if (maxDuration == null) {
			if (other.maxDuration != null) { 
				return false;
			}
		} else if (!maxDuration.equals(other.maxDuration)) { 
			return false;
		}
		if (muteAudio == null) {
			if (other.muteAudio != null) { 
				return false;
			}
		} else if (!muteAudio.equals(other.muteAudio)) { 
			return false;
		}
		if (samplingRate == null) {
			if (other.samplingRate != null) { 
				return false;
			}
		} else if (!samplingRate.equals(other.samplingRate)) { 
			return false;
		}
		if (text1 == null) {
			if (other.text1 != null) { 
				return false;
			}
		} else if (!text1.equals(other.text1)) { 
			return false;
		}
		if (text2 == null) {
			if (other.text2 != null) { 
				return false;
			}
		} else if (!text2.equals(other.text2)) { 
			return false;
		}
		return true;
	}
}
