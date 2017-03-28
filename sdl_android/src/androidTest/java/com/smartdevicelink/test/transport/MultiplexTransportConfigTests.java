package com.smartdevicelink.test.transport;

import com.smartdevicelink.transport.MultiplexTransportConfig;

import android.test.AndroidTestCase;

public class MultiplexTransportConfigTests extends AndroidTestCase {

	
	public void testDefaultSecurity(){
		MultiplexTransportConfig config = new MultiplexTransportConfig(this.mContext, "2341");
		assertEquals(config.getSecurityLevel(), MultiplexTransportConfig.FLAG_MULTI_SECURITY_MED);
	}
	
	public void testSettingSecurity(){
		MultiplexTransportConfig config = new MultiplexTransportConfig(this.mContext, "2341", MultiplexTransportConfig.FLAG_MULTI_SECURITY_HIGH);
		assertEquals(config.getSecurityLevel(), MultiplexTransportConfig.FLAG_MULTI_SECURITY_HIGH);
		
		config = new MultiplexTransportConfig(this.mContext, "2341");
		config.setSecurityLevel(MultiplexTransportConfig.FLAG_MULTI_SECURITY_HIGH);
		assertEquals(config.getSecurityLevel(), MultiplexTransportConfig.FLAG_MULTI_SECURITY_HIGH);
	}
	
}
