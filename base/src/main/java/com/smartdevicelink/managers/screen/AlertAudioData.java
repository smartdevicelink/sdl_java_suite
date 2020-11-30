package com.smartdevicelink.managers.screen;

import androidx.annotation.NonNull;

import com.smartdevicelink.managers.file.filetypes.SdlFile;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;
import com.smartdevicelink.util.DebugTool;

public class AlertAudioData extends AudioData implements Cloneable {

        private boolean playTone;

        // All vars have getters and setters

        public AlertAudioData() {
                super();
        }

        public AlertAudioData(@NonNull SdlFile audioFile) {
                super(audioFile);
        }

        public AlertAudioData(@NonNull String spokenString) {
                super(spokenString);
        }

        public AlertAudioData(@NonNull String phoneticString, @NonNull SpeechCapabilities phoneticType) {
                super(phoneticString, phoneticType);
        }

        public boolean isPlayTone() {
                return playTone;
        }

        public void setPlayTone(boolean playTone) {
                this.playTone = playTone;
        }

        /**
         * Creates a deep copy of the object
         *
         * @return deep copy of the object, null if an exception occurred
         */
        @Override
        public AlertAudioData clone() {
                try {
                        AlertAudioData alertAudioData = (AlertAudioData) super.clone();
                        return alertAudioData;
                } catch (CloneNotSupportedException e) {
                        if (DebugTool.isDebugEnabled()) {
                                throw new RuntimeException("Clone not supported by super class");
                        }
                }
                return null;
        }
}
