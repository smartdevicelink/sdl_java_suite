/*
 * Copyright (c) 2019 Livio, Inc.
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

import static com.smartdevicelink.managers.screen.menu.MenuReplaceUtilities.cloneMenuCellsList;

import androidx.annotation.NonNull;

import com.livio.taskmaster.Queue;
import com.livio.taskmaster.Task;
import com.smartdevicelink.managers.BaseSubManager;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.lifecycle.OnSystemCapabilityListener;
import com.smartdevicelink.managers.lifecycle.SystemCapabilityManager;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.DisplayCapability;
import com.smartdevicelink.proxy.rpc.OnCommand;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.WindowCapability;
import com.smartdevicelink.proxy.rpc.enums.DisplayType;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.PredefinedWindows;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;
import com.smartdevicelink.proxy.rpc.enums.SystemContext;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.util.DebugTool;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

abstract class BaseMenuManager extends BaseSubManager {
    private static final String TAG = "BaseMenuManager";
    static final int parentIdNotFound = 2000000000;

    final WeakReference<FileManager> fileManager;
    List<MenuCell> currentMenuCells;
    List<MenuCell> menuCells;
    DynamicMenuUpdatesMode dynamicMenuUpdatesMode;
    MenuConfiguration currentMenuConfiguration;
    MenuConfiguration menuConfiguration;
    private String displayType;
    HMILevel currentHMILevel;
    SystemContext currentSystemContext;
    OnRPCNotificationListener hmiListener;
    OnRPCNotificationListener commandListener;
    OnSystemCapabilityListener onDisplaysCapabilityListener;
    WindowCapability windowCapability;
    Queue transactionQueue;

    BaseMenuManager(@NonNull ISdl internalInterface, @NonNull FileManager fileManager) {
        super(internalInterface);

        this.menuConfiguration = new MenuConfiguration(null, null);
        this.menuCells = new ArrayList<>();
        this.currentMenuCells = new ArrayList<>();
        this.dynamicMenuUpdatesMode = DynamicMenuUpdatesMode.ON_WITH_COMPAT_MODE;
        this.transactionQueue = newTransactionQueue();
        this.fileManager = new WeakReference<>(fileManager);
        this.currentSystemContext = SystemContext.SYSCTXT_MAIN;
        this.currentHMILevel = HMILevel.HMI_NONE;
        addListeners();
    }

    @Override
    public void start(CompletionListener listener) {
        transitionToState(READY);
        super.start(listener);
    }

    @Override
    public void dispose() {
        menuCells = new ArrayList<>();
        currentMenuCells = new ArrayList<>();
        currentHMILevel = HMILevel.HMI_NONE;
        currentSystemContext = SystemContext.SYSCTXT_MAIN;
        dynamicMenuUpdatesMode = DynamicMenuUpdatesMode.ON_WITH_COMPAT_MODE;
        windowCapability = null;
        menuConfiguration = null;
        currentMenuConfiguration = null;

        // Cancel the operations
        if (transactionQueue != null) {
            transactionQueue.close();
            transactionQueue = null;
        }

        // Remove listeners
        internalInterface.removeOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, hmiListener);
        internalInterface.removeOnRPCNotificationListener(FunctionID.ON_COMMAND, commandListener);
        if (internalInterface.getSystemCapabilityManager() != null) {
            internalInterface.getSystemCapabilityManager().removeOnSystemCapabilityListener(SystemCapabilityType.DISPLAYS, onDisplaysCapabilityListener);
        }

        super.dispose();
    }

    private Queue newTransactionQueue() {
        Queue queue = internalInterface.getTaskmaster().createQueue("MenuManager", 7, false);
        queue.pause();
        return queue;
    }

    // Suspend the queue if the HMI level is NONE since we want to delay sending RPCs until we're in non-NONE
    private void updateTransactionQueueSuspended() {
        if (currentHMILevel == HMILevel.HMI_NONE || currentSystemContext == SystemContext.SYSCTXT_MENU) {
            DebugTool.logInfo(TAG, String.format("Suspending the transaction queue. Current HMI level is: %s, current system context is: %s", currentHMILevel, currentSystemContext));
            if (transactionQueue != null) {
                transactionQueue.pause();
            }
        } else {
            DebugTool.logInfo(TAG, "Starting the transaction queue");
            if (transactionQueue != null) {
                transactionQueue.resume();
            }
        }
    }

    public void setDynamicUpdatesMode(@NonNull DynamicMenuUpdatesMode value) {
        this.dynamicMenuUpdatesMode = value;
    }

    /**
     * @return The currently set DynamicMenuUpdatesMode. It defaults to ON_WITH_COMPAT_MODE
     */
    public DynamicMenuUpdatesMode getDynamicMenuUpdatesMode() {
        return this.dynamicMenuUpdatesMode;
    }

    /**
     * Creates and sends all associated Menu RPCs
     *
     * @param cells - the menu cells that are to be sent to the head unit, including their sub-cells.
     */
    public void setMenuCells(@NonNull List<MenuCell> cells) {
        if (transactionQueue == null) {
            DebugTool.logError(TAG, "Queue is null, cannot perform a text/graphic update");
            return;
        }

        if (cells == null) {
            DebugTool.logError(TAG, "Cells list is null. Skipping...");
            return;
        }

        if (!menuCellsAreUnique(cells, new ArrayList<String>())) {
            DebugTool.logError(TAG, "Not all set menu cells are unique, but that is required");
            return;
        }

        // Create a deep copy of the list so future changes by developers don't affect the algorithm logic
        this.menuCells = cloneMenuCellsList(cells);

        boolean isDynamicMenuUpdateActive = isDynamicMenuUpdateActive(dynamicMenuUpdatesMode, displayType);
        Task operation = new MenuReplaceOperation(internalInterface, fileManager.get(), windowCapability, currentMenuConfiguration, currentMenuCells, menuCells, isDynamicMenuUpdateActive, new MenuManagerCompletionListener() {
            @Override
            public void onComplete(boolean success, List<MenuCell> currentMenuCells) {
                BaseMenuManager.this.currentMenuCells = currentMenuCells;
                updateMenuReplaceOperationsWithNewCurrentMenu();
                DebugTool.logInfo(TAG, "Finished updating menu");
            }
        });

        // Cancel previous MenuReplaceOperations
        for (Task task : transactionQueue.getTasksAsList()) {
            if (task instanceof MenuReplaceOperation) {
                task.cancelTask();
            }
        }

        transactionQueue.add(operation, false);
    }

    /**
     * Returns current list of menu cells
     *
     * @return a List of Currently set menu cells
     */
    public List<MenuCell> getMenuCells() {
        return menuCells;
    }

    private boolean openMenuPrivate(MenuCell cell) {
        if (transactionQueue == null) {
            DebugTool.logError(TAG, "The queue is null, cannot open submenu");
            return false;
        }
        MenuCell foundClonedCell = null;

        if (cell != null) {
            // We must see if we have a copy of this cell, since we clone the objects
            for (MenuCell clonedCell : currentMenuCells) {
                if (clonedCell.equals(cell) && clonedCell.getCellId() != parentIdNotFound) {
                    // We've found the correct sub menu cell
                    foundClonedCell = clonedCell;
                    break;
                }
            }
        }

        if (cell != null && (!cell.isSubMenuCell())) {
            DebugTool.logError(TAG, String.format("The cell %s does not contain any sub cells, so no submenu can be opened", cell.getTitle()));
            return false;
        } else if (cell != null && foundClonedCell == null) {
            DebugTool.logError(TAG, "This cell has not been sent to the head unit, so no submenu can be opened. Make sure that the cell exists in the SDLManager.menu list");
            return false;
        } else if (internalInterface.getSdlMsgVersion().getMajorVersion() < 6) {
            DebugTool.logWarning(TAG, "The openSubmenu method is not supported on this head unit.");
            return false;
        }

        // Create the operation
        MenuShowOperation operation = new MenuShowOperation(internalInterface, foundClonedCell);

        // Cancel previous open menu operations
        for (Task task : transactionQueue.getTasksAsList()) {
            if (task instanceof MenuShowOperation) {
                task.cancelTask();
            }
        }

        transactionQueue.add(operation, false);

        return true;
    }

    /**
     * Opens the Main Menu
     */
    public boolean openMenu() {
        return openMenuPrivate(null);
    }

    /**
     * Opens a subMenu.
     *
     * @param cell - A <Strong>SubMenu</Strong> cell whose sub menu you wish to open
     */
    public boolean openSubMenu(@NonNull MenuCell cell) {
        return openMenuPrivate(cell);
    }


    /**
     * This method is called via the screen manager to set the menuConfiguration.
     * The menuConfiguration.SubMenuLayout value will be used when a menuCell with sub-cells has a null value for SubMenuLayout
     *
     * @param menuConfiguration - The default menuConfiguration
     */
    public void setMenuConfiguration(@NonNull final MenuConfiguration menuConfiguration) {
        if (transactionQueue == null) {
            DebugTool.logError(TAG, "Queue is null, cannot set menu configuration");
        }
        if (menuConfiguration.equals(this.menuConfiguration)) {
            DebugTool.logInfo(TAG, "New menu configuration is equal to existing one, will not set new configuration");
            return;
        }

        this.menuConfiguration = menuConfiguration;

        MenuConfigurationUpdateOperation operation = new MenuConfigurationUpdateOperation(internalInterface, windowCapability, menuConfiguration, new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                if (!success) {
                    DebugTool.logError(TAG, "Error updating menu configuration.");
                    return;
                }
                BaseMenuManager.this.currentMenuConfiguration = menuConfiguration;
                updateMenuReplaceOperationsWithNewMenuConfiguration();
            }
        });

        // Cancel previous menu configuration operations
        for (Task task : transactionQueue.getTasksAsList()) {
            if (task instanceof MenuConfigurationUpdateOperation) {
                task.cancelTask();
            }
        }

        transactionQueue.add(operation, false);
    }

    public MenuConfiguration getMenuConfiguration() {
        return this.menuConfiguration;
    }

    private void addListeners() {
        onDisplaysCapabilityListener = new OnSystemCapabilityListener() {
            @Override
            public void onCapabilityRetrieved(Object capability) {
                // instead of using the parameter it's more safe to use the convenience method
                List<DisplayCapability> capabilities = SystemCapabilityManager.convertToList(capability, DisplayCapability.class);
                if (capabilities == null || capabilities.size() == 0) {
                    DebugTool.logError(TAG, "Capabilities sent here are null or empty");
                } else {
                    DisplayCapability display = capabilities.get(0);
                    displayType = display.getDisplayName();
                    for (WindowCapability windowCapability : display.getWindowCapabilities()) {
                        int currentWindowID = windowCapability.getWindowID() != null ? windowCapability.getWindowID() : PredefinedWindows.DEFAULT_WINDOW.getValue();
                        if (currentWindowID == PredefinedWindows.DEFAULT_WINDOW.getValue()) {
                            BaseMenuManager.this.windowCapability = windowCapability;
                            updateMenuReplaceOperationsWithNewWindowCapability();
                        }
                    }
                }
            }

            @Override
            public void onError(String info) {
                DebugTool.logError(TAG, "Display Capability cannot be retrieved");
                windowCapability = null;
            }
        };
        if (internalInterface.getSystemCapabilityManager() != null) {
            this.internalInterface.getSystemCapabilityManager().addOnSystemCapabilityListener(SystemCapabilityType.DISPLAYS, onDisplaysCapabilityListener);
        }

        hmiListener = new OnRPCNotificationListener() {
            @Override
            public void onNotified(RPCNotification notification) {
                OnHMIStatus onHMIStatus = (OnHMIStatus) notification;
                if (onHMIStatus.getWindowID() != null && onHMIStatus.getWindowID() != PredefinedWindows.DEFAULT_WINDOW.getValue()) {
                    return;
                }
                currentHMILevel = onHMIStatus.getHmiLevel();
                currentSystemContext = onHMIStatus.getSystemContext();
                updateTransactionQueueSuspended();
            }
        };
        internalInterface.addOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, hmiListener);

        commandListener = new OnRPCNotificationListener() {
            @Override
            public void onNotified(RPCNotification notification) {
                OnCommand onCommand = (OnCommand) notification;
                callListenerForCells(currentMenuCells, onCommand);
            }
        };
        internalInterface.addOnRPCNotificationListener(FunctionID.ON_COMMAND, commandListener);
    }

    private boolean callListenerForCells(List<MenuCell> cells, OnCommand command) {
        if (cells == null || cells.isEmpty() || command == null) {
            return false;
        }

        for (MenuCell cell : cells) {
            if (cell.getCellId() == command.getCmdID() && cell.getMenuSelectionListener() != null) {
                cell.getMenuSelectionListener().onTriggered(command.getTriggerSource());
                return true;
            }
            if (cell.isSubMenuCell() && !cell.getSubCells().isEmpty()) {
                // for each cell, if it has sub cells, recursively loop through those as well
                boolean success = callListenerForCells(cell.getSubCells(), command);
                if (success) {
                    return true;
                }
            }
        }

        return false;
    }

    private void updateMenuReplaceOperationsWithNewCurrentMenu() {
        if (transactionQueue == null) {
            return;
        }
        for (Task task : transactionQueue.getTasksAsList()) {
            if (task instanceof MenuReplaceOperation) {
                ((MenuReplaceOperation) task).setCurrentMenu(this.currentMenuCells);
            }
        }
    }

    private void updateMenuReplaceOperationsWithNewWindowCapability() {
        if (transactionQueue == null) {
            return;
        }
        for (Task task : transactionQueue.getTasksAsList()) {
            if (task instanceof MenuReplaceOperation) {
                ((MenuReplaceOperation) task).setWindowCapability(this.windowCapability);
            }
        }
    }

    private void updateMenuReplaceOperationsWithNewMenuConfiguration() {
        if (transactionQueue == null) {
            return;
        }
        for (Task task : transactionQueue.getTasksAsList()) {
            if (task instanceof MenuReplaceOperation) {
                ((MenuReplaceOperation) task).setMenuConfiguration(currentMenuConfiguration);
            }
        }
    }

    private boolean isDynamicMenuUpdateActive(DynamicMenuUpdatesMode updateMode, String displayType) {
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

    /**
     * Check for cell lists with completely duplicate information, or any duplicate voiceCommands
     *
     * @param cells            List of MenuCell's you will be adding
     * @param allVoiceCommands List of String's for VoiceCommands (Used for recursive calls to check voiceCommands of the cells)
     * @return Boolean that indicates whether menuCells are unique or not
     */
    private boolean menuCellsAreUnique(List<MenuCell> cells, ArrayList<String> allVoiceCommands) {
        // Check all voice commands for identical items and check each list of cells for identical cells
        HashSet<MenuCell> identicalCellsCheckSet = new HashSet<>();

        for (MenuCell cell : cells) {
            identicalCellsCheckSet.add(cell);

            // Recursively check the sub-cell lists to see if they are all unique as well. If anything is not, this will chain back up the list to return false.
            if (cell.isSubMenuCell() && cell.getSubCells().size() > 0) {
                boolean subCellsAreUnique = menuCellsAreUnique(cell.getSubCells(), allVoiceCommands);

                if (!subCellsAreUnique) {
                    DebugTool.logError(TAG, "Not all subCells are unique. The menu will not be set.");
                    return false;
                }
            }

            // Voice commands have to be identical across all lists
            if (cell.getVoiceCommands() != null) {
                allVoiceCommands.addAll(cell.getVoiceCommands());
            }
        }

        // Check for duplicate cells
        if (identicalCellsCheckSet.size() != cells.size()) {
            DebugTool.logError(TAG, "Not all cells are unique. Cells in each list (such as main menu or sub cell list) must have some differentiating property other than the sub cells within a cell. The menu will not be set.");
            return false;
        }

        // All the VR commands must be unique
        HashSet<String> voiceCommandsSet = new HashSet<>(allVoiceCommands);
        if (allVoiceCommands.size() != voiceCommandsSet.size()) {
            DebugTool.logError(TAG, "Attempted to create a menu with duplicate voice commands. Voice commands must be unique. The menu will not be set");
            return false;
        }

        return true;
    }
}
