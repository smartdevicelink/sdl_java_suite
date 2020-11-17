package com.smartdevicelink.managers.screen;

import androidx.annotation.NonNull;

import com.smartdevicelink.managers.file.filetypes.SdlFile;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;
import com.smartdevicelink.util.DebugTool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AudioData {

    private static final String TAG = "AudioData";
    private List<TTSChunk> prompt;
    private SdlFile audioFile;

    // All vars have getters as well but no setters

    public AudioData() {
    }

    public AudioData(@NonNull SdlFile audioFile) {
        this.audioFile = audioFile;
    }

    public AudioData(@NonNull String spokenString) {
        prompt = Collections.singletonList(new TTSChunk().setText(spokenString));
    }

    public AudioData(@NonNull String phoneticString, @NonNull SpeechCapabilities phoneticType) {

        if (!(phoneticType.equals(SpeechCapabilities.SAPI_PHONEMES) || phoneticType.equals(SpeechCapabilities.LHPLUS_PHONEMES)
                || phoneticType.equals(SpeechCapabilities.TEXT) || phoneticType.equals(SpeechCapabilities.PRE_RECORDED))) {
            return;
        }
        prompt = Collections.singletonList(new TTSChunk(phoneticString, phoneticType));
    }

    public SdlFile getAudioFile() {
        return audioFile;
    }

    public List<TTSChunk> getPrompt() {
        return prompt;
    }
}
