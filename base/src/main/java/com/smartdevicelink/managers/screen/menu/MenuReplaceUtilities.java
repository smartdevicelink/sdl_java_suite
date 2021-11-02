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

import static com.smartdevicelink.managers.ManagerUtility.WindowCapabilityUtility.hasImageFieldOfName;
import static com.smartdevicelink.managers.ManagerUtility.WindowCapabilityUtility.hasTextFieldOfName;
import static com.smartdevicelink.managers.screen.menu.BaseMenuManager.parentIdNotFound;

import com.smartdevicelink.managers.ISdl;
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
import com.smartdevicelink.proxy.rpc.enums.TextFieldName;
import com.smartdevicelink.proxy.rpc.listeners.OnMultipleRequestListener;
import com.smartdevicelink.util.DebugTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Bilal Alsharifi on 1/25/21.
 */
class MenuReplaceUtilities {
    private static final String TAG = "MenuReplaceUtilities";
    private static int menuId = 0;

    static int getNextMenuId() {
        return ++menuId;
    }

    /**
     * Assign cell ids on a list of menu cells given a parent id (or no parent id)
     *
     * @param menuCells The list of menu cells to update
     * @param parentId  The parent id to assign if needed
     */
    static void addIdsToMenuCells(List<MenuCell> menuCells, int parentId) {
        for (MenuCell cell : menuCells) {
            cell.setCellId(getNextMenuId());
            if (parentId != parentIdNotFound) {
                cell.setParentCellId(parentId);
            }
            if (cell.isSubMenuCell() && !cell.getSubCells().isEmpty()) {
                addIdsToMenuCells(cell.getSubCells(), cell.getCellId());
            }
        }
    }

    static void transferCellIDsFromCells(List<MenuCell> fromCells, List<MenuCell> toCells) {
        if (fromCells == null || toCells == null || fromCells.isEmpty() || fromCells.size() != toCells.size()) {
            return;
        }
        for (int i = 0; i < toCells.size(); i++) {
            toCells.get(i).setCellId(fromCells.get(i).getCellId());
        }

        // Update parent ids
        for (MenuCell cell : toCells) {
            if (!cell.isSubMenuCell()) {
                continue;
            }

            for (MenuCell subCell : cell.getSubCells()) {
                subCell.setParentCellId(cell.getCellId());
            }
        }
    }

    static void transferCellListenersFromCells(List<MenuCell> fromCells, List<MenuCell> toCells) {
        if (fromCells == null || toCells == null || fromCells.isEmpty() || fromCells.size() != toCells.size()) {
            return;
        }
        for (int i = 0; i < fromCells.size(); i++) {
            toCells.get(i).setMenuSelectionListener(fromCells.get(i).getMenuSelectionListener());
        }
    }

    static Set<SdlArtwork> findAllArtworksToBeUploadedFromCells(List<MenuCell> cells, FileManager fileManager, WindowCapability windowCapability) {
        // Make sure we can use images in the menus
        if (!hasImageFieldOfName(windowCapability, ImageFieldName.cmdIcon)) {
            return new HashSet<>();
        }

        Set<SdlArtwork> artworks = new HashSet<>();
        for (MenuCell cell : cells) {
            if (fileManager != null) {
                if (fileManager.fileNeedsUpload(cell.getIcon())) {
                    artworks.add(cell.getIcon());
                }
                if (hasImageFieldOfName(windowCapability, ImageFieldName.menuCommandSecondaryImage) && fileManager.fileNeedsUpload(cell.getSecondaryArtwork())) {
                    artworks.add(cell.getSecondaryArtwork());
                }
            }
            if (cell.isSubMenuCell() && !cell.getSubCells().isEmpty()) {
                artworks.addAll(findAllArtworksToBeUploadedFromCells(cell.getSubCells(), fileManager, windowCapability));
            }
        }

        return artworks;
    }

    // If there is an icon and the icon has been uploaded, or if the icon is a static icon, it should include the image
    static boolean shouldCellIncludePrimaryImageFromCell(MenuCell cell, FileManager fileManager, WindowCapability windowCapability) {
        boolean supportsImage = cell.isSubMenuCell() ? hasImageFieldOfName(windowCapability, ImageFieldName.subMenuIcon) : hasImageFieldOfName(windowCapability, ImageFieldName.cmdIcon);
        return cell.getIcon() != null && supportsImage && (fileManager.hasUploadedFile(cell.getIcon()) || cell.getIcon().isStaticIcon());
    }

    // If there is an icon and the icon has been uploaded, or if the icon is a static icon, it should include the image
    static boolean shouldCellIncludeSecondaryImageFromCell(MenuCell cell, FileManager fileManager, WindowCapability windowCapability) {
        boolean supportsImage = cell.isSubMenuCell() ? hasImageFieldOfName(windowCapability, ImageFieldName.menuSubMenuSecondaryImage) : hasImageFieldOfName(windowCapability, ImageFieldName.menuCommandSecondaryImage);
        return cell.getSecondaryArtwork() != null && supportsImage && (fileManager.hasUploadedFile(cell.getSecondaryArtwork()) || cell.getSecondaryArtwork().isStaticIcon());
    }

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

    static int positionForRPCRequest(RPCRequest request) {
        int position = 0;
        if (request instanceof AddCommand) {
            position = ((AddCommand) request).getMenuParams().getPosition();
        } else if (request instanceof AddSubMenu) {
            position = ((AddSubMenu) request).getPosition();
        }
        return position;
    }

    static List<RPCRequest> deleteCommandsForCells(List<MenuCell> cells) {
        List<RPCRequest> deletes = new ArrayList<>();
        for (MenuCell cell : cells) {
            if (cell.isSubMenuCell()) {
                DeleteSubMenu delete = new DeleteSubMenu(cell.getCellId());
                deletes.add(delete);
            } else {
                DeleteCommand delete = new DeleteCommand(cell.getCellId());
                deletes.add(delete);
            }
        }
        return deletes;
    }

    static List<RPCRequest> mainMenuCommandsForCells(List<MenuCell> cells, FileManager fileManager, List<MenuCell> menu, WindowCapability windowCapability, MenuLayout defaultSubmenuLayout) {
        List<RPCRequest> commands = new ArrayList<>();

        // We need the index to use it as position so we will use this type of loop
        for (int menuInteger = 0; menuInteger < menu.size(); menuInteger++) {
            MenuCell mainCell = menu.get(menuInteger);
            for (int updateCellsIndex = 0; updateCellsIndex < cells.size(); updateCellsIndex++) {
                MenuCell addCell = cells.get(updateCellsIndex);
                if (mainCell.equals(addCell)) {
                    if (addCell.isSubMenuCell()) {
                        commands.add(subMenuCommandForMenuCell(addCell, fileManager, windowCapability, menuInteger, defaultSubmenuLayout));
                    } else {
                        commands.add(commandForMenuCell(addCell, fileManager, windowCapability, menuInteger));
                    }
                    break;
                }
            }
        }
        return commands;
    }

    static List<RPCRequest> subMenuCommandsForCells(List<MenuCell> cells, FileManager fileManager, WindowCapability windowCapability, MenuLayout defaultSubmenuLayout) {
        List<RPCRequest> commands = new ArrayList<>();
        for (MenuCell cell : cells) {
            if (cell.isSubMenuCell() && !cell.getSubCells().isEmpty()) {
                commands.addAll(allCommandsForCells(cell.getSubCells(), fileManager, windowCapability, defaultSubmenuLayout));
            }
        }
        return commands;
    }

    static List<RPCRequest> allCommandsForCells(List<MenuCell> cells, FileManager fileManager, WindowCapability windowCapability, MenuLayout defaultSubmenuLayout) {
        List<RPCRequest> commands = new ArrayList<>();

        for (int cellIndex = 0; cellIndex < cells.size(); cellIndex++) {
            MenuCell cell = cells.get(cellIndex);
            if (cell.isSubMenuCell()) {
                commands.add(subMenuCommandForMenuCell(cell, fileManager, windowCapability, cellIndex, defaultSubmenuLayout));

                // Recursively grab the commands for all the sub cells
                if (!cell.getSubCells().isEmpty()) {
                    commands.addAll(allCommandsForCells(cell.getSubCells(), fileManager, windowCapability, defaultSubmenuLayout));
                }
            } else {
                commands.add(commandForMenuCell(cell, fileManager, windowCapability, cellIndex));
            }
        }
        return commands;
    }

    static AddCommand commandForMenuCell(MenuCell cell, FileManager fileManager, WindowCapability windowCapability, int position) {
        AddCommand command = new AddCommand(cell.getCellId());

        MenuParams params = new MenuParams(cell.getUniqueTitle());
        params.setSecondaryText((cell.getSecondaryText() != null && !cell.getSecondaryText().isEmpty() && hasTextFieldOfName(windowCapability, TextFieldName.menuCommandSecondaryText)) ? cell.getSecondaryText() : null);
        params.setTertiaryText((cell.getTertiaryText() != null && !cell.getTertiaryText().isEmpty() && hasTextFieldOfName(windowCapability, TextFieldName.menuCommandTertiaryText)) ? cell.getTertiaryText() : null);
        params.setParentID(cell.getParentCellId() != parentIdNotFound ? cell.getParentCellId() : null);
        params.setPosition(position);

        command.setMenuParams(params);
        if (cell.getVoiceCommands() != null && !cell.getVoiceCommands().isEmpty()) {
            command.setVrCommands(cell.getVoiceCommands());
        } else {
            command.setVrCommands(null);
        }
        boolean shouldCellIncludePrimaryImage = cell.getIcon() != null && shouldCellIncludePrimaryImageFromCell(cell, fileManager, windowCapability);
        command.setCmdIcon(shouldCellIncludePrimaryImage ? cell.getIcon().getImageRPC() : null);

        boolean shouldCellIncludeSecondaryImage = cell.getSecondaryArtwork() != null && shouldCellIncludeSecondaryImageFromCell(cell, fileManager, windowCapability);
        command.setSecondaryImage(shouldCellIncludeSecondaryImage ? cell.getSecondaryArtwork().getImageRPC() : null);

        return command;
    }

    static AddSubMenu subMenuCommandForMenuCell(MenuCell cell, FileManager fileManager, WindowCapability windowCapability, int position, MenuLayout defaultSubmenuLayout) {
        boolean shouldCellIncludePrimaryImage = cell.getIcon() != null && cell.getIcon().getImageRPC() != null && shouldCellIncludePrimaryImageFromCell(cell, fileManager, windowCapability);
        Image icon = (shouldCellIncludePrimaryImage ? cell.getIcon().getImageRPC() : null);
        boolean shouldCellIncludeSecondaryImage = cell.getSecondaryArtwork() != null && cell.getSecondaryArtwork().getImageRPC() != null && shouldCellIncludeSecondaryImageFromCell(cell, fileManager, windowCapability);
        Image secondaryIcon = (shouldCellIncludeSecondaryImage ? cell.getSecondaryArtwork().getImageRPC() : null);

        if (cell.getVoiceCommands() != null && !cell.getVoiceCommands().isEmpty()) {
            DebugTool.logWarning(TAG, "Setting voice commands for submenu cells is not supported. The voice commands will not be set.");
        }

        MenuLayout submenuLayout;
        List<MenuLayout> availableMenuLayouts = windowCapability != null ? windowCapability.getMenuLayoutsAvailable() : null;
        if (cell.getSubMenuLayout() != null && availableMenuLayouts != null && availableMenuLayouts.contains(cell.getSubMenuLayout())) {
            submenuLayout = cell.getSubMenuLayout();
        } else {
            submenuLayout = defaultSubmenuLayout;
        }

        return new AddSubMenu(cell.getCellId(), cell.getUniqueTitle())
                .setParentID(cell.getParentCellId() != parentIdNotFound ? cell.getParentCellId() : null)
                .setSecondaryText((cell.getSecondaryText() != null && !cell.getSecondaryText().isEmpty() && hasTextFieldOfName(windowCapability, TextFieldName.menuSubMenuSecondaryText)) ? cell.getSecondaryText() : null)
                .setTertiaryText((cell.getTertiaryText() != null && !cell.getTertiaryText().isEmpty() && hasTextFieldOfName(windowCapability, TextFieldName.menuSubMenuTertiaryText)) ? cell.getTertiaryText() : null)
                .setPosition(position)
                .setMenuLayout(submenuLayout)
                .setMenuIcon(icon)
                .setSecondaryImage(secondaryIcon);
    }

    static boolean removeCellFromList(List<MenuCell> menuCellList, int commandId) {
        for (MenuCell menuCell : menuCellList) {
            if (menuCell.getCellId() == commandId) {
                // If the cell id matches the command id, remove it from the list and return
                menuCellList.remove(menuCell);
                return true;
            } else if (menuCell.isSubMenuCell() && !menuCell.getSubCells().isEmpty()) {
                // If the menu cell has sub cells, we need to recurse and check the sub cells
                List<MenuCell> newList = menuCell.getSubCells();
                boolean foundAndRemovedItem = removeCellFromList(newList, commandId);
                if (foundAndRemovedItem) {
                    menuCell.setSubCells(newList);
                    return true;
                }
            }
        }
        return false;
    }

    static boolean addCellWithCellId(int cellId, int position, List<MenuCell> newMenuList, List<MenuCell> mainMenuList) {
        MenuCell addedCell = null;
        for (MenuCell cell : newMenuList) {
            if (cell.getCellId() == cellId) {
                addedCell = cell;
                break;
            } else if (cell.isSubMenuCell() && !cell.getSubCells().isEmpty()) {
                boolean success = addCellWithCellId(cellId, position, cell.getSubCells(), mainMenuList);
                if (success) {
                    return true;
                }
            }
        }
        if (addedCell != null) {
            return addMenuCell(addedCell, mainMenuList, position);
        }
        return false;
    }

    private static boolean addMenuCell(MenuCell cell, List<MenuCell> menuCellList, int position) {
        if (cell.getParentCellId() == parentIdNotFound) {
            // The cell does not have a parent id, just insert it into the main menu
            insertMenuCell(cell, menuCellList, position);
            return true;
        }

        // If the cell has a parent id, we need to find the cell with a matching cell id and insert it into its submenu
        for (MenuCell menuCell : menuCellList) {
            if (menuCell.getCellId() == cell.getParentCellId()) {
                if (menuCell.getSubCells() == null) {
                    menuCell.setSubCells(new ArrayList<MenuCell>());
                }
                // If we found the correct submenu, insert it into that submenu
                insertMenuCell(cell, menuCell.getSubCells(), position);
                return true;
            } else if (menuCell.isSubMenuCell() && !menuCell.getSubCells().isEmpty()) {
                // Check the sub cells of this cell to see if any of those have cell ids that match the parent cell id
                List<MenuCell> newList = menuCell.getSubCells();
                boolean foundAndAddedItem = addMenuCell(cell, newList, position);
                if (foundAndAddedItem) {
                    menuCell.setSubCells(newList);
                    return true;
                }
            }
        }
        return false;
    }

    private static void insertMenuCell(MenuCell cell, List<MenuCell> cellList, int position) {
        MenuCell cellToInsert = cell;
        if (cellToInsert.isSubMenuCell()) {
            // We should not add the subCells automatically when adding a parent cell
            cellToInsert = cell.clone();
            cellToInsert.getSubCells().clear();
        }
        if (position > cellList.size()) {
            cellList.add(cellToInsert);
        } else {
            cellList.add(position, cellToInsert);
        }
    }

    static List<MenuCell> cloneMenuCellsList(List<MenuCell> originalList) {
        if (originalList == null) {
            return new ArrayList<>();
        }

        List<MenuCell> clone = new ArrayList<>();
        for (MenuCell menuCell : originalList) {
            clone.add(menuCell.clone());
        }
        return clone;
    }

    static void sendRPCs(final List<RPCRequest> requests, ISdl internalInterface, final SendingRPCsCompletionListener listener) {
        final Map<RPCRequest, String> errors = new HashMap<>();
        if (requests == null || requests.isEmpty()) {
            listener.onComplete(true, errors);
            return;
        }

        internalInterface.sendRPCs(requests, new OnMultipleRequestListener() {
            @Override
            public void onUpdate(int remainingRequests) {
            }

            @Override
            public void onFinished() {
                listener.onComplete(errors.isEmpty(), errors);
            }

            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                RPCRequest request = null;
                for (RPCRequest r : requests) {
                    if (response.getCorrelationID().equals(r.getCorrelationID())) {
                        request = r;
                        break;
                    }
                }
                if (!response.getSuccess()) {
                    errors.put(request, "Failed to send RPC. Result: " + response.getResultCode() + ". Info: " + response.getInfo());
                }
                listener.onResponse(request, response);
            }
        });
    }
}