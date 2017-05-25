package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.DisplayType;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.enums.DisplayType}
 */
public class DisplayTypeTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {
		String example = mContext.getString(R.string.cid_caps);
		DisplayType enumCid = DisplayType.valueForString(example);
		example = mContext.getString(R.string.type_two_caps);
		DisplayType enumType2 = DisplayType.valueForString(example);
		example = mContext.getString(R.string.type_five_caps);
		DisplayType enumType5 = DisplayType.valueForString(example);
		example = mContext.getString(R.string.ngn_caps);
		DisplayType enumNgn = DisplayType.valueForString(example);
		example = mContext.getString(R.string.gen_tewo_eight_dma_caps);
		DisplayType enumGen2_8Dma = DisplayType.valueForString(example);
		example = mContext.getString(R.string.gen_two_six_dma_caps);
		DisplayType enumGen2_6Dma = DisplayType.valueForString(example);
		example = mContext.getString(R.string.mfd_three_caps);
		DisplayType enumMfd3 = DisplayType.valueForString(example);
		example = mContext.getString(R.string.mfd_four_caps);
		DisplayType enumMfd4 = DisplayType.valueForString(example);
		example = mContext.getString(R.string.mfd_five_caps);
		DisplayType enumMfd5 = DisplayType.valueForString(example);
		example = mContext.getString(R.string.gen_three_eight_inch_caps);
		DisplayType enumGen3_8Inch = DisplayType.valueForString(example);
		example = mContext.getString(R.string.sdl_generic_caps);
		DisplayType enumGeneric = DisplayType.valueForString(example);

		assertNotNull("CID returned null", enumCid);
		assertNotNull("TYPE2 returned null", enumType2);
		assertNotNull("TYPE5 returned null", enumType5);
		assertNotNull("NGN returned null", enumNgn);
		assertNotNull("GEN2_8_DMA returned null", enumGen2_8Dma);
		assertNotNull("GEN2_6_DMA returned null", enumGen2_6Dma);
		assertNotNull("MFD3 returned null", enumMfd3);
		assertNotNull("MFD4 returned null", enumMfd4);
		assertNotNull("MFD5 returned null", enumMfd5);
		assertNotNull("GEN3_8-INCH returned null", enumGen3_8Inch);
		assertNotNull("SDL_GENERIC returned null", enumGeneric);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		try {
			DisplayType temp = DisplayType.valueForString(example);
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
			DisplayType temp = DisplayType.valueForString(example);
			assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
			fail("Null string throws NullPointerException.");
		}
	}

	/**
	 * Verifies the possible enum values of DisplayType.
	 */
	public void testListEnum() {
		List<DisplayType> enumValueList = Arrays.asList(DisplayType.values());

		List<DisplayType> enumTestList = new ArrayList<DisplayType>();
		enumTestList.add(DisplayType.CID);
		enumTestList.add(DisplayType.TYPE2);
		enumTestList.add(DisplayType.TYPE5);
		enumTestList.add(DisplayType.NGN);
		enumTestList.add(DisplayType.GEN2_8_DMA);
		enumTestList.add(DisplayType.GEN2_6_DMA);
		enumTestList.add(DisplayType.MFD3);
		enumTestList.add(DisplayType.MFD4);
		enumTestList.add(DisplayType.MFD5);
		enumTestList.add(DisplayType.GEN3_8_INCH);
		enumTestList.add(DisplayType.SDL_GENERIC);

		assertTrue("Enum value list does not match enum class list",
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
}