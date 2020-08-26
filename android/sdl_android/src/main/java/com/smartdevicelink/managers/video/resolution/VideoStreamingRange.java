package com.smartdevicelink.managers.video.resolution;

public class VideoStreamingRange {
    private Resolution minResolution;
    private Resolution maxResolution;
    private Double minScreenDiagonal;
    private AspectRatio aspectRatio;

    public VideoStreamingRange(
            Resolution minResolution,
            Resolution maxResolution,
            Double minScreenDiagonal,
            AspectRatio aspectRatio
    ) {
        this.minResolution = minResolution;
        this.maxResolution = maxResolution;
        this.minScreenDiagonal = minScreenDiagonal;
        this.aspectRatio = aspectRatio;
    }

    private VideoStreamingRange() { }

    public Resolution getMinResolution() {
        return minResolution;
    }

    public Resolution getMaxResolution() {
        return maxResolution;
    }

    public Double getMinScreenDiagonal() {
        return minScreenDiagonal;
    }

    public AspectRatio getAspectRatio(){ return aspectRatio; }

    public static class Builder {
        private VideoStreamingRange range = new VideoStreamingRange();

        public Builder setMinSupportedResolution(Resolution minSupportedResolution) {
            range.minResolution = minSupportedResolution;
            return this;
        }

        public Builder setMaxSupportedResolution(Resolution maxSupportedResolution) {
            range.maxResolution = maxSupportedResolution;
            return this;
        }

        public Builder setMinScreenDiagonal(Double minScreenDiagonal) {
            range.minScreenDiagonal = minScreenDiagonal;
            return this;
        }

        public Builder setAspectRatio(AspectRatio aspectRatio) {
            range.aspectRatio = aspectRatio;
            return this;
        }

        public VideoStreamingRange build() {
            return range;
        }
    }
}
