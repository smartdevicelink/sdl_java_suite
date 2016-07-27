package com.smartdevicelink.transport;

import java.util.List;

import org.json.JSONArray;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.test.AndroidTestCase;
import android.util.Log;

import com.smartdevicelink.transport.RouterServiceValidator.TrustedAppStore;
import com.smartdevicelink.util.HttpRequestTask.HttpRequestTaskCallback;

public class RSVTestCase extends AndroidTestCase {
	private static final String TAG = "RSVTestCase";
	RouterServiceValidator rsvp;
	/**
	 * Set this boolean if you want to test the actual validation of router service
	 */
	boolean liveTest = false;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		rsvp = new RouterServiceValidator(this.mContext);
		
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
/*
 * These tests are a little strange because they don't test the logic behind the validation of each piece.
 * However, they allow us to test
 */
	
	public void testInstalledFrom(){
		if(liveTest){
			rsvp.setFlags(RouterServiceValidator.FLAG_DEBUG_INSTALLED_FROM_CHECK);
			assertTrue(rsvp.validate());
		}
	}
	
	public void testPackageCheck(){
		if(liveTest){
			rsvp.setFlags(RouterServiceValidator.FLAG_DEBUG_PACKAGE_CHECK);
			assertTrue(rsvp.validate());
		}
	}
	
	public void testVersionCheck(){
		if(liveTest){
			rsvp.setFlags(RouterServiceValidator.FLAG_DEBUG_VERSION_CHECK);
			assertTrue(rsvp.validate());
		}
	}
	
	public void testNoFlags(){
		if(liveTest){
			rsvp.setFlags(RouterServiceValidator.FLAG_DEBUG_NONE);
			assertTrue(rsvp.validate());
		}
	}
	
	public void testAllFlags(){
		if(liveTest){
			rsvp.setFlags(RouterServiceValidator.FLAG_DEBUG_PERFORM_ALL_CHECKS);
			assertTrue(rsvp.validate());
		}
	}
	
	public void testJsonRecovery(){
		assertNotNull(rsvp.stringToJson(null));
		assertNotNull(rsvp.stringToJson("asdf235vq32{]]"));

	}
	
	public void testInvalidateList(){
		assertFalse(RouterServiceValidator.invalidateList(null));
		assertTrue(RouterServiceValidator.invalidateList(mContext));
	}
	
	public void testGetTrustedList(){
		assertNull(RouterServiceValidator.getTrustedList(null));
		assertNotNull(RouterServiceValidator.getTrustedList(mContext));
	}
	
	public void testSetTrustedList(){
		assertFalse(RouterServiceValidator.setTrustedList(null,null));
		assertFalse(RouterServiceValidator.setTrustedList(mContext,null));
		assertFalse(RouterServiceValidator.setTrustedList(null,"test"));
		assertTrue(RouterServiceValidator.setTrustedList(mContext,"test"));
		String test= "{\"response\": {\"com.livio.sdl\" : { \"versionBlacklist\":[] }, \"com.lexus.tcapp\" : { \"versionBlacklist\":[] }, \"com.toyota.tcapp\" : { \"versionBlacklist\": [] } , \"com.sdl.router\":{\"versionBlacklist\": [] } }}"; 
		assertTrue(RouterServiceValidator.setTrustedList(mContext,test));
		assertTrue(RouterServiceValidator.setTrustedList(mContext,test+test+test+test+test));
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i<1000; i++){
			builder.append(test);
		}
		assertTrue(RouterServiceValidator.setTrustedList(mContext,builder.toString()));
	}
	
	public void testTrustedListSetAndGet(){
		String test= "{\"response\": {\"com.livio.sdl\" : { \"versionBlacklist\":[] }, \"com.lexus.tcapp\" : { \"versionBlacklist\":[] }, \"com.toyota.tcapp\" : { \"versionBlacklist\": [] } , \"com.sdl.router\":{\"versionBlacklist\": [] } }}"; 
		assertTrue(RouterServiceValidator.setTrustedList(mContext,test));
		String retVal = RouterServiceValidator.getTrustedList(mContext);
		assertNotNull(retVal);
		assertTrue(test.equals(retVal));
		
		retVal = null;
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i<1000; i++){
			builder.append(test);
		}
		assertTrue(RouterServiceValidator.setTrustedList(mContext,builder.toString()));
		retVal = RouterServiceValidator.getTrustedList(mContext);
		assertNotNull(retVal);
		assertTrue(builder.toString().equals(retVal));
	}
	
	public void testInvalidationSequence(){
		assertTrue(RouterServiceValidator.invalidateList(mContext));
		assertTrue(RouterServiceValidator.createTrustedListRequest(mContext,false));
	}
	
	public void testAppStorePackages(){
		assertTrue(TrustedAppStore.isTrustedStore(TrustedAppStore.PLAY_STORE.packageString));
		assertTrue(TrustedAppStore.isTrustedStore("com.xiaomi.market"));
		assertFalse(TrustedAppStore.isTrustedStore("test"));
		assertFalse(TrustedAppStore.isTrustedStore(null));
		
		rsvp = new RouterServiceValidator(this.mContext);
		rsvp.setFlags(RouterServiceValidator.FLAG_DEBUG_INSTALLED_FROM_CHECK);
		
		PackageManager packageManager = mContext.getPackageManager();
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
	
	public void testVersionBlackList(){
		rsvp = new RouterServiceValidator(this.mContext);
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
	public void  testGetAndCheckList(){
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
			}
			@Override
			public void httpFailure(int statusCode) {
				Log.e(TAG, "Error while requesting trusted app list: " + statusCode);
				synchronized(REQUEST_LOCK){
					didFinish = true;
					REQUEST_LOCK.notify();
				}
			}
		};
		
		assertTrue(RouterServiceValidator.createTrustedListRequest(mContext,true, cb));
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
	
	 
}
