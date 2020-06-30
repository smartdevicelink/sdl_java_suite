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
package com.smartdevicelink.transport;

import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.trace.SdlTrace;
import com.smartdevicelink.trace.enums.InterfaceActivityDirection;
import com.smartdevicelink.transport.enums.TransportType;
import com.smartdevicelink.util.DebugTool;

public abstract class SdlTransport {
	private static final String TAG = "SdlTransport";
	private static final String SDL_LIB_TRACE_KEY = "42baba60-eb57-11df-98cf-0800200c9a66";
	
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
    protected void handleReceivedPacket(SdlPacket packet) {
		try {
			// Trace received data
			if (packet!=null) {
				// Send transport data to the siphon server
				//FIXME SiphonServer.sendBytesFromSDL(receivedBytes, 0, receivedBytesLength);
				//SdlTrace.logTransportEvent("", null, InterfaceActivityDirection.Receive, receivedBytes, receivedBytesLength, SDL_LIB_TRACE_KEY);
				
				_transportListener.onTransportPacketReceived(packet);
			} // end-if
		} catch (Exception excp) {
			DebugTool.logError(TAG, FailurePropagating_Msg + "handleBytesFromTransport: " + excp.toString(), excp);
			handleTransportError(FailurePropagating_Msg, excp);
		} // end-catch
    } // end-method

    // This method must be implemented by transport subclass, and is called by this
    // base class to actually send an array of bytes out over the transport.  This
    // method is meant to only be callable within the class hierarchy.
    protected abstract boolean sendBytesOverTransport(SdlPacket packet);

    // This method is called by whomever has reference to transport to have bytes
    // sent out over transport.
   /* public boolean sendBytes(byte[] message) {
        return sendBytes(message, 0, message.length);
    }*/ // end-method
    
    // This method is called by whomever has reference to transport to have bytes
    // sent out over transport.
    public boolean sendBytes(SdlPacket packet) {
        boolean bytesWereSent = false;
        synchronized (_sendLockObj) {
        	bytesWereSent = sendBytesOverTransport(packet);//message, offset, length);
        } // end-lock
        // Send transport data to the siphon server
		//FIXME SiphonServer.sendBytesFromAPP(message, offset, length);
        
		//FIXME SdlTrace.logTransportEvent("", null, InterfaceActivityDirection.Transmit, message, offset, length, SDL_LIB_TRACE_KEY);
        return bytesWereSent;
    } // end-method

    private ITransportListener _transportListener = null;

    // This method is called by the subclass to indicate that transport connection
    // has been established.
	protected void handleTransportConnected() {
		isConnected = true;
		try {
	    	SdlTrace.logTransportEvent("Transport.connected", null, InterfaceActivityDirection.Receive, null, 0, SDL_LIB_TRACE_KEY);
			_transportListener.onTransportConnected();
		} catch (Exception excp) {
			DebugTool.logError(TAG, FailurePropagating_Msg + "onTransportConnected: " + excp.toString(), excp);
			handleTransportError(FailurePropagating_Msg + "onTransportConnected", excp);
		} // end-catch
	} // end-method
	
    // This method is called by the subclass to indicate that transport disconnection
    // has occurred.
	protected void handleTransportDisconnected(final String info) {
		isConnected = false;

		try {
	    	SdlTrace.logTransportEvent("Transport.disconnect: " + info, null, InterfaceActivityDirection.Transmit, null, 0, SDL_LIB_TRACE_KEY);
			_transportListener.onTransportDisconnected(info);
		} catch (Exception excp) {
			DebugTool.logError(TAG, FailurePropagating_Msg + "onTransportDisconnected: " + excp.toString(), excp);
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
