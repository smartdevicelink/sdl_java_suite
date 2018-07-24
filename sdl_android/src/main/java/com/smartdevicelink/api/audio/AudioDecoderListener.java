package com.smartdevicelink.api.audio;

import java.nio.ByteBuffer;

public interface AudioDecoderListener {
    void onAudioDataAvailable(SampleBuffer sampleBuffer);

    void onDecoderFinish();

    void onDecoderError(Exception e);
}
