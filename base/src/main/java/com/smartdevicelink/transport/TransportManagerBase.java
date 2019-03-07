package com.smartdevicelink.transport;

import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.transport.enums.TransportType;
import com.smartdevicelink.transport.utl.TransportRecord;

import java.util.ArrayList;
import java.util.List;

public abstract class TransportManagerBase {
    private static final String TAG = "TransportManager";

    protected final Object TRANSPORT_STATUS_LOCK;

    //WebSocketServer2 transport;
    final List<TransportRecord> transportStatus;
    final TransportEventListener transportListener;

    public TransportManagerBase(BaseTransportConfig config,TransportEventListener listener){
        transportListener = listener;
        this.TRANSPORT_STATUS_LOCK = new Object();
        synchronized (TRANSPORT_STATUS_LOCK){
            this.transportStatus = new ArrayList<>();
        }
    }

    public abstract void start();

    public abstract void close(long sessionId);

    /**
     * Check to see if a transport is connected.
     * @param transportType the transport to have its connection status returned. If `null` is
     *                      passed in, all transports will be checked and if any are connected a
     *                      true value will be returned.
     * @param address the address associated with the transport type. If null, the first transport
     *                of supplied type will be used to return if connected.
     * @return if a transport is connected based on included variables
     */
    public abstract boolean isConnected(TransportType transportType, String address);
    /**
     * Retrieve a transport record with the supplied params
     * @param transportType the transport to have its connection status returned.
     * @param address the address associated with the transport type. If null, the first transport
     *                of supplied type will be returned.
     * @return the transport record for the transport type and address if supplied
     */
    public abstract TransportRecord getTransportRecord(TransportType transportType, String address);


    /**
     * Retrieves the currently connected transports
     * @return the currently connected transports
     */
    public List<TransportRecord> getConnectedTransports(){
        return this.transportStatus;
    }


    public boolean isHighBandwidthAvailable(){
        synchronized (TRANSPORT_STATUS_LOCK) {
            for (TransportRecord record : transportStatus) {
                if (record.getType().equals(TransportType.USB)
                        || record.getType().equals(TransportType.TCP)) {
                    return true;
                }
            }
            return false;
        }
    }

    public Object getRouterService(){
        return null;
    }

    public abstract void sendPacket(SdlPacket packet);

    public void requestNewSession(TransportRecord transportRecord){
        //FIXME do nothing
    }

    public void requestSecondaryTransportConnection(byte sessionId, Object params){
        //FIXME do nothing
    }

    protected synchronized void enterLegacyMode(final String info){
        //FIXME do nothing
    }

    protected synchronized void exitLegacyMode(String info ){
        //FIXME do nothing
    }

    public interface TransportEventListener{
        /** Called to indicate and deliver a packet received from transport */
        void onPacketReceived(SdlPacket packet);

        /** Called to indicate that transport connection was established */
        void onTransportConnected(List<TransportRecord> transports);

        /** Called to indicate that transport was disconnected (by either side) */
        void onTransportDisconnected(String info, TransportRecord type, List<TransportRecord> connectedTransports);

        // Called when the transport manager experiences an unrecoverable failure
        void onError(String info);
        /**
         * Called when the transport manager has determined it needs to move towards a legacy style
         * transport connection. It will always be bluetooth.
         * @param info simple info string about the situation
         * @return if the listener is ok with entering legacy mode
         */
        boolean onLegacyModeEnabled(String info);
    }
}
