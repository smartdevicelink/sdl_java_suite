package com.smartdevicelink.managers.lockscreen;

import com.smartdevicelink.AndroidTestCase2;
import com.smartdevicelink.test.TestValues;

/**
 * This is a unit test class for the SmartDeviceLink library manager class :
 * {@link com.smartdevicelink.managers.lockscreen.LockScreenConfig}
 *
 * We currently do not need to test null values, as each currently is a primitive
 */
public class LockScreenConfigTests extends AndroidTestCase2 {

	private LockScreenConfig lockScreenConfig;

	@Override
	public void setUp() throws Exception {
		super.setUp();

		// set info for all the setters
		lockScreenConfig = new LockScreenConfig();
		lockScreenConfig.setCustomView(TestValues.GENERAL_INT);
		lockScreenConfig.setAppIcon(TestValues.GENERAL_INT);
		lockScreenConfig.setBackgroundColor(TestValues.GENERAL_INT);
		lockScreenConfig.showDeviceLogo(true);
		lockScreenConfig.setEnabled(true);
		lockScreenConfig.setDisplayMode(LockScreenConfig.DISPLAY_MODE_OPTIONAL_OR_REQUIRED);
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	public void testLockScreenConfig() {

		// get the info and make sure its correct
		assertEquals(TestValues.GENERAL_INT, lockScreenConfig.getCustomView());
		assertEquals(TestValues.GENERAL_INT, lockScreenConfig.getAppIcon());
		assertEquals(TestValues.GENERAL_INT, lockScreenConfig.getBackgroundColor());
		assertTrue(lockScreenConfig.isEnabled());
		assertTrue(lockScreenConfig.isDeviceLogoEnabled());
		assertEquals(LockScreenConfig.DISPLAY_MODE_OPTIONAL_OR_REQUIRED, lockScreenConfig.getDisplayMode());
	}

}