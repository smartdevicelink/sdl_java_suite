package com.smartdevicelink.managers.screen.menu;

import com.smartdevicelink.managers.ManagerUtility;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.AddCommand;
import com.smartdevicelink.proxy.rpc.AddSubMenu;
import com.smartdevicelink.proxy.rpc.DeleteCommand;
import com.smartdevicelink.proxy.rpc.DeleteSubMenu;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.MenuParams;
import com.smartdevicelink.proxy.rpc.WindowCapability;
import com.smartdevicelink.proxy.rpc.enums.ImageFieldName;
import com.smartdevicelink.proxy.rpc.enums.MenuLayout;

import java.util.ArrayList;
import java.util.List;

import static com.smartdevicelink.managers.screen.menu.BaseMenuManager.parentIdNotFound;

/**
 * Created by Bilal Alsharifi on 1/25/21.
 */
class MenuReplaceUtilities {
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
        if (!supportsImages(windowCapability)) {
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

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    static boolean supportsImages(WindowCapability windowCapability) {
        return windowCapability == null || ManagerUtility.WindowCapabilityUtility.hasImageFieldOfName(windowCapability, ImageFieldName.cmdIcon);
    }

    static boolean shouldCellIncludeImage(MenuCell cell, FileManager fileManager) {
        // todo should check if images are supported?
        SdlArtwork artwork = cell.getIcon();
        if (artwork == null) {
            return false;
        }
        // If there is an icon and the icon has been uploaded, or if the icon is a static icon, it should include the image
        return (fileManager.hasUploadedFile(artwork) || artwork.isStaticIcon());
    }

    static List<RPCRequest> mainMenuCommandsForCells(List<MenuCell> cellsToAdd, FileManager fileManager, List<MenuCell> updatedMenu, List<MenuLayout> availableMenuLayouts, MenuLayout defaultSubmenuLayout) {
        List<RPCRequest> builtCommands = new ArrayList<>();

        // We need the index so we will use this type of loop
        for (int z = 0; z < updatedMenu.size(); z++) {
            MenuCell mainCell = updatedMenu.get(z);
            for (int i = 0; i < cellsToAdd.size(); i++) {
                MenuCell addCell = cellsToAdd.get(i);
                if (mainCell.equals(addCell)) {
                    if (addCell.getSubCells() != null && !addCell.getSubCells().isEmpty()) {
                        builtCommands.add(subMenuCommandForMenuCell(addCell, fileManager, z, availableMenuLayouts, defaultSubmenuLayout));
                    } else {
                        builtCommands.add(commandForMenuCell(addCell, fileManager, z));
                    }
                    break;
                }
            }
        }
        return builtCommands;
    }

    static List<RPCRequest> subMenuCommandsForCells(List<MenuCell> cells, FileManager fileManager, List<MenuLayout> availableMenuLayouts, MenuLayout defaultSubmenuLayout) {
        List<RPCRequest> builtCommands = new ArrayList<>();
        for (MenuCell cell : cells) {
            if (cell.getSubCells() != null && !cell.getSubCells().isEmpty()) {
                builtCommands.addAll(allCommandsForCells(cell.getSubCells(), fileManager, availableMenuLayouts, defaultSubmenuLayout));
            }
        }
        return builtCommands;
    }

    static List<RPCRequest> allCommandsForCells(List<MenuCell> cells, FileManager fileManager, List<MenuLayout> availableMenuLayouts, MenuLayout defaultSubmenuLayout) {
        List<RPCRequest> builtCommands = new ArrayList<>();

        for (int i = 0; i < cells.size(); i++) {
            MenuCell cell = cells.get(i);
            if (cell.getSubCells() != null && !cell.getSubCells().isEmpty()) {
                builtCommands.add(subMenuCommandForMenuCell(cell, fileManager, i, availableMenuLayouts, defaultSubmenuLayout));

                // recursively grab the commands for all the sub cells
                builtCommands.addAll(allCommandsForCells(cell.getSubCells(), fileManager, availableMenuLayouts, defaultSubmenuLayout));
            } else {
                builtCommands.add(commandForMenuCell(cell, fileManager, i));
            }
        }
        return builtCommands;
    }

    static AddCommand commandForMenuCell(MenuCell cell, FileManager fileManager, int position) {
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
        boolean shouldCellIncludeImage = cell.getIcon() != null && shouldCellIncludeImage(cell, fileManager);
        command.setCmdIcon(shouldCellIncludeImage ? cell.getIcon().getImageRPC() : null);

        return command;
    }

    static AddSubMenu subMenuCommandForMenuCell(MenuCell cell, FileManager fileManager, int position, List<MenuLayout> availableMenuLayouts, MenuLayout defaultSubmenuLayout) {
        boolean shouldCellIncludeImage = cell.getIcon() != null && cell.getIcon().getImageRPC() != null && shouldCellIncludeImage(cell, fileManager);
        Image icon = (shouldCellIncludeImage ? cell.getIcon().getImageRPC() : null);

        MenuLayout submenuLayout;
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
}
