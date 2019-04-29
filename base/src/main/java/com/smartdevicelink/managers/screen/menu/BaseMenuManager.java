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

import android.support.annotation.NonNull;

import com.smartdevicelink.managers.BaseSubManager;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.screen.menu.cells.MenuCell;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.SystemCapabilityManager;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.interfaces.OnSystemCapabilityListener;
import com.smartdevicelink.proxy.rpc.AddCommand;
import com.smartdevicelink.proxy.rpc.AddSubMenu;
import com.smartdevicelink.proxy.rpc.DisplayCapabilities;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.MenuParams;
import com.smartdevicelink.proxy.rpc.OnCommand;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.RegisterAppInterfaceResponse;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;
import com.smartdevicelink.proxy.rpc.enums.SystemContext;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.util.DebugTool;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

abstract class BaseMenuManager extends BaseSubManager {

	private final WeakReference<FileManager> fileManager;

	private List<MenuCell> menuCells;
	private List<MenuCell> waitingUpdateMenuCells;
	private List<MenuCell> oldMenuCells;
	private List<RPCMessage> inProgressUpdate;

	private boolean waitingOnHMIUpdate;
	private boolean hasQueuedUpdate;
	private HMILevel currentHMILevel;

	private OnRPCNotificationListener hmiListener;
	private OnRPCNotificationListener commandListener;
	private DisplayCapabilities displayCapabilities;

	private static final int parentIdNotFound = Integer.MAX_VALUE;
	private static final int menuCellIdMin = 1;
	private int lastMenuId;

	private SystemContext currentSystemContext;

	public BaseMenuManager(@NonNull ISdl internalInterface, @NonNull FileManager fileManager) {

		super(internalInterface);

		// Set up some Vars
		this.fileManager = new WeakReference<>(fileManager);
		menuCells = new ArrayList<>();
		oldMenuCells = new ArrayList<>();
		waitingUpdateMenuCells = new ArrayList<>();
		inProgressUpdate = new ArrayList<>();

		lastMenuId = menuCellIdMin;
		addListeners();
	}

	@Override
	public void start(CompletionListener listener) {
		transitionToState(READY);
		super.start(listener);
	}

	@Override
	public void dispose(){

		lastMenuId = menuCellIdMin;
		menuCells = null;
		oldMenuCells = null;
		currentHMILevel = null;
		currentSystemContext = SystemContext.SYSCTXT_MAIN;
		displayCapabilities = null;
		inProgressUpdate = null;
		hasQueuedUpdate = false;
		waitingOnHMIUpdate = false;
		waitingUpdateMenuCells = null;

		// remove listeners
		internalInterface.removeOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, hmiListener);
		internalInterface.removeOnRPCNotificationListener(FunctionID.ON_COMMAND, commandListener);

		super.dispose();
	}

	// COMMANDS / SUBMENU RPCs

	private List<RPCRequest> mainMenuCommandsForCells(List<MenuCell> cells, boolean shouldHaveArtwork) {
		List<RPCRequest> builtCommands = new ArrayList<>();

		// We need the index so we will use this type of loop
		for (int i = 0; i < cells.size(); i++) {
			MenuCell cell = cells.get(i);
			if (cell.getSubCells().size() > 0){
				builtCommands.add(subMenuCommandForMenuCell(cell, shouldHaveArtwork, i));
			}else{
				builtCommands.add(commandForMenuCell(cell, shouldHaveArtwork, i));
			}
		}
		return builtCommands;
	}

	private List<RPCRequest> subMenuCommandsForCells(List<MenuCell> cells, boolean shouldHaveArtwork){
		List<RPCRequest> builtCommands = new ArrayList<>();
		for (MenuCell cell : cells){
			if (cell.getSubCells().size() > 0){
				builtCommands.addAll(allCommandsForCells(cell.getSubCells(), shouldHaveArtwork));
			}
		}
		return builtCommands;
	}

	private List<RPCRequest> allCommandsForCells(List<MenuCell> cells, boolean shouldHaveArtwork){
		List<RPCRequest> builtCommands = new ArrayList<>();

		// We need the index so we will use this type of loop
		for (int i = 0; i < cells.size(); i++) {
			MenuCell cell = cells.get(i);
			if (cell.getSubCells().size() > 0){
				builtCommands.add(subMenuCommandForMenuCell(cell, shouldHaveArtwork, i));
				// recursively grab the commands for all the sub cells
				builtCommands.addAll(allCommandsForCells(cell.getSubCells(), shouldHaveArtwork));
			}else{
				builtCommands.add(commandForMenuCell(cell, shouldHaveArtwork, i));
			}
		}

		return builtCommands;
	}

	private AddCommand commandForMenuCell(MenuCell cell, boolean shouldHaveArtwork, int position){

		MenuParams params = new MenuParams(cell.getTitle());
		params.setParentID(cell.getCellId() != Integer.MAX_VALUE ? cell.getParentCellId() : null);
		params.setPosition(position);

		AddCommand command = new AddCommand(cell.getCellId());
		command.setMenuParams(params);
		command.setVrCommands(cell.getVoiceCommands());
		command.setCmdIcon((cell.getIcon() != null && shouldHaveArtwork) ? cell.getIcon().getImageRPC() : null);

		return command;
	}

	private AddSubMenu subMenuCommandForMenuCell(MenuCell cell, boolean shouldHaveArtwork, int position){
		AddSubMenu subMenu = new AddSubMenu(cell.getCellId(), cell.getTitle());
		subMenu.setPosition(position);
		subMenu.setMenuIcon((shouldHaveArtwork && (cell.getIcon().getName() != null)) ? cell.getIcon().getImageRPC() : null);
		return subMenu;
	}

	// CELL COMMAND HANDLING

	private boolean callListenerForCells(List<MenuCell> cells, OnCommand command){
		for (MenuCell cell : cells){
			if (cell.getCellId() == command.getCmdID() && cell.getMenuSelectionListener() != null){
				cell.getMenuSelectionListener().onTriggered(command.getTriggerSource());
				return true;
			}

			if (cell.getSubCells().size() > 0){
				// for each cell, if it has sub cells, recursively loop through those as well
				if (callListenerForCells(cell.getSubCells(), command)) {
					return true;
				}
			}
		}
		return false;
	}

	// LISTENERS

	private void addListeners(){

		// DISPLAY CAPABILITIES - via SCM
		internalInterface.getCapability(SystemCapabilityType.DISPLAY, new OnSystemCapabilityListener() {
			@Override
			public void onCapabilityRetrieved(Object capability) {
				displayCapabilities = (DisplayCapabilities) capability;
			}

			@Override
			public void onError(String info) {
				DebugTool.logError("Unable to retrieve display capabilities: "+ info);
			}
		});

		// HMI UPDATES
		hmiListener = new OnRPCNotificationListener() {
			@Override
			public void onNotified(RPCNotification notification) {
				HMILevel oldHMILevel = currentHMILevel;
				currentHMILevel = ((OnHMIStatus) notification).getHmiLevel();
				// TODO
			}
		};
		internalInterface.addOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, hmiListener);

		// COMMANDS
		commandListener = new OnRPCNotificationListener() {
			@Override
			public void onNotified(RPCNotification notification) {
				OnCommand onCommand = (OnCommand) notification;
				callListenerForCells(menuCells, onCommand);
			}
		};
		internalInterface.addOnRPCNotificationListener(FunctionID.ON_COMMAND, commandListener);
	}
}
