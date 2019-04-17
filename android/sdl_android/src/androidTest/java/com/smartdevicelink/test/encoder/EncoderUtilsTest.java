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

package com.smartdevicelink.test.encoder;

import android.annotation.TargetApi;
import android.media.MediaFormat;
import android.os.Build;

import com.smartdevicelink.encoder.EncoderUtils;

import junit.framework.TestCase;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.encoder.EncoderUtils}
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class EncoderUtilsTest extends TestCase {
    public void testGetCodecSpecificDataWithNull() {
        byte[] result = EncoderUtils.getCodecSpecificData(null);
        assertNull(result);
    }

    public void testGetCodecSpecificDataForAVC() {
        // example of SPS NAL unit with 4-byte start code
        byte[] sps = new byte[] {
                0x00, 0x00, 0x00, 0x01,
                0x67, 0x42, (byte)0xC0, 0x0A, (byte)0xA6, 0x11, 0x11, (byte)0xE8,
                0x40, 0x00, 0x00, (byte)0xFA, 0x40, 0x00, 0x3A, (byte)0x98,
                0x23, (byte)0xC4, (byte)0x89, (byte)0x84, 0x60
        };
        // example of PPS NAL unit with 4-byte start code
        byte[] pps = new byte[] {
                0x00, 0x00, 0x00, 0x01,
                0x68, (byte)0xC8, 0x42, 0x0F, 0x13, 0x20
        };

        ByteBuffer spsByteBuffer = ByteBuffer.allocate(sps.length);
        spsByteBuffer.put(sps);
        spsByteBuffer.flip();

        ByteBuffer ppsByteBuffer = ByteBuffer.allocate(pps.length);
        ppsByteBuffer.put(pps);
        ppsByteBuffer.flip();

        MediaFormat format = MediaFormat.createVideoFormat("video/avc", 16, 16);
        format.setByteBuffer("csd-0", spsByteBuffer);
        format.setByteBuffer("csd-1", ppsByteBuffer);

        byte[] result = EncoderUtils.getCodecSpecificData(format);
        assertNotNull(result);

        byte[] expected = new byte[sps.length + pps.length];
        System.arraycopy(sps, 0, expected, 0, sps.length);
        System.arraycopy(pps, 0, expected, sps.length, pps.length);
        assertTrue("Output codec specific data doesn't match", Arrays.equals(expected, result));
    }

    public void testGetCodecSpecificDataWithInvalidAVCData() {
        // testing an error case when the encoder emits SPS only (which should not happen)
        byte[] sps = new byte[] {
                0x00, 0x00, 0x00, 0x01,
                0x67, 0x42, (byte)0xC0, 0x0A, (byte)0xA6, 0x11, 0x11, (byte)0xE8,
                0x40, 0x00, 0x00, (byte)0xFA, 0x40, 0x00, 0x3A, (byte)0x98,
                0x23, (byte)0xC4, (byte)0x89, (byte)0x84, 0x60
        };

        ByteBuffer spsByteBuffer = ByteBuffer.allocate(sps.length);
        spsByteBuffer.put(sps);
        spsByteBuffer.flip();

        MediaFormat format = MediaFormat.createVideoFormat("video/avc", 16, 16);
        format.setByteBuffer("csd-0", spsByteBuffer);
        // no PPS

        byte[] result = EncoderUtils.getCodecSpecificData(format);
        assertNull(result);
    }

    public void testGetCodecSpecificDataForUnknownCodec() {
        MediaFormat format = MediaFormat.createVideoFormat("video/raw", 16, 16);
        byte[] result = EncoderUtils.getCodecSpecificData(format);
        assertNull("For unsupported codec, getCodecSpecificData should return null", result);
    }
}
