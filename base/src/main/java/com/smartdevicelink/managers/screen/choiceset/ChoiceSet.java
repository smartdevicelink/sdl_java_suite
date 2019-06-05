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
import android.util.Log;

import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.exception.SdlExceptionCause;
import com.smartdevicelink.proxy.TTSChunkFactory;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.VrHelpItem;
import com.smartdevicelink.util.DebugTool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
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

    /**
     * Initialize with a title, listener, and choices. It will use the default timeout and layout,
     * all other properties (such as prompts) will be `null`.
     *
     * @param title - The choice set's title
     * @param listener - The choice set listener called after the user has interacted with your choice set
     * @param choices - The choices to be displayed to the user for interaction
     */
    public ChoiceSet(@NonNull String title, @NonNull List<ChoiceCell> choices, @NonNull ChoiceSetSelectionListener listener) {

        setTitle(title);
        setChoiceSetSelectionListener(listener);
        setChoices(choices);

        // defaults
        setLayout(defaultLayout);
        setTimeout(defaultTimeout);

        // things to do
        checkChoiceSetParameters();
    }

    /**
     * Constructor  with all possible properties.
     *
     * @param title - The choice set's title
     * @param listener - The choice set listener called after the user has interacted with your choice set
     * @param layout - The layout of choice options (Manual/touch only)
     * @param timeout - The timeout of a touch interaction (Manual/touch only)
     * @param initialPrompt - A voice prompt spoken to the user when this set is displayed
     * @param timeoutPrompt - A voice prompt spoken to the user when the set times out (Voice only)
     * @param helpPrompt - A voice prompt spoken to the user when the user asks for "help"
     * @param helpList - A table list of text and images shown to the user during a voice recognition session for this choice set (Voice only)
     * @param choices - The list of choices presented to the user either as a manual/touch interaction or via the user's voice
     */
    public ChoiceSet(@NonNull String title, @Nullable ChoiceSetLayout layout, @Nullable Integer timeout, @Nullable String initialPrompt, @Nullable String timeoutPrompt, @Nullable String helpPrompt, @Nullable List<VrHelpItem> helpList, @NonNull List<ChoiceCell> choices, @NonNull ChoiceSetSelectionListener listener) {

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
        setUpHelpItems(helpList);
    }

    /**
     * Constructor  with all possible properties.
     *
     * @param title - The choice set's title
     * @param listener - The choice set listener called after the user has interacted with your choice set
     * @param layout - The layout of choice options (Manual/touch only)
     * @param timeout - The timeout of a touch interaction (Manual/touch only)
     * @param initialPrompt - A voice prompt spoken to the user when this set is displayed
     * @param timeoutPrompt - A voice prompt spoken to the user when the set times out (Voice only)
     * @param helpPrompt - A voice prompt spoken to the user when the user asks for "help"
     * @param helpList - A table list of text and images shown to the user during a voice recognition session for this choice set (Voice only)
     * @param choices - The list of choices presented to the user either as a manual/touch interaction or via the user's voice
     */
    public ChoiceSet(@NonNull String title, @Nullable ChoiceSetLayout layout, @Nullable Integer timeout, @Nullable List<TTSChunk> initialPrompt, @Nullable List<TTSChunk> timeoutPrompt, @Nullable List<TTSChunk> helpPrompt, @Nullable List<VrHelpItem> helpList, @NonNull List<ChoiceCell> choices, @NonNull ChoiceSetSelectionListener listener) {

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
        setUpHelpItems(helpList);
    }

    /**
     * Maps to PerformInteraction.initialText. The title of the choice set, and/or the initial text on a keyboard prompt.
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title - Maps to PerformInteraction.initialText. The title of the choice set, and/or the initial text on a keyboard prompt.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Maps to PerformInteraction.initialPrompt. The initial prompt spoken to the user at the start of an interaction.
     * @return The list of TTSChunks
     */
    public List<TTSChunk> getInitialPrompt() {
        return initialPrompt;
    }

    /**
     * @param initialPrompt - Maps to PerformInteraction.initialPrompt. The initial prompt spoken to the user at the start of an interaction.
     */
    public void setInitialPrompt(List<TTSChunk> initialPrompt) {
        this.initialPrompt = initialPrompt;
    }

    /**
     * Maps to PerformInteraction.timeoutPrompt. This text is spoken when a VR interaction times out.
     * If this set is presented in a manual (non-voice) only interaction, this will be ignored.
     * @return - The list of TTS Chunks
     */
    public List<TTSChunk> getTimeoutPrompt() {
        return timeoutPrompt;
    }

    /**
     * @param timeoutPrompt - Maps to PerformInteraction.timeoutPrompt. This text is spoken when a
     * VR interaction times out. If this set is presented in a manual (non-voice) only interaction, this will be ignored.
     */
    public void setTimeoutPrompt(List<TTSChunk> timeoutPrompt) {
        this.timeoutPrompt = timeoutPrompt;
    }

    /**
     * Maps to PerformInteraction.helpPrompt. This is the spoken string when a user speaks "help" when the interaction is occurring.
     * @return The List of TTS Chunks
     */
    public List<TTSChunk> getHelpPrompt() {
        return helpPrompt;
    }

    /**
     * @param helpPrompt - Maps to PerformInteraction.helpPrompt. This is the spoken string when a user
     * speaks "help" when the interaction is occurring.
     */
    public void setHelpPrompt(List<TTSChunk> helpPrompt) {
        this.helpPrompt = helpPrompt;
    }

    /**
     * Maps to PerformInteraction.vrHelp. This is a list of help text presented to the user when
     * they are in a voice recognition interaction from your choice set of options. If this set is
     * presented in a touch only interaction, this will be ignored.
     *
     * Note: That while VRHelpItem's position will be automatically set based on position in the
     * array, the image will need to uploaded by you before use using the FileManager.
     *
     * @return The List of VR Help Items
     */
    public List<VrHelpItem> getVrHelpList() {
        return vrHelpList;
    }

    /**
     * @param vrHelpList - Maps to PerformInteraction.vrHelp. This is a list of help text presented to the user when
     * they are in a voice recognition interaction from your choice set of options. If this set is
     * presented in a touch only interaction, this will be ignored.
     *
     * Note: That while SDLVRHelpItem's position will be automatically set based on position in the
     * array, the image will need to uploaded by you before use using the FileManager.
     */
    public void setVrHelpList(List<VrHelpItem> vrHelpList) {

        if (vrHelpList != null) {
            for (int i = 0; i < vrHelpList.size(); i++) {
                vrHelpList.get(i).setPosition(i+1);
            }
        }

        this.vrHelpList = vrHelpList;
    }

    /**
     * Maps to PerformInteraction.interactionLayout. Whether the presented choices are arranged as
     * a set of tiles or a list.
     * @return The ChoiceSetLayout
     */
    public ChoiceSetLayout getLayout() {
        return layout;
    }

    /**
     * @param layout - Maps to PerformInteraction.interactionLayout. Whether the presented choices
     * are arranged as a set of tiles or a list.
     */
    public void setLayout(ChoiceSetLayout layout) {
        if (layout == null){
            this.layout = defaultLayout;
        } else {
            this.layout = layout;
        }
    }

    /**
     * Maps to PerformInteraction.timeout. This applies only to a manual selection (not a voice
     * selection, which has its timeout handled by the system). Defaults to `defaultTimeout`.
     * @return The Timeout
     */
    public Integer getTimeout() {
        return timeout;
    }

    /**
     * @param timeout - Maps to PerformInteraction.timeout. This applies only to a manual selection
     * (not a voice selection, which has its timeout handled by the system). Defaults to `defaultTimeout`.
     */
    public void setTimeout(Integer timeout) {
        if (timeout == null) {
            this.timeout = defaultTimeout;
        } else {
            this.timeout = timeout;
        }
    }

    /**
     * The choices to be displayed to the user within this choice set. These choices could match
     * those already preloaded
     *
     * This is limited to 100 items. If you attempt to set more than 100 items, the set will not
     * have any items (this array will be empty).
     * @return The List of ChoiceCells
     */
    public List<ChoiceCell> getChoices() {
        return choices;
    }

    /**
     * @param choices - The choices to be displayed to the user within this choice set. These choices could match
     * those already preloaded
     *
     * This is limited to 100 items. If you attempt to set more than 100 items, the set will not
     * have any items (this array will be empty).
     */
    public void setChoices(List<ChoiceCell> choices) {
        this.choices = choices;
    }

    /**
     * The listener of this choice set, called when the user interacts with it.
     * @return The listener
     */
    public ChoiceSetSelectionListener getChoiceSetSelectionListener() {
        return choiceSetSelectionListener;
    }

    /**
     * @param choiceSetSelectionListener The listener of this choice set, called when the user interacts with it.
     */
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

    private void setUpHelpItems(List<VrHelpItem> helpItems){
        List<VrHelpItem> clonedHelpItems = null;
        VrHelpItem clonedHelpItem;
        if (helpItems != null) {
            clonedHelpItems = new ArrayList<>();
            if (!helpItems.isEmpty()) {
                for (int i = 0; i < helpItems.size(); i++) {
                    // clone helpItem so we don't modify the develop copy
                    clonedHelpItem = new VrHelpItem((Hashtable) helpItems.get(i).getStore().clone());

                    // set help item positioning
                    clonedHelpItem.setPosition(i + 1);

                    clonedHelpItems.add(clonedHelpItem);
                }
            }
        }
        setVrHelpList(clonedHelpItems);
    }

}
