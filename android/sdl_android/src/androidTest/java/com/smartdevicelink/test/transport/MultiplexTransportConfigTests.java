package com.smartdevicelink.test.transport;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.smartdevicelink.transport.MultiplexTransportConfig;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertEquals;

@RunWith(AndroidJUnit4.class)
public class MultiplexTransportConfigTests {

	@Test
	public void testDefaultSecurity(){
		MultiplexTransportConfig config = new MultiplexTransportConfig(getInstrumentation().getTargetContext(), "2341");
		assertEquals(config.getSecurityLevel(), MultiplexTransportConfig.FLAG_MULTI_SECURITY_MED);
	}

	@Test
	public void testSettingSecurity(){
		MultiplexTransportConfig config = new MultiplexTransportConfig(getInstrumentation().getTargetContext(), "2341", MultiplexTransportConfig.FLAG_MULTI_SECURITY_HIGH);
		assertEquals(config.getSecurityLevel(), MultiplexTransportConfig.FLAG_MULTI_SECURITY_HIGH);
		
		config = new MultiplexTransportConfig(getInstrumentation().getTargetContext(), "2341");
		config.setSecurityLevel(MultiplexTransportConfig.FLAG_MULTI_SECURITY_HIGH);
		assertEquals(config.getSecurityLevel(), MultiplexTransportConfig.FLAG_MULTI_SECURITY_HIGH);
	}
	
}
