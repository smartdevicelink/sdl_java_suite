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

import com.smartdevicelink.managers.CompletionListener;

import java.nio.ByteBuffer;

/**
 * A listener that receives audio streaming data from app.
 */
public interface IAudioStreamListener {
	/**
	 * Sends a chunk of audio data to SDL Core.
	 * <p>
	 * Note: this method must not be called after SdlProxyBase.endAudioStream() is called.
	 *
	 * @param data               Byte array containing audio data
	 * @param offset             Starting offset in 'data'
	 * @param length             Length of the data
	 * @param presentationTimeUs (Reserved for future use) Presentation timestamp (PTS) of the
	 *                           last audio sample data included in this chunk, in microseconds.
	 *                           It must be greater than the previous timestamp.
	 *                           Specify -1 if unknown.
	 * @throws ArrayIndexOutOfBoundsException When offset does not satisfy
	 *                                        {@code 0 <= offset && offset <= data.length}
	 *                                        or length does not satisfy
	 *                                        {@code 0 < length && offset + length <= data.length}
	 */
	void sendAudio(byte[] data, int offset, int length, long presentationTimeUs)
			throws ArrayIndexOutOfBoundsException;

	/**
	 * Sends a chunk of audio data to SDL Core.
	 * <p>
	 * Note: this method must not be called after SdlProxyBase.endAudioStream() is called.
	 *
	 * @param data               Data chunk to send. Its position will be updated upon return.
	 * @param presentationTimeUs (Reserved for future use) Presentation timestamp (PTS) of the
	 *                           last audio sample data included in this chunk, in microseconds.
	 *                           It must be greater than the previous timestamp.
	 *                           Specify -1 if unknown.
	 * @param completionListener A completion listener that informs when the audio file is played
	 */
	void sendAudio(ByteBuffer data, long presentationTimeUs, CompletionListener completionListener);
}
