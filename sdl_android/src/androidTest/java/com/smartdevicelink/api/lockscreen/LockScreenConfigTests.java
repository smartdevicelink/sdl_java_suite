package com.smartdevicelink.api.lockscreen;

import android.graphics.Color;
import android.test.AndroidTestCase;

import com.smartdevicelink.R;
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library manager class :
 * {@link com.smartdevicelink.api.SdlManager}
 */
public class LockScreenConfigTests extends AndroidTestCase {
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	public void testLockScreenConfig() {

		assertEquals(R.drawable.sdl_lockscreen_icon, Test.GENERAL_LOCKSCREENCONFIG.getAppIcon());
		assertEquals(Color.BLUE, Test.GENERAL_LOCKSCREENCONFIG.getBackgroundColor());
		assertEquals(true, Test.GENERAL_LOCKSCREENCONFIG.getEnabled());
		assertEquals(R.layout.activity_sdllock_screen, Test.GENERAL_LOCKSCREENCONFIG.getCustomView());
	}

}