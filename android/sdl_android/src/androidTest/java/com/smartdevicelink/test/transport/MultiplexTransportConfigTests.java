package com.smartdevicelink.test.transport;

import android.support.test.runner.AndroidJUnit4;

import com.smartdevicelink.transport.MultiplexTransportConfig;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getContext;
import static junit.framework.TestCase.assertEquals;

@RunWith(AndroidJUnit4.class)
public class MultiplexTransportConfigTests {

	@Test
	public void testDefaultSecurity(){
		MultiplexTransportConfig config = new MultiplexTransportConfig(getContext(), "2341");
		assertEquals(config.getSecurityLevel(), MultiplexTransportConfig.FLAG_MULTI_SECURITY_MED);
	}

	@Test
	public void testSettingSecurity(){
		MultiplexTransportConfig config = new MultiplexTransportConfig(getContext(), "2341", MultiplexTransportConfig.FLAG_MULTI_SECURITY_HIGH);
		assertEquals(config.getSecurityLevel(), MultiplexTransportConfig.FLAG_MULTI_SECURITY_HIGH);
		
		config = new MultiplexTransportConfig(getContext(), "2341");
		config.setSecurityLevel(MultiplexTransportConfig.FLAG_MULTI_SECURITY_HIGH);
		assertEquals(config.getSecurityLevel(), MultiplexTransportConfig.FLAG_MULTI_SECURITY_HIGH);
	}
	
}
