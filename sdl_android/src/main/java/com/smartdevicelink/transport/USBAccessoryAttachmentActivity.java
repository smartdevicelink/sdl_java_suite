/*
 * Copyright (c) 2018 Livio, Inc.
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

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.smartdevicelink.util.AndroidTools;
import com.smartdevicelink.util.SdlAppInfo;
import com.smartdevicelink.util.ServiceFinder;

import java.util.List;
import java.util.Vector;

import static com.smartdevicelink.transport.TransportConstants.FOREGROUND_EXTRA;

@TargetApi(12)
/**
 * The USBAccessoryAttachmentActivity is a proxy to listen for
 * USB_ACCESSORY_ATTACHED intents.
 *
 * Unfortunately, the USB_ACCESSORY_ATTACHED intent can only be sent to an
 * activity. So this class is a workaround to get that intent.
 *
 * Some reference: http://stackoverflow.com/questions/6981736/android-3-1-usb-host-broadcastreceiver-does-not-receive-usb-device-attached/9814826#9814826
 *
 * Inspired by OpenXC-Android: https://github.com/openxc/openxc-android
 *
 * <strong>NOTA BENE:</strong> An application that wants to use USB transport
 * must make the following changes to AndroidManifest.xml:
 *
 * 1. add these lines to <manifest>…</manifest>:
 * <!-- Required to use the USB Accessory mode -->
 * <uses-feature android:name="android.hardware.usb.accessory"/>
 *
 * 2. add these lines to <application>…</application>:
 * <activity android:name="com.smartdevicelink.transport.USBAccessoryAttachmentActivity">
 *     <intent-filter>
 *         <action android:name="android.hardware.usb.action.USB_ACCESSORY_ATTACHED"/>
 *     </intent-filter>
 *     <meta-data
 *         android:name="android.hardware.usb.action.USB_ACCESSORY_ATTACHED"
 *         android:resource="@xml/accessory_filter"/>
 * </activity>
 *
 * 3. set minimum SDK version to 12:
 * <uses-sdk android:minSdkVersion="12"/>
 */
public class USBAccessoryAttachmentActivity extends Activity {
    private static final String TAG =            USBAccessoryAttachmentActivity.class.getSimpleName();

    UsbAccessory usbAccessory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkUsbAccessoryIntent("Create");
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkUsbAccessoryIntent("Resume");
    }

    private void checkUsbAccessoryIntent(String sourceAction) {
        final Intent intent = getIntent();
        String action = intent.getAction();
        Log.d(TAG, sourceAction + " with action: " + action);

        if (UsbManager.ACTION_USB_ACCESSORY_ATTACHED.equals(action)) {
            Intent usbAccessoryAttachedIntent =  new Intent(USBTransport.ACTION_USB_ACCESSORY_ATTACHED);
            usbAccessoryAttachedIntent.putExtra(UsbManager.EXTRA_ACCESSORY,intent.getParcelableExtra(UsbManager.EXTRA_ACCESSORY));
            usbAccessoryAttachedIntent.putExtra(UsbManager.EXTRA_PERMISSION_GRANTED,intent.getParcelableExtra(UsbManager.EXTRA_PERMISSION_GRANTED));

            usbAccessory = intent.getParcelableExtra(UsbManager.EXTRA_ACCESSORY);


           // AndroidTools.sendExplicitBroadcast(getApplicationContext(),usbAccessoryAttachedIntent,null);
            wakeUpRouterService(getApplicationContext(),false, true);

        }

    }

    private boolean wakeUpRouterService(final Context context, final boolean ping, final boolean altTransportWake){
        new ServiceFinder(context, context.getPackageName(), new ServiceFinder.ServiceFinderCallback() {
            @Override
            public void onComplete(Vector<ComponentName> routerServices) {
                Vector<ComponentName> runningBluetoothServicePackage = new Vector<ComponentName>();
                runningBluetoothServicePackage.addAll(routerServices);
                if (runningBluetoothServicePackage.isEmpty()) {
                    //If there isn't a service running we should try to start one
                    //We will try to sort the SDL enabled apps and find the one that's been installed the longest
                    Intent serviceIntent;
                    List<SdlAppInfo> sdlAppInfoList = AndroidTools.querySdlAppInfo(context, new SdlAppInfo.BestRouterComparator());
                    if (sdlAppInfoList != null && !sdlAppInfoList.isEmpty()) {
                        serviceIntent = new Intent();
                        serviceIntent.setComponent(sdlAppInfoList.get(0).getRouterServiceComponentName());
                    } else{
                        Log.d(TAG, "No SDL Router Services found");
                        Log.d(TAG, "WARNING: This application has not specified its SdlRouterService correctly in the manifest. THIS WILL THROW AN EXCEPTION IN FUTURE RELEASES!!");
                        return;
                    }
                    if (altTransportWake) {
                        serviceIntent.setAction(TransportConstants.BIND_REQUEST_TYPE_ALT_TRANSPORT);
                    }
                    try {
                        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                            context.startService(serviceIntent);
                        }else {
                            serviceIntent.putExtra(FOREGROUND_EXTRA, true);
                            context.startForegroundService(serviceIntent);

                        }
                        //Make sure to send this out for old apps to close down
                        SdlRouterService.LocalRouterService self = SdlRouterService.getLocalRouterService(serviceIntent, serviceIntent.getComponent());
                        Intent restart = new Intent(SdlRouterService.REGISTER_NEWER_SERVER_INSTANCE_ACTION);
                        restart.putExtra(SdlBroadcastReceiver.LOCAL_ROUTER_SERVICE_EXTRA, self);
                        restart.putExtra(SdlBroadcastReceiver.LOCAL_ROUTER_SERVICE_DID_START_OWN, true);
                        context.sendBroadcast(restart);

                        if (altTransportWake && usbAccessory!=null) {
                            new UsbTransferProvider(context, serviceIntent.getComponent(), usbAccessory, new UsbTransferProvider.UsbTransferCallback() {
                                @Override
                                public void onUsbTransferUpdate(boolean success) {
                                    finish();
                                }
                            });

                            return;
                        }

                    } catch (SecurityException e) {
                        Log.e(TAG, "Security exception, process is bad");
                    }
                } else {
                    if (altTransportWake && usbAccessory!=null) {
                        new UsbTransferProvider(context,runningBluetoothServicePackage.get(0),usbAccessory, new UsbTransferProvider.UsbTransferCallback(){
                            @Override
                            public void onUsbTransferUpdate(boolean success) {
                                finish();
                            }
                        });

                        return;
                    }
                    return;
                }
            }
        });
        return true;
    }
}
