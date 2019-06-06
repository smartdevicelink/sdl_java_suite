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

package com.smartdevicelink.managers.screen.choiceset.operations;

import android.support.annotation.NonNull;

import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.file.MultipleFileCompletionListener;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.managers.screen.choiceset.ChoiceCell;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.DisplayCapabilities;
import com.smartdevicelink.proxy.rpc.ImageField;
import com.smartdevicelink.proxy.rpc.enums.ImageFieldName;
import com.smartdevicelink.util.DebugTool;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class PreloadChoicesOperation implements Runnable {

	private WeakReference<ISdl> internalInterface;
	private WeakReference<FileManager> fileManager;
	private DisplayCapabilities displayCapabilities;
	private Boolean isVROptional;
	private HashSet<ChoiceCell> cellsToUpload;
	private CompletionListener completionListener;
	private boolean isRunning;

	public PreloadChoicesOperation(ISdl internalInterface, FileManager fileManager, DisplayCapabilities displayCapabilities,
								   Boolean isVROptional, HashSet<ChoiceCell> cellsToPreload, CompletionListener listener){
		this.internalInterface = new WeakReference<>(internalInterface);
		this.fileManager = new WeakReference<>(fileManager);
		this.displayCapabilities = displayCapabilities;
		this.isVROptional = isVROptional;
		this.cellsToUpload = cellsToPreload;
		this.completionListener = listener;
	}

	@Override
	public void run() {
		preloadCellArtworks(new CompletionListener() {
			@Override
			public void onComplete(boolean success) {
				if (success) {
					preloadCells();
				}
			}
		});
	}

	public void removeChoicesFromUpload(HashSet<ChoiceCell> choices){
		if (isRunning){ return; }
		cellsToUpload.removeAll(choices);
	}

	private void preloadCellArtworks(@NonNull final CompletionListener listener){

		List<SdlArtwork> artworksToUpload = new ArrayList<>(cellsToUpload.size());
		for (ChoiceCell cell : cellsToUpload){
			if (hasImageFieldOfName(displayCapabilities, ImageFieldName.choiceImage) && artworkNeedsUpload(cell.getArtwork())){
				artworksToUpload.add(cell.getArtwork());
			}
			if (hasImageFieldOfName(displayCapabilities, ImageFieldName.choiceSecondaryImage) && artworkNeedsUpload(cell.getSecondaryArtwork())){
				artworksToUpload.add(cell.getSecondaryArtwork());
			}
		}

		if (artworksToUpload.size() == 0){
			DebugTool.logInfo("Choice Preload: No Choice Artworks to upload");
			listener.onComplete(true);
			return;
		}

		if (fileManager.get() != null){
			fileManager.get().uploadArtworks(artworksToUpload, new MultipleFileCompletionListener() {
				@Override
				public void onComplete(Map<String, String> errors) {
					if (errors != null && errors.size() > 0){
						DebugTool.logError("Error uploading choice cell Artworks: "+ errors.toString());
						listener.onComplete(false);
					}else{
						DebugTool.logInfo("Choice Artworks Uploaded");
						listener.onComplete(true);
					}
				}
			});
		}else{
			DebugTool.logError("File manager null in choice preload operation");
			listener.onComplete(false);
		}

	}

	private void preloadCells(){

	}

	private boolean artworkNeedsUpload(SdlArtwork artwork){
		if (fileManager.get() != null){
			return (artwork != null && !fileManager.get().hasUploadedFile(artwork) && !artwork.isStaticIcon());
		}
		return false;
	}

	private boolean hasImageFieldOfName(DisplayCapabilities displayCapabilities, ImageFieldName name){
		if (displayCapabilities == null ){ return false; }
		if (displayCapabilities.getGraphicSupported() == null || !displayCapabilities.getGraphicSupported()) { return false; }
		if (displayCapabilities.getImageFields() != null){
			for (ImageField field : displayCapabilities.getImageFields()){
				if (field.getName().equals(name)){
					return true;
				}
			}
		}
		return false;
	}

}