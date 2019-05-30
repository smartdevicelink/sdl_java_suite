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

package com.smartdevicelink.managers.screen.choiceset;

import android.support.annotation.NonNull;

import com.smartdevicelink.managers.BaseSubManager;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.interfaces.OnSystemCapabilityListener;
import com.smartdevicelink.proxy.rpc.DisplayCapabilities;
import com.smartdevicelink.proxy.rpc.KeyboardProperties;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.OnSdlChoiceChosen;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.InteractionMode;
import com.smartdevicelink.proxy.rpc.enums.KeyboardLayout;
import com.smartdevicelink.proxy.rpc.enums.KeypressMode;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;
import com.smartdevicelink.proxy.rpc.enums.SystemContext;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.util.DebugTool;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.List;

/**
 * <strong>ChoiceSetManager</strong> <br>
 * <p>
 * Note: This class must be accessed through the SdlManager. Do not instantiate it by itself. <br>
 */
public abstract class BaseChoiceSetManager extends BaseSubManager {

    // additional state
    private static final int CHECKING_VOICE = 0xA0;

    OnRPCNotificationListener hmiListener;
    OnSystemCapabilityListener displayListener;

    private final WeakReference<FileManager> fileManager;
    private KeyboardProperties keyboardConfiguration;
    private HMILevel currentHMILevel;
    private SystemContext currentSystemContext;
    private DisplayCapabilities displayCapabilities;

    private HashSet<ChoiceCell> preloadedChoices;
    private HashSet<ChoiceCell> pendingPreloadChoices;
    private ChoiceSet pendingPresentationSet;
    private Boolean isVROptional;

    private int nextChoiceId;
    private int choiceCellIdMin = 1;

    public BaseChoiceSetManager(@NonNull ISdl internalInterface, @NonNull FileManager fileManager) {
        super(internalInterface);

        this.fileManager = new WeakReference<>(fileManager);
        preloadedChoices = new HashSet<>();
        pendingPreloadChoices = new HashSet<>();
        nextChoiceId = choiceCellIdMin;
        isVROptional = true;
        keyboardConfiguration = defaultKeyboardConfiguration();
        addListeners();
    }

    @Override
    public void start(CompletionListener listener){

        transitionToState(READY);
        super.start(listener);
    }

    @Override
    public void dispose(){


        super.dispose();
    }

    public void preloadChoices(List<ChoiceCell> choices, CompletionListener listener){


    }

    public void deleteChoices(List<ChoiceCell> choices){


    }

    public void presentChoiceSet(ChoiceSet choiceSet, InteractionMode mode, KeyboardListener listener){


    }

    public void presentKeyboardWithInitialText(String initialText, KeyboardListener listener){


    }

    // SETTERS

    public void setKeyboardConfiguration(KeyboardProperties keyboardConfiguration){

        if (keyboardConfiguration == null){
            this.keyboardConfiguration = defaultKeyboardConfiguration();
        } else{
            KeyboardProperties properties = new KeyboardProperties();
            properties.setLanguage((keyboardConfiguration.getLanguage() == null ? Language.EN_US : keyboardConfiguration.getLanguage()));
            properties.setKeyboardLayout((keyboardConfiguration.getKeyboardLayout() == null ? KeyboardLayout.QWERTZ : keyboardConfiguration.getKeyboardLayout()));
            properties.setKeypressMode(KeypressMode.RESEND_CURRENT_ENTRY);
            properties.setLimitedCharacterList(keyboardConfiguration.getLimitedCharacterList());
            properties.setAutoCompleteText(keyboardConfiguration.getAutoCompleteText());
            this.keyboardConfiguration = properties;
        }
    }

    // GETTERS

    public HashSet<ChoiceCell> getPreloadedChoices(){
        return this.preloadedChoices;
    }

    public HashSet<ChoiceCell> getPendingPreloadChoices(){
        return this.pendingPreloadChoices;
    }

    // CHOICE SET MANAGEMENT HELPERS

    private HashSet<ChoiceCell> choicesToBeUploadedWithArray(List<ChoiceCell> choices){
        HashSet<ChoiceCell> choicesSet = new HashSet<>(choices);
        choicesSet.removeAll(this.preloadedChoices);
        return choicesSet;
    }

    private HashSet<ChoiceCell> choicesToBeDeletedWithArray(List<ChoiceCell> choices){
        HashSet<ChoiceCell> choicesSet = new HashSet<>(choices);
        choicesSet.retainAll(this.preloadedChoices);
        return choicesSet;
    }

    private HashSet<ChoiceCell> choicesToBeRemovedFromPendingWithArray(List<ChoiceCell> choices){
        HashSet<ChoiceCell> choicesSet = new HashSet<>(choices);
        choicesSet.retainAll(this.pendingPreloadChoices);
        return choicesSet;
    }

    private void updateIdsOnChoices(HashSet<ChoiceCell> choices){
        for (ChoiceCell cell : choices){
            cell.setChoiceId(this.nextChoiceId);
            this.nextChoiceId++;
        }
    }

    private void findIdsOnChoiceSet(ChoiceSet choiceSet){
        findIdsOnChoices(new HashSet<>(choiceSet.getChoices()));
    }

    private void findIdsOnChoices(HashSet<ChoiceCell> choices){
        for (ChoiceCell cell : choices){
            ChoiceCell uploadCell = null;
            if (pendingPreloadChoices.contains(cell)){
                uploadCell = findIfPresent(cell, pendingPreloadChoices);
            }else if (preloadedChoices.contains(cell)){
                uploadCell = findIfPresent(cell, preloadedChoices);
            }
            if (uploadCell != null ){
                cell.setChoiceId(uploadCell.getChoiceId());
            }
        }
    }

    private ChoiceCell findIfPresent(ChoiceCell cell, HashSet<ChoiceCell> set){
        if (set.contains(cell)) {
            for (ChoiceCell setCell : set) {
                if (setCell.equals(cell))
                    return setCell;
            }
        }
        return null;
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
                DebugTool.logError("Unable to retrieve display capabilities. Many things will probably break. Info: "+ info);
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
            }
        };
        internalInterface.addOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, hmiListener);

    }

    // HELPERS

    private KeyboardProperties defaultKeyboardConfiguration(){
        KeyboardProperties defaultProperties = new KeyboardProperties();
        defaultProperties.setLanguage(Language.EN_US);
        defaultProperties.setKeyboardLayout(KeyboardLayout.QWERTY);
        defaultProperties.setKeypressMode(KeypressMode.RESEND_CURRENT_ENTRY);
        return defaultProperties;
    }
}
