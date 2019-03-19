package com.smartdevicelink.test.streaming;

import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.streaming.IStreamListener;

/**
 * This is a mock class for testing the following :
 * {@link com.smartdevicelink.streaming.AbstractPacketizer}
 */
public class MockStreamListener implements IStreamListener {
	public MockStreamListener () { }
	@Override public void sendStreamPacket(ProtocolMessage pm) { }
}
