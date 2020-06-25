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

import com.smartdevicelink.managers.audio.AudioStreamManager.SampleType;
import com.smartdevicelink.util.DebugTool;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Wraps a buffer of raw audio samples depending on the sample type (8 bit, 16 bit)
 * Unifies samples into double.
 */
public class SampleBuffer {
    private static final String TAG = SampleBuffer.class.getSimpleName();

    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    private @SampleType final int sampleType;
    private final ByteBuffer byteBuffer;
    private final int channelCount;
    private final long presentationTimeUs;

    /**
     * Wraps a raw (mono) byte buffer to a new sample buffer.
     * @param buffer The raw buffer to be wrapped.
     * @param sampleType The sample type of the samples in the raw buffer.
     * @param presentationTimeUs The presentation time of the buffer.
     * @return A new sample buffer wrapping the specified raw buffer.
     */
    public static SampleBuffer wrap(ByteBuffer buffer, @SampleType int sampleType, long presentationTimeUs) {
        return new SampleBuffer(buffer, sampleType, 1, presentationTimeUs);
    }

    /**
     * Wraps a raw byte buffer to a new sample buffer.
     * @param buffer The raw buffer to be wrapped.
     * @param sampleType The sample type of the samples in the raw buffer.
     * @param channelCount The number of channels (1 = mono, 2 = stereo).
     * @param presentationTimeUs The presentation time of the buffer.
     * @return A new sample buffer wrapping the specified raw buffer.
     */
    public static SampleBuffer wrap(ByteBuffer buffer, @SampleType int sampleType, int channelCount, long presentationTimeUs) {
        return new SampleBuffer(buffer, sampleType, channelCount, presentationTimeUs);
    }

    /**
     * Allocates a new sample buffer.
     * @param capacity The specified sample capacity of the sample buffer.
     * @param sampleType The sample type of the samples the buffer should store.
     * @param byteOrder The byte order for the samples (little or big endian).
     * @param presentationTimeUs The presentation time for the buffer.
     * @return A new and empty sample buffer.
     */
    public static SampleBuffer allocate(int capacity, @SampleType int sampleType, ByteOrder byteOrder, long presentationTimeUs) {
        return new SampleBuffer(capacity, sampleType, 1, byteOrder, presentationTimeUs);
    }

    /**
     * Allocates a new sample buffer.
     * @param capacity The specified sample capacity of the sample buffer.
     * @param sampleType The sample type of the samples the buffer should store.
     * @param channelCount The number of channels (1 = mono, 2 = stereo).
     * @param byteOrder The byte order for the samples (little or big endian).
     * @param presentationTimeUs The presentation time for the buffer.
     * @return A new and empty sample buffer.
     */
    @SuppressWarnings("unused")
    public static SampleBuffer allocate(int capacity, @SampleType int sampleType, int channelCount, ByteOrder byteOrder, long presentationTimeUs) {
        return new SampleBuffer(capacity, sampleType, channelCount, byteOrder, presentationTimeUs);
    }

    private SampleBuffer(int capacity, @SampleType int sampleType, int channelCount, ByteOrder byteOrder, long presentationTimeUs) {
        this.byteBuffer = ByteBuffer.allocate(sampleType * capacity);
        this.byteBuffer.order(byteOrder);
        this.sampleType = sampleType;
        this.channelCount = channelCount;
        this.presentationTimeUs = presentationTimeUs;
    }

    private SampleBuffer(ByteBuffer buffer, @SampleType int sampleType, int channelCount, long presentationTimeUs) {
        this.byteBuffer = buffer;
        this.sampleType = sampleType;
        this.channelCount = channelCount;
        this.presentationTimeUs = presentationTimeUs;
    }

    /**
     * Returns the capacity of the buffer per channel.
     */
    @SuppressWarnings("unused")
    public int capacity() {
        return byteBuffer.capacity() / sampleType / channelCount;
    }

    /**
     * Returns the number of samples in the buffer per channel.
     */
    public int limit() {
        return byteBuffer.limit() / sampleType / channelCount;
    }

    /**
     * Sets the number of samples in the buffer to the new limit.
     * @param newLimit The new limit of the sample buffer.
     */
    public void limit(int newLimit) {
        byteBuffer.limit(newLimit * sampleType * channelCount);
    }

    /**
     * Returns the current position in the buffer per channel.
     * @return The position of the sample buffer.
     */
    public int position() {
        return byteBuffer.position() / sampleType / channelCount;
    }

    /**
     *Sets the position of the sample buffer to the new index.
     * @param newPosition The new position of the sample buffer.
     */
    public void position(int newPosition) {
        byteBuffer.position(newPosition * sampleType * channelCount);
    }

    /**
     * Returns the sample of the current position and then increments the position.
     * The sample returned is a mixed sample getting all samples from each channel.
     * @return The mixed sample.
     */
    public double get() {
        // convenient method to avoid duplicate code: we use -1 index to call get()
        return get(-1);
    }

    /**
     * Returns the sample from the given index in the buffer.
     * If the buffer's channel count is > 1 the sample returned
     * is a mixed sample getting all samples from each channel.
     * @param index The index of the sample requested.
     * @return The sample requested.
     */
    public double get(int index) {
        int internalIndex = index * channelCount * sampleType;

        switch (sampleType) {
            case SampleType.UNSIGNED_8_BIT: {
                double avg = 0;

                // get a sample mix to mono from the index
                for (int i = 0; i < channelCount; i++) {
                    byte b = index == -1 ? byteBuffer.get() : byteBuffer.get(internalIndex + i * sampleType);
                    int a = b & 0xff; // convert the 8 bits into int so we can calc > 127
                    avg += a / (double)channelCount;
                }

                return avg * 2.0 / 255.0 - 1.0; //magic? check out SampleType
            }
            case SampleType.SIGNED_16_BIT: {
                double avg = 0;

                // get a sample mix to mono from the index
                for (int i = 0; i < channelCount; i++) {
                    short a = index == -1 ? byteBuffer.getShort() : byteBuffer.getShort(internalIndex + i * sampleType);
                    avg += a / (double)channelCount;
                }

                return (avg + 32768.0) * 2.0 / 65535.0 - 1.0; //magic? check out SampleType
            }
            case SampleType.FLOAT: {
                double avg = 0;

                // get a sample mix to mono from the index
                for (int i = 0; i < channelCount; i++) {
                    double a = index == -1 ? byteBuffer.getFloat() : byteBuffer.getFloat(internalIndex + i * sampleType);
                    avg += a / (double)channelCount;
                }

                return avg;
            }
            default: {
                DebugTool.logError("SampleBuffer.get(int): The sample type is not known: " + sampleType);
                return 0.0;
            }
        }
    }

    /**
     * Puts a sample to the current position and increments the position.
     * @param sample The sample to put into the buffer.
     */
    public void put(double sample) {
        put(-1, sample);
    }

    /**
     * Puts a sample to the given index in the buffer.
     * If the buffer's channel count is > 1 the sample
     * will be stored in each channel at the given index.
     * @param index The index to put the sample.
     * @param sample The sample to store in the buffer.
     */
    public void put(int index, double sample) {
        int internalIndex = index * channelCount * sampleType;
        switch (sampleType) {
            case SampleType.UNSIGNED_8_BIT: {
                int a = (int)Math.round((sample + 1.0) * 255.0 / 2.0); //magic? check out SampleType
                byte b = (byte)a;
                if (index == -1) {
                    for (int i = 0; i < channelCount; i++) {
                        byteBuffer.put(b);
                    }
                } else {
                    for (int i = 0; i < channelCount; i++) {
                        byteBuffer.put(internalIndex + i * sampleType, b);
                    }
                }
                break;
            }
            case SampleType.SIGNED_16_BIT: {
                short a = (short)Math.round((sample + 1.0) * 65535 / 2.0 - 32767.0); //magic? check out SampleType
                if (index == -1) {
                    for (int i = 0; i < channelCount; i++) {
                        byteBuffer.putShort(a);
                    }
                } else {
                    for (int i = 0; i < channelCount; i++) {
                        byteBuffer.putShort(internalIndex + i * sampleType, a);
                    }
                }
                break;
            }
            case SampleType.FLOAT: {
                if (index == -1) {
                    for (int i = 0; i < channelCount; i++) {
                        byteBuffer.putFloat((float) sample);
                    }
                } else {
                    for (int i = 0; i < channelCount; i++) {
                        byteBuffer.putFloat(internalIndex + i * sampleType, (float) sample);
                    }
                }
                break;
            }
            default: {
                DebugTool.logError("SampleBuffer.set(int): The sample type is not known: " + sampleType);
            }
        }
    }

    /**
     * Returns the raw byte buffer managed by this sample buffer.
     * @return The raw byte buffer managed by this sample buffer.
     */
    public ByteBuffer getByteBuffer() {
        return byteBuffer;
    }

    /**
     * Returns a copy of the bytes from position 0 to the current limit.
     * @return A copy of the bytes.
     */
    public byte[] getBytes() {
        int limit = byteBuffer.limit();
        byte[] bytes = new byte[limit];

        for (int i = 0; i < limit; ++i) {
            bytes[i] = byteBuffer.get(i);
        }

        return bytes;
    }

    /**
     * The presentation time of this sample buffer.
     * @return The presentation time of this sample buffer.
     */
    public long getPresentationTimeUs() {
        return presentationTimeUs;
    }
}
