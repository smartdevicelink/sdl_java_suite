package com.smartdevicelink.transport;

/**
 * Container of Bluetooth transport specific configuration.
 */
public final class BtTransportConfig extends BaseTransportConfig {

	private boolean bKeepSocketActive = true;
	
	/**
	 * Overridden abstract method which returns specific type of this transport configuration.
	 * 
	 * @return Constant value TransportType.BLUETOOTH. 
	 * 
	 * @see TransportType
	 */
	public TransportType getTransportType() {
		return TransportType.BLUETOOTH;
	}
	
	public BtTransportConfig() {
		this(true);
	}
		 
	public BtTransportConfig(boolean shareConnection) {
		super.shareConnection = shareConnection;
	}	
	
	public void setKeepSocketActive(boolean bValue) {
		bKeepSocketActive = bValue;
	}
	
	public boolean getKeepSocketActive() {
		return bKeepSocketActive;
	}	
	
}
