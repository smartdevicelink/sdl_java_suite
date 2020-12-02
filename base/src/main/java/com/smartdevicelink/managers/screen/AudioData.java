package com.smartdevicelink.managers.screen;

import androidx.annotation.NonNull;

import com.smartdevicelink.managers.file.filetypes.SdlFile;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AudioData {

    private static final String TAG = "AudioData";
    private List<TTSChunk> prompts;
    private List<SdlFile> audioFiles;

    // All vars have getters as well but no setters

    public AudioData() {
    }

    public AudioData(@NonNull SdlFile audioFile) {
        this.audioFiles = new ArrayList<>();
        audioFiles.add(audioFile);
    }

    public AudioData(@NonNull String spokenString) {
        this.prompts = new ArrayList<>();
        prompts.add(new TTSChunk(spokenString, SpeechCapabilities.TEXT));
    }

    public AudioData(@NonNull String phoneticString, @NonNull SpeechCapabilities phoneticType) {

        if (!isValidPhoneticType(phoneticType)) {
            return;
        }
        this.prompts = new ArrayList<>();
        prompts.add(new TTSChunk(phoneticString, phoneticType));
    }

    boolean isValidPhoneticType(SpeechCapabilities phoneticType) {
        if (!(phoneticType.equals(SpeechCapabilities.SAPI_PHONEMES) || phoneticType.equals(SpeechCapabilities.LHPLUS_PHONEMES)
                || phoneticType.equals(SpeechCapabilities.TEXT) || phoneticType.equals(SpeechCapabilities.PRE_RECORDED))) {
            return false;
        }
        return true;
    }

    public void addAudioFiles(@NonNull List<SdlFile> audioFiles) {
        if (this.audioFiles == null) {
            this.audioFiles = new ArrayList<>();
        }
        this.audioFiles.addAll(audioFiles);
    }

    public void addSpeechSynthesizerStrings(@NonNull List<String> spokenString) {
        if (spokenString.size() == 0) {
            return;
        }
        List<TTSChunk> newPrompts = new ArrayList<>();
        for (String spoken : spokenString) {
            if (spoken.length() == 0) {
                continue;
            }
            newPrompts.add(new TTSChunk().setText(spoken));
        }
        if (newPrompts.size() == 0) {
            return;
        }
        if (prompts == null) {
            this.prompts = newPrompts;
            return;
        }
        prompts.addAll(newPrompts);
    }

    public void addPhoneticSpeechSynthesizerStrings(@NonNull List<String> phoneticString, @NonNull SpeechCapabilities phoneticType) {
        if (!(isValidPhoneticType(phoneticType)) || phoneticString.size() == 0) {
            return;
        }
        List<TTSChunk> newPrompts = new ArrayList<>();
        for (String phonetic : phoneticString) {
            if (phonetic.length() == 0) {
                continue;
            }
            newPrompts.add(new TTSChunk(phonetic, phoneticType));
        }
        if (newPrompts.size() == 0) {
            return;
        }
        if (prompts == null) {
            this.prompts = newPrompts;
            return;
        }
        prompts.addAll(newPrompts);
    }

    public List<SdlFile> getAudioFiles() {
        return audioFiles;
    }

    public List<TTSChunk> getPrompts() {
        return prompts;
    }
}
