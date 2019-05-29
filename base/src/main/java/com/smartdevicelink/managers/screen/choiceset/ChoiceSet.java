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
import android.support.annotation.Nullable;

import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.exception.SdlExceptionCause;
import com.smartdevicelink.proxy.TTSChunkFactory;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.VrHelpItem;
import com.smartdevicelink.util.DebugTool;

import java.util.HashSet;
import java.util.List;

public class ChoiceSet {
    private String title;
    private List<TTSChunk> initialPrompt, timeoutPrompt, helpPrompt;
    private ChoiceSetLayout layout;
    private Integer timeout;
    private List<ChoiceCell> choices;
    private List<VrHelpItem> vrHelpList;
    private ChoiceSetSelectionListener choiceSetSelectionListener;

    // defaults
    private Integer defaultTimeout = 10;
    private ChoiceSetLayout defaultLayout = ChoiceSetLayout.CHOICE_SET_LAYOUT_LIST;

    public ChoiceSet(@NonNull String title, @Nullable ChoiceSetSelectionListener listener, @NonNull List<ChoiceCell> choices) {
        setTitle(title);
        setChoiceSetSelectionListener(listener);
        setChoices(choices);

        // defaults
        setLayout(defaultLayout);
        setTimeout(defaultTimeout);

        // things to do
        checkChoiceSetParameters();
        setUpChoiceSet(null);
    }

    public ChoiceSet(@NonNull String title, @Nullable ChoiceSetSelectionListener listener, @Nullable ChoiceSetLayout layout, @Nullable Integer timeout, @Nullable String initialPrompt, @Nullable String timeoutPrompt, @Nullable String helpPrompt, @Nullable List<VrHelpItem> helpList, @NonNull List<ChoiceCell> choices) {
        setTitle(title);
        setChoiceSetSelectionListener(listener);
        setLayout(layout);
        setTimeout(timeout);
        setChoices(choices);

        // Help the dev by creating TTS chunks for them
        if (initialPrompt != null){
            setInitialPrompt(TTSChunkFactory.createSimpleTTSChunks(initialPrompt));
        }

        if (timeoutPrompt != null){
            setTimeoutPrompt(TTSChunkFactory.createSimpleTTSChunks(timeoutPrompt));
        }

        if (helpPrompt != null){
            setHelpPrompt(TTSChunkFactory.createSimpleTTSChunks(helpPrompt));
        }

        // things to do
        checkChoiceSetParameters();
        setUpChoiceSet(helpList);
    }

    public ChoiceSet(@NonNull String title, @Nullable ChoiceSetSelectionListener listener, @Nullable ChoiceSetLayout layout, @Nullable Integer timeout, @Nullable List<TTSChunk> initialPrompt, @Nullable List<TTSChunk> timeoutPrompt, @Nullable List<TTSChunk> helpPrompt, @Nullable List<VrHelpItem> helpList, @NonNull List<ChoiceCell> choices) {
        setTitle(title);
        setChoiceSetSelectionListener(listener);
        setInitialPrompt(initialPrompt);
        setTimeoutPrompt(timeoutPrompt);
        setHelpPrompt(helpPrompt);
        setChoices(choices);
        setTimeout(timeout);
        setLayout(layout);

        // things to do
        checkChoiceSetParameters();
        setUpChoiceSet(helpList);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<TTSChunk> getInitialPrompt() {
        return initialPrompt;
    }

    public void setInitialPrompt(List<TTSChunk> initialPrompt) {
        this.initialPrompt = initialPrompt;
    }

    public List<TTSChunk> getTimeoutPrompt() {
        return timeoutPrompt;
    }

    public void setTimeoutPrompt(List<TTSChunk> timeoutPrompt) {
        this.timeoutPrompt = timeoutPrompt;
    }

    public List<TTSChunk> getHelpPrompt() {
        return helpPrompt;
    }

    public void setHelpPrompt(List<TTSChunk> helpPrompt) {
        this.helpPrompt = helpPrompt;
    }

    public List<VrHelpItem> getVrHelpList() {
        return vrHelpList;
    }

    public void setVrHelpList(List<VrHelpItem> vrHelpList) {
        this.vrHelpList = vrHelpList;
    }

    public ChoiceSetLayout getLayout() {
        return layout;
    }

    public void setLayout(ChoiceSetLayout layout) {
        if (layout == null){
            this.layout = defaultLayout;
        } else {
            this.layout = layout;
        }
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        if (timeout == null) {
            this.timeout = defaultTimeout;
        } else {
            this.timeout = timeout;
        }
    }

    public List<ChoiceCell> getChoices() {
        return choices;
    }

    public void setChoices(List<ChoiceCell> choices) {
        this.choices = choices;
    }

    public ChoiceSetSelectionListener getChoiceSetSelectionListener() {
        return choiceSetSelectionListener;
    }

    public void setChoiceSetSelectionListener(ChoiceSetSelectionListener choiceSetSelectionListener) {
        this.choiceSetSelectionListener = choiceSetSelectionListener;
    }

    // HELPERS

    private void checkChoiceSetParameters(){
        if (DebugTool.isDebugEnabled()) {
            if (getTitle() != null) {
                if (getTitle().length() == 0 || getTitle().length() > 500) {
                    DebugTool.logWarning("Attempted to create a choice set with a title of " + getTitle().length() + " length. Only 500 characters are supported.");
                }
            }
            if (getTimeout() != null) {
                if (getTimeout() < 5 || getTimeout() > 100) {
                    DebugTool.logWarning("Attempted to create a choice set with a " + getTimeout() + " second timeout; Only 5 - 100 seconds is valid");
                }
            }
            if (getChoices() != null) {
                if (getChoices().size() == 0 || getChoices().size() > 100) {
                    DebugTool.logWarning("Attempted to create a choice set with "+getChoices().size()+" choices; Only 1 - 100 choices are valid");
                }
            }
        }
    }

    private void setUpChoiceSet(List<VrHelpItem> helpItems){

        if (getChoices() == null){
            try {
                throw new SdlException("Cannot initiate a choice set with no choices", SdlExceptionCause.INVALID_ARGUMENT);
            } catch (SdlException e) {
                e.printStackTrace();
            }
        }

        HashSet<String> choiceTextSet = new HashSet<>();
        HashSet<String> uniqueVoiceCommands = new HashSet<>();
        int allVoiceCommandsCount = 0;
        int choiceCellWithVoiceCommandCount = 0;

        for (ChoiceCell cell : getChoices()){

            choiceTextSet.add(cell.getText());

            if (cell.getVoiceCommands() == null){
                continue;
            }

            uniqueVoiceCommands.addAll(cell.getVoiceCommands());
            choiceCellWithVoiceCommandCount += 1;
            allVoiceCommandsCount += cell.getVoiceCommands().size();
        }

        if (choiceTextSet.size() < choices.size()){
            DebugTool.logError("Attempted to create a choice set with duplicate cell text. Cell text must be unique. The choice set will not be set.");
            return;
        }

        // All or none of the choices MUST have VR Commands
        if (choiceCellWithVoiceCommandCount > 0 && choiceCellWithVoiceCommandCount < choices.size()){
            DebugTool.logError("If using voice recognition commands, all of the choice set cells must have unique VR commands. There are "+uniqueVoiceCommands.size()+" cells with unique voice commands and "+allVoiceCommandsCount+" total cells. The choice set will not be set.");
            return;
        }

        // All VR Commands MUST be unique
        if (uniqueVoiceCommands.size() < allVoiceCommandsCount){
            DebugTool.logError("If using voice recognition commands, all VR commands must be unique. There are "+uniqueVoiceCommands.size()+" unique VR commands and "+allVoiceCommandsCount+" VR commands. The choice set will not be set.");
            return;
        }

        if (helpItems != null) {
            for (int i = 0; i < helpItems.size(); i++) {
                helpItems.get(i).setPosition(i+1);
            }
            setVrHelpList(helpItems);
        }

    }
}
