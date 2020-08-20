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

import com.smartdevicelink.managers.file.SdlArtworkTests;
import com.smartdevicelink.proxy.rpc.enums.MenuLayout;
import com.smartdevicelink.proxy.rpc.enums.TriggerSource;
import com.smartdevicelink.test.TestValues;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNotSame;
import static junit.framework.TestCase.assertTrue;

@RunWith(AndroidJUnit4.class)
public class MenuCellTests {

	private MenuSelectionListener menuSelectionListener = new MenuSelectionListener() {
		@Override
		public void onTriggered(TriggerSource trigger) {
			// stuff
		}
	};

	@Test
	public void testSettersAndGetters(){

		// set everything
		MenuCell menuCell = new MenuCell(TestValues.GENERAL_STRING, null, null, menuSelectionListener);
		menuCell.setIcon(TestValues.GENERAL_ARTWORK);
		menuCell.setVoiceCommands(TestValues.GENERAL_STRING_LIST);
		menuCell.setMenuSelectionListener(menuSelectionListener);
		menuCell.setSubMenuLayout(TestValues.GENERAL_MENU_LAYOUT);

		// use getters and assert equality
		assertEquals(menuCell.getTitle(), TestValues.GENERAL_STRING);
		assertEquals(menuCell.getIcon(), TestValues.GENERAL_ARTWORK);
		assertEquals(menuCell.getVoiceCommands(), TestValues.GENERAL_STRING_LIST);
		assertEquals(menuCell.getMenuSelectionListener(), menuSelectionListener);
		assertEquals(menuCell.getCellId(), TestValues.GENERAL_MENU_MAX_ID);
		assertEquals(menuCell.getParentCellId(), TestValues.GENERAL_MENU_MAX_ID);
		assertEquals(menuCell.getSubMenuLayout(), TestValues.GENERAL_MENU_LAYOUT);
	}

	@Test
	public void testConstructors(){

		// first constructor was tested in previous method, use the last two here

		MenuCell menuCell3 =new MenuCell(TestValues.GENERAL_STRING, TestValues.GENERAL_ARTWORK, TestValues.GENERAL_STRING_LIST, menuSelectionListener);
		assertEquals(menuCell3.getTitle(), TestValues.GENERAL_STRING);
		assertEquals(menuCell3.getIcon(), TestValues.GENERAL_ARTWORK);
		assertEquals(menuCell3.getVoiceCommands(), TestValues.GENERAL_STRING_LIST);
		assertEquals(menuCell3.getMenuSelectionListener(), menuSelectionListener);

		MenuCell menuCell4 =new MenuCell(TestValues.GENERAL_STRING,null, null, menuSelectionListener);
		assertEquals(menuCell4.getTitle(), TestValues.GENERAL_STRING);
		assertEquals(menuCell4.getMenuSelectionListener(), menuSelectionListener);

		MenuCell menuCell5 = new MenuCell(TestValues.GENERAL_STRING, TestValues.GENERAL_MENU_LAYOUT, TestValues.GENERAL_ARTWORK, TestValues.GENERAL_MENUCELL_LIST);
		assertEquals(menuCell5.getTitle(), TestValues.GENERAL_STRING);
		assertEquals(menuCell5.getIcon(), TestValues.GENERAL_ARTWORK);
		assertEquals(menuCell5.getSubMenuLayout(), TestValues.GENERAL_MENU_LAYOUT);
		assertEquals(menuCell5.getSubCells(), TestValues.GENERAL_MENUCELL_LIST);
	}

	@Test
	public void testEquality(){

		//We should use assertTrue (or assertFalse) because we want to use the overridden equals() method

		MenuCell menuCell = new MenuCell(TestValues.GENERAL_STRING, TestValues.GENERAL_ARTWORK, TestValues.GENERAL_STRING_LIST, menuSelectionListener);
		MenuCell menuCell2 = new MenuCell(TestValues.GENERAL_STRING, TestValues.GENERAL_ARTWORK, TestValues.GENERAL_STRING_LIST, menuSelectionListener);

		// these are the same object, should be equal.
		assertTrue(menuCell.equals(menuCell));

		// Make sure these are marked as equals, even though they are different objects
		assertTrue(menuCell.equals(menuCell2));

		MenuCell menuCell3 = new MenuCell(TestValues.GENERAL_STRING, null, TestValues.GENERAL_STRING_LIST, menuSelectionListener);

		// these should be different
		assertFalse(menuCell.equals(menuCell3));
	}

	@Test
	public void testClone(){
		MenuCell original = new MenuCell(TestValues.GENERAL_STRING, TestValues.GENERAL_ARTWORK, TestValues.GENERAL_STRING_LIST, menuSelectionListener);
		MenuCell clone = original.clone();

		assertNotNull(clone);
		assertNotSame(original, clone);

		assertEquals(original.getTitle(), clone.getTitle());
		assertEquals(original.getCellId(), clone.getCellId());
		assertEquals(original.getParentCellId(), clone.getParentCellId());

		SdlArtworkTests.equalTest(original.getIcon(), clone.getIcon());

		//Test subcells
		List<MenuCell> subcells = new ArrayList<>();
		subcells.add(original.clone());
		subcells.add(clone.clone());

		original = new MenuCell(TestValues.GENERAL_STRING, MenuLayout.LIST, TestValues.GENERAL_ARTWORK,subcells);
		clone = original.clone();

		assertNotNull(original.getSubCells());
		assertNotNull(clone.getSubCells());
		assertNotSame(original.getSubCells(), clone.getSubCells());

		List<MenuCell> originalSubCells = original.getSubCells();
		List<MenuCell> cloneSubCells = clone.getSubCells();

		assertEquals(originalSubCells.size(), cloneSubCells.size());

		for(int i = 0; i < originalSubCells.size(); i++){

			assertNotNull(originalSubCells.get(i));
			assertNotNull(cloneSubCells.get(i));

			assertNotSame(originalSubCells.get(i), cloneSubCells.get(i));
		}



	}

}
