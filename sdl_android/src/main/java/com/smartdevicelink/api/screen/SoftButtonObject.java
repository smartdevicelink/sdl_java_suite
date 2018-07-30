package com.smartdevicelink.api.screen;

import android.support.annotation.NonNull;
import android.util.Log;

import com.smartdevicelink.proxy.rpc.SoftButton;

import java.util.Collections;
import java.util.List;

/**
 * <strong>SoftButtonObject</strong> <br>
 * <p>
 * Note: This class must be accessed through the SdlManager->ScreenManager. Do not instantiate it by itself.
 */
public class SoftButtonObject {

    private static final String TAG = "SoftButtonObject";
    private String name;
    private List<SoftButtonState> states;
    private String currentStateName;
    private int buttonId;
    private SoftButtonManager softButtonManager;


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
        //this.listener = listener;
    }

    public SoftButtonObject(@NonNull String name, @NonNull SoftButtonState state) {
        this(name, Collections.singletonList(state), state.getName());
    }

    public boolean transitionToStateNamed(@NonNull String newStateName) {
        SoftButtonState newState = getStateNamed(newStateName);
        if (newState == null) {
            Log.e(TAG, String.format("Attempted to transition to state: %s on soft button object: %s but no state with that name was found", newStateName, this.name));
            return false;
        }
        Log.i(TAG, String.format("Transitioning soft button object %s to state %s", this.name, newStateName));
        //currentState = newState;
        currentStateName = newStateName;
        softButtonManager.update(null);
        return true;
    }

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

    public SoftButtonState getCurrentState() {
        SoftButtonState state = getStateNamed(currentStateName);
        if (state == null) {
            Log.e(TAG, String.format("Current state name : %s cannot be found for soft button object %s", currentStateName, this.name));
        }
        return state;
    }

    public SoftButton getCurrentStateSoftButton() {
        if (getCurrentState() == null || getCurrentState().getSoftButton() == null) {
            return null;
        }

        SoftButton softButton = getCurrentState().getSoftButton();
        softButton.setSoftButtonID(this.buttonId);
        //softButton.setListener(listener);

        return softButton;
    }

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

    protected void setSoftButtonManager(SoftButtonManager softButtonManager) {
        this.softButtonManager = softButtonManager;
    }

    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public List<SoftButtonState> getStates() {
        return states;
    }

    public void setStates(@NonNull List<SoftButtonState> states) {
        this.states = states;
    }

    public String getCurrentStateName() {
        return currentStateName;
    }

    public void setCurrentStateName(@NonNull String currentStateName) {
        this.currentStateName = currentStateName;
    }

    public Integer getButtonId() {
        return buttonId;
    }

    public void setButtonId(@NonNull Integer buttonId) {
        this.buttonId = buttonId;
    }
}
