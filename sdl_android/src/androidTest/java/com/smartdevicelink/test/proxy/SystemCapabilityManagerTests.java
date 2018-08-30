package com.smartdevicelink.test.proxy;

import android.test.AndroidTestCase;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.SystemCapabilityManager;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.interfaces.ISdlServiceListener;
import com.smartdevicelink.proxy.interfaces.OnSystemCapabilityListener;
import com.smartdevicelink.proxy.rpc.AudioPassThruCapabilities;
import com.smartdevicelink.proxy.rpc.ButtonCapabilities;
import com.smartdevicelink.proxy.rpc.DisplayCapabilities;
import com.smartdevicelink.proxy.rpc.GetSystemCapabilityResponse;
import com.smartdevicelink.proxy.rpc.HMICapabilities;
import com.smartdevicelink.proxy.rpc.PresetBankCapabilities;
import com.smartdevicelink.proxy.rpc.RegisterAppInterfaceResponse;
import com.smartdevicelink.proxy.rpc.SoftButtonCapabilities;
import com.smartdevicelink.proxy.rpc.SystemCapability;
import com.smartdevicelink.proxy.rpc.VideoStreamingCapability;
import com.smartdevicelink.proxy.rpc.enums.HmiZoneCapabilities;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.streaming.video.VideoStreamingParameters;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.util.CorrelationIdGenerator;

import java.util.List;

public class SystemCapabilityManagerTests extends AndroidTestCase {
	public static final String TAG = "SystemCapabilityManagerTests";
	public static SystemCapabilityManager systemCapabilityManager;

	@Override
	protected void setUp() throws Exception{
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public SystemCapabilityManager createSampleManager(){
		SystemCapabilityManager systemCapabilityManager = new SystemCapabilityManager(new InternalSDLInterface());

		RegisterAppInterfaceResponse raiResponse = new RegisterAppInterfaceResponse();

		raiResponse.setHmiCapabilities(Test.GENERAL_HMICAPABILITIES);
		raiResponse.setDisplayCapabilities(Test.GENERAL_DISPLAYCAPABILITIES);
		raiResponse.setAudioPassThruCapabilities(Test.GENERAL_AUDIOPASSTHRUCAPABILITIES_LIST);
		raiResponse.setButtonCapabilities(Test.GENERAL_BUTTONCAPABILITIES_LIST);
		raiResponse.setHmiZoneCapabilities(Test.GENERAL_HMIZONECAPABILITIES_LIST);
		raiResponse.setPresetBankCapabilities(Test.GENERAL_PRESETBANKCAPABILITIES);
		raiResponse.setSoftButtonCapabilities(Test.GENERAL_SOFTBUTTONCAPABILITIES_LIST);
		raiResponse.setSpeechCapabilities(Test.GENERAL_SPEECHCAPABILITIES_LIST);
		raiResponse.setSuccess(true);

		systemCapabilityManager.parseRAIResponse(raiResponse);
		return systemCapabilityManager;
	}

	public void testParseRAI() {
		systemCapabilityManager = createSampleManager();

		assertTrue(Test.TRUE,
				Validator.validateHMICapabilities(Test.GENERAL_HMICAPABILITIES, (HMICapabilities) systemCapabilityManager.getCapability(SystemCapabilityType.HMI)));
		assertTrue(Test.TRUE,
				Validator.validateDisplayCapabilities(Test.GENERAL_DISPLAYCAPABILITIES, (DisplayCapabilities) systemCapabilityManager.getCapability(SystemCapabilityType.DISPLAY)));
		assertTrue(Test.TRUE,
				Validator.validateAudioPassThruCapabilities(Test.GENERAL_AUDIOPASSTHRUCAPABILITIES_LIST, (List<AudioPassThruCapabilities>) systemCapabilityManager.getCapability(SystemCapabilityType.AUDIO_PASSTHROUGH)));
		assertTrue(Test.TRUE,
				Validator.validateButtonCapabilities(Test.GENERAL_BUTTONCAPABILITIES_LIST, (List<ButtonCapabilities> )systemCapabilityManager.getCapability(SystemCapabilityType.BUTTON)));
		assertTrue(Test.TRUE,
				Validator.validateHMIZoneCapabilities(Test.GENERAL_HMIZONECAPABILITIES_LIST, (List<HmiZoneCapabilities>) systemCapabilityManager.getCapability(SystemCapabilityType.HMI_ZONE)));
		assertTrue(Test.TRUE,
				Validator.validatePresetBankCapabilities(Test.GENERAL_PRESETBANKCAPABILITIES, (PresetBankCapabilities) systemCapabilityManager.getCapability(SystemCapabilityType.PRESET_BANK)));
		assertTrue(Test.TRUE,
				Validator.validateSoftButtonCapabilities(Test.GENERAL_SOFTBUTTONCAPABILITIES_LIST, (List<SoftButtonCapabilities>) systemCapabilityManager.getCapability(SystemCapabilityType.SOFTBUTTON)));
		assertTrue(Test.TRUE,
				Validator.validateSpeechCapabilities(Test.GENERAL_SPEECHCAPABILITIES_LIST, (List<SpeechCapabilities>) systemCapabilityManager.getCapability(SystemCapabilityType.SPEECH)));
	}

	public void testGetVSCapability(){
		VideoStreamingCapability vsCapability = new VideoStreamingCapability();
		vsCapability.setMaxBitrate(Test.GENERAL_INT);
		vsCapability.setPreferredResolution(Test.GENERAL_IMAGERESOLUTION);
		vsCapability.setSupportedFormats(Test.GENERAL_VIDEOSTREAMINGFORMAT_LIST);

		SystemCapability cap = new SystemCapability();
		cap.setSystemCapabilityType(SystemCapabilityType.VIDEO_STREAMING);
		cap.setCapabilityForType(SystemCapabilityType.VIDEO_STREAMING, vsCapability);

		final SystemCapability referenceCapability = cap;

		systemCapabilityManager = new SystemCapabilityManager(new InternalSDLInterface() {
			@Override
			public void sendRPCRequest(RPCRequest message) {
				GetSystemCapabilityResponse response = new GetSystemCapabilityResponse();
				response.setSystemCapability(referenceCapability);
				response.setSuccess(true);
				message.getOnRPCResponseListener().onResponse(CorrelationIdGenerator.generateId(), response);
			}
		});

		systemCapabilityManager.getCapability(SystemCapabilityType.VIDEO_STREAMING, new OnSystemCapabilityListener() {
			@Override
			public void onCapabilityRetrieved(Object capability) {
				assertTrue(Test.TRUE,
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

	public void testListConversion(){
		SystemCapabilityManager systemCapabilityManager = createSampleManager();
		Object capability = systemCapabilityManager.getCapability(SystemCapabilityType.SOFTBUTTON);
		assertNotNull(capability);
		List<SoftButtonCapabilities> list = SystemCapabilityManager.convertToList(capability, SoftButtonCapabilities.class);
		assertNotNull(list);

	}

	public void testFalsePositive(){
		SystemCapabilityManager systemCapabilityManager = createSampleManager();
		systemCapabilityManager.setCapability(SystemCapabilityType.AUDIO_PASSTHROUGH, null);
		assertFalse(systemCapabilityManager.isCapabilitySupported(SystemCapabilityType.AUDIO_PASSTHROUGH));
	}

	private class InternalSDLInterface implements ISdl{
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
		public void startAudioService(boolean encrypted) {}

		@Override
		public void stopAudioService() {}

		@Override
		public void sendRPCRequest(RPCRequest message) {}

		@Override
		public void addOnRPCNotificationListener(FunctionID notificationId, OnRPCNotificationListener listener) {}

		@Override
		public boolean removeOnRPCNotificationListener(FunctionID notificationId, OnRPCNotificationListener listener) {return false;}
	}


}
