package com.smartdevicelink.managers.screen;

import androidx.annotation.NonNull;

import com.smartdevicelink.managers.file.filetypes.SdlFile;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;

public class AlertAudioData extends AudioData {

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
}
