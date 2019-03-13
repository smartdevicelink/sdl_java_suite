package com.smartdevicelink.transport;

import com.smartdevicelink.protocol.SdlPacket;

/**
 * This interface is used to receive callbacks from a transport class
 */
public interface TransportCallback {
    void onConnectionEstablished();
    void onError();
    void onConnectionTerminated();
    void onPacketReceived(SdlPacket packet);
}