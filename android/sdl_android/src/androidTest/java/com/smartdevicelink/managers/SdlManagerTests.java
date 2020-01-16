package com.smartdevicelink.managers;

import android.content.Context;

import com.smartdevicelink.AndroidTestCase2;
import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.managers.lifecycle.LifecycleConfigurationUpdate;
import com.smartdevicelink.managers.lockscreen.LockScreenConfig;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.SdlProxyBase;
import com.smartdevicelink.proxy.rpc.GetAppServiceDataResponse;
import com.smartdevicelink.proxy.rpc.GetVehicleData;
import com.smartdevicelink.proxy.rpc.OnAppServiceData;
import com.smartdevicelink.proxy.rpc.Show;
import com.smartdevicelink.proxy.rpc.TemplateColorScheme;
import com.smartdevicelink.proxy.rpc.enums.AppHMIType;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.listeners.OnMultipleRequestListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.transport.BaseTransportConfig;
import com.smartdevicelink.transport.TCPTransportConfig;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

/**
 * This is a unit test class for the SmartDeviceLink library manager class :
 * {@link com.smartdevicelink.managers.SdlManager}
 */
public class SdlManagerTests extends AndroidTestCase2 {

	public static BaseTransportConfig transport = null;
	private Context mTestContext;
	private Vector<AppHMIType> appType;
	private TemplateColorScheme templateColorScheme;
	private int listenerCalledCounter;
	private SdlManager sdlManager;
	private SdlProxyBase sdlProxyBase;

	// transport related
	@SuppressWarnings("FieldCanBeLocal")
	private int TCP_PORT = 12345;
	@SuppressWarnings("FieldCanBeLocal")
	private String DEV_MACHINE_IP_ADDRESS = "0.0.0.0";

	@Override
	public void setUp() throws Exception{
		super.setUp();

		// set transport
		transport = new TCPTransportConfig(TCP_PORT, DEV_MACHINE_IP_ADDRESS, true);

		// add AppTypes
		appType = new Vector<>();
		appType.add(AppHMIType.DEFAULT);

		// Color Scheme
		templateColorScheme = new TemplateColorScheme();
		templateColorScheme.setBackgroundColor(Test.GENERAL_RGBCOLOR);
		templateColorScheme.setPrimaryColor(Test.GENERAL_RGBCOLOR);
		templateColorScheme.setSecondaryColor(Test.GENERAL_RGBCOLOR);

		sdlManager = createSampleManager("heyApp", "123456", Test.GENERAL_LOCKSCREENCONFIG);
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	// SETUP / HELPERS

	private Context getTestContext() {
		return mTestContext;
	}

	private SdlManager createSampleManager(String appName, String appId, LockScreenConfig lockScreenConfig){
		SdlManager manager;

		SdlManagerListener listener = new SdlManagerListener() {
			@Override
			public void onStart() {
				listenerCalledCounter++;
			}

			@Override
			public void onDestroy() {

			}

			@Override
			public void onError(String info, Exception e) {

			}

			@Override
			public LifecycleConfigurationUpdate managerShouldUpdateLifecycle(Language language, Language hmiLanguage){
				return null;
			}
		};

		// build manager object - use all setters, will test using getters below
		SdlManager.Builder builder = new SdlManager.Builder(getTestContext(),appId,appName,listener);
		builder.setShortAppName(appName);
		builder.setAppTypes(appType);
		builder.setTransportType(transport);
		builder.setLanguage(Language.EN_US);
		builder.setHMILanguage(Language.EN_US);
		builder.setDayColorScheme(templateColorScheme);
		builder.setNightColorScheme(templateColorScheme);
		builder.setVrSynonyms(Test.GENERAL_VECTOR_STRING);
		builder.setTtsName(Test.GENERAL_VECTOR_TTS_CHUNKS);
		builder.setLockScreenConfig(lockScreenConfig);
		builder.setMinimumProtocolVersion(Test.GENERAL_VERSION);
		builder.setMinimumRPCVersion(Test.GENERAL_VERSION);
		manager = builder.build();

		// mock SdlProxyBase and set it manually
		sdlProxyBase = mock(SdlProxyBase.class);
		manager.setProxy(sdlProxyBase);

		return manager;
	}

	// TESTS

	public void testNotNull(){
		assertNotNull(createSampleManager("app","123456", Test.GENERAL_LOCKSCREENCONFIG));
	}

	public void testMissingAppName() {
		try {
			createSampleManager(null,"123456", Test.GENERAL_LOCKSCREENCONFIG);
		} catch (IllegalArgumentException ex) {
			assertSame(ex.getMessage(), "You must specify an app name by calling setAppName");
		}
	}

	public void testMissingAppId() {
		try {
			createSampleManager("app",null, Test.GENERAL_LOCKSCREENCONFIG);
		} catch (IllegalArgumentException ex) {
			assertSame(ex.getMessage(), "You must specify an app ID by calling setAppId");
		}
	}

	public void testManagerSetters() {
		assertEquals("123456", sdlManager.getAppId());
		assertEquals("heyApp", sdlManager.getAppName());
		assertEquals("heyApp", sdlManager.getShortAppName());
		assertEquals(appType, sdlManager.getAppTypes());
		assertEquals(Language.EN_US, sdlManager.getHmiLanguage());
		assertEquals(Language.EN_US, sdlManager.getLanguage());
		assertEquals(transport, sdlManager.getTransport());
		assertEquals(templateColorScheme, sdlManager.getDayColorScheme());
		assertEquals(templateColorScheme, sdlManager.getNightColorScheme());
		assertEquals(Test.GENERAL_VECTOR_STRING, sdlManager.getVrSynonyms());
		assertEquals(Test.GENERAL_VECTOR_TTS_CHUNKS, sdlManager.getTtsChunks());
		assertEquals(Test.GENERAL_LOCKSCREENCONFIG, sdlManager.getLockScreenConfig());
		assertEquals(Test.GENERAL_VERSION, sdlManager.getMinimumProtocolVersion());
		assertEquals(Test.GENERAL_VERSION, sdlManager.getMinimumRPCVersion());
	}

	public void testStartingManager(){
		listenerCalledCounter = 0;

		sdlManager.start();

		// Create and force all sub managers to be ready manually. Because SdlManager will not start until all sub managers are ready.
		// Note: SdlManager.initialize() will not be called automatically by proxy as in real life because we have mock proxy not a real one
		sdlManager.initialize();

		// Set all sub managers' states to ready
		sdlManager.getPermissionManager().transitionToState(BaseSubManager.READY);
		sdlManager.getFileManager().transitionToState(BaseSubManager.READY);
		sdlManager.getScreenManager().transitionToState(BaseSubManager.READY);
		sdlManager.getLockScreenManager().transitionToState(BaseSubManager.READY);

		// Make sure the listener is called exactly once
		assertEquals("Listener was not called or called more/less frequently than expected", listenerCalledCounter, 1);
	}

	public void testManagerStates() {
		SdlManager sdlManager = createSampleManager("test", "00000", new LockScreenConfig());
		sdlManager.initialize();


		// Case 1-A:
		sdlManager.getPermissionManager().transitionToState(BaseSubManager.READY);
		sdlManager.getFileManager().transitionToState(BaseSubManager.READY);
		sdlManager.getScreenManager().transitionToState(BaseSubManager.READY);
		sdlManager.getLockScreenConfig().setEnabled(true);
		sdlManager.getLockScreenManager().transitionToState(BaseSubManager.READY);
		sdlManager.checkState();
		assertEquals(BaseSubManager.READY, sdlManager.getState());


		// Case 1-B:
		sdlManager.getPermissionManager().transitionToState(BaseSubManager.READY);
		sdlManager.getFileManager().transitionToState(BaseSubManager.READY);
		sdlManager.getScreenManager().transitionToState(BaseSubManager.READY);
		sdlManager.getLockScreenConfig().setEnabled(false);
		sdlManager.getLockScreenManager().transitionToState(BaseSubManager.SETTING_UP);
		sdlManager.checkState();
		assertEquals(BaseSubManager.READY, sdlManager.getState());


		// Case 2-A:
		sdlManager.getPermissionManager().transitionToState(BaseSubManager.ERROR);
		sdlManager.getFileManager().transitionToState(BaseSubManager.ERROR);
		sdlManager.getScreenManager().transitionToState(BaseSubManager.ERROR);
		sdlManager.getLockScreenConfig().setEnabled(true);
		sdlManager.getLockScreenManager().transitionToState(BaseSubManager.ERROR);
		sdlManager.checkState();
		assertEquals(BaseSubManager.ERROR, sdlManager.getState());


		// Case 1-B:
		sdlManager.getPermissionManager().transitionToState(BaseSubManager.ERROR);
		sdlManager.getFileManager().transitionToState(BaseSubManager.ERROR);
		sdlManager.getScreenManager().transitionToState(BaseSubManager.ERROR);
		sdlManager.getLockScreenConfig().setEnabled(false);
		sdlManager.getLockScreenManager().transitionToState(BaseSubManager.SETTING_UP);
		sdlManager.checkState();
		assertEquals(BaseSubManager.ERROR, sdlManager.getState());


		// Case 3-A:
		sdlManager.getPermissionManager().transitionToState(BaseSubManager.ERROR);
		sdlManager.getFileManager().transitionToState(BaseSubManager.READY);
		sdlManager.getScreenManager().transitionToState(BaseSubManager.SETTING_UP);
		sdlManager.getLockScreenConfig().setEnabled(true);
		sdlManager.getLockScreenManager().transitionToState(BaseSubManager.LIMITED);
		sdlManager.checkState();
		assertEquals(BaseSubManager.SETTING_UP, sdlManager.getState());


		// Case 3-B:
		sdlManager.getPermissionManager().transitionToState(BaseSubManager.ERROR);
		sdlManager.getFileManager().transitionToState(BaseSubManager.READY);
		sdlManager.getScreenManager().transitionToState(BaseSubManager.SETTING_UP);
		sdlManager.getLockScreenConfig().setEnabled(false);
		sdlManager.getLockScreenManager().transitionToState(BaseSubManager.SETTING_UP);
		sdlManager.checkState();
		assertEquals(BaseSubManager.SETTING_UP, sdlManager.getState());


		// Case 4-A:
		sdlManager.getPermissionManager().transitionToState(BaseSubManager.READY);
		sdlManager.getFileManager().transitionToState(BaseSubManager.ERROR);
		sdlManager.getScreenManager().transitionToState(BaseSubManager.READY);
		sdlManager.getLockScreenConfig().setEnabled(true);
		sdlManager.getLockScreenManager().transitionToState(BaseSubManager.READY);
		sdlManager.checkState();
		assertEquals(BaseSubManager.LIMITED, sdlManager.getState());


		// Case 4-B:
		sdlManager.getPermissionManager().transitionToState(BaseSubManager.READY);
		sdlManager.getFileManager().transitionToState(BaseSubManager.ERROR);
		sdlManager.getScreenManager().transitionToState(BaseSubManager.READY);
		sdlManager.getLockScreenConfig().setEnabled(false);
		sdlManager.getLockScreenManager().transitionToState(BaseSubManager.SETTING_UP);
		sdlManager.checkState();
		assertEquals(BaseSubManager.LIMITED, sdlManager.getState());


		// Case 5-A:
		sdlManager.getPermissionManager().transitionToState(BaseSubManager.READY);
		sdlManager.getFileManager().transitionToState(BaseSubManager.LIMITED);
		sdlManager.getScreenManager().transitionToState(BaseSubManager.ERROR);
		sdlManager.getLockScreenConfig().setEnabled(true);
		sdlManager.getLockScreenManager().transitionToState(BaseSubManager.READY);
		sdlManager.checkState();
		assertEquals(BaseSubManager.LIMITED, sdlManager.getState());


		// Case 5-B:
		sdlManager.getPermissionManager().transitionToState(BaseSubManager.READY);
		sdlManager.getFileManager().transitionToState(BaseSubManager.LIMITED);
		sdlManager.getScreenManager().transitionToState(BaseSubManager.ERROR);
		sdlManager.getLockScreenConfig().setEnabled(false);
		sdlManager.getLockScreenManager().transitionToState(BaseSubManager.SETTING_UP);
		sdlManager.checkState();
		assertEquals(BaseSubManager.LIMITED, sdlManager.getState());


		// Case 6
		sdlManager.dispose();
		assertEquals(BaseSubManager.SHUTDOWN, sdlManager.getState());
	}

	public void testSendRPC(){
		listenerCalledCounter = 0;

		// When sdlProxyBase.sendRPCRequest() is called, create a fake success response
		Answer<Void> answer = new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				RPCRequest request = (RPCRequest) args[0];
				RPCResponse response = new RPCResponse(FunctionID.GET_VEHICLE_DATA.toString());
				response.setSuccess(true);
				request.getOnRPCResponseListener().onResponse(0, response);
				return null;
			}
		};
		try {
			doAnswer(answer).when(sdlProxyBase).sendRPC(any(RPCMessage.class));
		} catch (SdlException e) {
			e.printStackTrace();
		}


		// Test send RPC request
		final GetVehicleData request = new GetVehicleData();
		request.setGps(true);
		request.setOnRPCResponseListener(new OnRPCResponseListener() {
			@Override
			public void onResponse(int correlationId, RPCResponse response) {
				assertTrue(response.getSuccess());
				listenerCalledCounter++;
			}
		});

		sdlManager.sendRPC(request);

		// Make sure the listener is called exactly once
		assertEquals("Listener was not called or called more/less frequently than expected", listenerCalledCounter, 1);
	}

	public void testSendRPCs(){
		testSendMultipleRPCs(false);
	}

	public void testSendSequentialRPCs(){
		testSendMultipleRPCs(true);
	}

	private void testSendMultipleRPCs(boolean sequentialSend){
		listenerCalledCounter = 0;

		// When sdlProxyBase.sendRPCRequests() is called, call listener.onFinished() to fake the response
		final Answer<Void> answer = new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				OnMultipleRequestListener listener = (OnMultipleRequestListener) args[1];
				listener.onFinished();
				return null;
			}
		};
		try {
			if (sequentialSend){
				doAnswer(answer).when(sdlProxyBase).sendSequentialRequests(any(List.class), any(OnMultipleRequestListener.class));

			} else {
				doAnswer(answer).when(sdlProxyBase).sendRequests(any(List.class), any(OnMultipleRequestListener.class));
			}
		} catch (SdlException e) {
			e.printStackTrace();
		}


		// Test send RPC requests
		List<RPCMessage> rpcsList = Arrays.asList(new GetVehicleData(), new Show(), new OnAppServiceData(), new GetAppServiceDataResponse());
		OnMultipleRequestListener onMultipleRequestListener = new OnMultipleRequestListener() {
			@Override
			public void onUpdate(int remainingRequests) { }

			@Override
			public void onFinished() {
				listenerCalledCounter++;
			}

			@Override
			public void onError(int correlationId, Result resultCode, String info) {}

			@Override
			public void onResponse(int correlationId, RPCResponse response) {}
		};
		if (sequentialSend) {
			sdlManager.sendSequentialRPCs(rpcsList, onMultipleRequestListener);
		} else {
			sdlManager.sendRPCs(rpcsList, onMultipleRequestListener);
		}


		// Make sure the listener is called exactly once
		assertEquals("Listener was not called or called more/less frequently than expected", listenerCalledCounter, 1);
	}

}
