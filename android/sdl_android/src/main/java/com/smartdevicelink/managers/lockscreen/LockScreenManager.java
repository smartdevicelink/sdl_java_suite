/*
 * Copyright (c) 2019 Livio, Inc.
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

package com.smartdevicelink.managers.lockscreen;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;

import com.smartdevicelink.managers.BaseSubManager;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.OnDriverDistraction;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.OnSystemRequest;
import com.smartdevicelink.proxy.rpc.enums.DriverDistractionState;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.LockScreenStatus;
import com.smartdevicelink.proxy.rpc.enums.RequestType;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.util.AndroidTools;

import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * <strong>LockscreenManager</strong> <br>
 *
 * Note: This class must be accessed through the SdlManager. Do not instantiate it by itself. <br>
 *
 * The LockscreenManager handles the logic of showing and hiding the lock screen. <br>
 *
 */
public class LockScreenManager extends BaseSubManager {

	private static final String TAG = "LockScreenManager";
	private WeakReference<Context> context;
	private HMILevel hmiLevel;
	private OnRPCNotificationListener systemRequestListener, ddListener, hmiListener;
	private String deviceIconUrl;
	private boolean driverDistStatus;
	private volatile boolean isApplicationForegrounded;
	private android.arch.lifecycle.LifecycleObserver lifecycleObserver;
	protected boolean lockScreenEnabled, deviceLogoEnabled;
	protected int lockScreenIcon, lockScreenColor, customView;
	protected Bitmap deviceLogo;

	public LockScreenManager(LockScreenConfig lockScreenConfig, Context context, ISdl internalInterface){

		super(internalInterface);
		this.context = new WeakReference<>(context);

		// set initial class variables
		hmiLevel = HMILevel.HMI_NONE;
		driverDistStatus = false;

		// setup the manager
		lockScreenIcon = lockScreenConfig.getAppIcon();
		lockScreenColor = lockScreenConfig.getBackgroundColor();
		customView = lockScreenConfig.getCustomView();
		lockScreenEnabled = lockScreenConfig.isEnabled();
		deviceLogoEnabled = lockScreenConfig.isDeviceLogoEnabled();

		setupListeners();
	}

	@Override
	public void start(CompletionListener listener) {
		transitionToState(READY);
		super.start(listener);
	}

	@Override
	public void dispose(){
		// send broadcast to close lock screen if open
		if (context.get() != null) {
			context.get().sendBroadcast(new Intent(SDLLockScreenActivity.CLOSE_LOCK_SCREEN_ACTION));
		}
		// remove listeners
		internalInterface.removeOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, hmiListener);
		internalInterface.removeOnRPCNotificationListener(FunctionID.ON_DRIVER_DISTRACTION, ddListener);
		if (deviceLogoEnabled) {
			internalInterface.removeOnRPCNotificationListener(FunctionID.ON_SYSTEM_REQUEST, systemRequestListener);
		}
		deviceLogo = null;
		deviceIconUrl = null;

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			try {
				if (android.arch.lifecycle.ProcessLifecycleOwner.get() != null && lifecycleObserver != null) {
					android.arch.lifecycle.ProcessLifecycleOwner.get().getLifecycle().removeObserver(lifecycleObserver);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			lifecycleObserver = null;
		}

		isApplicationForegrounded = false;

		super.dispose();
	}

	////
	// SETUP
	////

	/**
	 * Adds 3 listeners that help determine whether or not a lockscreen should be shown.
	 * This will change the variables that we hold in the manager to the newest values and then
	 * usually call launchLockScreenActivity
	 *
	 * 1. ON_HMI_STATUS
	 * 2. ON_DRIVER_DISTRACTION
	 * 3. ON_SYSTEM_REQUEST (used for device Icon Downloading)
	 */
	private void setupListeners(){
		// add hmi listener
		hmiListener = new OnRPCNotificationListener() {
			@Override
			public void onNotified(RPCNotification notification) {
				hmiLevel = ((OnHMIStatus)notification).getHmiLevel();
				launchLockScreenActivity();
			}
		};
		internalInterface.addOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, hmiListener);

		// set up driver distraction listener
		ddListener = new OnRPCNotificationListener() {
			@Override
			public void onNotified(RPCNotification notification) {
				// do something with the status
				if (notification != null) {
					OnDriverDistraction ddState = (OnDriverDistraction) notification;

					if (ddState.getState() == DriverDistractionState.DD_ON){
						// launch lock screen
						driverDistStatus = true;
						launchLockScreenActivity();
					}else{
						// close lock screen
						driverDistStatus = false;
						if (context.get() != null) {
							context.get().sendBroadcast(new Intent(SDLLockScreenActivity.CLOSE_LOCK_SCREEN_ACTION));
						}
					}
				}
			}
		};
		internalInterface.addOnRPCNotificationListener(FunctionID.ON_DRIVER_DISTRACTION, ddListener);

		// set up system request listener
		if (deviceLogoEnabled) {
			systemRequestListener = new OnRPCNotificationListener() {
				@Override
				public void onNotified(RPCNotification notification) {
					// do something with the status
					final OnSystemRequest msg = (OnSystemRequest) notification;
					if (msg.getRequestType() == RequestType.LOCK_SCREEN_ICON_URL &&
							msg.getUrl() != null) {
						// send intent to activity to download icon from core
						deviceIconUrl = msg.getUrl();
						downloadDeviceIcon(deviceIconUrl);
					}
				}
			};
			internalInterface.addOnRPCNotificationListener(FunctionID.ON_SYSTEM_REQUEST, systemRequestListener);
		}

		// Set up listener for Application Foreground / Background events
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			try {
				lifecycleObserver = new android.arch.lifecycle.LifecycleObserver() {
					@android.arch.lifecycle.OnLifecycleEvent(android.arch.lifecycle.Lifecycle.Event.ON_START)
					public void onMoveToForeground() {
						isApplicationForegrounded = true;
						launchLockScreenActivity();
					}

					@android.arch.lifecycle.OnLifecycleEvent(android.arch.lifecycle.Lifecycle.Event.ON_STOP)
					public void onMoveToBackground() {
						isApplicationForegrounded = false;
					}
				};

				if (android.arch.lifecycle.ProcessLifecycleOwner.get() != null) {
					android.arch.lifecycle.ProcessLifecycleOwner.get().getLifecycle().addObserver(lifecycleObserver);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else{
			isApplicationForegrounded = true;
		}
	}

	////
	// LAUNCH LOCK SCREEN LOGIC
	////

	/**
	 * 1. Check if user wants us to manage lock screen
	 * 2. If so, get the HMI level and LockScreenStatus from the method below
	 * 3. Build intent and start the SDLLockScreenActivity
	 *
	 * X. If the status is set to OFF, Send broadcast to close lock screen if it is open
	 */
	private void launchLockScreenActivity(){
		// intent to open SDLLockScreenActivity
		// pass in icon, background color, and custom view
		if (lockScreenEnabled && isApplicationForegrounded && context.get() != null) {
			LockScreenStatus status = getLockScreenStatus();
			if (status == LockScreenStatus.REQUIRED) {
				Intent showLockScreenIntent = new Intent(context.get(), SDLLockScreenActivity.class);
				showLockScreenIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

				// Extra parameters for customization of the lock screen view
				showLockScreenIntent.putExtra(SDLLockScreenActivity.LOCKSCREEN_ICON_EXTRA, lockScreenIcon);
				showLockScreenIntent.putExtra(SDLLockScreenActivity.LOCKSCREEN_COLOR_EXTRA, lockScreenColor);
				showLockScreenIntent.putExtra(SDLLockScreenActivity.LOCKSCREEN_CUSTOM_VIEW_EXTRA, customView);
				showLockScreenIntent.putExtra(SDLLockScreenActivity.LOCKSCREEN_DEVICE_LOGO_EXTRA, deviceLogoEnabled);
				showLockScreenIntent.putExtra(SDLLockScreenActivity.LOCKSCREEN_DEVICE_LOGO_BITMAP, deviceLogo);
				context.get().startActivity(showLockScreenIntent);
			} else if (status == LockScreenStatus.OFF) {
				context.get().sendBroadcast(new Intent(SDLLockScreenActivity.CLOSE_LOCK_SCREEN_ACTION));
			}
		}
	}

	////
	// HELPERS
	////

	/**
	 * Step through some logic to determine if we need to show the lock screen or not
	 * This function is usually triggered on some sort of notification.
	 *
	 * @return Whether or not the Lock Screen is required
	 */
	protected synchronized LockScreenStatus getLockScreenStatus() {

		if ( (hmiLevel == null) || (hmiLevel.equals(HMILevel.HMI_NONE))) {
			return LockScreenStatus.OFF;
		}
		else if ( hmiLevel.equals(HMILevel.HMI_BACKGROUND)) {
			if (!driverDistStatus) {
				//we don't have driver distraction, lock screen is entirely based on if user is using the app on the head unit
				return LockScreenStatus.OFF;
			} else {
				return LockScreenStatus.REQUIRED;
			}
		}
		else if ( (hmiLevel.equals(HMILevel.HMI_FULL)) || (hmiLevel.equals(HMILevel.HMI_LIMITED))) {
			if (!driverDistStatus) {
				return LockScreenStatus.OPTIONAL;
			} else {
				return LockScreenStatus.REQUIRED;
			}
		}
		return LockScreenStatus.OFF;
	}

	private void downloadDeviceIcon(final String url){

		if (deviceLogo != null || context.get() == null){
			return;
		}

		new Thread(new Runnable(){
			@Override
			public void run(){
				try{
					deviceLogo = AndroidTools.downloadImage(url);
					Intent intent = new Intent(SDLLockScreenActivity.LOCKSCREEN_DEVICE_LOGO_DOWNLOADED);
					intent.putExtra(SDLLockScreenActivity.LOCKSCREEN_DEVICE_LOGO_EXTRA, deviceLogoEnabled);
					intent.putExtra(SDLLockScreenActivity.LOCKSCREEN_DEVICE_LOGO_BITMAP, deviceLogo);
					if (context.get() != null) {
						context.get().sendBroadcast(intent);
					}
				}catch(IOException e){
					Log.e(TAG, "device Icon Error Downloading");
				}
			}
		}).start();
	}

}
