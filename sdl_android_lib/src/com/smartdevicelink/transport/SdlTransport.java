package com.smartdevicelink.transport;

import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.transport.enums.TransportType;
import com.smartdevicelink.util.SdlLog;
import com.smartdevicelink.util.SdlLog.Mod;

public abstract class SdlTransport {
	
	private final static String FailurePropagating_Msg = "Failure propagating ";
	private Boolean isConnected = false;
	
	private String _sendLockObj = "lock";
	
	
	// Get status of transport connection
	public Boolean getIsConnected() {
		return isConnected;
	}

    //protected SdlTransport(String endpointName, String param2, ITransportListener transportListener)
    protected SdlTransport(ITransportListener transportListener) {
    	if (transportListener == null) {
    		throw new IllegalArgumentException("Provided transport listener interface reference is null");
    	} // end-if
    	_transportListener = transportListener;
    } // end-method
    
    // This method is called by the subclass to indicate that data has arrived from
    // the transport.
    protected void handleReceivedBytes(byte[] receivedBytes, int receivedBytesLength) {
		try {
			// Trace received data
			if (receivedBytesLength > 0) {
				SdlLog.t(Mod.TRANSPORT, SdlLog.buildBasicTraceMessage("Receive", null, receivedBytes));
				
				_transportListener.onTransportBytesReceived(receivedBytes, receivedBytesLength);
			} // end-if
		} catch (Exception excp) {
			SdlLog.e(FailurePropagating_Msg + "handleBytesFromTransport: " + excp.toString(), excp);
			handleTransportError(FailurePropagating_Msg, excp);
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
        synchronized (_sendLockObj) {
        	bytesWereSent = sendBytesOverTransport(message, offset, length);
        }       
		SdlLog.t(Mod.TRANSPORT, SdlLog.buildBasicTraceMessage("Transmit", null, message));
        return bytesWereSent;
    } // end-method

    private ITransportListener _transportListener = null;

    // This method is called by the subclass to indicate that transport connection
    // has been established.
	protected void handleTransportConnected() {
		isConnected = true;
		try {
	    	String message = "SDL Transport: Connected.";
			SdlLog.t(Mod.TRANSPORT, SdlLog.buildBasicTraceMessage("Receive", message, null));
			_transportListener.onTransportConnected();
		} catch (Exception excp) {
			SdlLog.e(FailurePropagating_Msg + "onTransportConnected: " + excp.toString(), excp);
			handleTransportError(FailurePropagating_Msg + "onTransportConnected", excp);
		} // end-catch
	} // end-method
	
    // This method is called by the subclass to indicate that transport disconnection
    // has occurred.
	protected void handleTransportDisconnected(final String info) {
		isConnected = false;

		try {
			String message = "SDL Transport: Disconnected - " + info;
			SdlLog.t(Mod.TRANSPORT, SdlLog.buildBasicTraceMessage("Transmit", message, null));
			_transportListener.onTransportDisconnected(info);
		} catch (Exception excp) {
			SdlLog.e(FailurePropagating_Msg + "onTransportDisconnected: " + excp.toString(), excp);
		} // end-catch
	} // end-method
	
	// This method is called by the subclass to indicate a transport error has occurred.
	protected void handleTransportError(final String message, final Exception ex) {
		isConnected = false;
		_transportListener.onTransportError(message, ex);
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
