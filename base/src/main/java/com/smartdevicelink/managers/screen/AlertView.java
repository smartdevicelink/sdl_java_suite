/*
 * Copyright (c) 2020 Livio, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the Livio Inc. nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package com.smartdevicelink.managers.screen;

import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.util.DebugTool;

import java.util.ArrayList;
import java.util.List;

public class AlertView implements Cloneable {

    private static final String TAG = "AlertView";
    private static Integer defaultTimeout = 5;
    private static final int TIMEOUT_MIN = 3;
    private static final int TIMEOUT_MAX = 10;
    private String text, secondaryText, tertiaryText;
    private Integer timeout;
    private AlertAudioData audio;
    private boolean showWaitIndicator;
    private List<SoftButtonObject> softButtons;
    private SdlArtwork icon;
    AlertCanceledListener canceledListener;


    private AlertView() {
        this.timeout = defaultTimeout;
    }

    public static class Builder {

        AlertView alertView;

        public Builder() {
            alertView = new AlertView();
        }

        public Builder setText(String text) {
            this.alertView.text = text;
            return this;
        }

        public Builder setSecondaryText(String secondaryText) {
            alertView.secondaryText = secondaryText;
            return this;
        }

        public Builder setTertiaryText(String tertiaryText) {
            alertView.tertiaryText = tertiaryText;
            return this;
        }

        public Builder setTimeout(Integer timeout) {
            alertView.timeout = timeout;
            return this;
        }

        public Builder setShowWaitIndicator(boolean showWaitIndicator) {
            alertView.showWaitIndicator = showWaitIndicator;
            return this;
        }

        public Builder setSoftButtons(List<SoftButtonObject> softButtons) {
            alertView.setSoftButtons(softButtons);
            return this;
        }

        public Builder setAudio(AlertAudioData audio) {
            alertView.audio = audio;
            return this;
        }

        public Builder setIcon(SdlArtwork icon) {
            alertView.icon = icon;
            return this;
        }

        public Builder setDefaultTimeOut(int defaultTimeOut) {
            alertView.setDefaultTimeout(defaultTimeOut);
            return this;
        }

        public AlertView build() {
            return alertView;
        }
    }

    // All vars have get / set as well to match iOS read / write

    public void cancel() {
        if (canceledListener == null) {
            return;
        }
        canceledListener.onAlertCanceled();
    }

    public Integer getTimeout() {
        if (timeout == null) {
            timeout = defaultTimeout;
        } else if (timeout == defaultTimeout) {
            return defaultTimeout;
        } else if (timeout < TIMEOUT_MIN) {
            return TIMEOUT_MIN;
        } else if (timeout > TIMEOUT_MAX) {
            return TIMEOUT_MAX;
        }
        return timeout;
    }

    public AlertAudioData getAudio() {
        return audio;
    }

    public void setAudio(AlertAudioData audio) {
        this.audio = audio;
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

    public void setSecondaryText(String secondaryText) {
        this.secondaryText = secondaryText;
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
        for(SoftButtonObject softButtonObject : softButtons){
            if(softButtonObject.getStates().size() != 1){
                this.softButtons = null;
                DebugTool.logError(TAG,"Attempting create a soft button for an Alert with more than one state. Alerts only support soft buttons with one state. Soft buttons set to null for AlertView");
                return;
            }
        }
        this.softButtons = softButtons;
    }

    public SdlArtwork getIcon() {
        return icon;
    }

    public void setIcon(SdlArtwork icon) {
        this.icon = icon;
    }

    public int getDefaultTimeout() {
        return defaultTimeout;
    }

    public void setDefaultTimeout(int defaultTimeout) {
        if (defaultTimeout <= TIMEOUT_MIN) {
            AlertView.defaultTimeout = TIMEOUT_MIN;
            return;
        } else if (defaultTimeout >= TIMEOUT_MAX) {
            AlertView.defaultTimeout = TIMEOUT_MAX;
            return;
        }
        AlertView.defaultTimeout = defaultTimeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    /**
     * Creates a deep copy of the object
     *
     * @return deep copy of the object, null if an exception occurred
     */
    @Override
    public AlertView clone() {
        try {
            AlertView alertView = (AlertView) super.clone();
            if (alertView != null) {
                if (alertView.getAudio() != null) {
                    alertView.audio = audio.clone();
                }
                if (alertView.getSoftButtons() != null) {
                    List<SoftButtonObject> softButtonObjectList = new ArrayList<>();
                    for (int i = 0; i < alertView.softButtons.size(); i++) {
                        SoftButtonObject cloneSoftButton = alertView.softButtons.get(i).clone();
                        softButtonObjectList.add(cloneSoftButton);
                    }
                    alertView.softButtons = softButtonObjectList;
                }
                if (alertView.icon != null) {
                    alertView.icon = icon.clone();
                }
            }
            return alertView;
        } catch (CloneNotSupportedException e) {
            if (DebugTool.isDebugEnabled()) {
                throw new RuntimeException("Clone not supported by super class");
            }
        }
        return null;
    }
}
