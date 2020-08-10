package com.smartdevicelink.managers.lockscreen;

import android.content.Context;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.OnDriverDistraction;
import com.smartdevicelink.proxy.rpc.enums.DriverDistractionState;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.LockScreenStatus;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.test.TestValues;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static androidx.test.platform.app.InstrumentationRegistry.getContext;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

/**
 * This is a unit test class for the SmartDeviceLink library manager class :
 * {@link com.smartdevicelink.managers.lockscreen.LockScreenManager}
 */
@RunWith(AndroidJUnit4.class)
public class LockScreenManagerTests {

	private LockScreenManager lockScreenManager;
	private OnRPCNotificationListener onDDListener;

	@Before
	public void setUp() throws Exception{

		ISdl internalInterface = mock(ISdl.class);

		Answer<Void> onDDStatusAnswer = new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				onDDListener = (OnRPCNotificationListener) args[1];
				return null;
			}
		};
		doAnswer(onDDStatusAnswer).when(internalInterface).addOnRPCNotificationListener(eq(FunctionID.ON_DRIVER_DISTRACTION), any(OnRPCNotificationListener.class));

		Context context =  getContext();
		// create config
		LockScreenConfig lockScreenConfig = new LockScreenConfig();
		lockScreenConfig.setCustomView(TestValues.GENERAL_INT);
		lockScreenConfig.setAppIcon(TestValues.GENERAL_INT);
		lockScreenConfig.setBackgroundColor(TestValues.GENERAL_INT);
		lockScreenConfig.showDeviceLogo(true);
		lockScreenConfig.setEnabled(true);
		lockScreenConfig.setDisplayMode(LockScreenConfig.DISPLAY_MODE_OPTIONAL_OR_REQUIRED);

		lockScreenManager = new LockScreenManager(lockScreenConfig, context, internalInterface);
	}

	@Test
	public void testVariables() {
		assertEquals(TestValues.GENERAL_INT, lockScreenManager.customView);
		assertEquals(TestValues.GENERAL_INT, lockScreenManager.lockScreenIcon);
		assertEquals(TestValues.GENERAL_INT, lockScreenManager.lockScreenColor);
		assertTrue(lockScreenManager.deviceLogoEnabled);
		assertTrue(lockScreenManager.lockScreenEnabled);
		assertNull(lockScreenManager.deviceLogo);
		assertEquals(LockScreenConfig.DISPLAY_MODE_OPTIONAL_OR_REQUIRED, lockScreenManager.displayMode);
	}

	@Test
	public void testGetLockScreenStatusHmiNoneDDOff(){
		lockScreenManager.driverDistStatus = false;
		lockScreenManager.hmiLevel = HMILevel.HMI_NONE;
		assertEquals(LockScreenStatus.OFF, lockScreenManager.getLockScreenStatus());
	}

	@Test
	public void testGetLockScreenStatusHmiBackgroundDDOff(){
		lockScreenManager.driverDistStatus = false;
		lockScreenManager.hmiLevel = HMILevel.HMI_BACKGROUND;
		assertEquals(LockScreenStatus.OFF, lockScreenManager.getLockScreenStatus());
	}

	@Test
	public void testGetLockScreenStatusHmiNoneDDOn(){
		lockScreenManager.driverDistStatus = true;
		lockScreenManager.hmiLevel = HMILevel.HMI_BACKGROUND;
		assertEquals(LockScreenStatus.REQUIRED, lockScreenManager.getLockScreenStatus());
	}

	@Test
	public void testGetLockScreenStatusHmiFullDDOff(){
		lockScreenManager.driverDistStatus = false;
		lockScreenManager.hmiLevel = HMILevel.HMI_FULL;
		assertEquals(LockScreenStatus.OPTIONAL, lockScreenManager.getLockScreenStatus());
	}

	@Test
	public void testGetLockScreenStatusHmiFullDDOn(){
		lockScreenManager.driverDistStatus = true;
		lockScreenManager.hmiLevel = HMILevel.HMI_FULL;
		assertEquals(LockScreenStatus.REQUIRED, lockScreenManager.getLockScreenStatus());
	}

	@Test
	public void testGetLockScreenStatusHmiLimitedDDOff(){
		lockScreenManager.driverDistStatus = false;
		lockScreenManager.hmiLevel = HMILevel.HMI_LIMITED;
		assertEquals(LockScreenStatus.OPTIONAL, lockScreenManager.getLockScreenStatus());
	}

	@Test
	public void testGetLockScreenStatusHmiLimitedDDOn(){
		lockScreenManager.driverDistStatus = true;
		lockScreenManager.hmiLevel = HMILevel.HMI_LIMITED;
		assertEquals(LockScreenStatus.REQUIRED, lockScreenManager.getLockScreenStatus());
	}

	@Test
	public void testLockScreenDismissibleWithEnableTrueAndDismissibilityTrue(){
		lockScreenManager.enableDismissGesture = true;
		OnDriverDistraction onDriverDistraction = new OnDriverDistraction();
		onDriverDistraction.setLockscreenDismissibility(true);
		onDriverDistraction.setState(DriverDistractionState.DD_ON);
		onDDListener.onNotified(onDriverDistraction);
		assertTrue(lockScreenManager.enableDismissGesture);
		assertTrue(lockScreenManager.mIsLockscreenDismissible);
	}

	@Test
	public void testLockScreenDismissibleWithEnableFalseAndDismissibilityFalse(){
		lockScreenManager.enableDismissGesture = false;
		OnDriverDistraction onDriverDistraction = new OnDriverDistraction();
		onDriverDistraction.setLockscreenDismissibility(true);
		onDriverDistraction.setState(DriverDistractionState.DD_ON);
		onDDListener.onNotified(onDriverDistraction);
		assertFalse(lockScreenManager.enableDismissGesture);
		assertFalse(lockScreenManager.mIsLockscreenDismissible);
	}

	@Test
	public void testLockScreenDismissibleWithEnableTrueAndDismissibilityFalse(){
		lockScreenManager.enableDismissGesture = true;
		OnDriverDistraction onDriverDistraction = new OnDriverDistraction();
		onDriverDistraction.setLockscreenDismissibility(false);
		onDriverDistraction.setState(DriverDistractionState.DD_ON);
		onDDListener.onNotified(onDriverDistraction);
		assertTrue(lockScreenManager.enableDismissGesture);
		assertFalse(lockScreenManager.mIsLockscreenDismissible);
	}

	@Test
	public void testLockScreenDismissibleWithEnableFalseAndDismissibilityTrue(){
		lockScreenManager.enableDismissGesture = false;
		OnDriverDistraction onDriverDistraction = new OnDriverDistraction();
		onDriverDistraction.setLockscreenDismissibility(true);
		onDriverDistraction.setState(DriverDistractionState.DD_ON);
		onDDListener.onNotified(onDriverDistraction);
		assertFalse(lockScreenManager.enableDismissGesture);
		assertFalse(lockScreenManager.mIsLockscreenDismissible);
	}

}