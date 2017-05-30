package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.PrimaryAudioSource;


/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.PrimaryAudioSource}
 */
public class PrimaryAudioSourceTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.no_source_selected_caps);
		PrimaryAudioSource enumNoSourceSelected = PrimaryAudioSource.valueForString(example);
		example = mContext.getString(R.string.usb_caps);
		PrimaryAudioSource enumUsb = PrimaryAudioSource.valueForString(example);
		example = mContext.getString(R.string.usb_two_caps);
		PrimaryAudioSource enumUsb2 = PrimaryAudioSource.valueForString(example);
		example = mContext.getString(R.string.bt_stereo_btst_caps);
		PrimaryAudioSource enumBluetoothStereoBtst = PrimaryAudioSource.valueForString(example);
		example = mContext.getString(R.string.line_in_caps);
		PrimaryAudioSource enumLineIn = PrimaryAudioSource.valueForString(example);
		example = mContext.getString(R.string.ipod_caps);
		PrimaryAudioSource enumIpod = PrimaryAudioSource.valueForString(example);
		example = mContext.getString(R.string.mobile_app_caps);
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
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    PrimaryAudioSource temp = PrimaryAudioSource.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (IllegalArgumentException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}

	/**
	 * Verifies that a null assignment is invalid.
	 */
	public void testNullEnum () {
		String example = null;
		try {
		    PrimaryAudioSource temp = PrimaryAudioSource.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
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

		assertTrue(mContext.getString(R.string.enum_value_list_does_not_match_enum_class_list),
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}