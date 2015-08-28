package com.smartdevicelink.protocol.heartbeat;


public interface IHeartbeatMonitor {
    /**
     * Starts the monitor. If the monitor is already started, nothing happens.
     */
    public void start();

    /**
     * Stops the monitor. Does nothing if it is already stopped.
     */
    public void stop();

    /**
     * Returns the heartbeat messages interval.
     *
     * @return interval in milliseconds
     */
    public int getInterval();

    /**
     * Sets the interval for sending heartbeat messages if nothing is sent over
     * transport.
     *
     * @param interval interval in milliseconds (min/max values depend on
     *                 concrete implementations)
     */
    public void setInterval(int interval);

    /**
     * Returns the listener.
     *
     * @return the listener
     */
    public IHeartbeatMonitorListener getListener();

    /**
     * Sets the heartbeat's listener.
     *
     * @param listener the new listener
     */
    public void setListener(IHeartbeatMonitorListener listener);

    /**
     * Notifies the monitor about sent/received messages.
     */
    public void notifyTransportActivity();

    /**
     * Notifies the monitor about a received heartbeat ACK message.
     */
    public void heartbeatACKReceived();
    
    /**
     * Notifies the monitor about a received heartbeat message.
     */
    public void heartbeatReceived();
}