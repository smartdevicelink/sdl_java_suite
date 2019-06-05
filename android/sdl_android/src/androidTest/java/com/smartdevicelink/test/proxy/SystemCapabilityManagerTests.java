package com.smartdevicelink.test.proxy;

import android.util.SparseArray;

import com.smartdevicelink.AndroidTestCase2;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.SystemCapabilityManager;
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
import com.smartdevicelink.proxy.rpc.GetSystemCapabilityResponse;
import com.smartdevicelink.proxy.rpc.HMICapabilities;
import com.smartdevicelink.proxy.rpc.OnSystemCapabilityUpdated;
import com.smartdevicelink.proxy.rpc.PhoneCapability;
import com.smartdevicelink.proxy.rpc.PresetBankCapabilities;
import com.smartdevicelink.proxy.rpc.RegisterAppInterfaceResponse;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.SoftButtonCapabilities;
import com.smartdevicelink.proxy.rpc.SystemCapability;
import com.smartdevicelink.proxy.rpc.VideoStreamingCapability;
import com.smartdevicelink.proxy.rpc.enums.AppServiceType;
import com.smartdevicelink.proxy.rpc.enums.HmiZoneCapabilities;
import com.smartdevicelink.proxy.rpc.enums.ServiceUpdateReason;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;
import com.smartdevicelink.proxy.rpc.listeners.OnMultipleRequestListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCRequestListener;
import com.smartdevicelink.streaming.audio.AudioStreamingCodec;
import com.smartdevicelink.streaming.audio.AudioStreamingParams;
import com.smartdevicelink.streaming.video.VideoStreamingParameters;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.utl.AppServiceFactory;
import com.smartdevicelink.util.CorrelationIdGenerator;
import com.smartdevicelink.util.Version;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SystemCapabilityManagerTests extends AndroidTestCase2 {
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
		return createSampleManager(new InternalSDLInterface());
	}

	public SystemCapabilityManager createSampleManager(InternalSDLInterface iSdl){
		SystemCapabilityManager systemCapabilityManager = new SystemCapabilityManager(iSdl);

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


	public void testOnSystemCapabilityUpdatedOverwrite(){
		InternalSDLInterface iSDL = new InternalSDLInterface();
		SystemCapabilityManager systemCapabilityManager = createSampleManager(iSDL);
		OnRPCListener scmRpcListener = iSDL.rpcListeners.get(FunctionID.ON_SYSTEM_CAPABILITY_UPDATED.getId()).get(0);
		assertNotNull(scmRpcListener);
		systemCapabilityManager.setCapability(SystemCapabilityType.PHONE_CALL, Test.GENERAL_PHONECAPABILITY);

		PhoneCapability phoneCapability = (PhoneCapability)systemCapabilityManager.getCapability(SystemCapabilityType.PHONE_CALL);
		assertNotNull(phoneCapability);
		assertEquals(phoneCapability, Test.GENERAL_PHONECAPABILITY);

		phoneCapability.setDialNumberEnabled(!Test.GENERAL_PHONECAPABILITY.getDialNumberEnabled()); //Flip it
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
		public void getCapability(SystemCapabilityType systemCapabilityType, OnSystemCapabilityListener scListener) { }

		@Override
		public SdlMsgVersion getSdlMsgVersion(){
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

	}


}
