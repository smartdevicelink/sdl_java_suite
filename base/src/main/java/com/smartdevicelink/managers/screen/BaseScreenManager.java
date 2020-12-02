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
package com.smartdevicelink.managers.screen;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import com.smartdevicelink.managers.AlertCompletionListener;
import com.smartdevicelink.managers.BaseSubManager;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.managers.screen.choiceset.ChoiceCell;
import com.smartdevicelink.managers.screen.choiceset.ChoiceSet;
import com.smartdevicelink.managers.screen.choiceset.ChoiceSetManager;
import com.smartdevicelink.managers.screen.choiceset.KeyboardListener;
import com.smartdevicelink.managers.screen.menu.DynamicMenuUpdatesMode;
import com.smartdevicelink.managers.screen.menu.MenuCell;
import com.smartdevicelink.managers.screen.menu.MenuConfiguration;
import com.smartdevicelink.managers.screen.menu.MenuManager;
import com.smartdevicelink.managers.screen.menu.VoiceCommand;
import com.smartdevicelink.managers.screen.menu.VoiceCommandManager;
import com.smartdevicelink.proxy.rpc.KeyboardProperties;
import com.smartdevicelink.proxy.rpc.TemplateConfiguration;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.proxy.rpc.enums.InteractionMode;
import com.smartdevicelink.proxy.rpc.enums.MetadataType;
import com.smartdevicelink.proxy.rpc.enums.TextAlignment;
import com.smartdevicelink.util.DebugTool;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.List;

/**
 * <strong>ScreenManager</strong> <br>
 * <p>
 * Note: This class must be accessed through the SdlManager. Do not instantiate it by itself. <br>
 */
abstract class BaseScreenManager extends BaseSubManager {

    private static final String TAG = "ScreenManager";
    private final WeakReference<FileManager> fileManager;
    private SoftButtonManager softButtonManager;
    private TextAndGraphicManager textAndGraphicManager;
    private VoiceCommandManager voiceCommandManager;
    private MenuManager menuManager;
    private ChoiceSetManager choiceSetManager;
    private SubscribeButtonManager subscribeButtonManager;
    private AlertManager alertManager;

    static final int SOFT_BUTTON_ID_NOT_SET_VALUE = -1;
    static final int SOFT_BUTTON_ID_MIN_VALUE = 0;
    static final int SOFT_BUTTON_ID_MAX_VALUE = 65535;
    static HashSet<Integer> softButtonIDBySoftButtonManager;
    static HashSet<Integer> softButtonIDByAlertManager;

    // Sub manager listener
    private final CompletionListener subManagerListener = new CompletionListener() {

        @Override
        public synchronized void onComplete(boolean success) {
            if (softButtonManager != null && textAndGraphicManager != null && voiceCommandManager != null && menuManager != null && choiceSetManager != null && subscribeButtonManager != null) {
                if (softButtonManager.getState() == BaseSubManager.READY && textAndGraphicManager.getState() == BaseSubManager.READY && voiceCommandManager.getState() == BaseSubManager.READY && menuManager.getState() == BaseSubManager.READY
                        && subscribeButtonManager.getState() == BaseSubManager.READY && alertManager.getState() == BaseSubManager.READY) {
                    DebugTool.logInfo(TAG, "Starting screen manager, all sub managers are in ready state");
                    transitionToState(READY);
                } else if (softButtonManager.getState() == BaseSubManager.ERROR && textAndGraphicManager.getState() == BaseSubManager.ERROR && voiceCommandManager.getState() == BaseSubManager.ERROR && menuManager.getState() == BaseSubManager.ERROR
                        && choiceSetManager.getState() == BaseSubManager.ERROR && subscribeButtonManager.getState() == BaseSubManager.ERROR && alertManager.getState() == BaseSubManager.ERROR) {
                    DebugTool.logError(TAG, "ERROR starting screen manager, all sub managers are in error state");
                    transitionToState(ERROR);
                } else if (textAndGraphicManager.getState() == BaseSubManager.SETTING_UP || softButtonManager.getState() == BaseSubManager.SETTING_UP || voiceCommandManager.getState() == BaseSubManager.SETTING_UP || menuManager.getState() == BaseSubManager.SETTING_UP
                        || choiceSetManager.getState() == BaseSubManager.SETTING_UP || subscribeButtonManager.getState() == BaseSubManager.SETTING_UP || alertManager.getState() == BaseSubManager.SETTING_UP) {
                    DebugTool.logInfo(TAG, "SETTING UP screen manager, at least one sub manager is still setting up");
                    transitionToState(SETTING_UP);
                } else {
                    DebugTool.logWarning(TAG, "LIMITED starting screen manager, at least one sub manager is in error state and the others are ready");
                    transitionToState(LIMITED);
                }
            } else {
                // We should never be here, but somehow one of the sub-sub managers is null
                DebugTool.logError(TAG, "ERROR one of the screen sub managers is null");
                transitionToState(ERROR);
            }
        }
    };

    BaseScreenManager(@NonNull ISdl internalInterface, @NonNull FileManager fileManager) {
        super(internalInterface);
        this.fileManager = new WeakReference<>(fileManager);
        softButtonIDBySoftButtonManager = new HashSet<>();
        softButtonIDByAlertManager = new HashSet<>();
        initialize();
    }

    @Override
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public void start(CompletionListener listener) {
        super.start(listener);
        this.softButtonManager.start(subManagerListener);
        this.textAndGraphicManager.start(subManagerListener);
        this.voiceCommandManager.start(subManagerListener);
        this.menuManager.start(subManagerListener);
        this.choiceSetManager.start(subManagerListener);
        this.subscribeButtonManager.start(subManagerListener);
        this.alertManager.start(subManagerListener);
    }

    private void initialize() {
        if (fileManager.get() != null) {
            this.softButtonManager = new SoftButtonManager(internalInterface, fileManager.get());
            this.textAndGraphicManager = new TextAndGraphicManager(internalInterface, fileManager.get(), softButtonManager);
            this.menuManager = new MenuManager(internalInterface, fileManager.get());
            this.choiceSetManager = new ChoiceSetManager(internalInterface, fileManager.get());
            this.alertManager = new AlertManager(internalInterface, fileManager.get());
        }
        this.subscribeButtonManager = new SubscribeButtonManager(internalInterface);
        this.voiceCommandManager = new VoiceCommandManager(internalInterface);
    }

    /**
     * <p>Called when manager is being torn down</p>
     */
    @Override
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public void dispose() {
        softButtonManager.dispose();
        textAndGraphicManager.dispose();
        voiceCommandManager.dispose();
        menuManager.dispose();
        choiceSetManager.dispose();
        subscribeButtonManager.dispose();
        alertManager.dispose();
        softButtonIDByAlertManager = null;
        softButtonIDBySoftButtonManager = null;

        super.dispose();
    }

    /**
     * The top text field within a template layout. Pass an empty string "" to clear the text field.
     * <p>
     * If the system does not support a full 4 fields, this will automatically be concatenated and properly send the field available.
     * <p>
     * If 3 lines are available: [field1, field2, field3 - field 4]
     * <p>
     * If 2 lines are available: [field1 - field2, field3 - field4]
     * <p>
     * If 1 line is available: [field1 - field2 - field3 - field4]
     *
     * @param textField1 String value represents the textField1
     */
    public void setTextField1(String textField1) {
        this.softButtonManager.setCurrentMainField1(textField1);
        this.textAndGraphicManager.setTextField1(textField1);
    }

    /**
     * Get the current textField1 value
     *
     * @return a String value represents the current textField1 value
     */
    public String getTextField1() {
        return this.textAndGraphicManager.getTextField1();
    }

    /**
     * Sets the second text field within a template layout. Pass an empty string "" to clear the text field.
     * <p>
     * If the system does not support a full 4 fields, this will automatically be concatenated and properly send the field available.
     * <p>
     * If 3 lines are available: [field1, field2, field3 - field 4]
     * <p>
     * If 2 lines are available: [field1 - field2, field3 - field4]
     * <p>
     * If 1 line is available: [field1 - field2 - field3 - field4]
     *
     * @param textField2 String value represents the textField1
     */
    public void setTextField2(String textField2) {
        this.textAndGraphicManager.setTextField2(textField2);
    }

    /**
     * Get the current textField2 value
     *
     * @return a String value represents the current textField2 value
     */
    public String getTextField2() {
        return this.textAndGraphicManager.getTextField2();
    }

    /**
     * Sets the third text field within a template layout. Pass an empty string "" to clear the text field.
     * <p>
     * If the system does not support a full 4 fields, this will automatically be concatenated and properly send the field available.
     * <p>
     * If 3 lines are available: [field1, field2, field3 - field 4]
     * <p>
     * If 2 lines are available: [field1 - field2, field3 - field4]
     * <p>
     * If 1 line is available: [field1 - field2 - field3 - field4]
     *
     * @param textField3 String value represents the textField1
     */
    public void setTextField3(String textField3) {
        this.textAndGraphicManager.setTextField3(textField3);
    }

    /**
     * Get the current textField3 value
     *
     * @return a String value represents the current textField3 value
     */
    public String getTextField3() {
        return this.textAndGraphicManager.getTextField3();
    }

    /**
     * Sets the fourth text field within a template layout. Pass an empty string "" to clear the text field.
     * <p>
     * If the system does not support a full 4 fields, this will automatically be concatenated and properly send the field available.
     * <p>
     * If 3 lines are available: [field1, field2, field3 - field 4]
     * <p>
     * If 2 lines are available: [field1 - field2, field3 - field4]
     * <p>
     * If 1 line is available: [field1 - field2 - field3 - field4]
     *
     * @param textField4 String value represents the textField1
     */
    public void setTextField4(String textField4) {
        this.textAndGraphicManager.setTextField4(textField4);
    }

    /**
     * Get the current textField4 value
     *
     * @return a String value represents the current textField4 value
     */
    public String getTextField4() {
        return this.textAndGraphicManager.getTextField4();
    }

    /**
     * Set the mediaTrackTextField on the head unit screen
     *
     * @param mediaTrackTextField String value represents the mediaTrackTextField
     */
    public void setMediaTrackTextField(String mediaTrackTextField) {
        this.textAndGraphicManager.setMediaTrackTextField(mediaTrackTextField);
    }

    /**
     * Get the current mediaTrackTextField value
     *
     * @return a String value represents the current mediaTrackTextField
     */
    public String getMediaTrackTextField() {
        return this.textAndGraphicManager.getMediaTrackTextField();
    }

    /**
     * Set the primaryGraphic on the head unit screen
     *
     * @param primaryGraphic an SdlArtwork object represents the primaryGraphic
     */
    public void setPrimaryGraphic(SdlArtwork primaryGraphic) {
        if (primaryGraphic == null) {
            primaryGraphic = textAndGraphicManager.getBlankArtwork();
        }
        this.textAndGraphicManager.setPrimaryGraphic(primaryGraphic);
    }

    /**
     * Get the current primaryGraphic value
     *
     * @return an SdlArtwork object represents the current primaryGraphic
     */
    public SdlArtwork getPrimaryGraphic() {
        if (this.textAndGraphicManager.getPrimaryGraphic() == null || textAndGraphicManager.getPrimaryGraphic().getName() == null || this.textAndGraphicManager.getPrimaryGraphic().getName().equals(textAndGraphicManager.getBlankArtwork().getName())) {
            return null;
        }
        return this.textAndGraphicManager.getPrimaryGraphic();
    }

    /**
     * Set the secondaryGraphic on the head unit screen
     *
     * @param secondaryGraphic an SdlArtwork object represents the secondaryGraphic
     */
    public void setSecondaryGraphic(SdlArtwork secondaryGraphic) {
        if (secondaryGraphic == null) {
            secondaryGraphic = textAndGraphicManager.getBlankArtwork();
        }
        this.textAndGraphicManager.setSecondaryGraphic(secondaryGraphic);
    }

    /**
     * Get the current secondaryGraphic value
     *
     * @return an SdlArtwork object represents the current secondaryGraphic
     */
    public SdlArtwork getSecondaryGraphic() {
        if (this.textAndGraphicManager.getSecondaryGraphic() == null || textAndGraphicManager.getSecondaryGraphic().getName() == null || this.textAndGraphicManager.getSecondaryGraphic().getName().equals(textAndGraphicManager.getBlankArtwork().getName())) {
            return null;
        }
        return this.textAndGraphicManager.getSecondaryGraphic();
    }

    /**
     * Set the alignment for the text fields
     *
     * @param textAlignment TextAlignment value represents the alignment for the text fields
     */
    public void setTextAlignment(TextAlignment textAlignment) {
        this.textAndGraphicManager.setTextAlignment(textAlignment);
    }

    /**
     * Get the alignment for the text fields
     *
     * @return a TextAlignment value represents the alignment for the text fields
     */
    public TextAlignment getTextAlignment() {
        return this.textAndGraphicManager.getTextAlignment();
    }

    /**
     * Set the metadata type for the textField1
     *
     * @param textField1Type a MetadataType value represents the metadata for textField1
     */
    public void setTextField1Type(MetadataType textField1Type) {
        this.textAndGraphicManager.setTextField1Type(textField1Type);
    }

    /**
     * Get the metadata type for textField1
     *
     * @return a MetadataType value represents the metadata for textField1
     */
    public MetadataType getTextField1Type() {
        return this.textAndGraphicManager.getTextField1Type();
    }

    /**
     * Set the metadata type for the textField2
     *
     * @param textField2Type a MetadataType value represents the metadata for textField2
     */
    public void setTextField2Type(MetadataType textField2Type) {
        this.textAndGraphicManager.setTextField2Type(textField2Type);
    }

    /**
     * Get the metadata type for textField2
     *
     * @return a MetadataType value represents the metadata for textField2
     */
    public MetadataType getTextField2Type() {
        return this.textAndGraphicManager.getTextField2Type();
    }

    /**
     * Set the metadata type for the textField3
     *
     * @param textField3Type a MetadataType value represents the metadata for textField3
     */
    public void setTextField3Type(MetadataType textField3Type) {
        this.textAndGraphicManager.setTextField3Type(textField3Type);
    }

    /**
     * Get the metadata type for textField3
     *
     * @return a MetadataType value represents the metadata for textField3
     */
    public MetadataType getTextField3Type() {
        return this.textAndGraphicManager.getTextField3Type();
    }

    /**
     * Set the metadata type for the textField4
     *
     * @param textField4Type a MetadataType value represents the metadata for textField4
     */
    public void setTextField4Type(MetadataType textField4Type) {
        this.textAndGraphicManager.setTextField4Type(textField4Type);
    }

    /**
     * Get the metadata type for textField4
     *
     * @return a MetadataType value represents the metadata for textField4
     */
    public MetadataType getTextField4Type() {
        return this.textAndGraphicManager.getTextField4Type();
    }

    /**
     * Sets the title of the new template that will be displayed.
     * Sending an empty String "" will clear the field
     *
     * @param title the title of the new template that will be displayed. MaxLength: 100.
     */
    public void setTitle(String title) {
        this.textAndGraphicManager.setTitle(title);
    }

    /**
     * Gets the title of the new template that will be displayed
     *
     * @return title - String value that represents the title of the new template that will be displayed
     */
    public String getTitle() {
        return this.textAndGraphicManager.getTitle();
    }

    /**
     * Change the current layout to a new layout and optionally update the layout's night and day color schemes. The values set for the text, graphics,
     * buttons and template title persist between layout changes. To update the text, graphics, buttons and template title at the same time as the template,
     * batch all the updates between beginTransaction and commit. If the layout update fails while batching, then the updated text, graphics, buttons or template title will also not be updated.
     * <p>
     * If you are connected on a < v6.0 connection and batching the update, the layout will be updated, then the text and graphics will be updated.
     * If you are connected on a >= v6.0 connection, the layout will be updated at the same time that the text and graphics are updated.
     * <p>
     * If this update is batched between beginTransaction and commit, the completionListener here will not be called. Use the completionListener with commit(completionListener)
     * <p>
     * NOTE: If this update returns an false, it may have been superseded by another update.
     * This means that it was cancelled while in-progress because another update was requested, whether batched or not.
     *
     * @param templateConfiguration The new configuration of the template, including the layout and color scheme.
     * @param listener              A listener that will be called when the layout change finished.
     */
    public void changeLayout(@NonNull TemplateConfiguration templateConfiguration, CompletionListener listener) {
        textAndGraphicManager.changeLayout(templateConfiguration, listener);
    }

    /**
     * Set softButtonObjects list and upload the images to the head unit
     *
     * @param softButtonObjects the list of the SoftButtonObject values that should be displayed on the head unit
     */
    public void setSoftButtonObjects(@NonNull List<SoftButtonObject> softButtonObjects) {
        softButtonManager.setSoftButtonObjects(softButtonObjects);
    }

    /**
     * Get the soft button objects list
     *
     * @return a List<SoftButtonObject>
     */
    public List<SoftButtonObject> getSoftButtonObjects() {
        return softButtonManager.getSoftButtonObjects();
    }

    /**
     * Get the SoftButtonObject that has the provided name
     *
     * @param name a String value that represents the name
     * @return a SoftButtonObject
     */
    public SoftButtonObject getSoftButtonObjectByName(@NonNull String name) {
        return softButtonManager.getSoftButtonObjectByName(name);
    }

    /**
     * Get the SoftButtonObject that has the provided buttonId
     *
     * @param buttonId a int value that represents the id of the button
     * @return a SoftButtonObject
     */
    public SoftButtonObject getSoftButtonObjectById(int buttonId) {
        return softButtonManager.getSoftButtonObjectById(buttonId);
    }

    /**
     * Get the currently set voice commands
     *
     * @return a List of Voice Command objects
     */
    public List<VoiceCommand> getVoiceCommands() {
        return voiceCommandManager.getVoiceCommands();
    }

    /**
     * Set voice commands
     *
     * @param voiceCommands the voice commands to be sent to the head unit
     */
    public void setVoiceCommands(@NonNull List<VoiceCommand> voiceCommands) {
        this.voiceCommandManager.setVoiceCommands(voiceCommands);
    }

    // MENUS

    /**
     * The list of currently set menu cells
     *
     * @return a List of the currently set menu cells
     */
    public List<MenuCell> getMenu() {
        return this.menuManager.getMenuCells();
    }

    /**
     * Creates and sends all associated Menu RPCs
     * Note: the manager will store a deep copy the menuCells internally to be able to handle future updates correctly
     *
     * @param menuCells - the menu cells that are to be sent to the head unit, including their sub-cells.
     */
    public void setMenu(@NonNull List<MenuCell> menuCells) {
        this.menuManager.setMenuCells(menuCells);
    }

    /**
     * Sets the behavior of how menus are updated. For explanations of the differences, see {@link DynamicMenuUpdatesMode}
     *
     * @param value - the update mode
     */
    public void setDynamicMenuUpdatesMode(@NonNull DynamicMenuUpdatesMode value) {
        this.menuManager.setDynamicUpdatesMode(value);
    }

    /**
     * @return The currently set DynamicMenuUpdatesMode. It defaults to ON_WITH_COMPAT_MODE if not set.
     */
    public DynamicMenuUpdatesMode getDynamicMenuUpdatesMode() {
        return this.menuManager.getDynamicMenuUpdatesMode();
    }

    /**
     * Requires SDL RPC Version 6.0.0 or greater
     * Opens the Main Menu.
     *
     * @return boolean success / failure - whether the request was able to be sent
     */
    public boolean openMenu() {
        return this.menuManager.openMenu();
    }

    /**
     * Requires SDL RPC Version 6.0.0 or greater
     * Opens a subMenu. The cell you pass in must be constructed with {@link MenuCell(String,SdlArtwork,List)}
     *
     * @param cell - A <Strong>SubMenu</Strong> cell whose sub menu you wish to open
     * @return boolean success / failure - whether the request was able to be sent
     */
    public boolean openSubMenu(@NonNull MenuCell cell) {
        return this.menuManager.openSubMenu(cell);
    }

    /**
     * The main menu layout. See available menu layouts on WindowCapability.menuLayoutsAvailable.
     *
     * @param menuConfiguration - The default menuConfiguration
     */
    public void setMenuConfiguration(@NonNull MenuConfiguration menuConfiguration) {
        this.menuManager.setMenuConfiguration(menuConfiguration);
    }

    /**
     * The main menu layout. See available menu layouts on WindowCapability.menuLayoutsAvailable.
     *
     * @return the currently set MenuConfiguration
     */
    public MenuConfiguration getMenuConfiguration() {
        return this.menuManager.getMenuConfiguration();
    }

    // CHOICE SETS

    /**
     * Deletes choices that were sent previously
     *
     * @param choices - A list of ChoiceCell objects
     */
    public void deleteChoices(@NonNull List<ChoiceCell> choices) {
        this.choiceSetManager.deleteChoices(choices);
    }

    /**
     * Preload choices to improve performance while presenting a choice set at a later time
     *
     * @param choices  - a list of ChoiceCell objects that will be part of a choice set later
     * @param listener - a completion listener to inform when the operation is complete
     */
    public void preloadChoices(@NonNull List<ChoiceCell> choices, CompletionListener listener) {
        this.choiceSetManager.preloadChoices(choices, listener);
    }

    /**
     * Presents a searchable choice set
     *
     * @param choiceSet        - The choice set to be presented. This can include Choice Cells that were preloaded or not
     * @param mode             - The intended interaction mode
     * @param keyboardListener - A keyboard listener to capture user input
     */
    public void presentSearchableChoiceSet(@NonNull ChoiceSet choiceSet, @Nullable InteractionMode mode, @NonNull KeyboardListener keyboardListener) {
        this.choiceSetManager.presentChoiceSet(choiceSet, mode, keyboardListener);
    }

    /**
     * Presents a choice set
     *
     * @param choiceSet - The choice set to be presented. This can include Choice Cells that were preloaded or not
     * @param mode      - The intended interaction mode
     */
    public void presentChoiceSet(@NonNull ChoiceSet choiceSet, @Nullable InteractionMode mode) {
        this.choiceSetManager.presentChoiceSet(choiceSet, mode, null);
    }

    /**
     * Presents a keyboard on the Head unit to capture user input
     *
     * @param initialText              - The initial text that is used as a placeholder text. It might not work on some head units.
     * @param customKeyboardProperties - the custom keyboard configuration to be used when the keyboard is displayed
     * @param keyboardListener         - A keyboard listener to capture user input
     * @return A unique cancelID that can be used to cancel this keyboard. If `null`, no keyboard was created.
     */
    public Integer presentKeyboard(@NonNull String initialText, @Nullable KeyboardProperties customKeyboardProperties, @NonNull KeyboardListener keyboardListener) {
        return this.choiceSetManager.presentKeyboard(initialText, customKeyboardProperties, keyboardListener);
    }

    /**
     * Set a custom keyboard configuration for this session. If set to null, it will reset to default keyboard configuration.
     *
     * @param keyboardConfiguration - the custom keyboard configuration to be used when the keyboard is displayed
     */
    public void setKeyboardConfiguration(@Nullable KeyboardProperties keyboardConfiguration) {
        this.choiceSetManager.setKeyboardConfiguration(keyboardConfiguration);
    }

    /**
     * @return A set of choice cells that have been preloaded to the head unit
     */
    public HashSet<ChoiceCell> getPreloadedChoices() {
        return this.choiceSetManager.getPreloadedChoices();
    }

    /**
     * Dismisses a currently presented keyboard with the associated ID. Canceling a keyboard only works when connected to SDL Core v.6.0+. When connected to older versions of SDL Core the keyboard will not be dismissed.
     *
     * @param cancelID The unique ID assigned to the keyboard
     */
    public void dismissKeyboard(@NonNull Integer cancelID) {
        this.choiceSetManager.dismissKeyboard(cancelID);
    }

    // END CHOICE SETS

    /**
     * Begin a multiple updates transaction. The updates will be applied when commit() is called<br>
     * Note: if we don't use beginTransaction & commit, every update will be sent individually.
     */
    public void beginTransaction() {
        softButtonManager.setBatchUpdates(true);
        textAndGraphicManager.setBatchUpdates(true);
    }

    /**
     * Pairs with beginTransaction() to batch text, graphic, and layout changes into a single update with a callback when the update is complete.
     * Update text fields with new text set into the text field properties, updates the primary and secondary images with new image(s) if new one(s) been set,
     * and updates the template if one was changed using changeLayout(TemplateConfiguration, CompletionListener).
     * NOTE: The CompletionListener in changeLayout(TemplateConfiguration, CompletionListener) will not be called if the update is batched into this update
     * NOTE: If this CompletionListener returns false, it may have been superseded by another update. This means that it was cancelled while in-progress because another update was requested, whether batched or not.
     *
     * @param listener a CompletionListener that has a callback that will be called when the updates are finished
     */
    public void commit(final CompletionListener listener) {
        softButtonManager.setBatchUpdates(false);
        textAndGraphicManager.setBatchUpdates(false);
        textAndGraphicManager.update(new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                if (listener != null) {
                    listener.onComplete(success);
                }
            }
        });
    }

    public void addButtonListener(@NonNull ButtonName buttonName, @NonNull OnButtonListener listener) {
        subscribeButtonManager.addButtonListener(buttonName, listener);
    }

    public void removeButtonListener(@NonNull ButtonName buttonName, @NonNull OnButtonListener listener) {
        subscribeButtonManager.removeButtonListener(buttonName, listener);
    }

    public void presentAlert(AlertView alert, AlertCompletionListener listener) {
        AlertView alertViewCopy = alert.clone();
        alertManager.presentAlert(alertViewCopy, listener);
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ManagerLocation.SOFTBUTTON_MANAGER, ManagerLocation.ALERT_MANAGER})
    @interface ManagerLocation {
        int SOFTBUTTON_MANAGER = 0;
        int ALERT_MANAGER = 1;
    }

    static boolean checkAndAssignButtonIds(List<SoftButtonObject> softButtonObjects, @ManagerLocation int location) {
        // Depending on location form which the softButtons came from, we will clear out the id list so they can be reset
        if (location == ManagerLocation.ALERT_MANAGER) {
            softButtonIDByAlertManager.clear();
        } else if (location == ManagerLocation.SOFTBUTTON_MANAGER) {
            softButtonIDBySoftButtonManager.clear();
        }
        // Check if multiple soft button objects have the same id
        HashSet<Integer> buttonIdsSetHashSet = new HashSet<>();
        int currentSoftButtonId, numberOfButtonIdsSet = 0, maxButtonIdsSetByDev = SOFT_BUTTON_ID_MIN_VALUE;

        for (SoftButtonObject softButtonObject : softButtonObjects) {
            currentSoftButtonId = softButtonObject.getButtonId();
            if (currentSoftButtonId != SOFT_BUTTON_ID_NOT_SET_VALUE) {
                if (softButtonIDByAlertManager.contains(currentSoftButtonId) || softButtonIDBySoftButtonManager.contains(currentSoftButtonId)) {
                    return false;
                }
                numberOfButtonIdsSet++;
                if (currentSoftButtonId > maxButtonIdsSetByDev) {
                    maxButtonIdsSetByDev = currentSoftButtonId;
                }
                buttonIdsSetHashSet.add(softButtonObject.getButtonId());
            }
        }
        if (numberOfButtonIdsSet != buttonIdsSetHashSet.size()) {
            return false;
        }

        // Set ids for soft button objects
        int generatedSoftButtonId = maxButtonIdsSetByDev;
        for (SoftButtonObject softButtonObject : softButtonObjects) {
            // If the dev did not set the buttonId, the manager should set an id on the dev's behalf
            currentSoftButtonId = softButtonObject.getButtonId();
            if (currentSoftButtonId == SOFT_BUTTON_ID_NOT_SET_VALUE) {
                do {
                    if (generatedSoftButtonId >= SOFT_BUTTON_ID_MAX_VALUE) {
                        generatedSoftButtonId = SOFT_BUTTON_ID_MIN_VALUE;
                    }
                    generatedSoftButtonId++;
                } while (buttonIdsSetHashSet.contains(generatedSoftButtonId));
                softButtonObject.setButtonId(generatedSoftButtonId);
                buttonIdsSetHashSet.add(generatedSoftButtonId);
                if (location == ManagerLocation.ALERT_MANAGER) {
                    softButtonIDByAlertManager.add(generatedSoftButtonId);
                } else if (location == ManagerLocation.SOFTBUTTON_MANAGER) {
                    softButtonIDBySoftButtonManager.add(generatedSoftButtonId);
                }
            }
        }
        return true;
    }
}
