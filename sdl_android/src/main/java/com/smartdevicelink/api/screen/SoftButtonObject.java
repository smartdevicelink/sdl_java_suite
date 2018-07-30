package com.smartdevicelink.api.screen;

import android.util.Log;

import com.smartdevicelink.api.CompletionListener;
import com.smartdevicelink.proxy.rpc.SoftButton;

import java.util.Collections;
import java.util.List;

/**
 * <strong>SoftButtonObject</strong> <br>
 *
 * Note: This class must be accessed through the SdlManager->ScreenManager. Do not instantiate it by itself.
 */
public class SoftButtonObject {

    private static final String TAG = "SoftButtonObject";
    private String name;
	private List<SoftButtonState> states;
	private SoftButtonState currentState;
	private String currentStateName;
	private int buttonId;
	private SoftButtonManager softButtonManager;

	//private CompletionListener listener;
	//private SoftButton currentStateSoftButton;



	public SoftButtonObject(String name, List<SoftButtonState> states, String initialStateName) {

		// Make sure there aren't two states with the same name
		if (hasTwoStatesOfSameName(states)){
			Log.e(TAG, String.format("Two states have the same name in states list for soft button object: ", this.name));
			return;
		}

		this.name = name;
		this.states = states;
		currentStateName = initialStateName;
		this.buttonId = 0;
		//this.listener = listener;
	}

	public SoftButtonObject(String name, SoftButtonState state){
		this(name, Collections.singletonList(state), state.getName());
	}

	protected boolean transitionToStateNamed(String newStateName){
		SoftButtonState newState = getStateNamed(newStateName);
		if (newState == null){
			Log.e(TAG, String.format("Attempted to transition to state: %s on soft button object: %s but no state with that name was found", newStateName, this.name));
			return false;
		}
		Log.i(TAG, String.format("Transitioning soft button object %s to state %s", this.name, newStateName));
		currentState = newState;
		currentStateName = newStateName;
		softButtonManager.update(true,null);
		return true;
	}

	protected void transitionToNextState() {
		String nextStateName = null;
		for (int i = 0; i < states.size(); i++) {
			if (states.get(i).getName().equals(currentStateName)){
				if (i == (states.size() - 1)){
					nextStateName = states.get(0).getName();
				} else {
					nextStateName = states.get(i + 1).getName();
				}
				break;
			}
		}
		if (nextStateName == null){
			Log.e(TAG, String.format("Current state name : %s cannot be found for soft button object %s", currentStateName, this.name));
			return;
		}
		transitionToStateNamed(nextStateName);
	}

	protected SoftButtonState getCurrentState() {
		SoftButtonState state = getStateNamed(currentStateName);
		if (state == null){
			Log.e(TAG, String.format("Current state name : %s cannot be found for soft button object %s", currentStateName, this.name));
		}
		return state;
	}

	protected SoftButton getCurrentStateSoftButton(){
		SoftButton softButton = getCurrentState().getSoftButton();
		softButton.setSoftButtonID(this.buttonId);
		//softButton.setListener(listener);

		return softButton;
	}

	// used by screen manger ///
	private SoftButtonState getStateNamed(String stateName) {
		for (SoftButtonState state : states){
			if (state.getName().equals(stateName)){
				return state;
			}
		}
		return null;
	}

	private boolean hasTwoStatesOfSameName(List<SoftButtonState> states){
		for (int i = 0; i < states.size(); i++) {
			String stateName = states.get(i).getName();
			for (int j = (i + 1); j < states.size(); j++) {
				if (states.get(j).getName().equals(stateName)){
					return true;
				}
			}
		}
		return false;
	}

	protected SoftButtonManager getSoftButtonManager() {
		return softButtonManager;
	}

	protected void setSoftButtonManager(SoftButtonManager softButtonManager) {
		this.softButtonManager = softButtonManager;
	}

	protected Integer getButtonId() {
		return buttonId;
	}

	protected void setButtonId(Integer buttonId) {
		this.buttonId = buttonId;
	}







	protected String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}

	protected String getCurrentStateName() {
		return currentStateName;
	}

	protected void setCurrentStateName(String currentStateName) {
		this.currentStateName = currentStateName;
	}

	protected List<SoftButtonState> getStates() {
		return states;
	}

	protected void setStates(List<SoftButtonState> states) {
		this.states = states;
	}

	protected void setCurrentState(SoftButtonState currentState) {
		this.currentState = currentState;
	}





}
