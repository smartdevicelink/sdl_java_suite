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
package com.smartdevicelink.proxy.rpc;

import androidx.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.AudioType;
import com.smartdevicelink.proxy.rpc.enums.BitsPerSample;
import com.smartdevicelink.proxy.rpc.enums.SamplingRate;

import java.util.Hashtable;

/**
 * <p> Describes different audio type configurations for PerformAudioPassThru, e.g. {8kHz,8-bit,PCM}
 * Specifies the capabilities of audio capturing: sampling rate, bits per sample, audio type.</p>
 *
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 *         <tr>
 *             <th>Name</th>
 *             <th>Type</th>
 *             <th>Description</th>
 *             <th>SmartDeviceLink Ver. Available</th>
 *         </tr>
 *         <tr>
 *             <td>samplingRate</td>
 *             <td>SamplingRate</td>
 *             <td>Describes the sampling rate for AudioPassThru
 *             </td>
 *             <td>SmartDeviceLink 2.0</td>
 *         </tr>
 *         <tr>
 *             <td>bitsPerSample</td>
 *             <td>BitsPerSample</td>
 *             <td>Describes the sample depth in bit for AudioPassThru
 *             </td>
 *             <td>SmartDeviceLink 2.0</td>
 *         </tr>
 *         <tr>
 *             <td>audioType</td>
 *             <td>AudioType</td>
 *             <td>Describes the audioType for AudioPassThru
 *             </td>
 *             <td>SmartDeviceLink 2.0</td>
 *         </tr>
 *  </table>
 *
 * @since SmartDeviceLink 2.0
 */
public class AudioPassThruCapabilities extends RPCStruct {
    public static final String KEY_SAMPLING_RATE = "samplingRate";
    public static final String KEY_AUDIO_TYPE = "audioType";
    public static final String KEY_BITS_PER_SAMPLE = "bitsPerSample";

    /**
     * Constructs a newly allocated AudioPassThruCapabilities object
     */
    public AudioPassThruCapabilities() {
    }

    /**
     * Constructs a newly allocated AudioPassThruCapabilities object indicated by the Hashtable parameter
     *
     * @param hash The Hashtable to use
     */
    public AudioPassThruCapabilities(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a newly allocated AudioPassThruCapabilities object
     *
     * @param samplingRate  the sampling rate for AudioPassThru
     * @param bitsPerSample the sample depth in bit for AudioPassThru
     * @param audioType     the audioType for AudioPassThru
     */
    public AudioPassThruCapabilities(@NonNull SamplingRate samplingRate, @NonNull BitsPerSample bitsPerSample, @NonNull AudioType audioType) {
        this();
        setSamplingRate(samplingRate);
        setBitsPerSample(bitsPerSample);
        setAudioType(audioType);
    }

    /**
     * set the sampling rate for AudioPassThru
     *
     * @param samplingRate the sampling rate for AudioPassThru
     */
    public AudioPassThruCapabilities setSamplingRate(@NonNull SamplingRate samplingRate) {
        setValue(KEY_SAMPLING_RATE, samplingRate);
        return this;
    }

    /**
     * get the sampling rate for AudioPassThru
     *
     * @return the sampling rate for AudioPassThru
     */
    public SamplingRate getSamplingRate() {
        return (SamplingRate) getObject(SamplingRate.class, KEY_SAMPLING_RATE);
    }

    /**
     * set the sample depth in bit for AudioPassThru
     *
     * @param bitsPerSample the sample depth in bit for AudioPassThru
     */
    public AudioPassThruCapabilities setBitsPerSample(@NonNull BitsPerSample bitsPerSample) {
        setValue(KEY_BITS_PER_SAMPLE, bitsPerSample);
        return this;
    }

    /**
     * get  the sample depth in bit for AudioPassThru
     *
     * @return the sample depth in bit for AudioPassThru
     */
    public BitsPerSample getBitsPerSample() {
        return (BitsPerSample) getObject(BitsPerSample.class, KEY_BITS_PER_SAMPLE);
    }

    /**
     * set the audioType for AudioPassThru
     *
     * @param audioType the audioType for AudioPassThru
     */
    public AudioPassThruCapabilities setAudioType(@NonNull AudioType audioType) {
        setValue(KEY_AUDIO_TYPE, audioType);
        return this;
    }

    /**
     * get the audioType for AudioPassThru
     *
     * @return the audioType for AudioPassThru
     */
    public AudioType getAudioType() {
        return (AudioType) getObject(AudioType.class, KEY_AUDIO_TYPE);
    }
}
