package com.smartdevicelink.api.lockscreen;

import android.graphics.Color;
import android.test.AndroidTestCase;

import com.smartdevicelink.R;

/**
 * This is a unit test class for the SmartDeviceLink library manager class :
 * {@link com.smartdevicelink.api.lockscreen.LockScreenConfig}
 *
 * We currently do not need to test null values, as each currently is a primitive
 */
public class LockScreenConfigTests extends AndroidTestCase {

	private LockScreenConfig lockScreenConfig;

	@Override
	public void setUp() throws Exception {
		super.setUp();

		// set info for all the setters
		lockScreenConfig.setCustomView(R.layout.activity_sdllock_screen);
		lockScreenConfig.setAppIcon(R.drawable.sdl_lockscreen_icon);
		lockScreenConfig.setBackgroundColor(Color.BLUE);
		lockScreenConfig.setEnabled(true);
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	public void testLockScreenConfig() {

		// get the info and make sure its correct
		assertEquals(R.layout.activity_sdllock_screen, lockScreenConfig.getCustomView());
		assertEquals(R.drawable.sdl_lockscreen_icon, lockScreenConfig.getAppIcon());
		assertEquals(Color.BLUE, lockScreenConfig.getBackgroundColor());
		assertEquals(true, lockScreenConfig.getEnabled());
	}

}