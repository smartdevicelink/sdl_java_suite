package com.smartdevicelink.test.streaming;

import com.smartdevicelink.SdlConnection.SdlSession;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.streaming.AbstractPacketizer;
import com.smartdevicelink.streaming.IStreamListener;

import java.io.IOException;
import java.io.InputStream;

/**
 * This is a mock class for testing the following :
 * {@link com.smartdevicelink.streaming.AbstractPacketizer}
 */
public class MockPacketizer extends AbstractPacketizer {
	public MockPacketizer (IStreamListener l, InputStream i, SessionType s, byte sid, SdlSession sdlsession) throws IOException { super (l, i, s, sid, sdlsession); }

	@Override public void start() throws IOException { }
	@Override public void stop() { }

	public IStreamListener getListener () { return _streamListener; }
	public InputStream getInputStream  () { return is;              }
	public SessionType getSessionType  () { return _serviceType;    }
	public byte getSessionId           () { return _rpcSessionID;   }

	@Override public void pause() { }
	@Override public void resume() { }
}
