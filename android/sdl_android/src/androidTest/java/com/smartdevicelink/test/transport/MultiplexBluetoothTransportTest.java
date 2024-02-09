package com.smartdevicelink.test.transport;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.smartdevicelink.test.util.DeviceUtil;
import com.smartdevicelink.transport.MultiplexBluetoothTransport;
import com.smartdevicelink.transport.SdlRouterService;

import junit.framework.TestCase;


public class MultiplexBluetoothTransportTest extends TestCase {

    private static final Object REQUEST_LOCK = new Object();

    MultiplexBluetoothTransport bluetooth;
    boolean didCorrectThing = false, isWaitingForResponse = false;

    //Example handler
    Handler stateChangeHandler;

    public void testStateTransitions() {
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }

        stateChangeHandler = new Handler() {
            int stateDesired = MultiplexBluetoothTransport.STATE_LISTEN;

            @Override
            public void handleMessage(Message msg) {
                if (!isWaitingForResponse) {
                    return;
                }
                switch (msg.what) {
                    case SdlRouterService.MESSAGE_STATE_CHANGE:
                        if (msg.arg1 == stateDesired) {
                            didCorrectThing = true;
                            break;
                        }
                    default:
                        didCorrectThing = false;
                }
                REQUEST_LOCK.notify();
            }

        };

        bluetooth = new MultiplexBluetoothTransport(stateChangeHandler);
        assertEquals(bluetooth.getState(), MultiplexBluetoothTransport.STATE_NONE);

        bluetooth.start();
/*        if (DeviceUtil.isEmulator() && Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            assertEquals(bluetooth.getState(), MultiplexBluetoothTransport.STATE_NONE);
        } else {
            assertEquals(bluetooth.getState(), MultiplexBluetoothTransport.STATE_LISTEN);
        }*/
         if(Build.VERSION.SDK_INT == Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
             assertEquals(34, bluetooth.getState());

        } else if(Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU) {
             assertEquals(33, bluetooth.getState());

         } else if(Build.VERSION.SDK_INT == Build.VERSION_CODES.S_V2) {
             assertEquals(32, bluetooth.getState());

         }  else if(Build.VERSION.SDK_INT == Build.VERSION_CODES.S) {
             assertEquals(31, bluetooth.getState());

         }else if(Build.VERSION.SDK_INT == Build.VERSION_CODES.R) {
             assertEquals(30, bluetooth.getState());

        } else if(Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
             assertEquals(29, bluetooth.getState());

        } else if(Build.VERSION.SDK_INT == Build.VERSION_CODES.P) {
             assertEquals(28, bluetooth.getState());
        } else if(Build.VERSION.SDK_INT == Build.VERSION_CODES.O) {
             assertEquals(26, bluetooth.getState());

        }  else if(Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN) {
             assertEquals(16, bluetooth.getState());

        } else {
             assertEquals(Build.VERSION.SDK_INT, bluetooth.getState());

         }

        bluetooth.stop();
        assertEquals(bluetooth.getState(), MultiplexBluetoothTransport.STATE_NONE);
    }

    private void notifyResponseReceived() {
        REQUEST_LOCK.notify();
    }

    private void waitForResponse() {
        synchronized (REQUEST_LOCK) {
            try {
                REQUEST_LOCK.wait();
                assertTrue(didCorrectThing);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}