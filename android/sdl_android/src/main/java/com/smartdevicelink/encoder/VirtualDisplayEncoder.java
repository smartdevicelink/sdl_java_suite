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
import android.graphics.SurfaceTexture;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.opengl.GLES20;
import android.os.Build;
import android.os.ConditionVariable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.Surface;

import com.android.grafika.gles.EglCore;
import com.android.grafika.gles.FullFrameRect;
import com.android.grafika.gles.OffscreenSurface;
import com.android.grafika.gles.Texture2dProgram;
import com.android.grafika.gles.WindowSurface;
import com.smartdevicelink.proxy.interfaces.IVideoStreamListener;
import com.smartdevicelink.proxy.rpc.ImageResolution;
import com.smartdevicelink.proxy.rpc.VideoStreamingFormat;
import com.smartdevicelink.proxy.rpc.enums.VideoStreamingCodec;
import com.smartdevicelink.streaming.video.VideoStreamingParameters;

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

    // Codec-specific data (SPS and PPS)
    private byte[] mH264CodecSpecificData = null;

    //For older (<21) OS versions
    private Thread encoderThread;

    private CaptureThread mCaptureThread;
    private EglCore mEglCore;
    private OffscreenSurface mDummySurface;
    private int mTextureId = -1;
    private SurfaceTexture mInterSurfaceTexture;
    private Surface mInterSurface;
    private FullFrameRect mFullFrameBlit;
    private WindowSurface mEncoderSurface;

    /**
     * Initialization method for VirtualDisplayEncoder object. MUST be called before start() or shutdown()
     * Will overwrite previously set videoWeight and videoHeight
     * @param context to create the virtual display
     * @param outputListener the listener that the video frames will be sent through
     * @param streamingParams parameters to create the virtual display and encoder
     * @throws Exception if the API level is <19 or supplied parameters were null
     */
    public void init(Context context, IVideoStreamListener outputListener, VideoStreamingParameters streamingParams) throws Exception {
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            Log.e(TAG, "API level of 19 required for VirtualDisplayEncoder");
            throw new Exception("API level of 19 required");
        }

        if (context == null || outputListener == null || streamingParams == null || streamingParams.getResolution() == null || streamingParams.getFormat() == null) {
            Log.e(TAG, "init parameters cannot be null for VirtualDisplayEncoder");
            throw new Exception("init parameters cannot be null");
        }

        this.mDisplayManager = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);

        this.streamingParams.update(streamingParams);

        mOutputListener = outputListener;

        initPassed = true;
    }

    @SuppressWarnings("unused")
    public VideoStreamingParameters getStreamingParams(){
        return this.streamingParams;
    }

    @SuppressWarnings("unused")
    public void setStreamingParams(int displayDensity, ImageResolution resolution, int frameRate, int bitrate, int interval, VideoStreamingFormat format, boolean stableFrameRate) {
        this.streamingParams = new VideoStreamingParameters(displayDensity, frameRate, bitrate, interval, resolution, format, stableFrameRate);
    }

    @SuppressWarnings("unused")
    public void setStreamingParams(VideoStreamingParameters streamingParams) {
        this.streamingParams = streamingParams;
    }

    /**
     * NOTE: Must call init() without error before calling this method.
     * Prepares the encoder and virtual display.
     */
    public void start() throws Exception {
        if (!initPassed) {
            Log.e(TAG, "VirtualDisplayEncoder was not properly initialized with the init() method.");
            return;
        }
        if (streamingParams == null || streamingParams.getResolution() == null || streamingParams.getFormat() == null) {
            return;
        }

        int width = streamingParams.getResolution().getResolutionWidth();
        int height = streamingParams.getResolution().getResolutionHeight();
        if (streamingParams.isStableFrameRate()) {
            setupGLES(width, height);
        }

        synchronized (STREAMING_LOCK) {

            try {
                if (streamingParams.isStableFrameRate()) {
                    // We use WindowSurface for the input of MediaCodec.
                    mEncoderSurface = new WindowSurface(mEglCore, prepareVideoEncoder(), true);
                    virtualDisplay = mDisplayManager.createVirtualDisplay(TAG,
                            width, height, streamingParams.getDisplayDensity(), mInterSurface, DisplayManager.VIRTUAL_DISPLAY_FLAG_PRESENTATION);

                    startEncoder();
                    // also start capture thread.
                    final ConditionVariable cond = new ConditionVariable();
                    mCaptureThread = new CaptureThread(mEglCore, mInterSurfaceTexture, mTextureId,
                            mEncoderSurface, mFullFrameBlit, width, height, streamingParams.getFrameRate(), new Runnable() {
                        @Override
                        public void run() {
                            cond.open();
                        }
                    });
                    mCaptureThread.start();
                    cond.block(); // make sure Capture thread exists.

                    // setup listener prior to the surface is attached to VirtualDisplay.
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mInterSurfaceTexture.setOnFrameAvailableListener(mCaptureThread, mCaptureThread.getHandler());
                    } else {
                        mInterSurfaceTexture.setOnFrameAvailableListener(mCaptureThread);
                    }
                } else {
                    inputSurface = prepareVideoEncoder();

                    // Create a virtual display that will output to our encoder.
                    virtualDisplay = mDisplayManager.createVirtualDisplay(TAG,
                            streamingParams.getResolution().getResolutionWidth(), streamingParams.getResolution().getResolutionHeight(),
                            streamingParams.getDisplayDensity(), inputSurface, DisplayManager.VIRTUAL_DISPLAY_FLAG_PRESENTATION);

                    startEncoder();
                }

            } catch (Exception ex) {
                Log.e(TAG, "Unable to create Virtual Display.");
                throw new RuntimeException(ex);
            }
        }
    }

    public void shutDown() {
        if (!initPassed) {
            Log.e(TAG, "VirtualDisplayEncoder was not properly initialized with the init() method.");
            return;
        }
        try {
            // cleanup GLES stuff
            if (mCaptureThread != null) {
                mCaptureThread.stopAsync();
                try {
                    mCaptureThread.join();
                } catch(InterruptedException e) {

                }
                mCaptureThread = null;
            }
            if (encoderThread != null) {
                encoderThread.interrupt();
                encoderThread = null;
            }

            if (mVideoEncoder != null) {
                mVideoEncoder.stop();
                mVideoEncoder.release();
                mVideoEncoder = null;
            }

            if (virtualDisplay != null) {
                virtualDisplay.release();
                virtualDisplay = null;
            }

            if (inputSurface != null) {
                inputSurface.release();
                inputSurface = null;
            }
        } catch (Exception ex) {
            Log.e(TAG, "shutDown() failed");
        }
    }

    /**
     * setupGLES: create offscreen surface and surface texture.
     * @param Width
     * @param Height
     */
    private void setupGLES(int Width, int Height) {
        mEglCore = new EglCore(null, 0);

        // This 1x1 offscreen is created just to get the texture name (mTextureId).
        // (To create a SurfaceTexture, we need a texture name. Texture name can be created by
        // glGenTextures(), but for this method we need to acquire EGLContext. And to acquire
        // EGLContext, we need to call eglMakeCurrent() on SurfaceTexture ... which is not created yet!
        // So here, EGLContext is acquired by calling eglMakeCurrent() on a PBufferSurface which
        // can be created without a texture name. That's why mDummySurface is not used anywhere.)
        mDummySurface = new OffscreenSurface(mEglCore, 1, 1);
        mDummySurface.makeCurrent();

        mFullFrameBlit = new FullFrameRect(new Texture2dProgram(Texture2dProgram.ProgramType.TEXTURE_EXT));
        mTextureId = mFullFrameBlit.createTextureObject();

        mInterSurfaceTexture = new SurfaceTexture(mTextureId);
        mInterSurfaceTexture.setDefaultBufferSize(Width, Height);
        mInterSurface = new Surface(mInterSurfaceTexture);

        // Some devices (e.g. Xperia Z4 with Android 5.0.1) do not allow eglMakeCurrent() called
        // by multiple threads. (An EGLContext should be bound to a single thread.) Since the
        // EGLContext will be accessed by CaptureThread from now on, unbind it from current thread.
        mEglCore.makeNothingCurrent();
    }

    /**
     * CatureThread: utilize OpenGl to capture the rendering thru intermediate surface and surface texture.
     */
    private final class CaptureThread extends Thread implements SurfaceTexture.OnFrameAvailableListener {

        private static final int MSG_TICK = 1;
        private static final int MSG_UPDATE_SURFACE = 2;
        private static final int MSG_TERMINATE = -1;

        private static final long END_MARGIN_NSEC = 1000000;        // 1 msec
        private static final long LONG_SLEEP_THRES_NSEC = 16000000; // 16 msec
        private static final long LONG_SLEEP_MSEC = 10;

        private Handler mHandler;
        private Runnable mStartedCallback;

        private EglCore mEgl;
        private SurfaceTexture mSourceSurfaceTexture;
        private int mSourceTextureId;
        private WindowSurface mDestSurface;
        private FullFrameRect mBlit;
        private int mWidth;
        private int mHeight;

        private long mFrameIntervalNsec;
        private long mStartNsec;
        private long mNextTime;
        private boolean mFirstInput;
        private final float[] mMatrix = new float[16];

        public CaptureThread(EglCore eglCore, SurfaceTexture sourceSurfaceTexture, int sourceTextureId,
                             WindowSurface destSurface, FullFrameRect blit, int width, int height, float fps,
                             Runnable onStarted) {
            mEgl = eglCore;
            mSourceSurfaceTexture = sourceSurfaceTexture;
            mSourceTextureId = sourceTextureId;
            mDestSurface = destSurface;
            mBlit = blit;
            mWidth = width;
            mHeight = height;
            mFrameIntervalNsec = (long)(1000000000 / fps);
            mStartedCallback = onStarted;
        }

        @Override
        public void run() {
            Looper.prepare();

            // create a Handler for this thread
            mHandler = new Handler() {
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case MSG_TICK: {
                            long now = System.nanoTime();
                            if (now > mNextTime - END_MARGIN_NSEC) {
                                drawImage(now);
                                mNextTime += mFrameIntervalNsec;
                            }

                            if (mNextTime - END_MARGIN_NSEC - now > LONG_SLEEP_THRES_NSEC) {
                                mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_TICK), LONG_SLEEP_MSEC);
                            } else {
                                mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_TICK), 1);
                            }
                            break;
                        }
                        // this is for KitKat and below
                        case MSG_UPDATE_SURFACE:
                            updateSurface();
                            break;
                        case MSG_TERMINATE: {
                            removeCallbacksAndMessages(null);
                            Looper looper = Looper.myLooper();
                            if (looper != null) {
                                looper.quit();
                            }
                            break;
                        }
                        default:
                            break;
                    }
                }
            };

            mStartNsec = -1;
            mFirstInput = true;

            if (mStartedCallback != null) {
                mStartedCallback.run();
            }

            Log.d(TAG, "Starting Capture thread");
            Looper.loop();

            Log.d(TAG, "Stopping Capture thread");
            // this is for safe (unbind EGLContext when terminating the thread)
            mEgl.makeNothingCurrent();
        }

        // this may return null before mStartedCallback is called
        public Handler getHandler() {
            return mHandler;
        }

        // make sure this is called after mStartedCallback is called
        public void stopAsync() {
            if (mHandler != null) {
                mHandler.sendMessage(mHandler.obtainMessage(MSG_TERMINATE));
            }
        }

        @Override
        public void onFrameAvailable(SurfaceTexture surfaceTexture) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // With API level 21 and higher, setOnFrameAvailableListener(listener, handler) is used
                // so this method is called on the CaptureThread. We can call updateTexImage() directly.
                updateSurface();
            } else {
                // With API level 20 and lower, setOnFrameAvailableListener(listener) is used, and
                // this method is called on an "arbitrary" thread. (looks like the main thread is
                // used for the most case.) So switch to CaptureThread before calling updateTexImage().
                mHandler.sendMessage(mHandler.obtainMessage(MSG_UPDATE_SURFACE));
            }

            if (mFirstInput) {
                mFirstInput = false;
                mNextTime = System.nanoTime();
                // start the loop
                mHandler.sendMessage(mHandler.obtainMessage(MSG_TICK));
            }
        }

        private void updateSurface() {
            try {
                mDestSurface.makeCurrent();
            } catch (RuntimeException e) {
                Log.e(TAG, "Runtime exception in updateSurface: " + e);
                return;
            }
            // Workaround for the issue Nexus6,5x(6.0 or 6.0.1) stuck.
            // See https://github.com/google/grafika/issues/43
            // As in the comments, the nature of bug is still unclear...
            // But it seems to have the effect to improve.
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
            // get latest image from VirtualDisplay
            mSourceSurfaceTexture.updateTexImage();
            mSourceSurfaceTexture.getTransformMatrix(mMatrix);
        }

        private void drawImage(long currentTime) {
            if (mStartNsec < 0) {
                // first frame
                mStartNsec = currentTime;
            }

            try {
                mDestSurface.makeCurrent();
                // draw from mInterSurfaceTexture to mEncoderSurface
                GLES20.glViewport(0, 0, mWidth, mHeight);
                mBlit.drawFrame(mSourceTextureId, mMatrix);
            } catch (RuntimeException e) {
                Log.e(TAG, "Runtime exception in updateSurface: " + e);
                return;
            }

            // output to encoder
            mDestSurface.setPresentationTime(currentTime - mStartNsec);
            mDestSurface.swapBuffers();
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
                Log.e(TAG, "Unable to start encoder. Android OS version " + Build.VERSION.SDK_INT + "is not supported");
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

    public Display getVirtualDisplay() {
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
                Log.w(TAG, "Error attempting to drain encoder");
            } finally {
                mVideoEncoder.release();
            }
        }

        @SuppressWarnings("deprecation")
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
                if(encoderStatus < 0){
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
                            Log.w(TAG, "Output format change notified more than once, ignoring.");
                        }
                    }
                } else {
                    if ((mVideoBufferInfo.flags & MediaCodec.BUFFER_FLAG_CODEC_CONFIG) != 0) {
                        // If we already retrieve codec specific data via OUTPUT_FORMAT_CHANGED event,
                        // we do not need this data.
                        if (mH264CodecSpecificData != null) {
                            mVideoBufferInfo.size = 0;
                        } else {
                            Log.i(TAG, "H264 codec specific data not retrieved yet.");
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
