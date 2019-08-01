package com.smartdevicelink.managers.lockscreen;

import android.content.Context;

import com.smartdevicelink.AndroidTestCase2;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
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
		lockScreenConfig.showInOptionalState(true);

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
		assertTrue(lockScreenManager.deviceLogoEnabled);
		assertTrue(lockScreenManager.lockScreenEnabled);
		assertNull(lockScreenManager.deviceLogo);
		assertTrue(lockScreenManager.showInOptionalState);
	}

	public void testGetLockScreenStatusHmiNoneDDOff(){
		lockScreenManager.driverDistStatus = false;
		lockScreenManager.hmiLevel = HMILevel.HMI_NONE;
		assertEquals(LockScreenStatus.OFF, lockScreenManager.getLockScreenStatus());
	}

	public void testGetLockScreenStatusHmiBackgroundDDOff(){
		lockScreenManager.driverDistStatus = false;
		lockScreenManager.hmiLevel = HMILevel.HMI_BACKGROUND;
		assertEquals(LockScreenStatus.OFF, lockScreenManager.getLockScreenStatus());
	}

	public void testGetLockScreenStatusHmiNoneDDOn(){
		lockScreenManager.driverDistStatus = true;
		lockScreenManager.hmiLevel = HMILevel.HMI_BACKGROUND;
		assertEquals(LockScreenStatus.REQUIRED, lockScreenManager.getLockScreenStatus());
	}

	public void testGetLockScreenStatusHmiFullDDOff(){
		lockScreenManager.driverDistStatus = false;
		lockScreenManager.hmiLevel = HMILevel.HMI_FULL;
		assertEquals(LockScreenStatus.OPTIONAL, lockScreenManager.getLockScreenStatus());
	}

	public void testGetLockScreenStatusHmiFullDDOn(){
		lockScreenManager.driverDistStatus = true;
		lockScreenManager.hmiLevel = HMILevel.HMI_FULL;
		assertEquals(LockScreenStatus.REQUIRED, lockScreenManager.getLockScreenStatus());
	}

	public void testGetLockScreenStatusHmiLimitedDDOff(){
		lockScreenManager.driverDistStatus = false;
		lockScreenManager.hmiLevel = HMILevel.HMI_LIMITED;
		assertEquals(LockScreenStatus.OPTIONAL, lockScreenManager.getLockScreenStatus());
	}

	public void testGetLockScreenStatusHmiLimitedDDOn(){
		lockScreenManager.driverDistStatus = true;
		lockScreenManager.hmiLevel = HMILevel.HMI_LIMITED;
		assertEquals(LockScreenStatus.REQUIRED, lockScreenManager.getLockScreenStatus());
	}

}
