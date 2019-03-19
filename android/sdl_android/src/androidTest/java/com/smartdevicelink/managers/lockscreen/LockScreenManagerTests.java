package com.smartdevicelink.managers.lockscreen;

import android.content.Context;

import com.smartdevicelink.AndroidTestCase2;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.enums.LockScreenStatus;
import com.smartdevicelink.test.Test;

import static org.mockito.Mockito.mock;

/**
 * This is a unit test class for the SmartDeviceLink library manager class :
 * {@link com.smartdevicelink.managers.lockscreen.LockScreenManager}
 */
public class LockScreenManagerTests extends AndroidTestCase2 {

	private LockScreenManager lockScreenManager;

	@Override
	public void setUp() throws Exception{
		super.setUp();

		ISdl internalInterface = mock(ISdl.class);

		Context context =  getContext();
		// create config
		LockScreenConfig lockScreenConfig = new LockScreenConfig();
		lockScreenConfig.setCustomView(Test.GENERAL_INT);
		lockScreenConfig.setAppIcon(Test.GENERAL_INT);
		lockScreenConfig.setBackgroundColor(Test.GENERAL_INT);
		lockScreenConfig.showDeviceLogo(true);
		lockScreenConfig.setEnabled(true);

		lockScreenManager = new LockScreenManager(lockScreenConfig, context, internalInterface);
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	public void testVariables() {
		assertEquals(Test.GENERAL_INT, lockScreenManager.customView);
		assertEquals(Test.GENERAL_INT, lockScreenManager.lockScreenIcon);
		assertEquals(Test.GENERAL_INT, lockScreenManager.lockScreenColor);
		assertEquals(true, lockScreenManager.deviceLogoEnabled);
		assertEquals(true, lockScreenManager.lockScreenEnabled);
		assertNull(lockScreenManager.deviceLogo);
	}

	public void testGetLockScreenStatus(){
		assertEquals(LockScreenStatus.OFF, lockScreenManager.getLockScreenStatus());
	}

}
