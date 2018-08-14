package com.smartdevicelink.api.lockscreen;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
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
import com.smartdevicelink.util.HttpUtils;

import org.json.JSONException;

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
	private boolean driverDistStatus, lockScreenEnabled, showDisplayDeviceLogo;
	private int lockScreenIcon, lockScreenColor, customView;
	private OnRPCNotificationListener systemRequestListener, ddListener, hmiListener;
	private String OEMIconUrl;
	private Bitmap lockScreenOEMIcon;

	public LockScreenManager(LockScreenConfig lockScreenConfig, Context context, ISdl internalInterface){

		super(internalInterface);
		this.context = new WeakReference<>(context);

		// set initial class variables
		hmiLevel = HMILevel.HMI_NONE;
		driverDistStatus = false;

		// setup the manager
		readConfiguration(lockScreenConfig);
		setupListeners();

		// transition state
		transitionToState(READY);
	}

	@Override
	public void dispose(){
		// send broadcast to close lock screen if open
		context.get().sendBroadcast(new Intent(SDLLockScreenActivity.CLOSE_LOCK_SCREEN_ACTION));
		// remove listeners
		internalInterface.removeOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, hmiListener);
		internalInterface.removeOnRPCNotificationListener(FunctionID.ON_DRIVER_DISTRACTION, ddListener);
		if (showDisplayDeviceLogo) {
			internalInterface.removeOnRPCNotificationListener(FunctionID.ON_SYSTEM_REQUEST, systemRequestListener);
		}
		lockScreenOEMIcon = null;
		OEMIconUrl = null;

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
		lockScreenColor = lockScreenConfig.getBackgroundColor();
		customView = lockScreenConfig.getCustomView();
		lockScreenEnabled = lockScreenConfig.isEnabled();
		showDisplayDeviceLogo = lockScreenConfig.getDisplayDeviceLogo();
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
						context.get().sendBroadcast(new Intent(SDLLockScreenActivity.CLOSE_LOCK_SCREEN_ACTION));
					}
				}
			}
		};
		internalInterface.addOnRPCNotificationListener(FunctionID.ON_DRIVER_DISTRACTION, ddListener);

		// set up system request listener
		if (showDisplayDeviceLogo) {
			systemRequestListener = new OnRPCNotificationListener() {
				@Override
				public void onNotified(RPCNotification notification) {
					// do something with the status
					final OnSystemRequest msg = (OnSystemRequest) notification;
					try {
						Log.i(TAG, "SYSTEM REQUEST: " + notification.serializeJSON().toString());
					} catch (JSONException e) {
						e.printStackTrace();
					}
					if (msg.getRequestType() == RequestType.LOCK_SCREEN_ICON_URL &&
							msg.getUrl() != null) {
						// send intent to activity to download icon from core
						Log.i(TAG, "SYSTEM REQUEST - ICON Ready for Download");
						OEMIconUrl = msg.getUrl();
						downloadLockScreenIcon(OEMIconUrl);
					}
				}
			};
			internalInterface.addOnRPCNotificationListener(FunctionID.ON_SYSTEM_REQUEST, systemRequestListener);
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
		if (lockScreenEnabled && shouldShowNotification()) {
			LockScreenStatus status = getLockScreenStatus();
			if (hmiLevel == HMILevel.HMI_FULL && status == LockScreenStatus.REQUIRED) {
				Intent showLockScreenIntent = new Intent(context.get(), SDLLockScreenActivity.class);
				showLockScreenIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

				// Extra parameters for customization of the lock screen view
				showLockScreenIntent.putExtra(SDLLockScreenActivity.LOCKSCREEN_ICON_EXTRA, lockScreenIcon);
				showLockScreenIntent.putExtra(SDLLockScreenActivity.LOCKSCREEN_COLOR_EXTRA, lockScreenColor);
				showLockScreenIntent.putExtra(SDLLockScreenActivity.LOCKSCREEN_CUSTOM_VIEW_EXTRA, customView);
				showLockScreenIntent.putExtra(SDLLockScreenActivity.LOCKSCREEN_DEVICE_ICON_EXTRA, showDisplayDeviceLogo);
				showLockScreenIntent.putExtra(SDLLockScreenActivity.LOCKSCREEN_DEVICE_ICON_BITMAP, lockScreenOEMIcon);

				context.get().startActivity(showLockScreenIntent);
			} else if (status == LockScreenStatus.OFF) {
				context.get().sendBroadcast(new Intent(SDLLockScreenActivity.CLOSE_LOCK_SCREEN_ACTION));
			}
		}
	}

	private static boolean shouldShowNotification() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			ActivityManager.RunningAppProcessInfo myProcess = new ActivityManager.RunningAppProcessInfo();
			ActivityManager.getMyMemoryState(myProcess);
			return myProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
		}
		return true;
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
	private synchronized LockScreenStatus getLockScreenStatus() {

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

	private void downloadLockScreenIcon(final String url){
		new Thread(new Runnable(){
			@Override
			public void run(){
				try{
					lockScreenOEMIcon = HttpUtils.downloadImage(url);
					Log.v(TAG, "Lock Screen Icon Downloaded");
					Intent intent = new Intent(SDLLockScreenActivity.LOCKSCREEN_ICON_DOWNLOADED);
					intent.putExtra(SDLLockScreenActivity.LOCKSCREEN_DEVICE_ICON_EXTRA, showDisplayDeviceLogo);
					intent.putExtra(SDLLockScreenActivity.LOCKSCREEN_DEVICE_ICON_BITMAP, lockScreenOEMIcon);
					context.get().sendBroadcast(intent);
				}catch(IOException e){
					Log.e(TAG, "Lock Screen Icon Error Downloading");
				}
			}
		}).start();
	}

	// protected getters for testing class
	protected int getLockScreenIcon(){
		return lockScreenIcon;
	}

	protected int getLockScreenColor(){
		return lockScreenColor;
	}

	protected int getCustomView(){
		return customView;
	}

	protected boolean getDisplayDeviceLogo(){
		return showDisplayDeviceLogo;
	}

	protected boolean getLockScreenEnabled(){
		return lockScreenEnabled;
	}

	protected Bitmap getLockScreenOEMIcon(){
		return lockScreenOEMIcon;
	}

	protected LockScreenStatus testGetLockScreenStatus(){
		return getLockScreenStatus();
	}
}
