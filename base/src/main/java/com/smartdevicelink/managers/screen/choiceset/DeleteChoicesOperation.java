/*
 * Copyright (c)  2019 Livio, Inc.
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
 *
 * Created by brettywhite on 6/12/19 1:52 PM
 *
 */

package com.smartdevicelink.managers.screen.choiceset;

import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.DeleteInteractionChoiceSet;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.listeners.OnMultipleRequestListener;
import com.smartdevicelink.util.DebugTool;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

class DeleteChoicesOperation implements Runnable {

	private WeakReference<ISdl> internalInterface;
	private HashSet<ChoiceCell> cellsToDelete;
	private CompletionListener completionListener;

	DeleteChoicesOperation(ISdl internalInterface, HashSet<ChoiceCell> cellsToDelete, CompletionListener completionListener){
		this.internalInterface = new WeakReference<>(internalInterface);
		this.cellsToDelete = cellsToDelete;
		this.completionListener = completionListener;
	}

	@Override
	public void run() {
		DebugTool.logInfo("Choice Operation: Executing delete choices operation");
		sendDeletions();
	}

	private void sendDeletions(){

		List<DeleteInteractionChoiceSet> deleteChoices = createDeleteSets();

		if (deleteChoices.size() > 0) {

			if (internalInterface.get() != null) {
				internalInterface.get().sendRequests(deleteChoices, new OnMultipleRequestListener() {
					@Override
					public void onUpdate(int remainingRequests) {
					}

					@Override
					public void onFinished() {
						if (completionListener != null) {
							completionListener.onComplete(true);
						}
						DebugTool.logInfo("Successfully deleted choices");
					}

					@Override
					public void onError(int correlationId, Result resultCode, String info) {
						if (completionListener != null) {
							completionListener.onComplete(false);
						}
						DebugTool.logError("Failed to delete choice: " + info + " | Corr ID: " + correlationId);
					}

					@Override
					public void onResponse(int correlationId, RPCResponse response) {
					}
				});
			}
		} else{
			if (completionListener != null) {
				completionListener.onComplete(true);
			}
			DebugTool.logInfo("No Choices to delete, continue");
		}
	}

	List<DeleteInteractionChoiceSet> createDeleteSets(){
		List<DeleteInteractionChoiceSet> deleteChoices = new ArrayList<>(cellsToDelete.size());
		for (ChoiceCell cell : cellsToDelete){
			deleteChoices.add(new DeleteInteractionChoiceSet(cell.getChoiceId()));
		}
		return deleteChoices;
	}
}
