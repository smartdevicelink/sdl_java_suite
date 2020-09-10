/*
 * Copyright (c) 2018 Livio, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the Livio Inc. nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package com.smartdevicelink.transport;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.smartdevicelink.transport.enums.TransportType;
import com.smartdevicelink.transport.utl.TransportRecord;

public abstract class MultiplexBaseTransport {

    // Constants that indicate the current connection state
    public static final int STATE_NONE 			= 0;    // we're doing nothing
    public static final int STATE_LISTEN 		= 1;    // now listening for incoming connections
    public static final int STATE_CONNECTING	= 2; 	// now initiating an outgoing connection
    public static final int STATE_CONNECTED 	= 3;  	// now connected to a remote device
    public static final int STATE_ERROR 		= 4;  	// Something bad happened, we wil not try to restart the thread

    public static final String ERROR_REASON_KEY = "ERROR_REASON";
    public static final byte REASON_SPP_ERROR   = 0x01;    // REASON = SPP error, which is sent through bundle.
    public static final byte REASON_NONE        = 0x0;

    public static final String LOG = "log";
    public static final String DEVICE_NAME = "device_name";
    public static final String DEVICE_ADDRESS = "device_address";

    protected int mState = STATE_NONE;
    protected final Handler handler;
    protected final TransportType transportType;

    protected TransportRecord transportRecord;
    protected String connectedDeviceName = null;
    public String connectedDeviceAddress = null;


    protected MultiplexBaseTransport(Handler handler, TransportType transportType){
        this.handler = handler;
        this.transportType = transportType;
    }

    protected synchronized void setState(int state) {
        setState(state, null);
    }

    protected synchronized void setState(int state, Bundle bundle) {
        if(state == mState){
            return; //State hasn't changed. Will not updated listeners.
        }
        //Log.d(TAG, "Setting state from: " +mState + " to: " +state);
        int arg2 = mState;
        mState = state;

        // Give the new state to the Handler so the UI Activity can update
        //Also sending the previous state so we know if we lost a connection
        Message msg = handler.obtainMessage(SdlRouterService.MESSAGE_STATE_CHANGE, state, arg2, getTransportRecord());
        msg.setData(bundle);
        msg.sendToTarget();
    }

    public String getAddress(){
        return connectedDeviceAddress;
    }

    public String getDeviceName(){
        return connectedDeviceName;
    }

    /**
     * Should only be called after a connection has been established
     * @return
     */
    public TransportRecord getTransportRecord() {
        if(transportRecord == null){
            transportRecord = new TransportRecord(transportType,connectedDeviceAddress);
        }
        return transportRecord;
    }

    /**
     * Return the current connection state. */
    public synchronized int getState() {
        return mState;
    }

    public boolean isConnected()
    {
        return (mState == STATE_CONNECTED);
    }

    public synchronized void stop() {
        stop(STATE_NONE);
    }

    protected abstract void stop(int state);
    protected void stop(int state, byte error) {};

    public abstract void write(byte[] out,  int offset, int count);

}
