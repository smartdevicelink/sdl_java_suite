package com.smartdevicelink.managers.screen.menu;

import com.livio.taskmaster.Task;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.file.MultipleFileCompletionListener;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.WindowCapability;
import com.smartdevicelink.proxy.rpc.enums.MenuLayout;
import com.smartdevicelink.proxy.rpc.listeners.OnMultipleRequestListener;
import com.smartdevicelink.util.DebugTool;

import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

import static com.smartdevicelink.managers.screen.menu.BaseMenuManager.lastMenuId;
import static com.smartdevicelink.managers.screen.menu.BaseMenuManager.parentIdNotFound;
import static com.smartdevicelink.managers.screen.menu.MenuReplaceUtilities.deleteCommandsForCells;
import static com.smartdevicelink.managers.screen.menu.MenuReplaceUtilities.findAllArtworksToBeUploadedFromCells;
import static com.smartdevicelink.managers.screen.menu.MenuReplaceUtilities.mainMenuCommandsForCells;
import static com.smartdevicelink.managers.screen.menu.MenuReplaceUtilities.shouldRPCsIncludeImages;
import static com.smartdevicelink.managers.screen.menu.MenuReplaceUtilities.subMenuCommandsForCells;
import static com.smartdevicelink.managers.screen.menu.MenuReplaceUtilities.supportsImages;

/**
 * Created by Bilal Alsharifi on 1/20/21.
 */
class MenuReplaceStaticOperation extends Task {
    private static final String TAG = "MenuReplaceStaticOperation";

    private final WeakReference<ISdl> internalInterface;
    private final WeakReference<FileManager> fileManager;
    private final WindowCapability defaultMainWindowCapability;
    private List<MenuCell> currentMenu;
    private final List<MenuCell> updatedMenu;
    private final MenuManagerCompletionListener operationCompletionListener;
    private MenuConfiguration menuConfiguration;

    MenuReplaceStaticOperation(ISdl internalInterface, FileManager fileManager, MenuConfiguration menuConfiguration, WindowCapability defaultMainWindowCapability, List<MenuCell> currentMenu, List<MenuCell> updatedMenu, MenuManagerCompletionListener operationCompletionListener) {
        super(TAG);
        this.internalInterface = new WeakReference<>(internalInterface);
        this.fileManager = new WeakReference<>(fileManager);
        this.menuConfiguration = menuConfiguration;
        this.defaultMainWindowCapability = defaultMainWindowCapability;
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
        // Upload the Artworks
        List<SdlArtwork> artworksToBeUploaded = findAllArtworksToBeUploadedFromCells(updatedMenu, fileManager.get(), defaultMainWindowCapability);
        if (!artworksToBeUploaded.isEmpty() && fileManager.get() != null) {
            fileManager.get().uploadArtworks(artworksToBeUploaded, new MultipleFileCompletionListener() {
                @Override
                public void onComplete(Map<String, String> errors) {
                    if (errors != null && !errors.isEmpty()) {
                        DebugTool.logError(TAG, "Error uploading Menu Artworks: " + errors.toString());
                    } else {
                        DebugTool.logInfo(TAG, "Menu Artworks Uploaded");
                    }
                    // proceed
                    updateMenuAndDetermineBestUpdateMethod(listener);
                }
            });
        } else {
            // No Artworks to be uploaded, send off
            updateMenuAndDetermineBestUpdateMethod(listener);
        }
    }

    private void updateMenuAndDetermineBestUpdateMethod(CompletionListener listener) {
        if (getState() == Task.CANCELED) {
            return;
        }

        updateIdsOnMenuCells(updatedMenu, parentIdNotFound);
        createAndSendEntireMenu(listener);
    }

    private void createAndSendEntireMenu(final CompletionListener listener) {
        if (getState() == Task.CANCELED) {
            return;
        }

        deleteRootMenu(new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                createAndSendMenuCellRPCs(updatedMenu, new CompletionListener() {
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

    private void createAndSendMenuCellRPCs(final List<MenuCell> menu, final CompletionListener listener) {
        if (getState() == Task.CANCELED) {
            return;
        }

        if (menu == null || menu.isEmpty()) {
            if (listener != null) {
                // This can be considered a success if the user was clearing out their menu
                listener.onComplete(true);
            }
            return;
        }

        List<RPCRequest> mainMenuCommands;
        final List<RPCRequest> subMenuCommands;
        List<MenuLayout> availableMenuLayouts = defaultMainWindowCapability != null ? defaultMainWindowCapability.getMenuLayoutsAvailable() : null;
        MenuLayout defaultSubmenuLayout = menuConfiguration != null ? menuConfiguration.getSubMenuLayout() : null;

        if (!shouldRPCsIncludeImages(menu, fileManager.get()) || !supportsImages(defaultMainWindowCapability)) {
            // Send artwork-less menu
            mainMenuCommands = mainMenuCommandsForCells(menu, false, updatedMenu, availableMenuLayouts, defaultSubmenuLayout);
            subMenuCommands = subMenuCommandsForCells(menu, false, availableMenuLayouts, defaultSubmenuLayout);
        } else {
            mainMenuCommands = mainMenuCommandsForCells(menu, true, updatedMenu, availableMenuLayouts, defaultSubmenuLayout);
            subMenuCommands = subMenuCommandsForCells(menu, true, availableMenuLayouts, defaultSubmenuLayout);
        }

        internalInterface.get().sendSequentialRPCs(mainMenuCommands, new OnMultipleRequestListener() {
            @Override
            public void onUpdate(int remainingRequests) {
            }

            @Override
            public void onFinished() {
                if (!subMenuCommands.isEmpty()) {
                    DebugTool.logInfo(TAG, "Finished sending main menu commands. Sending sub menu commands.");
                    sendSubMenuCommandRPCs(subMenuCommands, listener);
                } else {
                    DebugTool.logInfo(TAG, "Finished sending main menu commands.");

                    if (listener != null) {
                        listener.onComplete(true);
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

    private void sendSubMenuCommandRPCs(List<RPCRequest> commands, final CompletionListener listener) {
        if (getState() == Task.CANCELED) {
            return;
        }

        internalInterface.get().sendSequentialRPCs(commands, new OnMultipleRequestListener() {
            @Override
            public void onUpdate(int remainingRequests) {
            }

            @Override
            public void onFinished() {
                DebugTool.logInfo(TAG, "Finished Updating Menu");

                if (listener != null) {
                    listener.onComplete(true);
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

    private void deleteRootMenu(final CompletionListener listener) {
        if (currentMenu == null || currentMenu.isEmpty()) {
            if (listener != null) {
                // technically this method is successful if there's nothing to delete
                DebugTool.logInfo(TAG, "No old cells to delete, returning");
                listener.onComplete(true);
            }
        } else {
            sendDeleteRPCs(deleteCommandsForCells(currentMenu), listener);
        }
    }

    private void sendDeleteRPCs(List<RPCRequest> deleteCommands, final CompletionListener listener) {
        if (getState() == Task.CANCELED) {
            return;
        }

        if (deleteCommands == null || deleteCommands.isEmpty()) {
            // no dynamic deletes required. return
            if (listener != null) {
                // technically this method is successful if there's nothing to delete
                listener.onComplete(true);
            }
            return;
        }

        internalInterface.get().sendRPCs(deleteCommands, new OnMultipleRequestListener() {
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

    private void finishOperation(boolean success) {
        if (operationCompletionListener != null) {
            operationCompletionListener.onComplete(success, currentMenu);
        }
        onFinished();
    }
}
