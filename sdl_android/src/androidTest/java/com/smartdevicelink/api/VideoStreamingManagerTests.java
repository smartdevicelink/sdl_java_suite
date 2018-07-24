package com.smartdevicelink.api;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.test.AndroidTestCase;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.smartdevicelink.R;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.interfaces.ISdlServiceListener;
import com.smartdevicelink.streaming.video.SdlRemoteDisplay;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static com.smartdevicelink.util.DebugTool.TAG;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

/**
 * This is a unit test class for the SmartDeviceLink video streaming manager class :
 * {@link com.smartdevicelink.api.VideoStreamingManager}
 */
public class VideoStreamingManagerTests extends AndroidTestCase {
	public static final String TAG = "VideoStreamingManagerTests";
	private VideoStreamingManager videoStreamingManager;
	private Context mTestContext;

	// SETUP / HELPERS

	@Override
	public void setUp() throws Exception{
		super.setUp();
		mTestContext = this.getContext();
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	public void testInitialization(){
		ISdl internalInterface = mock(ISdl.class);

		Answer<Void> onAddServiceListener = new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				SessionType sessionType = (SessionType) args[0];
				ISdlServiceListener sdlServiceListener = (ISdlServiceListener) args[1];
				assertEquals(sessionType, SessionType.NAV);
				assertNotNull(sdlServiceListener);
				return null;
			}
		};

		doAnswer(onAddServiceListener).when(internalInterface).addServiceListener(any(SessionType.class), any(ISdlServiceListener.class));

		videoStreamingManager = new VideoStreamingManager(internalInterface);
		videoStreamingManager.start(new CompletionListener() {
			@Override
			public void onComplete(boolean success) {
				assertTrue(success);
			}
		});
	}

	public static class TestPresentation extends SdlRemoteDisplay {
		static View simulatedView = mock(View.class);

		public TestPresentation(Context context, Display display) {
			super(context, display);
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(simulatedView);
		}

		@Override
		public boolean onTouchEvent(@NonNull MotionEvent event) {
			return super.onTouchEvent(event);
		}
	}

	public void testRemoteDisplayStream(){
		ISdl internalInterface = mock(ISdl.class);
	}
}
