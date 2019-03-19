package com.smartdevicelink.transport;

import com.smartdevicelink.transport.enums.TransportType;
import com.smartdevicelink.transport.utl.SSLConfig;

public class WebSocketServerConfig extends BaseTransportConfig{

    final int port, connectionLostTimeout;
    SSLConfig sslConfig;
    /**
     * Default constructor for WebsocketConfig
     * @param port the port this web socket should listen on
     * @param connectionLostTimeout the timeout for a connection lost, default would be 60 seconds. If a value less than
     *                              0 is used, then the websocket will wait indefinitely.
     */
    public WebSocketServerConfig(int port, int connectionLostTimeout){
        this.port = port;
        this.shareConnection = false;
        this.connectionLostTimeout = connectionLostTimeout;
    }

    public SSLConfig getSslConfig() {
        return sslConfig;
    }

    public void setSslConfig(SSLConfig sslConfig) {
        this.sslConfig = sslConfig;
    }

    @Override
    public TransportType getTransportType() {
        return TransportType.WEB_SOCKET_SERVER;
    }
}
