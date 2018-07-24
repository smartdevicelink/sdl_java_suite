package com.smartdevicelink.api.audio;

import android.util.Log;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Wraps a buffer of raw audio samples depending on the sample type (8 bit, 16 bit)
 * Unifies samples into double.
 */
public class SampleBuffer {
    private static final String TAG = SampleBuffer.class.getSimpleName();

    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    private SampleType sampleType;
    private ByteBuffer byteBuffer;
    private long presentationTimeUs;

    public static SampleBuffer wrap(ByteBuffer buffer, SampleType sampleType, long presentationTimeUs) {
        return new SampleBuffer(buffer, sampleType, presentationTimeUs);
    }

    /**
     * Allocates memory for a sample buffer with capacity provided
     * @param capacity The number of samples the buffer should hold
     * @param sampleType the type of the samples
     * @param byteOrder the byte order of the samples if a sampe size is > 1 byte
     * @return
     */
    public static SampleBuffer allocate(int capacity, SampleType sampleType, ByteOrder byteOrder, long presentationTimeUs) {
        return new SampleBuffer(capacity, sampleType, byteOrder, presentationTimeUs);
    }

    private SampleBuffer(int capacity, SampleType sampleType, ByteOrder byteOrder, long presentationTimeUs) {
        this.byteBuffer = ByteBuffer.allocate(sampleType.bytes * capacity);
        this.byteBuffer.order(byteOrder);
        this.sampleType = sampleType;
        this.presentationTimeUs = presentationTimeUs;
    }

    private SampleBuffer(ByteBuffer buffer, SampleType sampleType, long presentationTimeUs) {
        this.byteBuffer = buffer;
        this.sampleType = sampleType;
        this.presentationTimeUs = presentationTimeUs;
    }

    // returns the number of samples in the buffer
    public int limit() {
        return byteBuffer.limit() / sampleType.bytes;
    }

    public void limit(int newLimit) {
        byteBuffer.limit(newLimit * sampleType.bytes);
    }

    public int position() {
        return byteBuffer.position() / sampleType.bytes;
    }

    public void position(int newPosition) {
        byteBuffer.position(newPosition * sampleType.bytes);
    }

    public double get() {
        // convenient method to avoid duplicate code: we use -1 index to call get()
        return get(-1);
    }

    // returns the sample at the given index
    public double get(int index) {
        switch (sampleType) {
            case UNSIGNED_8_BIT: {
                byte b = index == -1 ? byteBuffer.get() : byteBuffer.get(index);
                int a = b & 0xff; // convert the 8 bits into int so we can calc > 127
                return a * 2.0 / 255.0 - 1.0; //magic? check out SampleType
            }
            case SIGNED_16_BIT: {
                short a = index == -1 ? byteBuffer.getShort() : byteBuffer.getShort(index * sampleType.bytes);
                return (a + 32768.0) * 2.0 / 65535.0 - 1.0; //magic? check out SampleType
            }
            case FLOAT: {
                return index == -1 ? byteBuffer.getFloat() : byteBuffer.getFloat(index * sampleType.bytes);
            }
            default: {
                Log.e(TAG, "SampleBuffer.get(int): The sample type is not known: " + sampleType.toString());
                return 0.0;
            }
        }
    }

    public void put(double sample) {
        put(-1, sample);
    }

    public void put(int index, double sample) {
        switch (sampleType) {
            case UNSIGNED_8_BIT: {
                int a = (int)Math.round((sample + 1.0) * 255.0 / 2.0); //magic? check out SampleType
                byte b = (byte)a;
                if (index == -1) {
                    byteBuffer.put(b);
                } else {
                    byteBuffer.put(index, b);
                }
                break;
            }
            case SIGNED_16_BIT: {
                short a = (short)Math.round((sample + 1.0) * 65535 / 2.0 - 32767.0); //magic? check out SampleType
                if (index == -1) {
                    byteBuffer.putShort(a);
                } else {
                    byteBuffer.putShort(index * sampleType.bytes, a);
                }
                break;
            }
            case FLOAT: {
                if (index == -1) {
                    byteBuffer.putFloat((float)sample);
                } else {
                    byteBuffer.putFloat(index * sampleType.bytes, (float)sample);
                }
                break;
            }
            default: {
                Log.e(TAG, "SampleBuffer.set(int): The sample type is not known: " + sampleType.toString());
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
