package com.smartdevicelink.encoder;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.Surface;

import com.smartdevicelink.proxy.interfaces.IVideoStreamListener;
import com.smartdevicelink.proxy.rpc.ImageResolution;
import com.smartdevicelink.proxy.rpc.VideoStreamingFormat;
import com.smartdevicelink.proxy.rpc.enums.VideoStreamingCodec;
import com.smartdevicelink.streaming.video.VideoStreamingParameters;

import java.nio.ByteBuffer;

import static com.smartdevicelink.proxy.constants.Names.screenParams;

@TargetApi(21)
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



    /**
     * Initialization method for VirtualDisplayEncoder object. MUST be called before start() or shutdown()
     * Will overwrite previously set videoWeight and videoHeight
     * @param context to create the virtual display
     * @param outputListener the listener that the video frames will be sent through
     * @param streamingParams parameters to create the virtual display and encoder
     * @throws Exception if the API level is <21 or supplied parameters were null
     */
    public void init(Context context, IVideoStreamListener outputListener, VideoStreamingParameters streamingParams) throws Exception {
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Log.e(TAG, "API level of 21 required for VirtualDisplayEncoder");
            throw new Exception("API level of 21 required");
        }

        if (context == null || outputListener == null || streamingParams == null || streamingParams.getResolution() == null || streamingParams.getFormat() == null) {
            Log.e(TAG, "init parameters cannot be null for VirtualDisplayEncoder");
            throw new Exception("init parameters cannot be null");
        }

        this.mDisplayManager = (DisplayManager)context.getSystemService(Context.DISPLAY_SERVICE);

        this.streamingParams.update(streamingParams);

        mOutputListener = outputListener;

        initPassed = true;
    }

    @SuppressWarnings("unused")
    public VideoStreamingParameters getStreamingParams(){
        return this.streamingParams;
    }

    @SuppressWarnings("unused")
    public void setStreamingParams(int displayDensity, ImageResolution resolution, int frameRate, int bitrate, int interval, VideoStreamingFormat format){
        this.streamingParams = new VideoStreamingParameters(displayDensity,frameRate,bitrate,interval,resolution,format);
    }

    @SuppressWarnings("unused")
    public void setStreamingParams(VideoStreamingParameters streamingParams){
        this.streamingParams = streamingParams;
    }

    /**
     * NOTE: Must call init() without error before calling this method.
     * Prepares the encoder and virtual display.
     */
    public void start() throws Exception {
        if(!initPassed){
            Log.e(TAG, "VirtualDisplayEncoder was not properly initialized with the init() method.");
            return;
        }
        if(streamingParams == null || streamingParams.getResolution() == null || streamingParams.getFormat() == null){
            return;
        }

        synchronized (STREAMING_LOCK) {

            try {
                inputSurface = prepareVideoEncoder();

                // Create a virtual display that will output to our encoder.
                virtualDisplay = mDisplayManager.createVirtualDisplay(TAG,
                        streamingParams.getResolution().getResolutionWidth(), streamingParams.getResolution().getResolutionHeight(),
                        streamingParams.getDisplayDensity(), inputSurface, DisplayManager.VIRTUAL_DISPLAY_FLAG_PRESENTATION);

                startEncoder();

            } catch (Exception ex) {
                Log.e(TAG, "Unable to create Virtual Display.");
                throw new RuntimeException(ex);
            }
        }
    }

    public void shutDown() {
        if(!initPassed){
            Log.e(TAG, "VirtualDisplayEncoder was not properly initialized with the init() method.");
            return;
        }
        try {

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
        }
        catch (Exception ex){
            Log.e(TAG, "shutDown() failed");
        }
    }

    private Surface prepareVideoEncoder() {

        if(streamingParams == null || streamingParams.getResolution() == null || streamingParams.getFormat() == null){
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
            Surface surface = mVideoEncoder.createInputSurface();

            mVideoEncoder.setCallback(new MediaCodec.Callback() {
                @Override
                public void onInputBufferAvailable(MediaCodec codec, int index) {
                    // nothing to do here
                }

                @Override
                public void onOutputBufferAvailable(MediaCodec codec, int index, MediaCodec.BufferInfo info) {
                    try {
                        ByteBuffer encodedData = codec.getOutputBuffer(index);
                        if(encodedData!=null) {

                            encodedData.position(info.offset);
                            encodedData.limit(info.offset + info.size);

                            if (info.size != 0) {
                                byte[] dataToWrite = new byte[info.size];
                                encodedData.get(dataToWrite,
                                        info.offset, info.size);
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
                    // nothing to do here
                }
            });

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
    }

    public Display getVirtualDisplay(){
        return virtualDisplay.getDisplay();
    }

    private String getMimeForFormat(VideoStreamingFormat format){
        if(format != null){
            VideoStreamingCodec codec = format.getCodec();
            if(codec != null){
                switch(codec){
                    case VP8:
                        return MediaFormat.MIMETYPE_VIDEO_VP8;
                    case VP9:
                        return MediaFormat.MIMETYPE_VIDEO_VP9;
                    case H264: //Fall through
                    default:
                        return MediaFormat.MIMETYPE_VIDEO_AVC;
                }
            }
        }
        return null;
    }

}