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
        super(jsonObject);
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
}
