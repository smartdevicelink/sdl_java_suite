package com.smartdevicelink.test.SdlConnection;

import com.smartdevicelink.protocol.ISdlServiceListener;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.session.SdlSession;
import com.smartdevicelink.test.streaming.MockInterfaceBroker;
import com.smartdevicelink.transport.MultiplexTransportConfig;

import junit.framework.TestCase;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.session.SdlSession}
 */
public class SdlSessionTests extends TestCase {

    public void testServiceListeners() {
        SdlSession session = new SdlSession(new MockInterfaceBroker(), new MultiplexTransportConfig(getInstrumentation().getTargetContext(), "19216801"));
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