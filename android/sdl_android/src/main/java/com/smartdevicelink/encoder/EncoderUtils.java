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

import android.annotation.TargetApi;
import android.media.MediaFormat;
import android.os.Build;
import android.util.Log;

import java.nio.ByteBuffer;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public final class EncoderUtils {
    private final static String TAG = "EncoderUtils";

    /**
     * Extracts codec-specific data from MediaFormat instance
     *
     * Currently, only AVC is supported.
     *
     * @param format MediaFormat instance retrieved from MediaCodec
     * @return byte array containing codec-specific data, or null if an error occurred
     */
    public static byte[] getCodecSpecificData(MediaFormat format) {
        if (format == null) {
            return null;
        }

        String name = format.getString(MediaFormat.KEY_MIME);
        if (name == null) {
            return null;
        }

        // same as MediaFormat.MIMETYPE_VIDEO_AVC but it requires API level 21
        if (name.equals("video/avc")) {
            return getAVCCodecSpecificData(format);
        } else {
            Log.w(TAG, "Retrieving codec-specific data for " + name + " is not supported");
            return null;
        }
    }

    /**
     * Extracts H.264 codec-specific data (SPS and PPS) from MediaFormat instance
     *
     * The codec-specific data is in byte-stream format; 4-byte start codes (0x00 0x00 0x00 0x01)
     * are added in front of SPS and PPS NAL units.
     *
     * @param format MediaFormat instance retrieved from MediaCodec
     * @return byte array containing codec-specific data, or null if an error occurred
     */
    private static byte[] getAVCCodecSpecificData(MediaFormat format) {
        // For H.264, "csd-0" contains SPS and "csd-1" contains PPS. Refer to the documentation
        // of MediaCodec.
        if (!(format.containsKey("csd-0") && format.containsKey("csd-1"))) {
            Log.w(TAG, "H264 codec specific data not found");
            return null;
        }

        ByteBuffer sps = format.getByteBuffer("csd-0");
        int spsLen = sps.remaining();
        ByteBuffer pps = format.getByteBuffer("csd-1");
        int ppsLen = pps.remaining();

        byte[] output = new byte[spsLen + ppsLen];
        try {
            sps.get(output, 0, spsLen);
            pps.get(output, spsLen, ppsLen);
        } catch (Exception e) {
            // should not happen
            Log.w(TAG, "Error while copying H264 codec specific data: " + e);
            return null;
        }

        return output;
    }

    private EncoderUtils() {}
}
