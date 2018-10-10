package com.smartdevicelink.transport.utl;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;

import java.io.IOException;
import java.net.Socket;

import static com.smartdevicelink.util.NativeLogTool.logInfo;

public class WiFiSocketFactory {
    /**
     * Creates a TCP socket which is bound to Wi-Fi network (for Android 5+)
     *
     * On Android 5 and later, this method returns a Socket which is always bound to a Wi-Fi
     * network. If the phone is not connected to a Wi-Fi network, this method throws an IOException.
     * Prior to Android 5, this method simply creates a Socket equivalent to "new Socket();".
     *
     * @return a Socket instance bound to Wi-Fi network.
     */
    public static Socket createSocket(Context context) throws IOException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Socket socket = createWiFiSocket(context);
            if (socket == null) {
                logInfo("Cannot find Wi-Fi network, aborting socket creation.");
                throw new IOException("The phone is not connected to Wi-Fi network");
            }
            return socket;
        } else {
            return new Socket();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static Socket createWiFiSocket(Context context) {
        ConnectivityManager connMan = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // requires ACCESS_NETWORK_STATE permission
        Network[] allNetworks = connMan.getAllNetworks();

        // Samsung Galaxy S9 (with Android 8.0.0) provides two `Network` instances which have
        // TRANSPORT_WIFI capability. The first one throws an IOException upon creating a Socket,
        // and the second one actually works. To support such case, here we iterate over all
        // `Network` instances until we can create a Socket.
        for (Network network : allNetworks) {
            // requires ACCESS_NETWORK_STATE permission
            NetworkCapabilities capabilities = connMan.getNetworkCapabilities(network);
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                try {
                    return network.getSocketFactory().createSocket();
                } catch (IOException e) {
                    logInfo("IOException during socket creation (ignored): " + e.getMessage());
                }
            }
        }

        return null;
    }
}
