package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.PrimaryAudioSource;


/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.PrimaryAudioSource}
 */
public class PrimaryAudioSourceTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = "NO_SOURCE_SELECTED";
		PrimaryAudioSource enumNoSourceSelected = PrimaryAudioSource.valueForString(example);
		example = "USB";
		PrimaryAudioSource enumUsb = PrimaryAudioSource.valueForString(example);
		example = "USB2";
		PrimaryAudioSource enumUsb2 = PrimaryAudioSource.valueForString(example);
		example = "BLUETOOTH_STEREO_BTST";
		PrimaryAudioSource enumBluetoothStereoBtst = PrimaryAudioSource.valueForString(example);
		example = "LINE_IN";
		PrimaryAudioSource enumLineIn = PrimaryAudioSource.valueForString(example);
		example = "IPOD";
		PrimaryAudioSource enumIpod = PrimaryAudioSource.valueForString(example);
		example = "MOBILE_APP";
		PrimaryAudioSource enumMobileApp = PrimaryAudioSource.valueForString(example);
		
		assertNotNull("NO_SOURCE_SELECTED returned null", enumNoSourceSelected);
		assertNotNull("USB returned null", enumUsb);
		assertNotNull("USB2 returned null", enumUsb2);
		assertNotNull("BLUETOOTH_STEREO_BTST returned null", enumBluetoothStereoBtst);
		assertNotNull("LINE_IN returned null", enumLineIn);
		assertNotNull("IPOD returned null", enumIpod);
		assertNotNull("MOBILE_APP returned null", enumMobileApp);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "no_SouRCe_SelEcteD";
		try {
		    PrimaryAudioSource temp = PrimaryAudioSource.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (IllegalArgumentException exception) {
            fail("Invalid enum throws IllegalArgumentException.");
		}
	}

	/**
	 * Verifies that a null assignment is invalid.
	 */
	public void testNullEnum () {
		String example = null;
		try {
		    PrimaryAudioSource temp = PrimaryAudioSource.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}	
	

	/**
	 * Verifies the possible enum values of PrimaryAudioSource.
	 */
	public void testListEnum() {
 		List<PrimaryAudioSource> enumValueList = Arrays.asList(PrimaryAudioSource.values());

		List<PrimaryAudioSource> enumTestList = new ArrayList<PrimaryAudioSource>();
		enumTestList.add(PrimaryAudioSource.NO_SOURCE_SELECTED);
		enumTestList.add(PrimaryAudioSource.USB);
		enumTestList.add(PrimaryAudioSource.USB2);
		enumTestList.add(PrimaryAudioSource.BLUETOOTH_STEREO_BTST);
		enumTestList.add(PrimaryAudioSource.LINE_IN);
		enumTestList.add(PrimaryAudioSource.IPOD);		
		enumTestList.add(PrimaryAudioSource.MOBILE_APP);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}