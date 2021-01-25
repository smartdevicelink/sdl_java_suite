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

import androidx.annotation.NonNull;

import com.livio.taskmaster.Queue;
import com.livio.taskmaster.Task;
import com.smartdevicelink.managers.BaseSubManager;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.managers.lifecycle.OnSystemCapabilityListener;
import com.smartdevicelink.managers.lifecycle.SystemCapabilityManager;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.DisplayCapability;
import com.smartdevicelink.proxy.rpc.OnCommand;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.WindowCapability;
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
    private static final int MAX_ID = 2000000000;
    static final int menuCellIdMin = 1;
    static final int parentIdNotFound = MAX_ID;

    private final WeakReference<FileManager> fileManager;
    private List<MenuCell> currentMenuCells;
    private List<MenuCell> oldMenuCells;
    private DynamicMenuUpdatesMode dynamicMenuUpdatesMode;
    private MenuConfiguration menuConfiguration;
    private SdlMsgVersion sdlMsgVersion;
    private String displayType;
    private HMILevel oldHMILevel;
    private HMILevel currentHMILevel;
    private SystemContext oldSystemContext;
    private SystemContext currentSystemContext;
    private OnRPCNotificationListener hmiListener;
    private OnRPCNotificationListener commandListener;
    private OnSystemCapabilityListener onDisplaysCapabilityListener;
    private WindowCapability defaultMainWindowCapability;
    private Queue transactionQueue;

    BaseMenuManager(@NonNull ISdl internalInterface, @NonNull FileManager fileManager) {
        super(internalInterface);

        this.fileManager = new WeakReference<>(fileManager);
        this.currentSystemContext = SystemContext.SYSCTXT_MAIN;
        this.currentHMILevel = HMILevel.HMI_NONE;
        this.dynamicMenuUpdatesMode = DynamicMenuUpdatesMode.ON_WITH_COMPAT_MODE;
        this.sdlMsgVersion = internalInterface.getSdlMsgVersion();
        this.transactionQueue = newTransactionQueue();

        addListeners();
    }

    @Override
    public void start(CompletionListener listener) {
        transitionToState(READY);
        super.start(listener);
    }

    @Override
    public void dispose() {
        currentMenuCells = null;
        oldMenuCells = null;
        currentHMILevel = HMILevel.HMI_NONE;
        currentSystemContext = SystemContext.SYSCTXT_MAIN;
        dynamicMenuUpdatesMode = DynamicMenuUpdatesMode.ON_WITH_COMPAT_MODE;
        defaultMainWindowCapability = null;
        menuConfiguration = null;
        sdlMsgVersion = null;

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
        // Create a deep copy of the list so future changes by developers don't affect the algorithm logic
        final List<MenuCell> clonedCells = cloneMenuCellsList(cells);

        // Set old list
        if (currentMenuCells != null) {
            oldMenuCells = new ArrayList<>(currentMenuCells);
        }
        // Copy new list
        currentMenuCells = new ArrayList<>();
        if (clonedCells != null && !clonedCells.isEmpty()) {
            currentMenuCells.addAll(clonedCells);
        }

        // HashSet order doesn't matter / doesn't allow duplicates
        HashSet<String> titleCheckSet = new HashSet<>();
        HashSet<String> allMenuVoiceCommands = new HashSet<>();
        int voiceCommandCount = 0;

        for (MenuCell cell : currentMenuCells) {
            titleCheckSet.add(cell.getTitle());
            if (cell.getVoiceCommands() != null) {
                allMenuVoiceCommands.addAll(cell.getVoiceCommands());
                voiceCommandCount += cell.getVoiceCommands().size();
            }
        }

        // Check for duplicate titles
        if (titleCheckSet.size() != currentMenuCells.size()) {
            DebugTool.logError(TAG, "Not all cell titles are unique. The menu will not be set");
            return;
        }

        // Check for duplicate voice commands
        if (allMenuVoiceCommands.size() != voiceCommandCount) {
            DebugTool.logError(TAG, "Attempted to create a menu with duplicate voice commands. Voice commands must be unique. The menu will not be set");
            return;
        }

        // Cancel pending MenuReplaceOperations
        for (Task operation : transactionQueue.getTasksAsList()) {
            if (!(operation instanceof MenuReplaceStaticOperation)) {
                continue;
            }
            operation.cancelTask();
        }

        MenuReplaceStaticOperation operation = new MenuReplaceStaticOperation(internalInterface, fileManager.get(), displayType, dynamicMenuUpdatesMode, menuConfiguration, defaultMainWindowCapability, oldMenuCells, currentMenuCells, new MenuManagerCompletionListener() {
            @Override
            public void onComplete(boolean success, List<MenuCell> oldMenuCells) {
                BaseMenuManager.this.oldMenuCells = oldMenuCells;
            }
        });
        transactionQueue.add(operation, false);
    }

    /**
     * Returns current list of menu cells
     *
     * @return a List of Currently set menu cells
     */
    public List<MenuCell> getMenuCells() {
        return currentMenuCells;
    }

    /**
     * Opens the Main Menu
     */
    public boolean openMenu() {
        if (sdlMsgVersion.getMajorVersion() < 6) {
            DebugTool.logWarning(TAG, "Menu opening is only supported on head units with RPC spec version 6.0.0 or later. Currently connected head unit RPC spec version is: " + sdlMsgVersion.getMajorVersion() + "." + sdlMsgVersion.getMinorVersion() + "." + sdlMsgVersion.getPatchVersion());
            return false;
        }

        MenuShowOperation operation = new MenuShowOperation(internalInterface, null);
        transactionQueue.add(operation, false);

        return true;
    }

    /**
     * Opens a subMenu. The cell you pass in must be constructed with {@link MenuCell(String,SdlArtwork,List)}
     *
     * @param cell - A <Strong>SubMenu</Strong> cell whose sub menu you wish to open
     */
    public boolean openSubMenu(@NonNull MenuCell cell) {
        if (sdlMsgVersion.getMajorVersion() < 6) {
            DebugTool.logWarning(TAG, "Sub menu opening is only supported on head units with RPC spec version 6.0.0 or later. Currently connected head unit RPC spec version is: " + sdlMsgVersion.getMajorVersion() + "." + sdlMsgVersion.getMinorVersion() + "." + sdlMsgVersion.getPatchVersion());
            return false;
        }

        if (currentMenuCells == null) {
            DebugTool.logError(TAG, "open sub menu called, but no Menu cells have been set");
            return false;
        }

        // We must see if we have a copy of this cell, since we clone the objects
        for (MenuCell clonedCell : currentMenuCells) {
            if (clonedCell.equals(cell) && clonedCell.getCellId() != MAX_ID) {
                // We've found the correct sub menu cell
                MenuShowOperation operation = new MenuShowOperation(internalInterface, clonedCell.getCellId());
                transactionQueue.add(operation, false);
                return true;
            }
        }

        return false;
    }


    /**
     * This method is called via the screen manager to set the menuConfiguration.
     * This will be used when a menuCell with sub-cells has a null value for SubMenuLayout
     *
     * @param menuConfiguration - The default menuConfiguration
     */
    public void setMenuConfiguration(@NonNull final MenuConfiguration menuConfiguration) {
        if (sdlMsgVersion == null) {
            DebugTool.logError(TAG, "SDL Message Version is null. Cannot set Menu Configuration");
            return;
        }

        if (sdlMsgVersion.getMajorVersion() < 6) {
            DebugTool.logWarning(TAG, "Menu configurations is only supported on head units with RPC spec version 6.0.0 or later. Currently connected head unit RPC spec version is: " + sdlMsgVersion.getMajorVersion() + "." + sdlMsgVersion.getMinorVersion() + "." + sdlMsgVersion.getPatchVersion());
            return;
        }

        if (menuConfiguration.getMenuLayout() == null) {
            DebugTool.logInfo(TAG, "Menu Layout is null, not sending setGlobalProperties");
            return;
        }

        MenuConfigurationUpdateOperation operation = new MenuConfigurationUpdateOperation(internalInterface, defaultMainWindowCapability, menuConfiguration, new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                BaseMenuManager.this.menuConfiguration = menuConfiguration;

                // Update pending operations with new menuConfiguration
                for (Task task : transactionQueue.getTasksAsList()) {
                    if (!(task instanceof MenuReplaceStaticOperation)) {
                        continue;
                    }
                    ((MenuReplaceStaticOperation) task).setMenuConfiguration(menuConfiguration);
                }
            }
        });

        // Cancel previous menu configuration operations
        for (Task task : transactionQueue.getTasksAsList()) {
            if (!(task instanceof MenuConfigurationUpdateOperation)) {
                continue;
            }
            task.cancelTask();
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
                            defaultMainWindowCapability = windowCapability;
                        }
                    }
                }
            }

            @Override
            public void onError(String info) {
                DebugTool.logError(TAG, "Display Capability cannot be retrieved");
                defaultMainWindowCapability = null;
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
                oldHMILevel = currentHMILevel;
                currentHMILevel = onHMIStatus.getHmiLevel();
                oldSystemContext = currentSystemContext;
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

    private Queue newTransactionQueue() {
        Queue queue = internalInterface.getTaskmaster().createQueue("MenuManager", 7, false);
        queue.pause();
        return queue;
    }

    private void updateTransactionQueueSuspended() {
        if (oldHMILevel == HMILevel.HMI_NONE && currentHMILevel != HMILevel.HMI_NONE && currentSystemContext != SystemContext.SYSCTXT_MENU) {
            // Resume queue if we were in NONE and now we are not
            DebugTool.logInfo(TAG, "We now have proper HMI, sending waiting update");
            transactionQueue.resume();
        } else if (oldSystemContext == SystemContext.SYSCTXT_MENU && currentSystemContext != SystemContext.SYSCTXT_MENU && currentHMILevel != HMILevel.HMI_NONE) {
            // If we don't check for this and only update when not in the menu, there can be IN_USE errors, especially with submenus.
            // We also don't want to encourage changing out the menu while the user is using it for usability reasons.
            DebugTool.logInfo(TAG, "We now have a proper system context, sending waiting update");
            transactionQueue.resume();
        } else if (currentHMILevel == HMILevel.HMI_NONE || currentSystemContext == SystemContext.SYSCTXT_MENU){
            transactionQueue.pause();
        }
    }

    private List<MenuCell> cloneMenuCellsList(List<MenuCell> originalList) {
        if (originalList == null) {
            return null;
        }

        List<MenuCell> clone = new ArrayList<>();
        for (MenuCell menuCell : originalList) {
            clone.add(menuCell.clone());
        }
        return clone;
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
            if (cell.getSubCells() != null && !cell.getSubCells().isEmpty()) {
                // for each cell, if it has sub cells, recursively loop through those as well
                return callListenerForCells(cell.getSubCells(), command);
            }
        }

        return false;
    }
}
