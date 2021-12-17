/*
 * Copyright (c) 2019, Livio, Inc.
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

import androidx.annotation.NonNull;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.proxy.rpc.OnButtonEvent;
import com.smartdevicelink.proxy.rpc.OnButtonPress;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.util.DebugTool;
import java.util.Collections;
import java.util.List;

/**
 * <strong>SoftButtonObject</strong> <br>
 * SoftButtonObject defines a button that can have multiple SoftButtonState values.<br>
 * The states of SoftButtonObject allow the developer to not have to manage multiple SoftButtons that have very similar functionality.<br>
 * For example, a repeat button in a music app can be thought of as one SoftButtonObject with three typical states: repeat off, repeat 1, and repeat on.<br>
 *
 * @see SoftButtonState
 */
public class SoftButtonObject implements Cloneable{
    private static final String TAG = "SoftButtonObject";
    static final int SOFT_BUTTON_ID_NOT_SET_VALUE = -1;
    static final int SOFT_BUTTON_ID_MIN_VALUE = 0;
    static final int SOFT_BUTTON_ID_MAX_VALUE = 65535;
    private String name;
    private List<SoftButtonState> states;
    private String currentStateName;
    private int buttonId;
    private OnEventListener onEventListener;
    private UpdateListener updateListener;

    /**
     * Create a new instance of the SoftButtonObject with multiple states
     *
     * @param name             a String value represents name of the object
     * @param states           a list of SoftButtonState represents the SoftButtonState values for the object. <strong>states should be unique for every SoftButtonObject. A SoftButtonState instance cannot be reused for multiple SoftButtonObjects.</strong>
     * @param initialStateName a String value represents the name for the initial state
     * @param onEventListener  a listener that has a callback that will be triggered when a button event happens
     *                         Note: the initialStateName should match exactly the name of one of the states for the object. Otherwise an exception will be thrown.
     */
    public SoftButtonObject(@NonNull String name, @NonNull List<SoftButtonState> states, @NonNull String initialStateName, OnEventListener onEventListener) {

        // If the list of states is empty, throw an error with DebugTool and return
        if (states.isEmpty()) {
            DebugTool.logError(TAG,"The state list is empty");
            return;
        }
        // Make sure there aren't two states with the same name
        if (hasTwoStatesOfSameName(states)) {
            DebugTool.logError(TAG, "Two states have the same name in states list for soft button object");
            return;
        }

        this.name = name;
        this.currentStateName = initialStateName;
        this.buttonId = SOFT_BUTTON_ID_NOT_SET_VALUE;
        this.onEventListener = onEventListener;
        this.states = states;
    }

    /**
     * Create a new instance of the SoftButtonObject with one state
     *
     * @param name            a String value represents name of the object
     * @param state           a SoftButtonState represents state for the object
     * @param onEventListener a listener that has a callback that will be triggered when a button event happens
     */
    public SoftButtonObject(@NonNull String name, @NonNull SoftButtonState state, OnEventListener onEventListener) {
        this(name, Collections.singletonList(state), state.getName(), onEventListener);
    }

    /**
     * Create a new instance of the SoftButtonObject with one state
     *
     * @param name            a String value represents name of the object
     * @param artwork         a SdlArtwork to be displayed on the button
     * @param onEventListener a listener that has a callback that will be triggered when a button event happens
     */
    public SoftButtonObject(@NonNull String name, @NonNull String text, SdlArtwork artwork, OnEventListener onEventListener) {
        this(name, Collections.singletonList(new SoftButtonState(name, text, artwork)), name, onEventListener);
    }

    /**
     * Transition the SoftButtonObject to a specific state
     *
     * @param newStateName a String value represents the name fo the state that we want to transition the SoftButtonObject to
     * @return a boolean value that represents whether the transition succeeded or failed
     */
    public boolean transitionToStateByName(@NonNull String newStateName) {
        SoftButtonState newState = getStateByName(newStateName);
        if (newState == null) {
            DebugTool.logError(TAG, String.format("Attempted to transition to state: %s on soft button object: %s but no state with that name was found", newStateName, this.name));
            return false;
        }

        if (states.size() == 1) {
            DebugTool.logWarning(TAG, "There's only one state, so no transitioning is possible!");
            return false;
        }

        DebugTool.logInfo(TAG, String.format("Transitioning soft button object %s to state %s", this.name, newStateName));
        currentStateName = newStateName;

        // Send a new Show RPC because the state has changed which means the actual SoftButton has changed
        if (updateListener != null) {
            updateListener.onUpdate();
        } else {
            DebugTool.logError(TAG, String.format("SoftButtonManager is not set for soft button object: %s. Update cannot be triggered", this.name));
        }

        return true;
    }

    /**
     * Transition the SoftButtonObject to the next state
     */
    public void transitionToNextState() {
        String nextStateName = null;
        for (int i = 0; i < states.size(); i++) {
            if (states.get(i).getName().equals(currentStateName)) {
                int nextStateNumber = (i == states.size() - 1) ? 0 : (i + 1);
                nextStateName = states.get(nextStateNumber).getName();
                break;
            }
        }
        if (nextStateName == null) {
            DebugTool.logError(TAG, String.format("Current state name : %s cannot be found for soft button object %s", currentStateName, this.name));
            return;
        }
        transitionToStateByName(nextStateName);
    }

    /**
     * Get the current state for the SoftButtonObject
     *
     * @return a SoftButtonState represents the current state
     */
    public SoftButtonState getCurrentState() {
        SoftButtonState state = getStateByName(currentStateName);
        if (state == null) {
            DebugTool.logError(TAG, String.format("Current state name : %s cannot be found for soft button object %s", currentStateName, this.name));
        }
        return state;
    }

    /**
     * Get the SoftButton object for the current state
     *
     * @return a SoftButton object that is associated with the current state
     */
    public SoftButton getCurrentStateSoftButton() {
        SoftButtonState currentState = getCurrentState();
        if (currentState == null || currentState.getSoftButton() == null) {
            return null;
        }

        SoftButton softButton = currentState.getSoftButton();
        softButton.setSoftButtonID(this.buttonId);
        return softButton;
    }

    /**
     * Find and get the SoftButtonState that has the provided name
     *
     * @param stateName a String value that represents the name of the state
     * @return a SoftButtonState object that represents the state that has the provided name
     */
    private SoftButtonState getStateByName(String stateName) {
        if (stateName != null && states != null) {
            for (SoftButtonState state : states) {
                if (state.getName().equals(stateName)) {
                    return state;
                }
            }
        }
        return null;
    }

    /**
     * Check if two SoftButtonState have the same name
     *
     * @param states a list of SoftButtonState
     * @return a boolean value that represents whether we have two states with the same name
     */
    private boolean hasTwoStatesOfSameName(List<SoftButtonState> states) {
        for (int i = 0; i < states.size(); i++) {
            String stateName = states.get(i).getName();
            for (int j = (i + 1); j < states.size(); j++) {
                if (states.get(j).getName().equals(stateName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Set the SoftButtonManager's update listener
     *
     * @param updateListener the SoftButtonManager.UpdateListener object
     */
    protected void setUpdateListener(UpdateListener updateListener) {
        this.updateListener = updateListener;
    }

    /**
     * Get the name of the SoftButtonObject
     *
     * @return a String that represents the name of the SoftButtonObject
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the SoftButtonObject
     *
     * @param name a String that represents the name of the SoftButtonObject
     */
    public void setName(@NonNull String name) {
        this.name = name;
    }

    /**
     * Get the SoftButtonState list
     *
     * @return a list of the object's soft button states
     */
    public List<SoftButtonState> getStates() {
        return states;
    }

    /**
     * Set the the SoftButtonState list
     *
     * @param states a list of the object's soft button states. <strong>states should be unique for every SoftButtonObject. A SoftButtonState instance cannot be reused for multiple SoftButtonObjects.</strong>
     */
    public void setStates(@NonNull List<SoftButtonState> states) {
        // If the list of states is empty, throw an error with DebugTool and return
        if (states.isEmpty()) {
            DebugTool.logError(TAG,"The state list is empty");
            return;
        }

        this.states = states;
    }

    /**
     * Get the name of the current state
     *
     * @return a String that represents the name of the current state
     */
    public String getCurrentStateName() {
        return currentStateName;
    }

    /**
     * Set the name of the current state
     *
     * @param currentStateName a String that represents the name of the current state
     */
    public void setCurrentStateName(@NonNull String currentStateName) {
        this.currentStateName = currentStateName;
    }

    /**
     * Get the id of the SoftButtonObject
     *
     * @return an int value that represents the id of the SoftButtonObject
     */
    public int getButtonId() {
        return buttonId;
    }

    /**
     * DO NOT USE! let the managers assign ID's. In next major version change this will be restricted to the library
     * Sets the id of the SoftButtonObject <br>
     * <strong>Note: If the developer did not set buttonId, the manager will automatically assign an id before the SoftButtons are sent to the head unit.
     * Please note that the manager may reuse ids from previous batch of SoftButtons that were already sent to the head unit</strong>
     *
     * @param buttonId an int value that represents the id of the SoftButtonObject
     */
    @Deprecated
    public void setButtonId(int buttonId) {
        if (buttonId < SOFT_BUTTON_ID_MIN_VALUE) {
            DebugTool.logError(TAG, "buttonId has to be equal or more than " + SOFT_BUTTON_ID_MIN_VALUE);
            return;
        }
        this.buttonId = buttonId;
    }

    /**
     * Get the event listener for the SoftButtonObject
     *
     * @return OnEventListener
     */
    public OnEventListener getOnEventListener() {
        return onEventListener;
    }

    /**
     * Set the event listener for the SoftButtonObject
     *
     * @param onEventListener a listener that has a callback that will be triggered when a button event happens
     */
    public void setOnEventListener(OnEventListener onEventListener) {
        this.onEventListener = onEventListener;
    }

    public interface OnEventListener {
        void onPress(SoftButtonObject softButtonObject, OnButtonPress onButtonPress);

        void onEvent(SoftButtonObject softButtonObject, OnButtonEvent onButtonEvent);
    }

    /**
     * A listener interface that is used by SoftButtonObject to request an update from SoftButtonManager
     */
    interface UpdateListener {
        /**
         * Requests an update from SoftButtonManager
         */
        void onUpdate();
    }

    /**
     * Used to compile hashcode for SoftButtonsObjects for use to compare in equals method
     *
     * @return Custom hashcode of SoftButtonObjects variables
     */
    @Override
    public int hashCode() {
        int result = 1;
        result += ((getName() == null) ? 0 : Integer.rotateLeft(getName().hashCode(), 1));
        result += ((getCurrentStateName() == null) ? 0 : Integer.rotateLeft(getCurrentStateName().hashCode(), 2));
        for (int i = 0; i < this.states.size(); i++) {
            result += ((getStates().get(i) == null) ? 0 : Integer.rotateLeft(getStates().get(i).hashCode(), i + 3));
        }
        return result;
    }

    /**
     * Uses our custom hashCode for SoftButtonObject objects
     *
     * @param o - The object to compare
     * @return boolean of whether the objects are the same or not
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        // if this is the same memory address, it's the same
        if (this == o) return true;
        // if this is not an instance of SoftButtonObject, not the same
        if (!(o instanceof SoftButtonObject)) return false;
        // return comparison
        return hashCode() == o.hashCode();
    }

    /**
     * Creates a deep copy of the object
     *
     * @return deep copy of the object, null if an exception occurred
     */
    @Override
    public SoftButtonObject clone() {
        try {
            SoftButtonObject softButtonObject = (SoftButtonObject) super.clone();
            return softButtonObject;
        } catch (CloneNotSupportedException e) {
            if (DebugTool.isDebugEnabled()) {
                throw new RuntimeException("Clone not supported by super class");
            }
        }
        return null;
    }
}
