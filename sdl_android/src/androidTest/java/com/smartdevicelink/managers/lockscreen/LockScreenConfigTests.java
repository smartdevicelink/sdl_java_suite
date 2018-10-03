package com.smartdevicelink.managers.lockscreen;

import com.smartdevicelink.AndroidTestCase2;
import com.smartdevicelink.test.Test;


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
		lockScreenConfig.setCustomView(Test.GENERAL_INT);
		lockScreenConfig.setAppIcon(Test.GENERAL_INT);
		lockScreenConfig.setBackgroundColor(Test.GENERAL_INT);
		lockScreenConfig.showDeviceLogo(true);
		lockScreenConfig.setEnabled(true);
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	public void testLockScreenConfig() {

		// get the info and make sure its correct
		assertEquals(Test.GENERAL_INT, lockScreenConfig.getCustomView());
		assertEquals(Test.GENERAL_INT, lockScreenConfig.getAppIcon());
		assertEquals(Test.GENERAL_INT, lockScreenConfig.getBackgroundColor());
		assertEquals(true, lockScreenConfig.isEnabled());
		assertEquals(true, lockScreenConfig.isDeviceLogoEnabled());
	}

}