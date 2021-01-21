package com.smartdevicelink.test.streaming;

import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.VehicleType;
import com.smartdevicelink.session.ISdlSessionListener;
import com.smartdevicelink.transport.BaseTransportConfig;
import com.smartdevicelink.util.Version;

/**
 * This is a mock class for testing the following :
 * {@link com.smartdevicelink.streaming.AbstractPacketizer}
 */
public class MockInterfaceBroker implements ISdlSessionListener {
    public MockInterfaceBroker() {
    }

    @Override
    public void onTransportDisconnected(String info, boolean availablePrimary, BaseTransportConfig transportConfig) {

    }

    @Override
    public void onRPCMessageReceived(RPCMessage rpcMessage) {

    }

    @Override
    public void onSessionStarted(int sessionID, Version version) {

    }

    @Override
    public void onSessionEnded(int sessionID) {

    }

    @Override
    public void onAuthTokenReceived(String authToken, int sessionID) {

    }

    @Override
    public boolean onVehicleTypeReceived(VehicleType type) {
        return false;
    }
}
