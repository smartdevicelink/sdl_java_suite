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

import android.content.ComponentName;
import android.content.Context;

import com.smartdevicelink.transport.enums.TransportType;
import com.smartdevicelink.transport.utl.TransportRecord;

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
	
	final Context context;
	final String appId;
	ComponentName service;
	int securityLevel;

	List<TransportType> primaryTransports, secondaryTransports;
	boolean requiresHighBandwidth = false;
	Boolean requiresAudioSupport = null;
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

	/**
	 * Gets the context attached to this config
	 * @return context supplied during creation
	 */
	public Context getContext(){
		return this.context;
	}

	/**
	 * Gets the ComponentName of the router service attached to this config
	 * @return ComponentName of the router service that will be bound to
	 */
	public ComponentName getService() {
		return service;
	}

	/**
	 * Supplies the config with the router service that should be bound to
	 * @param service the router service that should be bound to
	 */
	public void setService(ComponentName service) {
		this.service = service;
	}

	/**
	 * Sets the security level that should be used to verify a router service that is to be bound
	 * @param securityLevel the security level that will be used to perform certain tests
	 * @see #FLAG_MULTI_SECURITY_OFF
	 * @see #FLAG_MULTI_SECURITY_LOW
	 * @see #FLAG_MULTI_SECURITY_MED
	 * @see #FLAG_MULTI_SECURITY_HIGH
	 */
	public void setSecurityLevel(int securityLevel){
		this.securityLevel = securityLevel;
	}

	/**
	 * Get the security level that will be used to verify a router service before binding
	 * @return the set security level
	 * @see #FLAG_MULTI_SECURITY_OFF
	 * @see #FLAG_MULTI_SECURITY_LOW
	 * @see #FLAG_MULTI_SECURITY_MED
	 * @see #FLAG_MULTI_SECURITY_HIGH
	 */
	public int getSecurityLevel(){
		return securityLevel;
	}

	/**
	 * Set whether or not this app requires the use of a transport that supports high bandwidth
	 * services. Common use is when an app uses the video/audio streaming services and there is no
	 * other integration that could be useful to the user.
	 * <br><br> <b>For example:</b>
	 * <br><b>1. </b>If an app intends to perform audio or video streaming and does not wish
	 * to appear on the module when that isn't possible, a value of true should be sent.
	 * <br><b>2. </b>If the same app wishes to appear on the module even when those services aren't available
	 * a value of true should be sent. In this case, the app could display a message prompting the
	 * user to "Please connect USB or Wifi" or it could have a separate integration like giving turn
	 * by turn directions in place of streaming the full navigation map.
	 * @param requiresHighBandwidth whether the app should be treated as requiring a high
	 *                                 bandwidth transport.
	 */
	public void setRequiresHighBandwidth(boolean requiresHighBandwidth){
		this.requiresHighBandwidth = requiresHighBandwidth;
	}

	/**
	 * Get the setting from this config to see whether the app should be treated as requiring a high
	 * bandwidth transport.
	 * @return whether the app should be treated as requiring a high
	 * bandwidth transport.
	 */
	public boolean requiresHighBandwidth(){
		return this.requiresHighBandwidth;
	}

	/**
	 * Set whether or not this app requires the use of an audio streaming output device
	 *
	 * @param requiresAudioSupport whether the app should be treated as requiring an audio streaming
	 *                             output device
	 */
	public void setRequiresAudioSupport(boolean requiresAudioSupport){
		this.requiresAudioSupport = requiresAudioSupport;
	}

	/**
	 * Get the setting from this config to see whether the app should be treated as requiring an
	 * audio streaming output device
	 *
	 * @return whether the app should be treated as requiring an audio streaming output device
	 */
	public Boolean requiresAudioSupport(){
		return this.requiresAudioSupport;
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

	/**
	 * Get the list of primary transports that are set to be accepted by this config
	 * @return  acceptable primary transports
	 */
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

	/**
	 * Get the list of secondary transports that are set to be accepted by this config
	 * @return  acceptable secondary transports
	 */
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
		 *                                        available to connect with. False if there is no
		 *                                        transport connected to support audio streaming and
		 *                                        no possibility in the foreseeable future.
		 * @param videoStreamTransportAvail true if there is either a video streaming supported
		 *                                        transport currently connected or a transport is
		 *                                        available to connect with. False if there is no
		 *                                        transport connected to support video streaming and
		 *                                        no possibility in the foreseeable future.
		 */
		void onTransportEvent(List<TransportRecord> connectedTransports, boolean audioStreamTransportAvail,boolean videoStreamTransportAvail);
	}


}
