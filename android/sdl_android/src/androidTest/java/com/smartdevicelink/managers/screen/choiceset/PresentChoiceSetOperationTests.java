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

import android.support.test.runner.AndroidJUnit4;

import com.livio.taskmaster.Queue;
import com.livio.taskmaster.Task;
import com.livio.taskmaster.Taskmaster;
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
import com.smartdevicelink.test.TestValues;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Collections;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class PresentChoiceSetOperationTests {

	private PresentChoiceSetOperation presentChoiceSetOperation;
	private ChoiceSet choiceSet;
	private ISdl internalInterface;
	private KeyboardListener keyboardListener;
	private ChoiceSetSelectionListener choiceSetSelectionListener;

	private Taskmaster taskmaster;
	private Queue queue;

	@Before
	public void setUp() throws Exception{

		internalInterface = mock(ISdl.class);

		keyboardListener = mock(KeyboardListener.class);
		choiceSetSelectionListener = mock(ChoiceSetSelectionListener.class);

		ChoiceCell cell1 = new ChoiceCell("Cell1");
		cell1.setChoiceId(0);
		choiceSet = new ChoiceSet("Test", Collections.singletonList(cell1), choiceSetSelectionListener);

		taskmaster = new Taskmaster.Builder().build();
		queue = taskmaster.createQueue("test", 100, false);
		taskmaster.start();
	}


	private KeyboardProperties getKeyBoardProperties(){
		KeyboardProperties properties = new KeyboardProperties();
		properties.setLanguage(Language.EN_US);
		properties.setKeyboardLayout(KeyboardLayout.QWERTZ);
		properties.setKeypressMode(KeypressMode.RESEND_CURRENT_ENTRY);
		return properties;
	}

	@Test
    public void testGetLayoutMode(){
		// First we will check knowing our keyboard listener is NOT NULL
		presentChoiceSetOperation = new PresentChoiceSetOperation(internalInterface, choiceSet, InteractionMode.MANUAL_ONLY, getKeyBoardProperties(), keyboardListener, choiceSetSelectionListener, TestValues.GENERAL_INTEGER);

		assertEquals(presentChoiceSetOperation.getLayoutMode(), LayoutMode.LIST_WITH_SEARCH);
		presentChoiceSetOperation.keyboardListener = null;
		assertEquals(presentChoiceSetOperation.getLayoutMode(), LayoutMode.LIST_ONLY);
	}

	@Test
	public void testGetPerformInteraction(){
		presentChoiceSetOperation = new PresentChoiceSetOperation(internalInterface, choiceSet, InteractionMode.MANUAL_ONLY, getKeyBoardProperties(), keyboardListener, choiceSetSelectionListener, TestValues.GENERAL_INTEGER);

		PerformInteraction pi = presentChoiceSetOperation.getPerformInteraction();
		assertEquals(pi.getInitialText(), "Test");
		assertNull(pi.getHelpPrompt());
		assertNull(pi.getTimeoutPrompt());
		assertNull(pi.getVrHelp());
		assertEquals(pi.getTimeout(), Integer.valueOf(10000));
		assertEquals(pi.getCancelID(), TestValues.GENERAL_INTEGER);
		assertEquals(presentChoiceSetOperation.getLayoutMode(), LayoutMode.LIST_WITH_SEARCH);
	}

	@Test
	public void testSetSelectedCellWithId(){
		presentChoiceSetOperation = new PresentChoiceSetOperation(internalInterface, choiceSet, InteractionMode.MANUAL_ONLY, getKeyBoardProperties(), keyboardListener, choiceSetSelectionListener, TestValues.GENERAL_INTEGER);

		assertNull(presentChoiceSetOperation.selectedCellRow);
		presentChoiceSetOperation.setSelectedCellWithId(0);
		assertEquals(presentChoiceSetOperation.selectedCellRow, Integer.valueOf(0));
	}

	private void sleep() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCancelingChoiceSetSuccessfullyIfThreadIsRunning(){
		when(internalInterface.getSdlMsgVersion()).thenReturn(new SdlMsgVersion(6, 0));
		presentChoiceSetOperation = new PresentChoiceSetOperation(internalInterface, choiceSet, InteractionMode.MANUAL_ONLY, null, null, choiceSetSelectionListener, TestValues.GENERAL_INTEGER);
		queue.add(presentChoiceSetOperation, false);

		sleep();

		assertEquals(Task.IN_PROGRESS, presentChoiceSetOperation.getState());

		choiceSet.cancel();
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

		assertEquals(Task.IN_PROGRESS, presentChoiceSetOperation.getState());
	}

	@Test
	public void testCancelingChoiceSetUnsuccessfullyIfThreadIsRunning(){
		when(internalInterface.getSdlMsgVersion()).thenReturn(new SdlMsgVersion(6, 0));
		presentChoiceSetOperation = new PresentChoiceSetOperation(internalInterface, choiceSet, InteractionMode.MANUAL_ONLY, null, null, choiceSetSelectionListener, TestValues.GENERAL_INTEGER);
		queue.add(presentChoiceSetOperation, false);
		sleep();

		assertEquals(Task.IN_PROGRESS, presentChoiceSetOperation.getState());

		choiceSet.cancel();
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

		assertEquals(Task.IN_PROGRESS, presentChoiceSetOperation.getState());
	}

	@Test
	public void testCancelingChoiceSetIfThreadHasFinished(){
		when(internalInterface.getSdlMsgVersion()).thenReturn(new SdlMsgVersion(6, 0));
		presentChoiceSetOperation = new PresentChoiceSetOperation(internalInterface, choiceSet, InteractionMode.MANUAL_ONLY, null, null, choiceSetSelectionListener, TestValues.GENERAL_INTEGER);
		presentChoiceSetOperation.finishOperation();

		assertEquals(Task.FINISHED, presentChoiceSetOperation.getState());

		choiceSet.cancel();
		verify(internalInterface, never()).sendRPC(any(CancelInteraction.class));

		assertEquals(Task.FINISHED, presentChoiceSetOperation.getState());
	}

	@Test
	public void testCancelingChoiceSetIfThreadHasNotYetRun(){
		when(internalInterface.getSdlMsgVersion()).thenReturn(new SdlMsgVersion(6, 0));
		presentChoiceSetOperation = new PresentChoiceSetOperation(internalInterface, choiceSet, InteractionMode.MANUAL_ONLY, null, null, choiceSetSelectionListener, TestValues.GENERAL_INTEGER);

		assertEquals(Task.BLOCKED, presentChoiceSetOperation.getState());

		choiceSet.cancel();

		// Once the operation has started
		queue.add(presentChoiceSetOperation, false);
		sleep();

		assertEquals(Task.CANCELED, presentChoiceSetOperation.getState());

		// Make sure neither a `CancelInteraction` or `PerformInteraction` RPC is ever sent
		verify(internalInterface, never()).sendRPC(any(CancelInteraction.class));
		verify(internalInterface, never()).sendRPC(any(PerformInteraction.class));
	}

	@Test
	public void testCancelingChoiceSetIfHeadUnitDoesNotSupportFeature(){
		// Cancel Interaction is only supported on RPC specs v.6.0.0+
		when(internalInterface.getSdlMsgVersion()).thenReturn(new SdlMsgVersion(5, 3));
		presentChoiceSetOperation = new PresentChoiceSetOperation(internalInterface, choiceSet, InteractionMode.MANUAL_ONLY, null, null, choiceSetSelectionListener, TestValues.GENERAL_INTEGER);
		queue.add(presentChoiceSetOperation, false);
		sleep();

		assertEquals(Task.IN_PROGRESS, presentChoiceSetOperation.getState());

		choiceSet.cancel();

		verify(internalInterface, never()).sendRPC(any(CancelInteraction.class));
		verify(internalInterface, times(1)).sendRPC(any(PerformInteraction.class));
	}

	@Test
	public void testCancelingChoiceSetIfHeadUnitDoesNotSupportFeatureButThreadIsNotRunning(){
		// Cancel Interaction is only supported on RPC specs v.6.0.0+
		when(internalInterface.getSdlMsgVersion()).thenReturn(new SdlMsgVersion(5, 3));
		presentChoiceSetOperation = new PresentChoiceSetOperation(internalInterface, choiceSet, InteractionMode.MANUAL_ONLY, null, null, choiceSetSelectionListener, TestValues.GENERAL_INTEGER);

		assertEquals(Task.BLOCKED, presentChoiceSetOperation.getState());

		choiceSet.cancel();

		verify(internalInterface, never()).sendRPC(any(CancelInteraction.class));

		// Once the operation has started
		queue.add(presentChoiceSetOperation, false);
		sleep();

		assertEquals(Task.CANCELED, presentChoiceSetOperation.getState());

		// Make sure neither a `CancelInteraction` or `PerformInteraction` RPC is ever sent
		verify(internalInterface, never()).sendRPC(any(CancelInteraction.class));
		verify(internalInterface, never()).sendRPC(any(PerformInteraction.class));
	}
}