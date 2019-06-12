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
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.KeyboardProperties;
import com.smartdevicelink.proxy.rpc.PerformInteraction;
import com.smartdevicelink.proxy.rpc.enums.InteractionMode;
import com.smartdevicelink.proxy.rpc.enums.KeyboardLayout;
import com.smartdevicelink.proxy.rpc.enums.KeypressMode;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.proxy.rpc.enums.LayoutMode;

import java.util.Collections;

import static org.mockito.Mockito.mock;

public class PresentChoiceSetOperationTests extends AndroidTestCase2 {

	private PresentChoiceSetOperation presentChoiceSetOperation;

	@Override
	public void setUp() throws Exception{
		super.setUp();

		ISdl internalInterface = mock(ISdl.class);
		KeyboardListener keyboardListener = mock(KeyboardListener.class);
		ChoiceSetSelectionListener choiceSetSelectionListener = mock(ChoiceSetSelectionListener.class);
		ChoiceCell cell1 = new ChoiceCell("Cell1");
		cell1.setChoiceId(0);
		ChoiceSet choiceSet = new ChoiceSet("Test", Collections.singletonList(cell1), choiceSetSelectionListener);
		presentChoiceSetOperation = new PresentChoiceSetOperation(internalInterface, choiceSet, InteractionMode.MANUAL_ONLY, getKeyBoardProperties(), keyboardListener, choiceSetSelectionListener);
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

}
