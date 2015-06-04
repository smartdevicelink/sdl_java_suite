package com.smartdevicelink.streaming;

import java.io.IOException;
import java.io.InputStream;

import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.RpcRequest;
import com.smartdevicelink.streaming.interfaces.IStreamListener;

abstract public class AbstractPacketizer {

	protected IStreamListener streamListener = null;

	protected byte rpcSessionId = 0;
	
	protected SessionType session = null;
	protected InputStream is = null;
	protected byte[] buffer = new byte[1000000];
	protected boolean upts = false;
	protected RpcRequest request = null;
	protected byte wiproVersion = 1;
	
	//protected long ts = 0, intervalBetweenReports = 5000, delta = 0;
	protected long intervalBetweenReports = 5000, delta = 0;

	public AbstractPacketizer(IStreamListener streamListener, InputStream is, SessionType sType, byte rpcSessionId) throws IOException {
        this.streamListener = streamListener;
		this.is = is;
		this.rpcSessionId = rpcSessionId;
		session = sType;
	}
	
	public AbstractPacketizer(IStreamListener streamListener, InputStream is, RpcRequest request, SessionType sType, byte rpcSessionId, byte wiproVersion) throws IOException {
        this.streamListener = streamListener;
		this.is = is;
		this.rpcSessionId = rpcSessionId;
		session = sType;
		this.request = request;
		this.wiproVersion = wiproVersion;
	}	

	public abstract void start() throws IOException;

	public abstract void stop();

	public abstract void pause();

	public abstract void resume();

	protected static String printBuffer(byte[] buffer, int start,int end) {
		String str = "";
		for (int i=start;i<end;i++) str+=","+Integer.toHexString(buffer[i]&0xFF);
		return str;
	}
}
