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
import com.smartdevicelink.managers.BaseSubManager;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public class MenuManagerTests extends AndroidTestCase2 {

	private OnRPCNotificationListener onHMIStatusListener, commandListener;
	private MenuManager menuManager;

	// SETUP / HELPERS

	@Override
	public void setUp() throws Exception{
		super.setUp();

		// menu cell mock listener
		MenuSelectionListener menuSelectionListener = mock(MenuSelectionListener.class);

		// Create our menu cells


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
				commandListener = (OnRPCNotificationListener) args[1];
				return null;
			}
		};
		doAnswer(onCommandAnswer).when(internalInterface).addOnRPCNotificationListener(eq(FunctionID.ON_COMMAND), any(OnRPCNotificationListener.class));

		menuManager = new MenuManager(internalInterface, fileManager);

		assertEquals(menuManager.getState(), BaseSubManager.SETTING_UP);

	}

	@Override
	public void tearDown() throws Exception {

		menuManager.dispose();

		// after everything, make sure we are in the correct state
		assertEquals(menuManager.getState(), BaseSubManager.SHUTDOWN);

		super.tearDown();
	}

	public void testStartMenuManager(){

	}

}
