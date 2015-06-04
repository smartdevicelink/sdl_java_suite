package com.smartdevicelink.connection;

import java.util.concurrent.CopyOnWriteArrayList;

import android.util.Log;

import com.smartdevicelink.connection.interfaces.ISdlConnectionListener;
import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.protocol.interfaces.IHeartbeatMonitor;
import com.smartdevicelink.protocol.interfaces.IHeartbeatMonitorListener;
import com.smartdevicelink.proxy.LockScreenManager;
import com.smartdevicelink.transport.BaseTransportConfig;
import com.smartdevicelink.transport.TransportType;

public class SdlSession implements ISdlConnectionListener, IHeartbeatMonitorListener {
	private static CopyOnWriteArrayList<SdlConnection> shareConnections = new CopyOnWriteArrayList<SdlConnection>();
	
	SdlConnection sdlConnection = null;
	private byte sessionId;
	@SuppressWarnings("unused")
    private byte wiproProcolVer;
	private ISdlConnectionListener sessionListener;
	private BaseTransportConfig transportConfig;
    IHeartbeatMonitor heartbeatMonitor = null;
    private static final String TAG = "SdlSession";
    private LockScreenManager lockScreenMan  = new LockScreenManager();

    
	
	public static SdlSession createSession(byte wiproVersion, ISdlConnectionListener listener, BaseTransportConfig btConfig) {
		
		SdlSession session =  new SdlSession();
		session.wiproProcolVer = wiproVersion;
		session.sessionListener = listener;
		session.transportConfig = btConfig;
					
		return session;
	}
	
	public BaseTransportConfig getTransportConfig() {
		return this.transportConfig;
	}
	
	public LockScreenManager getLockScreenMan() {
		return lockScreenMan;
	}
		
	
	private SdlSession() {
	}
	
    public IHeartbeatMonitor getHeartbeatMonitor() {
        return heartbeatMonitor;
    }

    public void setHeartbeatMonitor(IHeartbeatMonitor heartbeatMonitor) {
        this.heartbeatMonitor = heartbeatMonitor;
        heartbeatMonitor.setListener(this);
    }	
	
	
	public byte getSessionId() {
		return this.sessionId;
	}
	
	public SdlConnection getSdlConnection() {
		return this.sdlConnection;
	}
	
	public void close() {
		if (sdlConnection != null) { //sessionId == 0 means session is not started.
			sdlConnection.unregisterSession(this);
			
			if (sdlConnection.getRegisterCount() == 0) {
				shareConnections.remove(sdlConnection);
			}

			sdlConnection = null;
		}
	}
	
	public String getBroadcastComment(BaseTransportConfig myTransport) {
		SdlConnection connection = null;
		if (myTransport.shareConnection()) {
			 connection = findTheProperConnection(myTransport);			
		} else {
			connection = this.sdlConnection;
		}
		
		if (connection != null)
			return connection.getBroadcastComment();
		
		return "";
	}
	
	
	public void startSession() throws SdlException {
		SdlConnection connection = null;
		if (this.transportConfig.shareConnection()) {
			 connection = findTheProperConnection(this.transportConfig);
			
			if (connection == null) {
				connection = new SdlConnection(this.transportConfig);
				shareConnections.add(connection);
			}
		} else {
			connection = new SdlConnection(this.transportConfig);
		}
		
		this.sdlConnection = connection;
		connection.registerSession(this); //Handshake will start when register.
	}
	
    private void initializeSession() {
        if (heartbeatMonitor != null) {
            heartbeatMonitor.start();
        }
    }	
	
	public void sendMessage(ProtocolMessage msg) {
		if (sdlConnection == null) 
			return;
		sdlConnection.sendMessage(msg);
	}
	
	public TransportType getCurrentTransportType() {
		if (sdlConnection == null) 
			return null;
		return sdlConnection.getCurrentTransportType();
	}
	
	public boolean getIsConnected() {
		if (sdlConnection == null) 
			return false;
		return sdlConnection != null && sdlConnection.getIsConnected();
	}

	@Override
	public void onTransportDisconnected(String info) {
		this.sessionListener.onTransportDisconnected(info);
	}

	@Override
	public void onTransportError(String info, Exception e) {
		this.sessionListener.onTransportError(info, e);
	}

	@Override
	public void onProtocolMessageReceived(ProtocolMessage msg) {
		this.sessionListener.onProtocolMessageReceived(msg);
	}
	
	@Override
	public void onHeartbeatTimedOut(byte sessionId) {
		this.sessionListener.onHeartbeatTimedOut(sessionId);
		
	}
		

	@Override
	public void onProtocolSessionStarted(SessionType sessionType,
			byte sessionId, byte version, String correlationId) {
		this.sessionId = sessionId;
		lockScreenMan.setSessionId(sessionId);
		this.sessionListener.onProtocolSessionStarted(sessionType, sessionId, version, correlationId);
		//if (version == 3)
			initializeSession();
	}

	@Override
	public void onProtocolSessionEnded(SessionType sessionType, byte sessionId,
			String correlationId) {
		this.sessionListener.onProtocolSessionEnded(sessionType, sessionId, correlationId);
	}

	@Override
	public void onProtocolError(String info, Exception e) {
		this.sessionListener.onProtocolError(info, e);
	}
    
    @Override
    public void sendHeartbeat(IHeartbeatMonitor monitor) {
        Log.d(TAG, "Asked to send heartbeat");
        if (sdlConnection != null)
        	sdlConnection.sendHeartbeat(this);
    }

    @Override
    public void heartbeatTimedOut(IHeartbeatMonitor monitor) {     
        if (sdlConnection != null)
        	sdlConnection.connectionListener.onHeartbeatTimedOut(this.sessionId);
        close();
    }

	private static SdlConnection findTheProperConnection(BaseTransportConfig config) {
		SdlConnection connection = null;
		
		int minCount = 0;
		for (SdlConnection c : shareConnections) {
			if (c.getCurrentTransportType() == config.getTransportType()) {
				if (minCount == 0 || minCount >= c.getRegisterCount()) {
					connection = c;
					minCount = c.getRegisterCount();
				}
			}
		}
		
		return connection;
	}

	@Override
	public void onProtocolSessionNack(SessionType sessionType,
			byte sessionID, byte version, String correlationID) {
		this.sessionListener.onProtocolSessionNack(sessionType, sessionID, version, correlationID);		
	}	
}
