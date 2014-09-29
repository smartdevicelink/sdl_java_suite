package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.constants.Names;
import com.smartdevicelink.proxy.rpc.enums.AudioType;
import com.smartdevicelink.proxy.rpc.enums.BitsPerSample;
import com.smartdevicelink.proxy.rpc.enums.SamplingRate;
import com.smartdevicelink.util.DebugTool;

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
public class AudioPassThruCapabilities extends RPCStruct {
	
	/**
	 * Constructs a newly allocated AudioPassThruCapabilities object
	 */
    public AudioPassThruCapabilities() {}
    
    /**
     * Constructs a newly allocated AudioPassThruCapabilities object indicated by the Hashtable parameter
     * @param hash The Hashtable to use
     */
    public AudioPassThruCapabilities(Hashtable hash) {
        super(hash);
    }
    
    /**
     * set the sampling rate for AudioPassThru
     * @param samplingRate the sampling rate for AudioPassThru
     */
    public void setSamplingRate(SamplingRate samplingRate) {
    	if (samplingRate != null) {
    		store.put(Names.samplingRate, samplingRate);
    	} else {
    		store.remove(Names.samplingRate);
    	}
    }
    
    /**
     * get the sampling rate for AudioPassThru
     * @return  the sampling rate for AudioPassThru
     */
    public SamplingRate getSamplingRate() {
        Object obj = store.get(Names.samplingRate);
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
     * set the sample depth in bit for AudioPassThru
     * @param bitsPerSample the sample depth in bit for AudioPassThru
     */
    public void setBitsPerSample(BitsPerSample bitsPerSample) {
    	if (bitsPerSample != null) {
    		store.put(Names.bitsPerSample, bitsPerSample);
    	} else {
    		store.remove(Names.bitsPerSample);
    	}
    }
    
    /**
     * get  the sample depth in bit for AudioPassThru
     * @return the sample depth in bit for AudioPassThru
     */
    public BitsPerSample getBitsPerSample() {
        Object obj = store.get(Names.bitsPerSample);
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
     * set the audiotype for AudioPassThru
     * @param audioType the audiotype for AudioPassThru
     */
    public void setAudioType(AudioType audioType) {
    	if (audioType != null) {
    		store.put(Names.audioType, audioType);
    	} else {
    		store.remove(Names.audioType);
    	}
    }
    
    /**
     * get the audiotype for AudioPassThru
     * @return the audiotype for AudioPassThru
     */
    public AudioType getAudioType() {
        Object obj = store.get(Names.audioType);
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
}
