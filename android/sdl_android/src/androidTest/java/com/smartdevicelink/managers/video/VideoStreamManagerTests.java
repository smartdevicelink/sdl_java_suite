package com.smartdevicelink.managers.video;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.lifecycle.OnSystemCapabilityListener;
import com.smartdevicelink.managers.lifecycle.SystemCapabilityManager;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.protocol.ISdlServiceListener;
import com.smartdevicelink.proxy.rpc.ImageResolution;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.OnTouchEvent;
import com.smartdevicelink.proxy.rpc.RegisterAppInterfaceResponse;
import com.smartdevicelink.proxy.rpc.TouchCoord;
import com.smartdevicelink.proxy.rpc.TouchEvent;
import com.smartdevicelink.proxy.rpc.VehicleType;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;
import com.smartdevicelink.proxy.rpc.enums.TouchType;
import com.smartdevicelink.proxy.rpc.enums.VideoStreamingState;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.streaming.video.SdlRemoteDisplay;
import com.smartdevicelink.streaming.video.VideoStreamingParameters;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.util.Version;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * This is a unit test class for the SmartDeviceLink video streaming manager class :
 * {@link VideoStreamManager}
 */
@RunWith(AndroidJUnit4.class)
public class VideoStreamManagerTests {
	public static final String TAG = "VideoStreamManagerTests";
	private Context mTestContext;
	private static boolean touchEventOccured = false;

	// SETUP / HELPERS

	@Before
	public void setUp() throws Exception{
		mTestContext = getInstrumentation().getContext();
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

	@Test
	public void testInitialization(){
		ISdl internalInterface = mock(ISdl.class);
		when(internalInterface.getProtocolVersion()).thenReturn(new Version(5,1,0));

		RegisterAppInterfaceResponse mockRegisterAppInterfaceResponse = new RegisterAppInterfaceResponse();
		VehicleType mockVehicleType = new VehicleType();
		mockVehicleType.setMake("Ford");
		mockRegisterAppInterfaceResponse.setVehicleType(mockVehicleType);
		when(internalInterface.getRegisterAppInterfaceResponse()).thenReturn(mockRegisterAppInterfaceResponse);

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

	@Test
	public void testHMILevelNotFull(){
		final ISdl internalInterface = mock(ISdl.class);

		SystemCapabilityManager systemCapabilityManager = mock(SystemCapabilityManager.class);
		doReturn(systemCapabilityManager).when(internalInterface).getSystemCapabilityManager();

		when(internalInterface.getProtocolVersion()).thenReturn((new Version(5,0,0)));

		RegisterAppInterfaceResponse mockRegisterAppInterfaceResponse = new RegisterAppInterfaceResponse();
		VehicleType mockVehicleType = new VehicleType();
		mockVehicleType.setMake("Ford");
		mockRegisterAppInterfaceResponse.setVehicleType(mockVehicleType);
		when(internalInterface.getRegisterAppInterfaceResponse()).thenReturn(mockRegisterAppInterfaceResponse);

		when(systemCapabilityManager.isCapabilitySupported(SystemCapabilityType.VIDEO_STREAMING)).thenReturn(true);

		final VideoStreamManager videoStreamManager = new VideoStreamManager(internalInterface);
		videoStreamManager.start(new CompletionListener() {
			@Override
			public void onComplete(boolean success) {
				VideoStreamingParameters params = new VideoStreamingParameters();
				boolean encrypted = false;
				videoStreamManager.startStreaming(params, encrypted);
				verify(internalInterface, times(0)).startVideoService(params, encrypted);
			}
		});
	}

	@Test
	public void testRemoteDisplayStream(){
		ISdl internalInterface = mock(ISdl.class);

		RegisterAppInterfaceResponse mockRegisterAppInterfaceResponse = new RegisterAppInterfaceResponse();
		VehicleType mockVehicleType = new VehicleType();
		mockVehicleType.setMake("Ford");
		mockRegisterAppInterfaceResponse.setVehicleType(mockVehicleType);
		when(internalInterface.getRegisterAppInterfaceResponse()).thenReturn(mockRegisterAppInterfaceResponse);

		final Set<Object> listenerSet = new HashSet<>();

		when(internalInterface.getProtocolVersion()).thenReturn(new Version(5,0,0));

		SystemCapabilityManager systemCapabilityManager = mock(SystemCapabilityManager.class);
		doReturn(systemCapabilityManager).when(internalInterface).getSystemCapabilityManager();
		when(systemCapabilityManager.isCapabilitySupported(SystemCapabilityType.VIDEO_STREAMING)).thenReturn(true);

		Answer<Void> onGetCapability = new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				OnSystemCapabilityListener systemCapabilityListener = (OnSystemCapabilityListener) args[1];
				systemCapabilityListener.onCapabilityRetrieved(TestValues.GENERAL_VIDEOSTREAMINGCAPABILITY);
				return null;
			}
		};

		doAnswer(onGetCapability).when(systemCapabilityManager).getCapability(eq(SystemCapabilityType.VIDEO_STREAMING), any(OnSystemCapabilityListener.class), anyBoolean());

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

		when(systemCapabilityManager.getCapability(eq(SystemCapabilityType.VIDEO_STREAMING), any(OnSystemCapabilityListener.class), anyBoolean())).thenReturn(TestValues.GENERAL_VIDEOSTREAMINGCAPABILITY);

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

	@Test
	public void testConvertTouchEvent() {
		ISdl internalInterface = mock(ISdl.class);

		RegisterAppInterfaceResponse mockRegisterAppInterfaceResponse = new RegisterAppInterfaceResponse();
		VehicleType mockVehicleType = new VehicleType();
		mockVehicleType.setMake("Ford");
		mockRegisterAppInterfaceResponse.setVehicleType(mockVehicleType);
		when(internalInterface.getRegisterAppInterfaceResponse()).thenReturn(mockRegisterAppInterfaceResponse);

		VideoStreamManager videoStreamManager = new VideoStreamManager(internalInterface);
		List<MotionEvent> motionEventList;
		long e1TS = 1558124390L, e2TS = 1558125390L, e3TS = 1558126390L;
		int e1x = 50, e1y = 100, e2x = 150, e2y = 200, e3x = 250, e3y = 300;
		int e1Id = 100, e2Id = 101, e3Id = 102;
		int movingStep = 10;
		OnTouchEvent testOnTouchEvent;
		MotionEvent motionEvent;
		TouchEvent touchEvent1 = new TouchEvent(e1Id, Collections.singletonList(e1TS), Collections.singletonList(new TouchCoord(e1x, e1y)));
		TouchEvent touchEvent2 = new TouchEvent(e2Id, Collections.singletonList(e2TS), Collections.singletonList(new TouchCoord(e2x, e2y)));
		TouchEvent touchEvent2AfterMovingPointer = new TouchEvent(e2Id, Collections.singletonList(e2TS), Collections.singletonList(new TouchCoord(e2x + movingStep, e2y + movingStep)));
		TouchEvent touchEvent3 = new TouchEvent(e3Id, Collections.singletonList(e3TS), Collections.singletonList(new TouchCoord(e3x, e3y)));



		/////////////////////////////////////////////////// First OnTouchEvent Notification ///////////////////////////////////////////////////
		testOnTouchEvent = new OnTouchEvent(TouchType.BEGIN, Arrays.asList(touchEvent1, touchEvent2));
		motionEventList = videoStreamManager.convertTouchEvent(testOnTouchEvent);


		// First MotionEvent should be ACTION_DOWN and have 1 pointer
		motionEvent = motionEventList.get(0);
		assertEquals(1, motionEvent.getPointerCount());
		assertEquals(e1x, Math.round(motionEvent.getX(0)));
		assertEquals(e1y, Math.round(motionEvent.getY(0)));
		assertEquals(MotionEvent.ACTION_DOWN, motionEvent.getActionMasked());


		// Second MotionEvent should be ACTION_POINTER_DOWN and have 2 pointers
		motionEvent = motionEventList.get(1);
		assertEquals(2, motionEvent.getPointerCount());
		assertEquals(e1x, Math.round(motionEvent.getX(0)));
		assertEquals(e1y, Math.round(motionEvent.getY(0)));
		assertEquals(e2x, Math.round(motionEvent.getX(1)));
		assertEquals(e2y, Math.round(motionEvent.getY(1)));
		assertEquals(MotionEvent.ACTION_POINTER_DOWN, motionEvent.getActionMasked());
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



		/////////////////////////////////////////////////// Second OnTouchEvent Notification ///////////////////////////////////////////////////
		testOnTouchEvent = new OnTouchEvent(TouchType.BEGIN, Arrays.asList(touchEvent3));
		motionEventList = videoStreamManager.convertTouchEvent(testOnTouchEvent);


		// First MotionEvent should be ACTION_POINTER_DOWN and have 3 pointers
		motionEvent = motionEventList.get(0);
		assertEquals(3, motionEvent.getPointerCount());
		assertEquals(e1x, Math.round(motionEvent.getX(0)));
		assertEquals(e1y, Math.round(motionEvent.getY(0)));
		assertEquals(e2x, Math.round(motionEvent.getX(1)));
		assertEquals(e2y, Math.round(motionEvent.getY(1)));
		assertEquals(e3x, Math.round(motionEvent.getX(2)));
		assertEquals(e3y, Math.round(motionEvent.getY(2)));
		assertEquals(MotionEvent.ACTION_POINTER_DOWN, motionEvent.getActionMasked());
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



		/////////////////////////////////////////////////// Third OnTouchEvent Notification ///////////////////////////////////////////////////
		testOnTouchEvent = new OnTouchEvent(TouchType.MOVE, Arrays.asList(touchEvent2AfterMovingPointer));
		motionEventList = videoStreamManager.convertTouchEvent(testOnTouchEvent);


		// First MotionEvent should be ACTION_MOVE and have 3 pointers
		motionEvent = motionEventList.get(0);
		assertEquals(3, motionEvent.getPointerCount());
		assertEquals(e1x, Math.round(motionEvent.getX(0)));
		assertEquals(e1y, Math.round(motionEvent.getY(0)));
		assertEquals(e2x + movingStep, Math.round(motionEvent.getX(1)));
		assertEquals(e2y + movingStep, Math.round(motionEvent.getY(1)));
		assertEquals(e3x, Math.round(motionEvent.getX(2)));
		assertEquals(e3y, Math.round(motionEvent.getY(2)));
		assertEquals(MotionEvent.ACTION_MOVE, motionEvent.getActionMasked());
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



		/////////////////////////////////////////////////// Fourth OnTouchEvent Notification ///////////////////////////////////////////////////
		testOnTouchEvent = new OnTouchEvent(TouchType.END, Arrays.asList(touchEvent2AfterMovingPointer));
		motionEventList = videoStreamManager.convertTouchEvent(testOnTouchEvent);


		// First MotionEvent should be ACTION_POINTER_UP and have 3 pointers
		motionEvent = motionEventList.get(0);
		assertEquals(3, motionEvent.getPointerCount());
		assertEquals(e1x, Math.round(motionEvent.getX(0)));
		assertEquals(e1y, Math.round(motionEvent.getY(0)));
		assertEquals(e2x + movingStep, Math.round(motionEvent.getX(1)));
		assertEquals(e2y + movingStep, Math.round(motionEvent.getY(1)));
		assertEquals(e3x, Math.round(motionEvent.getX(2)));
		assertEquals(e3y, Math.round(motionEvent.getY(2)));
		assertEquals(MotionEvent.ACTION_POINTER_UP, motionEvent.getActionMasked());
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



		/////////////////////////////////////////////////// Fifth OnTouchEvent Notification ///////////////////////////////////////////////////
		testOnTouchEvent = new OnTouchEvent(TouchType.END, Arrays.asList(touchEvent3));
		motionEventList = videoStreamManager.convertTouchEvent(testOnTouchEvent);


		// First MotionEvent should be ACTION_POINTER_UP and have 2 pointers
		motionEvent = motionEventList.get(0);
		assertEquals(2, motionEvent.getPointerCount());
		assertEquals(e1x, Math.round(motionEvent.getX(0)));
		assertEquals(e1y, Math.round(motionEvent.getY(0)));
		assertEquals(e3x, Math.round(motionEvent.getX(1)));
		assertEquals(e3y, Math.round(motionEvent.getY(1)));
		assertEquals(MotionEvent.ACTION_POINTER_UP, motionEvent.getActionMasked());
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



		/////////////////////////////////////////////////// Sixth OnTouchEvent Notification ///////////////////////////////////////////////////
		testOnTouchEvent = new OnTouchEvent(TouchType.END, Arrays.asList(touchEvent3));
		motionEventList = videoStreamManager.convertTouchEvent(testOnTouchEvent);


		// First MotionEvent should be ACTION_UP and have 1 pointer
		motionEvent = motionEventList.get(0);
		assertEquals(1, motionEvent.getPointerCount());
		assertEquals(e1x, Math.round(motionEvent.getX(0)));
		assertEquals(e1y, Math.round(motionEvent.getY(0)));
		assertEquals(MotionEvent.ACTION_UP, motionEvent.getActionMasked());
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



		/////////////////////////////////////////////////// Seventh OnTouchEvent Notification ///////////////////////////////////////////////////
		testOnTouchEvent = new OnTouchEvent(TouchType.BEGIN, Arrays.asList(touchEvent1, touchEvent2));
		motionEventList = videoStreamManager.convertTouchEvent(testOnTouchEvent);


		// First MotionEvent should be ACTION_DOWN and have 1 pointer
		motionEvent = motionEventList.get(0);
		assertEquals(1, motionEvent.getPointerCount());
		assertEquals(e1x, Math.round(motionEvent.getX(0)));
		assertEquals(e1y, Math.round(motionEvent.getY(0)));
		assertEquals(MotionEvent.ACTION_DOWN, motionEvent.getActionMasked());


		// Second MotionEvent should be ACTION_POINTER_DOWN and have 2 pointers
		motionEvent = motionEventList.get(1);
		assertEquals(2, motionEvent.getPointerCount());
		assertEquals(e1x, Math.round(motionEvent.getX(0)));
		assertEquals(e1y, Math.round(motionEvent.getY(0)));
		assertEquals(e2x, Math.round(motionEvent.getX(1)));
		assertEquals(e2y, Math.round(motionEvent.getY(1)));
		assertEquals(MotionEvent.ACTION_POINTER_DOWN, motionEvent.getActionMasked());
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



		/////////////////////////////////////////////////// Eighth OnTouchEvent Notification ///////////////////////////////////////////////////
		testOnTouchEvent = new OnTouchEvent(TouchType.CANCEL, Arrays.asList(touchEvent3));
		motionEventList = videoStreamManager.convertTouchEvent(testOnTouchEvent);


		// First MotionEvent should be ACTION_CANCEL and have 2 pointers
		motionEvent = motionEventList.get(0);
		assertEquals(2, motionEvent.getPointerCount());
		assertEquals(e1x, Math.round(motionEvent.getX(0)));
		assertEquals(e1y, Math.round(motionEvent.getY(0)));
		assertEquals(e2x, Math.round(motionEvent.getX(1)));
		assertEquals(e2y, Math.round(motionEvent.getY(1)));
		assertEquals(MotionEvent.ACTION_CANCEL, motionEvent.getActionMasked());
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



		/////////////////////////////////////////////////// Ninth OnTouchEvent Notification ///////////////////////////////////////////////////
		testOnTouchEvent = new OnTouchEvent(TouchType.BEGIN, Arrays.asList(touchEvent1));
		motionEventList = videoStreamManager.convertTouchEvent(testOnTouchEvent);


		// First MotionEvent should be ACTION_DOWN and have 1 pointer
		motionEvent = motionEventList.get(0);
		assertEquals(1, motionEvent.getPointerCount());
		assertEquals(e1x, Math.round(motionEvent.getX(0)));
		assertEquals(e1y, Math.round(motionEvent.getY(0)));
		assertEquals(MotionEvent.ACTION_DOWN, motionEvent.getActionMasked());
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



		/////////////////////////////////////////////////// Tenth OnTouchEvent Notification ///////////////////////////////////////////////////
		testOnTouchEvent = new OnTouchEvent(TouchType.END, Arrays.asList(touchEvent1));
		motionEventList = videoStreamManager.convertTouchEvent(testOnTouchEvent);


		// First MotionEvent should be ACTION_UP and have 1 pointer
		motionEvent = motionEventList.get(0);
		assertEquals(1, motionEvent.getPointerCount());
		assertEquals(e1x, Math.round(motionEvent.getX(0)));
		assertEquals(e1y, Math.round(motionEvent.getY(0)));
		assertEquals(MotionEvent.ACTION_UP, motionEvent.getActionMasked());
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	}

	@Test
    public void testConvertTouchEvent_Scale_1() {
        assertMotionEventWithScale(800, 480, 1.0f);
    }

    @Test
    public void testConvertTouchEvent_Scale_1_25() {
        assertMotionEventWithScale(1280, 768, 1.25f);
    }

    @Test
    public void testConvertTouchEvent_Scale_1_5() {
        assertMotionEventWithScale(1280, 768, 1.5f);
    }

    private void assertMotionEventWithScale(int width, int height, float scale) {
        ISdl internalInterface = mock(ISdl.class);

		RegisterAppInterfaceResponse mockRegisterAppInterfaceResponse = new RegisterAppInterfaceResponse();
		VehicleType mockVehicleType = new VehicleType();
		mockVehicleType.setMake("Ford");
		mockRegisterAppInterfaceResponse.setVehicleType(mockVehicleType);
		when(internalInterface.getRegisterAppInterfaceResponse()).thenReturn(mockRegisterAppInterfaceResponse);

        // Preferred Resolution capability
        ImageResolution resolution = new ImageResolution(width, height);

        // Remote display
        DisplayMetrics displayMetrics = new DisplayMetrics();
        displayMetrics.widthPixels = (int) (resolution.getResolutionWidth() / scale);
        displayMetrics.heightPixels = (int) (resolution.getResolutionHeight() / scale);


        VideoStreamManager videoStreamManager = new VideoStreamManager(internalInterface);
        videoStreamManager.createTouchScalar(resolution, displayMetrics);

        List<MotionEvent> motionEventList;
        long e1TS = 1558124390L;
        int e1x = 50, e1y = 100;
        int e1Id = 100;
        OnTouchEvent testOnTouchEvent;
        MotionEvent motionEvent;
        TouchEvent touchEvent1 = new TouchEvent(e1Id, Collections.singletonList(e1TS), Collections.singletonList(new TouchCoord(e1x, e1y)));

        testOnTouchEvent = new OnTouchEvent(TouchType.BEGIN, Arrays.asList(touchEvent1));
        motionEventList = videoStreamManager.convertTouchEvent(testOnTouchEvent);


        motionEvent = motionEventList.get(0);
        assertEquals(1, motionEvent.getPointerCount());
        assertEquals(Math.round(e1x / scale), Math.round(motionEvent.getX(0)));
        assertEquals(Math.round(e1y / scale), Math.round(motionEvent.getY(0)));
    }

    @Test
	public void testIsHMIStateVideoStreamCapable() {
		VideoStreamManager videoStreamManager = new VideoStreamManager(mock(ISdl.class));

		// Case 1 (VideoStreamingState = STREAMABLE)
		assertTrue(videoStreamManager.isHMIStateVideoStreamCapable(createOnHMIStatus(HMILevel.HMI_FULL, VideoStreamingState.STREAMABLE)));
		assertTrue(videoStreamManager.isHMIStateVideoStreamCapable(createOnHMIStatus(HMILevel.HMI_LIMITED, VideoStreamingState.STREAMABLE)));
		assertFalse(videoStreamManager.isHMIStateVideoStreamCapable(createOnHMIStatus(HMILevel.HMI_BACKGROUND, VideoStreamingState.STREAMABLE)));
		assertFalse(videoStreamManager.isHMIStateVideoStreamCapable(createOnHMIStatus(HMILevel.HMI_NONE, VideoStreamingState.STREAMABLE)));
		assertFalse(videoStreamManager.isHMIStateVideoStreamCapable(createOnHMIStatus(null, VideoStreamingState.STREAMABLE)));

		// Case 2 (VideoStreamingState = NOT_STREAMABLE)
		assertFalse(videoStreamManager.isHMIStateVideoStreamCapable(createOnHMIStatus(HMILevel.HMI_FULL, VideoStreamingState.NOT_STREAMABLE)));
		assertFalse(videoStreamManager.isHMIStateVideoStreamCapable(createOnHMIStatus(HMILevel.HMI_LIMITED, VideoStreamingState.NOT_STREAMABLE)));
		assertFalse(videoStreamManager.isHMIStateVideoStreamCapable(createOnHMIStatus(HMILevel.HMI_BACKGROUND, VideoStreamingState.NOT_STREAMABLE)));
		assertFalse(videoStreamManager.isHMIStateVideoStreamCapable(createOnHMIStatus(HMILevel.HMI_NONE, VideoStreamingState.NOT_STREAMABLE)));
		assertFalse(videoStreamManager.isHMIStateVideoStreamCapable(createOnHMIStatus(null, VideoStreamingState.NOT_STREAMABLE)));

		// Case 3 (VideoStreamingState = NULL)
		assertTrue(videoStreamManager.isHMIStateVideoStreamCapable(createOnHMIStatus(HMILevel.HMI_FULL, null)));
		assertTrue(videoStreamManager.isHMIStateVideoStreamCapable(createOnHMIStatus(HMILevel.HMI_LIMITED, null)));
		assertFalse(videoStreamManager.isHMIStateVideoStreamCapable(createOnHMIStatus(HMILevel.HMI_BACKGROUND, null)));
		assertFalse(videoStreamManager.isHMIStateVideoStreamCapable(createOnHMIStatus(HMILevel.HMI_NONE, null)));
		assertFalse(videoStreamManager.isHMIStateVideoStreamCapable(createOnHMIStatus(null, null)));

		// Case 4 (onHMIStatus = NULL)
		assertFalse(videoStreamManager.isHMIStateVideoStreamCapable(null));
	}

	private OnHMIStatus createOnHMIStatus(HMILevel hmiLevel, VideoStreamingState videoStreamingState) {
		OnHMIStatus onHMIStatus = new OnHMIStatus();
		onHMIStatus.setHmiLevel(hmiLevel);
		onHMIStatus.setVideoStreamingState(videoStreamingState);
		return onHMIStatus;
	}
}
