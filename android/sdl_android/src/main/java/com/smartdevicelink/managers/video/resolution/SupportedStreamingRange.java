package com.smartdevicelink.managers.video.resolution;

public class SupportedStreamingRange {
    private Resolution minSupportedResolution;
    private Resolution maxSupportedResolution;
    private Double maxScreenDiagonal;
    private AspectRatio aspectRatio;

    public SupportedStreamingRange(Resolution minSupportedResolution, Resolution maxSupportedResolution, Double maxScreenDiagonal, AspectRatio aspectRatio) {
        this.minSupportedResolution = minSupportedResolution;
        this.maxSupportedResolution = maxSupportedResolution;
        this.maxScreenDiagonal = maxScreenDiagonal;
        this.aspectRatio = aspectRatio;
    }

    public Resolution getMinSupportedResolution() {
        return minSupportedResolution;
    }

    public Resolution getMaxSupportedResolution() {
        return maxSupportedResolution;
    }

    public Double getMaxScreenDiagonal() {
        return maxScreenDiagonal;
    }

    public AspectRatio getAspectRatio() {
        return aspectRatio;
    }
}
