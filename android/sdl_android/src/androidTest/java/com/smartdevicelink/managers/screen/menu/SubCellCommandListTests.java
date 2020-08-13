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

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.smartdevicelink.test.TestValues;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertEquals;

@RunWith(AndroidJUnit4.class)
public class SubCellCommandListTests {

	@Test
	public void testSettersAndGetters() {

		RunScore runScore = new RunScore(TestValues.GENERAL_INT, TestValues.GENERAL_INTEGER_LIST, TestValues.GENERAL_INTEGER_LIST);

		// set everything
		SubCellCommandList subCellCommandList = new SubCellCommandList(TestValues.GENERAL_STRING, TestValues.GENERAL_INTEGER, runScore, TestValues.GENERAL_MENUCELL_LIST, TestValues.GENERAL_MENUCELL_LIST);

		// use getters and assert equality
		assertEquals(subCellCommandList.getMenuTitle(), TestValues.GENERAL_STRING);
		assertEquals(subCellCommandList.getParentId(), TestValues.GENERAL_INTEGER);
		assertEquals(runScore, runScore);
		assertEquals(subCellCommandList.getNewList(), TestValues.GENERAL_MENUCELL_LIST);
		assertEquals(subCellCommandList.getOldList(), TestValues.GENERAL_MENUCELL_LIST);

	}
}
