package com.smartdevicelink.managers.video.resolution;

import com.smartdevicelink.proxy.rpc.ImageResolution;

public class VideoStreamingRange {
    private Resolution minimumResolution;
    private Resolution maximumResolution;
    private Double minimumDiagonal;
    private Double minimumAspectRatio;
    private Double maximumAspectRatio;

    public VideoStreamingRange(Resolution minimumResolution, Resolution maximumResolution, Double minimumDiagonal, Double minimumAspectRatio, Double maximumAspectRatio) {
        this.minimumResolution = minimumResolution;
        this.maximumResolution = maximumResolution;
        this.minimumDiagonal = minimumDiagonal;
        this.minimumAspectRatio = minimumAspectRatio;
        this.maximumAspectRatio = maximumAspectRatio;
    }

    private VideoStreamingRange() {
    }

    public Resolution getMinimumResolution() {
        return minimumResolution;
    }

    public Resolution getMaximumResolution() {
        return maximumResolution;
    }

    public Double getMinimumDiagonal() {
        return minimumDiagonal;
    }

    public Double getMinimumAspectRatio() {
        return minimumAspectRatio;
    }

    public Double getMaximumAspectRatio() {
        return maximumAspectRatio;
    }

    public VideoStreamingRange setMinSupportedResolution(Resolution minSupportedResolution) {
        this.minimumResolution = minSupportedResolution;
        return this;
    }

    public VideoStreamingRange setMaxSupportedResolution(Resolution maxSupportedResolution) {
        this.maximumResolution = maxSupportedResolution;
        return this;
    }

    public VideoStreamingRange setMinimumDiagonal(Double minimumDiagonal) {
        this.minimumDiagonal = minimumDiagonal;
        return this;
    }

    public VideoStreamingRange setMinimumAspectRatio(Double minimumAspectRatio) {
        this.minimumAspectRatio = minimumAspectRatio;
        return this;
    }

    public VideoStreamingRange setMaximumAspectRatio(Double maximumAspectRatio) {
        this.maximumAspectRatio = maximumAspectRatio;
        return this;
    }

    public Boolean isImageResolutionInRange(ImageResolution currentResolution) {

        Integer constraintHeightMax = maximumResolution.getResolutionHeight();
        Integer constraintHeightMin = minimumResolution.getResolutionHeight();
        Integer constraintWidthMax = maximumResolution.getResolutionWidth();
        Integer constraintWidthMin = minimumResolution.getResolutionWidth();
        Integer resolutionHeight = currentResolution.getResolutionHeight();
        Integer resolutionWidth = currentResolution.getResolutionWidth();

        if (currentResolution == null) {
            return false;
        }

        if (minimumResolution == null && maximumResolution == null) {
            return true;
        }

        if (minimumResolution == null || (minimumResolution.getResolutionWidth() == 0 && minimumResolution.getResolutionHeight() == 0)) {
            return false;
        }

        if (maximumResolution == null || (maximumResolution.getResolutionWidth() == 0 && maximumResolution.getResolutionHeight() == 0)) {
            return false;
        }

        if (currentResolution.getResolutionHeight() > 0 && currentResolution.getResolutionWidth() > 0 && constraintHeightMax != null && constraintHeightMin != null) {
            if (!(resolutionHeight >= constraintHeightMin && resolutionHeight <= constraintHeightMax)) {
                return false;
            }

            if (!(resolutionWidth >= constraintWidthMin && resolutionWidth <= constraintWidthMax)) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    public Boolean isAspectRatioInRange(Double aspectRatio) {
        if (maximumAspectRatio == null && minimumAspectRatio == null) {
            return true;
        }

        boolean isInRange = true;
        if (minimumAspectRatio != null) {
            isInRange = aspectRatio >= minimumAspectRatio;
        }
        if (isInRange && maximumAspectRatio != null) {
            isInRange = aspectRatio <= maximumAspectRatio;
        }
        return isInRange;
    }

}
