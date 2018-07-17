package com.smartdevicelink.api.lockscreen;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

import com.smartdevicelink.api.BaseSubManager;
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
	private boolean isAppInUse, driverDistStatus, lockScreenEnabled;
	private int lockScreenIcon, lockscreenColor, customView;
	private OnRPCNotificationListener systemRequestListener, ddListener, hmiListener;

	public interface OnLockScreenIconDownloadedListener {
		void onLockScreenIconDownloaded(Bitmap icon);
		void onLockScreenIconDownloadError(Exception e);
	}

	public LockScreenManager(LockScreenConfig lockScreenConfig, Context context, ISdl internalInterface){

		super(internalInterface);
		this.context = new WeakReference<>(context);

		// set initial class variables
		hmiLevel = HMILevel.HMI_NONE;
		isAppInUse = false;
		driverDistStatus = false;

		// setup the manager
		readConfiguration(lockScreenConfig);
		setupListeners();

		// transition state
		transitionToState(READY);
	}

	@Override
	public void dispose(){
		// remove listeners
		internalInterface.removeOnRPCNotificationListener(FunctionID.ON_SYSTEM_REQUEST, systemRequestListener);
		internalInterface.removeOnRPCNotificationListener(FunctionID.ON_DRIVER_DISTRACTION, ddListener);
		internalInterface.removeOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, hmiListener);

		// transition state
		transitionToState(SHUTDOWN);
	}

	////
	// SETUP
	////

	/**
	 * Reads configuration set in SDLManager Builder
	 * @param lockScreenConfig - the configuration set.
	 */
	private void readConfiguration(LockScreenConfig lockScreenConfig){
		lockScreenIcon = lockScreenConfig.getAppIcon();
		lockscreenColor = lockScreenConfig.getBackgroundColor();
		customView = lockScreenConfig.getCustomView();
		lockScreenEnabled = lockScreenConfig.getEnabled();
	}

	/**
	 * Adds 3 listeners that help determine whether or not a lockscreen should be shown.
	 * This will change the variables that we hold in the manager to the newest values and then
	 * usually call launchLockScreenActivity
	 *
	 * 1. ON_HMI_STATUS
	 * 2. ON_DRIVER_DISTRACTION
	 * 3. ON_SYSTEM_REQUEST (used for OEM Icon Downloading)
	 */
	private void setupListeners(){
		// add hmi listener
		hmiListener = new OnRPCNotificationListener() {
			@Override
			public void onNotified(RPCNotification notification) {
				hmiLevel = ((OnHMIStatus)notification).getHmiLevel();
				if (hmiLevel.equals(HMILevel.HMI_FULL) || hmiLevel.equals(HMILevel.HMI_LIMITED)){
					Log.i(TAG, "HMI Notification APP_IN_USE True");
					isAppInUse = true;
				}else{
					Log.i(TAG, "HMI Notification APP_IN_USE False");
					isAppInUse = false;
				}
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
						Log.i(TAG, "DD On Notification");
						// launch lock screen
						driverDistStatus = true;
						launchLockScreenActivity();
					}else{
						Log.i(TAG, "DD Off Notification");
						// close lock screen
						driverDistStatus = false;
					}
				}
			}
		};
		internalInterface.addOnRPCNotificationListener(FunctionID.ON_DRIVER_DISTRACTION, ddListener);

		// set up system request listener
		systemRequestListener = new OnRPCNotificationListener() {
			@Override
			public void onNotified(RPCNotification notification) {
				// do something with the status
				final OnSystemRequest msg = (OnSystemRequest) notification;
				if (msg.getRequestType() == RequestType.LOCK_SCREEN_ICON_URL &&
						msg.getUrl() != null) {
					// send intent to activity to download icon from core
					Log.i(TAG, "SYSTEM REQUEST - ICON Ready for Download");
					sendDownloadIconBroadcast(msg.getUrl());
				}
			}
		};
		internalInterface.addOnRPCNotificationListener(FunctionID.ON_SYSTEM_REQUEST, systemRequestListener);
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
		if (lockScreenEnabled) {
			LockScreenStatus status = getLockScreenStatus();
			if (hmiLevel == HMILevel.HMI_FULL && status == LockScreenStatus.REQUIRED) {
				Intent showLockScreenIntent = new Intent(context.get(), SDLLockScreenActivity.class);
				showLockScreenIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

				// Extra parameters for customization of the lock screen view
				// Because each of them is an int primitive, we can send without null checks
				// Not being set will have an equivalent of 0
				showLockScreenIntent.putExtra(SDLLockScreenActivity.LOCKSCREEN_ICON_EXTRA, lockScreenIcon);
				showLockScreenIntent.putExtra(SDLLockScreenActivity.LOCKSCREEN_COLOR_EXTRA, lockscreenColor);
				showLockScreenIntent.putExtra(SDLLockScreenActivity.LOCKSCREEN_CUSTOM_VIEW_EXTRA, customView);

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
	 * Sends Broadcast to Download OEM Icon.
	 * Currently that method is in SDLLockScreenActivity
	 * @param URL - URL of Icon
	 */
	private void sendDownloadIconBroadcast(String URL){
		Intent intent = new Intent(SDLLockScreenActivity.DOWNLOAD_ICON_ACTION);
		intent.putExtra(SDLLockScreenActivity.DOWNLOAD_ICON_URL, URL);
		context.get().sendBroadcast(intent);
	}

	/**
	 * Step through some logic to determine if we need to show the lock screen or not
	 * This function is usually triggered on some sort of notification.
	 *
	 * @return Whether or not the Lock Screen is required
	 */
	private synchronized LockScreenStatus getLockScreenStatus() {

		if ( (hmiLevel == null) || (hmiLevel.equals(HMILevel.HMI_NONE))) {
			return LockScreenStatus.OFF;
		}
		else if ( hmiLevel.equals(HMILevel.HMI_BACKGROUND)) {
			if (!driverDistStatus) {
				//we don't have driver distraction, lock screen is entirely based on if user is using the app on the head unit
				if (isAppInUse) {
					return LockScreenStatus.REQUIRED;
				} else {
					return LockScreenStatus.OFF;
				}
			}
			else if (isAppInUse) {
				return LockScreenStatus.REQUIRED;
			} else {
				return LockScreenStatus.OFF;
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

}
