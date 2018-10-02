package com.smartdevicelink.managers.video;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.test.AndroidTestCase;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.interfaces.ISdlServiceListener;
import com.smartdevicelink.proxy.interfaces.IVideoStreamListener;
import com.smartdevicelink.proxy.interfaces.OnSystemCapabilityListener;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.OnTouchEvent;
import com.smartdevicelink.proxy.rpc.TouchEvent;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;
import com.smartdevicelink.proxy.rpc.enums.TouchType;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.streaming.video.SdlRemoteDisplay;
import com.smartdevicelink.streaming.video.VideoStreamingParameters;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.util.Version;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This is a unit test class for the SmartDeviceLink video streaming manager class :
 * {@link VideoStreamManager}
 */
public class VideoStreamManagerTests extends AndroidTestCase {
	public static final String TAG = "VideoStreamManagerTests";
	private Context mTestContext;
	private static boolean touchEventOccured = false;

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

	// TEST CLASSES

	public static class TestPresentation extends SdlRemoteDisplay {
		View simulatedView = new View(this.getContext());

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
			touchEventOccured = true;
			return super.onTouchEvent(event);
		}
	}

	// TESTS

	public void testInitialization(){
		ISdl internalInterface = mock(ISdl.class);
		when(internalInterface.getProtocolVersion()).thenReturn(new Version(5,1,0));

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

		VideoStreamManager videoStreamManager = new VideoStreamManager(internalInterface);
		videoStreamManager.start(new CompletionListener() {
			@Override
			public void onComplete(boolean success) {
				assertTrue(success);
			}
		});
	}

	public void testHMILevelNotFull(){
		ISdl internalInterface = mock(ISdl.class);

		when(internalInterface.getProtocolVersion()).thenReturn((new Version(5,0,0)));
		when(internalInterface.isCapabilitySupported(SystemCapabilityType.VIDEO_STREAMING)).thenReturn(true);

		final VideoStreamManager videoStreamManager = new VideoStreamManager(internalInterface);
		videoStreamManager.start(new CompletionListener() {
			@Override
			public void onComplete(boolean success) {
				assertNull(videoStreamManager.startVideoService(
						new VideoStreamingParameters(), false));
			}
		});
	}

	public void testRemoteDisplayStream(){
		ISdl internalInterface = mock(ISdl.class);

		final Set<Object> listenerSet = new HashSet<>();

		when(internalInterface.getProtocolVersion()).thenReturn(new Version(5,0,0));
		when(internalInterface.isCapabilitySupported(SystemCapabilityType.VIDEO_STREAMING)).thenReturn(true);

		Answer<Void> onGetCapability = new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				OnSystemCapabilityListener systemCapabilityListener = (OnSystemCapabilityListener) args[1];
				systemCapabilityListener.onCapabilityRetrieved(Test.GENERAL_VIDEOSTREAMINGCAPABILITY);
				return null;
			}
		};

		doAnswer(onGetCapability).when(internalInterface).getCapability(eq(SystemCapabilityType.VIDEO_STREAMING), any(OnSystemCapabilityListener.class));

		Answer<Void> onAddServiceListener = new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				listenerSet.add(args[1]);
				return null;
			}
		};

		doAnswer(onAddServiceListener).when(internalInterface).addServiceListener(eq(SessionType.NAV), any(ISdlServiceListener.class));

		final OnRPCNotificationListener[] hmiListener = {null};

		Answer<Void> onAddHMIListener = new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				hmiListener[0] = (OnRPCNotificationListener) args[1];
				listenerSet.add(args[1]);
				return null;
			}
		};

		doAnswer(onAddHMIListener).when(internalInterface).addOnRPCNotificationListener(eq(FunctionID.ON_HMI_STATUS), any(OnRPCNotificationListener.class));

		Answer<Void> onAddTouchListener = new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				listenerSet.add(args[1]);
				return null;
			}
		};

		doAnswer(onAddTouchListener).when(internalInterface).addOnRPCNotificationListener(eq(FunctionID.ON_TOUCH_EVENT), any(OnRPCNotificationListener.class));

		Answer<Void> onRemoveRPCNotificationListener = new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				listenerSet.remove(args[1]);
				return null;
			}
		};

		doAnswer(onRemoveRPCNotificationListener).when(internalInterface).removeOnRPCNotificationListener(eq(FunctionID.ON_HMI_STATUS), any(OnRPCNotificationListener.class));
		doAnswer(onRemoveRPCNotificationListener).when(internalInterface).removeOnRPCNotificationListener(eq(FunctionID.ON_TOUCH_EVENT), any(OnRPCNotificationListener.class));

		Answer<Void> onRemoveServiceListener = new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				listenerSet.remove(args[1]);
				return null;
			}
		};

		doAnswer(onRemoveServiceListener).when(internalInterface).removeServiceListener(eq(SessionType.NAV), any(ISdlServiceListener.class));

		when(internalInterface.startVideoStream(anyBoolean(), any(VideoStreamingParameters.class))).thenReturn(new IVideoStreamListener() {
			@Override
			public void sendFrame(byte[] data, int offset, int length, long presentationTimeUs) throws ArrayIndexOutOfBoundsException {}
			@Override
			public void sendFrame(ByteBuffer data, long presentationTimeUs) {}
		});

		when(internalInterface.getCapability(SystemCapabilityType.VIDEO_STREAMING)).thenReturn(Test.GENERAL_VIDEOSTREAMINGCAPABILITY);

		final VideoStreamManager videoStreamManager = new VideoStreamManager(internalInterface);
		videoStreamManager.start(new CompletionListener() {
			@Override
			public void onComplete(boolean success) {
				assertTrue(success);
				assertTrue(listenerSet.size() == 3);

				OnHMIStatus fullNotification = new OnHMIStatus();
				fullNotification.setHmiLevel(HMILevel.HMI_FULL);
				hmiListener[0].onNotified(fullNotification);

				videoStreamManager.startRemoteDisplayStream(mTestContext, TestPresentation.class, null, false);

				//assertTrue(touchEventOccured);

				videoStreamManager.dispose();
				assertTrue(listenerSet.isEmpty());
			}
		});

	}

	public void testConvertTouchEvent(){
		ISdl internalInterface = mock(ISdl.class);
		when(internalInterface.getProtocolVersion()).thenReturn(new Version(5,1,0));

		final VideoStreamManager videoStreamManager = new VideoStreamManager(internalInterface);
		videoStreamManager.start(new CompletionListener() {
			@Override
			public void onComplete(boolean success) {
				assertTrue(success);
				OnTouchEvent testOnTouchEvent = new OnTouchEvent();
				TouchEvent touchEvent = Test.GENERAL_TOUCHEVENT;
				testOnTouchEvent.setEvent(Collections.singletonList(touchEvent));
				testOnTouchEvent.setType(Test.GENERAL_TOUCHTYPE);
				MotionEvent motionEvent;

				// Touch one pointer (100)
				motionEvent = videoStreamManager.convertTouchEvent(testOnTouchEvent);
				assertEquals(motionEvent.getAction(), MotionEvent.ACTION_DOWN);

				// Touch another pointer (101) without release
				touchEvent.setId(Test.GENERAL_INT + 1);
				testOnTouchEvent.setEvent(Collections.singletonList(touchEvent));
				motionEvent = videoStreamManager.convertTouchEvent(testOnTouchEvent);
				assertEquals(motionEvent.getAction(), MotionEvent.ACTION_POINTER_DOWN);

				// Release one of the pointers (101)
				testOnTouchEvent.setType(TouchType.END);
				motionEvent = videoStreamManager.convertTouchEvent(testOnTouchEvent);
				assertEquals(motionEvent.getAction(), MotionEvent.ACTION_POINTER_UP);

				// Release the other pointer (100)
				touchEvent.setId(Test.GENERAL_INT);
				testOnTouchEvent.setEvent(Collections.singletonList(touchEvent));
				motionEvent = videoStreamManager.convertTouchEvent(testOnTouchEvent);
				assertEquals(motionEvent.getAction(), MotionEvent.ACTION_UP);
			}
		});
	}
}
