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

import androidx.annotation.NonNull;

import com.smartdevicelink.managers.file.filetypes.SdlArtwork;

import java.util.List;

public class ChoiceCell {
    private String text, secondaryText, tertiaryText;
    private List<String> voiceCommands;
    private SdlArtwork artwork, secondaryArtwork;
    private Integer choiceId;

    /**
     * MAX ID for cells - Cannot use Integer.MAX_INT as the value is too high.
     */
    private static final int MAX_ID = 2000000000;

    /**
     * Initialize the cell with text and nothing else.
     *
     * @param text - The primary text of the cell.
     */
    public ChoiceCell(@NonNull String text) {
        setText(text);
        setChoiceId(MAX_ID);
    }

    /**
     * Initialize the cell with text, optional artwork, and optional voice commands
     *
     * @param text          - The primary text of the cell
     * @param voiceCommands - Strings that can be spoken by the user to activate this cell in a voice or both interaction mode
     * @param artwork       - The primary artwork of the cell
     */
    public ChoiceCell(@NonNull String text, List<String> voiceCommands, SdlArtwork artwork) {
        setText(text);
        setVoiceCommands(voiceCommands);
        setArtwork(artwork);
        setChoiceId(MAX_ID);
    }

    /**
     * Initialize the cell with all optional items
     *
     * @param text             - The primary text
     * @param secondaryText    - The secondary text
     * @param tertiaryText     - The tertiary text
     * @param voiceCommands    - Strings that can be spoken by the user to activate this cell in a voice or both interaction mode
     * @param artwork          - The primary artwork of the cell
     * @param secondaryArtwork - The secondary artwork of the cell
     */
    public ChoiceCell(@NonNull String text, String secondaryText, String tertiaryText, List<String> voiceCommands, SdlArtwork artwork, SdlArtwork secondaryArtwork) {
        setText(text);
        setSecondaryText(secondaryText);
        setTertiaryText(tertiaryText);
        setVoiceCommands(voiceCommands);
        setArtwork(artwork);
        setSecondaryArtwork(secondaryArtwork);
        setChoiceId(MAX_ID);
    }

    /**
     * Maps to Choice.menuName. The primary text of the cell. Duplicates within an `ChoiceSet`
     * are not permitted and will result in the `ChoiceSet` failing to initialize.
     *
     * @return The primary text of the cell
     */
    public String getText() {
        return text;
    }

    /**
     * @param text - Maps to Choice.menuName. The primary text of the cell. Duplicates within an `ChoiceSet`
     *             are not permitted and will result in the `ChoiceSet` failing to initialize.
     */
    void setText(@NonNull String text) {
        this.text = text;
    }

    /**
     * Maps to Choice.secondaryText. Optional secondary text of the cell, if available. Duplicates
     * within an `ChoiceSet` are permitted.
     *
     * @return Optional secondary text of the cell
     */
    public String getSecondaryText() {
        return secondaryText;
    }

    /**
     * @param secondaryText - Maps to Choice.secondaryText. Optional secondary text of the cell, if
     *                      available. Duplicates within an `ChoiceSet` are permitted.
     */
    void setSecondaryText(String secondaryText) {
        this.secondaryText = secondaryText;
    }

    /**
     * Maps to Choice.tertiaryText. Optional tertiary text of the cell, if available. Duplicates within an `ChoiceSet` are permitted.
     *
     * @return Optional tertiary text of the cell
     */
    public String getTertiaryText() {
        return tertiaryText;
    }

    /**
     * @param tertiaryText - Maps to Choice.tertiaryText. Optional tertiary text of the cell, if
     *                     available. Duplicates within an `ChoiceSet` are permitted.
     */
    void setTertiaryText(String tertiaryText) {
        this.tertiaryText = tertiaryText;
    }

    /**
     * Maps to Choice.vrCommands. Optional voice commands the user can speak to activate the cell.
     * If not set and the head unit requires it, this will be set to the number in the list that this
     * item appears. However, this would be a very poor experience for a user if the choice set is
     * presented as a voice only interaction or both interaction mode. Therefore, consider not setting
     * this only when you know the choice set will be presented as a touch only interaction.
     *
     * @return The list of voice command strings
     */
    public List<String> getVoiceCommands() {
        return voiceCommands;
    }

    /**
     * @param voiceCommands - Maps to Choice.vrCommands. Optional voice commands the user can speak to activate the cell.
     *                      If not set and the head unit requires it, this will be set to the number in the list that this
     *                      item appears. However, this would be a very poor experience for a user if the choice set is
     *                      presented as a voice only interaction or both interaction mode. Therefore, consider not setting
     *                      this only when you know the choice set will be presented as a touch only interaction.
     */
    void setVoiceCommands(List<String> voiceCommands) {
        this.voiceCommands = voiceCommands;
    }

    /**
     * Maps to Choice.image. Optional image for the cell. This will be uploaded before the cell is
     * used when the cell is preloaded or presented for the first time.
     *
     * @return The SdlArtwork
     */
    public SdlArtwork getArtwork() {
        return artwork;
    }

    /**
     * @param artwork - Maps to Choice.image. Optional image for the cell. This will be uploaded
     *                before the cell is used when the cell is preloaded or presented for the first time.
     */
    void setArtwork(SdlArtwork artwork) {
        this.artwork = artwork;
    }

    /**
     * Maps to Choice.secondaryImage. Optional secondary image for the cell. This will be uploaded
     * before the cell is used when the cell is preloaded or presented for the first time.
     *
     * @return The SdlArtwork
     */
    public SdlArtwork getSecondaryArtwork() {
        return secondaryArtwork;
    }

    /**
     * @param secondaryArtwork - Maps to Choice.secondaryImage. Optional secondary image for the cell.
     *                         This will be uploaded before the cell is used when the cell is preloaded or presented for the first time.
     */
    void setSecondaryArtwork(SdlArtwork secondaryArtwork) {
        this.secondaryArtwork = secondaryArtwork;
    }

    /**
     * NOTE: USED INTERNALLY
     * Set the choice Id.
     *
     * @param choiceId - the choice Id
     */
    void setChoiceId(int choiceId) {
        this.choiceId = choiceId;
    }

    /**
     * NOTE: USED INTERNALLY
     * Get the choiceId
     *
     * @return the choiceId for this Choice Cell
     */
    int getChoiceId() {
        return choiceId;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result += ((getText() == null) ? 0 : Integer.rotateLeft(getText().hashCode(), 1));
        result += ((getSecondaryText() == null) ? 0 : Integer.rotateLeft(getSecondaryText().hashCode(), 2));
        result += ((getTertiaryText() == null) ? 0 : Integer.rotateLeft(getTertiaryText().hashCode(), 3));
        result += ((getArtwork() == null) ? 0 : Integer.rotateLeft(getArtwork().hashCode(), 4));
        result += ((getSecondaryArtwork() == null) ? 0 : Integer.rotateLeft(getSecondaryArtwork().hashCode(), 5));
        result += ((getVoiceCommands() == null) ? 0 : Integer.rotateLeft(getVoiceCommands().hashCode(), 6));
        return result;
    }

    /**
     * Uses our custom hashCode for ChoiceCell objects
     *
     * @param o - The object to compare
     * @return boolean of whether the objects are the same or not
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        // if this is the same memory address, its the same
        if (this == o) return true;
        // if this is not an instance of this class, not the same
        if (!(o instanceof ChoiceCell)) return false;
        // return comparison
        return hashCode() == o.hashCode();
    }

    /**
     * @return A string description of the cell, useful for debugging.
     */
    @Override
    @NonNull
    public String toString() {
        return "ChoiceCell: ID: " + this.choiceId + " Text: " + text + " - Secondary Text: " + secondaryText + " - Tertiary Text: " + tertiaryText + " " +
                "| Artwork Names: " + ((getArtwork() == null || getArtwork().getName() == null) ? "Primary Art null" : getArtwork().getName())
                + " Secondary Art - " + ((getSecondaryArtwork() == null || getSecondaryArtwork().getName() == null) ? "Secondary Art null" : getSecondaryArtwork().getName()) +
                " | Voice Commands Size: " + ((getVoiceCommands() == null) ? 0 : getVoiceCommands().size());
    }

}
