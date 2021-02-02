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
import com.smartdevicelink.util.DebugTool;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.smartdevicelink.managers.screen.menu.DynamicMenuUpdateAlgorithm.buildAllAddStatusesForMenu;
import static com.smartdevicelink.managers.screen.menu.DynamicMenuUpdateAlgorithm.buildAllDeleteStatusesForMenu;
import static com.smartdevicelink.managers.screen.menu.MenuReplaceUtilities.addMenuRequestWithCommandId;
import static com.smartdevicelink.managers.screen.menu.MenuReplaceUtilities.commandIdForRPCRequest;
import static com.smartdevicelink.managers.screen.menu.MenuReplaceUtilities.deleteCommandsForCells;
import static com.smartdevicelink.managers.screen.menu.MenuReplaceUtilities.findAllArtworksToBeUploadedFromCells;
import static com.smartdevicelink.managers.screen.menu.MenuReplaceUtilities.mainMenuCommandsForCells;
import static com.smartdevicelink.managers.screen.menu.MenuReplaceUtilities.positionForRPCRequest;
import static com.smartdevicelink.managers.screen.menu.MenuReplaceUtilities.removeMenuCellFromList;
import static com.smartdevicelink.managers.screen.menu.MenuReplaceUtilities.sendRPCs;
import static com.smartdevicelink.managers.screen.menu.MenuReplaceUtilities.subMenuCommandsForCells;

/**
 * Created by Bilal Alsharifi on 1/20/21.
 */
class MenuReplaceOperation extends Task {
    private static final String TAG = "MenuReplaceOperation";

    private final WeakReference<ISdl> internalInterface;
    private final WeakReference<FileManager> fileManager;
    private final WindowCapability windowCapability;
    private List<MenuCell> currentMenu;
    private final List<MenuCell> updatedMenu;
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
        if (getState() == Task.CANCELED) {
            return;
        }

        DynamicMenuUpdateRunScore runScore;

        if (isDynamicMenuUpdateActive) {
            DebugTool.logInfo(TAG, "Dynamic menu update is active. Running the algorithm to find the best run score");
            runScore = DynamicMenuUpdateAlgorithm.compareOldMenuCells(currentMenu, updatedMenu);
        } else {
            DebugTool.logInfo(TAG, "Dynamic menu update is not active. Forcing the deletion of all old cells and adding new ones");
            runScore = new DynamicMenuUpdateRunScore(buildAllDeleteStatusesForMenu(currentMenu), buildAllAddStatusesForMenu(updatedMenu), updatedMenu.size());
        }

        // If both old and new menu cells are empty. Then nothing needs to be done.
        if (runScore == null) {
            listener.onComplete(true);
            return;
        }

        List<MenuCellState> deleteMenuStatus = runScore.getOldStatus();
        List<MenuCellState> addMenuStatus = runScore.getUpdatedStatus();

        final List<MenuCell> cellsToDelete = filterMenuCellsWithStatusList(currentMenu, deleteMenuStatus, MenuCellState.DELETE);
        final List<MenuCell> cellsToAdd = filterMenuCellsWithStatusList(updatedMenu, addMenuStatus, MenuCellState.ADD);

        // These arrays should ONLY contain KEEPS. These will be used for SubMenu compares
        final List<MenuCell> oldKeeps = filterMenuCellsWithStatusList(currentMenu, deleteMenuStatus, MenuCellState.KEEP);
        final List<MenuCell> newKeeps = filterMenuCellsWithStatusList(updatedMenu, addMenuStatus, MenuCellState.KEEP);

        // Since we are creating a new Menu but keeping old cells we must first transfer the old cellIDs to the new menus kept cells.
        // This is needed for the onCommands to still work
        // We will transfer the ids for subCells later
        transferCellIDFromOldCells(oldKeeps, newKeeps);

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

    private void sendDeleteCurrentMenu(List<MenuCell> deleteMenuCells, final CompletionListener listener) {
        if (getState() == Task.CANCELED) {
            return;
        }

        if (deleteMenuCells.isEmpty()) {
            listener.onComplete(true);
            return;
        }

        List<RPCRequest> deleteMenuCommands = deleteCommandsForCells(deleteMenuCells);
        sendRPCs(deleteMenuCommands, internalInterface.get(), new SendingRPCsCompletionListener() {
            @Override
            public void onComplete(boolean success, Map<RPCRequest, String> errors) {
                if (!success) {
                    DebugTool.logWarning(TAG, "Unable to delete all old menu commands" + errors);
                } else {
                    DebugTool.logInfo(TAG, "Finished deleting old menu");
                }
                listener.onComplete(success);
            }

            @Override
            public void onResponse(RPCRequest request, RPCResponse response) {
                // Find the id of the successful request and remove it from the current menu list wherever it may have been
                int commandId = commandIdForRPCRequest(request);
                removeMenuCellFromList(currentMenu, commandId);
            }
        });
    }

    private void sendNewMenuCells(final List<MenuCell> newMenuCells, final CompletionListener listener) {
        if (getState() == Task.CANCELED) {
            return;
        }

        if (newMenuCells == null || newMenuCells.isEmpty()) {
            DebugTool.logInfo(TAG, "There are no cells to update.");
            listener.onComplete(true);
            return;
        }

        MenuLayout defaultSubmenuLayout = menuConfiguration != null ? menuConfiguration.getSubMenuLayout() : null;

        final List<RPCRequest> mainMenuCommands = mainMenuCommandsForCells(newMenuCells, fileManager.get(), windowCapability, updatedMenu, defaultSubmenuLayout);
        final List<RPCRequest> subMenuCommands = subMenuCommandsForCells(newMenuCells, fileManager.get(), windowCapability, defaultSubmenuLayout);

        sendRPCs(mainMenuCommands, internalInterface.get(), new SendingRPCsCompletionListener() {
            @Override
            public void onComplete(boolean success, Map<RPCRequest, String> errors) {
                if (!success) {
                    DebugTool.logError(TAG, "Failed to send main menu commands" + errors);
                    listener.onComplete(false);
                    return;
                }
                sendRPCs(subMenuCommands, internalInterface.get(), new SendingRPCsCompletionListener() {
                    @Override
                    public void onComplete(boolean success, Map<RPCRequest, String> errors) {
                        if (!success) {
                            DebugTool.logError(TAG, "Failed to send sub menu commands" + errors);
                        } else {
                            DebugTool.logInfo(TAG, "Finished updating menu");
                        }
                        listener.onComplete(success);
                    }

                    @Override
                    public void onResponse(RPCRequest request, RPCResponse response) {
                        // Find the id of the successful request and add it from the current menu list wherever it needs to be
                        int commandId = commandIdForRPCRequest(request);
                        int position = positionForRPCRequest(request);
                        addMenuRequestWithCommandId(commandId, position, newMenuCells, currentMenu);
                    }
                });
            }

            @Override
            public void onResponse(RPCRequest request, RPCResponse response) {
                // Find the id of the successful request and add it from the current menu list wherever it needs to be
                int commandId = commandIdForRPCRequest(request);
                int position = positionForRPCRequest(request);
                addMenuRequestWithCommandId(commandId, position, newMenuCells, currentMenu);
            }
        });
    }

    private void startSubMenuUpdatesWithOldKeptCells(final List<MenuCell> oldKeptCells, final List<MenuCell> newKeptCells, final int startIndex, final CompletionListener listener) {
        if (oldKeptCells.isEmpty() || startIndex >= oldKeptCells.size()) {
            listener.onComplete(true);
            return;
        }

        if (oldKeptCells.get(startIndex) != null && oldKeptCells.get(startIndex).getSubCells() != null && !oldKeptCells.get(startIndex).getSubCells().isEmpty()) {
            DynamicMenuUpdateRunScore tempScore = DynamicMenuUpdateAlgorithm.compareOldMenuCells(oldKeptCells.get(startIndex).getSubCells(), newKeptCells.get(startIndex).getSubCells());

            // If both old and new menu cells are empty. Then nothing needs to be done.
            if (tempScore == null) {
                // After the first set of submenu cells were added and deleted we must find the next set of sub cells until we loop through all the elements
                startSubMenuUpdatesWithOldKeptCells(oldKeptCells, newKeptCells, startIndex + 1, listener);
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
        if (oldCells == null || oldCells.isEmpty()) {
            return;
        }
        for (int i = 0; i < newCells.size(); i++) {
            newCells.get(i).setCellId(oldCells.get(i).getCellId());
        }
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