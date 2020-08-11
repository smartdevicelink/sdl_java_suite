/*
 * Copyright (c) 2019, Livio, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the Livio Inc. nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.smartdevicelink.transport;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;

import com.smartdevicelink.util.AndroidTools;
import com.smartdevicelink.util.DebugTool;
import com.smartdevicelink.util.HttpRequestTask;
import com.smartdevicelink.util.HttpRequestTask.HttpRequestTaskCallback;
import com.smartdevicelink.util.ServiceFinder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class will tell us if the currently running router service is valid or not.
 * To use this class simply create a new instance of RouterServiceValidator with a supplied context.
 * After that, you have the option to set if you want to test in a production setting. If not, it will default to a debug setting.
 * Once you are ready to check if the router service is trusted you simply call routerServiceValidator.validate();
 * <br><br> This validator should be passed into the multiplexing transport construction as well.
 * @author Joey Grover
 *
 */
public class RouterServiceValidator {
	private static final String TAG = "RSVP";
	public static final String ROUTER_SERVICE_PACKAGE = "com.sdl.router";

	private static final String REQUEST_PREFIX = "https://woprjr.smartdevicelink.com/api/1/applications/queryTrustedRouters"; 

	private static final String DEFAULT_APP_LIST = "{\"response\": {\"com.livio.sdl\" : { \"versionBlacklist\":[] }, \"com.lexus.tcapp\" : { \"versionBlacklist\":[] }, \"com.toyota.tcapp\" : { \"versionBlacklist\": [] } , \"com.sdl.router\":{\"versionBlacklist\": [] },\"com.ford.fordpass\" : { \"versionBlacklist\":[] } }}"; 
	
	
	private static final String JSON_RESPONSE_OBJECT_TAG = "response";
	private static final String JSON_RESONSE_APP_VERSIONS_TAG = "versionBlacklist";

	private static final String JSON_PUT_ARRAY_TAG = "installedApps";
	private static final String JSON_APP_PACKAGE_TAG = "packageName";
	private static final String JSON_APP_VERSION_TAG = "version";

	
	private static final long REFRESH_TRUSTED_APP_LIST_TIME_DAY 	= 3600000 * 24; // A day in ms
	
	private static final String SDL = "sdl";
	private static final String SDL_PACKAGE_LIST = "sdl_package_list";
	private static final String SDL_PACKAGE_LIST_TIMESTAMP = "sdl_package_list_timestamp";
	private static final String SDL_LAST_REQUEST = "sdl_last_request";
	private static final String SDL_RSVP_SECURITY_LEVEL = "sdl_rsvp_security_level";

	
	//Flags to aid in debugging and production checks
	public static final int FLAG_DEBUG_NONE 				= 0x00;
	public static final int FLAG_DEBUG_PACKAGE_CHECK 		= 0x01;
	/**
	 * This will flag the validator to check for app version during debugging. 
	 * <br><br><b>NOTE: This flag will include a package check as well.
	 */
	public static final int FLAG_DEBUG_VERSION_CHECK 		= 0x03; //We use 3 becuase version check will be 2, but since a version check implies a package check we do 2+1=3;
	public static final int FLAG_DEBUG_INSTALLED_FROM_CHECK = 0x04;
	public static final int FLAG_DEBUG_USE_TIMESTAMP_CHECK = 0x05;

	public static final int FLAG_DEBUG_PERFORM_ALL_CHECKS 	= 0xFF;
	
	
	private int flags = FLAG_DEBUG_NONE;

	private Context context= null;
	private boolean inDebugMode = false;
	@SuppressWarnings("unused")
	private static boolean pendingListRefresh = false;
	
	private ComponentName service;//This is how we can save different routers over another in a waterfall method if we choose to.

	private static int securityLevel = -1;
	
	public RouterServiceValidator(Context context){
		this.context = context;
		inDebugMode = inDebugMode();
	}
	
	public RouterServiceValidator(Context context, ComponentName service){
		this.context = context;
		inDebugMode = inDebugMode();
		this.service = service;
	}

	public RouterServiceValidator(@NonNull MultiplexTransportConfig config){
		this.context = config.context;
		this.service = config.service;
		setSecurityLevel(config.securityLevel);
		inDebugMode = inDebugMode();
	}

	/**
	 * Main function to call to ensure we are connecting to a validated router service
	 * @return whether or not the currently running router service can be trusted.
	 *
	 * Due to SDL 0220 proposal, we should use validateAsync always.
	 * This function remains only for backward compatibility.
	 */
	@Deprecated
	public boolean validate(){
		
		if(securityLevel == -1){
			securityLevel = getSecurityLevel(context);
		}
		
		if(securityLevel == MultiplexTransportConfig.FLAG_MULTI_SECURITY_OFF){ //If security isn't an issue, just return true;
			return true;
		}
		
		PackageManager pm = context.getPackageManager();
		//Grab the package for the currently running router service. We need this call regardless of if we are in debug mode or not.
		String packageName = null;
		
		if(this.service != null){
			DebugTool.logInfo(TAG, "Supplied service name of " + this.service.getClassName());
			if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O && !isServiceRunning(context,this.service)){
				//This means our service isn't actually running, so set to null. Hopefully we can find a real router service after this.
				service = null;
				DebugTool.logWarning(TAG, "Supplied service is not actually running.");
			} else {
				// If the running router service is created by this app, the validation is good by default
				if (this.service.getPackageName().equals(context.getPackageName())) {
					return true;
				}
			}
		}
		if(this.service == null){
			if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O ) {
				this.service = componentNameForServiceRunning(pm); //Change this to an array if multiple services are started?
				if (this.service == null) { //if this is still null we know there is no service running so we can return false
					wakeUpRouterServices();
					return false;
				}
			}else{
				wakeUpRouterServices();
				return false;
			}

		}
		
		//Log.d(TAG, "Checking app package: " + service.getClassName());
		packageName = this.appPackageForComponentName(service, pm);
		

		if(packageName!=null){//Make sure there is a service running
			if(wasInstalledByAppStore(packageName)){ //Was this package installed from a trusted app store
				if( isTrustedPackage(packageName, pm)){//Is this package on the list of trusted apps.
					return true;
				}
			}
		}//No running service found. Might need to attempt to start one
		//TODO spin up a known good router service
		wakeUpRouterServices();
		return false;
	}

	/**
	 * Asynchronously validate the target RouterService, which includes finding the right RouterService.
	 * @param callback: callback gets called when validation finishes.
	 */
	public void validateAsync(final ValidationStatusCallback callback) {
		if(securityLevel == -1){
			securityLevel = getSecurityLevel(context);
		}

		final PackageManager pm = context.getPackageManager();
		//Grab the package for the currently running router service. We need this call regardless of if we are in debug mode or not.

		if(this.service != null){
			DebugTool.logInfo(TAG, "Supplied service name of " + this.service.getClassName());
			if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O && !isServiceRunning(context,this.service)){
				//This means our service isn't actually running, so set to null. Hopefully we can find a real router service after this.
				service = null;
				DebugTool.logWarning(TAG, "Supplied service is not actually running.");
			} else {
				// If the running router service is created by this app, the validation is good by default
				if (this.service.getPackageName().equals(context.getPackageName()) && callback != null) {
					callback.onFinishedValidation(true, this.service);
					return;
				}
			}
		}

		if(this.service == null){
			DebugTool.logInfo(TAG, "about finding the best Router by using retrieveBestRouterServiceName");
			new FindRouterTask(new FindConnectedRouterCallback() {
				@Override
				public void onFound(ComponentName component) {
					DebugTool.logInfo(TAG, "FindConnectedRouterCallback.onFound got called. Package=" + component);
					checkTrustedRouter(callback, pm, component);
				}

				@Override
				public void onFailed() {
					DebugTool.logInfo(TAG, "FindConnectedRouterCallback.onFailed was called");
					if (callback != null) {
						callback.onFinishedValidation(false, null);
					}
				}
			}).execute(this.context);
		} else {
			// already found the RouterService
			checkTrustedRouter(callback, pm, service);
		}

	}

	/**
	 * checkTrustedRouter: This checks to see if the given component is Trusted RouterService,
	 * and calls ValidationStatusCallback#onFinishedValidation.
	 *
	 * @param callback
	 * @param pm
	 * @param component
	 */
	private void checkTrustedRouter(final ValidationStatusCallback callback, final PackageManager pm, final ComponentName component) {
		String packageName = appPackageForComponentName(component, pm);
		boolean valid = (securityLevel == MultiplexTransportConfig.FLAG_MULTI_SECURITY_OFF);

		if(!valid && packageName!=null){//Make sure there is a service running
			if(wasInstalledByAppStore(packageName)){ //Was this package installed from a trusted app store
				if( isTrustedPackage(packageName, pm)){//Is this package on the list of trusted apps.
					valid = true;
				}
			}
		}
		if (callback != null) {
			callback.onFinishedValidation(valid, component);
			if (valid) {
				synchronized (this) {
					this.service = component;
				}
			}
		}
	}
	/**
	 * This method retrieves the best routerservice name asynchronously.
	 * @param context
	 */
	//private void retrieveBestRouterServiceName(Context context) {
	//	FindRouterTask task = new FindRouterTask(null);
	//	task.execute(context);
	//}

	/**
	 * FindRouterTask: AsyncTask to find the connected RouterService.
	 */
	class FindRouterTask extends AsyncTask<Context, Void, ComponentName> {
		FindConnectedRouterCallback mCallback;
		final Handler mHandler = new Handler(Looper.getMainLooper());
		final Integer TIMEOUT_MSEC = 10000; // 10 sec

		FindRouterTask(FindConnectedRouterCallback callback) {
			mCallback = callback;
		}

		@Override
		protected ComponentName doInBackground(final Context... contexts) {
			// let's use ServiceFinder here
			final BlockingQueue<ComponentName> serviceQueue = new LinkedBlockingQueue<>();
			final AtomicInteger _counter = new AtomicInteger(0);
			Context context = contexts[0];
			final Thread _currentThread = Thread.currentThread();
			new ServiceFinder(context, context.getPackageName(), new ServiceFinder.ServiceFinderCallback() {
				@Override
				public void onComplete(Vector<ComponentName> routerServices) {
					// OK, we found the routerServices. Let's see one-by-one.
					if (routerServices == null || routerServices.isEmpty()) {
						_currentThread.interrupt();
						return;
					}


					final int numServices = routerServices.size();
					for (ComponentName name: routerServices) {
						final SdlRouterStatusProvider provider = new SdlRouterStatusProvider(contexts[0], name, new SdlRouterStatusProvider.ConnectedStatusCallback() {
							@Override
							public void onConnectionStatusUpdate(final boolean connected, final ComponentName service, final Context context) {
								// make sure this part runs on main thread.
								mHandler.post(new Runnable() {
									@Override
									public void run() {
										_counter.incrementAndGet();
										if (connected) {
											DebugTool.logInfo(TAG, "We found the connected service (" + service + "); currentThread is " + Thread.currentThread().getName());
											serviceQueue.add(service);
										} else if (_counter.get() == numServices) {
											DebugTool.logInfo(TAG, "SdlRouterStatusProvider returns service=" + service + "; connected=" + connected);
											_currentThread.interrupt();
										}
									}
								});
							}
						});
						DebugTool.logInfo(TAG, "about checkIsConnected; thread=" + Thread.currentThread().getName());
						provider.checkIsConnected();
					}
				}
			});

			try {
				ComponentName found = serviceQueue.poll(TIMEOUT_MSEC, TimeUnit.MILLISECONDS);
				return found;
			} catch(InterruptedException e) {
				DebugTool.logInfo(TAG,"FindRouterTask was interrupted because connected Router cannot be found");
			}
			return null;
		}

		@Override
		protected void onPostExecute(ComponentName componentName) {
			DebugTool.logInfo(TAG,"onPostExecute componentName=" + componentName);
			super.onPostExecute(componentName);
			if (mCallback != null) {
				if (componentName != null && componentName.getPackageName() != null && componentName.getPackageName().length() != 0) {
					mCallback.onFound(componentName);
				} else {
					mCallback.onFailed();
				}
			}
		}

	}

	/**
	 * FindConnectedRouterCallback
	 * Used internally for validating router service.
	 */
	private interface FindConnectedRouterCallback {
		void onFound(ComponentName component);
		void onFailed();
	}

	interface ValidationStatusCallback {
		void onFinishedValidation(boolean valid, ComponentName name);
	}

	/**
	 * This will ensure that all router services are aware that there are no valid router services running and should start up 
	 */
	private void wakeUpRouterServices(){
		if(BluetoothAdapter.getDefaultAdapter()!=null && BluetoothAdapter.getDefaultAdapter().isEnabled()){
			Intent intent = new Intent(TransportConstants.START_ROUTER_SERVICE_ACTION);
			intent.putExtra(TransportConstants.PING_ROUTER_SERVICE_EXTRA, true);
			AndroidTools.sendExplicitBroadcast(context,intent,null);
		}
	}
	public ComponentName getService(){
		return this.service;
	}

	private boolean shouldOverrideVersionCheck(){
		return (this.inDebugMode && ((this.flags & FLAG_DEBUG_VERSION_CHECK) != FLAG_DEBUG_VERSION_CHECK));
	}
	
	private boolean shouldOverridePackageName(){
		return (this.inDebugMode && ((this.flags & FLAG_DEBUG_PACKAGE_CHECK) != FLAG_DEBUG_PACKAGE_CHECK));
	}
	
	private boolean shouldOverrideInstalledFrom(){
		return securityLevel< MultiplexTransportConfig.FLAG_MULTI_SECURITY_HIGH 
				|| (this.inDebugMode && ((this.flags & FLAG_DEBUG_INSTALLED_FROM_CHECK) != FLAG_DEBUG_INSTALLED_FROM_CHECK));
	}
	
	@SuppressWarnings("unused")
	private boolean shouldOverrideTimeCheck(){
		return (this.inDebugMode && ((this.flags & FLAG_DEBUG_USE_TIMESTAMP_CHECK) != FLAG_DEBUG_USE_TIMESTAMP_CHECK));
	}
	
	
	/**
	 *  Use this method if you would like to test your app in a production setting rather than defaulting to a
	 * debug mode where you connect to whatever router service is running.
	 * <br><br><b>These flags are only used in debugging mode. During production they will be ignored.</b>
	 * @param flags
	 */
	public void setFlags(int flags){
		this.flags = flags;
	}
	
	public void setSecurityLevel(int securityLevel){
		RouterServiceValidator.securityLevel = securityLevel;
		cacheSecurityLevel(this.context,securityLevel);
	}
	
	protected static long getRefreshRate(){
		switch(securityLevel){
		case MultiplexTransportConfig.FLAG_MULTI_SECURITY_LOW:
			return 30 * REFRESH_TRUSTED_APP_LIST_TIME_DAY; 
		case MultiplexTransportConfig.FLAG_MULTI_SECURITY_HIGH:
		case MultiplexTransportConfig.FLAG_MULTI_SECURITY_MED:
		default:
			return 7 * REFRESH_TRUSTED_APP_LIST_TIME_DAY; 
		
		}
	}
	
	/**
	 * This method will find which router service is running. Use that info to find out more about that app and service.
	 * It will store the found service for later use and return the package name if found. 
	 * @param pm An instance of a package manager. This is no longer used so null can be sent.
	 * @return
	 */
	public ComponentName componentNameForServiceRunning(PackageManager pm){
		if(context==null){
			return null;
		}
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		//PackageManager pm = context.getPackageManager();
		
		
		for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
			//Log.d(TAG, service.service.getClassName());
			//We will check to see if it contains this name, should be pretty specific
			if ((service.service.getClassName()).toLowerCase(Locale.US).contains(SdlBroadcastReceiver.SDL_ROUTER_SERVICE_CLASS_NAME)){ 
				//this.service = service.service; //This is great
				if(service.started && service.restarting==0){ //If this service has been started and is not crashed
					return service.service; //appPackageForComponenetName(service.service,pm);
				}
			}
		}			

		return null;
	}
	
	/**
	 * Returns the package name for the component name
	 * @param cn
	 * @param pm
	 * @return
	 */
	private String appPackageForComponentName(ComponentName cn,PackageManager pm ){
		if(cn!=null && pm!=null){
			ServiceInfo info;
			try {
				info = pm.getServiceInfo(cn, 0);
				return info.applicationInfo.packageName;
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
		}
		return null;
		
	}
	
	/**
	 * Check to see if the app was installed from a trusted app store.
	 * @param packageName the package name of the app to be tested
	 * @return whether or not the app was installed from a trusted app store
	 */
	public boolean wasInstalledByAppStore(String packageName){
		if(shouldOverrideInstalledFrom()){
			return true;
		}
		PackageManager packageManager = context.getPackageManager();
		try {
			final ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 0);
			if(TrustedAppStore.isTrustedStore(packageManager.getInstallerPackageName(applicationInfo.packageName))){
				// App was installed by trusted app store
				return true;
			}
		} catch (final NameNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	/**
	 * This method will check to see if this app is a debug build. If it is, we will attempt to connect to any router service.
	 * If false, it will only connect to approved apps with router services.
	 * @return
	 */
	public boolean inDebugMode(){
		return (0 != (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE));
	}
	
	
	private boolean isTrustedPackage(String packageName, PackageManager pm){
		if(packageName == null){
			return false;
		}
		
		if(shouldOverridePackageName()){ //If we don't care about package names, just return true;
			return true;
		}

		int version = -1;
		try {version = pm.getPackageInfo(packageName,0).versionCode;} catch (NameNotFoundException e1) {e1.printStackTrace(); return false;}
		
		JSONObject trustedApps = stringToJson(getTrustedList(context));
		JSONArray versions;
		JSONObject app = null;

		try {
			app = trustedApps.getJSONObject(packageName);
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		} 
		
		if(app!=null){
			//At this point, an app object was found in the JSON list that matches the package name
			if(shouldOverrideVersionCheck()){ //If we don't care about versions, just return true
				return true;
			}
			try { versions = app.getJSONArray(JSON_RESONSE_APP_VERSIONS_TAG); } catch (JSONException e) {	e.printStackTrace();return false;}
			return verifyVersion(version, versions);
		}
		
		return false;
	}
	
	protected boolean verifyVersion(int version, JSONArray versions){
		if(version<0){
			return false;
		}
		if(versions == null || versions.length()==0){
			return true;
		}
		for(int i=0;i<versions.length();i++){
			try {
				if(version == versions.getInt(i)){
					return false;
				}
			} catch (JSONException e) {
				continue;
			}
		}//We didn't find our version in the black list.
		return true;
	}
	
	/**
	 * Using the knowledge that all SDL enabled apps have an SDL Broadcast Receiver that has an intent filter that includes a specific 
	 * intent. 
	 * @return 
	 */
	private static List<SdlApp> findAllSdlApps(Context context){
		List<SdlApp> apps = new ArrayList<SdlApp>();
		PackageManager packageManager = context.getPackageManager();
		Intent intent = new Intent();
		intent.setAction(TransportConstants.START_ROUTER_SERVICE_ACTION);
		List<ResolveInfo> infoList = packageManager.queryBroadcastReceivers(intent, 0);
		//We want to sort our list so that we know it's the same everytime
		Collections.sort(infoList,new Comparator<ResolveInfo>() {
	        @Override
	        public int compare(ResolveInfo lhs, ResolveInfo rhs) {
	            return lhs.activityInfo.packageName.compareTo(rhs.activityInfo.packageName);
	        }
	    });
		if(infoList!=null){
			String packageName;
			for(ResolveInfo info : infoList){
				//Log.i(TAG, "SDL apps: " + info.activityInfo.packageName);
				packageName = info.activityInfo.packageName;
				try {
					apps.add(new SdlApp(packageName,packageManager.getPackageInfo(packageName,0).versionCode));
				} catch (NameNotFoundException e) {
					e.printStackTrace();
				}
			}
			
			return apps;
		}else{
			DebugTool.logInfo(TAG, "No SDL apps, list was null");
			return null;
		}
	}
	
	/**
	 * Performs a look up against installed SDL apps that support the router service.
	 * When it receives a list back from the server it will store it for later use.
	 * @param context
	 */
	public static boolean createTrustedListRequest(final Context context, boolean forceRefresh){
		return createTrustedListRequest(context,forceRefresh,null,null);
	}
	public static boolean createTrustedListRequest(final Context context, boolean forceRefresh, TrustedListCallback listCallback){DebugTool.logInfo(TAG,"Checking to make sure we have a list");
		return createTrustedListRequest(context,forceRefresh,null,listCallback);
	}
	
	@Deprecated
	protected static boolean createTrustedListRequest(final Context context, boolean forceRefresh,HttpRequestTask.HttpRequestTaskCallback cb ){
		return createTrustedListRequest(context,forceRefresh,cb,null);
	}
	
	protected static boolean createTrustedListRequest(final Context context, boolean forceRefresh,HttpRequestTask.HttpRequestTaskCallback cb, final TrustedListCallback listCallback ){
		if(context == null){
			return false;
		}
		else if(getSecurityLevel(context) == MultiplexTransportConfig.FLAG_MULTI_SECURITY_OFF){ //If security is off, we can just return now
			if(listCallback!=null){
				listCallback.onListObtained(true);
			}
			return false;
		}
		
		pendingListRefresh = true;
		//Might want to store a flag letting this class know a request is currently pending
		StringBuilder builder = new StringBuilder();
		builder.append(REQUEST_PREFIX);
		
		List<SdlApp> apps = findAllSdlApps(context);
		
		final JSONObject object = new JSONObject();
		JSONArray array = new JSONArray();
		JSONObject jsonApp;

		if(apps != null) {
			for (SdlApp app : apps) {    //Format all the apps into a JSON object and add it to the JSON array
				try {
					jsonApp = new JSONObject();
					jsonApp.put(JSON_APP_PACKAGE_TAG, app.packageName);
					jsonApp.put(JSON_APP_VERSION_TAG, app.versionCode);
					array.put(jsonApp);
				} catch (JSONException e) {
					e.printStackTrace();
					continue;
				}
			}
		}
		
		try {object.put(JSON_PUT_ARRAY_TAG, array);} catch (JSONException e) {e.printStackTrace();}
		
		if(!forceRefresh && (System.currentTimeMillis()-getTrustedAppListTimeStamp(context))<getRefreshRate()){ 
			if(object.toString().equals(getLastRequest(context))){
			//Our list should still be ok for now so we will skip the request
				pendingListRefresh = false;
				if(listCallback!=null){
					listCallback.onListObtained(true);
				}
				return false;
			}else{
				DebugTool.logInfo(TAG, "Sdl apps have changed. Need to request new trusted router service list.");
			}
		}
		
		if (cb == null) {
			cb = new HttpRequestTaskCallback() {

				@Override
				public void httpCallComplete(String response) {
					// Might want to check if this list is ok
					//Log.d(TAG, "APPS! " + response);
					setTrustedList(context, response);
					setLastRequest(context, object.toString()); //Save our last request 
					pendingListRefresh = false;
					if(listCallback!=null){listCallback.onListObtained(true);}
				}

				@Override
				public void httpFailure(int statusCode) {
					DebugTool.logError(TAG, "Error while requesting trusted app list: "
							+ statusCode);
					pendingListRefresh = false;
					if(listCallback!=null){listCallback.onListObtained(false);}
				}
			};
		}

		new HttpRequestTask(cb).execute(REQUEST_PREFIX,HttpRequestTask.REQUEST_TYPE_POST,object.toString(),"application/json","application/json");
		
		return true;
	}
	
	/**
	 * This method will determine if our supplied component name is really running. 
	 * @param context
	 * @param service
	 * @return
	 */
	protected boolean isServiceRunning(Context context, ComponentName service){
		 ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		    for (RunningServiceInfo serviceInfo : manager.getRunningServices(Integer.MAX_VALUE)) {
		        if (serviceInfo.service.equals(service)) {
		            return true;
		        }
		    }
		return false;
	}
	
	
	/**
	 * Parses a string into a JSON array
	 * @param json
	 * @return
	 */
	protected JSONObject stringToJson(String json){
		if(json==null){
			return stringToJson(DEFAULT_APP_LIST);
		}
		try {
			JSONObject object = new JSONObject(json);
			return object.getJSONObject(JSON_RESPONSE_OBJECT_TAG);
			
		} catch (JSONException e) {
			e.printStackTrace();
			if(!json.equalsIgnoreCase(DEFAULT_APP_LIST)){ //Since we were unable to parse, let's fall back to at least our last known good list. If this list is somehow messed up, just quit.
				return stringToJson(DEFAULT_APP_LIST);
			}else{
				return null;
			}
		}		
	}
	
	public static boolean invalidateList(Context context){
		if(context == null){
			return false;
		}
		SharedPreferences pref = context.getSharedPreferences(SDL, Context.MODE_PRIVATE);
		// Write the new prefs
		SharedPreferences.Editor prefAdd = pref.edit();
		prefAdd.putLong(SDL_PACKAGE_LIST_TIMESTAMP, 0); //This will be the last time we updated
		return prefAdd.commit();
	}
	/******************************************************************
	 * 
	 * Saving the list for later!!!
	 * 
	 ******************************************************************/

	/**
	 * Saves the list of available applications into user's shared prefs.
	 * @param context The application's environment
	 * @param jsonString The JSON string to save.
	 */
	protected static boolean setTrustedList(Context context, String jsonString){
		if(jsonString!=null && context!=null){
			SharedPreferences pref = context.getSharedPreferences(SDL, Context.MODE_PRIVATE);
			// Write the new prefs
    		SharedPreferences.Editor prefAdd = pref.edit();
    		prefAdd.putString(SDL_PACKAGE_LIST, jsonString);
    		prefAdd.putLong(SDL_PACKAGE_LIST_TIMESTAMP, System.currentTimeMillis()); //This will be the last time we updated
    		return prefAdd.commit();
		}
		return false;
	}

	/**
	 * Retrieves the list of available applications from user's shared prefs.
	 * @param context The application's environment.
	 * @return The JSON string that was retrieved.
	 */
	protected static String getTrustedList(Context context){
			if(context!=null){
				SharedPreferences pref = context.getSharedPreferences(SDL, Context.MODE_PRIVATE);
				return pref.getString(SDL_PACKAGE_LIST, DEFAULT_APP_LIST);
			}
			return null;
	}
	
	/**
	 * Retrieves the time stamp from the user's shared prefs.
	 * @param context The application's environment.
	 * @return The time stamp that was retrieved.
	 */
	protected static Long getTrustedAppListTimeStamp(Context context){
		if(context!=null){
			SharedPreferences pref = context.getSharedPreferences(SDL, Context.MODE_PRIVATE);
			return pref.getLong(SDL_PACKAGE_LIST_TIMESTAMP, 0);
		}
		return -1L;
	}

	protected static boolean setLastRequest(Context context, String request){
		if(context!=null){
			SharedPreferences pref = context.getSharedPreferences(SDL, Context.MODE_PRIVATE);
			SharedPreferences.Editor prefAdd = pref.edit();
			prefAdd.putString(SDL_LAST_REQUEST, request);
			return prefAdd.commit();
		}
		return false;
	}
	
	/**
	 * Gets the last request JSON object we sent to the RSVP server. It basically contains a list of sdl enabled apps
	 * @param context
	 * @return
	 */
	protected static String getLastRequest(Context context){
		if(context!=null){
			SharedPreferences pref = context.getSharedPreferences(SDL, Context.MODE_PRIVATE);
			return pref.getString(SDL_LAST_REQUEST, null);
		}
		return null;
	}
	
	protected static boolean cacheSecurityLevel(Context context, int securityLevel){
		if(context!=null){
			SharedPreferences pref = context.getSharedPreferences(SDL, Context.MODE_PRIVATE);
			SharedPreferences.Editor prefAdd = pref.edit();
			prefAdd.putInt(SDL_RSVP_SECURITY_LEVEL, securityLevel);
			return prefAdd.commit();
		}
		return false;
	}
	
	protected static int getSecurityLevel(Context context){
		if(context!=null){
			SharedPreferences pref = context.getSharedPreferences(SDL, Context.MODE_PRIVATE);
			return pref.getInt(SDL_RSVP_SECURITY_LEVEL, MultiplexTransportConfig.FLAG_MULTI_SECURITY_MED);
		}
		return MultiplexTransportConfig.FLAG_MULTI_SECURITY_MED;
	}
	
	/**
	 * Class that holds all the info we want to send/receive from the validation server
	 */
	public static class SdlApp{
		String packageName;
		int versionCode;
		
		SdlApp(String packageName, int versionCode){
			this.packageName = packageName;
			this.versionCode = versionCode;
		}
	}
	
	public static enum TrustedAppStore{
		PLAY_STORE("com.android.vending"),
		AMAZON("com.amazon.venezia"),
		XIAOMI("com.xiaomi.market"),
		SAMSUNG("com.sec.android.app.samsungapps"),
		WANDOUJIA("com.wandoujia.phoenix2"),
		BAIDU_APP_SEARCH("com.baidu.appsearch"),
		HIAPK("com.hiapk.marketpho"),
		;
		
		String packageString;
		private TrustedAppStore(String packageString){
			this.packageString = packageString;
		}
		
		/**
		 * Test if the supplied store package is one of the trusted app stores
		 * @param packageString
		 * @return
		 */
		public static boolean isTrustedStore(String packageString){
			if(packageString == null){
				return false;
			}
			TrustedAppStore[] stores = TrustedAppStore.values();
			for(int i =0; i<stores.length; i++){
				if(packageString.equalsIgnoreCase(stores[i].packageString)){
					return true;
				}
			}
			return false;
		}
		
	}
	/**
	 * This interface is used as a callback to know when we have either obtained a list or at least returned from our attempt.
	 *
	 */
	public static interface TrustedListCallback{
		public void onListObtained(boolean successful);
	}

}
