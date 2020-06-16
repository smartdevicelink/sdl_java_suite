/*
 * Copyright (c)  2019 Livio, Inc.
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
 *
 * Created by brettywhite on 6/12/19 1:52 PM
 *
 */

package com.smartdevicelink.managers.screen.choiceset;

import com.smartdevicelink.AndroidTestCase2;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.CancelInteraction;
import com.smartdevicelink.proxy.rpc.KeyboardProperties;
import com.smartdevicelink.proxy.rpc.PerformInteraction;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.SetGlobalProperties;
import com.smartdevicelink.proxy.rpc.enums.KeyboardLayout;
import com.smartdevicelink.proxy.rpc.enums.KeypressMode;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.proxy.rpc.enums.LayoutMode;
import com.smartdevicelink.test.TestValues;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PresentKeyboardOperationTests extends AndroidTestCase2 {

	private PresentKeyboardOperation presentKeyboardOperation;
	private KeyboardListener keyboardListener;
	private ISdl internalInterface;

	private ExecutorService executor;

	@Override
	public void setUp() throws Exception{
		super.setUp();

		internalInterface = mock(ISdl.class);
		keyboardListener = mock(KeyboardListener.class);

		Answer<Void> setGlobalPropertiesAnswer = new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				SetGlobalProperties setGlobalProperties = (SetGlobalProperties) args[0];

				RPCResponse response = new RPCResponse(FunctionID.SET_GLOBAL_PROPERTIES.toString());
				response.setSuccess(true);
				setGlobalProperties.getOnRPCResponseListener().onResponse(0, response);

				return null;
			}
		};
		doAnswer(setGlobalPropertiesAnswer).when(internalInterface).sendRPC(any(SetGlobalProperties.class));

		executor = Executors.newCachedThreadPool();
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	private KeyboardProperties getKeyBoardProperties(){
		KeyboardProperties properties = new KeyboardProperties();
		properties.setLanguage(Language.EN_US);
		properties.setKeyboardLayout(KeyboardLayout.QWERTZ);
		properties.setKeypressMode(KeypressMode.RESEND_CURRENT_ENTRY);
		return properties;
	}

	public void testGetPerformInteraction(){
		presentKeyboardOperation = new PresentKeyboardOperation(internalInterface, getKeyBoardProperties(), "Test", null, keyboardListener, TestValues.GENERAL_INTEGER);

		PerformInteraction pi = presentKeyboardOperation.getPerformInteraction();
		assertEquals(pi.getInitialText(), "Test");
		assertNull(pi.getHelpPrompt());
		assertNull(pi.getTimeoutPrompt());
		assertNull(pi.getVrHelp());
		assertEquals(pi.getInteractionLayout(), LayoutMode.KEYBOARD);
		assertEquals(pi.getCancelID(), TestValues.GENERAL_INTEGER);
	}

	public void testCancelingKeyboardSuccessfullyIfThreadIsRunning(){
		when(internalInterface.getSdlMsgVersion()).thenReturn(new SdlMsgVersion(6, 0));
		presentKeyboardOperation = new PresentKeyboardOperation(internalInterface, null, "Test", null, null, TestValues.GENERAL_INTEGER);
		executor.execute(presentKeyboardOperation);
		try {
			executor.awaitTermination(1, TimeUnit.SECONDS);
		} catch (InterruptedException e) {}

		assertTrue(presentKeyboardOperation.isExecuting());
		assertFalse(presentKeyboardOperation.isFinished());
		assertFalse(presentKeyboardOperation.isCancelled());

		presentKeyboardOperation.dismissKeyboard();
		Answer<Void> cancelInteractionAnswer = new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				CancelInteraction cancelInteraction = (CancelInteraction) args[0];

				assertEquals(cancelInteraction.getCancelID(), TestValues.GENERAL_INTEGER);
				assertEquals(cancelInteraction.getInteractionFunctionID().intValue(), FunctionID.PERFORM_INTERACTION.getId());

				RPCResponse response = new RPCResponse(FunctionID.CANCEL_INTERACTION.toString());
				response.setSuccess(true);
				cancelInteraction.getOnRPCResponseListener().onResponse(0, response);

				return null;
			}
		};
		doAnswer(cancelInteractionAnswer).when(internalInterface).sendRPC(any(CancelInteraction.class));

		verify(internalInterface, times(1)).sendRPC(any(CancelInteraction.class));
		verify(internalInterface, times(1)).sendRPC(any(PerformInteraction.class));

		assertTrue(presentKeyboardOperation.isExecuting());
		assertFalse(presentKeyboardOperation.isFinished());
		assertFalse(presentKeyboardOperation.isCancelled());
	}

	public void testCancelingKeyboardUnsuccessfullyIfThreadIsRunning(){
        when(internalInterface.getSdlMsgVersion()).thenReturn(new SdlMsgVersion(6, 0));
        presentKeyboardOperation = new PresentKeyboardOperation(internalInterface, null, "Test", null, null, TestValues.GENERAL_INTEGER);
        executor.execute(presentKeyboardOperation);
        try {
            executor.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {}

        presentKeyboardOperation.dismissKeyboard();
        Answer<Void> cancelInteractionAnswer = new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				CancelInteraction cancelInteraction = (CancelInteraction) args[0];

				assertEquals(cancelInteraction.getCancelID(), TestValues.GENERAL_INTEGER);
				assertEquals(cancelInteraction.getInteractionFunctionID().intValue(), FunctionID.PERFORM_INTERACTION.getId());

				RPCResponse response = new RPCResponse(FunctionID.CANCEL_INTERACTION.toString());
				response.setSuccess(false);
				cancelInteraction.getOnRPCResponseListener().onResponse(0, response);

				return null;
			}
		};
		doAnswer(cancelInteractionAnswer).when(internalInterface).sendRPC(any(CancelInteraction.class));

        verify(internalInterface, times(1)).sendRPC(any(CancelInteraction.class));
        verify(internalInterface, times(1)).sendRPC(any(PerformInteraction.class));

        assertTrue(presentKeyboardOperation.isExecuting());
        assertFalse(presentKeyboardOperation.isFinished());
        assertFalse(presentKeyboardOperation.isCancelled());
	}

	public void testCancelingKeyboardIfThreadHasFinished(){
		when(internalInterface.getSdlMsgVersion()).thenReturn(new SdlMsgVersion(6, 0));
		presentKeyboardOperation = new PresentKeyboardOperation(internalInterface, null, "Test", null, null, TestValues.GENERAL_INTEGER);
		presentKeyboardOperation.finishOperation();

		assertFalse(presentKeyboardOperation.isExecuting());
		assertTrue(presentKeyboardOperation.isFinished());
		assertFalse(presentKeyboardOperation.isCancelled());

		presentKeyboardOperation.dismissKeyboard();
		verify(internalInterface, never()).sendRPC(any(CancelInteraction.class));

		assertFalse(presentKeyboardOperation.isExecuting());
		assertTrue(presentKeyboardOperation.isFinished());
		assertFalse(presentKeyboardOperation.isCancelled());
	}

	public void testCancelingKeyboardIfThreadHasNotYetRun(){
		when(internalInterface.getSdlMsgVersion()).thenReturn(new SdlMsgVersion(6, 0));
		presentKeyboardOperation = new PresentKeyboardOperation(internalInterface, null, "Test", null, null, TestValues.GENERAL_INTEGER);

		assertFalse(presentKeyboardOperation.isExecuting());
		assertFalse(presentKeyboardOperation.isFinished());
		assertFalse(presentKeyboardOperation.isCancelled());

		presentKeyboardOperation.dismissKeyboard();

		// Once the operation has started
		executor.execute(presentKeyboardOperation);
		try {
			executor.awaitTermination(1, TimeUnit.SECONDS);
		} catch (InterruptedException e) {}

		assertFalse(presentKeyboardOperation.isExecuting());
		assertTrue(presentKeyboardOperation.isFinished());
		assertFalse(presentKeyboardOperation.isCancelled());

		// Make sure neither a `CancelInteraction` or `PerformInteraction` RPC is ever sent
		verify(internalInterface, never()).sendRPC(any(CancelInteraction.class));
		verify(internalInterface, never()).sendRPC(any(PerformInteraction.class));
	}

	public void testCancelingChoiceSetIfHeadUnitDoesNotSupportFeature(){
		// Cancel Interaction is only supported on RPC specs v.6.0.0+
		when(internalInterface.getSdlMsgVersion()).thenReturn(new SdlMsgVersion(5, 3));
		presentKeyboardOperation = new PresentKeyboardOperation(internalInterface, null, "Test", null, null, TestValues.GENERAL_INTEGER);
		executor.execute(presentKeyboardOperation);
		try {
			executor.awaitTermination(1, TimeUnit.SECONDS);
		} catch (InterruptedException e) {}

		assertTrue(presentKeyboardOperation.isExecuting());
		assertFalse(presentKeyboardOperation.isFinished());
		assertFalse(presentKeyboardOperation.isCancelled());

		presentKeyboardOperation.dismissKeyboard();

		verify(internalInterface, never()).sendRPC(any(CancelInteraction.class));
		verify(internalInterface, times(1)).sendRPC(any(PerformInteraction.class));
	}

	public void testCancelingChoiceSetIfHeadUnitDoesNotSupportFeatureButThreadIsNotRunning(){
		// Cancel Interaction is only supported on RPC specs v.6.0.0+
		when(internalInterface.getSdlMsgVersion()).thenReturn(new SdlMsgVersion(5, 3));
		presentKeyboardOperation = new PresentKeyboardOperation(internalInterface, null, "Test", null, null, TestValues.GENERAL_INTEGER);

		assertFalse(presentKeyboardOperation.isExecuting());
		assertFalse(presentKeyboardOperation.isFinished());
		assertFalse(presentKeyboardOperation.isCancelled());

		presentKeyboardOperation.dismissKeyboard();

		verify(internalInterface, never()).sendRPC(any(CancelInteraction.class));

		// Once the operation has started
		executor.execute(presentKeyboardOperation);
		try {
			executor.awaitTermination(1, TimeUnit.SECONDS);
		} catch (InterruptedException e) {}

		assertFalse(presentKeyboardOperation.isExecuting());
		assertTrue(presentKeyboardOperation.isFinished());
		assertFalse(presentKeyboardOperation.isCancelled());

		// Make sure neither a `CancelInteraction` or `PerformInteraction` RPC is ever sent
		verify(internalInterface, never()).sendRPC(any(CancelInteraction.class));
		verify(internalInterface, never()).sendRPC(any(PerformInteraction.class));
	}
}
