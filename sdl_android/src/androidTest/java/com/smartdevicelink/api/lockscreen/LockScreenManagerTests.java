package com.smartdevicelink.api.lockscreen;

import android.content.Context;
import android.test.AndroidTestCase;
import android.test.mock.MockContext;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.interfaces.IAudioStreamListener;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.interfaces.ISdlServiceListener;
import com.smartdevicelink.proxy.interfaces.IVideoStreamListener;
import com.smartdevicelink.proxy.interfaces.OnSystemCapabilityListener;
import com.smartdevicelink.proxy.rpc.ListFiles;
import com.smartdevicelink.proxy.rpc.ListFilesResponse;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.enums.LockScreenStatus;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.streaming.audio.AudioStreamingCodec;
import com.smartdevicelink.streaming.audio.AudioStreamingParams;
import com.smartdevicelink.streaming.video.VideoStreamingParameters;
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library manager class :
 * {@link com.smartdevicelink.api.SdlManager}
 */
public class LockScreenManagerTests extends AndroidTestCase {

	private LockScreenManager lockScreenManager;
	private LockScreenConfig lockScreenConfig;

	protected class BaseISdl implements ISdl {
		@Override
		public void start() {

		}

		@Override
		public void stop() {

		}

		@Override
		public boolean isConnected() {
			return false;
		}

		@Override
		public void addServiceListener(SessionType serviceType, ISdlServiceListener sdlServiceListener) {

		}

		@Override
		public void removeServiceListener(SessionType serviceType, ISdlServiceListener sdlServiceListener) {

		}

		@Override
		public void startVideoService(VideoStreamingParameters parameters, boolean encrypted) {

		}

		@Override
		public void stopVideoService() {

		}

		@Override
		public IVideoStreamListener startVideoStream(boolean isEncrypted, VideoStreamingParameters parameters) {
			return null;
		}

		@Override
		public void startAudioService(boolean encrypted, AudioStreamingCodec codec, AudioStreamingParams params) {

		}

		@Override
		public void startAudioService(boolean encrypted) {

		}

		@Override
		public void stopAudioService() {

		}

		@Override
		public IAudioStreamListener startAudioStream(boolean isEncrypted, AudioStreamingCodec codec, AudioStreamingParams params) {
			return null;
		}

		@Override
		public void sendRPCRequest(RPCRequest message) {
			// Allow for successful initialization by default
			if(message instanceof ListFiles){
				int correlationId = message.getCorrelationID();
				ListFilesResponse listFilesResponse = new ListFilesResponse();
				listFilesResponse.setFilenames(Test.GENERAL_STRING_LIST);
				listFilesResponse.setSpaceAvailable(Test.GENERAL_INT);
				listFilesResponse.setSuccess(true);
				message.getOnRPCResponseListener().onResponse(correlationId, listFilesResponse);
			}
		}

		@Override
		public void addOnRPCNotificationListener(FunctionID notificationId, OnRPCNotificationListener listener) {

		}

		@Override
		public boolean removeOnRPCNotificationListener(FunctionID notificationId, OnRPCNotificationListener listener) {
			return false;
		}

		@Override
		public Object getCapability(SystemCapabilityType systemCapabilityType) {
			return null;
		}

		@Override
		public void getCapability(SystemCapabilityType systemCapabilityType, OnSystemCapabilityListener scListener) {

		}

		@Override
		public boolean isCapabilitySupported(SystemCapabilityType systemCapabilityType) {
			return false;
		}

		@Override
		public SdlMsgVersion getSdlMsgVersion() {
			return null;
		}
	};

	@Override
	public void setUp() throws Exception{
		super.setUp();

		Context context = new MockContext();
		// create config
		lockScreenConfig = new LockScreenConfig();
		lockScreenConfig.setCustomView(Test.GENERAL_INT);
		lockScreenConfig.setAppIcon(Test.GENERAL_INT);
		lockScreenConfig.setBackgroundColor(Test.GENERAL_INT);
		lockScreenConfig.setShowOEMLogo(true);
		lockScreenConfig.setEnabled(true);

		lockScreenManager = new LockScreenManager(lockScreenConfig, context, new BaseISdl());
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	public void testVariables() {
		assertEquals(Test.GENERAL_INT, lockScreenManager.getCustomView());
		assertEquals(Test.GENERAL_INT, lockScreenManager.getLockScreenIcon());
		assertEquals(Test.GENERAL_INT, lockScreenManager.getLockscreenColor());
		assertEquals(true, lockScreenManager.getShowOEMLogo());
		assertEquals(true, lockScreenManager.getLockScreenEnabled());
		assertNull(lockScreenManager.getLockScreenOEMIcon());
	}

	public void testGetLockScreenStatus(){
		assertEquals(LockScreenStatus.OFF, lockScreenManager.testGetLockScreenStatus());
	}

}
