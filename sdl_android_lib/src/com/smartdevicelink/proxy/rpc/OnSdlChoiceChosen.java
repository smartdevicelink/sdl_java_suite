package com.smartdevicelink.proxy.rpc;

import java.util.List;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.enums.TriggerSource;
import com.smartdevicelink.util.JsonUtils;

public class OnSdlChoiceChosen extends RPCNotification {
	public static final String KEY_SDL_CHOICE = "sdlChoice";
	public static final String KEY_TRIGGER_SOURCE = "triggerSource";
	
	private SdlChoice choice;
	private String triggerSource; // represents TriggerSource enum
	
	public class SdlSubMenu {
		private Integer _menuID = null;
		@SuppressWarnings("unused")
        private Integer _position = null;
		private String _menuName = null;
		
		// Constructor
		SdlSubMenu(Integer menuID, Integer position, String menuName) {
			_menuID = menuID;
			_position = position;
			_menuName = menuName;
		}
		
		// Restrict no-arg constructor
		@SuppressWarnings("unused")
        private SdlSubMenu() {}
		
		// Public Getters
		public Integer getMenuID() {
			return _menuID;
		}
		
		public String getMenuName() {
			return _menuName;
		}
		
		public String toString() {
			return _menuName;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((_menuID == null) ? 0 : _menuID.hashCode());
			result = prime * result + ((_menuName == null) ? 0 : _menuName.hashCode());
			result = prime * result + ((_position == null) ? 0 : _position.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) { 
				return true;
			}
			if (obj == null) { 
				return false;
			}
			if (getClass() != obj.getClass()) { 
				return false;
			}
			SdlSubMenu other = (SdlSubMenu) obj;
			if (!getOuterType().equals(other.getOuterType())) { 
				return false;
			}
			if (_menuID == null) {
				if (other._menuID != null) { 
					return false;
				}
			}
			else if (!_menuID.equals(other._menuID)) { 
				return false;
			}
			if (_menuName == null) {
				if (other._menuName != null) { 
					return false;
				}
			}
			else if (!_menuName.equals(other._menuName)) { 
				return false;
			}
			if (_position == null) {
				if (other._position != null) { 
					return false;
				}
			} 
			else if (!_position.equals(other._position)) { 
				return false;
			}
			return true;
		}

		private OnSdlChoiceChosen getOuterType() {
			return OnSdlChoiceChosen.this;
		}
	}
	
	public class SdlCommand {
		private Integer _commandID = null;
		private SdlSubMenu _parentSubMenu = null;
		@SuppressWarnings("unused")
        private Integer _position = null;
		private String _menuName = null;
		private List<String> _vrCommands = null;
		
		// Constructor
		SdlCommand(Integer commandID, SdlSubMenu parentSubMenu, Integer position, String menuName, List<String> vrCommands) {
			_commandID = commandID;
			_parentSubMenu = parentSubMenu;
			_position = position;
			_menuName = menuName;
			_vrCommands = vrCommands;
		}
		
		// Restrict no-arg constructor
		@SuppressWarnings("unused")
        private SdlCommand() {}
		
		// Public Getters
		public Integer getCommandID() {
			return _commandID;
		}
		
		public SdlSubMenu getParentSubMenu() {
			return _parentSubMenu;
		}
		
		public String getMenuName() {
			return _menuName;
		}
		
		public List<String> getVrCommands() {
			return _vrCommands;
		}
		
		public String toString() {
			return _menuName;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((_commandID == null) ? 0 : _commandID.hashCode());
			result = prime * result + ((_menuName == null) ? 0 : _menuName.hashCode());
			result = prime * result + ((_parentSubMenu == null) ? 0 : _parentSubMenu.hashCode());
			result = prime * result + ((_position == null) ? 0 : _position.hashCode());
			result = prime * result + ((_vrCommands == null) ? 0 : _vrCommands.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) { 
				return true;
			}
			if (obj == null) { 
				return false;
			}
			if (getClass() != obj.getClass()) { 
				return false;
			}
			SdlCommand other = (SdlCommand) obj;
			if (!getOuterType().equals(other.getOuterType())) { 
				return false;
			}
			if (_commandID == null) {
				if (other._commandID != null) { 
					return false;
				}
			}
			else if (!_commandID.equals(other._commandID)) { 
				return false;
			}
			if (_menuName == null) {
				if (other._menuName != null) { 
					return false;
				}
			} 
			else if (!_menuName.equals(other._menuName)) { 
				return false;
			}
			if (_parentSubMenu == null) {
				if (other._parentSubMenu != null) { 
					return false;
				}
			} 
			else if (!_parentSubMenu.equals(other._parentSubMenu)) { 
				return false;
			}
			if (_position == null) {
				if (other._position != null) { 
					return false;
				}
			} 
			else if (!_position.equals(other._position)) { 
				return false;
			}
			if (_vrCommands == null) {
				if (other._vrCommands != null) { 
					return false;
				}
			} 
			else if (!_vrCommands.equals(other._vrCommands)) { 
				return false;
			}
			return true;
		}

		private OnSdlChoiceChosen getOuterType() {
			return OnSdlChoiceChosen.this;
		}
	}
	
	public class SdlChoice {
		
		private Choice _choice = null;
		
		// Constructor
		SdlChoice(Choice choice) {
			_choice = choice;
		}
		
		public Choice getChoice() {
			return _choice;
		}
		
		public Integer getChoiceID() {
			return _choice.getChoiceID();
		}
		
		public String getMenuName() {
			return _choice.getMenuName();
		}
		
		public List<String> getVrCommands() {
			return _choice.getVrCommands();
		}
		
		public String toString() {
			return _choice.getMenuName();
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((_choice == null) ? 0 : _choice.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) { 
				return true;
			}
			if (obj == null) { 
				return false;
			}
			if (getClass() != obj.getClass()) { 
				return false;
			}
			SdlChoice other = (SdlChoice) obj;
			if (!getOuterType().equals(other.getOuterType())) { 
				return false;
			}
			if (_choice == null) {
				if (other._choice != null) { 
					return false;
				}
			} 
			else if (!_choice.equals(other._choice)) { 
				return false;
			}
			return true;
		}

		private OnSdlChoiceChosen getOuterType() {
			return OnSdlChoiceChosen.this;
		}
	}
	
	public class SdlChoiceSet {
		private Integer _choiceSetID = null;
		private List<SdlChoice> _choiceSet = null;
		
		// Constructor
		SdlChoiceSet(Integer choiceSetID, List<SdlChoice> choiceSet) {
			_choiceSetID = choiceSetID;
			_choiceSet = choiceSet;
		}
		
		public Integer getChoiceSetID() {
			return _choiceSetID;
		}
		
		public List<SdlChoice> getChoiceSet() {
			return _choiceSet;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((_choiceSet == null) ? 0 : _choiceSet.hashCode());
			result = prime * result + ((_choiceSetID == null) ? 0 : _choiceSetID.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) { 
				return true;
			}
			if (obj == null) { 
				return false;
			}
			if (getClass() != obj.getClass()) { 
				return false;
			}
			SdlChoiceSet other = (SdlChoiceSet) obj;
			if (!getOuterType().equals(other.getOuterType())) { 
				return false;
			}
			if (_choiceSet == null) {
				if (other._choiceSet != null) { 
					return false;
				}
			}
			else if (!_choiceSet.equals(other._choiceSet)) { 
				return false;
			}
			if (_choiceSetID == null) {
				if (other._choiceSetID != null) { 
					return false;
				}
			} 
			else if (!_choiceSetID.equals(other._choiceSetID)) { 
				return false;
			}
			return true;
		}

		private OnSdlChoiceChosen getOuterType() {
			return OnSdlChoiceChosen.this;
		}
	}
	
	public OnSdlChoiceChosen() {
		super(FunctionID.ON_SDL_CHOICE_CHOSEN);
	}

    /**
     * Creates a OnSdlChoiceChosen object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public OnSdlChoiceChosen(JSONObject jsonObject){
        super(com.smartdevicelink.proxy.rpc.enums.SdlCommand.ON_SDL_CHOICE_CHOSEN, jsonObject);
        jsonObject = getParameters(jsonObject);
        switch(sdlVersion){
        default:
            this.triggerSource = JsonUtils.readStringFromJsonObject(jsonObject, KEY_TRIGGER_SOURCE);
            
            //TODO: this needs testing and modification due to duplicate classes
            JSONObject temp = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_SDL_CHOICE);
            if(temp != null){
                Choice choice = new Choice(temp);
                this.choice = new SdlChoice(choice);
            }
            break;
        }
    }
	
    public SdlChoice getSdlChoice() {
    	return this.choice;
    }
    
    public void setSdlChoice(SdlChoice sdlChoice) {
    	this.choice = sdlChoice;
    }
    
    public TriggerSource getTriggerSource() {
        return TriggerSource.valueForJsonName(this.triggerSource, sdlVersion);
    }
    
    public void setTriggerSource( TriggerSource triggerSource ) {
        this.triggerSource = (triggerSource == null) ? null : triggerSource.getJsonName(sdlVersion);
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_TRIGGER_SOURCE, this.triggerSource);
            JsonUtils.addToJsonObject(result, KEY_SDL_CHOICE, 
                    (this.choice == null) ? null : this.choice.getChoice().getJsonParameters(sdlVersion));
            break;
        }
        
        return result;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((choice == null) ? 0 : choice.hashCode());
		result = prime * result + ((triggerSource == null) ? 0 : triggerSource.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) { 
			return true;
		}
		if (obj == null) { 
			return false;
		}
		if (getClass() != obj.getClass()) { 
			return false;
		}
		OnSdlChoiceChosen other = (OnSdlChoiceChosen) obj;
		if (choice == null) {
			if (other.choice != null) { 
				return false;
			}
		} else if (!choice.equals(other.choice)) { 
			return false;
		}
		if (triggerSource == null) {
			if (other.triggerSource != null) { 
				return false;
			}
		} else if (!triggerSource.equals(other.triggerSource)) { 
			return false;
		}
		return true;
	}
}
