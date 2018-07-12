package com.smartdevicelink.api;

import android.content.Context;
import android.test.AndroidTestCase;

import com.smartdevicelink.proxy.rpc.TemplateColorScheme;
import com.smartdevicelink.proxy.rpc.enums.AppHMIType;
import com.smartdevicelink.proxy.rpc.enums.Language;
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
	private Context mTestContext;

	// transport related
	@SuppressWarnings("FieldCanBeLocal")
	private int TCP_PORT = 12345;
	@SuppressWarnings("FieldCanBeLocal")
	private String DEV_MACHINE_IP_ADDRESS = "0.0.0.0";

	@Override
	public void setUp() throws Exception{
		super.setUp();
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	// SETUP / HELPERS

	private Context getTestContext() {
		return mTestContext;
	}

	private SdlManager createSampleManager(String appName, String appId){

		// Color Scheme
		TemplateColorScheme templateColorScheme = new TemplateColorScheme();
		templateColorScheme.setBackgroundColor(Test.GENERAL_RGBCOLOR);
		templateColorScheme.setPrimaryColor(Test.GENERAL_RGBCOLOR);
		templateColorScheme.setSecondaryColor(Test.GENERAL_RGBCOLOR);

		// set transport
		transport = new TCPTransportConfig(TCP_PORT, DEV_MACHINE_IP_ADDRESS, true);

		// add AppTypes
		Vector<AppHMIType> appType = new Vector<>();
		appType.add(AppHMIType.DEFAULT);

		// build manager object - use all setters, will test using getters below
		SdlManager.Builder builder = new SdlManager.Builder();
		builder.setAppId(appId);
		builder.setAppName(appName);
		builder.setShortAppName(appName);
		builder.setAppTypes(appType);
		builder.setTransportType(transport);
		builder.setContext(getTestContext());
		builder.setLanguage(Language.EN_US);
		builder.setDayColorScheme(templateColorScheme);
		builder.setNightColorScheme(templateColorScheme);
		builder.setVrSynonyms(null);
		builder.setTtsName(null);

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
			assertSame(ex.getMessage(), "You must specify an app name by calling setAppName");
		}
	}

	public void testMissingAppId() {
		try {
			createSampleManager("app",null);
		} catch (IllegalArgumentException ex) {
			assertSame(ex.getMessage(), "You must specify an app ID by calling setAppId");
		}
	}

}
