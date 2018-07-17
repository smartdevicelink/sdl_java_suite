package com.smartdevicelink.api.lockscreen;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

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

	private LockScreenConfig lockScreenConfig;
	private WeakReference<Context> context;
	private HMILevel hmiLevel;
	private boolean isAppInUse, driverDistStatus;
	private int lockScreenIcon, lockscreenColor, customView;
	private OnRPCNotificationListener systemRequestListener, ddListener, hmiListener;

	public interface OnLockScreenIconDownloadedListener{
		public void onLockScreenIconDownloaded(Bitmap icon);
		public void onLockScreenIconDownloadError(Exception e);
	}

	public LockScreenManager(LockScreenConfig lockScreenConfig, Context context, ISdl internalInterface){

		super(internalInterface);

		this.context = new WeakReference<>(context);
		this.lockScreenConfig = lockScreenConfig;
		hmiLevel = HMILevel.HMI_NONE;
		isAppInUse = false;
		driverDistStatus = false;

		// add hmi listener
		hmiListener = new OnRPCNotificationListener() {
			@Override
			public void onNotified(RPCNotification notification) {
				hmiLevel = ((OnHMIStatus)notification).getHmiLevel();
				if (hmiLevel.equals(HMILevel.HMI_FULL) || hmiLevel.equals(HMILevel.HMI_LIMITED)){
					isAppInUse = true;
				}
				launchLockScreenActivity();
			}
		};
		internalInterface.addOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, hmiListener);

		//set up driver distraction listener
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
					}
				}
			}
		};
		internalInterface.addOnRPCNotificationListener(FunctionID.ON_DRIVER_DISTRACTION, ddListener);

		//set up driver distraction listener
		systemRequestListener = new OnRPCNotificationListener() {
			@Override
			public void onNotified(RPCNotification notification) {
				// do something with the status
				final OnSystemRequest msg = (OnSystemRequest) notification;
				if (msg.getRequestType() == RequestType.LOCK_SCREEN_ICON_URL &&
						msg.getUrl() != null) {
					// send intent to activity to dl icon from core
					sendDownloadIconBroadcast(msg.getUrl());
				}
			}
		};
		internalInterface.addOnRPCNotificationListener(FunctionID.ON_SYSTEM_REQUEST, systemRequestListener);

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

	private void launchLockScreenActivity(){
		// intent to open SDLLockScreenActivity
		// pass in icon, background color, and custom view
		if (lockScreenConfig.getEnabled()) {
			LockScreenStatus status = getLockScreenStatus();
			if (hmiLevel == HMILevel.HMI_FULL && status == LockScreenStatus.REQUIRED) {
				Intent showLockScreenIntent = new Intent(context.get(), SDLLockScreenActivity.class);
				showLockScreenIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

				// Extra parameters for customization of the lockscreen view
				// Because each of them is an int primitive, we can send without null checks
				// Not being set will have an equivalent of 0
				showLockScreenIntent.putExtra("icon", lockScreenIcon);
				showLockScreenIntent.putExtra("color", lockscreenColor);
				showLockScreenIntent.putExtra("customView", customView);

				context.get().startActivity(showLockScreenIntent);
			} else if (status == LockScreenStatus.OFF) {
				context.get().sendBroadcast(new Intent(SDLLockScreenActivity.CLOSE_LOCK_SCREEN_ACTION));
			}
		}
	}

	private void sendDownloadIconBroadcast(String URL){
		Intent intent = new Intent(SDLLockScreenActivity.DOWNLOAD_ICON_ACTION);
		intent.putExtra("URL", URL);
		context.get().sendBroadcast(intent);
	}

	private synchronized LockScreenStatus getLockScreenStatus() {

		if ( (hmiLevel == null) || (hmiLevel.equals(HMILevel.HMI_NONE)) )
		{
			return LockScreenStatus.OFF;
		}
		else if ( hmiLevel.equals(HMILevel.HMI_BACKGROUND) )
		{
			if (!driverDistStatus)
			{
				//we don't have driver distraction, lockscreen is entirely based on userselection
				if (isAppInUse)
					return LockScreenStatus.REQUIRED;
				else
					return LockScreenStatus.OFF;
			}
			else if (driverDistStatus && isAppInUse)
			{
				return LockScreenStatus.REQUIRED;
			}
			else
			{
				return LockScreenStatus.OFF;
			}
		}
		else if ( (hmiLevel.equals(HMILevel.HMI_FULL)) || (hmiLevel.equals(HMILevel.HMI_LIMITED)) )
		{
			if (!driverDistStatus)
			{
				return LockScreenStatus.OPTIONAL;
			}
			else
				return LockScreenStatus.REQUIRED;
		}
		return LockScreenStatus.OFF;
	}

}
