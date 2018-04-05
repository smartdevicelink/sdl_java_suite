package com.smartdevicelink.transport;

import android.os.Handler;

public abstract class MultiplexBaseTransport {

    // Constants that indicate the current connection state
    public static final int STATE_NONE 			= 0;    // we're doing nothing
    public static final int STATE_LISTEN 		= 1;    // now listening for incoming connections
    public static final int STATE_CONNECTING	= 2; 	// now initiating an outgoing connection
    public static final int STATE_CONNECTED 	= 3;  	// now connected to a remote device
    public static final int STATE_ERROR 		= 4;  	// Something bad happend, we wil not try to restart the thread

    public static final String TOAST = "toast";


    protected int mState = STATE_NONE;
    protected final Handler handler;


    public MultiplexBaseTransport(Handler handler){
        this.handler = handler;
    }

    protected synchronized void setState(int state) {
        //Log.d(TAG, "Setting state from: " +mState + " to: " +state);
        int previousState = mState;
        mState = state;

        // Give the new state to the Handler so the UI Activity can update
        //Also sending the previous state so we know if we lost a connection
        handler.obtainMessage(SdlRouterService.MESSAGE_STATE_CHANGE, state, previousState).sendToTarget();
    }

    /**
     * Return the current connection state. */
    public synchronized int getState() {
        return mState;
    }

    public boolean isConnected()
    {
        return !(mState == STATE_NONE);
    }

    public synchronized void stop() {
        stop(STATE_NONE);
    }

    protected abstract void stop(int state);

    public abstract void write(byte[] out,  int offset, int count);

}
