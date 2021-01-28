package com.smartdevicelink.managers.screen.menu;

import com.livio.taskmaster.Task;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.file.MultipleFileCompletionListener;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.WindowCapability;
import com.smartdevicelink.proxy.rpc.enums.MenuLayout;
import com.smartdevicelink.util.DebugTool;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

import static com.smartdevicelink.managers.screen.menu.BaseMenuManager.lastMenuId;
import static com.smartdevicelink.managers.screen.menu.BaseMenuManager.parentIdNotFound;
import static com.smartdevicelink.managers.screen.menu.MenuReplaceUtilities.deleteCommandsForCells;
import static com.smartdevicelink.managers.screen.menu.MenuReplaceUtilities.findAllArtworksToBeUploadedFromCells;
import static com.smartdevicelink.managers.screen.menu.MenuReplaceUtilities.mainMenuCommandsForCells;
import static com.smartdevicelink.managers.screen.menu.MenuReplaceUtilities.sendRPCs;
import static com.smartdevicelink.managers.screen.menu.MenuReplaceUtilities.subMenuCommandsForCells;

/**
 * Created by Bilal Alsharifi on 1/20/21.
 */
class MenuReplaceStaticOperation extends Task {
    private static final String TAG = "MenuReplaceStaticOperation";

    private final WeakReference<ISdl> internalInterface;
    private final WeakReference<FileManager> fileManager;
    private final WindowCapability windowCapability;
    private List<MenuCell> currentMenu;
    private final List<MenuCell> updatedMenu;
    private final MenuManagerCompletionListener operationCompletionListener;
    private MenuConfiguration menuConfiguration;

    MenuReplaceStaticOperation(ISdl internalInterface, FileManager fileManager, WindowCapability windowCapability, MenuConfiguration menuConfiguration, List<MenuCell> currentMenu, List<MenuCell> updatedMenu, MenuManagerCompletionListener operationCompletionListener) {
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
        List<SdlArtwork> artworksToBeUploaded = findAllArtworksToBeUploadedFromCells(updatedMenu, fileManager.get(), windowCapability);
        if (!artworksToBeUploaded.isEmpty() && fileManager.get() != null) {
            fileManager.get().uploadArtworks(artworksToBeUploaded, new MultipleFileCompletionListener() {
                @Override
                public void onComplete(Map<String, String> errors) {
                    if (getState() == Task.CANCELED) {
                        return;
                    }

                    if (errors != null && !errors.isEmpty()) {
                        DebugTool.logError(TAG, "Error uploading menu artworks: " + errors.toString());
                    } else {
                        DebugTool.logInfo(TAG, "All menu artworks uploaded");
                    }

                    updateMenuWithCellsToDelete(currentMenu, updatedMenu, listener);
                }
            });
        } else {
            // Cells have no artwork to load
            updateMenuWithCellsToDelete(currentMenu, updatedMenu, listener);
        }
    }

    private void updateMenuWithCellsToDelete(final List<MenuCell> deleteCells, final List<MenuCell> addCells, final CompletionListener listener) {
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

    private void sendDeleteCurrentMenu(List<MenuCell> deleteMenuCells, final CompletionListener listener) {
        if (deleteMenuCells == null || deleteMenuCells.isEmpty()) {
            // Technically this method is successful if there's nothing to delete
            DebugTool.logInfo(TAG, "No old cells to delete, returning");
            listener.onComplete(true);
            return;
        }
        List<RPCRequest> deleteMenuCommands = deleteCommandsForCells(deleteMenuCells);

        if (deleteMenuCommands.isEmpty()) {
            // Technically this method is successful if there's nothing to delete
            listener.onComplete(true);
            return;
        }

        sendRPCs(deleteMenuCommands, internalInterface.get(), new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                DebugTool.logInfo(TAG, "Successfully deleted cells");
                listener.onComplete(success);
            }
        });
    }

    private void sendNewMenuCells(final List<MenuCell> newMenuCells, final CompletionListener listener) {
        if (newMenuCells == null || newMenuCells.isEmpty()) {
            // This can be considered a success if the user was clearing out their menu
            DebugTool.logInfo(TAG, "There are no cells to update.");
            listener.onComplete(true);
            return;
        }

        updateIdsOnMenuCells(newMenuCells, parentIdNotFound);

        MenuLayout defaultSubmenuLayout = menuConfiguration != null ? menuConfiguration.getSubMenuLayout() : null;

        List<RPCRequest> mainMenuCommands = mainMenuCommandsForCells(newMenuCells, fileManager.get(), windowCapability, updatedMenu, defaultSubmenuLayout);
        final List<RPCRequest> subMenuCommands = subMenuCommandsForCells(newMenuCells, fileManager.get(), windowCapability, defaultSubmenuLayout);

        sendRPCs(mainMenuCommands, internalInterface.get(), new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                DebugTool.logInfo(TAG, "Finished sending main menu commands. Sending sub menu commands.");

                sendRPCs(subMenuCommands, internalInterface.get(), new CompletionListener() {
                    @Override
                    public void onComplete(boolean success) {
                        DebugTool.logInfo(TAG, "Finished Updating Menu");
                        listener.onComplete(success);
                    }
                });
            }
        });
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
