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

import java.util.ArrayList;
import java.util.List;

class DynamicMenuUpdateAlgorithm {
    // Cell state that tells the menu manager what it should do with a given MenuCell
    enum MenuCellState {
        DELETE, // Marks the cell to be deleted
        ADD, // Marks the cell to be added
        KEEP // Marks the cell to be kept
    }

    static DynamicMenuUpdateRunScore compatibilityRunScoreWithOldMenuCells(List<MenuCell> oldMenuCells, List<MenuCell> updatedMenuCells) {
        return new DynamicMenuUpdateRunScore(buildAllDeleteStatusesForMenu(oldMenuCells), buildAllAddStatusesForMenu(updatedMenuCells), updatedMenuCells.size());
    }

    static DynamicMenuUpdateRunScore dynamicRunScoreOldMenuCells(List<MenuCell> oldMenuCells, List<MenuCell> updatedMenuCells) {
        if (!oldMenuCells.isEmpty() && updatedMenuCells.isEmpty()) {
            // Deleting all cells
            return new DynamicMenuUpdateRunScore(buildAllDeleteStatusesForMenu(oldMenuCells), new ArrayList<MenuCellState>(), 0);
        } else if (oldMenuCells.isEmpty() && !updatedMenuCells.isEmpty()) {
            // No cells to delete
            return new DynamicMenuUpdateRunScore(new ArrayList<MenuCellState>(), buildAllAddStatusesForMenu(updatedMenuCells), updatedMenuCells.size());
        } else if (oldMenuCells.isEmpty() && updatedMenuCells.isEmpty()) {
            // Empty menu to empty menu
            return new DynamicMenuUpdateRunScore(new ArrayList<MenuCellState>(), new ArrayList<MenuCellState>(), 0);
        }
        return startCompareAtRun(0, oldMenuCells, updatedMenuCells);
    }

    private static DynamicMenuUpdateRunScore startCompareAtRun(int startRun, List<MenuCell> oldMenuCells, List<MenuCell> updatedMenuCells) {
        DynamicMenuUpdateRunScore bestScore = new DynamicMenuUpdateRunScore(new ArrayList<MenuCellState>(), new ArrayList<MenuCellState>(), 0);

        for (int run = startRun; run < oldMenuCells.size(); run++) {
            // Set the menu status as a 1-1 list, start off will oldMenus = all Deletes, newMenu = all Adds
            List<MenuCellState> oldMenuStatus = buildAllDeleteStatusesForMenu(oldMenuCells);
            List<MenuCellState> newMenuStatus = buildAllAddStatusesForMenu(updatedMenuCells);

            int startIndex = 0;
            for (int oldCellIndex = run; oldCellIndex < oldMenuCells.size(); oldCellIndex++) {
                // For each old item, create inner loop to compare old cells to new cells to find a match
                // if a match if found we mark the index at match for both the old and the new status to
                // keep since we do not want to send RPCs for those cases
                for (int newCellIndex = startIndex; newCellIndex < updatedMenuCells.size(); newCellIndex++) {
                    if (oldMenuCells.get(oldCellIndex).equalsWithUniqueTitle(updatedMenuCells.get(newCellIndex))) {
                        oldMenuStatus.set(oldCellIndex, MenuCellState.KEEP);
                        newMenuStatus.set(newCellIndex, MenuCellState.KEEP);
                        startIndex = newCellIndex + 1;
                        break;
                    }
                }
            }

            // Add RPC are the biggest operation so we need to find the run with the least amount of Adds.
            // We will reset the run we use each time a runScore is less than the current score.
            int numberOfAdds = 0;
            for (int status = 0; status < newMenuStatus.size(); status++) {
                if (newMenuStatus.get(status).equals(MenuCellState.ADD)) {
                    numberOfAdds++;
                }
            }

            // As soon as we a run that requires 0 Adds we will use it since we cant do better then 0
            if (numberOfAdds == 0) {
                bestScore = new DynamicMenuUpdateRunScore(oldMenuStatus, newMenuStatus, numberOfAdds);
                return bestScore;
            }

            // if we haven't create the bestScore object or if the current score beats the old score then we will create a new bestScore
            if (bestScore.isEmpty() || numberOfAdds < bestScore.getScore()) {
                bestScore = new DynamicMenuUpdateRunScore(oldMenuStatus, newMenuStatus, numberOfAdds);
            }

        }
        return bestScore;
    }

    /**
     * Builds a 1-1 list of Deletes for every element in the array
     *
     * @param oldMenu The old menu list
     */
    static List<MenuCellState> buildAllDeleteStatusesForMenu(List<MenuCell> oldMenu) {
        List<MenuCellState> oldMenuStatus = new ArrayList<>(oldMenu.size());
        for (int index = 0; index < oldMenu.size(); index++) {
            oldMenuStatus.add(MenuCellState.DELETE);
        }
        return oldMenuStatus;
    }

    /**
     * Builds a 1-1 list of Adds for every element in the list
     *
     * @param newMenu The new menu list
     */
    static List<MenuCellState> buildAllAddStatusesForMenu(List<MenuCell> newMenu) {
        List<MenuCellState> newMenuStatus = new ArrayList<>(newMenu.size());
        for (int index = 0; index < newMenu.size(); index++) {
            newMenuStatus.add(MenuCellState.ADD);
        }
        return newMenuStatus;
    }
}
