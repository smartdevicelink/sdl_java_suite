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

    private static final int TIMEOUT_DEFAULT = 0;
    private static final int TIMEOUT_MIN = 3;
    private static final int TIMEOUT_MAX = 10;
    private static Integer defaultTimeout = 5;
    private String text, secondaryText, tertiaryText;
    private Integer timeout;
    private AlertAudioData audio;
    private boolean showWaitIndicator;
    private List<SoftButtonObject> softButtons;
    private SdlArtwork icon;
    AlertCanceledListener canceledListener;


    private AlertView() {
    }

    public static class Builder {

        AlertView alertView;

        public Builder() {
            alertView = new AlertView();
            if (alertView.timeout == null) {
                alertView.timeout = TIMEOUT_DEFAULT;
            }
        }

        /**
         * The primary line of text for display on the alert. If fewer than three alert lines are available
         * on the head unit, the screen manager will automatically concatenate some of the lines together.
         */
        public Builder setText(String text) {
            alertView.text = text;
            return this;
        }

        /**
         * The secondary line of text for display on the alert. If fewer than three alert lines are available
         * on the head unit, the screen manager will automatically concatenate some of the lines together.
         */
        public Builder setSecondaryText(String secondaryText) {
            alertView.secondaryText = secondaryText;
            return this;
        }

        /**
         * The tertiary line of text for display on the alert. If fewer than three alert lines are available
         * on the head unit, the screen manager will automatically concatenate some of the lines together.
         */
        public Builder setTertiaryText(String tertiaryText) {
            alertView.tertiaryText = tertiaryText;
            return this;
        }

        /**
         * Timeout in seconds. Defaults to 0, which will use `defaultTimeout`. If this is set below the
         * minimum, it will be capped at 3 seconds. Minimum 3 seconds, maximum 10 seconds. If this is
         * set above the maximum, it will be capped at 10 seconds. Defaults to 0.
         *
         * Please note that if a button is added to the alert, the defaultTimeout and timeout values will be ignored.
         */
        public Builder setTimeout(Integer timeout) {
            alertView.timeout = timeout;
            return this;
        }

        /**
         * If supported, the alert GUI will display some sort of indefinite waiting / refresh / loading
         * indicator animation. Defaults to NO.
         */
        public Builder setShowWaitIndicator(boolean showWaitIndicator) {
            alertView.showWaitIndicator = showWaitIndicator;
            return this;
        }

        /**
         * Soft buttons the user may select to perform actions. Only one `SoftButtonState` per object
         * is supported; if any soft button object contains multiple states, an exception will be thrown.
         */
        public Builder setSoftButtons(List<SoftButtonObject> softButtons) {
            alertView.setSoftButtons(softButtons);
            return this;
        }

        /**
         * Text spoken, file(s) played, and/or tone played when the alert appears
         */
        public Builder setAudio(AlertAudioData audio) {
            alertView.audio = audio;
            return this;
        }

        /**
         * An artwork that will be displayed when the icon appears. This will be uploaded prior to the
         * appearance of the alert if necessary. This will not be uploaded if the head unit does not
         * declare support for alertIcon.
         */
        public Builder setIcon(SdlArtwork icon) {
            alertView.icon = icon;
            return this;
        }

        /**
         * Set this to change the default timeout for all alerts. If a timeout is not set on an individual
         * alert object (or if it is set to 0.0), then it will use this timeout instead. See `timeout`
         * for more details. If this is not set by you, it will default to 5 seconds. The minimum is
         * 3 seconds, the maximum is 10 seconds. If this is set below the minimum, it will be capped
         * at 3 seconds. If this is set above the maximum, it will be capped at 10 seconds.
         */
        public Builder setDefaultTimeOut(int defaultTimeOut) {
            alertView.setDefaultTimeout(defaultTimeOut);
            return this;
        }

        public AlertView build() {
            return alertView;
        }
    }

    // Notifies the subscriber that the alert should be cancelled.
    public void cancel() {
        if (canceledListener == null) {
            return;
        }
        canceledListener.onAlertCanceled();
    }

    public Integer getTimeout() {
        if (timeout == TIMEOUT_DEFAULT) {
            timeout = getDefaultTimeout();
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

    public void setShowWaitIndicator(boolean showWaitIndicator) {
        this.showWaitIndicator = showWaitIndicator;
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
        for (SoftButtonObject softButtonObject : softButtons) {
            if (softButtonObject.getStates().size() != 1) {
                throw new IllegalArgumentException("Attempting create a soft button for an Alert with more than one state. Alerts only support soft buttons with one state");
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
        if (defaultTimeout < TIMEOUT_MIN) {
            return TIMEOUT_MIN;
        } else if (defaultTimeout > TIMEOUT_MAX) {
            return TIMEOUT_MAX;
        }
        return defaultTimeout;
    }

    public void setDefaultTimeout(int defaultTimeout) {
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
