package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.AudioType;
import com.smartdevicelink.proxy.rpc.enums.BitsPerSample;
import com.smartdevicelink.proxy.rpc.enums.SamplingRate;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus;

import java.util.Hashtable;

import static com.smartdevicelink.proxy.rpc.AirbagStatus.KEY_DRIVER_CURTAIN_AIRBAG_DEPLOYED;

/**
 *<p> Describes different audio type configurations for PerformAudioPassThru, e.g. {8kHz,8-bit,PCM}
 * Specifies the capabilities of audio capturing: sampling rate, bits per sample, audio type.</p>
 * 
 * <p><b>Parameter List</b></p>
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
 *  @see ButtonName
 *  @see GetCapabilities
 * @since SmartDeviceLink 2.0
 */
public class AudioPassThruCapabilities extends RPCStruct {
	public static final String KEY_SAMPLING_RATE = "samplingRate";
	public static final String KEY_AUDIO_TYPE = "audioType";
	public static final String KEY_BITS_PER_SAMPLE = "bitsPerSample";
	
	/**
	 * Constructs a newly allocated AudioPassThruCapabilities object
	 */
    public AudioPassThruCapabilities() {}
    
    /**
     * Constructs a newly allocated AudioPassThruCapabilities object indicated by the Hashtable parameter
     * @param hash The Hashtable to use
     */
    public AudioPassThruCapabilities(Hashtable<String, Object> hash) {
        super(hash);
    }
    
    /**
     * set the sampling rate for AudioPassThru
     * @param samplingRate the sampling rate for AudioPassThru
     */
    public void setSamplingRate(SamplingRate samplingRate) {
    	setValue(KEY_SAMPLING_RATE, samplingRate);
    }
    
    /**
     * get the sampling rate for AudioPassThru
     * @return  the sampling rate for AudioPassThru
     */
    public SamplingRate getSamplingRate() {
        return (SamplingRate) getObject(SamplingRate.class, KEY_SAMPLING_RATE);
    }
    
    /**
     * set the sample depth in bit for AudioPassThru
     * @param bitsPerSample the sample depth in bit for AudioPassThru
     */
    public void setBitsPerSample(BitsPerSample bitsPerSample) {
    	setValue(KEY_BITS_PER_SAMPLE, bitsPerSample);
    }
    
    /**
     * get  the sample depth in bit for AudioPassThru
     * @return the sample depth in bit for AudioPassThru
     */
    public BitsPerSample getBitsPerSample() {
        return (BitsPerSample) getObject(BitsPerSample.class, KEY_BITS_PER_SAMPLE);
    }
    
    /**
     * set the audiotype for AudioPassThru
     * @param audioType the audiotype for AudioPassThru
     */
    public void setAudioType(AudioType audioType) {
    	setValue(KEY_AUDIO_TYPE, audioType);
    }
    
    /**
     * get the audiotype for AudioPassThru
     * @return the audiotype for AudioPassThru
     */
    public AudioType getAudioType() {
        return (AudioType) getObject(AudioType.class, KEY_AUDIO_TYPE);
    }
}
