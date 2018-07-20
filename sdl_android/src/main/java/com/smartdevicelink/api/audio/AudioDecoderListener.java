package com.smartdevicelink.api.audio;

import java.nio.ByteBuffer;

public interface AudioDecoderListener {
    void onAudioDataAvailable(ByteBuffer buffer);

    void onDecoderFinish();

    void onDecoderError(Exception e);
}
