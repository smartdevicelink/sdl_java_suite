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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.RestrictTo;

import com.smartdevicelink.managers.BaseSubManager;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.OnDriverDistraction;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.OnSystemRequest;
import com.smartdevicelink.proxy.rpc.enums.DriverDistractionState;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.LockScreenStatus;
import com.smartdevicelink.proxy.rpc.enums.PredefinedWindows;
import com.smartdevicelink.proxy.rpc.enums.RequestType;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.util.AndroidTools;
import com.smartdevicelink.util.DebugTool;

import java.lang.ref.WeakReference;

/**
 * <strong>LockscreenManager</strong> <br>
 * <p>
 * Note: This class must be accessed through the SdlManager. Do not instantiate it by itself. <br>
 * <p>
 * The LockscreenManager handles the logic of showing and hiding the lock screen. <br>
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public class LockScreenManager extends BaseSubManager {

    private static final String TAG = "LockScreenManager";
    private final Object LOCKSCREEN_LAUNCH_LOCK = new Object();
    private final WeakReference<Context> context;
    HMILevel hmiLevel;
    private OnRPCNotificationListener systemRequestListener, ddListener, hmiListener;
    private String deviceIconUrl;
    boolean driverDistStatus;
    boolean isLockscreenDismissible = false;
    boolean enableDismissGesture;
    final boolean lockScreenEnabled;
    final boolean deviceLogoEnabled;
    private volatile boolean isApplicationForegrounded;
    private androidx.lifecycle.LifecycleObserver lifecycleObserver;
    final int lockScreenIcon;
    final int lockScreenColor;
    final int customView;
    int displayMode;
    Bitmap deviceLogo;
    private boolean lockscreenDismissReceiverRegistered, receivedFirstDDNotification;
    boolean mLockScreenShouldBeAutoDismissed;
    private String mLockscreenWarningMsg;
    BroadcastReceiver mLockscreenDismissedReceiver;
    private final LockScreenDeviceIconManager mLockScreenDeviceIconManager;
    private String lastIntentUsed;

    public LockScreenManager(LockScreenConfig lockScreenConfig, Context context, ISdl internalInterface) {

        super(internalInterface);
        this.context = new WeakReference<>(context);
        this.mLockScreenDeviceIconManager = new LockScreenDeviceIconManager(context);


        // set initial class variables
        hmiLevel = HMILevel.HMI_NONE;
        driverDistStatus = false;

        // setup the manager
        lockScreenIcon = lockScreenConfig.getAppIcon();
        lockScreenColor = lockScreenConfig.getBackgroundColor();
        customView = lockScreenConfig.getCustomView();
        lockScreenEnabled = lockScreenConfig.getDisplayMode() != LockScreenConfig.DISPLAY_MODE_NEVER;
        deviceLogoEnabled = lockScreenConfig.isDeviceLogoEnabled();
        displayMode = lockScreenConfig.getDisplayMode();
        enableDismissGesture = lockScreenConfig.enableDismissGesture();

        // for older projects that may not use DisplayMode. This can
        // be removed in a major release
        if (!lockScreenEnabled) {
            displayMode = LockScreenConfig.DISPLAY_MODE_NEVER;
        }

        setupListeners();
    }

    @Override
    public void start(CompletionListener listener) {
        transitionToState(READY);
        super.start(listener);
    }

    @Override
    public void dispose() {
        // send broadcast to close lock screen if open
        if (context.get() != null) {
            Intent intent = new Intent(SDLLockScreenActivity.CLOSE_LOCK_SCREEN_ACTION)
                    .setPackage(context.get().getPackageName());
            context.get().sendBroadcast(intent);
            try {
                context.get().unregisterReceiver(mLockscreenDismissedReceiver);
                lockscreenDismissReceiverRegistered = false;
            } catch (IllegalArgumentException e) {
                //do nothing
            }
        }
        // remove listeners
        internalInterface.removeOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, hmiListener);
        internalInterface.removeOnRPCNotificationListener(FunctionID.ON_DRIVER_DISTRACTION, ddListener);
        if (deviceLogoEnabled) {
            internalInterface.removeOnRPCNotificationListener(FunctionID.ON_SYSTEM_REQUEST, systemRequestListener);
        }
        deviceLogo = null;
        deviceIconUrl = null;

        try {
            if (androidx.lifecycle.ProcessLifecycleOwner.get() != null && lifecycleObserver != null) {
                androidx.lifecycle.ProcessLifecycleOwner.get().getLifecycle().removeObserver(lifecycleObserver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        lifecycleObserver = null;

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
     * <p>
     * 1. ON_HMI_STATUS
     * 2. ON_DRIVER_DISTRACTION
     * 3. ON_SYSTEM_REQUEST (used for device Icon Downloading)
     */
    private void setupListeners() {
        // add hmi listener
        hmiListener = new OnRPCNotificationListener() {
            @Override
            public void onNotified(RPCNotification notification) {
                OnHMIStatus onHMIStatus = (OnHMIStatus) notification;
                if (onHMIStatus.getWindowID() != null && onHMIStatus.getWindowID() != PredefinedWindows.DEFAULT_WINDOW.getValue()) {
                    return;
                }
                hmiLevel = onHMIStatus.getHmiLevel();
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
                    synchronized (this) {
                        OnDriverDistraction ddState = (OnDriverDistraction) notification;
                        driverDistStatus = DriverDistractionState.DD_ON.equals(ddState.getState());
                        mLockscreenWarningMsg = ddState.getLockscreenWarningMessage();
                        boolean previousDismissibleState = isLockscreenDismissible;
                        if (ddState.getLockscreenDismissibility() != null) {
                            isLockscreenDismissible = ddState.getLockscreenDismissibility();
                        } //If the param is null, we assume it stays as the previous value

                        DebugTool.logInfo(TAG, "Lock screen dismissible: " + isLockscreenDismissible);

                        if (displayMode == LockScreenConfig.DISPLAY_MODE_ALWAYS) {
                            //If the lockscreen is DISPLAY_MODE_ALWAYS, the only thing that matters
                            // is the dismissible state
                            if (enableDismissGesture) {
                                //Check if there was a change to the dismissible state
                                if (isLockscreenDismissible != previousDismissibleState) {
                                    // if DisplayMode is set to ALWAYS, it will be shown before the first DD notification.
                                    // If this is our first DD notification and we are in ALWAYS mode, send another intent to
                                    // enable the dismissal. There is a delay added to allow time for the activity
                                    // time to completely start and handle the new intent. There seems to be odd behavior
                                    // in Android when startActivity is called multiple times too quickly.
                                    if (previousDismissibleState) {
                                        // If lockscreen was dismissible, got dismissed by the user, then became not dismissible, the lockscreen activity should be allowed to launch again
                                        // https://github.com/smartdevicelink/sdl_java_suite/issues/1695
                                        mLockScreenShouldBeAutoDismissed = false;
                                    }
                                    if (!receivedFirstDDNotification) {
                                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                launchLockScreenActivity();
                                            }
                                        }, 1000);
                                    } else {
                                        launchLockScreenActivity();
                                    }
                                }
                            }
                            receivedFirstDDNotification = true;
                            return;
                        }

                        //For all other states the logic will be handled in the launchLockScreenActivity method call
                        if (driverDistStatus) {
                            // launch lock screen
                            launchLockScreenActivity();
                        } else {
                            // close lock screen
                            closeLockScreenActivity();
                        }
                        receivedFirstDDNotification = true;
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
                        deviceIconUrl = msg.getUrl().replace("http://", "https://");
                        downloadDeviceIcon(deviceIconUrl);
                    }
                }
            };
            internalInterface.addOnRPCNotificationListener(FunctionID.ON_SYSTEM_REQUEST, systemRequestListener);
        }

        // Set up listener for Application Foreground / Background events
        try {
            lifecycleObserver = new androidx.lifecycle.LifecycleObserver() {
                @androidx.lifecycle.OnLifecycleEvent(androidx.lifecycle.Lifecycle.Event.ON_START)
                public void onMoveToForeground() {
                    isApplicationForegrounded = true;
                    launchLockScreenActivity();
                }

                @androidx.lifecycle.OnLifecycleEvent(androidx.lifecycle.Lifecycle.Event.ON_STOP)
                public void onMoveToBackground() {
                    isApplicationForegrounded = false;
                    lastIntentUsed = null;
                }
            };

            if (androidx.lifecycle.ProcessLifecycleOwner.get() != null) {
                androidx.lifecycle.ProcessLifecycleOwner.get().getLifecycle().addObserver(lifecycleObserver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mLockscreenDismissedReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (SDLLockScreenActivity.KEY_LOCKSCREEN_DISMISSED.equals(intent.getAction())) {
                    mLockScreenShouldBeAutoDismissed = true;
                    lastIntentUsed = null;
                }
            }
        };
    }


    ////
    // LAUNCH LOCK SCREEN LOGIC
    ////

    /**
     * 1. Check if user wants us to manage lock screen
     * 2. If so, get the HMI level and LockScreenStatus from the method below
     * 3. Build intent and start the SDLLockScreenActivity
     * <p>
     * X. If the status is set to OFF, Send broadcast to close lock screen if it is open
     */
    private void launchLockScreenActivity() {
        synchronized (LOCKSCREEN_LAUNCH_LOCK) {
            // If the user has dismissed the lockscreen for this run or has disabled it, do not show it
            if (mLockScreenShouldBeAutoDismissed || displayMode == LockScreenConfig.DISPLAY_MODE_NEVER) {
                return;
            }
            // intent to open SDLLockScreenActivity
            // pass in icon, background color, and custom view
            if (lockScreenEnabled && isApplicationForegrounded && context.get() != null) {
                if (isLockscreenDismissible && !lockscreenDismissReceiverRegistered) {
                    AndroidTools.registerReceiver(context.get(), mLockscreenDismissedReceiver,
                            new IntentFilter(SDLLockScreenActivity.KEY_LOCKSCREEN_DISMISSED),
                            Context.RECEIVER_NOT_EXPORTED);
                    lockscreenDismissReceiverRegistered = true;

                }
                LockScreenStatus status = getLockScreenStatus();
                if (status == LockScreenStatus.REQUIRED || displayMode == LockScreenConfig.DISPLAY_MODE_ALWAYS || (status == LockScreenStatus.OPTIONAL && displayMode == LockScreenConfig.DISPLAY_MODE_OPTIONAL_OR_REQUIRED)) {
                    Intent showLockScreenIntent = new Intent(context.get(), SDLLockScreenActivity.class);
                    showLockScreenIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    // Extra parameters for customization of the lock screen view
                    showLockScreenIntent.putExtra(SDLLockScreenActivity.LOCKSCREEN_ICON_EXTRA, lockScreenIcon);
                    showLockScreenIntent.putExtra(SDLLockScreenActivity.LOCKSCREEN_COLOR_EXTRA, lockScreenColor);
                    showLockScreenIntent.putExtra(SDLLockScreenActivity.LOCKSCREEN_CUSTOM_VIEW_EXTRA, customView);
                    showLockScreenIntent.putExtra(SDLLockScreenActivity.LOCKSCREEN_DEVICE_LOGO_EXTRA, deviceLogoEnabled);
                    showLockScreenIntent.putExtra(SDLLockScreenActivity.LOCKSCREEN_DEVICE_LOGO_BITMAP, deviceLogo);
                    showLockScreenIntent.putExtra(SDLLockScreenActivity.KEY_LOCKSCREEN_DISMISSIBLE, isLockscreenDismissible && enableDismissGesture);
                    showLockScreenIntent.putExtra(SDLLockScreenActivity.KEY_LOCKSCREEN_WARNING_MSG, mLockscreenWarningMsg);

                    if (lastIntentUsed != null && lastIntentUsed.equals(showLockScreenIntent.toUri(0))) {
                        DebugTool.logInfo(TAG, "Not restarting lockscreen with same intent");
                        return;
                    } else {
                        context.get().startActivity(showLockScreenIntent);
                        lastIntentUsed = showLockScreenIntent.toUri(0);
                    }
                } else if (status == LockScreenStatus.OFF) {
                    closeLockScreenActivity();
                }
            }
        }
    }

    private void closeLockScreenActivity() {

        if (displayMode == LockScreenConfig.DISPLAY_MODE_ALWAYS) {
            return;
        }

        if (context.get() != null) {
            LockScreenStatus status = getLockScreenStatus();
            if (status == LockScreenStatus.OFF || (status == LockScreenStatus.OPTIONAL && displayMode != LockScreenConfig.DISPLAY_MODE_OPTIONAL_OR_REQUIRED)) {
                Intent intent = new Intent(SDLLockScreenActivity.CLOSE_LOCK_SCREEN_ACTION)
                        .setPackage(context.get().getPackageName());
                context.get().sendBroadcast(intent);
            }
        }
        lastIntentUsed = null;
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
    synchronized LockScreenStatus getLockScreenStatus() {

        if ((hmiLevel == null) || (hmiLevel.equals(HMILevel.HMI_NONE))) {
            return LockScreenStatus.OFF;
        } else if (hmiLevel.equals(HMILevel.HMI_BACKGROUND)) {
            if (!driverDistStatus) {
                //we don't have driver distraction, lock screen is entirely based on if user is using the app on the head unit
                return LockScreenStatus.OFF;
            } else {
                return LockScreenStatus.REQUIRED;
            }
        } else if ((hmiLevel.equals(HMILevel.HMI_FULL)) || (hmiLevel.equals(HMILevel.HMI_LIMITED))) {
            if (!driverDistStatus) {
                return LockScreenStatus.OPTIONAL;
            } else {
                return LockScreenStatus.REQUIRED;
            }
        }
        return LockScreenStatus.OFF;
    }

    private void downloadDeviceIcon(final String url) {

        if (deviceLogo != null || context.get() == null) {
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                mLockScreenDeviceIconManager.retrieveIcon(url, new LockScreenDeviceIconManager.OnIconRetrievedListener() {
                    @Override
                    public void onImageRetrieved(Bitmap icon) {
                        deviceLogo = icon;
                        if (deviceLogo != null) {
                            Intent intent = new Intent(SDLLockScreenActivity.LOCKSCREEN_DEVICE_LOGO_DOWNLOADED);
                            intent.putExtra(SDLLockScreenActivity.LOCKSCREEN_DEVICE_LOGO_EXTRA, deviceLogoEnabled);
                            intent.putExtra(SDLLockScreenActivity.LOCKSCREEN_DEVICE_LOGO_BITMAP, deviceLogo);
                            if (context.get() != null) {
                                intent.setPackage(context.get().getPackageName());
                                context.get().sendBroadcast(intent);
                            }
                        }
                    }

                    @Override
                    public void onError(String info) {
                        DebugTool.logError(TAG, info);
                    }
                });
            }
        }).start();
    }

}
