package com.smartdevicelink.test.streaming.video;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import androidx.test.platform.app.InstrumentationRegistry;
import android.view.Display;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.smartdevicelink.encoder.VirtualDisplayEncoder;
import com.smartdevicelink.proxy.interfaces.IVideoStreamListener;
import com.smartdevicelink.streaming.video.SdlRemoteDisplay;
import com.smartdevicelink.streaming.video.VideoStreamingParameters;

import junit.framework.TestCase;

import java.nio.ByteBuffer;
import java.util.concurrent.FutureTask;


public class SdlRemoteDisplayTest extends TestCase {

    MockRemoteDisplayCallback rdCallback = new MockRemoteDisplayCallback();

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        if(Looper.myLooper() == null){
            Looper.prepare();
        }
    }

    public void testCreator(){
        VirtualDisplayEncoder encoder = createVDE();
        assertNotNull(encoder);


        SdlRemoteDisplay.Creator creator = new SdlRemoteDisplay.Creator(InstrumentationRegistry.getContext(), encoder.getVirtualDisplay(), null, MockRemoteDisplay.class, rdCallback);
        assertNotNull(creator);
        FutureTask<Boolean> fTask = new FutureTask<Boolean>(creator);
        Thread showPresentation = new Thread(fTask);
        showPresentation.start();
        assert true;
    }

    public void testConstructor(){
        VirtualDisplayEncoder encoder = createVDE();
        assertNotNull(encoder);
        MockRemoteDisplay remoteDisplay = new MockRemoteDisplay(InstrumentationRegistry.getContext(), encoder.getVirtualDisplay());
        assertNotNull(remoteDisplay);

        encoder.shutDown();
    }


    @TargetApi(19)
    public void testTouchEvents(){
        VirtualDisplayEncoder encoder = createVDE();
        assertNotNull(encoder);
        MockRemoteDisplay remoteDisplay = new MockRemoteDisplay(InstrumentationRegistry.getContext(), encoder.getVirtualDisplay());
        assertNotNull(remoteDisplay);
        remoteDisplay.show();

        assertNotNull(remoteDisplay.getMainView());

        try{
            remoteDisplay.handleMotionEvent(MotionEvent.obtain(10, System.currentTimeMillis(), MotionEvent.ACTION_DOWN, 100, 100, 0));
        }catch (Exception e){
            assert false;
        }

        remoteDisplay.dismiss();
        encoder.shutDown();
    }


    public VirtualDisplayEncoder createVDE(){
        try{
            VirtualDisplayEncoder displayEncoder = new VirtualDisplayEncoder();
            displayEncoder.init(InstrumentationRegistry.getContext(), new MockVideoStreamListener(), new VideoStreamingParameters());
            displayEncoder.start();
            return displayEncoder;
        }catch (Exception e ){
            e.printStackTrace();
        }
        return null;
    }


    //Mock classes

    public static class MockRemoteDisplay extends SdlRemoteDisplay{

        public MockRemoteDisplay(Context context, Display display) {
            super(context, display);

        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView( new RelativeLayout(getContext()));

        }


    }

    class MockVideoStreamListener implements IVideoStreamListener{

        @Override
        public void sendFrame(byte[] data, int offset, int length, long presentationTimeUs) throws ArrayIndexOutOfBoundsException {

        }

        @Override
        public void sendFrame(ByteBuffer data, long presentationTimeUs) {

        }
    }

    class MockRemoteDisplayCallback implements SdlRemoteDisplay.Callback{

        @Override
        public void onCreated(SdlRemoteDisplay remoteDisplay) {

        }

        @Override
        public void onInvalidated(SdlRemoteDisplay remoteDisplay) {

        }
    }


}
