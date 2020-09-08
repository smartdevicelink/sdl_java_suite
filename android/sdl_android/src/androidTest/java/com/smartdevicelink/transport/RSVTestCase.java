package com.smartdevicelink.transport;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.ConditionVariable;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import android.util.Log;

import com.smartdevicelink.transport.RouterServiceValidator.TrustedAppStore;
import com.smartdevicelink.util.HttpRequestTask.HttpRequestTaskCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.Semaphore;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.JVM)
public class RSVTestCase {
	private static final String TAG = "RSVTestCase";
	
	private static final long REFRESH_TRUSTED_APP_LIST_TIME_DAY 	= 3600000 * 24; // A day in ms
	private static final long REFRESH_TRUSTED_APP_LIST_TIME_WEEK 	= REFRESH_TRUSTED_APP_LIST_TIME_DAY * 7; // A week in ms
	private static final long REFRESH_TRUSTED_APP_LIST_TIME_MONTH 	= REFRESH_TRUSTED_APP_LIST_TIME_DAY * 30; // A ~month in ms
	private static final String TEST =  "{\"response\": {\"com.livio.sdl\" : { \"versionBlacklist\":[] }, \"com.lexus.tcapp\" : { \"versionBlacklist\":[] }, \"com.toyota.tcapp\" : { \"versionBlacklist\": [] } , \"com.sdl.router\":{\"versionBlacklist\": [] },\"com.ford.fordpass\" : { \"versionBlacklist\":[] } }}";
	RouterServiceValidator rsvp;
	private static final String APP_ID = "com.smartdevicelink.test.RSVTestCase";
	
	@Before
	public void setUp() throws Exception {
		rsvp = new RouterServiceValidator(getInstrumentation().getTargetContext());
		
	}

	private static final Semaphore TRUSTED_LIST_LOCK = new Semaphore(1);

	private void requestTListLock(){
		try {
			TRUSTED_LIST_LOCK.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void releaseTListLock(){
		TRUSTED_LIST_LOCK.release();
	}

	private RouterServiceValidator.TrustedListCallback trustedListCallback = new RouterServiceValidator.TrustedListCallback(){
		@Override
		public void onListObtained(boolean successful) {
			releaseTListLock();
		}
	};

	@Test
	public void testSecuritySetting(){
		
		RouterServiceValidator rsvp = new RouterServiceValidator(getInstrumentation().getTargetContext()); //Use a locally scoped instance
		rsvp.setSecurityLevel(MultiplexTransportConfig.FLAG_MULTI_SECURITY_HIGH);
		
		try{
			Field securityLevelField =  RouterServiceValidator.class.getDeclaredField("securityLevel");
			securityLevelField.setAccessible(true);
			assertEquals(securityLevelField.get(rsvp),MultiplexTransportConfig.FLAG_MULTI_SECURITY_HIGH);
		}catch(NoSuchFieldException e1){
			fail(e1.getMessage());
		}catch( IllegalAccessException e2){
			fail(e2.getMessage());
		}
		assertEquals(RouterServiceValidator.getSecurityLevel(getInstrumentation().getTargetContext()), MultiplexTransportConfig.FLAG_MULTI_SECURITY_HIGH);
	}

	@Test
	public void testHighSecurity(){
		requestTListLock();

		RouterServiceValidator rsvp = new RouterServiceValidator(getInstrumentation().getTargetContext()); //Use a locally scoped instance
		rsvp.setSecurityLevel(MultiplexTransportConfig.FLAG_MULTI_SECURITY_HIGH);
		rsvp.setFlags(RouterServiceValidator.FLAG_DEBUG_INSTALLED_FROM_CHECK);
		
		assertTrue(checkShouldOverrideInstalledFrom(rsvp,false));
		
		assertEquals(RouterServiceValidator.getRefreshRate(), REFRESH_TRUSTED_APP_LIST_TIME_WEEK);
		
		assertTrue(RouterServiceValidator.createTrustedListRequest(getInstrumentation().getTargetContext(), true, null, trustedListCallback));
		
	}

	@Test
	public void testMediumSecurity(){
		requestTListLock();

		RouterServiceValidator rsvp = new RouterServiceValidator(getInstrumentation().getTargetContext()); //Use a locally scoped instance
		rsvp.setSecurityLevel(MultiplexTransportConfig.FLAG_MULTI_SECURITY_MED);
		rsvp.setFlags(RouterServiceValidator.FLAG_DEBUG_INSTALLED_FROM_CHECK);
		
		assertTrue(checkShouldOverrideInstalledFrom(rsvp,true));
		
		assertEquals(RouterServiceValidator.getRefreshRate(), REFRESH_TRUSTED_APP_LIST_TIME_WEEK);
		
		assertTrue(RouterServiceValidator.createTrustedListRequest(getInstrumentation().getTargetContext(), true, null, trustedListCallback));
		
	}

	@Test
	public void testLowSecurity(){
		requestTListLock();

		RouterServiceValidator rsvp = new RouterServiceValidator(getInstrumentation().getTargetContext()); //Use a locally scoped instance
		rsvp.setSecurityLevel(MultiplexTransportConfig.FLAG_MULTI_SECURITY_LOW);
		rsvp.setFlags(RouterServiceValidator.FLAG_DEBUG_INSTALLED_FROM_CHECK);
		
		assertTrue(checkShouldOverrideInstalledFrom(rsvp,true));
		
		assertEquals(RouterServiceValidator.getRefreshRate(), REFRESH_TRUSTED_APP_LIST_TIME_MONTH);
		
		assertTrue(RouterServiceValidator.createTrustedListRequest(getInstrumentation().getTargetContext(), true, null, trustedListCallback));
		
	}

	@Test
	public void testNoSecurity(){
		requestTListLock();

		RouterServiceValidator rsvp = new RouterServiceValidator(getInstrumentation().getTargetContext()); //Use a locally scoped instance
		rsvp.setSecurityLevel(MultiplexTransportConfig.FLAG_MULTI_SECURITY_OFF);
		rsvp.setFlags(RouterServiceValidator.FLAG_DEBUG_INSTALLED_FROM_CHECK);
		
		assertTrue(checkShouldOverrideInstalledFrom(rsvp,true));
		
		assertEquals(RouterServiceValidator.getRefreshRate(), REFRESH_TRUSTED_APP_LIST_TIME_WEEK);
		
		assertFalse(RouterServiceValidator.createTrustedListRequest(getInstrumentation().getTargetContext(), true, null, trustedListCallback));
		
	}
	
	public boolean checkShouldOverrideInstalledFrom(RouterServiceValidator rsvp, boolean shouldOverride){
		try{
			Method shouldOverrideInstalledFrom = RouterServiceValidator.class.getDeclaredMethod("shouldOverrideInstalledFrom");
			shouldOverrideInstalledFrom.setAccessible(true);
			boolean should = (Boolean)shouldOverrideInstalledFrom.invoke(rsvp);
			
			return shouldOverride == should;
		
		}catch(NoSuchMethodException e1){
			fail(e1.getMessage());
		}catch( IllegalAccessException e2){
			fail(e2.getMessage());
		}catch( InvocationTargetException e3){
			fail(e3.getMessage());
		}
		return false;
	}

	@Test
	public void testJsonRecovery(){
		assertNotNull(rsvp.stringToJson(null));
		assertNotNull(rsvp.stringToJson("asdf235vq32{]]"));

	}

	@Test
	public void testInvalidateList(){
		requestTListLock();

		assertFalse(RouterServiceValidator.invalidateList(null));
		assertTrue(RouterServiceValidator.invalidateList(getInstrumentation().getTargetContext()));

		releaseTListLock();
	}

	@Test
	public void testGetTrustedList(){
		requestTListLock();

		assertNull(RouterServiceValidator.getTrustedList(null));
		assertNotNull(RouterServiceValidator.getTrustedList(getInstrumentation().getTargetContext()));

		releaseTListLock();
	}

	@Test
	public void testSetTrustedList(){
		requestTListLock();

		assertFalse(RouterServiceValidator.setTrustedList(null,null));
		assertFalse(RouterServiceValidator.setTrustedList(getInstrumentation().getTargetContext(),null));
		assertFalse(RouterServiceValidator.setTrustedList(null,"test"));
		assertTrue(RouterServiceValidator.setTrustedList(getInstrumentation().getTargetContext(),"test"));
		assertTrue(RouterServiceValidator.setTrustedList(getInstrumentation().getTargetContext(),TEST));
		assertTrue(RouterServiceValidator.setTrustedList(getInstrumentation().getTargetContext(),TEST+TEST+TEST+TEST+TEST));
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i<1000; i++){
			builder.append(TEST);
		}
		assertTrue(RouterServiceValidator.setTrustedList(getInstrumentation().getTargetContext(),builder.toString()));

		releaseTListLock();
	}

	@Test
	public void testTrustedListSetAndGet(){
		requestTListLock();

		assertTrue(RouterServiceValidator.setTrustedList(getInstrumentation().getTargetContext(),TEST));
		String retVal = RouterServiceValidator.getTrustedList(getInstrumentation().getTargetContext());
		assertNotNull(retVal);
		assertTrue(TEST.equals(retVal));

		StringBuilder builder = new StringBuilder();
		for(int i = 0; i<1000; i++){
			builder.append(TEST);
		}
		assertTrue(RouterServiceValidator.setTrustedList(getInstrumentation().getTargetContext(),builder.toString()));
		retVal = RouterServiceValidator.getTrustedList(getInstrumentation().getTargetContext());
		assertNotNull(retVal);
		assertTrue(builder.toString().equals(retVal));

		releaseTListLock();
	}

	@Test
	public void testInvalidationSequence(){
		requestTListLock();

		assertTrue(RouterServiceValidator.invalidateList(getInstrumentation().getTargetContext()));
		assertTrue(RouterServiceValidator.createTrustedListRequest(getInstrumentation().getTargetContext(), false, null, trustedListCallback));
	}

	@Test
	public void testAppStorePackages(){
		assertTrue(TrustedAppStore.isTrustedStore(TrustedAppStore.PLAY_STORE.packageString));
		assertTrue(TrustedAppStore.isTrustedStore("com.xiaomi.market"));
		assertFalse(TrustedAppStore.isTrustedStore("test"));
		assertFalse(TrustedAppStore.isTrustedStore(null));
		
		rsvp = new RouterServiceValidator(getInstrumentation().getTargetContext());
		rsvp.setFlags(RouterServiceValidator.FLAG_DEBUG_INSTALLED_FROM_CHECK);
		rsvp.setSecurityLevel(MultiplexTransportConfig.FLAG_MULTI_SECURITY_HIGH);
		
		PackageManager packageManager = getInstrumentation().getTargetContext().getPackageManager();
		List<PackageInfo> packages = packageManager.getInstalledPackages(0);
		String appStore;
		for(PackageInfo info: packages){
			appStore = packageManager.getInstallerPackageName(info.packageName);
			if(TrustedAppStore.isTrustedStore(appStore)){
				assertTrue(rsvp.wasInstalledByAppStore(info.packageName));
			}
		}
		
		assertFalse(rsvp.wasInstalledByAppStore(null));
	}

	@Test
	public void testVersionBlackList(){
		rsvp = new RouterServiceValidator(getInstrumentation().getTargetContext());
		JSONArray array = new JSONArray();
		for(int i=0; i<25; i++){
			if(i%3 == 0){
				array.put(i);
			}
		}
		assertTrue(rsvp.verifyVersion(1, null));
		assertTrue(rsvp.verifyVersion(1, array));
		assertTrue(rsvp.verifyVersion(100, array));
		assertFalse(rsvp.verifyVersion(3, array));
		assertFalse(rsvp.verifyVersion(-3, array));

	}
	
	static boolean didFinish = false;
	@Test
	public void  testGetAndCheckList(){
		requestTListLock();

		final Object REQUEST_LOCK = new Object();
		didFinish = false;
		HttpRequestTaskCallback cb = new HttpRequestTaskCallback(){
			
			@Override
			public void httpCallComplete(String response) {
				//Might want to check if this list is ok
				Log.d(TAG, "APPS! " + response);
				synchronized(REQUEST_LOCK){
					didFinish = true;
					REQUEST_LOCK.notify();
				}
				releaseTListLock();
			}
			@Override
			public void httpFailure(int statusCode) {
				Log.e(TAG, "Error while requesting trusted app list: " + statusCode);
				synchronized(REQUEST_LOCK){
					didFinish = true;
					REQUEST_LOCK.notify();
				}
				releaseTListLock();
			}
		};
		
		assertTrue(RouterServiceValidator.createTrustedListRequest(getInstrumentation().getTargetContext(),true, cb, null));
		//Now wait for call to finish
		synchronized(REQUEST_LOCK){
			try {
				REQUEST_LOCK.wait();
				assertTrue(didFinish);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	/**
	 * Test to check that we can save our last request which actually houses all the previous known sdl enabled apps
	 */
	@Test
	public void testRequestChange(){
		requestTListLock();

		RouterServiceValidator.setLastRequest(getInstrumentation().getTargetContext(), null);
		assertNull(RouterServiceValidator.getLastRequest(getInstrumentation().getTargetContext()));

		JSONObject object = null;
		try {
			object = new JSONObject(TEST);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		assertNotNull(object);
		assertFalse(object.equals(RouterServiceValidator.getLastRequest(getInstrumentation().getTargetContext())));
		
		assertTrue(RouterServiceValidator.setLastRequest(getInstrumentation().getTargetContext(), object.toString()));
		
		String oldRequest = RouterServiceValidator.getLastRequest(getInstrumentation().getTargetContext());
		assertNotNull(oldRequest);
		assertTrue(object.toString().equals(oldRequest));
		
		//Now test a new list
		String test = "{\"response\": {\"com.livio.sdl\" : { \"versionBlacklist\":[] }, \"com.lexus.tcapp\" : { \"versionBlacklist\":[] }, \"com.test.test\" : { \"versionBlacklist\":[] },\"com.toyota.tcapp\" : { \"versionBlacklist\": [] } , \"com.sdl.router\":{\"versionBlacklist\": [] },\"com.ford.fordpass\" : { \"versionBlacklist\":[] } }}";
		object = null;
		try {
			object = new JSONObject(test);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		assertNotNull(object);
		assertFalse(object.equals(RouterServiceValidator.getLastRequest(getInstrumentation().getTargetContext())));
		//Clear it for next test
		RouterServiceValidator.setLastRequest(getInstrumentation().getTargetContext(), null);

		releaseTListLock();
	}

	/**
	 * Test app's router validation. Validation should fail when the given context and ComponentName object are from different packages and security setting is not OFF
	 * and app is not on trusted list. Validation should pass when the given context and ComponentName object are from the same package.
	 */
	@Test
	public void testAppSelfValidation() {

		class RouterServiceValidatorTest extends RouterServiceValidator{
			public RouterServiceValidatorTest(Context context){
				super(context);
			}

			public RouterServiceValidatorTest(Context context, ComponentName service){
				super(context, service);
			}

			// Override this method and simply returning true for the purpose of this test
			protected boolean isServiceRunning(Context context, ComponentName service){
				return true;
			}
		}

		// Fail, different package name for context and service and app security setting is not OFF and app is not on trusted list
		RouterServiceValidatorTest rsvpFail = new RouterServiceValidatorTest(getInstrumentation().getTargetContext(), new ComponentName("anything", getInstrumentation().getTargetContext().getClass().getSimpleName()));
		rsvpFail.setSecurityLevel(MultiplexTransportConfig.FLAG_MULTI_SECURITY_HIGH);
		rsvpFail.validateAsync(new RouterServiceValidator.ValidationStatusCallback() {
			@Override
			public void onFinishedValidation(boolean valid, ComponentName name) {
				assertFalse(valid);
			}
		});

		// Success, same package name for context and service
		RouterServiceValidatorTest rsvpPass = new RouterServiceValidatorTest(getInstrumentation().getTargetContext(), new ComponentName(getInstrumentation().getTargetContext().getPackageName(), getInstrumentation().getTargetContext().getClass().getSimpleName()));
		rsvpPass.setSecurityLevel(MultiplexTransportConfig.FLAG_MULTI_SECURITY_HIGH);
		rsvpPass.validateAsync(new RouterServiceValidator.ValidationStatusCallback() {
			@Override
			public void onFinishedValidation(boolean valid, ComponentName name) {
				assertTrue(valid);
			}
		});
	}

	/**
	 * Unit test for validateAsync.
	 */
	@Test
	public void testValidateAsync() {
		final MultiplexTransportConfig config = new MultiplexTransportConfig(getInstrumentation().getTargetContext(), APP_ID, MultiplexTransportConfig.FLAG_MULTI_SECURITY_HIGH);
		final RouterServiceValidator validator = new RouterServiceValidator(config);
		final ConditionVariable cond = new ConditionVariable();
		validator.validateAsync(new RouterServiceValidator.ValidationStatusCallback() {
			@Override
			public void onFinishedValidation(boolean valid, ComponentName name) {
				Log.d(TAG, "onFinishedValidation: valid=" + valid + "; componentName=" + name);
				assertFalse(valid); // expected valid = false for this (bogus) APP_ID..
				cond.open();
			}
		});
		cond.block();

		// next, test for FLAG_MULTI_SECURITY_OFF
		final MultiplexTransportConfig config2 = new MultiplexTransportConfig(getInstrumentation().getTargetContext(), APP_ID, MultiplexTransportConfig.FLAG_MULTI_SECURITY_OFF);
		final RouterServiceValidator validator2 = new RouterServiceValidator(config2);
		cond.close();
		validator2.validateAsync(new RouterServiceValidator.ValidationStatusCallback() {
			@Override
			public void onFinishedValidation(boolean valid, ComponentName name) {
				Log.d(TAG, "onFinishedValidation: valid=" + valid + "; componentName=" + name);
				// return value does not matter when security is off.
				cond.open();
			}
		});
		cond.block();
	}

}
