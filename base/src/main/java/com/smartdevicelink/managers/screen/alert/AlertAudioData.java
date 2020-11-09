package com.smartdevicelink.managers.screen.alert;

import androidx.annotation.NonNull;

import com.smartdevicelink.managers.file.filetypes.SdlFile;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;

public class AlertAudioData {

        private boolean playTone;

        // All vars have getters and setters

        AlertAudioData(@NonNull SdlFile audioFile, @NonNull boolean playTone) {

        }
        AlertAudioData(@NonNull String spokenString, @NonNull boolean playTone) {

        }
        AlertAudioData(@NonNull String phoneticString, @NonNull SpeechCapabilities phoneticType, @NonNull boolean playTone) {

        }
        AlertAudioData(@NonNull boolean playTone) {

        }

}
