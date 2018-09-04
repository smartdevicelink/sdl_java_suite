package com.smartdevicelink.transport;

import com.smartdevicelink.transport.enums.TransportType;

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


}
