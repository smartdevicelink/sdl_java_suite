package com.smartdevicelink.managers.screen;

import androidx.annotation.NonNull;

import com.smartdevicelink.managers.file.filetypes.SdlFile;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;

public class AlertAudioData extends AudioData{

        private boolean playTone;

        // All vars have getters and setters

        public AlertAudioData(@NonNull SdlFile audioFile, @NonNull boolean playTone) {
                super(audioFile);
                this.playTone = playTone;
        }

        public AlertAudioData(@NonNull String spokenString, @NonNull boolean playTone) {
                super(spokenString);
                this.playTone = playTone;
        }

        public AlertAudioData(@NonNull String phoneticString, @NonNull SpeechCapabilities phoneticType, @NonNull boolean playTone) {
                super(phoneticString, phoneticType);
                this.playTone = playTone;
        }

        public AlertAudioData(@NonNull boolean playTone) {
                super();
                this.playTone = playTone;
        }

        public boolean isPlayTone() {
                return playTone;
        }

        public void setPlayTone(boolean playTone) {
                this.playTone = playTone;
        }
}
