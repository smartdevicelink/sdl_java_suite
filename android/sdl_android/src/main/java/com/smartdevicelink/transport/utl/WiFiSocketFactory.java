/*
 * Copyright (c) 2017 - 2019, SmartDeviceLink Consortium, Inc.
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
 * Neither the name of the SmartDeviceLink Consortium, Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
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
package com.smartdevicelink.transport.utl;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;

import java.io.IOException;
import java.net.Socket;

import javax.net.SocketFactory;

import static com.smartdevicelink.util.NativeLogTool.logInfo;

public class WiFiSocketFactory {
    /**
     * Try to create a TCP socket which is bound to Wi-Fi network (for Android 5+)
     * <p>
     * On Android 5 and later, this method tries to create a Socket instance which is bound to a
     * Wi-Fi network. If the phone is not connected to a Wi-Fi network, or the app lacks
     * required permission (ACCESS_NETWORK_STATE), then this method simply creates a Socket instance
     * with "new Socket();".
     *
     * @return a Socket instance, preferably bound to a Wi-Fi network
     */
    public static Socket createSocket(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Socket socket = createWiFiSocket(context);
            if (socket != null) {
                logInfo("Created a Socket bound to Wi-Fi network");
                return socket;
            }
        }

        return new Socket();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static Socket createWiFiSocket(Context context) {
        if (context == null) {
            logInfo("Context supplied was null");
            return null;
        }
        PackageManager pm = context.getPackageManager();
        if (pm == null) {
            logInfo("PackageManager isn't available.");
            return null;
        }
        // getAllNetworks() and getNetworkCapabilities() require ACCESS_NETWORK_STATE
        if (pm.checkPermission(Manifest.permission.ACCESS_NETWORK_STATE, context.getPackageName()) != PackageManager.PERMISSION_GRANTED) {
            logInfo("Router service doesn't have ACCESS_NETWORK_STATE permission. It cannot bind a TCP transport to Wi-Fi network.");
            return null;
        }

        ConnectivityManager connMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connMan == null) {
            logInfo("ConnectivityManager isn't available.");
            return null;
        }

        Network[] allNetworks = connMan.getAllNetworks();
        if (allNetworks == null) {
            logInfo("Failed to acquire a list of networks.");
            return null;
        }

        // Samsung Galaxy S9 (with Android 8.0.0) provides two `Network` instances which have
        // TRANSPORT_WIFI capability. The first one throws an IOException upon creating a Socket,
        // and the second one actually works. To support such case, here we iterate over all
        // `Network` instances until we can create a Socket.
        for (Network network : allNetworks) {
            if (network == null) {
                continue;
            }

            NetworkCapabilities capabilities = connMan.getNetworkCapabilities(network);
            if (capabilities == null) {
                continue;
            }

            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                try {
                    SocketFactory factory = network.getSocketFactory();
                    if (factory != null) {
                        return factory.createSocket();
                    }
                } catch (IOException e) {
                    logInfo("IOException during socket creation (ignored): " + e.getMessage());
                }
            }
        }

        logInfo("Cannot find Wi-Fi network to bind a TCP transport.");
        return null;
    }
}
