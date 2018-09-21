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

import com.smartdevicelink.transport.enums.TransportType;
import com.smartdevicelink.transport.utl.TransportRecord;

import android.content.ComponentName;
import android.content.Context;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class MultiplexTransportConfig extends BaseTransportConfig{

    /**
     * Multiplexing security will be turned off. All router services will be trusted.
     */
    public static final int FLAG_MULTI_SECURITY_OFF         = 0x00;
    /**
     *  Multiplexing security will be minimal. Only trusted router services will be used. Trusted router list will be obtain from 
     *  server. List will be refreshed every <b>30 days</b> or during next connection session if an SDL enabled app has been
     *  installed or uninstalled. 
     */
    public static final int FLAG_MULTI_SECURITY_LOW         = 0x10;
    /**
     *  Multiplexing security will be on at a normal level. Only trusted router services will be used. Trusted router list will be obtain from 
     *  server. List will be refreshed every <b>7 days</b> or during next connection session if an SDL enabled app has been
     *  installed or uninstalled. 
     */
    public static final int FLAG_MULTI_SECURITY_MED         = 0x20;
    /**
     *  Multiplexing security will be very strict. Only trusted router services installed from trusted app stores will 
     *  be used. Trusted router list will be obtain from server. List will be refreshed every <b>7 days</b> 
     *  or during next connection session if an SDL enabled app has been installed or uninstalled. 
     */
    public static final int FLAG_MULTI_SECURITY_HIGH        = 0x30;
	
	Context context;
	String appId;
	ComponentName service;
	int securityLevel;

	List<TransportType> primaryTransports, secondaryTransports;
	boolean requiresHighBandwidth = false;
	TransportListener transportListener;

	
	public MultiplexTransportConfig(Context context, String appId) {
		this.context = context;
		this.appId = appId;
		this.securityLevel = FLAG_MULTI_SECURITY_MED;
		this.primaryTransports = Arrays.asList(TransportType.USB, TransportType.BLUETOOTH);
		this.secondaryTransports = Arrays.asList(TransportType.TCP, TransportType.USB, TransportType.BLUETOOTH);

	}

	public MultiplexTransportConfig(Context context, String appId, int securityLevel) {
		this.context = context;
		this.appId = appId;
		this.securityLevel = securityLevel;
		this.primaryTransports = Arrays.asList(TransportType.USB, TransportType.BLUETOOTH);
		this.secondaryTransports = Arrays.asList(TransportType.TCP, TransportType.USB, TransportType.BLUETOOTH);
	}	

	/**
	 * Overridden abstract method which returns specific type of this transport configuration.
	 * 
	 * @return Constant value TransportType.MULTIPLEX. 
	 * 
	 * @see TransportType
	 */
	public TransportType getTransportType() {
		return TransportType.MULTIPLEX;
	}
	
	public Context getContext(){
		return this.context;
	}


	public ComponentName getService() {
		return service;
	}


	public void setService(ComponentName service) {
		this.service = service;
	}
	
	public void setSecurityLevel(int securityLevel){
		this.securityLevel = securityLevel;
	}
	
	public int getSecurityLevel(){
		return securityLevel;
	}

	public void setRequiresHighBandwidth(boolean requiresHighBandwidth){
		this.requiresHighBandwidth = requiresHighBandwidth;
	}

	public boolean requiresHighBandwidth(){
		return this.requiresHighBandwidth;
	}

	/**
	 * This will set the order in which a primary transport is determined to be accepted or not.
	 * In the case of previous protocol versions ( < 5.1)
	 * @param transports list of transports that can be used as primary
	 */
	public void setPrimaryTransports(List<TransportType> transports){
		if(transports != null){
			//Sanitize
			transports.remove(TransportType.MULTIPLEX);
			this.primaryTransports = transports;
		}
	}

	public List<TransportType> getPrimaryTransports(){
		return this.primaryTransports;
	}

	/**
	 * This will set the order in which a primary transport is determined to be accepted or not.
	 * In the case of previous protocol versions ( < 5.1)
	 * @param transports list of transports that can be used as secondary
	 **/
	public void setSecondaryTransports(List<TransportType> transports){
		if(transports != null){
			//Sanitize
			transports.remove(TransportType.MULTIPLEX);
			this.secondaryTransports = transports;
		}
	}

	public List<TransportType> getSecondaryTransports(){
		return this.secondaryTransports;
	}

	/**
	 * Set a lister for transport events. Useful when connected high bandwidth services like audio
	 * or video streaming
	 * @param listener the TransportListener that will be called back when transport events happen
	 */
	public void setTransportListener(TransportListener listener){
		this.transportListener = listener;
	}

	/**
	 * Getter for the supplied transport listener
	 * @return the transport listener if any
	 */
	public TransportListener getTransportListener(){
		return this.transportListener;
	}

	/**
	 * Callback to be used if the state of the transports needs to be monitored for any reason.
	 */
	public interface TransportListener{
		/**
		 * Gets called whenever there is a change in the available transports for use
		 * @param connectedTransports the currently connected transports
		 * @param audioStreamTransportAvail true if there is either an audio streaming supported
		 *                                        transport currently connected or a transport is
		 *                                        available to connect with. false if there is no
		 *                                        transport connected to support audio streaming and
		 *                                        no possibility in the foreseeable future.
		 * @param videoStreamTransportAvail true if there is either an audio streaming supported
		 *                                        transport currently connected or a transport is
		 *                                        available to connect with. false if there is no
		 *                                        transport connected to support audio streaming and
		 *                                        no possibility in the foreseeable future.
		 */
		void onTransportEvent(List<TransportRecord> connectedTransports, boolean audioStreamTransportAvail,boolean videoStreamTransportAvail);
	}


}
