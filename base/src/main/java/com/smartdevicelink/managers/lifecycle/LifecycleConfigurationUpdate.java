/*
 * Copyright (c)  2019 Livio, Inc.
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
 *
 * Created by brettywhite on 8/1/19 3:11 PM
 *
 */

package com.smartdevicelink.managers.lifecycle;

import androidx.annotation.Nullable;

import com.smartdevicelink.proxy.rpc.TTSChunk;

import java.util.Vector;

/**
 * Configuration update options for SDLManager. This class can be used to update the lifecycle configuration in
 * cases the language of the head unit changes or does not match the app language.
 */
public class LifecycleConfigurationUpdate {

    private String appName, shortAppName;
    private Vector<TTSChunk> ttsName;
    private Vector<String> voiceRecognitionCommandNames;

    // default constructor
    public LifecycleConfigurationUpdate() {
    }

    /**
     * Initializes and returns a newly allocated lifecycle configuration update object with the specified app data.
     *
     * @param appName                      The full name of the app to that the configuration should be updated to.
     * @param shortAppName                 An abbreviated application name that will be used on the app launching screen if the full one would be truncated.
     * @param ttsName                      A Text to Speech String for voice recognition of the mobile application name.
     * @param voiceRecognitionCommandNames Additional voice recognition commands. May not interfere with any other app name or global commands.
     */
    public LifecycleConfigurationUpdate(@Nullable String appName, @Nullable String shortAppName, @Nullable Vector<TTSChunk> ttsName, @Nullable Vector<String> voiceRecognitionCommandNames) {
        setAppName(appName);
        setShortAppName(shortAppName);
        setTtsName(ttsName);
        setVoiceRecognitionCommandNames(voiceRecognitionCommandNames);
    }

    // SETTERS AND GETTERS

    /**
     * The full name of the app to that the configuration should be updated to.
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * The full name of the app to that the configuration should be updated to.
     */
    public String getAppName() {
        return appName;
    }

    /**
     * An abbreviated application name that will be used on the app launching screen if the full one would be truncated.
     */
    public void setShortAppName(String shortAppName) {
        this.shortAppName = shortAppName;
    }

    /**
     * An abbreviated application name that will be used on the app launching screen if the full one would be truncated.
     */
    public String getShortAppName() {
        return shortAppName;
    }

    /**
     * A Text to Speech String for voice recognition of the mobile application name.
     */
    public void setTtsName(Vector<TTSChunk> ttsName) {
        this.ttsName = ttsName;
    }

    /**
     * A Text to Speech String for voice recognition of the mobile application name.
     */
    public Vector<TTSChunk> getTtsName() {
        return ttsName;
    }

    public void setVoiceRecognitionCommandNames(Vector<String> voiceRecognitionCommandNames) {
        this.voiceRecognitionCommandNames = voiceRecognitionCommandNames;
    }

    /**
     * Additional voice recognition commands. May not interfere with any other app name or global commands.
     */
    public Vector<String> getVoiceRecognitionCommandNames() {
        return voiceRecognitionCommandNames;
    }
}
