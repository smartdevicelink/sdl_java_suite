package com.smartdevicelink.transport;

import android.content.Context;
import android.os.Message;
import android.os.Messenger;

import junit.framework.TestCase;

import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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

	protected void setUp() throws Exception {
		rsvp = mock(RouterServiceValidator.class);
		rsvp.validate();
	}


	public static final String TEST_APP_ID = "123456";



	public void testSendMessageToRouterServiceException() throws Exception{

		TransportBroker broker = spy(new TransportBroker(mMockContext, TEST_APP_ID ,rsvp.getService()));
		Message message = new Message();
		message.what = TransportConstants.ROUTER_REGISTER_CLIENT;
		broker.registeredWithRouterService = true;
		broker.isBound = true;

		Messenger mockMessenger = mock(Messenger.class);
		doThrow(new NullPointerException()).when(mockMessenger).send(message);
		broker.routerServiceMessenger = mockMessenger;
		boolean result = broker.sendMessageToRouterService(message);

		assertFalse(result);
		verify(mockMessenger).send(any(Message.class));
		verify(broker, times(1)).sendMessageToRouterService(any(Message.class), anyInt());
	}


}
