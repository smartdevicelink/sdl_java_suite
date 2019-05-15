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

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.util.Log;

import com.smartdevicelink.managers.BaseSubManager;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.file.MultipleFileCompletionListener;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.interfaces.OnSystemCapabilityListener;
import com.smartdevicelink.proxy.rpc.AddCommand;
import com.smartdevicelink.proxy.rpc.AddSubMenu;
import com.smartdevicelink.proxy.rpc.DeleteCommand;
import com.smartdevicelink.proxy.rpc.DeleteSubMenu;
import com.smartdevicelink.proxy.rpc.DisplayCapabilities;
import com.smartdevicelink.proxy.rpc.ImageField;
import com.smartdevicelink.proxy.rpc.MenuParams;
import com.smartdevicelink.proxy.rpc.OnCommand;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.ImageFieldName;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;
import com.smartdevicelink.proxy.rpc.enums.SystemContext;
import com.smartdevicelink.proxy.rpc.listeners.OnMultipleRequestListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.util.DebugTool;

import org.json.JSONException;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

abstract class BaseMenuManager extends BaseSubManager {

	/**
	 * State enums used internally for dynamic updating
	 * These should be used to set the 'state' variable
	 */
	@IntDef({KEEP, MARKED_FOR_ADDITION, MARKED_FOR_DELETION})
	@Retention(RetentionPolicy.SOURCE)
	@interface MenuCellState {}
	static final int KEEP = 0;
	static final int MARKED_FOR_ADDITION = 1;
	static final int MARKED_FOR_DELETION = 2;

	private final WeakReference<FileManager> fileManager;

	List<MenuCell> menuCells, waitingUpdateMenuCells, oldMenuCells, dynamicCells;
	List<RPCRequest> inProgressUpdate, deleteCommands;

	boolean waitingOnHMIUpdate;
	private boolean hasQueuedUpdate;
	HMILevel currentHMILevel;

	OnRPCNotificationListener hmiListener, commandListener;
	OnSystemCapabilityListener displayListener;
	DisplayCapabilities displayCapabilities;

	private static final int MAX_ID = 2000000000;
	private static final int parentIdNotFound = MAX_ID;
	private static final int menuCellIdMin = 1;
	int lastMenuId;

	SystemContext currentSystemContext;

	BaseMenuManager(@NonNull ISdl internalInterface, @NonNull FileManager fileManager) {

		super(internalInterface);

		// Set up some Vars
		this.fileManager = new WeakReference<>(fileManager);
		currentSystemContext = SystemContext.SYSCTXT_MAIN;
		currentHMILevel = HMILevel.HMI_NONE;
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
		deleteCommands = null;
		dynamicCells = null;

		// remove listeners
		internalInterface.removeOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, hmiListener);
		internalInterface.removeOnRPCNotificationListener(FunctionID.ON_COMMAND, commandListener);
		internalInterface.removeOnSystemCapabilityListener(SystemCapabilityType.DISPLAY, displayListener);

		super.dispose();
	}

	// SETTERS

	/**
	 * Creates and sends all associated Menu RPCs
	 * @param cells - the menu cells that are to be sent to the head unit, including their sub-cells.
	 */
	public void setMenuCells(List<MenuCell> cells){

		if (currentHMILevel == null || currentHMILevel.equals(HMILevel.HMI_NONE) || currentSystemContext.equals(SystemContext.SYSCTXT_MENU)){
			// We are in NONE or the menu is in use, bail out of here
			waitingOnHMIUpdate = true;
			waitingUpdateMenuCells = new ArrayList<>(cells);
			return;
		}
		waitingOnHMIUpdate = false;

		// Update our Lists
		this.oldMenuCells = new ArrayList<>(menuCells != null ? menuCells : cells);
		menuCells = new ArrayList<>(cells);

		// HashSet order doesnt matter / does not allow duplicates
		HashSet<String> titleCheckSet = new HashSet<>();
		HashSet<String> allMenuVoiceCommands = new HashSet<>();
		int voiceCommandCount = 0;

		for (MenuCell cell : menuCells){
			titleCheckSet.add(cell.getTitle());
			if (cell.getVoiceCommands() != null){
				allMenuVoiceCommands.addAll(cell.getVoiceCommands());
				voiceCommandCount += cell.getVoiceCommands().size();
			}
		}
		// Check for duplicate titles
		if (titleCheckSet.size() != menuCells.size()){
			DebugTool.logError("Not all cell titles are unique. The menu will not be set");
			return;
		}

		// Check for duplicate voice commands
		if (allMenuVoiceCommands.size() != voiceCommandCount){
			DebugTool.logError("Attempted to create a menu with duplicate voice commands. Voice commands must be unique. The menu will not be set");
			return;
		}

		// Set the IDs
		lastMenuId = menuCellIdMin;
		updateIdsOnMenuCells(menuCells, parentIdNotFound);
		// Upload the Artworks
		List<SdlArtwork> artworksToBeUploaded = findAllArtworksToBeUploadedFromCells(menuCells);
		if (artworksToBeUploaded.size() > 0 && fileManager.get() != null){
			fileManager.get().uploadArtworks(artworksToBeUploaded, new MultipleFileCompletionListener() {
				@Override
				public void onComplete(Map<String, String> errors) {

					if (errors != null && errors.size() > 0){
						DebugTool.logError("Error uploading Menu Artworks: "+ errors.toString());
					}else{
						DebugTool.logInfo("Menu Artworks Uploaded");
					}
					// proceed
					update();
				}
			});
		}else{
			// No Artworks to be uploaded, send off
			update();
		}
	}

	/**
	 * Returns current list of menu cells
	 * @return a List of Currently set menu cells
	 */
	public List<MenuCell> getMenuCells(){
		return menuCells;
	}

	// UPDATING SYSTEM

	private void update(){

		if (currentHMILevel == null || currentHMILevel.equals(HMILevel.HMI_NONE) || currentSystemContext.equals(SystemContext.SYSCTXT_MENU)){
			// We are in NONE or the menu is in use, bail out of here
			DebugTool.logInfo("HMI in None or System Context Menu, returning");
			waitingOnHMIUpdate = true;
			waitingUpdateMenuCells = menuCells;
			return;
		}

		if (inProgressUpdate != null && inProgressUpdate.size() > 0){
			// there's an in-progress update so this needs to wait
			DebugTool.logInfo("There is an in progress Menu Update, returning");
			hasQueuedUpdate = true;
			return;
		}

		// run the lists through the new algorithm
		createDynamicUpdate(oldMenuCells, menuCells);

		deleteCurrentMenu(new CompletionListener() {
			@Override
			public void onComplete(boolean success) {
				sendCurrentMenu(new CompletionListener() {
					@Override
					public void onComplete(boolean success) {
						inProgressUpdate = null;

						if (!success){
							DebugTool.logError("Error Sending Current Menu");
						}

						if (hasQueuedUpdate){
							update();
							hasQueuedUpdate = false;
						}
					}
				});
			}
		});
	}

	// DELETE OLD MENU ITEMS

	private void deleteCurrentMenu(final CompletionListener listener){
		if (oldMenuCells != null && oldMenuCells.size() == 0) {
			if (listener != null){
				// technically this method is successful if there's nothing to delete
				DebugTool.logInfo("No old cells to delete, returning");
				listener.onComplete(true);
			}
			return;
		}

		if (deleteCommands == null || deleteCommands.size() == 0){
			// no dynamic deletes required. return
			if (listener != null){
				// technically this method is successful if there's nothing to delete
				listener.onComplete(true);
			}
			return;
		}

		for (RPCRequest delete : deleteCommands){
			try {
				Log.i("MENU CELL DELETE: ", delete.serializeJSON().toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		//List<RPCRequest> deleteCommands = deleteCommandsForCells(oldMenuCells);
		if (oldMenuCells != null) {
			oldMenuCells.clear();
		}
		internalInterface.sendRequests(deleteCommands, new OnMultipleRequestListener() {
			@Override
			public void onUpdate(int remainingRequests) {

			}

			@Override
			public void onFinished() {
				DebugTool.logInfo("Successfully deleted all old menu commands");
				deleteCommands.clear();
				if (listener != null){
					listener.onComplete(true);
				}
			}

			@Override
			public void onError(int correlationId, Result resultCode, String info) {

			}

			@Override
			public void onResponse(int correlationId, RPCResponse response) {

			}
		});
	}

	// SEND NEW MENU ITEMS

	private void sendCurrentMenu(final CompletionListener listener){

		if (dynamicCells.size() == 0){
			DebugTool.logInfo("No updates to send, returning");
			if (listener != null){
				// This can be considered a success if the user was clearing out their menu
				listener.onComplete(true);
			}
			return;
		}

		List<RPCRequest> mainMenuCommands;
		final List<RPCRequest> subMenuCommands;

		for (MenuCell cell : dynamicCells){
			Log.i("MENU CELL SEND: ", cell.getTitle() + " Position: "+ cell.getCellId());
		}

		if (findAllArtworksToBeUploadedFromCells(dynamicCells).size() > 0 || !supportsImages()){
			// Send artwork-less menu
			mainMenuCommands = mainMenuCommandsForCells(dynamicCells, false);
			subMenuCommands = subMenuCommandsForCells(dynamicCells, false);
		} else {
			mainMenuCommands = mainMenuCommandsForCells(dynamicCells, true);
			subMenuCommands = subMenuCommandsForCells(dynamicCells, true);
		}

		// add all built commands to inProgressUpdate
		inProgressUpdate = new ArrayList<>(mainMenuCommands);
		inProgressUpdate.addAll(subMenuCommands);

		internalInterface.sendSequentialRPCs(mainMenuCommands, new OnMultipleRequestListener() {
			@Override
			public void onUpdate(int remainingRequests) {
				// nothing here
			}

			@Override
			public void onFinished() {

				oldMenuCells = menuCells;
				// we don't need these anymore
				dynamicCells.clear();

				if (subMenuCommands.size() > 0) {
					sendSubMenuCommands(subMenuCommands, listener);
					DebugTool.logInfo("Finished sending main menu commands. Sending sub menu commands.");
				}else{
					inProgressUpdate = null;
					DebugTool.logInfo("Finished sending main menu commands.");
				}
			}

			@Override
			public void onError(int correlationId, Result resultCode, String info) {

			}

			@Override
			public void onResponse(int correlationId, RPCResponse response) {
				try {
					DebugTool.logInfo("Main Menu response: "+ response.serializeJSON().toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});

	}

	private void sendSubMenuCommands(List<RPCRequest> commands, final CompletionListener listener){
		internalInterface.sendSequentialRPCs(commands, new OnMultipleRequestListener() {
			@Override
			public void onUpdate(int remainingRequests) {

			}

			@Override
			public void onFinished() {
				DebugTool.logInfo("Finished Updating Menu");
				inProgressUpdate = null;
				if (listener != null){
					listener.onComplete(true);
				}
			}

			@Override
			public void onError(int correlationId, Result resultCode, String info) {
				DebugTool.logError("Failed to send sub menu commands: "+ info);
				if (listener != null){
					listener.onComplete(false);
				}
			}

			@Override
			public void onResponse(int correlationId, RPCResponse response) {
				try {
					DebugTool.logInfo("Sub Menu response: "+ response.serializeJSON().toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	// OTHER HELPER METHODS:

	// COMPARISONS

	private void createDynamicUpdate(List<MenuCell> oldCells, List<MenuCell> newCells){

		if (oldCells == null || oldCells.size() == 0 || oldCells.equals(newCells)){
			// we don't need to be here as its the initial add, return
			if (dynamicCells == null){
				dynamicCells = new ArrayList<>(newCells);
			}else{
				dynamicCells.addAll(newCells);
			}
			return;
		}

		RunScore bestScore = compareOldAndNewLists(oldCells, newCells);

		// we need to run through the keeps and see if they have subCells, as they also need to be run
		// through the compare function. Take best run score and loop through keeps.
		// Currently submenus can only be one level deep
		List<Integer> newIntArray = bestScore.getCurrentMenu();
		List<Integer> oldIntArray = bestScore.getOldMenu();
		List<MenuCell> keepsNew = new ArrayList<>();
		List<MenuCell> keepsOld = new ArrayList<>();

		// Set up deletes
		List<MenuCell> deletes = new ArrayList<>();
		for (int x = 0; x < oldIntArray.size(); x++) {
			Integer old = oldIntArray.get(x);
			if (old.equals(MARKED_FOR_DELETION)){
				// grab cell to send to function to create delete commands
				deletes.add(oldCells.get(x));
			}else if (old.equals(KEEP)){
				// we need the array of keeps to check for sub cells
				keepsOld.add(oldCells.get(x));
			}
		}
		// create the delete commands
		deleteCommands = deleteCommandsForCells(deletes);

		// Set up the adds
		List<MenuCell> adds = new ArrayList<>();
		for (int x = 0; x < newIntArray.size(); x++) {
			Integer newInt = newIntArray.get(x);
			if (newInt.equals(MARKED_FOR_ADDITION)){
				// grab cell to send to function to create delete commands
				adds.add(newCells.get(x));
			}else if (newInt.equals(KEEP)){
				// we need the array of keeps to check for sub cells
				keepsNew.add(newCells.get(x));
			}
		}
		// add the cells needed to added here
		if (dynamicCells == null){
			dynamicCells = new ArrayList<>(adds);
		}else{
			dynamicCells.addAll(adds);
		}


		// now we can check the keeps lists for sub cells
		if (keepsNew.size() > 0){
			// lets see if we have any cells that contain sub-cells
			for (int i = 0; i < keepsNew.size(); i++) {

				MenuCell newCell = keepsNew.get(i);

				if (newCell.getSubCells() != null && newCell.getSubCells().size() > 0){
					// This cell HAS SUB-CELLS. Get the old cell
					MenuCell oldCell = keepsOld.get(i);

					List<MenuCell> newSubCells = newCell.getSubCells();
					List<MenuCell> oldSubCells = oldCell.getSubCells();

					RunScore subCellScore = compareOldAndNewLists(oldSubCells, newSubCells);

					// Grab the responses so that we can create the add / delete commands
					List<Integer> subCellNewArray = subCellScore.getCurrentMenu();
					List<Integer> subCellOldArray = subCellScore.getOldMenu();

					// Set up deletes
					List<MenuCell> subDeletes = new ArrayList<>();
					for (int x = 0; x < subCellOldArray.size(); x++) {
						Integer old = subCellOldArray.get(x);
						if (old.equals(MARKED_FOR_DELETION)){
							// grab cell to send to function to create delete commands
							subDeletes.add(oldCells.get(x));
						}
					}
					// create the delete commands and append to previous delete list
					deleteCommands.addAll(deleteCommandsForCells(subDeletes));

					// Set up the adds
					List<MenuCell> subAdds = new ArrayList<>();
					for (int x = 0; x < subCellNewArray.size(); x++) {
						Integer newInt = subCellNewArray.get(x);
						if (newInt.equals(MARKED_FOR_ADDITION)){
							// grab cell to send to function to create delete commands
							subAdds.add(newCells.get(x));
						}
					}
					// add subs to the list
					dynamicCells.addAll(subAdds);
				}
			}
		}
	}

	private RunScore compareOldAndNewLists(List<MenuCell> oldCells, List<MenuCell> newCells){

		if (oldCells == null || oldCells.size() == 0 || oldCells.equals(newCells)){
			// we don't need to be here as its the initial add, return
			return null;
		}

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

					if (oldCells.get(oldItems).equals(newCells.get(newItems))){
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
				if (newArray.get(x).equals(MARKED_FOR_ADDITION)){
					numberOfAdds++;
				}
			}

			// see if we have a new best score and set it if we do
			if (bestRunScore == null || numberOfAdds < bestRunScore.getScore()){
				bestRunScore = new RunScore(numberOfAdds, oldArray, newArray);
			}

		}
		return bestRunScore;
	}

	private void setDeleteStatus(Integer size, List<Integer> oldArray){
		for (int i = 0; i < size; i++) {
			oldArray.add(MARKED_FOR_DELETION);
		}
	}

	private void setAddStatus(Integer size, List<Integer> newArray){
		for (int i = 0; i < size; i++) {
			newArray.add(MARKED_FOR_ADDITION);
		}
	}

	// ARTWORKS

	private List<SdlArtwork> findAllArtworksToBeUploadedFromCells(List<MenuCell> cells){
		// Make sure we can use images in the menus
		if (!supportsImages()){
			return new ArrayList<>();
		}

		List<SdlArtwork> artworks = new ArrayList<>();
		for (MenuCell cell : cells){
			if (artworkNeedsUpload(cell.getIcon())){
				artworks.add(cell.getIcon());
			}
			if (cell.getSubCells() != null && cell.getSubCells().size() > 0){
				artworks.addAll(findAllArtworksToBeUploadedFromCells(cell.getSubCells()));
			}
		}

		return artworks;
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	private boolean supportsImages(){
		if (displayCapabilities != null && displayCapabilities.getImageFields() != null) {
			List<ImageField> imageFields = displayCapabilities.getImageFields();
			for (ImageField field : imageFields) {
				if (field.getName().equals(ImageFieldName.cmdIcon)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean artworkNeedsUpload(SdlArtwork artwork){
		if (fileManager.get() != null){
			return (artwork != null && !fileManager.get().hasUploadedFile(artwork) && !artwork.isStaticIcon());
		}
		return false;
	}

	// IDs

	private void updateIdsOnMenuCells(List<MenuCell> cells, int parentId){
		for (MenuCell cell : cells){
			cell.setCellId(++lastMenuId);
			cell.setParentCellId(parentId);
			if (cell.getSubCells() != null && cell.getSubCells().size() > 0){
				updateIdsOnMenuCells(cell.getSubCells(), cell.getCellId());
			}
		}
	}

	// DELETES

	List<RPCRequest> deleteCommandsForCells(List<MenuCell> cells){
		List<RPCRequest> deletes = new ArrayList<>();
		for (MenuCell cell : cells){
			if (cell.getSubCells() == null){
				DeleteCommand delete = new DeleteCommand(cell.getCellId());
				deletes.add(delete);
			}else{
				DeleteSubMenu delete = new DeleteSubMenu(cell.getCellId());
				deletes.add(delete);
			}
		}
		return deletes;
	}

	// COMMANDS / SUBMENU RPCs

	private List<RPCRequest> mainMenuCommandsForCells(List<MenuCell> cells, boolean shouldHaveArtwork) {
		List<RPCRequest> builtCommands = new ArrayList<>();

		// We need the index so we will use this type of loop
		for (int i = 0; i < cells.size(); i++) {
			MenuCell cell = cells.get(i);
			if (cell.getSubCells() != null && cell.getSubCells().size() > 0){
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
			if (cell.getSubCells() != null && cell.getSubCells().size() > 0){
				builtCommands.addAll(allCommandsForCells(cell.getSubCells(), shouldHaveArtwork));
			}
		}
		return builtCommands;
	}

	List<RPCRequest> allCommandsForCells(List<MenuCell> cells, boolean shouldHaveArtwork){
		List<RPCRequest> builtCommands = new ArrayList<>();

		// We need the index so we will use this type of loop
		for (int i = 0; i < cells.size(); i++) {
			MenuCell cell = cells.get(i);
			if (cell.getSubCells() != null && cell.getSubCells().size() > 0){
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
		params.setParentID(cell.getParentCellId() != MAX_ID ? cell.getParentCellId() : 0);
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
		subMenu.setMenuIcon((shouldHaveArtwork && (cell.getIcon()!= null && cell.getIcon().getImageRPC() != null)) ? cell.getIcon().getImageRPC() : null);
		return subMenu;
	}

	// CELL COMMAND HANDLING

	private boolean callListenerForCells(List<MenuCell> cells, OnCommand command){
		if (cells != null && cells.size() > 0 && command != null) {
			for (MenuCell cell : cells) {
				if (cell.getCellId() == command.getCmdID() && cell.getMenuSelectionListener() != null) {
					cell.getMenuSelectionListener().onTriggered(command.getTriggerSource());
					return true;
				}

				if (cell.getSubCells() != null && cell.getSubCells().size() > 0) {
					// for each cell, if it has sub cells, recursively loop through those as well
					if (callListenerForCells(cell.getSubCells(), command)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	// LISTENERS

	private void addListeners(){

		// DISPLAY CAPABILITIES - via SCM
		displayListener = new OnSystemCapabilityListener() {
			@Override
			public void onCapabilityRetrieved(Object capability) {
				displayCapabilities = (DisplayCapabilities) capability;
			}

			@Override
			public void onError(String info) {
				DebugTool.logError("Unable to retrieve display capabilities: "+ info);
			}
		};
		internalInterface.getCapability(SystemCapabilityType.DISPLAY, displayListener);

		// HMI UPDATES
		hmiListener = new OnRPCNotificationListener() {
			@Override
			public void onNotified(RPCNotification notification) {
				OnHMIStatus hmiStatus = (OnHMIStatus) notification;
				HMILevel oldHMILevel = currentHMILevel;
				currentHMILevel = hmiStatus.getHmiLevel();

				// Auto-send an updated menu if we were in NONE and now we are not, and we need an update
				if (oldHMILevel.equals(HMILevel.HMI_NONE) && !currentHMILevel.equals(HMILevel.HMI_NONE) && !currentSystemContext.equals(SystemContext.SYSCTXT_MENU)){
					if (waitingOnHMIUpdate){
						DebugTool.logInfo("We now have proper HMI, sending waiting update");
						setMenuCells(waitingUpdateMenuCells);
						waitingUpdateMenuCells.clear();
						return;
					}
				}

				// If we don't check for this and only update when not in the menu, there can be IN_USE errors, especially with submenus.
				// We also don't want to encourage changing out the menu while the user is using it for usability reasons.
				SystemContext oldContext = currentSystemContext;
				currentSystemContext = hmiStatus.getSystemContext();

				if (oldContext.equals(SystemContext.SYSCTXT_MENU) && !currentSystemContext.equals(SystemContext.SYSCTXT_MENU) && !currentHMILevel.equals(HMILevel.HMI_NONE)){
					if (waitingOnHMIUpdate){
						DebugTool.logInfo("We now have a proper system context, sending waiting update");
						setMenuCells(waitingUpdateMenuCells);
						waitingUpdateMenuCells.clear();
					}
				}
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
