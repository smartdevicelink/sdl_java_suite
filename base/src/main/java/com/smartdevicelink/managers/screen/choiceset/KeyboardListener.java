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

import com.smartdevicelink.proxy.rpc.enums.KeyboardEvent;

public interface KeyboardListener {

    /**
     * The keyboard session completed with some input.
     * <p>
     * This will be sent upon ENTRY_SUBMITTED or ENTRY_VOICE. If the event is ENTRY_VOICE, the user
     * requested to start a voice session in order to submit input to this keyboard. This MUST be
     * handled by you. Start an Audio Pass Thru session if supported.
     *
     * @param inputText - The submitted input text on the keyboard
     * @param event     - ENTRY_SUBMITTED if the user pressed the submit button on the keyboard, ENTRY_VOICE
     *                  if the user requested that a voice session begin
     */
    void onUserDidSubmitInput(String inputText, KeyboardEvent event);

    /**
     * The keyboard session aborted.
     * <p>
     * This will be sent if the keyboard event ENTRY_CANCELLED or ENTRY_ABORTED is sent
     *
     * @param event - ENTRY_CANCELLED if the user cancelled the keyboard input, or ENTRY_ABORTED if
     *              the system aborted the input due to a higher priority event
     */
    void onKeyboardDidAbortWithReason(KeyboardEvent event);

    /**
     * Implement this in order to provide a custom keyboard configuration to just this keyboard. To
     * apply default settings to all keyboards, see SDLScreenManager.keyboardConfiguration
     *
     * @param currentInputText                       - The user's full current input text
     * @param keyboardAutocompleteCompletionListener - A listener to update the autoCompleteText
     */
    void updateAutocompleteWithInput(String currentInputText, KeyboardAutocompleteCompletionListener keyboardAutocompleteCompletionListener);

    /**
     * Implement this if you wish to update the limitedCharacterSet as the user updates their input.
     * This is called upon a KEYPRESS event.
     *
     * @param currentInputText                       - The user's full current input text
     * @param keyboardCharacterSetCompletionListener - A listener to update the limitedCharacterSet
     */
    void updateCharacterSetWithInput(String currentInputText, KeyboardCharacterSetCompletionListener keyboardCharacterSetCompletionListener);

    /**
     * Implement this to be notified of all events occurring on the keyboard
     *
     * @param event            - The event that occurred
     * @param currentInputText - The user's full current input text
     */
    void onKeyboardDidSendEvent(KeyboardEvent event, String currentInputText);

    void onKeyboardInputMaskHasChanged(KeyboardEvent event);
}