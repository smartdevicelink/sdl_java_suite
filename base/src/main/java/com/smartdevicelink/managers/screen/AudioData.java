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

import androidx.annotation.NonNull;

import com.smartdevicelink.managers.file.filetypes.SdlFile;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class AudioData {

    // All audio data
    private List<TTSChunk> audioData;

    // The audio files that will be uploaded.
    private HashMap<String, SdlFile> audioFiles;

    public AudioData(@NonNull SdlFile audioFile) {
        this.audioFiles = new HashMap<>();
        this.audioData = new ArrayList<>();
        audioFiles.put(audioFile.getName(), audioFile);
        audioData.add(new TTSChunk(audioFile.getName(), SpeechCapabilities.FILE));
    }

    public AudioData(@NonNull String spokenString) {
        this.audioData = new ArrayList<>();
        audioData.add(new TTSChunk(spokenString, SpeechCapabilities.TEXT));
    }

    public AudioData(@NonNull String phoneticString, @NonNull SpeechCapabilities phoneticType) {
        if (!isValidPhoneticType(phoneticType)) {
            return;
        }
        this.audioData = new ArrayList<>();
        audioData.add(new TTSChunk(phoneticString, phoneticType));
    }

    /**
     * Checks if the phonetic type can be used to create a text-to-speech string.
     *
     * @param phoneticType The phonetic type of the text-to-speech string
     * @return True if the phoneticType is of type `SAPI_PHONEMES`, `LHPLUS_PHONEMES`, `TEXT`, or `PRE_RECORDED`; false if not.
     */
    boolean isValidPhoneticType(SpeechCapabilities phoneticType) {
        if (!(phoneticType.equals(SpeechCapabilities.SAPI_PHONEMES) || phoneticType.equals(SpeechCapabilities.LHPLUS_PHONEMES)
                || phoneticType.equals(SpeechCapabilities.TEXT) || phoneticType.equals(SpeechCapabilities.PRE_RECORDED))) {
            return false;
        }
        return true;
    }

    /**
     * Add additional SDLFiles holding data or pointing to a file on the file system. When this object
     * is passed to an `Alert` or `Speak`, the file will be uploaded if it is not already, then played
     * if the system supports that feature.
     *
     * @param audioFiles A list of audio file to be played by the system
     */
    public void addAudioFiles(@NonNull List<SdlFile> audioFiles) {
        if (this.audioFiles == null) {
            this.audioFiles = new HashMap<>();
        }
        if (this.audioData == null) {
            this.audioData = new ArrayList<>();
        }
        for (SdlFile file : audioFiles) {
            audioData.add(new TTSChunk(file.getName(), SpeechCapabilities.FILE));
            this.audioFiles.put(file.getName(), file);
        }
    }

    /**
     * Create additional strings to be spoken by the system speech synthesizer.
     *
     * @param spokenString The strings to be spoken by the system speech synthesizer
     */
    public void addSpeechSynthesizerStrings(@NonNull List<String> spokenString) {
        if (spokenString.size() == 0) {
            return;
        }
        List<TTSChunk> newPrompts = new ArrayList<>();
        for (String spoken : spokenString) {
            if (spoken.length() == 0) {
                continue;
            }
            newPrompts.add(new TTSChunk().setText(spoken).setType(SpeechCapabilities.TEXT));
        }
        if (newPrompts.size() == 0) {
            return;
        }
        if (audioData == null) {
            this.audioData = newPrompts;
            return;
        }
        audioData.addAll(newPrompts);
    }

    /**
     * Create additional strings to be spoken by the system speech synthesizer using a phonetic string.
     *
     * @param phoneticString The strings to be spoken by the system speech synthesizer
     * @param phoneticType   Must be one of `SAPI_PHONEMES`, `LHPLUS_PHONEMES`, `TEXT`, or `PRE_RECORDED` or no object will be created
     */
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
        if (audioData == null) {
            this.audioData = newPrompts;
            return;
        }
        audioData.addAll(newPrompts);
    }

    HashMap<String, SdlFile> getAudioFiles() {
        return audioFiles;
    }

    public List<TTSChunk> getAudioData() {
        return audioData;
    }
}
