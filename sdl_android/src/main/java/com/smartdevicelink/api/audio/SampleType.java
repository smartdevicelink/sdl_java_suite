package com.smartdevicelink.api.audio;

public enum SampleType {
    // ref https://developer.android.com/reference/android/media/AudioFormat "Encoding" section
    // The audio sample is a 8 bit unsigned integer in the range [0, 255], with a 128 offset for zero.
    // This is typically stored as a Java byte in a byte array or ByteBuffer. Since the Java byte is
    // signed, be careful with math operations and conversions as the most significant bit is inverted.
    //
    // The unsigned byte range is [0, 255] and should be converted to double [-1.0, 1.0]
    // The 8 bits of the byte are easily converted to int by using bitwise operator
    UNSIGNED_8_BIT(Byte.SIZE),

    // ref https://developer.android.com/reference/android/media/AudioFormat "Encoding" section
    // The audio sample is a 16 bit signed integer typically stored as a Java short in a short array,
    // but when the short is stored in a ByteBuffer, it is native endian (as compared to the default Java big endian).
    // The short has full range from [-32768, 32767], and is sometimes interpreted as fixed point Q.15 data.
    //
    // the conversion is slightly easier from [-32768, 32767] to [-1.0, 1.0]
    SIGNED_16_BIT(Short.SIZE),

    // ref https://developer.android.com/reference/android/media/AudioFormat "Encoding" section
    // Introduced in API Build.VERSION_CODES.LOLLIPOP, this encoding specifies that the audio sample
    // is a 32 bit IEEE single precision float. The sample can be manipulated as a Java float in a
    // float array, though within a ByteBuffer it is stored in native endian byte order. The nominal
    // range of ENCODING_PCM_FLOAT audio data is [-1.0, 1.0].
    FLOAT(Float.SIZE);

    public final int bytes;

    SampleType(int size) {
        bytes = size >> 3;
    }
}