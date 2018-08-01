package com.smartdevicelink.api.audio;

import android.util.Log;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import com.smartdevicelink.api.audio.AudioStreamManager.SampleType;

/**
 * Wraps a buffer of raw audio samples depending on the sample type (8 bit, 16 bit)
 * Unifies samples into double.
 */
public class SampleBuffer {
    private static final String TAG = SampleBuffer.class.getSimpleName();

    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    private @SampleType int sampleType;
    private ByteBuffer byteBuffer;
    private int channelCount;
    private long presentationTimeUs;

    public static SampleBuffer wrap(ByteBuffer buffer, @SampleType int sampleType, long presentationTimeUs) {
        return new SampleBuffer(buffer, sampleType, 1, presentationTimeUs);
    }

    public static SampleBuffer wrap(ByteBuffer buffer, @SampleType int sampleType, int channelCount, long presentationTimeUs) {
        return new SampleBuffer(buffer, sampleType, channelCount, presentationTimeUs);
    }

    public static SampleBuffer allocate(int capacity, @SampleType int sampleType, ByteOrder byteOrder, long presentationTimeUs) {
        return new SampleBuffer(capacity, sampleType, 1, byteOrder, presentationTimeUs);
    }

    /**
     * Allocates memory for a sample buffer with capacity provided
     * @param capacity The number of samples the buffer should hold
     * @param sampleType The type of the samples
     * @param channelCount The number of channels requested (mono use 1, stereo use 2)
     * @param byteOrder The byte order of the samples if a sampe size is > 1 byte
     * @return
     */
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
     * @param newLimit
     */
    public void limit(int newLimit) {
        byteBuffer.limit(newLimit * sampleType * channelCount);
    }

    /**
     * Returns the current position in the buffer per channel.
     * @return
     */
    public int position() {
        return byteBuffer.position() / sampleType / channelCount;
    }

    /**
     *Sets the position of the sample buffer to the new index.
     * @param newPosition
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
     * The sample returned is a mixed sample getting all samples from each channel.
     * @param index
     * @return
     */
    public double get(int index) {
        int internalIndex = index * channelCount * sampleType;

        switch (sampleType) {
            case SampleType.UNSIGNED_8_BIT: {
                double avg = 0;

                // get a sample mix to mono from the index
                for (int i = 0; i < channelCount; i++) {
                    byte b = index == -1 ? byteBuffer.get() : byteBuffer.get(internalIndex + i);
                    int a = b & 0xff; // convert the 8 bits into int so we can calc > 127
                    avg += a / (double)channelCount;
                }

                return avg * 2.0 / 255.0 - 1.0; //magic? check out SampleType
            }
            case SampleType.SIGNED_16_BIT: {
                double avg = 0;

                // get a sample mix to mono from the index
                for (int i = 0; i < channelCount; i++) {
                    short a = index == -1 ? byteBuffer.getShort() : byteBuffer.getShort(internalIndex + i);
                    avg += a / (double)channelCount;
                }

                return (avg + 32768.0) * 2.0 / 65535.0 - 1.0; //magic? check out SampleType
            }
            case SampleType.FLOAT: {
                double avg = 0;

                // get a sample mix to mono from the index
                for (int i = 0; i < channelCount; i++) {
                    double a = index == -1 ? byteBuffer.getFloat() : byteBuffer.getFloat(internalIndex);
                    avg += a / (double)channelCount;
                }

                return avg;
            }
            default: {
                Log.e(TAG, "SampleBuffer.get(int): The sample type is not known: " + sampleType);
                return 0.0;
            }
        }
    }

    public void put(double sample) {
        put(-1, sample);
    }

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
                        byteBuffer.put(internalIndex + i, b);
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
                        byteBuffer.putShort(internalIndex + i, a);
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
                        byteBuffer.putFloat(internalIndex + i, (float) sample);
                    }
                }
                break;
            }
            default: {
                Log.e(TAG, "SampleBuffer.set(int): The sample type is not known: " + sampleType);
            }
        }
    }

    public ByteBuffer getByteBuffer() {
        return byteBuffer;
    }

    public byte[] getBytes() {
        int limit = byteBuffer.limit();
        byte[] bytes = new byte[limit];

        for (int i = 0; i < limit; ++i) {
            bytes[i] = byteBuffer.get(i);
        }

        return bytes;
    }

    public long getPresentationTimeUs() {
        return presentationTimeUs;
    }
}
