/*
 * Copyright (c) 2017, Xevo Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.smartdevicelink.streaming.audio;

/**
 * A struct to hold LPCM specific audio format information.
 */
public class AudioStreamingLPCMParams extends AudioStreamingParams {
	/**
	 * Sample format of linear PCM data.
	 */
	public enum SampleFormat {
		/**
		 * LPCM data is represented by 8-bit unsigned integers. Center point is 128.
		 */
		LPCM_8BIT_UNSIGNED,

		/**
		 * LPCM data is represented by 16-bit signed integers, in little endian.
		 */
		LPCM_16BIT_SIGNED_LITTLE_ENDIAN,
	}

	/**
	 * Sample format in which app will provide LPCM data to
	 * IAudioStreamListener.sendAudio()
	 * <p>
	 * This is reserved for future and not used right now.
	 */
	public final SampleFormat sampleFormat;

	public AudioStreamingLPCMParams(SampleFormat sampleFormat, int samplingRate, int channels) {
		super(samplingRate, channels);
		this.sampleFormat = sampleFormat;
	}
}
