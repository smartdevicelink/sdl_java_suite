package com.smartdevicelink.transport;

import android.content.Context;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import junit.framework.TestCase;

import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by mat on 7/12/17.
 */

public class TransportBrokerTests extends TestCase{

	RouterServiceValidator rsvp;

	@Mock
	Context mMockContext;

	Context context;

	protected void setUp() throws Exception {
//		context = InstrumentationRegistry.getTargetContext();

		rsvp = mock(RouterServiceValidator.class);
		rsvp.validate();
	}


	public static final String TEST_APP_ID = "123456";

//
//
//	public void testSendMessageToRouterServiceException() throws Exception{
//		if (Looper.myLooper() == null) {
//			Looper.prepare();
//		}
//		TransportBroker broker = spy(new TransportBroker(mMockContext, TEST_APP_ID ,rsvp.getService()));
//		Messenger mockMessenger = mock(Messenger.class);
//		doThrow(RemoteException.class).when(mockMessenger).send(any(Message.class));
//		broker.routerServiceMessenger = mockMessenger;
//		broker.sendMessageToRouterService(null,0);
//
//		verify(broker, times(5)).sendMessageToRouterService(any(Message.class));
//	}


}
