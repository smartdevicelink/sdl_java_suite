package com.smartdevicelink.managers.video.resolution;

public class Resolution {
    private Integer resolutionWidth;
    private Integer resolutionHeight;

    public Resolution(Integer resolutionWidth, Integer resolutionHeight) {
        this.resolutionWidth = resolutionWidth;
        this.resolutionHeight = resolutionHeight;
    }

    public Integer getResolutionWidth() {
        return resolutionWidth;
    }

    public Integer getResolutionHeight() {
        return resolutionHeight;
    }
}
