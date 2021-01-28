package com.smartdevicelink.managers.screen.menu;

import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.managers.ManagerUtility;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.AddCommand;
import com.smartdevicelink.proxy.rpc.AddSubMenu;
import com.smartdevicelink.proxy.rpc.DeleteCommand;
import com.smartdevicelink.proxy.rpc.DeleteSubMenu;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.MenuParams;
import com.smartdevicelink.proxy.rpc.WindowCapability;
import com.smartdevicelink.proxy.rpc.enums.ImageFieldName;
import com.smartdevicelink.proxy.rpc.enums.MenuLayout;
import com.smartdevicelink.proxy.rpc.listeners.OnMultipleRequestListener;
import com.smartdevicelink.util.DebugTool;

import java.util.ArrayList;
import java.util.List;

import static com.smartdevicelink.managers.screen.menu.BaseMenuManager.parentIdNotFound;

/**
 * Created by Bilal Alsharifi on 1/25/21.
 */
class MenuReplaceUtilities {
    private static final String TAG = "MenuReplaceUtilities";

    static int commandIdForRPCRequest(RPCRequest request) {
        int commandId = 0;
        if (request instanceof AddCommand) {
            commandId = ((AddCommand) request).getCmdID();
        } else if (request instanceof AddSubMenu) {
            commandId = ((AddSubMenu) request).getMenuID();
        } else if (request instanceof DeleteCommand) {
            commandId = ((DeleteCommand) request).getCmdID();
        } else if (request instanceof DeleteSubMenu) {
            commandId = ((DeleteSubMenu) request).getMenuID();
        }
        return commandId;
    }

    static List<RPCRequest> deleteCommandsForCells(List<MenuCell> cells) {
        List<RPCRequest> deletes = new ArrayList<>();
        for (MenuCell cell : cells) {
            if (cell.getSubCells() == null) {
                DeleteCommand delete = new DeleteCommand(cell.getCellId());
                deletes.add(delete);
            } else {
                DeleteSubMenu delete = new DeleteSubMenu(cell.getCellId());
                deletes.add(delete);
            }
        }
        return deletes;
    }

    static List<SdlArtwork> findAllArtworksToBeUploadedFromCells(List<MenuCell> cells, FileManager fileManager, WindowCapability windowCapability) {
        // Make sure we can use images in the menus
        if (!ManagerUtility.WindowCapabilityUtility.hasImageFieldOfName(windowCapability, ImageFieldName.cmdIcon)) {
            return new ArrayList<>();
        }

        List<SdlArtwork> artworks = new ArrayList<>();
        for (MenuCell cell : cells) {
            if (fileManager != null && fileManager.fileNeedsUpload(cell.getIcon())) {
                artworks.add(cell.getIcon());
            }
            if (cell.getSubCells() != null && !cell.getSubCells().isEmpty()) {
                artworks.addAll(findAllArtworksToBeUploadedFromCells(cell.getSubCells(), fileManager, windowCapability));
            }
        }

        return artworks;
    }

    static boolean shouldCellIncludeImage(MenuCell cell, FileManager fileManager, WindowCapability windowCapability) {
        // If there is an icon and the icon has been uploaded, or if the icon is a static icon, it should include the image
        boolean supportsImage = cell.getSubCells() != null && !cell.getSubCells().isEmpty() ? ManagerUtility.WindowCapabilityUtility.hasImageFieldOfName(windowCapability, ImageFieldName.subMenuIcon) : ManagerUtility.WindowCapabilityUtility.hasImageFieldOfName(windowCapability, ImageFieldName.cmdIcon);
        if (cell.getIcon() == null || !supportsImage) {
            return false;
        }
        return (fileManager.hasUploadedFile(cell.getIcon()) || cell.getIcon().isStaticIcon());
    }

    static List<RPCRequest> mainMenuCommandsForCells(List<MenuCell> cellsToAdd, FileManager fileManager, WindowCapability windowCapability, List<MenuCell> updatedMenu, MenuLayout defaultSubmenuLayout) {
        List<RPCRequest> builtCommands = new ArrayList<>();

        // We need the index so we will use this type of loop
        for (int z = 0; z < updatedMenu.size(); z++) {
            MenuCell mainCell = updatedMenu.get(z);
            for (int i = 0; i < cellsToAdd.size(); i++) {
                MenuCell addCell = cellsToAdd.get(i);
                if (mainCell.equals(addCell)) {
                    if (addCell.getSubCells() != null && !addCell.getSubCells().isEmpty()) {
                        builtCommands.add(subMenuCommandForMenuCell(addCell, fileManager, windowCapability, z, defaultSubmenuLayout));
                    } else {
                        builtCommands.add(commandForMenuCell(addCell, fileManager, windowCapability, z));
                    }
                    break;
                }
            }
        }
        return builtCommands;
    }

    static List<RPCRequest> subMenuCommandsForCells(List<MenuCell> cells, FileManager fileManager, WindowCapability windowCapability, MenuLayout defaultSubmenuLayout) {
        List<RPCRequest> builtCommands = new ArrayList<>();
        for (MenuCell cell : cells) {
            if (cell.getSubCells() != null && !cell.getSubCells().isEmpty()) {
                builtCommands.addAll(allCommandsForCells(cell.getSubCells(), fileManager, windowCapability, defaultSubmenuLayout));
            }
        }
        return builtCommands;
    }

    static List<RPCRequest> allCommandsForCells(List<MenuCell> cells, FileManager fileManager, WindowCapability windowCapability, MenuLayout defaultSubmenuLayout) {
        List<RPCRequest> builtCommands = new ArrayList<>();

        for (int i = 0; i < cells.size(); i++) {
            MenuCell cell = cells.get(i);
            if (cell.getSubCells() != null && !cell.getSubCells().isEmpty()) {
                builtCommands.add(subMenuCommandForMenuCell(cell, fileManager, windowCapability, i, defaultSubmenuLayout));

                // recursively grab the commands for all the sub cells
                builtCommands.addAll(allCommandsForCells(cell.getSubCells(), fileManager, windowCapability, defaultSubmenuLayout));
            } else {
                builtCommands.add(commandForMenuCell(cell, fileManager, windowCapability, i));
            }
        }
        return builtCommands;
    }

    static AddCommand commandForMenuCell(MenuCell cell, FileManager fileManager, WindowCapability windowCapability, int position) {
        AddCommand command = new AddCommand(cell.getCellId());

        MenuParams params = new MenuParams(cell.getTitle());
        params.setParentID(cell.getParentCellId() != parentIdNotFound ? cell.getParentCellId() : null);
        params.setPosition(position);

        command.setMenuParams(params);
        if (cell.getVoiceCommands() != null && !cell.getVoiceCommands().isEmpty()) {
            command.setVrCommands(cell.getVoiceCommands());
        } else {
            command.setVrCommands(null);
        }
        boolean shouldCellIncludeImage = cell.getIcon() != null && shouldCellIncludeImage(cell, fileManager, windowCapability);
        command.setCmdIcon(shouldCellIncludeImage ? cell.getIcon().getImageRPC() : null);

        return command;
    }

    static AddSubMenu subMenuCommandForMenuCell(MenuCell cell, FileManager fileManager, WindowCapability windowCapability, int position, MenuLayout defaultSubmenuLayout) {
        boolean shouldCellIncludeImage = cell.getIcon() != null && cell.getIcon().getImageRPC() != null && shouldCellIncludeImage(cell, fileManager, windowCapability);
        Image icon = (shouldCellIncludeImage ? cell.getIcon().getImageRPC() : null);

        MenuLayout submenuLayout;
        List<MenuLayout> availableMenuLayouts = windowCapability != null ? windowCapability.getMenuLayoutsAvailable() : null;
        if (cell.getSubMenuLayout() != null && availableMenuLayouts != null && availableMenuLayouts.contains(cell.getSubMenuLayout())) {
            submenuLayout = cell.getSubMenuLayout();
        } else {
            submenuLayout = defaultSubmenuLayout;
        }

        return new AddSubMenu(cell.getCellId(), cell.getTitle())
                .setPosition(position)
                .setMenuLayout(submenuLayout)
                .setMenuIcon(icon);
    }

    static List<MenuCell> removeMenuCellFromCurrentMainMenuList(List<MenuCell> menuCellList, int commandId) {
        for (MenuCell menuCell : menuCellList) {
            if (menuCell.getCellId() == commandId) {
                menuCellList.remove(menuCell);
                return menuCellList;
            } else if (menuCell.getSubCells() != null && !menuCell.getSubCells().isEmpty()) {
                List<MenuCell> newList = removeMenuCellFromCurrentMainMenuList(menuCell.getSubCells(), commandId);
                if (newList != null) {
                    menuCell.setSubCells(newList);
                }
            }
        }
        return null;
    }

    static void sendRPCs(List<RPCRequest> requests, ISdl internalInterface, final CompletionListener listener) {
        if (requests == null || requests.isEmpty()) {
            listener.onComplete(true);
        }

        internalInterface.sendRPCs(requests, new OnMultipleRequestListener() {
            @Override
            public void onUpdate(int remainingRequests) {
            }

            @Override
            public void onFinished() {
                // todo should we pass false if one failed?
                listener.onComplete(true);
            }

            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                if (!response.getSuccess()) {
                    DebugTool.logError(TAG, "Failed to send RPC. Result: " + response.getResultCode() + " Info: " + response.getInfo());
                }
            }
        });
    }
}
