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

    static boolean shouldRPCsIncludeImages(List<MenuCell> cells, FileManager fileManager) {
        for (MenuCell cell : cells) {
            SdlArtwork artwork = cell.getIcon();
            if (artwork != null && !artwork.isStaticIcon() && fileManager != null && !fileManager.hasUploadedFile(artwork)) {
                return false;
            } else if (cell.getSubCells() != null && !cell.getSubCells().isEmpty()) {
                return shouldRPCsIncludeImages(cell.getSubCells(), fileManager);
            }
        }
        return true;
    }

    static List<RPCRequest> mainMenuCommandsForCells(List<MenuCell> cellsToAdd, boolean shouldHaveArtwork, List<MenuCell> updatedMenu, List<MenuLayout> availableMenuLayouts, MenuLayout defaultSubmenuLayout) {
        List<RPCRequest> builtCommands = new ArrayList<>();

        // We need the index so we will use this type of loop
        for (int z = 0; z < updatedMenu.size(); z++) {
            MenuCell mainCell = updatedMenu.get(z);
            for (int i = 0; i < cellsToAdd.size(); i++) {
                MenuCell addCell = cellsToAdd.get(i);
                if (mainCell.equals(addCell)) {
                    if (addCell.getSubCells() != null && !addCell.getSubCells().isEmpty()) {
                        builtCommands.add(subMenuCommandForMenuCell(addCell, shouldHaveArtwork, z, availableMenuLayouts, defaultSubmenuLayout));
                    } else {
                        builtCommands.add(commandForMenuCell(addCell, shouldHaveArtwork, z));
                    }
                    break;
                }
            }
        }
        return builtCommands;
    }

    static List<RPCRequest> subMenuCommandsForCells(List<MenuCell> cells, boolean shouldHaveArtwork, List<MenuLayout> availableMenuLayouts, MenuLayout defaultSubmenuLayout) {
        List<RPCRequest> builtCommands = new ArrayList<>();
        for (MenuCell cell : cells) {
            if (cell.getSubCells() != null && !cell.getSubCells().isEmpty()) {
                builtCommands.addAll(allCommandsForCells(cell.getSubCells(), shouldHaveArtwork, availableMenuLayouts, defaultSubmenuLayout));
            }
        }
        return builtCommands;
    }

    static List<RPCRequest> allCommandsForCells(List<MenuCell> cells, boolean shouldHaveArtwork, List<MenuLayout> availableMenuLayouts, MenuLayout defaultSubmenuLayout) {
        List<RPCRequest> builtCommands = new ArrayList<>();

        for (int i = 0; i < cells.size(); i++) {
            MenuCell cell = cells.get(i);
            if (cell.getSubCells() != null && !cell.getSubCells().isEmpty()) {
                builtCommands.add(subMenuCommandForMenuCell(cell, shouldHaveArtwork, i, availableMenuLayouts, defaultSubmenuLayout));

                // recursively grab the commands for all the sub cells
                builtCommands.addAll(allCommandsForCells(cell.getSubCells(), shouldHaveArtwork, availableMenuLayouts, defaultSubmenuLayout));
            } else {
                builtCommands.add(commandForMenuCell(cell, shouldHaveArtwork, i));
            }
        }
        return builtCommands;
    }

    static AddCommand commandForMenuCell(MenuCell cell, boolean shouldHaveArtwork, int position) {
        MenuParams params = new MenuParams(cell.getTitle());
        params.setParentID(cell.getParentCellId() != parentIdNotFound ? cell.getParentCellId() : null);
        params.setPosition(position);

        AddCommand command = new AddCommand(cell.getCellId());
        command.setMenuParams(params);
        if (cell.getVoiceCommands() != null && !cell.getVoiceCommands().isEmpty()) {
            command.setVrCommands(cell.getVoiceCommands());
        } else {
            command.setVrCommands(null);
        }
        command.setCmdIcon((cell.getIcon() != null && shouldHaveArtwork) ? cell.getIcon().getImageRPC() : null);

        return command;
    }

    static AddSubMenu subMenuCommandForMenuCell(MenuCell cell, boolean shouldHaveArtwork, int position, List<MenuLayout> availableMenuLayouts, MenuLayout defaultSubmenuLayout) {
        Image icon = (shouldHaveArtwork && (cell.getIcon() != null && cell.getIcon().getImageRPC() != null)) ? cell.getIcon().getImageRPC() : null;

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
