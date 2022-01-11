/*
 * Copyright (c) 2017 - 2019, SmartDeviceLink Consortium, Inc.
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
 * Neither the name of the SmartDeviceLink Consortium, Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
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
package com.smartdevicelink.proxy.rpc;

import androidx.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.InteractionMode;
import com.smartdevicelink.proxy.rpc.enums.LayoutMode;

import java.util.Hashtable;
import java.util.List;

/**
 * Performs an application-initiated interaction in which the user can select a
 * {@linkplain Choice} from among the specified Choice Sets. For instance, an
 * application may use a PerformInteraction to ask a user to say the name of a
 * song to play. The user's response is only valid if it appears in the
 * specified Choice Sets and is recognized by SDL
 * <p>
 * If connecting to SDL Core v.6.0+, the perform interaction can be canceled programmatically using the `cancelID`. On older versions of SDL Core, the perform interaction will persist until the user has interacted with the perform interaction or the specified timeout has elapsed.
 *
 * <p>Function Group: Base</p>
 *
 * <p><b>HMILevel needs to be FULL</b></p>
 *
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Param Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 *                 <th> Req.</th>
 * 			<th>Notes</th>
 * 			<th>Version Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>initialText</td>
 * 			<td>String</td>
 * 			<td>Displayed when the interaction begins. This text may be overlaid by the "Listening" prompt during the interaction. Text is displayed on first line of multiline display, and is centered. If text does not fit on line, it will be truncated</td>
 *                 <td>Y</td>
 * 			<td>maxlength:500</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>initialPrompt</td>
 * 			<td>TTSChunk</td>
 * 			<td>An array of one or more TTSChunks that, taken together, specify what is to be spoken to the user at the start of an interaction.</td>
 *                 <td>Y</td>
 * 			<td>minsize:1; maxsize:100</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>interactionMode</td>
 * 			<td>InteractionMode</td>
 * 			<td>Indicates how user selects interaction choice. User can choose either by voice (VR_ONLY), by visual selection from the menu (MANUAL_ONLY), or by either mode (BOTH). </td>
 *                 <td>Y</td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>interactionChoiceSetIDList</td>
 * 			<td>Integer</td>
 * 			<td>Array of one or more Choice Set IDs. User can select any choice from any of the specified Choice Sets.</td>
 *                 <td>Y</td>
 * 			<td>minsize:0; maxsize:100; minvalue:0; maxvalue:2000000000</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>helpPrompt</td>
 * 			<td>TTSChunk</td>
 * 			<td>An array of TTSChunks which, taken together, specify the help phrase to be spoken when the user says "help" during the VR session. If this parameter is omitted, the help prompt will be constructed by SDL from the first vrCommand of each choice of all the Choice Sets specified in the interactionChoiceSetIDList parameter. </td>
 *                 <td>N</td>
 * 			<td>minsize:1; maxsize:100; The helpPrompt specified in SetGlobalProperties is not used by PerformInteraction.</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>timeoutPrompt</td>
 * 			<td>TTSChunk</td>
 * 			<td>An array of TTSChunks which, taken together, specify the phrase to be spoken when the listen times out during the VR session. If this parameter is omitted, the timeout prompt will be the same as the help prompt (see helpPrompt parameter). </td>
 *                 <td>N</td>
 * 			<td>The timeoutPrompt specified in SetGlobalProperties is not used by PerformInteraction. minsize:1;maxsize:100</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>timeout</td>
 * 			<td>Integer</td>
 * 			<td>The amount of time, in milliseconds, SDL will wait for the user to make a choice (VR or Menu). If this time elapses without the user making a choice, the timeoutPrompt will be spoken. After this timeout value has been reached, the interaction will stop and a subsequent interaction will take place after SDL speaks the timeout prompt. If that times out as well, the interaction will end completely. If omitted, the default is 10000ms.</td>
 *                 <td>N</td>
 * 			<td>minvalue:5000; maxvalue:100000; defvalue:10000</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>vrHelp</td>
 * 			<td>VrHelpItem</td>
 * 			<td>Ability to send suggested VR Help Items to display on-screen during Perform Interaction If omitted on supported displays, the default SDL generated list of suggested choices will be displayed.</td>
 *                 <td>N</td>
 * 			<td>Min = 1;Max = 100</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>interactionLayout</td>
 * 			<td>LayoutMode</td>
 * 			<td>See {@linkplain LayoutMode}</td>
 *                 <td>N</td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 3.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>cancelID</td>
 * 			<td>Integer</td>
 * 			<td>An ID for this specific perform interaction to allow cancellation through the `CancelInteraction` RPC.</td>
 *          <td>N</td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 6.0</td>
 * 		</tr>
 *  </table>
 *
 * @see CreateInteractionChoiceSet
 * @see DeleteInteractionChoiceSet
 * @since SmartDeviceLink 1.0
 */
public class PerformInteraction extends RPCRequest {
    public static final String KEY_INITIAL_TEXT = "initialText";
    public static final String KEY_INTERACTION_MODE = "interactionMode";
    public static final String KEY_INTERACTION_CHOICE_SET_ID_LIST = "interactionChoiceSetIDList";
    public static final String KEY_INTERACTION_LAYOUT = "interactionLayout";
    public static final String KEY_INITIAL_PROMPT = "initialPrompt";
    public static final String KEY_HELP_PROMPT = "helpPrompt";
    public static final String KEY_TIMEOUT_PROMPT = "timeoutPrompt";
    public static final String KEY_TIMEOUT = "timeout";
    public static final String KEY_VR_HELP = "vrHelp";
    public static final String KEY_CANCEL_ID = "cancelID";

    /**
     * Constructs a new PerformInteraction object
     */
    public PerformInteraction() {
        super(FunctionID.PERFORM_INTERACTION.toString());
    }

    /**
     * Constructs a new PerformInteraction object indicated by the Hashtable
     * parameter
     *
     * @param hash The Hashtable to use
     */
    public PerformInteraction(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new PerformInteraction object
     *
     * @param initialText                a String value that is displayed when the interaction begins
     * @param interactionMode            indicate how user selects interaction choice (VR_ONLY, MANUAL_ONLY or BOTH)
     * @param interactionChoiceSetIDList a List<Integer> representing an Array of one or more Choice Set IDs. User can select any choice from any of the specified
     *                                   Choice Sets <b>Notes: </b>Min Value: 0; Max Value: 2000000000
     */
    public PerformInteraction(@NonNull String initialText, @NonNull InteractionMode interactionMode, @NonNull List<Integer> interactionChoiceSetIDList) {
        this();
        setInitialText(initialText);
        setInteractionMode(interactionMode);
        setInteractionChoiceSetIDList(interactionChoiceSetIDList);
    }

    /**
     * Gets the Text that Displayed when the interaction begins. This text may
     * be overlaid by the "Listening" prompt during the interaction. Text is
     * displayed on first line of multiline display, and is centered. If text
     * does not fit on line, it will be truncated
     *
     * @return String -the text displayed when the interaction begins
     */
    public String getInitialText() {
        return getString(KEY_INITIAL_TEXT);
    }

    /**
     * Sets the Text that Displayed when the interaction begins. This text may
     * be overlaid by the "Listening" prompt during the interaction. Text is
     * displayed on first line of multiline display, and is centered. If text
     * does not fit on line, it will be truncated
     *
     * @param initialText a String value that Displayed when the interaction begins
     */
    public PerformInteraction setInitialText(@NonNull String initialText) {
        setParameters(KEY_INITIAL_TEXT, initialText);
        return this;
    }

    /**
     * Gets an An array of one or more TTSChunks that, taken together, specify
     * what is to be spoken to the user at the start of an interaction
     *
     * @return List<TTSChunk> -a List<TTSChunk> value, specify what is to be
     * spoken to the user at the start of an interaction
     */
    @SuppressWarnings("unchecked")
    public List<TTSChunk> getInitialPrompt() {
        return (List<TTSChunk>) getObject(TTSChunk.class, KEY_INITIAL_PROMPT);
    }

    /**
     * Sets An array of one or more TTSChunks that, taken together, specify what
     * is to be spoken to the user at the start of an interaction
     *
     * @param initialPrompt a List<TTSChunk> value, specify what is to be spoken to the
     *                      user at the start of an interaction
     */
    public PerformInteraction setInitialPrompt(List<TTSChunk> initialPrompt) {
        setParameters(KEY_INITIAL_PROMPT, initialPrompt);
        return this;
    }

    /**
     * Gets the Indicates mode that indicate how user selects interaction
     * choice. User can choose either by voice (VR_ONLY), by visual selection
     * from the menu (MANUAL_ONLY), or by either mode (BOTH)
     *
     * @return InteractionMode -indicate how user selects interaction choice
     * (VR_ONLY, MANUAL_ONLY or BOTH)
     */
    public InteractionMode getInteractionMode() {
        return (InteractionMode) getObject(InteractionMode.class, KEY_INTERACTION_MODE);
    }

    /**
     * Sets the Indicates mode that indicate how user selects interaction
     * choice. User can choose either by voice (VR_ONLY), by visual selection
     * from the menu (MANUAL_ONLY), or by either mode (BOTH)
     *
     * @param interactionMode indicate how user selects interaction choice (VR_ONLY,
     *                        MANUAL_ONLY or BOTH)
     */
    public PerformInteraction setInteractionMode(@NonNull InteractionMode interactionMode) {
        setParameters(KEY_INTERACTION_MODE, interactionMode);
        return this;
    }

    /**
     * Gets a List<Integer> value representing an Array of one or more Choice
     * Set IDs
     *
     * @return List<Integer> -a List<Integer> value representing an Array of
     * one or more Choice Set IDs. User can select any choice from any
     * of the specified Choice Sets
     */
    @SuppressWarnings("unchecked")
    public List<Integer> getInteractionChoiceSetIDList() {
        return (List<Integer>) getObject(Integer.class, KEY_INTERACTION_CHOICE_SET_ID_LIST);
    }

    /**
     * Sets a List<Integer> representing an Array of one or more Choice Set
     * IDs. User can select any choice from any of the specified Choice Sets
     *
     * @param interactionChoiceSetIDList -a List<Integer> representing an Array of one or more Choice
     *                                   Set IDs. User can select any choice from any of the specified
     *                                   Choice Sets
     *                                   <p></p>
     *                                   <b>Notes: </b>Min Value: 0; Max Vlaue: 2000000000
     */
    public PerformInteraction setInteractionChoiceSetIDList(@NonNull List<Integer> interactionChoiceSetIDList) {
        setParameters(KEY_INTERACTION_CHOICE_SET_ID_LIST, interactionChoiceSetIDList);
        return this;
    }

    /**
     * Gets a List<TTSChunk> which taken together, specify the help phrase to
     * be spoken when the user says "help" during the VR session
     *
     * @return List<TTSChunk> -a List<TTSChunk> which taken together,
     * specify the help phrase to be spoken when the user says "help"
     * during the VR session
     */
    @SuppressWarnings("unchecked")
    public List<TTSChunk> getHelpPrompt() {
        return (List<TTSChunk>) getObject(TTSChunk.class, KEY_HELP_PROMPT);
    }

    /**
     * Sets An array of TTSChunks which, taken together, specify the help phrase
     * to be spoken when the user says "help" during the VR session
     * <p></p>
     * If this parameter is omitted, the help prompt will be constructed by SDL
     * from the first vrCommand of each choice of all the Choice Sets specified
     * in the interactionChoiceSetIDList parameter
     * <P></p>
     * <b>Notes: </b>The helpPrompt specified in
     * {@linkplain SetGlobalProperties} is not used by PerformInteraction
     *
     * @param helpPrompt a List<TTSChunk> which taken together, specify the help
     *                   phrase to be spoken when the user says "help" during the VR
     *                   session
     */
    public PerformInteraction setHelpPrompt(List<TTSChunk> helpPrompt) {
        setParameters(KEY_HELP_PROMPT, helpPrompt);
        return this;
    }

    /**
     * Gets An array of TTSChunks which, taken together, specify the phrase to
     * be spoken when the listen times out during the VR session
     *
     * @return List<TTSChunk> -a List<TTSChunk> specify the phrase to be
     * spoken when the listen times out during the VR session
     */
    @SuppressWarnings("unchecked")
    public List<TTSChunk> getTimeoutPrompt() {
        return (List<TTSChunk>) getObject(TTSChunk.class, KEY_TIMEOUT_PROMPT);
    }

    /**
     * Sets An array of TTSChunks which, taken together, specify the phrase to
     * be spoken when the listen times out during the VR session
     * <p></p>
     * <b>Notes: </b>The timeoutPrompt specified in
     * {@linkplain SetGlobalProperties} is not used by PerformInteraction
     *
     * @param timeoutPrompt a List<TTSChunk> specify the phrase to be spoken when the
     *                      listen times out during the VR session
     */
    public PerformInteraction setTimeoutPrompt(List<TTSChunk> timeoutPrompt) {
        setParameters(KEY_TIMEOUT_PROMPT, timeoutPrompt);
        return this;
    }

    /**
     * Gets a Integer value representing the amount of time, in milliseconds,
     * SDL will wait for the user to make a choice (VR or Menu)
     *
     * @return Integer -a Integer representing the amount of time, in
     * milliseconds, SDL will wait for the user to make a choice (VR or
     * Menu)
     */
    public Integer getTimeout() {
        return getInteger(KEY_TIMEOUT);
    }

    /**
     * Sets the amount of time, in milliseconds, SDL will wait for the user to
     * make a choice (VR or Menu). If this time elapses without the user making
     * a choice, the timeoutPrompt will be spoken. After this timeout value has
     * been reached, the interaction will stop and a subsequent interaction will
     * take place after SDL speaks the timeout prompt. If that times out as
     * well, the interaction will end completely. If omitted, the default is
     * 10000ms
     *
     * @param timeout an Integer value representing the amount of time, in
     *                milliseconds, SDL will wait for the user to make a choice (VR
     *                or Menu)
     *                <p></p>
     *                <b>Notes: </b>Min Value: 5000; Max Value: 100000
     */
    public PerformInteraction setTimeout(Integer timeout) {
        setParameters(KEY_TIMEOUT, timeout);
        return this;
    }

    /**
     * Gets a Voice Recognition Help list, which is a list of suggested VR Help Items to display on-screen during a Perform Interaction
     *
     * @return List<VrHelpItem> -a List value representing a suggested VR
     * Help Items to display on-screen during Perform Interaction
     * @since SmartDeviceLink 2.0
     */
    @SuppressWarnings("unchecked")
    public List<VrHelpItem> getVrHelp() {
        return (List<VrHelpItem>) getObject(VrHelpItem.class, KEY_VR_HELP);
    }

    /**
     * Sets a Voice Recognition Help list, which is a list of suggested VR Help Items to display on-screen during a Perform Interaction
     *
     * @param vrHelp a List representing a suggested VR Help Items to display
     *               on-screen during Perform Interaction
     *               If omitted on supported displays, the default SDL generated
     *               list of suggested choices will be displayed
     *               <p></p>
     *               <b>Notes: </b>Min=1; Max=100
     * @since SmartDeviceLink 2.0
     */
    public PerformInteraction setVrHelp(List<VrHelpItem> vrHelp) {
        setParameters(KEY_VR_HELP, vrHelp);
        return this;
    }

    /**
     * Gets the layout mode of how the choices are presented. For touchscreen interactions only.
     *
     * @return LayoutMode - The interaction layout mode
     * @since SmartDeviceLink 3.0
     */
    public LayoutMode getInteractionLayout() {
        return (LayoutMode) getObject(LayoutMode.class, KEY_INTERACTION_LAYOUT);
    }

    /**
     * Sets the mode of how the choices are presented. For touchscreen interactions only.
     *
     * @param interactionLayout A LayoutMode representing the interaction layout mode
     * @since SmartDeviceLink 3.0
     */
    public PerformInteraction setInteractionLayout(LayoutMode interactionLayout) {
        setParameters(KEY_INTERACTION_LAYOUT, interactionLayout);
        return this;
    }

    /**
     * Gets an Integer value representing the cancel ID
     *
     * @return Integer - An Integer value representing the ID for this specific perform interaction to allow cancellation through the `CancelInteraction` RPC.
     * @since SmartDeviceLink 6.0
     */
    public Integer getCancelID() {
        return getInteger(KEY_CANCEL_ID);
    }

    /**
     * Sets the cancel ID
     *
     * @param cancelID An Integer ID for this specific perform interaction to allow cancellation through the `CancelInteraction` RPC.
     * @since SmartDeviceLink 6.0
     */
    public PerformInteraction setCancelID(Integer cancelID) {
        setParameters(KEY_CANCEL_ID, cancelID);
        return this;
    }
}
