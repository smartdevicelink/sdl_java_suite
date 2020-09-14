/*
 * Copyright (c) 2017 - 2019, SmartDeviceLink Consortium, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the SmartDeviceLink Consortium, Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from this 
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.util.SdlDataTypeConverter;

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
	public static final String KEY_DIAGONAL_SCREEN_SIZE = "diagonalScreenSize";
	public static final String KEY_PIXEL_PER_INCH = "pixelPerInch";
	public static final String KEY_SCALE = "scale";

	public VideoStreamingCapability(){}
	public VideoStreamingCapability(Hashtable<String, Object> hash){super(hash);}

	public VideoStreamingCapability setPreferredResolution( ImageResolution res) {
        setValue(KEY_PREFERRED_RESOLUTION, res);
        return this;
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
	public VideoStreamingCapability setMaxBitrate( Integer maxBitrate) {
        setValue(KEY_MAX_BITRATE, maxBitrate);
        return this;
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

	public VideoStreamingCapability setSupportedFormats( List<VideoStreamingFormat> formats) {
        setValue(KEY_SUPPORTED_FORMATS, formats);
        return this;
    }

	public List<VideoStreamingFormat> getSupportedFormats(){
		return (List<VideoStreamingFormat>) getObject(VideoStreamingFormat.class, KEY_SUPPORTED_FORMATS);
	}

	@Deprecated
	public Boolean getIsHapticSpatialDataSupported() {
		return getBoolean(KEY_HAPTIC_SPATIAL_DATA_SUPPORTED);
	}

	@Deprecated
	public VideoStreamingCapability setIsHapticSpatialDataSupported( Boolean hapticSpatialDataSupported) {
        setValue(KEY_HAPTIC_SPATIAL_DATA_SUPPORTED, hapticSpatialDataSupported);
        return this;
    }

	public Boolean isHapticSpatialDataSupported() {
		return getBoolean(KEY_HAPTIC_SPATIAL_DATA_SUPPORTED);
	}

	public VideoStreamingCapability setHapticSpatialDataSupported( Boolean hapticSpatialDataSupported) {
		setValue(KEY_HAPTIC_SPATIAL_DATA_SUPPORTED, hapticSpatialDataSupported);
		return this;
	}

	/**
	 * @return the diagonal screen size in inches.
	 */
	public Double getDiagonalScreenSize() {
	    Object object = getValue(KEY_DIAGONAL_SCREEN_SIZE);
		return SdlDataTypeConverter.objectToDouble(object);
	}

	/**
	 * @param diagonalScreenSize the diagonal screen size in inches.
	 */
	public VideoStreamingCapability setDiagonalScreenSize( Double diagonalScreenSize) {
        setValue(KEY_DIAGONAL_SCREEN_SIZE, diagonalScreenSize);
        return this;
    }

	/**
	 * @return the diagonal resolution in pixels divided by the diagonal screen size in inches.
	 */
	public Double getPixelPerInch() {
	    Object object = getValue(KEY_PIXEL_PER_INCH);
	    return SdlDataTypeConverter.objectToDouble(object);
	}

	/**
	 * @param pixelPerInch the diagonal resolution in pixels divided by the diagonal screen size in inches.
	 */
	public VideoStreamingCapability setPixelPerInch( Double pixelPerInch) {
        setValue(KEY_PIXEL_PER_INCH, pixelPerInch);
        return this;
    }

	/**
	 * @return the scaling factor the app should use to change the size of the projecting view.
	 */
	public Double getScale() {
	    Object object = getValue(KEY_SCALE);
	    return SdlDataTypeConverter.objectToDouble(object);
	}

	/**
	 * @param scale the scaling factor the app should use to change the size of the projecting view.
	 */
	public VideoStreamingCapability setScale( Double scale) {
        setValue(KEY_SCALE, scale);
        return this;
    }
}
