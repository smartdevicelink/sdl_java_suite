package com.smartdevicelink.managers.screen.alert;

import androidx.annotation.NonNull;

import com.smartdevicelink.managers.file.filetypes.SdlFile;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;

import java.util.List;

public class AudioData {

    private List<TTSChunk> prompt;
    private SdlFile audioFile;

    // All vars have getters as well but no setters

    AudioData(@NonNull SdlFile audioFile) {
        this.audioFile = audioFile;
    }
    AudioData(@NonNull String spokenString) {

    }
    AudioData(@NonNull String phoneticString, @NonNull SpeechCapabilities phoneticType) {

    }
}
