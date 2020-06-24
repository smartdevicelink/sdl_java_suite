package com.smartdevicelink.managers.screen;

import com.smartdevicelink.AndroidTestCase2;
import com.smartdevicelink.managers.BaseSubManager;
import com.smartdevicelink.managers.OnButtonListener;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.OnButtonEvent;
import com.smartdevicelink.proxy.rpc.OnButtonPress;
import com.smartdevicelink.proxy.rpc.SubscribeButton;
import com.smartdevicelink.proxy.rpc.SubscribeButtonResponse;
import com.smartdevicelink.proxy.rpc.UnsubscribeButton;
import com.smartdevicelink.proxy.rpc.UnsubscribeButtonResponse;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.proxy.rpc.enums.Result;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public class SubscribeButtonManagerTest extends AndroidTestCase2 {
    private SubscribeButtonManager subscribeButtonManager;
    private ISdl internalInterface;

    private Answer<Void> onSubscribe_UnsubscribeSuccess = new Answer<Void>() {
        @Override
        public Void answer(InvocationOnMock invocation) {
            Object[] args = invocation.getArguments();
            RPCRequest message = (RPCRequest) args[0];
            if(message instanceof SubscribeButton){
                SubscribeButtonResponse subscribeButtonResponse = new SubscribeButtonResponse();
                subscribeButtonResponse.setSuccess(true);
                message.getOnRPCResponseListener().onResponse(message.getCorrelationID(),subscribeButtonResponse);
            }
            if(message instanceof UnsubscribeButton) {
                UnsubscribeButtonResponse unsubscribeButtonResponse = new UnsubscribeButtonResponse();
                unsubscribeButtonResponse.setSuccess(true);
                message.getOnRPCResponseListener().onResponse(message.getCorrelationID(), unsubscribeButtonResponse);
            }
            return null;
        }
    };


    private Answer<Void> onSubscribeFail = new Answer<Void>() {
        @Override
        public Void answer(InvocationOnMock invocation) {
            Object[] args = invocation.getArguments();
            RPCRequest message = (RPCRequest) args[0];
            if(message instanceof SubscribeButton){
                SubscribeButtonResponse subscribeButtonResponse = new SubscribeButtonResponse();
                subscribeButtonResponse.setSuccess(false);
                message.getOnRPCResponseListener().onError(message.getCorrelationID(), Result.GENERIC_ERROR, "Fail");
            }
            return null;
        }
    };
    private OnButtonListener listener = new OnButtonListener() {
        @Override
        public void onPress(ButtonName buttonName, OnButtonPress buttonPress) {

        }

        @Override
        public void onEvent(ButtonName buttonName, OnButtonEvent buttonEvent) {

        }

        @Override
        public void onError(String info) {

        }
    };

    private OnButtonListener listener2 = new OnButtonListener() {
        @Override
        public void onPress(ButtonName buttonName, OnButtonPress buttonPress) {

        }

        @Override
        public void onEvent(ButtonName buttonName, OnButtonEvent buttonEvent) {

        }

        @Override
        public void onError(String info) {

        }
    };

    @Override
    public void setUp() throws Exception {
        super.setUp();
        internalInterface = mock(ISdl.class);
        subscribeButtonManager = new SubscribeButtonManager(internalInterface);
    }

    public void testInstantiation(){
        assertNotNull(subscribeButtonManager.onButtonListeners);
        assertEquals(subscribeButtonManager.getState(), BaseSubManager.SETTING_UP);
    }

    public void testDispose() {
        subscribeButtonManager.addButtonListener(ButtonName.VOLUME_UP, listener);
        subscribeButtonManager.dispose();
        assertTrue(subscribeButtonManager.onButtonListeners == null);
    }

    public void testAddButtonListener() {
        doAnswer(onSubscribe_UnsubscribeSuccess).when(internalInterface).sendRPC(any(RPCMessage.class));

        subscribeButtonManager.addButtonListener(null, null);
        assertTrue(subscribeButtonManager.onButtonListeners.size() == 0);

        subscribeButtonManager.addButtonListener(null, listener);
        assertTrue(subscribeButtonManager.onButtonListeners.size() == 0);

        subscribeButtonManager.addButtonListener(ButtonName.VOLUME_UP, listener);
        assertTrue(subscribeButtonManager.onButtonListeners.containsKey(ButtonName.VOLUME_UP));

    }

    public void testAddButtonListenerError(){
        doAnswer(onSubscribeFail).when(internalInterface).sendRPC(any(RPCMessage.class));
        subscribeButtonManager.addButtonListener(ButtonName.VOLUME_UP, listener);
        assertTrue(!subscribeButtonManager.onButtonListeners.containsKey(ButtonName.VOLUME_UP));
    }

    public void testRemoveButtonListener() {
        doAnswer(onSubscribe_UnsubscribeSuccess).when(internalInterface).sendRPC(any(RPCMessage.class));

        subscribeButtonManager.removeButtonListener(ButtonName.VOLUME_DOWN, listener);
        assertFalse(subscribeButtonManager.onButtonListeners.containsKey(ButtonName.VOLUME_DOWN));

        subscribeButtonManager.addButtonListener(ButtonName.VOLUME_UP, listener);
        assertTrue(subscribeButtonManager.onButtonListeners.get(ButtonName.VOLUME_UP).size() == 1);

        subscribeButtonManager.removeButtonListener(ButtonName.VOLUME_UP, listener2);
        assertTrue(subscribeButtonManager.onButtonListeners.get(ButtonName.VOLUME_UP).size() == 1);

        subscribeButtonManager.addButtonListener(ButtonName.VOLUME_UP, listener);
        assertTrue(subscribeButtonManager.onButtonListeners.get(ButtonName.VOLUME_UP).size() == 1);

        subscribeButtonManager.addButtonListener(ButtonName.VOLUME_UP, listener2);
        assertTrue(subscribeButtonManager.onButtonListeners.get(ButtonName.VOLUME_UP).size() == 2);


        subscribeButtonManager.removeButtonListener(ButtonName.VOLUME_UP, listener);
        assertTrue(subscribeButtonManager.onButtonListeners.get(ButtonName.VOLUME_UP).size() == 1);

        subscribeButtonManager.removeButtonListener(ButtonName.VOLUME_UP, listener2);
        assertNull(subscribeButtonManager.onButtonListeners.get(ButtonName.VOLUME_UP));
    }
}
