package com.smartdevicelink.streaming.video;

import android.annotation.TargetApi;
import android.app.Presentation;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Constructor;
import java.util.concurrent.Callable;

/**
 * Created by Joey Grover on 10/4/17.
 */
@TargetApi(21)
public class SdlRemoteDisplay extends Presentation {
    private static final String TAG = "SdlRemoteDisplay";
    private static final int REFRESH_RATE_MS = 100;

    protected Window w;
    protected View mainView;
    protected Handler handler = new Handler();
    protected Handler uiHandler = new Handler(Looper.getMainLooper());


    public SdlRemoteDisplay(Context context, Display display) {
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

    public View getMainView(){
        return this.mainView;
    }

    public void handleMotionEvent(final MotionEvent motionEvent){
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                mainView.dispatchTouchEvent(motionEvent);
            }
        });
    }

    public void dismissPresentation() {
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        });
    }

    public static class ShowPresentationCallableMethod implements Callable<Boolean> {
        private Context context;
        private Display mDisplay;
        boolean presentationShowError = false;
        SdlRemoteDisplay remoteDisplay;
        Class<? extends SdlRemoteDisplay> remoteDisplayClass;
        protected Handler uiHandler = new Handler(Looper.getMainLooper()); //FIXME


        public ShowPresentationCallableMethod(Context context, Display display, SdlRemoteDisplay remoteDisplay, Class<? extends SdlRemoteDisplay> remoteDisplayClass){
            this.context = context;
            this.mDisplay = display;
            this.remoteDisplay = remoteDisplay;
            this.remoteDisplayClass = remoteDisplayClass;
        }
        @Override
        public Boolean call() {

            uiHandler.post(new Runnable() {
                @Override
                public void run() {
                    // Want to create presentation on UI thread so it finds the right Looper
                    // when setting up the Dialog.
                    if ((remoteDisplay == null) && (mDisplay != null))
                    {
                        Constructor constructor = null;
                        try {
                            constructor = remoteDisplayClass.getConstructor(Context.class, Display.class);
                            remoteDisplay = (SdlRemoteDisplay) constructor.newInstance(context, mDisplay);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e(TAG, "Unable to create Presentation Class");
                            presentationShowError = true;
                            return;
                        }

                        try {
                            remoteDisplay.show();
                        } catch (WindowManager.InvalidDisplayException ex) {
                            Log.e(TAG, "Couldn't show presentation! Display was removed in the meantime.", ex);
                            remoteDisplay = null;
                            presentationShowError = true;
                            return;
                        }
                    }
                }
            });

            return presentationShowError;
        }
    }
}
