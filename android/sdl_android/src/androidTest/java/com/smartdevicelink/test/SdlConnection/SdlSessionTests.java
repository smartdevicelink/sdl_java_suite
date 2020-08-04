package com.smartdevicelink.test.SdlConnection;

import com.smartdevicelink.SdlConnection.SdlSession;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.interfaces.ISdlServiceListener;
import com.smartdevicelink.test.streaming.MockInterfaceBroker;
import com.smartdevicelink.transport.MultiplexTransportConfig;
import com.smartdevicelink.transport.TCPTransportConfig;

import junit.framework.TestCase;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.mockito.Mockito.mock;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.SdlConnection.SdlSession}
 */
public class SdlSessionTests extends TestCase {

	public void testServiceListeners(){
		SdlSession session = new SdlSession(new MockInterfaceBroker(),  new MultiplexTransportConfig(getTargetContext(),"19216801"));
		ISdlServiceListener test = new ISdlServiceListener() {
			@Override
			public void onServiceStarted(SdlSession session, SessionType type, boolean isEncrypted) {

			}

			@Override
			public void onServiceEnded(SdlSession session, SessionType type) {

			}

			@Override
			public void onServiceError(SdlSession session, SessionType type, String reason) {

			}
		};

		session.addServiceListener(SessionType.RPC, test);
		assertNotNull(session.getServiceListeners());

		assertTrue(session.removeServiceListener(SessionType.RPC, test));

		assertFalse(session.removeServiceListener(SessionType.RPC, test));

	}


}