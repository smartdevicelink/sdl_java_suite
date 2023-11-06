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

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;

import com.smartdevicelink.util.AndroidTools;
import com.smartdevicelink.util.DebugTool;

import java.io.IOException;
import java.lang.ref.WeakReference;

@TargetApi(12)
public class UsbTransferProvider {
    private static final String TAG = "UsbTransferProvider";

    private Context context;
    private boolean isBound = false;
    private ComponentName routerService;
    private int flags = 0;

    final Messenger clientMessenger;

    UsbTransferCallback callback;
    Messenger routerServiceMessenger = null;
    ParcelFileDescriptor usbPfd;
    Bundle usbInfoBundle;

    private final ServiceConnection routerConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className, IBinder service) {
            DebugTool.logInfo(TAG, "Bound to service " + className.toString());
            routerServiceMessenger = new Messenger(service);
            isBound = true;
            //So we just established our connection
            //Register with router service
            Message msg = Message.obtain();
            msg.what = TransportConstants.USB_CONNECTED_WITH_DEVICE;
            msg.arg1 = flags;
            msg.replyTo = clientMessenger;
            msg.obj = usbPfd;
            if (usbInfoBundle != null) {
                msg.setData(usbInfoBundle);
            }
            try {
                routerServiceMessenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        public void onServiceDisconnected(ComponentName className) {
            DebugTool.logInfo(TAG, "UN-Bound from service " + className.getClassName());
            routerServiceMessenger = null;
            isBound = false;
        }
    };

    public UsbTransferProvider(Context context, ComponentName service, UsbAccessory usbAccessory, UsbTransferCallback callback) {
        if (context == null || service == null || usbAccessory == null) {
            throw new IllegalStateException("Supplied params are not correct. Context == null? " + (context == null) + " ComponentName == null? " + (service == null) + " Usb Accessory == null? " + usbAccessory);
        }
        usbPfd = getFileDescriptor(usbAccessory, context);
        if (usbPfd != null && usbPfd.getFileDescriptor() != null && usbPfd.getFileDescriptor().valid()) {
            this.context = context;
            this.routerService = service;
            this.callback = callback;
            this.clientMessenger = new Messenger(new ClientHandler(this));

            usbInfoBundle = new Bundle();
            usbInfoBundle.putString(MultiplexUsbTransport.MANUFACTURER, usbAccessory.getManufacturer());
            usbInfoBundle.putString(MultiplexUsbTransport.MODEL, usbAccessory.getModel());
            usbInfoBundle.putString(MultiplexUsbTransport.VERSION, usbAccessory.getVersion());
            usbInfoBundle.putString(MultiplexUsbTransport.URI, usbAccessory.getUri());
            usbInfoBundle.putString(MultiplexUsbTransport.SERIAL, usbAccessory.getSerial());
            usbInfoBundle.putString(MultiplexUsbTransport.DESCRIPTION, usbAccessory.getDescription());
            checkIsConnected();
        } else {
            DebugTool.logError(TAG, "Unable to open accessory");
            clientMessenger = null;
            if (callback != null) {
                callback.onUsbTransferUpdate(false);
            }
        }

    }

    protected UsbTransferProvider(Context context, ComponentName service, ParcelFileDescriptor usbPfd, UsbTransferCallback callback) {
        if (context == null || service == null || usbPfd == null) {
            throw new IllegalStateException("Supplied params are not correct. Context == null? " + (context == null) + " ComponentName == null? " + (service == null) + " Usb PFD == null? " + usbPfd);
        }
        if (usbPfd.getFileDescriptor() != null && usbPfd.getFileDescriptor().valid()) {
            this.context = context;
            this.routerService = service;
            this.callback = callback;
            this.clientMessenger = new Messenger(new ClientHandler(this));
            this.usbPfd = usbPfd;
            checkIsConnected();
        } else {
            DebugTool.logError(TAG, "Unable to open accessory");
            clientMessenger = null;
            if (callback != null) {
                callback.onUsbTransferUpdate(false);
            }
        }
    }

    @SuppressLint("NewApi")
    private ParcelFileDescriptor getFileDescriptor(UsbAccessory accessory, Context context) {
        if (AndroidTools.isUSBCableConnected(context)) {
            try {
                UsbManager manager = (UsbManager) context.getSystemService(Context.USB_SERVICE);

                if (manager != null) {
                    return manager.openAccessory(accessory);
                }
            } catch (Exception e) {
            }
        }
        return null;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public void checkIsConnected() {
        if (!AndroidTools.isServiceExported(context, routerService) || !bindToService()) {
            //We are unable to bind to service
            DebugTool.logError(TAG, "Unable to bind to service");
            unBindFromService();
        }
    }

    public void cancel() {
        if (isBound) {
            unBindFromService();
        }
    }

    private boolean bindToService() {
        if (isBound) {
            return true;
        }
        if (clientMessenger == null) {
            return false;
        }
        Intent bindingIntent = new Intent();
        bindingIntent.setClassName(this.routerService.getPackageName(), this.routerService.getClassName());//This sets an explicit intent
        //Quickly make sure it's just up and running
        bindingIntent.setAction(TransportConstants.BIND_REQUEST_TYPE_ALT_TRANSPORT);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            context.startService(bindingIntent);
        } else {
            context.startForegroundService(bindingIntent);
        }
        bindingIntent.setAction(TransportConstants.BIND_REQUEST_TYPE_USB_PROVIDER);
        return context.bindService(bindingIntent, routerConnection, Context.BIND_AUTO_CREATE);
    }

    private void unBindFromService() {
        try {
            if (context != null && routerConnection != null) {
                context.unbindService(routerConnection);
            } else {
                DebugTool.logWarning(TAG, "Unable to unbind from router service, context was null");
            }

        } catch (IllegalArgumentException e) {
            //This is ok
        }
    }

    private void finish() {
        try {
            usbPfd.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        usbPfd = null;
        unBindFromService();
        routerServiceMessenger = null;
        context = null;
        System.gc();
        if (callback != null) {
            callback.onUsbTransferUpdate(true);
        }
    }

    static class ClientHandler extends Handler {
        final WeakReference<UsbTransferProvider> provider;

        public ClientHandler(UsbTransferProvider provider) {
            super(Looper.getMainLooper());
            this.provider = new WeakReference<>(provider);
        }

        @Override
        public void handleMessage(Message msg) {
            if (provider.get() == null) {
                return;
            }
            switch (msg.what) {
                case TransportConstants.ROUTER_USB_ACC_RECEIVED:
                    DebugTool.logInfo(TAG, "Successful USB transfer");
                    provider.get().finish();
                    break;
                default:
                    break;
            }
        }
    }

    public interface UsbTransferCallback {
        void onUsbTransferUpdate(boolean success);
    }


}
