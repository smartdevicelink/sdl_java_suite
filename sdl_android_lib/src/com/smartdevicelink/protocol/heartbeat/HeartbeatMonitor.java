package com.smartdevicelink.protocol.heartbeat;

import com.smartdevicelink.protocol.interfaces.IHeartbeatMonitor;
import com.smartdevicelink.protocol.interfaces.IHeartbeatMonitorListener;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public class HeartbeatMonitor implements IHeartbeatMonitor {
    private static final String TAG = HeartbeatMonitor.class.getSimpleName();
    private final Object HEARTBEAT_THREAD_HANDLER_LOCK = new Object();
    private final Object LISTENER_LOCK = new Object();
    //
    private int interval;
    private IHeartbeatMonitorListener listener;
    private boolean ackReceived;
    private Thread heartbeatThread;
    private Looper heartbeatThreadLooper;
    private Handler heartbeatThreadHandler;
    
    private Runnable heartbeatTimeoutRunnable = new Runnable() {
        @Override
        public void run() {
        	
            synchronized (LISTENER_LOCK) {
                Log.d(TAG, "run()");

                if (ackReceived) {
                    Log.d(TAG,
                            "ACK has been received, sending and scheduling heartbeat");
                    if (listener != null) {
                        listener.sendHeartbeat(HeartbeatMonitor.this);
                    } else {
                        Log.w(TAG,
                                "Delegate is not set, scheduling heartbeat anyway");
                    }
                    ackReceived = false;
                } else {
                    Log.d(TAG, "ACK has not been received");
                    if (listener != null) {
                    	stop();
                        listener.heartbeatTimedOut(HeartbeatMonitor.this);
                    }
                    // TODO stop?
                }
            }
            rescheduleHeartbeat();
        }

        private void rescheduleHeartbeat() {
            synchronized (HEARTBEAT_THREAD_HANDLER_LOCK) {
                if (heartbeatThreadHandler != null) {
                    if (!Thread.interrupted()) {
                        Log.d(TAG, "Rescheduling run()");
                        if (!heartbeatThreadHandler.postDelayed(this,
                                interval)) {
                            Log.e(TAG, "Couldn't reschedule run()");
                        }
                    } else {
                        Log.i(TAG,
                                "The thread is interrupted; not scheduling heartbeat");
                    }
                } else {
                    Log.e(TAG,
                            "HeartbeatThread handler is not set; not scheduling heartbeat");
                    HeartbeatMonitor.this.stop();
                }
            }
        }
    };

    @Override
    public void start() {
        synchronized (HEARTBEAT_THREAD_HANDLER_LOCK) {
            if (heartbeatThread == null) {
                heartbeatThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (!Thread.interrupted()) {
                            Looper.prepare();
                            heartbeatThreadLooper = Looper.myLooper();

                            heartbeatThreadHandler = new Handler();
                            Log.d(TAG, "scheduling run()");
                            ackReceived = true;
                            if (!heartbeatThreadHandler.postDelayed(
                                    heartbeatTimeoutRunnable, interval)) {
                                Log.e(TAG, "Couldn't schedule run()");
                            }

                            Log.d(TAG, "Starting looper");
                            Looper.loop();
                            Log.d(TAG, "Looper stopped, exiting thread");
                        } else {
                            Log.i(TAG,
                                    "HeartbeatThread is run, but already interrupted");
                        }
                    }
                }, "HeartbeatThread");
                heartbeatThread.start();
            } else {
                Log.d(TAG, "HeartbeatThread is already started; doing nothing");
            }
        }
    }

    @Override
    public void stop() {
        synchronized (HEARTBEAT_THREAD_HANDLER_LOCK) {
        	
            if (heartbeatThread != null) {
                heartbeatThread.interrupt();
                heartbeatThread = null;

                if (heartbeatThreadHandler != null) {
                    heartbeatThreadHandler.removeCallbacks(
                            heartbeatTimeoutRunnable);
                    heartbeatThreadHandler = null;
                } else {
                    Log.e(TAG, "HeartbeatThread's handler is null");
                }

                if (heartbeatThreadLooper != null) {
                    heartbeatThreadLooper.quit();
                    heartbeatThreadLooper = null;
                } else {
                    Log.e(TAG, "HeartbeatThread's looper is null");
                }
            } else {
                Log.d(TAG, "HeartbeatThread is not started");
                // just in case
                heartbeatThreadHandler = null;
                heartbeatThreadLooper = null;
            }
        }
    }

    @Override
    public int getInterval() {
        return interval;
    }

    @Override
    public void setInterval(int interval) {
        this.interval = interval;
    }

    @Override
    public IHeartbeatMonitorListener getListener() {
        return listener;
    }

    @Override
    public void setListener(IHeartbeatMonitorListener listener) {
        this.listener = listener;
    }

    @Override
    public void notifyTransportActivity() {
        synchronized (HEARTBEAT_THREAD_HANDLER_LOCK) {
            if (heartbeatThreadHandler != null) {
                heartbeatThreadHandler.removeCallbacks(
                        heartbeatTimeoutRunnable);
                if (!heartbeatThreadHandler.postDelayed(
                        heartbeatTimeoutRunnable, interval)) {
                    Log.e(TAG, "Couldn't reschedule run()");
                }
            }
        }
    }

    @Override
    public void heartbeatAckReceived() {
        synchronized (LISTENER_LOCK) {
            Log.d(TAG, "ACK received");
            ackReceived = true;
        }
    }
}
