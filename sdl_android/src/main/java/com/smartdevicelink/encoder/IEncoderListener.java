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

package com.smartdevicelink.encoder;

/**
 * A listener that receives a chunk of data from an encoder.
 */
public interface IEncoderListener {
	/**
	 * Definitions of data formats.
	 */
	enum Format {
		/**
		 * H.264 byte stream in Annex B format. "data" contains one or more H.264 NAL units.
		 */
		H264_BYTE_STREAM,
	}

	/**
	 * Called when a chunk of data is output by the encoder.
	 *
	 * @param format             The format of the data
	 * @param data               The raw data output by the encoder
	 * @param presentationTimeUs The presentation timestamp (PTS) of this data in microseconds
	 */
	void onEncoderOutput(Format format, byte[] data, long presentationTimeUs);

	/**
	 * Called when a chunk of data is output by the encoder.
	 *
	 * @param format             The format of the data
	 * @param data               An array containing the raw data output by the encoder
	 * @param offset             Starting offset in 'data'
	 * @param length             Length of the raw data
	 * @param presentationTimeUs The presentation timestamp (PTS) of this data in microseconds
	 *
	 * @throws ArrayIndexOutOfBoundsException When offset does not satisfy
	 *                                        {@code 0 <= offset && offset <= data.length}
	 *                                        or length does not satisfy
	 *                                        {@code 0 < length && offset + length <= data.length}
	 */
	void onEncoderOutput(Format format, byte[] data, int offset, int length, long presentationTimeUs)
		throws ArrayIndexOutOfBoundsException;
}
