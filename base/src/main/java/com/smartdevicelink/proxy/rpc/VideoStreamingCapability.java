package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;
import java.util.List;

/**
 * Contains information about this system's video streaming capabilities.
 */

public class VideoStreamingCapability extends RPCStruct {
	public static final String KEY_PREFERRED_RESOLUTION = "preferredResolution";
	public static final String KEY_MAX_BITRATE = "maxBitrate";
	public static final String KEY_SUPPORTED_FORMATS = "supportedFormats";
	public static final String KEY_HAPTIC_SPATIAL_DATA_SUPPORTED = "hapticSpatialDataSupported";

	public VideoStreamingCapability(){}
	public VideoStreamingCapability(Hashtable<String, Object> hash){super(hash);}

	public void setPreferredResolution(ImageResolution res){
		setValue(KEY_PREFERRED_RESOLUTION, res);
	}

	public ImageResolution getPreferredResolution(){
		return (ImageResolution) getObject(ImageResolution.class, KEY_PREFERRED_RESOLUTION);
	}

	/**
	 * Set the max bitrate supported by this module.
	 *
	 * <b>NOTE: </b> Unit is in kbps.
	 * @param maxBitrate in kbps
	 */
	public void setMaxBitrate(Integer maxBitrate){
		setValue(KEY_MAX_BITRATE, maxBitrate);
	}

	/**
	 * Retrieves the max bitrate supported by this module.
	 *
	 * <b>NOTE: </b> Unit is in kbps.
	 * @return max bitrate in kbps
	 */
	public Integer getMaxBitrate(){
		return getInteger(KEY_MAX_BITRATE);
	}

	public void setSupportedFormats(List<VideoStreamingFormat> formats){
		setValue(KEY_SUPPORTED_FORMATS, formats);
	}

	public List<VideoStreamingFormat> getSupportedFormats(){
		return (List<VideoStreamingFormat>) getObject(VideoStreamingFormat.class, KEY_SUPPORTED_FORMATS);
	}

	public Boolean getIsHapticSpatialDataSupported() {
		return getBoolean(KEY_HAPTIC_SPATIAL_DATA_SUPPORTED);
	}

	public void setIsHapticSpatialDataSupported(Boolean hapticSpatialDataSupported) {
		setValue(KEY_HAPTIC_SPATIAL_DATA_SUPPORTED, hapticSpatialDataSupported);
	}
}
