package com.smartdevicelink.managers.video.resolution;

public class VideoStreamingRange {
    private Resolution minResolution;
    private Resolution maxResolution;
    private Double minScreenDiagonal;
    private Double minAspectRatio;
    private Double maxAspectRatio;

    public VideoStreamingRange(
            Resolution minResolution,
            Resolution maxResolution,
            Double minScreenDiagonal,
            Double minAspectRatio,
            Double maxAspectRatio
    ) {
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

    public static class Builder {
        private VideoStreamingRange range = new VideoStreamingRange();
        private Double maxAspectRatio;

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
        public Builder setMinAspectRatio(Double minAspectRatio) {
            range.minAspectRatio = minAspectRatio;
            return this;
        }

        public Builder setMaxAspectRatio(Double maxAspectRatio) {
            range.maxAspectRatio = maxAspectRatio;
            return this;
        }

        public VideoStreamingRange build() {
            return range;
        }
    }
}
