/*
 * Copyright (c) 2017 - 2019, SmartDeviceLink Consortium, Inc.
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
 * Neither the name of the SmartDeviceLink Consortium, Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from this 
 * software without specific prior written permission.
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
package com.smartdevicelink.protocol.heartbeat;


import android.os.Handler;
import android.os.Looper;

import androidx.annotation.RestrictTo;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public class HeartbeatMonitor implements IHeartbeatMonitor {

	public static final int HEARTBEAT_INTERVAL = 5000;
    public static final int HEARTBEAT_INTERVAL_MAX = Integer.MAX_VALUE;

    private final Object heartbeatThreadHandlerLock = new Object();
    private final Object listenerLock = new Object();

    private int mHeartBeatInterval = HEARTBEAT_INTERVAL;
    private boolean mHeartBeatAck = true;

    private IHeartbeatMonitorListener mListener;
    private volatile boolean mIsAckReceived;
    private volatile boolean isHeartbeatReceived;
    private Thread mHeartbeatThread;
    private Looper mHeartbeatThreadLooper;
    private Handler mHeartbeatThreadHandler;

    public HeartbeatMonitor() {
    }

    // Methods used to retrieve values for unit testing only.
    // See com/smartdevicelink/tests/protocol/heartbeat/HeartbeatMonitorTests.
    public Runnable getHeartbeatRunnable () { return heartbeatTimeoutRunnable; }
    public boolean isHeartbeatReceived () { return isHeartbeatReceived; }
    
    private Runnable heartbeatTimeoutRunnable = new Runnable() {

        @Override
        public void run() {            
        	try{
	        	synchronized (listenerLock) {
	                if (isHeartbeatReceived) {
	                    if (mListener != null) {
	                        mListener.sendHeartbeat(HeartbeatMonitor.this);
	                    } else {
	
	                    }
	                    isHeartbeatReceived = false;
	                } else {
	                    if (mListener != null) {
	                        mListener.heartbeatTimedOut(HeartbeatMonitor.this);
	                    }
	                }
	            }
        	}
        	catch(Exception ex) 
        	{ 
        		stop(); 
        	}
        }
    };

    private Runnable heartbeatAckTimeoutRunnable = new Runnable() {

        @Override
        public void run() {
        	
        	try
        	{
	        	synchronized (listenerLock) {
	                if (mIsAckReceived) {
	                    if (mListener != null) {
	                        mListener.sendHeartbeat(HeartbeatMonitor.this);
	                    } else {
	                    }
	                    mIsAckReceived = false;
	                } else {
	                    if (mListener != null) {
	                        mListener.heartbeatTimedOut(HeartbeatMonitor.this);
	                    }
	                    stop();
	                }
	            }
        	
        	}
        	catch(Exception ex)	
        	{
        		stop();
        	}
        	        
            rescheduleHeartbeat();
        }

        private void rescheduleHeartbeat() {
            synchronized (heartbeatThreadHandlerLock) {
                if (mHeartbeatThreadHandler != null) {
                    if (!Thread.interrupted()) {
                        if (!mHeartbeatThreadHandler.postDelayed(this, mHeartBeatInterval)) {
                        }
                    } else {
                    }
                } else {
                    stop();
                }
            }
        }
    };

    @Override
    public void start() {

        synchronized (heartbeatThreadHandlerLock) {

            if (mHeartbeatThread != null) {
                return;
            }

            mHeartbeatThread = new Thread(new Runnable() {

                @Override
                public void run() {

                    while (!Thread.interrupted()) {
                        Looper.prepare();
                        mHeartbeatThreadLooper = Looper.myLooper();

                        mHeartbeatThreadHandler = new Handler();
                        mIsAckReceived = true;
                        isHeartbeatReceived = true;

                        if (!mHeartbeatThreadHandler.postDelayed(
                                heartbeatAckTimeoutRunnable, mHeartBeatInterval)) {
                        }
                        Looper.loop();
                    }
                }

            }, "HeartbeatThread");
            mHeartbeatThread.setPriority(Thread.MAX_PRIORITY);
            mHeartbeatThread.start();
        }
    }

    @Override
    public void stop() {


        synchronized (heartbeatThreadHandlerLock) {

            if (mHeartbeatThread == null) {
                mHeartbeatThreadHandler = null;
                mHeartbeatThreadLooper = null;
                return;
            }

            mHeartbeatThread.interrupt();
            mHeartbeatThread = null;

            if (mHeartbeatThreadHandler != null) {
                mHeartbeatThreadHandler.removeCallbacks(heartbeatAckTimeoutRunnable);
                mHeartbeatThreadHandler.removeCallbacks(heartbeatTimeoutRunnable);
                mHeartbeatThreadHandler = null;
            } else {
            }

            if (mHeartbeatThreadLooper != null) {
                mHeartbeatThreadLooper.quit();
                mHeartbeatThreadLooper = null;
            } else {
            }
        }
    }

    @Override
    public int getInterval() {
        return mHeartBeatInterval;
    }

    @Override
    public void setInterval(int value) {
        mHeartBeatInterval = value;
    }

    @Override
    public IHeartbeatMonitorListener getListener() {
        return mListener;
    }

    @Override
    public void setListener(IHeartbeatMonitorListener value) {
        mListener = value;
    }

    @Override
    public void notifyTransportActivity() {
        if (mHeartbeatThreadHandler == null) {
            return;
        }
        synchronized (heartbeatThreadHandlerLock) {
            if (mHeartbeatThreadHandler == null) {
                return;
            }
            mHeartbeatThreadHandler.removeCallbacks(heartbeatAckTimeoutRunnable);
            if (!mHeartbeatThreadHandler.postDelayed(heartbeatAckTimeoutRunnable, mHeartBeatInterval)) {
            }
        }
    }


    @Override
    public synchronized void heartbeatACKReceived() {
        synchronized (listenerLock) {
            mIsAckReceived = true;
        }
    }

    @Override
    public void heartbeatReceived() {
        if (mHeartbeatThreadHandler == null) {
            return;
        }
        synchronized (listenerLock) {
            if (mHeartBeatAck) {
                isHeartbeatReceived = true;
                if (!mHeartbeatThreadHandler.post(heartbeatTimeoutRunnable)) {
                }
            }
        }
    }
}