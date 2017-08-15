package com.smartdevicelink.streaming;

import android.content.Intent;
import android.util.DisplayMetrics;

import com.smartdevicelink.proxy.rpc.ImageResolution;
import com.smartdevicelink.proxy.rpc.VideoStreamingFormat;
import com.smartdevicelink.proxy.rpc.enums.VideoStreamingCodec;
import com.smartdevicelink.proxy.rpc.enums.VideoStreamingProtocol;

public class VideoStreamingParams {
	private final VideoStreamingProtocol DEFAULT_PROTOCOL = VideoStreamingProtocol.RAW;
	private final VideoStreamingCodec DEFAULT_CODEC = VideoStreamingCodec.H264;
	private final int DEFAULT_WIDTH = 800;
	private final int DEFAULT_HEIGHT = 480;
	private final int DEFAULT_DENSITY = DisplayMetrics.DENSITY_HIGH;
	private final int DEFAULT_FRAMERATE = 24;
	private final int DEFAULT_BITRATE = 512000;
	private final int DEFAULT_INTERVAL = 5;


	private int displayDensity = DEFAULT_DENSITY;
	private int frameRate = DEFAULT_FRAMERATE;
	private int bitrate = DEFAULT_BITRATE;
	private int interval = DEFAULT_INTERVAL;
	private VideoStreamingFormat format;
	private ImageResolution resolution;

    public VideoStreamingParams(){
	    format = new VideoStreamingFormat();
	    format.setProtocol(DEFAULT_PROTOCOL);
	    format.setCodec(DEFAULT_CODEC);
	    resolution = new ImageResolution();
	    resolution.setResolutionWidth(DEFAULT_WIDTH);
	    resolution.setResolutionHeight(DEFAULT_HEIGHT);
    }

    public VideoStreamingParams(ImageResolution resolution, VideoStreamingFormat format){
	    this.resolution = resolution;
	    this.format = format;
    }

    public void setDisplayDensity(int displayDensity) {
        this.displayDensity = displayDensity;
    }

    public int getDisplayDensity() {
        return displayDensity;
    }

    public void setFrameRate(int frameRate) {
        this.frameRate = frameRate;
    }

    public int getFrameRate() {
        return frameRate;
    }

    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }

    public int getBitrate() {
        return bitrate;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getInterval() {
        return interval;
    }

    public void setFormat(VideoStreamingFormat format){
	    this.format = format;
    }

    public VideoStreamingFormat getFormat(){
	    return format;
    }

    public void setResolution(ImageResolution resolution){
	    this.resolution = resolution;
    }

	public ImageResolution getResolution() {
		return resolution;
	}
}