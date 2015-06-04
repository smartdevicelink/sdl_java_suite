package com.smartdevicelink.transport;

import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.trace.SdlTrace;
import com.smartdevicelink.trace.enums.InterfaceActivityDirection;
import com.smartdevicelink.transport.interfaces.ITransportListener;
import com.smartdevicelink.util.DebugTool;

public abstract class SdlTransport {
	private static final String SDL_LIB_TRACE_KEY = "42baba60-eb57-11df-98cf-0800200c9a66";
	
	private final static String FAILURE_PROPOGATING_MSG = "Failure propagating ";
	private Boolean isConnected = false;
	
	private String sendLockObj = "lock";
	
	
	// Get status of transport connection
	public Boolean getIsConnected() {
		return isConnected;
	}

    //protected SdlTransport(String endpointName, String param2, ITransportListener transportListener)
    protected SdlTransport(ITransportListener transportListener) {
    	if (transportListener == null) {
    		throw new IllegalArgumentException("Provided transport listener interface reference is null");
    	} // end-if
    	this.transportListener = transportListener;
    } // end-method
    
    // This method is called by the subclass to indicate that data has arrived from
    // the transport.
    protected void handleReceivedBytes(byte[] receivedBytes, int receivedBytesLength) {
		try {
			// Trace received data
			if (receivedBytesLength > 0) {
				// Send transport data to the siphon server
				SiphonServer.sendBytesFromSDL(receivedBytes, 0, receivedBytesLength);
				SdlTrace.logTransportEvent("", null, InterfaceActivityDirection.Receive, receivedBytes, receivedBytesLength, SDL_LIB_TRACE_KEY);
				
				transportListener.onTransportBytesReceived(receivedBytes, receivedBytesLength);
			} // end-if
		} catch (Exception excp) {
			DebugTool.logError(FAILURE_PROPOGATING_MSG + "handleBytesFromTransport: " + excp.toString(), excp);
			handleTransportError(FAILURE_PROPOGATING_MSG, excp);
		} // end-catch
    } // end-method

    // This method must be implemented by transport subclass, and is called by this
    // base class to actually send an array of bytes out over the transport.  This
    // method is meant to only be callable within the class hierarchy.
    protected abstract boolean sendBytesOverTransport(byte[] msgBytes, int offset, int length);

    // This method is called by whomever has reference to transport to have bytes
    // sent out over transport.
    public boolean sendBytes(byte[] message) {
        return sendBytes(message, 0, message.length);
    } // end-method
    
    // This method is called by whomever has reference to transport to have bytes
    // sent out over transport.
    public boolean sendBytes(byte[] message, int offset, int length) {
        boolean bytesWereSent = false;
        synchronized (sendLockObj) {
        	bytesWereSent = sendBytesOverTransport(message, offset, length);
        } // end-lock
        // Send transport data to the siphon server
		SiphonServer.sendBytesFromAPP(message, offset, length);
        
		SdlTrace.logTransportEvent("", null, InterfaceActivityDirection.Transmit, message, offset, length, SDL_LIB_TRACE_KEY);
        return bytesWereSent;
    } // end-method

    private ITransportListener transportListener = null;

    // This method is called by the subclass to indicate that transport connection
    // has been established.
	protected void handleTransportConnected() {
		isConnected = true;
		try {
	    	SdlTrace.logTransportEvent("Transport.connected", null, InterfaceActivityDirection.Receive, null, 0, SDL_LIB_TRACE_KEY);
			transportListener.onTransportConnected();
		} catch (Exception excp) {
			DebugTool.logError(FAILURE_PROPOGATING_MSG + "onTransportConnected: " + excp.toString(), excp);
			handleTransportError(FAILURE_PROPOGATING_MSG + "onTransportConnected", excp);
		} // end-catch
	} // end-method
	
    // This method is called by the subclass to indicate that transport disconnection
    // has occurred.
	protected void handleTransportDisconnected(final String info) {
		isConnected = false;

		try {
	    	SdlTrace.logTransportEvent("Transport.disconnect: " + info, null, InterfaceActivityDirection.Transmit, null, 0, SDL_LIB_TRACE_KEY);
			transportListener.onTransportDisconnected(info);
		} catch (Exception excp) {
			DebugTool.logError(FAILURE_PROPOGATING_MSG + "onTransportDisconnected: " + excp.toString(), excp);
		} // end-catch
	} // end-method
	
	// This method is called by the subclass to indicate a transport error has occurred.
	protected void handleTransportError(final String message, final Exception ex) {
		isConnected = false;
		transportListener.onTransportError(message, ex);
	}

	public abstract void openConnection() throws SdlException;
	public abstract void disconnect();
	
	/**
	 * Abstract method which should be implemented by subclasses in order to return actual type of the transport. 
	 * 
	 * @return One of {@link TransportType} enumeration values.
	 * 
	 * @see TransportType
	 */
	public abstract TransportType getTransportType();
	
	public abstract String getBroadcastComment();
} // end-class
