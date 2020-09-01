package com.smartdevicelink.managers.lockscreen;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.smartdevicelink.test.TestValues;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * This is a unit test class for the SmartDeviceLink library manager class :
 * {@link com.smartdevicelink.managers.lockscreen.LockScreenConfig}
 *
 * We currently do not need to test null values, as each currently is a primitive
 */
@RunWith(AndroidJUnit4.class)
public class LockScreenConfigTests {

	private LockScreenConfig lockScreenConfig;

	@Before
	public void setUp() throws Exception {
		// set info for all the setters
		lockScreenConfig = new LockScreenConfig();
		lockScreenConfig.setCustomView(TestValues.GENERAL_INT);
		lockScreenConfig.setAppIcon(TestValues.GENERAL_INT);
		lockScreenConfig.setBackgroundColor(TestValues.GENERAL_INT);
		lockScreenConfig.showDeviceLogo(true);
		lockScreenConfig.setDisplayMode(LockScreenConfig.DISPLAY_MODE_OPTIONAL_OR_REQUIRED);
	}

	@Test
	public void testLockScreenConfig() {

		// get the info and make sure its correct
		assertEquals(TestValues.GENERAL_INT, lockScreenConfig.getCustomView());
		assertEquals(TestValues.GENERAL_INT, lockScreenConfig.getAppIcon());
		assertEquals(TestValues.GENERAL_INT, lockScreenConfig.getBackgroundColor());
		assertTrue(lockScreenConfig.isDeviceLogoEnabled());
		assertEquals(LockScreenConfig.DISPLAY_MODE_OPTIONAL_OR_REQUIRED, lockScreenConfig.getDisplayMode());
	}

}