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
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.TouchType;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;


public class VirtualDisplayEncoder {
    private static final String TAG = "VirtualDisplayEncoder";
    private static final int DENSITY = DisplayMetrics.DENSITY_HIGH;
    private static final String VIDEO_MIME_TYPE = "video/avc";
    private static int refresh_rate_ms = 100;
    private static int video_width = 800;
    private static int video_height = 480;
    private static int frame_rate = 24;
    private static int bitrate = 500 * 1024;
    private static int interval = 5;
    private DisplayManager mDisplayManager;
    private volatile MediaCodec mVideoEncoder = null;
    private volatile MediaCodec.BufferInfo mVideoBufferInfo = null;
    volatile Surface inputSurface = null;
    volatile VirtualDisplay virtualDisplay = null;
    private volatile SdlPresentation presentation = null;
    private Class<? extends SdlPresentation> presentationClass = null;
    private VideoStreamWriterThread streamWriterThread = null;
    private Context mContext;
    private OutputStream sdlOutStream = null;
    private HMILevel hmiLevel = HMILevel.HMI_NONE;

    public void init(Context context, OutputStream videoStream, Class<? extends SdlPresentation> presentationClass, ScreenParams screenParams) throws Exception {
        if (android.os.Build.VERSION.SDK_INT < 21) {
            Log.e(TAG, "API level of 21 required for VirtualDisplayEncoder");
            return;
        }

        mDisplayManager = (DisplayManager)context.getSystemService(Context.DISPLAY_SERVICE);

        mContext = context;

        if(screenParams.getImageResolution().getResolutionHeight() != null){
            video_height = screenParams.getImageResolution().getResolutionHeight();
        }

        if(screenParams.getImageResolution().getResolutionWidth() != null){
            video_width = screenParams.getImageResolution().getResolutionWidth();
        }

        sdlOutStream = videoStream;

        this.presentationClass = presentationClass;

        setupVideoStreamWriter();
    }

    public void setStreamParams(int frameRate, int bitrate, int interval, int refresh_rate){
        this.frame_rate = frameRate;
        this.bitrate = bitrate;
        this.interval = interval;
        this.refresh_rate_ms = refresh_rate;
    }

    private final static Object lock = new Object();
    /**
     * Prepares the encoder and virtual display.
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void start() throws Exception {
        synchronized (lock) {

            try {
                inputSurface = prepareVideoEncoder();

                // Create a virtual display that will output to our encoder.
                virtualDisplay = mDisplayManager.createVirtualDisplay(TAG,
                        video_width, video_height, DENSITY, inputSurface, DisplayManager.VIRTUAL_DISPLAY_FLAG_PRESENTATION);
                Log.d(TAG, "Created virtualDisplay.");

                startEncoder();
                Log.d(TAG, "Start encoder.");

                displayPresentation();
                Log.d(TAG, "Displaying presentation.");

            } catch (Exception ex) {
                Log.w(TAG, "Unable to create Virtual Display.");
                throw new RuntimeException(ex);
            } finally {}
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void shutDown()
    {
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

    private final static Object closeVideoSessionLock = new Object();

    private void closeVideoSession() {

        synchronized (closeVideoSessionLock) {
            if (sdlOutStream != null) {

                try {
                    Log.d(TAG, "Closing sdlOutStream.");
                    sdlOutStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                sdlOutStream = null;

                if (streamWriterThread != null) {
                    Log.d(TAG, "Clearing output stream.");
                    streamWriterThread.clearOutputStream();

                    Log.d(TAG, "Clearing byte buffer.");
                    streamWriterThread.clearByteBuffer();
                }
            }
        }
    }

    @TargetApi(21)
    private Surface prepareVideoEncoder() {
        Log.d(TAG, "prepareVideoEncoder");

        if (mVideoBufferInfo == null)
            mVideoBufferInfo = new MediaCodec.BufferInfo();

        MediaFormat format = MediaFormat.createVideoFormat(VIDEO_MIME_TYPE, video_width, video_height);

        // Set some required properties. The media codec may fail if these aren't defined.
        format.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface);
        format.setInteger(MediaFormat.KEY_BIT_RATE, bitrate);
        format.setInteger(MediaFormat.KEY_FRAME_RATE, frame_rate);
        format.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, interval); // seconds between I-frames

        // Create a MediaCodec encoder and configure it. Get a Surface we can use for recording into.
        try {
            mVideoEncoder = MediaCodec.createEncoderByType(VIDEO_MIME_TYPE);
            mVideoEncoder.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
            Surface surf = mVideoEncoder.createInputSurface();
            Log.d(TAG, "Input surface Created");

            mVideoEncoder.setCallback(new MediaCodec.Callback() {
                @Override
                public void onInputBufferAvailable(MediaCodec codec, int index) {

                }

                @Override
                public void onOutputBufferAvailable(MediaCodec codec, int index, MediaCodec.BufferInfo info) {
                    try {
                        Log.e(TAG, "onOutputBufferAvailable");
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

                }
            });

            return surf;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    Handler UI_handler = new Handler(Looper.getMainLooper());

    /* handle TouchEvent */
    public void handleTouchEvent(OnTouchEvent touchEvent)
    {
        final MotionEvent motionEvent = convertTouchEvent(touchEvent);
        if (motionEvent != null && presentation.mainView != null) {
            Log.d(TAG, "Sending touch event!");

            UI_handler.post(new Runnable() {
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
        if (touchType == null) return null;

        float x;
        float y;

        TouchEvent event = eventList.get(eventList.size() - 1);
        List<TouchCoord> coordList = event.getTouchCoordinates();
        if (coordList == null || coordList.size() == 0) return null;

        TouchCoord coord = coordList.get(coordList.size() - 1);
        if (coord == null) return null;

        x = coord.getX();
        y = coord.getY();

        if (x == 0 && y == 0) return null;

        int eventAction = MotionEvent.ACTION_DOWN;
        long downTime = 0;

        if (touchType == TouchType.BEGIN) {
            downTime = SystemClock.uptimeMillis();
            eventAction = MotionEvent.ACTION_DOWN;
        }

        long eventTime = SystemClock.uptimeMillis();
        if (downTime == 0) downTime = eventTime - 100;

        if (touchType == TouchType.MOVE)
            eventAction = MotionEvent.ACTION_MOVE;

        if (touchType == TouchType.END)
            eventAction = MotionEvent.ACTION_UP;

        return MotionEvent.obtain(downTime, eventTime, eventAction, x, y, 0);
    }

    public void updateHmiLevel(HMILevel hmi){
        hmiLevel = hmi;
    }

    private void onStreamDataAvailable(byte[] data, int size) {
        Log.d(TAG, "onStreamDataAvailable");
        if (sdlOutStream != null) {
            try {
                if (hmiLevel != HMILevel.HMI_NONE) {
                    if (streamWriterThread.getOutputStream() == null) {
                        streamWriterThread.setOutputStream(sdlOutStream);
                        Log.d(TAG, "Setting output stream.");
                    }

                    Log.d(TAG, "Transmitting data.");
                    streamWriterThread.setByteBuffer(data, size);
                } else
                    Log.d(TAG, "Cannot stream in HMI_NONE state.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "sdlOutStream is null");
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void startEncoder()
    {
        if (mVideoEncoder != null) {
            mVideoEncoder.start();
            Log.d(TAG, "Starting video encoder.");
        }
    }

    private final static Object startDisplayingLock = new Object();

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static class SdlPresentation extends Presentation{
        Window w;
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

            Log.d(TAG, "Sdl Presentation created.");
        }

        protected void startRefreshTask() {
            handler.postDelayed(mStartRefreshTaskCallback, refresh_rate_ms);
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

                handler.postDelayed(this, refresh_rate_ms);
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
            try {
                UI_handler.post(new Runnable() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void run() {
                        // Want to create presentation on UI thread so it finds the right Looper
                        // when setting up the Dialog.
                        if ((presentation == null) && (mDisplay != null))
                        {
                            Constructor constructor = null;
                            try {
                                constructor = presentationClass.getConstructor(Context.class, Display.class);
                            } catch (NoSuchMethodException e) {
                                e.printStackTrace();
                                Log.e(TAG, "Unable to create Presentation Class");
                            }
                            try {
                                presentation = (SdlPresentation) constructor.newInstance(mContext, mDisplay);
                            } catch (InstantiationException e) {
                                e.printStackTrace();
                                Log.e(TAG, "Unable to create Presentation Class");
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                                Log.e(TAG, "Unable to create Presentation Class");
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                                Log.e(TAG, "Unable to create Presentation Class");
                            }

                            try {
                                Log.i(TAG, "Show presentation");
                                presentation.show();
                            } catch (WindowManager.InvalidDisplayException ex) {
                                Log.w(TAG, "Couldn't show presentation! Shouldn't have happened. Display was removed in "
                                        + "the meantime.", ex);
                                presentation = null;
                                bPresentationShowError = true;
                            }
                        }
                    }
                });

            } catch (Exception ex) {
                Log.w(TAG, "Couldn't show presentation");
                ex.printStackTrace();
                presentation = null;
                bPresentationShowError = true;
            }

            return bPresentationShowError;
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void displayPresentation() {

        synchronized (startDisplayingLock) {
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
        UI_handler.post(new Runnable() {
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
            Log.d(TAG, "setOutputStream");
            synchronized (lock) {
                clearOutputStream();
                this.os = os;
            }
        }

        public void setByteBuffer(byte[] buf, Integer size) {
            Log.d(TAG, "setByteBuffer");
            synchronized (lock) {
                clearByteBuffer();
                this.buf = buf;
                this.size = size;
            }
        }

        private void clearOutputStream() {
            synchronized (lock) {
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
            synchronized (lock) {
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
            synchronized (lock) {
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