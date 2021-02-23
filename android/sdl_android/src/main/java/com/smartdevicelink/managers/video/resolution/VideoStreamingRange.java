package com.smartdevicelink.managers.video.resolution;

public class VideoStreamingRange {
    private Resolution minResolution;
    private Resolution maxResolution;
    private Double minScreenDiagonal;
    private Double minAspectRatio;
    private Double maxAspectRatio;

    public VideoStreamingRange(Resolution minResolution, Resolution maxResolution, Double minScreenDiagonal, Double minAspectRatio, Double maxAspectRatio) {
        this.minResolution = minResolution;
        this.maxResolution = maxResolution;
        this.minScreenDiagonal = minScreenDiagonal;
        this.minAspectRatio = minAspectRatio;
        this.maxAspectRatio = maxAspectRatio;
    }

    private VideoStreamingRange() {
    }

    public Resolution getMinResolution() {
        return minResolution;
    }

    public Resolution getMaxResolution() {
        return maxResolution;
    }

    public Double getMinScreenDiagonal() {
        return minScreenDiagonal;
    }

    public Double getMinAspectRatio() {
        return minAspectRatio;
    }

    public Double getMaxAspectRatio() {
        return maxAspectRatio;
    }

    public VideoStreamingRange setMinSupportedResolution(Resolution minSupportedResolution) {
        this.minResolution = minSupportedResolution;
        return this;
    }

    public VideoStreamingRange setMaxSupportedResolution(Resolution maxSupportedResolution) {
        this.maxResolution = maxSupportedResolution;
        return this;
    }

    public VideoStreamingRange setMinScreenDiagonal(Double minScreenDiagonal) {
        this.minScreenDiagonal = minScreenDiagonal;
        return this;
    }

    public VideoStreamingRange setMinAspectRatio(Double minAspectRatio) {
        this.minAspectRatio = minAspectRatio;
        return this;
    }

    public VideoStreamingRange setMaxAspectRatio(Double maxAspectRatio) {
        this.maxAspectRatio = maxAspectRatio;
        return this;
    }
}
