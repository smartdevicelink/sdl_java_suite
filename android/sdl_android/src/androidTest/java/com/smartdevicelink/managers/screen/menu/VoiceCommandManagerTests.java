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
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;

public class VoiceCommandManagerTests extends AndroidTestCase2 {

	public static final String TAG = "VCMTests";
	private List<VoiceCommand> commands;
	private VoiceCommandManager voiceCommandManager;
	private static final int voiceCommandIdMin = 1900000000;

	// SETUP / HELPERS

	@Override
	public void setUp() throws Exception{
		super.setUp();

		VoiceCommand command = new VoiceCommand(Arrays.asList("Command one", "Command two"), null);
		VoiceCommand command2 = new VoiceCommand(Arrays.asList("Command three", "Command four"), null);
		commands = Arrays.asList(command,command2);

		ISdl internalInterface = mock(ISdl.class);
		voiceCommandManager = new VoiceCommandManager(internalInterface);

		// Check some stuff during setup
		assertEquals(voiceCommandManager.currentHMILevel, HMILevel.HMI_NONE);
		assertEquals(voiceCommandManager.getState(), BaseSubManager.SETTING_UP);
		assertEquals(voiceCommandManager.lastVoiceCommandId, voiceCommandIdMin);
		assertFalse(voiceCommandManager.hasQueuedUpdate);
		assertFalse(voiceCommandManager.waitingOnHMIUpdate);
		assertNotNull(voiceCommandManager.commandListener);
		assertNotNull(voiceCommandManager.hmiListener);
		assertNotNull(voiceCommandManager.voiceCommands);
		assertNotNull(voiceCommandManager.oldVoiceCommands);
		assertNull(voiceCommandManager.inProgressUpdate);
	}

	@Override
	public void tearDown() throws Exception {

		voiceCommandManager.dispose();

		assertEquals(voiceCommandManager.lastVoiceCommandId, voiceCommandIdMin);
		assertNull(voiceCommandManager.voiceCommands);
		assertNull(voiceCommandManager.oldVoiceCommands);
		assertNull(voiceCommandManager.currentHMILevel);
		assertNull(voiceCommandManager.inProgressUpdate);
		assertFalse(voiceCommandManager.hasQueuedUpdate);
		assertFalse(voiceCommandManager.waitingOnHMIUpdate);
		// after everything, make sure we are in the correct state
		assertEquals(voiceCommandManager.getState(), BaseSubManager.SHUTDOWN);

		super.tearDown();
	}

	public void testStartVoiceCommandManager(){

		voiceCommandManager.start(new CompletionListener() {
			@Override
			public void onComplete(boolean success) {
				assertTrue(success);
				// Make sure the state has changed, as the Screen Manager is dependant on it
				assertEquals(voiceCommandManager.getState(), BaseSubManager.READY);
				// now that we are started, set some voice commands
				sendCommands(voiceCommandManager);
			}
		});
	}

	private void sendCommands(VoiceCommandManager voiceCommandManager){

		voiceCommandManager.setVoiceCommands(commands);

	}

}
