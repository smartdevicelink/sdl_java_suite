package com.smartdevicelink.encoder;

import android.annotation.TargetApi;
import android.app.Presentation;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.smartdevicelink.proxy.rpc.OnTouchEvent;
import com.smartdevicelink.proxy.rpc.ScreenParams;
import com.smartdevicelink.proxy.rpc.TouchCoord;
import com.smartdevicelink.proxy.rpc.TouchEvent;
import com.smartdevicelink.proxy.rpc.enums.TouchType;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

@TargetApi(21)
public class VirtualDisplayEncoder {
    private static final String TAG = "VirtualDisplayEncoder";
    private final String videoMimeType = "video/avc";
    private StreamingParameters streamingParams = new StreamingParameters();
    private DisplayManager mDisplayManager;
    private volatile MediaCodec mVideoEncoder = null;
    private volatile MediaCodec.BufferInfo mVideoBufferInfo = null;
    private volatile Surface inputSurface = null;
    private volatile VirtualDisplay virtualDisplay = null;
    private volatile SdlPresentation presentation = null;
    private Class<? extends SdlPresentation> presentationClass = null;
    private VideoStreamWriterThread streamWriterThread = null;
    private Context mContext;
    private OutputStream sdlOutStream = null;
    private Boolean initPassed = false;
    private Handler uiHandler = new Handler(Looper.getMainLooper());
    private final static int REFRESH_RATE_MS = 100;
    private final static Object CLOSE_VID_SESSION_LOCK = new Object();
    private final static Object START_DISP_LOCK = new Object();
    private final static Object STREAMING_LOCK = new Object();

    public class StreamingParameters {
        protected int displayDensity = DisplayMetrics.DENSITY_HIGH;
        protected int videoWidth = 800;
        protected int videoHeight = 480;
        protected int frameRate = 24;
        protected int bitrate = 512000;
        protected int interval = 5;

        public StreamingParameters(){
            // empty
        }

        public StreamingParameters(int displayDensity, int videoWidth, int videoHeight, int frameRate, int bitrate, int interval) {
            this.displayDensity = displayDensity;
            this.videoWidth = videoWidth;
            this.videoHeight = videoHeight;
            this.frameRate = frameRate;
            this.bitrate = bitrate;
            this.interval = interval;
        }

        /**
         * Set displayDensity to a value from DisplayMetrics
         * @param displayDensity
         */
        public void setDisplayDensity(int displayDensity) {
            this.displayDensity = displayDensity;
        }

        public int getDisplayDensity() {
            return displayDensity;
        }

        public void setVideoWidth(int videoWidth) {
            this.videoWidth = videoWidth;
        }

        public int getVideoWidth() {
            return videoWidth;
        }

        public void setVideoHeight(int videoHeight) {
            this.videoHeight = videoHeight;
        }

        public int getVideoHeight() {
            return videoHeight;
        }

        public void setFrameRate(int frameRate) {
            this.frameRate = frameRate;
        }

        public int getFrameRate() {
            return frameRate;
        }

        public void setBitrate(int bitrate) {
            this.bitrate = bitrate;
        }

        public int getBitrate() {
            return bitrate;
        }

        public void setInterval(int interval) {
            this.interval = interval;
        }

        public int getInterval() {
            return interval;
        }
    }

    /**
     * Initialization method for VirtualDisplayEncoder object. MUST be called before start() or shutdown()
     * Will overwrite previously set videoWeight and videoHeight
     * @param context
     * @param videoStream
     * @param presentationClass
     * @param screenParams
     * @throws Exception
     */
    public void init(Context context, OutputStream videoStream, Class<? extends SdlPresentation> presentationClass, ScreenParams screenParams) throws Exception {
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Log.e(TAG, "API level of 21 required for VirtualDisplayEncoder");
            throw new Exception("API level of 21 required");
        }

        mDisplayManager = (DisplayManager)context.getSystemService(Context.DISPLAY_SERVICE);

        mContext = context;

        if(screenParams.getImageResolution().getResolutionHeight() != null){
            streamingParams.videoHeight = screenParams.getImageResolution().getResolutionHeight();
        }

        if(screenParams.getImageResolution().getResolutionWidth() != null){
            streamingParams.videoWidth = screenParams.getImageResolution().getResolutionWidth();
        }

        sdlOutStream = videoStream;

        this.presentationClass = presentationClass;

        setupVideoStreamWriter();

        initPassed = true;
    }

    public StreamingParameters getStreamingParams(){
        return this.streamingParams;
    }

    public void setStreamingParams(int displayDensity, int videoWidth, int videoHeight, int frameRate, int bitrate, int interval){
        this.streamingParams = new StreamingParameters(displayDensity, videoWidth, videoHeight, frameRate, bitrate, interval);
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

        synchronized (STREAMING_LOCK) {

            try {
                inputSurface = prepareVideoEncoder();

                // Create a virtual display that will output to our encoder.
                virtualDisplay = mDisplayManager.createVirtualDisplay(TAG,
                        streamingParams.videoWidth, streamingParams.videoHeight, streamingParams.displayDensity, inputSurface, DisplayManager.VIRTUAL_DISPLAY_FLAG_PRESENTATION);

                startEncoder();

                displayPresentation();
            } catch (Exception ex) {
                Log.e(TAG, "Unable to create Virtual Display.");
                throw new RuntimeException(ex);
            }
        }
    }

    public void shutDown()
    {
        if(!initPassed){
            Log.e(TAG, "VirtualDisplayEncoder was not properly initialized with the init() method.");
            return;
        }
        try {

            closeVideoSession();
            releaseVideoStreamWriter();

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

    private void closeVideoSession() {

        synchronized (CLOSE_VID_SESSION_LOCK) {
            if (sdlOutStream != null) {

                try {
                    sdlOutStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                sdlOutStream = null;

                if (streamWriterThread != null) {
                    streamWriterThread.clearOutputStream();
                    streamWriterThread.clearByteBuffer();
                }
            }
        }
    }

    private Surface prepareVideoEncoder() {

        if (mVideoBufferInfo == null) {
            mVideoBufferInfo = new MediaCodec.BufferInfo();
        }

        MediaFormat format = MediaFormat.createVideoFormat(videoMimeType, streamingParams.videoWidth, streamingParams.videoHeight);

        // Set some required properties. The media codec may fail if these aren't defined.
        format.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface);
        format.setInteger(MediaFormat.KEY_BIT_RATE, streamingParams.bitrate);
        format.setInteger(MediaFormat.KEY_FRAME_RATE, streamingParams.frameRate);
        format.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, streamingParams.interval); // seconds between I-frames

        // Create a MediaCodec encoder and configure it. Get a Surface we can use for recording into.
        try {
            mVideoEncoder = MediaCodec.createEncoderByType(videoMimeType);
            mVideoEncoder.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
            Surface surf = mVideoEncoder.createInputSurface();

            mVideoEncoder.setCallback(new MediaCodec.Callback() {
                @Override
                public void onInputBufferAvailable(MediaCodec codec, int index) {
                    // nothing to do here
                }

                @Override
                public void onOutputBufferAvailable(MediaCodec codec, int index, MediaCodec.BufferInfo info) {
                    try {
                        ByteBuffer encodedData = codec.getOutputBuffer(index);

                        encodedData.position(info.offset);
                        encodedData.limit(info.offset + info.size);

                        if (info.size != 0) {
                            byte[] dataToWrite = new byte[info.size];
                            encodedData.get(dataToWrite,
                                    info.offset, info.size);

                            onStreamDataAvailable(dataToWrite, info.size);
                        }

                        codec.releaseOutputBuffer(index, false);
                    } catch (IllegalStateException ex) {
                        ex.printStackTrace();
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

            return surf;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Forward an OnTouchEvent object to the current presentation
     * @param touchEvent
     */
    public void handleTouchEvent(OnTouchEvent touchEvent)
    {
        final MotionEvent motionEvent = convertTouchEvent(touchEvent);
        if (motionEvent != null && presentation.mainView != null) {

            uiHandler.post(new Runnable() {
                @Override
                public void run() {
                    presentation.mainView.dispatchTouchEvent(motionEvent);
                }
            });
        }
    }

    private MotionEvent convertTouchEvent(OnTouchEvent touchEvent){
        List<TouchEvent> eventList = touchEvent.getEvent();
        if (eventList == null || eventList.size() == 0) return null;

        TouchType touchType = touchEvent.getType();
        if (touchType == null){ return null;}

        float x;
        float y;

        TouchEvent event = eventList.get(eventList.size() - 1);
        List<TouchCoord> coordList = event.getTouchCoordinates();
        if (coordList == null || coordList.size() == 0){ return null;}

        TouchCoord coord = coordList.get(coordList.size() - 1);
        if (coord == null){ return null;}

        x = coord.getX();
        y = coord.getY();

        if (x == 0 && y == 0){ return null;}

        int eventAction = MotionEvent.ACTION_DOWN;
        long downTime = 0;

        if (touchType == TouchType.BEGIN) {
            downTime = SystemClock.uptimeMillis();
            eventAction = MotionEvent.ACTION_DOWN;
        }

        long eventTime = SystemClock.uptimeMillis();
        if (downTime == 0){ downTime = eventTime - 100;}

        if (touchType == TouchType.MOVE) {
            eventAction = MotionEvent.ACTION_MOVE;
        }

        if (touchType == TouchType.END) {
            eventAction = MotionEvent.ACTION_UP;
        }

        return MotionEvent.obtain(downTime, eventTime, eventAction, x, y, 0);
    }

    private void onStreamDataAvailable(byte[] data, int size) {
        if (sdlOutStream != null) {
            try {
                if (streamWriterThread.getOutputStream() == null) {
                    streamWriterThread.setOutputStream(sdlOutStream);
                }

                streamWriterThread.setByteBuffer(data, size);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "sdlOutStream is null");
        }
    }

    private void startEncoder()
    {
        if (mVideoEncoder != null) {
            mVideoEncoder.start();
        }
    }

    public static class SdlPresentation extends Presentation{
        protected Window w;
        protected View mainView;
        protected Handler handler = new Handler();

        public SdlPresentation(Context context, Display display) {
            super(context, display);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setTitle("Presentation");

            w  = getWindow();

            startRefreshTask();

            w.setType(WindowManager.LayoutParams.TYPE_PRIVATE_PRESENTATION);
        }

        protected void startRefreshTask() {
            handler.postDelayed(mStartRefreshTaskCallback, REFRESH_RATE_MS);
        }

        protected void stopRefreshTask() {
            handler.removeCallbacks(mStartRefreshTaskCallback);
        }

        protected Runnable mStartRefreshTaskCallback = new Runnable() {
            public void run() {
                mainView = w.getDecorView().findViewById(android.R.id.content);
                if (mainView != null) {
                    mainView.invalidate();
                }

                handler.postDelayed(this, REFRESH_RATE_MS);
            }
        };
    }

    private class ShowPresentationCallableMethod implements Callable<Boolean> {
        private Display mDisplay;
        boolean bPresentationShowError = false;

        public ShowPresentationCallableMethod(Display display){
            mDisplay = display;
        }
        @Override
        public Boolean call() {

            uiHandler.post(new Runnable() {
                @Override
                public void run() {
                    // Want to create presentation on UI thread so it finds the right Looper
                    // when setting up the Dialog.
                    if ((presentation == null) && (mDisplay != null))
                    {
                        Constructor constructor = null;
                        try {
                            constructor = presentationClass.getConstructor(Context.class, Display.class);
                            presentation = (SdlPresentation) constructor.newInstance(mContext, mDisplay);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e(TAG, "Unable to create Presentation Class");
                            bPresentationShowError = true;
                            return;
                        }

                        try {
                            presentation.show();
                        } catch (WindowManager.InvalidDisplayException ex) {
                            Log.e(TAG, "Couldn't show presentation! Display was removed in the meantime.", ex);
                            presentation = null;
                            bPresentationShowError = true;
                            return;
                        }
                    }
                }
            });

            return bPresentationShowError;
        }
    }

    private void displayPresentation() {

        synchronized (START_DISP_LOCK) {
            try {
                final Display disp = virtualDisplay.getDisplay();

                if (disp == null){
                    Log.i(TAG, "Display is null");
                    return;
                }

                // Dismiss the current presentation if the display has changed.
                if (presentation != null && presentation.getDisplay() != disp) {
                    Log.i(TAG, "Dismissing current presentation display.");
                    dismissPresentation();
                }

                FutureTask<Boolean> fTask =  new FutureTask<Boolean>(new ShowPresentationCallableMethod(disp));
                Thread showPresentation = new Thread(fTask);

                showPresentation.start();
                Log.i(TAG, "displayPresentation");
            } catch (Exception ex) {
                Log.w(TAG, "Unable to create Virtual Display.");
            }
        }
    }

    private void dismissPresentation() {
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                if (presentation != null) {
                    presentation.dismiss();
                    Log.i(TAG, "Dismiss Presentation.");
                    presentation = null;
                }
            }
        });
    }

    private void setupVideoStreamWriter() {
        if (streamWriterThread == null) {
            // Setup VideoStreamWriterThread thread
            streamWriterThread = new VideoStreamWriterThread();
            streamWriterThread.setName("VideoStreamWriter");
            streamWriterThread.setPriority(Thread.MAX_PRIORITY);
            streamWriterThread.setDaemon(true);
            streamWriterThread.start();
        }
    }

    private void releaseVideoStreamWriter() {
        if (streamWriterThread != null) {

            streamWriterThread.halt();

            try {
                streamWriterThread.interrupt();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            streamWriterThread.clearOutputStream();
            streamWriterThread.clearByteBuffer();
        }
        streamWriterThread = null;
    }

    private class VideoStreamWriterThread extends Thread {
        private Boolean isHalted = false;
        private byte[] buf = null;
        private Integer size = 0;
        private OutputStream os = null;

        public OutputStream getOutputStream() {
            return os;
        }

        public byte[] getByteBuffer() {
            return buf;
        }

        public void setOutputStream(OutputStream os) {
            synchronized (STREAMING_LOCK) {
                clearOutputStream();
                this.os = os;
            }
        }

        public void setByteBuffer(byte[] buf, Integer size) {
            synchronized (STREAMING_LOCK) {
                clearByteBuffer();
                this.buf = buf;
                this.size = size;
            }
        }

        private void clearOutputStream() {
            synchronized (STREAMING_LOCK) {
                try {
                    if (os != null) {
                        os.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                os = null;
            }
        }

        private void clearByteBuffer() {
            synchronized (STREAMING_LOCK) {
                try {
                    if (buf != null) {
                        buf = null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private void writeToStream() {
            synchronized (STREAMING_LOCK) {
                if (buf == null || os == null)
                    return;

                try {
                    os.write(buf, 0, size);
                    clearByteBuffer();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        public void run() {
            while (!isHalted) {
                writeToStream();
            }
        }

        /**
         * Method that marks thread as halted.
         */
        public void halt() {
            isHalted = true;
        }
    }

}