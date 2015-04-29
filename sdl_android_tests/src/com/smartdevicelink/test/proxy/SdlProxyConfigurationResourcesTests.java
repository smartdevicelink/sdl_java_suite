package com.smartdevicelink.test.proxy;

import com.smartdevicelink.proxy.SdlProxyConfigurationResources;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.test.AndroidTestCase;

public class SdlProxyConfigurationResourcesTests extends AndroidTestCase {
	
	// Note: Used the AndroidTestCase extension for the ability to mock context,
	// which was needed to test for a TelephonyManager.
	
	public void testData () {
		
		String testPath = "sdlConfigurationFilePath";
		TelephonyManager testTM = (TelephonyManager) getContext()
								  .getSystemService(Context.TELEPHONY_SERVICE);
		
		SdlProxyConfigurationResources testSPCR = null;
		
		testSPCR = new SdlProxyConfigurationResources(testPath, testTM);
		assertEquals("File path did not match expected value", testPath, 
					 testSPCR.getSdlConfigurationFilePath());
		assertEquals("TelephonyManager did not match expected value", testTM, 
					 testSPCR.getTelephonyManager());
		
		testSPCR = new SdlProxyConfigurationResources(null, null);
		assertNull("File path was not null", testSPCR.getSdlConfigurationFilePath());
		assertNull("TelephonyManager was not null", testSPCR.getTelephonyManager());
		
		testSPCR.setSdlConfigurationFilePath(testPath);
		assertEquals("File path did not match expected value", testPath, 
					  testSPCR.getSdlConfigurationFilePath());
		testSPCR.setSdlConfigurationFilePath(null);
		assertNull("File path was not null", testSPCR.getSdlConfigurationFilePath());
		
		testSPCR.setTelephonyManager(testTM);
		assertEquals("TelephonyManager did not match expected value", testTM, 
					  testSPCR.getTelephonyManager());
		testSPCR.setTelephonyManager(null);
		assertNull("TelephonyManager was not null", testSPCR.getTelephonyManager());
	}		
}