package com.smartdevicelink.transport;

import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;

import com.smartdevicelink.transport.enums.TransportType;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeoutException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

/**
 * Created by mat on 6/29/17.
 */


public class SdlRouterServiceTests {

	@Before
	public void setup() {
//		PowerMockito.mockStatic(Log.class);
	}

	@Test
	public void testOnTransportConnected(){
		SdlRouterService spy = spy(new SdlRouterService());
		TransportType mockTransportType = mock(TransportType.class);


	}

}
