package com.smartdevicelink.managers.screen.menu;

import com.smartdevicelink.util.DebugTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bilal Alsharifi on 1/25/21.
 */
class DynamicMenuUpdateAlgorithm {
    private static final String TAG = "DynamicMenuUpdateAlgorithm";

    // Cell state that tells the menu manager what it should do with a given SDLMenuCell
    enum MenuCellState {
        // Marks the cell to be deleted
        DELETE,

        // Marks the cell to be added
        ADD,

        // Marks the cell to be kept
        KEEP
    }

    static DynamicMenuUpdateRunScore compareOldMenuCells(List<MenuCell> oldMenuCells, List<MenuCell> updatedMenuCells) {
        if (oldMenuCells == null || oldMenuCells.isEmpty()) {
            return null;
        }

        DynamicMenuUpdateRunScore bestScore = startCompareAtRun(oldMenuCells, updatedMenuCells);
        DebugTool.logInfo(TAG, "Best menu run score: " + bestScore.getScore());

        return bestScore;
    }

    static DynamicMenuUpdateRunScore startCompareAtRun(List<MenuCell> oldMenuCells, List<MenuCell> updatedMenuCells) {
        DynamicMenuUpdateRunScore bestRunScore = null;

        // This first loop is for each 'run'
        for (int run = 0; run < oldMenuCells.size(); run++) {
            List<MenuCellState> oldArray = new ArrayList<>(oldMenuCells.size());
            List<MenuCellState> newArray = new ArrayList<>(updatedMenuCells.size());

            // Set the statuses
            for (int i = 0; i < oldMenuCells.size(); i++) {
                oldArray.add(MenuCellState.DELETE);
            }
            for (int i = 0; i < updatedMenuCells.size(); i++) {
                newArray.add(MenuCellState.KEEP);
            }

            int startIndex = 0;

            // Keep items that appear in both lists
            for (int oldItems = run; oldItems < oldMenuCells.size(); oldItems++) {

                for (int newItems = startIndex; newItems < updatedMenuCells.size(); newItems++) {

                    if (oldMenuCells.get(oldItems).equals(updatedMenuCells.get(newItems))) {
                        oldArray.set(oldItems, MenuCellState.KEEP);
                        newArray.set(newItems, MenuCellState.KEEP);
                        // set the new start index
                        startIndex = newItems + 1;
                        break;
                    }
                }
            }

            // Calculate number of adds, or the 'score' for this run
            int numberOfAdds = 0;
            for (int x = 0; x < newArray.size(); x++) {
                if (newArray.get(x).equals(MenuCellState.ADD)) {
                    numberOfAdds++;
                }
            }

            // see if we have a new best score and set it if we do
            if (bestRunScore == null || numberOfAdds < bestRunScore.getScore()) {
                bestRunScore = new DynamicMenuUpdateRunScore(numberOfAdds, oldArray, newArray);
            }

        }
        return bestRunScore;
    }
}
