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
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;
import com.smartdevicelink.util.DebugTool;

public class AlertAudioData extends AudioData implements Cloneable {

        // Whether the alert tone should be played before the prompt (if any) is spoken. Defaults to false
        private boolean playTone;

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
