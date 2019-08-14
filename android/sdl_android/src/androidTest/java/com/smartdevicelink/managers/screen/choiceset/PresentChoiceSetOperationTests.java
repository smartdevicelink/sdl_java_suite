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
import com.smartdevicelink.proxy.rpc.enums.InteractionMode;
import com.smartdevicelink.proxy.rpc.enums.KeyboardLayout;
import com.smartdevicelink.proxy.rpc.enums.KeypressMode;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.proxy.rpc.enums.LayoutMode;
import com.smartdevicelink.test.Test;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class PresentChoiceSetOperationTests extends AndroidTestCase2 {

	private PresentChoiceSetOperation presentChoiceSetOperation;
	private ChoiceSet choiceSet;
	private ISdl internalInterface;

	@Override
	public void setUp() throws Exception{
		super.setUp();

		internalInterface = mock(ISdl.class);
		KeyboardListener keyboardListener = mock(KeyboardListener.class);
        ChoiceSetSelectionListener choiceSetSelectionListener = mock(ChoiceSetSelectionListener.class);

		ChoiceCell cell1 = new ChoiceCell("Cell1");
		cell1.setChoiceId(0);
		choiceSet = new ChoiceSet("Test", Collections.singletonList(cell1), choiceSetSelectionListener);
		presentChoiceSetOperation = new PresentChoiceSetOperation(internalInterface, choiceSet, InteractionMode.MANUAL_ONLY, getKeyBoardProperties(), keyboardListener, choiceSetSelectionListener, Test.GENERAL_INTEGER);
		presentChoiceSetOperation.sdlMsgVersion = new SdlMsgVersion(6, 0);
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetLayoutMode(){
		// First we will check knowing our keyboard listener is NOT NULL
		assertEquals(presentChoiceSetOperation.getLayoutMode(), LayoutMode.LIST_WITH_SEARCH);
		presentChoiceSetOperation.keyboardListener = null;
		assertEquals(presentChoiceSetOperation.getLayoutMode(), LayoutMode.LIST_ONLY);
	}

	public void testGetPerformInteraction(){
		PerformInteraction pi = presentChoiceSetOperation.getPerformInteraction();
		assertEquals(pi.getInitialText(), "Test");
		assertNull(pi.getHelpPrompt());
		assertNull(pi.getTimeoutPrompt());
		assertNull(pi.getVrHelp());
		assertEquals(pi.getTimeout(), Integer.valueOf(10000));
		assertEquals(pi.getCancelID(), Test.GENERAL_INTEGER);
		assertEquals(presentChoiceSetOperation.getLayoutMode(), LayoutMode.LIST_WITH_SEARCH);
	}

	public void testSetSelectedCellWithId(){
		assertNull(presentChoiceSetOperation.selectedCellRow);
		presentChoiceSetOperation.setSelectedCellWithId(0);
		assertEquals(presentChoiceSetOperation.selectedCellRow, Integer.valueOf(0));
	}

	private KeyboardProperties getKeyBoardProperties(){
		KeyboardProperties properties = new KeyboardProperties();
		properties.setLanguage(Language.EN_US);
		properties.setKeyboardLayout(KeyboardLayout.QWERTZ);
		properties.setKeypressMode(KeypressMode.RESEND_CURRENT_ENTRY);
		return properties;
	}

	public void testCancelingChoiceSetSuccessfullyIfThreadIsRunning(){
		presentChoiceSetOperation.run();
		assertEquals(presentChoiceSetOperation.isExecuting().booleanValue(), true);
		assertEquals(presentChoiceSetOperation.isFinished().booleanValue(), false);

		Answer<Void> cancelInteractionAnswer = new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				CancelInteraction cancelInteraction = (CancelInteraction) args[0];

				assertEquals(cancelInteraction.getCancelID(), Test.GENERAL_INTEGER);
				assertEquals(cancelInteraction.getInteractionFunctionID().intValue(), FunctionID.PERFORM_INTERACTION.getId());

				RPCResponse response = new RPCResponse(FunctionID.CANCEL_INTERACTION.toString());
				response.setSuccess(true);
				cancelInteraction.getOnRPCResponseListener().onResponse(0, response);

				return null;
			}
		};
		doAnswer(cancelInteractionAnswer).when(internalInterface).sendRPC(any(CancelInteraction.class));

		choiceSet.cancel();

		assertEquals(presentChoiceSetOperation.isExecuting().booleanValue(), true);
		assertEquals(presentChoiceSetOperation.isFinished().booleanValue(), false);
	}

	public void testCancelingChoiceSetUnsuccessfullyIfThreadIsRunning(){
		presentChoiceSetOperation.run();
		assertEquals(presentChoiceSetOperation.isExecuting().booleanValue(), true);
		assertEquals(presentChoiceSetOperation.isFinished().booleanValue(), false);

		Answer<Void> cancelInteractionAnswer = new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				CancelInteraction cancelInteraction = (CancelInteraction) args[0];

				assertEquals(cancelInteraction.getCancelID(), Test.GENERAL_INTEGER);
				assertEquals(cancelInteraction.getInteractionFunctionID().intValue(), FunctionID.PERFORM_INTERACTION.getId());

				RPCResponse response = new RPCResponse(FunctionID.CANCEL_INTERACTION.toString());
				response.setSuccess(false);
				cancelInteraction.getOnRPCResponseListener().onResponse(0, response);

				return null;
			}
		};
		doAnswer(cancelInteractionAnswer).when(internalInterface).sendRPC(any(CancelInteraction.class));

		choiceSet.cancel();

		assertEquals(presentChoiceSetOperation.isExecuting().booleanValue(), true);
		assertEquals(presentChoiceSetOperation.isFinished().booleanValue(), false);
	}

	public void testCancelingChoiceSetIfThreadHasFinished(){
		presentChoiceSetOperation.run();
		presentChoiceSetOperation.finishOperation();

		assertEquals(presentChoiceSetOperation.isExecuting().booleanValue(), false);
		assertEquals(presentChoiceSetOperation.isFinished().booleanValue(), true);

		choiceSet.cancel();

		verify(internalInterface, never()).sendRPC(any(CancelInteraction.class));

		assertEquals(presentChoiceSetOperation.isExecuting().booleanValue(), false);
		assertEquals(presentChoiceSetOperation.isFinished().booleanValue(), true);
	}

	public void testCancelingChoiceSetIfThreadHasNotYetRun(){
		assertEquals(presentChoiceSetOperation.isExecuting().booleanValue(), false);
		assertEquals(presentChoiceSetOperation.isFinished().booleanValue(), false);

		choiceSet.cancel();

		verify(internalInterface, never()).sendRPC(any(CancelInteraction.class));

		// Once the thread has started
		presentChoiceSetOperation.run();

		// Make sure neither a `CancelInteraction` or `PerformInteraction` RPC is sent
		verify(internalInterface, never()).sendRPC(any(CancelInteraction.class));
		verify(internalInterface, never()).sendRPC(any(PerformInteraction.class));

		assertEquals(presentChoiceSetOperation.isExecuting().booleanValue(), false);
		assertEquals(presentChoiceSetOperation.isFinished().booleanValue(), true);
	}

	public void testCancelingChoiceSetIfHeadUnitDoesNotSupportFeature(){
		// Only supported with RPC spec versions 6.0.0+
		presentChoiceSetOperation.sdlMsgVersion = new SdlMsgVersion(5, 3);
		presentChoiceSetOperation.run();

		assertEquals(presentChoiceSetOperation.isExecuting().booleanValue(), true);
		assertEquals(presentChoiceSetOperation.isFinished().booleanValue(), false);

		choiceSet.cancel();

		verify(internalInterface, never()).sendRPC(any(CancelInteraction.class));
	}

	public void testCancelingChoiceSetIfHeadUnitDoesNotSupportFeatureButThreadIsNotRunning(){
		// Only supported with RPC spec versions 6.0.0+
		presentChoiceSetOperation.sdlMsgVersion = new SdlMsgVersion(5, 3);

		assertEquals(presentChoiceSetOperation.isExecuting().booleanValue(), false);
		assertEquals(presentChoiceSetOperation.isFinished().booleanValue(), false);

		choiceSet.cancel();

		verify(internalInterface, never()).sendRPC(any(CancelInteraction.class));

		// Once the thread has started
		presentChoiceSetOperation.run();

		// Make sure neither a `CancelInteraction` or `PerformInteraction` RPC is sent
		verify(internalInterface, never()).sendRPC(any(CancelInteraction.class));
		verify(internalInterface, never()).sendRPC(any(PerformInteraction.class));

		assertEquals(presentChoiceSetOperation.isExecuting().booleanValue(), false);
		assertEquals(presentChoiceSetOperation.isFinished().booleanValue(), true);
	}
}
