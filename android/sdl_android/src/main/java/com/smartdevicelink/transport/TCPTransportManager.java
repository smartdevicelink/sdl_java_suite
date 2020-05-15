package com.smartdevicelink.transport;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.transport.enums.TransportType;
import com.smartdevicelink.transport.utl.TransportRecord;
import com.smartdevicelink.util.DebugTool;

import java.lang.ref.WeakReference;

public class TCPTransportManager extends TransportManagerBase{

    private static final String TAG = "TCPTransportManager";

    private TCPHandler tcpHandler;
    private MultiplexTcpTransport transport;
    private TCPTransportConfig config;

    public TCPTransportManager(TCPTransportConfig config, TransportEventListener transportEventListener){
        super(config,transportEventListener);
        Log.d(TAG, "USING THE TCP TRANSPORT MANAGER");
        this.config = config;
        tcpHandler = new TCPHandler(this);
        transport = new MultiplexTcpTransport(config.getPort(), config.getIPAddress(),config.getAutoReconnect(),tcpHandler, null);
    }

    @Override
    public void start() {
        transport.start();

    }

    @Override
    public void close(long sessionId) {
        transport.stop();
    }

    @Override
    public void resetSession() {
        if(transport != null){
            transport.stop();
        }
        //TODO make sure this makes sense
        transport = new MultiplexTcpTransport(config.getPort(), config.getIPAddress(),config.getAutoReconnect(), tcpHandler, null);

    }

    @Override
    public boolean isConnected(TransportType transportType, String address) {
        return (transportType == null || TransportType.TCP.equals(transportType)) && transport.isConnected();
    }

    @Override
    public TransportRecord getTransportRecord(TransportType transportType, String address) {
        if(transport != null){
            return transport.getTransportRecord();
        }else{
            return null;
        }
    }

    @Override
    public void sendPacket(SdlPacket packet) {
        if(packet != null){
            byte[] rawBytes = packet.constructPacket();
            if(rawBytes != null && rawBytes.length >0){
                transport.write(rawBytes, 0, rawBytes.length);
            }
        }

    }


    protected static class TCPHandler extends Handler {

        final WeakReference<TCPTransportManager> provider;

        public TCPHandler(TCPTransportManager provider){
            this.provider = new WeakReference<>(provider);
        }
        @Override
        public void handleMessage(Message msg) {
            if(this.provider.get() == null){
                return;
            }
            TCPTransportManager service = this.provider.get();
            if(service.transportListener == null){
                return;
            }
            switch (msg.what) {
                case BaseRouterService.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case MultiplexBaseTransport.STATE_CONNECTED:
                            synchronized (service.TRANSPORT_STATUS_LOCK){
                                service.transportStatus.clear();
                                service.transportStatus.add(service.transport.getTransportRecord());
                            }
                            DebugTool.logInfo("TCP transport has connected");
                            service.transportListener.onTransportConnected(service.transportStatus);
                            break;
                        case MultiplexBaseTransport.STATE_CONNECTING:
                            // Currently attempting to connect - update UI?
                            break;
                        case MultiplexBaseTransport.STATE_LISTEN:
                            if(service.transport != null){
                                service.transport.stop();
                                service.transport = null;
                            }
                            break;
                        case MultiplexBaseTransport.STATE_NONE:
                            // We've just lost the connection
                            if(service.transport != null){
                                service.transportListener.onTransportDisconnected("TCP transport disconnected", service.transport.transportRecord, null);
                            }else{
                                service.transportListener.onTransportDisconnected("TCP transport disconnected", null, null);

                            }
                            break;
                        case MultiplexBaseTransport.STATE_ERROR:
                            Log.d(TAG, "TCP transport encountered an error");
                            service.transportListener.onError("TCP transport encountered an error" );
                            break;
                    }
                    break;

                case BaseRouterService.MESSAGE_READ:
                    service.transportListener.onPacketReceived((SdlPacket) msg.obj);
                    break;
            }
        }
    }
}
