package com.smartdevicelink.managers.screen.menu;

import static com.smartdevicelink.managers.ManagerUtility.WindowCapabilityUtility.hasImageFieldOfName;
import static com.smartdevicelink.managers.ManagerUtility.WindowCapabilityUtility.hasTextFieldOfName;
import static com.smartdevicelink.managers.screen.menu.MenuReplaceUtilities.addMenuRequestWithCommandId;
import static com.smartdevicelink.managers.screen.menu.MenuReplaceUtilities.cloneMenuCellsList;
import static com.smartdevicelink.managers.screen.menu.MenuReplaceUtilities.commandIdForRPCRequest;
import static com.smartdevicelink.managers.screen.menu.MenuReplaceUtilities.deleteCommandsForCells;
import static com.smartdevicelink.managers.screen.menu.MenuReplaceUtilities.findAllArtworksToBeUploadedFromCells;
import static com.smartdevicelink.managers.screen.menu.MenuReplaceUtilities.isSubMenuCell;
import static com.smartdevicelink.managers.screen.menu.MenuReplaceUtilities.mainMenuCommandsForCells;
import static com.smartdevicelink.managers.screen.menu.MenuReplaceUtilities.positionForRPCRequest;
import static com.smartdevicelink.managers.screen.menu.MenuReplaceUtilities.removeMenuCellFromList;
import static com.smartdevicelink.managers.screen.menu.MenuReplaceUtilities.sendRPCs;
import static com.smartdevicelink.managers.screen.menu.MenuReplaceUtilities.subMenuCommandsForCells;

import com.livio.taskmaster.Task;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.file.MultipleFileCompletionListener;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.managers.screen.menu.DynamicMenuUpdateAlgorithm.MenuCellState;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.WindowCapability;
import com.smartdevicelink.proxy.rpc.enums.ImageFieldName;
import com.smartdevicelink.proxy.rpc.enums.MenuLayout;
import com.smartdevicelink.proxy.rpc.enums.TextFieldName;
import com.smartdevicelink.util.DebugTool;

import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Bilal Alsharifi on 1/20/21.
 */
class MenuReplaceOperation extends Task {
    private static final String TAG = "MenuReplaceOperation";

    private final WeakReference<ISdl> internalInterface;
    private final WeakReference<FileManager> fileManager;
    private WindowCapability windowCapability;
    private List<MenuCell> currentMenu;
    private final List<MenuCell> updatedMenu;
    private List<MenuCell> currentStrippedMenu;
    private List<MenuCell> updatedStrippedMenu;
    private final boolean isDynamicMenuUpdateActive;
    private final MenuManagerCompletionListener operationCompletionListener;
    private MenuConfiguration menuConfiguration;

    MenuReplaceOperation(ISdl internalInterface, FileManager fileManager, WindowCapability windowCapability, MenuConfiguration menuConfiguration, List<MenuCell> currentMenu, List<MenuCell> updatedMenu, boolean isDynamicMenuUpdateActive, MenuManagerCompletionListener operationCompletionListener) {
        super(TAG);
        this.internalInterface = new WeakReference<>(internalInterface);
        this.fileManager = new WeakReference<>(fileManager);
        this.windowCapability = windowCapability;
        this.menuConfiguration = menuConfiguration;
        this.currentMenu = currentMenu;
        this.updatedMenu = updatedMenu;
        this.isDynamicMenuUpdateActive = isDynamicMenuUpdateActive;
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
        this.updatedStrippedMenu = cellsWithRemovedPropertiesFromCells(updatedMenu, windowCapability);
        this.currentStrippedMenu = cellsWithRemovedPropertiesFromCells(currentMenu, windowCapability);

        // Check if head unit supports cells with duplicate titles
        SdlMsgVersion rpcVersion = internalInterface.get().getSdlMsgVersion();
        boolean supportsMenuUniqueness = rpcVersion.getMajorVersion() > 7 || (rpcVersion.getMajorVersion() == 7 && rpcVersion.getMinorVersion() > 0);

        addUniqueNamesToCells(updatedStrippedMenu, supportsMenuUniqueness);
        applyUniqueNamesOnCells(updatedStrippedMenu, updatedMenu);

        DynamicMenuUpdateRunScore runScore;
        if (!isDynamicMenuUpdateActive) {
            DebugTool.logInfo(TAG, "Dynamic menu update inactive. Forcing the deletion of all old cells and adding all new ones, even if they're the same.");
            runScore = DynamicMenuUpdateAlgorithm.compatibilityRunScoreWithOldMenuCells(currentMenu, updatedMenu);
        } else {
            DebugTool.logInfo(TAG, "Dynamic menu update active. Running the algorithm to find the best way to delete / add cells.");
            runScore = DynamicMenuUpdateAlgorithm.dynamicRunScoreOldMenuCells(currentMenu, updatedStrippedMenu);
        }

        // If both old and new menu cells are empty, nothing needs to be done.
        if (runScore.isEmpty()) {
            listener.onComplete(true);
            return;
        }

        List<MenuCellState> deleteMenuStatus = runScore.getOldStatus();
        List<MenuCellState> addMenuStatus = runScore.getUpdatedStatus();

        // Drop the cells into buckets based on the run score
        final List<MenuCell> cellsToDelete = filterMenuCellsWithStatusList(currentMenu, deleteMenuStatus, MenuCellState.DELETE);
        final List<MenuCell> cellsToAdd = filterMenuCellsWithStatusList(updatedMenu, addMenuStatus, MenuCellState.ADD);

        // These arrays should ONLY contain KEEPS. These will be used for SubMenu compares
        final List<MenuCell> oldKeeps = filterMenuCellsWithStatusList(currentMenu, deleteMenuStatus, MenuCellState.KEEP);
        final List<MenuCell> newKeeps = filterMenuCellsWithStatusList(updatedMenu, addMenuStatus, MenuCellState.KEEP);

        // Since we are creating a new menu but keeping old cells, we must first transfer the old cellIDs to the new menu kept cells.
        // This is needed for the onCommands to still work
        // We will transfer the ids for subCells later
        transferCellIDFromOldCells(oldKeeps, newKeeps);

        // Upload the Artworks, then we will start updating the main menu
        uploadMenuArtworks(new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                if (getState() == Task.CANCELED) {
                    return;
                }

                if (!success) {
                    listener.onComplete(false);
                    return;
                }

                updateMenuWithCellsToDelete(cellsToDelete, cellsToAdd, new CompletionListener() {
                    @Override
                    public void onComplete(boolean success) {
                        if (getState() == Task.CANCELED) {
                            return;
                        }

                        if (!success) {
                            listener.onComplete(false);
                            return;
                        }

                        updateSubMenuWithOldKeptCells(oldKeeps, newKeeps, 0, listener);
                    }
                });
            }
        });
    }

    private void uploadMenuArtworks(final CompletionListener listener) {
        List<SdlArtwork> artworksToBeUploaded = findAllArtworksToBeUploadedFromCells(updatedMenu, fileManager.get(), windowCapability);
        if (artworksToBeUploaded.isEmpty()) {
            listener.onComplete(true);
            return;
        }

        if (fileManager.get() == null) {
            listener.onComplete(false);
            return;
        }

        fileManager.get().uploadArtworks(artworksToBeUploaded, new MultipleFileCompletionListener() {
            @Override
            public void onComplete(Map<String, String> errors) {
                if (errors != null && !errors.isEmpty()) {
                    DebugTool.logError(TAG, "Error uploading Menu Artworks: " + errors.toString());
                    listener.onComplete(false);
                } else {
                    DebugTool.logInfo(TAG, "Menu artwork upload completed, beginning upload of main menu");
                    listener.onComplete(true);
                }
            }
        });
    }

    /**
     * Takes the main menu cells to delete and add, and deletes the current menu cells, then adds the new menu cells in the correct locations
     *
     * @param deleteCells The cells that need to be deleted
     * @param addCells    The cells that need to be added
     * @param listener    A CompletionListener called when complete
     */
    private void updateMenuWithCellsToDelete(List<MenuCell> deleteCells, final List<MenuCell> addCells, final CompletionListener listener) {
        sendDeleteCurrentMenu(deleteCells, new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                if (getState() == Task.CANCELED) {
                    return;
                }

                sendNewMenuCells(addCells, new CompletionListener() {
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

    /**
     * Takes the submenu cells that are old keeps and new keeps and determines which cells need to be deleted or added
     *
     * @param oldKeptCells The old kept cells
     * @param newKeptCells The new kept cells
     * @param startIndex   The index of the main menu to use
     * @param listener     A CompletionListener called when complete
     */
    private void updateSubMenuWithOldKeptCells(final List<MenuCell> oldKeptCells, final List<MenuCell> newKeptCells, final int startIndex, final CompletionListener listener) {
        if (getState() == Task.CANCELED) {
            return;
        }

        if (oldKeptCells.isEmpty() || startIndex >= oldKeptCells.size()) {
            listener.onComplete(true);
            return;
        }

        if (oldKeptCells.get(startIndex) != null && isSubMenuCell(oldKeptCells.get(startIndex)) && !oldKeptCells.get(startIndex).getSubCells().isEmpty()) {
            DynamicMenuUpdateRunScore tempScore = DynamicMenuUpdateAlgorithm.dynamicRunScoreOldMenuCells(oldKeptCells.get(startIndex).getSubCells(), newKeptCells.get(startIndex).getSubCells());

            // If both old and new menu cells are empty. Then nothing needs to be done.
            if (tempScore.isEmpty()) {
                // After the first set of submenu cells were added and deleted we must find the next set of sub cells until we loop through all the elements
                updateSubMenuWithOldKeptCells(oldKeptCells, newKeptCells, startIndex + 1, listener);
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
                    sendNewMenuCells(cellsToAdd, new CompletionListener() {
                        @Override
                        public void onComplete(boolean success) {
                            // After the first set of submenu cells were added and deleted we must find the next set of sub cells until we loop through all the elements
                            updateSubMenuWithOldKeptCells(oldKeptCells, newKeptCells, startIndex + 1, listener);
                        }
                    });
                }
            });
        } else {
            // After the first set of submenu cells were added and deleted we must find the next set of sub cells until we loop through all the elements
            updateSubMenuWithOldKeptCells(oldKeptCells, newKeptCells, startIndex + 1, listener);
        }
    }

    /**
     * Send Delete RPCs for given menu cells
     *
     * @param deleteMenuCells The menu cells to be deleted
     * @param listener        A CompletionListener called when the RPCs are finished with an error if any failed
     */
    private void sendDeleteCurrentMenu(List<MenuCell> deleteMenuCells, final CompletionListener listener) {
        if (deleteMenuCells == null || deleteMenuCells.isEmpty()) {
            listener.onComplete(true);
            return;
        }

        List<RPCRequest> deleteMenuCommands = deleteCommandsForCells(deleteMenuCells);
        sendRPCs(deleteMenuCommands, internalInterface.get(), new SendingRPCsCompletionListener() {
            @Override
            public void onComplete(boolean success, Map<RPCRequest, String> errors) {
                if (!success) {
                    DebugTool.logWarning(TAG, "Unable to delete all old menu commands. " + convertErrorsMapToString(errors));
                } else {
                    DebugTool.logInfo(TAG, "Finished deleting old menu");
                }
                listener.onComplete(success);
            }

            @Override
            public void onResponse(RPCRequest request, RPCResponse response) {
                if (response.getSuccess()) {
                    // Find the id of the successful request and remove it from the current menu list wherever it may have been
                    int commandId = commandIdForRPCRequest(request);
                    removeMenuCellFromList(currentMenu, commandId);
                }
            }
        });
    }

    /**
     * Send Add RPCs for given new menu cells compared to old menu cells
     *
     * @param newMenuCells The new menu cells we want displayed
     * @param listener     A CompletionListener called when the RPCs are finished with an error if any failed
     */
    private void sendNewMenuCells(final List<MenuCell> newMenuCells, final CompletionListener listener) {
        if (newMenuCells == null || newMenuCells.isEmpty()) {
            DebugTool.logInfo(TAG, "There are no cells to update.");
            listener.onComplete(true);
            return;
        }

        MenuLayout defaultSubmenuLayout = menuConfiguration != null ? menuConfiguration.getSubMenuLayout() : null;

        // RPCs for cells on the main menu level. They could be AddCommands or AddSubMenus depending on whether the cell has child cells or not.
        final List<RPCRequest> mainMenuCommands = mainMenuCommandsForCells(newMenuCells, fileManager.get(), windowCapability, updatedMenu, defaultSubmenuLayout);

        // RPCs for cells on the second menu level (one level deep). They could be AddCommands or AddSubMenus.
        final List<RPCRequest> subMenuCommands = subMenuCommandsForCells(newMenuCells, fileManager.get(), windowCapability, defaultSubmenuLayout);

        sendRPCs(mainMenuCommands, internalInterface.get(), new SendingRPCsCompletionListener() {
            @Override
            public void onComplete(boolean success, Map<RPCRequest, String> errors) {
                if (!success) {
                    DebugTool.logError(TAG, "Failed to send main menu commands. " + convertErrorsMapToString(errors));
                    listener.onComplete(false);
                    return;
                }

                if (getState() == Task.CANCELED) {
                    return;
                }

                sendRPCs(subMenuCommands, internalInterface.get(), new SendingRPCsCompletionListener() {
                    @Override
                    public void onComplete(boolean success, Map<RPCRequest, String> errors) {
                        if (!success) {
                            DebugTool.logError(TAG, "Failed to send sub menu commands. " + convertErrorsMapToString(errors));
                        } else {
                            DebugTool.logInfo(TAG, "Finished updating menu");
                        }
                        listener.onComplete(success);
                    }

                    @Override
                    public void onResponse(RPCRequest request, RPCResponse response) {
                        if (response.getSuccess()) {
                            // Find the id of the successful request and add it from the current menu list wherever it needs to be
                            int commandId = commandIdForRPCRequest(request);
                            int position = positionForRPCRequest(request);
                            addMenuRequestWithCommandId(commandId, position, newMenuCells, currentMenu);
                        }
                    }
                });
            }

            @Override
            public void onResponse(RPCRequest request, RPCResponse response) {
                if (response.getSuccess()) {
                    // Find the id of the successful request and add it from the current menu list wherever it needs to be
                    int commandId = commandIdForRPCRequest(request);
                    int position = positionForRPCRequest(request);
                    addMenuRequestWithCommandId(commandId, position, newMenuCells, currentMenu);
                }
            }
        });
    }

    private List<MenuCell> filterMenuCellsWithStatusList(List<MenuCell> menuCells, List<MenuCellState> statusList, MenuCellState menuCellState) {
        List<MenuCell> filteredCells = new ArrayList<>();
        for (int index = 0; index < statusList.size(); index++) {
            if (statusList.get(index).equals(menuCellState)) {
                filteredCells.add(menuCells.get(index));
            }
        }
        return filteredCells;
    }

    private void transferCellIDFromOldCells(List<MenuCell> oldCells, List<MenuCell> newCells) {
        if (oldCells == null || oldCells.isEmpty()) {
            return;
        }
        for (int i = 0; i < newCells.size(); i++) {
            newCells.get(i).setCellId(oldCells.get(i).getCellId());
        }
    }

    private String convertErrorsMapToString(Map<RPCRequest, String> errors) {
        if (errors == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (RPCRequest request : errors.keySet()) {
            stringBuilder.append(errors.get(request));
            try {
                stringBuilder.append(request.serializeJSON().toString(4));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }

    List<MenuCell> cellsWithRemovedPropertiesFromCells(List<MenuCell> cells, WindowCapability windowCapability) {
        if (cells == null) {
            return null;
        }

        List<MenuCell> removePropertiesClone = cloneMenuCellsList(cells);

        for (MenuCell cell : removePropertiesClone) {
            // Strip away fields that cannot be used to determine uniqueness visually including fields not supported by the HMI
            cell.setVoiceCommands(null);

            // Don't check ImageFieldName.subMenuIcon because it was added in 7.0 when the feature was added in 5.0.
            // Just assume that if cmdIcon is not available, the submenu icon is not either.
            if (!hasImageFieldOfName(windowCapability, ImageFieldName.cmdIcon)) {
                cell.setIcon(null);
            }

            // Check for subMenu fields supported
            if (isSubMenuCell(cell)) {
                if (!hasTextFieldOfName(windowCapability, TextFieldName.menuSubMenuSecondaryText)) {
                    cell.setSecondaryText(null);
                }
                if (!hasTextFieldOfName(windowCapability, TextFieldName.menuSubMenuTertiaryText)) {
                    cell.setTertiaryText(null);
                }
                if (!hasImageFieldOfName(windowCapability, ImageFieldName.menuSubMenuSecondaryImage)) {
                    cell.setSecondaryArtwork(null);
                }
                cell.setSubCells(cellsWithRemovedPropertiesFromCells(cell.getSubCells(), windowCapability));
            } else {
                if (!hasTextFieldOfName(windowCapability, TextFieldName.menuCommandSecondaryText)) {
                    cell.setSecondaryText(null);
                }
                if (!hasTextFieldOfName(windowCapability, TextFieldName.menuCommandTertiaryText)) {
                    cell.setTertiaryText(null);
                }
                if (!hasImageFieldOfName(windowCapability, ImageFieldName.menuCommandSecondaryImage)) {
                    cell.setSecondaryArtwork(null);
                }
            }
        }
        return removePropertiesClone;
    }

    private void addUniqueNamesToCells(List<MenuCell> menuCells, boolean supportsMenuUniqueness) {
        if (menuCells == null) {
            return;
        }

        // Tracks how many of each cell primary text there are so that we can append numbers to make each unique as necessary
        HashMap<String, Integer> dictCounter = new HashMap<>();

        for (MenuCell cell : menuCells) {
            String key = supportsMenuUniqueness ? String.valueOf(cell.hashCode()) : cell.getTitle();
            Integer counter = dictCounter.get(key);

            if (counter != null) {
                dictCounter.put(key, ++counter);
            } else {
                dictCounter.put(key, 1);
            }

            counter = dictCounter.get(key);
            if (counter != null && counter > 1) {
                cell.setUniqueTitle(cell.getTitle()  + " (" + counter + ")");
            }

            if (isSubMenuCell(cell) && !cell.getSubCells().isEmpty()) {
                addUniqueNamesToCells(cell.getSubCells(), supportsMenuUniqueness);
            }
        }
    }

    private void applyUniqueNamesOnCells(List<MenuCell> fromMenuCells, List<MenuCell> toMenuCells) {
        if (fromMenuCells.size() != toMenuCells.size()) {
            return;
        }

        for (int i = 0; i < fromMenuCells.size(); i++) {
            toMenuCells.get(i).setUniqueTitle(fromMenuCells.get(i).getUniqueTitle());
            if (isSubMenuCell(fromMenuCells.get(i)) && !fromMenuCells.get(i).getSubCells().isEmpty()) {
                applyUniqueNamesOnCells(fromMenuCells.get(i).getSubCells(), toMenuCells.get(i).getSubCells());
            }
        }
    }

    void setMenuConfiguration(MenuConfiguration menuConfiguration) {
        this.menuConfiguration = menuConfiguration;
    }

    void setCurrentMenu(List<MenuCell> currentMenuCells) {
        this.currentMenu = currentMenuCells;
    }

    void setWindowCapability(WindowCapability windowCapability) {
        this.windowCapability = windowCapability;
    }

    private void finishOperation(boolean success) {
        if (operationCompletionListener != null) {
            operationCompletionListener.onComplete(success, currentMenu);
        }
        onFinished();
    }
}
