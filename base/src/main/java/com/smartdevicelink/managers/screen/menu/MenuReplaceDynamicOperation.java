package com.smartdevicelink.managers.screen.menu;

import com.livio.taskmaster.Task;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.file.MultipleFileCompletionListener;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.managers.screen.menu.DynamicMenuUpdateAlgorithm.MenuCellState;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.WindowCapability;
import com.smartdevicelink.proxy.rpc.enums.MenuLayout;
import com.smartdevicelink.util.DebugTool;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.smartdevicelink.managers.screen.menu.BaseMenuManager.lastMenuId;
import static com.smartdevicelink.managers.screen.menu.MenuReplaceUtilities.commandForMenuCell;
import static com.smartdevicelink.managers.screen.menu.MenuReplaceUtilities.deleteCommandsForCells;
import static com.smartdevicelink.managers.screen.menu.MenuReplaceUtilities.findAllArtworksToBeUploadedFromCells;
import static com.smartdevicelink.managers.screen.menu.MenuReplaceUtilities.mainMenuCommandsForCells;
import static com.smartdevicelink.managers.screen.menu.MenuReplaceUtilities.sendRPCs;
import static com.smartdevicelink.managers.screen.menu.MenuReplaceUtilities.subMenuCommandsForCells;

/**
 * Created by Bilal Alsharifi on 1/20/21.
 */
class MenuReplaceDynamicOperation extends Task {
    private static final String TAG = "MenuReplaceDynamicOperation";

    private final WeakReference<ISdl> internalInterface;
    private final WeakReference<FileManager> fileManager;
    private final WindowCapability windowCapability;
    private List<MenuCell> currentMenu;
    private final List<MenuCell> updatedMenu;
    private final MenuManagerCompletionListener operationCompletionListener;
    private MenuConfiguration menuConfiguration;

    MenuReplaceDynamicOperation(ISdl internalInterface, FileManager fileManager, WindowCapability windowCapability, MenuConfiguration menuConfiguration, List<MenuCell> currentMenu, List<MenuCell> updatedMenu, MenuManagerCompletionListener operationCompletionListener) {
        super(TAG);
        this.internalInterface = new WeakReference<>(internalInterface);
        this.fileManager = new WeakReference<>(fileManager);
        this.windowCapability = windowCapability;
        this.menuConfiguration = menuConfiguration;
        this.currentMenu = currentMenu;
        this.updatedMenu = updatedMenu;
        this.operationCompletionListener = operationCompletionListener;
    }

    @Override
    public void onExecute() {
        start();
    }

    private void start() {
        if (getState() == Task.CANCELED) {
            return;
        }

        updateMenuCells(new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                finishOperation(success);
            }
        });
    }

    private void updateMenuCells(final CompletionListener listener) {
        if (getState() == Task.CANCELED) {
            return;
        }

        DynamicMenuUpdateRunScore runScore = DynamicMenuUpdateAlgorithm.compareOldMenuCells(currentMenu, updatedMenu);

        // If both old and new menu cells are empty. Then nothing needs to be done.
        if (runScore == null) {
            finishOperation(true);
            return;
        }

        List<MenuCellState> deleteMenuStatus = runScore.getOldStatus();
        List<MenuCellState> addMenuStatus = runScore.getUpdatedStatus();

        final List<MenuCell> cellsToDelete = filterMenuCellsWithStatusList(currentMenu, deleteMenuStatus, MenuCellState.DELETE);
        final List<MenuCell> cellsToAdd = filterMenuCellsWithStatusList(updatedMenu, addMenuStatus, MenuCellState.ADD);

        // These arrays should ONLY contain KEEPS. These will be used for SubMenu compares
        final List<MenuCell> oldKeeps = filterMenuCellsWithStatusList(currentMenu, deleteMenuStatus, MenuCellState.KEEP);
        final List<MenuCell> newKeeps = filterMenuCellsWithStatusList(updatedMenu, addMenuStatus, MenuCellState.KEEP);

        updateIdsOnDynamicCells(cellsToAdd);
        
        // Since we are creating a new Menu but keeping old cells we must first transfer the old cellIDs to the new menus kept cells.
        // this is needed for the onCommands to still work
        transferIdsToKeptCells(newKeeps);

        // Upload the Artworks
        List<SdlArtwork> artworksToBeUploaded = findAllArtworksToBeUploadedFromCells(updatedMenu, fileManager.get(), windowCapability);
        if (!artworksToBeUploaded.isEmpty() && fileManager.get() != null) {
            fileManager.get().uploadArtworks(artworksToBeUploaded, new MultipleFileCompletionListener() {
                @Override
                public void onComplete(Map<String, String> errors) {
                    if (errors != null && !errors.isEmpty()) {
                        DebugTool.logError(TAG, "Error uploading Menu Artworks: " + errors.toString());
                    } else {
                        DebugTool.logInfo(TAG, "Menu Artworks Uploaded");
                    }
                    updateMenuWithCellsToDelete(cellsToDelete, cellsToAdd, new CompletionListener() {
                        @Override
                        public void onComplete(boolean success) {
                            startSubMenuUpdatesWithOldKeptCells(oldKeeps, newKeeps, 0, listener);
                        }
                    });
                }
            });
        } else {
            // Cells have no artwork to load
            updateMenuWithCellsToDelete(cellsToDelete, cellsToAdd, new CompletionListener() {
                @Override
                public void onComplete(boolean success) {
                    startSubMenuUpdatesWithOldKeptCells(oldKeeps, newKeeps, 0, listener);
                }
            });
        }
    }

    private void updateMenuWithCellsToDelete(List<MenuCell> deleteCells, final List<MenuCell> addCells, final CompletionListener listener) {
        if (getState() == Task.CANCELED) {
            return;
        }

        sendDeleteCurrentMenu(deleteCells, new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                sendNewMenuCells(addCells, currentMenu, new CompletionListener() {
                    @Override
                    public void onComplete(boolean success) {
                        if (!success) {
                            DebugTool.logError(TAG, "Error Sending Current Menu");
                        }

                        listener.onComplete(success);
                    }
                });
            }
        });
    }

    private void sendDeleteCurrentMenu(List<MenuCell> deleteMenuCells, final CompletionListener listener) {
        if (getState() == Task.CANCELED) {
            return;
        }

        if (deleteMenuCells.isEmpty()) {
            listener.onComplete(true);
            return;
        }

        List<RPCRequest> deleteMenuCommands = deleteCommandsForCells(deleteMenuCells);
        sendRPCs(deleteMenuCommands, internalInterface.get(), new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                if (!success) {
                    DebugTool.logWarning(TAG, "Unable to delete all old menu commands");
                } else {
                    DebugTool.logInfo(TAG, "Finished deleting old menu");
                }
                listener.onComplete(success);
            }
        });
    }

    private void sendNewMenuCells(final List<MenuCell> newMenuCells, final List<MenuCell> oldMenu, final CompletionListener listener) {
        if (getState() == Task.CANCELED) {
            return;
        }

        if (newMenuCells == null || newMenuCells.isEmpty()) {
            // This can be considered a success if the user was clearing out their menu
            DebugTool.logInfo(TAG, "There are no cells to update.");
            listener.onComplete(true);
            return;
        }

        MenuLayout defaultSubmenuLayout = menuConfiguration != null ? menuConfiguration.getSubMenuLayout() : null;

        final List<RPCRequest> mainMenuCommands = mainMenuCommandsForCells(newMenuCells, fileManager.get(), windowCapability, updatedMenu, defaultSubmenuLayout);
        final List<RPCRequest> subMenuCommands = subMenuCommandsForCells(newMenuCells, fileManager.get(), windowCapability, defaultSubmenuLayout);

        sendRPCs(mainMenuCommands, internalInterface.get(), new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                if (!success) {
                    DebugTool.logError(TAG, "Failed to send main menu commands");
                    listener.onComplete(false);
                    return;
                }
                sendRPCs(subMenuCommands, internalInterface.get(), new CompletionListener() {
                    @Override
                    public void onComplete(boolean success) {
                        if (!success) {
                            DebugTool.logError(TAG, "Failed to send sub menu commands");
                        } else {
                            DebugTool.logInfo(TAG, "Finished updating menu");
                        }
                        listener.onComplete(success);
                    }
                });
            }
        });
    }

    private void startSubMenuUpdatesWithOldKeptCells(final List<MenuCell> oldKeptCells, final List<MenuCell> newKeptCells, final int startIndex, final CompletionListener listener) {
        if (oldKeptCells.isEmpty() || startIndex >= oldKeptCells.size()) {
            listener.onComplete(true);
            return;
        }

        if (oldKeptCells.get(startIndex) != null && oldKeptCells.get(startIndex).getSubCells() != null && !oldKeptCells.get(startIndex).getSubCells().isEmpty()){
            DynamicMenuUpdateRunScore tempScore = DynamicMenuUpdateAlgorithm.compareOldMenuCells(oldKeptCells.get(startIndex).getSubCells(), newKeptCells.get(startIndex).getSubCells());

            // If both old and new menu cells are empty. Then nothing needs to be done.
            if (tempScore == null) {
                finishOperation(true);
                return;
            }

            List<MenuCellState> deleteMenuStatus = tempScore.getOldStatus();
            List<MenuCellState> addMenuStatus = tempScore.getUpdatedStatus();

            final List<MenuCell> cellsToDelete = filterMenuCellsWithStatusList(oldKeptCells.get(startIndex).getSubCells(), deleteMenuStatus, MenuCellState.DELETE);
            final List<MenuCell> cellsToAdd = filterMenuCellsWithStatusList(newKeptCells.get(startIndex).getSubCells(), addMenuStatus, MenuCellState.ADD);

            final List<MenuCell> oldKeeps = filterMenuCellsWithStatusList(oldKeptCells.get(startIndex).getSubCells(), deleteMenuStatus, MenuCellState.KEEP);
            final List<MenuCell> newKeeps = filterMenuCellsWithStatusList(newKeptCells.get(startIndex).getSubCells(), addMenuStatus, MenuCellState.KEEP);

            transferCellIDFromOldCells(oldKeeps, newKeeps);

            sendDeleteCurrentMenu(cellsToDelete, new CompletionListener() {
                @Override
                public void onComplete(boolean success) {
                    sendNewMenuCells(cellsToAdd, currentMenu.get(startIndex).getSubCells(), new CompletionListener() {
                        @Override
                        public void onComplete(boolean success) {
                            // After the first set of submenu cells were added and deleted we must find the next set of sub cells until we loop through all the elements
                            startSubMenuUpdatesWithOldKeptCells(oldKeptCells, newKeptCells, startIndex + 1, listener);
                        }
                    });
                }
            });
        } else {
            // After the first set of submenu cells were added and deleted we must find the next set of sub cells until we loop through all the elements
            startSubMenuUpdatesWithOldKeptCells(oldKeptCells, newKeptCells, startIndex + 1, listener);
        }
    }

    private void transferCellIDFromOldCells(List<MenuCell> oldCells, List<MenuCell> newCells) {
        if (oldCells.isEmpty()) {
            return;
        }
        for (int i = 0; i < newCells.size(); i++) {
            newCells.get(i).setCellId(oldCells.get(i).getCellId());
        }
    }

    private void startSubMenuUpdatesWithOldKeptCells2(List<MenuCell> oldKeptCells, List<MenuCell> newKeptCells, int startIndex, CompletionListener listener) {
        if (newKeptCells != null && !newKeptCells.isEmpty()) {
            runSubMenuCompareAlgorithm(oldKeptCells, newKeptCells, listener);
        } else {
            DebugTool.logInfo(TAG, "Finished sending main menu commands.");

            if (listener != null) {
                listener.onComplete(true);
            }
        }
    }

    private void runSubMenuCompareAlgorithm(List<MenuCell> oldKeeps, List<MenuCell> newKeeps, CompletionListener listener) {
        // any cells that were re-added have their sub-cells added with them
        // at this point all we care about are the cells that were deemed equal and kept.
        if (newKeeps == null || newKeeps.isEmpty()) {
            listener.onComplete(true);
            return;
        }

        List<SubCellCommandList> commandLists = new ArrayList<>();

        for (int i = 0; i < newKeeps.size(); i++) {
            MenuCell newKeptCell = newKeeps.get(i);
            MenuCell oldKeptCell = oldKeeps.get(i);

            if (oldKeptCell.getSubCells() != null && !oldKeptCell.getSubCells().isEmpty() && newKeptCell.getSubCells() != null && !newKeptCell.getSubCells().isEmpty()) {
                DynamicMenuUpdateRunScore subScore = DynamicMenuUpdateAlgorithm.startCompareAtRun(0, oldKeptCell.getSubCells(), newKeptCell.getSubCells());

                if (subScore != null) {
                    DebugTool.logInfo(TAG, "Sub menu Run Score: " + oldKeptCell.getTitle() + " Score: " + subScore.getScore());
                    SubCellCommandList commandList = new SubCellCommandList(oldKeptCell.getTitle(), oldKeptCell.getCellId(), subScore, oldKeptCell.getSubCells(), newKeptCell.getSubCells());
                    commandLists.add(commandList);
                }
            }
        }
        createSubMenuDynamicCommands(commandLists, listener);
    }

    private void createSubMenuDynamicCommands(final List<SubCellCommandList> commandLists, final CompletionListener listener) {
        if (getState() == Task.CANCELED) {
            return;
        }

        if (commandLists.isEmpty()) {
            DebugTool.logInfo(TAG, "All menu updates, including sub menus - done.");
            listener.onComplete(true);
            return;
        }

        final SubCellCommandList commandList = commandLists.remove(0);

        DebugTool.logInfo(TAG, "Creating and Sending Dynamic Sub Commands For Root Menu Cell: " + commandList.getMenuTitle());

        // grab the scores
        DynamicMenuUpdateRunScore score = commandList.getListsScore();
        List<MenuCellState> newStates = score.getUpdatedStatus();
        List<MenuCellState> oldStates = score.getOldStatus();

        // Grab the sub-menus from the parent cell
        final List<MenuCell> oldCells = commandList.getOldList();
        final List<MenuCell> newCells = commandList.getNewList();

        List<MenuCell> cellsToDelete = filterMenuCellsWithStatusList(currentMenu, oldStates, MenuCellState.DELETE);

        // Set up the adds
        List<MenuCell> cellsToAdd = filterMenuCellsWithStatusList(updatedMenu, newStates, MenuCellState.ADD);
        List<MenuCell> subCellKeepsNew = filterMenuCellsWithStatusList(updatedMenu, newStates, MenuCellState.KEEP);

        final List<MenuCell> addsWithNewIds = updateIdsOnDynamicSubCells(oldCells, cellsToAdd, commandList.getParentId());
        // this is needed for the onCommands to still work
        transferIdsToKeptSubCells(oldCells, subCellKeepsNew);

        sendDeleteCurrentMenu(cellsToDelete, new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                if (addsWithNewIds != null && !addsWithNewIds.isEmpty()) {
                    createAndSendDynamicSubMenuRPCs(newCells, addsWithNewIds, new CompletionListener() {
                        @Override
                        public void onComplete(boolean success) {
                            // recurse through next sub list
                            DebugTool.logInfo(TAG, "Finished Sending Dynamic Sub Commands For Root Menu Cell: " + commandList.getMenuTitle());
                            createSubMenuDynamicCommands(commandLists, listener);
                        }
                    });
                } else {
                    // no add commands to send, recurse through next sub list
                    DebugTool.logInfo(TAG, "Finished Sending Dynamic Sub Commands For Root Menu Cell: " + commandList.getMenuTitle());
                    createSubMenuDynamicCommands(commandLists, listener);
                }
            }
        });
    }

    private void createAndSendDynamicSubMenuRPCs(List<MenuCell> newMenu, final List<MenuCell> adds, final CompletionListener listener) {
        if (getState() == Task.CANCELED) {
            return;
        }

        if (adds.isEmpty()) {
            if (listener != null) {
                // This can be considered a success if the user was clearing out their menu
                DebugTool.logError(TAG, "Called createAndSendDynamicSubMenuRPCs with empty menu");
                listener.onComplete(true);
            }
            return;
        }

        List<RPCRequest> mainMenuCommands = createCommandsForDynamicSubCells(newMenu, adds);

        sendRPCs(mainMenuCommands, internalInterface.get(), new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                listener.onComplete(success);
            }
        });
    }

    private List<RPCRequest> createCommandsForDynamicSubCells(List<MenuCell> oldMenuCells, List<MenuCell> cells) {
        List<RPCRequest> builtCommands = new ArrayList<>();
        for (int z = 0; z < oldMenuCells.size(); z++) {
            MenuCell oldCell = oldMenuCells.get(z);
            for (int i = 0; i < cells.size(); i++) {
                MenuCell cell = cells.get(i);
                if (cell.equals(oldCell)) {
                    builtCommands.add(commandForMenuCell(cell, fileManager.get(), windowCapability, z));
                    break;
                }
            }
        }
        return builtCommands;
    }

    private void updateIdsOnDynamicCells(List<MenuCell> dynamicCells) {
        if (updatedMenu != null && !updatedMenu.isEmpty() && dynamicCells != null && !dynamicCells.isEmpty()) {
            for (int z = 0; z < updatedMenu.size(); z++) {
                MenuCell mainCell = updatedMenu.get(z);
                for (int i = 0; i < dynamicCells.size(); i++) {
                    MenuCell dynamicCell = dynamicCells.get(i);
                    if (mainCell.equals(dynamicCell)) {
                        int newId = ++lastMenuId;
                        updatedMenu.get(z).setCellId(newId);
                        dynamicCells.get(i).setCellId(newId);

                        if (mainCell.getSubCells() != null && !mainCell.getSubCells().isEmpty()) {
                            updateIdsOnMenuCells(mainCell.getSubCells(), mainCell.getCellId());
                        }
                        break;
                    }
                }
            }
        }
    }

    private List<MenuCell> updateIdsOnDynamicSubCells(List<MenuCell> oldList, List<MenuCell> dynamicCells, Integer parentId) {
        if (oldList != null && !oldList.isEmpty() && dynamicCells != null && !dynamicCells.isEmpty()) {
            for (int z = 0; z < oldList.size(); z++) {
                MenuCell mainCell = oldList.get(z);
                for (int i = 0; i < dynamicCells.size(); i++) {
                    MenuCell dynamicCell = dynamicCells.get(i);
                    int newId = ++lastMenuId;
                    if (mainCell.equals(dynamicCell)) {
                        oldList.get(z).setCellId(newId);
                    }
                    dynamicCells.get(i).setParentCellId(parentId);
                    dynamicCells.get(i).setCellId(newId);
                }
            }
            return dynamicCells;
        }
        return null;
    }

    private void updateIdsOnMenuCells(List<MenuCell> cells, int parentId) {
        for (MenuCell cell : cells) {
            int newId = ++lastMenuId;
            cell.setCellId(newId);
            cell.setParentCellId(parentId);
            if (cell.getSubCells() != null && !cell.getSubCells().isEmpty()) {
                updateIdsOnMenuCells(cell.getSubCells(), cell.getCellId());
            }
        }
    }

    private void transferIdsToKeptCells(List<MenuCell> keeps) {
        for (int z = 0; z < currentMenu.size(); z++) {
            MenuCell oldCell = currentMenu.get(z);
            for (int i = 0; i < keeps.size(); i++) {
                MenuCell keptCell = keeps.get(i);
                if (oldCell.equals(keptCell)) {
                    keptCell.setCellId(oldCell.getCellId());
                    break;
                }
            }
        }
    }

    private void transferIdsToKeptSubCells(List<MenuCell> old, List<MenuCell> keeps) {
        for (int z = 0; z < old.size(); z++) {
            MenuCell oldCell = old.get(z);
            for (int i = 0; i < keeps.size(); i++) {
                MenuCell keptCell = keeps.get(i);
                if (oldCell.equals(keptCell)) {
                    keptCell.setCellId(oldCell.getCellId());
                    break;
                }
            }
        }
    }

    private List<MenuCell> filterMenuCellsWithStatusList(List<MenuCell> menuCells, List<MenuCellState> statusList, MenuCellState menuCellState){
        List<MenuCell> filteredCells = new ArrayList<>();
        for (int index = 0; index < statusList.size(); index++) {
            if (statusList.get(index).equals(menuCellState)) {
                filteredCells.add(menuCells.get(index));
            }
        }
        return filteredCells;
    }

    void setMenuConfiguration(MenuConfiguration menuConfiguration) {
        this.menuConfiguration = menuConfiguration;
    }

    public void setCurrentMenu(List<MenuCell> currentMenuCells) {
        this.currentMenu = currentMenuCells;
    }

    private void finishOperation(boolean success) {
        if (operationCompletionListener != null) {
            operationCompletionListener.onComplete(success, currentMenu);
        }
        onFinished();
    }
}