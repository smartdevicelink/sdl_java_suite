/*
 * Copyright (c) 2021 Livio, Inc.
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

import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.proxy.rpc.enums.MenuLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.smartdevicelink.managers.screen.menu.BaseMenuManager.menuCellIdMin;
import static com.smartdevicelink.managers.screen.menu.BaseMenuManager.parentIdNotFound;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by Bilal Alsharifi on 2/4/21.
 */
@RunWith(AndroidJUnit4.class)
public class MenuReplaceUtilitiesTests {
    static int lastMenuId = menuCellIdMin;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testRemoveMenuCellFromList() {
        MenuCell menuCellToDelete;
        boolean cellRemoved;
        List<MenuCell> actualMenuCellList = createMenuCellList();
        List<MenuCell> expectedMenuCellList = createMenuCellList();

        // Delete cell c4
        menuCellToDelete = actualMenuCellList.get(3);
        cellRemoved = MenuReplaceUtilities.removeMenuCellFromList(actualMenuCellList, menuCellToDelete.getCellId());
        assertTrue(cellRemoved);
        expectedMenuCellList.remove(3);
        assertEquals(expectedMenuCellList, actualMenuCellList);
        assertEquals(3, actualMenuCellList.size());

        // Delete cell c4 again - removal should fail and list should not change
        cellRemoved = MenuReplaceUtilities.removeMenuCellFromList(actualMenuCellList, menuCellToDelete.getCellId());
        assertFalse(cellRemoved);
        assertEquals(expectedMenuCellList, actualMenuCellList);
        assertEquals(3, actualMenuCellList.size());

        // Delete cell c3
        menuCellToDelete = actualMenuCellList.get(2);
        cellRemoved = MenuReplaceUtilities.removeMenuCellFromList(actualMenuCellList, menuCellToDelete.getCellId());
        assertTrue(cellRemoved);
        expectedMenuCellList.remove(2);
        assertEquals(expectedMenuCellList, actualMenuCellList);
        assertEquals(2, actualMenuCellList.size());

        // Delete cell c2-2-2
        menuCellToDelete = actualMenuCellList.get(1).getSubCells().get(1).getSubCells().get(1);
        cellRemoved = MenuReplaceUtilities.removeMenuCellFromList(actualMenuCellList, menuCellToDelete.getCellId());
        assertTrue(cellRemoved);
        expectedMenuCellList.get(1).getSubCells().get(1).getSubCells().remove(1);
        assertEquals(expectedMenuCellList, actualMenuCellList);
        assertEquals(2, actualMenuCellList.size());
        assertEquals(1, actualMenuCellList.get(1).getSubCells().get(1).getSubCells().size());

        // Delete cell c2-2-1
        menuCellToDelete = actualMenuCellList.get(1).getSubCells().get(1).getSubCells().get(0);
        cellRemoved = MenuReplaceUtilities.removeMenuCellFromList(actualMenuCellList, menuCellToDelete.getCellId());
        assertTrue(cellRemoved);
        expectedMenuCellList.get(1).getSubCells().get(1).getSubCells().remove(0);
        assertEquals(expectedMenuCellList, actualMenuCellList);
        assertEquals(2, actualMenuCellList.size());
        assertEquals(0, actualMenuCellList.get(1).getSubCells().get(1).getSubCells().size());

        // Delete cell c2-2
        menuCellToDelete = actualMenuCellList.get(1).getSubCells().get(1);
        cellRemoved = MenuReplaceUtilities.removeMenuCellFromList(actualMenuCellList, menuCellToDelete.getCellId());
        assertTrue(cellRemoved);
        expectedMenuCellList.get(1).getSubCells().remove(1);
        assertEquals(expectedMenuCellList, actualMenuCellList);
        assertEquals(2, actualMenuCellList.size());
        assertEquals(1, actualMenuCellList.get(1).getSubCells().size());

        // Delete cell c2-1
        menuCellToDelete = actualMenuCellList.get(1).getSubCells().get(0);
        cellRemoved = MenuReplaceUtilities.removeMenuCellFromList(actualMenuCellList, menuCellToDelete.getCellId());
        assertTrue(cellRemoved);
        expectedMenuCellList.get(1).getSubCells().remove(0);
        assertEquals(expectedMenuCellList, actualMenuCellList);
        assertEquals(2, actualMenuCellList.size());
        assertEquals(0, actualMenuCellList.get(1).getSubCells().size());

        // Delete cell c2
        menuCellToDelete = actualMenuCellList.get(1);
        cellRemoved = MenuReplaceUtilities.removeMenuCellFromList(actualMenuCellList, menuCellToDelete.getCellId());
        assertTrue(cellRemoved);
        expectedMenuCellList.remove(1);
        assertEquals(expectedMenuCellList, actualMenuCellList);
        assertEquals(1, actualMenuCellList.size());

        // Delete cell c1
        menuCellToDelete = actualMenuCellList.get(0);
        cellRemoved = MenuReplaceUtilities.removeMenuCellFromList(actualMenuCellList, menuCellToDelete.getCellId());
        assertTrue(cellRemoved);
        expectedMenuCellList.remove(0);
        assertEquals(expectedMenuCellList, actualMenuCellList);
        assertEquals(0, actualMenuCellList.size());
    }

    private List<MenuCell> createMenuCellList() {
        /*

        c1            c2            c3            c4
                     /  \                        /  \
                    /    \                      /    \
                 c2-1   c2-2                  c4-1  c4-2
                        /  \
                       /    \
                   c2-2-1   c2-2-2

        */

        SdlArtwork sdlArtwork = null;
        List<String> voiceCommands = null;
        MenuSelectionListener listener = null;
        MenuLayout subMenuLayout = null;

        MenuCell menuCell1 = new MenuCell("c1", sdlArtwork, voiceCommands, listener);

        MenuCell menuCell2_1 = new MenuCell("c2_1", sdlArtwork, voiceCommands, listener);
        MenuCell menuCell2_2_1 = new MenuCell("c2_2_1", sdlArtwork, voiceCommands, listener);
        MenuCell menuCell2_2_2 = new MenuCell("c2_2_2", sdlArtwork, voiceCommands, listener);
        MenuCell menuCell2_2 = new MenuCell("c2_2", subMenuLayout, sdlArtwork, new ArrayList<>(Arrays.asList(menuCell2_2_1, menuCell2_2_2)));
        MenuCell menuCell2 = new MenuCell("c2", subMenuLayout, sdlArtwork, new ArrayList<>(Arrays.asList(menuCell2_1, menuCell2_2)));

        MenuCell menuCell3 = new MenuCell("c3", sdlArtwork, voiceCommands, listener);

        MenuCell menuCell4_1 = new MenuCell("c4_1", sdlArtwork, voiceCommands, listener);
        MenuCell menuCell4_2 = new MenuCell("c4_2", sdlArtwork, voiceCommands, listener);
        MenuCell menuCell4 = new MenuCell("c4", subMenuLayout, sdlArtwork, new ArrayList<>(Arrays.asList(menuCell4_1, menuCell4_2)));

        List<MenuCell> menuCellList = new ArrayList<>(Arrays.asList(menuCell1, menuCell2, menuCell3, menuCell4));
        updateIdsOnMenuCells(menuCellList, parentIdNotFound);

        return menuCellList ;
    }

    private  void updateIdsOnMenuCells(List<MenuCell> menuCells, int parentId) {
        for (MenuCell cell : menuCells) {
            cell.setCellId(lastMenuId++);
            if (parentId != parentIdNotFound) {
                cell.setParentCellId(parentId);
            }
            if (cell.getSubCells() != null && !cell.getSubCells().isEmpty()) {
                updateIdsOnMenuCells(cell.getSubCells(), cell.getCellId());
            }
        }
    }
}
