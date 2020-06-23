package com.smartdevicelink.managers.screen;

import android.content.Context;

import com.smartdevicelink.AndroidTestCase2;
import com.smartdevicelink.managers.OnButtonListener;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.OnButtonEvent;
import com.smartdevicelink.proxy.rpc.OnButtonPress;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;

import static org.mockito.Mockito.mock;

public class SubscribeButtonManagerTest extends AndroidTestCase2 {
    private SubscribeButtonManager subscribeButtonManager;
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
    public void setUp() throws Exception{
        super.setUp();
        Context mTestContext = this.getContext();
        // mock things
        ISdl internalInterface = mock(ISdl.class);
        subscribeButtonManager = new SubscribeButtonManager(internalInterface);
    }

    public void testAddButtonListener() {
        subscribeButtonManager.addButtonListener(null, null);
        assertTrue(subscribeButtonManager.onButtonListeners.size() == 0);

        subscribeButtonManager.addButtonListener(null, listener);
        assertTrue(subscribeButtonManager.onButtonListeners.size() == 0);

        subscribeButtonManager.addButtonListener(ButtonName.VOLUME_UP, listener);
        assertTrue(subscribeButtonManager.onButtonListeners.containsKey(ButtonName.VOLUME_UP));

    }

    public void testRemoveButtonListener(){
        subscribeButtonManager.addButtonListener(ButtonName.VOLUME_UP, listener);
        assertTrue(subscribeButtonManager.onButtonListeners.get(ButtonName.VOLUME_UP).size() == 1);

        subscribeButtonManager.addButtonListener(ButtonName.VOLUME_UP, listener);
        assertTrue(subscribeButtonManager.onButtonListeners.get(ButtonName.VOLUME_UP).size() == 1);

        subscribeButtonManager.addButtonListener(ButtonName.VOLUME_UP, listener2);
        assertTrue(subscribeButtonManager.onButtonListeners.get(ButtonName.VOLUME_UP).size() == 2);


        subscribeButtonManager.removeButtonListener(ButtonName.VOLUME_UP, listener);
        assertTrue(subscribeButtonManager.onButtonListeners.get(ButtonName.VOLUME_UP).size() == 1);

        subscribeButtonManager.removeButtonListener(ButtonName.VOLUME_UP, listener2);
        assertTrue(subscribeButtonManager.onButtonListeners.get(ButtonName.VOLUME_UP).size() == 0);


    }


}
