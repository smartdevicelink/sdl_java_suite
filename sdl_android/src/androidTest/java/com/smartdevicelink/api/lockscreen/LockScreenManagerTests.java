package com.smartdevicelink.api.lockscreen;

import android.content.Context;
import android.test.AndroidTestCase;
import android.test.mock.MockContext;

import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.enums.LockScreenStatus;
import com.smartdevicelink.test.Test;

import static org.mockito.Mockito.mock;

/**
 * This is a unit test class for the SmartDeviceLink library manager class :
 * {@link com.smartdevicelink.api.SdlManager}
 */
public class LockScreenManagerTests extends AndroidTestCase {

	private LockScreenManager lockScreenManager;

	@Override
	public void setUp() throws Exception{
		super.setUp();

		ISdl internalInterface = mock(ISdl.class);

		Context context = new MockContext();
		// create config
		LockScreenConfig lockScreenConfig = new LockScreenConfig();
		lockScreenConfig.setCustomView(Test.GENERAL_INT);
		lockScreenConfig.setAppIcon(Test.GENERAL_INT);
		lockScreenConfig.setBackgroundColor(Test.GENERAL_INT);
		lockScreenConfig.displayDeviceLogo(true);
		lockScreenConfig.setEnabled(true);

		lockScreenManager = new LockScreenManager(lockScreenConfig, context, internalInterface);
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	public void testVariables() {
		assertEquals(Test.GENERAL_INT, lockScreenManager.getCustomView());
		assertEquals(Test.GENERAL_INT, lockScreenManager.getLockScreenIcon());
		assertEquals(Test.GENERAL_INT, lockScreenManager.getLockScreenColor());
		assertEquals(true, lockScreenManager.displayDeviceLogo());
		assertEquals(true, lockScreenManager.getLockScreenEnabled());
		assertNull(lockScreenManager.getLockScreenOEMIcon());
	}

	public void testGetLockScreenStatus(){
		assertEquals(LockScreenStatus.OFF, lockScreenManager.testGetLockScreenStatus());
	}

}
