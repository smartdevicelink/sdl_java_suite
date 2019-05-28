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
import com.smartdevicelink.test.Test;

public class SubCellCommandListTests extends AndroidTestCase2 {

	@Override
	public void setUp() throws Exception{
		super.setUp();
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	public void testSettersAndGetters() {

		RunScore runScore = new RunScore(Test.GENERAL_INT, Test.GENERAL_INTEGER_LIST, Test.GENERAL_INTEGER_LIST);

		// set everything
		SubCellCommandList subCellCommandList = new SubCellCommandList(Test.GENERAL_STRING, Test.GENERAL_INTEGER, runScore, Test.GENERAL_MENUCELL_LIST, Test.GENERAL_MENUCELL_LIST);

		// use getters and assert equality
		assertEquals(subCellCommandList.getMenuTitle(), Test.GENERAL_STRING);
		assertEquals(subCellCommandList.getParentId(), Test.GENERAL_INTEGER);
		assertEquals(runScore, runScore);
		assertEquals(subCellCommandList.getNewList(), Test.GENERAL_MENUCELL_LIST);
		assertEquals(subCellCommandList.getOldList(), Test.GENERAL_MENUCELL_LIST);

	}
}
