package com.smartdevicelink.managers.screen.menu;

import com.livio.taskmaster.Task;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.file.MultipleFileCompletionListener;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.managers.screen.menu.DynamicMenuUpdateAlgorithm.MenuCellState;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.WindowCapability;
import com.smartdevicelink.proxy.rpc.enums.MenuLayout;
import com.smartdevicelink.proxy.rpc.listeners.OnMultipleRequestListener;
import com.smartdevicelink.util.DebugTool;

import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.smartdevicelink.managers.screen.menu.BaseMenuManager.lastMenuId;
import static com.smartdevicelink.managers.screen.menu.MenuReplaceUtilities.commandForMenuCell;
import static com.smartdevicelink.managers.screen.menu.MenuReplaceUtilities.deleteCommandsForCells;
import static com.smartdevicelink.managers.screen.menu.MenuReplaceUtilities.findAllArtworksToBeUploadedFromCells;
import static com.smartdevicelink.managers.screen.menu.MenuReplaceUtilities.mainMenuCommandsForCells;
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

        // Run the lists through the new algorithm
        DynamicMenuUpdateRunScore runScore = DynamicMenuUpdateAlgorithm.compareOldMenuCells(currentMenu, updatedMenu);
        if (runScore == null) {
            // Both old and new menu cells are empty. Nothing needs to be done.
            finishOperation(true);
            return;
        }

        // We need to run through the keeps and see if they have subCells, as they also need to be run through the compare function.
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
                    sendDynamicRootMenuRPCs(cellsToDelete, cellsToAdd, oldKeeps, newKeeps, listener);
                }
            });
        } else {
            // No Artworks to be uploaded, send off
            sendDynamicRootMenuRPCs(cellsToDelete, cellsToAdd, oldKeeps, newKeeps, listener);
        }
    }

    private void sendNewMenuCells(final List<MenuCell> newMenuCells, final List<MenuCell> oldKeeps, final List<MenuCell> newKeeps, final CompletionListener listener) {
        if (getState() == Task.CANCELED) {
            return;
        }

        if (newMenuCells == null || newMenuCells.isEmpty()) {
            // This can be considered a success if the user was clearing out their menu
            listener.onComplete(true);
            return;
        }

        MenuLayout defaultSubmenuLayout = menuConfiguration != null ? menuConfiguration.getSubMenuLayout() : null;

        List<RPCRequest> mainMenuCommands = mainMenuCommandsForCells(newMenuCells, fileManager.get(), windowCapability, updatedMenu, defaultSubmenuLayout);
        final List<RPCRequest> subMenuCommands = subMenuCommandsForCells(newMenuCells, fileManager.get(), windowCapability, defaultSubmenuLayout);

        internalInterface.get().sendRPCs(mainMenuCommands, new OnMultipleRequestListener() {
            @Override
            public void onUpdate(int remainingRequests) {
            }

            @Override
            public void onFinished() {
                if (!subMenuCommands.isEmpty()) {
                    DebugTool.logInfo(TAG, "Finished sending main menu commands. Sending sub menu commands.");
                    sendNewSubMenuCells(subMenuCommands, oldKeeps, newKeeps, listener);
                } else {
                    if (newKeeps != null && !newKeeps.isEmpty()) {
                        runSubMenuCompareAlgorithm(oldKeeps, newKeeps, listener);
                    } else {
                        DebugTool.logInfo(TAG, "Finished sending main menu commands.");

                        if (listener != null) {
                            listener.onComplete(true);
                        }
                    }
                }
            }

            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                if (response.getSuccess()) {
                    try {
                        DebugTool.logInfo(TAG, "Main Menu response: " + response.serializeJSON().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    DebugTool.logError(TAG, "Result: " + response.getResultCode() + " Info: " + response.getInfo());
                }
            }
        });
    }

    private void sendNewSubMenuCells(List<RPCRequest> commands, final List<MenuCell> oldKeeps, final List<MenuCell> newKeeps, final CompletionListener listener) {
        if (getState() == Task.CANCELED) {
            return;
        }

        internalInterface.get().sendRPCs(commands, new OnMultipleRequestListener() {
            @Override
            public void onUpdate(int remainingRequests) {
            }

            @Override
            public void onFinished() {
                if (newKeeps != null && !newKeeps.isEmpty()) {
                    runSubMenuCompareAlgorithm(oldKeeps, newKeeps, listener);
                } else {
                    DebugTool.logInfo(TAG, "Finished Updating Menu");

                    if (listener != null) {
                        listener.onComplete(true);
                    }
                }
            }

            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                if (response.getSuccess()) {
                    try {
                        DebugTool.logInfo(TAG, "Sub Menu response: " + response.serializeJSON().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    DebugTool.logError(TAG, "Failed to send sub menu commands: " + response.getInfo());
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

        internalInterface.get().sendRPCs(mainMenuCommands, new OnMultipleRequestListener() {
            @Override
            public void onUpdate(int remainingRequests) {
                // nothing here
            }

            @Override
            public void onFinished() {
                if (listener != null) {
                    listener.onComplete(true);
                }
            }

            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                if (response.getSuccess()) {
                    try {
                        DebugTool.logInfo(TAG, "Dynamic Sub Menu response: " + response.serializeJSON().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    DebugTool.logError(TAG, "Result: " + response.getResultCode() + " Info: " + response.getInfo());
                }
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

        internalInterface.get().sendRPCs(deleteMenuCommands, new OnMultipleRequestListener() {
            @Override
            public void onUpdate(int remainingRequests) {

            }

            @Override
            public void onFinished() {
                DebugTool.logInfo(TAG, "Successfully deleted cells");
                if (listener != null) {
                    listener.onComplete(true);
                }
            }

            @Override
            public void onResponse(int correlationId, RPCResponse response) {

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

    private void sendDynamicRootMenuRPCs(List<MenuCell> deleteMenuCells, final List<MenuCell> updatedCells, final List<MenuCell> oldKeeps, final List<MenuCell> newKeeps, final CompletionListener listener) {
        if (getState() == Task.CANCELED) {
            return;
        }

        sendDeleteCurrentMenu(deleteMenuCells, new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                sendNewMenuCells(updatedCells, oldKeeps, newKeeps, new CompletionListener() {
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
