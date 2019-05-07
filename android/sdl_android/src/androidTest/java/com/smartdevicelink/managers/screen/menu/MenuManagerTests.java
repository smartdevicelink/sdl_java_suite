/*
 * Copyright (c) 2019 Livio, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the Livio Inc. nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package com.smartdevicelink.managers.screen.menu;

import com.smartdevicelink.AndroidTestCase2;
import com.smartdevicelink.R;
import com.smartdevicelink.managers.BaseSubManager;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.SystemContext;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public class MenuManagerTests extends AndroidTestCase2 {

	private OnRPCNotificationListener onHMIStatusListener;
	private MenuManager menuManager;
	private MenuCell mainCell1, mainCell3, mainCell2, mainCell4;

	// SETUP / HELPERS

	@Override
	public void setUp() throws Exception{
		super.setUp();

		// Create our menu cells
		createTestCells();

		ISdl internalInterface = mock(ISdl.class);
		FileManager fileManager = mock(FileManager.class);

		// When internalInterface.addOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, OnRPCNotificationListener) is called
		// inside PermissionManager's constructor, then keep a reference to the OnRPCNotificationListener so we can trigger it later
		// to emulate what Core does when it sends OnHMIStatus notification
		Answer<Void> onHMIStatusAnswer = new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				onHMIStatusListener = (OnRPCNotificationListener) args[1];
				return null;
			}
		};
		doAnswer(onHMIStatusAnswer).when(internalInterface).addOnRPCNotificationListener(eq(FunctionID.ON_HMI_STATUS), any(OnRPCNotificationListener.class));

		Answer<Void> onCommandAnswer = new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				OnRPCNotificationListener commandListener = (OnRPCNotificationListener) args[1];
				return null;
			}
		};
		doAnswer(onCommandAnswer).when(internalInterface).addOnRPCNotificationListener(eq(FunctionID.ON_COMMAND), any(OnRPCNotificationListener.class));

		menuManager = new MenuManager(internalInterface, fileManager);

		// Check some stuff during setup
		assertEquals(menuManager.currentHMILevel, HMILevel.HMI_NONE);
		assertEquals(menuManager.getState(), BaseSubManager.SETTING_UP);
		assertEquals(menuManager.currentSystemContext, SystemContext.SYSCTXT_MAIN);
		assertEquals(menuManager.lastMenuId, 1);
		assertNotNull(menuManager.menuCells);
		assertNotNull(menuManager.waitingUpdateMenuCells);
		assertNotNull(menuManager.oldMenuCells);
		assertNotNull(menuManager.inProgressUpdate);
		assertNotNull(menuManager.hmiListener);
		assertNotNull(menuManager.commandListener);
		assertNotNull(menuManager.displayListener);

	}

	@Override
	public void tearDown() throws Exception {

		menuManager.dispose();

		assertEquals(menuManager.currentSystemContext, SystemContext.SYSCTXT_MAIN);
		assertEquals(menuManager.lastMenuId, 1);
		assertNull(menuManager.menuCells);
		assertNull(menuManager.oldMenuCells);
		assertNull(menuManager.currentHMILevel);
		assertNull(menuManager.displayCapabilities);
		assertNull(menuManager.inProgressUpdate);
		assertNull(menuManager.waitingUpdateMenuCells);

		// after everything, make sure we are in the correct state
		assertEquals(menuManager.getState(), BaseSubManager.SHUTDOWN);

		super.tearDown();
	}

	public void testStartMenuManager(){

		menuManager.start(new CompletionListener() {
			@Override
			public void onComplete(boolean success) {
				assertTrue(success);
				// Make sure the state has changed, as the Screen Manager is dependant on it
				assertEquals(menuManager.getState(), BaseSubManager.READY);
			}
		});
	}

	private void createTestCells(){

		// menu cell mock listener
		MenuSelectionListener menuSelectionListener1 = mock(MenuSelectionListener.class);
		MenuSelectionListener menuSelectionListener2 = mock(MenuSelectionListener.class);
		MenuSelectionListener menuSelectionListener3 = mock(MenuSelectionListener.class);
		MenuSelectionListener menuSelectionListenerSub1 = mock(MenuSelectionListener.class);
		MenuSelectionListener menuSelectionListenerSub2 = mock(MenuSelectionListener.class);

		// some arts
		SdlArtwork livio = new SdlArtwork("livio", FileType.GRAPHIC_PNG, R.drawable.sdl_lockscreen_icon, false);

		// some voice commands
		List<String> voice2 = Collections.singletonList("Cell two");

		mainCell1 = new MenuCell("Test Cell 1", livio, null, menuSelectionListener1);
		mainCell2 = new MenuCell("Test Cell 2", livio, voice2, menuSelectionListener2);
		mainCell3 = new MenuCell("Test Cell 3", menuSelectionListener3);

		// SUB MENU
		MenuCell subCell1 = new MenuCell("SubCell 1", menuSelectionListenerSub1);
		MenuCell subCell2 = new MenuCell("SubCell 2", menuSelectionListenerSub2);

		mainCell4 = new MenuCell("Test Cell 4", livio, Arrays.asList(subCell1,subCell2)); // sub menu parent cell
	}

	// Emulate what happens when Core sends OnHMIStatus notification
	private void sendFakeCoreOnHMIFullNotifications() {
		OnHMIStatus onHMIStatusFakeNotification = new OnHMIStatus();
		onHMIStatusFakeNotification.setHmiLevel(HMILevel.HMI_FULL);
		onHMIStatusListener.onNotified(onHMIStatusFakeNotification);
	}

}
