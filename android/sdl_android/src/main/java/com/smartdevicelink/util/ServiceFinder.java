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
package com.smartdevicelink.util;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import com.smartdevicelink.transport.SdlRouterService;

import java.util.HashMap;
import java.util.Vector;

import static com.smartdevicelink.transport.TransportConstants.BIND_LOCATION_CLASS_NAME_EXTRA;
import static com.smartdevicelink.transport.TransportConstants.BIND_LOCATION_PACKAGE_NAME_EXTRA;
import static com.smartdevicelink.transport.TransportConstants.SEND_PACKET_TO_APP_LOCATION_EXTRA_NAME;

/**
 * Created by Joey Grover on 8/18/17.
 */

public class ServiceFinder {
    public static final String TAG = ServiceFinder.class.getSimpleName();

    private static final int TIMEOUT = 1000;
    final String receiverLocation;
    final Context context;
    final ServiceFinderCallback callback;
    final Vector<ComponentName> services;
    final HashMap<String, ResolveInfo> sdlMultiMap;
    final Handler timeoutHandler;
    final Runnable timeoutRunnable;


    public ServiceFinder(Context context, String packageName, final ServiceFinderCallback callback) {
        this.receiverLocation = packageName + ".ServiceFinder";
        this.context = context.getApplicationContext();
        this.callback = callback;
        this.services = new Vector<>();

        this.sdlMultiMap = AndroidTools.getSdlEnabledApps(context, packageName);

        AndroidTools.registerReceiver(this.context, mainServiceReceiver,
                new IntentFilter(this.receiverLocation), Context.RECEIVER_EXPORTED);

        timeoutRunnable = new Runnable() {
            @Override
            public void run() {
                onFinished();
            }
        };
        timeoutHandler = new Handler(Looper.getMainLooper());
        timeoutHandler.postDelayed(timeoutRunnable, TIMEOUT + (50 * packageName.length()));

        //Send out our broadcast
        context.sendBroadcast(createQueryIntent(this.receiverLocation));


    }

    final BroadcastReceiver mainServiceReceiver = new BroadcastReceiver() {
        private final Object LIST_LOCK = new Object();

        @Override
        public void onReceive(Context context, Intent intent) {
            DebugTool.logInfo(TAG, "Received intent " + intent);
            if (intent != null) {
                String packageName = intent.getStringExtra(BIND_LOCATION_PACKAGE_NAME_EXTRA);
                String className = intent.getStringExtra(BIND_LOCATION_CLASS_NAME_EXTRA);
                DebugTool.logInfo(TAG, "Received intent from package: " + packageName + ". Classname: " + className);
                synchronized (LIST_LOCK) {
                    //Add to running services
                    services.add(new ComponentName(packageName, className));
                    //Remove from our waiting for response list
                    sdlMultiMap.remove(packageName);

                    //If list is empty, return to callback and unregister
                    if (sdlMultiMap.isEmpty() && callback != null) {
                        timeoutHandler.removeCallbacks(timeoutRunnable);
                        onFinished();
                    }
                }
            }
        }
    };

    private void onFinished() {
        if (callback != null) {
            callback.onComplete(services);
        }
        context.unregisterReceiver(mainServiceReceiver);

    }

//    /**
//     * Get all SDL enabled apps. If the package name is null, it will return all apps. However, if the package name is included, the
//     * resulting hash map will not include the app with that package name.
//     *
//     * @param context
//     * @param packageName
//     * @return
//     */
//    public static HashMap<String, ResolveInfo> getSdlEnabledApps(Context context, String packageName) {
//        Intent intent = new Intent(TransportConstants.START_ROUTER_SERVICE_ACTION);
//        PackageManager manager = context.getPackageManager();
//        List<ResolveInfo> infos = manager.queryBroadcastReceivers(intent, 0);
//        HashMap<String, ResolveInfo> sdlMultiMap = new HashMap<String, ResolveInfo>();
//        for (ResolveInfo info : infos) {
//            //Log.d(TAG, "Sdl enabled app: " + info.activityInfo.packageName);
//            if (info.activityInfo.applicationInfo.packageName.equals(packageName)) {
//                //Log.d(TAG, "Ignoring my own package");
//                continue;
//            }
//
//            sdlMultiMap.put(info.activityInfo.packageName, info);
//            try {
//                ServiceInfo[] services = manager.getPackageInfo(info.activityInfo.applicationInfo.packageName, PackageManager.GET_SERVICES).services;
//                for (int i = 0; i < services.length; i++) {
//                    Log.d(TAG, "Found : " + services[i].name);
//                }
//            } catch (PackageManager.NameNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//        return sdlMultiMap;
//    }

    private static Intent createQueryIntent(String receiverLocation) {
        Intent intent = new Intent();
        intent.setAction(SdlRouterService.REGISTER_WITH_ROUTER_ACTION);
        intent.putExtra(SEND_PACKET_TO_APP_LOCATION_EXTRA_NAME, receiverLocation);
        intent.setFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        return intent;
    }

    public interface ServiceFinderCallback {
        void onComplete(Vector<ComponentName> routerServices);
    }
}
