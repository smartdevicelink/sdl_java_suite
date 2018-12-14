package com.smartdevicelink.protocol.heartbeat;


import android.os.Handler;
import android.os.Looper;


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