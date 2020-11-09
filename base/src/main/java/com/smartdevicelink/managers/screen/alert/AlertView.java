package com.smartdevicelink.managers.screen.alert;

import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.managers.screen.SoftButtonObject;

import java.util.List;

public class AlertView {
    private static final int DEFAULT_TIMEOUT = 5;

    private String text, secondaryText, tertiaryText;
    private int timeout;
    private AlertAudioData audio;
    private boolean showWaitIndicator;
    private List<SoftButtonObject> softButtons;
    private SdlArtwork icon;
    AlertCanceledListener canceledListener;


    private AlertView(){

    }

    public int getTimeout() {
        return timeout;
    }

    public AlertAudioData getAudio() {
        return audio;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isShowWaitIndicator() {
        return showWaitIndicator;
    }

    public String getSecondaryText() {
        return secondaryText;
    }

    public String getTertiaryText() {
        return tertiaryText;
    }

    public void setTertiaryText(String tertiaryText) {
        this.tertiaryText = tertiaryText;
    }

    public List<SoftButtonObject> getSoftButtons() {
        return softButtons;
    }

    public void setSoftButtons(List<SoftButtonObject> softButtons) {
        this.softButtons = softButtons;
    }

    public SdlArtwork getIcon() {
        return icon;
    }

    public static class Builder {

        AlertView alertView;

        public Builder(){
            alertView = new AlertView();
        }

        public Builder setText(String text){
            this.alertView.text = text;
            return this;
        }

        public Builder setSecondaryText(String secondaryText){
            alertView.secondaryText = secondaryText;
            return this;
        }

        public Builder setTertiaryText(String tertiaryText){
            alertView.tertiaryText = tertiaryText;
            return this;
        }

        public Builder setTimeout(int timeout){
            alertView.timeout = timeout;
            return this;
        }

        public Builder setShowWaitIndicator(boolean showWaitIndicator){
            alertView.showWaitIndicator = showWaitIndicator;
            return this;
        }

        public Builder setSoftButtons(List<SoftButtonObject> softButtons){
            alertView.softButtons = softButtons;
            return this;
        }

        public Builder setAudio(AlertAudioData audio){
            alertView.audio = audio;
            return this;
        }

        public Builder setIcon(SdlArtwork icon){
            alertView.icon = icon;
            return this;
        }

        public AlertView build(){
            return alertView;
        }
    }

    // All vars have get / set as well to match iOS read / write

    public void cancel(){
        if (canceledListener == null) {
            return;
        }
        canceledListener.onAlertCanceled();
    }
}
