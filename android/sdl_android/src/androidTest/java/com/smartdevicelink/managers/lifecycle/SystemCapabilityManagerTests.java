package com.smartdevicelink.managers.lifecycle;

import android.support.test.runner.AndroidJUnit4;
import android.util.SparseArray;

import com.livio.taskmaster.Taskmaster;
import com.smartdevicelink.managers.ManagerUtility;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.interfaces.IAudioStreamListener;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.interfaces.ISdlServiceListener;
import com.smartdevicelink.proxy.interfaces.IVideoStreamListener;
import com.smartdevicelink.proxy.interfaces.OnSystemCapabilityListener;
import com.smartdevicelink.proxy.rpc.AppServiceCapability;
import com.smartdevicelink.proxy.rpc.AppServicesCapabilities;
import com.smartdevicelink.proxy.rpc.AudioPassThruCapabilities;
import com.smartdevicelink.proxy.rpc.ButtonCapabilities;
import com.smartdevicelink.proxy.rpc.DisplayCapabilities;
import com.smartdevicelink.proxy.rpc.DisplayCapability;
import com.smartdevicelink.proxy.rpc.GetSystemCapability;
import com.smartdevicelink.proxy.rpc.GetSystemCapabilityResponse;
import com.smartdevicelink.proxy.rpc.HMICapabilities;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.OnSystemCapabilityUpdated;
import com.smartdevicelink.proxy.rpc.PhoneCapability;
import com.smartdevicelink.proxy.rpc.PresetBankCapabilities;
import com.smartdevicelink.proxy.rpc.RegisterAppInterfaceResponse;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.SetDisplayLayoutResponse;
import com.smartdevicelink.proxy.rpc.SoftButtonCapabilities;
import com.smartdevicelink.proxy.rpc.SystemCapability;
import com.smartdevicelink.proxy.rpc.VideoStreamingCapability;
import com.smartdevicelink.proxy.rpc.WindowCapability;
import com.smartdevicelink.proxy.rpc.WindowTypeCapabilities;
import com.smartdevicelink.proxy.rpc.enums.AppServiceType;
import com.smartdevicelink.proxy.rpc.enums.AudioStreamingState;
import com.smartdevicelink.proxy.rpc.enums.DisplayType;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.HmiZoneCapabilities;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.MediaClockFormat;
import com.smartdevicelink.proxy.rpc.enums.PredefinedWindows;
import com.smartdevicelink.proxy.rpc.enums.PrerecordedSpeech;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.enums.ServiceUpdateReason;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;
import com.smartdevicelink.proxy.rpc.enums.SystemContext;
import com.smartdevicelink.proxy.rpc.enums.WindowType;
import com.smartdevicelink.proxy.rpc.listeners.OnMultipleRequestListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCRequestListener;
import com.smartdevicelink.streaming.audio.AudioStreamingCodec;
import com.smartdevicelink.streaming.audio.AudioStreamingParams;
import com.smartdevicelink.streaming.video.VideoStreamingParameters;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.utl.AppServiceFactory;
import com.smartdevicelink.util.CorrelationIdGenerator;
import com.smartdevicelink.util.Version;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class SystemCapabilityManagerTests {
	public static final String TAG = "SystemCapabilityManagerTests";
	public static SystemCapabilityManager systemCapabilityManager;
	private SystemCapability systemCapability;
	private VideoStreamingCapability videoStreamingCapability;

	@Before
	public void setUp() throws Exception{

		systemCapability = new SystemCapability(SystemCapabilityType.VIDEO_STREAMING);
		videoStreamingCapability = new VideoStreamingCapability();
		videoStreamingCapability.setMaxBitrate(TestValues.GENERAL_INT);
		videoStreamingCapability.setPreferredResolution(TestValues.GENERAL_IMAGERESOLUTION);
		videoStreamingCapability.setSupportedFormats(TestValues.GENERAL_VIDEOSTREAMINGFORMAT_LIST);
		systemCapability.setCapabilityForType(SystemCapabilityType.VIDEO_STREAMING, videoStreamingCapability);
	}

	public SystemCapabilityManager createSampleManager(){
		return createSampleManager(new InternalSDLInterface());
	}

	public SystemCapabilityManager createSampleManager(InternalSDLInterface iSdl){
		SystemCapabilityManager systemCapabilityManager = new SystemCapabilityManager(iSdl);

		RegisterAppInterfaceResponse raiResponse = new RegisterAppInterfaceResponse();

		raiResponse.setHmiCapabilities(TestValues.GENERAL_HMICAPABILITIES);
		raiResponse.setDisplayCapabilities(TestValues.GENERAL_DISPLAYCAPABILITIES);
		raiResponse.setAudioPassThruCapabilities(TestValues.GENERAL_AUDIOPASSTHRUCAPABILITIES_LIST);
		raiResponse.setButtonCapabilities(TestValues.GENERAL_BUTTONCAPABILITIES_LIST);
		raiResponse.setHmiZoneCapabilities(TestValues.GENERAL_HMIZONECAPABILITIES_LIST);
		raiResponse.setPresetBankCapabilities(TestValues.GENERAL_PRESETBANKCAPABILITIES);
		raiResponse.setSoftButtonCapabilities(TestValues.GENERAL_SOFTBUTTONCAPABILITIES_LIST);
		raiResponse.setSpeechCapabilities(TestValues.GENERAL_SPEECHCAPABILITIES_LIST);
		raiResponse.setPrerecordedSpeech(TestValues.GENERAL_PRERECORDEDSPEECH_LIST);
		raiResponse.setSuccess(true);

		systemCapabilityManager.parseRAIResponse(raiResponse);
		return systemCapabilityManager;
	}

	private List<DisplayCapability> createDisplayCapabilityList(DisplayCapabilities display, List<ButtonCapabilities> button, List<SoftButtonCapabilities> softButton) {
		WindowTypeCapabilities windowTypeCapabilities = new WindowTypeCapabilities(WindowType.MAIN, 1);

		DisplayCapability displayCapability = new DisplayCapability();
		displayCapability.setDisplayName(display != null ? display.getDisplayName() : null);
		displayCapability.setWindowTypeSupported(Collections.singletonList(windowTypeCapabilities));

		WindowCapability defaultWindowCapability = new WindowCapability();
		defaultWindowCapability.setWindowID(PredefinedWindows.DEFAULT_WINDOW.getValue());
		defaultWindowCapability.setButtonCapabilities(button);
		defaultWindowCapability.setSoftButtonCapabilities(softButton);

		if (display == null) {
			defaultWindowCapability.setTextFields(ManagerUtility.WindowCapabilityUtility.getAllTextFields());
			defaultWindowCapability.setImageFields(ManagerUtility.WindowCapabilityUtility.getAllImageFields());
			displayCapability.setWindowCapabilities(Collections.singletonList(defaultWindowCapability));
			return Collections.singletonList(displayCapability);
		}

		defaultWindowCapability.setTemplatesAvailable(display.getTemplatesAvailable());
		defaultWindowCapability.setNumCustomPresetsAvailable(display.getNumCustomPresetsAvailable());
		defaultWindowCapability.setTextFields(display.getTextFields());
		defaultWindowCapability.setImageFields(display.getImageFields());
		ArrayList<ImageType> imageTypeSupported = new ArrayList<>();
		imageTypeSupported.add(ImageType.STATIC);
		if (display.getGraphicSupported()) {
			imageTypeSupported.add(ImageType.DYNAMIC);
		}
		defaultWindowCapability.setImageTypeSupported(imageTypeSupported);

		displayCapability.setWindowCapabilities(Collections.singletonList(defaultWindowCapability));
		return Collections.singletonList(displayCapability);
	}

	private DisplayCapabilities createDisplayCapabilities(String displayName, WindowCapability defaultMainWindow) {
		DisplayCapabilities convertedCapabilities = new DisplayCapabilities();
		convertedCapabilities.setDisplayType(DisplayType.SDL_GENERIC); //deprecated but it is mandatory...
		convertedCapabilities.setDisplayName(displayName);
		convertedCapabilities.setTextFields(defaultMainWindow.getTextFields());
		convertedCapabilities.setImageFields(defaultMainWindow.getImageFields());
		convertedCapabilities.setTemplatesAvailable(defaultMainWindow.getTemplatesAvailable());
		convertedCapabilities.setNumCustomPresetsAvailable(defaultMainWindow.getNumCustomPresetsAvailable());
		convertedCapabilities.setMediaClockFormats(new ArrayList<MediaClockFormat>()); // mandatory field but can be empty
		convertedCapabilities.setGraphicSupported(defaultMainWindow.getImageTypeSupported().contains(ImageType.DYNAMIC));

		return convertedCapabilities;
	}

	@Test
	public void testParseRAI() {
		systemCapabilityManager = createSampleManager();

		List<DisplayCapability> displayCapabilityList = createDisplayCapabilityList(TestValues.GENERAL_DISPLAYCAPABILITIES, TestValues.GENERAL_BUTTONCAPABILITIES_LIST, TestValues.GENERAL_SOFTBUTTONCAPABILITIES_LIST);
		assertTrue(TestValues.TRUE,
				Validator.validateDisplayCapabilityList(displayCapabilityList, (List<DisplayCapability>) systemCapabilityManager.getCapability(SystemCapabilityType.DISPLAYS)));
		assertTrue(TestValues.TRUE,
				Validator.validateHMICapabilities(TestValues.GENERAL_HMICAPABILITIES, (HMICapabilities) systemCapabilityManager.getCapability(SystemCapabilityType.HMI)));
		assertTrue(TestValues.TRUE,
				Validator.validateDisplayCapabilities(TestValues.GENERAL_DISPLAYCAPABILITIES, (DisplayCapabilities) systemCapabilityManager.getCapability(SystemCapabilityType.DISPLAY)));
		assertTrue(TestValues.TRUE,
				Validator.validateAudioPassThruCapabilities(TestValues.GENERAL_AUDIOPASSTHRUCAPABILITIES_LIST, (List<AudioPassThruCapabilities>) systemCapabilityManager.getCapability(SystemCapabilityType.AUDIO_PASSTHROUGH)));
		assertTrue(TestValues.TRUE,
				Validator.validateButtonCapabilities(TestValues.GENERAL_BUTTONCAPABILITIES_LIST, (List<ButtonCapabilities> )systemCapabilityManager.getCapability(SystemCapabilityType.BUTTON)));
		assertTrue(TestValues.TRUE,
				Validator.validateHMIZoneCapabilities(TestValues.GENERAL_HMIZONECAPABILITIES_LIST, (List<HmiZoneCapabilities>) systemCapabilityManager.getCapability(SystemCapabilityType.HMI_ZONE)));
		assertTrue(TestValues.TRUE,
				Validator.validatePresetBankCapabilities(TestValues.GENERAL_PRESETBANKCAPABILITIES, (PresetBankCapabilities) systemCapabilityManager.getCapability(SystemCapabilityType.PRESET_BANK)));
		assertTrue(TestValues.TRUE,
				Validator.validateSoftButtonCapabilities(TestValues.GENERAL_SOFTBUTTONCAPABILITIES_LIST, (List<SoftButtonCapabilities>) systemCapabilityManager.getCapability(SystemCapabilityType.SOFTBUTTON)));
		assertTrue(TestValues.TRUE,
				Validator.validateSpeechCapabilities(TestValues.GENERAL_SPEECHCAPABILITIES_LIST, (List<SpeechCapabilities>) systemCapabilityManager.getCapability(SystemCapabilityType.SPEECH)));
		assertTrue(TestValues.TRUE,
				Validator.validatePreRecordedSpeechCapabilities(TestValues.GENERAL_PRERECORDEDSPEECH_LIST, (List<PrerecordedSpeech>) systemCapabilityManager.getCapability(SystemCapabilityType.PRERECORDED_SPEECH)));

	}

	@Test
	public void testNullDisplayCapabilitiesEnablesAllTextAndImageFields() {
		List<DisplayCapability> displayCapabilityList = createDisplayCapabilityList(null, TestValues.GENERAL_BUTTONCAPABILITIES_LIST, TestValues.GENERAL_SOFTBUTTONCAPABILITIES_LIST);
		assertEquals(displayCapabilityList.get(0).getWindowCapabilities().get(0).getTextFields().size(), 29);
		assertEquals(displayCapabilityList.get(0).getWindowCapabilities().get(0).getImageFields().size(), 14);
	}

	@Test
	public void testGetVSCapability(){
		VideoStreamingCapability vsCapability = new VideoStreamingCapability();
		vsCapability.setMaxBitrate(TestValues.GENERAL_INT);
		vsCapability.setPreferredResolution(TestValues.GENERAL_IMAGERESOLUTION);
		vsCapability.setSupportedFormats(TestValues.GENERAL_VIDEOSTREAMINGFORMAT_LIST);

		SystemCapability cap = new SystemCapability();
		cap.setSystemCapabilityType(SystemCapabilityType.VIDEO_STREAMING);
		cap.setCapabilityForType(SystemCapabilityType.VIDEO_STREAMING, vsCapability);

		final SystemCapability referenceCapability = cap;

		systemCapabilityManager = new SystemCapabilityManager(new InternalSDLInterface() {
			@Override
			public void sendRPC(RPCMessage message) {
				GetSystemCapabilityResponse response = new GetSystemCapabilityResponse();
				response.setSystemCapability(referenceCapability);
				response.setSuccess(true);
				if (message instanceof RPCRequest) {
					RPCRequest request = (RPCRequest) message;
					request.getOnRPCResponseListener().onResponse(CorrelationIdGenerator.generateId(), response);
				}
			}

			@Override
			public void addOnRPCListener(FunctionID messageId, OnRPCListener listener) {
				listener.onReceived(new OnHMIStatus(HMILevel.HMI_FULL, AudioStreamingState.NOT_AUDIBLE, SystemContext.SYSCTXT_MAIN));
			}
		});

		systemCapabilityManager.getCapability(SystemCapabilityType.VIDEO_STREAMING, new OnSystemCapabilityListener() {
			@Override
			public void onCapabilityRetrieved(Object capability) {
				assertTrue(TestValues.TRUE,
						Validator.validateVideoStreamingCapability(
								(VideoStreamingCapability) referenceCapability.getCapabilityForType(SystemCapabilityType.VIDEO_STREAMING),
								(VideoStreamingCapability) capability));
			}

			@Override
			public void onError(String info) {
				assertTrue(false);
			}
		});
	}

	private Answer<Void> createOnHMIStatusAnswer(final HMILevel hmiLevel){
		Answer<Void> onHMIStatusAnswer = new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				OnRPCListener onHMIStatusListener = (OnRPCListener) args[1];
				OnHMIStatus onHMIStatusFakeNotification = new OnHMIStatus();
				onHMIStatusFakeNotification.setHmiLevel(hmiLevel);
				onHMIStatusListener.onReceived(onHMIStatusFakeNotification);
				return null;
			}
		};
		return onHMIStatusAnswer;
	}

	private Answer<Void> createOnSendGetSystemCapabilityAnswer (final boolean success, final Boolean subscribe) {
		Answer<Void> onSendGetSystemCapabilityAnswer = new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				GetSystemCapability getSystemCapability = (GetSystemCapability) args[0];
				if (subscribe != null) {
					assertEquals(subscribe, getSystemCapability.getSubscribe());
				}
				GetSystemCapabilityResponse response;
				if (success) {
					response = new GetSystemCapabilityResponse(Result.SUCCESS, true);
				} else {
					response = new GetSystemCapabilityResponse(Result.REJECTED, false);
				}
				response.setSystemCapability(systemCapability);
				getSystemCapability.getOnRPCResponseListener().onResponse(CorrelationIdGenerator.generateId(), response);
				return null;
			}
		};
		return onSendGetSystemCapabilityAnswer;
	}

	@Test
	public void testGetCapability() {
		ISdl internalInterface;
		SystemCapabilityManager scm;
		OnSystemCapabilityListener onSystemCapabilityListener;
		VideoStreamingCapability retrievedCapability;


		// Test case 1 (capability not cached, listener not null, forceUpdate false)
		internalInterface = mock(ISdl.class);
		doAnswer(createOnHMIStatusAnswer(HMILevel.HMI_FULL)).when(internalInterface).addOnRPCListener(eq(FunctionID.ON_HMI_STATUS), any(OnRPCListener.class));
		scm = new SystemCapabilityManager(internalInterface);
		onSystemCapabilityListener = mock(OnSystemCapabilityListener.class);
		doAnswer(createOnSendGetSystemCapabilityAnswer(true, null)).when(internalInterface).sendRPC(any(GetSystemCapability.class));
		scm.setCapability(SystemCapabilityType.VIDEO_STREAMING, null);
		retrievedCapability = (VideoStreamingCapability) scm.getCapability(SystemCapabilityType.VIDEO_STREAMING, onSystemCapabilityListener, false);
		assertNull(retrievedCapability);
		verify(internalInterface, times(1)).sendRPC(any(GetSystemCapability.class));
		verify(onSystemCapabilityListener, times(1)).onCapabilityRetrieved(any(Object.class));


		// Test case 2 (capability cached, listener not null, forceUpdate true)
		internalInterface = mock(ISdl.class);
		doAnswer(createOnHMIStatusAnswer(HMILevel.HMI_FULL)).when(internalInterface).addOnRPCListener(eq(FunctionID.ON_HMI_STATUS), any(OnRPCListener.class));
		scm = new SystemCapabilityManager(internalInterface);
		onSystemCapabilityListener = mock(OnSystemCapabilityListener.class);
		doAnswer(createOnSendGetSystemCapabilityAnswer(true, null)).when(internalInterface).sendRPC(any(GetSystemCapability.class));
		scm.setCapability(SystemCapabilityType.VIDEO_STREAMING, videoStreamingCapability);
		retrievedCapability =  (VideoStreamingCapability) scm.getCapability(SystemCapabilityType.VIDEO_STREAMING, onSystemCapabilityListener, true);
		assertTrue(TestValues.TRUE, Validator.validateVideoStreamingCapability((VideoStreamingCapability) systemCapability.getCapabilityForType(SystemCapabilityType.VIDEO_STREAMING), retrievedCapability));
		verify(internalInterface, times(1)).sendRPC(any(GetSystemCapability.class));
		verify(onSystemCapabilityListener, times(1)).onCapabilityRetrieved(any(Object.class));


		// Test case 3 (capability cached, listener null, forceUpdate true)
		internalInterface = mock(ISdl.class);
		doAnswer(createOnHMIStatusAnswer(HMILevel.HMI_FULL)).when(internalInterface).addOnRPCListener(eq(FunctionID.ON_HMI_STATUS), any(OnRPCListener.class));
		scm = new SystemCapabilityManager(internalInterface);
		onSystemCapabilityListener = null;
		doAnswer(createOnSendGetSystemCapabilityAnswer(true, null)).when(internalInterface).sendRPC(any(GetSystemCapability.class));
		scm.setCapability(SystemCapabilityType.VIDEO_STREAMING, videoStreamingCapability);
		retrievedCapability =  (VideoStreamingCapability) scm.getCapability(SystemCapabilityType.VIDEO_STREAMING, onSystemCapabilityListener, true);
		assertTrue(TestValues.TRUE, Validator.validateVideoStreamingCapability((VideoStreamingCapability) systemCapability.getCapabilityForType(SystemCapabilityType.VIDEO_STREAMING), retrievedCapability));
		verify(internalInterface, times(1)).sendRPC(any(GetSystemCapability.class));


		// Test case 4 (capability cached, listener null, forceUpdate false)
		internalInterface = mock(ISdl.class);
		doAnswer(createOnHMIStatusAnswer(HMILevel.HMI_FULL)).when(internalInterface).addOnRPCListener(eq(FunctionID.ON_HMI_STATUS), any(OnRPCListener.class));
		scm = new SystemCapabilityManager(internalInterface);
		onSystemCapabilityListener = null;
		doAnswer(createOnSendGetSystemCapabilityAnswer(true, null)).when(internalInterface).sendRPC(any(GetSystemCapability.class));
		scm.setCapability(SystemCapabilityType.VIDEO_STREAMING, videoStreamingCapability);
		retrievedCapability =  (VideoStreamingCapability) scm.getCapability(SystemCapabilityType.VIDEO_STREAMING, onSystemCapabilityListener, false);
		assertTrue(TestValues.TRUE, Validator.validateVideoStreamingCapability((VideoStreamingCapability) systemCapability.getCapabilityForType(SystemCapabilityType.VIDEO_STREAMING), retrievedCapability));
		verify(internalInterface, times(0)).sendRPC(any(GetSystemCapability.class));
	}

	@Test
	public void testGetCapabilityHmiNone() {
		ISdl internalInterface = mock(ISdl.class);
		doAnswer(createOnHMIStatusAnswer(HMILevel.HMI_NONE)).when(internalInterface).addOnRPCListener(eq(FunctionID.ON_HMI_STATUS), any(OnRPCListener.class));
		SystemCapabilityManager scm = new SystemCapabilityManager(internalInterface);
		OnSystemCapabilityListener onSystemCapabilityListener = mock(OnSystemCapabilityListener.class);
		doAnswer(createOnSendGetSystemCapabilityAnswer(true, null)).when(internalInterface).sendRPC(any(GetSystemCapability.class));
		scm.setCapability(SystemCapabilityType.VIDEO_STREAMING, null);
		VideoStreamingCapability retrievedCapability = (VideoStreamingCapability) scm.getCapability(SystemCapabilityType.VIDEO_STREAMING, onSystemCapabilityListener, false);
		assertNull(retrievedCapability);
		verify(internalInterface, times(0)).sendRPC(any(GetSystemCapability.class));
		verify(onSystemCapabilityListener, times(0)).onCapabilityRetrieved(any(Object.class));
		verify(onSystemCapabilityListener, times(1)).onError(any(String.class));
	}

	@Test
	public void testAddOnSystemCapabilityListenerWithSubscriptionsSupportedAndCapabilityCached() {
		SdlMsgVersion sdlMsgVersion = new SdlMsgVersion(6, 0); // This version supports capability subscriptions
		sdlMsgVersion.setPatchVersion(0);
		ISdl internalInterface = mock(ISdl.class);
		doAnswer(createOnHMIStatusAnswer(HMILevel.HMI_FULL)).when(internalInterface).addOnRPCListener(eq(FunctionID.ON_HMI_STATUS), any(OnRPCListener.class));
		when(internalInterface.getSdlMsgVersion()).thenReturn(sdlMsgVersion);
		SystemCapabilityManager scm = new SystemCapabilityManager(internalInterface);
		scm.setCapability(SystemCapabilityType.VIDEO_STREAMING, videoStreamingCapability);


		// Add listener1
		// When the first listener is added, GetSystemCapability request should go out with subscribe=true
		OnSystemCapabilityListener onSystemCapabilityListener1 = mock(OnSystemCapabilityListener.class);
		doAnswer(createOnSendGetSystemCapabilityAnswer(true, true)).when(internalInterface).sendRPC(any(GetSystemCapability.class));
		scm.addOnSystemCapabilityListener(SystemCapabilityType.VIDEO_STREAMING, onSystemCapabilityListener1);
		verify(internalInterface, times(1)).sendRPC(any(GetSystemCapability.class));
		verify(onSystemCapabilityListener1, times(1)).onCapabilityRetrieved(any(Object.class));


		// Add listener2
		OnSystemCapabilityListener onSystemCapabilityListener2 = mock(OnSystemCapabilityListener.class);
		scm.addOnSystemCapabilityListener(SystemCapabilityType.VIDEO_STREAMING, onSystemCapabilityListener2);
		verify(onSystemCapabilityListener2, times(1)).onCapabilityRetrieved(any(Object.class));


		// Add listener3
		OnSystemCapabilityListener onSystemCapabilityListener3 = mock(OnSystemCapabilityListener.class);
		scm.addOnSystemCapabilityListener(SystemCapabilityType.VIDEO_STREAMING, onSystemCapabilityListener3);
		verify(onSystemCapabilityListener3, times(1)).onCapabilityRetrieved(any(Object.class));


		// Remove listener1
		scm.removeOnSystemCapabilityListener(SystemCapabilityType.VIDEO_STREAMING, onSystemCapabilityListener1);


		// Remove listener2
		scm.removeOnSystemCapabilityListener(SystemCapabilityType.VIDEO_STREAMING, onSystemCapabilityListener2);


		// Remove listener3
		// When the last listener is removed, GetSystemCapability request should go out with subscribe=false
		doAnswer(createOnSendGetSystemCapabilityAnswer(true, false)).when(internalInterface).sendRPC(any(GetSystemCapability.class));
		scm.removeOnSystemCapabilityListener(SystemCapabilityType.VIDEO_STREAMING, onSystemCapabilityListener3);
		verify(internalInterface, times(2)).sendRPC(any(GetSystemCapability.class));
	}

	@Test
	public void testAddOnSystemCapabilityListenerWithSubscriptionsSupportedAndCapabilityNotCached() {
		SdlMsgVersion sdlMsgVersion = new SdlMsgVersion(6, 0); // This version supports capability subscriptions
		sdlMsgVersion.setPatchVersion(0);
		ISdl internalInterface = mock(ISdl.class);
		doAnswer(createOnHMIStatusAnswer(HMILevel.HMI_FULL)).when(internalInterface).addOnRPCListener(eq(FunctionID.ON_HMI_STATUS), any(OnRPCListener.class));
		when(internalInterface.getSdlMsgVersion()).thenReturn(sdlMsgVersion);
		SystemCapabilityManager scm = new SystemCapabilityManager(internalInterface);
		scm.setCapability(SystemCapabilityType.VIDEO_STREAMING, null);


		// Add listener1
		// When the first listener is added, GetSystemCapability request should go out with subscribe=true
		OnSystemCapabilityListener onSystemCapabilityListener1 = mock(OnSystemCapabilityListener.class);
		doAnswer(createOnSendGetSystemCapabilityAnswer(true, true)).when(internalInterface).sendRPC(any(GetSystemCapability.class));
		scm.addOnSystemCapabilityListener(SystemCapabilityType.VIDEO_STREAMING, onSystemCapabilityListener1);
		verify(internalInterface, times(1)).sendRPC(any(GetSystemCapability.class));
		verify(onSystemCapabilityListener1, times(1)).onCapabilityRetrieved(any(Object.class));


		// Add listener2
		OnSystemCapabilityListener onSystemCapabilityListener2 = mock(OnSystemCapabilityListener.class);
		scm.addOnSystemCapabilityListener(SystemCapabilityType.VIDEO_STREAMING, onSystemCapabilityListener2);
		verify(onSystemCapabilityListener2, times(1)).onCapabilityRetrieved(any(Object.class));


		// Add listener3
		OnSystemCapabilityListener onSystemCapabilityListener3 = mock(OnSystemCapabilityListener.class);
		scm.addOnSystemCapabilityListener(SystemCapabilityType.VIDEO_STREAMING, onSystemCapabilityListener3);
		verify(onSystemCapabilityListener3, times(1)).onCapabilityRetrieved(any(Object.class));


		// Remove listener1
		scm.removeOnSystemCapabilityListener(SystemCapabilityType.VIDEO_STREAMING, onSystemCapabilityListener1);


		// Remove listener2
		scm.removeOnSystemCapabilityListener(SystemCapabilityType.VIDEO_STREAMING, onSystemCapabilityListener2);


		// Remove listener3
		// When the last listener is removed, GetSystemCapability request should go out with subscribe=false
		doAnswer(createOnSendGetSystemCapabilityAnswer(true, false)).when(internalInterface).sendRPC(any(GetSystemCapability.class));
		scm.removeOnSystemCapabilityListener(SystemCapabilityType.VIDEO_STREAMING, onSystemCapabilityListener3);
		verify(internalInterface, times(2)).sendRPC(any(GetSystemCapability.class));
	}

	@Test
	public void testAddOnSystemCapabilityListenerWithSubscriptionsNotSupportedAndCapabilityCached() {
		SdlMsgVersion sdlMsgVersion = new SdlMsgVersion(5, 0); // This version doesn't support capability subscriptions
		sdlMsgVersion.setPatchVersion(0);
		ISdl internalInterface = mock(ISdl.class);
		doAnswer(createOnHMIStatusAnswer(HMILevel.HMI_FULL)).when(internalInterface).addOnRPCListener(eq(FunctionID.ON_HMI_STATUS), any(OnRPCListener.class));
		when(internalInterface.getSdlMsgVersion()).thenReturn(sdlMsgVersion);
		SystemCapabilityManager scm = new SystemCapabilityManager(internalInterface);
		scm.setCapability(SystemCapabilityType.VIDEO_STREAMING, videoStreamingCapability);


		// Add listener1
		// When the first listener is added, GetSystemCapability request should not go out because subscription is not supported and the capability is cached
		OnSystemCapabilityListener onSystemCapabilityListener1 = mock(OnSystemCapabilityListener.class);
		doAnswer(createOnSendGetSystemCapabilityAnswer(true, true)).when(internalInterface).sendRPC(any(GetSystemCapability.class));
		scm.addOnSystemCapabilityListener(SystemCapabilityType.VIDEO_STREAMING, onSystemCapabilityListener1);
		verify(internalInterface, times(0)).sendRPC(any(GetSystemCapability.class));
		verify(onSystemCapabilityListener1, times(1)).onCapabilityRetrieved(any(Object.class));


		// Add listener2
		OnSystemCapabilityListener onSystemCapabilityListener2 = mock(OnSystemCapabilityListener.class);
		scm.addOnSystemCapabilityListener(SystemCapabilityType.VIDEO_STREAMING, onSystemCapabilityListener2);
		verify(onSystemCapabilityListener2, times(1)).onCapabilityRetrieved(any(Object.class));


		// Add listener3
		OnSystemCapabilityListener onSystemCapabilityListener3 = mock(OnSystemCapabilityListener.class);
		scm.addOnSystemCapabilityListener(SystemCapabilityType.VIDEO_STREAMING, onSystemCapabilityListener3);
		verify(onSystemCapabilityListener3, times(1)).onCapabilityRetrieved(any(Object.class));


		// Remove listener1
		scm.removeOnSystemCapabilityListener(SystemCapabilityType.VIDEO_STREAMING, onSystemCapabilityListener1);


		// Remove listener2
		scm.removeOnSystemCapabilityListener(SystemCapabilityType.VIDEO_STREAMING, onSystemCapabilityListener2);


		// Remove listener3
		// When the last listener is removed, GetSystemCapability request should not go out because subscription is not supported
		doAnswer(createOnSendGetSystemCapabilityAnswer(true, false)).when(internalInterface).sendRPC(any(GetSystemCapability.class));
		scm.removeOnSystemCapabilityListener(SystemCapabilityType.VIDEO_STREAMING, onSystemCapabilityListener3);
		verify(internalInterface, times(0)).sendRPC(any(GetSystemCapability.class));
	}

	@Test
	public void testAddOnSystemCapabilityListenerWithSubscriptionsNotSupportedAndCapabilityNotCached() {
		SdlMsgVersion sdlMsgVersion = new SdlMsgVersion(5, 0); // This version doesn't support capability subscriptions
		sdlMsgVersion.setPatchVersion(0);
		ISdl internalInterface = mock(ISdl.class);
		doAnswer(createOnHMIStatusAnswer(HMILevel.HMI_FULL)).when(internalInterface).addOnRPCListener(eq(FunctionID.ON_HMI_STATUS), any(OnRPCListener.class));
		when(internalInterface.getSdlMsgVersion()).thenReturn(sdlMsgVersion);
		SystemCapabilityManager scm = new SystemCapabilityManager(internalInterface);
		scm.setCapability(SystemCapabilityType.VIDEO_STREAMING, null);


		// Add listener1
		// When the first listener is added, GetSystemCapability request should out because because capability is not cached
		OnSystemCapabilityListener onSystemCapabilityListener1 = mock(OnSystemCapabilityListener.class);
		doAnswer(createOnSendGetSystemCapabilityAnswer(true, false)).when(internalInterface).sendRPC(any(GetSystemCapability.class));
		scm.addOnSystemCapabilityListener(SystemCapabilityType.VIDEO_STREAMING, onSystemCapabilityListener1);
		verify(internalInterface, times(1)).sendRPC(any(GetSystemCapability.class));
		verify(onSystemCapabilityListener1, times(1)).onCapabilityRetrieved(any(Object.class));


		// Add listener2
		OnSystemCapabilityListener onSystemCapabilityListener2 = mock(OnSystemCapabilityListener.class);
		scm.addOnSystemCapabilityListener(SystemCapabilityType.VIDEO_STREAMING, onSystemCapabilityListener2);
		verify(onSystemCapabilityListener2, times(1)).onCapabilityRetrieved(any(Object.class));


		// Add listener3
		OnSystemCapabilityListener onSystemCapabilityListener3 = mock(OnSystemCapabilityListener.class);
		scm.addOnSystemCapabilityListener(SystemCapabilityType.VIDEO_STREAMING, onSystemCapabilityListener3);
		verify(onSystemCapabilityListener3, times(1)).onCapabilityRetrieved(any(Object.class));


		// Remove listener1
		scm.removeOnSystemCapabilityListener(SystemCapabilityType.VIDEO_STREAMING, onSystemCapabilityListener1);


		// Remove listener2
		scm.removeOnSystemCapabilityListener(SystemCapabilityType.VIDEO_STREAMING, onSystemCapabilityListener2);


		// Remove listener3
		// When the last listener is removed, GetSystemCapability request should not go out because subscription is not supported
		doAnswer(createOnSendGetSystemCapabilityAnswer(true, false)).when(internalInterface).sendRPC(any(GetSystemCapability.class));
		scm.removeOnSystemCapabilityListener(SystemCapabilityType.VIDEO_STREAMING, onSystemCapabilityListener3);
		verify(internalInterface, times(1)).sendRPC(any(GetSystemCapability.class));
	}

	@Test
	public void testAddOnSystemCapabilityListenerThenGetCapabilityWhenSubscriptionsAreNotSupported() {
		SdlMsgVersion sdlMsgVersion = new SdlMsgVersion(5, 0); // This version doesn't support capability subscriptions
		sdlMsgVersion.setPatchVersion(0);
		ISdl internalInterface = mock(ISdl.class);
		doAnswer(createOnHMIStatusAnswer(HMILevel.HMI_FULL)).when(internalInterface).addOnRPCListener(eq(FunctionID.ON_HMI_STATUS), any(OnRPCListener.class));
		when(internalInterface.getSdlMsgVersion()).thenReturn(sdlMsgVersion);
		SystemCapabilityManager scm = new SystemCapabilityManager(internalInterface);
		scm.setCapability(SystemCapabilityType.VIDEO_STREAMING, videoStreamingCapability);


		// Add listener1
		// When the first listener is added, GetSystemCapability request should go out with subscribe=false
		OnSystemCapabilityListener onSystemCapabilityListener1 = mock(OnSystemCapabilityListener.class);
		doAnswer(createOnSendGetSystemCapabilityAnswer(true, false)).when(internalInterface).sendRPC(any(GetSystemCapability.class));
		scm.addOnSystemCapabilityListener(SystemCapabilityType.VIDEO_STREAMING, onSystemCapabilityListener1);
		verify(internalInterface, times(0)).sendRPC(any(GetSystemCapability.class));
		verify(onSystemCapabilityListener1, times(1)).onCapabilityRetrieved(any(Object.class));


		// Get Capability (should notify listener1 again)
		scm.getCapability(SystemCapabilityType.VIDEO_STREAMING, null, true);
		verify(internalInterface, times(1)).sendRPC(any(GetSystemCapability.class));
		verify(onSystemCapabilityListener1, times(2)).onCapabilityRetrieved(any(Object.class));


		// Add listener2
		OnSystemCapabilityListener onSystemCapabilityListener2 = mock(OnSystemCapabilityListener.class);
		scm.addOnSystemCapabilityListener(SystemCapabilityType.VIDEO_STREAMING, onSystemCapabilityListener2);
		verify(onSystemCapabilityListener2, times(1)).onCapabilityRetrieved(any(Object.class));


		// Get Capability (should notify listener1 & listener2 again)
		scm.getCapability(SystemCapabilityType.VIDEO_STREAMING, null, true);
		verify(internalInterface, times(2)).sendRPC(any(GetSystemCapability.class));
		verify(onSystemCapabilityListener1, times(3)).onCapabilityRetrieved(any(Object.class));
		verify(onSystemCapabilityListener2, times(2)).onCapabilityRetrieved(any(Object.class));


		// Add listener3
		OnSystemCapabilityListener onSystemCapabilityListener3 = mock(OnSystemCapabilityListener.class);
		scm.addOnSystemCapabilityListener(SystemCapabilityType.VIDEO_STREAMING, onSystemCapabilityListener3);
		verify(onSystemCapabilityListener3, times(1)).onCapabilityRetrieved(any(Object.class));


		// Get Capability (should notify listener1 & listener2 & listener3 again)
		scm.getCapability(SystemCapabilityType.VIDEO_STREAMING, null, true);
		verify(internalInterface, times(3)).sendRPC(any(GetSystemCapability.class));
		verify(onSystemCapabilityListener1, times(4)).onCapabilityRetrieved(any(Object.class));
		verify(onSystemCapabilityListener2, times(3)).onCapabilityRetrieved(any(Object.class));
		verify(onSystemCapabilityListener3, times(2)).onCapabilityRetrieved(any(Object.class));


		// Remove listener1
		scm.removeOnSystemCapabilityListener(SystemCapabilityType.VIDEO_STREAMING, onSystemCapabilityListener1);


		// Get Capability (should notify listener2 & listener3 again)
		scm.getCapability(SystemCapabilityType.VIDEO_STREAMING, null, true);
		verify(internalInterface, times(4)).sendRPC(any(GetSystemCapability.class));
		verify(onSystemCapabilityListener1, times(4)).onCapabilityRetrieved(any(Object.class));
		verify(onSystemCapabilityListener2, times(4)).onCapabilityRetrieved(any(Object.class));
		verify(onSystemCapabilityListener3, times(3)).onCapabilityRetrieved(any(Object.class));


		// Remove listener2
		scm.removeOnSystemCapabilityListener(SystemCapabilityType.VIDEO_STREAMING, onSystemCapabilityListener2);


		// Get Capability (should notify listener3 again)
		scm.getCapability(SystemCapabilityType.VIDEO_STREAMING, null, true);
		verify(internalInterface, times(5)).sendRPC(any(GetSystemCapability.class));
		verify(onSystemCapabilityListener1, times(4)).onCapabilityRetrieved(any(Object.class));
		verify(onSystemCapabilityListener2, times(4)).onCapabilityRetrieved(any(Object.class));
		verify(onSystemCapabilityListener3, times(4)).onCapabilityRetrieved(any(Object.class));


		// Remove listener3
		scm.removeOnSystemCapabilityListener(SystemCapabilityType.VIDEO_STREAMING, onSystemCapabilityListener3);
		verify(internalInterface, times(5)).sendRPC(any(GetSystemCapability.class));


		// Get Capability (should not notify any listener again because they are all removed)
		scm.getCapability(SystemCapabilityType.VIDEO_STREAMING, null, true);
		verify(internalInterface, times(6)).sendRPC(any(GetSystemCapability.class));
		verify(onSystemCapabilityListener1, times(4)).onCapabilityRetrieved(any(Object.class));
		verify(onSystemCapabilityListener2, times(4)).onCapabilityRetrieved(any(Object.class));
		verify(onSystemCapabilityListener3, times(4)).onCapabilityRetrieved(any(Object.class));
	}

	@Test
	public void testGetAndAddListenerForDisplaysCapability() {
		ISdl internalInterface;
		SystemCapabilityManager scm;
		OnSystemCapabilityListener onSystemCapabilityListener;
		DisplayCapabilities retrievedCapability;


		// Test case 1 (capability cached, listener not null, forceUpdate true)
		internalInterface = mock(ISdl.class);
		doAnswer(createOnHMIStatusAnswer(HMILevel.HMI_FULL)).when(internalInterface).addOnRPCListener(eq(FunctionID.ON_HMI_STATUS), any(OnRPCListener.class));
		scm = new SystemCapabilityManager(internalInterface);
		onSystemCapabilityListener = mock(OnSystemCapabilityListener.class);
		doAnswer(createOnSendGetSystemCapabilityAnswer(true, null)).when(internalInterface).sendRPC(any(GetSystemCapability.class));
		scm.setCapability(SystemCapabilityType.DISPLAYS, new DisplayCapabilities());
		retrievedCapability = (DisplayCapabilities) scm.getCapability(SystemCapabilityType.DISPLAYS, onSystemCapabilityListener, true);
		assertNotNull(retrievedCapability);
		verify(internalInterface, times(0)).sendRPC(any(GetSystemCapability.class));
		verify(onSystemCapabilityListener, times(1)).onCapabilityRetrieved(any(Object.class));
		verify(onSystemCapabilityListener, times(0)).onError(any(String.class));


		// Test case 2 (Add listener)
		// When the first DISPLAYS listener is added, GetSystemCapability request should not go out
		OnSystemCapabilityListener onSystemCapabilityListener1 = mock(OnSystemCapabilityListener.class);
		scm.addOnSystemCapabilityListener(SystemCapabilityType.DISPLAYS, onSystemCapabilityListener1);
		verify(internalInterface, times(0)).sendRPC(any(GetSystemCapability.class));
		verify(onSystemCapabilityListener1, times(1)).onCapabilityRetrieved(any(Object.class));


		// Test case 3 (Remove listener)
		// When the last DISPLAYS listener is removed, GetSystemCapability request should not go out
		scm.removeOnSystemCapabilityListener(SystemCapabilityType.DISPLAYS, onSystemCapabilityListener1);
		verify(internalInterface, times(0)).sendRPC(any(GetSystemCapability.class));
	}

	@Test
	public void testListConversion(){
		SystemCapabilityManager systemCapabilityManager = createSampleManager();
		Object capability = systemCapabilityManager.getCapability(SystemCapabilityType.SOFTBUTTON);
		assertNotNull(capability);
		List<SoftButtonCapabilities> list = SystemCapabilityManager.convertToList(capability, SoftButtonCapabilities.class);
		assertNotNull(list);
	}

	@Test
	public void testFalsePositive(){
		SystemCapabilityManager systemCapabilityManager = createSampleManager();
		systemCapabilityManager.setCapability(SystemCapabilityType.AUDIO_PASSTHROUGH, null);
		assertFalse(systemCapabilityManager.isCapabilitySupported(SystemCapabilityType.AUDIO_PASSTHROUGH));
	}

	@Test
	public void testOnSystemCapabilityUpdateWithNoExistingCap(){
		InternalSDLInterface iSDL = new InternalSDLInterface();
		SystemCapabilityManager systemCapabilityManager = createSampleManager(iSDL);
		OnRPCListener scmRpcListener = iSDL.rpcListeners.get(FunctionID.ON_SYSTEM_CAPABILITY_UPDATED.getId()).get(0);
		assertNotNull(scmRpcListener);

		assertNull(systemCapabilityManager.getCapability(SystemCapabilityType.APP_SERVICES));

		/* PERFORM A NOTIFICATION SEND THROUGH THE SCM */
		AppServiceCapability addServiceID = AppServiceFactory.createAppServiceCapability(AppServiceType.NAVIGATION, "test", "3453", true, null);
		AppServicesCapabilities serviceIdASC = new AppServicesCapabilities();
		serviceIdASC.setAppServices(Collections.singletonList(addServiceID));

		SystemCapability systemCapability = new SystemCapability();
		systemCapability.setSystemCapabilityType(SystemCapabilityType.APP_SERVICES);
		systemCapability.setCapabilityForType(SystemCapabilityType.APP_SERVICES, serviceIdASC);

		OnSystemCapabilityUpdated onSystemCapabilityUpdated = new OnSystemCapabilityUpdated();
		onSystemCapabilityUpdated.setSystemCapability(systemCapability);

		scmRpcListener.onReceived(onSystemCapabilityUpdated);

		assertNotNull(systemCapabilityManager.getCapability(SystemCapabilityType.APP_SERVICES));
	}

	@Test
	public void testOnSystemCapabilityUpdatedForDISPLAYS() {
		InternalSDLInterface iSDL = new InternalSDLInterface();
		SystemCapabilityManager systemCapabilityManager = createSampleManager(iSDL);
		OnRPCListener scmRpcListener = iSDL.rpcListeners.get(FunctionID.ON_SYSTEM_CAPABILITY_UPDATED.getId()).get(0);
		assertNotNull(scmRpcListener);

		assertNotNull(systemCapabilityManager.getCapability(SystemCapabilityType.DISPLAYS));
		assertNotNull(systemCapabilityManager.getCapability(SystemCapabilityType.DISPLAY));

		List<DisplayCapability> newCaps = createDisplayCapabilityList(TestValues.GENERAL_DISPLAYCAPABILITIES, TestValues.GENERAL_BUTTONCAPABILITIES_LIST, TestValues.GENERAL_SOFTBUTTONCAPABILITIES_LIST);;

		SystemCapability systemCapability = new SystemCapability();
		systemCapability.setSystemCapabilityType(SystemCapabilityType.DISPLAYS);
		systemCapability.setCapabilityForType(SystemCapabilityType.DISPLAYS, newCaps);

		OnSystemCapabilityUpdated onSystemCapabilityUpdated = new OnSystemCapabilityUpdated();
		onSystemCapabilityUpdated.setSystemCapability(systemCapability);

		scmRpcListener.onReceived(onSystemCapabilityUpdated);

		List<DisplayCapability> appliedCaps = (List<DisplayCapability>)systemCapabilityManager.getCapability(SystemCapabilityType.DISPLAYS);
		assertNotNull(appliedCaps);
		assertTrue(Validator.validateDisplayCapabilityList(newCaps, appliedCaps));

		DisplayCapabilities appliedConvertedCaps = (DisplayCapabilities)systemCapabilityManager.getCapability(SystemCapabilityType.DISPLAY);
		assertNotNull(appliedConvertedCaps);
		DisplayCapabilities testConvertedCaps = createDisplayCapabilities(newCaps.get(0).getDisplayName(), newCaps.get(0).getWindowCapabilities().get(0));
		assertTrue(Validator.validateDisplayCapabilities(appliedConvertedCaps, testConvertedCaps));
	}

	@Test
	public void testOnSystemCapabilityUpdated(){
		InternalSDLInterface iSDL = new InternalSDLInterface();
		String baseName = "NavTest", baseID = "37F98053AE";
		AppServiceCapability capability1 = AppServiceFactory.createAppServiceCapability(AppServiceType.NAVIGATION, baseName, null, true, null);

		AppServicesCapabilities appServicesCapabilities = new AppServicesCapabilities();
		appServicesCapabilities.setAppServices(Collections.singletonList(capability1));

		SystemCapabilityManager systemCapabilityManager = createSampleManager(iSDL);
		assertNotNull(iSDL.rpcListeners.get(FunctionID.ON_SYSTEM_CAPABILITY_UPDATED.getId()));
		OnRPCListener scmRpcListener = iSDL.rpcListeners.get(FunctionID.ON_SYSTEM_CAPABILITY_UPDATED.getId()).get(0);
		assertNotNull(scmRpcListener);

		/* CONFIRM THE CAP DOESN'T EXIST IN SCM */
		AppServicesCapabilities cachedCap = (AppServicesCapabilities)systemCapabilityManager.getCapability(SystemCapabilityType.APP_SERVICES);
		assertNull(cachedCap);

		/* ADD THE CAP IN SCM */
		systemCapabilityManager.setCapability(SystemCapabilityType.APP_SERVICES, appServicesCapabilities);
		/* CONFIRM THE CAP DOES EXIST IN SCM */
		cachedCap = (AppServicesCapabilities)systemCapabilityManager.getCapability(SystemCapabilityType.APP_SERVICES);
		assertNotNull(cachedCap);
		/* CONFIRM THE CAP IN SCM EQUALS ORIGINAL*/
		assertEquals(cachedCap, appServicesCapabilities);
		assertNull(cachedCap.getAppServices().get(0).getUpdatedAppServiceRecord().getServiceID());

		/* PERFORM A NOTIFICATION SEND THROUGH THE SCM */
		AppServiceCapability addServiceID = AppServiceFactory.createAppServiceCapability(AppServiceType.NAVIGATION, baseName, baseID, true, null);
		AppServicesCapabilities serviceIdASC = new AppServicesCapabilities();
		serviceIdASC.setAppServices(Collections.singletonList(addServiceID));

		SystemCapability systemCapability = new SystemCapability();
		systemCapability.setSystemCapabilityType(SystemCapabilityType.APP_SERVICES);
		systemCapability.setCapabilityForType(SystemCapabilityType.APP_SERVICES, serviceIdASC);

		OnSystemCapabilityUpdated onSystemCapabilityUpdated = new OnSystemCapabilityUpdated();
		onSystemCapabilityUpdated.setSystemCapability(systemCapability);

		scmRpcListener.onReceived(onSystemCapabilityUpdated);

		cachedCap = (AppServicesCapabilities)systemCapabilityManager.getCapability(SystemCapabilityType.APP_SERVICES);
		assertNotNull(cachedCap);

		assertTrue(cachedCap.getAppServices().get(0).getUpdatedAppServiceRecord().getServiceID().equals(baseID));

		appServicesCapabilities.updateAppServices(Collections.singletonList(addServiceID));
		assertTrue(serviceIdASC.getAppServices().get(0).getUpdatedAppServiceRecord().getServiceID().equalsIgnoreCase(appServicesCapabilities.getAppServices().get(0).getUpdatedAppServiceRecord().getServiceID()));

		assertEquals(cachedCap, appServicesCapabilities);


		/* PERFORM A NOTIFICATION SEND THROUGH AN UPDATED SERVICE NAME */
		AppServiceCapability newServiceName = AppServiceFactory.createAppServiceCapability(AppServiceType.NAVIGATION, "TestNav", baseID, true, null);
		AppServicesCapabilities newServiceNameASC = new AppServicesCapabilities();
		newServiceNameASC.setAppServices(Collections.singletonList(newServiceName));

		systemCapability = new SystemCapability();
		systemCapability.setSystemCapabilityType(SystemCapabilityType.APP_SERVICES);
		systemCapability.setCapabilityForType(SystemCapabilityType.APP_SERVICES, newServiceNameASC);

		onSystemCapabilityUpdated = new OnSystemCapabilityUpdated();
		onSystemCapabilityUpdated.setSystemCapability(systemCapability);

		scmRpcListener.onReceived(onSystemCapabilityUpdated);

		cachedCap = (AppServicesCapabilities)systemCapabilityManager.getCapability(SystemCapabilityType.APP_SERVICES);
		assertNotNull(cachedCap);
		assertEquals(cachedCap.getAppServices().size(), 1);


		/* PERFORM A NOTIFICATION SEND THROUGH THE SCM WITH DIFFERENT SERVICE */
		AppServiceCapability newService = AppServiceFactory.createAppServiceCapability(AppServiceType.NAVIGATION, "NewNav", "eeeeeeeee", false, null);
		AppServicesCapabilities newServiceASC = new AppServicesCapabilities();
		newServiceASC.setAppServices(Collections.singletonList(newService));

		systemCapability = new SystemCapability();
		systemCapability.setSystemCapabilityType(SystemCapabilityType.APP_SERVICES);
		systemCapability.setCapabilityForType(SystemCapabilityType.APP_SERVICES, newServiceASC);

		onSystemCapabilityUpdated = new OnSystemCapabilityUpdated();
		onSystemCapabilityUpdated.setSystemCapability(systemCapability);

		scmRpcListener.onReceived(onSystemCapabilityUpdated);

		cachedCap = (AppServicesCapabilities)systemCapabilityManager.getCapability(SystemCapabilityType.APP_SERVICES);
		assertNotNull(cachedCap);
		assertEquals(cachedCap.getAppServices().size(), 2);

		/* PERFORM A NOTIFICATION SEND THROUGH THE SCM WITH A REMOVED SERVICE */
		AppServiceCapability removedService = AppServiceFactory.createAppServiceCapability(AppServiceType.NAVIGATION, "NewNav", "eeeeeeeee", false, null);
		removedService.setUpdateReason(ServiceUpdateReason.REMOVED);
		AppServicesCapabilities removedServiceASC = new AppServicesCapabilities();
		removedServiceASC.setAppServices(Collections.singletonList(removedService));

		systemCapability = new SystemCapability();
		systemCapability.setSystemCapabilityType(SystemCapabilityType.APP_SERVICES);
		systemCapability.setCapabilityForType(SystemCapabilityType.APP_SERVICES, removedServiceASC);

		onSystemCapabilityUpdated = new OnSystemCapabilityUpdated();
		onSystemCapabilityUpdated.setSystemCapability(systemCapability);

		scmRpcListener.onReceived(onSystemCapabilityUpdated);

		cachedCap = (AppServicesCapabilities)systemCapabilityManager.getCapability(SystemCapabilityType.APP_SERVICES);
		assertNotNull(cachedCap);
		assertEquals(cachedCap.getAppServices().size(), 1);

	}


	@Test
	public void testOnSystemCapabilityUpdatedOverwrite(){
		InternalSDLInterface iSDL = new InternalSDLInterface();
		SystemCapabilityManager systemCapabilityManager = createSampleManager(iSDL);
		OnRPCListener scmRpcListener = iSDL.rpcListeners.get(FunctionID.ON_SYSTEM_CAPABILITY_UPDATED.getId()).get(0);
		assertNotNull(scmRpcListener);
		systemCapabilityManager.setCapability(SystemCapabilityType.PHONE_CALL, TestValues.GENERAL_PHONECAPABILITY);

		PhoneCapability phoneCapability = (PhoneCapability)systemCapabilityManager.getCapability(SystemCapabilityType.PHONE_CALL);
		assertNotNull(phoneCapability);
		assertEquals(phoneCapability, TestValues.GENERAL_PHONECAPABILITY);

		phoneCapability.setDialNumberEnabled(!TestValues.GENERAL_PHONECAPABILITY.getDialNumberEnabled()); //Flip it
		SystemCapability systemCapability = new SystemCapability();
		systemCapability.setSystemCapabilityType(SystemCapabilityType.PHONE_CALL);
		systemCapability.setCapabilityForType(SystemCapabilityType.PHONE_CALL, phoneCapability);
		OnSystemCapabilityUpdated onSystemCapabilityUpdated = new OnSystemCapabilityUpdated();
		onSystemCapabilityUpdated.setSystemCapability(systemCapability);

		scmRpcListener.onReceived(onSystemCapabilityUpdated);

		PhoneCapability phoneCapabilityUpdated = (PhoneCapability)systemCapabilityManager.getCapability(SystemCapabilityType.PHONE_CALL);
		assertNotNull(phoneCapabilityUpdated);
		assertFalse(phoneCapabilityUpdated.getDialNumberEnabled());
		assertEquals(phoneCapability, phoneCapabilityUpdated);
	}

	@Test
	public void testOnSetDisplayLayout() {
		InternalSDLInterface iSDL = new InternalSDLInterface();
		SystemCapabilityManager systemCapabilityManager = createSampleManager(iSDL);
		OnRPCListener dlRpcListener = iSDL.rpcListeners.get(FunctionID.SET_DISPLAY_LAYOUT.getId()).get(0);
		assertNotNull(dlRpcListener);

		SetDisplayLayoutResponse newLayout = new SetDisplayLayoutResponse();
		newLayout.setDisplayCapabilities(TestValues.GENERAL_DISPLAYCAPABILITIES);
		newLayout.setButtonCapabilities(TestValues.GENERAL_BUTTONCAPABILITIES_LIST);
		newLayout.setSoftButtonCapabilities(TestValues.GENERAL_SOFTBUTTONCAPABILITIES_LIST);
		newLayout.setPresetBankCapabilities(TestValues.GENERAL_PRESETBANKCAPABILITIES);
		newLayout.setSuccess(true);
		newLayout.setResultCode(Result.SUCCESS);

		dlRpcListener.onReceived(newLayout);


		DisplayCapabilities appliedCaps = (DisplayCapabilities)systemCapabilityManager.getCapability(SystemCapabilityType.DISPLAY);
		assertNotNull(appliedCaps);
		assertTrue(Validator.validateDisplayCapabilities(newLayout.getDisplayCapabilities(), appliedCaps));

		List<DisplayCapability> convertedCaps = (List<DisplayCapability>)systemCapabilityManager.getCapability(SystemCapabilityType.DISPLAYS);
		assertNotNull(convertedCaps);
		List<DisplayCapability> testCaps = createDisplayCapabilityList(newLayout.getDisplayCapabilities(), newLayout.getButtonCapabilities(), newLayout.getSoftButtonCapabilities());
		assertTrue(Validator.validateDisplayCapabilityList(convertedCaps, testCaps));

		WindowCapability matchWindowCapability = testCaps.get(0).getWindowCapabilities().get(0);
		WindowCapability testWindowCapability = systemCapabilityManager.getDefaultMainWindowCapability();
		assertTrue(Validator.validateWindowCapability(matchWindowCapability, testWindowCapability));
		assertNull(systemCapabilityManager.getWindowCapability(42));
	}

	@Test
	public void testManagerBeforeDisplayUpdate() {
		InternalSDLInterface iSDL = new InternalSDLInterface();
		SystemCapabilityManager systemCapabilityManager = new SystemCapabilityManager(iSDL);
		assertNull(systemCapabilityManager.getDefaultMainWindowCapability());
		assertNull(systemCapabilityManager.getWindowCapability(PredefinedWindows.DEFAULT_WINDOW.getValue()));
		assertNull(systemCapabilityManager.getWindowCapability(PredefinedWindows.PRIMARY_WIDGET.getValue()));
	}

	private class InternalSDLInterface implements ISdl{
		private final Object RPC_LISTENER_LOCK = new Object();
		SparseArray<CopyOnWriteArrayList<OnRPCListener>> rpcListeners = new SparseArray<>();

		@Override
		public void start(){}

		@Override
		public void stop() {}

		@Override
		public boolean isConnected() {return false;	}

		@Override
		public void addServiceListener(SessionType serviceType, ISdlServiceListener sdlServiceListener) {}

		@Override
		public void removeServiceListener(SessionType serviceType, ISdlServiceListener sdlServiceListener) {}

		@Override
		public void startVideoService(VideoStreamingParameters parameters, boolean encrypted) {	}

		@Override
		public void stopVideoService() {}

		@Override
		public void stopAudioService() {}

		@Override
		public void sendRPCRequest(RPCRequest message) {}

		@Override
		public void sendRPC(RPCMessage message) {}

		@Override
		public void sendRequests(List<? extends RPCRequest> rpcs, OnMultipleRequestListener listener) {

		}

		@Override
		public void sendRPCs(List<? extends RPCMessage> rpcs, OnMultipleRequestListener listener) {

		}

		@Override
		public void sendSequentialRPCs(List<? extends RPCMessage> rpcs, OnMultipleRequestListener listener) {

		}

		@Override
		public void addOnRPCNotificationListener(FunctionID notificationId, OnRPCNotificationListener listener) {

		}

		@Override
		public boolean removeOnRPCNotificationListener(FunctionID notificationId, OnRPCNotificationListener listener) {return false;}

		@Override
		public void addOnRPCRequestListener(FunctionID functionID, OnRPCRequestListener listener) {

		}

		@Override
		public boolean removeOnRPCRequestListener(FunctionID functionID, OnRPCRequestListener listener) {
			return false;
		}
		@Override
		public void addOnRPCListener(FunctionID messageId, OnRPCListener listener){
			synchronized(RPC_LISTENER_LOCK){
				if(messageId != null && listener != null){
					if(rpcListeners.indexOfKey(messageId.getId()) < 0 ){
						rpcListeners.put(messageId.getId(),new CopyOnWriteArrayList<OnRPCListener>());
					}
					rpcListeners.get(messageId.getId()).add(listener);
				}
			}
		}
		@Override
		public boolean removeOnRPCListener(FunctionID messageId, OnRPCListener listener){
			synchronized(RPC_LISTENER_LOCK){
				if(rpcListeners!= null
						&& messageId != null
						&& listener != null
						&& rpcListeners.indexOfKey(messageId.getId()) >= 0){
					return rpcListeners.get(messageId.getId()).remove(listener);
				}
			}
			return false;
		}

		@Override
		public Object getCapability(SystemCapabilityType systemCapabilityType){return null;}

		@Override
		public void getCapability(SystemCapabilityType systemCapabilityType, OnSystemCapabilityListener scListener) {
		}

		@Override
		public RegisterAppInterfaceResponse getRegisterAppInterfaceResponse() {
			return null;
		}

		@Override
		public Object getCapability(SystemCapabilityType systemCapabilityType, OnSystemCapabilityListener scListener, boolean forceUpdate) {
			return null;
		}

		@Override
		public SdlMsgVersion getSdlMsgVersion() {
			return null;
		}

		@Override
		public Version getProtocolVersion() {
			return new Version(1,0,0);
		}


		@Override
		public boolean isCapabilitySupported(SystemCapabilityType systemCapabilityType){
			return false;
		}

		@Override
		public void addOnSystemCapabilityListener(SystemCapabilityType systemCapabilityType, OnSystemCapabilityListener listener) { }

		@Override
		public boolean removeOnSystemCapabilityListener(SystemCapabilityType systemCapabilityType, OnSystemCapabilityListener listener) { return false; }

		@Override
		public boolean isTransportForServiceAvailable(SessionType serviceType) {
			return false;
		}

		@Override
		public void startAudioService(boolean isEncrypted, AudioStreamingCodec codec,
									  AudioStreamingParams params) {}

		@Override
		public IVideoStreamListener startVideoStream(boolean isEncrypted, VideoStreamingParameters parameters){
			return null;
		}

		@Override
		public IAudioStreamListener startAudioStream(boolean isEncrypted, AudioStreamingCodec codec,
													 AudioStreamingParams params) {
			return null;
		}

		@Override
		public void startAudioService(boolean encrypted){}

		@Override
		public void startRPCEncryption() {}

		@Override
		public Taskmaster getTaskmaster() {
			return null;
		}
	}
}
