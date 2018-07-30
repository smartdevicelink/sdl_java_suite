package com.smartdevicelink.api.screen;

import android.support.annotation.NonNull;
import android.util.Log;

import com.smartdevicelink.proxy.rpc.SoftButton;

import java.util.Collections;
import java.util.List;

/**
 * <strong>SoftButtonObject</strong> <br>
 * SoftButtonObject define a button that can have multiple SoftButtonState values.<br>
 * The states of SoftButtonObject allow the developer to not have to manage multiple SoftButtons that have very similar functionality.<br>
 * For example, a repeat button in a music app can be thought of as one SoftButtonObject with three typical states: repeat off, repeat 1, and repeat on.<br>
 * @see SoftButtonState
 */
public class SoftButtonObject {

    private static final String TAG = "SoftButtonObject";
    private String name;
    private List<SoftButtonState> states;
    private String currentStateName;
    private int buttonId;
    private SoftButtonManager softButtonManager;

    /**
     * Create a new instance of the SoftButtonObject with multiple states
     * @param name a String value represents name of the object
     * @param states a list of SoftButtonState represents the SoftButtonState values for the object
     * @param initialStateName a String value represents the name for the initial state
     * Note: the initialStateName should match exactly the name of one of the states for the object. Otherwise an exception will be thrown.
     */
    public SoftButtonObject(@NonNull String name, @NonNull List<SoftButtonState> states, @NonNull String initialStateName) {

        // Make sure there aren't two states with the same name
        if (hasTwoStatesOfSameName(states)) {
            Log.e(TAG, "Two states have the same name in states list for soft button object");
            return;
        }

        this.name = name;
        this.states = states;
        currentStateName = initialStateName;
        this.buttonId = 0;
    }

    /**
     * Create a new instance of the SoftButtonObject with one state
     * @param name a String value represents name of the object
     * @param state a SoftButtonState represents state for the object
     */
    public SoftButtonObject(@NonNull String name, @NonNull SoftButtonState state) {
        this(name, Collections.singletonList(state), state.getName());
    }

    /**
     * Transition the SoftButtonObject to a specific state
     * @param newStateName a String value represents the name fo the state that we want to transition the SoftButtonObject to
     * @return a boolean value that represents whether the transition succeeded or failed
     */
    public boolean transitionToStateNamed(@NonNull String newStateName) {
        SoftButtonState newState = getStateNamed(newStateName);
        if (newState == null) {
            Log.e(TAG, String.format("Attempted to transition to state: %s on soft button object: %s but no state with that name was found", newStateName, this.name));
            return false;
        }
        Log.i(TAG, String.format("Transitioning soft button object %s to state %s", this.name, newStateName));
        currentStateName = newStateName;

        // Send a new Show RPC because the state has changed which means the actual SoftButton has changed
        softButtonManager.update(null);

        return true;
    }

    /**
     * Transition the SoftButtonObject to the next state
     */
    public void transitionToNextState() {
        String nextStateName = null;
        for (int i = 0; i < states.size(); i++) {
            if (states.get(i).getName().equals(currentStateName)) {
                if (i == (states.size() - 1)) {
                    nextStateName = states.get(0).getName();
                } else {
                    nextStateName = states.get(i + 1).getName();
                }
                break;
            }
        }
        if (nextStateName == null) {
            Log.e(TAG, String.format("Current state name : %s cannot be found for soft button object %s", currentStateName, this.name));
            return;
        }
        transitionToStateNamed(nextStateName);
    }

    /**
     * Get the current state for the SoftButtonObject
     * @return a SoftButtonState represents the current state
     */
    public SoftButtonState getCurrentState() {
        SoftButtonState state = getStateNamed(currentStateName);
        if (state == null) {
            Log.e(TAG, String.format("Current state name : %s cannot be found for soft button object %s", currentStateName, this.name));
        }
        return state;
    }

    /**
     * Get the SoftButton object for the current state
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
     * @param stateName a String value that represents the name of the state
     * @return a SoftButtonState object that represents the state that has the provided name
     */
    private SoftButtonState getStateNamed(String stateName) {
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
     * Set the SoftButtonManager
     * @param softButtonManager the SoftButtonManager object
     */
    protected void setSoftButtonManager(SoftButtonManager softButtonManager) {
        this.softButtonManager = softButtonManager;
    }

    /**
     * Get the name of the SoftButtonObject
     * @return a String that represents the name of the SoftButtonObject
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the SoftButtonObject
     * @param name a String that represents the name of the SoftButtonObject
     */
    public void setName(@NonNull String name) {
        this.name = name;
    }

    /**
     * Get the SoftButtonState list
     * @return a list of the object's soft button states
     */
    public List<SoftButtonState> getStates() {
        return states;
    }

    /**
     * Set the the SoftButtonState list
     * @param states a list of the object's soft button states
     */
    public void setStates(@NonNull List<SoftButtonState> states) {
        this.states = states;
    }

    /**
     * Get the name of the current state
     * @return a String that represents the name of the current state
     */
    public String getCurrentStateName() {
        return currentStateName;
    }

    /**
     * Set the name of the current state
     * @param currentStateName a String that represents the name of the current state
     */
    public void setCurrentStateName(@NonNull String currentStateName) {
        this.currentStateName = currentStateName;
    }

    /**
     * Get the dd of the SoftButtonObject
     * @return an int value that represents the id of the SoftButtonObject
     */
    public int getButtonId() {
        return buttonId;
    }

    /**
     * Set the id of the SoftButtonObject
     * @param buttonId an int value that represents the id of the SoftButtonObject
     */
    public void setButtonId(int buttonId) {
        this.buttonId = buttonId;
    }
}
