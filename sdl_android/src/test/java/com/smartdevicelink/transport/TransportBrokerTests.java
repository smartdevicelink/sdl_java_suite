package com.smartdevicelink.transport;

import android.content.Context;
import android.os.Message;
import android.os.Messenger;
import android.os.TransactionTooLargeException;

import junit.framework.TestCase;

import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

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
//		Messenger mockMessenger = mock(Messenger.class, new Answer<Object>() {
//			@Override
//			public Object answer(InvocationOnMock invocation) throws Throwable {
////				int count = 0;
////				String methodName = invocation.getMethod().getName();
////				if (count<1 && methodName=="send") {
////					count++;
//						throw new TransactionTooLargeException();
////				} else {
////					throw new NullPointerException();
////				}
//
//			}
//		});

		Messenger mockMessenger = mock(Messenger.class);
		doThrow(TransactionTooLargeException.class).when(mockMessenger).send(any(Message.class));
		broker.routerServiceMessenger = mockMessenger;
		Message message = new Message();
		message.what = TransportConstants.ROUTER_REGISTER_CLIENT;
		broker.registeredWithRouterService = true;
		broker.isBound = true;

		boolean result = true;
		try {
			 result = broker.sendMessageToRouterService(message);
		} catch(Exception e){

		}

		assertFalse(result);
		verify(mockMessenger).send(any(Message.class));
		verify(broker, times(3)).sendMessageToRouterService(any(Message.class), anyInt());
	}


}
