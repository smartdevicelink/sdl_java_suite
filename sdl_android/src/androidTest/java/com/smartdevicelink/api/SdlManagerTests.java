package com.smartdevicelink.api;

import android.content.Context;
import android.test.AndroidTestCase;
import android.test.mock.MockContext;

import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.TemplateColorScheme;
import com.smartdevicelink.proxy.rpc.enums.AppHMIType;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.proxy.rpc.enums.VrCapabilities;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.transport.BaseTransportConfig;
import com.smartdevicelink.transport.TCPTransportConfig;

import java.util.Vector;

/**
 * This is a unit test class for the SmartDeviceLink library manager class :
 * {@link com.smartdevicelink.api.SdlManager}
 */
public class SdlManagerTests extends AndroidTestCase {

	public static final String TAG = "SdlManagerTests";
	public static BaseTransportConfig transport = null;
	private Vector<AppHMIType> appType;
	private TemplateColorScheme templateColorScheme;

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
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	// SETUP / HELPERS

	private SdlManager createSampleManager(String appName, String appId){

		Context context = new MockContext();

		// build manager object - use all setters, will test using getters below
		SdlManager.Builder builder = new SdlManager.Builder();
		builder.setAppId(appId);
		builder.setAppName(appName);
		builder.setShortAppName(appName);
		builder.setAppTypes(appType);
		builder.setTransportType(transport);
		builder.setContext(context);
		builder.setLanguage(Language.EN_US);
		builder.setDayColorScheme(templateColorScheme);
		builder.setNightColorScheme(templateColorScheme);
		builder.setVrSynonyms(Test.GENERAL_VECTOR_STRING);
		builder.setTtsName(Test.GENERAL_VECTOR_TTS_CHUNKS);
		builder.setLockScreenConfig(Test.GENERAL_LOCKSCREENCONFIG);

		return builder.build();
	}

	// TESTS

	public void testNotNull(){
		assertNotNull(createSampleManager("app","123456"));
	}

	public void testMissingAppName() {
		try {
			createSampleManager(null,"123456");
		} catch (IllegalArgumentException ex) {
			assertSame(ex.getMessage(), "You must specify an app name by calling setAppName()");
		}
	}

	public void testMissingAppId() {
		try {
			createSampleManager("app",null);
		} catch (IllegalArgumentException ex) {
			assertSame(ex.getMessage(), "You must specify an app ID by calling setAppId()");
		}
	}

	public void testManagerSetters() {

		SdlManager manager = createSampleManager("heyApp", "123456");

		assertEquals("123456", manager.getAppId());
		assertEquals("heyApp", manager.getAppName());
		assertEquals("heyApp", manager.getShortAppName());
		assertEquals(appType, manager.getAppTypes());
		assertEquals(Language.EN_US, manager.getHmiLanguage());
		assertEquals(transport, manager.getTransport());
		assertEquals(templateColorScheme, manager.getDayColorScheme());
		assertEquals(templateColorScheme, manager.getNightColorScheme());
		assertEquals(Test.GENERAL_VECTOR_STRING, manager.getVrSynonyms());
		assertEquals(Test.GENERAL_VECTOR_TTS_CHUNKS, manager.getTtsChunks());
		assertEquals(Test.GENERAL_LOCKSCREENCONFIG, manager.getLockScreenConfig());
	}

}
