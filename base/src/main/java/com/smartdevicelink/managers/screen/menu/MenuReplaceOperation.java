package com.smartdevicelink.managers.screen.menu;

import com.livio.taskmaster.Task;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.managers.ManagerUtility;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.file.MultipleFileCompletionListener;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.AddCommand;
import com.smartdevicelink.proxy.rpc.AddSubMenu;
import com.smartdevicelink.proxy.rpc.DeleteCommand;
import com.smartdevicelink.proxy.rpc.DeleteSubMenu;
import com.smartdevicelink.proxy.rpc.MenuParams;
import com.smartdevicelink.proxy.rpc.WindowCapability;
import com.smartdevicelink.proxy.rpc.enums.DisplayType;
import com.smartdevicelink.proxy.rpc.enums.ImageFieldName;
import com.smartdevicelink.proxy.rpc.listeners.OnMultipleRequestListener;
import com.smartdevicelink.util.DebugTool;

import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.smartdevicelink.managers.screen.menu.BaseMenuManager.menuCellIdMin;
import static com.smartdevicelink.managers.screen.menu.BaseMenuManager.parentIdNotFound;

/**
 * Created by Bilal Alsharifi on 1/20/21.
 */
class MenuReplaceOperation extends Task {
    private static final String TAG = "MenuReplaceOperation";
    private static final int KEEP = 0;
    private static final int MARKED_FOR_ADDITION = 1;
    private static final int MARKED_FOR_DELETION = 2;

    private final WeakReference<ISdl> internalInterface;
    private final WeakReference<FileManager> fileManager;
    private final WindowCapability defaultMainWindowCapability;
    private List<MenuCell> oldMenuCells;
    private final List<MenuCell> menuCells;
    private List<MenuCell> keepsOld;
    private List<MenuCell> keepsNew;
    private final MenuManagerCompletionListener operationCompletionListener;
    private final String displayType;
    private final DynamicMenuUpdatesMode dynamicMenuUpdatesMode;
    private int lastMenuId;
    private MenuConfiguration menuConfiguration;

    // todo split to static and dynamic operations
    // todo call onFinish & listener when done

    MenuReplaceOperation(ISdl internalInterface, FileManager fileManager, String displayType, DynamicMenuUpdatesMode dynamicMenuUpdatesMode, MenuConfiguration menuConfiguration, WindowCapability defaultMainWindowCapability, List<MenuCell> oldMenuCells, List<MenuCell> menuCells, MenuManagerCompletionListener operationCompletionListener) {
        super(TAG);
        this.internalInterface = new WeakReference<>(internalInterface);
        this.fileManager = new WeakReference<>(fileManager);
        this.displayType = displayType;
        this.dynamicMenuUpdatesMode = dynamicMenuUpdatesMode;
        this.menuConfiguration = menuConfiguration;
        this.defaultMainWindowCapability = defaultMainWindowCapability;
        this.oldMenuCells = oldMenuCells;
        this.menuCells = menuCells;
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
        List<SdlArtwork> artworksToBeUploaded = findAllArtworksToBeUploadedFromCells(menuCells);
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

        // Checks against what the developer set for update mode and against the display type to
        // determine how the menu will be updated. This has the ability to be changed during a session.
        if (checkUpdateMode(dynamicMenuUpdatesMode, displayType)) {
            // Run the lists through the new algorithm
            RunScore rootScore = runMenuCompareAlgorithm(oldMenuCells, menuCells);
            if (rootScore == null) {
                // send initial menu (score will return null)
                // make a copy of our current cells
                DebugTool.logInfo(TAG, "Creating initial Menu");
                // Set the IDs if needed
                lastMenuId = menuCellIdMin;
                updateIdsOnMenuCells(menuCells, parentIdNotFound);
                this.oldMenuCells = new ArrayList<>(menuCells); // todo why?
                createAndSendEntireMenu(listener);
            } else {
                DebugTool.logInfo(TAG, "Dynamically Updating Menu");
                if (menuCells.isEmpty() && (oldMenuCells != null && !oldMenuCells.isEmpty())) {
                    // the dev wants to clear the menu. We have old cells and an empty array of new ones.
                    deleteMenuWhenNewCellsEmpty();
                } else {
                    // lets dynamically update the root menu
                    dynamicallyUpdateRootMenu(rootScore);
                }
            }
        } else {
            // We are in compatibility mode. No need to run the algorithm
            DebugTool.logInfo(TAG, "Updating menus in compatibility mode");
            lastMenuId = menuCellIdMin;
            updateIdsOnMenuCells(menuCells, parentIdNotFound);
            // if the old cell array is not null, we want to delete the entire thing, else copy the new array
            if (oldMenuCells == null) {
                this.oldMenuCells = new ArrayList<>(menuCells);  // todo why?
            }
            createAndSendEntireMenu(listener);
        }
    }

    private boolean checkUpdateMode(DynamicMenuUpdatesMode updateMode, String displayType) {
        if (updateMode.equals(DynamicMenuUpdatesMode.ON_WITH_COMPAT_MODE)) {
            if (displayType == null) {
                return true;
            }
            return (!displayType.equals(DisplayType.GEN3_8_INCH.toString()));

        } else if (updateMode.equals(DynamicMenuUpdatesMode.FORCE_OFF)) {
            return false;
        } else if (updateMode.equals(DynamicMenuUpdatesMode.FORCE_ON)) {
            return true;
        }

        return true;
    }

    private void deleteMenuWhenNewCellsEmpty() {
        if (getState() == Task.CANCELED) {
            return;
        }

        sendDeleteRPCs(createDeleteRPCsForCells(oldMenuCells), new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                if (!success) {
                    DebugTool.logError(TAG, "Error Sending Current Menu");
                } else {
                    DebugTool.logInfo(TAG, "Successfully Cleared Menu");
                }
                oldMenuCells = null;
            }
        });
    }

    private void dynamicallyUpdateRootMenu(RunScore bestRootScore) {
        // we need to run through the keeps and see if they have subCells, as they also need to be run
        // through the compare function.
        List<Integer> newIntArray = bestRootScore.getCurrentMenu();
        List<Integer> oldIntArray = bestRootScore.getOldMenu();
        List<RPCRequest> deleteCommands;

        // Set up deletes
        List<MenuCell> deletes = new ArrayList<>();
        keepsOld = new ArrayList<>();
        for (int x = 0; x < oldIntArray.size(); x++) {
            Integer old = oldIntArray.get(x);
            if (old.equals(MARKED_FOR_DELETION)) {
                // grab cell to send to function to create delete commands
                deletes.add(oldMenuCells.get(x));
            } else if (old.equals(KEEP)) {
                keepsOld.add(oldMenuCells.get(x));
            }
        }
        // create the delete commands
        deleteCommands = createDeleteRPCsForCells(deletes);

        // Set up the adds
        List<MenuCell> adds = new ArrayList<>();
        keepsNew = new ArrayList<>();
        for (int x = 0; x < newIntArray.size(); x++) {
            Integer newInt = newIntArray.get(x);
            if (newInt.equals(MARKED_FOR_ADDITION)) {
                // grab cell to send to function to create add commands
                adds.add(menuCells.get(x));
            } else if (newInt.equals(KEEP)) {
                keepsNew.add(menuCells.get(x));
            }
        }
        updateIdsOnDynamicCells(adds);
        // this is needed for the onCommands to still work
        transferIdsToKeptCells(keepsNew);

        if (!adds.isEmpty()) {
            DebugTool.logInfo(TAG, "Sending root menu updates");
            sendDynamicRootMenuRPCs(deleteCommands, adds);
        } else {
            DebugTool.logInfo(TAG, "All root menu items are kept. Check the sub menus");
            runSubMenuCompareAlgorithm();
        }
    }

    private void createAndSendEntireMenu(final CompletionListener listener) {
        if (getState() == Task.CANCELED) {
            return;
        }

        deleteRootMenu(new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                createAndSendMenuCellRPCs(menuCells, new CompletionListener() {
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

        if (menu.isEmpty()) {
            if (listener != null) {
                // This can be considered a success if the user was clearing out their menu
                listener.onComplete(true);
            }
            return;
        }

        List<RPCRequest> mainMenuCommands;
        final List<RPCRequest> subMenuCommands;

        if (!shouldRPCsIncludeImages(menu) || !supportsImages()) {
            // Send artwork-less menu
            mainMenuCommands = mainMenuCommandsForCells(menu, false);
            subMenuCommands = subMenuCommandsForCells(menu, false);
        } else {
            mainMenuCommands = mainMenuCommandsForCells(menu, true);
            subMenuCommands = subMenuCommandsForCells(menu, true);
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
                    if (keepsNew != null && !keepsNew.isEmpty()) {
                        runSubMenuCompareAlgorithm(); // todo should we pass listener here?
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
                if (keepsNew != null && !keepsNew.isEmpty()) {
                    runSubMenuCompareAlgorithm();  // todo should we pass listener here?
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

        List<RPCRequest> mainMenuCommands;

        if (!shouldRPCsIncludeImages(adds) || !supportsImages()) {
            // Send artwork-less menu
            mainMenuCommands = createCommandsForDynamicSubCells(newMenu, adds, false);
        } else {
            mainMenuCommands = createCommandsForDynamicSubCells(newMenu, adds, true);
        }

        internalInterface.get().sendSequentialRPCs(mainMenuCommands, new OnMultipleRequestListener() {
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

    private void deleteRootMenu(final CompletionListener listener) {
        if (oldMenuCells == null || oldMenuCells.isEmpty()) {
            if (listener != null) {
                // technically this method is successful if there's nothing to delete
                DebugTool.logInfo(TAG, "No old cells to delete, returning");
                listener.onComplete(true);
            }
        } else {
            sendDeleteRPCs(createDeleteRPCsForCells(oldMenuCells), listener);
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

    private List<RPCRequest> subMenuCommandsForCells(List<MenuCell> cells, boolean shouldHaveArtwork) {
        List<RPCRequest> builtCommands = new ArrayList<>();
        for (MenuCell cell : cells) {
            if (cell.getSubCells() != null && !cell.getSubCells().isEmpty()) {
                builtCommands.addAll(allCommandsForCells(cell.getSubCells(), shouldHaveArtwork));
            }
        }
        return builtCommands;
    }

    List<RPCRequest> allCommandsForCells(List<MenuCell> cells, boolean shouldHaveArtwork) {
        List<RPCRequest> builtCommands = new ArrayList<>();

        for (int i = 0; i < cells.size(); i++) {
            MenuCell cell = cells.get(i);
            if (cell.getSubCells() != null && !cell.getSubCells().isEmpty()) {
                builtCommands.add(subMenuCommandForMenuCell(cell, shouldHaveArtwork, i));

                // recursively grab the commands for all the sub cells
                builtCommands.addAll(allCommandsForCells(cell.getSubCells(), shouldHaveArtwork));
            } else {
                builtCommands.add(commandForMenuCell(cell, shouldHaveArtwork, i));
            }
        }
        return builtCommands;
    }

    private List<RPCRequest> createCommandsForDynamicSubCells(List<MenuCell> oldMenuCells, List<MenuCell> cells, boolean shouldHaveArtwork) {
        List<RPCRequest> builtCommands = new ArrayList<>();
        for (int z = 0; z < oldMenuCells.size(); z++) {
            MenuCell oldCell = oldMenuCells.get(z);
            for (int i = 0; i < cells.size(); i++) {
                MenuCell cell = cells.get(i);
                if (cell.equals(oldCell)) {
                    builtCommands.add(commandForMenuCell(cell, shouldHaveArtwork, z));
                    break;
                }
            }
        }
        return builtCommands;
    }

    private AddCommand commandForMenuCell(MenuCell cell, boolean shouldHaveArtwork, int position) {
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

    private AddSubMenu subMenuCommandForMenuCell(MenuCell cell, boolean shouldHaveArtwork, int position) {
        AddSubMenu subMenu = new AddSubMenu(cell.getCellId(), cell.getTitle());
        subMenu.setPosition(position);
        if (cell.getSubMenuLayout() != null) {
            subMenu.setMenuLayout(cell.getSubMenuLayout());
        } else if (menuConfiguration != null && menuConfiguration.getSubMenuLayout() != null) {
            subMenu.setMenuLayout(menuConfiguration.getSubMenuLayout());
        }
        subMenu.setMenuIcon((shouldHaveArtwork && (cell.getIcon() != null && cell.getIcon().getImageRPC() != null)) ? cell.getIcon().getImageRPC() : null);
        return subMenu;
    }

    private void sendDynamicRootMenuRPCs(List<RPCRequest> deleteCommands, final List<MenuCell> updatedCells) {
        if (getState() == Task.CANCELED) {
            return;
        }

        sendDeleteRPCs(deleteCommands, new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                createAndSendMenuCellRPCs(updatedCells, new CompletionListener() {
                    @Override
                    public void onComplete(boolean success) {

                        if (!success) {
                            DebugTool.logError(TAG, "Error Sending Current Menu");
                        }
                    }
                });
            }
        });
    }

    // This is called in the listener in the sendMenu and sendSubMenuCommands Methods
    private void runSubMenuCompareAlgorithm() {
        // any cells that were re-added have their sub-cells added with them
        // at this point all we care about are the cells that were deemed equal and kept.
        if (keepsNew == null || keepsNew.isEmpty()) {
            return;
        }

        List<SubCellCommandList> commandLists = new ArrayList<>();

        for (int i = 0; i < keepsNew.size(); i++) {

            MenuCell keptCell = keepsNew.get(i);
            MenuCell oldKeptCell = keepsOld.get(i);

            if (oldKeptCell.getSubCells() != null && !oldKeptCell.getSubCells().isEmpty() && keptCell.getSubCells() != null && !keptCell.getSubCells().isEmpty()) {
                // ACTUAL LOGIC
                RunScore subScore = compareOldAndNewLists(oldKeptCell.getSubCells(), keptCell.getSubCells());

                if (subScore != null) {
                    DebugTool.logInfo(TAG, "Sub menu Run Score: " + oldKeptCell.getTitle() + " Score: " + subScore.getScore());
                    SubCellCommandList commandList = new SubCellCommandList(oldKeptCell.getTitle(), oldKeptCell.getCellId(), subScore, oldKeptCell.getSubCells(), keptCell.getSubCells());
                    commandLists.add(commandList);
                }
            }
        }
        createSubMenuDynamicCommands(commandLists);
    }

    private void createSubMenuDynamicCommands(final List<SubCellCommandList> commandLists) {
        if (getState() == Task.CANCELED) {
            return;
        }

        // break out
        if (commandLists.isEmpty()) {
//            if (hasQueuedUpdate) {
//                DebugTool.logInfo(TAG, "Menu Manager has waiting updates, sending now");
//                setMenuCells(waitingUpdateMenuCells);
//            }
            DebugTool.logInfo(TAG, "All menu updates, including sub menus - done.");
            return;
        }

        final SubCellCommandList commandList = commandLists.remove(0);

        DebugTool.logInfo(TAG, "Creating and Sending Dynamic Sub Commands For Root Menu Cell: " + commandList.getMenuTitle());

        // grab the scores
        RunScore score = commandList.getListsScore();
        List<Integer> newIntArray = score.getCurrentMenu();
        List<Integer> oldIntArray = score.getOldMenu();

        // Grab the sub-menus from the parent cell
        final List<MenuCell> oldCells = commandList.getOldList();
        final List<MenuCell> newCells = commandList.getNewList();

        // Create the list for the adds
        List<MenuCell> subCellKeepsNew = new ArrayList<>();

        List<RPCRequest> deleteCommands;

        // Set up deletes
        List<MenuCell> deletes = new ArrayList<>();
        for (int x = 0; x < oldIntArray.size(); x++) {
            Integer old = oldIntArray.get(x);
            if (old.equals(MARKED_FOR_DELETION)) {
                // grab cell to send to function to create delete commands
                deletes.add(oldCells.get(x));
            }
        }
        // create the delete commands
        deleteCommands = createDeleteRPCsForCells(deletes);

        // Set up the adds
        List<MenuCell> adds = new ArrayList<>();
        for (int x = 0; x < newIntArray.size(); x++) {
            Integer newInt = newIntArray.get(x);
            if (newInt.equals(MARKED_FOR_ADDITION)) {
                // grab cell to send to function to create add commands
                adds.add(newCells.get(x));
            } else if (newInt.equals(KEEP)) {
                subCellKeepsNew.add(newCells.get(x));
            }
        }
        final List<MenuCell> addsWithNewIds = updateIdsOnDynamicSubCells(oldCells, adds, commandList.getParentId());
        // this is needed for the onCommands to still work
        transferIdsToKeptSubCells(oldCells, subCellKeepsNew);

        sendDeleteRPCs(deleteCommands, new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                if (addsWithNewIds != null && !addsWithNewIds.isEmpty()) {
                    createAndSendDynamicSubMenuRPCs(newCells, addsWithNewIds, new CompletionListener() {
                        @Override
                        public void onComplete(boolean success) {
                            // recurse through next sub list
                            DebugTool.logInfo(TAG, "Finished Sending Dynamic Sub Commands For Root Menu Cell: " + commandList.getMenuTitle());
                            createSubMenuDynamicCommands(commandLists);
                        }
                    });
                } else {
                    // no add commands to send, recurse through next sub list
                    DebugTool.logInfo(TAG, "Finished Sending Dynamic Sub Commands For Root Menu Cell: " + commandList.getMenuTitle());
                    createSubMenuDynamicCommands(commandLists);
                }
            }
        });
    }

    RunScore runMenuCompareAlgorithm(List<MenuCell> oldCells, List<MenuCell> newCells) {
        if (oldCells == null || oldCells.isEmpty()) {
            return null;
        }

        RunScore bestScore = compareOldAndNewLists(oldCells, newCells);
        DebugTool.logInfo(TAG, "Best menu run score: " + bestScore.getScore());

        return bestScore;
    }

    private RunScore compareOldAndNewLists(List<MenuCell> oldCells, List<MenuCell> newCells) {

        RunScore bestRunScore = null;

        // This first loop is for each 'run'
        for (int run = 0; run < oldCells.size(); run++) {

            List<Integer> oldArray = new ArrayList<>(oldCells.size());
            List<Integer> newArray = new ArrayList<>(newCells.size());

            // Set the statuses
            setDeleteStatus(oldCells.size(), oldArray);
            setAddStatus(newCells.size(), newArray);

            int startIndex = 0;

            // Keep items that appear in both lists
            for (int oldItems = run; oldItems < oldCells.size(); oldItems++) {

                for (int newItems = startIndex; newItems < newCells.size(); newItems++) {

                    if (oldCells.get(oldItems).equals(newCells.get(newItems))) {
                        oldArray.set(oldItems, KEEP);
                        newArray.set(newItems, KEEP);
                        // set the new start index
                        startIndex = newItems + 1;
                        break;
                    }
                }
            }

            // Calculate number of adds, or the 'score' for this run
            int numberOfAdds = 0;

            for (int x = 0; x < newArray.size(); x++) {
                if (newArray.get(x).equals(MARKED_FOR_ADDITION)) {
                    numberOfAdds++;
                }
            }

            // see if we have a new best score and set it if we do
            if (bestRunScore == null || numberOfAdds < bestRunScore.getScore()) {
                bestRunScore = new RunScore(numberOfAdds, oldArray, newArray);
            }

        }
        return bestRunScore;
    }

    private void setDeleteStatus(Integer size, List<Integer> oldArray) {
        for (int i = 0; i < size; i++) {
            oldArray.add(MARKED_FOR_DELETION);
        }
    }

    private void setAddStatus(Integer size, List<Integer> newArray) {
        for (int i = 0; i < size; i++) {
            newArray.add(MARKED_FOR_ADDITION);
        }
    }

    private boolean shouldRPCsIncludeImages(List<MenuCell> cells) {
        for (MenuCell cell : cells) {
            SdlArtwork artwork = cell.getIcon();
            if (artwork != null && !artwork.isStaticIcon() && fileManager.get() != null && !fileManager.get().hasUploadedFile(artwork)) {
                return false;
            } else if (cell.getSubCells() != null && !cell.getSubCells().isEmpty()) {
                return shouldRPCsIncludeImages(cell.getSubCells());
            }
        }
        return true;
    }

    private void updateIdsOnDynamicCells(List<MenuCell> dynamicCells) {
        if (menuCells != null && !menuCells.isEmpty() && dynamicCells != null && !dynamicCells.isEmpty()) {
            for (int z = 0; z < menuCells.size(); z++) {
                MenuCell mainCell = menuCells.get(z);
                for (int i = 0; i < dynamicCells.size(); i++) {
                    MenuCell dynamicCell = dynamicCells.get(i);
                    if (mainCell.equals(dynamicCell)) {
                        int newId = ++lastMenuId;
                        menuCells.get(z).setCellId(newId);
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
                    if (mainCell.equals(dynamicCell)) {
                        int newId = ++lastMenuId;
                        oldList.get(z).setCellId(newId);
                        dynamicCells.get(i).setParentCellId(parentId);
                        dynamicCells.get(i).setCellId(newId);
                    } else {
                        int newId = ++lastMenuId;
                        dynamicCells.get(i).setParentCellId(parentId);
                        dynamicCells.get(i).setCellId(newId);
                    }
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
        for (int z = 0; z < oldMenuCells.size(); z++) {
            MenuCell oldCell = oldMenuCells.get(z);
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

    private List<RPCRequest> createDeleteRPCsForCells(List<MenuCell> cells) {
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

    private List<RPCRequest> mainMenuCommandsForCells(List<MenuCell> cellsToAdd, boolean shouldHaveArtwork) {
        List<RPCRequest> builtCommands = new ArrayList<>();

        // We need the index so we will use this type of loop
        for (int z = 0; z < menuCells.size(); z++) {
            MenuCell mainCell = menuCells.get(z);
            for (int i = 0; i < cellsToAdd.size(); i++) {
                MenuCell addCell = cellsToAdd.get(i);
                if (mainCell.equals(addCell)) {
                    if (addCell.getSubCells() != null && !addCell.getSubCells().isEmpty()) {
                        builtCommands.add(subMenuCommandForMenuCell(addCell, shouldHaveArtwork, z));
                    } else {
                        builtCommands.add(commandForMenuCell(addCell, shouldHaveArtwork, z));
                    }
                    break;
                }
            }
        }
        return builtCommands;
    }

    private List<SdlArtwork> findAllArtworksToBeUploadedFromCells(List<MenuCell> cells) {
        // Make sure we can use images in the menus
        if (!supportsImages()) {
            return new ArrayList<>();
        }

        List<SdlArtwork> artworks = new ArrayList<>();
        for (MenuCell cell : cells) {
            if (fileManager.get() != null && fileManager.get().fileNeedsUpload(cell.getIcon())) {
                artworks.add(cell.getIcon());
            }
            if (cell.getSubCells() != null && !cell.getSubCells().isEmpty()) {
                artworks.addAll(findAllArtworksToBeUploadedFromCells(cell.getSubCells()));
            }
        }

        return artworks;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean supportsImages() {
        return defaultMainWindowCapability == null || ManagerUtility.WindowCapabilityUtility.hasImageFieldOfName(defaultMainWindowCapability, ImageFieldName.cmdIcon);
    }

    void setMenuConfiguration(MenuConfiguration menuConfiguration) {
        this.menuConfiguration = menuConfiguration;
    }

    private void finishOperation(boolean success) {
        if (operationCompletionListener != null) {
            operationCompletionListener.onComplete(success, oldMenuCells);
        }
        onFinished();
    }
}
