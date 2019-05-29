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

    public ChoiceCell(@NonNull String text) {
        setText(text);
        setChoiceId(MAX_ID);
    }

    public ChoiceCell(@NonNull String text, List<String> voiceCommands, SdlArtwork artwork) {
        setText(text);
        setVoiceCommands(voiceCommands);
        setArtwork(artwork);
        setChoiceId(MAX_ID);
    }

    public ChoiceCell(@NonNull String text, String secondaryText, String tertiaryText, List<String> voiceCommands, SdlArtwork artwork, SdlArtwork secondaryArtwork) {
        setText(text);
        setSecondaryText(secondaryText);
        setTertiaryText(tertiaryText);
        setVoiceCommands(voiceCommands);
        setArtwork(artwork);
        setSecondaryArtwork(secondaryArtwork);
        setChoiceId(MAX_ID);
    }

    public String getText() {
        return text;
    }

    public void setText(@NonNull String text) {
        this.text = text;
    }

    public String getSecondaryText() {
        return secondaryText;
    }

    public void setSecondaryText(String secondaryText) {
        this.secondaryText = secondaryText;
    }

    public String getTertiaryText() {
        return tertiaryText;
    }

    public void setTertiaryText(String tertiaryText) {
        this.tertiaryText = tertiaryText;
    }

    public List<String> getVoiceCommands() {
        return voiceCommands;
    }

    public void setVoiceCommands(List<String> voiceCommands) {
        this.voiceCommands = voiceCommands;
    }

    public SdlArtwork getArtwork() {
        return artwork;
    }

    public void setArtwork(SdlArtwork artwork) {
        this.artwork = artwork;
    }

    public SdlArtwork getSecondaryArtwork() {
        return secondaryArtwork;
    }

    public void setSecondaryArtwork(SdlArtwork secondaryArtwork) {
        this.secondaryArtwork = secondaryArtwork;
    }

    /**
     * Set the choice Id.
     * @param choiceId - the choice Id
     */
    void setChoiceId(int choiceId) {
        this.choiceId = choiceId;
    }

    /**
     * Get the choiceId
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
        result += ((getArtwork() == null || getArtwork().getName() == null) ? 0 : Integer.rotateLeft(getArtwork().getName().hashCode(), 4));
        result += ((getSecondaryArtwork() == null || getSecondaryArtwork().getName() == null) ? 0 : Integer.rotateLeft(getSecondaryArtwork().getName().hashCode(), 5));
        result += ((getVoiceCommands() == null) ? 0 : Integer.rotateLeft(getVoiceCommands().hashCode(), 6));
        return result;
    }

    /**
     * Uses our custom hashCode for ChoiceCell objects
     * @param o - The object to compare
     * @return boolean of whether the objects are the same or not
     */
    @Override
    public boolean equals(Object o) {
        // if this is the same memory address, its the same
        if (this == o) return true;
        // if this is not an instance of this class, not the same
        if (!(o instanceof ChoiceCell)) return false;

        ChoiceCell choiceCell = (ChoiceCell) o;
        // if we get to this point, create the hashes and compare them
        return hashCode() == choiceCell.hashCode();
    }

    /**
     * Overriding toString was throwing a warning in AS, so I changed the name for now
     * @return A string description of the cell, useful for debugging.
     */
    public String getDescription() {
        return "ChoiceCell: ID: " + this.choiceId + " Text" + text+ " - "+ secondaryText+" - "+ " - "+ tertiaryText+ " " +
                "| Artwork Names: "+ ((getArtwork() == null || getArtwork().getName() == null) ? "Primary Art null" : getArtwork().getName())
                + " - "+((getSecondaryArtwork() == null || getSecondaryArtwork().getName() == null) ? "Secondary Art null" : getSecondaryArtwork().getName()) +
                " Voice Commands Size: "+ ((getVoiceCommands() == null) ? 0 : getVoiceCommands().size());
    }

}
