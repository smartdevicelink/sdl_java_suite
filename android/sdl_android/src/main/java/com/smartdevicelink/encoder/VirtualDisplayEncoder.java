/*
 * Copyright (c) 2017 Livio, Inc.
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
 * Neither the name of the Livio Inc. nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
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

package com.smartdevicelink.encoder;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.os.Build;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.Surface;

import com.smartdevicelink.proxy.rpc.ImageResolution;
import com.smartdevicelink.proxy.rpc.VideoStreamingFormat;
import com.smartdevicelink.proxy.rpc.enums.VideoStreamingCodec;
import com.smartdevicelink.streaming.video.IVideoStreamListener;
import com.smartdevicelink.streaming.video.VideoStreamingParameters;
import com.smartdevicelink.util.DebugTool;

import java.nio.ByteBuffer;

@TargetApi(19)
@SuppressWarnings("NullableProblems")
public class VirtualDisplayEncoder {
    private static final String TAG = "VirtualDisplayEncoder";
    private VideoStreamingParameters streamingParams = new VideoStreamingParameters();
    private DisplayManager mDisplayManager;
    private volatile MediaCodec mVideoEncoder = null;
    private volatile MediaCodec.BufferInfo mVideoBufferInfo = null;
    private volatile Surface inputSurface = null;
    private volatile VirtualDisplay virtualDisplay = null;
    private IVideoStreamListener mOutputListener;
    private Boolean initPassed = false;
    private final Object STREAMING_LOCK = new Object();

    private int predefinedWidth = 1920;
    private int predefinedHeight = 1080;
    // Codec-specific data (SPS and PPS)
    private byte[] mH264CodecSpecificData = null;

    //For older (<21) OS versions
    private Thread encoderThread;


    /**
     * Initialization method for VirtualDisplayEncoder object. MUST be called before start() or shutdown()
     * Will overwrite previously set videoWeight and videoHeight
     *
     * @param context         to create the virtual display
     * @param outputListener  the listener that the video frames will be sent through
     * @param streamingParams parameters to create the virtual display and encoder
     * @throws Exception if the API level is <19 or supplied parameters were null
     */
    public void init(Context context, IVideoStreamListener outputListener, VideoStreamingParameters streamingParams) throws Exception {
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            DebugTool.logError(TAG, "API level of 19 required for VirtualDisplayEncoder");
            throw new Exception("API level of 19 required");
        }

        if (context == null || outputListener == null || streamingParams == null || streamingParams.getResolution() == null || streamingParams.getFormat() == null) {
            DebugTool.logError(TAG, "init parameters cannot be null for VirtualDisplayEncoder");
            throw new Exception("init parameters cannot be null");
        }

        this.mDisplayManager = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);

        this.streamingParams.update(streamingParams);

        mOutputListener = outputListener;

        initPassed = true;
    }

    public VideoStreamingParameters getStreamingParams() {
        return this.streamingParams;
    }

    public void setStreamingParams(int displayDensity, ImageResolution resolution, int frameRate, int bitrate, int interval, VideoStreamingFormat format) {
        this.streamingParams = new VideoStreamingParameters(displayDensity, frameRate, bitrate, interval, resolution, format);
    }

    public void setStreamingParams(VideoStreamingParameters streamingParams) {
        this.streamingParams = streamingParams;
    }

    /**
     * NOTE: Must call init() without error before calling this method.
     * Prepares the encoder and virtual display.
     */
    public void start() throws Exception {
        if (!initPassed) {
            DebugTool.logError(TAG, "VirtualDisplayEncoder was not properly initialized with the init() method.");
            return;
        }
        if (streamingParams == null || streamingParams.getResolution() == null || streamingParams.getFormat() == null) {
            return;
        }

        synchronized (STREAMING_LOCK) {

            try {
                inputSurface = prepareVideoEncoder();

                // Create a virtual display that will output to our encoder.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && virtualDisplay != null) {
                    virtualDisplay.setSurface(null);
                    virtualDisplay.setSurface(inputSurface);
                }
                else {
                    // recreate after stop in most of cases
                    virtualDisplay = mDisplayManager.createVirtualDisplay(TAG,
                            predefinedWidth, predefinedHeight,
                            streamingParams.getDisplayDensity(), inputSurface, DisplayManager.VIRTUAL_DISPLAY_FLAG_PRESENTATION);
                }


                startEncoder();

            } catch (Exception ex) {
                DebugTool.logError(TAG, "Unable to create Virtual Display.");
                throw new RuntimeException(ex);
            }
        }
    }

    public void shutDown(boolean withPendingRestart) {
        if (!initPassed) {
            DebugTool.logError(TAG, "VirtualDisplayEncoder was not properly initialized with the init() method.");
            return;
        }
        try {
            if (encoderThread != null) {
                encoderThread.interrupt();
                encoderThread = null;
            }

            if (mVideoEncoder != null) {
                mVideoEncoder.stop();
                mVideoEncoder.release();
                mVideoEncoder = null;
            }

            if (virtualDisplay != null && !withPendingRestart) {
                virtualDisplay.release();
                virtualDisplay = null;
            }

            if (inputSurface != null) {
                inputSurface.release();
                inputSurface = null;
            }
        } catch (Exception ex) {
            DebugTool.logError(TAG, "shutDown() failed");
        }
    }

    private Surface prepareVideoEncoder() {

        if (streamingParams == null || streamingParams.getResolution() == null || streamingParams.getFormat() == null) {
            return null;
        }

        if (mVideoBufferInfo == null) {
            mVideoBufferInfo = new MediaCodec.BufferInfo();
        }

        String videoMimeType = getMimeForFormat(streamingParams.getFormat());

        MediaFormat format = MediaFormat.createVideoFormat(videoMimeType, streamingParams.getResolution().getResolutionWidth(), streamingParams.getResolution().getResolutionHeight());

        // Set some required properties. The media codec may fail if these aren't defined.
        format.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface);
        format.setInteger(MediaFormat.KEY_BIT_RATE, streamingParams.getBitrate());
        format.setInteger(MediaFormat.KEY_FRAME_RATE, streamingParams.getFrameRate());
        format.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, streamingParams.getInterval()); // seconds between I-frames


        // Create a MediaCodec encoder and configure it. Get a Surface we can use for recording into.
        try {
            mVideoEncoder = MediaCodec.createEncoderByType(videoMimeType);
            mVideoEncoder.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
            Surface surface = mVideoEncoder.createInputSurface(); //prepared

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mVideoEncoder.setCallback(new MediaCodec.Callback() {
                    @Override
                    public void onInputBufferAvailable(MediaCodec codec, int index) {
                        // nothing to do here
                    }

                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onOutputBufferAvailable(MediaCodec codec, int index, MediaCodec.BufferInfo info) {
                        try {
                            ByteBuffer encodedData = codec.getOutputBuffer(index);
                            if (encodedData != null) {
                                if ((info.flags & MediaCodec.BUFFER_FLAG_CODEC_CONFIG) != 0) {
                                    info.size = 0;
                                }
                                if (info.size != 0) {
                                    byte[] dataToWrite;// = new byte[info.size];
                                    int dataOffset = 0;

                                    // append SPS and PPS in front of every IDR NAL unit
                                    if ((info.flags & MediaCodec.BUFFER_FLAG_KEY_FRAME) != 0 && mH264CodecSpecificData != null) {
                                        dataToWrite = new byte[mH264CodecSpecificData.length + info.size];
                                        System.arraycopy(mH264CodecSpecificData, 0, dataToWrite, 0, mH264CodecSpecificData.length);
                                        dataOffset = mH264CodecSpecificData.length;
                                    } else {
                                        dataToWrite = new byte[info.size];
                                    }

                                    encodedData.position(info.offset);
                                    encodedData.limit(info.offset + info.size);

                                    encodedData.get(dataToWrite, dataOffset, info.size);
                                    if (mOutputListener != null) {
                                        mOutputListener.sendFrame(dataToWrite, 0, dataToWrite.length, info.presentationTimeUs);
                                    }
                                }

                                codec.releaseOutputBuffer(index, false);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(MediaCodec codec, MediaCodec.CodecException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onOutputFormatChanged(MediaCodec codec, MediaFormat format) {
                        mH264CodecSpecificData = EncoderUtils.getCodecSpecificData(format);
                    }
                });
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                //Implied from previous if check that this has to be older than Build.VERSION_CODES.LOLLIPOP
                encoderThread = new Thread(new EncoderCompat());

            } else {
                DebugTool.logError(TAG, "Unable to start encoder. Android OS version " + Build.VERSION.SDK_INT + "is not supported");
            }

            return surface;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private void startEncoder() {
        if (mVideoEncoder != null) {
            mVideoEncoder.start();
        }
        if (encoderThread != null) {
            encoderThread.start();
        }
    }

    public Display getDisplay() {
        return virtualDisplay.getDisplay();
    }

    private String getMimeForFormat(VideoStreamingFormat format) {
        if (format != null) {
            VideoStreamingCodec codec = format.getCodec();
            if (codec != null) {
                switch (codec) { //MediaFormat constants are only available in Android 21+
                    case VP8:
                        return "video/x-vnd.on2.vp8"; //MediaFormat.MIMETYPE_VIDEO_VP8
                    case VP9:
                        return "video/x-vnd.on2.vp9"; //MediaFormat.MIMETYPE_VIDEO_VP9
                    case H264: //Fall through
                    default:
                        return "video/avc"; // MediaFormat.MIMETYPE_VIDEO_AVC
                }
            }
        }
        return null;
    }

    private class EncoderCompat implements Runnable {

        @Override
        public void run() {
            try {
                drainEncoder(false);
            } catch (Exception e) {
                DebugTool.logWarning(TAG, "Error attempting to drain encoder");
            } finally {
                mVideoEncoder.release();
            }
        }

        void drainEncoder(boolean endOfStream) {
            if (mVideoEncoder == null || mOutputListener == null) {
                return;
            }

            if (endOfStream) {
                mVideoEncoder.signalEndOfInputStream();
            }

            ByteBuffer[] encoderOutputBuffers = mVideoEncoder.getOutputBuffers();
            Thread currentThread = Thread.currentThread();
            while (!currentThread.isInterrupted()) {
                int encoderStatus = mVideoEncoder.dequeueOutputBuffer(mVideoBufferInfo, -1);
                if (encoderStatus < 0) {
                    if (encoderStatus == MediaCodec.INFO_TRY_AGAIN_LATER) {
                        // no output available yet
                        if (!endOfStream) {
                            break; // out of while
                        }
                    } else if (encoderStatus == MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED) {
                        // not expected for an encoder
                        encoderOutputBuffers = mVideoEncoder.getOutputBuffers();
                    } else if (encoderStatus == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                        if (mH264CodecSpecificData == null) {
                            MediaFormat format = mVideoEncoder.getOutputFormat();
                            mH264CodecSpecificData = EncoderUtils.getCodecSpecificData(format);
                        } else {
                            DebugTool.logWarning(TAG, "Output format change notified more than once, ignoring.");
                        }
                    }
                } else {
                    if ((mVideoBufferInfo.flags & MediaCodec.BUFFER_FLAG_CODEC_CONFIG) != 0) {
                        // If we already retrieve codec specific data via OUTPUT_FORMAT_CHANGED event,
                        // we do not need this data.
                        if (mH264CodecSpecificData != null) {
                            mVideoBufferInfo.size = 0;
                        } else {
                            DebugTool.logInfo(TAG, "H264 codec specific data not retrieved yet.");
                        }
                    }

                    if (mVideoBufferInfo.size != 0) {
                        ByteBuffer encoderOutputBuffer = encoderOutputBuffers[encoderStatus];
                        byte[] dataToWrite;
                        int dataOffset = 0;

                        // append SPS and PPS in front of every IDR NAL unit
                        if ((mVideoBufferInfo.flags & MediaCodec.BUFFER_FLAG_KEY_FRAME) != 0 && mH264CodecSpecificData != null) {
                            dataToWrite = new byte[mH264CodecSpecificData.length + mVideoBufferInfo.size];
                            System.arraycopy(mH264CodecSpecificData, 0, dataToWrite, 0, mH264CodecSpecificData.length);
                            dataOffset = mH264CodecSpecificData.length;
                        } else {
                            dataToWrite = new byte[mVideoBufferInfo.size];
                        }

                        try {
                            encoderOutputBuffer.position(mVideoBufferInfo.offset);
                            encoderOutputBuffer.limit(mVideoBufferInfo.offset + mVideoBufferInfo.size);

                            encoderOutputBuffer.get(dataToWrite, dataOffset, mVideoBufferInfo.size);

                            if (mOutputListener != null) {
                                mOutputListener.sendFrame(dataToWrite, 0, dataToWrite.length, mVideoBufferInfo.presentationTimeUs);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    mVideoEncoder.releaseOutputBuffer(encoderStatus, false);

                    if ((mVideoBufferInfo.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
                        break; // out of while
                    }
                }
            }
        }
    }
}
