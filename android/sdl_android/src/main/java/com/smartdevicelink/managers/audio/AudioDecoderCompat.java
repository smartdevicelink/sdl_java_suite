/*
 * Copyright (c) 2017 - 2019, SmartDeviceLink Consortium, Inc.
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
 * Neither the name of the SmartDeviceLink Consortium, Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
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
package com.smartdevicelink.managers.audio;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.livio.taskmaster.Queue;
import com.livio.taskmaster.Taskmaster;
import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.managers.audio.AudioStreamManager.SampleType;

import java.lang.ref.WeakReference;

/**
 * The audio decoder to decode a single audio file to PCM.
 * This decoder supports phones with api < 21 but uses methods deprecated with api 21.
 */
public class AudioDecoderCompat extends BaseAudioDecoder {
    private WeakReference<ISdl> internalInterface;

    /**
     * Creates a new object of AudioDecoder.
     *
     * @param internalInterface The internal interface to the connected device.
     * @param audioSource       The audio source to decode.
     * @param context           The context object to use to open the audio source.
     * @param sampleRate        The desired sample rate for decoded audio data.
     * @param sampleType        The desired sample type (8bit, 16bit, float).
     * @param listener          A listener who receives the decoded audio.
     */
    AudioDecoderCompat(@NonNull ISdl internalInterface, @NonNull Uri audioSource, @NonNull Context context, int sampleRate, @SampleType int sampleType, AudioDecoderListener listener) {
        super(audioSource, context, sampleRate, sampleType, listener);
        this.internalInterface = new WeakReference<>(internalInterface);
    }

    /**
     * Starts the audio decoding asynchronously.
     */
    public void start() {
        try {
            initMediaComponents();
            decoder.start();

            if (internalInterface != null && internalInterface.get() != null) {
                Taskmaster taskmaster = internalInterface.get().getTaskmaster();
                if (taskmaster != null) {
                    Queue transactionQueue = taskmaster.createQueue("AudioDecoderCompat", 6, false);
                    AudioDecoderCompatOperation operation = new AudioDecoderCompatOperation(this);
                    transactionQueue.add(operation, false);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (this.listener != null) {
                this.listener.onDecoderError(e);
                this.listener.onDecoderFinish(false);
            }
            stop();
        }
    }
}
