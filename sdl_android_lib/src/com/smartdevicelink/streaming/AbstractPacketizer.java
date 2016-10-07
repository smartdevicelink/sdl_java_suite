package com.smartdevicelink.streaming;

import java.io.IOException;
import java.io.InputStream;

import com.smartdevicelink.SdlConnection.SdlSession;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.RPCRequest;

abstract public class AbstractPacketizer {

	protected IStreamListener _streamListener = null;
	private final static int BUFF_READ_SIZE = 1000000;
	protected byte _rpcSessionID = 0;
	
	protected SessionType _serviceType = null;
	protected SdlSession _session = null;
	protected InputStream is = null;
	protected byte[] buffer = new byte[BUFF_READ_SIZE];
	protected boolean upts = false;
	protected RPCRequest _request = null;
	protected byte _wiproVersion = 1;
	
	//protected long ts = 0, intervalBetweenReports = 5000, delta = 0;
	protected long intervalBetweenReports = 5000, delta = 0;

	public AbstractPacketizer(IStreamListener streamListener, InputStream is, SessionType sType, byte rpcSessionID, SdlSession session) throws IOException {
        this._streamListener = streamListener;
		this.is = is;
		_rpcSessionID = rpcSessionID;
		_serviceType = sType;
		this._session = session;
	}
	
	public AbstractPacketizer(IStreamListener streamListener, InputStream is, RPCRequest request, SessionType sType, byte rpcSessionID, byte wiproVersion, SdlSession session) throws IOException {
        this._streamListener = streamListener;
		this.is = is;
		_rpcSessionID = rpcSessionID;
		_serviceType = sType;
		_request = request;
		_wiproVersion = wiproVersion;
		this._session = session;
	}	

	public abstract void start() throws IOException;

	public abstract void stop();

	public abstract void pause();

	public abstract void resume();
}
