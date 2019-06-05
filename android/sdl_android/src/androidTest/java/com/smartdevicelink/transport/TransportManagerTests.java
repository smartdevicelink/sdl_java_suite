package com.smartdevicelink.transport;

import android.content.ComponentName;
import android.os.Looper;

import com.smartdevicelink.AndroidTestCase2;
import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.protocol.SdlPacketFactory;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.test.SdlUnitTestContants;
import com.smartdevicelink.transport.enums.TransportType;
import com.smartdevicelink.transport.utl.TransportRecord;

import java.util.Collections;
import java.util.List;

public class TransportManagerTests extends AndroidTestCase2 {

    MultiplexTransportConfig config;
    final TransportRecord defaultBtRecord = new TransportRecord(TransportType.BLUETOOTH, "12:34:56:78:90");
    final ComponentName routerServiceComponentName = new ComponentName("com.smartdevicelink.test","com.smartdevicelink.test.SdlRouterService");
    final SdlPacket defaultPacket = SdlPacketFactory.createStartSessionACK(SessionType.RPC,(byte)1,100,(byte)5);


    TransportManager.TransportEventListener defaultListener = new TransportManager.TransportEventListener() {
        @Override
        public void onPacketReceived(SdlPacket packet) { assertEquals(defaultPacket,packet);}
        @Override
        public void onTransportConnected(List<TransportRecord> transports) {}
        @Override
        public void onTransportDisconnected(String info, TransportRecord type, List<TransportRecord> connectedTransports) {}
        @Override
        public void onError(String info) {}
        @Override
        public boolean onLegacyModeEnabled(String info) {return false; }
    };


    @Override
    protected void setUp() throws Exception {
        config = new MultiplexTransportConfig(this.mContext, SdlUnitTestContants.TEST_APP_ID);
        config.setService(routerServiceComponentName);
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }


    }

    public TransportManager createTransportManager(){
        TransportManager manager = new TransportManager(config,defaultListener);

        //The default listener returns false as legacy is unacceptable
        assertNull(manager.legacyBluetoothHandler);

        manager.exitLegacyMode("Test");
        manager.transport = manager.new TransportBrokerImpl(mContext, config.appId, routerServiceComponentName);

        manager.start();
        assert true;
        return manager;
    }

    public void testBase(){
        TransportManager manager = new TransportManager(config,defaultListener);
        assertNotNull(manager);
    }

    public void testConnectionStatus(){
        TransportManager manager = new TransportManager(config,defaultListener);

        manager.transportStatus.clear();
        manager.transportStatus.add(defaultBtRecord);

        assertTrue(manager.isConnected(null, null));
        assertTrue(manager.isConnected(TransportType.BLUETOOTH, null));
        assertTrue(manager.isConnected(TransportType.BLUETOOTH, defaultBtRecord.getAddress()));

        assertNull(manager.getTransportRecord(null, null));
        assertNull(manager.getTransportRecord(null, defaultBtRecord.getAddress()));
        assertNull(manager.getTransportRecord(TransportType.USB, null));

        assertEquals(defaultBtRecord, manager.getTransportRecord(defaultBtRecord.getType(), null));
        assertEquals(defaultBtRecord, manager.getTransportRecord(defaultBtRecord.getType(), defaultBtRecord.getAddress()));

        assertFalse(manager.isHighBandwidthAvailable());

        manager.transportStatus.add(new TransportRecord(TransportType.USB,"test"));
        assertTrue(manager.isHighBandwidthAvailable());
        assertNotNull(manager.getTransportRecord(TransportType.USB, null));

    }

    public void testOnTransportConnections(){

        TransportManager manager = createTransportManager();

        manager.transport.onHardwareConnected(Collections.singletonList(defaultBtRecord));

        assertTrue(manager.isConnected(null, null));
        assertTrue(manager.isConnected(TransportType.BLUETOOTH, null));
        assertTrue(manager.isConnected(TransportType.BLUETOOTH, defaultBtRecord.getAddress()));

        assertNull(manager.getTransportRecord(null, null));
        assertNull(manager.getTransportRecord(null, defaultBtRecord.getAddress()));
        assertNull(manager.getTransportRecord(TransportType.USB, null));

        assertEquals(defaultBtRecord, manager.getTransportRecord(defaultBtRecord.getType(), null));
        assertEquals(defaultBtRecord, manager.getTransportRecord(defaultBtRecord.getType(), defaultBtRecord.getAddress()));

        assertFalse(manager.isHighBandwidthAvailable());

    }

    public void testOnPacket(){
        TransportManager manager = createTransportManager();
        assertNotNull(manager.transportListener);

        manager.transport.onPacketReceived(defaultPacket);

    }



}
