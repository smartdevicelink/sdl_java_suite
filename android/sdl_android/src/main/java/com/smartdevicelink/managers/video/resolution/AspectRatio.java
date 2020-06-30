package com.smartdevicelink.managers.video.resolution;

public class AspectRatio {
    private Double minAspectRatio;
    private Double maxAspectRatio;

    public AspectRatio(Double minAspectRatio, Double maxAspectRatio) {
        this.minAspectRatio = minAspectRatio;
        this.maxAspectRatio = maxAspectRatio;
    }

    public Double getMinAspectRatio() {
        return minAspectRatio;
    }

    public Double getMaxAspectRatio() {
        return maxAspectRatio;
    }
}
