package com.smartdevicelink.managers.audio;

/**
 * An interface for the audio decoder classes.
 * The caller using the audio decoder will be
 * notified when the decoding is finished or if an error occurred.
 * During decoding the caller receives sample buffers with decoded audio data.
 */
public interface AudioDecoderListener {
    /**
     * Notifies that decoded audio data is available.
     * @param sampleBuffer The sample buffer holding the decoded audio data.
     */
    void onAudioDataAvailable(SampleBuffer sampleBuffer);

    /**
     * Notifies that the audio decoding is finished.
     * @param success Indicates whether audio decoding was successful or if an error occurred.
     */
    void onDecoderFinish(boolean success);

    /**
     * Notifies the caller that an error/exception occurred during audio decoding.
     * @param e The exception storing information about the error.
     */
    void onDecoderError(Exception e);
}
