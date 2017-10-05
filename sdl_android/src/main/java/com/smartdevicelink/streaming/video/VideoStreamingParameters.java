package com.smartdevicelink.streaming.video;

import android.content.Intent;
import android.util.DisplayMetrics;

import com.smartdevicelink.proxy.rpc.ImageResolution;
import com.smartdevicelink.proxy.rpc.VideoStreamingFormat;
import com.smartdevicelink.proxy.rpc.enums.VideoStreamingCodec;
import com.smartdevicelink.proxy.rpc.enums.VideoStreamingProtocol;

public class VideoStreamingParameters {
	private final VideoStreamingProtocol DEFAULT_PROTOCOL = VideoStreamingProtocol.RAW;
	private final VideoStreamingCodec DEFAULT_CODEC = VideoStreamingCodec.H264;
	private final int DEFAULT_WIDTH = 800;
	private final int DEFAULT_HEIGHT = 480;
	private final int DEFAULT_DENSITY = DisplayMetrics.DENSITY_HIGH;
	private final int DEFAULT_FRAMERATE = 24;
	private final int DEFAULT_BITRATE = 512000;
	private final int DEFAULT_INTERVAL = 5;


	private int displayDensity;
	private int frameRate;
	private int bitrate;
	private int interval;
	private ImageResolution resolution;
	private VideoStreamingFormat format;

    public VideoStreamingParameters(){
	    displayDensity = DEFAULT_DENSITY;
	    frameRate = DEFAULT_FRAMERATE;
	    bitrate = DEFAULT_BITRATE;
	    interval = DEFAULT_INTERVAL;
	    resolution = new ImageResolution();
	    resolution.setResolutionWidth(DEFAULT_WIDTH);
	    resolution.setResolutionHeight(DEFAULT_HEIGHT);
	    format = new VideoStreamingFormat();
	    format.setProtocol(DEFAULT_PROTOCOL);
	    format.setCodec(DEFAULT_CODEC);
    }

    public VideoStreamingParameters(int displayDensity, int frameRate, int bitrate, int interval,
                                    ImageResolution resolution, VideoStreamingFormat format){
	    this.displayDensity = displayDensity;
	    this.frameRate = frameRate;
	    this.bitrate = bitrate;
	    this.interval = interval;
	    this.resolution = resolution;
	    this.format = format;
    }

    /**
     * Will only copy values that are not null or are greater than 0
     * @param params VideoStreamingParameters that should be copied into this new instants
     */
    public VideoStreamingParameters(VideoStreamingParameters params){
        update(params);
    }

    /**
     * Will only copy values that are not null or are greater than 0
     * @param params VideoStreamingParameters that should be copied into this new instants
     */
    public void update(VideoStreamingParameters params){
        if(params.displayDensity > 0){ this.displayDensity = params.displayDensity; }
        if(params.frameRate > 0){ this.frameRate = params.frameRate; }
        if(params.bitrate > 0){ this.bitrate = params.bitrate; }
        if(params.interval > 0){ this.interval = params.interval; }
        if(params.resolution !=null){
            if(params.resolution.getResolutionHeight() > 0){ this.resolution.setResolutionHeight(params.resolution.getResolutionHeight()); }
            if(params.resolution.getResolutionWidth() > 0){ this.resolution.setResolutionWidth(params.resolution.getResolutionWidth()); }

        }
        if(params.format != null){this.format = params.format;}
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

    @Override
    public String toString() {
        return "format: {" + String.valueOf(format) +
               "}, resolution: {" + String.valueOf(resolution) + "}";
    }
}