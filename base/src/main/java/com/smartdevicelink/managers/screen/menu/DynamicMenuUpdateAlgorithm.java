package com.smartdevicelink.managers.screen.menu;

import com.smartdevicelink.util.DebugTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bilal Alsharifi on 1/25/21.
 */
class DynamicMenuUpdateAlgorithm {
    private static final String TAG = "DynamicMenuUpdateAlgorithm";
    static final int KEEP = 0;
    static final int MARKED_FOR_ADDITION = 1;
    static final int MARKED_FOR_DELETION = 2;

    static DynamicMenuUpdateRunScore runMenuCompareAlgorithm(List<MenuCell> oldCells, List<MenuCell> newCells) {
        if (oldCells == null || oldCells.isEmpty()) {
            return null;
        }

        DynamicMenuUpdateRunScore bestScore = compareOldAndNewLists(oldCells, newCells);
        DebugTool.logInfo(TAG, "Best menu run score: " + bestScore.getScore());

        return bestScore;
    }

    static DynamicMenuUpdateRunScore compareOldAndNewLists(List<MenuCell> oldCells, List<MenuCell> newCells) {
        DynamicMenuUpdateRunScore bestRunScore = null;

        // This first loop is for each 'run'
        for (int run = 0; run < oldCells.size(); run++) {
            List<Integer> oldArray = new ArrayList<>(oldCells.size());
            List<Integer> newArray = new ArrayList<>(newCells.size());

            // Set the statuses
            for (int i = 0; i < oldCells.size(); i++) {
                oldArray.add(MARKED_FOR_DELETION);
            }
            for (int i = 0; i < newCells.size(); i++) {
                newArray.add(MARKED_FOR_ADDITION);
            }

            int startIndex = 0;

            // Keep items that appear in both lists
            for (int oldItems = run; oldItems < oldCells.size(); oldItems++) {

                for (int newItems = startIndex; newItems < newCells.size(); newItems++) {

                    if (oldCells.get(oldItems).equals(newCells.get(newItems))) {
                        oldArray.set(oldItems, KEEP);
                        newArray.set(newItems, KEEP);
                        // set the new start index
                        startIndex = newItems + 1;
                        break;
                    }
                }
            }

            // Calculate number of adds, or the 'score' for this run
            int numberOfAdds = 0;
            for (int x = 0; x < newArray.size(); x++) {
                if (newArray.get(x).equals(MARKED_FOR_ADDITION)) {
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
