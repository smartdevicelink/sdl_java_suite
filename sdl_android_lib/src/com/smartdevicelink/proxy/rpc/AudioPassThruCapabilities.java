package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCObject;
import com.smartdevicelink.proxy.rpc.enums.AudioType;
import com.smartdevicelink.proxy.rpc.enums.BitsPerSample;
import com.smartdevicelink.proxy.rpc.enums.SamplingRate;
import com.smartdevicelink.util.JsonUtils;

/**
 * Describes different audio type configurations for PerformAudioPassThru, e.g. {8kHz,8-bit,PCM}
 * <p><b>Parameter List
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 * 			<th>SmartDeviceLink Ver. Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>samplingRate</td>
 * 			<td>SamplingRate</td>
 * 			<td>Describes the sampling rate for AudioPassThru
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>bitsPerSample</td>
 * 			<td>BitsPerSample</td>
 * 			<td>Describes the sample depth in bit for AudioPassThru
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>audioType</td>
 * 			<td>AudioType</td>
 * 			<td>Describes the audiotype for AudioPassThru
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *  </table>
 * @since SmartDeviceLink 2.0
 */
public class AudioPassThruCapabilities extends RPCObject {
	public static final String KEY_SAMPLING_RATE = "samplingRate";
	public static final String KEY_AUDIO_TYPE = "audioType";
	public static final String KEY_BITS_PER_SAMPLE = "bitsPerSample";
	
	private String samplingRate; // represents SamplingRate enum
	private String bitsPerSample; // represents BitsPerSample enum
	private String audioType; // represents AudioType enum
	
	/**
	 * Constructs a newly allocated AudioPassThruCapabilities object
	 */
    public AudioPassThruCapabilities() {}
    
    /**
     * Creates an AudioPassThruCapabilities object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public AudioPassThruCapabilities(JSONObject jsonObject){
        switch(sdlVersion){
        default:
            this.samplingRate = JsonUtils.readStringFromJsonObject(jsonObject, KEY_SAMPLING_RATE);
            this.bitsPerSample = JsonUtils.readStringFromJsonObject(jsonObject, KEY_BITS_PER_SAMPLE);
            this.audioType = JsonUtils.readStringFromJsonObject(jsonObject, KEY_AUDIO_TYPE);
            break;
        }
    }
    
    /**
     * set the sampling rate for AudioPassThru
     * @param samplingRate the sampling rate for AudioPassThru
     */
    public void setSamplingRate(SamplingRate samplingRate) {
    	this.samplingRate = samplingRate.getJsonName(sdlVersion);
    }
    
    /**
     * get the sampling rate for AudioPassThru
     * @return  the sampling rate for AudioPassThru
     */
    public SamplingRate getSamplingRate() {
        return SamplingRate.valueForJsonName(this.samplingRate, sdlVersion);
    }
    
    /**
     * set the sample depth in bit for AudioPassThru
     * @param bitsPerSample the sample depth in bit for AudioPassThru
     */
    public void setBitsPerSample(BitsPerSample bitsPerSample) {
        this.bitsPerSample = bitsPerSample.getJsonName(sdlVersion);
    }
    
    /**
     * get  the sample depth in bit for AudioPassThru
     * @return the sample depth in bit for AudioPassThru
     */
    public BitsPerSample getBitsPerSample() {
        return BitsPerSample.valueForJsonName(this.bitsPerSample, sdlVersion);
    }
    
    /**
     * set the audiotype for AudioPassThru
     * @param audioType the audiotype for AudioPassThru
     */
    public void setAudioType(AudioType audioType) {
        this.audioType = audioType.getJsonName(sdlVersion);
    }
    
    /**
     * get the audiotype for AudioPassThru
     * @return the audiotype for AudioPassThru
     */
    public AudioType getAudioType() {
        return AudioType.valueForJsonName(this.audioType, sdlVersion);
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_AUDIO_TYPE, this.audioType);
            JsonUtils.addToJsonObject(result, KEY_BITS_PER_SAMPLE, this.bitsPerSample);
            JsonUtils.addToJsonObject(result, KEY_SAMPLING_RATE, this.samplingRate);
            break;
        }
        
        return result;
    }
}
